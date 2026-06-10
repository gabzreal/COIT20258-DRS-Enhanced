Team,

The JavaFX client workflow is now complete and ready for integration.

For the system to function as a real Disaster Response System (DRS), each branch needs to support the following functionality.

---

## feature/models-design

Create all core model classes used by the UI and server.

Required models:

* User
* Citizen
* EmergencyWorker
* CityManager
* Incident
* DisasterReport
* Resource
* Shelter
* DispatchRequest
* StatusUpdate
* Notification

Example flow:

Citizen submits DisasterReport
↓
DisasterReport becomes Incident
↓
Incident assigned to EmergencyWorker
↓
StatusUpdate recorded
↓
CityManager dashboard updated

---

## feature/database-integration

Create database schema and DAO layer.

Required tables:

USERS

* user_id
* username
* password
* role

INCIDENTS

* incident_id
* type
* location
* severity
* status
* assigned_worker

DISASTER_REPORTS

* report_id
* citizen_id
* incident_id

RESOURCE_DISPATCH

* dispatch_id
* incident_id
* resource_type
* status

STATUS_UPDATES

* update_id
* incident_id
* worker_id
* message
* timestamp

SHELTERS

* shelter_id
* name
* capacity
* current_occupancy

The database must support all dashboard information currently shown in the UI.

---

## feature/server-testing

Create server communication layer.

Required functionality:

* User login authentication
* Incident retrieval
* Incident updates
* Dispatch requests
* Resource requests
* Worker status updates
* Live notifications

Server should provide data to:

Citizen Dashboard
City Manager Dashboard
Emergency Worker Dashboard

Currently all UI data is hardcoded and should eventually come from server responses.

---

## feature/requirements-services

Implement business services.

Required services:

AuthenticationService

* login()
* logout()

IncidentService

* createIncident()
* getIncidents()
* updateIncidentStatus()

DispatchService

* assignResources()
* requestSupport()

ShelterService

* getShelters()
* updateCapacity()

NotificationService

* sendNotification()
* getNotifications()

WorkerService

* assignMission()
* submitStatusUpdate()

CitizenService

* submitReport()
* viewReports()

These services will connect the UI layer to database and server layers.

---

## Final Expected Workflow

Citizen Login
↓
Citizen submits disaster report
↓
Report stored in database
↓
Server creates incident
↓
City Manager dashboard receives new incident
↓
Manager assesses incident
↓
Resources dispatched
↓
Emergency Worker receives mission
↓
Worker updates status in field
↓
Database updated
↓
Manager dashboard refreshed
↓
Citizen receives live updates
↓
Incident resolved
↓
System archives incident

Current Status:

✓ JavaFX Client Workflow Complete

Pending:

* Models Integration
* Database Integration
* Service Layer
* Server Communication
* End-to-End Testing

Once these modules are connected, the system will operate as a fully integrated DRS rather than a UI prototype.


Recommended Order
Phase 1 (First Priority)
1. feature/models-design

This should be completed first.

Reason:

Every other team needs the model classes.
Database tables depend on models.
Services depend on models.
Server messages depend on models.
UI controllers will eventually use models.

Example:

Incident
Citizen
EmergencyWorker
DisasterReport
Shelter

Without these classes everyone will create their own versions and integration becomes messy.

Phase 2 (Can run in parallel)
2. feature/database-integration

Uses models to create:

INCIDENTS
USERS
SHELTERS
REPORTS
3. feature/requirements-services

Uses models to create:

IncidentService
CitizenService
DispatchService
WorkerService

These can be mocked initially without database.

Phase 3
4. feature/server-testing

After models and services exist.

Reason:

Server messages will need:

Incident
StatusUpdate
ResourceRequest

already defined.

Server team can then expose:

LOGIN
GET_INCIDENTS
CREATE_REPORT
UPDATE_STATUS
REQUEST_SUPPORT

## Notes for Integration Team

The JavaFX client module is currently implemented using static/demo data to demonstrate the complete Disaster Response System workflow and user interface.

When integrating your respective branches, you may replace these static values with dynamic data retrieved from the database, service layer, or server.

Examples include:

### Citizen Dashboard

Current implementation:

```java
reportList.getItems().add(
    "INC-1001 | Flood | Responding"
);
```

Can be replaced with:

```java
// Dynamic data from IncidentService
reportList.getItems().addAll(
    citizenService.getCitizenReports()
);
```

---

### City Manager Dashboard

Current implementation:

```java
incidentList.getItems().add(
    "Fire - Melbourne CBD [HIGH]"
);
```

Can be replaced with:

```java
// Dynamic incident data from server/database
incidentList.getItems().addAll(
    incidentService.getActiveIncidents()
);
```

---

### Emergency Worker Dashboard

Current implementation:

```java
statusLabel.setText("Mission Active");
```

Can be replaced with:

```java
// Dynamic mission status
statusLabel.setText(
    workerService.getCurrentMissionStatus()
);
```

---

### Shelter Information

Current implementation:

```java
Label text="Town Hall Shelter : 120 / 200"
```

Can be replaced with:

```java
// Dynamic shelter capacity data
shelterService.getShelterCapacity()
```

---

### Resource Monitoring

Current implementation:

```java
ProgressBar progress="0.80"
```

Can be replaced with:

```java
// Dynamic resource availability
resourceService.getFireServiceCapacity()
```

---

### Important

Please retain:

* Existing FXML layouts
* Screen navigation flow
* Controller event methods
* UI structure and design

Only replace hardcoded/static values with dynamic service, database, or server data where appropriate.

If any UI modifications are required during integration, please add a short code comment indicating the reason for the change.

Example:

```java
// Modified by Database Integration Team:
// Replaced static incident list with database query results.
incidentList.setItems(
    incidentService.getActiveIncidents()
);
```

or

```java
// Modified by Server Team:
// Data now received from live server updates.
statusLabel.setText(serverResponse.getStatus());
```

This will help track integration changes across branches and simplify final testing and documentation.