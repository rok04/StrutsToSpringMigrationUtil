
public class HtmlOptionStartTagParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String value = null;
		for (String token : takens) {
			if (token.startsWith("value")) {
				 int length = token.split("\\=").length;
				 if(length>1){
				   value = token.split("\\=")[1];
				 }
			}
		}
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<form:option ");
	    replacedString.append("value=");
		if(value!=null){
	      replacedString.append(value);
		}else{
			replacedString.append("\"\"");
		}
		return replacedString.toString();
		
	}

}
