package sba.project.tuvanluatgiaothong.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sba.project.tuvanluatgiaothong.enums.TransactionMethod;

@Component
public class PaymentFactory {

    @Autowired
    private VNPayService vnPayService;

    public PaymentService getPaymentService(TransactionMethod paymentMethod) {
        if (paymentMethod == TransactionMethod.VNPAY) {
            return vnPayService;
        }
        throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
    }
}
