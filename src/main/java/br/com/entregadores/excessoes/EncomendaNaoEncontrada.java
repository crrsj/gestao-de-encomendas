package br.com.entregadores.excessoes;

public class EncomendaNaoEncontrada extends RuntimeException {
    public EncomendaNaoEncontrada(String mensagem) {
        super(mensagem);
    }

    public EncomendaNaoEncontrada(){
        super();
    }
}
