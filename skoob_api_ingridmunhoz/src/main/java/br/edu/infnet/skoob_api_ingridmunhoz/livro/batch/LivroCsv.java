package br.edu.infnet.skoob_api_ingridmunhoz.livro.batch;

public class LivroCsv {

    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private Integer paginas;
    private String genero;
    private Double avaliacaoMedia;
    private Boolean disponivel;
    private String sinopse;

    public LivroCsv() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public Integer getPaginas() { return paginas; }
    public void setPaginas(Integer paginas) { this.paginas = paginas; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Double getAvaliacaoMedia() { return avaliacaoMedia; }
    public void setAvaliacaoMedia(Double avaliacaoMedia) { this.avaliacaoMedia = avaliacaoMedia; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    @Override
    public String toString() {
        return "LivroCsv{titulo='" + titulo + "', autor='" + autor + "', isbn='" + isbn + "'}";
    }
}