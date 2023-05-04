package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlanImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_image_id")
    private Long id;

    private String filePath;

    @Builder
    public PlanImage(Long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

}