package ru.ulmc.parserbooking;


public class Hotel {
    public String name;
    public String rate;
    public String address;
    public String link;
    public String numbersOfRoom;
    public String type;
    public String metroStation;

    public Hotel(String name, String rate, String address, String link, String numbersOfRoom, String type, String metroStation) {
        this.address = address;
        this.link = link;
        this.name = name;
        this.rate = rate;
        this.numbersOfRoom = numbersOfRoom;
        this.type = type;
        this.metroStation = metroStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNumbersOfRoom() {
        return numbersOfRoom;
    }

    public void setNumbersOfRoom(String numbersOfRoom) {
        this.numbersOfRoom = numbersOfRoom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                ", address='" + address + '\'' +
                ", link='" + link + '\'' +
                ", numbersOfRoom='" + numbersOfRoom + '\'' +
                ", type='" + type + '\'' + ", metro='" + metroStation + '\''+
                '}';
    }
}
