package Data;

import Data.DatabaseData.Device;

import java.util.ArrayList;
import java.util.List;

public class RealRoom {
    private final Integer id;
    private final String roomType;
    private List<Device> deviceList;

    public RealRoom(Integer id, String roomType) {
        this.id = id;
        this.roomType = roomType;
        deviceList = new ArrayList<Device>();
    }
    public void addDevices(Device device) {
        this.deviceList.add(device);
    }
    public void addDevices(List<Device> deviceList) {
        this.deviceList.addAll(deviceList);
    }

    public Integer getId() {
        return id;
    }

    public String getRoomType() {
        return roomType;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }
}
