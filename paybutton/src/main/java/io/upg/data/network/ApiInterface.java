package io.upg.data.network;

import io.upg.data.model.request.CheckTransactionStatusRequest;
import io.upg.data.model.request.ManualPaymentRequest;
import io.upg.data.model.request.MerchantInfoRequest;
import io.upg.data.model.request.QrGeneratorRequest;
import io.upg.data.model.request.RequestToPayRequest;
import io.upg.data.model.request.SendReceiptByMailRequest;
import io.upg.data.model.request.TransactionStatusRequest;
import io.upg.data.model.response.CheckTransactionStatusResponse;
import io.upg.data.model.response.GenerateQrCodeResponse;
import io.upg.data.model.response.ManualPaymentResponse;
import io.upg.data.model.response.MerchantInfoResponse;
import io.upg.data.model.response.RequestToPayResponse;
import io.upg.data.model.response.SendReceiptByMailResponse;
import io.upg.data.model.response.TransactionStatusResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Paysky-202 on 5/17/2018.
 */

public interface ApiInterface {


    @POST(ApiLinks.SEND_RECEIPT_BY_MAIL)
    Call<SendReceiptByMailResponse> sendReceiptByMail(@Body SendReceiptByMailRequest mailRequest);

    @POST(ApiLinks.CHECK_PAYMENT_STATUS)
    Call<TransactionStatusResponse> checkTransactionStatus(@Body TransactionStatusRequest request);

    @POST(ApiLinks.GENERATE_QRCODE)
    Call<GenerateQrCodeResponse> generateQrCode(@Body QrGeneratorRequest request);

    @POST(ApiLinks.EXECUTE_PAYMENT)
    Call<ManualPaymentResponse> executeManualPayment(@Body ManualPaymentRequest paymentRequest);

    @POST(ApiLinks.SMS_PAYMENT)
    Call<RequestToPayResponse> requestToPay(@Body RequestToPayRequest request);

    @POST(ApiLinks.MERCHANT_INFO)
    Call<MerchantInfoResponse> getMerchantInfo(@Body MerchantInfoRequest request);
//
//    @POST(ApiLinks.COMPOSE_3DS_TRANSACTION)
//    Call<Compose3dsTransactionResponse> compose3dpsTransaction(@Body Compose3dsTransactionRequest request);

//    @POST(ApiLinks.PROCESS_3D_TRANSACTION)
//    Call<Process3dTransactionResponse> process3dTransaction(@Body Process3dTransactionRequest request);

    @POST(ApiLinks.CHECK_TRANSACTION_STATUS)
    Call<CheckTransactionStatusResponse> checkTransaction(@Body CheckTransactionStatusRequest request);
}
