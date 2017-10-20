package com.tutorialspoint.xml;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class EditBaton {
	
	
	String absoluteFilePath = new File("").getAbsolutePath();
	
	File relativeFilePath_BatonXML = new File(absoluteFilePath + "\\BatonXML");
	
	String testPlanName;
	String versionName;
	Element classElement;
	Element testPlan;
	
	boolean ifState = false;

	private List<Element> sectionList;

	private Element main_section;

	private Attribute att_category;
	
	int counter = 0;
	

	public static void main(String[] args) {
		try {
			   EditBaton frame = new EditBaton();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public void EditAttribute(String category_value, String item_name, String item_att, String item_att_value ){
		
		
		for (int iterSection = 0; iterSection < sectionList.size(); iterSection++) {
			main_section = sectionList.get(iterSection);
			att_category = main_section.getAttribute("category");
			if(att_category.getValue().equals(category_value)){
				if (!main_section.getChildren().isEmpty()){
					for (int iterItem = 0; iterItem < main_section.getChildren().size(); iterItem++){
						if (sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name")!= null &&
								sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name").equals(item_name)){
		    				sectionList.get(iterSection).getChildren().get(iterItem).setAttribute(item_att, item_att_value);
		    				counter++;
		    			}
					}
				}
			}
    	}
	}
	
	public void EditAttribute(String category_value, String item_name, String comp_name, String item_att, String item_att_value){
	
		for (int iterSection = 0; iterSection < sectionList.size(); iterSection++) {
			main_section = sectionList.get(iterSection);
			att_category = main_section.getAttribute("category");
			if(att_category.getValue().equals(category_value)){
				if (!main_section.getChildren().isEmpty()){
					for (int iterItem = 0; iterItem < main_section.getChildren().size(); iterItem++){
						
						if(main_section.getChildren().get(iterItem).getAttributeValue("name")!=null &&
								main_section.getChildren().get(iterItem).getAttributeValue("name").equals(item_name)){
							if (!main_section.getChildren().get(iterItem).getChildren().isEmpty()){
								for (int iterComp = 0; iterComp < main_section.getChildren().get(iterItem).getChildren().size(); iterComp++){
									if (sectionList.get(iterSection).getChildren().get(iterItem).getChildren().get(iterComp).getAttributeValue("name")!= null &&
											sectionList.get(iterSection).getChildren().get(iterItem).getChildren().get(iterComp).getAttributeValue("name").equals(comp_name)){
					    				
					    				sectionList.get(iterSection).getChildren().get(iterItem).getChildren().get(iterComp).setAttribute(item_att, item_att_value);
					    				counter++;
					    			}
								}
							}
						}
					}
				}
			}
    	}
	}
	
	
	public void IfAttribute(String category_value, String item_name, String item_att, String item_att_value ){
		
		
		for (int iterSection = 0; iterSection < sectionList.size(); iterSection++) {
			main_section = sectionList.get(iterSection);
			att_category = main_section.getAttribute("category");
			if(att_category.getValue().equals(category_value)){
				if (!main_section.getChildren().isEmpty()){
					for (int iterItem = 0; iterItem < main_section.getChildren().size(); iterItem++){
						if (sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name")!= null &&
								sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name").equals(item_name) &&
								sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue(item_att).equals(item_att_value)){
							
		    				ifState=true;
		    			}
					}
				}
			}
    	}
	}
	
	public void IfAttribute(String category_value, String item_name, String comp_name, String item_att, String item_att_value){
	
		for (int iterSection = 0; iterSection < sectionList.size(); iterSection++) {
			main_section = sectionList.get(iterSection);
			att_category = main_section.getAttribute("category");
			if(att_category.getValue().equals(category_value)){
				if (!main_section.getChildren().isEmpty()){
					for (int iterItem = 0; iterItem < main_section.getChildren().size(); iterItem++){
						
						if(main_section.getChildren().get(iterItem).getAttributeValue("name")!=null &&
								main_section.getChildren().get(iterItem).getAttributeValue("name").equals(item_name)){
							if (!main_section.getChildren().get(iterItem).getChildren().isEmpty()){
								for (int iterComp = 0; iterComp < main_section.getChildren().get(iterItem).getChildren().size(); iterComp++){
									if (sectionList.get(iterSection).getChildren().get(iterItem).getChildren().get(iterComp).getAttributeValue("name")!= null &&
											sectionList.get(iterSection).getChildren().get(iterItem).getChildren().get(iterComp).getAttributeValue("name").equals(comp_name) &&
											sectionList.get(iterSection).getChildren().get(iterItem).getChildren().get(iterComp).getAttributeValue(item_att).equals(item_att_value)){
					    				
					    				ifState=true;
					    			}
								}
							}
						}
					}
				}
			}
    	}
	}


	public void seriousToFatal(String category, String name){
		IfAttribute(category, name, "severity", "Serious");
        if(ifState){
        	ifState=false;
        	IfAttribute(category, name, "check", "true");
        	if(ifState){
	        	EditAttribute(category, name, "severity", "Fatal");
	        	ifState=false;
        	}
        }
	}
	
	public void seriousToFatal(String category, String name, String component){
        	IfAttribute(category, name, component, "check", "true");
        	if(ifState){
	        	EditAttribute(category, name, "severity", "Fatal");
	        	ifState=false;
        	}
	}
	
	
	public void EditName(){
		String currentName;
		currentName = testPlan.getAttribute("name").getValue();
		testPlan.setAttribute("name", currentName + "_PIT");
	}
	
	public void EditDescription(){
		String currentName;
		currentName = testPlan.getAttribute("name").getValue();
		testPlan.getChild("description").setText(currentName.substring(0, currentName.length() - 6) + ". Editted by Kevin Kha on Oct 2, 2017.");
		System.out.println(testPlan.getChild("description").getValue());
	}
	
	
	public void EditFatal(){
		
		for (int iterSection = 0; iterSection < testPlan.getChild("instructions").getChildren().size(); iterSection++) {
			main_section = testPlan.getChild("instructions").getChildren().get(iterSection);
			att_category = main_section.getAttribute("name");
			if(att_category.getValue().equals("Continue on Fatal Check Failure")){
				main_section.setAttribute("enable", "false");
			}
		}
	}
	
	public void EditATEMEDescription(){
		System.out.println(testPlan.getChild("metadata").getChildText("brief"));
		testPlan.getChild("metadata").getChild("brief").setText("Created by Kevin Kha on June 29, 2017 for ATEME 3.7.5.3.");
		System.out.println(testPlan.getChild("metadata").getChildText("brief"));
	}
	
	
	
	public void editTestPlan(String FileName) throws JDOMException, IOException{
		File inputFile = new File(FileName);
		
        SAXBuilder saxBuilder = new SAXBuilder();

        Document document = saxBuilder.build(inputFile);

        testPlan = document.getRootElement();
        testPlanName = document.getRootElement().getAttribute("name").getValue();
        versionName = document.getRootElement().getChild("productVersion").getAttributeValue("build");
        classElement = document.getRootElement().getChild("sections");

        sectionList = classElement.getChildren();
        
        
        EditName();
        EditDescription();
        //EditFatal();
        
        //EditAttribute("VideoEncoding", "Average Bitrate", "Average Bitrate", "restriction", "(v >= 1) and (v <= 20)");
        //EditAttribute("VideoEncoding", "Average Bitrate", "severity", "Warning" );
        
        
        /*
        
        
        
        EditAttribute("H264Video", "Profile", "log", "true");
        EditAttribute("H264Video", "Level", "log", "true");
        EditAttribute("H264Video", "DPB Size", "log", "true");
        EditAttribute("H264Video", "MBAFF Mode", "log", "true");
        EditAttribute("H264Video", "Constraint Set0 Flag", "log", "true");
        EditAttribute("H264Video", "Constraint Set1 Flag", "log", "true");
        EditAttribute("H264Video", "Constraint Set2 Flag", "log", "true");
        EditAttribute("H264Video", "Constraint Set3 Flag", "log", "true");
        EditAttribute("H264Video", "Entropy Coding Mode", "log", "true");
        
        EditAttribute("MP4", "Creation Time", "log", "true");
        EditAttribute("MP4", "Modification Time", "log", "true");
        EditAttribute("MP4", "AudioLangTrackIDMap", "log", "true");
        EditAttribute("MP4", "3GPP Profile", "log", "true");
        EditAttribute("MP4", "Progressive Download", "log", "true");
        EditAttribute("MP4", "TimeCode Track", "log", "true");
        

        
        EditAttribute("AC3Audio", "Stream Type", "log", "true");
        EditAttribute("AC3Audio", "Compression Gain", "log", "true");
        EditAttribute("AC3Audio", "Audio Type", "log", "true");
        EditAttribute("AC3Audio", "Surround Mode", "log", "true");
        EditAttribute("AC3Audio", "Room Type", "log", "true");
        EditAttribute("AC3Audio", "Dialogue Normalization", "Dialogue Normalization", "log", "true");
        
        EditAttribute("PlayList", "Item Count", "log", "true");
        
        EditAttribute("VideoEncoding", "Chroma Format", "log", "true");
        EditAttribute("VideoEncoding", "Frame Rate", "log", "true");
        EditAttribute("VideoEncoding", "Active Format", "log", "true");
        EditAttribute("VideoEncoding", "Average Bitrate", "log", "true");
        EditAttribute("VideoEncoding", "Duration", "log", "true");
        EditAttribute("VideoEncoding", "Scan Order", "log", "true");
        EditAttribute("VideoEncoding", "Resolution", "NewResolution", "log", "true");
        EditAttribute("VideoEncoding", "Resolution", "Resolution Change", "log", "true");

        EditAttribute("VideoEncoding", "Bits Per Sample", "log", "true");
        EditAttribute("VideoEncoding", "Sample Aspect Ratio", "log", "true");
        EditAttribute("VideoEncoding", "Display Aspect Ratio", "log", "true");
        EditAttribute("VideoEncoding", "Video Format", "Video Format", "log", "true");
        EditAttribute("VideoEncoding", "Picture Scanning Type", "Picture Scanning Type", "log", "true");
        EditAttribute("VideoEncoding", "Cadence", "Cadence Change", "log", "true");
        EditAttribute("VideoEncoding", "Cadence", "CadencePattern", "log", "true");
        EditAttribute("VideoEncoding", "Color Information", "Color Matrix", "log", "true");
        
        
        EditAttribute("AudioEncoding", "Bits per sample", "log", "true");
        EditAttribute("AudioEncoding", "Channel Configuration", "log", "true");
        EditAttribute("AudioEncoding", "LFE Channels", "log", "true");
        EditAttribute("AudioEncoding", "Duration", "log", "true");
        EditAttribute("AudioEncoding", "Audio Channels", "Audio Channels", "log", "true");
        EditAttribute("AudioEncoding", "Audio Channels", "Audio Channels Change", "log", "true");
        EditAttribute("AudioEncoding", "Bit Rate", "Bit Rate", "log", "true");
        EditAttribute("AudioEncoding", "Bit Rate", "Bit Rate Change", "log", "true");
        EditAttribute("AudioEncoding", "Sampling Frequency", "Sampling Frequency", "log", "true");
        EditAttribute("AudioEncoding", "Sampling Frequency", "Sampling Frequency Change", "log", "true");
        
        EditAttribute("ClosedCaption", "ClosedCaption608", "log", "true");
        EditAttribute("ClosedCaption", "ClosedCaption708", "log", "true");
        
        EditAttribute("MPEG2TS", "PacketSize", "log", "true");
        EditAttribute("MPEG2TS", "Null Packets(%)", "log", "true");
        EditAttribute("MPEG2TS", "TeleText Language", "log", "true");
        EditAttribute("MPEG2TS", "AudioLangPIDMap", "log", "true");
        EditAttribute("MPEG2TS", "Ancillary Data", "log", "true");
        EditAttribute("MPEG2TS", "Program Count", "log", "true");
        EditAttribute("MPEG2TS", "PCR Duration", "log", "true");
        EditAttribute("MPEG2TS", "PCR PIDs expected", "log", "true");
        EditAttribute("MPEG2TS", "PMT PIDs expected", "log", "true");
        EditAttribute("MPEG2TS", "SIT PIDs", "log", "true");
        EditAttribute("MPEG2TS", "Unknown PIDs", "log", "true");
        EditAttribute("MPEG2TS", "Teletext", "log", "true");
        EditAttribute("MPEG2TS", "PCR In Video", "log", "true");
        EditAttribute("MPEG2TS", "SCTE-35 Present", "log", "true");
        EditAttribute("MPEG2TS", "Timing Info", "log", "true");
        EditAttribute("MPEG2TS", "DVB Subtitle", "log", "true");
        
        EditAttribute("MPEG4Part2Video", "Profile", "log", "true");
        EditAttribute("MPEG4Part2Video", "Level", "log", "true");
        
        EditAttribute("MPEG2PS", "AudioLangStreamIDMap", "log", "true");
        EditAttribute("MPEG2PS", "Duration", "log", "true");
        
        EditAttribute("MPEG2Video", "Profile", "log", "true");
        EditAttribute("MPEG2Video", "Level", "log", "true");
        EditAttribute("MPEG2Video", "bit_rate_extension", "log", "true");
        EditAttribute("MPEG2Video", "Encoded bitrate", "log", "true");
        EditAttribute("MPEG2Video", "bit_rate_value", "log", "true");
        EditAttribute("MPEG2Video", "top_field_first", "log", "true");
        EditAttribute("MPEG2Video", "progressive_sequence", "log", "true");
        EditAttribute("MPEG2Video", "picture_structure", "log", "true");
        EditAttribute("MPEG2Video", "GOP Status", "log", "true");
        EditAttribute("MPEG2Video", "Picture Coding Type", "log", "true");
        
        EditAttribute("AACAudio", "AAC Content", "log", "true");
        EditAttribute("AACAudio", "Header Type", "log", "true");
        EditAttribute("AACAudio", "Stream Type", "log", "true");
        EditAttribute("AACAudio", "Object Type", "log", "true");
        EditAttribute("AACAudio", "TNS Data Present", "log", "true");
        EditAttribute("AACAudio", "SBR Sampling Frequency", "log", "true");
        EditAttribute("AACAudio", "Encoded Bitrate Mode", "log", "true");
        EditAttribute("LPCMAudio", "Endianness", "log", "true");
        EditAttribute("MP3Audio", "ID", "log", "true");
        EditAttribute("MP3Audio", "Layer", "log", "true");
        EditAttribute("MP3Audio", "ID3Tag", "log", "true");
        EditAttribute("MP3Audio", "Audio Coding Mode", "Audio Coding Mode", "log", "true");
        EditAttribute("MP3Audio", "Audio Coding Mode", "Audio Coding Mode Change", "log", "true");
        EditAttribute("ContainerEncoding", "FileSize", "log", "true");
        EditAttribute("ContainerEncoding", "VideoTracks", "log", "true");
        EditAttribute("ContainerEncoding", "AudioTracks", "log", "true");
        EditAttribute("ContainerEncoding", "Audio Video Duration Mismatch", "log", "true");
        EditAttribute("ContainerEncoding", "Allowable PIDs", "Audio PIDs", "log", "true");
        EditAttribute("ContainerEncoding", "Allowable PIDs", "Video PIDs", "log", "true");
        EditAttribute("ContainerEncoding", "ClosedCaptions", "ClosedCaptions", "log", "true");
        EditAttribute("ContainerEncoding", "CCLocation", "log", "true");
        
        
        
        seriousToFatal("ContainerEncoding", "Allowable PIDs", "Audio PIDs");
        seriousToFatal("ContainerEncoding", "Allowable PIDs", "Video PIDs");
        seriousToFatal("VideoEncoding", "Frame Rate");
        seriousToFatal("ContainerEncoding", "AudioTracks");
        seriousToFatal("MPEG2TS	PCR", "PIDs expected");
        seriousToFatal("MPEG2TS	PCR-PTS", "Analysis");
        seriousToFatal("VideoEncoding", "Picture Scanning Type", "Picture Scanning Type");
        seriousToFatal("MPEG2TS", "PMT PIDs expected");
        seriousToFatal("VideoEncoding","Resolution", "Resolution Change");
        seriousToFatal("VideoEncoding","Resolution", "NewResolution");
        seriousToFatal("ContainerEncoding", "VideoTracks");
        seriousToFatal("AudioEncoding", "Bits per sample");
        seriousToFatal("VideoEncoding", "Bits Per Sample");
        seriousToFatal("VideoEncoding", "Chroma Format");
        seriousToFatal("H264Video", "Entropy Coding Mode");


        
        IfAttribute("VideoEncoding", "Frame Rate", "severity", "Serious");
        if(ifState){
        	EditAttribute("VideoEncoding", "Frame Rate", "severity", "Fatal");
        	ifState=false;
        }
        
        */
        
        /*
        //Some profiles use 60000 instead of 600000
        EditAttribute("ContainerEncoding", "Audio Video Duration Mismatch", "check", "true");
        EditAttribute("ContainerEncoding", "Audio Video Duration Mismatch", "Allowed mismatch", "check", "true");
        EditAttribute("ContainerEncoding", "Audio Video Duration Mismatch", "Allowed mismatch", "restriction", "v > 600000");
        
        //Some use Serious instead of Warning. Some ignore CC.
        EditAttribute("ContainerEncoding", "ClosedCaptions", "severity", "Warning");
        EditAttribute("ContainerEncoding", "ClosedCaptions", "ClosedCaptions", "check", "true");
        EditAttribute("ContainerEncoding", "ClosedCaptions", "ClosedCaptions", "restriction", "Present");
        
        //Some use Serious instead of Warning
        EditAttribute("ClosedCaptions", "ClosedCaptions608", "check", "true");
        EditAttribute("ClosedCaptions", "ClosedCaptions608", "severity", "Warning");
        EditAttribute("ClosedCaptions", "ClosedCaptions608", "ClosedCaptions608", "check", "true");
        EditAttribute("ClosedCaptions", "ClosedCaptions608", "ClosedCaptions608", "restriction", "Present");
        
        EditAttribute("ClosedCaptions", "ClosedCaptions708", "check", "true");
        EditAttribute("ClosedCaptions", "ClosedCaptions708", "severity", "Warning");
        EditAttribute("ClosedCaptions", "ClosedCaptions708", "ClosedCaptions708", "check", "true");
        EditAttribute("ClosedCaptions", "ClosedCaptions708", "ClosedCaptions708", "restriction", "Present");
        
        */




        
        
        
        
        /*
        for (int iterSection = 0; iterSection < sectionList.size(); iterSection++) {
        	if(sectionList.get(iterSection).getAttributeValue("category").equals("ContainerEncoding")){
        		for (int iterItem = 0; iterItem < sectionList.get(iterSection).getChildren().size(); iterItem++){
        			if (sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name")!= null){
        				if (sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name").equals("FileSize")){
        					System.out.println(sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("name"));
        					System.out.println(sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("log"));
        					sectionList.get(iterSection).getChildren().get(iterItem).setAttribute("log", "false");
        					System.out.println(sectionList.get(iterSection).getChildren().get(iterItem).getAttributeValue("log"));
        				}
        			}
        		}
        	}
        }
        */
        
        
        XMLOutputter xmlOutput = new XMLOutputter();
        
        xmlOutput.setFormat(Format.getPrettyFormat());
        
        try(OutputStream out = new FileOutputStream(absoluteFilePath+String.format("\\BatonXML_output\\%s",inputFile.getName()))) {
            new XMLOutputter().output(document, out);
        }
	}
	
	
	public void listFilesForFolder(final File folder) throws JDOMException, IOException {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				editTestPlan(fileEntry.getParentFile() + "\\" + fileEntry.getName());
			}
		}
	}
	
	
	public EditBaton() throws JDOMException, IOException{
		
		listFilesForFolder(relativeFilePath_BatonXML);
		
		
        
        //createRowDataArray3();
		
        //SimpleExcelWriterExample XMLtoExcel = new SimpleExcelWriterExample();
        
        //XMLtoExcel.runCreation(rowDataArray);

	}
	
}
