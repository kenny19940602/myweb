package cn.jl.myweb.controller.ex;

/**
 *文件过大或过小的异常 
 */
public class FileSizeException extends FileUploadException {

	private static final long serialVersionUID = -6861184643940117874L;

	public FileSizeException() {
		super();
	}

	public FileSizeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FileSizeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileSizeException(String arg0) {
		super(arg0);
	}

	public FileSizeException(Throwable arg0) {
		super(arg0);
	}

}
