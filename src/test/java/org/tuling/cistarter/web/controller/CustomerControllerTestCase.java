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
package org.tuling.cistarter.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.util.NestedServletException;
import org.tuling.cistarter.entity.Customer;
import org.tuling.cistarter.web.service.CustomerService;

/**
 * Created by myan on 11/29/2017.
 * Intellij IDEA
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CustomerControllerTestCase {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private CustomerController controller;
    
    @Mock
    private CustomerService service;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        Jackson2ObjectMapperBuilder objectMapperBuilder = Jackson2ObjectMapperBuilder.json();
        objectMapperBuilder.featuresToDisable(SerializationFeature.INDENT_OUTPUT);
        objectMapper = objectMapperBuilder.build();
        StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(this.controller);
        builder.setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper));
        this.mvc = builder.build();
    }
    
    @Test
    public void testSaveCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("222@173.com");
        customer.setName("tester");
    
        Mockito.when(service.checkNameExist(Matchers.anyString())).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer));
        
        ResultActions result = mvc.perform(requestBuilder);
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.content().string("true"));
    }
    
    // IllegalStateException has been cast to NestedServletException by FrameworkServlet
    @Test(expected = IllegalStateException.class)
    public void testSaveCustomerWithException() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("www@gmail.com");
        customer.setEmail("myan");
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer));
        
        ResultActions result = mvc.perform(requestBuilder);
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
   
    
}


