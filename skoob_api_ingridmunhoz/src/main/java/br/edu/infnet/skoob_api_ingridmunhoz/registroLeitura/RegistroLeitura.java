package br.edu.infnet.skoob_api_ingridmunhoz.registroLeitura;

import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.shared.Identificavel;
import br.edu.infnet.skoob_api_ingridmunhoz.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

@Entity
@Table(name = "registros_leitura")
public class RegistroLeitura implements Identificavel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status; // "LENDO", "TERMINADO", "ABANDONADO", "NAO_INICIADO"

    @Min(value = 0, message = "Paginas lidas não pode ser negativo")
    private int paginasLidas;

    @Min(value = 0, message = "Percentual deve ser entre 0 e 100")
    @Max(value = 100, message = "Percentual deve ser entre 0 e 100")
    private double percentualLeitura;

    @Min(value = 0, message = "Avaliação deve ser entre 0 e 5")
    @Max(value = 5, message = "Avaliação deve ser entre 0 e 5")
    private int avaliacaoUsuario;

    private LocalDate dataInicio;
    private LocalDate dataConclusao;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    @JsonBackReference
    private Livro livro;

    // Construtores
    public RegistroLeitura() {
    }

    public RegistroLeitura(String status, int paginasLidas, double percentualLeitura,
                           int avaliacaoUsuario, LocalDate dataInicio, LocalDate dataConclusao) {
        this.status = status;
        this.paginasLidas = paginasLidas;
        this.percentualLeitura = percentualLeitura;
        this.avaliacaoUsuario = avaliacaoUsuario;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
    }

    // toString()
    @Override
    public String toString() {
        return String.format("RegistroLeitura{id=%d, status='%s', progresso=%.1f%%, avaliacao=%d}",
                id, status, percentualLeitura, avaliacaoUsuario);
    }

    // Getters e Setters

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getPaginasLidas() { return paginasLidas; }
    public void setPaginasLidas(int paginasLidas) { this.paginasLidas = paginasLidas; }

    public double getPercentualLeitura() { return percentualLeitura; }
    public void setPercentualLeitura(double percentualLeitura) {
        this.percentualLeitura = percentualLeitura;
    }

    public int getAvaliacaoUsuario() { return avaliacaoUsuario; }
    public void setAvaliacaoUsuario(int avaliacaoUsuario) {
        this.avaliacaoUsuario = avaliacaoUsuario;
    }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}