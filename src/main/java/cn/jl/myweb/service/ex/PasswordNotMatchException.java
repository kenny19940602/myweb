package cn.jl.myweb.service.ex;

/**
 * 用户密码错误的异常
 */
public class PasswordNotMatchException extends ServiceException {

	private static final long serialVersionUID = -1689273575842599799L;

	public PasswordNotMatchException() {
		super();
	}

	public PasswordNotMatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordNotMatchException(String message) {
		super(message);
	}

	public PasswordNotMatchException(Throwable cause) {
		super(cause);
	}

	
}
