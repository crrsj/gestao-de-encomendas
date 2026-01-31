🚚 Projeto Logística - EntregadoresSistema completo para gestão de encomendas e entregadores, utilizando uma arquitetura moderna com containers.
🚀 Como Rodar o Projeto1. Pré-requisitosDocker e Docker Compose instalados.Gerar o pacote da API (na raiz do projeto):PowerShell./mvnw clean package -DskipTests
2. Subir os ContainersPowerShelldocker compose up --build -d
3. AcessoFrontend: http://localhost:8081API Backend: http://localhost:8080
✨ Funcionalidades PrincipaisO sistema foi projetado para resolver o fluxo de ponta a ponta da logística de entregas:Gestão de Clientes:
Cadastro e manutenção de dados dos clientes que solicitam as entregas.Controle de Encomendas: Registro de novos pedidos, incluindo descrição, peso e destino.Rastreamento de Status: 
Acompanhamento em tempo real se a entrega está "Pendente", "Em Trânsito" ou "Entregue".Vínculo de Entregadores: Sistema de atribuição de encomendas para profissionais disponíveis.
Persistência de Dados: Banco de dados PostgreSQL configurado para garantir que nenhuma informação seja perdida ao reiniciar os containers.
🛠 Comandos ÚteisAçãoComandoVerificar containers ativosdocker psAcompanhar logs da APIdocker compose logs api-entregadores -fParar os serviçosdocker compose stopReiniciar containersdocker compose restart
🏗 Estrutura TécnicaBackend: Java 21 com Spring Boot (Spring Data JPA, Web).Frontend: HTML5, CSS3 e JavaScript puro, servido via Nginx.Banco de Dados: PostgreSQL 15.Orquestração: 
Docker Compose para isolamento de ambiente.Dica extra: 
⚙️ Detalhes da API (Backend)A API é o coração do sistema, responsável pela lógica de negócio e pela comunicação segura com o banco de dados.
Tecnologias UtilizadasJava 21: Utilizando as funcionalidades mais recentes da linguagem para melhor performance.
Spring Boot 3: Framework base para criação de microserviços.Spring Data JPA: Para abstração da camada de persistência e consultas ao PostgreSQL.Hibernate:
Como provedor ORM para mapeamento das entidades (Clientes e Encomendas).Principais EndpointsAbaixo estão os endpoints base para integração com o Frontend:
👥 ClientesMétodoEndpointDescriçãoGET/clientesLista todos os clientes cadastrados.POST/clientesCadastra um novo cliente.GET/clientes/{id}Busca detalhes de um cliente específico.
📦 EncomendasMétodoEndpointDescriçãoGET/encomendasLista todas as encomendas e seus status.POST/encomendasRegistra uma nova encomenda vinculada a um cliente.PUT/encomendas/{id}/statusAtualiza o status da entrega (ex: Pendente -> Entregue).
🔗 Integração com o Banco de DadosA API se comunica com o PostgreSQL através da rede interna do Docker. 
No arquivo application.properties, a configuração utiliza o nome do serviço definido no Docker Compose:spring.datasource.url=jdbc:postgresql://db-logistica:5432/logistica_db
🛡️ CORS (Cross-Origin Resource Sharing)A API está configurada para aceitar requisições vindas do endereço do Frontend (http://localhost:8081), permitindo que o navegador consuma os dados sem bloqueios de segurança.

![entregas1](https://github.com/user-attachments/assets/09550a7e-7dad-4108-997b-d50ae4de44ca)

![entregas2](https://github.com/user-attachments/assets/5ba554dd-bef4-4d9a-9c62-7ebe074186dd)

![entregas3](https://github.com/user-attachments/assets/3a767fd8-a5f0-411a-a6ea-c59c4cc28867)

![entregas4](https://github.com/user-attachments/assets/11af9dc1-9221-498c-80f6-711ac4c2b07b)

![entregas5](https://github.com/user-attachments/assets/6f60d138-8c42-4633-8cb0-9791b440ce54)

![encomenda1](https://github.com/user-attachments/assets/9336a8e3-00cb-4f2c-9be8-14542135755b)

![enxomenda2](https://github.com/user-attachments/assets/e6fbe6fe-fea5-46d6-b9e8-33ee9028a4ac)
