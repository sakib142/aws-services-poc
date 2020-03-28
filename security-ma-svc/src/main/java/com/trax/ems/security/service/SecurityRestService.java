package com.trax.ems.security.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trax.ems.security.datasource.blink.domain.InstrumentMasterSecurity;
import com.trax.ems.security.datasource.blink.repo.InstrumentMasterSecurityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class SecurityRestService {

	@Autowired
	InstrumentMasterSecurityRepository instrumentMasterSecurityRepository;

	List<InstrumentMasterSecurity> instrumentMasterSecurity;

	@Value("${batch.timestamp.format}")
	private String TIMESTAMP_FORMAT;

	public List<InstrumentMasterSecurity> getSecurityDetails(String lastExecTimestamp, String currentTimestamp,
			String page, String batchSize) throws ParseException {
		log.info("SecurityRestService.getInstrumentDataSecurity()+");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		Instant curTimeStamp = simpleDateFormat.parse(currentTimestamp.trim()).toInstant();
		Instant lasTimeStamp = simpleDateFormat.parse(lastExecTimestamp.trim()).toInstant();
		Integer curBatchSize = Integer.parseInt(batchSize);
		int curPage = Integer.parseInt(page);

		log.info("StartTime:" + lastExecTimestamp + "\n " + "EndTime  :" + currentTimestamp + "\n" + "Page :" + page
				+ "\n" + "BatchSize  :" + batchSize);
		if (curBatchSize == null || curBatchSize == 0) {
			curBatchSize = 1000;
		}
		Pageable pageWithBatchElements = PageRequest.of(curPage, curBatchSize);

		if (!CollectionUtils.isEmpty(instrumentMasterSecurity = instrumentMasterSecurityRepository
				.getInstruments(lasTimeStamp, curTimeStamp, pageWithBatchElements))
				&& curBatchSize > instrumentMasterSecurityRepository
						.getInstruments(lasTimeStamp, curTimeStamp, pageWithBatchElements).size()) {
			log.info("Pageable siz" + instrumentMasterSecurity.size());
			instrumentMasterSecurity = instrumentMasterSecurityRepository.getInstruments(lasTimeStamp, curTimeStamp,
					pageWithBatchElements);
			log.info("Total Elemennts are :" + instrumentMasterSecurityRepository
					.getInstruments(lasTimeStamp, curTimeStamp, pageWithBatchElements).size());
		}
		log.info("SecurityRestService.getInstrumentDataSecurity()-");
		return instrumentMasterSecurity;
	}
}
