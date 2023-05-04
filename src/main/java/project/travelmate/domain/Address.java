package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    private String nation;
    private String city;
    private String detail;

    private String latitude;
    private String longitude;

    public Address(String nation, String city, String detail, String latitude, String longitude) {
        this.nation = nation;
        this.city = city;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}