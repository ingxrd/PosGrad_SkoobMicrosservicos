package br.edu.infnet.skoob_api_ingridmunhoz.usuario;

import br.edu.infnet.skoob_api_ingridmunhoz.comentario.Comentario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements br.edu.infnet.skoob_api_ingridmunhoz.shared.Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome deve ser informado")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O username deve ser informado")
    @Size(max = 50, message = "O username deve ter no máximo 50 caracteres")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "O email deve ser informado")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha deve ser informada")
    @Size(min = 3, max = 100, message = "A senha deve ter entre 3 e 100 caracteres")
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-comentario")
    private List<Comentario> comentarios = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String username, String email, String senha) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.comentarios = new ArrayList<>();
    }

    public void adicionarComentario(Comentario comentario) {
        if (comentario == null) {
            throw new IllegalArgumentException("Comentário não pode ser nulo!");
        }
        this.comentarios.add(comentario);
        comentario.setUsuario(this);
    }

    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nome='%s', username='%s', email='%s', totalComentarios=%d}",
                id, nome, username, email,
                comentarios != null ? comentarios.size() : 0);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<Comentario> getComentarios() { return Collections.unmodifiableList(comentarios); }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    @Override
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}