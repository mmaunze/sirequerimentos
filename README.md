# Sistema de Gestão de Requerimentos

## Descrição

O Sistema de Gestão de Requerimentos é uma aplicação desenvolvida para facilitar o gerenciamento de pedidos e requerimentos em ambientes administrativos, como secretarias escolares. O sistema acompanha todo o ciclo de vida dos requerimentos, desde a criação até a emissão de despachos e geração de documentos em PDF, podendo ser adaptado para diversas realidades.

## Funcionalidades

1. **Gestão de Requerimentos:**
   - Criação de novos requerimentos.
   - Acompanhamento do status de cada requerimento.
   - Histórico detalhado de cada pedido.

2. **Processamento de Pedidos:**
   - Acompanhamento e atualização do processo dos pedidos.
   - Emissão de despachos e decisões.
   - Produção de requerimentos em formato de formulário para PDF.

3. **Notificações e Alertas:**
   - Sistema de notificações para manter os usuários informados sobre o status dos requerimentos.
   - Utilização do pnotify para alertas visuais.

4. **Interface Intuitiva:**
   - Interface de usuário desenvolvida com HTML, CSS, JavaScript, JSP, jQuery e Ajax.
   - Formulários interativos e responsivos para melhor experiência do usuário.

## Tecnologias Utilizadas

- **Frontend:**
  - HTML5, CSS3, JavaScript
  - JSP (JavaServer Pages)
  - jQuery e Ajax para interatividade
  - pnotify para notificações e alertas

- **Backend:**
  - Java EE (Enterprise Edition) para lógica de negócios e controle de fluxo

- **Banco de Dados:**
  - MySQL para armazenamento de dados

## Arquitetura do Sistema

1. **Camada de Apresentação:**
   - Desenvolvida com HTML, CSS, JSP, JavaScript, jQuery e Ajax.
   - Responsável pela interface do usuário e interação com o sistema.

2. **Camada de Negócios:**
   - Implementada com Java EE.
   - Contém a lógica de negócios para processamento e gestão dos requerimentos.

3. **Camada de Dados:**
   - Utiliza MySQL.
   - Responsável pelo armazenamento e gerenciamento dos dados dos requerimentos.

## Requisitos do Sistema

- **Java Development Kit (JDK)**: Versão 11 ou superior.
- **Servidor de Aplicação Java EE**: Apache Tomcat ou similar.
- **MySQL Database**: Versão 8 ou superior.
- **IDE Compatível com Java EE**: Eclipse, IntelliJ, NetBeans, etc.

## Configuração do Projeto

1. **Configuração do Banco de Dados:**
   - Crie uma base de dados MySQL e configure as tabelas necessárias.
   - Configure as credenciais de acesso no arquivo de configuração do projeto.

2. **Configuração do Servidor de Aplicação:**
   - Importe o projeto para o seu ambiente de desenvolvimento (IDE) preferido.
   - Configure o servidor de aplicação (ex: Apache Tomcat) para executar o projeto.

3. **Configuração do JPA:**
   - Configure as propriedades do JPA (persistence.xml) para se conectar à base de dados MySQL.

4. **Execução do Projeto:**
   - Importe o projeto para o seu ambiente de desenvolvimento e execute-o.
   - Certifique-se de que todas as dependências estejam corretamente configuradas no seu `pom.xml` (para projetos Maven) ou `build.gradle` (para projetos Gradle).

## Instruções de Uso

1. **Autenticação:**
   - Ao iniciar o sistema, faça login utilizando suas credenciais.
   - Se necessário, crie uma conta nova.

2. **Criação de Requerimentos:**
   - Navegue até a seção de requerimentos e preencha o formulário para criar um novo pedido.
   - Submeta o formulário para salvar o requerimento no sistema.

3. **Acompanhamento de Requerimentos:**
   - Visualize a lista de requerimentos e acompanhe o status de cada pedido.
   - Utilize filtros para buscar requerimentos específicos.

4. **Emissão de Despachos:**
   - Abra um requerimento específico e emita despachos conforme necessário.
   - Utilize a funcionalidade de geração de PDF para produzir documentos formais.

5. **Notificações:**
   - Fique atento às notificações e alertas fornecidos pelo sistema para acompanhar atualizações e mudanças de status.

## Contribuição e Suporte

- Contribuições são bem-vindas! Se encontrar algum problema ou tiver alguma sugestão de melhoria, sinta-se à vontade para abrir uma issue ou enviar um pull request no repositório do projeto no GitHub.
- Para suporte adicional, entre em contato com a equipe de desenvolvimento através dos canais fornecidos no repositório.

---

Obrigado por utilizar o Sistema de Gestão de Requerimentos! Esperamos que ele atenda às suas necessidades de gerenciamento de pedidos e requerimentos de forma eficiente e eficaz.

---