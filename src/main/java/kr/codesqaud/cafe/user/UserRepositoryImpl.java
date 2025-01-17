package kr.codesqaud.cafe.user;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * H2 DB에 JdbcTemplate을 사용하여 데이터를 저장하는 저장소
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate template;

    public UserRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public User save(User user) {
        String sql = "insert into user (login_id, password, name, email) values (:loginId, :password, :name, :email)";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("loginId", user.getLoginId())
                .addValue("password", user.getPassword())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail());
        template.update(sql, param);
        return user;
    }

    @Override
    public Optional<User> findById(String loginId) {
        String sql = "select id, login_id, password, name, email from user where login_id = :loginId";
        SqlParameterSource param = new MapSqlParameterSource("loginId", loginId);
        try {
            return Optional.ofNullable(template.queryForObject(sql, param, userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> findByName(String name) {
        String sql = "select id, login_id, password, name, email from user where name = :name"; // TODO: 불필요한 정보도 담고 있는 것 같다. 서비스에서 뷰로 넘어줄 때 적당한 DTO로 변환해야 할까?
        SqlParameterSource param = new MapSqlParameterSource("name", name);
        try {
            return Optional.ofNullable(template.queryForObject(sql, param, userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, login_id, password, name, email from user";
        return template.query(sql, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (resultSet, rowNumber) -> new User.Builder()
                .id(resultSet.getLong("id"))
                .loginId(resultSet.getString("login_Id"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .build();
    }
}
