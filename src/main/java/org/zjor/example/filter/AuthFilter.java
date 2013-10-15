package org.zjor.example.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Sergey Royz
 * @since: 15.10.2013
 */
@Slf4j
public class AuthFilter implements Filter {

    public static final String AUTH_USER = "auth_user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Object user = req.getSession().getAttribute(AUTH_USER);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            req.setAttribute(AUTH_USER, user);
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {
    }
}
