package io.upg.ui.fragment.qr;

import android.content.Context;
import android.graphics.Bitmap;

import io.upg.data.model.PaymentData;
import io.upg.data.model.response.TransactionStatusResponse;
import io.upg.ui.mvp.BaseView;

public interface QrView extends BaseView {


    void setPaymentApproved(TransactionStatusResponse response, PaymentData paymentData);

    void listenToPaymentApproval();

    void setGenerateQrSuccess(long txnId);

    void showInfoToast(String message);

    void showErrorInServerToast();

    Context getContext();

    void showQrImage(Bitmap bitmap);

    void disableR2pViews();
}
