# Encurtador de URL

## Como executar

Com o docker-compose instalado basta executar:

```
docker-compose build --no-cache
docker-compose up -d
```

Caso contrário, será necesário uma instância local do MySQL.
As configurações devem ser mudadas no arquivo `application.properties`

## Endpoints
As definições dos endpoints podem ser checkadas no [swagger](http://localhost:8080/swagger-ui/index.html) com o serviço em execução.


## Algumas observações

O padrão [REST](https://www.geeksforgeeks.org/rest-api-architectural-constraints/) não foi seguido a risca por 
se tratar de um encurtador de URL. A ideia é fazer com que a url final seja pequena,
e não faria sentido usar o padrão na rota de encurtada.

Os testes rodam num banco da dados em memória para simplificar a execução. Outra vantagem é possibilitar a implementação
de unit tests.

