
public class LogicNestedEqualStartTag  extends AbstractTagParser{

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		String value = null;
		String name = null;
		
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("value")) {
				value = token.split("\\=")[1];
			}
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
		}
		StringBuffer  replacedString = new StringBuffer("");
		replacedString.append("<c:if test=");
		replacedString.append("\"");
		replacedString.append("${");
		if(!isEmpty(property)){
			replacedString.append(getElementPath(property, parserContext));
		}else{
			replacedString.append(removeLeadingAndTrailingCommas(name));
		}
		replacedString.append(" eq ");
		replacedString.append(SINGLE_QUOTE);
		replacedString.append(removeLeadingAndTrailingCommas(value));
		replacedString.append(SINGLE_QUOTE);
		replacedString.append("} >");
		replacedString.append('"');
		return replacedString.toString();

	}

	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return true;
	}
}
