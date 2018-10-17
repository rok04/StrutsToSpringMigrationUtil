
public class HtmlNestedNestEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String previousPath = parserContext.getNestedPath();
		int index= previousPath.lastIndexOf(".");
		if(index>0){
		  parserContext.setNestedPath(previousPath.substring(0,index));
		}else{
		   parserContext.setNestedPath(null);
		}
		return line;
	}

}
