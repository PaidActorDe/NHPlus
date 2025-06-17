package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Nurses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NursesDao {
    private final Connection connection;

    public NursesDao(Connection connection) {
        this.connection = connection;
    }

    public long createAndReturnId(Nurses nurse) throws SQLException {
        String insertSQL = "INSERT INTO nurses(surname, firstName, phone) VALUES (?, ?, ?)";
        String idSQL = "SELECT last_insert_rowid()";

        try (
                PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
                PreparedStatement idStmt = connection.prepareStatement(idSQL)
        ) {
            insertStmt.setString(1, nurse.getSurname());
            insertStmt.setString(2, nurse.getFirstName());
            insertStmt.setString(3, nurse.getPhone());
            insertStmt.executeUpdate();

            try (ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("ID konnte nicht abgefragt werden.");
                }
            }
        }
    }

    public List<Nurses> readAll() throws SQLException {
        List<Nurses> result = new ArrayList<>();
        String sql = "SELECT * FROM nurses";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(new Nurses(
                        rs.getLong("nid"),
                        rs.getString("surname"),
                        rs.getString("firstName"),
                        rs.getString("phone")
                ));
            }
        }
        return result;
    }

    public void update(Nurses nurse) throws SQLException {
        String sql = "UPDATE nurses SET surname = ?, firstName = ?, phone = ? WHERE nid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nurse.getSurname());
            stmt.setString(2, nurse.getFirstName());
            stmt.setString(3, nurse.getPhone());
            stmt.setLong(4, nurse.getNid());
            stmt.executeUpdate();
        }
    }

    public void deleteById(long id) throws SQLException {
        String sql = "DELETE FROM nurses WHERE nid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
