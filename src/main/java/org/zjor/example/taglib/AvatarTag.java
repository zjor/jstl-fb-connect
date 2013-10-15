package org.zjor.example.taglib;

import lombok.extern.slf4j.Slf4j;
import org.zjor.example.FacebookUser;
import org.zjor.example.filter.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @author: Sergey Royz
 * @since: 15.10.2013
 */
@Slf4j
public class AvatarTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        FacebookUser user = (FacebookUser) getJspContext().getAttribute(AuthFilter.AUTH_USER, PageContext.SESSION_SCOPE);
        if (user == null) {
            log.error("User is not set");
            return;
        }
        getJspContext().getOut().print(String.format("<img src=\"%s\" />", user.getPicture().getData().getUrl()));
    }
}
