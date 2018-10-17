
public class HtmlNestedIterateEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		
		String previousPath = parserContext.getNestedPath();
	
		String currentPath =  parserContext.getCurrentPath();
		
		int index= previousPath.lastIndexOf(currentPath);
		parserContext.setNestedPath(previousPath.substring(0,index-1));
		return "</form:select>";
	}

}
