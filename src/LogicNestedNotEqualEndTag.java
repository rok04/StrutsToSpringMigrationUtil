
public class LogicNestedNotEqualEndTag implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		return "</c:if>";
	}

}
