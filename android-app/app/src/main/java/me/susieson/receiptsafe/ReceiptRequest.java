package me.susieson.receiptsafe;

public class ReceiptRequest {
    private String[] additionalFields;
    private boolean getLines;
    private boolean text;
    private boolean enableMerchantEnrichment;
    private boolean isEnableMerchantLookup;
    private String fileContent;

    public ReceiptRequest(String file) {
        this.additionalFields = new String[]{"merchantname", "totalbillamount", "billingdate", "category"};
        this.getLines = true;
        this.text = false;
        this.enableMerchantEnrichment = false;
        this.isEnableMerchantLookup = false;
        this.fileContent = file;
    }

    public String[] getAdditionalFields() {
        return additionalFields;
    }

    public boolean isGetLines() {
        return getLines;
    }

    public boolean isText() {
        return text;
    }

    public boolean isEnableMerchantEnrichment() {
        return enableMerchantEnrichment;
    }

    public boolean isEnableMerchantLookup() {
        return isEnableMerchantLookup;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setAdditionalFields(String[] additionalFields) {
        this.additionalFields = additionalFields;
    }

    public void setGetLines(boolean getLines) {
        this.getLines = getLines;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    public void setEnableMerchantEnrichment(boolean enableMerchantEnrichment) {
        this.enableMerchantEnrichment = enableMerchantEnrichment;
    }

    public void setEnableMerchantLookup(boolean enableMerchantLookup) {
        isEnableMerchantLookup = enableMerchantLookup;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

}
