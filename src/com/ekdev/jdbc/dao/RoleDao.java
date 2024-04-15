package com.ekdev.jdbc.dao;

import com.ekdev.jdbc.entity.Role;
import com.ekdev.jdbc.exception.DaoException;
import com.ekdev.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDao implements Dao<Long, Role> {

    private static final RoleDao INSTANCE = new RoleDao();
    private static final String DELETE_SQL = """
            DELETE FROM role
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO role (role)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE role
            SET role = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, role
            FROM role
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private RoleDao() {
    }

    public static RoleDao getInstance() {
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
    public Role save(Role role) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getRole());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                role.setId(generatedKeys.getLong("id"));
            }
            return role;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(Role role) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setLong(6, role.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public Optional<Role> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Role role = null;
            if (resultSet.next()) {
                role = buildRole(resultSet);
            }

            return Optional.ofNullable(role);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public List<Role> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(buildRole(resultSet));
            }
            return roles;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    private Role buildRole(ResultSet resultSet) throws SQLException {
        return new Role(
                resultSet.getLong("id"),
                resultSet.getString("role")
        );
    }
}
