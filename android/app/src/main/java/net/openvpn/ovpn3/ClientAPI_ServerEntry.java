package net.openvpn.ovpn3;

public class ClientAPI_ServerEntry {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_ServerEntry(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ServerEntry clientAPI_ServerEntry) {
        if (clientAPI_ServerEntry == null) {
            return 0;
        }
        return clientAPI_ServerEntry.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ServerEntry(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setServer(String str) {
        ovpncliJNI.ClientAPI_ServerEntry_server_set(this.swigCPtr, this, str);
    }

    public String getServer() {
        return ovpncliJNI.ClientAPI_ServerEntry_server_get(this.swigCPtr, this);
    }

    public void setFriendlyName(String str) {
        ovpncliJNI.ClientAPI_ServerEntry_friendlyName_set(this.swigCPtr, this, str);
    }

    public String getFriendlyName() {
        return ovpncliJNI.ClientAPI_ServerEntry_friendlyName_get(this.swigCPtr, this);
    }

    public ClientAPI_ServerEntry() {
        this(ovpncliJNI.new_ClientAPI_ServerEntry(), true);
    }
}
