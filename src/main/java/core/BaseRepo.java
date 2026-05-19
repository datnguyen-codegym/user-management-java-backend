package core;

import config.Database;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseRepo<T extends BaseEntity, ID> implements Repo<T, ID> {

    protected final String TABLE;

    protected final List<String> ORDER_FIELD_INSERT_DB;

    public BaseRepo(String TABLE, List<String> orderFieldInsertId) {
        this.TABLE = TABLE;
        ORDER_FIELD_INSERT_DB = orderFieldInsertId;
    }

    @Override
    public Collection<T> list() {
        String SQL = String.format("SELECT * FROM %s", this.TABLE);

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            List<T> dataList = new ArrayList<>();
            while (rs.next()) {
                dataList.add(this.mappingFromDatabase(rs));
            }
            return dataList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", TABLE);

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (id instanceof Long) {
                ps.setLong(1, (Long) id);
            } else if (id instanceof UUID){
                ps.setString(1, id.toString());
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(this.mappingFromDatabase(rs));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(ID id) {
        String sql = String.format("DELETE %s WHERE id = ?", TABLE);

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (id instanceof Long) {
                ps.setLong(1, (Long) id);
            } else if (id instanceof UUID) {
                ps.setString(1, id.toString());
            }

            ps.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T create(T obj) {
        String sql =
                String.format("INSERT INTO %s (%s) VALUES(%s)",
                        TABLE,
                        String.join(", ", ORDER_FIELD_INSERT_DB),
                        ORDER_FIELD_INSERT_DB.stream().map(e -> " ? ")
                                .collect(Collectors.joining(","))
                )
                ;

        try (Connection connection = Database.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement ps =
                         connection.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {
                this.mappingFromEntity(ps, obj);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    obj.setId(rs.getLong(1));
                }
                connection.commit();
                return obj;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public T update(T obj) {
        return null;
    }

    protected abstract T mappingFromDatabase(ResultSet rs) throws SQLException;

    protected abstract void mappingFromEntity(PreparedStatement ps, T entity) throws SQLException;
}
