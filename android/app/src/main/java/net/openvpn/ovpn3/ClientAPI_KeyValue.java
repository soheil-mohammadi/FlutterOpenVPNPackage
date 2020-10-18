package net.openvpn.ovpn3;

public class ClientAPI_KeyValue {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_KeyValue(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_KeyValue clientAPI_KeyValue) {
        if (clientAPI_KeyValue == null) {
            return 0;
        }
        return clientAPI_KeyValue.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_KeyValue(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public ClientAPI_KeyValue() {
        this(ovpncliJNI.new_ClientAPI_KeyValue__SWIG_0(), true);
    }

    public ClientAPI_KeyValue(String str, String str2) {
        this(ovpncliJNI.new_ClientAPI_KeyValue__SWIG_1(str, str2), true);
    }

    public void setKey(String str) {
        ovpncliJNI.ClientAPI_KeyValue_key_set(this.swigCPtr, this, str);
    }

    public String getKey() {
        return ovpncliJNI.ClientAPI_KeyValue_key_get(this.swigCPtr, this);
    }

    public void setValue(String str) {
        ovpncliJNI.ClientAPI_KeyValue_value_set(this.swigCPtr, this, str);
    }

    public String getValue() {
        return ovpncliJNI.ClientAPI_KeyValue_value_get(this.swigCPtr, this);
    }
}
