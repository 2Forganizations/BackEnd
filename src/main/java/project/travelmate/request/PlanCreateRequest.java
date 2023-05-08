package project.travelmate.request;
import lombok.Builder;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;
import project.travelmate.domain.enums.Category;

import java.time.LocalDateTime;

@Getter
public class PlanCreateRequest {

    private String title;
    private String content;
    private Category category;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private Integer minimumAge;
    private Integer maximumAge;
    private String nation;
    private String city;
    private String detail;
    private String latitude;
    private String longitude;
    private Integer recruitManNumber;
    private Integer recruitWomanNumber;
    private Integer recruitEtcNumber;
    private Integer currentRecruitMember;

    @Builder
    public PlanCreateRequest(String title, String content, Category category, LocalDateTime startDate, LocalDateTime endDate, Integer minimumAge, Integer maximumAge, String nation, String city, String detail, String latitude, String longitude, Integer recruitManNumber, Integer recruitWomanNumber, Integer recruitEtcNumber, Integer currentRecruitMember) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.nation = nation;
        this.city = city;
        this.detail = detail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recruitManNumber = recruitManNumber;
        this.recruitWomanNumber = recruitWomanNumber;
        this.recruitEtcNumber = recruitEtcNumber;
        this.currentRecruitMember = currentRecruitMember;
    }

}