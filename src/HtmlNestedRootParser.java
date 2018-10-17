
public class HtmlNestedRootParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
	
		String name = null;
		for (String token : takens) {
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
		}
	    parserContext.setRootPath(removeLeadingAndTrailingCommas(name));
		return line;
	}
	
	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return false;
	}

}
