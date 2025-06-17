package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Nurses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NursesDao extends DaoImp<Nurses> {

    public NursesDao(Connection connection) {
        super(connection);
    }

    @Override
    protected PreparedStatement getCreateStatement(Nurses nurse) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO nurses (surname, firstname, phone) VALUES (?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, nurse.getSurname());
            preparedStatement.setString(2, nurse.getFirstName());
            preparedStatement.setString(3, nurse.getDepartment()); // Assuming phone is stored in `department`
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getReadByIDStatement(long nid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM nurses WHERE nid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, nid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    protected Nurses getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Nurses(
                result.getString("surname"),
                result.getString("firstname"),
                result.getString("nid"),
                result.getString("phone")
        );
    }

    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM nurses";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    @Override
    protected ArrayList<Nurses> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Nurses> list = new ArrayList<>();
        while (result.next()) {
            Nurses nurse = new Nurses(
                    result.getString("surname"),
                    result.getString("firstName"),
                    result.getString("nurseId"),
                    result.getString("department")
            );
            list.add(nurse);
        }
        return list;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Nurses nurse) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE nurses SET surname = ?, firstname = ?, phone = ? WHERE nid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, nurse.getSurname());
            preparedStatement.setString(2, nurse.getFirstName());
            preparedStatement.setString(3, nurse.getDepartment()); // Assuming phone is stored in `department`
            preparedStatement.setString(4, nurse.getNurseId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getDeleteStatement(long nid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM nurses WHERE nid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, nid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}