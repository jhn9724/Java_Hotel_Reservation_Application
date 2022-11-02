package service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {

    private Set<IRoom> setOfRoom = new HashSet<>();
    private Set<Reservation> setOfReservation = new HashSet<>();
    private static ReservationService srefResrvationService = new ReservationService();

    public void addRoom(IRoom room) {
        privateModifierExample(room);

    }

    private void privateModifierExample(IRoom room) {
        setOfRoom.add(room);

    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : setOfRoom) {
            if (room.getRoomNumber() == (roomId)) {
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        setOfReservation.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> roomsAvailable = new HashSet<>(setOfRoom);
        if (setOfReservation.size() != 0) {
            for (IRoom room : setOfRoom) {
                for (Reservation reservation : setOfReservation) {
                    if (room.getRoomNumber() == (reservation.getRoom().getRoomNumber())) {
                        if ((reservation.getCheckInDate().before(checkOutDate)
                                && reservation.getCheckOutDate().after(checkInDate))) {
                            roomsAvailable.remove(room);
                        }
                    }
                }
            }
        }
        return roomsAvailable;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return setOfReservation;

    }

    public void printAllReservation() {
        defaultMethodModifierExample();

    }

    void defaultMethodModifierExample() {
        for (Reservation reservation : setOfReservation) {
            System.out.println(reservation);
        }
    }

    public static ReservationService getsrefReservationService() {
        return srefResrvationService;
    }

    public Set<IRoom> getRooms() {
        return setOfRoom;
    }

}
