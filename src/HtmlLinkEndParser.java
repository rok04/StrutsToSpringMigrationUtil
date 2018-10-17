
public class HtmlLinkEndParser implements TagParser {

	@Override
	public String parse(String line, ParserContext parserContext) {
		return "</a>";
	}

}
