package sba.project.tuvanluatgiaothong.repository;

import sba.project.tuvanluatgiaothong.pojo.User;

public interface ITokenTransaction {
    void saveToken(User user, String jwtToken);
    void revokeAllOldUserToken(User user);
}
