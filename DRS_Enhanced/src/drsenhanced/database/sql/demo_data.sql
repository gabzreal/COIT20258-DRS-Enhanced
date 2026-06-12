-- Idempotent demo data loaded during application startup.
-- Gabriel Fernandez Balbuena - 12292617

INSERT INTO users (username, password_hash, full_name, role)
VALUES
    ('citizen',
     '4b4b4c19fdc4b422ca5a52085c3ba8fd2087c62afb06dae791f8fb9c51c56b4b',
     'Demo Citizen', 'Citizen'),
    ('manager',
     '866485796cfa8d7c0cf7111640205b83076433547577511d81f8030ae99ecea5',
     'Demo City Manager', 'CityManager'),
    ('worker',
     '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
     'Demo Emergency Worker', 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO shelters (name, location, capacity, current_occupancy)
SELECT 'Town Hall Shelter', 'Melbourne CBD', 200, 120
WHERE NOT EXISTS (
    SELECT 1 FROM shelters WHERE name = 'Town Hall Shelter'
);

INSERT INTO shelters (name, location, capacity, current_occupancy)
SELECT 'Docklands Community Centre', 'Docklands', 150, 40
WHERE NOT EXISTS (
    SELECT 1 FROM shelters WHERE name = 'Docklands Community Centre'
);
INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker2',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 2',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker3',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 3',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker4',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 4',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker5',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 5',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker6',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 6',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker7',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 7',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

INSERT INTO users (username, password_hash, full_name, role)
VALUES
('worker8',
 '312bba6ac1c4274943d7d3c1f346e8e27310c731e407ce5592d82f0d101fbff1',
 'Demo Emergency Worker 8',
 'EmergencyWorker')
ON DUPLICATE KEY UPDATE username = VALUES(username);

-- Demo Incidents

INSERT INTO incidents
(type, location, severity, status)
SELECT
'Fire',
'Melbourne CBD',
'HIGH',
'PENDING'
WHERE NOT EXISTS (
    SELECT 1
    FROM incidents
    WHERE type = 'Fire'
    AND location = 'Melbourne CBD'
);

INSERT INTO incidents
(type, location, severity, status)
SELECT
'Flood',
'Richmond',
'MEDIUM',
'PENDING'
WHERE NOT EXISTS (
    SELECT 1
    FROM incidents
    WHERE type = 'Flood'
    AND location = 'Richmond'
);

INSERT INTO incidents
(type, location, severity, status)
SELECT
'Storm Damage',
'Southbank',
'HIGH',
'PENDING'
WHERE NOT EXISTS (
    SELECT 1
    FROM incidents
    WHERE type = 'Storm Damage'
    AND location = 'Southbank'
);

INSERT INTO incidents
(type, location, severity, status)
SELECT
'Power Outage',
'Docklands',
'LOW',
'PENDING'
WHERE NOT EXISTS (
    SELECT 1
    FROM incidents
    WHERE type = 'Power Outage'
    AND location = 'Docklands'
);
-- Demo Response Logs

INSERT INTO response_logs
(incident_id, action, performed_by, details)
SELECT
1,
'INITIAL_RESPONSE',
2,
'Fire brigade dispatched'
WHERE NOT EXISTS (
    SELECT 1
    FROM response_logs
    WHERE action = 'INITIAL_RESPONSE'
);

INSERT INTO response_logs
(incident_id, action, performed_by, details)
SELECT
2,
'SITUATION_ASSESSED',
2,
'Flood assessment completed'
WHERE NOT EXISTS (
    SELECT 1
    FROM response_logs
    WHERE action = 'SITUATION_ASSESSED'
);

INSERT INTO response_logs
(incident_id, action, performed_by, details)
SELECT
3,
'RESOURCE_ALLOCATED',
2,
'Emergency team assigned'
WHERE NOT EXISTS (
    SELECT 1
    FROM response_logs
    WHERE action = 'RESOURCE_ALLOCATED'
);