# COIT20258-DRS-Enhanced

DRS-Enhanced is the group project for COIT20258 Assignment 3.

## Project Description
This project is a 3-tier Java Disaster Response System with:
- JavaFX client
- Multi-threaded server
- Service layer
- DAO layer
- MySQL database

## Repository Branches

| Person | Role | Branch |
|---|---|---|
| P1 | Team Leader, Database, DAOs, Integration | feature/database-integration |
| P2 | Requirements, Services, Report Lead | feature/requirements-services |
| P3 | Models, UML, ERD | feature/models-design |
| P4 | JavaFX Client, GUI, Controllers | feature/javafx-client |
| P5 | Server, Protocol, Testing | feature/server-testing |

## Main Features
1. Resource and Department Availability Management
2. Evacuation and Shelter Capacity Tracking

## Development Setup

- Install JDK 21 and configure it as `JDK_21` in NetBeans.
- Open the `DRS_Enhanced` NetBeans project.
- Build and run the project normally.

JavaFX 21.0.2 Windows libraries and MySQL Connector/J 8.4.0 are stored in the
repository `lib/` directory. The project uses relative library paths, so a
separate local JavaFX SDK installation is not required on Windows.

### Database Setup

Run `sql/drs_enhanced_schema.sql` in MySQL before using database-backed
features. The default connection is:

- URL: `jdbc:mysql://localhost:3306/drs_enhanced`
- User: `root`
- Password: `pass`

Override these defaults with `DRS_DB_URL`, `DRS_DB_USER`, and
`DRS_DB_PASSWORD` environment variables. Existing databases created with the
old schema can be migrated using `sql/migrate_legacy_schema.sql` after taking a
backup.

## GitHub Rules
- Do not push directly to main.
- Do not push directly to develop.
- Each member works only on their assigned branch.
- Use clear commit messages.
- Create GitHub Issues for problems found and resolved.
