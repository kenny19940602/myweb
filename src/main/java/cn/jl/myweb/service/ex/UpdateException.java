package cn.jl.myweb.service.ex;

/**
 * 修改用户密码的异常类
 */
public class UpdateException extends ServiceException {


    public UpdateException() {
        super();
    }

    public UpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }
}