package br.edu.infnet.skoob_api_ingridmunhoz.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Livro {

    // Atributos
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private int paginas;
    private String genero;
    private double avaliacaoMedia;
    private boolean disponivel;
    private String sinopse;

    // Relacionamentos (1:N)
    private List<RegistroLeitura> registrosLeitura;
    private List<Comentario> comentarios;

    // Construtores
    public Livro() {
    }

    public Livro(int id, String titulo, String autor, String isbn, String editora,
                 int paginas, String genero, double avaliacaoMedia, boolean disponivel, String sinopse) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editora = editora;
        this.paginas = paginas;
        this.genero = genero;
        this.avaliacaoMedia = avaliacaoMedia;
        this.disponivel = disponivel;
        this.sinopse = sinopse;
        this.registrosLeitura = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    // Métodos de Associação
    public void adicionarRegistroLeitura(RegistroLeitura registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro de leitura não pode ser nulo!");
        }
        this.registrosLeitura.add(registro);
        registro.setLivro(this);
    }

    public void adicionarComentario(Comentario comentario) {
        if (comentario == null) {
            throw new IllegalArgumentException("Comentário não pode ser nulo!");
        }
        this.comentarios.add(comentario);
        comentario.setLivro(this);
    }

    // toString()
    @Override
    public String toString() {
        return String.format(
                "Livro{id=%d, titulo='%s', autor='%s', isbn='%s', editora='%s', " +
                        "paginas=%d, genero='%s', avaliacaoMedia=%.1f, disponivel=%s, " +
                        "totalRegistros=%d, totalComentarios=%d}",
                id, titulo, autor, isbn, editora, paginas, genero,
                avaliacaoMedia, disponivel ? "Sim" : "Não",
                registrosLeitura != null ? registrosLeitura.size() : 0,
                comentarios != null ? comentarios.size() : 0
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public List<RegistroLeitura> getRegistrosLeitura() {
        return registrosLeitura;
    }

    public void setRegistrosLeitura(List<RegistroLeitura> registrosLeitura) {
        this.registrosLeitura = registrosLeitura;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}