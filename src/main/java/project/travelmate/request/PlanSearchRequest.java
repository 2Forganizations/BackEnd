package project.travelmate.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.travelmate.domain.enums.Category;
import project.travelmate.domain.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlanSearchRequest {
    private String title;
    private Gender gender;
    private Category category;
    private Integer minAge;
    private Integer maxAge;
}
