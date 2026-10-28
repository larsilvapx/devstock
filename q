[1mdiff --git a/README.md b/README.md[m
[1mindex 7dab15a..f280bb3 100644[m
[1m--- a/README.md[m
[1m+++ b/README.md[m
[36m@@ -63,17 +63,83 @@[m [mA implementação dessas entidades e seus relacionamentos ocorre de forma increm[m
 [m
 ## Endpoints[m
 [m
[31m-Os endpoints de notebooks estão organizados sob o prefixo `/api/notebooks`.[m
[32m+[m[32mO DevStock disponibiliza endpoints REST para gerenciamento de notebooks e desenvolvedores.[m
[32m+[m
[32m+[m[32m### API de Notebooks[m
[32m+[m
[32m+[m[32m**Endpoint-base:** `/api/notebooks`[m
[32m+[m
[32m+[m[32m| Método | Endpoint | Descrição | Resposta de sucesso |[m
[32m+[m[32m|---|---|---|---|[m
[32m+[m[32m| GET | `/api/notebooks` | Lista todos os notebooks | `200 OK` |[m
[32m+[m[32m| GET | `/api/notebooks/{id}` | Busca notebook pelo ID | `200 OK` |[m
[32m+[m[32m| POST | `/api/notebooks` | Cadastra um notebook | `201 Created` |[m
[32m+[m[32m| PUT | `/api/notebooks/{id}` | Atualiza um notebook | `200 OK` |[m
[32m+[m[32m| DELETE | `/api/notebooks/{id}` | Exclui um notebook | `204 No Content` |[m
[32m+[m
[32m+[m[32m#### Exemplo de cadastro[m
[32m+[m
[32m+[m[32m**Requisição:** `POST /api/notebooks`[m
[32m+[m
[32m+[m[32m```json[m
[32m+[m[32m{[m
[32m+[m[32m  "patrimonio": "PAT-006",[m
[32m+[m[32m  "serialNumber": "SN-DEVSTOCK-006",[m
[32m+[m[32m  "marca": "Dell",[m
[32m+[m[32m  "modelo": "Latitude 5440",[m
[32m+[m[32m  "processador": "Intel Core i5",[m
[32m+[m[32m  "memoriaRam": "16 GB",[m
[32m+[m[32m  "armazenamento": "512 GB SSD",[m
[32m+[m[32m  "sistemaOperacional": "Windows 11",[m
[32m+[m[32m  "status": "DISPONIVEL",[m
[32m+[m[32m  "dataUltimaComunicacao": "2026-10-27",[m
[32m+[m[32m  "dataAquisicao": "2026-10-28"[m
[32m+[m[32m}[m
[32m+[m[32m```[m
[32m+[m
[32m+[m[32mOs valores são ilustrativos. Utilize um número de série único e um status compatível com as regras da aplicação.[m
[32m+[m
[32m+[m[32mO cadastro retorna `201 Created` e informa a localização do recurso no cabeçalho `Location`.[m
[32m+[m
[32m+[m[32m### API de Desenvolvedores[m
[32m+[m
[32m+[m[32m**Endpoint-base:** `/api/desenvolvedores`[m
[32m+[m
[32m+[m[32m| Método | Endpoint | Descrição | Resposta de sucesso |[m
[32m+[m[32m|---|---|---|---|[m
[32m+[m[32m| GET | `/api/desenvolvedores` | Lista todos os desenvolvedores | `200 OK` |[m
[32m+[m[32m| GET | `/api/desenvolvedores/{id}` | Busca desenvolvedor pelo ID | `200 OK` |[m
[32m+[m[32m| POST | `/api/desenvolvedores` | Cadastra um desenvolvedor | `200 OK` |[m
[32m+[m
[32m+[m[32m#### Exemplo de cadastro[m
 [m
[31m-| Método HTTP | Endpoint | Operação |[m
[31m-|---|---|---|[m
[31m-| GET | `/api/notebooks` | Listar notebooks |[m
[31m-| GET | `/api/notebooks/{id}` | Consultar notebook por ID |[m
[31m-| POST | `/api/notebooks` | Cadastrar notebook |[m
[31m-| PUT | `/api/notebooks/{id}` | Atualizar notebook |[m
[31m-| DELETE | `/api/notebooks/{id}` | Excluir notebook |[m
[32m+[m[32m**Requisição:** `POST /api/desenvolvedores`[m
[32m+[m
[32m+[m[32m```json[m
[32m+[m[32m{[m
[32m+[m[32m  "nome": "João da Silva",[m
[32m+[m[32m  "email": "joao.silva@example.com",[m
[32m+[m[32m  "matricula": "DEV006",[m
[32m+[m[32m  "cargo": "Desenvolvedor Backend",[m
[32m+[m[32m  "dataAdmissao": "2026-10-28",[m
[32m+[m[32m  "ativo": true[m
[32m+[m[32m}[m
[32m+[m[32m```[m
[32m+[m
[32m+[m[32mO e-mail e a matrícula devem ser únicos. O cadastro retorna o objeto criado no corpo da resposta.[m
[32m+[m
[32m+[m[32m### Códigos de erro[m
[32m+[m
[32m+[m[32mA aplicação possui tratamento global de exceções para padronizar erros.[m
[32m+[m
[32m+[m[32m| Código HTTP | Significado |[m
[32m+[m[32m|---|---|[m
[32m+[m[32m| `400 Bad Request` | Requisição malformada ou parâmetro inválido |[m
[32m+[m[32m| `404 Not Found` | Recurso não encontrado |[m
[32m+[m[32m| `409 Conflict` | Conflito de cadastro ou violação de integridade |[m
[32m+[m[32m| `500 Internal Server Error` | Erro inesperado no servidor |[m
 [m
[31m-Os códigos HTTP e os formatos de resposta devem ser conferidos conforme a implementação atual da API.[m
[32m+[m[32mA ocorrência de cada erro depende das regras de negócio, das exceções lançadas e das restrições do banco de dados.[m
 [m
 ## Configuração do ambiente[m
 [m
