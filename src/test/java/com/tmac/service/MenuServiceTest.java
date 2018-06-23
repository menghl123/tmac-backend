package com.tmac.service;

import com.google.common.collect.ImmutableList;
import com.tmac.AbstractTest;
import com.tmac.entity.Menu;
import com.tmac.exception.ValidateException;
import com.tmac.mapper.MenuMapper;
import com.tmac.repository.MenuRepository;
import com.tmac.validator.MenuValidator;
import com.tmac.vo.TreeNode;
import com.tmac.vo.ValidationResponse;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuServiceTest extends AbstractTest {
    @InjectMocks
    private MenuService menuService;
    @Mock
    private MenuValidator menuValidator;
    @Mock
    MenuRepository menuRepository;

    @Test
    public void should_call_selectList() {
        // when
        final String parentId = "test";
        final String name = "test";
        final String url = "test";
        final Integer page = 0;
        final Integer size = 10;
        final Pageable pageRequest = new PageRequest(page, size, new Sort("sortId"));

        // given
        final Page<Menu> menus = menuService.selectList(parentId, name, url, page, size);

        // then
        verify(menuRepository, times(1))
                .findAllByParentIdContainingAndNameContainingAndUrlContaining(
                        parentId,
                        name,
                        url,
                        pageRequest
                );
    }

    @Test
    public void should_call_save() {
        // when
        final String id = "test";

        // given
        menuService.get(id);

        // then
        verify(menuRepository, times(1)).findOne(id);
    }

    @Test(expected = ValidateException.class)
    public void shoule_save_failed_if_invalid() {
        // when
        final Menu menu = new Menu();
        doThrow(new ValidateException("test")).when(menuValidator).validateBeforeSave(menu);

        // given
        menuService.save(menu);

        // then
    }


    @Test
    public void should_save_success_if_valid() {
        // when
        final Menu menu = new Menu();
        doNothing().when(menuValidator).validateBeforeSave(menu);
        when(menuRepository.save(menu)).thenReturn(menu);

        // given
        final Menu savedMenu = menuService.save(menu);

        // then
        assertThat(savedMenu).isEqualTo(menu);
    }

    @Test
    public void should_call_delete_5_times() {
        // when
        final String[] ids = new String[]{"1", "2", "3", "4", "5"};
        doNothing().when(menuRepository).delete(anyString());

        // given
        menuService.delete(ids);

        // then
        verify(menuRepository, times(5)).delete(anyString());
    }

    @Test
    public void name_unique_should_get_response_with_true() {
        // when
        final String name = "test";
        when(menuRepository.countByName(name)).thenReturn(0);


        // given
        final ValidationResponse validationResponse = menuService.nameUnique(name);

        // then
        assertThat(validationResponse.getValid()).isTrue();
    }

    @Test
    public void name_unique_should_get_response_with_false() {
        // when
        final String name = "test";
        when(menuRepository.countByName(name)).thenReturn(1);

        // given
        final ValidationResponse validationResponse = menuService.nameUnique(name);

        // then
        assertThat(validationResponse.getValid()).isFalse();
    }

    @Test
    public void shoule_get_tree() {
        // when
        final String pId = "testOrigin";
        final Menu menuA = new Menu();
        menuA.setId("menuA");
        final Menu menuB = new Menu();
        menuB.setId("menuB");
        final Menu childC = new Menu();
        final Menu childD = new Menu();
        when(menuRepository.findByParentId(pId)).thenReturn(ImmutableList.of(menuA, menuB));
        when(menuRepository.findByParentId(menuA.getId())).thenReturn(ImmutableList.of(childC, childD));
        when(menuRepository.findByParentId(menuB.getId())).thenReturn(ImmutableList.of(childC));
        // given
        final List<TreeNode> tree = menuService.getTree(pId);

        // then
        assertThat(tree.size()).isEqualTo(2);
        assertThat(tree.get(0).getChildren().size()).isEqualTo(2);
        assertThat(tree.get(1).getChildren().size()).isEqualTo(1);
    }

    @Test
    public void should_modify_success() {
        // when
        final Menu menu = new Menu();
        menu.setId("test");
        menu.setName("test");
        menu.setUrl("test");
        menu.setLogo("test");
        menu.setSortId(1);
        when(menuRepository.findOne(menu.getId())).thenReturn(menu);
        when(menuRepository.save(MenuMapper.mapToModifiedMenu(menu, menu))).thenReturn(menu);
        // given
        final Menu modifyMenu = this.menuService.modify(menu);

        // then
        assertThat(modifyMenu.getId()).isEqualTo("test");
    }
}