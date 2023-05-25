package project.travelmate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.travelmate.WithMockCustomUser;
import project.travelmate.config.security.SecurityConfig;
import project.travelmate.domain.enums.Category;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;
import project.travelmate.service.PlanService;

import java.time.LocalDateTime;


@AutoConfigureMockMvc
//@WebMvcTest(controllers = PlanControllerTest.class)
@WebMvcTest(
        controllers = PlanController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanService planService;

    @Test
    @WithMockCustomUser
    public void test() throws Exception {
        Mockito.when(planService.createPlan(ArgumentMatchers.anyString(), ArgumentMatchers.nullable(PlanCreateRequest.class)))
                .thenReturn(new PlanCreateResponse(1L));

        PlanCreateRequest planCreateRequest = new PlanCreateRequest("title", "content", Category.WALK,
                LocalDateTime.now(), LocalDateTime.now(), 10, 20,
                "korea", "seoul", "kangnam",
                "123", "456", 3, 4, 5);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/plan/create")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planCreateRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.planId").exists());
    }

}