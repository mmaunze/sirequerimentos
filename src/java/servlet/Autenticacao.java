package servlet;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class Autenticacao extends HttpServlet {

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
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String senha = request.getParameter("senha");

    // Validação de entrada
    if (username == null || senha == null || username.isEmpty() || senha.isEmpty()) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    // Verificar a autenticação do usuário no banco de dados
    Utilizador utilizador = null;
    
    // Utilizar um pool de conexões para melhorar o desempenho
    EntityManagerFactory emf = null;
    EntityManager em = null;

    try {
        emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
        em = emf.createEntityManager();

        // Execute uma consulta JPQL para verificar a autenticação do usuário
        String jpql = "SELECT u FROM Utilizador u WHERE u.username = :username AND u.senha = :senha";
        TypedQuery<Utilizador> query = em.createQuery(jpql, Utilizador.class);
        query.setParameter("username", username);
        query.setParameter("senha", senha);

        // Obtenha o primeiro resultado ou null se não houver resultados
        utilizador = query.getResultStream().findFirst().orElse(null);

        if (utilizador != null) {
            // Autenticação bem-sucedida
            HttpSession session = request.getSession();
            session.setAttribute("utilizador", utilizador);
            // Redirecione para a página apropriada com base no tipo de usuário
            response.sendRedirect("inicio");

        } else {
            // Autenticação falhou
            request.setAttribute("erro", "Nome de usuário ou senha incorretos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    } catch (IOException | ServletException e) {
        // Trate a exceção aqui, por exemplo, registre-a ou redirecione para uma página de erro.
        // Isso imprime a exceção no console, mas você pode fazer algo mais apropriado.
        
    } finally {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
