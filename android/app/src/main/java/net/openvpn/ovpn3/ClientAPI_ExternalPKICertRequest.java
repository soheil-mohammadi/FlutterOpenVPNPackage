package net.openvpn.ovpn3;

public class ClientAPI_ExternalPKICertRequest extends ClientAPI_ExternalPKIRequestBase {
    private transient long swigCPtr;

    protected ClientAPI_ExternalPKICertRequest(long j, boolean z) {
        super(ovpncliJNI.ClientAPI_ExternalPKICertRequest_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ExternalPKICertRequest clientAPI_ExternalPKICertRequest) {
        if (clientAPI_ExternalPKICertRequest == null) {
            return 0;
        }
        return clientAPI_ExternalPKICertRequest.swigCPtr;
    }

    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ExternalPKICertRequest(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
        super.delete();
    }

    public void setCert(String str) {
        ovpncliJNI.ClientAPI_ExternalPKICertRequest_cert_set(this.swigCPtr, this, str);
    }

    public String getCert() {
        return ovpncliJNI.ClientAPI_ExternalPKICertRequest_cert_get(this.swigCPtr, this);
    }

    public void setSupportingChain(String str) {
        ovpncliJNI.ClientAPI_ExternalPKICertRequest_supportingChain_set(this.swigCPtr, this, str);
    }

    public String getSupportingChain() {
        return ovpncliJNI.ClientAPI_ExternalPKICertRequest_supportingChain_get(this.swigCPtr, this);
    }

    public ClientAPI_ExternalPKICertRequest() {
        this(ovpncliJNI.new_ClientAPI_ExternalPKICertRequest(), true);
    }
}
