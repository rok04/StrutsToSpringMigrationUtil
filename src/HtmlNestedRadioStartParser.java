
public class HtmlNestedRadioStartParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		String styleClass = null;
		String value = null;
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("value")) {
				value = token.split("\\=")[1];
			}
			if (token.startsWith("styleClass")) {
				styleClass = token.split("\\=")[1];
			}
		}
		
		
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<form:radiobutton ");
		
		replacedString.append("path=");
		replacedString.append("\"");
		replacedString.append(getElementPath(property, parserContext));
		replacedString.append("\"");
		replacedString.append(" ");
		replacedString.append("value=");
    	replacedString.append("\"");
    	replacedString.append(value);
    	replacedString.append("\"");
		replacedString.append(" ");
		replacedString.append("cssClass");
		replacedString.append("=");
		replacedString.append(styleClass);
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
