package model;
//A free room using the room class
public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);

    }

    @Override
    public String toString() {
        return "Room Number: " + super.getRoomNumber() +
        " Room Price: " + super.getRoomPrice() +
        " Room Type: " + super.getRoomType()+ 
        "Free Room:" + super.isFree();
    }

}
