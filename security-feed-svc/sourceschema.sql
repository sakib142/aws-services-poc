DROP TABLE INSTRUMENT_MASTERSECURITY;

DROP TABLE INSTRUMENT_PRODUCTINSTRUMENTS;

DROP TABLE INSTRUMENT_PRODUCT;

DROP TABLE INSTRUMENT_MARKETSEGMENT;

DROP TABLE INSTRUMENT_PRODUCTPROTOCOLS;

DROP TABLE INQUIRY_TRADINGPROTOCOL;

DROP TABLE UTIL_TEMAP;

DROP TABLE UTIL_CURRENCY;

DROP TABLE INSTRUMENT_SECTOR;



CREATE TABLE INSTRUMENT_MASTERSECURITY (
    ID                          NUMBER NOT NULL,
    NAME                        VARCHAR2(255) NOT NULL,
    COUPON                      NUMBER NOT NULL,
    MATURITY                    DATE NOT NULL,
    CUSIP                       VARCHAR2(30) NOT NULL,
    ISSUEDATE                   DATE,
    ACCRUALSTART                DATE,
    FIRSTPAYMENT                DATE,
    SECONDPAYMENT               DATE,
    PAYMENTTYPE                 CHAR(1),
    INTERESTRATETYPE            CHAR(1),
    BONDTYPE                    CHAR(1),
    DEFAULTED                   CHAR(1),
    ACCRUALMETHOD               CHAR(1),
    PAYMENTFREQUENCY            CHAR(1),
    REDEMPTION                  NUMBER,
    TRAILER                     VARCHAR2(255),
    DESCRIPTION                 VARCHAR2(500),
    ISDELETED                   CHAR(1) DEFAULT 'N' NOT NULL,
    ISMULTICOUPON               CHAR(1) DEFAULT 'N' NOT NULL,
    TIMESTAMP                   DATE NOT NULL,
    LASTPAYMENT                 DATE,
    ISIN                        VARCHAR2(12),
    COUNTRYCODE                 VARCHAR2(4),
    CLEARINGHOUSE               VARCHAR2(10),
    MINLOTSIZE                  NUMBER,
    MARKETSEGMENT               NUMBER,
    MARKETCATEGORY              NUMBER,
    MARKETSUBCATEGORY           NUMBER,
    COMMISSIONRATE              NUMBER,
    FRNINDEX                    VARCHAR2(20),
    FRNBASIS                    NUMBER,
    SHORTNAME                   VARCHAR2(100),
    ISSUER                      VARCHAR2(255),
    ISCVTPREFSTOCK              CHAR(1),
    PARVALUE                    NUMBER,
    FITCHRATING                 NUMBER,
    SNPRATING                   NUMBER,
    MOODYRATING                 NUMBER,
    ISOCURRENCYCODE             CHAR(3),
    SECTORID                    NUMBER,
    NOUPDATEFROMFEED            CHAR(1) DEFAULT 'N' NOT NULL,
    ISSUEAMOUNT                 NUMBER,
    AMOUNTOUTSTANDING           NUMBER,
    FIRSTSETTLEMENTDATE         DATE,
    CALLABLE                    CHAR(1),
    PUTABLE                     CHAR(1),
    SINKABLE                    CHAR(1),
    MAKEWHOLE                   CHAR(1),
    NEXTCALLDATE                DATE,
    NEXTPUTDATE                 DATE,
    NEXTSINKDATE                DATE,
    SECURITYTYPE                VARCHAR2(32),
    FLTFORMULA                  VARCHAR2(255),
    FLTMULT                     NUMBER,
    FLTCAP                      NUMBER,
    FLTFLOOR                    NUMBER,
    VENDORNAME                  VARCHAR2(65),
    DEBT_TYPE_CD                VARCHAR2(20),
    CALC_ISSUE                  CHAR(1),
    EOM_FLAG                    CHAR(1),
    ORIGINAL_COUPON             NUMBER,
    DEFAULTTICKER               VARCHAR2(15),
    VENDORBONDID                NUMBER,
    TOKENIZEDSHORTNAME          VARCHAR2(175),
    SYNDICATED                  CHAR(1) DEFAULT 'N',
    DENOMINCR                   NUMBER,
    DV01_WORST                  NUMBER,
    MOD_DUR_WORST               NUMBER,
    REDPAIRCLIP                 VARCHAR2(9),
    WKN                         VARCHAR2(6),
    BMKATISSUEID                NUMBER,
    SEASONED                    CHAR(1),
    LEADUNDERWRITER             VARCHAR2(300),
    CALC_TRACE                  CHAR(1),
    FDIC                        CHAR(1),
    ISSUEPRICE                  NUMBER,
    NEXTPAYDATE                 DATE,
    EXDIVDATE                   DATE,
    CUMULDIV                    CHAR(1),
    INITIALDIV                  NUMBER,
    ANNUALDIV                   NUMBER,
    SEDOL                       VARCHAR2(7),
    COLLAT_GROSS_CPN            NUMBER,
    COLLAT_NET_CPN              NUMBER,
    COLLAT_REM_TERM             NUMBER,
    COUPON_DATE                 DATE,
    COUPON_EFF_DATE             DATE,
    COUPON_UPDATE_DATE          DATE,
    DEAL_TYPE                   VARCHAR2(100),
    DELAY_DAYS                  NUMBER,
    EXPECTED_MAT_DATE           DATE,
    FACTOR                      NUMBER,
    FACTOR_DATE                 DATE,
    FLT_INIT_RESET_DATE         DATE,
    FLT_RESET_FREQ              CHAR(1),
    MATURITY_AVG_LIFE           NUMBER,
    MATURITY_AVG_LIFE_DATE      DATE,
    MATURITY_PREPAY_MODEL       VARCHAR2(5),
    MATURITY_PREPAY_SPEED       NUMBER,
    ORIGINAL_AVG_LIFE           NUMBER,
    PRICE_SPEED                 NUMBER,
    PRICE_SPEED_METHOD          VARCHAR2(8),
    PRICED_DATE                 DATE,
    SERIES                      VARCHAR2(20),
    TRANCHE                     VARCHAR2(20),
    TRANCHE_CHAR                VARCHAR2(100),
    TRUSTEE                     VARCHAR2(100),
    IBOXX_SECTORID              NUMBER,
    COLLAT_TYPE                 VARCHAR2(100),
    HAS_ARM_COLLAT              VARCHAR2(10),
    INIT_ARM_RESET_PER          NUMBER,
    MTN                         CHAR(1),
    CINS                        VARCHAR2(10),
    TRACE_CODE                  CHAR(1),
    DEFAULT_PRODUCTID_EU        NUMBER,
    SUBORDINATION_CLAUSE        VARCHAR2(20),
    SETTLE_DAYS                 NUMBER,
    OT_DISABLED                 VARCHAR2(20),
    FEED_PRICE                  NUMBER,
    OUTSTANDING_INDICATOR       CHAR(1),
    FEDERAL_TAX_STATUS          VARCHAR2(80),
    INCORPORATED_STATE_CODE     VARCHAR2(2),
    ORGANIZATION_TYPE           VARCHAR2(80),
    CONDUIT_OBLIGOR_NAME        VARCHAR2(75),
    ORIGINAL_YIELD              FLOAT(126),
    MATURITY_AMOUNT             FLOAT(126),
    BANK_ELIGIBLE               CHAR(1),
    BANK_QUALIFIED              CHAR(1),
    AMT                         CHAR(1),
    ETM                         CHAR(1),
    PREREFUNDED                 CHAR(1),
    REGISTERED                  CHAR(1),
    TAXABLE                     CHAR(1),
    TOBACCO                     CHAR(1),
    BOND_INSURANCE              VARCHAR2(50),
    OTHER_ENHANCEMENT_COMPANY   VARCHAR2(50),
    MOODY_UNDERLYING_RATING     NUMBER,
    SNP_UNDERLYING_RATING       NUMBER,
    FITCH_UNDERLYING_RATING     NUMBER,
    GENERAL_OBLIGATION          CHAR(1),
    REVENUE                     CHAR(1),
    DOUBLE_BARREL               CHAR(1),
    NEXT_PRERE_DATE             DATE,
    DISPLAY_CALL_DATE           DATE,
    DISPLAY_CALL_TYPE           VARCHAR2(20),
    DISPLAY_CALL_PRICE          FLOAT(126),
    ISSUER_ABBREV_NAME          VARCHAR2(255),
    PURPOSE                     VARCHAR2(50),
    AGENT_BANK                  VARCHAR2(255),
    DEAL_CUSIP                  VARCHAR2(25),
    DEAL_NAME                   VARCHAR2(255),
    DEAL_GLOBAL_AMOUNT          NUMBER(15,4),
    DEAL_EFF_DATE               DATE,
    LOANX                       VARCHAR2(50),
    IS_FIXED                    CHAR(1),
    IS_GUARANTEED               CHAR(1),
    EQUITY_TICKER               VARCHAR2(50),
    ASSIGNMENT_FEE              NUMBER(15,4),
    PUBLIC_LOAN                 VARCHAR2(10),
    CALL_SCHEDULE_DATE          DATE,
    LIEN_TYPE                   VARCHAR2(50),
    GUARANTOR                   VARCHAR2(254),
    INTERBANK_ID                VARCHAR2(20),
    EXCH_CD                     VARCHAR2(3),
    PERPETUAL_FL                CHAR(1),
    CFI_CD                      VARCHAR2(10),
    ISSUER_LEI_CD               VARCHAR2(30),
    ESMA_BOND_TYPE              VARCHAR2(10),
    LIQUIDITY_FLAG              CHAR(1),
    COFIA_LIQUIDITY_FLAG        CHAR(1),
    LIS_SIZE                    NUMBER,
    SSTI_SIZE                   NUMBER,
    SUSPEND_FLAG                CHAR(1),
    MIFIR_INDICATOR             VARCHAR2(10),
    MIFID_SI                    VARCHAR2(30),
    ON_MAEL                     CHAR(1),
    ON_MTXX                     CHAR(1),
    ON_MASG                     CHAR(1),
    ASSET_STATUS                VARCHAR2(3)
);

CREATE TABLE INSTRUMENT_PRODUCTINSTRUMENTS (
    INSTRUMENTID   NUMBER NOT NULL,
    PRODUCTID      NUMBER NOT NULL,
    ISDEFAULT      CHAR(1),
    PRIORITY       NUMBER(*,0)
);

CREATE TABLE INSTRUMENT_PRODUCT (
    ID                 NUMBER NOT NULL,
    MARKETSEGMENTID    NUMBER NOT NULL,
    DESCRIPTION        VARCHAR2(32),
    PRODUCT_CD         CHAR(4),
    CALENDARID         NUMBER DEFAULT 0 NOT NULL,
    REGIONID           NUMBER,
    APPLY_FEE          CHAR(1) DEFAULT 'N',
    VISIBLEPRODUCTID   NUMBER,
    ACCEPTSBMK         CHAR(1),
    STLDAYS            NUMBER DEFAULT 3 NOT NULL,
    FXSTLDAYS          NUMBER DEFAULT 3 NOT NULL,
    BMKPRODUCTID       NUMBER,
    LEVEL_INCREMENT    NUMBER,
    ISDELETED          CHAR(1) DEFAULT 'N' NOT NULL,
    CCPNAME            VARCHAR2(10)
);

CREATE TABLE INSTRUMENT_MARKETSEGMENT (
    ID                       NUMBER NOT NULL,
    MARKETSEGMENT            VARCHAR2(4) NOT NULL,
    DESCRIPTION              VARCHAR2(32),
    POSTTRADEMARKETSEGMENT   VARCHAR2(15)
);

CREATE TABLE INSTRUMENT_PRODUCTPROTOCOLS (
    ID                NUMBER,
    PRODUCTID         NUMBER NOT NULL,
    INQUIRYTYPE       CHAR(1),
    PRICETYPE         CHAR(1),
    TRANSACTIONTYPE   VARCHAR2(8),
    PROTOCOLID        NUMBER NOT NULL,
    FORCERANK         NUMBER NOT NULL
);

CREATE TABLE INQUIRY_TRADINGPROTOCOL (
    TRADINGPROTOCOLID   NUMBER NOT NULL,
    DESCRIPTION         VARCHAR2(32),
    SHORTNAME           VARCHAR2(15),
    APINAME             VARCHAR2(10)
);

CREATE TABLE UTIL_TEMAP (
    ID           NUMBER,
    LONGSTRING   VARCHAR2(250),
    TYPE         VARCHAR2(20)
);

CREATE TABLE UTIL_CURRENCY (
    ISOCODE     CHAR(3),
    SHORTNAME   VARCHAR2(10)
);

CREATE TABLE INSTRUMENT_SECTOR (
    ID            NUMBER,
    DESCRIPTION   VARCHAR2(100)
);