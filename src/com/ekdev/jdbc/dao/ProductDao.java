package com.ekdev.jdbc.dao;

import com.ekdev.jdbc.entity.Product;
import com.ekdev.jdbc.exception.DaoException;
import com.ekdev.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Long, Product> {

    private static final ProductDao INSTANCE = new ProductDao();
    private static final String DELETE_SQL = """
            DELETE FROM product
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO product (name, price, description, rating)
            VALUES (?, ?, ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE product
            SET name = ?,
            price = ?,
            description = ?,
            rating = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, name, price, description, rating
            FROM product
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private ProductDao() {
    }

    public static ProductDao getInstance() {
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
    public Product save(Product product) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getRating());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong("id"));
            }
            return product;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public void update(Product product) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getRating());
            preparedStatement.setLong(5, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = buildProduct(resultSet);
            }

            return Optional.ofNullable(product);
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public List<Product> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(buildProduct(resultSet));
            }
            return products;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getBigDecimal("price"),
                resultSet.getString("description"),
                resultSet.getDouble("rating")
        );
    }
}
