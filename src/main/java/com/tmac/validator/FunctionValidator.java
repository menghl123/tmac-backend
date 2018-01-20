package com.tmac.validator;

import com.tmac.entity.Function;
import com.tmac.enums.CommonEnum;
import com.tmac.exception.ValidateException;
import com.tmac.repository.FunctionRepository;
import com.tmac.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
public class FunctionValidator {
    private final static String ORIGIN_MENU_ID = "1000000000000000000000000";
    private final FunctionRepository functionRepository;

    @Autowired
    public FunctionValidator(final FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    public void validateBeforeSave(final Function function) {
        this.idValidate(function.getId(), CommonEnum.SAVE);
        this.nameValidate(function.getName(), CommonEnum.SAVE, function);
    }

    private void nameValidate(final String name, final CommonEnum strategy, final Function function) {
        Assert.isTrue(!StringUtils.isBlank(name), "function.name.not.null");
        ValidatorUtils.rejectIfNotMatch(name, "^[a-z0-9A-Z\\u4e00-\\u9fa5]+$", "function.name.not.match");
        if (Objects.equals(strategy, CommonEnum.SAVE)) {
            Assert.isTrue(Objects.equals(this.functionRepository.countByName(name), 0), "function.name.already.exists");
        } else {
            this.functionRepository.findByName(name)
                    .stream()
                    .map(Function::getId)
                    .filter(id -> !Objects.equals(function.getId(), id))
                    .findAny()
                    .ifPresent((id) -> {
                        throw new ValidateException("function.name.already.exists");
                    });
        }
    }

    private void idValidate(final String id, final CommonEnum strategy) {
        Assert.isTrue(!StringUtils.isBlank(id), "function.id.not.null");
        ValidatorUtils.rejectIfNotMatch(id, "^[a-z0-9A-Z]+$", "function.id.not.match");
        ValidatorUtils.rejectIfNotInScope(id, 6, 6, "function.id.not.inscope");
        if (Objects.equals(strategy, CommonEnum.SAVE) && !Objects.equals(this.functionRepository.countById(id), 0)) {
            throw new ValidateException("function.id.already.exists");
        } else if (Objects.equals(strategy, CommonEnum.MODIFY)
                && !Objects.equals(this.functionRepository.countById(id), 1)) {
            throw new ValidateException("function.id.not.exists");
        }
    }

    public void validateBeforeModify(final Function function) {
        this.idValidate(function.getId(), CommonEnum.MODIFY);
        this.nameValidate(function.getName(), CommonEnum.MODIFY, function);
    }
}
