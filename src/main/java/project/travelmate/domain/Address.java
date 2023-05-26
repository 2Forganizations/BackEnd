package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.request.PlanCreateRequest;

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

    public Address(PlanCreateRequest planCreateRequest) {
        this.nation = planCreateRequest.getNation();
        this.city = planCreateRequest.getCity();
        this.detail = planCreateRequest.getDetail();
        this.latitude = planCreateRequest.getLatitude();
        this.longitude = planCreateRequest.getLongitude();
    }
}