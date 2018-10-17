
public class HtmlBeanWriteParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		
		String[] takens = line.split("\\s+");
		String name = null;
		String property = null;
		
		for (String token : takens) {
			if (token.startsWith("name")) {
				name = token.split("\"")[1];
			}
			if (token.startsWith("property")) {
				property = token.split("\"")[1];
			}
		}
		
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:out value=\"${");
		replacedString.append(name);
		replacedString.append(".");
		replacedString.append(property);
		replacedString.append("}\" ");
		replacedString.append("/>");
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		return false;
	}

	
}
