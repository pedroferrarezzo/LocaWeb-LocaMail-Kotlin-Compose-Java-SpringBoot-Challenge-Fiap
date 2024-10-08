# DESAFIO - 2ª PARTE

`1ª PARTE DO DESAFIO:` https://github.com/pedroferrarezzo/LocaWeb-LocaMail-Kotlin-Compose-Challenge-Fiap

"O desafio agora é plugar o aplicativo de e-mail em uma robusta solução de back-end. Esta fase do projeto enfoca a integração de uma interface de usuário intuitiva, eficiente e personalizável com uma arquitetura de back-end capaz de suportar configurações avançadas de usuário, controle de spam e simulação de serviços de e-mail.

Requisitos:
- API de Preferências e configurações de usuário:
  - Permitir que os usuários ajustem configurações como: temas, cores, categorias, rótulos e outras preferências personalizadas;
  - Possibilidade das configurações serem migradas para outro dispositivo (armazenar essas configurações em um banco de dados);
  - Escolha dois temas para tratar como preferência do usuário.

- Mock de Serviços de e-mail:
  - Criar endpoints que simulem a funcionalidade de listagem de e-mails, abertura de detalhes, envio de e-mails e integração com calendário. (Não é obrigatório integrar com serviço de e-mail).
 
- Mecanismo de controle de Spam para Envio:
  - Implementar lógicas para detectar padrões de envio que podem ser considerados Spam;
  - Limitar a frequência de envio de e-mails por usuário ou por aplicação para prevenir abuso.

- Aplicativo:
  - Conectar o aplicativo nas API de Preferências e Mock de Serviços."
 
# TECNOLOGIAS UTILIZADAS
- Kotlin;
  - Android API Level 27 (Android Oreo);
  - Framework UI: Jetpack Compose;
  - Retrofit e GSON;
  - Google Gemini LLM;
- Java 21;
  - Spring Boot 3;
    - Spring Web;
    - Spring Data JPA. 
  - Flyway;
  - Lombok;
  - Resilience4j.
- Oracle SGBD 21c.

# MECANISMO DE SPAM
Para o controle de spam, foi implementado uma chamada a API externa (BlackList Checker), onde é validado o domínio do e-mail remetente em mais de 100 blacklists atualizadas frequentemente por providers ativos, prevenindo o recebimento de conteúdos maliciosos, aumentando a segurança do usuário. Também foi implementado o uso da biblioteca Resilience4J, onde fazemos o uso de:
- Ratelimiter : Limita a quantidade de requisições em um determinado período de tempo, tanto no recebimento quanto no envio de e-mail, evitando assim o abuso de recursos.
  ![image](https://github.com/user-attachments/assets/79d6f5d0-0209-49f5-ac45-85ac194ea9f7)
  ![image](https://github.com/user-attachments/assets/c61fb943-2694-468c-b90a-ccc2c91cbb38)

# PREFERÊNCIAS DO USUÁRIO
- Pastas personalizadas:
  - É possível criar pastas personalizadas para organizar os emails da caixa de entrada;
  - Emails movidos para pastas de organização são automaticamente retirados da caixa de emails principal.
    ![image](https://github.com/user-attachments/assets/359ccf44-1e1b-4aa5-8f77-ab89213c37fe)
    ![image](https://github.com/user-attachments/assets/3e1fcc95-1059-415a-9065-dfdfe453c218)
    ![image](https://github.com/user-attachments/assets/cde34633-e48a-4073-8ea3-c76dfb9f680b)
    ![image](https://github.com/user-attachments/assets/84a35e18-9383-41f0-8374-a9f62d9a5eac)
  - Agenda integrada ao calendário:
    - O aplicativo conta com um calendário integrado;
    - É possível criar tarefas e eventos no calendário;
    - Tarefas e eventos contam com opções de repetição automática de dias;
    - O dia atual é estilizado de maneira especial, facilitando a usabilidade do usuário;
    - É possível atribuir uma cor personalizada a tarefa ou evento, definindo assim, por exemplo, seu grau de importância.
      
      ![image](https://github.com/user-attachments/assets/f1ddcb39-d88c-447e-8045-d8ac9f752bac)
      ![image](https://github.com/user-attachments/assets/dba05c43-760c-4e47-bd65-125dca00412c)
      ![image](https://github.com/user-attachments/assets/511c2a79-fb48-44f0-b3a2-d1a41349053d)
      ![image](https://github.com/user-attachments/assets/e6e6de95-1695-489e-8e5a-498de5068dea)

# DIFERENCIAIS DA API
- Circuit Breaker:
  Caso algum serviço esteja indisponível ou retornando falha consecutivamente, o circuitbreaker é aberto, rejeitando todas as demais requests por um determinado período, salvando recursos do sistema.
  ![image](https://github.com/user-attachments/assets/b6eee959-6802-40e5-b284-0136bdd0a887)
  ![image](https://github.com/user-attachments/assets/5e99dcf4-243f-415c-b83e-085e6edd805a)

- Actuator:
  Monitoramento da API utilizando a biblioteca Actuator.
  ![image](https://github.com/user-attachments/assets/c163c2f1-2a50-45c6-a222-0cf99c618641)

- Design Patterns:
  API desenvolvida utilizando MVC, DTO, Services e Repository.

- Lombok:
  Biblioteca usada para minuir o uso de códigos boiler plate.

- Bean Validation:
  Validação seguindo as boas práticas nas classes DTO.

# DADOS ESTATÍSTICOS
- Rate Limiting:
  Este artigo da Cloudflare explica como a limitação de taxa pode bloquear usuários, bots ou aplicativos que abusam de uma propriedade da web. A limitação de taxa pode ajudar a parar certos tipos de atividades de bots maliciosas, incluindo ataques de força bruta e     DoS, que podem contribuir para o envio de spam: https://www.cloudflare.com/pt-br/learning/bots/what-is-rate-limiting/

- Importância do Rate Limiting:
  Este artigo da Aiqon destaca a importância do rate limiting como uma parte vital da segurança web moderna, ajudando a bloquear atividades maliciosas, como tentativas de login não autorizadas, que podem estar associadas ao envio de spam: https://aiqon.com.br/blog/rate-limit-uma-parte-vital-da-seguranca-web-moderna/





















