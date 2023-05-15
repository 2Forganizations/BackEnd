package project.travelmate.request;

import lombok.Getter;

@Getter
public class AuthInfo {

    private String id;

    public AuthInfo(String id) {
        this.id = id;
    }

}