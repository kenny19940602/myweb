package cn.jl.myweb.service.ex;

/**
 * 业务异常，是当前项目中业务层抛出异常的基类
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5079364445343607034L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
