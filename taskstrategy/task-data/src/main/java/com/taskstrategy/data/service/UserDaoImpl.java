package com.taskstrategy.data.service;

import com.taskstrategy.commons.domain.User;
import com.taskstrategy.data.api.UserDao;
import com.taskstrategy.data.api.exception.UserUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 11/4/13
 * Time: 6:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
public class UserDaoImpl implements UserDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoImpl.class);
    public static final String INSERT_PASSWORD_RESET = "INSERT INTO PasswordReset (EMAIL, RESET_ID, CREATE_DATE) VALUES(:email, :resetId, NOW())";
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_USER_BY_USERNAME = "SELECT * FROM users where username = :username";
    private static final String GET_USER_BY_ID = "SELECT * FROM users where USER_ID = :id";
    private static final String INSERT_USER = "INSERT INTO users(USER_ID, USERNAME, PASSWORD, ENABLED) VALUES(:userId, :username, :password, 1)";
    private static final String DELETE_USER = "DELETE FROM users where USER_ID = :userId";
    private static final String UPDATE_USERNAME = "UPDATE users SET USERNAME = :username WHERE USER_ID = :userId";
    private static final String UPDATE_PASSWORD = "UPDATE users SET PASSWORD = :password WHERE USER_ID = :userId";
    private static final String UPDATE_PASSWORD_USERNAME = "UPDATE users SET PASSWORD = :password WHERE USERNAME = :email";
    private static final String GET_USER_ID_BY_RESET_ID = "SELECT EMAIL FROM PasswordReset WHERE RESET_ID = :resetId";
    private static final String INVALIDATE_PASSWORD_RESETS = "DELETE FROM PasswordReset WHERE CREATE_DATE < NOW() - INTERVAL 2 DAY;";

    @Override
    public User loadUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        User user = jdbcTemplate.query(GET_USER_BY_USERNAME, params, new UserResultSetExtractor());
        return user;
    }

    @Override
    public User loadUser(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        User user = jdbcTemplate.query(GET_USER_BY_ID, params, new UserResultSetExtractor());
        return user;
    }

    @Override
    public void createUser(User user) throws UserUpdateException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", user.getId());
            params.put("username", user.getUsername());
            params.put("password", user.getPassword());
            int count = jdbcTemplate.update(INSERT_USER, params);
            if (count != 1) {
                throw new UserUpdateException("Unable to create user, contact support");
            }
        } catch (DataIntegrityViolationException e) {
            throw new UserUpdateException("Username already in use, please choose another name");
        }
    }

    @Override
    public void deleteUser(String currentUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUser);
        jdbcTemplate.update(DELETE_USER, params);
    }

    @Override
    public void updateUsername(String currentUserId, String username) throws UserUpdateException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", currentUserId);
            params.put("username", username);
            jdbcTemplate.update(UPDATE_USERNAME, params);
        } catch (DataIntegrityViolationException e) {
            throw new UserUpdateException("Username already in use, please choose another name");
        }
    }

    @Override
    public void updatePassword(String currentUserId, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        params.put("password", password);
        jdbcTemplate.update(UPDATE_PASSWORD, params);
    }

    @Override
    public String getUserIdFromReset(String resetId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("resetId", resetId);
            return jdbcTemplate.queryForObject(GET_USER_ID_BY_RESET_ID, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void logResetPasswordEvent(String email, String resetId) {
        Map<String, Object> params = new HashMap<>();
        params.put("resetId", resetId);
        params.put("email", email);
        jdbcTemplate.update(INSERT_PASSWORD_RESET, params);
    }

    @Override
    public void updatePasswordByUsername(String userId, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", userId);
        params.put("password", password);
        jdbcTemplate.update(UPDATE_PASSWORD_USERNAME, params);
    }

    @Override
    public void invalidatePasswordResets() {
        jdbcTemplate.update(INVALIDATE_PASSWORD_RESETS, new HashMap<String, Object>());
    }

    protected static final class UserResultSetExtractor implements ResultSetExtractor<User> {

        @Override
        public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            User user = null;
            if (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                List<GrantedAuthority> authList = new ArrayList<>(2);
                authList.add(new SimpleGrantedAuthority("ROLE_USER"));
                user = new User(username, password, authList);
                user.setId(resultSet.getString("USER_ID"));
            }
            return user;
        }
    }


    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
