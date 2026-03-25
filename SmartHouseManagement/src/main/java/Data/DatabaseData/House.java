package Data.DatabaseData;

public class House {
    private final Integer id;
    private final String address;
    private Integer nrRooms;

    public House(Integer id, String address, Integer nrRooms) {
        this.id = id;
        this.address = address;
        this.nrRooms = nrRooms;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Integer getNrRooms() {
        return nrRooms;
    }
}
