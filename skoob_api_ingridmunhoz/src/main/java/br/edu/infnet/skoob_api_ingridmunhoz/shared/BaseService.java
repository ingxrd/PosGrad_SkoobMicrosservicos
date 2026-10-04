package br.edu.infnet.skoob_api_ingridmunhoz.shared;

import br.edu.infnet.skoob_api_ingridmunhoz.exception.IdentificadorDuplicadoException;
import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Esta classe tem por objetivo servir de base genérica para todos os serviços que utilizam o armazenamento em memória com o Map.
// @param <T> Tipo do objeto que será armazenado (deve ter getId())
public abstract class BaseService<T> {

    // Armazenamento em memória
    private final Map<Long, T> dados = new HashMap<>();

    // Contador para gerar IDs automáticos
    private Long proximoId = 1L;

    // IDENTIFICADOR: getId + criarId. Poderao ser implementados pelas subclasses
    protected abstract Long getId(T objeto);

    protected Long gerarId(){
        return proximoId++;
    }

    // ==================== CRUD ====================

    // Incluir
    public T incluir(T objeto){
        validarObjeto(objeto);
        Long id = getId(objeto);
        if (id == null){
            id = gerarId();
            setId(objeto,id);
        } else {
            if(dados.containsKey(id)){
                throw new IdentificadorDuplicadoException(
                        "Já existe um objeto com o identificador " + id
                );
            }
        }
        dados.put(id, objeto);
        return objeto;
    }

    // Alterar
    public T alterar(T objeto){
        validarObjeto(objeto);
        Long id = getId(objeto);
        if (id == null) {
            throw new IllegalArgumentException("O identificador não pode ser nulo para alteração!");
        }
        verificarExistencia(id);
        dados.put(id, objeto);
        return objeto;
    }


    // Remover
    public void excluir(Long id) {
        verificarExistencia(id);
        dados.remove(id);
    }

    // get by ID
    public T obterPorId(Long id) {
        verificarExistencia(id);
        return dados.get(id);
    }

    // get TODOS
    public List<T> obterLista() {
        return new ArrayList<>(dados.values());
    }

    // ==================== MÉTODOS DE VALIDAÇÃO ====================

    // Valida se o objeto nao é nulo e se o ID esta preenchido corretamente
    private void validarObjeto(T objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("Objeto não pode ser nulo!");
        }

        Long id = getId(objeto);
        if (id == null) {
            // Não lança exceção aqui - pode ser uma inclusão sem ID
            // que será tratada no método incluir()
            return;
        }
    }

    // Valida se um objeto com o ID existe.
    // Se não existir, lança RecursoNaoEncontradoException

    protected void verificarExistencia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Identificador não pode ser nulo!");
        }

        if (!dados.containsKey(id)) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum recurso encontrado para o identificador " + id
            );
        }
    }

    // Verifica se o id nao existe no map, usado para validar ai nclusao
    protected boolean existe(Long id){
        return id != null && dados.containsKey(id);
    }

    //Método abstrato para setar o ID no objeto pq Cada subclasse deve implementar
    protected abstract void setId(T objeto, Long id);


    // ==================== MÉTODOS PARA CONSULTA COM STREAMS ====================

    // Filtra objetos que tem termo no campo de string

    protected List<T> filtrarPorTexto(String termo, java.util.function.Function<T, String> extrator) {
        if (termo == null || termo.trim().isEmpty()) {
            return obterLista();
        }

        String termoLower = termo.toLowerCase().trim();

        return obterLista().stream()
                .filter(objeto -> {
                    String valor = extrator.apply(objeto);
                    return valor != null && valor.toLowerCase().contains(termoLower);
                })
                .collect(java.util.stream.Collectors.toList());
    }

    // Ordenar a lista por um campo especifico
    protected List<T> ordenarPor(java.util.Comparator<T> comparador) {
        List<T> lista = obterLista();
        lista.sort(comparador);
        return lista;
    }

    // ==================== MÉTODOS PROTEGIDOS PARA ACESSO AO MAP ====================

    //Retorna o Map de dados para uso nas subclasses.
    protected Map<Long, T> getDados() {
        return dados;
    }

    //Verifica se o Map está vazio.
    protected boolean isEmpty() {
        return dados.isEmpty();
    }

    //Retorna a quantidade de objetos armazenados.
    protected int size() {
        return dados.size();
    }















}

