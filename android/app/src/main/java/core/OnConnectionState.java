package core;


public interface OnConnectionState {

    void onConnected();
    void onFailed();
    void onDisconnected();
    void onConnecting();
    void onTransferData(String downloaded, String uploaded);
    void onTimeConnectedChange(String connectedTime);
}
