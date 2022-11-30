<br>
<h1 align="center">
Sistema de Doação Sanguínea (Back-end) 💉🩸
</h1>
<br>

## 💬 Sobre o repositório

O Sistema de Doação Sanguínea recebe uma massa de dados contendo informações de candidatos a doadores de sangue, processa todas as informações e devolve os seguintes dados:

• Quantos candidatos temos nessa lista em cada estado do Brasil? 

• IMC médio em cada faixa de idade de dez em dez anos: 0 a 10; 11 a 20; 21 a 30, etc.

• Qual o percentual de obesos entre os homens e entre as mulheres?

• Qual a média de idade para cada tipo sanguíneo? 

• A quantidade de possíveis doadores para cada tipo sanguíneo receptor

## ⚠ Pré-requisitos para execução do projeto

* Java 11 ou versões superiores
* Maven
* NodeJS
* NPM
* MySQL

## 📌 Como utilizar?

Para utilizar o Sistema de Doação Sanguínea, é necessário ter uma instância de conexão do banco de dados ativa (no caso MySQL), que por padrão fica na porta 3306, caso sua porta esteja diferente, especifique no application.properties em:

```
spring.datasource.url=jdbc:mysql://localhost:<PORTA_BD>/testewk-doacao-sangue?createDatabaseIfNotExist=true
spring.datasource.username=<USUARIO_BD>
spring.datasource.password=<SENHA_BD>
```

Com o banco de dados devidamente configurado, rode o [back-end da aplicação](https://github.com/wienerdev/sds) através do seguinte comando:

*Disponível em http://localhost:8080/

```
mvn spring-boot:run 
```

Após rodar o back-end com sucesso, abra o [front-end da aplicação](https://github.com/wienerdev/sds-angular) e instale os pacotes necessários, através dos seguinte comando:

```
npm install
```

Com os pacotes instalados, suba a aplicação com o seguinte comando:

*Disponível em http://localhost:4200/

```
ng serve
```

Com a aplicação rodando, acesse o localhost (porta 4200), e navegue pelo sistema!

## 🧠 Links importantes

* [Documentação oficial do Angular](https://angular.io/)
* [Site oficial do NodeJS](https://nodejs.org/en/)
* [Site oficial do NPM](https://www.npmjs.com/)
* [Referência para o padrão arquitetural REST](https://restfulapi.net/)
* [Palheta de atalhos de comandos do IntelliJ](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
* [Site oficial do Spring](https://spring.io/)
* [Site oficial do Spring Initialzr para setup do projeto](https://start.spring.io/)
* [SDKMan! para gerenciamento e instalação do Java e Maven](https://sdkman.io/)
* [Site oficial do MySQL](https://www.mysql.com/)

---
