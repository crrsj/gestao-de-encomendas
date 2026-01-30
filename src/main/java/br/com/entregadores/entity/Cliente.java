package br.com.entregadores.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String rg;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String complemento;
    private String uf;
    private String estado;
    private String regiao;
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL)
    private List<Encomenda> encomendas ;

}
