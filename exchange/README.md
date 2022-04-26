# Exchange
Serviço responsável por efetuar operações no exchange.

### Instalação
- Install OpenJDK 11 [Installing OpenJDK](https://openjdk.java.net/install/)
- Maven [Installing Apache Maven](https://maven.apache.org/install.html)
- Git [Installing Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- PostgreSQL [Install PostgreSQL](https://www.postgresql.org/download/)
- RabbitMQ [Install RabbitMQ](https://www.rabbitmq.com/download.html)

Download do repositório do github
```
❯ git clone git@github.com:robsonkades/vibranium-exchange.git
```

Entre na pasta do repositório
```
❯ cd vibranium-exchange/exchange
```

Executar os testes
```
❯ mvn test
```

Gerar os artefatos
```
❯ mvn package
```

### Configurações

#### Variáveis de ambiente
| name              | type   | required | default                                   |
|-------------------|--------|----------|-------------------------------------------|
| RABBITMQ_HOST     | STRING | FALSE    | localhost                                 |
| RABBITMQ_USERNAME | STRING | FALSE    | admin                                     |
| RABBITMQ_PASSWORD | STRING | FALSE    | admin                                     |
| RABBITMQ_PORT     | NUMBER | FALSE    | 5672                                      |
| DATABASE_HOST     | STRING | FALSE    | jdbc:postgresql://localhost:5432/postgres |
| DATABASE_USERNAME | STRING | FALSE    | postgres                                  |
| DATABASE_PASSWORD | STRING | FALSE    | postgres                                  |
| SERVER_PORT       | NUMBER | FALSE    | 8081                                      |

# Executando a aplicação

## Executando a aplicação local
```
mvn spring-boot:run
```

## Execurando a aplicação com docker

### Criando imagem docker
```
docker build  -t exchange:1.0.0 .
```

### Executando o container
```
docker run --name exchange -p 8081:8081 -e RABBITMQ_HOST=${RABBITMQ_HOST} -e RABBITMQ_USERNAME=${RABBITMQ_USERNAME} -e RABBITMQ_PASSWORD=${RABBITMQ_PASSWORD} -e RABBITMQ_PORT=${RABBITMQ_PORT} -e DATABASE_HOST={DATABASE_HOST} -e DATABASE_USERNAME=${DATABASE_USERNAME} -e DATABASE_PASSWORD=${DATABASE_PASSWORD} -e HOST_EXCHANGE=${HOST_EXCHANGE} -d exchange:1.0.0
```

# Documentação das apis
[Swagger](./swagger.yaml) - Documentação