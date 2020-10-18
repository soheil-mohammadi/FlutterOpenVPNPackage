package net.openvpn.ovpn3;

import java.util.AbstractList;
import java.util.RandomAccess;

public class ClientAPI_LLVector extends AbstractList<Long> implements RandomAccess {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_LLVector(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_LLVector clientAPI_LLVector) {
        if (clientAPI_LLVector == null) {
            return 0;
        }
        return clientAPI_LLVector.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_LLVector(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public ClientAPI_LLVector(long[] jArr) {
        this();
        reserve((long) jArr.length);
        for (long valueOf : jArr) {
            add(Long.valueOf(valueOf));
        }
    }

    public ClientAPI_LLVector(Iterable<Long> iterable) {
        this();
        for (Long longValue : iterable) {
            add(Long.valueOf(longValue.longValue()));
        }
    }

    public Long get(int i) {
        return Long.valueOf(doGet(i));
    }

    public Long set(int i, Long l) {
        return Long.valueOf(doSet(i, l.longValue()));
    }

    public boolean add(Long l) {
        this.modCount++;
        doAdd(l.longValue());
        return true;
    }

    public void add(int i, Long l) {
        this.modCount++;
        doAdd(i, l.longValue());
    }

    public Long remove(int i) {
        this.modCount++;
        return Long.valueOf(doRemove(i));
    }

    /* access modifiers changed from: protected */
    public void removeRange(int i, int i2) {
        this.modCount++;
        doRemoveRange(i, i2);
    }

    public int size() {
        return doSize();
    }

    public ClientAPI_LLVector() {
        this(ovpncliJNI.new_ClientAPI_LLVector__SWIG_0(), true);
    }

    public ClientAPI_LLVector(ClientAPI_LLVector clientAPI_LLVector) {
        this(ovpncliJNI.new_ClientAPI_LLVector__SWIG_1(getCPtr(clientAPI_LLVector), clientAPI_LLVector), true);
    }

    public long capacity() {
        return ovpncliJNI.ClientAPI_LLVector_capacity(this.swigCPtr, this);
    }

    public void reserve(long j) {
        ovpncliJNI.ClientAPI_LLVector_reserve(this.swigCPtr, this, j);
    }

    public boolean isEmpty() {
        return ovpncliJNI.ClientAPI_LLVector_isEmpty(this.swigCPtr, this);
    }

    public void clear() {
        ovpncliJNI.ClientAPI_LLVector_clear(this.swigCPtr, this);
    }

    public ClientAPI_LLVector(int i, long j) {
        this(ovpncliJNI.new_ClientAPI_LLVector__SWIG_2(i, j), true);
    }

    private int doSize() {
        return ovpncliJNI.ClientAPI_LLVector_doSize(this.swigCPtr, this);
    }

    private void doAdd(long j) {
        ovpncliJNI.ClientAPI_LLVector_doAdd__SWIG_0(this.swigCPtr, this, j);
    }

    private void doAdd(int i, long j) {
        ovpncliJNI.ClientAPI_LLVector_doAdd__SWIG_1(this.swigCPtr, this, i, j);
    }

    private long doRemove(int i) {
        return ovpncliJNI.ClientAPI_LLVector_doRemove(this.swigCPtr, this, i);
    }

    private long doGet(int i) {
        return ovpncliJNI.ClientAPI_LLVector_doGet(this.swigCPtr, this, i);
    }

    private long doSet(int i, long j) {
        return ovpncliJNI.ClientAPI_LLVector_doSet(this.swigCPtr, this, i, j);
    }

    private void doRemoveRange(int i, int i2) {
        ovpncliJNI.ClientAPI_LLVector_doRemoveRange(this.swigCPtr, this, i, i2);
    }
}
