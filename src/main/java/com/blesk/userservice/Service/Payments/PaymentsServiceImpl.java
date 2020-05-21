package com.blesk.userservice.Service.Payments;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    @Autowired
    public PaymentsServiceImpl() {
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
}