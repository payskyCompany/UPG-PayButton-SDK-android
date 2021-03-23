package io.upg.ui.fragment.paymentsuccess;

import android.content.Context;

import androidx.annotation.StringRes;

import io.upg.ui.mvp.BaseView;

interface PaymentApprovedView extends BaseView {
    void setSendEmailSuccess(String emailTo, int operationType);

    Context getContext();

    void showErrorToast(@StringRes int error);
}
