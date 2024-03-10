<%@page import="modelo.Utilizador"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String nomeArquivo = request.getServletPath();
    nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf('/') + 1, nomeArquivo.lastIndexOf('.'));
    // Verifica se o nome do arquivo é "index"
     if (nomeArquivo.equalsIgnoreCase("anulacao_de_matricula")) {
        nomeArquivo = "Anulação de Matrícula";
    }
     else if (nomeArquivo.equalsIgnoreCase("cartao_de_estudante_2_via")) {
        nomeArquivo = "Cartão de Estudante 2ª via";
    }
      else if (nomeArquivo.equalsIgnoreCase("declaracao_discriminativa_de_notas")) {
        nomeArquivo = "Declaração Discriminativa de Notas";
    }
    else if (nomeArquivo.equalsIgnoreCase("index")) {
        nomeArquivo = "Pagina Inicial";
    } else {
        String primeiraLetra = nomeArquivo.substring(0, 1).toUpperCase();
        String restoDaString = nomeArquivo.substring(1);
        nomeArquivo = primeiraLetra + restoDaString;
    }
    Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");
     
%>
<!-- Sidebar inner chat end-->
<div class="pcoded-main-container">
    <div class="pcoded-wrapper">
        
 <nav class="pcoded-navbar">
    <div class="sidebar_toggle"><a href="#"><i class="icon-close icons"></i></a></div>
    <div class="pcoded-inner-navbar main-menu">
        <ul class="pcoded-item pcoded-left-item">
            <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("index.jsp")) { %>active<% } %>">
                <a href="index.jsp">
                    <span class="pcoded-micon"><i class="ti-home"></i></span>
                    <span class="pcoded-mtext" data-i18n="nav.dash.main">Pagina Inicial</span>
                    <span class="pcoded-mcaret"></span>
                </a>
            </li>
            <li class="pcoded-hasmenu">
                <a href="javascript:void(0)">
                    <span class="pcoded-micon"><i class="feather icon-plus"></i></span>
                    <span class="pcoded-mtext">Pedidos</span>
                </a>
                <ul class="pcoded-submenu">
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("anulacao_de_matricula.jsp")) { %>active<% } %>">
                        <a href="anulacao_de_matricula.jsp">
                            <span class="pcoded-micon"><i class="ti-shopping-cart"></i></span>
                            <span class="pcoded-mtext" data-i18n="nav.page_layout.main">  Anulação de Matrícula</span>
                            <span class="pcoded-badge label label-success">${reservasActivas}</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("matricula_fora_da_epocajsp")) { %>active<% } %>">
                        <a href="matricula_fora_da_epoca.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext">Matrícula fora da época</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("reintegracao_para_o_ano_academico_20__.jsp")) { %>active<% } %>">
                        <a href="reintegracao_para_o_ano_academico_20__.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext"> Reintegração para o ano Académico</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>       
                </ul>
            </li>    
            <li class="pcoded-hasmenu">
             <a href="javascript:void(0)">
                    <span class="pcoded-micon"><i class="feather ti-book"></i></span>
                    <span class="pcoded-mtext">Documentos</span>
                </a>
                <ul class="pcoded-submenu">
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("cartao_de_estudante_2_via.jsp")) { %>active<% }%>">
                        <a href="cartao_de_estudante_2_via.jsp">
                            <span class="pcoded-micon"><i class="ti-shift-right"></i></span>
                            <span class="pcoded-mtext" data-i18n="nav.page_layout.main">Cartão de Estudante 2ª via</span>

                            <span class="pcoded-badge label label-warning">${sessionScope.emprestimosActivos}</span>

                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("declaracao_discriminativa_de_notas.jsp")) { %>active<% }%>">
                        <a href="declaracao_discriminativa_de_notas.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-book"></i></span>
                            <span class="pcoded-mtext"> Declaração
                            </span>
                            <span class="pcoded-badge label "> ${sessionScope.obrasDisponiveis} </span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>                     
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("pedidos_de_certificados.jsp")) { %>active<% } %>">
                        <a href="pedidos_de_certificados.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext">
                                Pedidos de Certificados</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                </ul>
            </li>
            
            <li class="pcoded-hasmenu">
                <a href="javascript:void(0)">
                    <span class="pcoded-micon"><i class="feather ti-shift-left"></i></span>
                    <span class="pcoded-mtext">Mudanças</span>
                </a>
                <ul class="pcoded-submenu">
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("mudanca_de_curso.jsp")) { %>active<% }%>">
                        <a href="mudanca_de_curso.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-money"></i></span>
                            <span class="pcoded-mtext"> Mudança de Curso</span>

                            <span class="pcoded-badge label label-danger"> ${sessionScope.multasActivas}</span>

                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>

                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("mudanca_de_plano_de_pagamento_de_propinas.jsp")) { %>active<% } %>">
                        <a href="mudanca_de_plano_de_pagamento_de_propinas.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext">Mudança de Plano de Pagamento de Propinas</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("transferencia_para_outros_crs.jsp")) { %>active<% } %>">
                        <a href="transferencia_para_outros_crs.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext">Transferência para outros CRs</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>

                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("equivalencia.jsp")) { %>active<% } %>">
                        <a href="equivalencia.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext">Equivalência</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>

                </ul>
            </li> 

            <li class="pcoded-hasmenu">
                <a href="javascript:void(0)">
                    <span class="pcoded-micon"><i class="feather icon-envelope"></i></span>
                    <span class="pcoded-mtext">Reclamação</span>
                </a>
                <ul class="pcoded-submenu">
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("reclamacao_de_nota_de_teste_exame.jsp")) { %>active<% } %>">
                        <a href="reclamacao_de_nota_de_teste_exame.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext"> Reclamação de Nota de Teste/Exame</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>

                </ul>
            </li> 

            <li class="pcoded-hasmenu">
                <a href="javascript:void(0)">
                    <span class="pcoded-micon"><i class="feather ti-shift-right"></i></span>
                    <span class="pcoded-mtext">Submissão</span>
                </a>
                <ul class="pcoded-submenu">
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("submissao_de_monografias_fora_do_prazo.jsp")) { %>active<% } %>">
                        <a href="submissao_de_monografias_fora_do_prazo.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext"> Submissão de Monografias fora do Prazo
                            </span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("submissao_de_relatorio_de_estagio.jsp")) { %>active<% } %>">
                        <a href="submissao_de_relatorio_de_estagio.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext"> Submissão de Relatório de Estágio
                            </span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>

                </ul>
            </li> 


            <li class="pcoded-hasmenu">
                <a href="javascript:void(0)">
                    <span class="pcoded-micon"><i class="feather ti-shopping-cart"></i></span>
                    <span class="pcoded-mtext">Outros Pedidos</span>
                </a>
                <ul class="pcoded-submenu">
                    <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("situacao_de_divida.jsp")) { %>active<% } %>">
                        <a href="situacao_de_divida.jsp" data-i18n="nav.widget.main">

                            <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                            <span class="pcoded-mtext">Situação de Dívida</span>
                            <span class="pcoded-mcaret"></span>
                        </a>
                    </li>
                </ul>
                
            </li> 

           
            <% if (utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("Administador") || 
                    utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("Secretário")
            ) {%>
            
            <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("utilizadores.jsp")) { %>active<% }%>">
                <a href="utilizadores" data-i18n="nav.widget.main">

                    <span class="pcoded-micon"><i class="ti-user"></i></span>
                    <span class="pcoded-mtext">Utilizadores</span>
                    <span class="pcoded-mcaret"></span>
                </a>
            </li>
            <li class="pcoded-trigger <% if (request.getRequestURI().endsWith("relatorios.jsp")) { %>active<% }%>">
                <a href="relatorios" data-i18n="nav.widget.main">

                    <span class="pcoded-micon"><i class="ti-bar-chart-alt"></i></span>
                    <span class="pcoded-mtext">Relatorios</span>
                    <span class="pcoded-mcaret"></span>
                </a>
            </li>
            <%}%>
        </ul>
    </div>
</nav>
        <div class="pcoded-content">
            <div class="pcoded-inner-content">
                <div class="main-body">
                    <div class="page-wrapper">
                        <div class="page-header">
                            <div class="page-header-title">
                                <h4><%= nomeArquivo%></h4>
                            </div>
                            <div class="page-header-breadcrumb">
                                <ul class="breadcrumb-title">
                                    <li class="breadcrumb-item">
                                        <a href="index.html">
                                            <i class="icofont icofont-home"></i>
                                        </a>
                                    </li>
                                    <li class="breadcrumb-item"><a href="index.jsp">Inicio</a>
                                    </li>
                                    <li class="breadcrumb-item"><a href="javascrip:void(0)"><%=nomeArquivo%></a>
                                    </li>
                                </ul>
                            </div>
                        </div>