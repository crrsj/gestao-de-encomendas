package br.com.entregadores.controller;

import br.com.entregadores.dto.EncomendaDTO;
import br.com.entregadores.service.EncomendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável por cadastrar encomenda.")
    @ApiResponse(responseCode = "201", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<EncomendaDTO>salvarEncomenda(@PathVariable Long clienteId, @RequestBody @Valid EncomendaDTO encomendaDTO){
        var encomenda = encomendaService.salvarEncomenda(clienteId, encomendaDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(encomenda.getId()).toUri();
        return ResponseEntity.created(uri).body(encomenda);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável pela busca de encomendas.")
    @ApiResponse(responseCode = "200", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<Page<EncomendaDTO>>listarEncomendas(Pageable pageable){
        Page<EncomendaDTO>encomendas = encomendaService.listarEncomendas(pageable);
        return ResponseEntity.ok(encomendas);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável pela busca de encomenda pelo id.")
    @ApiResponse(responseCode = "200", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<EncomendaDTO>buscarEncomendaPorId(@PathVariable Long id){
        return ResponseEntity.ok(encomendaService.buscarEncomendaPorId(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável pela atualização da encomenda.")
    @ApiResponse(responseCode = "200", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<EncomendaDTO>atualizarEncomenda(@PathVariable Long id,@RequestBody EncomendaDTO encomendaDTO){
        return ResponseEntity.ok(encomendaService.atualizarEncomenda(id,encomendaDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável por excluir encomenda.")
    @ApiResponse(responseCode = "204", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<Void>excluirEncomenda(@PathVariable Long id){
        encomendaService.excluirEncomenda(id);
        return ResponseEntity.noContent().build();
    }

}
