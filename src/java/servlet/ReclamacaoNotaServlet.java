package servlet;

import controller.EstadoJpaController;
import controller.MovimentoJpaController;
import controller.PedidoJpaController;
import controller.ReclamacaoNotaJpaController;
import controller.TipoMovimentoJpaController;
import controller.TipoPedidoJpaController;
import controller.UtilizadorJpaController;
import java.io.IOException;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Estado;
import modelo.Movimento;
import modelo.Pedido;
import modelo.ReclamacaoNota;
import modelo.TipoMovimento;
import modelo.TipoPedido;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class ReclamacaoNotaServlet extends HttpServlet {

    /**
     *
     */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");

    /**
     *
     */
    ReclamacaoNotaJpaController cartaoEstudanteController = new ReclamacaoNotaJpaController(emf);

    /**
     *
     */
    PedidoJpaController pedidoController = new PedidoJpaController(emf);

    /**
     *
     */
    MovimentoJpaController movimentoController = new MovimentoJpaController(emf);

    /**
     *
     */
    UtilizadorJpaController utilizadorController = new UtilizadorJpaController(emf);

    /**
     *
     */
    EstadoJpaController estadoController = new EstadoJpaController(emf);

    /**
     *
     */
    TipoPedidoJpaController tipoPedidoController = new TipoPedidoJpaController(emf);

    /**
     *
     */
    TipoMovimentoJpaController tipoMovimentoController = new TipoMovimentoJpaController(emf);

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
        if (request.getParameterMap().isEmpty()) {
            request.getRequestDispatcher("reclamacao_de_nota_de_teste_exame.jsp").forward(request, response);
        } else {

            request.getRequestDispatcher("reclamacao_de_nota_de_teste_exame.jsp").forward(request, response);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Pedido pedido = new Pedido();
        ReclamacaoNota reclamacaoNota = new ReclamacaoNota();
        Movimento movimento = new Movimento();
// Recupere os parâmetros do formulário
        String utilizadorSTR = request.getParameter("utilizador");
        Long utilizadorId = Long.valueOf(utilizadorSTR);
        Utilizador utilizador = utilizadorController.findUtilizador(utilizadorId);

        TypedQuery<Estado> estadoQuery = estadoController.getEntityManager().createNamedQuery("Estado.findByDescricao", Estado.class);
        estadoQuery.setParameter("descricao", "Em Processamento");
        Estado estado = estadoQuery.getSingleResult();

        //Estado estado = estadoController.findEstado(Long.valueOf("4"));
        TypedQuery<TipoPedido> tipoPedidoQuery = tipoPedidoController.getEntityManager().createNamedQuery("TipoPedido.findByDescricao", TipoPedido.class);
        tipoPedidoQuery.setParameter("descricao", "Reclamação de Nota de Teste/Exame");
        TipoPedido tipoPedido = tipoPedidoQuery.getSingleResult();

        TypedQuery<TipoMovimento> tipoMovimentoQuery = tipoMovimentoController.getEntityManager().createNamedQuery("TipoMovimento.findByDescricao", TipoMovimento.class);
        tipoMovimentoQuery.setParameter("descricao", "Requerimento de "+tipoPedido.getDescricao());
        TipoMovimento tipoMovimento = tipoMovimentoQuery.getSingleResult();

        String justificativa = request.getParameter("justificativa");

        // Restante do código...
        pedido.setUtilizador(utilizador);
        pedido.setAssunto("Pedido "+tipoPedido.getDescricao());
        pedido.setDataPedido(new Date());
        pedido.setJustifiativa(justificativa);
        pedido.setTipoPedido(tipoPedido);
        pedido.setEstado(estado);
        
        pedidoController.create(pedido);
        reclamacaoNota.setPedido(pedido);
        
        cartaoEstudanteController.create(reclamacaoNota);

        movimento.setUtilizador(utilizador);
        movimento.setDataMovimento(new Date());
        movimento.setObservacao(tipoPedido.getDescricao());
        movimento.setTipoMovimento(tipoMovimento);
        movimento.setPedido(pedido);
        movimentoController.create(movimento);

        response.sendRedirect("reclamacao_de_nota_de_teste_exame.jsp");
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
