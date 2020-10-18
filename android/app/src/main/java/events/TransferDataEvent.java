package events;

public class TransferDataEvent {

    private String downloaded ;
    private String uploaded ;


    public TransferDataEvent(String downloaded, String uploaded) {
        this.downloaded = downloaded;
        this.uploaded = uploaded;
    }


    public String getDownloaded() {
        return downloaded;
    }

    public String getUploaded() {
        return uploaded;
    }
}
