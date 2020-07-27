/**
 * 
 */
package com.datatable.pdf.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.datatable.pdf.pojo.PDFDataTable;
import com.datatable.pdf.pojo.PDFInput;
import com.datatable.pdf.util.PDFUtil;



/**
 * @author Vijay Joshi
 *This is a rest controller for testing generate PDF Utility
 *A sample request format has been created which is used a base request
 *object for PDF utility.
 *PDFUtility.generateDataTablePDF is uses this respective input object.
 */
@RestController
@RequestMapping("/pdfGenerator")
public class PDFGeneratorController {

	@RequestMapping(method = RequestMethod.GET, value="/generateTestPDF", headers="Accept=*/*")
	public @ResponseBody void testService(HttpServletResponse response) {
		byte[] pdfBytes = null;
		
		PDFInput pdfInput = new PDFInput();
		pdfInput.setPageTitle("PDF Generator Sample");
		pdfInput.setTitleFontSize(20);
		pdfInput.setPageSize(PDRectangle.A4);
		pdfInput.setStartX(150);
		pdfInput.setStartY(800);
		
		PDFDataTable pdfDataTable = new PDFDataTable();
		
		List<Map<String, String>> headerList = new ArrayList<>();
		Map<String, String> headerData = new HashMap<>();
		headerData.put("title", "Employee Name");
		headerData.put("xaxis", "20");
		headerList.add(0, headerData);
		
		headerData = new HashMap<>();
		headerData.put("title", "Employee Designation");
		headerData.put("xaxis", "250");
		headerList.add(1, headerData);
		
		headerData = new HashMap<>();
		headerData.put("title", "Employee Phone Number");
		headerData.put("xaxis", "400");
		headerList.add(2, headerData);
		
		pdfDataTable.setHeaderList(headerList);
		pdfDataTable.setDtHeaderFntSize(12);
		pdfDataTable.setDtDataFntSize(11);
		
		List<List<String>> dataset = new ArrayList<>();
		List<String> dataResult = new ArrayList<>();
		for(int i = 1; i <= 1000; i++){
			dataResult = new ArrayList<>();
			dataResult.add(0, "Employee Name "+i);
			dataResult.add(1, "Employee Designation "+i);
			dataResult.add(2, "Employee Phone Number "+i);
			dataset.add(dataResult);
		}
		
		pdfDataTable.setDataSet(dataset);
		
		pdfInput.setPdfDataTable(pdfDataTable);
		try {
			pdfBytes = PDFUtil.generateDataTablePDF(pdfInput);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		response.setContentType("application/pdf");
		OutputStream out = null;
		try {
		    out = response.getOutputStream();
		    out.write(pdfBytes);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
