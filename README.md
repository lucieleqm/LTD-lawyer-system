# ⚖️ Sistema NPJ (LTD-lawyer-system)

Plataforma de gerenciamento desenvolvida para o Núcleo de Prática Jurídica (NPJ). Este projeto faz parte do programa acadêmico LTD, focado em entregar soluções tecnológicas práticas para clientes reais da instituição Estácio de Sá.

## ⚙️ Pré-requisitos

- **Java 17**
- **Node.js**
- **MariaDB**

## 🏗️ Arquitetura do Projeto

O repositório está estruturado em um formato _monorepo_ plano, separando claramente as responsabilidades de back-end e front-end (web e mobile):

```text
/
├── api/       # Backend (Java / Spring Boot)
├── web/       # Frontend Web (React / Vite)
└── mobile/    # App Mobile (React Native - Desenvolvimento Futuro)
```

## 🔧 Configuração de Ambiente

O backend possui perfis de execução pré-configurados (`dev`, `test`, `prod`). Antes de rodar localmente, acesse a pasta `api/src/main/resources/` e preencha as credenciais do banco MariaDB e as variáveis de segurança pendentes no arquivo `application-dev.properties` (ou injete-as através das variáveis de ambiente da sua IDE).
_(Atenção: Tenha o cuidado de não realizar commits com suas senhas locais)._

## 🚀 Como Executar Localmente

### 1. Backend (API)

Navegue até a pasta da API e inicie a aplicação Spring Boot:

```bash
cd api
./mvnw spring-boot:run -Dmaven.test.skip=true
```

A API rodará em http://localhost:8080

### 2. Frontend (Web)

Em outro terminal, navegue até a pasta web, instale as dependências e inicie o ambiente de desenvolvimento:

```bash
cd web
npm install
npm start
```

O sistema web rodará em http://localhost:3000

## 👥 Equipe

- [Luciele Queiroz](https://github.com/lucieleqm)
- [Kawan Caminha](https://github.com/kawanHenrick)
- [Wilson Pedro](https://github.com/Wilson-Pedro)
