package cn.jl.myweb.util;

import java.io.Serializable;

/**
 * 用于向客户端响应操作结果的类型
 * 
 * @param <T> 操作结果中包含的数据的类型
 */
public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = 8254162176696261696L;

	private Long state;
	private String message;
	private T data;

	public ResponseResult() {
		super();
	}

	public ResponseResult(Long state, String message) {
		super();
		this.state = state;
		this.message = message;
	}

	public ResponseResult(T data) {
		super();
		this.data = data;
	}

	public ResponseResult(Long state, String message, T data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
