CREATE SEQUENCE clickpost_logs_id_seq;
CREATE TABLE clickpost_logs (
    id BIGINT DEFAULT NEXTVAL('clickpost_logs_id_seq'),
    pickup_pincode VARCHAR(6),
    drop_pincode VARCHAR(6),
    partner_id BIGINT,
    serviceability_id BIGINT,
    request_type VARCHAR,
    details TEXT,
    created_at BIGINT,
    updated_at BIGINT,
    primary key(id),
    CONSTRAINT clickpost_logs_serviceability_id_fk FOREIGN KEY (serviceability_id) REFERENCES serviceability_mappings (id),
    CONSTRAINT clickpost_logs_partner_id_fk FOREIGN KEY (partner_id) REFERENCES delivery_partners (id)
);
