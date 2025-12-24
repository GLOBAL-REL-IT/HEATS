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
import com.onsemi.mib.model.EquipmentTech;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentTechDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentTechDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentTechDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentTech(EquipmentTech equipmenttech) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_tech (spts_pkid, name, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmenttech.getSptsPkid());
            ps.setString(2, equipmenttech.getName());
            ps.setString(3, equipmenttech.getCreatedBy());
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

    public QueryResult updateEquipmentTech(EquipmentTech equipmenttech) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_tech SET spts_pkid = ?, name = ? WHERE id = ?"
            );
            ps.setString(1, equipmenttech.getSptsPkid());
            ps.setString(2, equipmenttech.getName());
            ps.setString(3, equipmenttech.getId());
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

    public QueryResult updateEquipmentTechBySptsPkid(EquipmentTech equipmenttech) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_tech SET name = ? WHERE spts_pkid = ?"
            );
            ps.setString(1, equipmenttech.getName());
            ps.setString(2, equipmenttech.getSptsPkid());
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

    public QueryResult deleteEquipmentTech(String equipmenttechId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_tech WHERE id = '" + equipmenttechId + "'"
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

    public EquipmentTech getEquipmentTech(String equipmenttechId) {
        String sql = "SELECT * FROM equipment_tech WHERE id = '" + equipmenttechId + "'";
        EquipmentTech equipmenttech = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmenttech = new EquipmentTech();
                equipmenttech.setId(rs.getString("id"));
                equipmenttech.setSptsPkid(rs.getString("spts_pkid"));
                equipmenttech.setName(rs.getString("name"));
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
        return equipmenttech;
    }

    public List<EquipmentTech> getEquipmentTechList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_tech ORDER BY id ASC";
        List<EquipmentTech> equipmenttechList = new ArrayList<EquipmentTech>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentTech equipmenttech;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmenttech = new EquipmentTech();
                equipmenttech.setId(rs.getString("id"));
                equipmenttech.setSptsPkid(rs.getString("spts_pkid"));
                equipmenttech.setName(rs.getString("name"));
                equipmenttech.setCreatedBy(rs.getString("created_by"));
                equipmenttech.setCreatedDate(rs.getString("createdDate"));
                equipmenttechList.add(equipmenttech);
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
        return equipmenttechList;
    }

    public List<EquipmentTech> getEquipmentTechList(String eqptTechPkid) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate, IF(spts_pkid=\"" + eqptTechPkid + "\",\"selected=''\",\"\") AS selected FROM equipment_tech ORDER BY id ASC";
        List<EquipmentTech> equipmenttechList = new ArrayList<EquipmentTech>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentTech equipmenttech;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmenttech = new EquipmentTech();
                equipmenttech.setId(rs.getString("id"));
                equipmenttech.setSptsPkid(rs.getString("spts_pkid"));
                equipmenttech.setName(rs.getString("name"));
                equipmenttech.setCreatedBy(rs.getString("created_by"));
                equipmenttech.setCreatedDate(rs.getString("createdDate"));
                equipmenttech.setSelected(rs.getString("selected"));
                equipmenttechList.add(equipmenttech);
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
        return equipmenttechList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_tech inc WHERE inc.spts_pkid = '" + pkid + "'"
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

    public Integer getCountTechName(String eqptTech) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_tech inc WHERE inc.name = '" + eqptTech + "'"
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
