package br.com.entregadores.config;

import br.com.entregadores.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepConfig {

    @GetMapping("/{cep}/json/")
    ClienteDTO buscarEnderecoPor(@PathVariable("cep") String cep);
}
