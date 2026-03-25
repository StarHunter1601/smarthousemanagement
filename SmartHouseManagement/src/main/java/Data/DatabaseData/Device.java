package Data.DatabaseData;

public class Device {
    private final Integer id;
    private final Integer type;
    private final String name;
    private Integer roomId;

    public Device(Integer id, Integer type, String name, Integer roomId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
