from __future__ import absolute_import, unicode_literals

from clickpost.constants import ClickPostStatusToConsignmentStatusMappingIdDict
from constants import DeliveryPartnerEnum

import json


def handle_tracking_data(event, context):
    import time
    from info import push_message_to_sqs_queue

    body = json.loads(event['body'])
    try:
        unprocessed_status_code = body['additional']['latest_status']['clickpost_status_code']
        consignment_status_mapping_id = ClickPostStatusToConsignmentStatusMappingIdDict.get(unprocessed_status_code)
        partner_data_timestamp = int(time.mktime(time.strptime(
            body['additional']['latest_status']['timestamp'], "%Y-%m-%dT%H:%M:%SZ")
        )) + 19800
        awb = body['waybill']
    except:
        tracking_data = dict()
        tracking_data['delivery_partner_id'] = DeliveryPartnerEnum.CLICK_POST.value
        tracking_data['delivery_partner_payload'] = body
        push_message_to_sqs_queue(json.dumps(tracking_data))
        return {"statusCode": 200}
    
    tracking_data = dict()
    tracking_data['delivery_partner_id'] = DeliveryPartnerEnum.CLICK_POST.value
    tracking_data['consignment_status_mapping_id'] = consignment_status_mapping_id
    tracking_data['awb'] = awb
    tracking_data['received_at'] = int(time.time())
    tracking_data['processed_at'] = partner_data_timestamp
    tracking_data['delivery_partner_payload'] = body
    push_message_to_sqs_queue(json.dumps(tracking_data))
    return {"statusCode": 200}
