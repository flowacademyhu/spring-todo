package hu.flowacademy.todo.service;

import hu.flowacademy.todo.dto.TodoDTO;
import hu.flowacademy.todo.persistance.model.Todo;
import hu.flowacademy.todo.persistance.repository.TodoRepository;
import hu.flowacademy.todo.persistance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public Todo create(TodoDTO todoDTO, MultipartFile file) throws IOException {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDTO, todo);
        if (file != null) {
            todo.setMedia(file.getBytes());
            todo.setMediaContentType(file.getContentType());
        }
        todo.setOwner(userRepository.findById(todoDTO.getOwnerId()).orElseThrow());
        if (todoDTO.getSharedWith() != null && !todoDTO.getSharedWith().isEmpty()) {
            todo.setSharedWith(userRepository.findAllById(todoDTO.getSharedWith()));
        }
        return todoRepository.save(todo);
    }

    public void createShare(Long id, List<Long> userIds) {
        if(userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("User ids cannot be empty");
        }
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.getSharedWith().addAll(userRepository.findAllById(userIds));
        todoRepository.save(todo);
    }
}
