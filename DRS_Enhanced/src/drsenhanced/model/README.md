# DRS Model Package

This package contains the core model classes for the Disaster Response System.

## Purpose

The model classes define the main data objects shared between the UI, database service layer and server communication layer.

## Workflow

```text
Citizen
   |
   | submits
   v
DisasterReport
   |
   | creates / links to
   v
Incident
   |
   | assigned to
   v
EmergencyWorker
   |
   | submits progress
   v
StatusUpdate
   |
   | updates
   v
CityManager Dashboard
   |
   | sends updates
   v
Notification
```

## Model Classes

| Class           | Purpose                                  |
| --------------- | ---------------------------------------- |
| User            | Base class for all system users          |
| Citizen         | User who submits disaster reports        |
| EmergencyWorker | User assigned to emergency incidents     |
| CityManager     | User who monitors and manages incidents  |
| DisasterReport  | Report submitted by a citizen            |
| Incident        | Emergency incident created from a report |
| Resource        | Resource support linked to incidents     |
| Shelter         | Evacuation shelter details               |
| DispatchRequest | Request for resource dispatch            |
| StatusUpdate    | Progress update from emergency worker    |
| Notification    | Message sent to system users             |

## Integration Notes

These models should be used by:

* JavaFX UI controllers
* DAO/database classes
* Service layer classes
* Server communication classes

This helps all branches use the same data structure and avoids duplicate model definitions.
