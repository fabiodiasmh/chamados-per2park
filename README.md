# 🧾 Chamados Per2Park

Sistema **backend** para gerenciamento de chamados e atendimentos técnicos da plataforma **Per2Park**.  
Desenvolvido para centralizar solicitações, agilizar atendimentos e permitir integração com outros sistemas internos.

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Swagger (OpenAPI)**
- **Docker**
- **Oracle Cloud Infrastructure (OCI)**

---

## 🏗️ Estrutura do Projeto

Chamados-p2p-backend/
├── src/
│ ├── main/
│ │ ├── java/com/per2park/chamados/ → Código-fonte principal
│ │ └── resources/ → application.properties, logs, etc.
│ └── test/ → Testes unitários
├── target/ → Build gerado (JAR)
├── Dockerfile
├── pom.xml
└── README.md

yaml
Copiar código

---

## ⚙️ Como Executar Localmente

### 1️⃣ Clonar o projeto
```bash
git clone https://github.com/fabiodiasmh/chamados-per2park.git
cd chamados-per2park
2️⃣ Configurar o banco de dados (PostgreSQL)
Crie o banco no PostgreSQL:

sql
Copiar código
CREATE DATABASE chamados_per2park;
Atualize o arquivo application.properties com suas credenciais:

properties
Copiar código
spring.datasource.url=jdbc:postgresql://localhost:5432/chamados_per2park
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=8080
3️⃣ Executar o projeto com Maven
bash
Copiar código
mvn spring-boot:run
ou, se quiser rodar o .jar:

bash
Copiar código
mvn clean package
java -jar target/chamados-per2park-0.0.1-SNAPSHOT.jar
🧩 Endpoints Principais da API
Método	Endpoint	Descrição
GET	/chamados	Lista todos os chamados
POST	/chamados	Cria um novo chamado
GET	/chamados/{id}	Busca um chamado pelo ID
PUT	/chamados/{id}	Atualiza um chamado existente
DELETE	/chamados/{id}	Remove um chamado

📘 Swagger UI disponível em:

bash
Copiar código
http://localhost:8080/swagger-ui.html
🧠 Objetivo do Projeto
Centralizar os chamados técnicos da infraestrutura Per2Park.

Integrar dados de diferentes ambientes (showroom, pista, totem etc).

Permitir rastreabilidade e histórico de cada atendimento.

Gerar relatórios para análise de desempenho e SLA.

🐳 Deploy com Docker
1️⃣ Criar a imagem Docker
No diretório raiz do projeto (onde está o Dockerfile):

bash
Copiar código
docker build -t chamados-per2park:latest .
2️⃣ Rodar localmente
bash
Copiar código
docker run -d -p 8080:8080 chamados-per2park
3️⃣ Enviar imagem para o OCI (Oracle Cloud Infrastructure)
Faça login no OCIR:

bash
Copiar código
docker login iad.ocir.io
Renomeie a imagem para o repositório OCI:

bash
Copiar código
docker tag chamados-per2park iad.ocir.io/<tenancy-namespace>/chamados-per2park:latest
Faça o push:

bash
Copiar código
docker push iad.ocir.io/<tenancy-namespace>/chamados-per2park:latest
No OCI, crie um container instance usando essa imagem.

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
📧 GitHub: fabiodiasmh

🤝 Contribuição
Contribuições são bem-vindas!
Para contribuir:

Faça um fork do projeto

Crie uma branch com sua feature:

bash
Copiar código
git checkout -b feature/nome-da-feature
Faça o commit:

bash
Copiar código
git commit -m "Descrição da feature"
Faça o push e abra um Pull Request

🪪 Licença
Este projeto está sob a licença MIT.
Consulte o arquivo LICENSE para mais informações.
