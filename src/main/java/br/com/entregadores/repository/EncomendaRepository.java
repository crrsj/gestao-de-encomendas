package br.com.entregadores.repository;

import br.com.entregadores.entity.Encomenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncomendaRepository extends JpaRepository<Encomenda,Long> {
}
