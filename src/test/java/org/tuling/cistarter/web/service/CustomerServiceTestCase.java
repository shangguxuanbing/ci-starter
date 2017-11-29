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
package org.tuling.cistarter.web.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.test.context.junit4.SpringRunner;
import org.tuling.cistarter.entity.Customer;
import org.tuling.cistarter.web.dao.CustomerDAO;
import org.tuling.cistarter.web.dao.CustomerMapper;

/**
 * Created by myan on 11/29/2017.
 * Intellij IDEA
 */
@RunWith(SpringRunner.class)
public class CustomerServiceTestCase {
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private CustomerDAO customerDAO;
    private CustomerService service;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CustomerService();
        Whitebox.setInternalState(service, "customerDao", customerDAO);
        Whitebox.setInternalState(service, "customerMapper", customerMapper);
    }
    
    @Test
    public void testgetCustomer() {
        Customer customer = Mockito.mock(Customer.class);
        Mockito.when(customerDAO.getCustomerById(Matchers.anyInt())).thenReturn(customer);
        Assert.assertEquals(customer, service.getCustomer(1));
    }
}
