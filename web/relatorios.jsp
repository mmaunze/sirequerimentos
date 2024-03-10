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
%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modelo.Movimento"%>
<%@page import="java.util.List"%>
<%@page import="controller.MovimentoJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
    MovimentoJpaController movimentos = new MovimentoJpaController(emf);
    List<Movimento> movimento = movimentos.findMovimentoEntities();
    SimpleDateFormat dia = new SimpleDateFormat("dd  MMMM  yyyy ");
    SimpleDateFormat hora = new SimpleDateFormat("HH:MM:SS ");
%>
<jsp:include page="header.jsp" />
<jsp:include page="sidebar.jsp"   />
<div class="page-body">
    <div class="tab-header">
        <ul class="nav nav-tabs md-tabs tab-timeline" role="tablist"
            id="mytab">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab"
                   href="#personal" role="tab">Relatorios Periodicos</a>
                <div class="slide"></div>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#binfo"
                   role="tab">Historicos de Requerimentos</a>
                <div class="slide"></div>
            </li>

        </ul>
    </div>
    <div class="tab-content">       
        
        <div class="tab-pane" id="binfo" role="tabpanel">          
            <div class="card rounded-card user-card">
                <div class="card-header">
                    <h5>Historico de Movimentos</h5>
                    <div class="card-header-right">
                        <i class="icofont icofont-rounded-down"></i>
                    </div>
                </div>
                <%if (!movimento.isEmpty()) {%>
                <div class="card-block">
                    <div class="dt-responsive table-responsive">
                        <table id="responsive-reorder" class="table table-striped table-bordered nowrap">
                            <thead>
                                <tr>
                                    <th>Data</th>
                                    <th>Hora</th>
                                    <th>Tipo de Movimento</th>
                                    <th>Utente</th>
                                    <th>Pedido</th>
                                    <th>Assunto</th>
                                    <th>Observação</th>
                                </tr>
                            </thead>

                            <tbody>
                                <% for (Movimento m : movimento) {%>
                                <tr>
                                    <td><%= dia.format(m.getDataMovimento())%></td>
                                    <td><%=hora.format(m.getDataMovimento())%></td>
                                    <td><%= m.getTipoMovimento().getDescricao()%></td>
                                    <td><%=m.getUtilizador().getNome()%></td>
                                    <td><%= m.getPedido().getId() %></td>
                                    <td><%= m.getPedido().getAssunto()  %></td>
                                    <td><%= m.getObservacao()%></td>
                                </tr>
                                <% } %>
                            </tbody>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp" />