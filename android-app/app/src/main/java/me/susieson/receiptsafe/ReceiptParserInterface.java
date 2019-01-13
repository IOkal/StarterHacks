package me.susieson.receiptsafe;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ReceiptParserInterface {
    //@Header("apikey: ")
    @POST("ocr/v2/receipt")
    Call<ReceiptRequest> requestReceipt(@Body ReceiptRequest receiptRequest);
}
