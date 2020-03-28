package com.trax.ems.security.service;


import com.trax.ems.security.datasource.blink.domain.InstrumentMasterSecurity;
import com.trax.ems.security.datasource.blink.repo.InstrumentMasterSecurityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SecurityRestServiceTest {

    @Mock
    InstrumentMasterSecurityRepository instrumentMasterSecurityRepository ;

    InstrumentMasterSecurity instrumentMasterSecurity;

    @Value("${batch.timestamp.format}")
    private String TIMESTAMP_FORMAT;

    @InjectMocks
    SecurityRestService securityRestService;

    Instant lastExcTmstp;
    Instant currentTimestamp;
    Pageable pageWithHundredElements;
    Pageable pageWithThousandElements;
    Pageable pageWithTwoElements;


    @Before
    public void setUp() throws ParseException {
        instrumentMasterSecurity=new InstrumentMasterSecurity();
        ReflectionTestUtils.setField(securityRestService,"TIMESTAMP_FORMAT","yyyyMMddHHmmssSS");
        SimpleDateFormat dateFormat = new SimpleDateFormat(ReflectionTestUtils.getField(securityRestService,"TIMESTAMP_FORMAT").toString());
        lastExcTmstp= dateFormat.parse("1951042501010000").toInstant();
        currentTimestamp = dateFormat.parse("2019042601000000").toInstant();
        pageWithHundredElements= PageRequest.of(0, 100);
        pageWithThousandElements= PageRequest.of(0,1000);
        pageWithTwoElements= PageRequest.of(0,2);
    }



    @Test
    public void testGetInstrumnetsWithProperdateFormatandPageBatch() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("1951042501010000","2019042601000000","0","100");
        Mockito.verify(instrumentMasterSecurityRepository,Mockito.times(4)).getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements);
    }

    @Test(expected = ParseException.class)
    public void testGetIstrumentswithImproperDateFormat() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        //Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("19510425010100","20190426010000","0","100");
    }

    @Test(expected = NumberFormatException.class)
    public void testGetIstrumentswithImproperPage() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        //Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("1951042501010000","2019042601000000","page","100");
    }

    @Test(expected = NumberFormatException.class)
    public void testGetIstrumentswithImproperBatch() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        //Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("1951042501010000","2019042601000000","0","batch");
    }


    @Test
    public void testGetIstrumentsWhenNoElements() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
       // instrumentMasterSecurityList.add(instrumentMasterSecurity);
        Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("1951042501010000","2019042601000000","0","100");
        Mockito.verify(instrumentMasterSecurityRepository,Mockito.times(1)).getInstruments(lastExcTmstp,currentTimestamp,pageWithHundredElements);
    }


    @Test
    public void testGetIstrumentsWhenBatchisZero() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithThousandElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("1951042501010000","2019042601000000","0","0");
        Mockito.verify(instrumentMasterSecurityRepository,Mockito.times(4)).getInstruments(lastExcTmstp,currentTimestamp,pageWithThousandElements);
    }

    @Test
    public void testGetIstrumentsWhenBatchSizeisLess() throws Exception{
        List<InstrumentMasterSecurity> instrumentMasterSecurityList = new ArrayList<>();
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        instrumentMasterSecurityList.add(instrumentMasterSecurity);
        Mockito.when(instrumentMasterSecurityRepository.getInstruments(lastExcTmstp,currentTimestamp,pageWithTwoElements)).thenReturn(instrumentMasterSecurityList);
        securityRestService.getSecurityDetails("1951042501010000","2019042601000000","0","2");
        Mockito.verify(instrumentMasterSecurityRepository,Mockito.times(2)).getInstruments(lastExcTmstp,currentTimestamp,pageWithTwoElements);
    }



}
