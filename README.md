# DevStock — API de Gestão de Ativos de TI

API REST desenvolvida com Java e Spring Boot para gerenciar ativos de TI e apoiar o processo de onboarding de desenvolvedores.

O projeto tem como objetivo aplicar conceitos de desenvolvimento backend em um cenário próximo ao ambiente corporativo, incluindo persistência de dados, organização em camadas, regras de negócio, relacionamentos entre entidades, tratamento de erros e testes automatizados.

## Objetivo do projeto

Facilitar o controle de notebooks e equipamentos disponibilizados aos desenvolvedores, permitindo gerenciar o estoque de ativos, kits de equipamentos, processos de onboarding, entregas, devoluções, ocorrências e histórico de movimentações.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- MockMvc
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
- Consulta de todos os onboardings cadastrados.
- Consulta de onboarding por identificador.
- Confirmação da entrega dos equipamentos.
- Registro automático da data e do responsável pela entrega.
- Atualização automática do status do notebook para `EM_USO` após a entrega.
- Confirmação da devolução dos equipamentos.
- Registro da situação individual dos itens devolvidos.
- Atualização do status do notebook conforme sua condição física.
- Encerramento do onboarding após a devolução.

### Histórico de movimentações

A aplicação possui uma estrutura para registrar e consultar movimentações relacionadas aos notebooks.

Os tipos de movimentação definidos são:

| Tipo | Descrição | Situação |
|---|---|---|
| `ENTREGA` | Registro da entrega do notebook ao desenvolvedor. | Implementado |
| `DEVOLUCAO` | Registro da devolução física do notebook. | Implementado |
| `OCORRENCIA` | Registro de ocorrências relacionadas ao equipamento. | Implementado |

As movimentações de entrega e devolução são registradas automaticamente durante os respectivos processos de onboarding.

As ocorrências podem ser registradas em qualquer notebook, independentemente de existir um onboarding associado. O registro de uma ocorrência não altera automaticamente o status do equipamento.

**Regra importante:** quando um notebook é marcado como `NAO_APRESENTADO`, o onboarding pode ser encerrado, mas o notebook permanece `EM_USO` e não recebe uma movimentação de devolução.

Cada movimentação armazena:

- Notebook relacionado.
- Onboarding associado, quando aplicável.
- Tipo de movimentação.
- Data e hora do evento.
- Responsável pelo registro.
- Observações.

A API também disponibiliza consulta do histórico de movimentações por notebook, ordenado da mais recente para a mais antiga.

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
- Uma movimentação está vinculada a um notebook e pode estar associada a um onboarding.

## Endpoints

A API disponibiliza endpoints REST para gerenciamento de notebooks, desenvolvedores, processos de onboarding e movimentações dos equipamentos.

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
| GET | `/api/onboardings/{id}` | Busca onboarding por identificador | `200 OK` |
| POST | `/api/onboardings` | Cria um onboarding para um desenvolvedor utilizando um kit | `201 Created` |
| PUT | `/api/onboardings/{id}/confirmar-entrega` | Confirma a entrega dos equipamentos | `200 OK` |
| PUT | `/api/onboardings/{id}/confirmar-devolucao` | Confirma a devolução dos equipamentos | `200 OK` |

O endpoint de criação retorna o recurso criado no corpo da resposta e informa sua localização no cabeçalho `Location`.

### API de Movimentações

**Endpoint-base:** `/api/notebooks/{notebookId}`

| Método | Endpoint | Descrição | Resposta de sucesso |
|---|---|---|---|
| POST | `/api/notebooks/{notebookId}/ocorrencias` | Registra uma ocorrência relacionada ao notebook | `201 Created` |
| GET | `/api/notebooks/{notebookId}/movimentacoes` | Consulta o histórico de movimentações do notebook | `200 OK` |

## Exemplos de requisições

### Criar onboarding

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

### Confirmar entrega

`PUT /api/onboardings/2/confirmar-entrega`

```json
{
  "responsavel": "Luciano Rodrigues",
  "observacao": "Entrega do kit realizada e equipamentos conferidos."
}
```

Exemplo de resposta:

```json
{
  "id": 2,
  "status": "EM_ANDAMENTO",
  "dataEntrega": "2026-11-10T17:54:01",
  "responsavelEntrega": "Luciano Rodrigues"
}
```

A confirmação da entrega também registra automaticamente uma movimentação do tipo `ENTREGA`, vinculada ao notebook e ao onboarding correspondente.

Uma nova tentativa de confirmar a mesma entrega é impedida pela regra de negócio, retornando `409 Conflict`.

### Confirmar devolução

`PUT /api/onboardings/3/confirmar-devolucao`

O processo de devolução registra o responsável, a data da operação e a situação individual dos equipamentos.

As regras de atualização do notebook são:

| Situação do item | Status resultante do notebook |
|---|---|
| `BOM_ESTADO` | `DISPONIVEL` |
| `DANIFICADO` | `MANUTENCAO` |
| `NAO_APRESENTADO` | Permanece `EM_USO` |

A devolução física dos equipamentos também gera movimentações do tipo `DEVOLUCAO`. Para itens não apresentados, não é registrada movimentação de devolução.

### Registrar ocorrência

`POST /api/notebooks/13/ocorrencias`

```json
{
  "responsavel": "Luciano Rodrigues",
  "observacao": "Notebook apresentou falha na inicialização do sistema operacional."
}
```

Exemplo de resposta:

```json
{
  "id": 8,
  "notebookId": 13,
  "tipo": "OCORRENCIA",
  "dataHora": "2026-11-14T12:37:13.241615",
  "responsavel": "Luciano Rodrigues",
  "observacao": "Notebook apresentou falha na inicialização do sistema operacional."
}
```

O registro de ocorrência não exige vínculo com um onboarding e não altera automaticamente o status do notebook.

O responsável e a descrição da ocorrência são obrigatórios.

### Consultar histórico de movimentações

`GET /api/notebooks/13/movimentacoes`

Exemplo de resposta:

```json
[
  {
    "id": 8,
    "notebookId": 13,
    "onboardId": null,
    "tipo": "OCORRENCIA",
    "dataHora": "2026-11-14T12:37:13.241615",
    "responsavel": "Luciano Rodrigues",
    "observacao": "Notebook apresentou falha na inicialização do sistema operacional."
  },
  {
    "id": 5,
    "notebookId": 13,
    "onboardId": 4,
    "tipo": "ENTREGA",
    "dataHora": "2026-11-12T15:38:07.151293",
    "responsavel": "Luciano Rodrigues",
    "observacao": "Entrega do kit de teste para validar devolução de notebook não apresentado."
  }
]
```

O histórico apresenta as movimentações em ordem decrescente de data e hora.

Quando o notebook existe, mas ainda não possui movimentações, a API retorna uma lista vazia (`[]`). Para um notebook inexistente, retorna `404 Not Found`.

## Tratamento de erros

A aplicação possui tratamento global de exceções para padronizar erros e respostas da API.

| Código HTTP | Significado |
|---|---|
| `400 Bad Request` | Requisição malformada ou parâmetro inválido |
| `404 Not Found` | Recurso não encontrado |
|