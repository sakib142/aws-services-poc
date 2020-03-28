package com.trax.ems.security.datasource.blink.repo;

import com.trax.ems.security.datasource.blink.domain.InstrumentMasterSecurity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


@Repository
public interface InstrumentMasterSecurityRepository extends PagingAndSortingRepository<InstrumentMasterSecurity, Long> {

    @Query("select i from InstrumentMasterSecurity as i where  i.updatets > :lastExcTmstp and i.updatets <= :currentTimestamp")
//   public List<InstrumentMasterSecurity> findAll(Pageable pageable);
    List<InstrumentMasterSecurity> getInstruments(@Param(value = "lastExcTmstp") Instant lastExcTmstp, @Param(value = "currentTimestamp") Instant currentTimestamp, Pageable pageable);

}