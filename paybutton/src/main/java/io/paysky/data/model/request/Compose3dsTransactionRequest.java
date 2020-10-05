package io.paysky.data.model.request;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Compose3dsTransactionRequest extends BaseRequest {

/*	@SerializedName("MerchantReference")
	public String merchantReference;*/

    @SerializedName("DateTimeLocalTrxn")
    public String dateTimeLocalTrxn;

    @SerializedName("MerchantReference")
    public String merchantReference;

    @SerializedName("AmountTrxn")
    public int amountTrxn;

    @SerializedName("ReturnURL")
    public String returnURL;

    @SerializedName("MerchantId")
    public String merchantId;

/*	@SerializedName("OrderId")
	public String orderId;*/

    @SerializedName("CVV2")
    public String cVV2;

    @SerializedName("DateExpiration")
    public String dateExpiration;

    @SerializedName("SecureHash")
    public String secureHash;


    @SerializedName("TerminalId")
    public String terminalId;

    @SerializedName("CurrencyCodeTrxn")
    public int currencyCodeTrxn;

    @SerializedName("PAN")
    public String pAN;
}