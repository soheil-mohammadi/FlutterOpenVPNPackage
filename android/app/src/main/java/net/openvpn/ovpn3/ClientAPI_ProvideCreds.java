package net.openvpn.ovpn3;

public class ClientAPI_ProvideCreds {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_ProvideCreds(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ProvideCreds clientAPI_ProvideCreds) {
        if (clientAPI_ProvideCreds == null) {
            return 0;
        }
        return clientAPI_ProvideCreds.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ProvideCreds(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setUsername(String str) {
        ovpncliJNI.ClientAPI_ProvideCreds_username_set(this.swigCPtr, this, str);
    }

    public String getUsername() {
        return ovpncliJNI.ClientAPI_ProvideCreds_username_get(this.swigCPtr, this);
    }

    public void setPassword(String str) {
        ovpncliJNI.ClientAPI_ProvideCreds_password_set(this.swigCPtr, this, str);
    }

    public String getPassword() {
        return ovpncliJNI.ClientAPI_ProvideCreds_password_get(this.swigCPtr, this);
    }

    public void setResponse(String str) {
        ovpncliJNI.ClientAPI_ProvideCreds_response_set(this.swigCPtr, this, str);
    }

    public String getResponse() {
        return ovpncliJNI.ClientAPI_ProvideCreds_response_get(this.swigCPtr, this);
    }

    public void setDynamicChallengeCookie(String str) {
        ovpncliJNI.ClientAPI_ProvideCreds_dynamicChallengeCookie_set(this.swigCPtr, this, str);
    }

    public String getDynamicChallengeCookie() {
        return ovpncliJNI.ClientAPI_ProvideCreds_dynamicChallengeCookie_get(this.swigCPtr, this);
    }

    public void setReplacePasswordWithSessionID(boolean z) {
        ovpncliJNI.ClientAPI_ProvideCreds_replacePasswordWithSessionID_set(this.swigCPtr, this, z);
    }

    public boolean getReplacePasswordWithSessionID() {
        return ovpncliJNI.ClientAPI_ProvideCreds_replacePasswordWithSessionID_get(this.swigCPtr, this);
    }

    public void setCachePassword(boolean z) {
        ovpncliJNI.ClientAPI_ProvideCreds_cachePassword_set(this.swigCPtr, this, z);
    }

    public boolean getCachePassword() {
        return ovpncliJNI.ClientAPI_ProvideCreds_cachePassword_get(this.swigCPtr, this);
    }

    public ClientAPI_ProvideCreds() {
        this(ovpncliJNI.new_ClientAPI_ProvideCreds(), true);
    }
}
