
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modelo.Pedido"%>
<%@page import="modelo.Curso"%>
<%@page import="controller.CursoJpaController"%>
<%@page import="controller.FaculdadeJpaController"%>
<%@page import="modelo.Faculdade"%>
<%@page import="modelo.Estudante"%>
<%@page import="modelo.Cta"%>
<%@page import="controller.CtaJpaController"%>
<%@page import="controller.EstudanteJpaController"%>
<%@page import="modelo.Utilizador"%>
<%
// Verificar se o atributo de sessão está presente
    if (session == null || session.getAttribute("utilizador") == null) {
        response.sendRedirect("sair");
        return;
    }
    
    Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");

     if (!utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("Administador") || 
                    utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("Secretário")){
         response.sendRedirect("inicio");
        return;            
    }
    
    
    List<Pedido> emprestimos = (List<Pedido>) request.getAttribute("listaEmprestimosUtente");

    SimpleDateFormat dia = new SimpleDateFormat("dd MMMM yyyy ");
    SimpleDateFormat hora = new SimpleDateFormat("HH:MM:SS ");
%>

<%@page import="java.util.List"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
%>
<jsp:include page="header.jsp" />
<jsp:include page="sidebar.jsp"   />
<div class="row">
    <div class="col-lg-12">
        <!-- tab header start -->
        <div class="tab-header card">
            <ul class="nav nav-tabs md-tabs tab-timeline" role="tablist" id="mytab">
                <li class="nav-item">
                    <a class="nav-link active" data-toggle="tab" href="#personal" role="tab">Informacoes do Utente</a>
                    <div class="slide"></div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#binfo" role="tab">Historico de Requerimentos</a>
                    <div class="slide"></div>
                </li>
                
            </ul>
        </div>
        <!-- tab header end -->
        <!-- tab content start -->
        <div class="tab-content">
            <!-- tab panel personal start -->
            <div class="tab-pane active" id="personal" role="tabpanel">
                <!-- personal card start -->
                <div class="card rounded-card user-card">
                    <div class="card-header">
                        <h5 class="card-header-text">Informacao do Utente</h5>
                        <button id="edit-btn" type="button" class="btn btn-sm btn-primary waves-effect waves-light f-right">
                            <i class="icofont icofont-edit"></i>
                        </button>
                    </div>
                    <div class="card-block">
                        <div class="view-info">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="general-info">
                                        <div class="row">
                                            <div class="col-lg-12 col-xl-6">
                                                <div class="table-responsive">
                                                    <table class="table m-0">
                                                        <tbody>
                                                            <tr>
                                                                <th scope="row">
                                                                    Nome Compeleto
                                                                </th>
                                                                <td>${utente.nome}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Sexo</th>
                                                                <td>${utente.sexo}</td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Tipo de Permissao
                                                                </th>
                                                                <td>
                                                                    ${utente.tipoUtilizador.descricao}
                                                                </td>
                                                            </tr>

                                                            <tr>
                                                                <th scope="row">
                                                                    Departamento
                                                                </th>
                                                                <td>
                                                                    ${utente.departamento.sigla}
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-xl-6">
                                                <div class="table-responsive">
                                                    <table class="table">
                                                        <tbody>
                                                            <tr>
                                                                <th scope="row">
                                                                    Email</th>
                                                                <td><a
                                                                        href="#!">${utente.email}</a>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Contacto
                                                                </th>
                                                                <td>${utente.contacto}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Movimentos</th>
                                                                <td>
                                                                    <%=emprestimos.size()%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Username</th>
                                                                <td>${utente.username}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Numero de Identifcacao</th>
                                                                <td><a
                                                                        href="#!">${utente.id}</a>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- end of card-block -->
                </div>

                <!-- personal card end-->
            </div>
            <!-- tab pane personal end -->
            <!-- tab pane info start -->
            <div class="tab-pane" id="binfo" role="tabpanel">
                <!-- info card start -->
                <div class="card rounded-card user-card">
                    <div class="card-header">
                        <h5 class="card-header-text">Historico de Requerimentos</h5>
                    </div>
                    <div class="card-block">
                        <div class="dt-responsive table-responsive">
                            <table id="new-cons" class="table table-striped table-bordered nowrap">
                                <thead>
                                    <tr>
                                        <th>Pedido</th>
                                        <th>Assunto</th>                               
                                        <th>Data</th>
                                        <th>Hora</th>
                                        <th>Estado</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Pedido e : emprestimos) {%>
                                    <tr>
                                        <td><%= e.getId()%></td>
                                        <td><%= e.getAssunto()%></td>
                                        <td><%= dia.format(e.getDataPedido())%></td>
                                        <td><%= hora.format(e.getDataPedido())%></td>
                                        <th><%=e.getEstado().getDescricao()%></th>
                                    </tr>
                                    <%}
                                    %>

                                </tbody>

                            </table>
                        </div>
                    </div>
                </div>

                <!-- info card end -->
            </div>

        </div>
        <!-- tab content end -->
    </div>
</div>
<jsp:include page="footer.jsp" /> 

