package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import modelo.Pedido;

/**
 *
 * @author Meldo Maunze
 */
public class GeneratePdf {

    /**
     *
     */
    SimpleDateFormat dia = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy ");

    /**
     *
     * @param pedido
     * @param response
     * @throws Exception
     */
    public void gerarPdf(Pedido pedido, HttpServletResponse response) throws Exception {
        // Definir o tipo de conteúdo da resposta para PDF
        response.setContentType("application/pdf");

        // Definir o cabeçalho de resposta para indicar que o conteúdo é um PDF
        response.setHeader("Content-Disposition", "inline; filename=requerimento.pdf");

        // Criar um novo documento PDF
        Document document = new Document();

        // Criar um novo escritor PDF que escreve diretamente na resposta
        PdfWriter.getInstance(document, response.getOutputStream());

        // Abrir o documento
        document.open();
        Font redFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(255, 0, 0));
        Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 255));
        Font greenFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(75, 160, 75));

        // Adicionar título
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        Paragraph title = new Paragraph("Centro de Recursos");
        title.setFont(FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));
        document.add(title);

        PdfPTable table = new PdfPTable(2); // 2 colunas

        table.addCell("Parecer do Centro de Recursos");
        table.addCell("Despacho");
        table.addCell(new Phrase(pedido.getTipoPedido().getDescricao(), redFont));
        table.addCell(new Phrase(pedido.getEstado().getDescricao(), blueFont));

        table.addCell("Situação administrativa");
        table.addCell("");
        table.addCell("Regular");
        table.addCell("");
        table.addCell("Observações");
        table.addCell(new Phrase(pedido.getAssunto(), greenFont));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable estudante = new PdfPTable(1);
        // Adicionar linhas à tabela
        estudante.addCell("Identificação do requerente");
        estudante.addCell("\nNome do estudante:\t\t\t" + pedido.getUtilizador().getNome()
                + "\nCódigo do estudante:\t\t\t" + pedido.getUtilizador().getId()
                + "             Curso: " + pedido.getUtilizador().getDepartamento().getSigla()
                + "\nAno de frequência: " + String.valueOf(pedido.getUtilizador().getId())
                + "              Email: " + pedido.getUtilizador().getEmail()
                + "\nContacto " + String.valueOf(pedido.getUtilizador().getContacto())
                + "\n\n"
        );

        // Adicionar tabela ao documento
        document.add(estudante);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        // Criar tabela para a justificativa
        PdfPTable tableJustificativa = new PdfPTable(1); // 1 coluna
        tableJustificativa.setSpacingBefore(30f);

        // Adicionar o título "Justificativa" à tabela
        tableJustificativa.addCell("Justificativa:");

        // Adicionar a justificativa do pedido à tabela
        tableJustificativa.addCell("\n" + pedido.getJustifiativa() + "\n");

        // Adicionar a tabela ao documento
        document.add(tableJustificativa);
        Paragraph data = new Paragraph("\nCR______, aos " + dia.format(new Date()));
        data.setAlignment(ALIGN_CENTER);
        document.add(data);

        Paragraph oEstudante = new Paragraph("O Estudante\n_____________________________________________\n"+pedido.getUtilizador().getNome());
        oEstudante.setAlignment(ALIGN_CENTER);
        document.add(oEstudante);
        // Fechar o documento
        document.close();
    }
}
