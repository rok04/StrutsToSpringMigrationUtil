
public class HtmlOptionsTagParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String collection = null;
		String property = null;
		String labelProperty = null;
		for (String token : takens) {
			if (token.startsWith("collection")) {
				collection = token.split("\\=")[1];
			}
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("labelProperty")) {
				labelProperty = token.split("\\=")[1];
			}
		}
		
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<form:options ");
		replacedString.append("items=");
		replacedString.append("\"");
		replacedString.append("${");
		replacedString.append(collection.substring(1, collection.length()-1));
		replacedString.append("}");
		replacedString.append("\"");
		replacedString.append(" ");
		replacedString.append("itemLabel");
		replacedString.append("=");
		replacedString.append(labelProperty);
		replacedString.append(" ");
		replacedString.append("itemKey");
		replacedString.append("=");
		replacedString.append(property);
		replacedString.append(" ");
		replacedString.append("/>");
		return replacedString.toString();
	}

}
