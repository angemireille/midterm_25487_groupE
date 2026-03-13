# Library Management System

A comprehensive Spring Boot application for managing library operations with hierarchical location management.

## Features

### Core Functionality
- **Location Management**: Hierarchical location system (Province → District → Sector → Cell → Village)
- **Member Management**: User registration, authentication with 2FA, password reset
- **Book Management**: CRUD operations with author and category relationships
- **Borrowing System**: Track book borrowing and returns
- **Advanced Queries**: Search members by province, pagination, sorting

### Technical Features
- **Spring Boot 3.x** with Spring Data JPA
- **PostgreSQL** database
- **Email Integration** for 2FA and password reset
- **RESTful APIs** with proper HTTP status codes
- **DTO Pattern** for data transfer
- **Pagination & Sorting** support

## Database Schema

### Entities (7 Tables)
1. **Location** - Hierarchical location structure
2. **Member** - Library members with authentication
3. **Book** - Book catalog
4. **Author** - Book authors
5. **Category** - Book categories
6. **Borrow** - Borrowing records
7. **Address** - Member addresses

### Relationships
- **Many-to-Many**: Book ↔ Author
- **One-to-Many**: Category → Book, Location → Children
- **One-to-One**: Member ↔ Address
- **Many-to-One**: Member → Location (Village)

## API Endpoints

### Location Management
```
POST   /api/locations/save
GET    /api/locations/provinces
GET    /api/locations/districts/{provinceCode}
GET    /api/locations/sectors/{districtCode}
GET    /api/locations/cells/{sectorCode}
GET    /api/locations/villages/{cellCode}
GET    /api/locations/province/{provinceId}/villages
```

### Member Management
```
POST   /api/member/signup
POST   /api/member/login
POST   /api/member/verify-2fa
GET    /api/member/getAllMember
GET    /api/member/paginated
GET    /api/member/by-province/code/{provinceCode}
GET    /api/member/by-province/name/{provinceName}
```

### Book Management
```
POST   /api/book/add
GET    /api/book/all
GET    /api/book/paginated
GET    /api/book/sorted
GET    /api/book/search
```

### Database Setup
1. Create PostgreSQL database: `library_db`
2. Update database credentials in `application.properties`


### Running the Application
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Testing

### Sample Data Creation Order
1. Create locations (Province → District → Sector → Cell → Village)
2. Create categories and authors
3. Create members with addresses
4. Create books with author relationships
5. Test borrowing functionality

