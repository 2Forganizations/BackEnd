package project.travelmate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.travelmate.config.WebConfig;
import project.travelmate.domain.ProfileImage;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.Gender;
import project.travelmate.request.AuthInfo;
import project.travelmate.response.ProfileResponse;
import project.travelmate.service.ProfileService;
import project.travelmate.util.JwtUtil;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ProfileController.class, excludeAutoConfiguration = WebConfig.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProfileController profileController;

    @MockBean
    private ProfileService profileService;
    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setAuthUser() {
        AuthInfo authInfo = new AuthInfo("id", "KAKAO", "123456");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authInfo, null, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


    @Nested
    class GetProfile {
        @Test
        @Disabled
        void success() throws Exception {


            mockMvc.perform(MockMvcRequestBuilders.get("/api/member/get"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }



}