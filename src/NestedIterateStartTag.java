
public class NestedIterateStartTag extends AbstractTagParser{

	@Override
	public String doParse(String line, ParserContext parserContext) {
		String[] takens = line.split("\\s+");
		String property = null;
		String id = null;
		String styleClass = null;
		String offSet = null;
		String length = null;
		String indexId = null;
		
		for(String token :takens){
			if(token.startsWith("id")){
				id = token.split("\\=")[1];
			}
			if(token.startsWith("property")){
				property = token.split("\\=")[1];
			}
			if(token.startsWith("offset")){
				offSet = token.split("\"")[1];
			}
			if(token.startsWith("length")){
				length = token.split("\"")[1];
			}
			if(token.startsWith("indexId")){
				indexId = token.split("\\=")[1];
				//TODO Here we need to use index.index where index is uset in ["+ index +"]
			}
		}
		if(id == null || "".equalsIgnoreCase(id.trim())){
			id = removeLeadingAndTrailingCommas(property) +"Item";
		}else{
			id = removeLeadingAndTrailingCommas(id);
				
		}

		
//		String previousPath = parserContext.getNestedPath();
//		
//		if(parserContext.isInsideNestedIterate()){
//			previousPath = parserContext.getCurrentIterateVariable();
//		}
//		
//		String elementPath = previousPath+"."+property.substring(1,property.length()-1);
		//parserContext.setNestedPath();
		//parserContext.addPaths(property.substring(1,property.length()-1));

		StringBuffer replacedString = new StringBuffer();
		replacedString.append("<c:forEach var=\"");
		replacedString.append(id);
		replacedString.append("\" ");
		replacedString.append("items=\"${");
		replacedString.append(getElementPath(property, parserContext));
		replacedString.append("}\"");
		replacedString.append(" ");
		
		if(!isEmpty(offSet))
		{
			replacedString.append("begin=");
			replacedString.append("\"");
			replacedString.append(offSet);
			replacedString.append("\"");
			replacedString.append(" ");
			replacedString.append("end=");
			replacedString.append("\"");
			replacedString.append(length);
			replacedString.append("\"");
			replacedString.append(" ");
		}
		
		if(!isEmpty(indexId))
		{	
		replacedString.append("varStatus=");
		replacedString.append("\"");
		replacedString.append(indexId.substring(1, indexId.length()-1));
		replacedString.append("\"");
		replacedString.append(" ");
		}
		replacedString.append(">");
		
		if(id == null || "".equalsIgnoreCase(id.trim())){
			id = property.substring(1,property.length()-1) +"Item";
		    parserContext.addNestedIterateVariable(id);
		}else{
		    parserContext.addNestedIterateVariable(id);
		}
		
		return replacedString.toString();

	}

	@Override
	protected boolean appendModelAttributeName() {
		return true;
	}
		
}
