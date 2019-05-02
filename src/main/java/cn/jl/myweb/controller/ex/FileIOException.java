package cn.jl.myweb.controller.ex;

/**
 *文件IO异常
 */
public class FileIOException extends FileUploadException {

	private static final long serialVersionUID = -3635867402475950674L;

	public FileIOException() {
	}

	public FileIOException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FileIOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileIOException(String arg0) {
		super(arg0);
	}

	public FileIOException(Throwable arg0) {
		super(arg0);
	}

}
