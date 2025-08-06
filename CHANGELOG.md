# 📋 Changelog

Todas as mudanças importantes do projeto estão documentadas aqui, seguindo o padrão [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

---

### [0.4.0] - 2025-08-05
## 🚀 Testes de Integração com WireMock + Testcontainers
### 📌 Visão Geral
Esta versão marca um avanço significativo nos testes de integração do projeto, com foco em **resiliência de chamadas externas**, **mapeamento dinâmico via WireMock** e **validação de comportamento real da aplicação usando Testcontainers**. Também foi iniciado o tratamento mais robusto de cenários de erro e configurações de headers e logging para clientes Feign.

### ✅ Funcionalidades Implementadas
#### ✅ Implementação de testes de integração com Testcontainers + WireMock
- Criação da classe `CountryServiceIT`.
- Simulação de 5 países com mapeamentos estáticos (`brazil`, `canada`, `germany`, `japan`, `nauru`).
- Validação completa dos dados retornados (nome, capital, região, população, flag).
#### ⚠️ Cenário de exceção implementado
- Simulação de país inexistente via WireMock para retorno `404`.
- Validação do lançamento de `CountryNotFoundException`.
#### 🎯 Inclusão de headers obrigatórios nas chamadas externas
- Adição do header `Accept: application/json` via `RequestInterceptor`.
- Criação da classe `CountryFeignConfig` com beans de interceptação e logging (`Logger.Level.FULL`).
#### 🔗 Configurações específicas de logging para Feign
- Adição de nível de log `DEBUG` para Feign e o client específico no `application.yml`.
#### 🏷️ Organização e anotação dos testes
- Marcação de testes com `@Tag("unit")` e `@Tag("integration")` para facilitar a execução segmentada.

### 🧠 Aprendizados
- Domínio do uso de **WireMock com mappings e delays customizados**.
- Configuração de **Testcontainers** para subir o WireMock isoladamente nos testes.
- Estratégias para **definir e sobrescrever URLs dinâmicas com** `@DynamicPropertySource`.
- Uso de `RequestInterceptor` para **padronizar headers em todas as chamadas Feign**.
- Estruturação de testes mais resilientes com `assertThrows`, `WireMock.verify` e uso de **cenários negativos controlados**.

### 🧰 Tecnologias Utilizadas
- **Spring Boot 3**
- **Feign Client** com configuração customizada
- **WireMock** via container Docker com mappings e arquivos JSON
- **Testcontainers** para testes de integração reprodutíveis
- **JUnit 5** com `@SpringBootTest`, `@Tag`, `assertThrows`, entre outros
- **SLF4J / Feign Logging** para inspeção detalhada das chamadas
- **Docker** e estrutura local de `tools/wiremock`

### [0.3.0] - 2025-08-03
## 🚀 GitHub Actions, SonarCloud, Proteções de Branch e Automação de PRs
### 📌 Visão Geral
Esta release marca um avanço importante no ciclo de desenvolvimento do projeto, com foco em automações via GitHub Actions, melhorias de qualidade de código com SonarCloud, controle de branches com GitFlow e validações automáticas de cobertura. Agora, o projeto passa a ter um pipeline de CI mais robusto, facilitando contribuições, garantindo qualidade de código e segurança nos merges.

### ✅ Funcionalidades implementadas

#### 📁 Estrutura e Processo Git
1. **Criação da branch** `develop` com base no modelo GitFlow para isolar o desenvolvimento contínuo e manter a `main` com código de produção.
2. **Configuração de proteções nas branches** `main` e `develop`:
- Requer Pull Request antes de merge.
- Requer status checks passados.
- Requer atualização com a base antes de merge.
- Bloqueio para administradores (desativada a permissão de bypass).
3. **Desabilitação da obrigatoriedade de aprovação de PRs**, permitindo merges mais ágeis durante o desenvolvimento.

#### ⚙️ CI/CD com GitHub Actions
4. **Criação do arquivo** `ci-feature-develop.yml` com três jobs:
- `Build`: compila o projeto com Maven (`mvn clean install`).
- `Test`: executa testes unitários (`mvn test`).
- `Open Pull Request`: cria automaticamente um PR da branch atual para a `develop`, utilizando `gh pr create`.
5. **Autenticação via GitHub CLI (`gh`) com Personal Access Token (PAT)** armazenado em `GH_TOKEN`.
6. **Criação de PR automática da `develop` para `main`** após atualizações na `develop`, com:
- Verificação de cobertura de testes via **JaCoCo**.
- Bloqueio se cobertura estiver abaixo de **90%**.
7. **Tratativa para evitar erro em caso de PR já existente** (`|| echo "PR já existente ou sem alterações."`).

#### 📈 Integração com SonarCloud
8. **Criação da conta no SonarCloud**, geração de token (`SONAR_TOKEN`) e integração com GitHub.
9. **Criação do arquivo** `sonar.yml` para rodar o SonarCloud a partir do CI.
10. **Configuração do** `sonar-project.properties` com os parâmetros do projeto.
11. **Inclusão do plugin** `jacoco-maven-plugin` no `pom.xml` para geração do relatório de cobertura.
12. **Desativação da análise automática do SonarCloud** para evitar conflito com CI-based analysis.
13. **Inclusão de parâmetros explícitos para SonarCloud no job de análise**:

```bash
-Dsonar.projectKey=caaiobomfim_journey-lab-rest-countries
-Dsonar.organization=caaiobomfim
-Dsonar.host.url=https://sonarcloud.io
-Dsonar.login=${{ secrets.SONAR_TOKEN }}
```

### 🧠 Aprendizados
- ✅ Como estruturar pipelines com GitHub Actions e tokens seguros.
- ✅ Integração real com SonarCloud e uso de análise baseada em CI.
- ✅ Como aplicar proteções robustas de branch no GitHub.
- ✅ Uso do `gh pr create` para automação de Pull Requests.
- ✅ Validação de cobertura mínima com `xmllint` em arquivos JaCoCo XML.
- ✅ Controle de qualidade com ferramentas open-source mesmo sem plano pago.

### 🧰 Tecnologias Utilizadas
- Java 21
- Maven
- GitHub Actions
- GitHub CLI (gh)
- SonarCloud
- JaCoCo
- XMLLint (libxml2-utils)

### [0.2.0] - 2025-08-02
## 🚀 Robustez e confiança: testes unitários e tratamento de exceções
### 📌 Visão Geral
Essa entrega foi focada em garantir **qualidade**, **segurança** e **previsibilidade** das respostas da API, mesmo em cenários de erro. Foram implementadas validações com Jakarta, tratamento global de exceções seguindo o padrão **RFC 7807** (`application/problem+json`) e testes automatizados com foco em confiabilidade.

### ✅ Funcionalidades implementadas

#### 🔒 Validações com Jakarta Bean Validation no parâmetro name:
- Não nulo ou vazio.
- Tamanho entre 2 e 56 caracteres.
- Somente letras (regex).

#### 🛑 Tratamento global de exceções com @RestControllerAdvice, retornando erros formatados com:
- `type`, `title`, `status`, `detail`, `instance`, `timestamp`, `violations`.

#### 💥 Try/Catch no service para capturar:
- `FeignException.NotFound` → `CountryNotFoundException`.
- Outras `FeignException` → `RuntimeException`.

#### 🧪 Testes automatizados:
- `CountryControllerTest`.
- `RestExceptionHandlerTest`.
- `CountryServiceImplTest`.
- Cobertura de cenários de sucesso, erro genérico, validação e país não encontrado.

#### 🧱 Outros aprimoramentos:
- Classe `ValidationMessages` com mensagens e expressões regulares centralizadas.
- Uso de `Instancio` para facilitar a criação de mocks em testes unitários.
- Inclusão de campo `rejectedValue` em violações de validação.
- Isolamento de exceção customizada `CountryNotFoundException`.

### 🧠 Aprendizados
- ✅ Prática de validação avançada com Bean Validation (Jakarta).
- ✅ Implementação do padrão **Problem Details (RFC 7807)** para respostas de erro.
- ✅ Boas práticas em `@ControllerAdvice` e modelagem de erros.
- ✅ Geração de cenários robustos de teste com **Mockito**, **JUnit 5**, **MockMvc** e **Instancio**.

### 🧰 Tecnologias utilizadas
- Jakarta Bean Validation
- Spring Validation + ControllerAdvice
- Spring MockMvc
- JUnit 5
- Mockito
- Instancio

### 🏗️ Arquitetura
> CountryController → CountryServiceImpl → CountryClient (Feign) → REST Countries
> ⤷ @RestControllerAdvice para tratamento global de exceções
> ⤷ Validações são aplicadas no @PathVariable name com mensagens customizadas

---

### [0.1.0] - 2025-08-02
## 🚀 Integração inicial com REST Countries
### 📌 Visão Geral
Este projeto expõe um endpoint REST que recebe o nome de um país e retorna suas informações principais (nome, capital, região, população, e URL da bandeira), consumindo a **API REST Countries v3.1**. Para facilitar os testes locais, o projeto utiliza o **WireMock** para simular chamadas HTTP.

### ✅ Funcionalidades implementadas

- Criação do endpoint REST `GET /countries/{name}` para retornar dados do país informado.
- Integração com a **REST Countries API v3.1** usando **Feign Client**.
- Implementação da camada de serviço (`CountryServiceImpl`) com regra para capital "N/A" quando ausente.
- Criação de estrutura baseada em boas práticas de engenharia: `Controller`, `Service`, `Client` e `DTOs`.
- Utilização de **Java Records** para modelar as respostas de forma imutável e concisa.
- Simulação de respostas da API externa com **WireMock** para testes locais.
- Configuração via `application.yml` com suporte a variáveis de ambiente.
- Dockerização da aplicação com **Dockerfile** e orquestração local via **Docker Compose**.

### 🧠 Aprendizados

Nesta primeira entrega, aprofundei meus conhecimentos em:

- ✅ Uso de **Feign Client** para consumir APIs REST de forma desacoplada e elegante.
- ✅ Criação de mocks de APIs com **WireMock**, simulando endpoints externos para testes locais.
- ✅ Organização de estrutura com **DTOs usando Java Records**, facilitando legibilidade e imutabilidade.
- ✅ Separação por camadas (Controller, Service, Port, Client) seguindo boas práticas de engenharia.
- ✅ Configuração de variáveis de ambiente via `application.yml` e `docker-compose.yml`, usando placeholders com `${}`.
- ✅ Construção de imagem customizada com **Dockerfile** e orquestração com **Docker Compose**, permitindo simulação do ambiente completo localmente.
- ✅ Entendimento da importância de injeção de dependências e isolamento de responsabilidades com interfaces (`CountryServicePort`).

### 🧰 Tecnologias utilizadas
- Java 21
- Spring Boot 3.4.8
- Maven
- OpenFeign
- Docker + Docker Compose
- WireMock
- Insomnia

### 🏗️ Arquitetura

> CountryController --> CountryServiceImpl --> CountryClient (Feign) --> REST Countries ou WireMock

- **`CountryController`**: expõe `/countries/{name}`.
- **`CountryServiceImpl`**: aplica regra e transforma a resposta.
- **`CountryClient`**: usa Feign para consumir a API REST Countries.
- **`WireMock`**: simula respostas REST Countries durante testes locais.

---