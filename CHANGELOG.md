# 📋 Changelog

Todas as mudanças importantes do projeto estão documentadas aqui, seguindo o padrão [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

---

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

### Tecnologias utilizadas
- Jakarta Bean Validation
- Spring Validation + ControllerAdvice
- Spring MockMvc
- JUnit 5
- Mockito
- Instancio

### Arquitetura
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

### Tecnologias utilizadas
- Java 21
- Spring Boot 3.4.8
- Maven
- OpenFeign
- Docker + Docker Compose
- WireMock
- Insomnia

### Arquitetura

> CountryController --> CountryServiceImpl --> CountryClient (Feign) --> REST Countries ou WireMock

- **`CountryController`**: expõe `/countries/{name}`.
- **`CountryServiceImpl`**: aplica regra e transforma a resposta.
- **`CountryClient`**: usa Feign para consumir a API REST Countries.
- **`WireMock`**: simula respostas REST Countries durante testes locais.

---