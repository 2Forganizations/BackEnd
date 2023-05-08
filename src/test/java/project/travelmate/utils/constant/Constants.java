package project.travelmate.utils.constant;

import project.travelmate.domain.enums.AuthProvider;
import project.travelmate.domain.enums.Category;
import project.travelmate.domain.enums.Gender;

import java.time.LocalDateTime;

public class Constants {

    public static final String FILE_PATH = "src/test/java/resources/test_image.jpg";

    public static final String SOCIAL_ID = "Test Social Id";
    public static final String EMAIL = "Test Email";
    public static final String NAME = "Test Name";
    public static final Integer MANNER_TEMPERATURE = 36;
    public static final String INTRO = "Test Intro";
    public static final Gender GENDER = Gender.MALE;
    public static final AuthProvider AUTH_PROVIDER = AuthProvider.KAKAO;

    public static final String TITLE = "Test Title";
    public static final String CONTENT = "Test Content";
    public static final Category CATEGORY = Category.EAT;
    public static final LocalDateTime START_DATE = LocalDateTime.now();
    public static final LocalDateTime END_DATE = LocalDateTime.now();
    public static final Integer MINIMUM_AGE = 10;
    public static final Integer MAXIMUM_AGE = 20;

    public static final String NATION = "Test Nation";
    public static final String CITY = "Test City";
    public static final String DETAIL = "Test Detail";
    public static final String LATITUDE = "Test Latitude";
    public static final String LONGITUDE = "Test Longitude";

    public static final Integer RECRUIT_MAN_NUMBER = 5;
    public static final Integer RECRUIT_WOMAN_NUMBER = 5;
    public static final Integer RECRUIT_ETC_NUMBER = 5;

    public static final Integer CURRENT_RECRUIT_MEMBER = 15;

}