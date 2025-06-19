package com.globetrotter.bff.model;

import lombok.Data;

@Data
public class TextSearchRequest {
    private String text;

    public String getText() {
        return text;
    }
} 