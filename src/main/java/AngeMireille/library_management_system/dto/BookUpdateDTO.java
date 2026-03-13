package AngeMireille.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDTO {
    private int bookid;
    private String title;
    private int author_id;
    private int category_id;
}
