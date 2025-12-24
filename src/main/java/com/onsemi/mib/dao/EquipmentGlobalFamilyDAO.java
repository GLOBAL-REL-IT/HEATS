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
import com.onsemi.mib.model.EquipmentGlobalFamily;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentGlobalFamilyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentGlobalFamilyDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentGlobalFamilyDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentGlobalFamily(EquipmentGlobalFamily equipmentglobalFamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_global_family (spts_guid, created_date, family_name, gefn_authorization_guid, modified_date, modified_by, modified_site_id) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentglobalFamily.getSptsGuid());
            ps.setString(2, equipmentglobalFamily.getCreatedDate());
            ps.setString(3, equipmentglobalFamily.getFamilyName());
            ps.setString(4, equipmentglobalFamily.getGefnAuthorizationGuid());
            ps.setString(5, equipmentglobalFamily.getModifiedDate());
            ps.setString(6, equipmentglobalFamily.getModifiedBy());
            ps.setString(7, equipmentglobalFamily.getModifiedSiteId());
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

    public QueryResult updateEquipmentGlobalFamily(EquipmentGlobalFamily equipmentglobalFamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_global_family SET spts_guid = ?, created_date = ?, family_name = ?, gefn_authorization_guid = ?, modified_date = ?, modified_by = ?, modified_site_id = ? WHERE id = ?"
            );
            ps.setString(1, equipmentglobalFamily.getSptsGuid());
            ps.setString(2, equipmentglobalFamily.getCreatedDate());
            ps.setString(3, equipmentglobalFamily.getFamilyName());
            ps.setString(4, equipmentglobalFamily.getGefnAuthorizationGuid());
            ps.setString(5, equipmentglobalFamily.getModifiedDate());
            ps.setString(6, equipmentglobalFamily.getModifiedBy());
            ps.setString(7, equipmentglobalFamily.getModifiedSiteId());
            ps.setString(8, equipmentglobalFamily.getId());
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

    public QueryResult updateEquipmentGlobalFamilyByFamilyName(EquipmentGlobalFamily equipmentglobalFamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_global_family SET spts_guid = ?, created_date = ?, family_name = ?, gefn_authorization_guid = ?, modified_date = ?, modified_by = ?, modified_site_id = ? WHERE family_name = ?"
            );
            ps.setString(1, equipmentglobalFamily.getSptsGuid());
            ps.setString(2, equipmentglobalFamily.getCreatedDate());
            ps.setString(3, equipmentglobalFamily.getFamilyName());
            ps.setString(4, equipmentglobalFamily.getGefnAuthorizationGuid());
            ps.setString(5, equipmentglobalFamily.getModifiedDate());
            ps.setString(6, equipmentglobalFamily.getModifiedBy());
            ps.setString(7, equipmentglobalFamily.getModifiedSiteId());
            ps.setString(8, equipmentglobalFamily.getFamilyName());
//            ps.setString(8, equipmentglobalFamily.getId());
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

    public QueryResult updateEquipmentGlobalFamilyByGuid(EquipmentGlobalFamily equipmentglobalFamily) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_global_family SET spts_guid = ?, created_date = ?, family_name = ?, gefn_authorization_guid = ?, modified_date = ?, modified_by = ?, modified_site_id = ? WHERE spts_guid = ?"
            );
            ps.setString(1, equipmentglobalFamily.getSptsGuid());
            ps.setString(2, equipmentglobalFamily.getCreatedDate());
            ps.setString(3, equipmentglobalFamily.getFamilyName());
            ps.setString(4, equipmentglobalFamily.getGefnAuthorizationGuid());
            ps.setString(5, equipmentglobalFamily.getModifiedDate());
            ps.setString(6, equipmentglobalFamily.getModifiedBy());
            ps.setString(7, equipmentglobalFamily.getModifiedSiteId());
            ps.setString(8, equipmentglobalFamily.getSptsGuid());
//            ps.setString(8, equipmentglobalFamily.getId());
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

    public QueryResult deleteEquipmentGlobalFamily(String equipmentglobalFamilyId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_global_family WHERE id = '" + equipmentglobalFamilyId + "'"
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

    public QueryResult deleteEquipmentGlobalFamilyByGuid(String guid) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_global_family WHERE spts_guid = '" + guid + "'"
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

    public EquipmentGlobalFamily getEquipmentGlobalFamily(String equipmentglobalFamilyId) {
        String sql = "SELECT * FROM equipment_global_family WHERE id = '" + equipmentglobalFamilyId + "'";
        EquipmentGlobalFamily equipmentglobalFamily = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentglobalFamily = new EquipmentGlobalFamily();
                equipmentglobalFamily.setId(rs.getString("id"));
                equipmentglobalFamily.setSptsGuid(rs.getString("spts_guid"));
                equipmentglobalFamily.setCreatedDate(rs.getString("created_date"));
                equipmentglobalFamily.setFamilyName(rs.getString("family_name"));
                equipmentglobalFamily.setGefnAuthorizationGuid(rs.getString("gefn_authorization_guid"));
                equipmentglobalFamily.setModifiedDate(rs.getString("modified_date"));
                equipmentglobalFamily.setModifiedBy(rs.getString("modified_by"));
                equipmentglobalFamily.setModifiedSiteId(rs.getString("modified_site_id"));
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
        return equipmentglobalFamily;
    }

    public EquipmentGlobalFamily getEquipmentGlobalFamilyByFamilyName(String familyName) {
        String sql = "SELECT * FROM equipment_global_family WHERE family_name = '" + familyName + "'";
        EquipmentGlobalFamily equipmentglobalFamily = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentglobalFamily = new EquipmentGlobalFamily();
                equipmentglobalFamily.setId(rs.getString("id"));
                equipmentglobalFamily.setSptsGuid(rs.getString("spts_guid"));
                equipmentglobalFamily.setCreatedDate(rs.getString("created_date"));
                equipmentglobalFamily.setFamilyName(rs.getString("family_name"));
                equipmentglobalFamily.setGefnAuthorizationGuid(rs.getString("gefn_authorization_guid"));
                equipmentglobalFamily.setModifiedDate(rs.getString("modified_date"));
                equipmentglobalFamily.setModifiedBy(rs.getString("modified_by"));
                equipmentglobalFamily.setModifiedSiteId(rs.getString("modified_site_id"));
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
        return equipmentglobalFamily;
    }

    public List<EquipmentGlobalFamily> getEquipmentGlobalFamilyList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_global_family ORDER BY id ASC";
        List<EquipmentGlobalFamily> equipmentglobalFamilyList = new ArrayList<EquipmentGlobalFamily>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentGlobalFamily equipmentglobalFamily;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentglobalFamily = new EquipmentGlobalFamily();
                equipmentglobalFamily.setId(rs.getString("id"));
                equipmentglobalFamily.setSptsGuid(rs.getString("spts_guid"));
                equipmentglobalFamily.setCreatedDate(rs.getString("createdDate"));
                equipmentglobalFamily.setFamilyName(rs.getString("family_name"));
                equipmentglobalFamily.setGefnAuthorizationGuid(rs.getString("gefn_authorization_guid"));
                equipmentglobalFamily.setModifiedDate(rs.getString("modified_date"));
                equipmentglobalFamily.setModifiedBy(rs.getString("modified_by"));
                equipmentglobalFamily.setModifiedSiteId(rs.getString("modified_site_id"));
                equipmentglobalFamilyList.add(equipmentglobalFamily);
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
        return equipmentglobalFamilyList;
    }

    public Integer getCountGlobalFamilyName(String familyName) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_global_family inc WHERE inc.family_name = '" + familyName + "'"
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

    public Integer getCountGlobalFamilyNameByGuid(String sptsGuid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_global_family inc WHERE inc.spts_guid = '" + sptsGuid + "'"
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
