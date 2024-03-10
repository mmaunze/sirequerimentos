<%@page import="controller.TipoUtilizadorJpaController"%>
<%@page import="controller.CargoJpaController"%>
<%@page import="controller.DepartamentoJpaController"%>
<%@page import="modelo.TipoUtilizador"%>
<%@page import="modelo.Cargo"%>
<%@page import="modelo.Departamento"%>
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
// Verificar se o atributo de sessÃ£o estÃ¡ presente
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

<%@page import="java.util.List"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
    CursoJpaController curso = new CursoJpaController(emf);
    FaculdadeJpaController faculdade = new FaculdadeJpaController(emf);
    DepartamentoJpaController departamento = new DepartamentoJpaController(emf);
    CargoJpaController cargo = new CargoJpaController(emf);
    TipoUtilizadorJpaController tipoUtilizador = new TipoUtilizadorJpaController(emf);
    EstudanteJpaController estudante = new EstudanteJpaController(emf);
    CtaJpaController funcionario = new CtaJpaController(emf);
    
    List<Curso> cursos = curso.findCursoEntities();
    List<Faculdade> faculdades = faculdade.findFaculdadeEntities();
    List<Departamento> departamentos = departamento.findDepartamentoEntities();
    List<Cargo> cargos = cargo.findCargoEntities();
    List<TipoUtilizador> tiposUtilizadores = tipoUtilizador.findTipoUtilizadorEntities();
    List<Cta> funcionarios = funcionario.findCtaEntities();
    List<Estudante> estudantes = estudante.findEstudanteEntities();

%>
<jsp:include page="header.jsp" />
<jsp:include page="sidebar.jsp"   />
<div class="page-body">

    <div class="tab-header">
        <ul class="nav nav-tabs md-tabs tab-timeline" role="tablist"
            id="mytab">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab"
                   href="#personal" role="tab">Lista de Estudantes</a>
                <div class="slide"></div>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#binfo"
                   role="tab">Lista de Funcionarios</a>
                <div class="slide"></div>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#cadastrar"
                   role="tab">Cadastrar</a>
                <div class="slide"></div>
            </li>

        </ul>
    </div>

    <div class="tab-content">

        <div class="tab-pane active" id="personal" role="tabpanel">
            <br>
            <div class="card rounded-card user-card">
                <div class="card-header">
                    <h5>Lista de Estudantes </h5>
                    <span></span>
                    <div class="card-header-right">
                        <i class="icofont icofont-rounded-down"></i>
                    </div>
                </div>
                <%if (!estudantes.isEmpty()) {%>
                <div class="card-block">
                    <div class="dt-responsive table-responsive">
                        <table id="new-cons"
                               class="table table-striped table-bordered nowrap">
                            <thead>
                                <tr>
                                    <th>Nome Completo</th>
                                    <th>Contacto</th>
                                    <th>Email</th>
                                    <th>Curso</th>
                                    <th>Departamento</th>

                                    <th>Nivel</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Estudante e : estudantes) {%>
                                <tr>
                                    <td><a href="utilizadores?utente=<%=e.getUtilizador()%>"><%=e.getUtilizador1().getNome()%></a> </td>
                                    <td><%= e.getUtilizador1().getContacto()%></td>
                                    <td><%= e.getUtilizador1().getEmail()%></td>
                                    <td><%=e.getCurso().getSigla()%></td>
                                    <td><%=e.getUtilizador1().getDepartamento().getSigla()%></td>
                                    <td><%=e.getNivel()%></td>
                                </tr>
                                <%}%>
                            </tbody>
                            <%}%>
                        </table>
                    </div>
                </div>
            </div>

        </div>
        <br>
        <div class="tab-pane" id="binfo" role="tabpanel">
            <!-- info card start -->
            <div class="card rounded-card user-card">
                <div class="card-header">
                    <h5>Lista de Funcionarios </h5>
                    <span></span>
                    <div class="card-header-right">
                        <i class="icofont icofont-rounded-down"></i>
                    </div>
                </div>

                <%if (!funcionarios.isEmpty()) {%>
                <div class="card-block">
                    <div class="dt-responsive table-responsive">
                        <table id="responsive-reorder"
                               class="table table-striped table-bordered nowrap">
                            <thead>
                                <tr>
                                    <th>Nome Completo</th>
                                    <th>Contacto</th>
                                    <th>Email</th>
                                    <th>Departamento</th>
                                    <th>Cargo</th>
                                    <th>Grau</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Cta f : funcionarios) {%>
                                <tr>
                                    <td><%=f.getUtilizador1().getNome()%></td>
                                    <td><%= f.getUtilizador1().getContacto()%></td>
                                    <td><%= f.getUtilizador1().getEmail()%></td>
                                    <td><%=f.getUtilizador1().getDepartamento().getSigla()%></td>
                                    <td><%=f.getCargo().getDescricao()%></td>
                                    <td><%=f.getGrau()%></td>
                                </tr>
                                <%}%>
                            </tbody>
                            <%}%>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane" id="cadastrar" role="tabpanel">
              <form action="/sirequerimentos/utilizadores" method="post" class="j-pro">
                    <div class="j-content">
                        <!-- start name -->
                        <div class="j-unit">
                            <label class="j-label">Nome</label>
                            <div class="j-input">
                                <label class="j-icon-right" for="nome">
                                    <i class="icofont icofont-ui-user"></i>
                                </label>
                                <input type="text" id="nome" name="nome">
                            </div>
                        </div>
                        <!-- end name -->
                        <!-- start email phone -->
                        <div class="j-row">
                            <div class="j-span6 j-unit">
                                <label class="j-label">Sexo</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="sexo">
                                        <i class="icofont icofont-ui-user"></i>
                                    </label>
                                    <select name="sexo">
                                        <option>Seleccionar o sexo...</option>
                                        <option value="M">Masculino</option>
                                        <option value="F">Feminino</option>
                                    </select>
                                </div>
                            </div>
                            <div class="j-span6 j-unit">
                                <label class="j-label">Contacto</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="contacto">
                                        <i class="icofont icofont-phone"></i>
                                    </label>
                                    <input type="text" id="contacto" name="contacto">
                                </div>
                            </div>
                        </div>
                        <!-- end email phone -->
                        <div class="j-divider j-gap-bottom-25"></div>
                        <!-- start date -->
                        <div class="j-row">
                            <div class="j-span6 j-unit">
                                <label class="j-label">Departamento</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="departamento">
                                        <i class="icofont icofont-ui-calendar"></i>
                                    </label>
                                    <select name="departamento">
                                        <option>Seleccionar o departamento...</option>
                                        <%for (Departamento d : departamentos) {%>
                                        <option value="<%=d.getId()%>"><%out.print(d.getDescricao());%></option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="j-span6 j-unit">
                                <label class="j-label">Tipo de utilizador</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="tipo_utilizador">
                                        <i class="icofont icofont-ui-calendar"></i>
                                    </label>
                                    <select name="tipo_utilizador">
                                        <option>Seleccionar o tipo...</option>
                                        <%for (TipoUtilizador tu : tiposUtilizadores) {%>
                                        <option value="<%=tu.getId()%>"><%out.print(tu.getDescricao());%></option>
                                        <%}%>
                                    </select>
                                    <input type="hidden" name="utilizador" value="<%=utilizador.getId()%>">
                                </div>
                            </div>
                        </div>
                        <div class="j-row">
                            <div class="j-span6 j-unit">
                                <label class="j-label">Curso(* se for estudante)</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="curso">
                                        <i class="icofont icofont-ui-calendar"></i>
                                    </label>
                                    <select name="curso">
                                        <option>Seleccionar o curso...</option>
                                        <%for (Curso c : cursos) {%>
                                        <option value="<%=c.getId()%>"><%out.print(c.getDescricao());%></option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="j-span6 j-unit">
                                <label class="j-label">Cargo(*se nao for estudante)</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="cargo">
                                        <i class="icofont icofont-ui-calendar"></i>
                                    </label>
                                    <select name="cargo">
                                        <option>Seleccionar o Cargo...</option>
                                        <%for (Cargo c : cargos) {%>
                                        <option value="<%=c.getId()%>"><%out.print(c.getDescricao());%></option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                        </div>  
                        <div class="j-row">
                            <div class="j-span6 j-unit">
                                <label class="j-label">Nivel(* se for estudante)</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="nivel">
                                        <i class="icofont icofont-ui-check"></i>
                                    </label>
                                    <select name="nivel">
                                        <option>Seleccionar o nivel...</option>

                                        <option value="1">Primeiro Ano</option>
                                        <option value="2">Segundo Ano</option>
                                        <option value="3">Terceiro Ano</option>
                                        <option value="4">Quarto Ano</option>
                                        <option value="5">Quinto Ano</option>

                                    </select>
                                </div>
                            </div>
                            <div class="j-span6 j-unit">
                                <label class="j-label">Faculdade(* se for estudante ou secretario)</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="faculdade">
                                        <i class="icofont icofont-ui-home"></i>
                                    </label>
                                    <select name="faculdade">
                                        <option>Seleccionar Faculdade...</option>
                                        <%for (Faculdade f : faculdades) {%>
                                        <option value="<%=f.getId()%>"><%out.print(f.getDescricao());%></option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>        
                            <div class="j-span6 j-unit">
                                <label class="j-label">Grau(*se nao for estudante)</label>
                                <div class="j-input">
                                    <label class="j-icon-right" for="grau">
                                        <i class="icofont icofont-ui-clock"></i>
                                    </label>
                                    <select name="grau">
                                        <option>Seleccionar o grau...</option>
                                        <option value="Mestre">Medio</option>
                                        <option value="Licenciado">Licenciado</option>
                                        <option value="Doutor">Doutor</option>


                                    </select>
                                </div>
                            </div>
                        </div>             

                    </div>
                    <!-- end /.content -->
                    <div class="j-footer">
                        <button type="submit" class="btn btn-primary">Cadastrar</button>
                    </div>
                    <!-- end /.footer -->
                </form>
        </div>
    </div>

</div>

<jsp:include page="footer.jsp" /> 

