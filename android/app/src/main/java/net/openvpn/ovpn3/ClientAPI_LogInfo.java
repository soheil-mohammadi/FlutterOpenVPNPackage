package net.openvpn.ovpn3;

public class ClientAPI_LogInfo {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_LogInfo(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_LogInfo clientAPI_LogInfo) {
        if (clientAPI_LogInfo == null) {
            return 0;
        }
        return clientAPI_LogInfo.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_LogInfo(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public ClientAPI_LogInfo() {
        this(ovpncliJNI.new_ClientAPI_LogInfo__SWIG_0(), true);
    }

    public ClientAPI_LogInfo(String str) {
        this(ovpncliJNI.new_ClientAPI_LogInfo__SWIG_1(str), true);
    }

    public void setText(String str) {
        ovpncliJNI.ClientAPI_LogInfo_text_set(this.swigCPtr, this, str);
    }

    public String getText() {
        return ovpncliJNI.ClientAPI_LogInfo_text_get(this.swigCPtr, this);
    }
}
