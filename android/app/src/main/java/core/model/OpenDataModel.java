package core.model;

public class OpenDataModel {

    private String config ;
    private String userName ;
    private String userPassword ;


    public OpenDataModel(String config , String userName , String userPassword) {
        this.config = config;
        this.userName = userName;
        this.userPassword = userPassword;
    }


    public String getConfig() {
        return config;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

}
