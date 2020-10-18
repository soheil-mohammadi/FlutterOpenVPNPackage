package net.openvpn.ovpn3;

public class ClientAPI_Status {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_Status(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_Status clientAPI_Status) {
        if (clientAPI_Status == null) {
            return 0;
        }
        return clientAPI_Status.swigCPtr;
    }

    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_Status(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setError(boolean z) {
        ovpncliJNI.ClientAPI_Status_error_set(this.swigCPtr, this, z);
    }

    public boolean getError() {
        return ovpncliJNI.ClientAPI_Status_error_get(this.swigCPtr, this);
    }

    public void setStatus(String str) {
        ovpncliJNI.ClientAPI_Status_status_set(this.swigCPtr, this, str);
    }

    public String getStatus() {
        return ovpncliJNI.ClientAPI_Status_status_get(this.swigCPtr, this);
    }

    public void setMessage(String str) {
        ovpncliJNI.ClientAPI_Status_message_set(this.swigCPtr, this, str);
    }

    public String getMessage() {
        return ovpncliJNI.ClientAPI_Status_message_get(this.swigCPtr, this);
    }

    public ClientAPI_Status() {
        this(ovpncliJNI.new_ClientAPI_Status(), true);
    }
}
