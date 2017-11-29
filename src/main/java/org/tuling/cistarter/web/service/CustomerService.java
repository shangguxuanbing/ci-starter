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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuling.cistarter.entity.Customer;
import org.tuling.cistarter.web.dao.CustomerDAO;
import org.tuling.cistarter.web.dao.CustomerMapper;

import java.util.List;

/**
 * Created by myan on 11/27/2017.
 * Intellij IDEA
 */

@Service
public class CustomerService {
    private CustomerDAO customerDao;
    private CustomerMapper customerMapper;
    
    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }
    
    @Autowired
    private void setCustomerDao(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getCustomerList() {
        return customerDao.getAll();
    }
    
    public Customer getCustomer(int id) {
        return customerDao.getCustomerById(id);
    }
    
    public void save(Customer customer) {
        customerMapper.save(customer);
    }
    
    public boolean checkNameExist(String name) {
        return customerMapper.checkNameExists(name) > 0;
    }
}
