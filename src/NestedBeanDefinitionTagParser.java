public class NestedBeanDefinitionTagParser extends AbstractTagParser implements	TagParser {

	@Override
	protected boolean appendModelAttributeName() {
		return false;
	}

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String name = null;
		String id = null;
		String property = null;
		String toScope = null;
		String value = null;

		for (String token : takens) {

			if (token.toLowerCase().startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.toLowerCase().startsWith("id")) {
				id = token.split("\\=")[1];
			}
			if (token.toLowerCase().startsWith("name")) {
				name = token.split("\\=")[1];
			}
			if (token.toLowerCase().contains("scope")) {
				toScope = token.split("\\=")[1];
			}
			if (token.toLowerCase().startsWith("value")) {
				value = token.split("\\=")[1];
			}
		}
		
		if(!isEmpty(property))
		{
			StringBuffer replacedString = new StringBuffer();
			replacedString.append("<c:set ");
			replacedString.append("var");
			replacedString.append("=");
			replacedString.append(id);
			replacedString.append(" ");
			replacedString.append("value=");
			replacedString.append(DOUBLE_QUOTE);
			replacedString.append("${");
			
			if(!isEmpty(name)){
				replacedString.append(removeLeadingAndTrailingCommas(name));
				replacedString.append(".");
			}
			replacedString.append(getElementPath(property, parserContext));
			replacedString.append("}");
			replacedString.append(DOUBLE_QUOTE);
			replacedString.append(" ");
			if(!isEmpty(toScope)){
				replacedString.append("scope");
				replacedString.append("=");
				replacedString.append(toScope);
			}
			replacedString.append(" ");
			replacedString.append("/>");
		}
		return line;
	}

}
