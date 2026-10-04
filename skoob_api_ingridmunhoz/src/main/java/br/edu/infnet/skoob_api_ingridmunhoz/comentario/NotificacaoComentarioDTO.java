package br.edu.infnet.skoob_api_ingridmunhoz.comentario.dto;

import java.io.Serializable;

public class NotificacaoComentarioDTO implements Serializable {

    private String tipo = "COMENTARIO_CRIADO";
    private Long comentarioId;
    private Long livroId;
    private String tituloLivro;
    private Long usuarioAutorComentarioId;
    private String nomeAutorComentario;

    public NotificacaoComentarioDTO() {}

    public NotificacaoComentarioDTO(Long comentarioId, Long livroId, String tituloLivro,
                                    Long usuarioAutorComentarioId, String nomeAutorComentario) {
        this.comentarioId = comentarioId;
        this.livroId = livroId;
        this.tituloLivro = tituloLivro;
        this.usuarioAutorComentarioId = usuarioAutorComentarioId;
        this.nomeAutorComentario = nomeAutorComentario;
    }

    // getters e setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getComentarioId() { return comentarioId; }
    public void setComentarioId(Long comentarioId) { this.comentarioId = comentarioId; }
    public Long getLivroId() { return livroId; }
    public void setLivroId(Long livroId) { this.livroId = livroId; }
    public String getTituloLivro() { return tituloLivro; }
    public void setTituloLivro(String tituloLivro) { this.tituloLivro = tituloLivro; }
    public Long getUsuarioAutorComentarioId() { return usuarioAutorComentarioId; }
    public void setUsuarioAutorComentarioId(Long usuarioAutorComentarioId) { this.usuarioAutorComentarioId = usuarioAutorComentarioId; }
    public String getNomeAutorComentario() { return nomeAutorComentario; }
    public void setNomeAutorComentario(String nomeAutorComentario) { this.nomeAutorComentario = nomeAutorComentario; }
}