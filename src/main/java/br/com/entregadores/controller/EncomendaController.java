package br.com.entregadores.controller;

import br.com.entregadores.dto.EncomendaDTO;
import br.com.entregadores.service.EncomendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/encomendas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EncomendaController {

    private final EncomendaService encomendaService;

    @PostMapping("/{clienteId}")
    public ResponseEntity<EncomendaDTO>salvarEncomenda(@PathVariable Long clienteId, @RequestBody @Valid EncomendaDTO encomendaDTO){
        var encomenda = encomendaService.salvarEncomenda(clienteId, encomendaDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(encomenda.getId()).toUri();
        return ResponseEntity.created(uri).body(encomenda);
    }

    @GetMapping
    public ResponseEntity<Page<EncomendaDTO>>listarEncomendas(Pageable pageable){
        Page<EncomendaDTO>encomendas = encomendaService.listarEncomendas(pageable);
        return ResponseEntity.ok(encomendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncomendaDTO>buscarEncomendaPorId(@PathVariable Long id){
        return ResponseEntity.ok(encomendaService.buscarEncomendaPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EncomendaDTO>atualizarEncomenda(@PathVariable Long id,@RequestBody EncomendaDTO encomendaDTO){
        return ResponseEntity.ok(encomendaService.atualizarEncomenda(id,encomendaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirEncomenda(@PathVariable Long id){
        encomendaService.excluirEncomenda(id);
        return ResponseEntity.noContent().build();
    }

}
