package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public final String getRoomNumber() {
        return this.roomNumber;

    }

    public final Double getRoomPrice() {
        return this.price;

    }

    public final RoomType getRoomType() {
        return this.enumeration;

    }

    public final boolean isFree() {
        return false;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        Room room = (Room) obj;
        return Objects.equals(this.roomNumber, room.roomNumber);

    }

    @Override
    public int hashCode() {
        return roomNumber != null ? roomNumber.hashCode() : 0;

    }

    @Override
    public String toString() {
        return "(Room Number:" + this.roomNumber + ")" +
                "(Price: " + this.price + ")" +
                "(Room Type: " + this.enumeration + ")";
    }

}
