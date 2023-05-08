package project.travelmate.utils.constant;

import org.junit.jupiter.api.BeforeEach;
import project.travelmate.domain.*;

import static project.travelmate.utils.constant.Constants.*;

public class SetUpData {

    public ProfileImage profileImage;
    public User user;
    public PlanImage planImage;
    public Plan plan;

    @BeforeEach
    public void setup() {
        profileImage = ProfileImage.builder()
                .filePath(FILE_PATH)
                .build();

        user = User.builder()
                .id(SOCIAL_ID)
                .email(EMAIL)
                .name(NAME)
                .mannerTemperature(MANNER_TEMPERATURE)
                .intro(INTRO)
                .gender(GENDER)
                .authProvider(AUTH_PROVIDER)
                .profileImage(profileImage)
                .build();

        planImage = PlanImage.builder()
                .filePath(FILE_PATH)
                .build();

        plan = Plan.builder()
                .title(TITLE)
                .content(CONTENT)
                .category(CATEGORY)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .minimumAge(MINIMUM_AGE)
                .maximumAge(MAXIMUM_AGE)
                .address(createAddress())
                .requireRecruitMember(getRequireRecruitMember())
                .currentRecruitMember(CURRENT_RECRUIT_MEMBER)
                .planImage(planImage)
                .user(user)
                .build();
    }

    private Address createAddress() {
        return new Address(NATION, CITY, DETAIL, LATITUDE, LONGITUDE);
    }

    private String getRequireRecruitMember() {
        return RECRUIT_MAN_NUMBER + "/" + RECRUIT_WOMAN_NUMBER + "/" + RECRUIT_ETC_NUMBER;
    }

}