package servlet;

import controller.GeneratePdf;
import controller.PedidoJpaController;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Pedido;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class gerarPDfServlet extends HttpServlet {

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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
        HttpSession sessao = request.getSession();

        Utilizador u = (Utilizador) sessao.getAttribute("utilizador");
        if (u != null) {

            PedidoJpaController pedidos = new PedidoJpaController(emf);
            Pedido pedido = pedidos.findPedido(Long.valueOf(request.getParameter("pedido")));
            GeneratePdf gerarPDF = new GeneratePdf();

            try {

                if (!u.getTipoUtilizador().getDescricao().equalsIgnoreCase("Administador")
                        || u.getTipoUtilizador().getDescricao().equalsIgnoreCase("Secretário")) {
                    if (Objects.equals(u.getId(), pedido.getUtilizador().getId())) {
                        gerarPDF.gerarPdf(pedido, response);
                      
                    }
                    else
                         response.sendRedirect("inicio");
                }
                else
                   gerarPDF.gerarPdf(pedido, response);

            } catch (Exception ex) {
                Logger.getLogger(gerarPDfServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.sendRedirect("inicio");
        }
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
