package br.edu.infnet.skoob_api_ingridmunhoz;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Comentario;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.RegistroLeitura;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Loader implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        System.out.println("========================================");
        System.out.println("INICIANDO SISTEMA SKOOB");
        System.out.println("========================================\n");


        System.out.println("SEMANA 01");

        // Teste 01: Criar Usuários
        Usuario usuario1 = new Usuario(1,"Ingrid Munhoz", "ingrid", "ingrid@email.com", "123");
        Usuario usuario2 = new Usuario(2,"William Poiato", "william", "william@email.com", "456");
        Usuario usuario3 = new Usuario(3,"Tito", "tito", "tito@email.com", "789");

        // Imprimindo usuários criados
        System.out.println("--- USUÁRIOS CRIADOS ---");
        System.out.println(usuario1);
        System.out.println(usuario2);
        System.out.println();

        // Teste 02: Criar Livros
        Livro livro1 = new Livro(
                1, "O Senhor dos Anéis", "J.R.R. Tolkien", "978-0-547-92934-9",
                "HarperCollins", 1216, "Fantasia", 4.8, true,
                "Uma jornada épica pela Terra Média..."
        );

        Livro livro2 = new Livro(
                2, "Dom Casmurro", "Machado de Assis", "978-85-250-4261-3",
                "Editora Globo", 256, "Romance", 4.5, true,
                "A história de Bentinho e Capitu..."
        );

        System.out.println("--- LIVROS CRIADOS ---");
        System.out.println(livro1);
        System.out.println(livro2);
        System.out.println();

        // Teste 03: Criar REGISTROS de leitura
        RegistroLeitura registro1 = new RegistroLeitura(
                1, "LENDO", 300, 25.0, 0,
                LocalDate.of(2026, 8, 1), null
        );

        RegistroLeitura registro2 = new RegistroLeitura(
                2, "TERMINADO", 256, 100.0, 5,
                LocalDate.of(2026, 7, 15), LocalDate.of(2026, 8, 20)
        );

        System.out.println("--- REGISTROS DE LEITURA CRIADOS ---");
        System.out.println(registro1);
        System.out.println(registro2);
        System.out.println();

        // Teste 04: RELACIONAMENTOS
        usuario1.adicionarRegistroLeitura(registro1);
        livro1.adicionarRegistroLeitura(registro1);

        usuario1.adicionarRegistroLeitura(registro2);
        livro2.adicionarRegistroLeitura(registro2);

        System.out.println("--- RELACIONAMENTOS ESTABELECIDOS ---");
        System.out.println("Usuário 1 adicionou 2 registros de leitura");
        System.out.println();


        // Teste 05: Criando COMENTÁRIOS
        Comentario comentario1 = new Comentario(
                1, "Fantástico!", "Tolkien é um gênio! A história é envolvente.",
                5, LocalDateTime.now(), false
        );

        Comentario comentario2 = new Comentario(
                2, "Bom livro", "A história é interessante. Traiu ou nao traiu??",
                3, LocalDateTime.now(), false
        );

        System.out.println("--- COMENTÁRIOS CRIADOS ---");
        System.out.println(comentario1);
        System.out.println(comentario2);
        System.out.println();

        // Teste 06: Relacionando COMENTARIOS com USUARIOS
        usuario1.adicionarComentario(comentario1);
        livro1.adicionarComentario(comentario1);

        usuario2.adicionarComentario(comentario2);
        livro2.adicionarComentario(comentario2);

        System.out.println("--- RELACIONAMENTOS DE COMENTÁRIOS ESTABELECIDOS ---");
        System.out.println();

        // Teste 07 > RESULTADOS

        System.out.println("========================================");
        System.out.println("     RESULTADO FINAL");
        System.out.println("========================================\n");

        System.out.println("=== USUÁRIOS COM SEUS DADOS ===");
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
    }
}
