# Challenge 3 - Gerenciamento de Usuários: MSNotification (CompassUOL)
O projeto consiste no desenvolvimento de uma API REST para um sistema de gerenciamento de usuários, utilizando as tecnologias e conhecimentos aprendidos até o momento durante essa jornada do programa de bolsas de estágio da Compass UOL | Back-end Journey (Spring Boot) - AWS Cloud Context.

O projeto foi organizado em formato de microsserviços, onde cada microsserviço é responsável por uma parte do sistema. Segue os microsserviços desenvolvidos e a funcionalidade de cada um:

| **Microsserviço**                                                                | **Funcionalidade**                                                                                                         |
|----------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| [MSUser](https://github.com/quasemago/Compass_Challenge3_MSUser)                 | É responsável por armazenar e gerenciar os dados dos usuários, sendo integrado ao MSAddress e MSNotification.              |
| [MSAddress](https://github.com/quasemago/Compass_Challenge3_MSAddress)           | Encapsula a API ViaCEP e fornece um endpoint para consulta de endereço, tanto por CEP quanto por Id.                       |
| [MSNotification](https://github.com/quasemago/Compass_Challenge3_MSNotification) | Recebe e armazena notificações de eventos por parte dos usuários (MSUser), sendo eventos de cadastro, atualização e login. |

### Autor do Projeto
O projeto foi desenvolvido por **Bruno Patrick Formehl Ronning**.

| **E-mail**                           | **Usuário Github** |
|--------------------------------------|--------------------|
| bruno.ronning.pb@compasso.com.br     | quasemago          |

## Sumário
- [# Challenge 3 - Gerenciamento de Usuários: MSNotification (CompassUOL)](#challenge-3---gerenciamento-de-usuários-msnotification-compassuol)
    - [Autor do Projeto](#autor-do-projeto)
    - [Microsserviço de Notificações (MSNotification)](#microsserviço-de-notificações-msnotification)
        - [Estrutura do banco de dados](#estrutura-do-banco-de-dados)
        - [Regras de Negócio](#regras-de-negócio)
      - [Payloads](#payloads)
    - [Como executar o projeto](#como-executar-o-projeto)
        - [Pré-configurações](#pré-configurações)
    - [Executando o projeto (Terminal)](#executando-o-projeto-terminal)
        - [Projeto em execução](#projeto-em-execução)
    - [Considerações Finais](#considerações-finais)
      - [Amazon AWS](#amazon-aws)
      - [Agradecimentos](#agradecimentos)

## Tecnologias Utilizadas
- Java JDK 17
- Spring Boot 3

### Dependências
- Spring Boot Test
- Spring for RabbitMQ
- Spring Validation
- Spring Data MongoDB
- Spring DevTools
- Lombok
- Jackson Dataformat XML (Core e Databind)

---
# Microsserviço de Notificações (MSNotification)
O microsserviço de notificações tem a responsabilidade de receber e armazenar notificações de eventos por parte dos usuários (MSUser), sendo eventos em que o usuário foi criado, atualizado, atualizado a senha ou fez login.

### Estrutura do banco de dados
O banco de dados utilizado para armazenar as notificações de eventos é o MongoDB. A estrutura do banco de dados é composta por uma coleção chamada `event_notifications` e os campos:
- `id` (String): Identificador único da notificação.
- `email` (String): Identificador único do usuário que gerou a notificação.
- `event` (String): Tipo do evento que gerou a notificação.
- `date` (Date): Data e hora em que a notificação foi gerada (ISO 8601 - UTC).

### Regras de Negócio
- O microsserviço de notificações irá receber notificações de eventos dos seguintes tipos: `CREATE`, `UPDATE`, `UPDATE_PASSWORD` e `LOGIN`.

## Payloads
O microsserviço de notificações possui apenas um payload de requisição (Request) que recebe as notificações de eventos dos usuários através do serviço de mensageria RabbitMQ. Sendo:
- `UserRequestEvent`: Payload utilizado para receber as notificações de eventos dos usuários. Exemplo:
    ```json
    {
      "email": "teste@hotmail.com",
      "event": "CREATE",
      "date": "*DATA DA NOTIFICAÇÃO DO EVENTO EM ISO 8601*"
    }
    ```

---
# Como executar o projeto
O projeto foi desenvolvido utilizando a linguagem de programação Java, utilizando o Java Development Kit (JDK) na versão 17.
Portanto, para executar o projeto, é necessário ter o JDK 17 instalado na máquina, que pode ser baixado através do link: [Download Java JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)

### Pré-configurações
Antes de executar o projeto, é necessário fazer algumas configurações, como, por exemplo, a configuração do banco de dados MongoDB e o serviço RabbitMQ.
Para isso, basta acessar o arquivo ``application.yml`` localizado na pasta ``src/main/resources`` e alterar as seguintes propriedades de acordo com suas preferências.

Porém, é recomendado utilizar variáveis de ambiente para configuração do projeto, evitando assim que informações sensíveis fiquem expostas no código-fonte, além de não ser necessário recompilar o projeto toda vez que for necessário alterar alguma configuração.
Caso você não configure as variáveis de ambiente, o projeto utilizará as configurações padrões.

**Segue a lista de propriedades que podem ser configuradas via variáveis de ambiente:**

| **Nome da variável**            | **Descrição**                                | **Valor Padrão**                 |
|---------------------------------|----------------------------------------------|----------------------------------|
| RABBITMQ_HOST                   | IP/DNS do host RabbitMQ                      | localhost                        |
| RABBITMQ_PORT                   | Porta de conexão do RabbitMQ                 | 5672                             |
| RABBITMQ_USERNAME               | Usuário para conexão do RabbitMQ             | guest                            |
| RABBITMQ_PASSWORD               | Senha do usuário para conexão do RabbitMQ    | guest                            |
| CHALLENGE3_MQ_USER_QUEUE        | Nome da fila de mensagens do RabbitMQ        | user-queue                       |
| MONGODB_HOST                    | Chave secreta para autenticação do Token JWT | localhost                        |
| MONGODB_PORT                    | Host para conexão do banco MongoDB           | 27017                            |
| MONGODB_USERNAME                | Usuário para conexão do MongoDB              | root                             |
| MONGODB_PASSWORD                | Senha do usuário para conexão do MongoDB     | 123456                           |
| MONGODB_AUTHENTICATION_DATABASE | Database para autenticação do MongoDB        | admin                            |
| MONGODB_DATABASE                | Database para conexão do MongoDB             | compasschallenge3_msnotification |

## Executando o projeto (Terminal)
Para executar o projeto diretamente via terminal, além do JDK 17, é necessário ter o _apache maven_ instalado na máquina, que pode ser baixado através do link: [Download Apache Maven](https://maven.apache.org/download.cgi)

Após tudo instalado, basta abrir o terminal na pasta raiz do projeto, e executar o comando ``mvn clean install`` para que todas as dependências sejam baixadas. Após isso execute o comando ``mvn clean package`` para compilar nosso projeto.

Após a execução dos comandos acima, observe que será criado uma pasta chamada ``target`` na raiz do projeto, essa pasta contem o nosso projeto compilado, sendo nomeado de ``msnotification-1.0.jar``. Após entrar na pasta, basta executar o arquivo compilado do projeto utilizando o java.

Para executar o projeto, basta executar o comando ``java -jar msnotification-1.0.jar``.

### Projeto em execução
Após executar o projeto, o microsserviço de notificações estará disponível para receber notificações de eventos dos usuários, e armazená-las no banco de dados MongoDB.

Essas notificações de eventos devem ser enviadas para o serviço de mensageria RabbitMQ, que por sua vez, irá encaminhar as mensagens para o microsserviço de notificações.

---
# Considerações Finais

### Amazon AWS
Durante o desenvolvimento do projeto, foi necessário realizar testes em um ambiente real, para garantir que a integração entre os microsserviços funcionasse corretamente, visto que até o momento tudo estava sendo testado de forma local.
Para isso, a abordagem utilizada foi hospedar cada microsserviço em uma instância do Amazon AWS EC2, e então realizar as requisições entre os microsserviços, simulando um ambiente real de produção.

Os microsserviços foram escalados da seguinte forma:

| **Microsserviço** | **Abordagem**                                                                                                                                                                                    |
|-------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| MSUser            | Hospedado em uma instância do AWS EC2, utilizando um banco de dados MySQL local hospedado na própria instância.                                                                                  |
| MSAddress         | Hospedado em uma instância do AWS EC2, utilizando um banco de dados MySQL através do serviço RDS da AWS, proporcionando maior escalabilidade e disponibilidade.                                  |
| MSNotification    | Hospedado em uma instância do AWS EC2, utilizando um banco de dados MongoDB hospedado na própria instância. Além disso, o serviço de mensageria RabbitMQ também ficou hospedado nessa instância. |

Portanto, no final dos testes em um ambiente real de produção, foi possível observar que a integração entre os microsserviços funcionou corretamente, e que tudo estava funcionando corretamente, atendendo a todos os requisitos propostos.

Essa etapa no desenvolvimento foi muito relevante, pois contribuiu para o aprendizado de como é o processo de deploy de um projeto em um ambiente real, mesmo que tenha sido feito de forma manual e não automatizada.
Pois foi necessário configurar as instâncias, instalar as dependências, configurar o banco de dados, configurar o firewall para comunicação entre os serviços, entre outras configurações, evidenciando as dificuldades e desafios que podem ser encontrados em um ambiente real.

### Agradecimentos
O terceiro desafio (Challenge 3) do programa de bolsas de estágio da Compass UOL | Back-end Journey (Spring Boot) - AWS Cloud Context, representou uma grande oportunidade de aprendizado para aplicar os conhecimentos adquiridos até o momento no programa.

Ao utilizar tecnologias como Spring Boot, Swagger e JUnit e Mockito, foi possível criar uma API robusta, testada e bem documentada, seguindo boas práticas de desenvolvimento, garantindo assim a qualidade do projeto, além de facilitar o uso da API por parte de outros desenvolvedores.

Portanto, é importante destacar que esse desafio foi de grande importância, pois além de implementar uma API REST, que havia sido o foco do desafio anterior, foi necessário implementar uma integração entre microsserviços, utilizando de serviços de mensageria como o RabbitMQ e serviços de requisição HTTP como o OpenFeign.

Agradeço à Compass UOL pela oportunidade de participar do programa de bolsas de estágio, e por proporcionar desafios que contribuem para o meu crescimento profissional.

Atenciosamente, [Bruno Patrick Formehl Ronning](#autor-do-projeto).