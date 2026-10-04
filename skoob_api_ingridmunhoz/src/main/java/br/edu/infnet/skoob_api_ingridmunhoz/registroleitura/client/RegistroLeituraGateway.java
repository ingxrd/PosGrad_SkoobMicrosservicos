package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client;

import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraNaoEncontradoException;
import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraServiceIndisponivelException;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistroLeituraGateway {

    private final RegistroLeituraClient client;

    public RegistroLeituraGateway(RegistroLeituraClient client) {
        this.client = client;
    }

    public List<RegistroLeituraResponse> obterLista() {
        try {
            return client.obterLista();
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }

    public RegistroLeituraResponse obterPorId(Long id) {
        try {
            return client.obterPorId(id);
        } catch (FeignException.NotFound e) {
            throw new RegistroLeituraNaoEncontradoException(id);
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }

    public List<RegistroLeituraResponse> buscarPorUsuario(Long usuarioId) {
        try {
            return client.buscarPorUsuario(usuarioId);
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }

    public List<RegistroLeituraResponse> buscarPorLivro(Long livroId) {
        try {
            return client.buscarPorLivro(livroId);
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }

    public RegistroLeituraResponse incluir(RegistroLeituraRequest request) {
        try {
            return client.incluir(request);
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }

    public RegistroLeituraResponse alterar(Long id, RegistroLeituraRequest request) {
        try {
            return client.alterar(id, request);
        } catch (FeignException.NotFound e) {
            throw new RegistroLeituraNaoEncontradoException(id);
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }

    public void excluir(Long id) {
        try {
            client.excluir(id);
        } catch (FeignException.NotFound e) {
            throw new RegistroLeituraNaoEncontradoException(id);
        } catch (RetryableException e) {
            throw new RegistroLeituraServiceIndisponivelException();
        }
    }
}