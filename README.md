# 🚚 Projeto Logística - Entregadores

Sistema completo para gestão de encomendas e entregadores, utilizando uma arquitetura moderna baseada em containers. O projeto resolve o fluxo de ponta a ponta da logística de entregas, desde o cadastro de clientes até o rastreamento em tempo real.

---

## 🚀 Como Rodar o Projeto

### 1. Pré-requisitos
* **Docker** e **Docker Compose** instalados.
* Java 21 (para compilação local, se necessário).

### 2. Gerar o pacote da API
Na raiz do projeto, execute o comando para gerar o `.jar` (pulando os testes para agilizar):

```powershell
./mvnw clean package -DskipTests

docker compose up --build -d

4. Acesso aos ServiçosFrontend: http://localhost:8081API Backend: http://localhost:8080
✨ Funcionalidades PrincipaisGestão de Clientes: Cadastro e manutenção de dados dos clientes solicitantes.Controle de Encomendas: Registro de novos pedidos com descrição, peso e destino.Rastreamento de Status: Acompanhamento em tempo real (Pendente, Em Trânsito, Entregue).Vínculo de Entregadores: Sistema de atribuição de encomendas para profissionais disponíveis.Persistência de Dados: PostgreSQL 15 configurado para garantir a integridade dos dados entre reinicializações.🏗 Estrutura TécnicaBackend: Java 21 com Spring Boot 3 (Spring Data JPA, Spring Web).Frontend: HTML5, CSS3 e JavaScript puro, servido via Nginx.Banco de Dados: PostgreSQL 15.Orquestração: Docker Compose para isolamento total do ambiente.
⚙️ Detalhes da API (Backend)A API é o coração do sistema, responsável pela lógica de negócio e comunicação segura.Tecnologias UtilizadasJava 21: Funcionalidades recentes para melhor performance.Spring Boot 3: Framework base para microserviços.Spring Data JPA / Hibernate: Abstração da camada de persistência e mapeamento ORM.
🔗 Integração e SegurançaBanco de Dados: A comunicação ocorre via rede interna do Docker.spring.datasource.url=jdbc:postgresql://db-logistica:5432/logistica_dbCORS: Configurado para permitir requisições do endereço do Frontend (http://localhost:8081), evitando bloqueios de segurança no navegador.
👥 Principais EndpointsCategoriaMétodoEndpointDescriçãoClientesGET/clientesLista todos os clientes cadastrados.ClientesPOST/clientesCadastra um novo cliente.ClientesGET/clientes/{id}Busca detalhes de um cliente específico.EncomendasGET/encomendasLista todas as encomendas e seus status.EncomendasPOST/encomendasRegistra nova encomenda vinculada a um cliente.EncomendasPUT/encomendas/{id}/statusAtualiza o status (ex: Pendente -> Entregue).
🛠 Comandos ÚteisAçãoComandoVerificar containers ativosdocker psAcompanhar logs da APIdocker compose logs api-entregadores -fParar os serviçosdocker compose stopReiniciar containersdocker compose restart

````

![entregas1](https://github.com/user-attachments/assets/09550a7e-7dad-4108-997b-d50ae4de44ca)

![entregas2](https://github.com/user-attachments/assets/5ba554dd-bef4-4d9a-9c62-7ebe074186dd)

![entregas3](https://github.com/user-attachments/assets/3a767fd8-a5f0-411a-a6ea-c59c4cc28867)

![entregas4](https://github.com/user-attachments/assets/11af9dc1-9221-498c-80f6-711ac4c2b07b)

![entregas5](https://github.com/user-attachments/assets/6f60d138-8c42-4633-8cb0-9791b440ce54)

![encomenda1](https://github.com/user-attachments/assets/9336a8e3-00cb-4f2c-9be8-14542135755b)

![enxomenda2](https://github.com/user-attachments/assets/e6fbe6fe-fea5-46d6-b9e8-33ee9028a4ac)
