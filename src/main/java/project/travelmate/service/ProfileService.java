package project.travelmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.User;
import project.travelmate.repository.UserRepository;
import project.travelmate.response.ProfileResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileResponse getProfile(String id) {
//        return userRepository.findUserWithProfileImageById(id).map(ProfileResponse::new).orElseThrow(UserNotFoundException::new);
        return userRepository.findById(id).map(ProfileResponse::new).orElseThrow(UserNotFoundException::new);
    }


}
