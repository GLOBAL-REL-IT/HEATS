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
import com.onsemi.mib.model.EquipmentFamily;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentFamilyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentFamilyDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentFamilyDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentFamily(EquipmentFamily equipmentfamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_family (spts_pkid, family_name, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentfamily.getSptsPkid());
            ps.setString(2, equipmentfamily.getFamilyName());
            ps.setString(3, equipmentfamily.getCreatedBy());
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

    public QueryResult updateEquipmentFamily(EquipmentFamily equipmentfamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_family SET spts_pkid = ?, family_name = ? WHERE id = ?"
            );
            ps.setString(1, equipmentfamily.getSptsPkid());
            ps.setString(2, equipmentfamily.getFamilyName());
            ps.setString(3, equipmentfamily.getId());
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

    public QueryResult updateEquipmentFamilyBySptsPkid(EquipmentFamily equipmentfamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_family SET family_name = ? WHERE spts_pkid = ?"
            );
            ps.setString(1, equipmentfamily.getFamilyName());
            ps.setString(2, equipmentfamily.getSptsPkid());
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

    public QueryResult deleteEquipmentFamily(String equipmentfamilyId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_family WHERE id = '" + equipmentfamilyId + "'"
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

    public EquipmentFamily getEquipmentFamily(String equipmentfamilyId) {
        String sql = "SELECT * FROM equipment_family WHERE id = '" + equipmentfamilyId + "'";
        EquipmentFamily equipmentfamily = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentfamily = new EquipmentFamily();
                equipmentfamily.setId(rs.getString("id"));
                equipmentfamily.setSptsPkid(rs.getString("spts_pkid"));
                equipmentfamily.setFamilyName(rs.getString("family_name"));
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
        return equipmentfamily;
    }

    public List<EquipmentFamily> getEquipmentFamilyList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_family ORDER BY family_name ASC";
        List<EquipmentFamily> equipmentfamilyList = new ArrayList<EquipmentFamily>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentFamily equipmentfamily;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentfamily = new EquipmentFamily();
                equipmentfamily.setId(rs.getString("id"));
                equipmentfamily.setSptsPkid(rs.getString("spts_pkid"));
                equipmentfamily.setFamilyName(rs.getString("family_name"));
                equipmentfamily.setCreatedBy(rs.getString("created_by"));
                equipmentfamily.setCreatedDate(rs.getString("createdDate"));
                equipmentfamilyList.add(equipmentfamily);
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
        return equipmentfamilyList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_family inc WHERE inc.spts_pkid = '" + pkid + "'"
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

    public Integer getCountFamilyName(String familyName) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_family inc WHERE inc.family_name = '" + familyName + "'"
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
