package AngeMireille.library_management_system.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowUpdateDTO {
    private int id;
    private int book_id;

    private int member_id;

    private LocalDate borrowDate;
    private LocalDate returnDate;
}
