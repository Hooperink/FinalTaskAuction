package epam.by.Auction.dao.impl;

import epam.by.Auction.connection.ProxyConnection;
import epam.by.Auction.dao.api.Dao;
import epam.by.Auction.mapper.RowMapper;
import epam.by.Auction.entity.Identifiable;
import epam.by.Auction.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO <T extends Identifiable> implements Dao<T> {

    protected ProxyConnection proxyConnection;

    protected AbstractDAO(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    protected List<T> executeGetQuery(String query, RowMapper<T> mapper, Object... params) throws DaoException {
        try(PreparedStatement preparedStatement = createStatement(query, params)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = proxyConnection.prepareStatement(query);
        for (int i = 1; i <= params.length ; i++) {
            preparedStatement.setObject(i, params[i - 1]);
        }
        return preparedStatement;
    }

    public void executeUpdate(String query, Object... params) throws DaoException {
        try(PreparedStatement preparedStatement = createStatement(query, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        String table = getTableName();
        RowMapper<T> mapper = (RowMapper<T>) RowMapper.create(table);
        return executeGetQuery("SELECT * FROM " + table, mapper);
    }

    @Override
    public void removeById(long id) throws DaoException {
        String table = getTableName();
        try(PreparedStatement preparedStatement = proxyConnection.prepareStatement("DELETE FROM " + table + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error in removing by ID");
        }
    }

    @Override
    public Optional<T> getById(long id) throws DaoException {
        String table = getTableName();
        RowMapper<T> mapper = (RowMapper<T>) RowMapper.create(table);
        return executeGetForSingleResult("SELECT * FROM " + table + " WHERE ID = ?" , mapper, id);
    }

    protected Optional<T> executeGetForSingleResult(String query, RowMapper<T> builder, Object... params) throws DaoException {
        List<T> items = executeGetQuery(query, builder, params);
        if (items.size() == 1) {
            return Optional.of(items.get(0));
        } else if (items.size() > 1) {
            throw new IllegalArgumentException("More than one record found");
        } else {
            return Optional.empty();
        }
    }

    protected void executeQueryForSave(String query, T item) throws DaoException {
        String table = getTableName();
        RowMapper<T> rowMapper = (RowMapper<T>) RowMapper.create(table);
        List<Object> objectsToSave = rowMapper.getFieldsToSave(item);
        try (PreparedStatement preparedStatement = createStatement(query, objectsToSave.toArray())){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(T item, String query) throws DaoException {
        executeQueryForSave(query, item);
    }
    protected abstract String getTableName();
}
