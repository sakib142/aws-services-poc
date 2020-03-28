
  package com.mktx.websocket.model;
  
public class AuthorizerResponse {

	private String principalId;
	private AuthorizationPolicy policyDocument;
//	private AuthorizationContext context;

	public AuthorizerResponse() {
	}

	public AuthorizerResponse(String id) {
		this.principalId = id;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String id) {
		this.principalId = id;
	}

	public AuthorizationPolicy getPolicyDocument() {
		return policyDocument;
	}

	public void setPolicyDocument(AuthorizationPolicy doc) {
		this.policyDocument = doc;
	}

	/*public AuthorizationContext getContext() {
		return context;
	}

	public void setContext(AuthorizationContext context) {
		this.context = context;
	}*/
}
 