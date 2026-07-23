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
    private final Connection conn;
    private final DataSource dataSource;

    public ParameterDetailsDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertParameterDetails(ParameterDetails parameterDetails) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO parameter_details (master_code, detail_code, name, remarks, created_by, created_date) VALUES (?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, parameterDetails.getMasterCode());
            ps.setString(2, parameterDetails.getDetailCode());
            ps.setString(3, parameterDetails.getName());
            ps.setString(4, parameterDetails.getRemarks());
            ps.setString(5, parameterDetails.getCreatedBy());
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult updateParameterDetails(ParameterDetails parameterDetails) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE parameter_details SET master_code = ?, detail_code = ?, name = ?, remarks = ?, modified_by = ?, modified_date = NOW() WHERE id = ?"
            );
            ps.setString(1, parameterDetails.getMasterCode());
            ps.setString(2, parameterDetails.getDetailCode());
            ps.setString(3, parameterDetails.getName());
            ps.setString(4, parameterDetails.getRemarks());
            ps.setString(5, parameterDetails.getModifiedBy());
            ps.setString(6, parameterDetails.getId());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public QueryResult deleteParameterDetails(String parameterDetailsId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM parameter_details WHERE id = ?"
            );
            ps.setString(1, parameterDetailsId);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public ParameterDetails getParameterDetails(String parameterDetailsId) {
        String sql = "SELECT * FROM parameter_details WHERE id = '? ";
        ParameterDetails parameterDetails = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, parameterDetailsId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetails;
    }

    public List<ParameterDetails> getParameterDetailsList() {
        String sql = "SELECT * FROM parameter_details ORDER BY id ASC";
        List<ParameterDetails> parameterDetailsList = new ArrayList<ParameterDetails>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ParameterDetails parameterDetails;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                parameterDetailsList.add(parameterDetails);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetailsList;
    }

    public List<ParameterDetails> getParameterDetailsListByMasterCode(String masterCode) {
        String sql = "SELECT * FROM parameter_details WHERE master_code = ? ORDER BY id ASC";
        List<ParameterDetails> parameterDetailsList = new ArrayList<ParameterDetails>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, masterCode);
            ParameterDetails parameterDetails;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                parameterDetailsList.add(parameterDetails);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetailsList;
    }

    public String getNextDetailCode(String masterCode) {
        String sql = "SELECT LPAD(IFNULL(MAX(m.detail_code)+1, CONCAT(?,'001')),6,'0') AS code "
                + "FROM parameter_details m "
                + "WHERE m.master_code = ? ";
        String code = "";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, masterCode);
            ps.setString(2, masterCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                code = rs.getString("code");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return code;
    }

    public QueryResult removeDetailsByMasterCode(String masterCode) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM parameter_details WHERE master_code = ? "
            );
            ps.setString(1, masterCode);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public Integer getCountByMasterCode(String masterCode) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM parameter_details WHERE master_code = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, masterCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public List<ParameterDetails> getGroupParameterDetailList(String name, String masterCode) {
        String sql = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, IF(name=\"" + name + "\",\"selected=''\",\"\") AS selected FROM parameter_details "
                + "Where master_code = '" + masterCode + "' ORDER BY name";
        List<ParameterDetails> parameterDetailList = new ArrayList<ParameterDetails>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ParameterDetails parameterDetails;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parameterDetails = new ParameterDetails(
                        rs.getString("id"),
                        rs.getString("masterCode"),
                        rs.getString("detailCode"),
                        rs.getString("name"),
                        rs.getString("selected")
                );
                parameterDetailList.add(parameterDetails);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetailList;
    }

    public List<ParameterDetails> getActivityParameter(String name, String masterCode) {
        String sql = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, remarks AS remarks, IF(name=\"" + name + "\",\"selected=''\",\"\") AS selected FROM parameter_details "
                + " WHERE master_code = '" + masterCode + "' ORDER BY detail_code";
        List<ParameterDetails> parameterDetailList = new ArrayList<ParameterDetails>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ParameterDetails parameterDetails;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parameterDetails = new ParameterDetails(
                        rs.getString("id"),
                        rs.getString("masterCode"),
                        rs.getString("detailCode"),
                        rs.getString("name"),
                        rs.getString("selected"),
                        rs.getString("remarks")
                );
                parameterDetailList.add(parameterDetails);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetailList;
    }

    public List<ParameterDetails> getGroupParameterDetailListForPriorityBooking(String name, String masterCode) {
        String sql = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, IF(name=\"" + name + "\",\"selected=''\",\"\") AS selected FROM parameter_details "
                + "Where name NOT IN (SELECT rm1.priority FROM rms_booking_detail rm1 WHERE rm1.flag = '0') AND master_code = '" + masterCode + "' ORDER BY detailCode";
        List<ParameterDetails> parameterDetailList = new ArrayList<ParameterDetails>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ParameterDetails parameterDetails;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parameterDetails = new ParameterDetails(
                        rs.getString("id"),
                        rs.getString("masterCode"),
                        rs.getString("detailCode"),
                        rs.getString("name"),
                        rs.getString("selected")
                );
                parameterDetailList.add(parameterDetails);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetailList;
    }

    public List<ParameterDetails> getGroupParameterDetailListForPriorityBooking2(String name, String masterCode) {
        String sql = "SELECT id, master_code AS masterCode, detail_code AS detailCode, name AS name, "
                + "IF(name=\"" + name + "\",\"selected=''\",\"\") AS selected, "
                + "IF(name IN (SELECT rm1.priority FROM rms_booking_detail rm1 WHERE rm1.flag = '0'),\"disabled\",\"\") AS disabled, "
                + "FROM parameter_details "
                + "Where master_code = '" + masterCode + "' ORDER BY detailCode";
        List<ParameterDetails> parameterDetailsList = new ArrayList<ParameterDetails>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ParameterDetails parameterDetails;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                parameterDetails = new ParameterDetails();
                parameterDetails.setId(rs.getString("id"));
                parameterDetails.setMasterCode(rs.getString("masterCode"));
                parameterDetails.setDetailCode(rs.getString("detailCode"));
                parameterDetails.setName(rs.getString("name"));
                parameterDetails.setSelected(rs.getString("selected"));
                parameterDetails.setDisabled(rs.getString("disabled"));
                parameterDetailsList.add(parameterDetails);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return parameterDetailsList;
    }

    public Integer getCountMasterCodeAndName(String masterCode, String name) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM parameter_details inc WHERE inc.master_code = ? AND name = ? "
            );
            ps.setString(1, masterCode);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();

            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

//    public List<ParameterDetails> getNameByDetailCode(String detailCode) {
//        String sql = "SELECT name AS name FROM cdars_parameter_details WHERE detail_code = '" + detailCode + "'";
//        List<ParameterDetails> parameterDetailList = new ArrayList<ParameterDetails>();
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ParameterDetails parameterDetails;
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                parameterDetails = new ParameterDetails(
//                        rs.getString("name")
//                );
//                parameterDetailList.add(parameterDetails);
//            }
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return parameterDetailList;
//    }

}