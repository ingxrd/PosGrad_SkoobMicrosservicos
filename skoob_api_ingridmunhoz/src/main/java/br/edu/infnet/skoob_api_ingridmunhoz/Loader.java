package br.edu.infnet.skoob_api_ingridmunhoz;

import br.edu.infnet.skoob_api_ingridmunhoz.exception.IdentificadorDuplicadoException;
import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Comentario;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.RegistroLeitura;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import br.edu.infnet.skoob_api_ingridmunhoz.service.ComentarioService;
import br.edu.infnet.skoob_api_ingridmunhoz.service.LivroService;
import br.edu.infnet.skoob_api_ingridmunhoz.service.RegistroLeituraService;
import br.edu.infnet.skoob_api_ingridmunhoz.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Loader implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final RegistroLeituraService registroLeituraService;
    private final ComentarioService comentarioService;

    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;

    private Livro livro1;
    private Livro livro2;
    private Livro livro3;

    public Loader(UsuarioService usuarioService, LivroService livroService,
                  RegistroLeituraService registroLeituraService, ComentarioService comentarioService) {
        this.usuarioService = usuarioService;
        this.livroService = livroService;
        this.registroLeituraService = registroLeituraService;
        this.comentarioService = comentarioService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("========================================");
        System.out.println("INICIANDO SISTEMA SKOOB - ETAPA 2");
        System.out.println("========================================\n");

        criarUsuarios();
        criarLivros();
        criarRegistrosLeitura();
        criarComentarios();

        executarConsultasComStreams();
        testarExcecoes();
        exibirResultadoFinal();
        exibirInstrucoesFinais();
    }

    // ==================== SEMANA 01: CRIAÇÃO DOS DADOS ====================

    private void criarUsuarios() {
        System.out.println("--- SEMANA 01: CRIANDO OBJETOS ---\n");

        usuario1 = new Usuario(1, "Ingrid Munhoz", "ingrid", "ingrid@email.com", "123");
        usuario2 = new Usuario(2, "William Poiato", "william", "william@email.com", "456");
        usuario3 = new Usuario(3, "Tito", "tito", "tito@email.com", "789");

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
                1, "O Senhor dos Anéis", "J.R.R. Tolkien", "978-0-547-92934-9",
                "HarperCollins", 1216, "Fantasia", 4.8, true,
                "Uma jornada épica pela Terra Média..."
        );

        livro2 = new Livro(
                2, "Dom Casmurro", "Machado de Assis", "978-85-250-4261-3",
                "Editora Globo", 256, "Romance", 4.5, true,
                "A história de Bentinho e Capitu..."
        );

        livro3 = new Livro(
                3, "A Arte da Guerra", "Sun Tzu", "978-85-336-0361-3",
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

    private void criarRegistrosLeitura() {
        RegistroLeitura registro1 = new RegistroLeitura(
                1, "LENDO", 300, 25.0, 0,
                LocalDate.of(2026, 8, 1), null
        );

        RegistroLeitura registro2 = new RegistroLeitura(
                2, "TERMINADO", 256, 100.0, 5,
                LocalDate.of(2026, 7, 15), LocalDate.of(2026, 8, 20)
        );

        RegistroLeitura registro3 = new RegistroLeitura(
                3, "LENDO", 50, 35.0, 0,
                LocalDate.of(2026, 8, 10), null
        );

        registro1.setUsuario(usuario1);
        registro1.setLivro(livro1);
        usuario1.adicionarRegistroLeitura(registro1);
        livro1.adicionarRegistroLeitura(registro1);

        registro2.setUsuario(usuario1);
        registro2.setLivro(livro2);
        usuario1.adicionarRegistroLeitura(registro2);
        livro2.adicionarRegistroLeitura(registro2);

        registro3.setUsuario(usuario2);
        registro3.setLivro(livro3);
        usuario2.adicionarRegistroLeitura(registro3);
        livro3.adicionarRegistroLeitura(registro3);

        registroLeituraService.incluir(registro1);
        registroLeituraService.incluir(registro2);
        registroLeituraService.incluir(registro3);

        System.out.println("--- REGISTROS DE LEITURA CRIADOS E INCLUIDOS ---");
        System.out.println(registro1);
        System.out.println(registro2);
        System.out.println(registro3);
        System.out.println();
    }

    private void criarComentarios() {
        Comentario comentario1 = new Comentario(
                1, "Fantástico!", "Tolkien é um gênio! A história é envolvente.",
                5, LocalDateTime.now(), false
        );

        Comentario comentario2 = new Comentario(
                2, "Bom livro", "A história é interessante. Traiu ou não traiu??",
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
        System.out.println("   CONSULTAS COM STREAMS (ETAPA 2)");
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

        System.out.println("5. TOP 3 MELHORES AVALIADOS (limit)");
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

        System.out.println("8. REGISTROS EM ANDAMENTO (progresso < 100%)");
        registroLeituraService.listarEmAndamento().forEach(r ->
                System.out.println("   -> Usuário: " + r.getUsuario().getUsername() +
                        " | Livro: " + r.getLivro().getTitulo() +
                        " | Progresso: " + r.getPercentualLeitura() + "%"));
        System.out.println();

        System.out.println("9. REGISTROS FINALIZADOS (status = TERMINADO)");
        registroLeituraService.listarFinalizados().forEach(r ->
                System.out.println("   -> Usuário: " + r.getUsuario().getUsername() +
                        " | Livro: " + r.getLivro().getTitulo() +
                        " | Avaliação: " + r.getAvaliacaoUsuario()));
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
        System.out.println("   TESTE DE EXCECOES (ETAPA 2)");
        System.out.println("========================================\n");

        testarIncluirLivroIdDuplicado();
        testarBuscarLivroIdInexistente();
        testarIncluirUsuarioIdDuplicado();
        testarExcluirLivroIdInexistente();
        testarAlterarLivroIdInexistente();
    }

    private void testarIncluirLivroIdDuplicado() {
        System.out.println("TESTE 1: Incluir livro com ID duplicado");
        try {
            Livro livroDuplicado = new Livro(
                    1, "Livro Duplicado", "Autor Teste", "0000",
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

    private void testarIncluirUsuarioIdDuplicado() {
        System.out.println("TESTE 3: Incluir usuário com ID duplicado");
        try {
            Usuario usuarioDuplicado = new Usuario(1, "Duplicado", "dup", "dup@email.com", "123");
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
                    999, "Inexistente", "Autor", "0000",
                    "Editora", 100, "Ficção", 4.0, true,
                    "Sinopse..."
            );
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
        System.out.println(usuario1);
        System.out.println("  Registros: " + usuario1.getRegistrosLeitura().size());
        System.out.println("  Comentários: " + usuario1.getComentarios().size());
        System.out.println();
        System.out.println(usuario2);
        System.out.println("  Registros: " + usuario2.getRegistrosLeitura().size());
        System.out.println("  Comentários: " + usuario2.getComentarios().size());
        System.out.println();

        System.out.println("=== LIVROS COM SEUS DADOS ===");
        System.out.println(livro1);
        System.out.println("  Registros: " + livro1.getRegistrosLeitura().size());
        System.out.println("  Comentários: " + livro1.getComentarios().size());
        System.out.println();
        System.out.println(livro2);
        System.out.println("  Registros: " + livro2.getRegistrosLeitura().size());
        System.out.println("  Comentários: " + livro2.getComentarios().size());
        System.out.println();

        System.out.println("=== ESTATISTICAS COM STREAMS ===");
        System.out.println("  Total de usuários: " + usuarioService.obterLista().size());
        System.out.println("  Total de livros: " + livroService.obterLista().size());
        System.out.println("  Total de registros de leitura: " + registroLeituraService.obterLista().size());
        System.out.println("  Total de comentários: " + comentarioService.obterLista().size());
        System.out.println("  Média de páginas dos livros: " + String.format("%.1f", livroService.calcularMediaPaginas()));
        System.out.println("  Média de avaliações dos comentários: " + String.format("%.1f", comentarioService.calcularMediaAvaliacoesComentarios()));
        System.out.println();
    }

    private void exibirInstrucoesFinais() {
        System.out.println("========================================");
        System.out.println("   SKOOB INICIALIZADO COM SUCESSO!");
    }
}