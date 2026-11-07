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
| Controller | Rece