
public class HtmlNestedLinkStartParser extends AbstractTagParser implements TagParser {

	@Override
	protected boolean appendModelAttributeName() {
		return true;
	}

	@Override
	public String doParse(String line, ParserContext parserContext) {
	
		String[] takens = line.split("\\s+");
		String name = null;
		String property = null;
		String page = null;
		String href = null;
		String paramId = null;
		String paramName = null;
		String paramProperty = null;
		String styleClass = null;
		
		for (String token : takens) {
			if (token.startsWith("name")) {
				name = token.split("\\=")[1];
			}
			if (token.startsWith("property")) {
				property = token.split("\\=")[1];
			}
			if (token.startsWith("page")) {
				page = token.split("\\=")[1];
			}
			if (token.startsWith("href")) {
				href = token.split("\\=")[1];
			}
			if (token.startsWith("paramId")) {
				paramId = token.split("\\=")[1];
			}
			if (token.startsWith("paramName")) {
				paramName = token.split("\\=")[1];
			}
			if (token.startsWith("paramProperty")) {
				paramProperty = token.split("\\=")[1];
			}
			if (token.startsWith("styleClass")) {
				styleClass = token.split("\\=")[1];
			}
		}


		 
		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:url ");
		String value = "";
		if(!isEmpty(href)){
			value = href;
		}else{
			value = page;
		}
		replacedString.append("value=");
		replacedString.append(value);
		replacedString.append(" ");
		replacedString.append("var=");
		replacedString.append(DOUBLE_QUOTE);
		replacedString.append(extractLinkVarName(removeLeadingAndTrailingCommas(value)));
		replacedString.append(DOUBLE_QUOTE);
		if(hasParameters(name, property, paramId, paramProperty)){
			replacedString.append(">");
			replacedString.append("\n");
			replacedString.append("\t");
			replacedString.append(addParameters(name, property, paramId,paramName,paramProperty,parserContext));
			replacedString.append("\n");
			replacedString.append("</c:url>");
		}else{
			replacedString.append(" />");
		}
		replacedString.append("\n");
		replacedString.append("<a href=");
		replacedString.append(SINGLE_QUOTE);
		replacedString.append("<c:out ");
		replacedString.append("value=");
		replacedString.append(DOUBLE_QUOTE);
		replacedString.append("${");
		replacedString.append(extractLinkVarName(removeLeadingAndTrailingCommas(value)));
		replacedString.append("}");
		replacedString.append(DOUBLE_QUOTE);
		replacedString.append(SINGLE_QUOTE);
		
		if(!isEmpty(styleClass)){
			replacedString.append(" class=");
			replacedString.append(styleClass);
		
		}
		replacedString.append(" >");
	
		return replacedString.toString();
	}

	private String addParameters(String name, String property, String paramId,
			String paramName,String paramProperty, ParserContext parserContext) {
		StringBuffer stringBuffer = new StringBuffer();
		if(!isEmpty(paramId) && (!isEmpty(paramName) || !isEmpty(paramProperty))){
		
			stringBuffer.append("<c:param ");
			stringBuffer.append("name ");
			stringBuffer.append(paramId);
			stringBuffer.append(" value=");
			stringBuffer.append(!isEmpty(paramName)? paramName : paramProperty);
			stringBuffer.append(" />");
			return stringBuffer.toString();
		}else{
			stringBuffer.append("<c:forEach ");
			stringBuffer.append("var=");
			stringBuffer.append(DOUBLE_QUOTE);
			stringBuffer.append("queryParameterMap");
			stringBuffer.append(DOUBLE_QUOTE);
			stringBuffer.append(" items=\"${");
			
			String currentPath = "";
			
			boolean isDotRequired = false;
			if(!isEmpty(name))
			{
				currentPath = removeLeadingAndTrailingCommas(name);
				isDotRequired = true;
			}
			
			if(!isEmpty(property)){
				if(isDotRequired)
				{
					currentPath = currentPath + "." + removeLeadingAndTrailingCommas(property);
				}else{
					currentPath = removeLeadingAndTrailingCommas(property);
							
				}
			}
			
			stringBuffer.append(getElementPath(currentPath, parserContext));
			stringBuffer.append("}");
			stringBuffer.append(DOUBLE_QUOTE);
			stringBuffer.append(">");
			stringBuffer.append("\n");
			stringBuffer.append("\t\t");
			stringBuffer.append("<c:param ");
			stringBuffer.append("name=");
			stringBuffer.append("${queryParameterMap.key}");
			stringBuffer.append(" value=");
			stringBuffer.append("${queryParameterMap.value}");
			stringBuffer.append(" />");
			stringBuffer.append("\n");
			stringBuffer.append("\t");
			stringBuffer.append("</c:forEach> ");
			return stringBuffer.toString();
		}
	}

	private boolean hasParameters(String name, String property, String paramId, String paramProperty) {
		return !isEmpty(name) || !isEmpty(property) || !isEmpty(paramId) || !isEmpty(paramProperty);
	}
	
	private String extractLinkVarName(String pageUrl){
		int index = pageUrl.lastIndexOf("/");
		if(index > 0){
			return pageUrl.substring(index+1).replace(".do", "")+ "Link";
		}
		return pageUrl;
	}
	
	
}
