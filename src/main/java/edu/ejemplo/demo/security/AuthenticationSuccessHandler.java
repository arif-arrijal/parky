package edu.ejemplo.demo.security;

import edu.ejemplo.demo.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    private UserRepository appUserDao;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Redirect to Dashboard Main Page
        SavedRequest savedRequest
                = new HttpSessionRequestCache().getRequest(request, response);

        String baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toString();
        
        HttpSession session = request.getSession(false);
        session.setAttribute("authUsername", appUserDao.findOneByEmailAndActive(authentication.getName(), true));

        if (savedRequest == null || savedRequest.getRedirectUrl().equals(baseUrl) || savedRequest.getRedirectUrl().equals(baseUrl + "/")) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.sendRedirect(savedRequest.getRedirectUrl());
        }
    }
}