package com.projeto.academia.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetails {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private LocalDateTime timesTamp;

}
