package com.tutorialspoint.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class CompareElemental {
	
	int excelRowSize = 8000;
	
	String absoluteFilePath = new File("").getAbsolutePath();
	
	File relativeFilePath_BatonXML = new File(absoluteFilePath + "\\BatonXML");
	
	ArrayList<ArrayList<String>> rowDataArray = new ArrayList<ArrayList<String>>();
	
	ArrayList<String> historyHandler = new ArrayList<String>();
	
	String[][] rowData = new String[excelRowSize][8000];
	
	List<Element> sectionList;
	List<Element> itemList;
	
	Element classElement;
	
	int iterateRow = 0;
	int iterateColumn = 0;
	
	int recCount = 1;
	
	
	String testPlanName;
	String versionName;
	
	int columnCount(){
		iterateColumn++;
		return iterateColumn;
	}
	
	
	
	public static void main(String[] args) {
		try {
			   CompareElemental frame = new CompareElemental();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public void cureNull(){
		for (int i = 0; i <= iterateColumn; i++) {
        	for (int x = 0; x < excelRowSize; x++){
        		if (rowData[x][i] == null){
       				rowData[x][i] = " ";
       			}
        	}
        }
	}
	
	public void createRowDataArray4(){
		
		cureNull();
		
		ArrayList<String> rowDataHolder = new ArrayList<String>();

		for (int x = 0; x < 20; x++) {
			if (rowData[0][x] != null) {
				rowDataHolder.add(rowData[0][x]);
			}
		}

		rowDataArray.add(rowDataHolder);

		for (int i = 1; i < 4000; i++) {
			rowDataHolder = new ArrayList<String>();
			for (int x = 0; x < 100; x++) {
				if (rowData[i][x] != null) {
					rowDataHolder.add(rowData[i][x]);
				}
			}
			rowDataArray.add(rowDataHolder);
		}
	}
	
	
	
	
	
	
	
	
	public void addTestPlan(String FileName) throws JDOMException, IOException{
		File inputFile = new File(FileName);
		
        SAXBuilder saxBuilder = new SAXBuilder();

        Document document = saxBuilder.build("http://10.27.99.62/restapi/jobs/F9E0FFBE-3BE5-4014-8CC4-CA73ABCFEA94");

        sectionList = document.getRootElement().getChildren();
        
        
        addItem1(sectionList);
        
        
	}
	
	
	public void addItem1(List<Element> proxyList){
		
		
		for (int iterSection = 0; iterSection < proxyList.size(); iterSection++) {
			
			
			
        	rowData[columnCount()][recCount] = proxyList.get(iterSection).getName();
        	historyHandler.add(recCount, proxyList.get(iterSection).getName());
        	
        	if (!proxyList.get(iterSection).getAttributes().isEmpty()){
        		rowData[iterateColumn][0] = proxyList.get(iterSection).getAttributes().get(0).getName() + "=" + proxyList.get(iterSection).getAttributes().get(0).getValue();
        		for (int i = 1; i < proxyList.get(iterSection).getAttributes().size(); i++){
	        		rowData[iterateColumn][0] = rowData[iterateColumn][0] + 
	        		proxyList.get(iterSection).getAttributes().get(i).getName() + "=" + proxyList.get(iterSection).getAttributes().get(i).getValue() + ", " ;
        		}
        	}
        	if (proxyList.get(iterSection).getText().length()!=0){
        		if( rowData[iterateColumn][0] == null ){
        			rowData[iterateColumn][0] = " ";
        		}
        		rowData[iterateColumn][0] = rowData[iterateColumn][0] + proxyList.get(iterSection).getText();
        	}
        	
        	for (int i = recCount-1; i > 0; i--){
        		rowData[iterateColumn][i] = historyHandler.get(i);
        	}
        	
        	
        	if (!proxyList.get(iterSection).getChildren().isEmpty()){
        		rowData[iterateColumn+1][recCount] = proxyList.get(iterSection).getName();
        		recCount++;
        		addItem1(proxyList.get(iterSection).getChildren());
        	}
        }
		recCount--;
	}
	
	
	
	
	
	
	public void listFilesForFolder(final File folder) throws JDOMException, IOException {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				addTestPlan(fileEntry.getParentFile() + "\\" + fileEntry.getName());
			}
		}
	}
	
	
	
	
	public CompareElemental() throws JDOMException, IOException{
		
		for (int i = 0; i < 25; i++) {
			historyHandler.add("");
			}
		
		listFilesForFolder(relativeFilePath_BatonXML);
		
        
        createRowDataArray4();
		
        SimpleExcelWriterExample XMLtoExcel = new SimpleExcelWriterExample();
        
        XMLtoExcel.runCreation(rowDataArray);

	}
	
	
	
	
	public class SimpleExcelWriterExample{
		
	    public void runCreation(ArrayList<ArrayList<String>> rowData){
	    	
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Java Books");
	        
	        int rowCount = 0;
	         
	        for (ArrayList<String> aBook : rowData) {
	            Row row = sheet.createRow(++rowCount);
	            int columnCount = 0;
	            for (Object field : aBook) {
	                Cell cell = row.createCell(++columnCount);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }
	         
	         
	        try (FileOutputStream outputStream = new FileOutputStream(absoluteFilePath + "\\JavaBooks2.xlsx")) {
	            workbook.write(outputStream);
	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
}