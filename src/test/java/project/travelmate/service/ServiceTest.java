package project.travelmate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import project.travelmate.repository.PlanRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.utils.constant.SetUpData;
import project.travelmate.utils.storage.S3Util;

@SpringBootTest
@Transactional
class ServiceTest extends SetUpData {

    @MockBean
    protected PlanRepository planRepository;

    @MockBean
    protected UserRepository userRepository;

    @Autowired
    protected PlanService planService;

    @Autowired
    protected S3Util s3Util;

}