package dev.tioachan.constant;

public class RedisMessageConstant {
    public static final String SENDTYPE_ORDER = "SMS:Reservation:";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "SMS:Quick_Login:";//用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "SMS:Passwd_recovery:";//用于缓存找回密码时发送的验证码
}