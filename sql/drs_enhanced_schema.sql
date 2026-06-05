-- =====================================================
-- COIT20258 Assignment 3
-- DRS-Enhanced Database Schema
-- Author: Gabriel Fernandez Balbuena - 12292617
-- =====================================================

CREATE DATABASE IF NOT EXISTS drs_enhanced;

USE drs_enhanced;

-- =====================================================
-- USERS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS users (

    user_id INT AUTO_INCREMENT PRIMARY KEY,

    username VARCHAR(50) NOT NULL UNIQUE,

    password_hash VARCHAR(64) NOT NULL,

    full_name VARCHAR(100) NOT NULL,

    role VARCHAR(30) NOT NULL,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- DISASTER REPORTS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS disaster_reports (

    report_id INT AUTO_INCREMENT PRIMARY KEY,

    disaster_type VARCHAR(100) NOT NULL,

    location VARCHAR(150) NOT NULL,

    description TEXT NOT NULL,

    people_affected INT NOT NULL,

    status VARCHAR(50) DEFAULT 'Pending',

    created_by INT,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (created_by)
    REFERENCES users(user_id)
);

-- =====================================================
-- ASSESSMENTS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS assessments (

    assessment_id INT AUTO_INCREMENT PRIMARY KEY,

    report_id INT NOT NULL,

    severity_level VARCHAR(30) NOT NULL,

    priority_level VARCHAR(30) NOT NULL,

    priority_score INT NOT NULL,

    impact_summary TEXT,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (report_id)
    REFERENCES disaster_reports(report_id)
);

-- =====================================================
-- DEPARTMENTS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS departments (

    department_id INT AUTO_INCREMENT PRIMARY KEY,

    department_name VARCHAR(100) NOT NULL,

    department_type VARCHAR(100) NOT NULL,

    contact_info VARCHAR(150),

    availability_status VARCHAR(30)
    DEFAULT 'Available',

    available_units INT DEFAULT 0,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- RESOURCES TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS resources (

    resource_id INT AUTO_INCREMENT PRIMARY KEY,

    department_id INT NOT NULL,

    resource_type VARCHAR(100) NOT NULL,

    resource_name VARCHAR(100) NOT NULL,

    availability_status VARCHAR(30)
    DEFAULT 'Available',

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (department_id)
    REFERENCES departments(department_id)
);

-- =====================================================
-- SHELTERS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS shelters (

    shelter_id INT AUTO_INCREMENT PRIMARY KEY,

    shelter_name VARCHAR(100) NOT NULL,

    location VARCHAR(150) NOT NULL,

    capacity INT NOT NULL,

    current_occupancy INT DEFAULT 0,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- EVACUATION RECORDS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS evacuation_records (

    evacuation_id INT AUTO_INCREMENT PRIMARY KEY,

    report_id INT NOT NULL,

    shelter_id INT NOT NULL,

    people_affected INT NOT NULL,

    evacuation_required BOOLEAN NOT NULL,

    assigned_people INT NOT NULL,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (report_id)
    REFERENCES disaster_reports(report_id),

    FOREIGN KEY (shelter_id)
    REFERENCES shelters(shelter_id)
);

-- =====================================================
-- COORDINATION RECORDS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS coordination_records (

    coordination_id INT AUTO_INCREMENT PRIMARY KEY,

    report_id INT NOT NULL,

    department_id INT NOT NULL,

    resource_id INT,

    assigned_action VARCHAR(150),

    incident_status VARCHAR(50),

    update_note TEXT,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (report_id)
    REFERENCES disaster_reports(report_id),

    FOREIGN KEY (department_id)
    REFERENCES departments(department_id),

    FOREIGN KEY (resource_id)
    REFERENCES resources(resource_id)
);

-- =====================================================
-- RESPONSE LOGS TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS response_logs (

    log_id INT AUTO_INCREMENT PRIMARY KEY,

    report_id INT NOT NULL,

    action VARCHAR(150) NOT NULL,

    performed_by INT NOT NULL,

    details TEXT,

    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (report_id)
    REFERENCES disaster_reports(report_id),

    FOREIGN KEY (performed_by)
    REFERENCES users(user_id)
);