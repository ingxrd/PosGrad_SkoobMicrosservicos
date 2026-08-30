package br.edu.infnet.skoob_api_ingridmunhoz.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate; // ✅ Usando LocalDate (mais moderno que Date)

public class RegistroLeitura {

    private int id;
    private String status; // "LENDO", "TERMINADO", "ABANDONADO", "NAO_INICIADO"
    private int paginasLidas;
    private double percentualLeitura;
    private int avaliacaoUsuario; // 0 a 5 estrelas
    private LocalDate dataInicio;
    private LocalDate dataConclusao;

    // Relacionamentos (N:1)
    @JsonIgnore
    private Livro livro;
    @JsonIgnore
    private Usuario usuario;

    // Construtores
    public RegistroLeitura() {
    }

    public RegistroLeitura(int id, String status, int paginasLidas, double percentualLeitura,
                           int avaliacaoUsuario, LocalDate dataInicio, LocalDate dataConclusao) {
        this.id = id;
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
        return String.format(
                "RegistroLeitura{id=%d, usuario='%s', livro='%s', status='%s', " +
                        "paginasLidas=%d, percentual=%.1f%%, avaliacao=%d estrelas, " +
                        "inicio=%s, conclusao=%s}",
                id,
                usuario != null ? usuario.getUsername() : "Nenhum",
                livro != null ? livro.getTitulo() : "Nenhum",
                status,
                paginasLidas,
                percentualLeitura,
                avaliacaoUsuario,
                dataInicio != null ? dataInicio : "N/A",
                dataConclusao != null ? dataConclusao : "Em andamento"
        );
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPaginasLidas() {
        return paginasLidas;
    }

    public void setPaginasLidas(int paginasLidas) {
        this.paginasLidas = paginasLidas;
    }

    public double getPercentualLeitura() {
        return percentualLeitura;
    }

    public void setPercentualLeitura(double percentualLeitura) {
        this.percentualLeitura = percentualLeitura;
    }

    public int getAvaliacaoUsuario() {
        return avaliacaoUsuario;
    }

    public void setAvaliacaoUsuario(int avaliacaoUsuario) {
        this.avaliacaoUsuario = avaliacaoUsuario;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}