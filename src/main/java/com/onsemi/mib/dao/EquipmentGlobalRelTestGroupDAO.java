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
import com.onsemi.mib.model.EquipmentGlobalRelTestGroup;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentGlobalRelTestGroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentGlobalRelTestGroupDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentGlobalRelTestGroupDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentGlobalRelTestGroup(EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_global_rel_test_group (spts_guid, created_date, rel_test_group_name, grtg_authorization_guid, modified_date, modified_by, modified_site_id) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentglobalRelTestGroup.getSptsGuid());
            ps.setString(2, equipmentglobalRelTestGroup.getCreatedDate());
            ps.setString(3, equipmentglobalRelTestGroup.getRelTestGroupName());
            ps.setString(4, equipmentglobalRelTestGroup.getGrtgAuthorizationGuid());
            ps.setString(5, equipmentglobalRelTestGroup.getModifiedDate());
            ps.setString(6, equipmentglobalRelTestGroup.getModifiedBy());
            ps.setString(7, equipmentglobalRelTestGroup.getModifiedSiteId());
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

    public QueryResult updateEquipmentGlobalRelTestGroup(EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_global_rel_test_group SET spts_guid = ?, created_date = ?, rel_test_group_name = ?, grtg_authorization_guid = ?, modified_date = ?, modified_by = ?, modified_site_id = ? WHERE id = ?"
            );
            ps.setString(1, equipmentglobalRelTestGroup.getSptsGuid());
            ps.setString(2, equipmentglobalRelTestGroup.getCreatedDate());
            ps.setString(3, equipmentglobalRelTestGroup.getRelTestGroupName());
            ps.setString(4, equipmentglobalRelTestGroup.getGrtgAuthorizationGuid());
            ps.setString(5, equipmentglobalRelTestGroup.getModifiedDate());
            ps.setString(6, equipmentglobalRelTestGroup.getModifiedBy());
            ps.setString(7, equipmentglobalRelTestGroup.getModifiedSiteId());
            ps.setString(8, equipmentglobalRelTestGroup.getId());
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

    public QueryResult updateEquipmentGlobalRelTestGroupByRelTestGroupName(EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_global_rel_test_group SET spts_guid = ?, created_date = ?, rel_test_group_name = ?, grtg_authorization_guid = ?, modified_date = ?, modified_by = ?, modified_site_id = ? WHERE rel_test_group_name = ?"
            );
            ps.setString(1, equipmentglobalRelTestGroup.getSptsGuid());
            ps.setString(2, equipmentglobalRelTestGroup.getCreatedDate());
            ps.setString(3, equipmentglobalRelTestGroup.getRelTestGroupName());
            ps.setString(4, equipmentglobalRelTestGroup.getGrtgAuthorizationGuid());
            ps.setString(5, equipmentglobalRelTestGroup.getModifiedDate());
            ps.setString(6, equipmentglobalRelTestGroup.getModifiedBy());
            ps.setString(7, equipmentglobalRelTestGroup.getModifiedSiteId());
            ps.setString(8, equipmentglobalRelTestGroup.getRelTestGroupName());
//             ps.setString(8, equipmentglobalRelTestGroup.getId());
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

    public QueryResult updateEquipmentGlobalRelTestGroupByGuid(EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_global_rel_test_group SET spts_guid = ?, created_date = ?, rel_test_group_name = ?, grtg_authorization_guid = ?, modified_date = ?, modified_by = ?, modified_site_id = ? WHERE spts_guid = ?"
            );
            ps.setString(1, equipmentglobalRelTestGroup.getSptsGuid());
            ps.setString(2, equipmentglobalRelTestGroup.getCreatedDate());
            ps.setString(3, equipmentglobalRelTestGroup.getRelTestGroupName());
            ps.setString(4, equipmentglobalRelTestGroup.getGrtgAuthorizationGuid());
            ps.setString(5, equipmentglobalRelTestGroup.getModifiedDate());
            ps.setString(6, equipmentglobalRelTestGroup.getModifiedBy());
            ps.setString(7, equipmentglobalRelTestGroup.getModifiedSiteId());
            ps.setString(8, equipmentglobalRelTestGroup.getSptsGuid());
//             ps.setString(8, equipmentglobalRelTestGroup.getId());
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

    public QueryResult deleteEquipmentGlobalRelTestGroup(String equipmentglobalRelTestGroupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_global_rel_test_group WHERE id = '" + equipmentglobalRelTestGroupId + "'"
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

    public QueryResult deleteEquipmentGlobalRelTestGroupByGuid(String sptsGuid) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_global_rel_test_group WHERE spts_guid = '" + sptsGuid + "'"
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

    public EquipmentGlobalRelTestGroup getEquipmentGlobalRelTestGroup(String equipmentglobalRelTestGroupId) {
        String sql = "SELECT * FROM equipment_global_rel_test_group WHERE id = '" + equipmentglobalRelTestGroupId + "'";
        EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentglobalRelTestGroup = new EquipmentGlobalRelTestGroup();
                equipmentglobalRelTestGroup.setId(rs.getString("id"));
                equipmentglobalRelTestGroup.setSptsGuid(rs.getString("spts_guid"));
                equipmentglobalRelTestGroup.setCreatedDate(rs.getString("created_date"));
                equipmentglobalRelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
                equipmentglobalRelTestGroup.setGrtgAuthorizationGuid(rs.getString("grtg_authorization_guid"));
                equipmentglobalRelTestGroup.setModifiedDate(rs.getString("modified_date"));
                equipmentglobalRelTestGroup.setModifiedBy(rs.getString("modified_by"));
                equipmentglobalRelTestGroup.setModifiedSiteId(rs.getString("modified_site_id"));
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
        return equipmentglobalRelTestGroup;
    }

    public EquipmentGlobalRelTestGroup getEquipmentGlobalRelTestGroupByRelTestGroupName(String relTestGroupName) {
        String sql = "SELECT * FROM equipment_global_rel_test_group WHERE rel_test_group_name = '" + relTestGroupName + "'";
        EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentglobalRelTestGroup = new EquipmentGlobalRelTestGroup();
                equipmentglobalRelTestGroup.setId(rs.getString("id"));
                equipmentglobalRelTestGroup.setSptsGuid(rs.getString("spts_guid"));
                equipmentglobalRelTestGroup.setCreatedDate(rs.getString("created_date"));
                equipmentglobalRelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
                equipmentglobalRelTestGroup.setGrtgAuthorizationGuid(rs.getString("grtg_authorization_guid"));
                equipmentglobalRelTestGroup.setModifiedDate(rs.getString("modified_date"));
                equipmentglobalRelTestGroup.setModifiedBy(rs.getString("modified_by"));
                equipmentglobalRelTestGroup.setModifiedSiteId(rs.getString("modified_site_id"));
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
        return equipmentglobalRelTestGroup;
    }

    public List<EquipmentGlobalRelTestGroup> getEquipmentGlobalRelTestGroupList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_global_rel_test_group ORDER BY id ASC";
        List<EquipmentGlobalRelTestGroup> equipmentglobalRelTestGroupList = new ArrayList<EquipmentGlobalRelTestGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentGlobalRelTestGroup equipmentglobalRelTestGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentglobalRelTestGroup = new EquipmentGlobalRelTestGroup();
                equipmentglobalRelTestGroup.setId(rs.getString("id"));
                equipmentglobalRelTestGroup.setSptsGuid(rs.getString("spts_guid"));
                equipmentglobalRelTestGroup.setCreatedDate(rs.getString("createdDate"));
                equipmentglobalRelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
                equipmentglobalRelTestGroup.setGrtgAuthorizationGuid(rs.getString("grtg_authorization_guid"));
                equipmentglobalRelTestGroup.setModifiedDate(rs.getString("modified_date"));
                equipmentglobalRelTestGroup.setModifiedBy(rs.getString("modified_by"));
                equipmentglobalRelTestGroup.setModifiedSiteId(rs.getString("modified_site_id"));
                equipmentglobalRelTestGroupList.add(equipmentglobalRelTestGroup);
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
        return equipmentglobalRelTestGroupList;
    }

    public Integer getCountGlobalRelTestGroup(String relTestGroup) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_global_rel_test_group inc WHERE inc.rel_test_group_name = '" + relTestGroup + "'"
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

    public Integer getCountGlobalRelTestGroupByGuid(String guid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_global_rel_test_group inc WHERE inc.spts_guid = '" + guid + "'"
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
