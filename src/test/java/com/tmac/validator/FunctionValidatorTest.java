package com.tmac.validator;

import com.google.common.collect.ImmutableList;
import com.tmac.AbstractTest;
import com.tmac.entity.Function;
import com.tmac.exception.ValidateException;
import com.tmac.repository.FunctionRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class FunctionValidatorTest extends AbstractTest {
    @InjectMocks
    private FunctionValidator functionValidator;
    @Mock
    private FunctionRepository functionRepository;

    @Test(expected = IllegalArgumentException.class)
    public void should_not_validate_before_save_if_id_null() {
        // when
        final Function function = new Function();

        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_if_id_not_match() {
        // when
        final Function function = new Function();
        function.setId("帅哥");
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_if_id_out_scope() {
        // when
        final Function function = new Function();
        function.setId("1234567");
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_if_id_exsits() {
        // when
        final Function function = new Function();
        function.setId("123456");
        when(functionRepository.countById(function.getId())).thenReturn(1);
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_validate_before_save_if_name_null() {
        // when
        final Function function = new Function();
        function.setId("123456");
        when(functionRepository.countById(function.getId())).thenReturn(0);
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_if_name_not_match() {
        // when
        final Function function = new Function();
        function.setId("123456");
        function.setName("!@#");
        when(functionRepository.countById(function.getId())).thenReturn(0);
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_validate_before_save_if_name_exsits() {
        // when
        final Function function = new Function();
        function.setId("123456");
        function.setName("管理功能");
        when(functionRepository.countByName(function.getName())).thenReturn(1);
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test
    public void should_validate_before_save() {
        // when
        final Function function = new Function();
        function.setId("123456");
        function.setName("管理功能");
        when(functionRepository.countByName(function.getName())).thenReturn(0);
        // given
        this.functionValidator.validateBeforeSave(function);

        // then
    }

    @Test
    public void should_save_before_modify() {
        // when
        final Function function = new Function();
        final Function function2 = new Function();
        function2.setId("223456");
        function2.setName("超级管理员");
        function.setId("123456");
        function.setName("超级管理员");
        when(functionRepository.countById(function.getId())).thenReturn(1);
        when(functionRepository.findByName(function.getName())).thenReturn(ImmutableList.of());
        // given
        this.functionValidator.validateBeforeModify(function);
        // then
    }
}