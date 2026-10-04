package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client;

import java.time.LocalDate;

public record RegistroLeituraResponse(
        Long id,
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