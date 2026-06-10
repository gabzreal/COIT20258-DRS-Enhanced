-- Run this file from the MySQL command-line client at the repository root:
-- mysql -u root -p < sql/migrate_legacy_schema.sql
--
-- It preserves the complete legacy schema in drs_enhanced_legacy_backup,
-- rebuilds drs_enhanced, and restores compatible user and department data.

CREATE DATABASE IF NOT EXISTS drs_enhanced_legacy_backup
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.users
LIKE drs_enhanced.users;
INSERT IGNORE INTO drs_enhanced_legacy_backup.users
SELECT * FROM drs_enhanced.users;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.departments
LIKE drs_enhanced.departments;
INSERT IGNORE INTO drs_enhanced_legacy_backup.departments
SELECT * FROM drs_enhanced.departments;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.disaster_reports
LIKE drs_enhanced.disaster_reports;
INSERT IGNORE INTO drs_enhanced_legacy_backup.disaster_reports
SELECT * FROM drs_enhanced.disaster_reports;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.assessments
LIKE drs_enhanced.assessments;
INSERT IGNORE INTO drs_enhanced_legacy_backup.assessments
SELECT * FROM drs_enhanced.assessments;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.resources
LIKE drs_enhanced.resources;
INSERT IGNORE INTO drs_enhanced_legacy_backup.resources
SELECT * FROM drs_enhanced.resources;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.shelters
LIKE drs_enhanced.shelters;
INSERT IGNORE INTO drs_enhanced_legacy_backup.shelters
SELECT * FROM drs_enhanced.shelters;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.evacuation_records
LIKE drs_enhanced.evacuation_records;
INSERT IGNORE INTO drs_enhanced_legacy_backup.evacuation_records
SELECT * FROM drs_enhanced.evacuation_records;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.coordination_records
LIKE drs_enhanced.coordination_records;
INSERT IGNORE INTO drs_enhanced_legacy_backup.coordination_records
SELECT * FROM drs_enhanced.coordination_records;

CREATE TABLE IF NOT EXISTS drs_enhanced_legacy_backup.response_logs
LIKE drs_enhanced.response_logs;
INSERT IGNORE INTO drs_enhanced_legacy_backup.response_logs
SELECT * FROM drs_enhanced.response_logs;

DROP DATABASE drs_enhanced;

SOURCE sql/drs_enhanced_schema.sql;

INSERT INTO drs_enhanced.users
    (user_id, username, password_hash, full_name, role, created_at)
SELECT user_id, username, password_hash, full_name, role, created_at
FROM drs_enhanced_legacy_backup.users;

INSERT INTO drs_enhanced.departments
    (department_id, department_name, department_type, contact_info,
     availability_status, available_units, created_at, updated_at)
SELECT department_id, department_name, department_type, contact_info,
       availability_status, available_units, created_at, updated_at
FROM drs_enhanced_legacy_backup.departments;

SOURCE sql/demo_data.sql;
