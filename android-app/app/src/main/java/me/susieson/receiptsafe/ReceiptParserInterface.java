package me.susieson.receiptsafe;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ReceiptParserInterface {
    @Headers("apikey: f03766e0176111e9834113e1d510e62d")
    @POST("api/receipt/v1/simple/encoded")
    Call<ReceiptRequest> requestReceipt(@Body ReceiptRequest receiptRequest);
}
