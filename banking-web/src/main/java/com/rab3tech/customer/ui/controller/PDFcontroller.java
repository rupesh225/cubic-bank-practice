package com.rab3tech.customer.ui.controller;


import com.rab3tech.customer.service.CustomerTransactionService;
import com.rab3tech.customer.ui.controller.pdfHelper.TransactionPDFExporter;
import com.rab3tech.vo.CustomerTransactionVO;
import com.rab3tech.vo.LoginVO;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PDFcontroller {


    @Autowired
    CustomerTransactionService customerTransactionService;


    @GetMapping("/customer/exportTransactionsToPDF")
    public void exportTransactionToPDF(HttpServletResponse response, HttpSession session) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDataTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" +currentDataTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        LoginVO loginVO2=(LoginVO)session.getAttribute("userSessionVO");
        String currentLoggedInUserName=loginVO2.getUsername();
        List<CustomerTransactionVO> customerTransactionVOs=customerTransactionService.findCustomerTransaction(currentLoggedInUserName);
//        model.addAttribute("customerTransactionVOs", customerTransactionVOs);

        TransactionPDFExporter exporter = new TransactionPDFExporter(customerTransactionVOs);
        exporter.export(response);

    }
}
