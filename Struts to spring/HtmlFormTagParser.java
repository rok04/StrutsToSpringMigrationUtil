
public class HtmlFormTagParser implements TagParser {

	@Override
	public String parse(String line, ParserContext context) {
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
		replacedString.append("name=");
		replacedString.append(name);
		replacedString.append(" ");
		replacedString.append("action");
		replacedString.append("=");
		replacedString.append("\"");
		int indexOf = action.lastIndexOf("/");
		replacedString.append(action.substring(indexOf+1).replace(".do", ".htm"));
		replacedString.append(" ");
		replacedString.append("modelAttribute");
		replacedString.append("=");
		replacedString.append("\"");
		String upperCase = name.substring(1,2);
		
		replacedString.append(upperCase.toLowerCase());
		replacedString.append(name.substring(2));
		replacedString.append(" ");
		replacedString.append("/>");
		return replacedString.toString();
	}

}
