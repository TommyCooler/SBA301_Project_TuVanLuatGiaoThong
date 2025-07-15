package sba.project.tuvanluatgiaothong.dto.request;

import java.time.Instant;

public class UserInfoRequest {
    private String fullname;
    private String avatarUrl;
    private Instant birthDay;

    public UserInfoRequest() {}

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }
}
