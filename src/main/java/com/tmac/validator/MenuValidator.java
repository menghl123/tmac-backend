package com.tmac.validator;

import com.tmac.entity.Menu;
import com.tmac.enums.CommonEnum;
import com.tmac.exception.ValidateException;
import com.tmac.repository.FunctionRepository;
import com.tmac.repository.MenuRepository;
import com.tmac.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
public class MenuValidator {
    private final static String ORIGIN_MENU_ID = "1000000000000000000000000";
    private final MenuRepository menuRepository;
    private final FunctionRepository functionRepository;

    @Autowired
    public MenuValidator(final MenuRepository menuRepository, final FunctionRepository functionRepository) {
        this.menuRepository = menuRepository;
        this.functionRepository = functionRepository;
    }

    public void validateBeforeSave(final Menu menu) {
        this.urlValidate(menu.getUrl());
        this.nameValidate(menu.getName(), CommonEnum.SAVE, menu);
        this.functionValidate(menu.getFunctionId());
        this.parentIdValidate(menu.getParentId());
    }

    private void urlValidate(final String url) {
        ValidatorUtils.rejectIfBlank(url, "menu.url.not.null");
    }

    private void parentIdValidate(final String parentId) {
        ValidatorUtils.rejectIfBlank(parentId, "menu.parentId.not.null");
        if (!Objects.equals(parentId, ORIGIN_MENU_ID) && this.menuRepository.countById(parentId) == 0) {
            throw new ValidateException("menu.parentId.not.exists");
        }

    }

    private void functionValidate(final String functionId) {
        ValidatorUtils.rejectIfBlank(functionId, "menu.functionId.not.null");
        if (this.functionRepository.countById(functionId) == 0) {
            throw new ValidateException("menu.functionId.not.exists");
        }
    }

    private void nameValidate(final String name, final CommonEnum strategy, final Menu menu) {
        ValidatorUtils.rejectIfBlank(name, "menu.name.not.null");
        ValidatorUtils.rejectIfNotMatch(name, "^[a-z0-9A-Z\\u4e00-\\u9fa5]+$", "menu.name.not.match");
        ValidatorUtils.rejectIfNotInScope(name, 0, 12, "menu.name.not.inscope");
        if (Objects.equals(strategy, CommonEnum.SAVE)) {
            Assert.isTrue(Objects.equals(this.menuRepository.countByName(name), 0), "menu.name.not.unique");
        } else {
            this.menuRepository.findByName(name)
                    .stream()
                    .map(Menu::getId)
                    .filter(id -> !Objects.equals(menu.getId(), id))
                    .findAny()
                    .ifPresent((id) -> {
                        throw new ValidateException("menu.name.not.unique");
                    });
        }
    }

    public void validateBeforeModify(final Menu menu) {
        this.idValidate(menu.getId());
        this.urlValidate(menu.getUrl());
        this.nameValidate(menu.getName(), CommonEnum.MODIFY, menu);
    }

    private void idValidate(final String id) {
        Assert.isTrue(!StringUtils.isBlank(id), "menu.id.not.null");
        Assert.isTrue(!Objects.equals(menuRepository.countById(id), 0),
                "menu.id.not.exists");
    }
}
