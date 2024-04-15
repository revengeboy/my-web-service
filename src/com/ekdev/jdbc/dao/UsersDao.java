package com.ekdev.jdbc.dao;

import com.ekdev.jdbc.entity.Users;
import com.ekdev.jdbc.exception.DaoException;
import com.ekdev.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDao implements Dao<Long, Users> {

    private static final UsersDao INSTANCE = new UsersDao();
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO users (role_id, login, password, first_name, last_name, orders_amount, is_blocked, blocked_by)
            VALUES (?, ?, ?, ?, ?, ? , ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET role_id = ?,
            login = ?,
            password = ?,
            first_name = ?,
            last_name = ?,
            orders_amount = ?,
            is_blocked = ?,
            blocked_by = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, role_id, login, password, first_name, last_name, orders_amount, is_blocked, blocked_by
            FROM users
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private UsersDao() {
    }

    public static UsersDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public Users save(Users users) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, users.getRoleId());
            preparedStatement.setString(2, users.getLogin());
            preparedStatement.setString(3, users.getPassword());
            preparedStatement.setString(4, users.getFirstName());
            preparedStatement.setString(5, users.getLastName());
            preparedStatement.setLong(6, users.getOrdersAmount());
            preparedStatement.setBoolean(7, users.getBlocked());
            preparedStatement.setLong(8, users.getBlockedBy());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                users.setId(generatedKeys.getLong("id"));
            }
            return users;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(Users users) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, users.getRoleId());
            preparedStatement.setString(2, users.getLogin());
            preparedStatement.setString(3, users.getPassword());
            preparedStatement.setString(4, users.getFirstName());
            preparedStatement.setString(5, users.getLastName());
            preparedStatement.setLong(6, users.getOrdersAmount());
            preparedStatement.setBoolean(7, users.getBlocked());
            preparedStatement.setLong(8, users.getBlockedBy());
            preparedStatement.setLong(9, users.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public Optional<Users> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Users users = null;
            if (resultSet.next()) {
                users = buildUsers(resultSet);
            }

            return Optional.ofNullable(users);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public List<Users> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Users> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUsers(resultSet));
            }
            return users;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    private Users buildUsers(ResultSet resultSet) throws SQLException {
        return new Users(
                resultSet.getLong("id"),
                resultSet.getLong("role_id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getLong("orders_amount"),
                resultSet.getBoolean("is_blocked"),
                resultSet.getLong("blocked_by")
        );
    }
}
