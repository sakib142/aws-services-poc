package com.mktx.ems.order.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * The DTO sent from Order/Execution Service to MA CTAPI Connector on below actions -
 *
 * PASS_QUOTE COUNTER_QUOTE HITLIFT_QUOTE SUBMIT_INQUIRY CANCEL_INQUIRY
 * 
 * The DTO sent from Order/Execution Service to GUI on below actions -
 *
 * PASS_QUOTE COUNTER_QUOTE HITLIFT_QUOTE SUBMIT_INQUIRY CANCEL_INQUIRY
 * 
 * @author sverma
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class OrderDTO {

    private Order order;

    private Event event;

    private String message;
}
