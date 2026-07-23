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
import com.onsemi.mib.model.Hardware;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardwareDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public HardwareDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertHardware(Hardware hardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO hardware (item_id, hw_config_id, hardware_name, alu, mfg_date, registered_date, rms_event, status, enable_alu, movement_alu, created_date, created_by, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, hardware.getItemId());
            ps.setString(2, hardware.getHwConfigId());
            ps.setString(3, hardware.getHardwareName());
            ps.setString(4, hardware.getAlu());
            ps.setString(5, hardware.getMfgDate());
            ps.setString(6, hardware.getRegisteredDate());
            ps.setString(7, hardware.getRmsEvent());
            ps.setString(8, hardware.getStatus());
            ps.setString(9, hardware.getEnableAlu());
            ps.setString(10, hardware.getMovementAlu());
            ps.setString(11, hardware.getCreatedDate());
            ps.setString(12, hardware.getCreatedBy());
            ps.setString(13, hardware.getFlag());
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

    public QueryResult updateHardware(Hardware hardware) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE hardware SET item_id = ?, hw_config_id = ?, hardware_name = ?, alu = ?, mfg_date = ?, registered_date = ?, rms_event = ?, status = ?, enable_alu = ?, movement_alu = ?, created_date = ?, created_by = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, hardware.getItemId());
            ps.setString(2, hardware.getHwConfigId());
            ps.setString(3, hardware.getHardwareName());
            ps.setString(4, hardware.getAlu());
            ps.setString(5, hardware.getMfgDate());
            ps.setString(6, hardware.getRegisteredDate());
            ps.setString(7, hardware.getRmsEvent());
            ps.setString(8, hardware.getStatus());
            ps.setString(9, hardware.getEnableAlu());
            ps.setString(10, hardware.getMovementAlu());
            ps.setString(11, hardware.getCreatedDate());
            ps.setString(12, hardware.getCreatedBy());
            ps.setString(13, hardware.getFlag());
            ps.setString(14, hardware.getId());
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

    public QueryResult deleteHardware(String hardwareId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM hardware WHERE id = ? "
            );
            ps.setString(1, hardwareId);
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

    public Hardware getHardware(String hardwareId) {
        String sql = "SELECT * FROM hardware WHERE id = ? ";
        Hardware hardware = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hardwareId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardware = new Hardware();
                hardware.setId(rs.getString("id"));
                hardware.setItemId(rs.getString("item_id"));
                hardware.setHwConfigId(rs.getString("hw_config_id"));
                hardware.setHardwareName(rs.getString("hardware_name"));
                hardware.setAlu(rs.getString("alu"));
                hardware.setMfgDate(rs.getString("mfg_date"));
                hardware.setRegisteredDate(rs.getString("registered_date"));
                hardware.setRmsEvent(rs.getString("rms_event"));
                hardware.setStatus(rs.getString("status"));
                hardware.setEnableAlu(rs.getString("enable_alu"));
                hardware.setMovementAlu(rs.getString("movement_alu"));
                hardware.setCreatedDate(rs.getString("created_date"));
                hardware.setCreatedBy(rs.getString("created_by"));
                hardware.setFlag(rs.getString("flag"));
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
        return hardware;
    }

    public List<Hardware> getHardwareList() {
        String sql = "SELECT * FROM hardware ORDER BY id ASC";
        List<Hardware> hardwareList = new ArrayList<Hardware>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Hardware hardware;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardware = new Hardware();
                hardware.setId(rs.getString("id"));
                hardware.setItemId(rs.getString("item_id"));
                hardware.setHwConfigId(rs.getString("hw_config_id"));
                hardware.setHardwareName(rs.getString("hardware_name"));
                hardware.setAlu(rs.getString("alu"));
                hardware.setMfgDate(rs.getString("mfg_date"));
                hardware.setRegisteredDate(rs.getString("registered_date"));
                hardware.setRmsEvent(rs.getString("rms_event"));
                hardware.setStatus(rs.getString("status"));
                hardware.setEnableAlu(rs.getString("enable_alu"));
                hardware.setMovementAlu(rs.getString("movement_alu"));
                hardware.setCreatedDate(rs.getString("created_date"));
                hardware.setCreatedBy(rs.getString("created_by"));
                hardware.setFlag(rs.getString("flag"));
                hardwareList.add(hardware);
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
        return hardwareList;
    }

    public List<Hardware> getHardwareListByItemId(String itemId) {
        String sql = "SELECT * FROM hardware WHERE item_id = ? ORDER BY id ASC";
        List<Hardware> hardwareList = new ArrayList<Hardware>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, itemId);
            Hardware hardware;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hardware = new Hardware();
                hardware.setId(rs.getString("id"));
                hardware.setItemId(rs.getString("item_id"));
                hardware.setHwConfigId(rs.getString("hw_config_id"));
                hardware.setHardwareName(rs.getString("hardware_name"));
                hardware.setAlu(rs.getString("alu"));
                hardware.setMfgDate(rs.getString("mfg_date"));
                hardware.setRegisteredDate(rs.getString("registered_date"));
                hardware.setRmsEvent(rs.getString("rms_event"));
                hardware.setStatus(rs.getString("status"));
                hardware.setEnableAlu(rs.getString("enable_alu"));
                hardware.setMovementAlu(rs.getString("movement_alu"));
                hardware.setCreatedDate(rs.getString("created_date"));
                hardware.setCreatedBy(rs.getString("created_by"));
                hardware.setFlag(rs.getString("flag"));
                hardwareList.add(hardware);
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
        return hardwareList;
    }

}