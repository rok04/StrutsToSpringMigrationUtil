
public class NestedIterateEndTag implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {

		//String nestedPath = parserContext.getNestedPath();
		//String currentPath = parserContext.getCurrentPath();
		//int index = nestedPath.lastIndexOf(currentPath);
		//parserContext.setNestedPath(nestedPath.substring(0, index - 1));
		parserContext.removeNestedIterateVariable();
		return "</c:forEach>";
	}

}
