CREATE SEQUENCE consignment_partner_logs_id_seq;
CREATE TABLE consignment_partner_logs (
  id BIGINT DEFAULT NEXTVAL('consignment_partner_logs_id_seq'),
  consignment_id BIGINT NOT NULL,
  received_data JSONB NOT NULL,
  received_type VARCHAR CHECK (received_type IN ('WEBHOOK', 'POLL')),
  created_at BIGINT,
  updated_at BIGINT,
  CONSTRAINT consignment_partner_logs_consignment_id FOREIGN KEY (consignment_id) REFERENCES consignments (id),
  PRIMARY KEY (id)
);

CREATE SEQUENCE consignment_subscription_logs_id_seq;
CREATE TABLE consignment_subscription_logs (
  id BIGINT DEFAULT NEXTVAL('consignment_subscription_logs_id_seq'),
  consignment_partner_log_id BIGINT NOT NULL,
  consignment_subscriptions_id BIGINT  NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  response JSONB NOT NULL,
  request JSONB NOT NULL,
  CONSTRAINT consignment_subscription_logs_consignment_partner_log_id FOREIGN KEY (consignment_partner_log_id) REFERENCES consignment_partner_logs (id),
  CONSTRAINT consignment_subscription_logs_consignment_subscriptions_id FOREIGN KEY (consignment_subscriptions_id) REFERENCES consignment_subscriptions (id),
  PRIMARY KEY (id)
);

ALTER TABLE consignment_status_logs ADD COLUMN consignment_partner_log_id BIGINT;
ALTER TABLE consignment_status_logs ADD COLUMN notified BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE consignment_status_logs ADD CONSTRAINT consignment_status_logs_consignment_partner_log_id FOREIGN KEY (consignment_partner_log_id) REFERENCES consignment_partner_logs (id);
