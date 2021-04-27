package com.project.sportsgeek.repository.userrepo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.project.sportsgeek.mapper.UserRowMapper;
import com.project.sportsgeek.mapper.UserWithPasswordRowMapper;
import com.project.sportsgeek.mapper.UserWithWinningPointsRowMapper;
import com.project.sportsgeek.mapper.userWithLoosingPointsRowMapper;
import com.project.sportsgeek.model.profile.User;
import com.project.sportsgeek.model.profile.UserAtLogin;
import com.project.sportsgeek.model.profile.UserForLoginState;
import com.project.sportsgeek.model.profile.UserWinningAndLossingPoints;
import com.project.sportsgeek.model.profile.UserWithNewPassword;
import com.project.sportsgeek.model.profile.UserWithOtp;
import com.project.sportsgeek.model.profile.UserWithPassword;

@Repository(value = "userRepo")
public class UserRepoImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- SELECT QUERY ------------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public List<User> findAllUsers() {
		String sql = "SELECT * FROM User";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public List<User> findUserByUserId(int id) throws Exception {
		String sql = "SELECT * FROM User WHERE UserId=" + id;
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public List<User> findAllUsersByRole(int role) throws Exception {
		String sql = "SELECT * FROM User WHERE RoleId=" + role;
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public List<User> findUserByEmailId(User user) throws Exception {
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, GenderId, RoleId, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber "
				+ "FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join MobileContact on User.UserId=MobileContact.UserId WHERE EmailContact.EmailId='"
				+ user.getEmail() + "' AND MobileContact.MobileNumber='" + user.getMobileNumber() + "' ";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public List<User> findUsersByStatus(boolean status) throws Exception {
		String sql = "SELECT User.UserId as UserId, FirstName, LastName, GenderId, RoleId, Username, AvailablePoints, ProfilePicture, Status, EmailContact.EmailId as Email, MobileContact.MobileNumber as MobileNumber "
				+ "FROM User inner join EmailContact on User.UserId=EmailContact.UserId inner join MobileContact on User.UserId=MobileContact.UserId WHERE User.Status="
				+ status;
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public List<UserWinningAndLossingPoints> findLoosingPointsByUserId(int userId) throws Exception {
		String sql = "SELECT SUM(BetPoints) as LoosingPoints,bot.UserId \n"
				+ "FROM BetOnTeam as bot INNER JOIN Matches as m ON bot.MatchId = m.MatchId\n"
				+ "WHERE WinningPoints=0 AND m.ResultStatus=1 AND UserId=" + userId;
		return jdbcTemplate.query(sql, new userWithLoosingPointsRowMapper());
	}

	@Override
	public List<UserWinningAndLossingPoints> findWinningPointsByUserId(int userId) throws Exception {
		String sql = "select sum(WinningPoints) as WinningPoints,UserId from BetOnTeam where UserId=" + userId;
		return jdbcTemplate.query(sql, new UserWithWinningPointsRowMapper());
	}

	@Override
	public List<UserWithPassword> findUserByUserName(String userName) throws Exception {
		String sql = "SELECT u.UserName as UserName,u.Password as Password,r.Name as Name FROM User as u INNER JOIN Role as r on u.RoleId=r.RoleId WHERE UserName='"
				+ userName + "'";
		return jdbcTemplate.query(sql, new UserWithPasswordRowMapper());
	}

//	---------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- AUTHENTICATION QUERY ----------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public UserForLoginState authenticate(UserAtLogin userAtLogin) throws Exception {
		String sql = "select u.UserId, u.UserName, r.Name, u.Status from User as u inner join Role as r on u.RoleId = r.RoleId where u.UserName = :username";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
				new BeanPropertySqlParameterSource(userAtLogin));
		System.out.println(list.size());
		if (list.size() > 0) {

			return new UserForLoginState(Integer.parseInt(list.get(0).get("UserId") + ""),
					list.get(0).get("UserName") + "", list.get(0).get("Name") + "",
					Boolean.parseBoolean(list.get(0).get("Status").toString()), "");
		}
		return null;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- INSERT QUERY -----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public int addUser(UserWithPassword userWithPassword) throws Exception {

		String insert_sql = "INSERT INTO User (FirstName,LastName,GenderId,Username,Password,ProfilePicture,RoleId,AvailablePoints,Status)"
				+ "values ('" + userWithPassword.getFirstName() + "','" + userWithPassword.getLastName() + "',"
				+ userWithPassword.getGenderId() + ",'" + userWithPassword.getUsername() + "'" + ",'"
				+ userWithPassword.getPassword() + "','" + userWithPassword.getProfilePicture() + "',"
				+ userWithPassword.getRoleId() + "," + userWithPassword.getAvailablePoints() + ","
				+ userWithPassword.isStatus() + ")";
		return jdbcTemplate.update(insert_sql, new BeanPropertySqlParameterSource(userWithPassword));
	}

	@Override
	public int addEmail(UserWithPassword userWithPassword) throws Exception {
		String email_sql = "INSERT INTO EmailContact (UserId,EmailId) values (" + userWithPassword.getUserId() + ", '"
				+ userWithPassword.getEmail() + "')";
		return jdbcTemplate.update(email_sql, new BeanPropertySqlParameterSource(userWithPassword));
	}

	@Override
	public int addMobile(UserWithPassword userWithPassword) throws Exception {
		String mobile_sql = "INSERT INTO MobileContact (UserId,MobileNumber) values (" + userWithPassword.getUserId()
				+ ", '" + userWithPassword.getMobileNumber() + "')";
		return jdbcTemplate.update(mobile_sql, new BeanPropertySqlParameterSource(userWithPassword));
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- UPDATE QUERY -----------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public boolean updateUser(int id, User user) throws Exception {
		String update_query = "UPDATE User SET FirstName = '" + user.getFirstName() + "' ,LastName = '"
				+ user.getLastName() + "' ,GenderId ='" + user.getGenderId() + "' WHERE UserId=" + id;
		return jdbcTemplate.update(update_query, new BeanPropertySqlParameterSource(user)) > 0;
	}

	@Override
	public boolean updateStatus(int id, boolean status) throws Exception {
		String update_status = "UPDATE User set Status =" + status + " WHERE UserId =" + id;
		return jdbcTemplate.update(update_status, new BeanPropertySqlParameterSource(id)) > 0;
	}

	@Override
	public int updateUserPassword(UserWithNewPassword userWithNewPassword) throws Exception {
		String select_password = "SELECT * FROM User Where UserId=" + userWithNewPassword.getUserId();
		List<UserWithPassword> userWithOldPassword = jdbcTemplate.query(select_password,
				new UserWithPasswordRowMapper());
		if (userWithOldPassword.size() > 0 && bCryptPasswordEncoder.matches(userWithNewPassword.getOldPassword(),
				userWithOldPassword.get(0).getPassword())) {
			userWithNewPassword.setNewPassword(bCryptPasswordEncoder.encode(userWithNewPassword.getNewPassword()));
			String sql = "UPDATE User SET Password = :newPassword WHERE UserId = :UserId";
			return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userWithNewPassword));
		}

		return -1;
	}

	@Override
	public int updateForgetPassword(UserWithOtp userWithOtp) throws Exception {
		System.out.println("Password : " + userWithOtp.getPassword());
		String encodedPassword = bCryptPasswordEncoder.encode(userWithOtp.getPassword());
		String sql = "UPDATE User SET Password ='" + encodedPassword + "' WHERE UserId='" + userWithOtp.getUserId()
				+ "'";
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userWithOtp));
	}

	@Override
	public int updateUserRole(int userId, int role) throws Exception {
		String sql = "UPDATE User SET RoleId=" + role + " WHERE UserId=" + userId;
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userId));
	}

	@Override
	public boolean updateUserProfilePicture(int userId, String profilePicture) throws Exception {
		String update_pp = "UPDATE User SET ProfilePicture =" + profilePicture + " WHERE UserId =" + userId;
		return jdbcTemplate.update(update_pp, new BeanPropertySqlParameterSource(profilePicture)) > 0;
	}

	@Override
	public boolean updateUserAvailablePoints(int userId, int availablePoints) throws Exception {
		String update_AvailablePoints = "UPDATE User SET AvailablePoints =" + availablePoints + " WHERE UserId ="
				+ userId;
		return jdbcTemplate.update(update_AvailablePoints, new BeanPropertySqlParameterSource(availablePoints)) > 0;
	}

	@Override
	public boolean updateEmail(int id, User user) throws Exception {
		String update_email = "UPDATE EmailContact SET EmailId = '" + user.getEmail() + "' WHERE UserId = " + id;
		return jdbcTemplate.update(update_email, new BeanPropertySqlParameterSource(user)) > 0;
	}

	@Override
	public boolean updateMobile(int id, User user) throws Exception {
		String update_mobile = "UPDATE MobileContact SET MobileNumber='" + user.getMobileNumber() + "' WHERE UserId="
				+ id;
		return jdbcTemplate.update(update_mobile, new BeanPropertySqlParameterSource(user)) > 0;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------
//	------------------------------------------------- DELETE QUERY -----------------------------------------------------------------------------
//	--------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public int deleteUser(int id) throws Exception {
		String delete_user = "DELETE FROM User WHERE UserId=" + id;
		return jdbcTemplate.update(delete_user, new BeanPropertySqlParameterSource(id));
	}

	@Override
	public int deleteEmail(int id) throws Exception {
		String delete_emailContact = "DELETE FROM EmailContact WHERE UserId=" + id;
		return jdbcTemplate.update(delete_emailContact, new BeanPropertySqlParameterSource(id));
	}

	@Override
	public int deleteMobile(int id) throws Exception {
		String delete_mobileContact = "DELETE FROM MobileContact WHERE UserId=" + id;
		return jdbcTemplate.update(delete_mobileContact, new BeanPropertySqlParameterSource(id));
	}

	@Override
	public int deleteBOT(int id) throws Exception {
		String delete_betOnTeam = "DELETE FROM BetOnTeam Where UserId=" + id;
		jdbcTemplate.update(delete_betOnTeam, new BeanPropertySqlParameterSource(id));
		return 0;
	}

	@Override
	public int deleteRecharge(int id) throws Exception {
		String delete_Recharge = "DELETE FROM Recharge WHERE UserId=" + id;
		return jdbcTemplate.update(delete_Recharge, new BeanPropertySqlParameterSource(id));
	}

}
