package com.tmac.config;

import com.tmac.vo.UserVo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuditorAwareConfig implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "system";
        }
        final UserVo userVo = (UserVo) authentication.getPrincipal();
        return userVo.getId();
    }
}
