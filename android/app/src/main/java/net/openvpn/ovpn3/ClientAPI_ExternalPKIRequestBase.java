package net.openvpn.ovpn3;

public class ClientAPI_ExternalPKIRequestBase {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_ExternalPKIRequestBase(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ExternalPKIRequestBase clientAPI_ExternalPKIRequestBase) {
        if (clientAPI_ExternalPKIRequestBase == null) {
            return 0;
        }
        return clientAPI_ExternalPKIRequestBase.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ExternalPKIRequestBase(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setError(boolean z) {
        ovpncliJNI.ClientAPI_ExternalPKIRequestBase_error_set(this.swigCPtr, this, z);
    }

    public boolean getError() {
        return ovpncliJNI.ClientAPI_ExternalPKIRequestBase_error_get(this.swigCPtr, this);
    }

    public void setErrorText(String str) {
        ovpncliJNI.ClientAPI_ExternalPKIRequestBase_errorText_set(this.swigCPtr, this, str);
    }

    public String getErrorText() {
        return ovpncliJNI.ClientAPI_ExternalPKIRequestBase_errorText_get(this.swigCPtr, this);
    }

    public void setInvalidAlias(boolean z) {
        ovpncliJNI.ClientAPI_ExternalPKIRequestBase_invalidAlias_set(this.swigCPtr, this, z);
    }

    public boolean getInvalidAlias() {
        return ovpncliJNI.ClientAPI_ExternalPKIRequestBase_invalidAlias_get(this.swigCPtr, this);
    }

    public void setAlias(String str) {
        ovpncliJNI.ClientAPI_ExternalPKIRequestBase_alias_set(this.swigCPtr, this, str);
    }

    public String getAlias() {
        return ovpncliJNI.ClientAPI_ExternalPKIRequestBase_alias_get(this.swigCPtr, this);
    }

    public ClientAPI_ExternalPKIRequestBase() {
        this(ovpncliJNI.new_ClientAPI_ExternalPKIRequestBase(), true);
    }
}
