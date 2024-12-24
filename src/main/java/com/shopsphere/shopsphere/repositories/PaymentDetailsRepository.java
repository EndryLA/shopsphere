package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
}
