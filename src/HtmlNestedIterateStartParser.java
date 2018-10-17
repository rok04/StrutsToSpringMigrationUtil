
public class HtmlNestedIterateStartParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		
		String property = null;
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
		}
		String previousPath = parserContext.getNestedPath();
		parserContext.setNestedPath(previousPath+"."+property.substring(1,property.length()-1));
		return line;
	}

}
