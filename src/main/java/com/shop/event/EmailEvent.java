package com.shop.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;
import java.util.Map;

@Getter
@Setter
public class EmailEvent extends ApplicationEvent {
    private String to;
    private String subjectCode;
    private String template;
    private Locale locale;
    private Map<String, Object> variables;

    public EmailEvent(String to, String subjectCode, String template, Locale locale,
                      Map<String, Object> variables) {
        super(to);
        this.to = to;
        this.subjectCode = subjectCode;
        this.variables = variables;
        this.locale = locale;
        this.template = template;
    }

}
