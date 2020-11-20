package com.projeto.academia.handler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_NOT_FOUND("/entity-not-found", "entity-not-found"),
    ENTITY_ALREADY_EXISTS("/already-existis", "already-exists");

    private String url;
    private String title;

    ProblemType(String path, String title) {
        this.title = title;
        this.url = "https://academia.com.br" + path;
    }
}
