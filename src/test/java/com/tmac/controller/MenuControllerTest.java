package com.tmac.controller;

import com.tmac.AbstractTest;
import com.tmac.entity.Menu;
import com.tmac.service.MenuService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MenuControllerTest extends AbstractTest {
    @InjectMocks
    private MenuController menuController;
    @Mock
    private MenuService menuService;

    @Test
    public void should_call_selectList() {
        // when

        // given
        menuController.findAll(null, null, null, 2, null);

        // then
        verify(menuService, times(1)).selectList(null, null, null, 1, null);

    }

    @Test
    public void should_call_get() {
        // when
        final String id = "1";

        // given
        menuController.get(id);

        // then
        verify(menuService, times(1)).get(id);
    }

    @Test
    public void should_call_save() {
        // when
        final Menu menu = new Menu();

        // given
        menuController.save(menu);

        // then
        verify(menuService, times(1)).save(menu);
    }

    @Test
    public void should_call_modify() {
        // when
        final Menu menu = new Menu();

        // given
        menuController.modify(menu);

        // then
        verify(menuService, times(1)).modify(menu);
    }


    @Test
    public void should_call_delete() {
        // when
        final String[] ids = null;

        // given
        menuController.delete(ids);

        // then
        verify(menuService, times(1)).delete(ids);
    }

    @Test
    public void should_call_nameUnique() {
        // when
        final String name = "";

        // given
        menuController.nameUnique(name);

        // then
        verify(menuService, times(1)).nameUnique(name);
    }

    @Test
    public void should_call_getTree() {
        // when
        final String pId = "";

        // given
        menuController.getTree(pId);

        // then
        verify(menuService, times(1)).getTree(pId);
    }
}