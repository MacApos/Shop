package com.shop.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class AbstractControllerTest {

    public MockHttpServletRequestBuilder postRequestBuilder(String uriTemplate, String content) {
        return post(uriTemplate)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder putRequestBuilder(String uriTemplate, String content) {
        return put(uriTemplate)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder deleteRequestBuilder(String uriTemplate, String content) {
        return delete(uriTemplate)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
    }


}
