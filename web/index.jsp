 
<%@page import="modelo.Pedido"%>
<%@page import="controller.PedidoJpaController"%>
<%@page import="controller.CartaoEstudanteJpaController"%>
<%@page import="modelo.CartaoEstudante"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="controller.AnulacaoMatriculaJpaController"%>
<%@page import="modelo.AnulacaoMatricula"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="controller.UtilizadorJpaController"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="modelo.Utilizador"%>
<%
// Verificar se o atributo de sessão está presente
    if (session == null || session.getAttribute("utilizador") == null) {
        response.sendRedirect("sair");
        return;
    }
    Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");
%>

<% EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
    PedidoJpaController pedidoController = new PedidoJpaController(emf);
    SimpleDateFormat dia = new SimpleDateFormat("dd MMMM yyyy ");
    SimpleDateFormat hora = new SimpleDateFormat("HH:MM:SS ");
    List<Pedido> pedidos = pedidoController.findPedidoEntities();

%>

<jsp:include page="header.jsp" />
<jsp:include page="sidebar.jsp" />

<div class="card rounded-card user-card">
    <div class="card-header">
        <h5>Lista de Pedidos Pendentes</h5>
        <span></span>
        <div class="card-header-right">
            <i class="icofont icofont-rounded-down"></i>
        </div>
    </div>
    <%if (!pedidos.isEmpty()) {%>
    <div class="card-block">
        <div class="dt-responsive table-responsive">
            <table id="new-cons" class="table table-striped table-bordered nowrap">
                <thead>
                    <tr>
                        <th>Pedido</th>
                        <th>Assunto</th>     
                            <% if (utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("administador")) {%>
                        <th>Utilizador</th>
                        <th>Departamento</th>
                            <%}%>
                        <th>Data</th>                                                                        
                        <th>Despacho</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("administador")) {
                            for (Pedido e : pedidos)
                                if (e.getEstado().getDescricao().equalsIgnoreCase("Em Processamento")) {
                                    {%>
                    <tr>
                        <td> <a href="pedidos?pedido=<%=e.getId()%>"> <%= e.getId()%> </a></td>
                        <td><%= e.getAssunto()%></td>
                        <% if (utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("administador")) {%>

                        <td><a href="utilizadores?utente=<%=e.getUtilizador().getId()%>"><%= e.getUtilizador().getNome()%></td>
                        <td><%= e.getUtilizador().getDepartamento().getSigla()%></td>       
                        <%}%>                        
                        <td><%= dia.format(e.getDataPedido())%></td>
                        <td id="devolver-td" class="d-flex text-center justify-content-center align-content-lg-center">
                            <form id="aprovar-form" class="mr-2"
                                  action="pedidos"
                                  method="post">
                                <input type="hidden"
                                       name="pedido"
                                       value="<%=e.getId()%>">
                                <input type="hidden"
                                       name="utilizador"
                                       value="<%=e.getUtilizador().getId()%>">
                                <input type="hidden"
                                       name="accao"
                                       value="aprovar">
                                <input type="hidden"
                                       name="secretario"
                                       value="<%=utilizador.getId()%>">
                                <button
                                    type="submit"
                                    class="btn btn-success">Aprovar</button>
                            </form> 
                                <form id="rejeitar-form" class="ml-3"
                                  action="pedidos"
                                  method="post">
                                <input type="hidden"
                                       name="pedido"
                                       value="<%=e.getId()%>">
                                <input type="hidden"
                                       name="utilizador"
                                       value="<%=e.getUtilizador().getId()%>">
                                <input type="hidden"
                                       name="accao"
                                       value="rejeitar">
                                <input type="hidden"
                                       name="secretario"
                                       value="<%=utilizador.getId()%>">
                                <button
                                    type="submit"
                                    class="btn btn-danger">Rejeitar</button>
                            </form>      
                        </td>

                    </tr>
                    <%}
                            }
                    } //else
                    else {
                        for (Pedido e : pedidos)
                            if (e.getUtilizador().getId() == utilizador.getId()) {
                                {%>
                    <tr>
                        <td> <a href="pedidos?pedido=<%=e.getId()%>"> <%= e.getId()%> </a></td>
                        <td><%= e.getAssunto()%></td>                      
                        <td><%= dia.format(e.getDataPedido())%></td>
                        <td id="devolver-td">
                            <%if (e.getEstado().getDescricao().equalsIgnoreCase("Em Processamento")) {%>
                            <form id="cancelar-form"
                                  action="pedidos"
                                  method="post">
                                <input type="hidden"
                                       name="pedido"
                                       value="<%=e.getId()%>">
                                <input type="hidden"
                                       name="utilizador"
                                       value="<%=e.getUtilizador().getId()%>">
                                <input type="hidden"
                                       name="accao"
                                       value="cancelar">
                                <button
                                    type="submit"
                                    class="btn btn-success">Cancelar</button>
                            </form> 
                            <%} else {%>
                            <%=e.getEstado().getDescricao()%>
                        </td>

                    </tr>
                    <%}
                                    }
                                }
                        } %>

                </tbody>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>                                       
<jsp:include page="footer.jsp" />

