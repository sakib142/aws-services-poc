package com.trax.ems.security.utils;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

@Component
public class EmsBatchProcessorUtil {

	private String batchName;
	private Integer batchSize;
	private String batchStartTimestamp;
	private String batchEndTimestamp;
	private BigInteger noOfSecuritiesProcessed;
	private Integer page;
	private Boolean isCurrentRunInprogress;

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public String getBatchStartTimestamp() {
		return batchStartTimestamp;
	}

	public void setBatchStartTimestamp(String batchStartTimestamp) {
		this.batchStartTimestamp = batchStartTimestamp;
	}

	public String getBatchEndTimestamp() {
		return batchEndTimestamp;
	}

	public void setBatchEndTimestamp(String batchEndTimestamp) {
		this.batchEndTimestamp = batchEndTimestamp;
	}

	public BigInteger getNoOfSecuritiesProcessed() {
		return noOfSecuritiesProcessed;
	}

	public void setNoOfSecuritiesProcessed(BigInteger noOfSecuritiesProcessed) {
		this.noOfSecuritiesProcessed = noOfSecuritiesProcessed;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Boolean getIsCurrentRunInprogress() {
		return isCurrentRunInprogress;
	}

	public void setIsCurrentRunInprogress(Boolean isCurrentRunInprogress) {
		this.isCurrentRunInprogress = isCurrentRunInprogress;
	}

}
