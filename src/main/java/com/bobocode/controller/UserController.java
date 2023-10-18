package com.bobocode.controller;

import com.bobocode.dto.Note;
import com.bobocode.dto.User;
import com.bobocode.mapper.NoteRepresentationMapper;
import com.bobocode.mapper.UserRepresentationMapper;
import com.bobocode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepresentationMapper userRepresentationMapper;
    private final NoteRepresentationMapper noteRepresentationMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userRepresentationMapper.toModel(userService.getUserById(id)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> findAll() {
        return ResponseEntity.ok(userRepresentationMapper.toCollectionModel(userService.findAll()));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
    }

    @PostMapping("/{userId}/notes")
    public ResponseEntity<?> createUserNote(@PathVariable String userId, @RequestBody Note note) {
        userService.createUserNote(userId, note);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(note.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{userId}/notes/{noteId}")
    public ResponseEntity<EntityModel<Note>> getUserNoteById(@PathVariable String userId, @PathVariable String noteId) {
        return ResponseEntity.ok(noteRepresentationMapper.toModel(userService.getUserNoteById(userId, noteId)));
    }

    @GetMapping("/{userId}/notes")
    public ResponseEntity<CollectionModel<EntityModel<Note>>> getUserNotes(@PathVariable String userId) {
        return ResponseEntity.ok(noteRepresentationMapper.toCollectionModel(userService.getUserNotes(userId)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/notes/{noteId}")
    public void deleteUserNoteById(@PathVariable String userId, @PathVariable String noteId) {
        userService.deleteUserNoteById(userId, noteId);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleError(HttpClientErrorException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
    }
}
