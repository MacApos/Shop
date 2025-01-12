package pl.coderslab.security;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class MutableHttpServerRequest extends HttpServletRequestWrapper {
    private final HashMap<String, String> customHeaders;

    public MutableHttpServerRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void putHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = customHeaders.get(name);
        if (value != null) {
            return value;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        HashSet<String> customHeaderNames = new HashSet<>(customHeaders.keySet());

        Enumeration<String> headerNames = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            customHeaderNames.add(header);
        }

        return Collections.enumeration(customHeaderNames);
    }

}
