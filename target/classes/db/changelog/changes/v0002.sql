ALTER TABLE consignments ADD COLUMN invoice_number VARCHAR NOT NULL;
ALTER TABLE consignments ADD COLUMN invoice_date VARCHAR NOT NULL;
ALTER TABLE consignments ADD COLUMN security_key TEXT;
ALTER TABLE consignments ADD COLUMN label TEXT;
ALTER TABLE serviceability_mappings ALTER COLUMN delivery_partners SET NOT NULL;
ALTER TABLE serviceability_mapping_logs DROP COLUMN delivery_partners;
ALTER TABLE serviceability_mapping_logs ADD COLUMN delivery_partners JSONB NOT NULL;
Alter table consignments add column items JSONB NOT NULL;
ALTER TABLE fc ADD COLUMN created_at BIGINT;
ALTER TABLE fc ADD COLUMN updated_at BIGINT;
