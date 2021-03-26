package com.arpiatecnologia.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class Cliente {

    private Long id;

    @NotEmpty
    private String nome;
}
