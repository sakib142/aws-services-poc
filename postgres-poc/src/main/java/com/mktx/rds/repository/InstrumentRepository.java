package com.mktx.rds.repository;

import com.mktx.rds.model.EmsInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<EmsInstrument, Long> {

    @Override
    List<EmsInstrument> findAll();

}
