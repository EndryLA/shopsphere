package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.repositories.PaymentDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetails {

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetails(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

}

