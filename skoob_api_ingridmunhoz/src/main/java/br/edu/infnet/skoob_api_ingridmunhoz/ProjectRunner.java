package br.edu.infnet.skoob_api_ingridmunhoz;

import br.edu.infnet.skoob_api_ingridmunhoz.exception.IdentificadorDuplicadoException;
import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;
import br.edu.infnet.skoob_api_ingridmunhoz.comentario.Comentario;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.usuario.Usuario;
import br.edu.infnet.skoob_api_ingridmunhoz.comentario.ComentarioService;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.LivroService;
import br.edu.infnet.skoob_api_ingridmunhoz.usuario.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProjectRunner implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final ComentarioService comentarioService;

    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;

    private Livro livro1;
    private Livro livro2;
    private Livro livro3;

    public ProjectRunner(UsuarioService usuarioService,
                         LivroService livroService,
                         ComentarioService comentarioService) {
        this.usuarioService = usuarioService;
        this.livroService = livroService;
        this.comentarioService = comentarioService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("========================================");
        System.out.println("INICIANDO SISTEMA SKOOB - ETAPA 4");
        System.out.println("========================================\n");

        try {
            criarUsuarios();
            criarLivros();
            criarComentarios();
        } catch (IdentificadorDuplicadoException e) {
            System.out.println("!!! Carga inicial ignorada: dados de exemplo já existem no banco.");
            System.out.println("    Detalhe: " + e.getMessage());
            System.out.println();
            carregarDadosExistentes();   // ← AJUSTE: repopula as variáveis a partir do banco
        }

        executarConsultasComStreams();
        testarExcecoes();
        exibirResultadoFinal();
    }

    // ==================== CARGA DE DADOS EXISTENTES (fallback) ====================

    private void carregarDadosExistentes() {
        System.out.println("--- CARREGANDO DADOS EXISTENTES DO BANCO ---\n");

        List<Usuario> usuarios = usuarioService.obterLista();
        List<Livro> livros = livroService.obterLista();

        if (usuarios.size() >= 1) usuario1 = usuarios.get(0);
        if (usuarios.size() >= 2) usuario2 = usuarios.get(1);
        if (usuarios.size() >= 3) usuario3 = usuarios.get(2);

        if (livros.size() >= 1) livro1 = livros.get(0);
        if (livros.size() >= 2) livro2 = livros.get(1);
        if (livros.size() >= 3) livro3 = livros.get(2);

        System.out.println("Usuários carregados: " + usuarios.size());
        System.out.println("Livros carregados: " + livros.size());
        System.out.println();
    }

    // ==================== CRIAÇÃO DOS DADOS ====================

    private void criarUsuarios() {
        System.out.println("--- CRIANDO OBJETOS ---\n");

        usuario1 = new Usuario("Ingrid Munhoz", "ingrid", "ingrid@email.com", "123");
        usuario2 = new Usuario("William Poiato", "william", "william@email.com", "456");
        usuario3 = new Usuario("Tito", "tito", "tito@email.com", "789");

        usuarioService.incluir(usuario1);
        usuarioService.incluir(usuario2);
        usuarioService.incluir(usuario3);

        System.out.println("--- USUARIOS CRIADOS E INCLUIDOS ---");
        System.out.println(usuario1);
        System.out.println(usuario2);
        System.out.println(usuario3);
        System.out.println();
    }

    private void criarLivros() {
        livro1 = new Livro(
                "O Senhor dos Anéis", "J.R.R. Tolkien", "978-0-547-92934-9",
                "HarperCollins", 1216, "Fantasia", 4.8, true,
                "Uma jornada épica pela Terra Média..."
        );

        livro2 = new Livro(
                "Dom Casmurro", "Machado de Assis", "978-85-250-4261-3",
                "Editora Globo", 256, "Romance", 4.5, true,
                "A história de Bentinho e Capitu..."
        );

        livro3 = new Livro(
                "A Arte da Guerra", "Sun Tzu", "978-85-336-0361-3",
                "Martins Fontes", 144, "Estratégia", 4.2, false,
                "Ensinamentos milenares sobre estratégia..."
        );

        livroService.incluir(livro1);
        livroService.incluir(livro2);
        livroService.incluir(livro3);

        System.out.println("--- LIVROS CRIADOS E INCLUIDOS ---");
        System.out.println(livro1);
        System.out.println(livro2);
        System.out.println(livro3);
        System.out.println();
    }

    private void criarComentarios() {
        Comentario comentario1 = new Comentario(
                "Fantástico!", "Tolkien é um gênio! A história é envolvente.",
                5, LocalDateTime.now(), false
        );

        Comentario comentario2 = new Comentario(
                "Bom livro", "A história é interessante. Traiu ou não traiu??",
                3, LocalDateTime.now(), false
        );

        comentario1.setUsuario(usuario1);
        comentario1.setLivro(livro1);
        usuario1.adicionarComentario(comentario1);
        livro1.adicionarComentario(comentario1);

        comentario2.setUsuario(usuario2);
        comentario2.setLivro(livro2);
        usuario2.adicionarComentario(comentario2);
        livro2.adicionarComentario(comentario2);

        comentarioService.incluir(comentario1);
        comentarioService.incluir(comentario2);

        System.out.println("--- COMENTARIOS CRIADOS E INCLUIDOS ---");
        System.out.println(comentario1);
        System.out.println(comentario2);
        System.out.println();

        System.out.println("--- RELACIONAMENTOS ESTABELECIDOS ---");
        System.out.println("Usuário 1: 2 registros de leitura, 1 comentário");
        System.out.println("Usuário 2: 1 registro de leitura, 1 comentário");
        System.out.println("Livro 1: 1 registro de leitura, 1 comentário");
        System.out.println("Livro 2: 1 registro de leitura, 1 comentário");
        System.out.println();
    }

    // ==================== CONSULTAS COM STREAMS ====================

    private void executarConsultasComStreams() {
        System.out.println("========================================");
        System.out.println("   CONSULTAS COM STREAMS");
        System.out.println("========================================\n");

        System.out.println("1. BUSCAR LIVROS POR TITULO: 'Senhor'");
        livroService.buscarPorTitulo("Senhor")
                .forEach(l -> System.out.println("   -> " + l.getTitulo() + " - " + l.getAutor()));
        System.out.println();

        System.out.println("2. BUSCAR LIVROS POR AUTOR: 'Machado'");
        livroService.buscarPorAutor("Machado")
                .forEach(l -> System.out.println("   -> " + l.getTitulo() + " - " + l.getAutor()));
        System.out.println();

        System.out.println("3. LISTAR LIVROS DISPONIVEIS (filter)");
        livroService.listarDisponiveis()
                .forEach(l -> System.out.println("   -> " + l.getTitulo() + " (Disponivel: " + l.isDisponivel() + ")"));
        System.out.println();

        System.out.println("4. LISTAR LIVROS POR AVALIACAO (sorted)");
        livroService.listarPorAvaliacao()
                .forEach(l -> System.out.println("   -> " + l.getTitulo() + " - " + l.getAvaliacaoMedia()));
        System.out.println();

        System.out.println("5. TOP 5 MELHORES AVALIADOS (limit)");
        livroService.listarTop5MelhorAvaliados()
                .forEach(l -> System.out.println("   -> " + l.getTitulo() + " - " + l.getAvaliacaoMedia()));
        System.out.println();

        System.out.println("6. BUSCAR USUARIO POR NOME: 'ingrid'");
        usuarioService.buscarPorNome("ingrid")
                .forEach(u -> System.out.println("   -> " + u.getNome() + " (" + u.getUsername() + ")"));
        System.out.println();

        System.out.println("7. BUSCAR USUARIO POR USERNAME: 'william'");
        usuarioService.buscarPorUsername("william")
                .forEach(u -> System.out.println("   -> " + u.getNome() + " (" + u.getUsername() + ")"));
        System.out.println();

        System.out.println("10. COMENTARIOS COM AVALIACAO >= 4 ESTRELAS");
        comentarioService.buscarPorAvaliacaoMinima(4).forEach(c ->
                System.out.println("   -> Usuário: " + c.getUsuario().getUsername() +
                        " | Livro: " + c.getLivro().getTitulo() +
                        " | Avaliação: " + c.getAvaliacao()));
        System.out.println();

        System.out.println("11. MEDIA DE PAGINAS DOS LIVROS");
        double mediaPaginas = livroService.calcularMediaPaginas();
        System.out.println("   -> Média: " + String.format("%.1f", mediaPaginas) + " páginas");
        System.out.println();

        System.out.println("12. BUSCAR POR TERMO: 'Tolkien'");
        livroService.buscarPorTermo("Tolkien")
                .forEach(l -> System.out.println("   -> " + l.getTitulo() + " - " + l.getAutor()));
        System.out.println();
    }

    // ==================== TESTE DE EXCEÇÕES ====================

    private void testarExcecoes() {
        System.out.println("========================================");
        System.out.println("   TESTE DE EXCECOES");
        System.out.println("========================================\n");

        testarIncluirLivroIsbnDuplicado();
        testarBuscarLivroIdInexistente();
        testarIncluirUsuarioDuplicado();
        testarExcluirLivroIdInexistente();
        testarAlterarLivroIdInexistente();
    }

    private void testarIncluirLivroIsbnDuplicado() {
        System.out.println("TESTE 1: Incluir livro com ISBN duplicado");
        try {
            Livro livroDuplicado = new Livro(
                    "Livro Duplicado", "Autor Teste", "978-0-547-92934-9",
                    "Editora Teste", 100, "Ficção", 4.0, true,
                    "Sinopse de teste..."
            );
            livroService.incluir(livroDuplicado);
            System.out.println("   ERRO: Não deveria ter incluído!");
        } catch (IdentificadorDuplicadoException e) {
            System.out.println("   EXCECAO CAPTURADA: " + e.getMessage());
        }
        System.out.println();
    }

    private void testarBuscarLivroIdInexistente() {
        System.out.println("TESTE 2: Buscar livro com ID inexistente (999)");
        try {
            livroService.obterPorId(999L);
            System.out.println("   ERRO: Não deveria ter encontrado!");
        } catch (RecursoNaoEncontradoException e) {
            System.out.println("   EXCECAO CAPTURADA: " + e.getMessage());
        }
        System.out.println();
    }

    private void testarIncluirUsuarioDuplicado() {
        System.out.println("TESTE 3: Incluir usuário com username/email duplicado");
        try {
            Usuario usuarioDuplicado = new Usuario(
                    "Duplicado", "ingrid", "ingrid@email.com", "123"
            );
            usuarioService.incluir(usuarioDuplicado);
            System.out.println("   ERRO: Não deveria ter incluído!");
        } catch (IdentificadorDuplicadoException e) {
            System.out.println("   EXCECAO CAPTURADA: " + e.getMessage());
        }
        System.out.println();
    }

    private void testarExcluirLivroIdInexistente() {
        System.out.println("TESTE 4: Excluir livro com ID inexistente (999)");
        try {
            livroService.excluir(999L);
            System.out.println("   ERRO: Não deveria ter excluído!");
        } catch (RecursoNaoEncontradoException e) {
            System.out.println("   EXCECAO CAPTURADA: " + e.getMessage());
        }
        System.out.println();
    }

    private void testarAlterarLivroIdInexistente() {
        System.out.println("TESTE 5: Alterar livro com ID inexistente");
        try {
            Livro livroInexistente = new Livro(
                    "Inexistente", "Autor", "0000-INEXISTENTE",
                    "Editora", 100, "Ficção", 4.0, true,
                    "Sinopse..."
            );

            livroInexistente.setId(999L);

            livroService.alterar(livroInexistente);
            System.out.println("   ERRO: Não deveria ter alterado!");
        } catch (RecursoNaoEncontradoException e) {
            System.out.println("   EXCECAO CAPTURADA: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== RESULTADO FINAL ====================

    private void exibirResultadoFinal() {
        System.out.println("========================================");
        System.out.println("     RESULTADO FINAL");
        System.out.println("========================================\n");

        System.out.println("=== USUARIOS COM SEUS DADOS ===");
        if (usuario1 != null) {
            System.out.println(usuario1);
            System.out.println("  Comentários: " + comentarioService.buscarPorUsuario(usuario1.getId()).size());
        } else {
            System.out.println("(nenhum usuário carregado)");
        }
        System.out.println();
        if (usuario2 != null) {
            System.out.println(usuario2);
            System.out.println("  Comentários: " + comentarioService.buscarPorUsuario(usuario2.getId()).size());
        } else {
            System.out.println("(nenhum usuário carregado)");
        }
        System.out.println();

        System.out.println("=== LIVROS COM SEUS DADOS ===");
        if (livro1 != null) {
            System.out.println(livro1);
            System.out.println("  Comentários: " + comentarioService.buscarPorLivro(livro1.getId()).size());
        } else {
            System.out.println("(nenhum livro carregado)");
        }
        System.out.println();
        if (livro2 != null) {
            System.out.println(livro2);
            System.out.println("  Comentários: " + comentarioService.buscarPorLivro(livro2.getId()).size());
        } else {
            System.out.println("(nenhum livro carregado)");
        }
        System.out.println();

        System.out.println("=== ESTATISTICAS COM STREAMS ===");
        System.out.println("  Total de usuários: " + usuarioService.obterLista().size());
        System.out.println("  Total de livros: " + livroService.obterLista().size());
        System.out.println("  Total de comentários: " + comentarioService.obterLista().size());
        System.out.println("  Média de páginas dos livros: " + String.format("%.1f", livroService.calcularMediaPaginas()));
        System.out.println("  Média de avaliações dos comentários: " + String.format("%.1f", comentarioService.calcularMediaAvaliacoes()));
        System.out.println();
    }

}