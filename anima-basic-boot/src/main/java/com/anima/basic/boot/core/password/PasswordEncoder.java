package com.anima.basic.boot.core.password;

/**
 * 密码加密接口
 *
 * @author hww
 * @date 2022/8/29
 */
public interface PasswordEncoder {

    String encode(CharSequence var1);

    boolean matches(CharSequence var1, String var2);

    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }

}
