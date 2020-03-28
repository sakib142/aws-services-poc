package com.mktx.order.svc.gradle.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;

/**
 * JPA annotated class mapped to EMS_ORDER
 *
 */
@Entity(name = "Order")
@Table(name = "EMS_ORDER")
@Data
//@Audited
@EqualsAndHashCode(of = { "id" })
@ToString
@JsonInclude(Include.NON_NULL)
@ApiModel(description = "Represents a order of the system")
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "EMS_ORDER_SEQ")
    @SequenceGenerator(name = "EMS_ORDER_SEQ", sequenceName = "EMS_ORDER_SEQ", allocationSize = 50)
    @ApiModelProperty(notes = "Id for the order")
    private BigInteger id;

    @Basic
    @Column(name = "CLIENT_ORDER_ID")
    @ApiModelProperty(notes = "Client Order Id of the order")
    private String clientOrderId;

    @Basic
    @Column(name = "FIRM_ID", length=50)
    @ApiModelProperty(notes = "Firm Id of the order")
    private String firmId;

    @Basic
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Order Status")
    private OrderStatus status;

    @Basic
    @Column(name = "QUANTITY")
    @ApiModelProperty(notes = "Order quantity")
    private BigDecimal quantity;

    @Basic
    @Column(name = "CURRENCY", length=3)
    @ApiModelProperty(notes = "Currency used for order")
    private String currency;

    @Basic
    @Column(name = "QUANTITY_TYPE" , length=50)
    @ApiModelProperty(notes = "Type of Quantity")
    private String quantityType;

    @Basic
    @Column(name = "PROTOCOL", length=50)
    @ApiModelProperty(value = "Protocol used")
    private String protocol;

    @Basic
    @Column(name = "PRICING_PROCESS")
    @ApiModelProperty(value = "Pricing Process")
    private BigDecimal pricingProcess;

    @Basic
    @Column(name = "SOURCE", length=50)
    private String source;

    @Basic
    @Column(name = "SENDER_COMPANY", length=100)
    private String senderCompany;

    @Basic
    @Column(name = "ISIN", length=12)
    private String isin;

    @Basic
    @Column(name = "CUSIP", length=9)
    private String cusip;

    @Basic
    @Column(name = "INST_ID")
    private BigInteger instId;

    @Basic
    @Column(name = "DIRECTION", length=50)
    private String direction;

    @Basic
    @Column(name = "ORDER_TYPE")
    private String orderType;

    @Basic
    @Column(name = "TRADER_ID", length=100)
    private String traderId;

    @Basic
    @Column(name = "INVESTMENT_DECISION_MAKER", length=100)
    private String decisionMaker;

    @Basic
    @Column(name = "ALGORITHM", length=50)
    private String algorithm;

    @Basic
    @Column(name = "CAPACITY", length=50)
    private String capacity;

    @Basic
    @Column(name = "SETTLEMENT_TYPE_ON_ORDER")
    private String settlmentType;

    @Basic
    @Column(name = "SETTLEMENT_DATE_ON_ORDER")
    private LocalDate settlmentDate;

    @Basic
    @Column(name = "AUTO_EXECUTION", length=1)
    private boolean autoExecution;

    @Basic
    @Column(name = "ACTIVE", length=1)
    private boolean active;

    @Basic
    @Column(name = "LIMIT_PRICE")
    private BigDecimal price;

    @Basic
    @Column(name = "LIMIT_PRICE_TYPE", length=4)
    private String priceType;

    @Basic
    @Column(name = "ORDER_TS", length=6)
    private Instant orderTs;

    @Basic
    @CreationTimestamp
    @Column(name = "CREATE_TS", length=6)
    private Instant createTs;

    @Basic
    @UpdateTimestamp
    @Column(name = "UPDATE_TS", length=6)
    private Instant updateTs ;

    @Basic
    @Column(name = "SENDING_TS", length=6)
    private Instant sendingTs;

    @Basic
    @Column(name = "EXPIRY_TS", length=6)
    private Instant expiryTs;

    @Basic
    @Column(name = "EXPIRY_TYPE")
    private String expiryType;

    @Basic
    @Column(name = "ON_BEHALF_OF_COMPANY", length=100)
    private String onBehalfOfCompany;

    @Basic
    @Column(name = "TRANSACTION_TS", length=6)
    private Instant transactionTs;

    @Basic
    @Column(name = "LAST_CHANGE_USER", length=50)
    private String lastChangeUser;

    @Basic
    @Column(name = "ASSIGNED_TO", length=50)
    private String assignedTo;

    @Basic
    @Column(name = "BENCHMARK_ISIN", length=12)
    private String benchmarkIsin;

    @Basic
    @Column(name = "BENCHMARK_CURVE_NAME", length=50)
    private String benchmarkCurveName;

    @Basic
    @Column(name = "BENCHMARK_CURVE_POINT", length=50)
    private String benchmarkCurvePoint;

    @Basic
    @Column(name = "YIELD_TO_DATE_TYPE", length=20)
    private String yieldToDateType;

    @Basic
    @Column(name = "YIELD_TO_DATE")
    private LocalDate yieldToDate;

    @Basic
    @Column(name = "SIDE", length = 50)
    private String side;

    @Basic
    @Column(name = "SIZE_ORIGINAL")
    private BigDecimal sizeOriginal;

    @Basic
    @Column(name = "SIZE_REMAINING")
    private BigDecimal sizeRemaining;

    @Basic
    @Column(name = "SIZE_OPEN")
    private BigDecimal sizeOpen;

    @Basic
    @Column(name = "SIZE_FILLED")
    private BigDecimal sizeFilled;

    @Basic
    @Column(name = "VENUE_ON_ORDER", length=4)
    private String venueOnOrder;

    @Basic
    @Column(name = "CLIENT_ID", length=100)
    private String clientId;

    @Basic
    @Column(name = "INQUIRIES_SUBMITTED")
    private BigDecimal inquiriesSubmitted;

    @Basic
    @Column(name = "INQUIRIES_OPEN")
    private BigDecimal inquiriesOpen;

    @Basic
    @Column(name = "INQUIRIES_PARTIALLY_FILLED")
    private BigDecimal inquiriesPartiallyFilled;

    @Basic
    @Column(name = "INQUIRIES_FILLED")
    private BigDecimal inquiriesFilled;

    @Basic
    @Column(name = "INQUIRIES_NOT_TRADED")
    private BigDecimal inquiriesNotTraded;

    @Basic
    @Column(name = "INQUIRIES_CANCELLED")
    private BigDecimal inquiriesCancelled;

    @Basic
    @Column(name = "VWA_PRICE")
    private BigDecimal vwaPrice;

    @Basic
    @Column(name = "VWA_SPREAD")
    private BigDecimal vwaSpread;

    @Basic
    @Column(name = "VWA_YIELD")
    private BigDecimal vwaYield;

    @Basic
    @Column(name = "SECURITY_ID", length=20)
    private String SecurityID;

    @Basic
    @Column(name = "HANDLING_INST")
    private BigDecimal handlingInstruction;

    @Basic
    @Column(name = "CLIENT_NOTES", length=1000)
    private String clientNotes;

    @Basic
    @Column(name = "PORTFOLIO_NAME")
    private String portfolioName;

    @Basic
    @Column(name = "BUY_SELL_DECISION_MAKER", length=50)
    private String  buySellDecisionMaker;


    @Basic
    @Column(name = "CLIENT_TRADER_SHORT_CODE", length=50)
    private String clientTraderShortCode;

    @Basic
    @Column(name = "REASON", length=1000)
    private String reason;

    @Basic
    @Column(name = "BENCHMARK_SECURITY_ID", length=8)
    private String benchmarkSecurityID;

    @Basic
    @Column(name = "BENCHMARK_ID_SOURCE", length=20)
    private String benchmarkIdSource;

    @Basic
    @Column(name = "FIX_CONNECTION_ID")
    private BigInteger fixConnectionID;

    @Basic
    @Column(name = "EXECID")
    private String execID;

    @Transient
    private String execRefID;

    @Transient
    private String orderMessageType;

    @Transient
    private String originalClientOrderId;

    @Transient
    private String idSource;

    @Transient
    private BigDecimal reasonCode;

    @Transient
    private String maturityDate;

    @Transient
    private String issueDate;

    @Transient
    private String datedDate;

    @Transient
    private BigDecimal couponRate;

    @Transient
    private String issuer;

    @Transient
    private String securityDesc;

    @Transient
    private String settlCurrency;
}
