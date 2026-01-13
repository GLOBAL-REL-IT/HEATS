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
import com.onsemi.mib.model.EquipmentLog;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentLogDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentLogDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentLogDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentLog(EquipmentLog equipmentlog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_log (equipment_id, detail, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentlog.getEquipmentId());
            ps.setString(2, equipmentlog.getDetail());
            ps.setString(3, equipmentlog.getCreatedBy());
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

    public QueryResult updateEquipmentLog(EquipmentLog equipmentlog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_log SET equipment_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?"
            );
            ps.setString(1, equipmentlog.getEquipmentId());
            ps.setString(2, equipmentlog.getDetail());
            ps.setString(3, equipmentlog.getCreatedBy());
            ps.setString(4, equipmentlog.getCreatedDate());
            ps.setString(5, equipmentlog.getId());
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

    public QueryResult deleteEquipmentLog(String equipmentlogId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_log WHERE id = '" + equipmentlogId + "'"
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

    public EquipmentLog getEquipmentLog(String equipmentlogId) {
        String sql = "SELECT * FROM equipment_log WHERE id = '" + equipmentlogId + "'";
        EquipmentLog equipmentlog = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentlog = new EquipmentLog();
                equipmentlog.setId(rs.getString("id"));
                equipmentlog.setEquipmentId(rs.getString("equipment_id"));
                equipmentlog.setDetail(rs.getString("detail"));
                equipmentlog.setCreatedBy(rs.getString("created_by"));
                equipmentlog.setCreatedDate(rs.getString("created_date"));
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
        return equipmentlog;
    }

    public List<EquipmentLog> getEquipmentLogList() {
        String sql = "SELECT * FROM equipment_log ORDER BY id ASC";
        List<EquipmentLog> equipmentlogList = new ArrayList<EquipmentLog>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentLog equipmentlog;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentlog = new EquipmentLog();
                equipmentlog.setId(rs.getString("id"));
                equipmentlog.setEquipmentId(rs.getString("equipment_id"));
                equipmentlog.setDetail(rs.getString("detail"));
                equipmentlog.setCreatedBy(rs.getString("created_by"));
                equipmentlog.setCreatedDate(rs.getString("created_date"));
                equipmentlogList.add(equipmentlog);
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
        return equipmentlogList;
    }
}
