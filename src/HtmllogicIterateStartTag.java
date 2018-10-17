
public class HtmllogicIterateStartTag extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String id = null;
		String name = null;
		String property = null;
		StringBuffer items = new StringBuffer();
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:forEach");
		for (String token : takens) {
			if (token.startsWith("id")) {
				id = token.split("\\=")[1];
				replacedString.append(" ");
				replacedString.append("var=");
				replacedString.append(id);
				replacedString.append(" ");

			}
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
				items.append(name);

			}
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
				// TODO Change here
				items.append(".");
				items.append(property.substring(1, property.length()));
			}

		}
		replacedString.append("items =${");
		replacedString.append(items);

		replacedString.append("}");
		replacedString.append(">");
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return false;
	}

}
