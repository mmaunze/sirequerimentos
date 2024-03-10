
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
    Pedido ped = (Pedido) request.getAttribute("pedido");
    if (!utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("Administador")
            || utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("Secretário")) {
        if (utilizador.getId() != ped.getUtilizador().getId()) {
            response.sendRedirect("inicio");
            return;
        }
    }

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
                    <a class="nav-link active" data-toggle="tab" href="#personal" role="tab">Informacoes do Pedido</a>
                    <div class="slide"></div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="gerar_pdf?pedido=${pedido.id}" target="_blank" role="tab">Imprimir PDF do Pedido</a>
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
                        <h5 class="card-header-text">Informacao do Pedido</h5>
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
                                                                    Assunto
                                                                </th>
                                                                <td>${pedido.assunto}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Utilizador</th>
                                                                <td>${pedido.utilizador.nome} </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Tipo de Pedido
                                                                </th>
                                                                <td>${pedido.tipoPedido.descricao}

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
                                                                        href="#!">${pedido.utilizador.email}</a>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row">
                                                                    Contacto
                                                                </th>
                                                                <td>${pedido.utilizador.contacto}
                                                                </td>
                                                            </tr>


                                                            <tr>
                                                                <th scope="row">
                                                                    Despacho</th>
                                                                <td><a
                                                                        href="#!">${pedido.estado.descricao}</a>
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

                </div>


            </div>


        </div>
        <!-- tab content end -->
    </div>
</div>
<jsp:include page="footer.jsp" /> 

