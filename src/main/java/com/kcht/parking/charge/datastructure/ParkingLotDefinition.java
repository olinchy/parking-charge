package com.kcht.parking.charge.datastructure;

import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kcht.parking.charge.ParkingLot;
import com.kcht.parking.charge.timeline.Period;

@XmlRootElement(name = "chargeDetail")
public class ParkingLotDefinition {
    private ParkingLotDefinition() {

    }

    private static ParkingLotDefinition self;
    @XmlElement
    private List<Place> place;
    @XmlAttribute
    private String day;
    @XmlAttribute
    private String night;

    public static ParkingLotDefinition getInstance() {
        return getInstance("charge_detail.xml");
    }

    public static ParkingLotDefinition getInstance(String fileName) {
        if (self == null) {
            try {
                self = marshall(fileName);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return self;
    }

    private static ParkingLotDefinition marshall(String fileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ParkingLotDefinition.class);
        Unmarshaller u = jc.createUnmarshaller();
        return (ParkingLotDefinition) u.unmarshal(ParkingLotDefinition.class.getResourceAsStream("/" + fileName));
    }

    public static void setInstance(ParkingLotDefinition parkingLotDefinition) {
        self = parkingLotDefinition;
    }

    public static ParkingLotDefinition getEmptyInstance() {
        return new ParkingLotDefinition();
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public void setPlace(final List<Place> place) {
        this.place = place;
    }

    public void setNight(final String night) {
        this.night = night;
    }

    public ParkingLot decideParkingLot(final Places placeType) {
        return this.decideParkingLot(placeType, Levels.level_central);
    }

    public ParkingLot decideParkingLot(final Places placeType, final Levels level) {
        ParkingLot parkingLot = new ParkingLot(new Period(day), new Period(night));

        Place target = this.place.stream().filter(
                place1 -> place1.equals(Place.defaultPlace(placeType.name()))).findFirst().get();

        return target.decorator(level).decor(parkingLot);
    }
}
