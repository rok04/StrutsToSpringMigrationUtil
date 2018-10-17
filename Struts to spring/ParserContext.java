import java.util.Stack;

public class ParserContext {

	private Stack<String> elementVisted = new Stack<String>();

	public Stack<String> getElementVisted() {
		return elementVisted;
	}

	public void setElementVisted(Stack<String> elementVisted) {
		this.elementVisted = elementVisted;
	}

	private String nestedPath;

	private String rootPath;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getNestedPath() {
		return nestedPath;
	}

	public void setNestedPath(String nestedPath) {
		this.nestedPath = nestedPath;
	}
}