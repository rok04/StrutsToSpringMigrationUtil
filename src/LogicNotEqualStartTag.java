public class LogicNotEqualStartTag  extends AbstractTagParser{

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String name = null;
		String property = null;
		String value = null;
		
		
		for (String token : takens) {
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("value")) {
				value = token.split("\\=")[1];
			}
		}
		StringBuffer  replacedString = new StringBuffer("");
		replacedString.append("<c:if test=");
		replacedString.append("\"");
		replacedString.append("${");
		replacedString.append(removeLeadingAndTrailingCommas(name));
		
		if(property != null){
			replacedString.append(".");
			replacedString.append(removeLeadingAndTrailingCommas(property));
		}
		replacedString.append(" ne ");
		replacedString.append(TagParser.SINGLE_QUOTE);
		replacedString.append(removeLeadingAndTrailingCommas(value));
		replacedString.append(TagParser.SINGLE_QUOTE);
		replacedString.append("}>");
		replacedString.append('"');
		return replacedString.toString();

	}
	
	
	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return true;
	}
}
