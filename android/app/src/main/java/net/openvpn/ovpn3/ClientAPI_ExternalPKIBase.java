package net.openvpn.ovpn3;

public class ClientAPI_ExternalPKIBase {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_ExternalPKIBase(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ExternalPKIBase clientAPI_ExternalPKIBase) {
        if (clientAPI_ExternalPKIBase == null) {
            return 0;
        }
        return clientAPI_ExternalPKIBase.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ExternalPKIBase(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public boolean sign(String str, SWIGTYPE_p_std__string sWIGTYPE_p_std__string, String str2) {
        return ovpncliJNI.ClientAPI_ExternalPKIBase_sign(this.swigCPtr, this, str, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string), str2);
    }
}
