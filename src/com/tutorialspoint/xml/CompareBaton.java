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



public class CompareBaton {
	
	int excelRowSize = 400000;
	
	String absoluteFilePath = new File("").getAbsolutePath();
	
	File relativeFilePath_BatonXML = new File(absoluteFilePath + "\\BatonXML");
	
	ArrayList<ArrayList<String>> rowDataArray = new ArrayList<ArrayList<String>>();
	
	String[][] rowData = new String[excelRowSize][150];
	
	List<Element> sectionList;
	List<Element> itemList;
	
	Element classElement;
	
	int iterateRow = 0;
	int iterateColumn = 0;
	int iterSection = 0;
	
	int startOffset = 5;
	
	String testPlanName;
	String versionName;
	
	int columnCount(){
		return startOffset + iterateColumn;
	}
	
	
	
	public static void main(String[] args) {
		try {
			   CompareBaton frame = new CompareBaton();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void createRow(int i){
		List attributes = sectionList.get(i).getAttributes();
		if (!attributes.isEmpty()){
	   		Iterator iterator = attributes.iterator();
	   		
	   		iterateRow++;
	   		
	   		rowData[iterateRow][0] = versionName;
	   		rowData[iterateRow][1] = testPlanName;
	   		
	   		while (iterator.hasNext()){
	   			 
	   			Attribute attribute = (Attribute) iterator.next();
	   			 
	   			String name = attribute.getName();
	   			String value = attribute.getValue();
	   			
	   			if (name == "category")
	   				rowData[iterateRow][2] = value;
	   			else if (name == "label")
	   				rowData[iterateRow][3] = value;
	   			else if (name == "name")
	   				rowData[iterateRow][4] = value;
	   			else if (name == "summary")
	   				rowData[iterateRow][5] = value;
	   			else
	   				System.out.println("ERROR IN ATTRIBUTE OF ELEMENTS:" + name + " " + value); 
	   		} 
	   	}
	}
	
	
	public void addNameValue(String name, String value){
		
		boolean accepted = false;
		
		for (int i = startOffset; i <= columnCount(); i++){
   			if (name.equals(rowData[0][i])){
   				rowData[iterateRow][i] = value;
   				accepted = true;
   			}
   		}
   		if (accepted == false){
   			iterateColumn++;
   			rowData[0][columnCount()] = name;
   			rowData[iterateRow][columnCount()] = value;
   		}
	}
	
	public void setCheckLogPosition(){
		
	}
	
	public void addItem(int x){
		
		List attributes = itemList.get(x).getAttributes();
		if (!attributes.isEmpty()){
	   		Iterator iterator = attributes.iterator();
	   		while (iterator.hasNext()){
	   			 
	   			Attribute attribute = (Attribute) iterator.next();
	   			 
	   			String name = attribute.getName();
	   			String value = attribute.getValue();
	   			 
	   			boolean accepted = false;
	   			 
	   			for (int i = startOffset; i <= columnCount(); i++){
	   				if (name.equals(rowData[0][i])){
	   					rowData[iterateRow][i] = value;
	   					accepted = true;
	   				}
	   			}
	   			 
	   			if (accepted == false){
	   				iterateColumn++;
	   				System.out.println(columnCount());
	   				rowData[0][columnCount()] = name;
	   				rowData[iterateRow][columnCount()] = value;
	   			}
	   		}
	   	}
		
		int parentItem = iterateRow;
		
		if (!itemList.get(x).getChildren().isEmpty()){
			for (int y = 0; y < itemList.get(x).getChildren().size(); y++){
				createRow(iterSection);
				attributes = itemList.get(x).getChildren().get(y).getAttributes();
				if (!attributes.isEmpty()){
			   		Iterator iterator = attributes.iterator();
			   		while (iterator.hasNext()){
			   			 
			   			Attribute attribute = (Attribute) iterator.next();
			   			 
			   			String name = attribute.getName();
			   			String value = attribute.getValue();
			   			 
			   			boolean accepted = false;
			   			 
			   			if (name.equals("name")){
			   				addNameValue("component name", value);
			   				addNameValue("name", itemList.get(x).getAttributeValue("name"));
			   			}
			   			else	
			   				addNameValue(name, value);
			   		}
			   		
			   		int countVariant = -1;
			   		
			   		for (String variant : rowData[0]){
			   			countVariant++;
			   			if (countVariant > startOffset){
			   				if (variant != null){
					   			if (itemList.get(x).getChildren().get(y).getAttribute(variant) == null){
						   			if (itemList.get(x).getAttribute(variant) != null){
						   				rowData[iterateRow][countVariant] = rowData[parentItem][countVariant] + " (inherit)";
						   			}
						   		}
				   			}
			   			}
			   		}
			   		if (itemList.get(x).getChildren().get(y).getAttribute("check") == null){
			   			if (itemList.get(x).getAttribute("check") != null){
			   				if (itemList.get(x).getAttribute("check").getValue().equals("true")){
			   					rowData[iterateRow][6] = "true (inherit)";
			   				}
			   			}
			   		}
			   		if (itemList.get(x).getAttribute("check") == null){
			   			if (itemList.get(x).getChildren().get(y).getAttribute("check") != null){
			   				if (itemList.get(x).getChildren().get(y).getAttribute("check").getValue().equals("true")){
			   					rowData[parentItem][6] = "true (forfeit)";
			   				}
			   			}
			   		}
			   	}
			}
			
		}
	}
	
	
	
	
	public void addProfile(){
		
	}
	
	
	
	
	public void cureNull(){
		for (int i = 0; i <= columnCount(); i++) {
        	for (int x = 0; x < excelRowSize; x++){
        		if (rowData[x][i] == null){
       				rowData[x][i] = " ";
       			}
        	}
        }
	}
	
	
	public void createRowDataArray(){
		cureNull();
		for (int i = 0; i < 1000; i++) {
        	ArrayList<String> rowDataHolder = new ArrayList<String>();
        	for (int x = 0; x < 100; x++){
        		if(rowData[i][x] != null){
        			rowDataHolder.add(rowData[i][x]);
        		}
        	}
        	rowDataArray.add(rowDataHolder);
        }
	}
	
	
	public void createRowDataArray2(){
		cureNull();
		
		ArrayList<String> rowDataHolder = new ArrayList<String>();
		
		for (int x = 0; x < 100; x++){
    		if(rowData[0][x] != null){
    			rowDataHolder.add(rowData[0][x]);
    		}
    	}
		
		rowDataArray.add(rowDataHolder);
		
		for (int i = 1; i < excelRowSize; i++) {
        	rowDataHolder = new ArrayList<String>();
        	if (rowData[i][7].equals("true")){
        		for (int x = 0; x < 149; x++){
            		if(rowData[i][x] != null){
            			rowDataHolder.add(rowData[i][x]);
            		}
            	}
        		rowDataArray.add(rowDataHolder);
        	}
        }
	}
	
	
	public void createRowDataArray3(){
		
		cureNull();
		
		ArrayList<String> rowDataHolder = new ArrayList<String>();
		
		for (int x = 0; x < 150; x++){
    		if(rowData[0][x] != null){
    			rowDataHolder.add(rowData[0][x]);
    		}
    	}
		
		
		rowDataArray.add(rowDataHolder);
		
		for (int i = 1; i < 400000; i++) {
        	rowDataHolder = new ArrayList<String>();
        	if (rowData[i][12].equals("Fatal") || rowData[i][12].equals("Fatal (inherit)") ||
        			rowData[i][12].equals("Fatal (forfeit)") )//|| rowData[i][9].equals("true") || 
        			//rowData[i][9].equals("true (inherit)") ||
        			//rowData[i][9].equals("true (forfeit)"))
        			{
        		for (int x = 0; x < 100; x++){
            		if(rowData[i][x] != null){
            			rowDataHolder.add(rowData[i][x]);
            		}
            	}
        		rowDataArray.add(rowDataHolder);
        	}
        }
	}
	
	
	public void createRowDataArray4(){
		
		cureNull();
		
		ArrayList<String> rowDataHolder = new ArrayList<String>();
		
		for (int x = 0; x < 150; x++){
    		if(rowData[0][x] != null){
    			rowDataHolder.add(rowData[0][x]);
    		}
    	}
		
		
		rowDataArray.add(rowDataHolder);
		
		for (int i = 1; i < 400000; i++) {
        	rowDataHolder = new ArrayList<String>();
        	//if (rowData[i][6].equals("true") || rowData[i][6].equals("true (inherit)") ||
        			//rowData[i][6].equals("true (forfeit)")){
        		//if (rowData[i][12].equals("Serious") || rowData[i][12].equals("Serious (inherit)") ){
	        		for (int x = 0; x < 100; x++){
	            		if(rowData[i][x] != null){
	            			rowDataHolder.add(rowData[i][x]);
	            		}
	            	}
	        		rowDataArray.add(rowDataHolder);
        		}
        	//}
        //}
	}
	
	
	
	
	
	
	
	
	public void addTestPlan(String FileName) throws JDOMException, IOException{
		File inputFile = new File(FileName);
		
        SAXBuilder saxBuilder = new SAXBuilder();

        Document document = saxBuilder.build(inputFile);

        testPlanName = document.getRootElement().getAttribute("name").getValue();
        versionName = document.getRootElement().getChild("productVersion").getAttributeValue("build");
        classElement = document.getRootElement().getChild("sections");

        sectionList = classElement.getChildren();
        
        rowData[0][0] = "Version";
        rowData[0][1] = "Preset";
        rowData[0][2] = "Category";
        rowData[0][3] = "Label";
        rowData[0][4] = "Name";
        rowData[0][5] = "Summary";
        
        for (iterSection = 0; iterSection < sectionList.size(); iterSection++) {
        	itemList = sectionList.get(iterSection).getChildren();
        	for (int iterItem = 0; iterItem < itemList.size(); iterItem++){
        		createRow(iterSection);
        		addItem(iterItem);
        	}
        }
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
	
	
	
	
	public CompareBaton() throws JDOMException, IOException{
		
		listFilesForFolder(relativeFilePath_BatonXML);
        
        createRowDataArray3();
		
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