package project.travelmate.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileUpdateRequest {
    private String name;
    private String intro;
}
