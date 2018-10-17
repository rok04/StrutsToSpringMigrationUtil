import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StrutsToSpringTagMigrator {
 
	private static Map<String, TagParser> defaultTagParser = new HashMap<String, TagParser>();
   
	private static String[] tagClosedWithinSingleLine = {"<html:options", "<bean:define","<html:errors",
	"<nested:text", "<nested:radio", "<nested:hidden", "<nested:write", "<bean:write", "<nested:define"};

	private static String[] tagContainingClosingPairTag = {
		    "<nested:form", "<html:form", "<nested:root",
			"<nested:nest", "</nested:nest", "<nested:iterate", "</nested:iterate", "<nested:select", "</nested:select",
			"<html:option", "</html:option", "<logic:iterate", "</logic:iterate", "<nested:iterate", "</nested:iterate",
			
			"<logic:present", "</logic:present" ,
			"<logic:notPresent","</logic:notPresent",  

			"<nested:present", "</logic:present" 	,
			"<nested:notPresent","</logic:notPresent",
			
			 "<logic:empty", "</logic:empty",
			"</logic:notEmpty", "</logic:notEmpty" ,
		
			 "<nested:empty" , "</nested:empty",
		     "<nested:notEmpty",	"</nested:notEmpty",
			
	 		 "<logic:equal", "</logic:equal", 
			 "<logic:notEqual", "</logic:notEqual", 
			 "<nested:equal", "</nested:equal",
			 "<nested:notEqual", "</nested:notEqual",
			 "<html:link", "</html:link",
			 "<nested:link", "</nested:link"
			 
		};

	static {
		defaultTagParser.put("<bean:define", new BeanDefinitionTagParser());
		defaultTagParser.put("<nested:define", new NestedBeanDefinitionTagParser());
		
		defaultTagParser.put("<html:errors", new HtmlErrorsTagParser());
		defaultTagParser.put("<html:form", new HtmlFormTagParser());
		defaultTagParser.put("<nested:form", new HtmlFormTagParser());
		defaultTagParser.put("<nested:root", new HtmlNestedRootParser());
		defaultTagParser.put("<html:options", new HtmlOptionsTagParser());
		
		
		defaultTagParser.put("<nested:nest", new HtmlNestedNestStartParser());
		defaultTagParser.put("</nested:nest", new HtmlNestedNestEndParser());
		
		defaultTagParser.put("<nested:select", new HtmlNestedSelectStartParser());
		defaultTagParser.put("</nested:select", new HtmlNestedIterateEndParser());
		
		defaultTagParser.put("<html:option", new HtmlOptionStartTagParser());
		defaultTagParser.put("</html:option", new HtmlOptionEndTagParser());
		
		defaultTagParser.put("<nested:text", new HtmlNestedTextStartParser());

		defaultTagParser.put("<nested:radio", new HtmlNestedRadioStartParser());
		defaultTagParser.put("<nested:hidden", new HtmlNestedHiddenParser());
	
		defaultTagParser.put("<logic:iterate", new HtmllogicIterateStartTag());
		defaultTagParser.put("</logic:iterate", new HtmlLogicIterateEndTag());

		defaultTagParser.put("<nested:iterate", new NestedIterateStartTag());
		defaultTagParser.put("</nested:iterate", new NestedIterateEndTag());

		//defaultTagParser.put("<nested:iterate", new HtmlNestedIterateStartParser());
		//defaultTagParser.put("</nested:iterate", new HtmlNestedIterateEndParser());
		
		
		defaultTagParser.put("<logic:present", new LogicPresentStartTag());
		defaultTagParser.put("</logic:present", new LogicPresentEndTag());
		defaultTagParser.put("<nested:present", new HtmlNestedNotEmptyStartParser());
	    defaultTagParser.put("</nested:present", new HtmlNestedNotEmptyEndParser());
	    
		
		defaultTagParser.put("<logic:empty", new LogicPresentStartTag());
		defaultTagParser.put("</logic:empty", new LogicPresentEndTag());
		defaultTagParser.put("<nested:empty", new HtmlNestedEmptyStartParser());
	    defaultTagParser.put("</nested:empty", new HtmlNestedEmptyEndParser());
		
		defaultTagParser.put("<logic:notPresent", new LogicNotPresentStartTag());
		defaultTagParser.put("</logic:notPresent", new LogicNotPresentEndTag());
	    defaultTagParser.put("<nested:notPresent", new HtmlNestedEmptyStartParser());
	    defaultTagParser.put("</nested:notPresent", new HtmlNestedEmptyEndParser());

		
		defaultTagParser.put("<logic:notEmpty", new LogicNotPresentStartTag());
		defaultTagParser.put("</logic:notEmpty",  new LogicNotPresentEndTag());
	    defaultTagParser.put("<nested:notEmpty", new HtmlNestedNotEmptyStartParser());
	    defaultTagParser.put("</nested:notEmpty", new HtmlNestedNotEmptyEndParser());
		 
	    defaultTagParser.put("<logic:equal", new LogicEqualStartTag());
	    defaultTagParser.put("</logic:equal", new LogicEqualEndTag());
	    defaultTagParser.put("<nested:equal", new LogicNestedEqualStartTag());
	    defaultTagParser.put("</nested:equal", new LogicNestedEqualEndTag());
	   
	    defaultTagParser.put("<logic:notEqual", new LogicNotEqualStartTag());
	    defaultTagParser.put("</logic:notEqual", new LogicNotEqualEndTag());
	    defaultTagParser.put("<nested:notEqual", new LogicNestedNotEqualStartTag());
	    defaultTagParser.put("</nested:notEqual", new LogicNestedNotEqualEndTag());
	    
	    
	    defaultTagParser.put("<bean:write", new HtmlBeanWriteParser());
	    defaultTagParser.put("<nested:write", new HtmlNestedWriteParser());
	   
	    defaultTagParser.put("<html:link", new HtmlLinkStartParser());
	    defaultTagParser.put("</html:link", new HtmlLinkEndParser());
	    
	    defaultTagParser.put("<nested:link", new HtmlNestedLinkStartParser());
	    defaultTagParser.put("</nested:link", new HtmlNestedLinkEndParser());

	    
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		File parentFile = new File("D:\\Enbridge\\ENTRAC-SOURCE\\ENTRAC-SOURCE\\j2ee\\web\\jsp\\au\\gm\\");
		
		
		if(parentFile.isDirectory()){
			 for(File file:parentFile.listFiles())
			 {
				 if(!file.isDirectory()){
					 try{
					 FileReader fileReader = new FileReader(file);
					 processFile(fileReader);
					//System.out.println("Successfully Processing File");
					//  System.out.println(file.getAbsolutePath());
					 }catch(Exception ex){
						 //System.out.println("Error Processing File");
						// System.out.println(file.getAbsolutePath());
						// ex.printStackTrace();
					 }										 
				 }
			 }
		}else{
			 FileReader fileReader = new FileReader(parentFile);
			 processFile(fileReader);
		}
	}

	private static void processFile(FileReader fileReader) throws IOException {
		BufferedReader br = new BufferedReader(fileReader);
		ParserContext parserContext = new ParserContext();
		StringBuffer stringBuffer = new StringBuffer();
		List<String> lines = new ArrayList<String>();
		for (String line; (line = br.readLine()) != null;) {
			lines.add(line);
			String processedLine = null;
			try{
			  processedLine = processTagClosedWithinSingleLine(lines,parserContext);
			}catch(EndTagNotFoundException ex){
				continue;
			}
			lines.clear();
			
			lines.add(processedLine);
			
			//System.out.println(processedLine);
			try{
			 //processedLine = 	processedLine.replace("<%=", "@@@");
			 //processedLine =	processedLine.replace("%>", "@@");
			processedLine    =  processTagContainingClosingPairTag(lines,parserContext);
			}
			catch(EndTagNotFoundException ex){
				continue;
			}
			lines.clear();
			System.out.println(processedLine);
			//stringBuffer.append(processedLine);
			
		}
	}

	private static String processTagClosedWithinSingleLine(List<String> strings, ParserContext parserContext) {
		String line = convertToSingleLine(strings);
		StringBuffer stringBuffer = new StringBuffer(); 
		List<String> tokens = new ArrayList<>();
		boolean isLineProcessed = false;
		
		List<String> listOfTags = new ArrayList<>();
		for(String tag :tagClosedWithinSingleLine){
			AddTagsBasedOnOccurance(line, listOfTags, tag);
		}	
		
		String previousTag = "";
		for(String tag :listOfTags)
		{
			int indexOf = line.indexOf(tag);
			// int endIndex = line.indexOf("/>", indexOf);
			int endIndex = getLastIndex(line, indexOf);
			if (indexOf > -1 ) {
				if(endIndex > -1){
		
				if(tag.equalsIgnoreCase(previousTag)){
				       tokens.remove(tokens.size()-1);
				}
					
				//System.out.println("Before Processing");
				//System.out.println(line);
				tokens.add(line.substring(0,indexOf));
			    String matchedTag = line.substring(indexOf, endIndex-1);
			   // System.out.println("*********Found Matching Tag");
			   // System.out.println(matchedTag);
			   
			    String replaceTag = defaultTagParser.get(tag).parse((matchedTag),parserContext);
			
			    //System.out.println("Nested Path    "+ parserContext.getNestedPath());
			    
				tokens.add(replaceTag);
				//System.out.println("*********Replace with ");
				//System.out.println(replaceTag);
				isLineProcessed = true;
				int length=  line.length();
				if(length>endIndex+2){
					String remaining = line.substring(endIndex+2, length);
					previousTag = tag;
					tokens.add(remaining);
					//System.out.println("Remaining string" + remaining);
					line= remaining;
				}else
				{
				   line="";
				}
				}else {
					throw new EndTagNotFoundException("End Tag is not found for " + tag);
				}
			}
		}
		for(String token: tokens){
			stringBuffer.append(token);
		}
		if(!isLineProcessed){
			return line;
		}
		return stringBuffer.toString();
	}

	private static void AddTagsBasedOnOccurance(String line,
			List<String> string, String tag) {
		int indexOf = line.indexOf(tag);
		// int endIndex = line.indexOf("/>", indexOf);
		int endIndex = getLastIndex(line, indexOf);
		if (indexOf > -1) {
			if(endIndex > -1){
				string.add(tag);
				if(endIndex< line.length()){
					AddTagsBasedOnOccurance(line.substring(endIndex, line.length()),string,tag);
				}else{
					return;
				}
			}else {
			throw new EndTagNotFoundException("End Tag is not found for " + tag);
		}
	}
	}

	private static String processTagContainingClosingPairTag(List<String> strings, ParserContext parserContext) {
		String line = convertToSingleLine(strings);
		StringBuffer stringBuffer = new StringBuffer(); 
		List<String> tokens = new ArrayList<>();
		boolean isLineProcessed = false;
		
		List<String> listOfTags = new ArrayList<>();
		for(String tag :tagContainingClosingPairTag){
			AddTagsBasedOnOccurance(line, listOfTags, tag);
		}	
		
		
		for (String tag : listOfTags) {
			int indexOf = line.indexOf(tag);
			int endIndex = getLastIndex(line, indexOf);

			if (indexOf >= 0) {
				if (endIndex > -1) {
					 //System.out.println("Before Processing");
					 //System.out.println(line);
					tokens.add(line.substring(0, indexOf));
					String matchedTag = line.substring(indexOf, endIndex);
					//System.out.println("*********Found Matching Tag*******");
					//System.out.println(matchedTag);
					   
					String replaceTag = defaultTagParser.get(tag).parse((matchedTag), parserContext);
					// System.out.println("Nested Path    "+ parserContext.getNestedPath());
					   
					tokens.add(replaceTag);
					//System.out.println("********Replace with*********");
					//System.out.println(replaceTag);
					isLineProcessed = true;
					int length = line.length();
					if (length > endIndex + 1) {
						String remaining = line.substring(endIndex + 1, length);
						// System.out.println("Remaining string" + remaining);
						line = remaining;
					} else {
						line = "";
					}
				}else {
					throw new EndTagNotFoundException("End Tag is not found for " + tag);
				}
			}
		}
		
		for(String token: tokens){
			stringBuffer.append(token);
		}
		stringBuffer.append(line);
		if(!isLineProcessed){
			return line;
		}
		return stringBuffer.toString();
	}
	
	
	private static String convertToSingleLine(List<String> strings) {
		StringBuffer lines = new StringBuffer();
		for (String string : strings) {
			lines.append(string);
		}
		String line= lines.toString();
		return line;
	}

	private static int getLastIndex(String line, int startIndex) {
		int countofInxex = 0;
		int index = 0;
		if (startIndex < 0)
			startIndex *= 0; // power of multiplication
		boolean isLastIndexFound = false;
		for (index = startIndex; index < line.length(); index++) {
			if (line.charAt(index) == '<')
				countofInxex++;
			if (line.charAt(index) == '>')
				countofInxex--;
			if (countofInxex == 0) {
				isLastIndexFound = true;
				break;
			}
		}
		if (isLastIndexFound)
			return index;
		else
			return -1;
	}

}
