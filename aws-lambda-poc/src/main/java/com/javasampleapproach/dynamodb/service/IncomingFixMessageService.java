package com.javasampleapproach.dynamodb.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.dynamodb.model.IncomingFixMessage;
import com.javasampleapproach.dynamodb.repo.IncomingFixMessageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IncomingFixMessageService {

	@Autowired
	private IncomingFixMessageRepository incomingFixMessageRepository;

	private IncomingFixMessage incomingFixMessage;

	public void setFixMessage(String inFixMessage) {

		incomingFixMessage = new IncomingFixMessage();
		log.info("MarketDepthService.setFixMessage()+");
		List<IncomingFixMessage> incomingFixMessageList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			incomingFixMessage.setMsgText(inFixMessage);
			incomingFixMessage.setTimeStamp(Instant.now().toString());
			incomingFixMessageList.add(incomingFixMessage);
		}

		incomingFixMessageRepository.saveAll(incomingFixMessageList);

		log.info("MarketDepthService.setFixMessage()-");
	}
}
