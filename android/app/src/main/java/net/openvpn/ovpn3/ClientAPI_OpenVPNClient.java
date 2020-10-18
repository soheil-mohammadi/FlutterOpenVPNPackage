package net.openvpn.ovpn3;

public class ClientAPI_OpenVPNClient extends ClientAPI_TunBuilderBase {
    private transient long swigCPtr;

    protected ClientAPI_OpenVPNClient(long j, boolean z) {
        super(ovpncliJNI.ClientAPI_OpenVPNClient_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_OpenVPNClient clientAPI_OpenVPNClient) {
        if (clientAPI_OpenVPNClient == null) {
            return 0;
        }
        return clientAPI_OpenVPNClient.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_OpenVPNClient(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
        super.delete();
    }

    /* access modifiers changed from: protected */
    public void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        ovpncliJNI.ClientAPI_OpenVPNClient_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        ovpncliJNI.ClientAPI_OpenVPNClient_change_ownership(this, this.swigCPtr, true);
    }

    public ClientAPI_OpenVPNClient() {
        this(ovpncliJNI.new_ClientAPI_OpenVPNClient(), true);
        ovpncliJNI.ClientAPI_OpenVPNClient_director_connect(this, this.swigCPtr, true, true);
    }

    public static void init_process() {
        ovpncliJNI.ClientAPI_OpenVPNClient_init_process();
    }

    public static void uninit_process() {
        ovpncliJNI.ClientAPI_OpenVPNClient_uninit_process();
    }

    public static ClientAPI_MergeConfig merge_config_static(String str, boolean z) {
        return new ClientAPI_MergeConfig(ovpncliJNI.ClientAPI_OpenVPNClient_merge_config_static(str, z), true);
    }

    public static ClientAPI_MergeConfig merge_config_string_static(String str) {
        return new ClientAPI_MergeConfig(ovpncliJNI.ClientAPI_OpenVPNClient_merge_config_string_static(str), true);
    }

    public static ClientAPI_EvalConfig eval_config_static(ClientAPI_Config clientAPI_Config) {
        return new ClientAPI_EvalConfig(ovpncliJNI.ClientAPI_OpenVPNClient_eval_config_static(ClientAPI_Config.getCPtr(clientAPI_Config), clientAPI_Config), true);
    }

    public static int max_profile_size() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_max_profile_size();
    }

    public static boolean parse_dynamic_challenge(String str, ClientAPI_DynamicChallenge clientAPI_DynamicChallenge) {
        return ovpncliJNI.ClientAPI_OpenVPNClient_parse_dynamic_challenge(str, ClientAPI_DynamicChallenge.getCPtr(clientAPI_DynamicChallenge), clientAPI_DynamicChallenge);
    }

    public ClientAPI_EvalConfig eval_config(ClientAPI_Config clientAPI_Config) {
        return new ClientAPI_EvalConfig(ovpncliJNI.ClientAPI_OpenVPNClient_eval_config(this.swigCPtr, this, ClientAPI_Config.getCPtr(clientAPI_Config), clientAPI_Config), true);
    }

    public ClientAPI_Status provide_creds(ClientAPI_ProvideCreds clientAPI_ProvideCreds) {
        return new ClientAPI_Status(ovpncliJNI.ClientAPI_OpenVPNClient_provide_creds(this.swigCPtr, this, ClientAPI_ProvideCreds.getCPtr(clientAPI_ProvideCreds), clientAPI_ProvideCreds), true);
    }

    public boolean socket_protect(int i, String str, boolean z) {
        return getClass() == ClientAPI_OpenVPNClient.class ? ovpncliJNI.ClientAPI_OpenVPNClient_socket_protect(this.swigCPtr, this, i, str, z) : ovpncliJNI.ClientAPI_OpenVPNClient_socket_protectSwigExplicitClientAPI_OpenVPNClient(this.swigCPtr, this, i, str, z);
    }

    public ClientAPI_Status connect() {
        return new ClientAPI_Status(ovpncliJNI.ClientAPI_OpenVPNClient_connect(this.swigCPtr, this), true);
    }

    public ClientAPI_ConnectionInfo connection_info() {
        return new ClientAPI_ConnectionInfo(ovpncliJNI.ClientAPI_OpenVPNClient_connection_info(this.swigCPtr, this), true);
    }

    public boolean session_token(ClientAPI_SessionToken clientAPI_SessionToken) {
        return ovpncliJNI.ClientAPI_OpenVPNClient_session_token(this.swigCPtr, this, ClientAPI_SessionToken.getCPtr(clientAPI_SessionToken), clientAPI_SessionToken);
    }

    public void stop() {
        ovpncliJNI.ClientAPI_OpenVPNClient_stop(this.swigCPtr, this);
    }

    public void pause(String str) {
        ovpncliJNI.ClientAPI_OpenVPNClient_pause(this.swigCPtr, this, str);
    }

    public void resume() {
        ovpncliJNI.ClientAPI_OpenVPNClient_resume(this.swigCPtr, this);
    }

    public void reconnect(int i) {
        ovpncliJNI.ClientAPI_OpenVPNClient_reconnect(this.swigCPtr, this, i);
    }

    public boolean pause_on_connection_timeout() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_pause_on_connection_timeout(this.swigCPtr, this);
    }

    public static int stats_n() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_stats_n();
    }

    public static String stats_name(int i) {
        return ovpncliJNI.ClientAPI_OpenVPNClient_stats_name(i);
    }

    public long stats_value(int i) {
        return ovpncliJNI.ClientAPI_OpenVPNClient_stats_value(this.swigCPtr, this, i);
    }

    public ClientAPI_LLVector stats_bundle() {
        return new ClientAPI_LLVector(ovpncliJNI.ClientAPI_OpenVPNClient_stats_bundle(this.swigCPtr, this), true);
    }

    public ClientAPI_InterfaceStats tun_stats() {
        return new ClientAPI_InterfaceStats(ovpncliJNI.ClientAPI_OpenVPNClient_tun_stats(this.swigCPtr, this), true);
    }

    public ClientAPI_TransportStats transport_stats() {
        return new ClientAPI_TransportStats(ovpncliJNI.ClientAPI_OpenVPNClient_transport_stats(this.swigCPtr, this), true);
    }

    public void post_cc_msg(String str) {
        ovpncliJNI.ClientAPI_OpenVPNClient_post_cc_msg(this.swigCPtr, this, str);
    }

    public void event(ClientAPI_Event clientAPI_Event) {
        ovpncliJNI.ClientAPI_OpenVPNClient_event(this.swigCPtr, this, ClientAPI_Event.getCPtr(clientAPI_Event), clientAPI_Event);
    }

    public void log(ClientAPI_LogInfo clientAPI_LogInfo) {
        ovpncliJNI.ClientAPI_OpenVPNClient_log(this.swigCPtr, this, ClientAPI_LogInfo.getCPtr(clientAPI_LogInfo), clientAPI_LogInfo);
    }

    public void external_pki_cert_request(ClientAPI_ExternalPKICertRequest clientAPI_ExternalPKICertRequest) {
        ovpncliJNI.ClientAPI_OpenVPNClient_external_pki_cert_request(this.swigCPtr, this, ClientAPI_ExternalPKICertRequest.getCPtr(clientAPI_ExternalPKICertRequest), clientAPI_ExternalPKICertRequest);
    }

    public void external_pki_sign_request(ClientAPI_ExternalPKISignRequest clientAPI_ExternalPKISignRequest) {
        ovpncliJNI.ClientAPI_OpenVPNClient_external_pki_sign_request(this.swigCPtr, this, ClientAPI_ExternalPKISignRequest.getCPtr(clientAPI_ExternalPKISignRequest), clientAPI_ExternalPKISignRequest);
    }

    public boolean remote_override_enabled() {
        return getClass() == ClientAPI_OpenVPNClient.class ? ovpncliJNI.ClientAPI_OpenVPNClient_remote_override_enabled(this.swigCPtr, this) : ovpncliJNI.ClientAPI_OpenVPNClient_remote_override_enabledSwigExplicitClientAPI_OpenVPNClient(this.swigCPtr, this);
    }

    public void remote_override(ClientAPI_RemoteOverride clientAPI_RemoteOverride) {
        if (getClass() == ClientAPI_OpenVPNClient.class) {
            ovpncliJNI.ClientAPI_OpenVPNClient_remote_override(this.swigCPtr, this, ClientAPI_RemoteOverride.getCPtr(clientAPI_RemoteOverride), clientAPI_RemoteOverride);
            return;
        }
        ovpncliJNI.ClientAPI_OpenVPNClient_clock_tickSwigExplicitClientAPI_OpenVPNClient(this.swigCPtr, this, ClientAPI_RemoteOverride.getCPtr(clientAPI_RemoteOverride), clientAPI_RemoteOverride);
    }

    public void clock_tick() {
        if (getClass() == ClientAPI_OpenVPNClient.class) {
            ovpncliJNI.ClientAPI_OpenVPNClient_clock_tick(this.swigCPtr, this);
        } else {
            ovpncliJNI.ClientAPI_OpenVPNClient_clock_tickSwigExplicitClientAPI_OpenVPNClient(this.swigCPtr, this);
        }
    }

    public static String crypto_self_test() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_crypto_self_test();
    }

    public static int app_expire() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_app_expire();
    }

    public static String platform() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_platform();
    }

    public static String copyright() {
        return ovpncliJNI.ClientAPI_OpenVPNClient_copyright();
    }
}
