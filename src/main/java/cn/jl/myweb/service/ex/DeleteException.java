package cn.jl.myweb.service.ex;

/**
 * 删除的异常类
 */
public class DeleteException extends ServiceException {

	private static final long serialVersionUID = 7165965218426029224L;

	public DeleteException() {
	}

	public DeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteException(String message) {
		super(message);
	}

	public DeleteException(Throwable cause) {
		super(cause);
	}

}
