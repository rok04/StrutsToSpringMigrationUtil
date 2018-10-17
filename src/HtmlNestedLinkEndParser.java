
public class HtmlNestedLinkEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		return "</a>";
	}

}
