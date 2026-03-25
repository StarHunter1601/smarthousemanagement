package Data.DatabaseData;

public class Room {
    private final Integer id;
    private final String roomType;

    public Room(Integer id, String roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public Integer getId() {
        return id;
    }

    public String getRoomType() {
        return roomType;
    }
}
