package net.openvpn.ovpn3;

public class ClientAPI_ConnectionInfo {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_ConnectionInfo(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ConnectionInfo clientAPI_ConnectionInfo) {
        if (clientAPI_ConnectionInfo == null) {
            return 0;
        }
        return clientAPI_ConnectionInfo.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ConnectionInfo(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setDefined(boolean z) {
        ovpncliJNI.ClientAPI_ConnectionInfo_defined_set(this.swigCPtr, this, z);
    }

    public boolean getDefined() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_defined_get(this.swigCPtr, this);
    }

    public void setUser(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_user_set(this.swigCPtr, this, str);
    }

    public String getUser() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_user_get(this.swigCPtr, this);
    }

    public void setServerHost(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_serverHost_set(this.swigCPtr, this, str);
    }

    public String getServerHost() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_serverHost_get(this.swigCPtr, this);
    }

    public void setServerPort(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_serverPort_set(this.swigCPtr, this, str);
    }

    public String getServerPort() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_serverPort_get(this.swigCPtr, this);
    }

    public void setServerProto(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_serverProto_set(this.swigCPtr, this, str);
    }

    public String getServerProto() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_serverProto_get(this.swigCPtr, this);
    }

    public void setServerIp(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_serverIp_set(this.swigCPtr, this, str);
    }

    public String getServerIp() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_serverIp_get(this.swigCPtr, this);
    }

    public void setVpnIp4(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_vpnIp4_set(this.swigCPtr, this, str);
    }

    public String getVpnIp4() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_vpnIp4_get(this.swigCPtr, this);
    }

    public void setVpnIp6(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_vpnIp6_set(this.swigCPtr, this, str);
    }

    public String getVpnIp6() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_vpnIp6_get(this.swigCPtr, this);
    }

    public void setGw4(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_gw4_set(this.swigCPtr, this, str);
    }

    public String getGw4() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_gw4_get(this.swigCPtr, this);
    }

    public void setGw6(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_gw6_set(this.swigCPtr, this, str);
    }

    public String getGw6() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_gw6_get(this.swigCPtr, this);
    }

    public void setClientIp(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_clientIp_set(this.swigCPtr, this, str);
    }

    public String getClientIp() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_clientIp_get(this.swigCPtr, this);
    }

    public void setTunName(String str) {
        ovpncliJNI.ClientAPI_ConnectionInfo_tunName_set(this.swigCPtr, this, str);
    }

    public String getTunName() {
        return ovpncliJNI.ClientAPI_ConnectionInfo_tunName_get(this.swigCPtr, this);
    }

    public ClientAPI_ConnectionInfo() {
        this(ovpncliJNI.new_ClientAPI_ConnectionInfo(), true);
    }
}
