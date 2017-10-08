package com.kcht.parking.charge.datastructure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import com.kcht.parking.charge.ParkingLot;
import com.kcht.parking.charge.timeline.Period;

/**
 * Created by olinchy on 04/10/2017.
 */
@XmlRootElement(name = "chargeDetail")
public class ParkingLotDefinition {
    private ParkingLotDefinition() {
    }

    private static ParkingLotDefinition self;

    static {
        try {
            self = marshall();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @XmlElement
    private List<Place> place;
    @XmlAttribute
    private String day;
    @XmlAttribute
    private String night;

    private static ParkingLotDefinition marshall() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ParkingLotDefinition.class);
        Unmarshaller u = jc.createUnmarshaller();
        return (ParkingLotDefinition) u.unmarshal(ParkingLotDefinition.class.getResourceAsStream("/charge_detail.xml"));
    }

    public static ParkingLotDefinition getInstance() {
        return self;
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

    public ParkingLot decideParkingLot(final Places placeType, final Levels level) {

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setDayShift(new Period(day));
        parkingLot.setNightShift(new Period(night));

        Place target = this.place.stream().filter(
                place1 -> place1.equals(Place.defaultPlace(placeType.name()))).findFirst().get();

        return target.decorator(level).decor(parkingLot);
    }
}
