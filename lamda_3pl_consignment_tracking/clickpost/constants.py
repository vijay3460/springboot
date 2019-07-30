from __future__ import absolute_import, unicode_literals

from enum import Enum


class ClickPostUnprocessedStatusEnum(int, Enum):
    ORDER_PLACED = 1
    PICKUP_PENDING = 2
    PICKUP_CANCELLED = 3
    PICKED_UP = 4
    IN_TRANSIT = 5
    OUT_FOR_DELIVERY = 6
    NOT_SERVICEABLE = 7
    DELIVERED = 8
    FAILED_DELIVERY = 9
    CANCELLED_ORDER = 10
    RTO_REQUESTED = 11
    RTO = 12
    RTO_OUT_FOR_DELIVERY = 13
    RTO_DELIVERED = 14
    RTO_FAILED = 15
    LOST = 16
    DAMAGED = 17
    SHIPMENT_DELAYED = 18
    CONTACT_CUSTOMER_CARE = 19
    SHIPMENT_HELD = 20
    RTO_IN_TRANSIT = 21
    OUT_FOR_PICKUP = 25
    RTO_CONTACT_CUSTOMER_CARE = 26
    RTO_SHIPMENT_DELAY = 27
    AWB_REGISTERED = 28
    RETURN_ORDER_PLACED = 101

    @staticmethod
    def list():
        return list(map(lambda c: c.value, ClickPostUnprocessedStatusEnum))


ClickPostStatusToConsignmentStatusMappingIdDict = {
    ClickPostUnprocessedStatusEnum.ORDER_PLACED: 1,
    ClickPostUnprocessedStatusEnum.OUT_FOR_PICKUP: 2,
    ClickPostUnprocessedStatusEnum.AWB_REGISTERED: 3,
    ClickPostUnprocessedStatusEnum.PICKUP_PENDING: 4,
    ClickPostUnprocessedStatusEnum.PICKED_UP: 5,
    ClickPostUnprocessedStatusEnum.IN_TRANSIT: 6,
    ClickPostUnprocessedStatusEnum.OUT_FOR_DELIVERY: 7,
    ClickPostUnprocessedStatusEnum.DELIVERED: 8,
    ClickPostUnprocessedStatusEnum.LOST: 9,
    ClickPostUnprocessedStatusEnum.FAILED_DELIVERY: 10,
    ClickPostUnprocessedStatusEnum.NOT_SERVICEABLE: 11,
    ClickPostUnprocessedStatusEnum.CANCELLED_ORDER: 12,
    ClickPostUnprocessedStatusEnum.RTO_REQUESTED: 13,
    ClickPostUnprocessedStatusEnum.RTO: 14,
    ClickPostUnprocessedStatusEnum.RTO_CONTACT_CUSTOMER_CARE: 15,
    ClickPostUnprocessedStatusEnum.RTO_DELIVERED: 16,
    ClickPostUnprocessedStatusEnum.RTO_FAILED: 17,
    ClickPostUnprocessedStatusEnum.RTO_IN_TRANSIT: 18,
    ClickPostUnprocessedStatusEnum.RTO_SHIPMENT_DELAY: 19,
    ClickPostUnprocessedStatusEnum.DAMAGED: 20,
    ClickPostUnprocessedStatusEnum.SHIPMENT_DELAYED: 21,
    ClickPostUnprocessedStatusEnum.CONTACT_CUSTOMER_CARE: 22,
    ClickPostUnprocessedStatusEnum.SHIPMENT_HELD: 23,
    ClickPostUnprocessedStatusEnum.RETURN_ORDER_PLACED: 24,
}
