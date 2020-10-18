package events;

import core.ConnectionType;
import core.StateType;

public class ConnectionStateEvent {

    private StateType stateType ;
    private ConnectionType connectionType;


    public ConnectionStateEvent(StateType stateType, ConnectionType connectionType) {
        this.stateType = stateType;
        this.connectionType = connectionType;
    }

    public StateType getStateType() {
        return stateType;
    }


    public ConnectionType getConnectionType() {
        return connectionType;
    }
}
