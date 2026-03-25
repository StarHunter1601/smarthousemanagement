package Data;

import Data.DatabaseData.Room;

import java.util.ArrayList;
import java.util.List;

public class RealHouse {
    private final Integer id;
    private final String address;
    private Integer nrRooms;
    private List<RealRoom> rooms;

    public RealHouse(Integer id, String address, Integer nrRooms) {
        this.id = id;
        this.address = address;
        this.nrRooms = nrRooms;
        rooms = new ArrayList<RealRoom>();
    }
    public void addRoom(RealRoom room) {
        this.rooms.add(room);
    }
    public void addRooms(List<RealRoom> rooms) {
        this.rooms.addAll(rooms);
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

    public List<RealRoom> getRooms() {
        return rooms;
    }
}
