package com.trax.ems.security.datasource.ems.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trax.ems.security.datasource.ems.domain.EmsProductInstrumentEmbedded;
import com.trax.ems.security.datasource.ems.domain.EmsProductInstruments;

@Repository
public interface EmsProductInstrumentRepository
		extends JpaRepository<EmsProductInstruments, EmsProductInstrumentEmbedded> {

}
