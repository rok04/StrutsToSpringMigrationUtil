public class HtmlNestedHiddenParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
	String[] takens = line.split("\\s+");
	String property = null;
	String value = null;
	for (String token : takens) {
		if (token.startsWith("property")) {
			property = token.split("\\=")[1];
		}
		if (token.startsWith("value")) {
			value = token.split("\\=")[1];
		}
	}
	
	String previousPath = parserContext.getNestedPath();
	String textPath = "";
	if(previousPath != null){
	   textPath = previousPath+"."+property.substring(1,property.length()-1);
	}else{
		textPath = property.substring(1,property.length()-1);
	}

	StringBuffer replacedString = new StringBuffer();
	if(!isEmpty(value))
	{
		replacedString.append("<input type=\"hidden\" ");
		replacedString.append("name=");
		replacedString.append("\"");
		replacedString.append(textPath);
		replacedString.append("\"");
		replacedString.append(" ");
		replacedString.append("value=");
	    replacedString.append("\"");
    	replacedString.append(value);
    	replacedString.append("\"");
    }else{
    	replacedString.append("<form:hidden path=\"");
		replacedString.append(textPath);
	    replacedString.append("\"");
    }
	
	replacedString.append(" ");
	replacedString.append("/>");
	return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return false;
	}
}
