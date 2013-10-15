package org.zjor.example.servlet;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.zjor.example.FacebookUser;
import org.zjor.example.filter.AuthFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 15.10.2013
 */
@Slf4j
public class AuthServlet extends HttpServlet {

    private static final String FACEBOOK_APP_ID = "270568069679176";
    private static final String FACEBOOK_APP_SECRET = "03061347ee4b382ca75a33f835411d6d";

    private CloseableHttpClient httpClient;

    public AuthServlet() {
        httpClient = HttpClientBuilder.create()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .build();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(req.getRequestURI());
        log.info(req.getQueryString());

        String code = req.getParameter("code");
        if (code == null) {
            resp.sendError(403, "Failed to login via Facebook");
        }
        String accessToken = exchangeCodeForAccessToken(req, code);
        log.info("Access token: {}", accessToken);

        FacebookUser user = authenticate(accessToken);
        log.info("User: {}", user);

        req.getSession().setAttribute(AuthFilter.AUTH_USER, user);

        resp.sendRedirect("secure/profile.jsp");
    }

    private String exchangeCodeForAccessToken(HttpServletRequest request, String code) throws IOException {

        String query = "code=" + URLEncoder.encode(code, "UTF-8") + '&' +
                "client_id=" + URLEncoder.encode(FACEBOOK_APP_ID, "UTF-8") + '&' +
                "client_secret=" + URLEncoder.encode(FACEBOOK_APP_SECRET, "UTF-8") + '&' +
                "redirect_uri=" + URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
        String uri = "https://graph.facebook.com/oauth/access_token?" + query;
        log.info(uri);


        HttpResponse response = httpClient.execute(new HttpGet(uri));
        if (HttpURLConnection.HTTP_OK != response.getStatusLine().getStatusCode()) {
            log.error("Unable to get access token: {}", EntityUtils.toByteArray(response.getEntity()));
            return null;
        }
        List<NameValuePair> params = URLEncodedUtils.parse(EntityUtils.toString(response.getEntity()), Charset.forName("UTF-8"));
        for (NameValuePair pair: params) {
            if ("access_token".equals(pair.getName())) {
                return pair.getValue();
            }
        }

        return null;
    }

    private FacebookUser authenticate(String accessToken) throws IOException {

        HttpGet request = new HttpGet("https://graph.facebook.com/me?fields=id,name,username,picture&access_token=" + accessToken);
        HttpResponse httpResponse = httpClient.execute(request);

        return new GsonBuilder().create().fromJson(EntityUtils.toString(httpResponse.getEntity()), FacebookUser.class);
    }

}
