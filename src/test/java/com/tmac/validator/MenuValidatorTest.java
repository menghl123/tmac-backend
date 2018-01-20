package com.tmac.validator;

import com.tmac.AbstractTest;
import com.tmac.entity.Menu;
import com.tmac.exception.ValidateException;
import com.tmac.repository.FunctionRepository;
import com.tmac.repository.MenuRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class MenuValidatorTest extends AbstractTest {
    @InjectMocks
    private MenuValidator menuValidator;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private FunctionRepository functionRepository;

    @Test(expected = ValidateException.class)
    public void should_valid_failed_when_name_not_match() {
        //given
        final Menu menu = getValidMenu();
        menu.setName("!-@aa");

        //when
        this.menuValidator.validateBeforeSave(menu);

        //then
    }

    @Test(expected = ValidateException.class)
    public void should_valid_failed_when_url_null() {
        //given
        final Menu menu = getValidMenu();
        menu.setUrl(null);

        //when
        this.menuValidator.validateBeforeSave(menu);

        //then
    }

    @Test(expected = ValidateException.class)
    public void should_valid_failed_when_name_not_inscope() {
        //given
        final Menu menu = getValidMenu();
        menu.setName("123456789111211");

        //when
        this.menuValidator.validateBeforeSave(menu);

        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_valid_failed_when_name_not_unique() {
        //given
        final Menu menu = getValidMenu();
        when(menuRepository.countByName(menu.getName())).thenReturn(1);

        //when
        this.menuValidator.validateBeforeSave(menu);

        //then
    }

    @Test(expected = ValidateException.class)
    public void should_valid_failed_when_functionId_not_exists() {
        //given
        final Menu menu = getValidMenu();
        when(menuRepository.countByName(menu.getName())).thenReturn(0);
        when(functionRepository.countById(menu.getFunctionId())).thenReturn(0);

        //when
        this.menuValidator.validateBeforeSave(menu);

        //then
    }

    @Test(expected = ValidateException.class)
    public void should_valid_failed_when_parentId_not_exists() {
        //given
        final Menu menu = getValidMenu();
        when(menuRepository.countByName(menu.getName())).thenReturn(0);
        when(functionRepository.countById(menu.getFunctionId())).thenReturn(1);
        when(menuRepository.countById(menu.getParentId())).thenReturn(0);

        //when
        this.menuValidator.validateBeforeSave(menu);

        //then
    }

    @Test
    public void should_valid_success_when_menu_valid() {
        //given
        final Menu menu = getValidMenu();
        when(menuRepository.countByName(menu.getName())).thenReturn(0);
        when(functionRepository.countById(menu.getFunctionId())).thenReturn(1);
        when(menuRepository.countById(menu.getParentId())).thenReturn(1);

        //when
        this.menuValidator.validateBeforeSave(menu);

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_valid_failed_when_id_null() {
        // when
        final Menu menu = getValidMenu();
        menu.setId(null);

        // given
        this.menuValidator.validateBeforeModify(menu);

        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_valid_failed_when_id_not_exists() {
        // when
        final Menu menu = getValidMenu();
        menu.setId("111");
        when(menuRepository.countById(menu.getId())).thenReturn(0);

        // given
        this.menuValidator.validateBeforeModify(menu);

        // then
    }

    private Menu getValidMenu() {
        final Menu menu = new Menu();
        menu.setName("testName");
        menu.setSortId(1);
        menu.setFunctionId("F00001");
        menu.setUrl("/system");
        menu.setParentId("1000000000000000000000001");
        return menu;
    }

}