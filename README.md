# DevStock — Gestão de Ativos de TI

Sistema backend para gerenciamento de notebooks corporativos e kits de onboarding, com foco no controle de estoque, movimentação de equipamentos e acompanhamento das entregas aos desenvolvedores.

O projeto está sendo desenvolvido como aplicação prática de backend, aplicando conceitos de Java, Spring Boot, persistência de dados e regras de negócio inspiradas em um cenário real de TI.

## Objetivos

- Controlar notebooks disponíveis, em preparação, em uso e obsoletos.
- Organizar kits de equipamentos para novos colaboradores.
- Registrar entregas e devoluções de equipamentos.
- Manter o histórico de movimentações dos notebooks.
- Identificar equipamentos sem comunicação há mais de 45 dias.
- Registrar pendências e observações relacionadas às devoluções.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Bean Validation

## Arquitetura planejada

A aplicação seguirá uma estrutura em camadas para separar as responsabilidades:

- **Controller:** recebe e responde às requisições HTTP.
- **Service:** concentra as regras de negócio.
- **Repository:** realiza o acesso aos dados.
- **Entity:** representa as entidades persistidas no banco de dados.

## Modelo de domínio

O banco de dados inicial contempla as seguintes tabelas:

- `desenvolvedor`
- `notebook`
- `kit`
- `kit_item`
- `onboarding`
- `movimentacao`

## Regras de negócio previstas

- O número de série de cada notebook deve ser único.
- Equipamentos em uso não podem ser entregues novamente sem regularização.
- Notebooks obsoletos não podem ser destinados a novos colaboradores.
- Equipamentos sem comunicação há mais de 45 dias devem gerar um alerta operacional.
- Entregas e devoluções devem manter um histórico de movimentações.
- Devoluções parciais ou com itens danificados devem permitir o registro de pendências.

## Status do projeto

**Em desenvolvimento.**

Etapas iniciais concluídas:
- Criação do banco de dados PostgreSQL.
- Criação das tabelas iniciais.
- Geração do projeto Spring Boot.

Próximas etapas:
- Configuração da conexão entre Spring Boot e PostgreSQL.
- Mapeamento das entidades com JPA.
- Implementação dos repositórios e serviços.
- Desenvolvimento dos endpoints REST.
- Validações, tratamento de erros e testes automatizados.
- Documentação da API.

## Como executar

Os passos de configuração da conexão com o PostgreSQL e execução da aplicação serão documentados conforme o desenvolvimento avançar.

## Objetivo de aprendizagem

Projeto desenvolvido para consolidar conhecimentos de Java e desenvolvimento backend, com aplicação de modelagem relacional, APIs REST, persistência de dados e regras de negócio.

## Autor

**Luciano Rodrigues**

- GitHub: [@larsilvapx](https://github.com/larsilvapx)
- LinkedIn: [linkedin.com/in/lucasards](https://linkedin.com/in/lucasards)