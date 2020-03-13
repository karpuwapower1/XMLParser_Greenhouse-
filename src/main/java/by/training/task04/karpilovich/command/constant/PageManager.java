package by.training.task04.karpilovich.command.constant;

public enum PageManager {
	
	WELCOME("view/index.jsp"), RESULT("view/result.jsp"), ERROR("view/error.jsp");
	
	private String page;
	
	private PageManager(String page) {
		this.page = page;
	}
	
	public String getPage() {
		return page;
	}

}
