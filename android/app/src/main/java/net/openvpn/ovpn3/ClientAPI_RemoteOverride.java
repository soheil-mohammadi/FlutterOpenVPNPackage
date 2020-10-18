package net.openvpn.ovpn3;

public class ClientAPI_RemoteOverride {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_RemoteOverride(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_RemoteOverride clientAPI_RemoteOverride) {
        if (clientAPI_RemoteOverride == null) {
            return 0;
        }
        return clientAPI_RemoteOverride.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_RemoteOverride(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setHost(String str) {
        ovpncliJNI.ClientAPI_RemoteOverride_host_set(this.swigCPtr, this, str);
    }

    public String getHost() {
        return ovpncliJNI.ClientAPI_RemoteOverride_host_get(this.swigCPtr, this);
    }

    public void setIp(String str) {
        ovpncliJNI.ClientAPI_RemoteOverride_ip_set(this.swigCPtr, this, str);
    }

    public String getIp() {
        return ovpncliJNI.ClientAPI_RemoteOverride_ip_get(this.swigCPtr, this);
    }

    public void setPort(String str) {
        ovpncliJNI.ClientAPI_RemoteOverride_port_set(this.swigCPtr, this, str);
    }

    public String getPort() {
        return ovpncliJNI.ClientAPI_RemoteOverride_port_get(this.swigCPtr, this);
    }

    public void setProto(String str) {
        ovpncliJNI.ClientAPI_RemoteOverride_proto_set(this.swigCPtr, this, str);
    }

    public String getProto() {
        return ovpncliJNI.ClientAPI_RemoteOverride_proto_get(this.swigCPtr, this);
    }

    public void setError(String str) {
        ovpncliJNI.ClientAPI_RemoteOverride_error_set(this.swigCPtr, this, str);
    }

    public String getError() {
        return ovpncliJNI.ClientAPI_RemoteOverride_error_get(this.swigCPtr, this);
    }

    public ClientAPI_RemoteOverride() {
        this(ovpncliJNI.new_ClientAPI_RemoteOverride(), true);
    }
}
