package modelo;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Meldo Maunze
 */

public class PrincipalServlet extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
    EntityManager em = emf.createEntityManager();

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
/*
        String anulacaoMatricula = "SELECT COUNT(a) FROM AnulacaoMatricula a WHERE a.estado.descricao = :descricao";
        TypedQuery<Long> anulacoes = em.createQuery(anulacaoMatricula, Long.class);
        anulacoes.setParameter("descricao", "Em Processamento");
        Long anulacaoMatriculaActive = anulacoes.getSingleResult();
       
        
        HttpSession session = request.getSession();
        session.setAttribute("anulacaoMatriculaActive", anulacaoMatriculaActive);
       */
        if (request.getParameterMap().isEmpty()) {

            // Redirecione para a página obras.jsp
            //request.setAttribute("anulacaoMatriculaActive", anulacaoMatriculaActive); // Adicione o atributo à requisição
         
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            // Lógica para o caso em que parâmetros foram passados
            // ...
            request.getRequestDispatcher("login.jsp").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
