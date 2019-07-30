from __future__ import absolute_import, unicode_literals

import boto3


def push_message_to_sqs_queue(tracking_data):
    sqs = boto3.client('sqs')
    response = sqs.send_message(
        QueueUrl='https://sqs.ca-central-1.amazonaws.com/919467880622/third_party_consignment_tracking',
        DelaySeconds=10,
        MessageBody=tracking_data
    )
    return response
