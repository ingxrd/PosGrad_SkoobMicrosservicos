package br.edu.infnet.skoob_api_ingridmunhoz.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    // Atributos
    private int id;
    private String nome;
    private String username;
    private String email;
    private String senha;

    // Relacionamentos (1:N)
    @JsonIgnore
    private List<RegistroLeitura> registrosLeitura = new ArrayList<>();
    @JsonIgnore
    private List<Comentario> comentarios = new ArrayList<>();

    // Construtores
    public Usuario() {
    }

    public Usuario(int id, String nome, String username, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.registrosLeitura = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    // Métodos cujo objetivo é trabalhar encapsulamento, mantendo a ref. bidirecional.
    public void adicionarRegistroLeitura(RegistroLeitura registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro de leitura não pode ser nulo!");
        }
        this.registrosLeitura.add(registro);
        registro.setUsuario(this);
    }

    public void adicionarComentario(Comentario comentario) {
        if (comentario == null) {
            throw new IllegalArgumentException("Comentário não pode ser nulo!");
        }
        this.comentarios.add(comentario);
        comentario.setUsuario(this);
    }

    // Método toString()
    @Override
    public String toString() {
        return String.format(
                "Usuario{id=%d, nome='%s', username='%s', email='%s', " +
                        "totalLeituras=%d, totalComentarios=%d}",
                id, nome, username, email,
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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