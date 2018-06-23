package com.tmac.validator;

import com.google.common.collect.ImmutableList;
import com.tmac.AbstractTest;
import com.tmac.entity.Role;
import com.tmac.exception.ValidateException;
import com.tmac.repository.RoleRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public class RoleValidatorTest extends AbstractTest {
    @InjectMocks
    private RoleValidator roleValidator;
    @Mock
    private RoleRepository roleRepository;

    @Test(expected = IllegalArgumentException.class )
    public void should_not_validate_before_save_when_id_empty() {
        // when
        final Role role = new Role();

        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_when_id_not_match() {
        // when
        final Role role = new Role();
        role.setId("不能用中文");
        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_when_id_not_in_scope() {
        // when
        final Role role = new Role();
        role.setId("212121212121");

        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_when_id_already_exists() {
        // when
        final Role role = new Role();
        role.setId("123456");
        when(roleRepository.countById(role.getId())).thenReturn(1);
        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_validate_before_save_when_name_null() {
        // when
        final Role role = new Role();
        role.setId("123456");
        when(roleRepository.countById(role.getId())).thenReturn(0);
        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_save_when_name_not_match() {
        // when
        final Role role = new Role();
        role.setId("123456");
        role.setName("!222");
        when(roleRepository.countById(role.getId())).thenReturn(0);
        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_validate_before_save_when_name_already_exsits() {
        // when
        final Role role = new Role();
        role.setId("123456");
        role.setName("222");
        when(roleRepository.countById(role.getId())).thenReturn(0);
        when(roleRepository.countByName(role.getName())).thenReturn(1);
        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test
    public void should_validate_before_save() {
        // when
        final Role role = new Role();
        role.setId("123456");
        role.setName("222");
        when(roleRepository.countById(role.getId())).thenReturn(0);
        when(roleRepository.countByName(role.getName())).thenReturn(0);
        // given
        this.roleValidator.validateBeforeSave(role);
        // then
    }

    @Test(expected = ValidateException.class)
    public void should_not_validate_before_modify_when_name_exists() {
        // when
        final Role role = new Role();
        role.setId("123456");
        role.setName("222");
        final Role role2 = new Role();
        role2.setId("1234516");
        role2.setName("222");
        when(roleRepository.countById(role.getId())).thenReturn(1);
        when(roleRepository.findByName(role.getName())).thenReturn(ImmutableList.of(role2));
        // given
        this.roleValidator.validateBeforeModify(role);
        // then
    }

    @Test
    public void should_validate_before_modify() {
        // when
        final Role role = new Role();
        role.setId("123456");
        role.setName("222");
        when(roleRepository.countById(role.getId())).thenReturn(1);
        when(roleRepository.findByName(role.getName())).thenReturn(ImmutableList.of(role));
        // given
        this.roleValidator.validateBeforeModify(role);
        // then
    }



}