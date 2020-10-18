package net.openvpn.ovpn3;

public class ClientAPI_Config {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_Config(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_Config clientAPI_Config) {
        if (clientAPI_Config == null) {
            return 0;
        }
        return clientAPI_Config.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_Config(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public void setContent(String str) {
        ovpncliJNI.ClientAPI_Config_content_set(this.swigCPtr, this, str);
    }

    public String getContent() {
        return ovpncliJNI.ClientAPI_Config_content_get(this.swigCPtr, this);
    }

    public void setContentList(SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t sWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t) {
        ovpncliJNI.ClientAPI_Config_contentList_set(this.swigCPtr, this, SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t.getCPtr(sWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t));
    }

    public SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t getContentList() {
        long ClientAPI_Config_contentList_get = ovpncliJNI.ClientAPI_Config_contentList_get(this.swigCPtr, this);
        if (ClientAPI_Config_contentList_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t(ClientAPI_Config_contentList_get, false);
    }

    public void setGuiVersion(String str) {
        ovpncliJNI.ClientAPI_Config_guiVersion_set(this.swigCPtr, this, str);
    }

    public String getGuiVersion() {
        return ovpncliJNI.ClientAPI_Config_guiVersion_get(this.swigCPtr, this);
    }

    public void setSsoMethods(String str) {
        ovpncliJNI.ClientAPI_Config_ssoMethods_set(this.swigCPtr, this, str);
    }

    public String getSsoMethods() {
        return ovpncliJNI.ClientAPI_Config_ssoMethods_get(this.swigCPtr, this);
    }

    public void setHwAddrOverride(String str) {
        ovpncliJNI.ClientAPI_Config_hwAddrOverride_set(this.swigCPtr, this, str);
    }

    public String getHwAddrOverride() {
        return ovpncliJNI.ClientAPI_Config_hwAddrOverride_get(this.swigCPtr, this);
    }

    public void setPlatformVersion(String str) {
        ovpncliJNI.ClientAPI_Config_platformVersion_set(this.swigCPtr, this, str);
    }

    public String getPlatformVersion() {
        return ovpncliJNI.ClientAPI_Config_platformVersion_get(this.swigCPtr, this);
    }

    public void setServerOverride(String str) {
        ovpncliJNI.ClientAPI_Config_serverOverride_set(this.swigCPtr, this, str);
    }

    public String getServerOverride() {
        return ovpncliJNI.ClientAPI_Config_serverOverride_get(this.swigCPtr, this);
    }

    public void setPortOverride(String str) {
        ovpncliJNI.ClientAPI_Config_portOverride_set(this.swigCPtr, this, str);
    }

    public String getPortOverride() {
        return ovpncliJNI.ClientAPI_Config_portOverride_get(this.swigCPtr, this);
    }

    public void setProtoOverride(String str) {
        ovpncliJNI.ClientAPI_Config_protoOverride_set(this.swigCPtr, this, str);
    }

    public String getProtoOverride() {
        return ovpncliJNI.ClientAPI_Config_protoOverride_get(this.swigCPtr, this);
    }

    public void setIpv6(String str) {
        ovpncliJNI.ClientAPI_Config_ipv6_set(this.swigCPtr, this, str);
    }

    public String getIpv6() {
        return ovpncliJNI.ClientAPI_Config_ipv6_get(this.swigCPtr, this);
    }

    public void setConnTimeout(int i) {
        ovpncliJNI.ClientAPI_Config_connTimeout_set(this.swigCPtr, this, i);
    }

    public int getConnTimeout() {
        return ovpncliJNI.ClientAPI_Config_connTimeout_get(this.swigCPtr, this);
    }

    public void setTunPersist(boolean z) {
        ovpncliJNI.ClientAPI_Config_tunPersist_set(this.swigCPtr, this, z);
    }

    public boolean getTunPersist() {
        return ovpncliJNI.ClientAPI_Config_tunPersist_get(this.swigCPtr, this);
    }

    public void setGoogleDnsFallback(boolean z) {
        ovpncliJNI.ClientAPI_Config_googleDnsFallback_set(this.swigCPtr, this, z);
    }

    public boolean getGoogleDnsFallback() {
        return ovpncliJNI.ClientAPI_Config_googleDnsFallback_get(this.swigCPtr, this);
    }

    public void setSynchronousDnsLookup(boolean z) {
        ovpncliJNI.ClientAPI_Config_synchronousDnsLookup_set(this.swigCPtr, this, z);
    }

    public boolean getSynchronousDnsLookup() {
        return ovpncliJNI.ClientAPI_Config_synchronousDnsLookup_get(this.swigCPtr, this);
    }

    public void setAutologinSessions(boolean z) {
        ovpncliJNI.ClientAPI_Config_autologinSessions_set(this.swigCPtr, this, z);
    }

    public boolean getAutologinSessions() {
        return ovpncliJNI.ClientAPI_Config_autologinSessions_get(this.swigCPtr, this);
    }

    public void setRetryOnAuthFailed(boolean z) {
        ovpncliJNI.ClientAPI_Config_retryOnAuthFailed_set(this.swigCPtr, this, z);
    }

    public boolean getRetryOnAuthFailed() {
        return ovpncliJNI.ClientAPI_Config_retryOnAuthFailed_get(this.swigCPtr, this);
    }

    public void setExternalPkiAlias(String str) {
        ovpncliJNI.ClientAPI_Config_externalPkiAlias_set(this.swigCPtr, this, str);
    }

    public String getExternalPkiAlias() {
        return ovpncliJNI.ClientAPI_Config_externalPkiAlias_get(this.swigCPtr, this);
    }

    public void setDisableClientCert(boolean z) {
        ovpncliJNI.ClientAPI_Config_disableClientCert_set(this.swigCPtr, this, z);
    }

    public boolean getDisableClientCert() {
        return ovpncliJNI.ClientAPI_Config_disableClientCert_get(this.swigCPtr, this);
    }

    public void setSslDebugLevel(int i) {
        ovpncliJNI.ClientAPI_Config_sslDebugLevel_set(this.swigCPtr, this, i);
    }

    public int getSslDebugLevel() {
        return ovpncliJNI.ClientAPI_Config_sslDebugLevel_get(this.swigCPtr, this);
    }

    public void setCompressionMode(String str) {
        ovpncliJNI.ClientAPI_Config_compressionMode_set(this.swigCPtr, this, str);
    }

    public String getCompressionMode() {
        return ovpncliJNI.ClientAPI_Config_compressionMode_get(this.swigCPtr, this);
    }

    public void setPrivateKeyPassword(String str) {
        ovpncliJNI.ClientAPI_Config_privateKeyPassword_set(this.swigCPtr, this, str);
    }

    public String getPrivateKeyPassword() {
        return ovpncliJNI.ClientAPI_Config_privateKeyPassword_get(this.swigCPtr, this);
    }

    public void setDefaultKeyDirection(int i) {
        ovpncliJNI.ClientAPI_Config_defaultKeyDirection_set(this.swigCPtr, this, i);
    }

    public int getDefaultKeyDirection() {
        return ovpncliJNI.ClientAPI_Config_defaultKeyDirection_get(this.swigCPtr, this);
    }

    public void setForceAesCbcCiphersuites(boolean z) {
        ovpncliJNI.ClientAPI_Config_forceAesCbcCiphersuites_set(this.swigCPtr, this, z);
    }

    public boolean getForceAesCbcCiphersuites() {
        return ovpncliJNI.ClientAPI_Config_forceAesCbcCiphersuites_get(this.swigCPtr, this);
    }

    public void setTlsVersionMinOverride(String str) {
        ovpncliJNI.ClientAPI_Config_tlsVersionMinOverride_set(this.swigCPtr, this, str);
    }

    public String getTlsVersionMinOverride() {
        return ovpncliJNI.ClientAPI_Config_tlsVersionMinOverride_get(this.swigCPtr, this);
    }

    public void setTlsCertProfileOverride(String str) {
        ovpncliJNI.ClientAPI_Config_tlsCertProfileOverride_set(this.swigCPtr, this, str);
    }

    public String getTlsCertProfileOverride() {
        return ovpncliJNI.ClientAPI_Config_tlsCertProfileOverride_get(this.swigCPtr, this);
    }

    public void setPeerInfo(SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t sWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t) {
        ovpncliJNI.ClientAPI_Config_peerInfo_set(this.swigCPtr, this, SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t.getCPtr(sWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t));
    }

    public SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t getPeerInfo() {
        long ClientAPI_Config_peerInfo_get = ovpncliJNI.ClientAPI_Config_peerInfo_get(this.swigCPtr, this);
        if (ClientAPI_Config_peerInfo_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_std__vectorT_openvpn__ClientAPI__KeyValue_t(ClientAPI_Config_peerInfo_get, false);
    }

    public void setProxyHost(String str) {
        ovpncliJNI.ClientAPI_Config_proxyHost_set(this.swigCPtr, this, str);
    }

    public String getProxyHost() {
        return ovpncliJNI.ClientAPI_Config_proxyHost_get(this.swigCPtr, this);
    }

    public void setProxyPort(String str) {
        ovpncliJNI.ClientAPI_Config_proxyPort_set(this.swigCPtr, this, str);
    }

    public String getProxyPort() {
        return ovpncliJNI.ClientAPI_Config_proxyPort_get(this.swigCPtr, this);
    }

    public void setProxyUsername(String str) {
        ovpncliJNI.ClientAPI_Config_proxyUsername_set(this.swigCPtr, this, str);
    }

    public String getProxyUsername() {
        return ovpncliJNI.ClientAPI_Config_proxyUsername_get(this.swigCPtr, this);
    }

    public void setProxyPassword(String str) {
        ovpncliJNI.ClientAPI_Config_proxyPassword_set(this.swigCPtr, this, str);
    }

    public String getProxyPassword() {
        return ovpncliJNI.ClientAPI_Config_proxyPassword_get(this.swigCPtr, this);
    }

    public void setProxyAllowCleartextAuth(boolean z) {
        ovpncliJNI.ClientAPI_Config_proxyAllowCleartextAuth_set(this.swigCPtr, this, z);
    }

    public boolean getProxyAllowCleartextAuth() {
        return ovpncliJNI.ClientAPI_Config_proxyAllowCleartextAuth_get(this.swigCPtr, this);
    }

    public void setAltProxy(boolean z) {
        ovpncliJNI.ClientAPI_Config_altProxy_set(this.swigCPtr, this, z);
    }

    public boolean getAltProxy() {
        return ovpncliJNI.ClientAPI_Config_altProxy_get(this.swigCPtr, this);
    }

    public void setDco(boolean z) {
        ovpncliJNI.ClientAPI_Config_dco_set(this.swigCPtr, this, z);
    }

    public boolean getDco() {
        return ovpncliJNI.ClientAPI_Config_dco_get(this.swigCPtr, this);
    }

    public void setEcho(boolean z) {
        ovpncliJNI.ClientAPI_Config_echo_set(this.swigCPtr, this, z);
    }

    public boolean getEcho() {
        return ovpncliJNI.ClientAPI_Config_echo_get(this.swigCPtr, this);
    }

    public void setInfo(boolean z) {
        ovpncliJNI.ClientAPI_Config_info_set(this.swigCPtr, this, z);
    }

    public boolean getInfo() {
        return ovpncliJNI.ClientAPI_Config_info_get(this.swigCPtr, this);
    }

    public void setAllowLocalLanAccess(boolean z) {
        ovpncliJNI.ClientAPI_Config_allowLocalLanAccess_set(this.swigCPtr, this, z);
    }

    public boolean getAllowLocalLanAccess() {
        return ovpncliJNI.ClientAPI_Config_allowLocalLanAccess_get(this.swigCPtr, this);
    }

    public void setClockTickMS(long j) {
        ovpncliJNI.ClientAPI_Config_clockTickMS_set(this.swigCPtr, this, j);
    }

    public long getClockTickMS() {
        return ovpncliJNI.ClientAPI_Config_clockTickMS_get(this.swigCPtr, this);
    }

    public void setGremlinConfig(String str) {
        ovpncliJNI.ClientAPI_Config_gremlinConfig_set(this.swigCPtr, this, str);
    }

    public String getGremlinConfig() {
        return ovpncliJNI.ClientAPI_Config_gremlinConfig_get(this.swigCPtr, this);
    }

    public void setWintun(boolean z) {
        ovpncliJNI.ClientAPI_Config_wintun_set(this.swigCPtr, this, z);
    }

    public boolean getWintun() {
        return ovpncliJNI.ClientAPI_Config_wintun_get(this.swigCPtr, this);
    }

    public ClientAPI_Config() {
        this(ovpncliJNI.new_ClientAPI_Config(), true);
    }
}
