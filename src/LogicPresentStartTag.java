
public class LogicPresentStartTag extends AbstractTagParser {

	public String doParse(String line, ParserContext parserContext) {
	
		String[] takens = line.split("\\s+");
		String name = null;
		String property = null;
		
		for (String token : takens) {
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
		}
		StringBuffer  replacedString = new StringBuffer("");
		replacedString.append("<c:if test=");
		replacedString.append(TagParser.DOUBLE_QUOTE);
		replacedString.append("${not empty ");
		replacedString.append(removeLeadingAndTrailingCommas(name));
		
		if(property != null){
			replacedString.append(".");
			replacedString.append(removeLeadingAndTrailingCommas(property));
		}
		replacedString.append("} >");
		replacedString.append(TagParser.DOUBLE_QUOTE);

		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		return false;
	}
}
