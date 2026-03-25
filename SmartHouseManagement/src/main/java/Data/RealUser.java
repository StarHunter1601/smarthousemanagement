package Data;

import Data.DatabaseData.User;

public class RealUser {
    private final Integer id;
    private String name;
    private String password;
    private RealHouse house;

    public RealUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = "not found";
        this.house = null;
    }

    public RealUser(Integer id, String name, String password,RealHouse house) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.house = house;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public RealHouse getHouse() {
        return house;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHouse(RealHouse house) {
        this.house = house;
    }
}
