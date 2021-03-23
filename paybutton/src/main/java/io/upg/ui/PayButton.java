package io.upg.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.paybutton.R;

import io.upg.data.model.CurrencyCode;
import io.upg.data.model.PaymentData;
import io.upg.data.model.SuccessfulCardTransaction;
import io.upg.data.model.SuccessfulWalletTransaction;
import io.upg.data.model.request.MerchantInfoRequest;
import io.upg.data.model.response.MerchantInfoResponse;
import io.upg.data.network.ApiConnection;
import io.upg.data.network.ApiLinks;
import io.upg.data.network.ApiResponseListener;
import io.upg.exception.TransactionException;
import io.upg.ui.activity.payment.PaymentActivity;
import io.upg.util.AllURLsStatus;
import io.upg.util.AppConstant;
import io.upg.util.AppUtils;
import io.upg.util.CurrencyHelper;
import io.upg.util.HashGenerator;
import io.upg.util.ToastUtils;

public class PayButton {

    private Context context;
    private String merchantId, terminalId;
    private double amount = 0.0;
    private int currencyCode = 0;
    private String merchantSecureHash;
    private String transactionReferenceNumber;
    public static PaymentTransactionCallback transactionCallback;
    private ProgressDialog progressDialog;
    //    private boolean isProduction =false;
    private AllURLsStatus productionStatus;

    public void setProductionStatus(AllURLsStatus productionStatus) {
        this.productionStatus = productionStatus;
    }

    public PayButton(Context context) {
        this.context = context;
    }


    public PayButton setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public PayButton setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }

    public PayButton setAmount(double amount) {
        String englishAmount = AppUtils.convertToEnglishDigits(amount + "");
        this.amount = Double.valueOf(englishAmount);
        return this;
    }

    public PayButton setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public PayButton setMerchantSecureHash(String merchantSecureHash) {
        this.merchantSecureHash = merchantSecureHash;
        return this;
    }

    public PayButton setTransactionReferenceNumber(String transactionReferenceNumber) {
        this.transactionReferenceNumber = transactionReferenceNumber;
        return this;
    }


    public void createTransaction(PaymentTransactionCallback transactionCallback) {

        switch (productionStatus) {
            case UPG_STAGING:
                ApiLinks.PAYMENT_LINK = ApiLinks.STAGING;
                break;
            case UPG_PRODUCTION:
                ApiLinks.PAYMENT_LINK = ApiLinks.PRODUCTION;
                break;
        }


//
//        if (isProduction) {
//            ApiLinks.PAYMENT_LINK = ApiLinks.CUBE;
//        } else {
//            ApiLinks.PAYMENT_LINK = ApiLinks.GRAY_LINK;
//        }
        PayButton.transactionCallback = transactionCallback;
        // validate user inputs.
        validateUserInputs();
        showProgress();
        loadMerchantInfo();
    }


//    public boolean isProduction() {
//        return isProduction;
//    }
//
//    public void setProduction(boolean production) {
//        isProduction = production;
//    }


    private void showProgress() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.show();
    }


    private void loadMerchantInfo() {
        MerchantInfoRequest request = new MerchantInfoRequest();
        request.merchantId = merchantId;
        request.terminalId = terminalId;
        request.paymentMethod = null;
        request.dateTimeLocalTrxn = AppUtils.getDateTimeLocalTrxn();
        request.secureHash = HashGenerator.encode(merchantSecureHash, request.dateTimeLocalTrxn, merchantId, terminalId);
        ApiConnection.getMerchantInfo(request, new ApiResponseListener<MerchantInfoResponse>() {
            @Override
            public void onSuccess(MerchantInfoResponse response) {
                dismissProgressDialog();
                if (response == null || !response.success) {
                    ToastUtils.showToast(context, R.string.failed_load_merchant_data);
                    PayButton.transactionCallback.onError(new TransactionException("invalid merchant data"));
                    return;
                }
                PaymentData paymentData = new PaymentData();
                paymentData.merchantId = merchantId;
                paymentData.terminalId = terminalId;
                paymentData.transactionReferenceNumber = transactionReferenceNumber;
                paymentData.merchantName = response.merchantName;
                paymentData.is3dsEnabled = response.is3DS;
                paymentData.isTahweel = response.isTahweel;
                paymentData.isVisa = response.ismVisa;
                if (currencyCode == 0) {
                    paymentData.currencyCode = response.merchantCurrency;
                } else {
                    paymentData.currencyCode = currencyCode + "";
                }
                paymentData.amount = amount;
                paymentData.amountFormatted = amount + "";
                paymentData.paymentMethod = response.paymentMethod;
                paymentData.secureHashKey = merchantSecureHash;
                String[] c = {paymentData.currencyCode};
                CurrencyCode currencyCode = CurrencyHelper.getCurrencyCode(context, c[0]);
                if (currencyCode != null) {
                    paymentData.currencyName = currencyCode.currencyShortName;
                } else {
                    paymentData.currencyName = "";
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstant.BundleKeys.PAYMENT_DATA, paymentData);
                bundle.putInt(AppConstant.BundleKeys.URL_ENUM_KEY, productionStatus.ordinal());
                context.startActivity(new Intent(context, PaymentActivity.class).putExtras(bundle));
            }

            @Override
            public void onFail(Throwable error) {
                error.printStackTrace();
                dismissProgressDialog();
                ToastUtils.showToast(context, R.string.check_internet_connection);
                PayButton.transactionCallback.onError(new TransactionException("invalid merchant data"));
            }
        });
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void validateUserInputs() {

        if (merchantId == null || terminalId == null || merchantSecureHash == null || transactionReferenceNumber == null) {
            throw new IllegalArgumentException("add all inputs data");
        }


        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        if (transactionCallback == null) {
            throw new IllegalArgumentException("transaction callback cannot be null");
        }

    }

    public interface PaymentTransactionCallback {
        void onCardTransactionSuccess(SuccessfulCardTransaction cardTransaction);

        void onWalletTransactionSuccess(SuccessfulWalletTransaction walletTransaction);

        void onError(TransactionException error);
    }
}
