package br.com.entregadores.dto;

import br.com.entregadores.entity.Encomenda;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ClienteDTO {


    private Long id;
    @NotBlank(message = " não pode estar em branco")
    private String nome;
    @NotBlank(message = " não pode estar em branco")
    private String telefone;
    private String rg;
    @NotBlank(message = " não pode estar em branco")
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String complemento;
    private String uf;
    private String estado;
    private String regiao;
    private List<Encomenda> encomendas ;
}
