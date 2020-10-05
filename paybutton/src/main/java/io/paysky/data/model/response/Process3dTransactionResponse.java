package io.paysky.data.model.response;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Process3dTransactionResponse {

    @SerializedName("VerToken")
    public String verToken;

    @SerializedName("ThreeDSenrolled")
    public String threeDSenrolled;

    @SerializedName("ThreeDSstatus")
    public String threeDSstatus;

    @SerializedName("VerType")
    public String verType;

    @SerializedName("Message")
    public String message;

    @SerializedName("ThreeDSXID")
    public String threeDSXID;

    @SerializedName("Success")
    public boolean success;

    @SerializedName("ThreeDSECI")
    public String threeDSECI;
}