package net.openvpn.ovpn3;

public class ClientAPI_TransportStats {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_TransportStats(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_TransportStats clientAPI_TransportStats) {
        if (clientAPI_TransportStats == null) {
            return 0;
        }
        return clientAPI_TransportStats.swigCPtr;
    }

    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_TransportStats(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setBytesIn(long j) {
        ovpncliJNI.ClientAPI_TransportStats_bytesIn_set(this.swigCPtr, this, j);
    }

    public long getBytesIn() {
        return ovpncliJNI.ClientAPI_TransportStats_bytesIn_get(this.swigCPtr, this);
    }

    public void setBytesOut(long j) {
        ovpncliJNI.ClientAPI_TransportStats_bytesOut_set(this.swigCPtr, this, j);
    }

    public long getBytesOut() {
        return ovpncliJNI.ClientAPI_TransportStats_bytesOut_get(this.swigCPtr, this);
    }

    public void setPacketsIn(long j) {
        ovpncliJNI.ClientAPI_TransportStats_packetsIn_set(this.swigCPtr, this, j);
    }

    public long getPacketsIn() {
        return ovpncliJNI.ClientAPI_TransportStats_packetsIn_get(this.swigCPtr, this);
    }

    public void setPacketsOut(long j) {
        ovpncliJNI.ClientAPI_TransportStats_packetsOut_set(this.swigCPtr, this, j);
    }

    public long getPacketsOut() {
        return ovpncliJNI.ClientAPI_TransportStats_packetsOut_get(this.swigCPtr, this);
    }

    public void setLastPacketReceived(int i) {
        ovpncliJNI.ClientAPI_TransportStats_lastPacketReceived_set(this.swigCPtr, this, i);
    }

    public int getLastPacketReceived() {
        return ovpncliJNI.ClientAPI_TransportStats_lastPacketReceived_get(this.swigCPtr, this);
    }

    public ClientAPI_TransportStats() {
        this(ovpncliJNI.new_ClientAPI_TransportStats(), true);
    }
}
