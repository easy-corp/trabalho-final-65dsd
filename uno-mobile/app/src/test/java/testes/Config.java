package testes;

public class Config {

    public static Config instance;
    private String ip;

    private Config() {
        this.ip = "192.168.3.34:2000";
    }

    public synchronized static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    public String getIp() {
        return this.ip;
    }

}
