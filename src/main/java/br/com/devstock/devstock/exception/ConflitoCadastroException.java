package br.com.devstock.devstock.exception;

public class ConflitoCadastroException extends RuntimeException{

    public ConflitoCadastroException(String mensagem){
        super(mensagem);
    }
}
