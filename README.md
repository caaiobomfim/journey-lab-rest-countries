# 🌍 Journey Lab REST Countries

Projeto experimental focado em práticas de DevOps e SRE, utilizando a [REST Countries API](https://restcountries.com/) como base de dados pública.

> Este repositório faz parte da minha jornada de migração para áreas de **SRE** e **Cloud/DevOps**, com foco em boas práticas de observabilidade, resiliência, infraestrutura como código, automação e escalabilidade.

## 📅 Planejamento de Releases

| Release                     | Objetivo                                                        | Status         |
|-----------------------------|-----------------------------------------------------------------|----------------|
| [**0.1.0**](#release-0-1-0) | API funcional com consumo da REST Countries                     | ✅ Concluída    |
| [**0.2.0**](#release-0-2-0) | Robustez e confiança: testes unitários e tratamento de exceções | ✅ Concluída    |
| **0.3.0**                   | Observabilidade com Micrometer + Prometheus                     | ⏳ Planejada    |
| **0.4.0**                   | Testes + mock da API externa                                    | ⏳ Planejada    |
| **0.5.0**                   | Dockerização                                                    | ⏳ Planejada    |
| **0.6.0**                   | Deploy com Terraform em AWS ECS                                 | ⏳ Planejada    |
| **0.7.0**                   | API Gateway + autenticação básica                               | ⏳ Planejada    |
| **0.8.0**                   | Alertas e dashboards com Grafana/Datadog                        | ⏳ Planejada    |
| **0.9.0**                   | Resiliência: Retry, Timeout, Circuit Breaker                    | ⏳ Planejada    |
| **1.0.0**                   | Publicação no GitHub + LinkedIn                                 | ⏳ Planejada    |

---

<a id="release-0-1-0"></a>
## 🚀 Release 0.1.0 - Integração inicial com REST Countries

### 📌 Visão Geral
Este projeto expõe um endpoint REST que recebe o nome de um país e retorna suas informações principais (nome, capital, região, população, e URL da bandeira), consumindo a API REST Countries v3.1. Para facilitar os testes locais, o projeto utiliza o WireMock para simular chamadas HTTP.

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

<a id="release-0-2-0"></a>
## 🚀 Release 0.2.0 - Robustez e confiança: testes unitários e tratamento de exceções

### 📌 Visão Geral
Essa entrega foi focada em garantir **qualidade**, **segurança** e **previsibilidade** das respostas da API, mesmo em cenários de erro. Foram implementadas validações com Jakarta, tratamento global de exceções seguindo o padrão **RFC 7807** (`application/problem+json`) e testes automatizados com foco em confiabilidade.

### ✅ Funcionalidades implementadas

#### 🔒 Validações com Jakarta Bean Validation no parâmetro name:
- Não nulo ou vazio
- Tamanho entre 2 e 56 caracteres
- Somente letras (regex)

#### 🛑 Tratamento global de exceções com @RestControllerAdvice, retornando erros formatados com:
- `type`, `title`, `status`, `detail`, `instance`, `timestamp`, `violations`

#### 💥 Try/Catch no service para capturar:
- `FeignException.NotFound` → `CountryNotFoundException`
- Outras `FeignException` → `RuntimeException`

#### 🧪 Testes automatizados:
- `CountryControllerTest`
- `RestExceptionHandlerTest`
- `CountryServiceImplTest`
- Cobertura de cenários de sucesso, erro genérico, validação e país não encontrado

#### 🧱 Outros aprimoramentos:
- Classe `ValidationMessages` com mensagens e expressões regulares centralizadas
- Uso de `Instancio` para facilitar a criação de mocks em testes unitários
- Inclusão de campo `rejectedValue` em violações de validação
- Isolamento de exceção customizada `CountryNotFoundException`

### 🧠 Aprendizados
- ✅ Prática de validação avançada com Bean Validation (Jakarta)
- ✅ Implementação do padrão **Problem Details (RFC 7807)** para respostas de erro
- ✅ Boas práticas em `@ControllerAdvice` e modelagem de erros
- ✅ Geração de cenários robustos de teste com **Mockito**, **JUnit 5**, **MockMvc** e **Instancio**

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

## 📦 Executando com Docker Compose

### 1. API com WireMock (mock local)

```bash
docker compose up --build
```

WireMock estará disponível na porta `10104`, e a API principal na porta `8080`.

### 2. API real (REST Countries)

Edite o docker-compose.yml e altere a URL:

```bash
environment:
#      - COUNTRY_API_URL=http://wiremock:8080/v3.1
      - COUNTRY_API_URL=https://restcountries.com/v3.1
```

## 🧪 Testes

Você pode testar usando Insomnia ou Postman:

```bash
GET http://localhost:8080/countries/brazil
```

Resposta esperada:

```bash
{
  "name": "Brazil",
  "capital": "Brasilia",
  "region": "Americas",
  "population": 212559409,
  "flagUrl": "https://flagcdn.com/w320/br.png"
}
```

---

## 📘 Referências
- https://restcountries.com/
- https://start.spring.io/
- https://micrometer.io/
- https://docs.spring.io/spring-boot/docs/current/reference/html/

---

## 📌 Autor
**Caio Bomfim** – Projeto pessoal de transição para SRE/DevOps
