package com.arpiatecnologia.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cliente {

    private Long id;
    private String nome;
}
