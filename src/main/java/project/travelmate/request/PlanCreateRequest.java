package project.travelmate.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.enums.Category;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanCreateRequest {

    private String title;
    private String content;
    private Category category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer minAge;
    private Integer maxAge;

    private String nation;
    private String city;
    private String detail;
    private String longitude;
    private String latitude;

    private Integer recruitManNumber;
    private Integer recruitWomanNumber;
    private Integer recruitEtcNumber;
}
