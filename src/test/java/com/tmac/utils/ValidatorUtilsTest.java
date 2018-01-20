package com.tmac.utils;

import com.tmac.AbstractTest;
import com.tmac.exception.ValidateException;
import org.junit.Test;

public class ValidatorUtilsTest extends AbstractTest {

    @Test(expected = ValidateException.class)
    public void test_reject_if_not_match() {
        //given

        //when
        ValidatorUtils.rejectIfNotMatch("!223!", "^[a-z0-9A-Z\\u4e00-\\u9fa5]+$", "校验错误");

        //then
    }

    @Test
    public void test_success_if_match() {
        //given

        //when
        ValidatorUtils.rejectIfNotMatch("123abcABC你好", "^[a-z0-9A-Z\\u4e00-\\u9fa5]+$", "校验错误");

        //then
    }

    @Test(expected = ValidateException.class)
    public void test_reject_if_not_inscope() {
        //given

        //when
        ValidatorUtils.rejectIfNotInScope("你好呀", 5, 8, "长度不在范围内");

        //then
    }

    @Test
    public void test_success_if_inscope() {
        //given

        //when
        ValidatorUtils.rejectIfNotInScope("你好呀", 3, 8, "长度不在范围内");

        //then
    }

    @Test(expected = ValidateException.class)
    public void test_failed_if_blank() {
        // when

        // given
        ValidatorUtils.rejectIfBlank(null, "不能为空!");

        // then
    }
}