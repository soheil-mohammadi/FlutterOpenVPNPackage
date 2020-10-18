package de.blinkt.openvpn.core;

import java.util.UUID;


public class PasswordCache {
    public static final int PCKS12ORCERTPASSWORD = 2;
    public static final int AUTHPASSWORD = 3;
    private static PasswordCache mInstance;
    final private UUID mUuid;
    private String mKeyOrPkcs12Password;
    private String mAuthPassword;

    private PasswordCache(UUID uuid) {
        mUuid = uuid;
    }

    public static PasswordCache getInstance(UUID uuid) {
        if (mInstance == null || !mInstance.mUuid.equals(uuid)) {
            mInstance = new PasswordCache(uuid);
        }
        return mInstance;
    }

    public static String getPKCS12orCertificatePassword(UUID uuid, boolean resetPw) {
        String pwcopy = getInstance(uuid).mKeyOrPkcs12Password;
        if (resetPw)
            getInstance(uuid).mKeyOrPkcs12Password = null;
        return pwcopy;
    }


    public static String getAuthPassword(UUID uuid, boolean resetPW) {
        String pwcopy = getInstance(uuid).mAuthPassword;
        if (resetPW)
            getInstance(uuid).mAuthPassword = null;
        return pwcopy;
    }

    public static void setCachedPassword(String uuid, int type, String password) {
        PasswordCache instance = getInstance(UUID.fromString(uuid));
        switch (type) {
            case PCKS12ORCERTPASSWORD:
                instance.mKeyOrPkcs12Password = password;
                break;
            case AUTHPASSWORD:
                instance.mAuthPassword = password;
                break;
        }
    }


}
