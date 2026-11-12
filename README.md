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
- Cadastro, atualização e exclusão de notebooks.
- Verificação de duplicidade do número de série.
- Tratamento de conflitos de dados e restrições de integridade.

### Gerenciamento de desenvolvedores

- Cadastro de desenvolvedores.
- Listagem e consulta por identificador.
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
- Impedimento de criação de novo onboarding enquanto existir outro em andamento para o mesmo desenvolvedor.
- Preservação do histórico dos processos de onboarding.
- Consulta de todos os onboardings e consulta por identificador.
- Confirmação da entrega dos equipamentos.
- Registro automático da data e do responsável pela entrega.
- Atualização automática do notebook para `EM_USO` após a entrega.
- Confirmação da devolução dos equipamentos.
- Registro da data e do responsável pela devolução.
- Registro individual da situação dos itens devolvidos.
- Atualização do notebook conforme sua condição:
  - `BOM_ESTADO`: retorna para `DISPONIVEL`.
  - `DANIFICADO`: passa para `MANUTENCAO`.
  - `NAO_APRESENTADO`: permanece `EM_USO`.
- Encerramento do onboarding após a confirmação da devolução.
- Bloqueio de devolução duplicada.

### Histórico de movimentações

A aplicação possui uma estrutura para registrar movimentações relacionadas aos notebooks.

| Tipo | Descrição | Situação |
|---|---|---|
| `ENTREGA` | Registro da entrega do notebook ao desenvolvedor. | Implementado |
| `DEVOLUCAO` | Registro da devolução física do notebook. | Implementado |
| `OCORRENCIA` | Registro de ocorrências relacionadas ao equipamento. | Pendente |

As movimentações de entrega e devolução são registradas automaticamente durante os respectivos processos de onboarding.

**Regra importante:** quando um notebook é marcado como `NAO_APRESENTADO`, o onboarding pode ser encerrado, mas o notebook permanece `EM_USO` e não recebe uma movimentação de devolução.

Cada movimentação armazena:

- Notebook relacionado.
- Onboarding associado.
- Tipo de movimentação.
- Data e hora do evento.
- Responsável pelo registro.
- Observações.

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

## Endpoints

A API disponibiliza endpoints REST para gerenciamento de notebooks, desenvolvedores e processos de onboarding.

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
| GET | `/api/onboardings` | Lista todos os onboardings | `200 OK` |
| GET | `/api/onboardings/{id}` | Busca onboarding pelo ID | `200 OK` |
| POST | `/api/onboardings` | Cria onboarding vinculando desenvolvedor e kit | `201 Created` |
| PUT | `/api/onboardings/{id}/confirmar-entrega` | Confirma a entrega dos equipamentos | `200 OK` |
| PUT | `/api/onboardings/{id}/confirmar-devolucao` | Confirma a devolução dos equipamentos | `200 OK` |

O endpoint de criação retorna o recurso criado no corpo da resposta e informa sua localização no cabeçalho `Location`.

### Exemplos de requisições

#### Criar onboarding

`POST /api/onboardings`

```json
{
  "desenvolvedorId": 1,
  "kitId": 1
}
```

Exemplo de resposta:

```json
{
  "id": 1,
  "desenvolvedorId": 1,
  "kitId": 1,
  "status": "EM_ANDAMENTO",
  "dataCriacao": "2026-11-10T16:20:11"
}
```

#### Confirmar entrega

`PUT /api/onboardings/2/confirmar-entrega`

```json
{
  "responsavel": "Luciano Rodrigues",
  "observacao": "Entrega do kit realizada e equipamentos conferidos."
}
```

A confirmação registra automaticamente uma movimentação `ENTREGA`, vinculada ao notebook e ao onboarding correspondente, e atualiza o status do notebook para `EM_USO`.

Uma nova tentativa de confirmar a mesma entrega é impedida pela regra de negócio, retornando `409 Conflict`.

#### Confirmar devolução

`PUT /api/onboardings/2/confirmar-devolucao`

```json
{
  "responsavel": "Luciano Rodrigues",
  "observacao": "Devolução dos equipamentos ao setor de TI.",
  "itens": [
    {
      "onboardingItemId": 6,
      "situacao": "BOM_ESTADO",
      "observacao": "Notebook devolvido em perfeito estado."
    },
    {
      "onboardingItemId": 7,
      "situacao": "BOM_ESTADO",
      "observacao": "Mouse devolvido em boas condições."
    }
  ]
}
```

Valores aceitos para a situação de devolução:

- `BOM_ESTADO`
- `DANIFICADO`
- `NAO_APRESENTADO`

A requisição deve informar todos os itens associados ao onboarding, registrando individualmente a situação e as observações de cada um.

A aplicação registra automaticamente uma movimentação `DEVOLUCAO` para notebooks fisicamente apresentados. No caso de item não apresentado, a situação fica registrada no onboarding, sem alterar o status `EM_USO` do notebook e sem gerar movimentação de devolução.

Uma nova tentativa de devolução de um onboarding encerrado é bloqueada, retornando `409 Conflict`.

## Códigos de erro

A aplicação possui tratamento global de exceções para padronizar erros.

| Código HTTP | Significado |
|---|---|
| `400 Bad Request` | Requisição malformada ou parâmetro inválido |
| `404 Not Found` | Recurso não encontrado |
| `409 Conflict` | Conflito de cadastro ou violação de regra de negócio |
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

## Checklist de evolução do projeto

### Gestão de notebooks

- [x] Implementar CRUD de notebooks.
- [x] Implementar validações de cadastro e tratamento de erros.

### Processo de onboarding

- [x] Implementar criação de onboarding.
- [x] Validar disponibilidade dos equipamentos antes da entrega.
- [x] Implementar confirmação de entrega dos equipamentos.
- [x] Registrar automaticamente a movimentação `ENTREGA`.
- [x] Implementar processo de devolução dos equipamentos.
  - [x] Registrar responsável e data da devolução.
  - [x] Registrar a situação individual dos itens.
  - [x] Retornar notebook em bom estado para `DISPONIVEL`.
  - [x] Alterar notebook danificado para `MANUTENCAO`.
  - [x] Registrar item não apresentado sem alterar seu status `EM_USO`.
  - [x] Encerrar onboarding após a devolução.
- [x] Registrar automaticamente a movimentação `DEVOLUCAO` para notebooks apresentados.
- [x] Validar os cenários de devolução: bom estado, danificado e não apresentado.
- [x] Validar bloqueio de devolução duplicada.

### Próximas funcionalidades

- [ ] Implementar registro de ocorrências.
- [ ] Criar endpoint de histórico de movimentações por notebook.
- [ ] Ampliar a documentação da API com exemplos de requisições.
- [ ] Revisar e ampliar o tratamento de erros.
- [ ] Implementar testes automatizados.
- [ ] Revisar validações dos DTOs.
- [ ] Configurar documentação OpenAPI/Swagger.
- [ ] Preparar ambiente com Docker.

## Status

**Em desenvolvimento.**

O DevStock é um projeto prático de aprendizado e portfólio, com evolução incremental das funcionalidades e aplicação de conceitos de desenvolvimento backend com Java e Spring Boot.

## Autor

Desenvolvido por Luciano Rodrigues.

- GitHub: [larsilvapx](https://github.com/larsilvapx)
- Repositório: [DevStock](https://github.com/larsilvapx/devstock)