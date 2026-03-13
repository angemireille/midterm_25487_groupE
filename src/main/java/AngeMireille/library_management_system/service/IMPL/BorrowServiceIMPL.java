package AngeMireille.library_management_system.service.IMPL;

import AngeMireille.library_management_system.dto.BorrowRequestDTO;
import AngeMireille.library_management_system.model.Borrow;
import AngeMireille.library_management_system.model.Book;
import AngeMireille.library_management_system.model.Member;
import AngeMireille.library_management_system.repository.BorrowRepo;
import AngeMireille.library_management_system.repository.BookRepo;
import AngeMireille.library_management_system.repository.MemberRepo;
import AngeMireille.library_management_system.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;

@Service
public class BorrowServiceIMPL implements BorrowService {

    @Autowired
    private BorrowRepo borrowRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private MemberRepo memberRepo;

    private static final int BORROW_DAYS = 14;
    private static final double FINE_PER_MINUTE = 1.0;

    @Override
public Borrow borrowBook(BorrowRequestDTO request) {
    Member member = memberRepo.findById(request.getMemberId())
            .orElseThrow(() -> new RuntimeException("Member not found"));
    Book book = bookRepo.findById(request.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));

    if (book.getAvailableCopies() <= 0) {
        throw new RuntimeException("No available copies");
    }

    book.setAvailableCopies(book.getAvailableCopies() - 1);
    bookRepo.save(book);

    Borrow borrow = new Borrow();
    borrow.setMember(member);
    borrow.setBook(book);
    borrow.setBorrowDate(LocalDateTime.now());
    borrow.setDueDate(LocalDateTime.now().plusDays(BORROW_DAYS));
    borrow.setReturnDate(null);
    borrowRepo.save(borrow);
    return borrow;
}
@Override
public List<Borrow> getAllBorrows() {
    return borrowRepo.findAll();
}

@Override
public Borrow returnBook(int borrowId) {
    Borrow borrow = borrowRepo.findById(borrowId)
            .orElseThrow(() -> new RuntimeException("Borrow record not found"));
    if (borrow.getReturnDate() != null) {
        throw new RuntimeException("Book already returned");
    }
    borrow.setReturnDate(LocalDateTime.now());
    double fees = calculateFine(borrow);
    borrow.setFine(fees);

    // Increase available copies
    Book book = borrow.getBook();
    book.setAvailableCopies(book.getAvailableCopies() + 1);
    bookRepo.save(book);

    borrowRepo.save(borrow);
    return borrow;
}

@Override
public double calculateFine(Borrow borrow) {
    if (borrow.getReturnDate() == null) return 0;
    long overdueMinutes = Duration.between(
            borrow.getDueDate(),
            borrow.getReturnDate()
    ).toMinutes();
    return overdueMinutes > 0 ? overdueMinutes * FINE_PER_MINUTE : 0;
}
}