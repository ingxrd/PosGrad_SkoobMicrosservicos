package br.edu.infnet.skoob_api_ingridmunhoz.model.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro implements Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título deve ser informado")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "O autor deve ser informado")
    @Size(max = 100, message = "O autor deve ter no máximo 100 caracteres")
    private String autor;

    @Column(unique = true)
    private String isbn;

    @Size(max = 100)
    private String editora;

    @Min(value = 1, message = "O número de páginas deve ser maior que 0")
    private int paginas;

    private String genero;

    @DecimalMin(value = "0.0", message = "A avaliação deve ser entre 0 e 5")
    @DecimalMax(value = "5.0", message = "A avaliação deve ser entre 0 e 5")
    private double avaliacaoMedia;

    private boolean disponivel;

    @Column(length = 1000)
    private String sinopse;

    // Relacionamentos
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RegistroLeitura> registrosLeitura = new ArrayList<>();

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comentario> comentarios = new ArrayList<>();

    // Construtores
    public Livro() {
    }

    public Livro(String titulo, String autor, String isbn, String editora, int paginas,
                 String genero, double avaliacaoMedia, boolean disponivel, String sinopse) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editora = editora;
        this.paginas = paginas;
        this.genero = genero;
        this.avaliacaoMedia = avaliacaoMedia;
        this.disponivel = disponivel;
        this.sinopse = sinopse;
    }

    // Métodos de Associação
    public void adicionarRegistroLeitura(RegistroLeitura registro) {
        registrosLeitura.add(registro);
        registro.setLivro(this);
    }

    public void adicionarComentario(Comentario comentario) {
        comentarios.add(comentario);
        comentario.setLivro(this);
    }

    // toString()
    @Override
    public String toString() {
        return String.format("Livro{id=%d, titulo='%s', autor='%s', avaliacao=%.1f}",
                id, titulo, autor, avaliacaoMedia);
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

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public double getAvaliacaoMedia() { return avaliacaoMedia; }
    public void setAvaliacaoMedia(double avaliacaoMedia) { this.avaliacaoMedia = avaliacaoMedia; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public List<RegistroLeitura> getRegistrosLeitura() { return registrosLeitura; }
    public void setRegistrosLeitura(List<RegistroLeitura> registrosLeitura) {
        this.registrosLeitura = registrosLeitura;
    }

    public List<Comentario> getComentarios() { return Collections.unmodifiableList(comentarios); } //melhora o encapsulamento

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}