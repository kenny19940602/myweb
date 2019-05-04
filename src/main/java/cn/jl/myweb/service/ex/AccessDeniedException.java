package cn.jl.myweb.service.ex;

/**
 * 收货地址归属异常
 */
public class AccessDeniedException extends ServiceException {

	private static final long serialVersionUID = 996680555821172030L;

	public AccessDeniedException() {
	}

	public AccessDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}

}
