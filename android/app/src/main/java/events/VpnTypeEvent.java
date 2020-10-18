package events;

import core.ConnectionType;

public class VpnTypeEvent {

    private ConnectionType connectionType;

    public VpnTypeEvent(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }
}
