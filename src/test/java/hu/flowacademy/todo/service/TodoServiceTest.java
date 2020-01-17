package hu.flowacademy.todo.service;

import hu.flowacademy.todo.dto.TodoDTO;
import hu.flowacademy.todo.persistance.model.Todo;
import hu.flowacademy.todo.persistance.model.User;
import hu.flowacademy.todo.persistance.repository.TodoRepository;
import hu.flowacademy.todo.persistance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void testCreate_whenNoShare() throws IOException {
        // given
        var userId = 1L;
        var todoDTO = new TodoDTO();
        todoDTO.setTitle("mytitle");
        todoDTO.setContent("mycontent");
        todoDTO.setOwnerId(userId);
        var todo = new Todo();
        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(new User()));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        // there is no share, so we won't teach findAllById because it's not called

        // when
        var savedTodo = todoService.create(todoDTO, null);

        // then
        verify(userRepository).findById(eq(userId));
        verify(userRepository, never()).findAllById(any());
        verify(todoRepository).save(any(Todo.class));
        assertEquals(todo, savedTodo);
    }

    @Test
    void testCreateShare() {
        // given
        var todoId = 1L;
        var userIds = List.of(1L, 2L, 3L);
        var users = List.of(new User(), new User(), new User());
        var todo = new Todo(todoId, "title", "content", null, null, false, null, new ArrayList<>());

        when(todoRepository.findById(eq(todoId))).thenReturn(Optional.of(todo));
        when(userRepository.findAllById(eq(userIds))).thenReturn(users);

        // when

        todoService.createShare(todoId, userIds);

        // then
        verify(todoRepository).findById(eq(todoId));
        verify(userRepository).findAllById(eq(userIds));
        verify(todoRepository).save(any(Todo.class));
    }
}
