
public final class HtmlNestedNotEmptyEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		return "</c:if>";
	}

}
