# Monitoramento da API `app-loja-mkt` com Prometheus e Grafana

Este projeto configura a API `app-loja-mkt` com monitoramento de métricas usando **Prometheus** e **Grafana**. O Docker Compose é utilizado para orquestrar todos os containers necessários.

## Visão Geral

- **API `app-loja-mkt`**: API desenvolvida com Spring Boot, que simula uma loja e fornece dados de produtos, tópicos e respostas.
- **Prometheus**: Sistema de monitoramento que coleta e armazena métricas da API.
- **Grafana**: Ferramenta para visualização de métricas coletadas pelo Prometheus.

O projeto é orquestrado usando o **Docker Compose**, simplificando o processo de configuração e execução de todos os serviços necessários.

## Estrutura

- `app-loja-mkt`: API em Spring Boot.
- `prometheus`: Serviço que coleta as métricas da API.
- `grafana`: Ferramenta de visualização de métricas coletadas pelo Prometheus.

## Pré-requisitos

Antes de começar, verifique se você tem os seguintes pré-requisitos:

- [Docker](https://www.docker.com/get-started) instalado.
- [Docker Compose](https://docs.docker.com/compose/install/) instalado.

## Passo a Passo para Rodar o Projeto

### 1. Clonando o Repositório

Primeiro, clone este repositório para sua máquina local:

```bash
git clone https://github.com/DiogoHumberto/prometheus-app.git
cd app-loja
```
### 2. Configurando o Docker Compose
   Dentro do diretório do projeto, você encontrará o arquivo docker-compose.yml, que define os serviços necessários para rodar o ambiente de monitoramento. Este arquivo inclui a configuração do app-loja-mkt, Prometheus e Grafana.

### 3. Configurando o Prometheus
   O Prometheus precisa de um arquivo de configuração chamado prometheus.yml para saber de onde coletará as métricas.
   Você encontrará ele na raiz do projeto.

### 4. Rodando o Projeto
   Com o Docker Compose e os arquivos configurados, você pode subir os containers. No diretório do projeto, execute o seguinte comando:

```bash
docker-compose up -d
```

### 5. Acessando a Aplicação
   A API app-loja-mkt estará disponível em: http://localhost:8080
   O Prometheus estará disponível em: http://localhost:9090
   O Grafana estará disponível em: http://localhost:3000
   Use as credenciais padrão para acessar o Grafana:

```text
Usuário: admin
Senha: admin
```

### 6. Configurando o Grafana
   - Acesse o Grafana em http://localhost:3000.
   No painel do Grafana, vá para Configuration > Data Sources.
   - Adicione o Prometheus como uma fonte de dados:
   Nome: Prometheus
   URL: http://prometheus:9090
   - Salvar & Testar para garantir que a conexão foi bem-sucedida.
   Agora você pode criar dashboards no Grafana para visualizar as métricas coletadas pelo Prometheus.

###  7. Monitorando a API
   O Prometheus irá coletar métricas da API app-loja-mkt com base na configuração definida no prometheus.yml. Você pode criar queries no Prometheus e também visualizar esses dados no Grafana, criando gráficos e alertas personalizados.

### Exemplos de Métricas
Com a configuração básica do Prometheus, você pode coletar métricas da sua API app-loja-mkt, como por exemplo:

- http_requests_total: Número total de requisições HTTP feitas para a API.
- http_request_duration_seconds: Duração das requisições HTTP.
- jvm_memory_bytes_used: Memória utilizada pela JVM.
- jvm_threads_current: Número de threads ativas na JVM. 
Essas métricas podem ser usadas no Grafana para criar painéis de controle dinâmicos.

### Além disso é possivel importar as configurações já existentes pelo aqruivo .json 

