package AngeMireille.library_management_system.controller;

import AngeMireille.library_management_system.dto.BorrowRequestDTO;
import AngeMireille.library_management_system.model.Borrow;
import AngeMireille.library_management_system.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow")
    public Borrow borrowBook(@RequestBody BorrowRequestDTO request) {
        return borrowService.borrowBook(request);
    }

    @PostMapping("/return/{borrowId}")
    public Borrow returnBook(@PathVariable int borrowId) {
        return borrowService.returnBook(borrowId);
    }

    @GetMapping("/fine/{borrowId}")
    public double getFine(@PathVariable int borrowId) {
        Borrow borrow = borrowService.returnBook(borrowId);
        return borrowService.calculateFine(borrow);
    }
    @GetMapping("/all")
    public List<Borrow> getAllBorrows() {
        return borrowService.getAllBorrows();
}
}