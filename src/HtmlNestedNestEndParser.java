
public class HtmlNestedNestEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String previousPath = parserContext.getNestedPath();
		
		String currentPath =  parserContext.getCurrentPath();
		if(currentPath != null){
	     	int index= previousPath.lastIndexOf(currentPath);
		    parserContext.setNestedPath(previousPath.substring(0,index-1>0? index-1:0));
		}
		
		return line;
	}

}
