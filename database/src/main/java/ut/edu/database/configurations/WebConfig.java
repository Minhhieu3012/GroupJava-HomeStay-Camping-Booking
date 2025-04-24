package ut.edu.database.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //cho phep client truy cap anh tu /images/**
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static-admin/assets/images/");
    }
}
