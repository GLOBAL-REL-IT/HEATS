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
import com.onsemi.mib.model.EquipmentMonitoring;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentMonitoringDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentMonitoringDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentMonitoringDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentMonitoring(EquipmentMonitoring equipmentmonitoring) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_monitoring (spts_pkid, name, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentmonitoring.getSptsPkid());
            ps.setString(2, equipmentmonitoring.getName());
            ps.setString(3, equipmentmonitoring.getCreatedBy());
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

    public QueryResult updateEquipmentMonitoring(EquipmentMonitoring equipmentmonitoring) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_monitoring SET spts_pkid = ?, name = ? WHERE id = ?"
            );
            ps.setString(1, equipmentmonitoring.getSptsPkid());
            ps.setString(2, equipmentmonitoring.getName());
            ps.setString(3, equipmentmonitoring.getId());
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

    public QueryResult updateEquipmentMonitoringBySptsPkid(EquipmentMonitoring equipmentmonitoring) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_monitoring SET name = ? WHERE spts_pkid = ?"
            );
            ps.setString(1, equipmentmonitoring.getName());
            ps.setString(2, equipmentmonitoring.getSptsPkid());
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

    public QueryResult deleteEquipmentMonitoring(String equipmentmonitoringId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_monitoring WHERE id = '" + equipmentmonitoringId + "'"
            );
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

    public EquipmentMonitoring getEquipmentMonitoring(String equipmentmonitoringId) {
        String sql = "SELECT * FROM equipment_monitoring WHERE id = '" + equipmentmonitoringId + "'";
        EquipmentMonitoring equipmentmonitoring = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentmonitoring = new EquipmentMonitoring();
                equipmentmonitoring.setId(rs.getString("id"));
                equipmentmonitoring.setSptsPkid(rs.getString("spts_pkid"));
                equipmentmonitoring.setName(rs.getString("name"));
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
        return equipmentmonitoring;
    }

    public List<EquipmentMonitoring> getEquipmentMonitoringList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_monitoring ORDER BY id ASC";
        List<EquipmentMonitoring> equipmentmonitoringList = new ArrayList<EquipmentMonitoring>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentMonitoring equipmentmonitoring;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentmonitoring = new EquipmentMonitoring();
                equipmentmonitoring.setId(rs.getString("id"));
                equipmentmonitoring.setSptsPkid(rs.getString("spts_pkid"));
                equipmentmonitoring.setName(rs.getString("name"));
                equipmentmonitoring.setCreatedBy(rs.getString("created_by"));
                equipmentmonitoring.setCreatedDate(rs.getString("createdDate"));
                equipmentmonitoringList.add(equipmentmonitoring);
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
        return equipmentmonitoringList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_monitoring inc WHERE inc.spts_pkid = '" + pkid + "'"
            );
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

    public Integer getCountMonitoringName(String eqptMonitoring) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_monitoring inc WHERE inc.name = '" + eqptMonitoring + "'"
            );
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
}
