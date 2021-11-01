# Projeto Gerenciador de Movimentos de Extrato Bancario

Projeto responsável por disponibilizar os enpoints para gerar os eventos de transferencia, retirar e deposito de dinheiro em uma conta e também consultar o balanço (saldo). 

## Tecnologias utilizadas

* Linguagem Java - Versão 1.11 (OpenJDK 11.0.10)

```
openjdk version "11.0.10" 2021-01-19
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.10+9)
Eclipse OpenJ9 VM AdoptOpenJDK (build openj9-0.24.0, JRE 11 Mac OS X amd64-64-Bit Compressed References 20210120_897 (JIT enabled, AOT enabled)
OpenJ9   - 345e1b09e
OMR      - 741e94ea8
JCL      - 0a86953833 based on jdk-11.0.10+9)
```

* Maven 3 - Ferramenta de Build

```
Apache Maven 3.8.3 (ff8e977a158738155dc465c6a97ffaf31982d739)
Maven home: /Users/andferreira/.m2/wrapper/dists/apache-maven-3.8.3-bin/5a6n1u8or3307vo2u2jgmkhm0t/apache-maven-3.8.3
Java version: 11.0.10, vendor: AdoptOpenJDK, runtime: /Users/andferreira/.sdkman/candidates/java/11.0.10.j9-adpt
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
```

* Spring Boot - Framerwork Web para geração das API's (versão 2.X.X) com Tomcat 9

O repositório utilizado é o Github, onde utilizei o Git flow com a branch develop e main para gerenciar o fonte.

Na pasta postman tem um projeto que poderá importar para testar  as apis localmente.

Utilizei o framerk Junit (sufixo Test) para os teste unitários e o teste dos enpoints.

Observação: Para executar o ambiente produtivo, substitua o http://localhost:8080

* Docker - Para rodar a aplicação local

## Para realizar o build, teste unitários e iniciar o software localmente

####Sem Docker

Para realizar o build do projeto e realizar somente os testes unitários execute o comando:
(Comandos testados para ambiente Linux e Mac)

Gerar o pacote:

```shell script
./mvnw clean package
```

Para iniciar o software:

```shell script
./mvnw spring-boot:run
```

####Com Docker

Gerar o pacote:

```shell script
./mvnw clean package
```

Para iniciar o software:

```shell script
docker build -t moviment-api .

```

## Gestão do Projeto e técnicas para construção da API

Não precisei usar Kaban paraa administrar as atividades, tendo conhecimento do problema.

Mas as etapas foram:

* Passo 0: Criação do projeto no https://start.spring.io/
* Passo 1: Contrução das classes de dominio;
* Passo 2: Construção dos testes unitários;
* Passo 3: Construção da API e mecanismo para armazenar os dados;

Utilizei a prática TDD para desenvolver o design e testes da aplicação.

Nota: 

Eu tenho constume de sempre trabalhar com git flow, então gosto de criar PR para mostrar um pouco da minha prática do dia a dia.
Olhe a página https://github.com/ander-f-silva/movements-api/pulls?q=is%3Apr+is%3Aclosed.

Por fator de tempo eu não inclui a documentação do Swagger, mas é algo que tenho como prática inserir nos projetos.


