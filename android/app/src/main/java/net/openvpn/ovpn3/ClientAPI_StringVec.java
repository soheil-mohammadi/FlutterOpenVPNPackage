package net.openvpn.ovpn3;

import java.util.AbstractList;
import java.util.RandomAccess;

public class ClientAPI_StringVec extends AbstractList<String> implements RandomAccess {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    protected ClientAPI_StringVec(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(ClientAPI_StringVec clientAPI_StringVec) {
        if (clientAPI_StringVec == null) {
            return 0;
        }
        return clientAPI_StringVec.swigCPtr;
    }

    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ovpncliJNI.delete_ClientAPI_StringVec(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public ClientAPI_StringVec(String[] strArr) {
        this();
        reserve((long) strArr.length);
        for (String add : strArr) {
            add(add);
        }
    }

    public ClientAPI_StringVec(Iterable<String> iterable) {
        this();
        for (String add : iterable) {
            add(add);
        }
    }

    public String get(int i) {
        return doGet(i);
    }

    public String set(int i, String str) {
        return doSet(i, str);
    }

    public boolean add(String str) {
        this.modCount++;
        doAdd(str);
        return true;
    }

    public void add(int i, String str) {
        this.modCount++;
        doAdd(i, str);
    }

    public String remove(int i) {
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

    public ClientAPI_StringVec() {
        this(ovpncliJNI.new_ClientAPI_StringVec__SWIG_0(), true);
    }

    public ClientAPI_StringVec(ClientAPI_StringVec clientAPI_StringVec) {
        this(ovpncliJNI.new_ClientAPI_StringVec__SWIG_1(getCPtr(clientAPI_StringVec), clientAPI_StringVec), true);
    }

    public long capacity() {
        return ovpncliJNI.ClientAPI_StringVec_capacity(this.swigCPtr, this);
    }

    public void reserve(long j) {
        ovpncliJNI.ClientAPI_StringVec_reserve(this.swigCPtr, this, j);
    }

    public boolean isEmpty() {
        return ovpncliJNI.ClientAPI_StringVec_isEmpty(this.swigCPtr, this);
    }

    public void clear() {
        ovpncliJNI.ClientAPI_StringVec_clear(this.swigCPtr, this);
    }

    public ClientAPI_StringVec(int i, String str) {
        this(ovpncliJNI.new_ClientAPI_StringVec__SWIG_2(i, str), true);
    }

    private int doSize() {
        return ovpncliJNI.ClientAPI_StringVec_doSize(this.swigCPtr, this);
    }

    private void doAdd(String str) {
        ovpncliJNI.ClientAPI_StringVec_doAdd__SWIG_0(this.swigCPtr, this, str);
    }

    private void doAdd(int i, String str) {
        ovpncliJNI.ClientAPI_StringVec_doAdd__SWIG_1(this.swigCPtr, this, i, str);
    }

    private String doRemove(int i) {
        return ovpncliJNI.ClientAPI_StringVec_doRemove(this.swigCPtr, this, i);
    }

    private String doGet(int i) {
        return ovpncliJNI.ClientAPI_StringVec_doGet(this.swigCPtr, this, i);
    }

    private String doSet(int i, String str) {
        return ovpncliJNI.ClientAPI_StringVec_doSet(this.swigCPtr, this, i, str);
    }

    private void doRemoveRange(int i, int i2) {
        ovpncliJNI.ClientAPI_StringVec_doRemoveRange(this.swigCPtr, this, i, i2);
    }
}
