package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.model.ParameterMaster;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterMasterDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterMasterDAO.class);
    private final DataSource dataSource;

    public ParameterMasterDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String INSERT_PARAMETER_MASTER = "INSERT INTO parameter_master (code, name, remarks, created_by, created_date) VALUES (?, ?, ?, ?, NOW())";
    private static final String UPDATE_PARAMETER_MASTER = "UPDATE parameter_master SET code = ?, name = ?, remarks = ?, modified_by = ?, modified_date = NOW() WHERE id = ?";
    private static final String DELETE_PARAMETER_MASTER = "DELETE FROM parameter_master WHERE id = ?";

    public QueryResult insertParameterMaster(ParameterMaster parameterMaster) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_PARAMETER_MASTER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, parameterMaster.getCode());
            ps.setString(2, parameterMaster.getName());
            ps.setString(3, parameterMaster.getRemarks());
            ps.setString(4, parameterMaster.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting parameter master. Code: {}, Name: {}, Error: {}", parameterMaster.getCode(), parameterMaster.getName(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult updateParameterMaster(ParameterMaster parameterMaster) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_PARAMETER_MASTER)) {
            ps.setString(1, parameterMaster.getCode());
            ps.setString(2, parameterMaster.getName());
            ps.setString(3, parameterMaster.getRemarks());
            ps.setString(4, parameterMaster.getModifiedBy());
            ps.setString(5, parameterMaster.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating parameter master. ID: {}, Code: {}, Name: {}, Error: {}", parameterMaster.getId(), parameterMaster.getCode(), parameterMaster.getName(), e.getMessage(), e);
        }
        return queryResult;
    }

    public QueryResult deleteParameterMaster(String parameterMasterId) {
        QueryResult queryResult = new QueryResult();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_PARAMETER_MASTER)) {
            ps.setString(1, parameterMasterId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting parameter master. ID: {}, Error: {}", parameterMasterId, e.getMessage(), e);
        }
        return queryResult;
    }

    private static final String GET_PARAMETER_MASTER = "SELECT id, code, name, remarks, created_by, created_date, modified_by, modified_date FROM parameter_master WHERE id = ?";
    private static final String GET_PARAMETER_MASTER_LIST = "SELECT id, code, name, remarks, created_by, created_date, modified_by, modified_date FROM parameter_master ORDER BY id ASC";
    private static final String GET_NEXT_MASTER_CODE = "SELECT LPAD(IFNULL(MAX(code) + 1, 1), 3, '0') AS code FROM parameter_master";
    private static final String GET_MASTER_CODE_AND_NAME = "SELECT code, name FROM parameter_master WHERE code = ?";
    private static final String GET_MASTER_CODE = "SELECT code FROM parameter_master WHERE code = ?";

    public ParameterMaster getParameterMaster(String parameterMasterId) {
        ParameterMaster parameterMaster = null;
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_PARAMETER_MASTER)) {
            ps.setString(1, parameterMasterId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    parameterMaster = new ParameterMaster();
                    parameterMaster.setId(rs.getString("id"));
                    parameterMaster.setCode(rs.getString("code"));
                    parameterMaster.setName(rs.getString("name"));
                    parameterMaster.setRemarks(rs.getString("remarks"));
                    parameterMaster.setCreatedBy(rs.getString("created_by"));
                    parameterMaster.setCreatedDate(rs.getString("created_date"));
                    parameterMaster.setModifiedBy(rs.getString("modified_by"));
                    parameterMaster.setModifiedDate(rs.getString("modified_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter master. ID: {}, Error: {}", parameterMasterId, e.getMessage(), e);
        }
        return parameterMaster;
    }

    public List<ParameterMaster> getParameterMasterList() {
        List<ParameterMaster> parameterMasterList = new ArrayList<>();
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_PARAMETER_MASTER_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ParameterMaster parameterMaster = new ParameterMaster();
                parameterMaster.setId(rs.getString("id"));
                parameterMaster.setCode(rs.getString("code"));
                parameterMaster.setName(rs.getString("name"));
                parameterMaster.setRemarks(rs.getString("remarks"));
                parameterMaster.setCreatedBy(rs.getString("created_by"));
                parameterMaster.setCreatedDate(rs.getString("created_date"));
                parameterMaster.setModifiedBy(rs.getString("modified_by"));
                parameterMaster.setModifiedDate(rs.getString("modified_date"));
                parameterMasterList.add(parameterMaster);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter master list. Error: {}", e.getMessage(), e);
        }
        return parameterMasterList;
    }

    public String getNextMasterCode() {
        String code = "";
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_NEXT_MASTER_CODE); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                code = rs.getString("code");
            }
        } catch (SQLException e) {
            LOGGER.error("Error generating next parameter master code. Error: {}", e.getMessage(), e);
        }
        return code;
    }

    public String getMasterCodeandName(String masterCode) {
        String codeName = "";
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_MASTER_CODE_AND_NAME)) {
            ps.setString(1, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    codeName = code + " - " + name;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter master code and name. Code: {}, Error: {}", masterCode, e.getMessage(), e);
        }
        return codeName;
    }

    public String getMasterCode(String masterCode) {
        String code = "";
        try (
            Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_MASTER_CODE)) {
            ps.setString(1, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    code = rs.getString("code");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter master code. Code: {}, Error: {}", masterCode, e.getMessage(), e);
        }
        return code;
    }

}