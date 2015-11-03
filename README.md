boot-angularjs
===============
Aplicação Java web demonstra como trabalhar com Java, MongoDB e AngularJS. O MongoDB é um banco de dados, NoSQL, orientado a Documentos. Aplicação bem simples, formada por uma página que permite que usuário realize consultas flexíveis fazendo composição com diferentes filtros e ordenação.

Nesse projeto utilizo o Spring Web, Spring Data, JQuery, Bootstrap e AngularJS (front-end). O build e ciclo de vida do projeto são gerenciados pelo Maven ou Gradle. Outra tecnologia incorporada é o Spring Boot, para facilitar a definição de dependências e para disponibilizar um web container (Tomcat) embutido para executar a aplicação. Durante a inicialização da aplicação o banco de dados é re-criado com dados default (veja o arquivo src/main/resources/data.json).

O AngularJS é uma sofisticada tecnologia para desenvolvimento do front-end de aplicações Web. Um framework com suporte a modularização, componentes, separação de camadas, injeção de dependências e binding com conteúdo estático. Saiba mais sobre o AngularJS em: https://angularjs.org/

Detalhes da implementação
-------
Tecnologias utilizadas na implementação:

* Driver MongoDB para Java: a camada de persistência utiliza a API disponível no driver do MongoDB para Java;
* Spring Data JPA: módulo de otimização de persistência do Spring;
* Spring MVC: framework MVC do back-end com a API REST;
* Bootstrap: framework css;
* JQuery: framework JavaScript;
* AngularJS: framework web p/ front-end;
* Spring Boot: template de dependências e web container embutido;

Pré-requisitos
-------
* JDK - versão 1.8 do Java, ou mais recente;
* Qualquer IDE Java com suporte ao Maven;
* Maven ou Gradle - para build e dependências.
* MongoDB versão 2.10.1 ou superior;

Após baixar os fontes, para executar a aplicação execute o comando (terminal / prompt):

* Maven: $ mvn clean package spring-boot:run
* Gradle: $ gradle bootRun
