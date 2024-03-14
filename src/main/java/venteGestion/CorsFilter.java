package venteGestion;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebFilter(urlPatterns = "/*") // Filter all requests
public class CorsFilter implements Filter {

    private final List<String> allowedOrigins = Arrays.asList("http://localhost:3000"); // Replace with allowed origins

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String origin = httpRequest.getHeader("Origin");

        // Check if origin is allowed
        if (origin != null && allowedOrigins.contains(origin)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
        }

        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"); // Adjust allowed methods if needed
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Adjust allowed headers if needed
        httpResponse.setHeader("Access-Control-Max-Age", "3600"); // Cache preflight response for an hour

        // Allow OPTIONS requests for preflight checks
        if (httpRequest.getMethod().equals("OPTIONS")) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization required
    }

    public void destroy() {
        // No cleanup required
    }

	@Override
	public boolean accept(Object entry) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
}
