package servlet;

import controller.DeclaracaoJpaController;
import controller.EstadoJpaController;
import controller.MovimentoJpaController;
import controller.PedidoJpaController;
import controller.TipoDelacaraoJpaController;
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
import modelo.Declaracao;
import modelo.Estado;
import modelo.Movimento;
import modelo.Pedido;
import modelo.TipoDelacarao;
import modelo.TipoMovimento;
import modelo.TipoPedido;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class DeclaracaoDiscriminativaNotasServlet extends HttpServlet {

    /**
     *
     */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");

    /**
     *
     */
    DeclaracaoJpaController declaracaoNotasController = new DeclaracaoJpaController(emf);

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
    TipoDelacaraoJpaController tipoDeclaracaoController = new TipoDelacaraoJpaController(emf);

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
            request.getRequestDispatcher("declaracao_discriminativa_de_notas.jsp").forward(request, response);
        } else {

            request.getRequestDispatcher("declaracao_discriminativa_de_notas.jsp").forward(request, response);

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
        Declaracao declaracao = new Declaracao();
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
        tipoPedidoQuery.setParameter("descricao", "Declaração Discriminativa de Notas");
        TipoPedido tipoPedido = tipoPedidoQuery.getSingleResult();
        
        TypedQuery<TipoDelacarao> tipoDeclaracaoQuery = tipoDeclaracaoController.getEntityManager().createNamedQuery("TipoDelacarao.findByDescricao", TipoDelacarao.class);
        tipoDeclaracaoQuery.setParameter("descricao", "Declaração Discriminativa de Notas");
        TipoDelacarao tipoDelacarao = tipoDeclaracaoQuery.getSingleResult();


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
        declaracao.setPedido(pedido);
        declaracao.setTipoDelacarao(tipoDelacarao);
        
        declaracaoNotasController.create(declaracao);

        movimento.setUtilizador(utilizador);
        movimento.setDataMovimento(new Date());
        movimento.setObservacao(tipoPedido.getDescricao());
        movimento.setTipoMovimento(tipoMovimento);
        movimento.setPedido(pedido);
        movimentoController.create(movimento);

        response.sendRedirect("declaracao_discriminativa_de_notas.jsp");
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
