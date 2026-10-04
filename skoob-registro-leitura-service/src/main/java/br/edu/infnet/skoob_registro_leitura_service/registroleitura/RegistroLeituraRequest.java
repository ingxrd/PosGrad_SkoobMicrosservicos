package br.edu.infnet.skoob_registro_leitura_service.registroleitura;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegistroLeituraRequest(
        @NotNull(message = "O status é obrigatório")
        String status,

        @Min(value = 0, message = "Páginas lidas não pode ser negativo")
        int paginasLidas,

        @Min(value = 0, message = "Percentual deve ser entre 0 e 100")
        @Max(value = 100, message = "Percentual deve ser entre 0 e 100")
        double percentualLeitura,

        @Min(value = 0, message = "Avaliação deve ser entre 0 e 5")
        @Max(value = 5, message = "Avaliação deve ser entre 0 e 5")
        int avaliacaoUsuario,

        LocalDate dataInicio,
        LocalDate dataConclusao,

        @NotNull(message = "O usuário é obrigatório")
        Long usuarioId,

        @NotNull(message = "O livro é obrigatório")
        Long livroId
) {
}