package com.bobocode.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private List<Note> notes;
}
