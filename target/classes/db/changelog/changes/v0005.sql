ALTER  TABLE consignment_status_logs DROP COLUMN unprocessed_status;
ALTER TABLE consignment_status_logs DROP COLUMN processed_status;
ALTER TABLE consignment_partner_logs DROP COLUMN consignment_id;
ALTER TABLE consignment_status_logs ALTER COLUMN consignment_id DROP NOT NULL;
ALTER TABLE consignment_status_logs ALTER COLUMN consignment_partner_log_id SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN delivery_type SET NOT NULL;
ALTER TABLE consignments ADD CONSTRAINT consignments_awb_delivery_type_unique UNIQUE (awb, delivery_type);

CREATE SEQUENCE processed_statuses_id_seq;
CREATE TABLE processed_statuses (
  id BIGINT DEFAULT NEXTVAL('processed_statuses_id_seq'),
  status VARCHAR NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  primary key (id),
  CONSTRAINT processed_statuses_status_uq UNIQUE (status)

);

CREATE SEQUENCE consignment_status_mappings_id_seq;
CREATE TABLE consignment_status_mappings (
  id BIGINT DEFAULT NEXTVAL('consignment_status_mappings_id_seq'),
  processed_status VARCHAR NOT NULL,
  unprocessed_status VARCHAR NOT NULL,
  unprocessed_status_id BIGINT NOT NULL,
  delivery_partner_id BIGINT NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  CONSTRAINT consignment_status_mappings_processed_statusxwww_fk FOREIGN KEY (processed_status) REFERENCES processed_statuses (status),
  CONSTRAINT consignment_status_mappings_partner_id_fk FOREIGN KEY (delivery_partner_id) REFERENCES delivery_partners (id),
  primary key (id)
);

ALTER TABLE consignment_status_logs ADD COLUMN status VARCHAR NOT NULL;
ALTER TABLE consignment_status_logs ADD CONSTRAINT  consignment_status_logs_status FOREIGN KEY (status) REFERENCES processed_statuses (status);
ALTER TABLE consignment_status_mappings ADD CONSTRAINT consignment_status_mappings_processed_status_delivery_partner_id_unprocessed_status_id_uq UNIQUE (processed_status, delivery_partner_id, unprocessed_status_id);
ALTER TABLE consignments ALTER COLUMN partner_id DROP NOT NULL;

INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (1, 'UNMAPPED', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (3, 'PENDING', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (10, 'NOTIFY', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (11, 'DROPPED', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (13, 'QUICK_CHECKOUT', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (12, 'CHECKOUT_REQUESTED', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (14, 'BACK_ORDER', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (4, 'PLACED', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (9, 'WAITING_FOR_DELIVERY', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (5, 'ENROUTE', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (6, 'COMPLETED', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (7, 'CANCELLED', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (8, 'UPCOMING', extract(epoch from now()), extract(epoch from now()));
INSERT INTO processed_statuses (id, status, created_at, updated_at) VALUES (15, 'SCHEDULED', extract(epoch from now()), extract(epoch from now()));

INSERT INTO delivery_partners (id, name, is_active,partner_type, created_at, updated_at) VALUES (1, 'CLICKPOST',true,'EXTERNAL' ,extract(epoch from now()), extract(epoch from now()));

INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (1, 'PLACED', 'ORDER_PLACED', 1, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (2, 'PLACED', 'OUT_FOR_PICKUP', 25, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (3, 'PLACED', 'AWB_REGISTERED', 28, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (4, 'PLACED', 'PICKUP_PENDING', 2, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (5, 'ENROUTE', 'PICKED_UP', 4, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (6, 'ENROUTE', 'IN_TRANSIT', 5, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (7, 'ENROUTE', 'OUT_FOR_DELIVERY', 6, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (8, 'COMPLETED', 'DELIVERED', 8, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (9, 'UNMAPPED', 'LOST', 16, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (10, 'UNMAPPED', 'FAILED_DELIVERY', 9, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (11, 'UNMAPPED', 'NOT_SERVICEABLE', 7, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (12, 'CANCELLED', 'CANCELLED_ORDER', 10, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (13, 'UNMAPPED', 'RTO_REQUESTED', 11, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (14, 'UNMAPPED', 'RTO', 12, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (15, 'UNMAPPED', 'RTO_CONTACT_CUSTOMER_CARE', 26, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (16, 'UNMAPPED', 'RTO_DELIVERED', 14, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (17, 'UNMAPPED', 'RTO_FAILED', 15, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (18, 'UNMAPPED', 'RTO_IN_TRANSIT', 21, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (19, 'UNMAPPED', 'RTO_SHIPMENT_DELAY', 27, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (20, 'UNMAPPED', 'SHIPMENT_DELAYED', 18, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (21, 'UNMAPPED', 'CONTACT_CUSTOMER_CARE', 19, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (22, 'UNMAPPED', 'SHIPMENT_HELD', 20, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (23, 'UNMAPPED', 'RETURN_ORDER_PLACED', 101, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (24, 'UNMAPPED', 'PICKUP_CANCELLED', 3, extract(epoch from now()), extract(epoch from now()),1);
INSERT INTO consignment_status_mappings (id, processed_status, unprocessed_status, unprocessed_status_id, created_at, updated_at, delivery_partner_id) VALUES (25, 'UNMAPPED', 'RTO_OUT_FOR_DELIVERY', 13, extract(epoch from now()), extract(epoch from now()),1);


ALTER TABLE consignments ALTER COLUMN customer_name SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN customer_address SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN customer_city SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN customer_state SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN customer_pincode SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN customer_country SET DEFAULT 'INDIA';
ALTER TABLE consignments ALTER COLUMN customer_country SET NOT NULL;
