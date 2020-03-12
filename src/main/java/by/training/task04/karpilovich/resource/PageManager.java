package by.training.task04.karpilovich.resource;

public enum PageManager {
	
	WELCOME("view/index.jsp"), LOGIN("view/login.jsp"), RESULT("view/result.jsp"), ERROR("view/error.jsp");
	
	private String page;
	
	private PageManager(String page) {
		this.page = page;
	}
	
	public String getPage() {
		return page;
	}

}
