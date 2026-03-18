package br.com.entregadores.dto;

import br.com.entregadores.entity.Cliente;
import br.com.entregadores.enums.Empresa;
import br.com.entregadores.enums.StatusEntrega;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EncomendaDTO {

    private Long id;
    @Future(message = "não pode ser anterior a data atual")
    private LocalDate dataEntrega;
    @NotNull( message="não pode ser nulo")
    private int pacotes;
    private Empresa empresa;
    private String descricao;
    private StatusEntrega status;
    private Cliente cliente;
}
