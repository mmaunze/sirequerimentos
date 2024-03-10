 
 
<%@page import="modelo.SituacaoDivida"%>
<%@page import="controller.SituacaoDividaJpaController"%>
<%@page import="modelo.MatriculaForaEpoca"%>
<%@page import="controller.MatriculaForaEpocaJpaController"%>
<%@page import="controller.EquivalenciaJpaController"%>
<%@page import="modelo.Equivalencia"%>
<%@page import="modelo.Declaracao"%>
<%@page import="controller.DeclaracaoJpaController"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="controller.AnulacaoMatriculaJpaController"%>
<%@page import="modelo.AnulacaoMatricula"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="controller.UtilizadorJpaController"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="modelo.Utilizador"%>
<%
// Verificar se o atributo de sessÃ£o estÃ¡ presente
    if (session == null || session.getAttribute("utilizador") == null) {
        response.sendRedirect("sair");
        return;
    }
    Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");
%>

<% EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
    SituacaoDividaJpaController situacaoDivida = new SituacaoDividaJpaController(emf);
    SimpleDateFormat dia = new SimpleDateFormat("dd MMMM yyyy ");
    SimpleDateFormat hora = new SimpleDateFormat("HH:MM:SS ");
    List<SituacaoDivida> pedidos = situacaoDivida.findSituacaoDividaEntities();

%>

<jsp:include page="header.jsp" />
<jsp:include page="sidebar.jsp" />
<%if (!utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("administador")) {%>
<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <h5>Situação de Dívida</h5>
                <span>Preencha os Dados </span>

            </div>
            <div class="card-block">
                <div class="j-wrapper j-wrapper-640">
                    <form action="/sirequerimentos/situacao_de_divida" method="post" class="j-pro">
                        <div class="j-content">
                            <!-- start name -->
                            <div class="j-unit">
                                <label class="j-label">Nome</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="name">
                                        <i class="icofont icofont-ui-user"></i>
                                    </label>
                                    <input type="text" id="name" name="name" value="<%=utilizador.getNome()%>" readonly>
                                </div>
                            </div>
                            <!-- end name -->
                            <!-- start email phone -->
                            <div class="j-row">
                                <div class="j-span6 j-unit">
                                    <label class="j-label">Email</label>
                                    <div class="j-input">
                                        <label class="j-icon-right" for="email">
                                            <i class="icofont icofont-envelope"></i>
                                        </label>
                                        <input type="text" id="email" name="email" value="<%=utilizador.getEmail()%>" readonly>
                                    </div>
                                </div>
                                <div class="j-span6 j-unit">
                                    <label class="j-label">Contacto</label>
                                    <div class="j-input">
                                        <label class="j-icon-right" for="phone">
                                            <i class="icofont icofont-phone"></i>
                                        </label>
                                        <input type="text" id="contacto" name="contacto" value="<%=utilizador.getContacto()%>" readonly>
                                    </div>
                                </div>
                            </div>
                            <!-- end email phone -->
                            <div class="j-divider j-gap-bottom-25"></div>
                            <!-- start date -->
                            <div class="j-row">
                                <div class="j-span6 j-unit">
                                    <label class="j-label">Data do Pedido</label>
                                    <div class="j-input">
                                        <label class="j-icon-right" for="date_from">
                                            <i class="icofont icofont-ui-calendar"></i>
                                        </label>
                                        <input type="text" id="data_pedido" name="data_pedido" readonly="" value="" onfocus="this.value = new Date().toISOString().split('T')[0]" >
                                    </div>
                                </div>
                                <div class="j-span6 j-unit">
                                    <label class="j-label">Tipo de Pedido</label>
                                    <div class="j-input">
                                        <label class="j-icon-right" for="tipo_pedido">
                                            <i class="icofont icofont-ui-calendar"></i>
                                        </label>
                                        <input type="text" id="tipo_pedido" name="tipo_pedido" value="Situação de Dívida" readonly="">
                                        <input type="hidden" name="utilizador" value="<%=utilizador.getId()%>">
                                    </div>
                                </div>
                            </div>
                            <div class="j-unit">
                                <label class="j-label">Valor da Divida</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="valor">
                                        <i class="icofont icofont-ui-user"></i>
                                    </label>
                                    <input type="text" id="valor" name="valor" >
                                </div>
                            </div>     
                            <!-- end date -->
                            <div class="j-divider j-gap-bottom-25"></div>
                            <!-- start message -->
                            <div class="j-unit">
                                <label class="j-label">Justificativa</label>
                                <div class="j-input">
                                    <textarea spellcheck="false" name="justificativa"></textarea>
                                </div>
                            </div>
                            <!-- end message -->
                            <!-- start response from server -->
                            <div class="j-response"></div>
                            <!-- end response from server -->
                        </div>
                        <!-- end /.content -->
                        <div class="j-footer">
                            <button type="submit" class="btn btn-primary">Submeter</button>
                        </div>
                        <!-- end /.footer -->
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>
<%}%>
<div class="card rounded-card user-card">
    <div class="card-header">
        <h5>Lista de Pedidos</h5>
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
                            for (SituacaoDivida e : pedidos)
                                if (e.getPedido().getEstado().getDescricao().equalsIgnoreCase("Em Processamento")) {
                                    {%>
                    <tr>
                        <td> <a href="pedidos?pedido=<%=e.getPedido().getId()%>"> <%= e.getPedido().getId()%> </a></td>
                        <td><%= e.getPedido().getAssunto()%></td>
                        <% if (utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("administador")) {%>

                        <td><a href="utilizadores?utente=<%=e.getPedido().getUtilizador().getId()%>"><%= e.getPedido().getUtilizador().getNome()%></td>
                        <td><%= e.getPedido().getUtilizador().getDepartamento().getSigla()%></td>       
                        <%}%>                        
                        <td><%= dia.format(e.getPedido().getDataPedido())%></td>
                        <td id="devolver-td" class="d-flex justify-content-between ">
                            <form id="aprovar-form"
                                  action="pedidos"
                                  method="post">
                                <input type="hidden"
                                       name="pedido"
                                       value="<%=e.getPedido().getId()%>">
                                <input type="hidden"
                                       name="utilizador"
                                       value="<%=e.getPedido().getUtilizador().getId()%>">
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
                            <form id="rejeitar-form"
                                  action="pedidos"
                                  method="post">
                                <input type="hidden"
                                       name="pedido"
                                       value="<%=e.getPedido().getId()%>">
                                <input type="hidden"
                                       name="utilizador"
                                       value="<%=e.getPedido().getUtilizador().getId()%>">
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
                        for (SituacaoDivida e : pedidos)
                            if (e.getPedido().getUtilizador().getId() == utilizador.getId()) {
                                {%>
                    <tr>
                        <td> <a href="pedidos?pedido=<%=e.getPedido().getId()%>"> <%= e.getPedido().getId()%> </a></td>
                        <td><%= e.getPedido().getAssunto()%></td>                      
                        <td><%= dia.format(e.getPedido().getDataPedido())%></td>
                        <td id="devolver-td">
                            <%if (e.getPedido().getEstado().getDescricao().equalsIgnoreCase("Em Processamento")) {%>
                            <form id="cancelar-form"
                                  action="pedidos"
                                  method="post">
                                <input type="hidden"
                                       name="pedido"
                                       value="<%=e.getPedido().getId()%>">
                                <input type="hidden"
                                       name="utilizador"
                                       value="<%=e.getPedido().getUtilizador().getId()%>">
                                <input type="hidden"
                                       name="accao"
                                       value="cancelar">
                                <button
                                    type="submit"
                                    class="btn btn-success">Cancelar</button>
                            </form> 
                            <%} else {%>
                            <%=e.getPedido().getEstado().getDescricao()%>
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
 
 
