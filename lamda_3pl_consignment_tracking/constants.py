from __future__ import absolute_import, unicode_literals

from enum import Enum


class DeliveryPartnerEnum(int, Enum):
    CLICK_POST = 1

    @staticmethod
    def list():
        return list(map(lambda c: c.value, DeliveryPartnerEnum))
