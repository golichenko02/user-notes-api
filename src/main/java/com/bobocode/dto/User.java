package com.bobocode.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        note.setUserId(this.id);
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.getNotes().remove(note);
    }
}
