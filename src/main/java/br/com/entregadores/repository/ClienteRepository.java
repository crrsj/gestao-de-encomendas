package br.com.entregadores.repository;

import br.com.entregadores.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
  Optional<Cliente> findByRg(String rg);
    Page<Cliente> findByRegiao(String regiao, Pageable pageable);
}
