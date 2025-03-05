# Desafio de Empréstimos

Este projeto foi desenvolvido para o desafio de implementação de um serviço de empréstimos. O objetivo é criar uma API que determina as modalidades de empréstimo disponíveis para um cliente com base nas suas variáveis de **idade**, **salário** e **localização**.

Link do Desafio: [backend-br/desafios](https://github.com/backend-br/desafios/blob/master/loans/PROBLEM.md)
---

## Rodando Localmente

Clone o projeto

```bash
git clone https://github.com/MiguelLopesDel/emprestimos-service-challenge.git
```

Entre no diretório do projeto

```bash
cd emprestimos-service-challenge
```

Compile o projeto com o Gradle

```bash
  ./gradlew build
```
---

## Documentação da API

A API possui um único endpoint para determinar os empréstimos que o cliente pode acessar.

**[POST]** `{{host}}/customer-loans`

### Exemplo de Requisição

```json
{
    "age": 26,
    "cpf": "275.484.389-23",
    "name": "Vuxaywua Zukiagou",
    "income": 7000.00,
    "location": "SP"
}
```

### Exemplo de Resposta

```json
{
    "customer": "Vuxaywua Zukiagou",
    "loans": [
        {
            "type": "CONSIGNMENT",
            "interest_rate": 2
        }
    ]
}
```
---

## Rodando os testes

Para rodar os testes, execute o seguinte comando

```bash
  ./gradlew test
```
