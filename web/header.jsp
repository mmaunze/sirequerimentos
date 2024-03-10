<%
// Verificar se o atributo de sess�o est� presente
    if (session == null || session.getAttribute("utilizador") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<%@page import="modelo.Utilizador"%>
<!DOCTYPE html>
<%
    // Obt�m o nome do arquivo JSP atual
    String nomeArquivo = request.getServletPath();
    nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf('/') + 1, nomeArquivo.lastIndexOf('.'));
    String titulo = "";
    Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");

    // Verifica se o nome do arquivo � "index"
    if (nomeArquivo.equalsIgnoreCase("anulacao_de_matricula")) {
        titulo = "Anula��o de Matr�cula";
    }
    else if (nomeArquivo.equalsIgnoreCase("cartao_de_estudante_2_via")) {
        titulo = "Cart�o de Estudante 2� via";
    }
     else if (nomeArquivo.equalsIgnoreCase("declaracao_discriminativa_de_notas")) {
        titulo = "Declara��o Discriminativa de Notas";
    }
    else if (nomeArquivo.equalsIgnoreCase("index")) {
        titulo = "Pagina Inicial";
    } else {
        String primeiraLetra = nomeArquivo.substring(0, 1).toUpperCase();
        String restoDaString = nomeArquivo.substring(1);
        titulo = primeiraLetra + restoDaString;
    }
%>
<html lang="pt">
    <head>
        <title><%=titulo%> - SIstema de Requerimentos</title>
        <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="#">
    <meta name="author" content="meldo leonardo maunze">

    <!-- Favicon icon -->
    <link rel="icon" href="assets/images/unilurio.png" type="image/x-icon">

    <!-- Required Framework -->
    <link rel="stylesheet" type="text/css" href="components/bootstrap/css/bootstrap.min.css">
    
    <!-- Icon Fonts -->
    <link rel="stylesheet" type="text/css" href="assets/icon/themify-icons/themify-icons.css">
    <link rel="stylesheet" type="text/css" href="assets/icon/icofont/css/icofont.css">
    <link rel="stylesheet" type="text/css" href="assets/icon/feather/css/feather.css">
    
    <!-- Menu-Search CSS -->
    <link rel="stylesheet" type="text/css" href="assets/pages/menu-search/css/component.css">
    
    <!-- jpro Forms CSS -->
    <link rel="stylesheet" type="text/css" href="assets/pages/j-pro/css/demo.css">
    <link rel="stylesheet" type="text css" href="assets/pages/j-pro/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="assets/pages/j-pro/css/j-pro-modern.css">
    <link rel="stylesheet" type="text/css" href="assets/pages/j-pro/css/j-forms.css">
    <link rel="stylesheet" type="text/css" href="components/switchery/css/switchery.min.css">
    
    <!-- Select 2 CSS -->
    <link rel="stylesheet" href="select2/css/select2.min.css">
    
    <!-- Multi-Select CSS -->
    <link rel="stylesheet" type="text/css" href="bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css" href="multiselect/css/multi-select.css">
    <link rel="stylesheet" type="text/css" href="components/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css" href="components/multiselect/css/multi-select.css">
    
    <!-- Notify.js Framework -->
    <link rel="stylesheet" type="text/css" href="components/pnotify/css/pnotify.css">
    <link rel="stylesheet" type="text/css" href="components/pnotify/css/pnotify.brighttheme.css">
    <link rel="stylesheet" type="text/css" href="components/pnotify/css/pnotify.buttons.css">
    <link rel="stylesheet" type="text/css" href="components/pnotify/css/pnotify.history.css">
    <link rel="stylesheet" type="text/css" href="components/pnotify/css/pnotify.mobile.css">
    <link rel="stylesheet" type="text/css" href="assets/pages/pnotify/notify.css">
    
    <!-- Style.css -->
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    
    <!-- Color CSS -->
    <link rel="stylesheet" type="text/css" href="assets/css/linearicons.css">
    <link rel="stylesheet" type="text/css" href="assets/css/simple-line-icons.css">
    <link rel="stylesheet" type="text/css" href="assets/css/ionicons.css">
    <link rel="stylesheet" type="text/css" href="assets/css/jquery.mCustomScrollbar.css">
    
    <!-- Data Table CSS -->
    <link rel="stylesheet" type="text/css" href="components/datatables.net-bs4/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="assets/pages/data-table/css/buttons.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="components/datatables.net-responsive-bs4/css/responsive.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="assets/pages/data-table/extensions/responsive/css/responsive.dataTables.css">
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </head>
    <body>
        <!-- Pre-loader start -->
        <div class="theme-loader">
            <div class="loader-block">
                <svg id="loader2" viewbox="0 0 100 100">
                <circle id="circle-loader2" cx="50" cy="50" r="50"></circle>
                </svg>
            </div>
        </div>
    </div>
    <div id="pcoded" class="pcoded">
        <div class="pcoded-overlay-box"></div>
        <div class="pcoded-container navbar-wrapper">
            <nav class="navbar header-navbar pcoded-header">
                <div class="navbar-wrapper">
                    <div class="navbar-logo" data-navbar-theme="theme4">
                        <a class="mobile-menu" id="mobile-collapse" href="#!">
                            <i class="ti-menu"></i>
                        </a>
                        <a href="javascrip:void(0)" class="logo-text">
                            <h4>sirequerimentos</h4>
                        </a>
                        <a class="mobile-options">
                            <i class="ti-angle-down"></i>
                        </a>
                    </div>
                    <div class="navbar-container container-fluid">
                        <div>
                            <ul class="nav-left">
                                <li>
                                    <div class="sidebar_toggle"><a href="javascript:void(0)"><i class="ti-menu"></i></a>
                                    </div>
                                </li>
                            </ul>
                            <ul class="nav-right">
                                <li class="user-profile header-notification">
                                    <a href="#!">
                                        <img class="img-fluid" src="assets/images/perfil.png" alt="Logo">
                                        <span><%= utilizador.getNome()%></span>
                                        <i class="ti-angle-down"></i>
                                    </a>
                                    <ul class="show-notification profile-notification">
                                        <li>
                                            <a href="conta.jsp">
                                                <i class="ti-user"></i> Minha Conta
                                            </a>
                                        </li>
                                        <li>
                                            <a href="sair">
                                                <i class="ti-layout-sidebar-left"></i> Sair do Sistema
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </nav>