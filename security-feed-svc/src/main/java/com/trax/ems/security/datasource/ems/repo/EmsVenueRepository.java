package com.trax.ems.security.datasource.ems.repo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trax.ems.security.datasource.ems.domain.EmsVenue;

@Repository
public interface EmsVenueRepository extends JpaRepository<EmsVenue, BigInteger> {

}
