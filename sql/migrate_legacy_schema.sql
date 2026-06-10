-- One-time migration for databases created from the original schema.
-- Back up local data before running this file.

USE drs_enhanced;

RENAME TABLE disaster_reports TO disaster_reports_legacy;
RENAME TABLE shelters TO shelters_legacy;

SOURCE drs_enhanced_schema.sql;

INSERT INTO shelters (shelter_id, name, location, capacity, current_occupancy)
SELECT shelter_id, shelter_name, location, capacity, current_occupancy
FROM shelters_legacy;

-- Legacy reports do not contain the new incident relationship. They remain in
-- disaster_reports_legacy for manual review and mapping to citizen accounts.
