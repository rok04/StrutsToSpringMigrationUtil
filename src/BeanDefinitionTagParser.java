
public class BeanDefinitionTagParser extends AbstractTagParser {

	@Override
	public String doParse(String line, ParserContext parserContext) {
		
		String[] takens = line.split("\\s+");
		//System.out.println("Token are as follow");
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:set ");

		String name = null;
		String id = null;
		String property = null;
		String toScope = null;
		String value = null;
		

		for (String token : takens) {

			if (token.toLowerCase().startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.toLowerCase().startsWith("id")) {
				id = token.split("\\=")[1];
			}
			if (token.toLowerCase().startsWith("name")) {
				name = token.split("\\=")[1];
			}
			if (token.toLowerCase().contains("scope")) {
				toScope = token.split("\\=")[1];
			}
			if (token.toLowerCase().startsWith("value")) {
				value = token.split("\\=")[1];
			}
		}
		replacedString.append("var");
		replacedString.append("=");
		replacedString.append(id);
		replacedString.append(" ");
		replacedString.append("value=");
		replacedString.append("\"");
		replacedString.append("${");
		if(!isEmpty(value)){
			replacedString.append(value);
		}else{
			replacedString.append(name.substring(1, name.length() - 1));
			if(!isEmpty(property)){
				replacedString.append(".");
				replacedString.append(property.substring(1, property.length() - 1));
			}
		}
		replacedString.append("}\"");
		replacedString.append(" ");
		
		if(!isEmpty(toScope)){
			replacedString.append("scope");
			replacedString.append("=");
			replacedString.append(toScope);
		}
		replacedString.append(" ");
		replacedString.append("/>");
		
		return replacedString.toString();
	}

	@Override
	protected boolean appendModelAttributeName() {
		return false;
	}
	
}
