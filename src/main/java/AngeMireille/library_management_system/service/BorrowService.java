package AngeMireille.library_management_system.service;

import java.util.List;

import AngeMireille.library_management_system.dto.BorrowRequestDTO;
import AngeMireille.library_management_system.model.Borrow;

public interface BorrowService {
    Borrow borrowBook(BorrowRequestDTO request);
    Borrow returnBook(int borrowId);
    double calculateFine(Borrow borrow);
    List<Borrow> getAllBorrows();
}