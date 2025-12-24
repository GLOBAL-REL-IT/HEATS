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
import com.onsemi.mib.model.EquipmentViMonitoring;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentViMonitoringDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentViMonitoringDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentViMonitoringDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentViMonitoring(EquipmentViMonitoring equipmentviMonitoring) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_vi_monitoring (spts_pkid, name, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentviMonitoring.getSptsPkid());
            ps.setString(2, equipmentviMonitoring.getName());
            ps.setString(3, equipmentviMonitoring.getCreatedBy());
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

    public QueryResult updateEquipmentViMonitoring(EquipmentViMonitoring equipmentviMonitoring) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_vi_monitoring SET spts_pkid = ?, name = ? WHERE id = ?"
            );
            ps.setString(1, equipmentviMonitoring.getSptsPkid());
            ps.setString(2, equipmentviMonitoring.getName());
            ps.setString(3, equipmentviMonitoring.getId());
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

    public QueryResult updateEquipmentViMonitoringBySptsPkid(EquipmentViMonitoring equipmentviMonitoring) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_vi_monitoring SET name = ? WHERE spts_pkid = ?"
            );
            ps.setString(1, equipmentviMonitoring.getName());
            ps.setString(2, equipmentviMonitoring.getSptsPkid());
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

    public QueryResult deleteEquipmentViMonitoring(String equipmentviMonitoringId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_vi_monitoring WHERE id = '" + equipmentviMonitoringId + "'"
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

    public EquipmentViMonitoring getEquipmentViMonitoring(String equipmentviMonitoringId) {
        String sql = "SELECT * FROM equipment_vi_monitoring WHERE id = '" + equipmentviMonitoringId + "'";
        EquipmentViMonitoring equipmentviMonitoring = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentviMonitoring = new EquipmentViMonitoring();
                equipmentviMonitoring.setId(rs.getString("id"));
                equipmentviMonitoring.setSptsPkid(rs.getString("spts_pkid"));
                equipmentviMonitoring.setName(rs.getString("name"));
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
        return equipmentviMonitoring;
    }

    public List<EquipmentViMonitoring> getEquipmentViMonitoringList() {
        String sql = "SELECT *, DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_vi_monitoring ORDER BY id ASC";
        List<EquipmentViMonitoring> equipmentviMonitoringList = new ArrayList<EquipmentViMonitoring>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentViMonitoring equipmentviMonitoring;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentviMonitoring = new EquipmentViMonitoring();
                equipmentviMonitoring.setId(rs.getString("id"));
                equipmentviMonitoring.setSptsPkid(rs.getString("spts_pkid"));
                equipmentviMonitoring.setName(rs.getString("name"));
                equipmentviMonitoring.setCreatedBy(rs.getString("created_by"));
                equipmentviMonitoring.setCreatedDate(rs.getString("createdDate"));
                equipmentviMonitoringList.add(equipmentviMonitoring);
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
        return equipmentviMonitoringList;
    }

    public List<EquipmentViMonitoring> getEquipmentViMonitoringList(String eqptViMonPkid) {
        String sql = "SELECT *, DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate, IF(spts_pkid=\"" + eqptViMonPkid + "\",\"selected=''\",\"\") AS selected FROM equipment_vi_monitoring ORDER BY id ASC";
        List<EquipmentViMonitoring> equipmentviMonitoringList = new ArrayList<EquipmentViMonitoring>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentViMonitoring equipmentviMonitoring;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentviMonitoring = new EquipmentViMonitoring();
                equipmentviMonitoring.setId(rs.getString("id"));
                equipmentviMonitoring.setSptsPkid(rs.getString("spts_pkid"));
                equipmentviMonitoring.setName(rs.getString("name"));
                equipmentviMonitoring.setCreatedBy(rs.getString("created_by"));
                equipmentviMonitoring.setCreatedDate(rs.getString("createdDate"));
                equipmentviMonitoring.setSelected(rs.getString("selected"));
                equipmentviMonitoringList.add(equipmentviMonitoring);
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
        return equipmentviMonitoringList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_vi_monitoring inc WHERE inc.spts_pkid = '" + pkid + "'"
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

    public Integer getCountViMonitoringName(String eqptViMonitoring) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_vi_monitoring inc WHERE inc.name = '" + eqptViMonitoring + "'"
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
