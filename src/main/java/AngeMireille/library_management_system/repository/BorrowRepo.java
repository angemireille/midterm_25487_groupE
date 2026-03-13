package AngeMireille.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AngeMireille.library_management_system.model.Borrow;

public interface BorrowRepo extends JpaRepository<Borrow, Integer> {

}