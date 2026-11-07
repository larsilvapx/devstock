# DevStock — API de Gestão de Ativos de TI

API REST desenvolvida com Java e Spring Boot para gerenciar ativos de TI e apoiar o processo de onboarding de desenvolvedores.

O projeto tem como objetivo aplicar conceitos de desenvolvimento backend em um cenário próximo ao ambiente corporativo, incluindo persistência de dados, organização em camadas, regras de negócio, relacionamentos entre entidades e tratamento de erros.

## Objetivo do projeto

Facilitar o controle de notebooks e equipamentos disponibilizados aos desenvolvedores, permitindo gerenciar o estoque de ativos, kits de equipamentos, processos de onboarding, entregas, devoluções e histórico de movimentações.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Git e GitHub

> Consulte o `pom.xml` para verificar as versões exatas das dependências utilizadas no projeto.

## Funcionalidades implementadas

### Gerenciamento de notebooks

- Listagem de notebooks cadastrados.
- Consulta de notebook por identificador.
- Cadastro de notebooks.
- Atualização de informações.
- Exclusão de notebooks, respeitando as restrições de integridade do banco de dados.
- Verificação de duplicidade do número de série.
- Tratamento de conflitos de dados.

### Gerenciamento de desenvolvedores

- Cadastro de desenvolvedores.
- Listagem de desenvolvedores.
- Consulta por identificador.
- Controle de informações cadastrais.
- Restrição de duplicidade de e-mail e matrícula.

### Gerenciamento de kits

- Cadastro e gerenciamento de kits.
- Associação de itens aos kits.
- Organização dos equipamentos que compõem cada kit.
- Utilização dos itens cadastrados na preparação do onboarding.

### Processo de onboarding

- Criação de onboarding vinculando desenvolvedor e kit.
- Associação dos itens do kit ao processo de onboarding.
- Identificação do notebook pelo número de série.
- Verificação da disponibilidade do notebook antes de associá-lo ao onboarding.
- Impedimento de criação de um novo onboarding enquanto existir outro em andamento para o mesmo desenvolvedor.
- Preservação do histórico dos processos de onboarding.

### Histórico de movimentações

A aplicação possui a estrutura inicial para registrar movimentações relacionadas exclusivamente aos notebooks.

Os tipos de movimentação definidos são:

| Tipo | Descrição |
|---|---|
| `ENTREGA` | Registro da entrega do notebook ao desenvolvedor. |
| `DEVOLUCAO` | Registro da devolução do notebook. |
| `OCORRENCIA` | Registro de ocorrências relacionadas ao equipamento. |

Cada movimentação foi modelada para armazenar:

- Notebook relacionado.
- Onboarding associado.
- Tipo de movimentação.
- Data e hora do evento.
- Responsável pelo registro.
- Observações.

**Status:** estrutura de entidades, enumerações e repositórios criada. A integração com as operações de entrega, devolução e ocorrência está em desenvolvimento.

## Arquitetura

A aplicação utiliza uma organização em camadas para separar as responsabilidades:

| Camada | Responsabilidade |
|---|---|
| Controller | Receber requisições HTTP e retornar respostas da API. |
| Service | Centralizar as regras de negócio. |
| Repository | Realizar operações de persistência por meio do Spring Data JPA. |
| Entity | Representar as entidades persistidas no banco de dados. |
| DTO | Organizar os dados de entrada e saída da API. |
| Exception | Organizar exceções específicas e o tratamento de erros. |
| Enum | Padronizar valores utilizados nas regras de negócio. |

Essa separação facilita a manutenção, a evolução das funcionalidades e a realização de testes.

## Banco de dados

O PostgreSQL é utilizado para armazenar as informações da aplicação.

O modelo de dados contempla entidades relacionadas à gestão de ativos de TI:

- Desenvolvedor.
- Notebook.
- Kit.
- KitItem.
- Onboarding.
- OnboardingItem.
- Movimentacao.

### Relacionamentos principais

- Um desenvolvedor pode possuir vários registros de onboarding ao longo do tempo.
- Um kit pode conter diversos itens.
- Um onboarding está associado a um desenvolvedor e a um kit.
- Um onboarding possui seus próprios itens, preservando as informações do processo.
- Um item de onboarding pode estar associado a um notebook.
- Uma movimentação está vinculada a um notebook e ao onboarding correspondente.

O histórico de movimentações foi planejado para permitir o rastreamento dos eventos relacionados aos notebooks ao longo do ciclo de utilização.

## Endpoints

O DevStock disponibiliza endpoints REST para gerenciamento de notebooks, desenvolvedores e processos de onboarding.

### API de Notebooks

**Endpoint-base:** `/api/notebooks`

| Método | Endpoint | Descrição | Resposta de sucesso |
|---|---|---|---|
| GET | `/api/notebooks` | Lista todos os notebooks | `200 OK` |
| GET | `/api/notebooks/{id}` | Busca notebook pelo ID | `200 OK` |
| POST | `/api/notebooks` | Cadastra um notebook | `201 Created` |
| PUT | `/api/notebooks/{id}` | Atualiza um notebook | `200 OK` |
| DELETE | `/api/notebooks/{id}` | Exclui um notebook | `204 No Content` |

### API de Desenvolvedores

**Endpoint-base:** `/api/desenvolvedores`

| Método | Endpoint | Descrição | Resposta de sucesso |
|---|---|---|---|
| GET | `/api/desenvolvedores` | Lista todos os desenvolvedores | `200 OK` |
| GET | `/api/desenvolvedores/{id}` | Busca desenvolvedor pelo ID | `200 OK` |
| POST | `/api/desenvolvedores` | Cadastra um desenvolvedor | `200 OK` |

### API de Onboarding

**Endpoint-base:** `/api/onboardings`

| Método | Endpoint | Descrição | Resposta de sucesso |
|---|---|---|---|
| POST | `/api/onboardings` | Cria um onboarding para um desenvolvedor utilizando um kit | `201 Created` |

O endpoint de criação retorna o recurso criado no corpo da resposta e informa sua localização no cabeçalho `Location`.

> Os endpoints de entrega, devolução e consulta de movimentações serão documentados após sua implementação.

## Códigos de erro

A aplicação possui tratamento global de exceções para padronizar erros.

| Código HTTP | Significado |
|---|---|
| `400 Bad Request` | Requisição malformada ou parâmetro inválido |
| `404 Not Found` | Recurso não encontrado |
| `409 Conflict` | Conflito de cadastro ou violação de integridade |
| `500 Internal Server Error` | Erro inesperado no servidor |

A ocorrência de cada erro depende das regras de negócio, das exceções lançadas e das restrições do banco de dados.

## Configuração do ambiente

### Pré-requisitos

- JDK compatível com a versão configurada no projeto.
- PostgreSQL.
- Git.

O Maven Wrapper está incluído no repositório para facilitar a execução dos comandos Maven.

### Configuração do banco de dados

Crie o banco de dados `devstock` no PostgreSQL e configure as variáveis de ambiente utilizadas pela aplicação:

- `DB_URL`: endereço de conexão com o banco.
- `DB_USERNAME`: usuário do banco.
- `DB_PASSWORD`: senha do banco.

A aplicação utiliza o Hibernate para validar o mapeamento das entidades em relação à estrutura existente no banco de dados.

**Importante:** a configuração atual utiliza `ddl-auto=validate`. Portanto, é necessário que as tabelas e colunas esperadas já existam e sejam compatíveis com as entidades Java.

Nunca publique senhas ou credenciais reais no repositório.

### Executando a aplicação no Windows

No terminal, acesse a pasta raiz do projeto e execute:

```powershell
.\mvnw.cmd clean compile
```

Se a compilação terminar com sucesso, inicie a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

A execução depende de o PostgreSQL estar acessível e de as variáveis de ambiente estarem configuradas corretamente.

## Próximas etapas

- [ ] Implementar a confirmação de entrega do notebook.
- [ ] Registrar automaticamente a movimentação `ENTREGA`.
- [ ] Implementar o processo de devolução dos equipamentos.
- [ ] Registrar automaticamente a movimentação `DEVOLUCAO`.
- [ ] Implementar o registro de ocorrências.
- [ ] Criar endpoint para consulta do histórico de movimentações por notebook.
- [ ] Finalizar a documentação dos endpoints e exemplos de requisições.
- [ ] Revisar e ampliar o tratamento de erros.
- [ ] Criar e ampliar testes automatizados.
- [ ] Avaliar a utilização de DTOs e validação de entrada.
- [ ] Documentar a API com OpenAPI/Swagger.
- [ ] Preparar a execução com Docker.

## Status

**Em desenvolvimento.**

O DevStock é um projeto prático de aprendizado e portfólio, com evolução incremental das funcionalidades e aplicação de conceitos de desenvolvimento backend com Java e Spring Boot.

## Autor

Desenvolvido por Luciano Rodrigues.

- GitHub: [larsilvapx](https://github.com/larsilvapx)
- Repositório: [DevStock](https://github.com/larsilvapx/devstock)