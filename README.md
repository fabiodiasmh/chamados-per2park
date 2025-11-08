🧾 Chamados Per2Park

Sistema backend para gerenciamento de chamados e atendimentos técnicos da plataforma Per2Park.
Desenvolvido para centralizar solicitações, agilizar atendimentos e permitir integração com outros sistemas internos.

🚀 Tecnologias Utilizadas

Java 17+

Spring Boot 3+

Spring Data JPA

PostgreSQL

Maven

Lombok

Swagger (OpenAPI)

Docker

Oracle Cloud Infrastructure (OCI)

🏗️ Estrutura do Projeto
chamados-per2park/
├── src/
│   ├── main/
│   │   ├── java/com/per2park/chamados/   → Código-fonte principal
│   │   └── resources/                    → application.properties, logs, etc.
│   └── test/                             → Testes unitários
├── target/                               → Build gerado (.jar)
├── Dockerfile
├── pom.xml
└── README.md

⚙️ Como Executar Localmente
1️⃣ Clonar o projeto
git clone https://github.com/fabiodiasmh/chamados-per2park.git
cd chamados-per2park

2️⃣ Configurar o banco de dados (PostgreSQL)

Crie o banco:

CREATE DATABASE chamados_per2park;


Atualize o arquivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/chamados_per2park
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=8080

3️⃣ Executar o projeto com Maven
mvn spring-boot:run


Ou gerar e executar o .jar:

mvn clean package
java -jar target/chamados-per2park-0.0.1-SNAPSHOT.jar

🧩 Endpoints Principais da API
Método	Endpoint	Descrição
GET	/chamados	Lista todos os chamados
POST	/chamados	Cria um novo chamado
GET	/chamados/{id}	Busca um chamado pelo ID
PUT	/chamados/{id}	Atualiza um chamado
DELETE	/chamados/{id}	Remove um chamado

📘 Swagger UI:
👉 http://localhost:8080/swagger-ui.html

🧠 Objetivo do Projeto

Centralizar os chamados técnicos da infraestrutura Per2Park

Integrar dados de diferentes ambientes (showroom, pista, totem etc.)

Permitir rastreabilidade e histórico de cada atendimento

Gerar relatórios para análise de desempenho e SLA

🐳 Deploy com Docker
1️⃣ Criar a imagem Docker
docker build -t chamados-per2park:latest .

2️⃣ Rodar localmente
docker run -d -p 8080:8080 chamados-per2park

3️⃣ Enviar imagem para o OCI
docker login iad.ocir.io
docker tag chamados-per2park iad.ocir.io/<tenancy-namespace>/chamados-per2park:latest
docker push iad.ocir.io/<tenancy-namespace>/chamados-per2park:latest


💡 Depois, crie uma Container Instance no OCI usando a imagem enviada.

🧰 Variáveis de Ambiente (opcional)
Variável	Descrição	Exemplo
DB_HOST	Host do banco de dados	localhost
DB_PORT	Porta do PostgreSQL	5432
DB_USER	Usuário do banco	postgres
DB_PASS	Senha do banco	postgres
DB_NAME	Nome do banco	chamados_per2park
🧑‍💻 Autor

Fabio Dias
💼 Desenvolvedor de Sistemas
📍 Cotia - SP
🔗 GitHub: fabiodiasmh

🤝 Contribuição

Contribuições são bem-vindas! 🙌

Faça um fork do projeto

Crie uma branch da sua feature:

git checkout -b feature/nome-da-feature


Faça o commit:

git commit -m "Descrição da feature"


Envie para o seu fork e abra um Pull Request

🪪 Licença

Este projeto está sob a licença MIT.
Consulte o arquivo LICENSE
 para mais informações.
