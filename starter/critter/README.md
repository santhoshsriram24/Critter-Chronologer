# Critter Chronologer - starter/critter

Quick run & test instructions (PowerShell)

1. Run tests

```powershell
mvn -f starter/critter/pom.xml test
```

2. Start locally using the included `local` profile (uses in-memory H2 and port 8083)

```powershell
mvn "-Dspring-boot.run.profiles=local" -f starter/critter/pom.xml spring-boot:run
```

3. Alternative: start on a different port (quote the whole -D argument)

```powershell
mvn "-Dspring-boot.run.arguments=--server.port=8084" -f starter/critter/pom.xml spring-boot:run
```

4. Quick endpoint checks (PowerShell/curl)

```powershell
# list schedules
curl.exe http://localhost:8083/schedule

# create resources use Postman or curl with JSON payloads
```

5. Postman collection

Import `postman/Critter.postman_collection.json` into Postman to run example requests for customers, pets, employees and schedules.
