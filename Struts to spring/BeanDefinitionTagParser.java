public class BeanDefinitionTagParser implements TagParser {

	public String parse(String line,ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		//System.out.println("Token are as follow");
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:set ");

		String name = null;
		String id = null;
		String property = null;
		String toScope = null;

		for (String token : takens) {

			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("id")) {
				id = token.split("\\=")[1];
			}
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
			if (token.startsWith("toScope")) {
				toScope = token.split("\\=")[1];
			}
		}
		replacedString.append("var");
		replacedString.append("=");
		replacedString.append(id);
		replacedString.append(" ");
		replacedString.append("value=");
		replacedString.append("\"");
		replacedString.append("${");
		replacedString.append(name.substring(1, name.length() - 1));
		replacedString.append(".");
		replacedString.append(property.substring(1, property.length() - 1));
		replacedString.append("}\"");
		replacedString.append(" ");
		replacedString.append("scope");
		replacedString.append("=");
		replacedString.append(toScope);
		replacedString.append(" ");
		replacedString.append("/>");
		return replacedString.toString();
	}

}
