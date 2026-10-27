# DevStock — API de Gestão de Ativos de TI

API REST desenvolvida com Java e Spring Boot para gerenciar ativos de TI e apoiar o processo de onboarding de desenvolvedores.

O projeto tem como objetivo aplicar conceitos de desenvolvimento backend em um cenário próximo ao ambiente corporativo, incluindo persistência de dados, organização em camadas, regras de negócio e tratamento de erros.

## Objetivo do projeto

Facilitar o controle de notebooks e equipamentos disponibilizados aos desenvolvedores, preparando a aplicação para gerenciar kits de equipamentos, entregas, devoluções e histórico de movimentações.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Data JPA
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

O projeto está em desenvolvimento incremental. As funcionalidades descritas nesta seção devem corresponder ao código e aos testes disponíveis na versão publicada.

## Arquitetura

A aplicação utiliza uma organização em camadas para separar as responsabilidades:

| Camada | Responsabilidade |
|---|---|
| Controller | Receber requisições HTTP e retornar respostas da API. |
| Service | Centralizar as regras de negócio. |
| Repository | Realizar operações de persistência por meio do Spring Data JPA. |
| Entity | Representar as entidades persistidas no banco de dados. |
| Exception | Organizar exceções específicas e o tratamento de erros. |

Essa separação facilita a manutenção, a evolução das funcionalidades e a realização de testes.

## Banco de dados

O PostgreSQL é utilizado para armazenar as informações da aplicação.

O modelo de dados foi planejado para contemplar entidades relacionadas à gestão de ativos de TI, incluindo:

- Desenvolvedores.
- Notebooks.
- Kits e itens de equipamentos.
- Registros de onboarding.
- Movimentações de ativos.

A implementação dessas entidades e seus relacionamentos ocorre de forma incremental.

## Endpoints

Os endpoints de notebooks estão organizados sob o prefixo `/api/notebooks`.

| Método HTTP | Endpoint | Operação |
|---|---|---|
| GET | `/api/notebooks` | Listar notebooks |
| GET | `/api/notebooks/{id}` | Consultar notebook por ID |
| POST | `/api/notebooks` | Cadastrar notebook |
| PUT | `/api/notebooks/{id}` | Atualizar notebook |
| DELETE | `/api/notebooks/{id}` | Excluir notebook |

Os códigos HTTP e os formatos de resposta devem ser conferidos conforme a implementação atual da API.

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

- [ ] Finalizar a documentação dos endpoints e exemplos de requisições.
- [ ] Revisar e ampliar o tratamento de erros.
- [ ] Criar testes automatizados.
- [ ] Implementar o gerenciamento de kits e itens.
- [ ] Implementar o fluxo de onboarding.
- [ ] Registrar o histórico de movimentações dos equipamentos.
- [ ] Avaliar a utilização de DTOs e validação de entrada.
- [ ] Documentar a API com OpenAPI/Swagger.
- [ ] Preparar a execução com Docker.

## Status

**Em desenvolvimento.**

O DevStock é um projeto prático de aprendizado e portfólio, com evolução incremental das funcionalidades e aplicação de conceitos de desenvolvimento backend com Java.

## Autor

Desenvolvido por Luciano Rodrigues.

- GitHub: [larsilvapx](https://github.com/larsilvapx)
- Repositório: [DevStock](https://github.com/larsilvapx/devstock)
