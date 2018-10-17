
public class HtmlNestedNestStartParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		
		String property = null;
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
		}
		
		String nestedPath = parserContext.getNestedPath();
		String currentPath = property.substring(1,property.length()-1);
		
		parserContext.addPaths(currentPath);
		
		if(nestedPath == null || "".equals(nestedPath) ){
			nestedPath = property.substring(1,property.length()-1);
		}else{
			nestedPath = nestedPath+"."+property.substring(1,property.length()-1);
		}
		parserContext.setNestedPath(nestedPath);
		return line;
	}

	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return false;
	}

}
