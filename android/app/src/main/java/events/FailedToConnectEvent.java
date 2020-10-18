package events;

public class FailedToConnectEvent {

    private boolean isFailed ;

    public FailedToConnectEvent(boolean isFailed) {
        this.isFailed = isFailed;
    }
}
