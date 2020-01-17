package hu.flowacademy.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private String title;
    private String content;
    private String mediaContentType;
    private Long ownerId;
    private List<Long> sharedWith;
    private Boolean done = Boolean.FALSE;
}
