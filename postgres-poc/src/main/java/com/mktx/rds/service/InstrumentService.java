package com.mktx.rds.service;

import com.mktx.rds.model.EmsInstrument;
import com.mktx.rds.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstrumentService {

    @Autowired
    InstrumentRepository instrumentRepository;

   /* public void saveAll(List<EmsInstrument> orders){
        instrumentRepository.saveAll(orders);
    }

    public void createOrder(EmsInstrument order){
        instrumentRepository.save(order);
    }*/

    public List<EmsInstrument> findAll(){
        return instrumentRepository.findAll();
    }


}
