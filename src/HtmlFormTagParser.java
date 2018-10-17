
public class HtmlFormTagParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext context) {
		String[] takens = line.split("\\s+");
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<form:form ");
		String name = null;
		String action = null;
		for (String token : takens) {
			if (token.startsWith("action")) {
				action = token.split("\\=")[1];
			}
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
		}
		
		if(!isEmpty(name)){
			replacedString.append("name=");
			replacedString.append(name);
			replacedString.append(" ");
			String modelAttribute = convertToCamelCase(removeLeadingAndTrailingCommas(name));
			context.setModelAttributeName(modelAttribute);
			replacedString.append("modelAttribute");
			replacedString.append("=");
			replacedString.append("\"");
			replacedString.append(modelAttribute);
			replacedString.append(" ");
		}
		
		replacedString.append("action");
		replacedString.append("=");
		replacedString.append("\"");
		int indexOf = action.lastIndexOf("/");
		replacedString.append(action.substring(indexOf+1).replace(".do", ".htm"));
		replacedString.append(" ");
	
		replacedString.append("method=");
		replacedString.append('"');
		replacedString.append("POST");
		replacedString.append('"');
		replacedString.append(" ");
		
		
		
		
		replacedString.append("/>");
		return replacedString.toString();
	}

	private String convertToCamelCase(String name) {
		StringBuffer replacedString = new StringBuffer();
		String upperCase = name.substring(0,1);
		replacedString.append(upperCase.toLowerCase());
		replacedString.append(name.substring(1));
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		return false;
	}



}
