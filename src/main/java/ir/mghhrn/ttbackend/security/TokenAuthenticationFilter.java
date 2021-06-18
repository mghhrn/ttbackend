package ir.mghhrn.ttbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mghhrn.ttbackend.repository.UserRepository;
import ir.mghhrn.ttbackend.dto.ErrorDto;
import ir.mghhrn.ttbackend.entity.User;
import ir.mghhrn.ttbackend.exception.handler.TTBusinessException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenHelper tokenHelper;
    private final UserRepository userRepository;
    private ObjectMapper objectMapper;
    private MessageSource messageSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authToken = tokenHelper.getToken(request);
            if (authToken != null) {
                Long userId = tokenHelper.getUserIdFromToken(authToken);
                JwtTokenType tokenType = tokenHelper.getTokenTypeFromToken(authToken);
                if (tokenType.equals(JwtTokenType.REFRESH)) {
                    throw new TTBusinessException("error.token.invalid.token.type");
                }
                User user = userRepository.findUserById(userId)
                        .orElseThrow(() -> new TTBusinessException("error.user.not.found"));

                TokenBasedAuthentication authentication = new TokenBasedAuthentication(user);
                authentication.setToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new TTBusinessException("error.authentication.not.found", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            String message;
            ErrorDto errorDto;
            if (e instanceof TTBusinessException) {
                TTBusinessException ttbe = (TTBusinessException)e;
                message = messageSource.getMessage(ttbe.getMessageKey(), ttbe.getMessageArgs(), ttbe.getLocale());
                errorDto = new ErrorDto(ttbe.getHttpStatus().value(), message);
            } else {
                message = "default error message in authentication filter";
                errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
            }

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(errorDto.getStatus());
            response.getWriter().print(objectMapper.writeValueAsString(errorDto));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
