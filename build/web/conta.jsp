<%@page import="javax.persistence.EntityManager"%>

<%@page import="modelo.Utilizador"%>
<%
// Verificar se o atributo de sessão está presente
    if (session == null || session.getAttribute("utilizador") == null) {
        response.sendRedirect("sair");
        return;
    }
    Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");

%>

<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="controller.UtilizadorJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="java.util.List"%>
<%@page import="controller.UtilizadorJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<jsp:include page="header.jsp" />

<div class="main-body user-profile">
    <div class="page-wrapper">
        <div class="page-body">
            <div class="row">
                <div class="col-lg-12">
                    <div class="cover-profile">
                        <div class="profile-bg-img">
                            <div class="card-block user-info">
                                <div class="col-md-12">
                                    <div class="media-left">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="tab-header">
                        <ul class="nav nav-tabs md-tabs tab-timeline" role="tablist"
                            id="mytab">
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab"
                                   href="#personal" role="tab">Informacoes Peessoais</a>
                                <div class="slide"></div>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#binfo"
                                   role="tab">Dados de Acesso</a>
                                <div class="slide"></div>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"  href="/sirequerimentos/"
                                   role="tab">Voltar A Tela principal</a>
                                <div class="slide"></div>
                            </li>
                        </ul>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane active" id="personal" role="tabpanel">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-header-text">Minhas Informacoes</h5>
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
                                                                            <td><%=utilizador.getNome()%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Sexo</th>
                                                                            <td><%=utilizador.getSexo()%></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Tipo de Permissao
                                                                            </th>
                                                                            <td><%=utilizador.getTipoUtilizador().getDescricao()%>

                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Area Cientifica
                                                                            </th>
                                                                            <td><%=utilizador.getNome() %></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Departamento
                                                                            </th>
                                                                            <td><%=utilizador.getDepartamento().getDescricao()%>
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
                                                                                    href="#!"><%=utilizador.getEmail()%></a>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Contacto
                                                                            </th>
                                                                            <td><%= "+258" + utilizador.getContacto()%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Movimentos</th>
                                                                            <td><%=utilizador.getMovimentoList().size()%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Username</th>
                                                                            <td><%=utilizador.getUsername()%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <th scope="row">
                                                                                Numero de Identifcacao</th>
                                                                            <td><a
                                                                                    href="#"><%= utilizador.getId() %></a>
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
                        <div class="tab-pane" id="binfo" role="tabpanel">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-header-text">Alterar Dados</h5>
                                </div>
                                <div class="card-block">
                                    <form action="trocar_senha" method="post">
                                        <div class="form-group">
                                            <label for="senhaAntiga">Senha Antiga:</label>
                                            <input type="password" class="form-control" id="senhaAntiga" name="senhaAntiga">
                                        </div>
                                        <div class="form-group">
                                            <label for="novaSenha">Nova Senha:</label>
                                            <input type="password" class="form-control" id="novaSenha" name="novaSenha">
                                        </div>
                                        <div class="form-group">
                                            <label for="confirmarSenha">Confirmar Nova Senha:</label>
                                            <input type="password" class="form-control" id="confirmarSenha" name="confirmarSenha">
                                            <input type="hidden" name="user" value="<%=utilizador.getId()%>" />
                                        </div>
                                        <button type="submit" class="btn btn-primary">Alterar Senha</button>
                                    </form>
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
</div>
</div>
</div>
<jsp:include page="footer.jsp" />