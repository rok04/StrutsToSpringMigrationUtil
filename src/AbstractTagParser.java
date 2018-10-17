import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTagParser implements TagParser {

	protected boolean isEmpty(String string) {
		return string == null || "".equalsIgnoreCase(string);
	}

	protected abstract boolean appendModelAttributeName();
	
	
	protected String removeWhiteSpacesAroundEqualSign(String line) {
		return line.replaceAll("\\s+=", "=").replaceAll("=\\s+", "=");
	}

	protected String removeWhiteSpacesAroundBrackets(String line) {
		return line.replaceAll("\\(\\s+", "(").replaceAll("\\s+\\)", ")");
	}

	protected String replaceSingleQuotes(String line) {
		return line.replaceAll("'", "\"");
	}
	
	protected String removeLeadingAndTrailingCommas(String property) {
		String result = property;
		if(!isEmpty(result)){
		    if(result.charAt(0) == '"'){ 
		    	result = result.substring(1);    		
		    }
		    if(result.charAt(result.length()-1)== '"'){
		    	result = result.substring(0, result.length() - 1);
		    }
		}
	    return result;

	}

	protected String getElementPath(String property, ParserContext parserContext) {
		StringBuffer pathPrefix = new StringBuffer();
		// If This tag is inside a nested iterate tag, then use parent local
		// variable
		if (parserContext.isInsideNestedIterate()) {
			pathPrefix.append(parserContext.getCurrentIterateVariable());
		} else {
			boolean isDotRequired = false;
			if(appendModelAttributeName() && !isEmpty(parserContext.getModelAttributeName()))
			{
				pathPrefix.append(parserContext.getModelAttributeName());
				isDotRequired = true;
			}
			if(!isEmpty(parserContext.getNestedPath())){
				if(isDotRequired){
					pathPrefix.append(".");
				}
				pathPrefix.append(parserContext.getNestedPath());
			}
		}
		return !isEmpty(pathPrefix.toString()) ? pathPrefix.toString() + "."
				+ removeLeadingAndTrailingCommas(property)
				: removeLeadingAndTrailingCommas(property);
	}

	public String parse(String line, ParserContext parserContext) {
		return restoreWhiteSpacesWithinQuotes
					(restoreScriptletCharacter
						(doParse
							(replaceWhiteSpacesWithinQuotes
								(replaceSingleQuotes
									(replaceScripletCharacters
											(removeWhiteSpacesAroundBrackets
												(removeWhiteSpacesAroundEqualSign(line))))), parserContext)));
	}

	public abstract String doParse(String line, ParserContext parserContext);

	protected String restoreScriptletCharacter(String line){
		return line.replaceAll("@@@", "<%=").replaceAll("@@", "%>");
	}

	protected String restoreWhiteSpacesWithinQuotes(String line){
		return line.replaceAll("~", " ");
	}
	protected String replaceWhiteSpacesWithinQuotes(String line) {
		boolean quotesPairFound = true;
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '"') {
				quotesPairFound = !quotesPairFound;
				stringBuffer.append(line.charAt(i));
			} else if (line.charAt(i) == ' ' && !quotesPairFound) {
				stringBuffer.append('~');
			} else {
				stringBuffer.append(line.charAt(i));
			}
		}
		return stringBuffer.toString();
	}
	
	protected String replaceDoubleQuoteWithSingleQuotes(String line) {
		return line.replaceAll("\"", "'");
	}
	
	
	protected String replaceScripletCharacters(String line) {
		StringBuffer replaceString = new StringBuffer();
		int startIndex = line.indexOf("<%=");
		int endIndex = line.indexOf("%>");
		if(!(startIndex> -1 && endIndex > -1)){
			return line;
		}
		
		int lastIndex = line.lastIndexOf("%>");
		do
		{
			startIndex = line.indexOf("<%=");
			endIndex = line.indexOf("%>");
	
			if(startIndex > -1 && endIndex > -1)
			{
				replaceString.append(line.substring(0,startIndex));
				replaceString.append(replaceDoubleQuoteWithSingleQuotes(line.substring(startIndex, endIndex).replaceAll("<%=", "@@@")));
				replaceString.append("@@");
				replaceString.append(line.substring(endIndex+2,line.length()));
				
				line = replaceString.toString();
			}
		}while(endIndex != lastIndex);
		return replaceString.toString();
	}
	
}
