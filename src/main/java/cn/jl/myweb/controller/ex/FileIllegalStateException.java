package cn.jl.myweb.controller.ex;

/**
 * 文件状体的异常
 */
public class FileIllegalStateException extends FileUploadException{

	private static final long serialVersionUID = -6969121274247527594L;

	public FileIllegalStateException() {
		super();
	}

	public FileIllegalStateException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FileIllegalStateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileIllegalStateException(String arg0) {
		super(arg0);
	}

	public FileIllegalStateException(Throwable arg0) {
		super(arg0);
	}

	
}
