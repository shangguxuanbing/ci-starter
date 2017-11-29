/**
 * MIT License
 * <p>
 * Copyright (c) 2017 Michael Yan
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.tuling.cistarter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ValidConnectionChecker;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.JdbcUtils;
import com.google.common.collect.Lists;
import org.h2.server.web.WebServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by myan on 11/27/2017.
 * Intellij IDEA
 */
@Configuration
@MapperScan("org.tuling.cistarter.web.dao")
public class DataBaseConfig {
    
    @Bean
    public ServletRegistrationBean h2ServletRegistration() {
        // add the h2 database web admin console servlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/h2/*");
        return registrationBean;
    }
    
    // enable druid datasource web admin
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        // add connection checker.
        dataSource.setValidConnectionChecker(new ValidConnectionChecker() {
            @Override
            public boolean isValidConnection(Connection connection, String validateSql, int i) throws Exception {
                if(connection.isClosed())
                    return false;
                String query = validateSql;
                if(query == null || query.isEmpty())
                    query = "SELECT 1";
                Statement stmt = null;
                ResultSet rs = null;
    
                boolean result;
                try {
                    stmt = connection.createStatement();
                    rs = stmt.executeQuery(query);
                    result = true;
                } finally {
                    JdbcUtils.close(rs);
                    JdbcUtils.close(stmt);
                }
    
                return result;
            }
    
            @Override
            public void configFromProperties(Properties properties) {
        
            }
        });
        return dataSource;
    }
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        
        // do not filter static resources
        Map<String, String> intParams = new HashMap<>();
        intParams.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setName("DruidWebStatFilter");
        filterRegistrationBean.setUrlPatterns(Lists.newArrayList("/*"));
        filterRegistrationBean.setInitParameters(intParams);
        return filterRegistrationBean;
    }
    
    @Bean
    public ServletRegistrationBean druidRegistrationBean() {
        ServletRegistrationBean druidRegistrationBean = new ServletRegistrationBean();
        druidRegistrationBean.setServlet(new StatViewServlet());
        druidRegistrationBean.setName("druid");
        druidRegistrationBean.setUrlMappings(Lists.newArrayList("/druid/*"));
        
        // add init parameters
        Map<String, String> intParams = new HashMap<>();
        intParams.put("loginUsername", "druid");
        intParams.put("loginPassword", "druid");
        druidRegistrationBean.setInitParameters(intParams);
        return druidRegistrationBean;
    }
}
