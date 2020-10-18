package net.openvpn.ovpn3;

public class ClientAPI_InterfaceStats {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_InterfaceStats(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_InterfaceStats clientAPI_InterfaceStats) {
        if (clientAPI_InterfaceStats == null) {
            return 0;
        }
        return clientAPI_InterfaceStats.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_InterfaceStats(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setBytesIn(long j) {
        ovpncliJNI.ClientAPI_InterfaceStats_bytesIn_set(this.swigCPtr, this, j);
    }

    public long getBytesIn() {
        return ovpncliJNI.ClientAPI_InterfaceStats_bytesIn_get(this.swigCPtr, this);
    }

    public void setPacketsIn(long j) {
        ovpncliJNI.ClientAPI_InterfaceStats_packetsIn_set(this.swigCPtr, this, j);
    }

    public long getPacketsIn() {
        return ovpncliJNI.ClientAPI_InterfaceStats_packetsIn_get(this.swigCPtr, this);
    }

    public void setErrorsIn(long j) {
        ovpncliJNI.ClientAPI_InterfaceStats_errorsIn_set(this.swigCPtr, this, j);
    }

    public long getErrorsIn() {
        return ovpncliJNI.ClientAPI_InterfaceStats_errorsIn_get(this.swigCPtr, this);
    }

    public void setBytesOut(long j) {
        ovpncliJNI.ClientAPI_InterfaceStats_bytesOut_set(this.swigCPtr, this, j);
    }

    public long getBytesOut() {
        return ovpncliJNI.ClientAPI_InterfaceStats_bytesOut_get(this.swigCPtr, this);
    }

    public void setPacketsOut(long j) {
        ovpncliJNI.ClientAPI_InterfaceStats_packetsOut_set(this.swigCPtr, this, j);
    }

    public long getPacketsOut() {
        return ovpncliJNI.ClientAPI_InterfaceStats_packetsOut_get(this.swigCPtr, this);
    }

    public void setErrorsOut(long j) {
        ovpncliJNI.ClientAPI_InterfaceStats_errorsOut_set(this.swigCPtr, this, j);
    }

    public long getErrorsOut() {
        return ovpncliJNI.ClientAPI_InterfaceStats_errorsOut_get(this.swigCPtr, this);
    }

    public ClientAPI_InterfaceStats() {
        this(ovpncliJNI.new_ClientAPI_InterfaceStats(), true);
    }
}
