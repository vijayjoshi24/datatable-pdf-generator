/**
 * 
 */
package com.datatable.pdf.pojo;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 * @author Vijay Joshi
 * PDFInput is wrapper utility for PDFDataTable object
 * This input is generalized form of input required for 
 * PDFUtil.generateDataTable
 *
 */
public class PDFInput {

	private String pageTitle;
	
	private float titleFontSize;
	
	private float startXPagetTitle;
	
	private float startY;
	
	private PDRectangle pageSize;
	
	private PDFDataTable pdfDataTable;

	/**
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * @param pageTitle the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * @return the pdfDataTable
	 */
	public PDFDataTable getPdfDataTable() {
		return pdfDataTable;
	}

	/**
	 * @param pdfDataTable the pdfDataTable to set
	 */
	public void setPdfDataTable(PDFDataTable pdfDataTable) {
		this.pdfDataTable = pdfDataTable;
	}

	/**
	 * @return the titleFontSize
	 */
	public float getTitleFontSize() {
		return titleFontSize;
	}

	/**
	 * @param titleFontSize the titleFontSize to set
	 */
	public void setTitleFontSize(float titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	/**
	 * @return the startX
	 */
	public float getStartX() {
		return startXPagetTitle;
	}

	/**
	 * @param startX the startX to set
	 */
	public void setStartX(float startX) {
		this.startXPagetTitle = startX;
	}

	/**
	 * @return the startY
	 */
	public float getStartY() {
		return startY;
	}

	/**
	 * @param startY the startY to set
	 */
	public void setStartY(float startY) {
		this.startY = startY;
	}

	/**
	 * @return the pageSize
	 */
	public PDRectangle getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(PDRectangle pageSize) {
		this.pageSize = pageSize;
	}
	
}
