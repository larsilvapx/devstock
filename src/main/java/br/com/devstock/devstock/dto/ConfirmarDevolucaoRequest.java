package br.com.devstock.devstock.dto;

import java.util.List;

public record ConfirmarDevolucaoRequest(

        String responsavel,

        String observacao,

        List<DevolucaoItemRequest> itens

) {
}