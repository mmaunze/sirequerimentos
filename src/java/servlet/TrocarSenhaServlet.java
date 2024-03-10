
package servlet;

import controller.UtilizadorJpaController;
import controller.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class TrocarSenhaServlet extends HttpServlet {

    /**
     *
     */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");

    /**
     *
     */
    UtilizadorJpaController utilizadores = new UtilizadorJpaController(emf);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String user = request.getParameter("user");
      String senhaAntiga = request.getParameter("senhaAntiga");
      String novaSenha = request.getParameter("novaSenha");
      String confirmarSenha = request.getParameter("confirmarSenha");
      Long userValue = Long.valueOf(user);
      Utilizador utilizador = utilizadores.findUtilizador(userValue);
      
      if (senhaAntiga.equals(utilizador.getSenha())){
          if (novaSenha.equals(confirmarSenha) && !novaSenha.isBlank()){
              utilizador.setSenha(novaSenha);
              try {
                  utilizadores.edit(utilizador);
                   response.sendRedirect("sair");
              } catch (NonexistentEntityException ex) {
                  Logger.getLogger(TrocarSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
              } catch (Exception ex) {
                  Logger.getLogger(TrocarSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      }
      else {
          response.sendRedirect("conta.jsp");
      }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
