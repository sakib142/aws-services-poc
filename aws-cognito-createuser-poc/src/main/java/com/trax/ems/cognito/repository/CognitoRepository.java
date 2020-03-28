package com.trax.ems.cognito.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.trax.ems.cognito.model.CognitoUser;

@EnableScan
public interface CognitoRepository extends CrudRepository<CognitoUser, String> {

}