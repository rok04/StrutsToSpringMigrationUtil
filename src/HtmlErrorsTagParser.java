
public class HtmlErrorsTagParser implements TagParser {

	@Override
	public String parse(String line,ParserContext context) {
		return " <error:errors path=\"*\" cssClass= \"err_msg\"  />";
	}

}
