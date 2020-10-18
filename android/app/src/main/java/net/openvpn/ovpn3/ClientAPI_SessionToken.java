package net.openvpn.ovpn3;

public class ClientAPI_SessionToken {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_SessionToken(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_SessionToken clientAPI_SessionToken) {
        if (clientAPI_SessionToken == null) {
            return 0;
        }
        return clientAPI_SessionToken.swigCPtr;
    }

    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_SessionToken(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setUsername(String str) {
        ovpncliJNI.ClientAPI_SessionToken_username_set(this.swigCPtr, this, str);
    }

    public String getUsername() {
        return ovpncliJNI.ClientAPI_SessionToken_username_get(this.swigCPtr, this);
    }

    public void setSession_id(String str) {
        ovpncliJNI.ClientAPI_SessionToken_session_id_set(this.swigCPtr, this, str);
    }

    public String getSession_id() {
        return ovpncliJNI.ClientAPI_SessionToken_session_id_get(this.swigCPtr, this);
    }

    public ClientAPI_SessionToken() {
        this(ovpncliJNI.new_ClientAPI_SessionToken(), true);
    }
}
