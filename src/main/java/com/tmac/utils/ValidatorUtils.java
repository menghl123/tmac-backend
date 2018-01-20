package com.tmac.utils;

import com.tmac.exception.ValidateException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class ValidatorUtils {
    public ValidatorUtils() {
    }

    public static void rejectIfNotMatch(final String value, final String regExp, final String msg) {
        if (!Pattern.compile(regExp).matcher(value).matches()) {
            throw new ValidateException(msg);
        }
    }

    public static void rejectIfNotInScope(final String value, final Integer min,
                                          final Integer max, final String msg) {
        if (value.length() < min || value.length() > max) {
            throw new ValidateException(msg);
        }
    }

    public static void rejectIfBlank(final String value, final String msg) {
        if (StringUtils.isEmpty(value)) {
            throw new ValidateException(msg);
        }
    }

    public static void rejectIfArrayEmpty(final Object[] value, final String msg) {
        if (value == null || value.length == 0) {
            throw new ValidateException(msg);
        }
    }

}
