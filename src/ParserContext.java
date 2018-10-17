import java.util.Stack;

public class ParserContext {

	private Stack<String> paths = new Stack<String>();

	private Stack<String> nestedIterateVariable = new Stack<String>();

	private Stack<String> iteratePaths = new Stack<String>();

	public void addNestedIterateVariable(String path){
		nestedIterateVariable.push(path);
	}
	
	public String removeNestedIterateVariable(){
		if(!this.nestedIterateVariable.isEmpty()){
			return nestedIterateVariable.pop();
		}
		return null;
	}

	public String getCurrentIterateVariable(){
		return nestedIterateVariable.peek();
	}
	
	public void addPaths(String path){
		paths.push(path);
	}
	
	public String getCurrentPath(){
		if(!this.paths.isEmpty()){
		   return paths.pop();
		}
		return null;
	}
	
	public void addIteratePaths(String path){
		iteratePaths.push(path);
	}
	
	public String getCurrentIteratePath(){
		return iteratePaths.peek();
	}
	
	public String removeIteratePath(){
		return iteratePaths.pop();
	}
	
	private String nestedPath;

	private String rootPath;

	private String modelAttributeName;


	public boolean isInsideNestedIterate() {
		return nestedIterateVariable.size()>0;
	}


	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
		if(this.modelAttributeName ==null){
			this.modelAttributeName = rootPath;
		}
	}

	public String getNestedPath() {
		return nestedPath;
	}

	public void setNestedPath(String nestedPath) {
		this.nestedPath = nestedPath;
	}

	public String getModelAttributeName() {
		return modelAttributeName;
	}

	public void setModelAttributeName(String modelAttributeName) {
		this.modelAttributeName = modelAttributeName;
	}
    
}