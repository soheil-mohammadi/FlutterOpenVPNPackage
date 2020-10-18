package core;


import java.io.Serializable;
import androidx.room.TypeConverter;

public enum ConnectionType implements Serializable {

    OPENVPN_CONNECTION (100);

    private int code;

    ConnectionType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


    @TypeConverter
    public static ConnectionType getType(Integer numeral){
        for(ConnectionType ds : values()){
            if(ds.code == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getTypeInt(ConnectionType status){

        if(status != null)
            return status.code;

        return  null;
    }

}
