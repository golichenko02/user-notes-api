package com.bobocode.mapper;

import com.bobocode.controller.UserController;
import com.bobocode.dto.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteRepresentationMapper implements RepresentationModelAssembler<Note, EntityModel<Note>> {

    @Override
    public EntityModel<Note> toModel(Note note) {
        return EntityModel.of(
                note,
                linkTo(methodOn(UserController.class).getUserNoteById(note.getUserId(), note.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserNotes(note.getUserId())).withRel("userNotes"),
                linkTo(methodOn(UserController.class).getUserById(note.getUserId())).withRel("user")
        );
    }

}
