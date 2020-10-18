package net.openvpn.ovpn3;

public class ClientAPI_EvalConfig {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_EvalConfig(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_EvalConfig clientAPI_EvalConfig) {
        if (clientAPI_EvalConfig == null) {
            return 0;
        }
        return clientAPI_EvalConfig.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_EvalConfig(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setError(boolean z) {
        ovpncliJNI.ClientAPI_EvalConfig_error_set(this.swigCPtr, this, z);
    }

    public boolean getError() {
        return ovpncliJNI.ClientAPI_EvalConfig_error_get(this.swigCPtr, this);
    }

    public void setMessage(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_message_set(this.swigCPtr, this, str);
    }

    public String getMessage() {
        return ovpncliJNI.ClientAPI_EvalConfig_message_get(this.swigCPtr, this);
    }

    public void setUserlockedUsername(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_userlockedUsername_set(this.swigCPtr, this, str);
    }

    public String getUserlockedUsername() {
        return ovpncliJNI.ClientAPI_EvalConfig_userlockedUsername_get(this.swigCPtr, this);
    }

    public void setProfileName(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_profileName_set(this.swigCPtr, this, str);
    }

    public String getProfileName() {
        return ovpncliJNI.ClientAPI_EvalConfig_profileName_get(this.swigCPtr, this);
    }

    public void setFriendlyName(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_friendlyName_set(this.swigCPtr, this, str);
    }

    public String getFriendlyName() {
        return ovpncliJNI.ClientAPI_EvalConfig_friendlyName_get(this.swigCPtr, this);
    }

    public void setAutologin(boolean z) {
        ovpncliJNI.ClientAPI_EvalConfig_autologin_set(this.swigCPtr, this, z);
    }

    public boolean getAutologin() {
        return ovpncliJNI.ClientAPI_EvalConfig_autologin_get(this.swigCPtr, this);
    }

    public void setExternalPki(boolean z) {
        ovpncliJNI.ClientAPI_EvalConfig_externalPki_set(this.swigCPtr, this, z);
    }

    public boolean getExternalPki() {
        return ovpncliJNI.ClientAPI_EvalConfig_externalPki_get(this.swigCPtr, this);
    }

    public void setStaticChallenge(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_staticChallenge_set(this.swigCPtr, this, str);
    }

    public String getStaticChallenge() {
        return ovpncliJNI.ClientAPI_EvalConfig_staticChallenge_get(this.swigCPtr, this);
    }

    public void setStaticChallengeEcho(boolean z) {
        ovpncliJNI.ClientAPI_EvalConfig_staticChallengeEcho_set(this.swigCPtr, this, z);
    }

    public boolean getStaticChallengeEcho() {
        return ovpncliJNI.ClientAPI_EvalConfig_staticChallengeEcho_get(this.swigCPtr, this);
    }

    public void setPrivateKeyPasswordRequired(boolean z) {
        ovpncliJNI.ClientAPI_EvalConfig_privateKeyPasswordRequired_set(this.swigCPtr, this, z);
    }

    public boolean getPrivateKeyPasswordRequired() {
        return ovpncliJNI.ClientAPI_EvalConfig_privateKeyPasswordRequired_get(this.swigCPtr, this);
    }

    public void setAllowPasswordSave(boolean z) {
        ovpncliJNI.ClientAPI_EvalConfig_allowPasswordSave_set(this.swigCPtr, this, z);
    }

    public boolean getAllowPasswordSave() {
        return ovpncliJNI.ClientAPI_EvalConfig_allowPasswordSave_get(this.swigCPtr, this);
    }

    public void setRemoteHost(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_remoteHost_set(this.swigCPtr, this, str);
    }

    public String getRemoteHost() {
        return ovpncliJNI.ClientAPI_EvalConfig_remoteHost_get(this.swigCPtr, this);
    }

    public void setRemotePort(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_remotePort_set(this.swigCPtr, this, str);
    }

    public String getRemotePort() {
        return ovpncliJNI.ClientAPI_EvalConfig_remotePort_get(this.swigCPtr, this);
    }

    public void setRemoteProto(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_remoteProto_set(this.swigCPtr, this, str);
    }

    public String getRemoteProto() {
        return ovpncliJNI.ClientAPI_EvalConfig_remoteProto_get(this.swigCPtr, this);
    }

    public void setServerList(ClientAPI_ServerEntryVector clientAPI_ServerEntryVector) {
        ovpncliJNI.ClientAPI_EvalConfig_serverList_set(this.swigCPtr, this, ClientAPI_ServerEntryVector.getCPtr(clientAPI_ServerEntryVector), clientAPI_ServerEntryVector);
    }

    public ClientAPI_ServerEntryVector getServerList() {
        long ClientAPI_EvalConfig_serverList_get = ovpncliJNI.ClientAPI_EvalConfig_serverList_get(this.swigCPtr, this);
        if (ClientAPI_EvalConfig_serverList_get == 0) {
            return null;
        }
        return new ClientAPI_ServerEntryVector(ClientAPI_EvalConfig_serverList_get, false);
    }

    public void setWindowsDriver(String str) {
        ovpncliJNI.ClientAPI_EvalConfig_windowsDriver_set(this.swigCPtr, this, str);
    }

    public String getWindowsDriver() {
        return ovpncliJNI.ClientAPI_EvalConfig_windowsDriver_get(this.swigCPtr, this);
    }

    public ClientAPI_EvalConfig() {
        this(ovpncliJNI.new_ClientAPI_EvalConfig(), true);
    }
}
