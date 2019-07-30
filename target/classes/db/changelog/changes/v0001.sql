CREATE SEQUENCE delivery_partners_id_seq;
CREATE TABLE delivery_partners (
  id BIGINT DEFAULT NEXTVAL('delivery_partners_id_seq'),
  name TEXT NOT NULL,
  details JSONB,
  partner_type TEXT,
  is_active boolean default true not null,
  created_at BIGINT,
  updated_at BIGINT,
  deleted_at BIGINT,
  max_consignments_per_day BIGINT,
  is_recommendation_enabled  BOOLEAN default false not null,
  CONSTRAINT delivery_partners_name_uniq UNIQUE (name),
  primary key (id)
);

CREATE SEQUENCE consignment_consolidations_id_seq;
CREATE TABLE consignment_consolidations (
  id BIGINT DEFAULT NEXTVAL('consignment_consolidations_id_seq'),
  consignment_ids BIGINT [],
  created_at BIGINT,
  updated_at BIGINT,
  primary key (id)
);

CREATE SEQUENCE fc_id_seq;
CREATE TABLE fc (
    id BIGINT DEFAULT NEXTVAL('fc_id_seq'),
    name VARCHAR,
    address TEXT,
    city VARCHAR,
    state VARCHAR,
    pincode VARCHAR(6),
    country VARCHAR default 'INDIA',
    phone VARCHAR,
    primary key (id)
);

CREATE SEQUENCE consignments_id_seq;
CREATE TABLE consignments (
  id BIGINT DEFAULT NEXTVAL('consignments_id_seq'),
  status TEXT NOT NULL,
  external_reference_id TEXT NOT NULL,
  partner_id BIGINT NOT NULL,
  awb TEXT,
  expected_sla BIGINT,
  actual_sla BIGINT,
  fc_id BIGINT NOT NULL,
  payable_amount DOUBLE PRECISION,
  customer_name VARCHAR,
  customer_address TEXT,
  customer_city VARCHAR,
  customer_state VARCHAR,
  customer_pincode VARCHAR(6),
  customer_country VARCHAR DEFAULT 'INDIA',
  customer_phone VARCHAR,
  is_prepaid BOOLEAN,
  consignment_value NUMERIC(9,2),
  delivery_type VARCHAR CHECK (delivery_type IN ('inbound', 'outbound')),
  details JSONB,
  dimensions JSONB,
  value_details JSONB,
  is_refrigerated boolean,
  is_lost_by_partner BOOLEAN,
  first_delivered_at BIGINT,
  rto_initiated_at BIGINT,
  rto_delivered_at BIGINT,
  booked_at BIGINT,
  picked_at BIGINT,
  delivered_at BIGINT,
  created_at BIGINT,
  updated_at BIGINT,
  CONSTRAINT consignments_fc_id_fk FOREIGN KEY (fc_id) REFERENCES fc (id),
  primary key (id)
);

CREATE SEQUENCE consignment_status_logs_id_seq;
CREATE TABLE consignment_status_logs (
  id BIGINT DEFAULT NEXTVAL('consignment_status_logs_id_seq'),
  consignment_id BIGINT NOT NULL,
  processed_status TEXT NOT NULL,
  unprocessed_status TEXT NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  CONSTRAINT consignment_status_logs_consignment_id_fk FOREIGN KEY (consignment_id) REFERENCES consignments (id),
  primary key (id)
);

CREATE SEQUENCE consignment_subscriptions_id_seq;
CREATE TABLE consignment_subscriptions (
  id BIGINT DEFAULT NEXTVAL('consignment_subscriptions_id_seq'),
  consignment_id BIGINT NOT NULL,
  endpoint TEXT NOT NULL,
  http_method TEXT NOT NULL,
  headers JSONB,
  retries BIGINT,
  subscribed_statuses TEXT [],
  response JSONB,
  created_at BIGINT,
  updated_at BIGINT,
  deleted_at BIGINT,
  notified_at BIGINT,
  CONSTRAINT consignment_subscriptions_consignment_id_fk FOREIGN KEY (consignment_id) REFERENCES consignments (id),
  primary key (id)
);

CREATE SEQUENCE area_mappings_id_seq;
CREATE TABLE area_mappings (
  id BIGINT DEFAULT NEXTVAL('area_mappings_id_seq'),
  area TEXT NOT NULL,
  pincode BIGINT NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  deleted_at BIGINT,
  primary key (id)
);

CREATE SEQUENCE serviceability_mappings_id_seq;
CREATE TABLE serviceability_mappings (
  id BIGINT DEFAULT NEXTVAL('serviceability_mappings_id_seq'),
  fc_id BIGINT NOT NULL,
  area_mapping_id BIGINT NOT NULL,
  mode TEXT NOT NULL,
  delivery_partners JSONB,
  is_refrigerated_allowed BOOLEAN,
  priority BIGINT NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  deleted_at BIGINT,
  CONSTRAINT serviceability_mappings_area_mapping_id_fk FOREIGN KEY (area_mapping_id) REFERENCES area_mappings (id),
  CONSTRAINT serviceability_mappings_fc_id_fk FOREIGN KEY (fc_id) REFERENCES fc (id),
  primary key (id)
);

CREATE SEQUENCE serviceability_mapping_logs_id_seq;
CREATE TABLE serviceability_mapping_logs (
  id BIGINT DEFAULT NEXTVAL('serviceability_mapping_logs_id_seq'),
  serviceability_mapping_id BIGINT NOT NULL,
  origin_area BIGINT NOT NULL,
  destination_area TEXT NOT NULL,
  mode TEXT NOT NULL,
  delivery_partners BIGINT [],
  is_refrigerated_allowed BOOLEAN,
  priority BIGINT NOT NULL,
  created_at BIGINT,
  updated_at BIGINT,
  CONSTRAINT serviceability_mapping_logs_serviceability_mapping_id_fk FOREIGN KEY (serviceability_mapping_id) REFERENCES serviceability_mappings (id),
  primary key (id)
);
