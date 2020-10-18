package net.openvpn.ovpn3;

public class ClientAPI_DynamicChallenge {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_DynamicChallenge(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_DynamicChallenge clientAPI_DynamicChallenge) {
        if (clientAPI_DynamicChallenge == null) {
            return 0;
        }
        return clientAPI_DynamicChallenge.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_DynamicChallenge(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setChallenge(String str) {
        ovpncliJNI.ClientAPI_DynamicChallenge_challenge_set(this.swigCPtr, this, str);
    }

    public String getChallenge() {
        return ovpncliJNI.ClientAPI_DynamicChallenge_challenge_get(this.swigCPtr, this);
    }

    public void setEcho(boolean z) {
        ovpncliJNI.ClientAPI_DynamicChallenge_echo_set(this.swigCPtr, this, z);
    }

    public boolean getEcho() {
        return ovpncliJNI.ClientAPI_DynamicChallenge_echo_get(this.swigCPtr, this);
    }

    public void setResponseRequired(boolean z) {
        ovpncliJNI.ClientAPI_DynamicChallenge_responseRequired_set(this.swigCPtr, this, z);
    }

    public boolean getResponseRequired() {
        return ovpncliJNI.ClientAPI_DynamicChallenge_responseRequired_get(this.swigCPtr, this);
    }

    public void setStateID(String str) {
        ovpncliJNI.ClientAPI_DynamicChallenge_stateID_set(this.swigCPtr, this, str);
    }

    public String getStateID() {
        return ovpncliJNI.ClientAPI_DynamicChallenge_stateID_get(this.swigCPtr, this);
    }

    public ClientAPI_DynamicChallenge() {
        this(ovpncliJNI.new_ClientAPI_DynamicChallenge(), true);
    }
}
