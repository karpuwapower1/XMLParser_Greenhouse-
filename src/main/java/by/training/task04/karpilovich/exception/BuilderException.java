package by.training.task04.karpilovich.exception;

public class BuilderException extends Exception {

	private static final long serialVersionUID = 1L;

	public BuilderException() {
		super();
	}

	public BuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	public BuilderException(String message) {
		super(message);
	}

	public BuilderException(Throwable cause) {
		super(cause);
	}
	
}
