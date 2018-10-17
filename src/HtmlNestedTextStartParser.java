
public class HtmlNestedTextStartParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		String size = null;
		String styleClass = null;
		StringBuffer maxLength = new StringBuffer("");
		String disabled = null;
		StringBuffer replacedString = new StringBuffer();
		String previousPath = parserContext.getNestedPath();
		
		for (String token : takens) {
			
			if (token.startsWith("property")) {
				
				property = token.split("\\=")[1];
				String textPath = previousPath+"."+property.substring(1,property.length()-1);
				
				
				replacedString.append("<form:input ");
				
				replacedString.append("path=");
				replacedString.append("\"");
				replacedString.append(textPath);
				
				replacedString.append("\"");
				replacedString.append(" ");
				
			}
			if (token.startsWith("size")) {
				size = token.split("\\=")[1];
				replacedString.append("size");
				replacedString.append("=");
				replacedString.append(size);
				replacedString.append(" ");
			}
			if (token.startsWith("styleClass")) {
				styleClass = token.split("\\=")[1];
				if(styleClass != null){
				replacedString.append("cssClass");
				replacedString.append("=");
				replacedString.append(styleClass);
				replacedString.append(" ");
				}
			}
			if (token.startsWith("maxlength")) {
				maxLength.append(token.split("\\=")[1]);
				replacedString.append("maxlength");
				replacedString.append("=");
				replacedString.append(maxLength);
				replacedString.append(" ");
			}
			if(token.startsWith("disabled")){
				disabled = token.split("\\=")[1];
				replacedString.append("disabled");
				replacedString.append("=");
				replacedString.append(disabled);
				replacedString.append(" ");
			}
			if(token.startsWith("onchange")) 
			{
				replacedString.append(token);	
			}
		}
		
		replacedString.append("/>");
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		return false;
	}
}
	
	