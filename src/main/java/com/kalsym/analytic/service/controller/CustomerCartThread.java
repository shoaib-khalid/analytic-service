/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service.controller;

import com.kalsym.analytic.service.model.CustomerActivity;
import com.kalsym.analytic.service.model.CustomerSessionCarts;
import com.kalsym.analytic.service.model.repository.CustomerSessionCartsRepository;
import java.util.Date;

import java.util.List;

/**
 *
 * @author taufik
 */
public class CustomerCartThread extends Thread {
    private final CustomerActivity bodyActivity;
    private final CustomerSessionCartsRepository customerSessionCartsRepository;
    
    public CustomerCartThread(CustomerActivity bodyActivity, CustomerSessionCartsRepository customerSessionCartsRepository) {
        this.bodyActivity = bodyActivity;
        this.customerSessionCartsRepository = customerSessionCartsRepository;
    }
    
    @Override
    public void run(){
        String sessionId = bodyActivity.getSessionId();
        for (int i=0;i<bodyActivity.getCart().size();i++) {
            String cartId = bodyActivity.getCart().get(i);
            List<CustomerSessionCarts> cartList = customerSessionCartsRepository.findBySessionIdAndCartId(sessionId, cartId);
            if (cartList.isEmpty()) {
                CustomerSessionCarts cart = new CustomerSessionCarts();
                cart.setCartId(cartId);
                cart.setSessionId(sessionId);
                cart.setCreated(new Date());
                cart.setUpdated(new Date());
                customerSessionCartsRepository.save(cart);
            }
        }
    }
}
