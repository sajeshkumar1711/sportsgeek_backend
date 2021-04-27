package com.project.sportsgeek.repository.userrepo;

import com.project.sportsgeek.model.profile.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userRepo")
public interface UserRepository {

    public List<User> findAllUsers();

    public List<User> findUserByUserId(int id) throws Exception;

    public List<UserWithPassword> findUserByUserName(String userName) throws Exception;

    public List<User> findAllUsersByRole(int role) throws Exception;

    public List<User> findUserByEmailId(User user) throws Exception;

    public List<UserWinningAndLossingPoints> findLoosingPointsByUserId(int userId) throws Exception;

    public List<UserWinningAndLossingPoints> findWinningPointsByUserId(int userId) throws Exception;

    public List<User> findUsersByStatus(boolean status) throws Exception;

    public int addUser(UserWithPassword userWithPassword) throws Exception;

    public int addEmail(UserWithPassword userWithPassword) throws Exception;

    public int addMobile(UserWithPassword userWithPassword) throws Exception;

    public boolean updateUser(int id, User user) throws Exception;

    public boolean updateStatus(int id, boolean status) throws Exception;

    public int updateUserPassword(UserWithNewPassword userWithNewPassword) throws Exception;

    public int updateForgetPassword(UserWithOtp userWithOtp) throws Exception;

    public int updateUserRole(int userId, int role) throws Exception;

    public boolean updateUserProfilePicture(int userId, String profilePicture) throws Exception;

    public boolean updateUserAvailablePoints(int uderId, int availablePoints) throws Exception;

    public UserForLoginState authenticate(UserAtLogin userAtLogin) throws Exception;

    public int deleteUser(int id) throws Exception;

    public int deleteEmail(int id) throws Exception;

    public int deleteMobile(int id) throws Exception;

    public int deleteBOT(int id) throws Exception;

    public int deleteRecharge(int id) throws Exception;

    public boolean updateEmail(int id, User user) throws Exception;

    public boolean updateMobile(int id, User user) throws Exception;

    public int addAvailablePoints(int points) throws Exception;

    public int deductAvailablePoints(int points) throws Exception;
}
