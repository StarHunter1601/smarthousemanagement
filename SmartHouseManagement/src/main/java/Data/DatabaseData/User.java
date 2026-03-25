package Data.DatabaseData;

public class User {
    private final Integer id;
    private String name;
    private final Integer passwordId;//Should be changed to password
    private Integer houseId;

    public User(Integer id, String name, Integer passwordId, Integer houseId) {
        this.id = id;
        this.name = name;
        this.passwordId = passwordId;
        this.houseId = houseId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPasswordId() {
        return passwordId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }
}
