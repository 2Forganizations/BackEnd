package project.travelmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.ProfileImage;
import project.travelmate.domain.User;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.MemberProfileUpdateRequest;
import project.travelmate.response.ProfileResponse;
import project.travelmate.utils.storage.FileSystem;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final UserRepository userRepository;
    private final FileSystem fileSystem;

    public ProfileResponse getProfile(String id) {
        return userRepository.findUserWithProfileImageById(id).map(ProfileResponse::new).orElseThrow(UserNotFoundException::new);
//        return userRepository.findById(id).map(ProfileResponse::new).orElseThrow(UserNotFoundException::new);
    }


    @Transactional
    public void editProfile(String id, MemberProfileUpdateRequest memberProfileUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.edit(memberProfileUpdateRequest);
    }

    @Transactional
    public void editProfileImage(String id, MultipartFile imageFile) {
        ProfileImage profileImage = userRepository.findUserWithProfileImageById(id).map(user -> user.getProfileImage())
                .orElseThrow(UserNotFoundException::new);
        fileSystem.deleteImage(profileImage.getFilePath());

        String savedFilePath = fileSystem.saveImage(imageFile);

        profileImage.edit(savedFilePath);
    }
}
