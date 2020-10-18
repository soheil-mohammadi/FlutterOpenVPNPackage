package net.openvpn.ovpn3;

import java.util.AbstractList;
import java.util.RandomAccess;

public class ClientAPI_ServerEntryVector extends AbstractList<ClientAPI_ServerEntry> implements RandomAccess {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_ServerEntryVector(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_ServerEntryVector clientAPI_ServerEntryVector) {
        if (clientAPI_ServerEntryVector == null) {
            return 0;
        }
        return clientAPI_ServerEntryVector.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_ServerEntryVector(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public ClientAPI_ServerEntryVector(ClientAPI_ServerEntry[] clientAPI_ServerEntryArr) {
        this();
        reserve((long) clientAPI_ServerEntryArr.length);
        for (ClientAPI_ServerEntry add : clientAPI_ServerEntryArr) {
            add(add);
        }
    }

    public ClientAPI_ServerEntryVector(Iterable<ClientAPI_ServerEntry> iterable) {
        this();
        for (ClientAPI_ServerEntry add : iterable) {
            add(add);
        }
    }

    public ClientAPI_ServerEntry get(int i) {
        return doGet(i);
    }

    public ClientAPI_ServerEntry set(int i, ClientAPI_ServerEntry clientAPI_ServerEntry) {
        return doSet(i, clientAPI_ServerEntry);
    }

    public boolean add(ClientAPI_ServerEntry clientAPI_ServerEntry) {
        this.modCount++;
        doAdd(clientAPI_ServerEntry);
        return true;
    }

    public void add(int i, ClientAPI_ServerEntry clientAPI_ServerEntry) {
        this.modCount++;
        doAdd(i, clientAPI_ServerEntry);
    }

    public ClientAPI_ServerEntry remove(int i) {
        this.modCount++;
        return doRemove(i);
    }

    public void removeRange(int i, int i2) {
        this.modCount++;
        doRemoveRange(i, i2);
    }

    public int size() {
        return doSize();
    }

    public ClientAPI_ServerEntryVector() {
        this(ovpncliJNI.new_ClientAPI_ServerEntryVector__SWIG_0(), true);
    }

    public ClientAPI_ServerEntryVector(ClientAPI_ServerEntryVector clientAPI_ServerEntryVector) {
        this(ovpncliJNI.new_ClientAPI_ServerEntryVector__SWIG_1(getCPtr(clientAPI_ServerEntryVector), clientAPI_ServerEntryVector), true);
    }

    public long capacity() {
        return ovpncliJNI.ClientAPI_ServerEntryVector_capacity(this.swigCPtr, this);
    }

    public void reserve(long j) {
        ovpncliJNI.ClientAPI_ServerEntryVector_reserve(this.swigCPtr, this, j);
    }

    public boolean isEmpty() {
        return ovpncliJNI.ClientAPI_ServerEntryVector_isEmpty(this.swigCPtr, this);
    }

    public void clear() {
        ovpncliJNI.ClientAPI_ServerEntryVector_clear(this.swigCPtr, this);
    }

    public ClientAPI_ServerEntryVector(int i, ClientAPI_ServerEntry clientAPI_ServerEntry) {
        this(ovpncliJNI.new_ClientAPI_ServerEntryVector__SWIG_2(i, ClientAPI_ServerEntry.getCPtr(clientAPI_ServerEntry), clientAPI_ServerEntry), true);
    }

    private int doSize() {
        return ovpncliJNI.ClientAPI_ServerEntryVector_doSize(this.swigCPtr, this);
    }

    private void doAdd(ClientAPI_ServerEntry clientAPI_ServerEntry) {
        ovpncliJNI.ClientAPI_ServerEntryVector_doAdd__SWIG_0(this.swigCPtr, this, ClientAPI_ServerEntry.getCPtr(clientAPI_ServerEntry), clientAPI_ServerEntry);
    }

    private void doAdd(int i, ClientAPI_ServerEntry clientAPI_ServerEntry) {
        ovpncliJNI.ClientAPI_ServerEntryVector_doAdd__SWIG_1(this.swigCPtr, this, i, ClientAPI_ServerEntry.getCPtr(clientAPI_ServerEntry), clientAPI_ServerEntry);
    }

    private ClientAPI_ServerEntry doRemove(int i) {
        return new ClientAPI_ServerEntry(ovpncliJNI.ClientAPI_ServerEntryVector_doRemove(this.swigCPtr, this, i), true);
    }

    private ClientAPI_ServerEntry doGet(int i) {
        return new ClientAPI_ServerEntry(ovpncliJNI.ClientAPI_ServerEntryVector_doGet(this.swigCPtr, this, i), false);
    }

    private ClientAPI_ServerEntry doSet(int i, ClientAPI_ServerEntry clientAPI_ServerEntry) {
        return new ClientAPI_ServerEntry(ovpncliJNI.ClientAPI_ServerEntryVector_doSet(this.swigCPtr, this, i, ClientAPI_ServerEntry.getCPtr(clientAPI_ServerEntry), clientAPI_ServerEntry), true);
    }

    private void doRemoveRange(int i, int i2) {
        ovpncliJNI.ClientAPI_ServerEntryVector_doRemoveRange(this.swigCPtr, this, i, i2);
    }
}
