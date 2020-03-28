package com.javasampleapproach.dynamodb.repo;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.dynamodb.model.IncomingFixMessage;

@EnableScan
public interface IncomingFixMessageRepository extends CrudRepository<IncomingFixMessage, String> {

}
