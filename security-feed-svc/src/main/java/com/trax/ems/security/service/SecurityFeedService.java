package com.trax.ems.security.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.trax.ems.security.constants.EmsEnabledVenue;
import com.trax.ems.security.datasource.ems.domain.EmsBatchExecution;
import com.trax.ems.security.datasource.ems.domain.EmsInstrument;
import com.trax.ems.security.datasource.ems.domain.EmsInstrumentVenue;
import com.trax.ems.security.datasource.ems.domain.EmsInstrumentVenueEmbedded;
import com.trax.ems.security.datasource.ems.domain.EmsProduct;
import com.trax.ems.security.datasource.ems.domain.EmsProductInstrumentEmbedded;
import com.trax.ems.security.datasource.ems.domain.EmsProductInstruments;
import com.trax.ems.security.datasource.ems.domain.EmsVenue;
import com.trax.ems.security.datasource.ems.repo.EmsBatchExecutionRespository;
import com.trax.ems.security.datasource.ems.repo.EmsInstrumentProductRepository;
import com.trax.ems.security.datasource.ems.repo.EmsInstrumentRepository;
import com.trax.ems.security.datasource.ems.repo.EmsMarketSegmentRepository;
import com.trax.ems.security.datasource.ems.repo.EmsProductInstrumentRepository;
import com.trax.ems.security.datasource.ems.repo.EmsUtilCalendarRepository;
import com.trax.ems.security.datasource.ems.repo.EmsVenueRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class SecurityFeedService {

	@Autowired
	private EmsInstrumentRepository emsInstrumentRepository;
	@Autowired
	private EmsUtilCalendarRepository emsUtilCalendarRepository;
	@Autowired
	private EmsProductInstrumentRepository emsProductInstrumentRepository;
	@Autowired
	private EmsInstrumentProductRepository emsInstrumentProductRepository;
	@Autowired
	private EmsMarketSegmentRepository emsMarketSegmentRepository;
	@Autowired
	private EmsVenueRepository emsVenueRepository;
	@Autowired
	EmsBatchExecutionRespository emsBatchExecutionRespository;

	@Value("${batch.noruntmstmp}")
	String noRunTmstmp;

	@Value("${batch.name}")
	String emsBatchName;

	@Value("${batch.timestamp.format}")
	private String TIMESTAMP_FORMAT;

	@Transactional
	public void feedSecurityDetails(List<EmsInstrument> emsInstrument) throws Exception {
		log.info("SecurityFeedService.process()+");

		List<EmsVenue> emsVenus = emsVenueRepository.findAll();
		EmsInstrumentVenue emsInsVenue = null;
		Set<EmsInstrumentVenue> emsInsVenueList = null;
		EmsProductInstrumentEmbedded emsProductInstrumentEmbedded = null;

		for (EmsInstrument instrument : emsInstrument) {
			emsInsVenueList = new HashSet<>();

			Set<EmsProduct> emsProd = instrument.getMarketSegment().getInstrumentProducts();
			instrument.getMarketSegment().setInstrumentProducts(null);
			instrument.setEmsInstrumentVenue(null);
			emsMarketSegmentRepository.save(instrument.getMarketSegment());
			for (EmsVenue emsVenue : emsVenus) {
				emsInsVenue = assignInstrumentVenue(instrument, emsVenue);
				if (emsInsVenue.getId() != null)
					emsInsVenueList.add(emsInsVenue);
			}
			if (emsInsVenueList.size() != 0)
				instrument.setEmsInstrumentVenue(emsInsVenueList);
			for (EmsProduct prod : emsProd) {
				emsUtilCalendarRepository.save(prod.getUtilCalendar());
				Set<EmsProductInstruments> EmsProductInstruments = prod.getInstrumentProductInstruments();
				prod.setInstrumentProductInstruments(null);
				emsInstrumentProductRepository.save(prod);
				emsInstrumentRepository.save(instrument);

				for (EmsProductInstruments prodInst : EmsProductInstruments) {
					emsProductInstrumentEmbedded = new EmsProductInstrumentEmbedded();
					emsProductInstrumentEmbedded.setInstrumentId(instrument.getInstrumentId());
					emsProductInstrumentEmbedded.setProductId(prod.getProductId());
					prodInst.setId(emsProductInstrumentEmbedded);
					prodInst.setInstrumentMasterSecurity(instrument);
					prodInst.setInstrumentProduct(prod);
					emsProductInstrumentRepository.save(prodInst);
				}
			}
		}
		log.info("SecurityFeedService.process()-");
	}

	/**
	 * @param instrument
	 * @param emsVenue
	 * @return
	 */
	private EmsInstrumentVenue assignInstrumentVenue(EmsInstrument instrument, EmsVenue emsVenue) {
		EmsInstrumentVenue emsInsVenue;
		EmsInstrumentVenueEmbedded emsInstrumentVenueEmbeddedId;
		emsInsVenue = new EmsInstrumentVenue();
		emsInstrumentVenueEmbeddedId = new EmsInstrumentVenueEmbedded();
		if ((instrument.getOnMAELVenue().equals("Y") && emsVenue.getName().equals(EmsEnabledVenue.ON_MAEL))
				|| (instrument.getOnMANLVenue().equals("Y") && emsVenue.getName().equals(EmsEnabledVenue.ON_MANL))
				|| (instrument.getOnMASGVenue().equals("Y") && emsVenue.getName().equals(EmsEnabledVenue.ON_MASG))
				|| (instrument.getOnMTXXVenue().equals("Y") && emsVenue.getName().equals(EmsEnabledVenue.ON_MTXX))

		) {
			emsInstrumentVenueEmbeddedId.setEmsInstrumentId(instrument.getInstrumentId());
			emsInstrumentVenueEmbeddedId.setEmsVenueId(emsVenue.getId());
			emsInsVenue.setId(emsInstrumentVenueEmbeddedId);
			emsInsVenue.setEmsInstrument(instrument);
			emsInsVenue.setEmsVenue(emsVenue);

		}
		return emsInsVenue;
	}

	public Instant getLastExecutionTime(String batchName) {

		log.info("SecurityFeedService.getLastExecutionTime()+");
		Optional<EmsBatchExecution> optEmsBatchExecution = emsBatchExecutionRespository.findById(batchName);

		Instant excEndTime = null;
		if (optEmsBatchExecution.isPresent()) {
			EmsBatchExecution emsBatchExecution = optEmsBatchExecution.get();
			excEndTime = emsBatchExecution.getExecutionEndTime();
		} else {
			SimpleDateFormat dateformat = new SimpleDateFormat(TIMESTAMP_FORMAT);
			Date date;
			try {
				date = dateformat.parse(noRunTmstmp);
				excEndTime = date.toInstant();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		log.info("SecurityFeedService.getLastExecutionTime()-");
		return excEndTime;
	}

	public void updateBatchExecutionDetails(String batchStartTimestamp, String batchEndTimestamp,
			BigInteger noOfInstrumentProcesssed) {
		log.info("SecurityFeedService.updateBatchExecutionDetails()+");

		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
		Instant batchStartTmstmp = null;
		Instant batchEndTmstmp = null;
		try {
			log.info("batchStartTimestamp Before" + batchStartTimestamp);
			log.info("batchEndTimestamp Before" + batchEndTimestamp);

			Date batchStartDate = sdf.parse(batchStartTimestamp);
			batchStartTmstmp = batchStartDate.toInstant();

			Date batchEndDate = sdf.parse(batchEndTimestamp);
			log.info("batchEndDate After" + batchEndDate);
			batchEndTmstmp = batchEndDate.toInstant();

			log.info("batchStartTmstmp After" + batchStartTmstmp);
			log.info("batchEndTmstmp After" + batchEndTmstmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmsBatchExecution emsBatchExecution = new EmsBatchExecution();
		emsBatchExecution.setBatchName(emsBatchName);
		emsBatchExecution.setExecutionStartTime(batchStartTmstmp);
		emsBatchExecution.setExecutionEndTime(batchEndTmstmp);
		emsBatchExecution.setNoOfInstrumentsProcessed(noOfInstrumentProcesssed);
		emsBatchExecutionRespository.save(emsBatchExecution);
		log.info("SecurityFeedService.updateBatchExecutionDetails()-");
	}

}
