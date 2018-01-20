package com.tmac.validator;

import com.tmac.entity.Role;
import com.tmac.enums.CommonEnum;
import com.tmac.exception.ValidateException;
import com.tmac.repository.RoleRepository;
import com.tmac.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@Service
public class RoleValidator {

    @Autowired
    final private RoleRepository roleRepository;

    public RoleValidator(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void validateBeforeModify(final Role role) {
        this.idValidate(role.getId(), CommonEnum.MODIFY);
        this.nameValidate(role.getName(), CommonEnum.MODIFY, role);
    }

    public void validateBeforeSave(final Role role) {
        this.idValidate(role.getId(), CommonEnum.SAVE);
        this.nameValidate(role.getName(), CommonEnum.SAVE, role);
    }

    private void nameValidate(final String name, final CommonEnum strategy, final Role role) {
        Assert.isTrue(!StringUtils.isBlank(name), "role.name.not.null");
        ValidatorUtils.rejectIfNotMatch(name, "^[a-z0-9A-Z\\u4e00-\\u9fa5]+$", "role.name.not.match");
        if (Objects.equals(strategy, CommonEnum.SAVE)) {
            Assert.isTrue(Objects.equals(this.roleRepository.countByName(name), 0), "role.name.already.exists");
        } else {
            this.roleRepository.findByName(name)
                    .stream()
                    .map(Role::getId)
                    .filter(id -> !Objects.equals(role.getId(), id))
                    .findAny()
                    .ifPresent((id) -> {
                        throw new ValidateException("role.name.already.exists");
                    });
        }
    }

    private void idValidate(final String id, final CommonEnum strategy) {
        Assert.isTrue(!StringUtils.isBlank(id), "role.id.not.null");
        ValidatorUtils.rejectIfNotMatch(id, "^[a-z0-9A-Z]+$", "role.id.not.match");
        ValidatorUtils.rejectIfNotInScope(id, 6, 6, "role.id.not.inscope");
        if (Objects.equals(strategy, CommonEnum.SAVE) && !Objects.equals(this.roleRepository.countById(id), 0)) {
            throw new ValidateException("role.id.already.exists");
        } else if (Objects.equals(strategy, CommonEnum.MODIFY)
                && !Objects.equals(this.roleRepository.countById(id), 1)) {
            throw new ValidateException("role.id.not.exists");
        }
    }

}
