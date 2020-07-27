/**
 * 
 */
package com.datatable.pdf.pojo;

import java.util.List;
import java.util.Map;

/**
 * @author Vijay Joshi
 * Representation of Data Table Object.
 * This Data Table Object will be used map header and data set
 * to be converted in PDF table.
 */
public class PDFDataTable {

	private List<Map<String, String>> headerList;
	private List<List<String>> dataSet; 
	private float startY; 
	private int line;
	private float dtHeaderFntSize;
	private float dtDataFntSize;
	/**
	 * @return the headerList
	 */
	public List<Map<String, String>> getHeaderList() {
		return headerList;
	}
	/**
	 * @param headerList the headerList to set
	 */
	public void setHeaderList(List<Map<String, String>> headerList) {
		this.headerList = headerList;
	}
	/**
	 * @return the dataSet
	 */
	public List<List<String>> getDataSet() {
		return dataSet;
	}
	/**
	 * @param dataSet the dataSet to set
	 */
	public void setDataSet(List<List<String>> dataSet) {
		this.dataSet = dataSet;
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
	 * @return the line
	 */
	public int getLine() {
		return line;
	}
	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}
	/**
	 * @return the dtHeaderFntSize
	 */
	public float getDtHeaderFntSize() {
		return dtHeaderFntSize;
	}
	/**
	 * @param dtHeaderFntSize the dtHeaderFntSize to set
	 */
	public void setDtHeaderFntSize(float dtHeaderFntSize) {
		this.dtHeaderFntSize = dtHeaderFntSize;
	}
	/**
	 * @return the dtDataFntSize
	 */
	public float getDtDataFntSize() {
		return dtDataFntSize;
	}
	/**
	 * @param dtDataFntSize the dtDataFntSize to set
	 */
	public void setDtDataFntSize(float dtDataFntSize) {
		this.dtDataFntSize = dtDataFntSize;
	}
	
	
}
