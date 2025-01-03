package com.ict.edu.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ict.edu.common.handler.AdminLevelNameTypeHandler;
import com.ict.edu.common.handler.CommentBoardTypeHandler;
import com.ict.edu.common.handler.UserGenderTypeHandler;
import com.ict.edu.common.handler.UserLevelNameTypeHandler;
import com.ict.edu.common.handler.UserLicenseTypeHandler;

@Configuration
public class HandlerConfig {
    
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.getTypeHandlerRegistry().register(AdminLevelNameTypeHandler.class);
                configuration.getTypeHandlerRegistry().register(CommentBoardTypeHandler.class);
                configuration.getTypeHandlerRegistry().register(UserGenderTypeHandler.class);
                configuration.getTypeHandlerRegistry().register(UserLevelNameTypeHandler.class);
                configuration.getTypeHandlerRegistry().register(UserLicenseTypeHandler.class);
            }
            
        };
    }
}
