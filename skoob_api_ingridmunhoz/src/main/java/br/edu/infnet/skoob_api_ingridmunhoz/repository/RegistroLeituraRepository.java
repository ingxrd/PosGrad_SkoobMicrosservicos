package br.edu.infnet.skoob_api_ingridmunhoz.repository;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.RegistroLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RegistroLeituraRepository extends JpaRepository<RegistroLeitura, Long> {

    List<RegistroLeitura> findByUsuarioId(Long usuarioId);

    List<RegistroLeitura> findByLivroId(Long livroId);

    List<RegistroLeitura> findByStatus(String status);

    List<RegistroLeitura> findByPercentualLeituraLessThan(double percentual);

    List<RegistroLeitura> findByStatusAndPercentualLeituraEquals(String status, double percentual);
}