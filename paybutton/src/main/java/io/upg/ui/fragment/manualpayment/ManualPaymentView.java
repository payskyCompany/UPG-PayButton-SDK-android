package io.upg.ui.fragment.manualpayment;

import android.os.Bundle;

import io.upg.data.model.PaymentData;
import io.upg.ui.mvp.BaseView;

public interface ManualPaymentView extends BaseView {


    void show3dpWebView(String paymentServerURL, PaymentData paymentData);

    void showErrorInServerToast();

    void showPaymentFailedFragment(Bundle bundle);

    void showTransactionApprovedFragment(String transactionNo, String authCode, String receiptNumber, String cardHolder, String cardNumber, String systemReference, PaymentData paymentData);
}
