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
import com.onsemi.mib.model.EquipmentRelTestGroup;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquipmentRelTestGroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentRelTestGroupDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public EquipmentRelTestGroupDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertEquipmentRelTestGroup(EquipmentRelTestGroup equipmentrelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO equipment_rel_test_group (spts_pkid, rel_test_group_name, created_by, created_date) VALUES (?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, equipmentrelTestGroup.getSptsPkid());
            ps.setString(2, equipmentrelTestGroup.getRelTestGroupName());
            ps.setString(3, equipmentrelTestGroup.getCreatedBy());
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

    public QueryResult updateEquipmentRelTestGroup(EquipmentRelTestGroup equipmentrelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_rel_test_group SET spts_pkid = ?, rel_test_group_name = ? WHERE id = ?"
            );
            ps.setString(1, equipmentrelTestGroup.getSptsPkid());
            ps.setString(2, equipmentrelTestGroup.getRelTestGroupName());
            ps.setString(3, equipmentrelTestGroup.getId());
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

    public QueryResult updateEquipmentRelTestGroupBySptsPkid(EquipmentRelTestGroup equipmentrelTestGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE equipment_rel_test_group SET rel_test_group_name = ? WHERE spts_pkid = ?"
            );
            ps.setString(1, equipmentrelTestGroup.getRelTestGroupName());
            ps.setString(2, equipmentrelTestGroup.getSptsPkid());
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

    public QueryResult deleteEquipmentRelTestGroup(String equipmentrelTestGroupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM equipment_rel_test_group WHERE id = '" + equipmentrelTestGroupId + "'"
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

    public EquipmentRelTestGroup getEquipmentRelTestGroup(String equipmentrelTestGroupId) {
        String sql = "SELECT * FROM equipment_rel_test_group WHERE id = '" + equipmentrelTestGroupId + "'";
        EquipmentRelTestGroup equipmentrelTestGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentrelTestGroup = new EquipmentRelTestGroup();
                equipmentrelTestGroup.setId(rs.getString("id"));
                equipmentrelTestGroup.setSptsPkid(rs.getString("spts_pkid"));
                equipmentrelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
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
        return equipmentrelTestGroup;
    }

    public EquipmentRelTestGroup getEquipmentRelTestGroupByRelTestGroupName(String relTestGroup) {
        String sql = "SELECT * FROM equipment_rel_test_group WHERE rel_test_group_name = '" + relTestGroup + "'";
        EquipmentRelTestGroup equipmentrelTestGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentrelTestGroup = new EquipmentRelTestGroup();
                equipmentrelTestGroup.setId(rs.getString("id"));
                equipmentrelTestGroup.setSptsPkid(rs.getString("spts_pkid"));
                equipmentrelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
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
        return equipmentrelTestGroup;
    }

    public List<EquipmentRelTestGroup> getEquipmentRelTestGroupList() {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate FROM equipment_rel_test_group ORDER BY rel_test_group_name ASC";
        List<EquipmentRelTestGroup> equipmentrelTestGroupList = new ArrayList<EquipmentRelTestGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentRelTestGroup equipmentrelTestGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentrelTestGroup = new EquipmentRelTestGroup();
                equipmentrelTestGroup.setId(rs.getString("id"));
                equipmentrelTestGroup.setSptsPkid(rs.getString("spts_pkid"));
                equipmentrelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
                equipmentrelTestGroup.setCreatedBy(rs.getString("created_by"));
                equipmentrelTestGroup.setCreatedDate(rs.getString("createdDate"));
                equipmentrelTestGroupList.add(equipmentrelTestGroup);
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
        return equipmentrelTestGroupList;
    }

    public List<EquipmentRelTestGroup> getEquipmentRelTestGroupListLeftJoinGlobalTable() {
        String sql = "SELECT ef.*, gf.spts_guid, IF(ef.rel_test_group_name = gf.rel_test_group_name, 'Yes','No') AS globalOrNot, DATE_FORMAT(ef.created_date,'%d %M %Y %h:%i %p') AS createdDate "
                + "FROM equipment_rel_test_group ef LEFT JOIN equipment_global_rel_test_group gf ON ef.rel_test_group_name = gf.rel_test_group_name "
                + "ORDER BY ef.rel_test_group_name";
        List<EquipmentRelTestGroup> equipmentrelTestGroupList = new ArrayList<EquipmentRelTestGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentRelTestGroup equipmentrelTestGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentrelTestGroup = new EquipmentRelTestGroup();
                equipmentrelTestGroup.setId(rs.getString("id"));
                equipmentrelTestGroup.setSptsPkid(rs.getString("spts_pkid"));
                equipmentrelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
                equipmentrelTestGroup.setCreatedBy(rs.getString("created_by"));
                equipmentrelTestGroup.setCreatedDate(rs.getString("createdDate"));
                equipmentrelTestGroup.setSptsGuid(rs.getString("gf.spts_guid"));
                equipmentrelTestGroup.setInGlobalOrNot(rs.getString("globalOrNot"));
                equipmentrelTestGroupList.add(equipmentrelTestGroup);
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
        return equipmentrelTestGroupList;
    }

    public List<EquipmentRelTestGroup> getEquipmentRelTestGroupList(String relTestGroup) {
        String sql = "SELECT *,DATE_FORMAT(created_date,'%d %M %Y %h:%i %p') AS createdDate, IF(rel_test_group_name=\"" + relTestGroup + "\",\"selected=''\",\"\") AS selected FROM equipment_rel_test_group ORDER BY rel_test_group_name ASC";
        List<EquipmentRelTestGroup> equipmentrelTestGroupList = new ArrayList<EquipmentRelTestGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EquipmentRelTestGroup equipmentrelTestGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentrelTestGroup = new EquipmentRelTestGroup();
                equipmentrelTestGroup.setId(rs.getString("id"));
                equipmentrelTestGroup.setSptsPkid(rs.getString("spts_pkid"));
                equipmentrelTestGroup.setRelTestGroupName(rs.getString("rel_test_group_name"));
                equipmentrelTestGroup.setCreatedBy(rs.getString("created_by"));
                equipmentrelTestGroup.setCreatedDate(rs.getString("createdDate"));
                equipmentrelTestGroup.setSelected(rs.getString("selected"));
                equipmentrelTestGroupList.add(equipmentrelTestGroup);
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
        return equipmentrelTestGroupList;
    }

    public Integer getCountPkid(String pkid) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_rel_test_group inc WHERE inc.spts_pkid = '" + pkid + "'"
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

    public Integer getCountRelTestGroupName(String relTestGroup) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM equipment_rel_test_group inc WHERE inc.rel_test_group_name = '" + relTestGroup + "'"
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
