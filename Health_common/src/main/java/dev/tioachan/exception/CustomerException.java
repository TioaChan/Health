package dev.tioachan.exception;

public class CustomerException extends RuntimeException {
	public static final String INVALID_CODE = "-1";
	public static final String SMS_CODE_INVALID = "短信验证码校验失败";
	public static final String SMS_CODE_STILL_ALIVE = "一分钟内不能重复发送验证码";

	private String retCd ;  //异常对应的返回码
	private String msgDes;  //异常对应的描述信息

	public CustomerException() {
		super();
	}

	public CustomerException(String message) {
		super(message);
		msgDes = message;
	}

	public CustomerException(String retCd, String msgDes) {
		super();
		this.retCd = retCd;
		this.msgDes = msgDes;
	}

	public String getRetCd() {
		return retCd;
	}

	public String getMsgDes() {
		return msgDes;
	}
}