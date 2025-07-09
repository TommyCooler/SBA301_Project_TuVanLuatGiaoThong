package sba.project.tuvanluatgiaothong.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sba.project.tuvanluatgiaothong.enums.TransactionMethod;
@Component
public class PaymentFactory {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private MoMoService moMoService;

    public PaymentService getPaymentService(TransactionMethod paymentMethod) {
        switch (paymentMethod) {
            case VNPAY:
                return vnPayService;
            case MOMO:
                return moMoService;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }
}