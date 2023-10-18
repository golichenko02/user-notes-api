package com.bobocode.dto;

import lombok.Data;

@Data
public class Note {
    private String id;
    private String title;
    private String body;
    private String userId;
}
