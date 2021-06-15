package ir.mghhrn.ttbackend.util;

import ir.mghhrn.ttbackend.entity.User;
import ir.mghhrn.ttbackend.security.TokenBasedAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        UserDetails principal = ((TokenBasedAuthentication) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        return ((User)principal).getId();
    }
}
