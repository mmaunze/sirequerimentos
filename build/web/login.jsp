
<%@page import="modelo.Utilizador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   Utilizador utilizador = (Utilizador) session.getAttribute("utilizador");
    if (utilizador != null) {
        if (utilizador.getTipoUtilizador().getDescricao().equalsIgnoreCase("administrador")) {
            response.sendRedirect("/sirequerimentos/inicio");
        } else {
            response.sendRedirect("/sirequerimentos/inicio");
        }
        return;
    }
%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="pt-pt">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <title>Autenticacao || sirequerimentos</title>
        <link rel="stylesheet" type="text/css" href="assets/css/login.css">
    <link rel="icon" href="assets/images/unilurio.png" type="image/x-icon">
        
    </head>

    <body>
        <div class="formulario">
            <form action="autenticacao" method="post">
                <div class="campo username">
                    <div class="entrada">
                        <input type="text" placeholder="username" name="username">
                        <i class="icon bx bx-user"></i>
                        <i class="error error-icon bx bx-exclamation-circle"></i>
                    </div>
                    <div class="error erro">username não pode estar vazio</div>
                </div>

                <div class="campo senha">
                    <div class="entrada">
                        <input type="password" placeholder="senha" name="senha">
                        <i class="icon bx bx-lock"></i>
                        <i class="error error-icon bx bx-exclamation-circle"></i>
                    </div>
                    <div class="error erro">senha não pode estar vazia</div>
                </div>

                <div class="pass-txt"><a href="#">Recuperar senha</a></div>
                <input type="submit" value="Entrar">
                <div class="">
    <c:if test="${not empty erro}">
        <br>
        <p class="danger-text">${erro}</p>
    </c:if>
</div>

            </form>
        </div>

<script src="assets/js/login.js"></script>

    </body>

</html>
