package br.edu.infnet.skoob_api_ingridmunhoz.comentario;

import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.shared.Identificavel;
import br.edu.infnet.skoob_api_ingridmunhoz.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario implements Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título deve ser informado")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String titulo;

    @NotBlank(message = "O comentário deve ser informado")
    @Size(max = 2000, message = "O comentário deve ter no máximo 2000 caracteres")
    @Column(length = 2000)
    private String comentario;

    @Min(value = 1, message = "Avaliação deve ser entre 1 e 5")
    @Max(value = 5, message = "Avaliação deve ser entre 1 e 5")
    private int avaliacao;

    private LocalDateTime dataCriacao;
    private boolean editado;

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
    public Comentario() {
    }

    public Comentario(String titulo, String comentario, int avaliacao,
                      LocalDateTime dataCriacao, boolean editado) {
        this.titulo = titulo;
        this.comentario = comentario;
        this.avaliacao = avaliacao;
        this.dataCriacao = dataCriacao;
        this.editado = editado;
    }

    // toString()
    @Override
    public String toString() {
        return String.format("Comentario{id=%d, titulo='%s', avaliacao=%d}",
                id, titulo, avaliacao);
    }

    // Getters e Setters
    @Override
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getAvaliacao() { return avaliacao; }
    public void setAvaliacao(int avaliacao) { this.avaliacao = avaliacao; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isEditado() { return editado; }
    public void setEditado(boolean editado) { this.editado = editado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
}