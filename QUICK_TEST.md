# Quick Application Test

## 1. Start your application
Before testing, make sure:
1. PostgreSQL is running
2. Database 'library_db' exists
3. Update your database credentials in application.properties

## 2. Start the Spring Boot application
Run this command in your project directory:
```bash
mvn spring-boot:run
```

## 3. Quick Test - Create a Province
Once your app is running, test this endpoint first:

**POST** http://localhost:8080/api/locations/save
**Headers:** Content-Type: application/json
**Body:**
```json
{
    "code": "01",
    "name": "Kigali City",
    "type": "PROVINCE",
    "parentCode": null
}
```

**Expected Response:** "Location saved successfully."

## 4. Test Province Retrieval
**GET** http://localhost:8080/api/locations/provinces

**Expected Response:** JSON array with the province you just created

## 5. If both tests work, your system is fully functional!

Then follow the complete testing guide in API_TESTING_GUIDE.md for all requirements.

## Database Setup Reminder
Make sure your PostgreSQL database is set up:
1. Database name: library_db
2. Update username/password in application.properties
3. The application will auto-create tables on first run