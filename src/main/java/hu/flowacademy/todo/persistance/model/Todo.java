package hu.flowacademy.todo.persistance.model;

import hu.flowacademy.todo.dto.TodoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private byte[] media;

    private String mediaContentType;

    private Boolean done;

    @ManyToOne
    @JoinColumn
    private User owner;

    @OneToMany
    private List<User> sharedWith;

    // @TODO checklist

}
