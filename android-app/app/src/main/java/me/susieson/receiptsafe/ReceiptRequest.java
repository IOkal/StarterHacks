package me.susieson.receiptsafe;

public class ReceiptRequest {
    private String image;
    private String filename;
    private String contentType;

    public ReceiptRequest(String file) {
        this.filename = "image/jpeg";
        this.image = file;
        this.filename = "example.jpg";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}


