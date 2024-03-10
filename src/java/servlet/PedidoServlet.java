package servlet;

import java.util.logging.Level;
import java.util.logging.Logger;
import controller.EstadoJpaController;
import controller.MovimentoJpaController;
import controller.PedidoJpaController;
import controller.SecretarioJpaController;
import controller.TipoMovimentoJpaController;
import controller.TratamentoPedidoJpaController;
import controller.UtilizadorJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Estado;
import modelo.Movimento;
import modelo.Pedido;
import modelo.Secretario;
import modelo.TipoMovimento;
import modelo.TratamentoPedido;
import modelo.Utilizador;

/**
 *
 * @author Meldo Maunze
 */
public class PedidoServlet extends HttpServlet {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaSubmissaoRequereimentosPU");
    PedidoJpaController pedidoController = new PedidoJpaController(emf);
    MovimentoJpaController movimentoController = new MovimentoJpaController(emf);
    UtilizadorJpaController utilizadorController = new UtilizadorJpaController(emf);
    SecretarioJpaController secretarioController = new SecretarioJpaController(emf);
    TratamentoPedidoJpaController tratamentoPedidoController = new TratamentoPedidoJpaController(emf);
    EstadoJpaController estadoController = new EstadoJpaController(emf);
    TipoMovimentoJpaController tipoMovimentoController = new TipoMovimentoJpaController(emf);
    SimpleDateFormat dia = new SimpleDateFormat("dd MMMM yyyy ");
    SimpleDateFormat hora = new SimpleDateFormat("HH:MM:SS ");

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String utenteParameter = request.getParameter("pedido");
        
        if (request.getParameterMap().isEmpty()) {
            // Redirecione para a página obras.jsp
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            // Lógica para o caso em que parâmetros foram passados
            // ...
            if (utenteParameter != null) {
                try {
                    // Faça o parse do parâmetro "utente" para um tipo Long (supondo que seja um número)
                    Long pedidoId = Long.valueOf(utenteParameter);

                    // Substitua 'utilizadoresController' pelo seu controlador real
                    Pedido pedido = pedidoController.findPedido(pedidoId);
                    
                    if (pedido != null) {
                        // Defina o objeto 'utilizador' como um atributo da requisição
                        request.setAttribute("pedido", pedido);
                        
                        request.getRequestDispatcher("pedido.jsp").forward(request, response);
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Entrando no Servlet");
        
        HttpSession sessao = request.getSession();
        
        Utilizador u = (Utilizador) sessao.getAttribute("utilizador");
        
      if (u != null){
            long pedidoID = 0, idutilizador = 0;
            long secretarioID = 0;
            if (request.getParameter("pedido") != null) {
                pedidoID = Long.parseLong(request.getParameter("pedido"));
                System.out.println("Recebido o prametro pedido " + pedidoID);
            }
            
            if (request.getParameter("utilizador") != null) {
                idutilizador = Long.parseLong(request.getParameter("utilizador"));
                System.out.println("Recebido o prametro utilizador " + idutilizador);
            }
            
            if (request.getParameter("secretario") != null) {
                secretarioID = Long.parseLong(request.getParameter("secretario"));
                System.out.println("Recebido o prametro secretario " + secretarioID);
            }
            
            if (request.getParameter("idreserva") != null) {
                secretarioID = Long.parseLong(request.getParameter("secretario"));
                System.out.println("Recebido o prametro secretario " + secretarioID);
            }
            
            String accao = request.getParameter("accao");
            System.out.println("Teste o parametro accao");
            
            Date datamovimento = new Date();
            Pedido pedido = pedidoController.findPedido(pedidoID);
            System.out.println("" + pedido);
            Utilizador utilizador = utilizadorController.findUtilizador(idutilizador);
            System.out.println("" + utilizador);
            Secretario secretario = secretarioController.findSecretario(secretarioID);
            System.out.println("" + secretario);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            String jsonResponse;
            
               
            
            
            if (accao.equalsIgnoreCase("aprovar") && ( u.getTipoUtilizador().getDescricao().equalsIgnoreCase("Administador")
                || u.getTipoUtilizador().getDescricao().equalsIgnoreCase("Secretário")) ) {
                jsonResponse = aprovar(pedido, secretario, datamovimento);
                
            } else if (accao.equalsIgnoreCase("rejeitar") && ( u.getTipoUtilizador().getDescricao().equalsIgnoreCase("Administador")
                || u.getTipoUtilizador().getDescricao().equalsIgnoreCase("Secretário"))) {
                jsonResponse = rejeitar(pedido, secretario, datamovimento);
                
            } else if (accao.equalsIgnoreCase("cancelar") && (Objects.equals(u.getId(), pedido.getUtilizador().getId()))) {
                jsonResponse = cancelar(pedido, u);
                
            } else {
                String errorMessage = "Acção desconhecida.";

                // Construir a resposta JSON de erro
                jsonResponse = "{\"success\": false, \"error\": \"" + errorMessage + "\"}";
            }
            
            response.getWriter().write(jsonResponse);
        } else {
            response.sendRedirect("inicio");
        }
    }

    private String aprovar(Pedido pedido, Secretario secretario, Date datamovimento) {
//verificar se os dados nao sao nulos
        if (pedido != null && secretario != null) {
            System.out.println("verificar se os dados nao sao nulos");
            
            try {
                TratamentoPedido tratamentoPedido = new TratamentoPedido();
                tratamentoPedido.setDataTratamento(datamovimento);
                tratamentoPedido.setPedido(pedido);
                tratamentoPedido.setSecretario(secretario);
                tratamentoPedido.setObservacao("Aprovação do Pedido de " + pedido.getAssunto());
                // registar o tratamento do pedido
                System.out.println("registar o tratamento do pedido");
                tratamentoPedidoController.create(tratamentoPedido);
                TypedQuery<Estado> estadoQuery = estadoController.getEntityManager().createNamedQuery("Estado.findByDescricao", Estado.class);
                estadoQuery.setParameter("descricao", "Aprovado");
                Estado estado = estadoQuery.getSingleResult();
                
                pedido = tratamentoPedido.getPedido();
                pedido.setEstado(estado);

                //actualizar o estado do pedido
                System.out.println("actualizar o estado do pedido");
                pedidoController.edit(pedido);
                
                Movimento movimento = new Movimento();
                TypedQuery<TipoMovimento> tipoMovimentoQuery = tipoMovimentoController.getEntityManager().createNamedQuery("TipoMovimento.findByDescricao", TipoMovimento.class);
                tipoMovimentoQuery.setParameter("descricao", "Requerimento de Anulação de Matrícula");
                TipoMovimento tipoMovimento = tipoMovimentoQuery.getSingleResult();
                
                movimento.setPedido(pedido);
                movimento.setUtilizador(secretario.getUtilizador1());
                movimento.setDataMovimento(datamovimento);
                movimento.setObservacao("Aprovação de Anulação de Matrícula");

                //registar o tipo de movimento
                System.out.println("registar o tipo de movimento");
                movimento.setTipoMovimento(tipoMovimento);

                //registar o movimento 
                System.out.println("registar o movimento ");
                movimentoController.create(movimento);
                
                System.out.println(" Operação de validação bem-sucedida");
                // Operação de validação bem-sucedida
                return "{\"success\": true, \"message\": \"Pedido validado com sucesso <br> Pedido: " + tratamentoPedido.getPedido().getId() + " <br> Despacho para utilizador " + tratamentoPedido.getPedido().getUtilizador().getNome() + " <br> Pelo " + tratamentoPedido.getSecretario().getUtilizador1().getNome() + "<br> Data:" + hora.format(tratamentoPedido.getDataTratamento()) + "\"} ";
            } catch (Exception ex) {
                Logger.getLogger(PedidoServlet.class.getName()).log(Level.SEVERE, null, ex);
                String errorMessage = "Não foi possível aprovar o pedido " + pedido.getId() + " ";
                String mensagem = "Erro ";
                // Construir a resposta JSON de erro
                return "{\"success\": false, \"error\": \"" + errorMessage + "<br>" + mensagem + "\"}";
                
            }
        } else {
            String errorMessage = "Parâmetros inválidos.";

            // Construir a resposta JSON de erro
            return "{\"success\": false, \"error\": \"" + errorMessage + "\"}";
        }
    }
    
    
    private String rejeitar(Pedido pedido, Secretario secretario, Date datamovimento) {
//verificar se os dados nao sao nulos
        if (pedido != null && secretario != null) {
            System.out.println("verificar se os dados nao sao nulos");
            
            try {
                TratamentoPedido tratamentoPedido = new TratamentoPedido();
                tratamentoPedido.setDataTratamento(datamovimento);
                tratamentoPedido.setPedido(pedido);
                tratamentoPedido.setSecretario(secretario);
                tratamentoPedido.setObservacao("Rejeição do Pedido de " + pedido.getAssunto());
                // registar o tratamento do pedido
                TypedQuery<Estado> estadoQuery = estadoController.getEntityManager().createNamedQuery("Estado.findByDescricao", Estado.class);
                estadoQuery.setParameter("descricao", "Rejeitado");
                Estado estado = estadoQuery.getSingleResult();
                pedido = tratamentoPedido.getPedido();
                pedido.setEstado(estado);
                pedidoController.edit(pedido);
                System.out.println("registar o tratamento do pedido");
                
                tratamentoPedidoController.create(tratamentoPedido);

                //actualizar o estado do pedido
                System.out.println("actualizar o estado do pedido" + pedido);
                
                Movimento movimento = new Movimento();
                TypedQuery<TipoMovimento> tipoMovimentoQuery = tipoMovimentoController.getEntityManager().createNamedQuery("TipoMovimento.findByDescricao", TipoMovimento.class);
                tipoMovimentoQuery.setParameter("descricao", "Requerimento de " + pedido.getTipoPedido().getDescricao());
                TipoMovimento tipoMovimento = tipoMovimentoQuery.getSingleResult();
                
                movimento.setPedido(pedido);
                movimento.setUtilizador(secretario.getUtilizador1());
                movimento.setDataMovimento(datamovimento);
                movimento.setObservacao("Rejeição de " + pedido.getTipoPedido().getDescricao());

                //registar o tipo de movimento
                System.out.println("registar o tipo de movimento");
                movimento.setTipoMovimento(tipoMovimento);

                //registar o movimento 
                System.out.println("registar o movimento ");
                movimentoController.create(movimento);
                
                System.out.println(" Operação de Rejeição bem-sucedida");
                // Operação de validação bem-sucedida
                return "{\"success\": true, \"message\": \"Pedido rejeitado com sucesso <br> Pedido: " + tratamentoPedido.getPedido().getId() + " <br> Despacho para utilizador " + tratamentoPedido.getPedido().getUtilizador().getNome() + " <br> Pelo " + tratamentoPedido.getSecretario().getUtilizador1().getNome() + "<br> Data:" + hora.format(tratamentoPedido.getDataTratamento()) + "\"} ";
            } catch (Exception ex) {
                Logger.getLogger(PedidoServlet.class.getName()).log(Level.SEVERE, null, ex);
                String errorMessage = "Não foi possível rejeitar o pedido " + pedido.getId() + " " + ex;
                String mensagem = "Erro ";
                // Construir a resposta JSON de erro
                return "{\"success\": false, \"error\": \"" + errorMessage + "<br>" + mensagem + "\"}";
                
            }
        } else {
            String errorMessage = "Parâmetros inválidos.";

            // Construir a resposta JSON de erro
            return "{\"success\": false, \"error\": \"" + errorMessage + "\"}";
        }
    }
    
    
    private String cancelar(Pedido pedido, Utilizador utilizador) {
//verificar se os dados nao sao nulos
        if (pedido != null && utilizador != null) {
            System.out.println("verificar se os dados nao sao nulos");
            
            try {

                // registar o tratamento do pedido
                TypedQuery<Estado> estadoQuery = estadoController.getEntityManager().createNamedQuery("Estado.findByDescricao", Estado.class);
                estadoQuery.setParameter("descricao", "Cancelado");
                Estado estado = estadoQuery.getSingleResult();
                pedido.setEstado(estado);
                pedidoController.edit(pedido);
                System.out.println("Cancelar pedido");

                //actualizar o estado do pedido
                System.out.println("actualizar o estado do pedido" + pedido);
                
                Movimento movimento = new Movimento();
                TypedQuery<TipoMovimento> tipoMovimentoQuery = tipoMovimentoController.getEntityManager().createNamedQuery("TipoMovimento.findByDescricao", TipoMovimento.class);
                tipoMovimentoQuery.setParameter("descricao", "Requerimento de Anulação de Matrícula");
                TipoMovimento tipoMovimento = tipoMovimentoQuery.getSingleResult();
                
                movimento.setPedido(pedido);
                movimento.setUtilizador(utilizador);
                movimento.setDataMovimento(new Date());
                movimento.setObservacao("Rejeição de " + pedido.getTipoPedido().getDescricao());

                //registar o tipo de movimento
                System.out.println("registar o tipo de movimento");
                movimento.setTipoMovimento(tipoMovimento);

                //registar o movimento 
                System.out.println("registar o movimento ");
                movimentoController.create(movimento);
                
                System.out.println(" Operação de Cancelar bem-sucedida");
                // Operação de validação bem-sucedida
                return "{\"success\": true, \"message\": \"Pedido cancelado com sucesso <br> Pedido: " + pedido.getId() + "\"} ";
            } catch (Exception ex) {
                Logger.getLogger(PedidoServlet.class.getName()).log(Level.SEVERE, null, ex);
                String errorMessage = "Não foi possível cancelar o pedido " + pedido.getId() + " " + ex;
                String mensagem = "Erro ";
                // Construir a resposta JSON de erro
                return "{\"success\": false, \"error\": \"" + errorMessage + "<br>" + mensagem + "\"}";
                
            }
        } else {
            String errorMessage = "Parâmetros inválidos.";

            // Construir a resposta JSON de erro
            return "{\"success\": false, \"error\": \"" + errorMessage + "\"}";
        }
    }
    
}
