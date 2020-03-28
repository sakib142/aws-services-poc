package com.trax.ems.security.service;


import com.trax.ems.security.datasource.ems.domain.*;
import com.trax.ems.security.datasource.ems.repo.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFeedServiceTest {

    @Mock
    EmsInstrumentRepository emsInstrumentRepository;

    @Mock
    EmsUtilCalendarRepository emsUtilCalendarRepository;

    @Mock
    EmsProductInstrumentRepository emsProductInstrumentRepository;

    @Mock
    EmsInstrumentProductRepository emsInstrumentProductRepository;

    @Mock
    EmsMarketSegmentRepository emsMarketSegmentRepository;

    @Mock
    private EmsVenueRepository emsVenueRepository;

    @Mock
    EmsBatchExecutionRespository emsBatchExecutionRespository;

    @Mock
    EmsBatchExecution emsBatchExecution;

    EmsVenue emsVenue;

    EmsInstrument emsInstrument;
    EmsProduct emsProduct;
    EmsMarketSegment emsMarketSegment;
    EmsUtilCalendar emsUtilCalendar;
    EmsProductInstruments emsProductInstruments;




    @Value("${batch.noruntmstmp}")
    String noRunTmstmp;

    @Value("${batch.name}")
    String emsBatchName;

    @Value("${batch.timestamp.format}")
    private String TIMESTAMP_FORMAT;


    @InjectMocks
    SecurityFeedService securityFeedService;



//    Instant lastExcTmstp;
//    Instant currentTimestamp;
//    Pageable pageWithThousandElements;


    @Before
    public void setUp() throws ParseException {

        emsVenue = new EmsVenue();
        emsInstrument= new EmsInstrument();
        emsProduct = new EmsProduct();
        emsMarketSegment= new EmsMarketSegment();
        emsUtilCalendar = new EmsUtilCalendar();
        emsProductInstruments = new EmsProductInstruments();
        ReflectionTestUtils.setField(securityFeedService,"noRunTmstmp","1951042501010000");
        ReflectionTestUtils.setField(securityFeedService,"emsBatchName","ems.securityfeed");
        ReflectionTestUtils.setField(securityFeedService,"TIMESTAMP_FORMAT","yyyyMMddHHmmssSS");


    }

    @Test
    public void testProcessInstrumentsWithAllPositiveCases() throws Exception {
        List<EmsVenue> emsVenueList = new ArrayList<>();
        emsVenueList.add(emsVenue);
        List<EmsInstrument> emsInstrumentList = new ArrayList<>();
        emsInstrument.setInstrumentId(new BigInteger("1000"));
        //emsInstrumentList.add(emsInstrument);
        emsInstrumentList.add(emsInstrument);
        emsMarketSegment.setMarketSegment("HG");
        Set<EmsProduct> emsProductList = new HashSet<>();
        emsProduct.setProductId(new BigInteger("100"));
        emsProductList.add(emsProduct);
        emsProductList.add(emsProduct);
        emsMarketSegment.setInstrumentProducts(emsProductList);
        emsUtilCalendar.setCalendarId(new BigInteger("200"));
        emsProduct.setUtilCalendar(emsUtilCalendar);
        Set<EmsProductInstruments> emsProductInstrumentsSet= new HashSet<>();
        emsProductInstrumentsSet.add(emsProductInstruments);
        emsProductInstrumentsSet.add(emsProductInstruments);
        emsProduct.setInstrumentProductInstruments(emsProductInstrumentsSet);
        emsInstrument.setMarketSegment(emsMarketSegment);
        emsInstrument.setOnMAELVenue("Y");
        securityFeedService.feedSecurityDetails(emsInstrumentList);
        Mockito.verify(emsVenueRepository,Mockito.times(1)).findAll();
        Mockito.verify(emsMarketSegmentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsUtilCalendarRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsInstrumentProductRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsInstrumentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());

    }

    @Test
    public void testProcessInstrumentsWithVenueNegativeCase() throws Exception {
        List<EmsVenue> emsVenueList = new ArrayList<>();
        emsVenueList.add(emsVenue);
        List<EmsInstrument> emsInstrumentList = new ArrayList<>();
        emsInstrument.setInstrumentId(new BigInteger("1000"));
        //emsInstrumentList.add(emsInstrument);
        emsInstrumentList.add(emsInstrument);
        emsMarketSegment.setMarketSegment("HG");
        Set<EmsProduct> emsProductList = new HashSet<>();
        emsProduct.setProductId(new BigInteger("100"));
        emsProductList.add(emsProduct);
        emsProductList.add(emsProduct);
        emsMarketSegment.setInstrumentProducts(emsProductList);
        emsUtilCalendar.setCalendarId(new BigInteger("200"));
        emsProduct.setUtilCalendar(emsUtilCalendar);
        Set<EmsProductInstruments> emsProductInstrumentsSet= new HashSet<>();
        emsProductInstrumentsSet.add(emsProductInstruments);
        emsProductInstrumentsSet.add(emsProductInstruments);
        emsProduct.setInstrumentProductInstruments(emsProductInstrumentsSet);
        emsInstrument.setMarketSegment(emsMarketSegment);
        emsInstrument.setOnMAELVenue("N");
        securityFeedService.feedSecurityDetails(emsInstrumentList);
        Mockito.verify(emsVenueRepository,Mockito.times(1)).findAll();
        Mockito.verify(emsMarketSegmentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsUtilCalendarRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());

    }

    @Test
    public void testProcessInstrumentsWithVenueIDNull() throws Exception {
        List<EmsVenue> emsVenueList = new ArrayList<>();
        emsVenueList.add(emsVenue);
        List<EmsInstrument> emsInstrumentList = new ArrayList<>();
        emsInstrument.setInstrumentId(new BigInteger("1000"));
        //emsInstrumentList.add(emsInstrument);
        emsInstrumentList.add(emsInstrument);
        emsMarketSegment.setMarketSegment("HG");
        Set<EmsProduct> emsProductList = new HashSet<>();
        emsProduct.setProductId(new BigInteger("100"));
        emsProductList.add(emsProduct);
        emsProductList.add(emsProduct);
        emsMarketSegment.setInstrumentProducts(emsProductList);
        emsUtilCalendar.setCalendarId(new BigInteger("200"));
        emsProduct.setUtilCalendar(emsUtilCalendar);
        Set<EmsProductInstruments> emsProductInstrumentsSet= new HashSet<>();
        emsProductInstrumentsSet.add(emsProductInstruments);
        emsProductInstrumentsSet.add(emsProductInstruments);
        emsProduct.setInstrumentProductInstruments(emsProductInstrumentsSet);
        emsInstrument.setMarketSegment(emsMarketSegment);
        emsInstrument.setOnMAELVenue("N");
        emsVenue.setId(null);
        securityFeedService.feedSecurityDetails(emsInstrumentList);
        Mockito.verify(emsVenueRepository,Mockito.times(1)).findAll();
        Mockito.verify(emsMarketSegmentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsUtilCalendarRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(emsProductInstrumentRepository,Mockito.times(1)).save(Mockito.any());

    }



    @Test
    public void testgetLastExecutionTime(){
        Optional<EmsBatchExecution> emsBatchExec = Optional.of(emsBatchExecution);
        Mockito.when(emsBatchExecutionRespository.findById(Mockito.any())).thenReturn(emsBatchExec);
        securityFeedService.getLastExecutionTime("ems.securityfeed");
        Mockito.verify(emsBatchExecutionRespository,Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    public void testgetLastExecutionTimewhenNull(){
        Optional<EmsBatchExecution> emsBatchExec = Optional.empty();
        Mockito.when(emsBatchExecutionRespository.findById(Mockito.any())).thenReturn(emsBatchExec);
        ReflectionTestUtils.setField(securityFeedService,"noRunTmstmp","1951042501010000");
        securityFeedService.getLastExecutionTime("ems.securityfeed");
        Mockito.verify(emsBatchExecutionRespository,Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    public void testUpdateBatchExecutionDetails() throws Exception{
        securityFeedService.updateBatchExecutionDetails("1951042501010000","2019052701010000",new BigInteger("100"));
        Mockito.verify(emsBatchExecutionRespository,Mockito.times(1)).save(Mockito.any());

    }
    

    @Test(expected = NumberFormatException.class)
    public void testUpdateBatchExecutionDetailsWhenWrongNoOfProccesItems() throws Exception{
        securityFeedService.updateBatchExecutionDetails("1951042501010000","2019052701010000",new BigInteger("Deepak"));
        Mockito.verify(emsBatchExecutionRespository,Mockito.times(1)).save(Mockito.any());

    }



}
