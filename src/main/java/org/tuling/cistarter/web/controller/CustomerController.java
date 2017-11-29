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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tuling.cistarter.entity.Customer;
import org.tuling.cistarter.web.service.CustomerService;

import java.util.List;

/**
 * Created by myan on 11/27/2017.
 * Intellij IDEA
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    private CustomerService service;

    @Autowired
    private void setService(CustomerService service) {
        this.service = service;
    }
    
    @RequestMapping("/list")
    public List<Customer> getCustomers() {
        return service.getCustomerList();
    }
    
    @RequestMapping("/{id}")
    public Customer getById(@PathVariable("id") int id) {
        if(id <= 0)
            throw new IllegalStateException("Invalid customer id");
        return service.getCustomer(id);
    }
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public boolean saveCustomer(@RequestBody Customer customer) {
        // we can use bean validation instead of duplicate checking here.
        if(StringUtils.isEmpty(customer.getEmail()) ||
                !customer.getEmail().matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
            throw new IllegalStateException("Email invalid");
        } else if(StringUtils.isEmpty(customer.getName())) {
            throw new IllegalStateException("Name invalid");
        } else if(service.checkNameExist(customer.getName())) {
            throw new IllegalStateException("Customer name already exists");
        }
        service.save(customer);
        return true;
    }

}
