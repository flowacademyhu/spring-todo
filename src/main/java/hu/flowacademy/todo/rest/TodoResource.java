package hu.flowacademy.todo.rest;

import hu.flowacademy.todo.dto.TodoDTO;
import hu.flowacademy.todo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class TodoResource {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<Object> create(@RequestParam(name = "file", required = false) MultipartFile file,
                                         @RequestParam("todo") String todoString) throws IOException {
        TodoDTO todoDTO = MAPPER.readValue(todoString, TodoDTO.class);
        todoService.create(todoDTO, file);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/todos/{id}/share")
    public void createShare(@PathVariable Long id, @RequestBody List<Long> userIds) {
        todoService.createShare(id, userIds);
    }
}
