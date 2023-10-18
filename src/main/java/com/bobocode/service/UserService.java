package com.bobocode.service;

import com.bobocode.dto.Note;
import com.bobocode.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> userById = new ConcurrentHashMap<>();

    public Collection<User> findAll() {
        return userById.values();
    }

    public User getUserById(String id) {
        checkId(id);
        return Optional.ofNullable(userById.get(id))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User by id:%s not found".formatted(id)));
    }

    public void createUser(User user) {
        if (user.getId() != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User id must be empty");
        }
        user.setId(generateId());
        userById.put(user.getId(), user);
    }

    public void deleteUserById(String id) {
        checkId(id);
        userById.remove(id);
    }

    public void createUserNote(String id, Note note) {
        if (note.getId() != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Note id must be empty");
        }

        if (note.getUserId() != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User id must be empty");
        }

        User user = getUserById(id);
        note.setId(generateId());
        note.setUserId(id);

        if (user.getNotes() == null) {
            user.setNotes(new ArrayList<>());
        }

        user.getNotes().add(note);
    }

    public Note getUserNoteById(String userId, String noteId) {
        checkId(noteId);
        User user = getUserById(userId);
        return user.getNotes().stream()
                .filter(note -> note.getId().equals(noteId))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Note by id:%s not found".formatted(noteId)));
    }

    public Collection<Note> getUserNotes(String userId) {
        return getUserById(userId).getNotes();
    }

    public void deleteUserNoteById(String userId, String noteId) {
        User user = getUserById(userId);
        Note note = getUserNoteById(userId, noteId);
        user.getNotes().remove(note);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private void checkId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid id specified");
        }
    }
}
