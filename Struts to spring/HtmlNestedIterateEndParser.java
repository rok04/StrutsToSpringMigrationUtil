
public class HtmlNestedIterateEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		
		String previousPath = parserContext.getNestedPath();
		int index= previousPath.lastIndexOf(".");
		parserContext.setNestedPath(previousPath.substring(0,index));
		return line;
	}

}
