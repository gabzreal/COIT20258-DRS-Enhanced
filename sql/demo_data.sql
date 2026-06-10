USE drs_enhanced;

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
