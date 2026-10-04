package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client;

import java.time.LocalDate;

public record RegistroLeituraRequest(
        String status,
        int paginasLidas,
        double percentualLeitura,
        int avaliacaoUsuario,
        LocalDate dataInicio,
        LocalDate dataConclusao,
        Long usuarioId,
        Long livroId
) {
}