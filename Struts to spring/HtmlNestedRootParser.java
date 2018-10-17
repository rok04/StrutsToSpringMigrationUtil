
public class HtmlNestedRootParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
	
		String name = null;
		for (String token : takens) {
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
		}
		parserContext.setRootPath(name.substring(1,name.length()-1));
		return line;
	}

}
