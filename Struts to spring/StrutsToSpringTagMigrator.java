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
   
	private static String[] tagClosedWithinSingleLine = {"<html:options", "<bean:define","<html:errors","<nested:text"};

	private static String[] tagContainingClosingPairTag = {"<nested:form","<html:form","<nested:root","<nested:nest","</nested:nest","<nested:iterate","</nested:iterate",
		"<nested:select","</nested:select","<html:option","</html:option" 
	};
	
	
	static {
		defaultTagParser.put("<bean:define", new BeanDefinitionTagParser());
		defaultTagParser.put("<html:errors", new HtmlErrorsTagParser());
		defaultTagParser.put("<html:form", new HtmlFormTagParser());
		defaultTagParser.put("<nested:form", new HtmlFormTagParser());
		defaultTagParser.put("<nested:root", new HtmlNestedRootParser());
		defaultTagParser.put("<nested:nest", new HtmlNestedNestStartParser());
		defaultTagParser.put("</nested:nest", new HtmlNestedNestEndParser());
		defaultTagParser.put("<nested:iterate", new HtmlNestedIterateStartParser());
		defaultTagParser.put("</nested:iterate", new HtmlNestedIterateEndParser());
		defaultTagParser.put("<nested:select", new HtmlNestedSelectStartParser());
		defaultTagParser.put("</nested:select", new HtmlNestedIterateEndParser());
		defaultTagParser.put("<html:option", new HtmlOptionStartTagParser());
		defaultTagParser.put("</html:option", new HtmlOptionEndTagParser());
		defaultTagParser.put("<html:options", new HtmlOptionsTagParser());
		defaultTagParser.put("<nested:text", new HtmlNestedTextStartParser());
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		
		ParserContext parserContext = new ParserContext();
		FileReader fileReader = new FileReader(
				new File(
						"D:\\Enbridge\\ENTRAC-SOURCE\\ENTRAC-SOURCE\\j2ee\\web\\jsp\\au\\cm\\addnewpool.jsp"));
		BufferedReader br = new BufferedReader(fileReader);
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
			processedLine    =  processTagContainingClosingPairTag(lines,parserContext);
			}
			catch(EndTagNotFoundException ex){
				continue;
			}
			lines.clear();
			System.out.println(processedLine);
			stringBuffer.append(processedLine);
			
		}
		
		
	}

	private static String processTagClosedWithinSingleLine(List<String> strings, ParserContext parserContext) {
		String line = convertToSingleLine(strings);
		StringBuffer stringBuffer = new StringBuffer(); 
		List<String> tokens = new ArrayList<>();
		boolean isLineProcessed = false;
		for(String tag :tagClosedWithinSingleLine)
		{
			int indexOf = line.indexOf(tag);
			int endIndex = line.indexOf("/>", indexOf);
			if (indexOf > -1) {
				if(endIndex > -1){
				
				//System.out.println("Before Processing");
				//System.out.println(line);
				tokens.add(line.substring(0,indexOf));
			    String matchedTag = line.substring(indexOf, endIndex-1);
			   // System.out.println("Natched Tag" + matchedTag);
				
			    String replaceTag = defaultTagParser.get(tag).parse((matchedTag),parserContext);
			
				tokens.add(replaceTag);
				//System.out.println("Replace Tag" + replaceTag);
				isLineProcessed = true;
				int length=  line.length();
				if(length>endIndex+2){
					String remaining = line.substring(endIndex+2, length);
					tokens.add(remaining);
					//System.out.println("Remaining string" + remaining);
					line= remaining;
				}
				line="";
				
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

	private static String processTagContainingClosingPairTag(List<String> strings, ParserContext parserContext) {
		String line = convertToSingleLine(strings);
		StringBuffer stringBuffer = new StringBuffer(); 
		List<String> tokens = new ArrayList<>();
		boolean isLineProcessed = false;
		for(String tag :tagContainingClosingPairTag)
		{
			int indexOf = line.indexOf(tag);
			int endIndex = line.indexOf(">", indexOf);
			if (indexOf > -1) {
				if(endIndex > -1){
				//System.out.println("Before Processing");
				//System.out.println(line);
				tokens.add(line.substring(0,indexOf));
			    String matchedTag = line.substring(indexOf, endIndex);
			   // System.out.println("Natched Tag" + matchedTag);
				
			    String replaceTag = defaultTagParser.get(tag).parse((matchedTag),parserContext);
			
				tokens.add(replaceTag);
				//System.out.println("Replace Tag" + replaceTag);
				isLineProcessed = true;
				int length=  line.length();
				if(length>endIndex+1){
					String remaining = line.substring(endIndex+1, length);
					tokens.add(remaining);
					//System.out.println("Remaining string" + remaining);
					line= remaining;
				}
				line="";
				
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
	
	
	private static String convertToSingleLine(List<String> strings) {
		StringBuffer lines = new StringBuffer(); 
		for(String string: strings)	
		{
			lines.append(string);
		}
		String line= lines.toString();
		return line;
	}

}
