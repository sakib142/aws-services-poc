package com.mktx.cognito.repo;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.mktx.cognito.model.CognitoUser;

@EnableScan
public interface CognitoRepository extends CrudRepository<CognitoUser, String> {

}