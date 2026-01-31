package br.com.entregadores.excessoes;

import br.com.entregadores.dto.MensagemDTO;
import br.com.entregadores.dto.ValidandoCampos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcessoesGlobais {

    @ExceptionHandler(ClienteNaoEncontrado.class)
    public ResponseEntity<MensagemDTO>clienteNaoEncontrado(){
       var msg =  new MensagemDTO(HttpStatus.NOT_FOUND,"Cliente não encontrado.");
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(EncomendaNaoEncontrada.class)
    public ResponseEntity<MensagemDTO>encomendaNaoEncontrada(){
        var msg = new MensagemDTO(HttpStatus.NOT_FOUND,"Encomenda não encontrada.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>validarCampos(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.stream().map(ValidandoCampos::new).toList());
    }
}
