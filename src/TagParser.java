public interface TagParser {

	public static final String SINGLE_QUOTE = "'";
	public static final String DOUBLE_QUOTE = "\"";
	
	public String parse(String line, ParserContext parserContext);
}
