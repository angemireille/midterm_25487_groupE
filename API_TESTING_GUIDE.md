# API Testing Guide - Library Management System

## Prerequisites
1. Start your Spring Boot application: `mvn spring-boot:run`
2. Ensure PostgreSQL is running with database `library_db`
3. Application should be running on `http://localhost:8080`

## Testing Order (Follow this sequence)

### STEP 1: Test Location Hierarchy (Requirement 2)

#### 1.1 Create Province
```
POST http://localhost:8080/api/locations/save
Content-Type: application/json

{
    "code": "01",
    "name": "Kigali City",
    "type": "PROVINCE",
    "parentCode": null
}
```
**Expected Response:** "Location saved successfully."

#### 1.2 Create District
```
POST http://localhost:8080/api/locations/save
Content-Type: application/json

{
    "code": "0101",
    "name": "Nyarugenge",
    "type": "DISTRICT",
    "parentCode": "01"
}
```

#### 1.3 Create Sector
```
POST http://localhost:8080/api/locations/save
Content-Type: application/json

{
    "code": "010101",
    "name": "Gitega",
    "type": "SECTOR",
    "parentCode": "0101"
}
```

#### 1.4 Create Cell
```
POST http://localhost:8080/api/locations/save
Content-Type: application/json

{
    "code": "01010101",
    "name": "Akagera",
    "type": "CELL",
    "parentCode": "010101"
}
```

#### 1.5 Create Village
```
POST http://localhost:8080/api/locations/save
Content-Type: application/json

{
    "code": "0101010101",
    "name": "Ubumwe",
    "type": "VILLAGE",
    "parentCode": "01010101"
}
```

#### 1.6 Test Location Retrieval
```
GET http://localhost:8080/api/locations/provinces
GET http://localhost:8080/api/locations/districts/01
GET http://localhost:8080/api/locations/sectors/0101
GET http://localhost:8080/api/locations/cells/010101
GET http://localhost:8080/api/locations/villages/01010101
```

### STEP 2: Test Categories and Authors (Requirements 4 & 5)

#### 2.1 Create Categories
```
POST http://localhost:8080/api/category/add
Content-Type: application/json

{
    "name": "Fiction"
}
```

```
POST http://localhost:8080/api/category/add
Content-Type: application/json

{
    "name": "Science"
}
```

#### 2.2 Create Authors
```
POST http://localhost:8080/api/author/add
Content-Type: application/json

{
    "name": "J.K. Rowling"
}
```

```
POST http://localhost:8080/api/author/add
Content-Type: application/json

{
    "name": "Stephen King"
}
```

### STEP 3: Test Member Management (Requirements 6 & 8)

#### 3.1 Create Member with Address (One-to-One relationship)
```
POST http://localhost:8080/api/member/add
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "USER",
    "villageCode": "0101010101",
    "address": {
        "street": "123 Main St",
        "city": "Kigali",
        "province": "Kigali City",
        "postalCode": "12345",
        "country": "Rwanda"
    }
}
```

#### 3.2 Create Another Member
```
POST http://localhost:8080/api/member/add
Content-Type: application/json

{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "password": "password456",
    "role": "USER",
    "villageCode": "0101010101",
    "address": {
        "street": "456 Oak Ave",
        "city": "Kigali",
        "province": "Kigali City",
        "postalCode": "12346",
        "country": "Rwanda"
    }
}
```

### STEP 4: Test Books (Many-to-Many relationship)

#### 4.1 Create Book with Multiple Authors
```
POST http://localhost:8080/api/book/add
Content-Type: application/json

{
    "title": "Fantasy Adventure",
    "authorIds": [1, 2],
    "categoryId": 1,
    "language": "English",
    "totalCopies": 10,
    "availableCopies": 10
}
```

#### 4.2 Create More Books
```
POST http://localhost:8080/api/book/add
Content-Type: application/json

{
    "title": "Science Fiction Novel",
    "authorIds": [1],
    "categoryId": 2,
    "language": "English",
    "totalCopies": 5,
    "availableCopies": 5
}
```

### STEP 5: Test Sorting & Pagination (Requirement 3)

#### 5.1 Test Book Pagination
```
GET http://localhost:8080/api/book/paginated?page=0&size=5&sortBy=title&direction=asc
GET http://localhost:8080/api/book/paginated?page=0&size=5&sortBy=title&direction=desc
```

#### 5.2 Test Book Sorting
```
GET http://localhost:8080/api/book/sorted?sortBy=title&direction=asc
GET http://localhost:8080/api/book/sorted?sortBy=language&direction=desc
```

#### 5.3 Test Member Pagination
```
GET http://localhost:8080/api/member/paginated?page=0&size=10&sortBy=name&direction=asc
GET http://localhost:8080/api/member/sorted?sortBy=email&direction=asc
```

### STEP 6: Test Province Queries (Requirement 8)

#### 6.1 Get Members by Province Code
```
GET http://localhost:8080/api/member/by-province/code/01
```

#### 6.2 Get Members by Province Name
```
GET http://localhost:8080/api/member/by-province/name/Kigali City
```

#### 6.3 Get All Villages in Province (copy province UUID from previous responses)
```
GET http://localhost:8080/api/locations/province/{PROVINCE_UUID}/villages
```

### STEP 7: Test ExistBy Methods (Requirement 7)

#### 7.1 Check if Email Exists
```
GET http://localhost:8080/api/member/exists/email/john@example.com
```

#### 7.2 Check if Location Code Exists
```
GET http://localhost:8080/api/locations/exists/code/01
```

### STEP 8: Test Authentication Features

#### 8.1 Member Signup
```
POST http://localhost:8080/api/member/signup
Content-Type: application/json

{
    "name": "Test User",
    "email": "test@example.com",
    "password": "testpass123"
}
```

#### 8.2 Member Login
```
POST http://localhost:8080/api/member/login
Content-Type: application/json

{
    "name": "Test User",
    "password": "testpass123"
}
```

## Expected Results Summary

### ✅ What Should Work:
1. **Location Hierarchy**: All locations save with proper parent-child relationships
2. **Pagination**: Returns paginated results with proper metadata
3. **Sorting**: Results sorted by specified fields
4. **Many-to-Many**: Books associated with multiple authors
5. **One-to-Many**: Categories containing multiple books
6. **One-to-One**: Members with unique addresses
7. **Province Queries**: Members filtered by province code/name
8. **ExistBy**: Boolean responses for existence checks

### 🔍 Verification Points:
- Check response status codes (200 for success, 400/404 for errors)
- Verify JSON structure matches expected DTOs
- Confirm relationships are properly established
- Test pagination metadata (totalElements, totalPages, etc.)
- Validate sorting order in responses

## Troubleshooting

### Common Issues:
1. **Database Connection**: Ensure PostgreSQL is running
2. **Port Conflicts**: Check if port 8080 is available
3. **Foreign Key Errors**: Create parent entities before children
4. **JSON Format**: Ensure proper Content-Type headers

### Quick Fixes:
- Restart application if needed
- Check application logs for detailed error messages
- Verify database tables are created properly
- Test with simple GET requests first