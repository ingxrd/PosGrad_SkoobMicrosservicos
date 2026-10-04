
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