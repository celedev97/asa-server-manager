package dev.cele.asa_sm.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        SpringApplicationContext.context = context;
    }

    public static <T> T autoWire(Class<T> java) {
        return context.getBean(java);
    }

    @SuppressWarnings("unchecked")
    public static <T> T autoWire(String qualifier) {
        return (T) context.getBean(qualifier);
    }
}

