package com.datatable.pdf.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.datatable.pdf.pojo.PDFInput;

/**
 * 
 * @author Vijay Joshi
 * This is the core class to render PDF content for data table format
 * content.
 * It utilizes Apache PDF utility and generalizes PDF content generation 
 * for data table specific use cases.
 */
public class PDFUtil {
	
	public static final PDFont FONT            = PDType1Font.TIMES_ROMAN;
	public static final PDFont FONT_BOLD       = PDType1Font.TIMES_BOLD;
	public static final PDFont FONT_BOLD_ITALIC = PDType1Font.TIMES_BOLD_ITALIC;
	public static final float HEADER_FONT_SIZE = 12;
	public static final float FONT_SIZE        = 9.5f;
	public static final float HEADING_FONT_SIZE= 10.5f;
	public static final float LINE_WIDTH       = 0.8f;
	public static final float TITLE_FONT_SIZE  = 22;
	public static final float LEADING          = -1.5f * FONT_SIZE;
	public static final float TITLE_LEADING    = -1.2f * FONT_SIZE;
	public static final float LINE_LEADING     =  - (1.3f * FONT_SIZE - FONT_SIZE + 3*LINE_WIDTH) ;
	public static final float MARGIN_TOP   = 25;
	public static final float MARGIN_LEFT  = 35;	
	
	
	public static byte[] generateDataTablePDF(PDFInput pdfInput) throws IOException{
		byte[] pdfBytes = null;
		float startX = 20;
		float startY = 780;
		
		if(null != pdfInput){
			
			if(pdfInput.getStartX() <= 0){
				pdfInput.setStartX(startX);
			}
			
			if(pdfInput.getStartY() <= 0){
				pdfInput.setStartY(startY);
			}
			
			if(!(null != pdfInput.getPageSize())){
				pdfInput.setPageSize(PDRectangle.A4);
			}
			
			PDDocument document = new PDDocument();
			try {
				PDPageContentStream contentStream = addPage(document, pdfInput);
				generatePageTitle(contentStream, pdfInput);
				pdfInput.getPdfDataTable().setStartY(startY-20);
				if(null != pdfInput && null != pdfInput.getPdfDataTable()){
					generateDataTable(document, contentStream, pdfInput);	
				}
				contentStream.close();
				pdfBytes = getPdfBytes(document);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				document.close();
			}
		}
		
		return pdfBytes;
	}
	
	/**This method will draw line at specified location on given document stream*/
	public static void drawline(PDPageContentStream contentStream, float width, float startX, float startY) throws Exception {
		contentStream.setLineWidth(LINE_WIDTH);
		contentStream.moveTo(startX, startY);
		contentStream.lineTo(startX+width, startY);
		contentStream.closeAndStroke();		
	}

	/**This method will add text at specified location on given document stream*/
	public static void addTextOption(PDPageContentStream contentStream, float startX, float startY, String text, boolean option)
			throws Exception {
		if(option) {
			contentStream.beginText();
			contentStream.newLineAtOffset(startX, startY);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 9.5f);
			if(text == null) {
				text = "";
			}
			contentStream.showText(text);
			contentStream.endText();	
		}
	}
	
	public static void addText(PDPageContentStream contentStream, float startX, float startY, String text, float fontSize)
			throws Exception {
		contentStream.beginText();
		contentStream.newLineAtOffset(startX, startY);
		contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
		if(text == null) {
			text = "";
		}
		contentStream.showText(text);
		contentStream.endText();
	}
	
	/**This method will add Bold text at specified location on given document stream*/
	public static void addBoldTextOption(PDPageContentStream contentStream, float startX, float startY, String text, boolean option) throws Exception {
		if(option) {
			if(text != null) {
				text = text.replaceAll("\\P{Print}", "");
			}
			 contentStream.beginText();
		     contentStream.newLineAtOffset(startX, startY);
		     contentStream.setFont(PDType1Font.TIMES_BOLD, 10f);
		     contentStream.showText(text);
		     contentStream.endText();	
		}
	}
	
	public static PDPageContentStream addPage(PDDocument document, PDFInput pdfInput) throws Exception {
		PDPage page = new PDPage(pdfInput.getPageSize());
		//page.setRotation(90);
		document.addPage(page);

		PDPageContentStream.AppendMode appendContent = PDPageContentStream.AppendMode.OVERWRITE;
		PDPageContentStream contentStream = new PDPageContentStream(document, page, appendContent, true, true);
		page.getResources().getFontNames();
		// Set color to gray for stroking
		contentStream.setStrokingColor(Color.GRAY);

		return contentStream;
	}
	
	public static float addParagraph(PDPageContentStream contentStream, float width, float sx,
			float sy, String text, float fontSize, PDFont font, boolean justify) throws IOException {
		List<String> lines = parseLines(text, width, fontSize, font);
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(sx, sy);
		for (String line: lines) {
			float charSpacing = 0;
			if (justify){
				if (line.length() > 1) {
					float size = fontSize * font.getStringWidth(line) / 1000;
					float free = width - size;
					if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
						charSpacing = free / (line.length() - 1);
					}
				}
			}
			contentStream.setCharacterSpacing(charSpacing);
			contentStream.showText(line);
			contentStream.newLineAtOffset(0, LEADING);
			sy = sy + LEADING;
		}
		return sy;		
	}
	
	public static List<String> parseLines(String text, float width, float fontSize, PDFont font) throws IOException {
		List<String> lines = new ArrayList<String>();
		int lastSpace = -1;
		if(text == null) {
			text = "";
		}
		while (text.length() > 0) {
			int spaceIndex = text.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0)
				spaceIndex = text.length();
			String subString = text.substring(0, spaceIndex);
			float size = fontSize * font.getStringWidth(subString) / 1000;
			if (size > width) {
				if (lastSpace < 0){
					lastSpace = spaceIndex;
				}
				subString = text.substring(0, lastSpace);
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
			} else if (spaceIndex == text.length()) {
				lines.add(text);
				text = "";
			} else {
				lastSpace = spaceIndex;
			}
		}
		return lines;
	}
	
	public static byte[] getPdfBytes(PDDocument document) throws Exception {
		byte[] pdfBytes = null;

		// Send excel in the email
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			document.save(bos);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		pdfBytes = bos.toByteArray();

		// Closing the document
		document.close();

		return pdfBytes;
	}
	
	public static void generatePageTitle(PDPageContentStream contentStream, PDFInput pdfInput) throws Exception {
		contentStream.beginText();
		contentStream.newLineAtOffset(pdfInput.getStartX(), pdfInput.getStartY());
		contentStream.setFont(PDType1Font.TIMES_BOLD, pdfInput.getTitleFontSize());
		contentStream.showText(pdfInput.getPageTitle());
		contentStream.endText();
	}
	
	public static void addBoldText(PDPageContentStream contentStream, float startX, float startY, String text, float fontSize) throws Exception {
		if(text != null) {
			text = text.replaceAll("\\P{Print}", "");
			
		}
		 contentStream.beginText();
	     contentStream.newLineAtOffset(startX, startY);
	     contentStream.setFont(PDType1Font.TIMES_BOLD, fontSize);
	     contentStream.showText(text);
	     contentStream.endText();
	}
	
	public static float addSplitText(PDPageContentStream contentStream, float startX, float startY, String text, float fontSize, int wrapTextLen) throws Exception {
		String[] wrT = null;
		String s = null;
		wrT = WordUtils.wrap(text, wrapTextLen).split("\\r?\\n");
		for (int i=0; i< wrT.length; i++) {
		    if(i != 0){
			startY = startY-10;
		    }
		    contentStream.beginText();
		    contentStream.setFont(PDType1Font.TIMES_ROMAN, 8f);
		    contentStream.newLineAtOffset(startX,startY);
		    s = wrT[i];
		    contentStream.showText(s);
		    contentStream.endText(); 
		}
		return startY;
	    }
	
	/**
	 * headerList - List of MAP Items
	 * 				title - Header Title.
	 * 				X Axis position - Position to be added.
	 * 				
	 * 
	 * @param headerList
	 * @return
	 * @throws Exception 
	 */
	public static float generateDataTable(PDDocument document, PDPageContentStream contentStream, PDFInput pdfInput) throws Exception{
		
		float startingY = pdfInput.getPdfDataTable().getStartY();

		/**Setting Font Size*/
		if(pdfInput.getPdfDataTable().getDtHeaderFntSize() <= 0){
			pdfInput.getPdfDataTable().setDtHeaderFntSize(18);
		}
		
		if(pdfInput.getPdfDataTable().getDtDataFntSize() <= 0){
			pdfInput.getPdfDataTable().setDtDataFntSize(16);
		}
		/**End of Font Size*/
		
		List<Float> datasetXAxis = new ArrayList<>();
		int line = pdfInput.getPdfDataTable().getLine();
		contentStream.beginText();
		contentStream.setFont(PDType1Font.TIMES_ROMAN, pdfInput.getPdfDataTable().getDtHeaderFntSize());
		contentStream.setLeading(10f);
		contentStream.endText();
		startingY = startingY - 20;
		for(int i = 0; i < pdfInput.getPdfDataTable().getHeaderList().size(); i++){
			Map<String, String> header = pdfInput.getPdfDataTable().getHeaderList().get(i);
			datasetXAxis.add(Float.valueOf(header.get("xaxis")));
			addBoldText(contentStream, Float.valueOf(header.get("xaxis")), startingY, header.get("title"), pdfInput.getPdfDataTable().getDtHeaderFntSize());
		}

		startingY = startingY - 10;
		for(int j = 0; j < pdfInput.getPdfDataTable().getDataSet().size(); j++){
			startingY = startingY - 12;
			
			if (line == 0) {
				for(int k = 0; k < pdfInput.getPdfDataTable().getDataSet().get(j).size(); k++){
					addText(contentStream, datasetXAxis.get(k), startingY, pdfInput.getPdfDataTable().getDataSet().get(j).get(k), pdfInput.getPdfDataTable().getDtDataFntSize());
				}
			} else if (line % 55 == 0) {
				contentStream.close();
				contentStream = addPage(document, pdfInput);
				contentStream.beginText();
				contentStream.setFont(PDType1Font.TIMES_ROMAN, pdfInput.getPdfDataTable().getDtHeaderFntSize());
				contentStream.setLeading(10f);
				startingY = 790f;
				contentStream.newLine();
				contentStream.endText();
				for(int i = 0; i < pdfInput.getPdfDataTable().getHeaderList().size(); i++){
					Map<String, String> header = pdfInput.getPdfDataTable().getHeaderList().get(i);
					datasetXAxis.add(Float.valueOf(header.get("xaxis")));
					addBoldText(contentStream, Float.valueOf(header.get("xaxis")), startingY, header.get("title"), pdfInput.getPdfDataTable().getDtHeaderFntSize());
				}
				startingY = startingY - 20;
				for(int k = 0; k < pdfInput.getPdfDataTable().getDataSet().get(j).size(); k++){
					addText(contentStream, datasetXAxis.get(k), startingY, pdfInput.getPdfDataTable().getDataSet().get(j).get(k), pdfInput.getPdfDataTable().getDtDataFntSize());
				}
			} else {
				for(int k = 0; k < pdfInput.getPdfDataTable().getDataSet().get(j).size(); k++){
					addText(contentStream, datasetXAxis.get(k), startingY, pdfInput.getPdfDataTable().getDataSet().get(j).get(k), pdfInput.getPdfDataTable().getDtDataFntSize());
				}
			}
			line++;
		}
		contentStream.close();
		return startingY;
	}
}