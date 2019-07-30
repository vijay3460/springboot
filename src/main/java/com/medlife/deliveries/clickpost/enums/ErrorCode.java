package com.medlife.deliveries.clickpost.enums;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {

    AUTHENTICATION_FAILED(301),
    INVALID_PARTNER_ID(302),
    WAYBILL_ALREADY_REGISTERED(303),
    ALREADY_REGISTERED_FOR_SERVICE(304),
    NOT_REGISTERED_FOR_SERVICE(306),
    NO_CREDENTIALS_FOR_PARTNER(316),
    SERVICE_NOT_SUBSCRIBED(320),
    INVALID_CREDENTIALS_FOR_PARTNER(324),
    INVALID_STATUS_UPDATE_ATTEMPT(325),
    INVALID_TRACKING_ID(326),
    INVALID_POST_DATA(328),
    WAYBILL_DOES_NOT_EXIST(330),
    NO_ACCOUNT_EXISTS(351),
    MULTIPLE_ACCOUNT_EXIST(352),
    INACTIVE_ACCOUNT(353),
    UNHANDLED_CP_ERROR(354),
    VENDOR_CODE_NOT_FOUND(355),
    INVALID_ENTITY(422),
    INVALID_ORDER_TYPE(307),
    INVALID_ORDER_PRIORITY(308),
    INVALID_DELIVERY_TYPE(309),
    RVP_REASON_IS_MISSING(310),
    INVALID_RVP_PARTNER_COURIER(311),
    ITEM_DATA_MISSING(312),
    INVALID_FORMAT_FOR_ITEMS(313),
    INVALID_ORDER_DATA_FORMAT(314),
    INVALID_CODE_VALUE(315),
    ORDER_PLACING_ERROR(319),
    INVALID_AWB_NUMBER(321),
    PARTNER_INTERNAL_SERVER_ERROR(322),
    ORDER_ALREADY_PLACED(323),
    WEBHOOK_ALREADY_REGISTERED(305),
    WEEBHOOK_NOT_REGISTERED(327),
    ORDER_ALREADY_CANCELLED(600),
    PARTNER_ORDER_CANCELLATION_ERROR(350);

    private Integer statusCode;

    ErrorCode(Integer statusCode){
        this.statusCode = statusCode;
    }

    public Integer statusCode(){
        return this.statusCode;
    }

    private static final Map<Integer, ErrorCode> lookup = new HashMap<>();

    static {
        for (ErrorCode e : ErrorCode.values()) {
            lookup.put(e.statusCode(), e);
        }
    }

    public static ErrorCode get(Integer statusCode) {
        return lookup.get(statusCode);
    }
}
