-- Automatic startup schema for DRS-Enhanced.
-- Gabriel Fernandez Balbuena - 12292617

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL,
    
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS incidents (
    incident_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    location VARCHAR(150) NOT NULL,
    severity VARCHAR(30) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    assigned_worker VARCHAR(100),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_incidents_status (status),
    INDEX idx_incidents_worker (assigned_worker)
);

CREATE TABLE IF NOT EXISTS disaster_reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    citizen_id INT NULL,
    incident_id INT,
    disaster_type VARCHAR(100),
    location VARCHAR(150),
    description TEXT,
    people_affected INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reports_citizen
        FOREIGN KEY (citizen_id) REFERENCES users(user_id),
    CONSTRAINT fk_reports_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(incident_id),
    INDEX idx_reports_citizen (citizen_id),
    INDEX idx_reports_incident (incident_id)
);

CREATE TABLE IF NOT EXISTS resource_dispatch (
    dispatch_id INT AUTO_INCREMENT PRIMARY KEY,
    incident_id INT NOT NULL,
    resource_type VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'REQUESTED',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_dispatch_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(incident_id),
    INDEX idx_dispatch_incident (incident_id)
);

CREATE TABLE IF NOT EXISTS status_updates (
    update_id INT AUTO_INCREMENT PRIMARY KEY,
    incident_id INT NOT NULL,
    worker_id INT NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_updates_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(incident_id),
    CONSTRAINT fk_updates_worker
        FOREIGN KEY (worker_id) REFERENCES users(user_id),
    INDEX idx_updates_incident (incident_id),
    INDEX idx_updates_worker (worker_id)
);

CREATE TABLE IF NOT EXISTS shelters (
    shelter_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(150),
    capacity INT NOT NULL,
    current_occupancy INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_shelter_capacity CHECK (capacity >= 0),
    CONSTRAINT chk_shelter_occupancy
        CHECK (current_occupancy BETWEEN 0 AND capacity)
);

CREATE TABLE IF NOT EXISTS notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_notifications_user
        FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_notifications_user_read (user_id, is_read)
);

CREATE TABLE IF NOT EXISTS assessments (
    assessment_id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT NOT NULL,
    severity_level VARCHAR(30) NOT NULL,
    priority_level VARCHAR(30) NOT NULL,
    priority_score INT NOT NULL,
    impact_summary TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assessments_report
        FOREIGN KEY (report_id) REFERENCES disaster_reports(report_id)
);

CREATE TABLE IF NOT EXISTS departments (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    department_type VARCHAR(100) NOT NULL,
    contact_info VARCHAR(150),
    availability_status VARCHAR(30) NOT NULL DEFAULT 'Available',
    available_units INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS resources (
    resource_id INT AUTO_INCREMENT PRIMARY KEY,
    department_id INT NOT NULL,
    resource_type VARCHAR(100) NOT NULL,
    resource_name VARCHAR(100) NOT NULL,
    availability_status VARCHAR(30) NOT NULL DEFAULT 'Available',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_resources_department
        FOREIGN KEY (department_id) REFERENCES departments(department_id)
);

CREATE TABLE IF NOT EXISTS evacuation_records (
    evacuation_id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT NOT NULL,
    shelter_id INT NOT NULL,
    people_affected INT NOT NULL,
    evacuation_required BOOLEAN NOT NULL,
    assigned_people INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evacuation_report
        FOREIGN KEY (report_id) REFERENCES disaster_reports(report_id),
    CONSTRAINT fk_evacuation_shelter
        FOREIGN KEY (shelter_id) REFERENCES shelters(shelter_id)
);

CREATE TABLE IF NOT EXISTS coordination_records (
    coordination_id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT NOT NULL,
    department_id INT NOT NULL,
    resource_id INT,
    assigned_action VARCHAR(150),
    incident_status VARCHAR(50),
    update_note TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_coordination_report
        FOREIGN KEY (report_id) REFERENCES disaster_reports(report_id),
    CONSTRAINT fk_coordination_department
        FOREIGN KEY (department_id) REFERENCES departments(department_id),
    CONSTRAINT fk_coordination_resource
        FOREIGN KEY (resource_id) REFERENCES resources(resource_id)
);

CREATE TABLE IF NOT EXISTS response_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    incident_id INT NOT NULL,
    action VARCHAR(150) NOT NULL,
    performed_by INT NOT NULL,
    details TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_response_incident
        FOREIGN KEY (incident_id) REFERENCES incidents(incident_id),
    CONSTRAINT fk_response_user
        FOREIGN KEY (performed_by) REFERENCES users(user_id)
);
