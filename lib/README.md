# Shared Runtime Libraries

This directory contains the binary dependencies used by the NetBeans Ant
project in `DRS_Enhanced`.

Included dependencies:

- OpenJFX 21.0.2 for Windows
- MySQL Connector/J 8.4.0

The paths in `DRS_Enhanced/nbproject/project.properties` are relative to the
project, so team members do not need to configure a local JavaFX SDK path.

JavaFX native libraries are platform-specific. The committed OpenJFX jars are
for Windows and require JDK 21. Team members using macOS or Linux must use the
matching OpenJFX platform artifacts instead.

Dependency licenses:

- OpenJFX: GPLv2 with the Classpath Exception
- MySQL Connector/J: GPLv2 with the Universal FOSS Exception
