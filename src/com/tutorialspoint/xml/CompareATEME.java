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

import com.tutorialspoint.xml.CompareBaton.SimpleExcelWriterExample;

public class CompareATEME {
	
	
int excelRowSize = 200000;
	
	String absoluteFilePath = new File("").getAbsolutePath();
	
	File relativeFilePath_ATEME_XML = new File(absoluteFilePath + "\\ATEME_XML");
	
	ArrayList<ArrayList<String>> rowDataArray = new ArrayList<ArrayList<String>>();
	
	ArrayList<String> rowDataArrayHolder = new ArrayList<String>();
	
	String[][] rowData = new String[excelRowSize][150];
	
	int[][] trackXML = new int[20][1];
	
	
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
			   CompareATEME frame = new CompareATEME();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void addItem(Element endElement){
		int originalSize = rowDataArrayHolder.size();
		List attributes = endElement.getAttributes();
		if (!attributes.isEmpty()){
	   		Iterator iterator = attributes.iterator();
	   		while (iterator.hasNext()){
	   			
	   			Attribute attribute = (Attribute) iterator.next();
	   			 
	   			String name = attribute.getName();
	   			String value = attribute.getValue();
	   			
	   			rowDataArrayHolder.add("~"+name+"="+value);
	   		}
	   		System.out.println(iterateColumn);
	   		iterateColumn++;
	   		System.out.println(rowDataArrayHolder);
	   		rowDataArray.add(rowDataArrayHolder);
	   		rowDataArrayHolder.subList(originalSize, rowDataArrayHolder.size()).clear();
		}
	}
	
	
	public void goDeeper(){
		
		List<Element> exploreSection = sectionList;
		
		for (int iterSection = 0; iterSection < exploreSection.size(); iterSection++) {
			
			boolean moveOn = false;
			
			do{
				
				if (exploreSection.get(iterSection).getChildren().isEmpty()){
					rowDataArrayHolder.add(exploreSection.get(iterSection).getName());
					
					addItem(exploreSection.get(iterSection));
					rowDataArrayHolder.remove(rowDataArrayHolder.size() - 1);
				}
				
				else{
					rowDataArrayHolder.add(exploreSection.get(iterSection).getName());
					addItem(exploreSection.get(iterSection));
					exploreSection = exploreSection.get(iterSection).getChildren();
	        	}
				
			}while (moveOn = false);
			
			if (exploreSection.get(iterSection).getChildren().isEmpty()){
				rowDataArrayHolder.add(exploreSection.get(iterSection).getName());
				addItem(exploreSection.get(iterSection));
				rowDataArrayHolder.remove(rowDataArrayHolder.size() - 1);
			}
			else{
				rowDataArrayHolder.add(exploreSection.get(iterSection).getName());
				addItem(exploreSection.get(iterSection));
				sectionList = exploreSection.get(iterSection).getChildren();
				rowDataArrayHolder.add(exploreSection.get(iterSection).getName());
				goDeeper();
				rowDataArrayHolder.remove(rowDataArrayHolder.size() - 1);
        	}
        }
	}
	
	
	public void addTestPlan(String FileName) throws JDOMException, IOException{
		
		rowDataArray = new ArrayList<ArrayList<String>>();
		
		File inputFile = new File(FileName);
		
        SAXBuilder saxBuilder = new SAXBuilder();

        Document document = saxBuilder.build(inputFile);

        testPlanName = document.getRootElement().getAttributeValue("version");
        versionName = document.getRootElement().getChild("metadata").getChild("brief").getValue();

        sectionList = document.getRootElement().getChildren();
        
        System.out.println(testPlanName);
        System.out.println(versionName);
        System.out.println(document.getRootElement().getName());
        
        goDeeper();
        
        System.out.println("_________");
        System.out.println(rowDataArray);
        //System.out.println(rowDataArray);
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
	
	public CompareATEME() throws JDOMException, IOException{
		
		listFilesForFolder(relativeFilePath_ATEME_XML);
		System.out.println("_________");
        System.out.println(rowDataArray);
		
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
