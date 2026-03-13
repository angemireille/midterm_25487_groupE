# GitHub Push Commands for Library Management System

## Step 1: Open Command Prompt/Terminal in your project directory
cd "c:\Users\ANGE\Desktop\library-management-system\library-management-system\library-management-system"

## Step 2: Initialize Git (if not already done)
git init

## Step 3: Add all files
git add .

## Step 4: Create initial commit
git commit -m "Initial commit: Complete Library Management System

Features implemented:
- ✅ ERD with 7 entities and proper relationships
- ✅ Location hierarchy saving with validation
- ✅ Sorting and pagination for all entities
- ✅ Many-to-Many: Book-Author relationship
- ✅ One-to-Many: Category-Book, Location hierarchy
- ✅ One-to-One: Member-Address relationship
- ✅ existBy() methods for validation
- ✅ Province-based member queries (by ID, code, name)
- ✅ 2FA authentication and password reset
- ✅ Complete CRUD operations for all entities
- ✅ RESTful API design with proper DTOs"

## Step 5: Add GitHub remote (replace YOUR_USERNAME with your actual GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/library-management-system.git

## Step 6: Push to GitHub
git branch -M main
git push -u origin main

## If you get authentication errors, you might need to:
## 1. Use GitHub Desktop app, OR
## 2. Generate a Personal Access Token from GitHub Settings > Developer Settings > Personal Access Tokens
## 3. Use the token as your password when prompted

## After successful push, your repository will be available at:
## https://github.com/YOUR_USERNAME/library-management-system