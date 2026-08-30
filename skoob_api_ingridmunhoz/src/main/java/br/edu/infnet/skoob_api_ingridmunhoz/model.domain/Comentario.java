package br.edu.infnet.skoob_api_ingridmunhoz.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Comentario {

    // Atributos
    private int id;
    private String titulo;
    private String comentario;
    private int avaliacao;        // 1 a 5 estrelas
    private LocalDateTime dataCriacao;
    private boolean editado;

    // Relacionamentos (N:1)
    @JsonIgnore
    private Usuario usuario;
    @JsonIgnore
    private Livro livro;

    // Construtores
    public Comentario() {
    }

    public Comentario(int id, String titulo, String comentario, int avaliacao,
                      LocalDateTime dataCriacao, boolean editado) {
        this.id = id;
        this.titulo = titulo;
        this.comentario = comentario;
        this.avaliacao = avaliacao;
        this.dataCriacao = dataCriacao;
        this.editado = editado;
    }

    // toString()
    @Override
    public String toString() {
        return String.format(
                "Comentario{id=%d, usuario='%s', livro='%s', titulo='%s', " +
                        "avaliacao=%d estrelas, editado=%s, data=%s}",
                id,
                usuario != null ? usuario.getUsername() : "Anônimo",
                livro != null ? livro.getTitulo() : "Nenhum",
                titulo != null ? titulo : "Sem título",
                avaliacao,
                editado ? "Sim" : "Não",
                dataCriacao != null ? dataCriacao.toLocalDate() : "N/A"
        );
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}