package servlet;

import controller.CargoJpaController;
import controller.CtaJpaController;
import controller.CursoJpaController;
import controller.DepartamentoJpaController;
import controller.EstudanteJpaController;
import controller.FaculdadeJpaController;
import controller.SecretarioJpaController;
import controller.TipoUtilizadorJpaController;
import controller.UtilizadorJpaController;
import controller.exceptions.PreexistingEntityException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cargo;
import modelo.Cta;
import modelo.Curso;
import modelo.Departamento;
import modelo.Estudante;
import modelo.Faculdade;
import modelo.Pedido;
import modelo.Secretario;
import modelo.TipoUtilizador;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class UtilizadorServlet extends HttpServlet {

    /**
     *
     */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");

    /**
     *
     */
    EntityManager em = emf.createEntityManager();

    /**
     *
     */
    CursoJpaController cursoController = new CursoJpaController(emf);

    /**
     *
     */
    FaculdadeJpaController faculdadeController = new FaculdadeJpaController(emf);

    /**
     *
     */
    DepartamentoJpaController departamentoController = new DepartamentoJpaController(emf);

    /**
     *
     */
    CargoJpaController cargoController = new CargoJpaController(emf);

    /**
     *
     */
    TipoUtilizadorJpaController tipoUtilizadorController = new TipoUtilizadorJpaController(emf);

    /**
     *
     */
    EstudanteJpaController estudanteController = new EstudanteJpaController(emf);

    /**
     *
     */
    CtaJpaController funcionarioController = new CtaJpaController(emf);

    /**
     *
     */
    SecretarioJpaController secretarioJpaControllerController = new SecretarioJpaController(emf);

    /**
     *
     */
    UtilizadorJpaController utilizadores = new UtilizadorJpaController(emf);

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String utenteParameter = request.getParameter("utente");

        if (request.getParameterMap().isEmpty()) {
            // Redirecione para a página obras.jsp
            request.getRequestDispatcher("utilizadores.jsp").forward(request, response);
        } else {
            // Lógica para o caso em que parâmetros foram passados
            // ...
            if (utenteParameter != null) {
                try {
                    // Faça o parse do parâmetro "utente" para um tipo Long (supondo que seja um número)
                    Long utenteId = Long.valueOf(utenteParameter);

                    // Substitua 'utilizadoresController' pelo seu controlador real
                    Utilizador utente = utilizadores.findUtilizador(utenteId);

                    if (utente != null) {
                        // Defina o objeto 'utilizador' como um atributo da requisição
                        request.setAttribute("utente", utente);
                        String emprestimo = "SELECT e FROM Pedido e WHERE e.utilizador.id = :id";
                        TypedQuery<Pedido> emprestimos = em.createQuery(emprestimo, Pedido.class);
                        emprestimos.setParameter("id", utenteId);
                        List<Pedido> listaEmprestimosUtente = emprestimos.getResultList();

                        // Encaminhe a requisição para a página 'utente.jsp'
                        request.setAttribute("listaEmprestimosUtente", listaEmprestimosUtente);

                        request.getRequestDispatcher("utente.jsp").forward(request, response);
                    } else {
                        // Lidere com o caso em que o utente não foi encontrado
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                } catch (NumberFormatException e) {
                    // Lidere com o caso em que o parâmetro 'utente' não é um número válido
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String grau = request.getParameter("grau");
        String nome = request.getParameter("nome");
        String sexoStr = request.getParameter("sexo");
        char sexo = sexoStr != null && !sexoStr.isEmpty() ? sexoStr.charAt(0) : ' '; // valor

        String contactoStr = request.getParameter("contacto");
        long contacto = contactoStr != null ? Long.parseLong(contactoStr) : 0L; // valor padrão

        String departamentoStr = request.getParameter("departamento");
        long departamentoValue = departamentoStr != null ? Long.parseLong(departamentoStr) : 0L; // valor padrão
        Departamento departamento = departamentoController.findDepartamento(departamentoValue);

        String tipoUtilizadorStr = request.getParameter("tipo_utilizador");
        long tipo_utilizadorValue = tipoUtilizadorStr != null ? Long.parseLong(tipoUtilizadorStr) : 0L; // valor padrão
        TipoUtilizador tipoUtilizador = tipoUtilizadorController.findTipoUtilizador(tipo_utilizadorValue);

        String cursoSTR = request.getParameter("curso");
        long cursoValue = cursoSTR != null ? Long.parseLong(cursoSTR) : 0L; // valor padrão
        Curso curso = cursoController.findCurso(cursoValue);

        String cargoSTR = request.getParameter("cargo");
        long cargoValue = cargoSTR != null ? Long.parseLong(cargoSTR) : 0L; // valor padrão
        Cargo cargo = cargoController.findCargo(cargoValue);

        String faculdadeSTR = request.getParameter("faculdade");
        long faculdadeValue = faculdadeSTR != null ? Long.parseLong(faculdadeSTR) : 0L; // valor padrão
        Faculdade faculdade = faculdadeController.findFaculdade(faculdadeValue);

        String nivelSTR = request.getParameter("nivel");
        long nivelValue = nivelSTR != null ? Long.parseLong(nivelSTR) : 0L; // valor padrão

        Utilizador utilizador = new Utilizador();
        long id = gerarIdUsuario();
        String username = gerarUsername(nome, id);
        utilizador.setId(id);
        utilizador.setNome(nome);
        utilizador.setSexo(sexo);
        utilizador.setContacto(contacto);
        utilizador.setTipoUtilizador(tipoUtilizador);
        utilizador.setDepartamento(departamento);
        utilizador.setUsername(username);
        utilizador.setEmail(username + "@email.com");
        utilizador.setSenha(username);
        
        try {
            utilizadores.create(utilizador);
        } catch (Exception ex) {
            Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      //Secretario
        if (tipoUtilizador.getDescricao().equalsIgnoreCase("Secretário")) {
            Secretario secretario = new Secretario();
            secretario.setFaculdade(faculdade);
            secretario.setUtilizador(utilizador.getId());
            secretario.setUtilizador1(utilizador);
            try {
                secretarioJpaControllerController.create(secretario);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
  //Estudante 
        if (tipoUtilizador.getDescricao().equalsIgnoreCase("estudante")) {
            Estudante estudante = new Estudante();
            estudante.setNivel(nivelValue);
            estudante.setCurso(curso);
            estudante.setUtilizador1(utilizador);
            estudante.setUtilizador(utilizador.getId());
            try {
                estudanteController.create(estudante);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Cta cta = new Cta();
            cta.setCargo(cargo);
            cta.setUtilizador(utilizador.getId());
            cta.setUtilizador1(utilizador);
            cta.setGrau(grau);
            try {
                funcionarioController.create(cta);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UtilizadorServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        response.sendRedirect("utilizadores");

    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /**
     *
     * @return
     */
    private long gerarIdUsuario() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String idUsuarioStr = agora.format(formatter);
        long idUsuario = Long.parseLong(idUsuarioStr);
        return idUsuario;
    }

    /**
     *
     * @param nomeCompleto
     * @param idUsuario
     * @return
     */
    private String gerarUsername(String nomeCompleto, long idUsuario) {
        String[] partes = nomeCompleto.split(" ");
        String nome = partes[0];
        String apelido = partes[partes.length - 1];

        String username = nome.charAt(0) + apelido; // primeira letra do nome + apelido

        if (usernameExiste(username, em)) {
            username = nome + "." + apelido; // nome.apelido

            if (usernameExiste(username, em)) {
                username = apelido + "." + nome; // apelido.nome

                if (usernameExiste(username, em)) {
                    username = apelido.charAt(0) + "." + nome; // primeira letra do apelido.nome

                    if (usernameExiste(username, em)) {
                        username = apelido + idUsuario; // apelido + id do usuário
                    }
                }
            }
        }

        return username.toLowerCase();
    }

    /**
     *
     * @param username
     * @param em
     * @return
     */
    private boolean usernameExiste(String username, EntityManager em) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM Utilizador u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        long count = query.getSingleResult();
        return count > 0;
    }

}
