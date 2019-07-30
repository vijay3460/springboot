ALTER TABLE consignments ALTER COLUMN payable_amount SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN delivery_type SET NOT NULL;
ALTER TABLE consignments ALTER COLUMN is_refrigerated SET NOT NULL;

ALTER TABLE consignments ADD CONSTRAINT external_reference_id_unique UNIQUE (external_reference_id);
ALTER TABLE delivery_partners ADD CONSTRAINT check_partner_type CHECK (partner_type IN ('INTERNAL', 'EXTERNAL') );
ALTER TABLE serviceability_mappings ALTER COLUMN is_refrigerated_allowed SET NOT NULL;
ALTER TABLE consignment_subscriptions ALTER COLUMN subscribed_statuses SET NOT NULL;