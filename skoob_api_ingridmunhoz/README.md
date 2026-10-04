
## Skoob 

Projeto desenvolvido para a disciplina Desenvolvimento de Aplicações Java com Spring Boot.

A proposta do projeto é desenvolver uma rede social de livros inspirada no Skoob, permitindo que usuários mantenham seus registros de leitura, acompanhem seu progresso e compartilhem comentários e avaliações sobre os livros.

### Modelo de dados

Diagrama de relacionamento entre as principais entidades do sistema:
![Diagrama do Skoob](https://i.imgur.com/l4nfZ7H.png)

 - Como um mesmo usuário pode comentar vários livros, e um mesmo livro pode receber comentários de vários usuários diferentes, a relação entre Usuario e Livro (através dos comentários) é N:N. **Comentário** registra justamente essa relação, por isso optei pela separação. 
 
---
### Entidades

 1. **Usuário**: cadastro e informações dos leitores. 
 2. **Livro**: informações dos livros cadastrados. 
 3. **RegistroLeitura**: relaciona usuários e livros e registra o status e o progresso da leitura.
 4. **Comentário**: permite que usuários comentem e avaliem livros.

---
###### *Versão 1.0: 10.08.2026. Ingrid Munhoz*


---
# Microsserviços -> avançando na arquitetura
## Etapa 02

---

## Etapa 2 — Separação do RegistroLeitura

### Qual funcionalidade foi separada?

O módulo **RegistroLeitura** foi extraído para o serviço independente `skoob-registro-leitura-service`.

### Por que ela foi escolhida?

Porque possui uma **responsabilidade clara** (acompanhar a leitura), **regras próprias** (status, progresso e avaliação) e é **pouco acoplada** ao restante da aplicação, dependendo apenas de `Usuario` e `Livro` por ID.

### O que ficou mais complexo?

- A comunicação agora é feita via **HTTP (Feign)**, o que introduz a possibilidade de falhas de rede.
- Os relacionamentos com `Usuario` e `Livro` deixaram de ser relacionamentos **JPA** e passaram a ser representados apenas por **IDs**.
- Agora existem **dois bancos de dados separados**.
- Foi necessário implementar **tratamento para falhas de comunicação**, como:
    - `404 Not Found` para recurso não encontrado.
    - `503 Service Unavailable` para serviço indisponível.

### O que aconteceria se o serviço ficasse indisponível?

A aplicação principal continua funcionando normalmente para as demais operações, como **usuário, livro e comentário**.

Porém, as operações relacionadas ao **registro de leitura** retornam:

```text
HTTP 503 Service Unavailable
```
com uma mensagem amigável informando que o serviço está temporariamente indisponível.
A funcionalidade realmente precisa ser um serviço independente?

Não necessariamente.

A separação faz sentido didaticamente e também pode ser útil arquiteturalmente, por exemplo, permitindo escalar o serviço de leitura de forma independente.

Por outro lado, a divisão adiciona complexidade operacional, principalmente por introduzir comunicação via rede, múltiplos bancos de dados e necessidade de tratamento de falhas.

Portanto, a decisão de transformar uma funcionalidade em um serviço independente é uma decisão arquitetural e depende do contexto, dos requisitos e dos benefícios esperados.

##  Escolha REST, Mensageria ou Batch?

* **REST:** usado em operações que precisam de uma **resposta imediata**, como consultar um livro.
* **Mensageria:** usada em operações que podem ser **processadas de forma assíncrona**, como enviar uma notificação sobre um novo comentário.
* **Batch:** usado para **processamento de grandes volumes de dados**, como importar livros a partir de um arquivo CSV.
