package com.rab3tech.customer.ui.controller.pdfHelper;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import com.rab3tech.vo.CustomerTransactionVO;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
public class TransactionPDFExporter {

    List<CustomerTransactionVO> customerTransactionVOs;

    public TransactionPDFExporter(List<CustomerTransactionVO> customerTransactionVOs) {
        this.customerTransactionVOs = customerTransactionVOs;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(2);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Transaction ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("To Account", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Details", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date of Tx.", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (CustomerTransactionVO customerTransactionVO : customerTransactionVOs) {
            table.addCell(String.valueOf(customerTransactionVO.getTxid()));
            table.addCell(String.valueOf(customerTransactionVO.getToAccout()));
            table.addCell(String.valueOf(customerTransactionVO.getAmount()));
            table.addCell(String.valueOf(customerTransactionVO.getDetails()));
            table.addCell(String.valueOf(customerTransactionVO.getDot()));
            table.addCell(String.valueOf(customerTransactionVO.getTxStatus()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(6);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Transaction list", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.0f, 1.5f, 3.0f, 3.5f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
