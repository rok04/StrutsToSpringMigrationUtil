
public class HtmlNestedSelectStartParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		String size = null;
		String styleClass = null;
		String disabled = null;

		StringBuffer replacedString = new StringBuffer();

		for (String token : takens) {
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
				String previousPath = parserContext.getNestedPath();
				String currentPath = property.substring(1,property.length()-1);
		
				parserContext.addPaths(currentPath);
				parserContext.setNestedPath(previousPath + "." + currentPath);

				replacedString.append("<form:select ");

				replacedString.append("path=");
				replacedString.append("\"");
				replacedString.append(parserContext.getNestedPath());
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
				replacedString.append("cssClass");
				replacedString.append("=");
				replacedString.append(styleClass);
				replacedString.append(" ");

				
			}
			if (token.startsWith("disabled")) {
				disabled = token.split("\\=")[1];
				replacedString.append("disabled");
				replacedString.append("=");
				replacedString.append(disabled);
				replacedString.append(" ");

			}
			
			if(token.startsWith("onchange")){
				replacedString.append(token);
				replacedString.append(" ");
			}
			
		}
		replacedString.append(">");
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		// TODO Auto-generated method stub
		return false;
	}

}
