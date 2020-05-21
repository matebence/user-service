package com.blesk.userservice.Service.Payouts;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PayoutsServiceImpl implements PayoutsService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Autowired
    public PayoutsServiceImpl() {
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
}