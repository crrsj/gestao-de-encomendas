package br.com.entregadores.dto;

import org.springframework.http.HttpStatus;

public record MensagemDTO(HttpStatus status, String mensagem) {
}
