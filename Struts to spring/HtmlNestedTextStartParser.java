
public class HtmlNestedTextStartParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		String size = null;
		String styleClass = null;
		String maxLength = null;
		
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("size")) {
				size = token.split("\\=")[1];
			}
			if (token.startsWith("styleClass")) {
				styleClass = token.split("\\=")[1];
			}
			if (token.startsWith("maxlength")) {
				maxLength = token.split("\\=")[1];
			}
		}
		String previousPath = parserContext.getNestedPath();
		String textPath = previousPath+"."+property.substring(1,property.length()-1);
		
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<form:input ");
		
		replacedString.append("path=");
		replacedString.append("\"");
		replacedString.append(textPath);
		replacedString.append("\"");
		replacedString.append(" ");
		replacedString.append("size");
		replacedString.append("=");
		replacedString.append(size);
		replacedString.append(" ");
		replacedString.append("maxlength");
		replacedString.append("=");
		replacedString.append(maxLength);
		replacedString.append(" ");
		replacedString.append("cssClass");
		replacedString.append("=");
		replacedString.append(styleClass);
		replacedString.append(" ");
		replacedString.append("/>");
		return replacedString.toString();
	}

}
