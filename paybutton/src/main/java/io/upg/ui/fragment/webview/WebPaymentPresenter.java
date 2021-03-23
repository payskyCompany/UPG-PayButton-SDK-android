package io.upg.ui.fragment.webview;

import io.upg.ui.mvp.BasePresenter;

class WebPaymentPresenter extends BasePresenter<WebPaymentView> {


    public void load3dWebView() {
        view.load3dTransactionWebView();
    }
}
