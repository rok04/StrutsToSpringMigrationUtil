
public class HtmlNestedWriteParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
		}
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:out value=\"${");
		replacedString.append(getElementPath(property, parserContext));
		replacedString.append("}\" ");
		replacedString.append("/>");
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		return true;
	}

	
}
