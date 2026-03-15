# Entity Relationship Diagram (ERD)
## Library Management System

### Entities Overview
The system contains **7 main entities** with various relationships:

---

## 1. LOCATION
**Table:** `locations`
- **Primary Key:** `id` (UUID)
- **Attributes:**
  - `id` (UUID) - Primary Key
  - `code` (String, Unique) - Location code
  - `name` (String) - Location name
  - `type` (Enum: PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE)
  - `parent_id` (UUID) - Foreign Key to Location

**Self-Referencing Relationship:**
- **One-to-Many:** Location → Location (parent-children hierarchy)

---

## 2. MEMBER
**Table:** `member`
- **Primary Key:** `member_id` (int)
- **Attributes:**
  - `member_id` (int) - Primary Key
  - `name` (String, 45)
  - `email` (String, 45)
  - `password` (String, 255)
  - `role` (String, 20)
  - `reset_token` (String, 64)
  - `reset_token_expiry` (LocalDateTime)
  - `two_factor_code` (String, 6)
  - `two_factor_expiry` (LocalDateTime)
  - `village_id` (UUID) - Foreign Key to Location
  - `address_id` (int) - Foreign Key to Address

**Relationships:**
- **Many-to-One:** Member → Location (village)
- **One-to-One:** Member → Address

---

## 3. ADDRESS
**Table:** `address`
- **Primary Key:** `id` (int)
- **Attributes:**
  - `id` (int) - Primary Key
  - `street` (String)
  - `city` (String)
  - `province` (String)
  - `postalCode` (String)
  - `country` (String)

**Relationships:**
- **One-to-One:** Address ← Member

---

## 4. CATEGORY
**Table:** `category`
- **Primary Key:** `category_id` (int)
- **Attributes:**
  - `category_id` (int) - Primary Key
  - `name` (String, 45)

**Relationships:**
- **One-to-Many:** Category → Book

---

## 5. BOOK
**Table:** `book`
- **Primary Key:** `book_id` (int)
- **Attributes:**
  - `book_id` (int) - Primary Key
  - `book_title` (String, 45)
  - `language` (String, 30)
  - `total_copies` (int)
  - `available_copies` (int)
  - `category_id` (int) - Foreign Key to Category

**Relationships:**
- **Many-to-One:** Book → Category
- **Many-to-Many:** Book ↔ Author (via book_author join table)
- **One-to-Many:** Book → Borrow

---

## 6. AUTHOR
**Table:** `author`
- **Primary Key:** `author_id` (int)
- **Attributes:**
  - `author_id` (int) - Primary Key
  - `name` (String, 45)

**Relationships:**
- **Many-to-Many:** Author ↔ Book (via book_author join table)

---

## 7. BORROW
**Table:** `borrow`
- **Primary Key:** `borrowId` (int)
- **Attributes:**
  - `borrowId` (int) - Primary Key
  - `member_id` (int) - Foreign Key to Member
  - `book_id` (int) - Foreign Key to Book
  - `borrowDate` (LocalDateTime)
  - `dueDate` (LocalDateTime)
  - `returnDate` (LocalDateTime)
  - `fine` (double)

**Relationships:**
- **Many-to-One:** Borrow → Member
- **Many-to-One:** Borrow → Book

---

## Join Tables

### BOOK_AUTHOR (Many-to-Many)
- `book_id` (int) - Foreign Key to Book
- `author_id` (int) - Foreign Key to Author

---

## Relationship Summary

### One-to-One Relationships
- **Member ↔ Address**

### One-to-Many Relationships
- **Location → Location** (self-referencing hierarchy)
- **Location → Member** (village → members)
- **Category → Book**
- **Member → Borrow**
- **Book → Borrow**

### Many-to-Many Relationships
- **Book ↔ Author** (via book_author join table)

---

## Location Hierarchy Structure
```
PROVINCE
    └── DISTRICT
        └── SECTOR
            └── CELL
                └── VILLAGE
```

Each level references its parent, creating a hierarchical location system.

---
