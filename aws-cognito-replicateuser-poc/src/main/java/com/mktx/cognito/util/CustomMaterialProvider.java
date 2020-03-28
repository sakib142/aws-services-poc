package com.mktx.cognito.util;

import static com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.WrappedRawMaterials.CONTENT_KEY_ALGORITHM;
import static com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.WrappedRawMaterials.ENVELOPE_KEY;
import static com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.WrappedRawMaterials.KEY_WRAPPING_ALGORITHM;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionContext;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.DecryptionMaterials;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.EncryptionMaterials;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.SymmetricRawMaterials;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.materials.WrappedRawMaterials;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.DirectKmsMaterialProvider;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.EncryptionMaterialsProvider;
import com.amazonaws.services.dynamodbv2.datamodeling.internal.Hkdf;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.VersionInfoUtils;

public class CustomMaterialProvider implements EncryptionMaterialsProvider {
	private static final String VERSION_STRING = "1.0";
	private static final String USER_AGENT = CustomMaterialProvider.class.getName() + "/" + VERSION_STRING + "/"
			+ VersionInfoUtils.getVersion();
	private static final String COVERED_ATTR_CTX_KEY = "aws-mktx-ec-attr";
	private static final String SIGNING_KEY_ALGORITHM = "amzn-ddb-sig-alg";
	private static final String TABLE_NAME_EC_KEY = "*aws-mktx-table*";

	private static final String DEFAULT_ENC_ALG = "AES/256";
	private static final String DEFAULT_SIG_ALG = "HmacSHA256/256";
	private static final String KEY_COVERAGE = "*keys*";
	private static final String KDF_ALG = "HmacSHA256";
	private static final String KDF_SIG_INFO = "Signing";
	private String KDF_ENC_INFO = "Encryption";

	private final String encryptionKeyId;
	private final Map<String, String> description;
	private final String dataKeyAlg;
	private final int dataKeyLength;
	private final String dataKeyDesc;
	private final String sigKeyAlg;
	private final int sigKeyLength;
	private final String sigKeyDesc;

	public CustomMaterialProvider(String encryptionKeyId, Map<String, String> materialDescription) {

		this.encryptionKeyId = encryptionKeyId;
		this.KDF_ENC_INFO = encryptionKeyId; // overriding default encryption key
		this.description = materialDescription != null ? Collections.unmodifiableMap(new HashMap<>(materialDescription))
				: Collections.<String, String>emptyMap();

		dataKeyDesc = description.containsKey(WrappedRawMaterials.CONTENT_KEY_ALGORITHM)
				? description.get(WrappedRawMaterials.CONTENT_KEY_ALGORITHM)
				: DEFAULT_ENC_ALG;

		String[] parts = dataKeyDesc.split("/", 2);
		this.dataKeyAlg = parts[0];
		this.dataKeyLength = parts.length == 2 ? Integer.parseInt(parts[1]) : 256;

		sigKeyDesc = description.containsKey(SIGNING_KEY_ALGORITHM) ? description.get(SIGNING_KEY_ALGORITHM)
				: DEFAULT_SIG_ALG;

		parts = sigKeyDesc.split("/", 2);
		this.sigKeyAlg = parts[0];
		this.sigKeyLength = parts.length == 2 ? Integer.parseInt(parts[1]) : 256;
	}

	public CustomMaterialProvider(String encryptionKeyId) {
		this(encryptionKeyId, Collections.<String, String>emptyMap());
	}

	@Override
	public DecryptionMaterials getDecryptionMaterials(EncryptionContext context) {
		final Map<String, String> materialDescription = context.getMaterialDescription();

		final Map<String, String> ec = new HashMap<>();
		final String providedEncAlg = materialDescription.get(CONTENT_KEY_ALGORITHM);
		final String providedSigAlg = materialDescription.get(SIGNING_KEY_ALGORITHM);

		ec.put("*" + CONTENT_KEY_ALGORITHM + "*", providedEncAlg);
		ec.put("*" + SIGNING_KEY_ALGORITHM + "*", providedSigAlg);

		populateKmsEcFromEc(context, ec);

		DecryptRequest request = appendUserAgent(new DecryptRequest());
		request.setCiphertextBlob(ByteBuffer.wrap(Base64.decode(materialDescription.get(ENVELOPE_KEY))));
		request.setEncryptionContext(ec);
		// final DecryptResult decryptResult = kms.decrypt(request);
		// validateEncryptionKeyId(decryptResult.getKeyId(), context);
		ByteBuffer bf = ByteBuffer.allocate(100);
		final Hkdf kdf;
		try {
			kdf = Hkdf.getInstance(KDF_ALG);
		} catch (NoSuchAlgorithmException e) {
			throw new DynamoDBMappingException(e);
		}
		kdf.init(toArray(bf));

		final String[] encAlgParts = providedEncAlg.split("/", 2);
		int encLength = encAlgParts.length == 2 ? Integer.parseInt(encAlgParts[1]) : 256;
		final String[] sigAlgParts = providedSigAlg.split("/", 2);
		int sigLength = sigAlgParts.length == 2 ? Integer.parseInt(sigAlgParts[1]) : 256;

		final SecretKey encryptionKey = new SecretKeySpec(kdf.deriveKey(KDF_ENC_INFO, encLength / 8), encAlgParts[0]);
		final SecretKey macKey = new SecretKeySpec(kdf.deriveKey(KDF_SIG_INFO, sigLength / 8), sigAlgParts[0]);

		return new SymmetricRawMaterials(encryptionKey, macKey, materialDescription);
	}

	@Override
	public EncryptionMaterials getEncryptionMaterials(EncryptionContext context) {
		final Map<String, String> ec = new HashMap<>();
		ec.put("*" + CONTENT_KEY_ALGORITHM + "*", dataKeyDesc);
		ec.put("*" + SIGNING_KEY_ALGORITHM + "*", sigKeyDesc);
		populateKmsEcFromEc(context, ec);

		final String keyId = selectEncryptionKeyId(context);
		if (StringUtils.isNullOrEmpty(keyId)) {
			throw new DynamoDBMappingException("Encryption key id is empty.");
		}

		final GenerateDataKeyRequest req = appendUserAgent(new GenerateDataKeyRequest());
		req.setKeyId(keyId);
		// NumberOfBytes parameter is used because we're not using this key as an
		// AES-256 key,
		// we're using it as an HKDF-SHA256 key.
		req.setNumberOfBytes(256 / 8);
		req.setEncryptionContext(ec);
		ByteBuffer bf = ByteBuffer.allocate(100);

		// final GenerateDataKeyResult dataKeyResult = kms.generateDataKey(req);

		final Map<String, String> materialDescription = new HashMap<>();
		materialDescription.putAll(description);
		materialDescription.put(COVERED_ATTR_CTX_KEY, KEY_COVERAGE);
		materialDescription.put(KEY_WRAPPING_ALGORITHM, "kms");
		materialDescription.put(CONTENT_KEY_ALGORITHM, dataKeyDesc);
		materialDescription.put(SIGNING_KEY_ALGORITHM, sigKeyDesc);
		materialDescription.put(ENVELOPE_KEY, Base64.encodeAsString(toArray(bf)));

		final Hkdf kdf;
		try {
			kdf = Hkdf.getInstance(KDF_ALG);
		} catch (NoSuchAlgorithmException e) {
			throw new DynamoDBMappingException(e);
		}

		kdf.init(toArray(bf));

		final SecretKey encryptionKey = new SecretKeySpec(kdf.deriveKey(KDF_ENC_INFO, dataKeyLength / 8), dataKeyAlg);
		final SecretKey signatureKey = new SecretKeySpec(kdf.deriveKey(KDF_SIG_INFO, sigKeyLength / 8), sigKeyAlg);
		return new SymmetricRawMaterials(encryptionKey, signatureKey, materialDescription);
	}

	/**
	 * Get encryption key id.
	 * 
	 * @return encryption key id.
	 */
	protected String getEncryptionKeyId() {
		return this.encryptionKeyId;
	}

	/**
	 * Select encryption key id to be used to generate data key. The default
	 * implementation of this method returns
	 * {@link DirectKmsMaterialProvider#encryptionKeyId}.
	 * 
	 * @param context encryption context.
	 * @return the encryptionKeyId.
	 * @throws DynamoDBMappingException when we fails to select a valid encryption
	 *                                  key id.
	 */
	protected String selectEncryptionKeyId(EncryptionContext context) throws DynamoDBMappingException {
		return getEncryptionKeyId();
	}

	/**
	 * Validate the encryption key id. The default implementation of this method
	 * does nothing.
	 * 
	 * @param encryptionKeyId encryption key id from {@link DecryptResult}.
	 * @param context         encryption context.
	 * @throws DynamoDBMappingException when encryptionKeyId is invalid.
	 */
	protected void validateEncryptionKeyId(String encryptionKeyId, EncryptionContext context)
			throws DynamoDBMappingException {
		// No action taken.
	}

	/**
	 * Extracts relevant information from {@code context} and uses it to populate
	 * fields in {@code kmsEc}. Currently, these fields are:
	 * <dl>
	 * <dt>{@code HashKeyName}</dt>
	 * <dd>{@code HashKeyValue}</dd>
	 * <dt>{@code RangeKeyName}</dt>
	 * <dd>{@code RangeKeyValue}</dd>
	 * <dt>{@link #TABLE_NAME_EC_KEY}</dt>
	 * <dd>{@code TableName}</dd>
	 */
	private static void populateKmsEcFromEc(EncryptionContext context, Map<String, String> mktxEc) {
		final String hashKeyName = context.getHashKeyName();
		if (hashKeyName != null) {
			final AttributeValue hashKey = context.getAttributeValues().get(hashKeyName);
			if (hashKey.getN() != null) {
				mktxEc.put(hashKeyName, hashKey.getN());
			} else if (hashKey.getS() != null) {
				mktxEc.put(hashKeyName, hashKey.getS());
			} else if (hashKey.getB() != null) {
				mktxEc.put(hashKeyName, Base64.encodeAsString(toArray(hashKey.getB())));
			} else {
				throw new UnsupportedOperationException(
						"DirectKmsMaterialProvider only supports String, Number, and Binary HashKeys");
			}
		}
		final String rangeKeyName = context.getRangeKeyName();
		if (rangeKeyName != null) {
			final AttributeValue rangeKey = context.getAttributeValues().get(rangeKeyName);
			if (rangeKey.getN() != null) {
				mktxEc.put(rangeKeyName, rangeKey.getN());
			} else if (rangeKey.getS() != null) {
				mktxEc.put(rangeKeyName, rangeKey.getS());
			} else if (rangeKey.getB() != null) {
				mktxEc.put(rangeKeyName, Base64.encodeAsString(toArray(rangeKey.getB())));
			} else {
				throw new UnsupportedOperationException(
						"DirectKmsMaterialProvider only supports String, Number, and Binary RangeKeys");
			}
		}

		final String tableName = context.getTableName();
		if (tableName != null) {
			mktxEc.put(TABLE_NAME_EC_KEY, tableName);
		}
	}

	private static byte[] toArray(final ByteBuffer buff) {
		final ByteBuffer dup = buff.asReadOnlyBuffer();
		byte[] result = new byte[dup.remaining()];
		dup.get(result);
		return result;
	}

	private static <X extends AmazonWebServiceRequest> X appendUserAgent(final X request) {
		request.getRequestClientOptions().appendUserAgent(USER_AGENT);
		return request;
	}

	@Override
	public void refresh() {
		// No action needed
	}
}
