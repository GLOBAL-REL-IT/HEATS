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
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterDetailsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterDetailsDAO.class);
    private final DataSource dataSource;

    public ParameterDetailsDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    private static final String SQL_INSERT_PARAMETER_DETAILS = "INSERT INTO parameter_details (master_code, detail_code, name, remarks, created_by, created_date) VALUES (?,?,?,?,?,NOW())";
    private static final String SQL_UPDATE_PARAMETER_DETAILS = "UPDATE parameter_details SET master_code = ?, detail_code = ?, name = ?, remarks = ?, modified_by = ?, modified_date = NOW() WHERE id = ?";
    private static final String SQL_DELETE_PARAMETER_DETAILS = "DELETE FROM parameter_details WHERE id = ?";
    private static final String SQL_REMOVE_DETAILS_BY_MASTER_CODE = "DELETE FROM parameter_details WHERE master_code = ?";

    public QueryResult insertParameterDetails(ParameterDetails parameterDetails) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_PARAMETER_DETAILS, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, parameterDetails.getMasterCode());
            ps.setString(2, parameterDetails.getDetailCode());
            ps.setString(3, parameterDetails.getName());
            ps.setString(4, parameterDetails.getRemarks());
            ps.setString(5, parameterDetails.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error inserting parameter detail. masterCode={}, detailCode={}", parameterDetails.getMasterCode(), parameterDetails.getDetailCode(), e);
        }
        return queryResult;
    }

    public QueryResult updateParameterDetails(ParameterDetails parameterDetails) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_PARAMETER_DETAILS)) {
            ps.setString(1, parameterDetails.getMasterCode());
            ps.setString(2, parameterDetails.getDetailCode());
            ps.setString(3, parameterDetails.getName());
            ps.setString(4, parameterDetails.getRemarks());
            ps.setString(5, parameterDetails.getModifiedBy());
            ps.setString(6, parameterDetails.getId());
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error updating parameter detail. id={}, masterCode={}, detailCode={}", parameterDetails.getId(), parameterDetails.getMasterCode(), parameterDetails.getDetailCode(), e);
        }
        return queryResult;
    }

    public QueryResult deleteParameterDetails(String parameterDetailsId) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_PARAMETER_DETAILS)) {
            ps.setString(1, parameterDetailsId);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error deleting parameter detail. id={}", parameterDetailsId, e);
        }
        return queryResult;
    }

    public QueryResult removeDetailsByMasterCode(String masterCode) {
        QueryResult queryResult = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_REMOVE_DETAILS_BY_MASTER_CODE)) {
            ps.setString(1, masterCode);
            queryResult.setResult(ps.executeUpdate());
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error("Error removing parameter details by master code. masterCode={}", masterCode, e);
        }
        return queryResult;
    }

    private static final String SQL_GET_PARAMETER_DETAILS = "SELECT * FROM parameter_details WHERE id = ?";
    private static final String SQL_GET_PARAMETER_DETAILS_LIST = "SELECT * FROM parameter_details ORDER BY id ASC";
    private static final String SQL_GET_PARAMETER_DETAILS_LIST_BY_MASTER_CODE = "SELECT * FROM parameter_details WHERE master_code = ? ORDER BY id ASC";
    private static final String SQL_GET_NEXT_DETAIL_CODE = "SELECT LPAD(IFNULL(MAX(m.detail_code) + 1, CONCAT(?, '001')), 6, '0') AS code FROM parameter_details m WHERE m.master_code = ?";
    private static final String SQL_GET_COUNT_BY_MASTER_CODE = "SELECT COUNT(id) AS count FROM parameter_details WHERE master_code = ?";
    private static final String SQL_GET_GROUP_PARAMETER_DETAIL_LIST = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, IF(name = ?, \"selected=''\", \"\") AS selected FROM parameter_details WHERE master_code = ? ORDER BY name";
    private static final String SQL_GET_ACTIVITY_PARAMETER = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, remarks AS remarks, IF(name = ?, \"selected=''\", \"\") AS selected FROM parameter_details WHERE master_code = ? ORDER BY detail_code";
    private static final String SQL_GET_GROUP_PARAMETER_DETAIL_LIST_FOR_PRIORITY_BOOKING = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, IF(name = ?, \"selected=''\", \"\") AS selected FROM parameter_details WHERE name NOT IN (SELECT rm1.priority FROM rms_booking_detail rm1 WHERE rm1.flag = '0' ) AND master_code = ? ORDER BY detailCode";
    private static final String SQL_GET_GROUP_PARAMETER_DETAIL_LIST_FOR_PRIORITY_BOOKING_2 = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, IF(name = ?, \"selected=''\", \"\") AS selected, IF(name IN (SELECT rm1.priority FROM rms_booking_detail rm1 WHERE rm1.flag = '0' ), \"disabled\", \"\") AS disabled FROM parameter_details WHERE master_code = ? ORDER BY detailCode";
    private static final String SQL_GET_COUNT_MASTER_CODE_AND_NAME = "SELECT COUNT(*) AS count FROM parameter_details inc WHERE inc.master_code = ? AND name = ?";

    public ParameterDetails getParameterDetails(String parameterDetailsId) {
        ParameterDetails parameterDetails = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_PARAMETER_DETAILS)) {
            ps.setString(1, parameterDetailsId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    parameterDetails = new ParameterDetails();
                    parameterDetails.setId(rs.getString("id"));
                    parameterDetails.setMasterCode(rs.getString("master_code"));
                    parameterDetails.setDetailCode(rs.getString("detail_code"));
                    parameterDetails.setName(rs.getString("name"));
                    parameterDetails.setRemarks(rs.getString("remarks"));
                    parameterDetails.setCreatedBy(rs.getString("created_by"));
                    parameterDetails.setCreatedDate(rs.getString("created_date"));
                    parameterDetails.setModifiedBy(rs.getString("modified_by"));
                    parameterDetails.setModifiedDate(rs.getString("modified_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter detail. id={}", parameterDetailsId, e);
        }
        return parameterDetails;
    }

    public List<ParameterDetails> getParameterDetailsList() {
        List<ParameterDetails> parameterDetailsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_PARAMETER_DETAILS_LIST); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ParameterDetails parameterDetails = new ParameterDetails();
                parameterDetails.setId(rs.getString("id"));
                parameterDetails.setMasterCode(rs.getString("master_code"));
                parameterDetails.setDetailCode(rs.getString("detail_code"));
                parameterDetails.setName(rs.getString("name"));
                parameterDetails.setRemarks(rs.getString("remarks"));
                parameterDetails.setCreatedBy(rs.getString("created_by"));
                parameterDetails.setCreatedDate(rs.getString("created_date"));
                parameterDetails.setModifiedBy(rs.getString("modified_by"));
                parameterDetails.setModifiedDate(rs.getString("modified_date"));
                parameterDetailsList.add(parameterDetails);
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter details list.", e);
        }
        return parameterDetailsList;
    }

    public List<ParameterDetails> getParameterDetailsListByMasterCode(String masterCode) {
        List<ParameterDetails> parameterDetailsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_PARAMETER_DETAILS_LIST_BY_MASTER_CODE)) {
            ps.setString(1, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParameterDetails parameterDetails = new ParameterDetails();
                    parameterDetails.setId(rs.getString("id"));
                    parameterDetails.setMasterCode(rs.getString("master_code"));
                    parameterDetails.setDetailCode(rs.getString("detail_code"));
                    parameterDetails.setName(rs.getString("name"));
                    parameterDetails.setRemarks(rs.getString("remarks"));
                    parameterDetails.setCreatedBy(rs.getString("created_by"));
                    parameterDetails.setCreatedDate(rs.getString("created_date"));
                    parameterDetails.setModifiedBy(rs.getString("modified_by"));
                    parameterDetails.setModifiedDate(rs.getString("modified_date"));
                    parameterDetailsList.add(parameterDetails);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter details list. masterCode={}", masterCode, e);
        }
        return parameterDetailsList;
    }

    public String getNextDetailCode(String masterCode) {
        String code = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_NEXT_DETAIL_CODE)) {
            ps.setString(1, masterCode);
            ps.setString(2, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    code = rs.getString("code");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving next detail code. masterCode={}", masterCode, e);
        }
        return code;
    }

    public Integer getCountByMasterCode(String masterCode) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_BY_MASTER_CODE)) {
            ps.setString(1, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving parameter details count. masterCode={}", masterCode, e);
        }
        return count;
    }

    public List<ParameterDetails> getGroupParameterDetailList(String name, String masterCode) {
        List<ParameterDetails> parameterDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP_PARAMETER_DETAIL_LIST)) {
            ps.setString(1, name);
            ps.setString(2, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParameterDetails parameterDetails = new ParameterDetails(
                            rs.getString("id"),
                            rs.getString("masterCode"),
                            rs.getString("detailCode"),
                            rs.getString("name"),
                            rs.getString("selected")
                    );
                    parameterDetailList.add(parameterDetails);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving group parameter detail list. name={}, masterCode={}", name, masterCode, e);
        }
        return parameterDetailList;
    }

    public List<ParameterDetails> getActivityParameter(String name, String masterCode) {
        List<ParameterDetails> parameterDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ACTIVITY_PARAMETER)) {
            ps.setString(1, name);
            ps.setString(2, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParameterDetails parameterDetails = new ParameterDetails(
                            rs.getString("id"),
                            rs.getString("masterCode"),
                            rs.getString("detailCode"),
                            rs.getString("name"),
                            rs.getString("selected"),
                            rs.getString("remarks")
                    );
                    parameterDetailList.add(parameterDetails);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving activity parameter. name={}, masterCode={}", name, masterCode, e);
        }
        return parameterDetailList;
    }

    public List<ParameterDetails> getGroupParameterDetailListForPriorityBooking(String name, String masterCode) {
        List<ParameterDetails> parameterDetailList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP_PARAMETER_DETAIL_LIST_FOR_PRIORITY_BOOKING)) {
            ps.setString(1, name);
            ps.setString(2, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParameterDetails parameterDetails = new ParameterDetails(
                            rs.getString("id"),
                            rs.getString("masterCode"),
                            rs.getString("detailCode"),
                            rs.getString("name"),
                            rs.getString("selected")
                    );
                    parameterDetailList.add(parameterDetails);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving priority booking parameter details. name={}, masterCode={}", name, masterCode, e);
        }
        return parameterDetailList;
    }

    public List<ParameterDetails> getGroupParameterDetailListForPriorityBooking2(String name, String masterCode) {
        List<ParameterDetails> parameterDetailsList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_GROUP_PARAMETER_DETAIL_LIST_FOR_PRIORITY_BOOKING_2)) {
            ps.setString(1, name);
            ps.setString(2, masterCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ParameterDetails parameterDetails = new ParameterDetails();
                    parameterDetails.setId(rs.getString("id"));
                    parameterDetails.setMasterCode(rs.getString("masterCode"));
                    parameterDetails.setDetailCode(rs.getString("detailCode"));
                    parameterDetails.setName(rs.getString("name"));
                    parameterDetails.setSelected(rs.getString("selected"));
                    parameterDetails.setDisabled(rs.getString("disabled"));
                    parameterDetailsList.add(parameterDetails);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving priority booking parameter details. name={}, masterCode={}", name, masterCode, e);
        }
        return parameterDetailsList;
    }

    public Integer getCountMasterCodeAndName(String masterCode, String name) {
        Integer count = 0;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_MASTER_CODE_AND_NAME)) {
            ps.setString(1, masterCode);
            ps.setString(2, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving count by masterCode and name. masterCode={}, name={}", masterCode, name, e);
        }
        return count;
    }

}