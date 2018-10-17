
public class HtmlOptionEndTagParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		return "</form:option";
		
	}

}
