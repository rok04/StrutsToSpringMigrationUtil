
public class HtmlNestedNestStartParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		
		String property = null;
		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
		}
		String nestedPath = parserContext.getNestedPath();
		if(nestedPath == null){
			nestedPath = property.substring(1,property.length()-1);
		}else{
			nestedPath = nestedPath+"."+property.substring(1,property.length()-1);
		}
		parserContext.setNestedPath(nestedPath);
		return line;
	}

}
