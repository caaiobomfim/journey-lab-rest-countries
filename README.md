# 🌍 journey-lab-rest-countries

Projeto experimental focado em práticas de DevOps e SRE, utilizando a [REST Countries API](https://restcountries.com/) como base de dados pública.

Este repositório faz parte da minha jornada de migração para áreas de **SRE** e **Cloud/DevOps**, com foco em boas práticas de observabilidade, resiliência, infraestrutura como código, automação e escalabilidade.

---

## 🚀 Release 0.1 - Integração inicial com REST Countries

### Objetivo
Criar uma API backend simples que consome a REST Countries e expõe dados essenciais de um país via HTTP GET.

### Funcionalidade
- `GET /countries/{name}`: retorna nome, capital, região, população e bandeira de um país.
- Consome dados da REST Countries API pública.
- Retorna resposta padronizada em JSON.

### Tecnologias utilizadas
- Java 21
- Spring Boot 3.x
- Maven

---

## 📦 Como executar localmente

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/journey-lab-rest-countries.git
cd journey-lab-rest-countries

# Compile e execute
./mvnw spring-boot:run
```

---

## 📅 Planejamento de Releases

| Release   | Objetivo                                         | Status           |
|-----------|--------------------------------------------------|------------------|
| **0.1**   | API funcional com consumo da REST Countries      | 🛠️ Em Andamento |
| **0.2**   | Cache com Caffeine                               | ⏳ Planejada      |
| **0.3**   | Observabilidade com Micrometer + Prometheus      | ⏳ Planejada      |
| **0.4**   | Testes + mock da API externa                     | ⏳ Planejada      |
| **0.5**   | Dockerização                                     | ⏳ Planejada      |
| **0.6**   | Deploy com Terraform em AWS ECS                  | ⏳ Planejada      |
| **0.7**   | API Gateway + autenticação básica                | ⏳ Planejada      |
| **0.8**   | Alertas e dashboards com Grafana/Datadog         | ⏳ Planejada      |
| **0.9**   | Resiliência: Retry, Timeout, Circuit Breaker     | ⏳ Planejada      |
| **1.0**   | Publicação no GitHub + LinkedIn                  | ⏳ Planejada      |

---

## 📘 Referências
- https://restcountries.com/
- https://start.spring.io/
- https://micrometer.io/
- https://docs.spring.io/spring-boot/docs/current/reference/html/

---

## 📌 Autor
**Caio Bomfim** – Projeto pessoal de transição para SRE/DevOps
