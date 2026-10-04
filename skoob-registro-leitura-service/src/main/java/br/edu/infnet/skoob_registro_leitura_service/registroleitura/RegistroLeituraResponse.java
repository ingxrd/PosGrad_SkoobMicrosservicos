package br.edu.infnet.skoob_registro_leitura_service.registroleitura;

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