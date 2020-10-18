package net.openvpn.ovpn3;

public class ClientAPI_Event {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_Event(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_Event clientAPI_Event) {
        if (clientAPI_Event == null) {
            return 0;
        }
        return clientAPI_Event.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_Event(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setError(boolean z) {
        ovpncliJNI.ClientAPI_Event_error_set(this.swigCPtr, this, z);
    }

    public boolean getError() {
        return ovpncliJNI.ClientAPI_Event_error_get(this.swigCPtr, this);
    }

    public void setFatal(boolean z) {
        ovpncliJNI.ClientAPI_Event_fatal_set(this.swigCPtr, this, z);
    }

    public boolean getFatal() {
        return ovpncliJNI.ClientAPI_Event_fatal_get(this.swigCPtr, this);
    }

    public void setName(String str) {
        ovpncliJNI.ClientAPI_Event_name_set(this.swigCPtr, this, str);
    }

    public String getName() {
        return ovpncliJNI.ClientAPI_Event_name_get(this.swigCPtr, this);
    }

    public void setInfo(String str) {
        ovpncliJNI.ClientAPI_Event_info_set(this.swigCPtr, this, str);
    }

    public String getInfo() {
        return ovpncliJNI.ClientAPI_Event_info_get(this.swigCPtr, this);
    }

    public ClientAPI_Event() {
        this(ovpncliJNI.new_ClientAPI_Event(), true);
    }
}
