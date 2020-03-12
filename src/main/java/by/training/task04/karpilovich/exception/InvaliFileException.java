package by.training.task04.karpilovich.exception;

public class InvaliFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvaliFileException() {
		super();
	}

	public InvaliFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvaliFileException(String message) {
		super(message);
	}

	public InvaliFileException(Throwable cause) {
		super(cause);
	}

}
