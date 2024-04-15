package com.ekdev.jdbc.dao;

import com.ekdev.jdbc.entity.Orders;
import com.ekdev.jdbc.exception.DaoException;
import com.ekdev.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersDao implements Dao<Long, Orders> {

    private static final OrdersDao INSTANCE = new OrdersDao();
    private static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO orders (buyer_id, price, is_paid)
            VALUES (?, ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE orders
            SET buyer_id = ?,
            price = ?,
            is_paid = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, buyer_id, price, is_paid
            FROM orders
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private OrdersDao() {
    }

    public static OrdersDao getInstance() {
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
    public Orders save(Orders orders) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, orders.getBuyerId());
            preparedStatement.setBigDecimal(2, orders.getPrice());
            preparedStatement.setBoolean(3, orders.getPaid());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orders.setId(generatedKeys.getLong("id"));
            }
            return orders;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(Orders orders) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, orders.getBuyerId());
            preparedStatement.setBigDecimal(2, orders.getPrice());
            preparedStatement.setBoolean(3, orders.getPaid());
            preparedStatement.setLong(4, orders.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public Optional<Orders> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Orders orders = null;
            if (resultSet.next()) {
                orders = buildOrders(resultSet);
            }

            return Optional.ofNullable(orders);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public List<Orders> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Orders> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrders(resultSet));
            }
            return orders;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    private Orders buildOrders(ResultSet resultSet) throws SQLException {
        return new Orders(
                resultSet.getLong("id"),
                resultSet.getLong("buyer_id"),
                resultSet.getBigDecimal("price"),
                resultSet.getBoolean("is_paid")
        );
    }
}
