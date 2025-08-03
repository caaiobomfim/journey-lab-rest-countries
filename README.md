# 🌍 Journey Lab REST Countries

Projeto experimental focado em práticas de DevOps e SRE, utilizando a [REST Countries API](https://restcountries.com/) como base de dados pública.

> Este repositório faz parte da minha jornada de migração para áreas de **SRE** e **Cloud/DevOps**, com foco em boas práticas de observabilidade, resiliência, infraestrutura como código, automação e escalabilidade.

## 📅 Planejamento de Releases

| Release                       | Objetivo                                                              | Status         |
|-------------------------------|-----------------------------------------------------------------------|----------------|
| [**0.1.0**](CHANGELOG.md#010) | API funcional com consumo da REST Countries                           | ✅ Concluída    |
| [**0.2.0**](CHANGELOG.md#020) | Robustez e confiança: testes unitários e tratamento de exceções       | ✅ Concluída    |
| [**0.3.0**](CHANGELOG.md#030) | Automação de CI/CD, controle de qualidade e integração com SonarCloud | ⏳ Planejada    |
| **0.4.0**                     | Testes + mock da API externa                                          | ⏳ Planejada    |
| **0.5.0**                     | Dockerização                                                          | ⏳ Planejada    |
| **0.6.0**                     | Deploy com Terraform em AWS ECS                                       | ⏳ Planejada    |
| **0.7.0**                     | API Gateway + autenticação básica                                     | ⏳ Planejada    |
| **0.8.0**                     | Alertas e dashboards com Grafana/Datadog                              | ⏳ Planejada    |
| **0.9.0**                     | Resiliência: Retry, Timeout, Circuit Breaker                          | ⏳ Planejada    |
| **1.0.0**                     | Publicação no GitHub + LinkedIn                                       | ⏳ Planejada    |

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

Você pode testar usando **Insomnia** ou **Postman**:

#### ✅ Requisição válida

```bash
GET http://localhost:8080/countries/brazil
```

Resposta esperada (HTTP 200):

```bash
{
  "name": "Brazil",
  "capital": "Brasilia",
  "region": "Americas",
  "population": 212559409,
  "flagUrl": "https://flagcdn.com/w320/br.png"
}
```

#### ❌ Requisição inválida (validação)

```bash
GET http://localhost:8080/countries/c
```

Resposta esperada (HTTP 400 - application/problem+json):

```bash
{
  "type": "https://journey.dev/problems/validation-error",
  "title": "Validation error",
  "status": 400,
  "detail": "One or more fields are invalid.",
  "instance": "/countries/c",
  "timestamp": "2025-08-02T22:33:44.491Z",
  "violations": [
    {
      "field": "name",
      "message": "The country name must be between 2 and 56 characters.",
      "rejectedValue": "c"
    }
  ]
}
```

---

## 📘 Referências

#### 🧩 Integração de APIs e consumo HTTP
- [Spring Cloud OpenFeign Docs](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
- [REST Countries API](https://restcountries.com/)
- [WireMock Documentation](https://wiremock.org/docs/)

#### ⚖️ Validações com Jakarta Bean Validation
- [Jakarta Bean Validation 3.0 Spec (JSR 380)](https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html)
- [Hibernate Validator (Referência de implementação)](https://hibernate.org/validator/)

#### 🚨 Tratamento de erros e RFC 7807
- [RFC 7807 - Problem Details for HTTP APIs](https://datatracker.ietf.org/doc/html/rfc7807)
- [Spring Boot Exception Handling Best Practices](https://reflectoring.io/spring-boot-exception-handling/)

#### 🧪 Testes em APIs REST com Spring
- [Spring Boot Testing Docs](https://docs.spring.io/spring-boot/reference/testing/index.html#testing)
- [Mockito](https://site.mockito.org/)
- [Spring MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
- [Instancio – Java object generator for testing](https://www.instancio.org/)

#### 🧠 GitHub Actions e CI/CD
- [GitHub Actions Documentation](https://docs.github.com/actions)
- [Using `gh pr create`](https://cli.github.com/manual/gh_pr_create)
- [Configuração de tokens no GitHub](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
- [Como proteger branches no GitHub](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches)

#### 🔍 Qualidade de código e cobertura
- [JaCoCo – Java Code Coverage Library](https://www.jacoco.org/jacoco/)
- [SonarCloud Documentation](https://docs.sonarcloud.io/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [xmllint command line tool](http://xmlsoft.org/xmllint.html)

#### Extras opcionais (para contexto e futuras releases)
- [Micrometer – Metrics Collection for JVM-Based Apps](https://micrometer.io/)
- [Docker Compose Docs](https://docs.docker.com/compose/)
- [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)
- [OpenAPI Specification (para futura documentação de erros e endpoints)](https://spec.openapis.org/oas/latest.html)
- [Gitmoji – An emoji guide for your commit messages](https://gitmoji.dev/)

---

## 📌 Autor
**Caio Bomfim** – Projeto pessoal de transição para SRE/DevOps
