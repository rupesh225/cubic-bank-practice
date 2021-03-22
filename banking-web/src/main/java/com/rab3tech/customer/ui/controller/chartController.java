package com.rab3tech.customer.ui.controller;

import com.rab3tech.customer.service.CustomerTransactionService;
import com.rab3tech.vo.CustomerTransactionVO;
import com.rab3tech.vo.LoginVO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
public class chartController {

    @Autowired
    private CustomerTransactionService customerTransactionService;


    @GetMapping("/customer/barChart")
    public void createChart(HttpServletResponse response,HttpSession session) throws IOException{

        LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
        String currentLoggedInUserName=loginVO2.getUsername();


        //This I need for showing all the transaction details of customer again from,
        //database!
        List<CustomerTransactionVO>  customerTransactionVOs=customerTransactionService.findCustomerTransaction(currentLoggedInUserName);

        response.setContentType("image/png");
        //Creating data first
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(CustomerTransactionVO customerTransactionVO:customerTransactionVOs){
            dataset.setValue(customerTransactionVO.getAmount(), "Transaction Comparasion", customerTransactionVO.getTxid()+"");
        }

	       /* dataset.setValue(38, "Gold medals", "China");
	        dataset.setValue(29, "Gold medals", "UK");
	        dataset.setValue(22, "Gold medals", "Russia");
	        dataset.setValue(13, "Gold medals", "South Korea");
	        dataset.setValue(11, "Gold medals", "Germany");*/

        JFreeChart barChart = ChartFactory.createBarChart(
                "Transaction Comparasion",
                "x-axis",
                "y-axis",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(128, 0, 0));
        plot.getRenderer().setSeriesPaint(1, new Color(0, 0, 255));
        plot.getRenderer().setSeriesPaint(2, new Color(0, 230, 255));

        barChart.getCategoryPlot().setBackgroundPaint(Color.white);
        barChart.getCategoryPlot().setRangeGridlinePaint(Color.black);

        ChartUtilities.writeChartAsPNG(response.getOutputStream(), barChart,
                820, 450);

    }
}
