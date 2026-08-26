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
import com.onsemi.mib.model.ItemHardware;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemHardwareDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemHardwareDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public ItemHardwareDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    private String getGeneratedKey(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getString(1);
            }
            throw new SQLException("No generated key returned.");
        }
    }
    
    private static final String SQL_INSERT_ITEM_HARDWARE = "INSERT INTO item_hardware (mib_item_id, spts_pkid, hardware_id, alu, status, rms_event, shelf_time, created_by, created_date, verify_by, verify_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_HARDWARE_ID = "INSERT INTO item_hardware (mib_item_id, hardware_id, status, created_by, created_date, flag) VALUES (?,?,?,?,NOW(),?)";
    private static final String SQL_INSERT_HARDWARE_ID_FROM_SPTS = "INSERT INTO item_hardware (mib_item_id, hardware_id, status, created_by, created_date, flag, spts_pkid, rms_event, alu, shelf_time) VALUES (?,?,?,?,NOW(),?,?,?,?,?)";
    
    public QueryResult insertItemHardware(ItemHardware itemhardware) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_ITEM_HARDWARE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemhardware.getMibItemId());
            ps.setString(2, itemhardware.getSptsPkid());
            ps.setString(3, itemhardware.getHardwareId());
            ps.setString(4, itemhardware.getAlu());
            ps.setString(5, itemhardware.getStatus());
            ps.setString(6, itemhardware.getRmsEvent());
            ps.setString(7, itemhardware.getShelfTime());
            ps.setString(8, itemhardware.getCreatedBy());
            ps.setString(9, itemhardware.getCreatedDate());
            ps.setString(10, itemhardware.getVerifyBy());
            ps.setString(11, itemhardware.getVerifyDate());
            ps.setString(12, itemhardware.getFlag());
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }
    
    public QueryResult insertHardwareID(ItemHardware itemhardware) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_HARDWARE_ID, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemhardware.getMibItemId());
            ps.setString(2, itemhardware.getHardwareId());
            ps.setString(3, itemhardware.getStatus());
            ps.setString(4, itemhardware.getCreatedBy());
            ps.setString(5, itemhardware.getFlag());
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }
    
    public QueryResult insertHardwareIDFromSpts(ItemHardware itemhardware) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT_HARDWARE_ID_FROM_SPTS, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, itemhardware.getMibItemId());
            ps.setString(2, itemhardware.getHardwareId());
            ps.setString(3, itemhardware.getStatus());
            ps.setString(4, itemhardware.getCreatedBy());
            ps.setString(5, itemhardware.getFlag());
            ps.setString(6, itemhardware.getSptsPkid());
            ps.setString(7, itemhardware.getRmsEvent());
            ps.setString(8, itemhardware.getAlu());
            ps.setString(9, itemhardware.getShelfTime());
            result.setResult(ps.executeUpdate());
            result.setGeneratedKey(getGeneratedKey(ps));
        }
        return result;
    }
    
    private static final String SQL_UPDATE_ITEM_HARDWARE = "UPDATE item_hardware SET mib_item_id = ?, spts_pkid = ?, hardware_id = ?, alu = ?, status = ?, rms_event = ?, shelf_time = ?, created_by = ?, created_date = ?, verify_by = ?, verify_date = ?, flag = ? WHERE id = ?";
    private static final String SQL_UPDATE_ITEM_HARDWARE_FROM_SPTS = "UPDATE item_hardware SET spts_pkid = ?, hardware_id = ?, alu = ?, status = ?, rms_event = ?, shelf_time = ? WHERE spts_pkid = ?";
    private static final String SQL_DELETE_ITEM_HARDWARE = "DELETE FROM item_hardware WHERE id = ? ";
    private static final String SQL_UPDATE_SPTS_PKID_HARDWARE_ID = "UPDATE item_hardware SET spts_pkid = ? WHERE id = ? ";
    private static final String SQL_UPDATE_HARDWARE_ID_STATUS = "UPDATE item_hardware SET status = 'Good', flag = '1', verify_date = NOW(), verify_by = ? WHERE id = ? ";
    private static final String SQL_UPDATE_HARDWARE_ID_STATUS_AVAILABLE = "UPDATE item_hardware SET status = 'Available', flag = '1', verify_date = NOW(), verify_by = ? WHERE id = ?";

    public QueryResult updateItemHardware(ItemHardware itemhardware) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ITEM_HARDWARE)) {
            ps.setString(1, itemhardware.getMibItemId());
            ps.setString(2, itemhardware.getSptsPkid());
            ps.setString(3, itemhardware.getHardwareId());
            ps.setString(4, itemhardware.getAlu());
            ps.setString(5, itemhardware.getStatus());
            ps.setString(6, itemhardware.getRmsEvent());
            ps.setString(7, itemhardware.getShelfTime());
            ps.setString(8, itemhardware.getCreatedBy());
            ps.setString(9, itemhardware.getCreatedDate());
            ps.setString(10, itemhardware.getVerifyBy());
            ps.setString(11, itemhardware.getVerifyDate());
            ps.setString(12, itemhardware.getFlag());
            ps.setString(13, itemhardware.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateItemHardwareFromSPTS(ItemHardware itemhardware) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ITEM_HARDWARE_FROM_SPTS)) {
            ps.setString(1, itemhardware.getSptsPkid());
            ps.setString(2, itemhardware.getHardwareId());
            ps.setString(3, itemhardware.getAlu());
            ps.setString(4, itemhardware.getStatus());
            ps.setString(5, itemhardware.getRmsEvent());
            ps.setString(6, itemhardware.getShelfTime());
            ps.setString(7, itemhardware.getSptsPkid());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult deleteItemHardware(String itemhardwareId) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_DELETE_ITEM_HARDWARE)) {
            ps.setString(1, itemhardwareId);
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateSPTSPKID_HardwareId(ItemHardware item) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_SPTS_PKID_HARDWARE_ID)) {
            ps.setString(1, item.getSptsPkid());
            ps.setString(2, item.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateHardwareIdStatus(ItemHardware item) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_HARDWARE_ID_STATUS)) {
            ps.setString(1, item.getVerifyBy());
            ps.setString(2, item.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    public QueryResult updateHardwareIdStatusAvailable(ItemHardware item) throws SQLException {
        QueryResult result = new QueryResult();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_HARDWARE_ID_STATUS_AVAILABLE)) {
            ps.setString(1, item.getVerifyBy());
            ps.setString(2, item.getId());
            result.setResult(ps.executeUpdate());
        }
        return result;
    }

    private static final String SQL_GET_ITEM_HARDWARE = "SELECT * FROM item_hardware WHERE id = ? ";
    private static final String SQL_GET_ITEM_HARDWARE_BY_HWID = "SELECT * FROM item_hardware WHERE hardware_id = ? ";
    
    private ItemHardware mapComponentHw(ResultSet rs) throws SQLException {
        ItemHardware itemhardware = new ItemHardware();
        itemhardware.setId(rs.getString("id"));
        itemhardware.setMibItemId(rs.getString("mib_item_id"));
        itemhardware.setSptsPkid(rs.getString("spts_pkid"));
        itemhardware.setHardwareId(rs.getString("hardware_id"));
        itemhardware.setAlu(rs.getString("alu"));
        itemhardware.setStatus(rs.getString("status"));
        itemhardware.setRmsEvent(rs.getString("rms_event"));
        itemhardware.setShelfTime(rs.getString("shelf_time"));
        itemhardware.setCreatedBy(rs.getString("created_by"));
        itemhardware.setCreatedDate(rs.getString("created_date"));
        itemhardware.setVerifyBy(rs.getString("verify_by"));
        itemhardware.setVerifyDate(rs.getString("verify_date"));
        itemhardware.setFlag(rs.getString("flag"));
        return itemhardware;
    }
    
    public ItemHardware getItemHardware(String itemhardwareId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_HARDWARE)) {
            ps.setString(1, itemhardwareId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentHw(rs);
                }
            }
        }
        return null;
    }
    
    public ItemHardware getItemHardwareByHardwareId(String hwId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_HARDWARE_BY_HWID)) {
            ps.setString(1, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapComponentHw(rs);
                }
            }
        }
        return null;
    }
    
    private static final String SQL_GET_MIB_ITEM_ID_BY_HWID = "SELECT mib_item_id FROM item_hardware WHERE id = ? ";
    private static final String SQL_GET_LATEST_HARDWARE_ID  = "SELECT MAX(hardware_id) as data FROM item_hardware WHERE mib_item_id = ? AND hardware_id LIKE ? ";
    private static final String SQL_CHECK_FOR_EXISTING_DATA = "SELECT COUNT(*) AS data FROM item_hardware WHERE mib_item_id != ? AND hardware_id LIKE ? ";
    private static final String SQL_GET_ANOTHER_MIB_ID = "SELECT mib_item_id FROM item_hardware WHERE mib_item_id != ? AND hardware_id LIKE ? ";
    private static final String SQL_GET_SPTS_ID_BY_HWID = "SELECT IFNULL(spts_pkid, 0) AS sptsId FROM item_hardware WHERE id = ? ";
    private static final String SQL_GET_HARDWARE_ID_BY_HW_ID = "SELECT hardware_id FROM item_hardware WHERE id = ? ";

    public String getMibItemIdByItemHwId(String itemhardwareId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MIB_ITEM_ID_BY_HWID)) {
            ps.setString(1, itemhardwareId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mib_item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. itemhardwareId={} in getCountHwByGroupIdAndHwId ", itemhardwareId, e);
        }
        return null;
    }

    public String getLatestHardwareID(String mibItemId, String hardwareId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_LATEST_HARDWARE_ID)) {
            ps.setString(1, mibItemId);
            ps.setString(2, hardwareId+"%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String data = rs.getString("data");
                    if (data != null) {
                        data = data.substring(data.length() - 3);
                    }
                    return data;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. mibItemId={}, hardwareId={} in getLatestHardwareID ", mibItemId, hardwareId, e);
        }
        return null;
    }

    public String checkForExistingData(String mibItemId, String hardwareId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_CHECK_FOR_EXISTING_DATA)) {
            ps.setString(1, mibItemId);
            ps.setString(2, hardwareId+"%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("data");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. mibItemId={}, hardwareId={} in checkForExistingData ", mibItemId, hardwareId, e);
        }
        return null;
    }

    public String getOtherMibId(String mibItemId, String hardwareId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ANOTHER_MIB_ID)) {
            ps.setString(1, mibItemId);
            ps.setString(2, hardwareId+"%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mib_item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count. mibItemId={}, hardwareId={} in getOtherMibId ", mibItemId, hardwareId, e);
        }
        return null;
    }

    public String getSptsIdByHwId(String hardwareId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_SPTS_ID_BY_HWID)) {
            ps.setString(1, hardwareId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("sptsId");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count hardwareId={} in getOtherMibId ", hardwareId, e);
        }
        return null;
    }

    public String getHardwareIdByHwId(String hwid) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_HARDWARE_ID_BY_HW_ID)) {
            ps.setString(1, hwid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mib_item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count hwid={} in getHardwareIdByHwId ", hwid, e);
        }
        return null;
    }
    
    private static final String SQL_GET_TOTAL_HARDWARE_CREATED = "SELECT COUNT(*) AS count FROM item_hardware WHERE mib_item_id = ? ";
    private static final String SQL_GET_TOTAL_HARDWARE_AVAILABLE = "SELECT COUNT(*) AS count FROM item_hardware WHERE mib_item_id = ? AND flag = '1' ";
    private static final String SQL_GET_COUNT_AVAILABLE_HARDWARE_ID = "SELECT COUNT(*) AS count FROM item_hardware inc WHERE inc.hardware_id = ? AND status in ('Available','Good') ";
    private static final String SQL_GET_COUNT_HARDWARE_ID = "SELECT COUNT(*) AS count FROM item_hardware inc WHERE inc.hardware_id = ? ";
    
    public Integer getTotalHardwareCreated(String mibItemId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_TOTAL_HARDWARE_CREATED)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count mibItemId={} in getTotalHardwareCreated ", mibItemId, e);
        }
        return null;
    }
    
    public Integer getTotalHardwareAvailable(String mibItemId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_TOTAL_HARDWARE_AVAILABLE)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count mibItemId={} in getTotalHardwareAvailable ", mibItemId, e);
        }
        return null;
    }
    
    public Integer getCountAvailableHardwareId(String hwId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_AVAILABLE_HARDWARE_ID)) {
            ps.setString(1, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count hwId={} in getCountAvailableHardwareId ", hwId, e);
        }
        return null;
    }
    
    public Integer getCountHardwareId(String hwId) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_HARDWARE_ID)) {
            ps.setString(1, hwId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting hardware count hwId={} in getCountHardwareId ", hwId, e);
        }
        return null;
    }
    
    private static final String SQL_GET_ITEM_HARDWARE_BY_ITEM_ID = "SELECT * FROM item_hardware WHERE mib_item_id = ? ";
    private static final String SQL_GET_ITEM_HARDWARE_LIST = "SELECT * FROM item_hardware ORDER BY id ASC ";
    private static final String SQL_GET_ITEM_HW_LIST_BY_ITEM_ID = "SELECT * FROM item_hardware WHERE mib_item_id = ? ORDER BY id ASC ";
    
    public List<ItemHardware> getItemHardwareByItemId(String itemId) {
        List<ItemHardware> itemHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_HARDWARE_BY_ITEM_ID)) {
            ps.setString(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemHardware itemhardware = new ItemHardware();
                    itemhardware.setId(rs.getString("id"));
                    itemhardware.setMibItemId(rs.getString("mib_item_id"));
                    itemhardware.setSptsPkid(rs.getString("spts_pkid"));
                    itemhardware.setHardwareId(rs.getString("hardware_id"));
                    itemhardware.setAlu(rs.getString("alu"));
                    itemhardware.setStatus(rs.getString("status"));
                    itemhardware.setRmsEvent(rs.getString("rms_event"));
                    itemhardware.setShelfTime(rs.getString("shelf_time"));
                    itemhardware.setCreatedBy(rs.getString("created_by"));
                    itemhardware.setCreatedDate(rs.getString("created_date"));
                    itemhardware.setVerifyBy(rs.getString("verify_by"));
                    itemhardware.setVerifyDate(rs.getString("verify_date"));
                    itemhardware.setFlag(rs.getString("flag"));
                    itemHardwareList.add(itemhardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for groupId={} in getItemHardwareByItemId ", itemId, e);
        }
        return itemHardwareList;
    }
    
    public List<ItemHardware> getItemHardwareList() {
        List<ItemHardware> itemHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_HARDWARE_LIST)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemHardware itemhardware = new ItemHardware();
                    itemhardware.setId(rs.getString("id"));
                    itemhardware.setMibItemId(rs.getString("mib_item_id"));
                    itemhardware.setSptsPkid(rs.getString("spts_pkid"));
                    itemhardware.setHardwareId(rs.getString("hardware_id"));
                    itemhardware.setAlu(rs.getString("alu"));
                    itemhardware.setStatus(rs.getString("status"));
                    itemhardware.setRmsEvent(rs.getString("rms_event"));
                    itemhardware.setShelfTime(rs.getString("shelf_time"));
                    itemhardware.setCreatedBy(rs.getString("created_by"));
                    itemhardware.setCreatedDate(rs.getString("created_date"));
                    itemhardware.setVerifyBy(rs.getString("verify_by"));
                    itemhardware.setVerifyDate(rs.getString("verify_date"));
                    itemhardware.setFlag(rs.getString("flag"));
                    itemHardwareList.add(itemhardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware id list for getItemHardwareList ", e);
        }
        return itemHardwareList;
    }

    public List<ItemHardware> getItemHwListByItemId(String mibItemId) {
        List<ItemHardware> itemHardwareList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ITEM_HW_LIST_BY_ITEM_ID)) {
            ps.setString(1, mibItemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemHardware itemhardware = new ItemHardware();
                    itemhardware.setId(rs.getString("id"));
                    itemhardware.setMibItemId(rs.getString("mib_item_id"));
                    itemhardware.setSptsPkid(rs.getString("spts_pkid"));
                    itemhardware.setHardwareId(rs.getString("hardware_id"));
                    itemhardware.setAlu(rs.getString("alu"));
                    itemhardware.setStatus(rs.getString("status"));
                    itemhardware.setRmsEvent(rs.getString("rms_event"));
                    itemhardware.setShelfTime(rs.getString("shelf_time"));
                    itemhardware.setCreatedBy(rs.getString("created_by"));
                    itemhardware.setCreatedDate(rs.getString("created_date"));
                    itemhardware.setVerifyBy(rs.getString("verify_by"));
                    itemhardware.setVerifyDate(rs.getString("verify_date"));
                    itemhardware.setFlag(rs.getString("flag"));
                    itemHardwareList.add(itemhardware);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving hardware group list for mibItemId={} in getItemHwListByItemId ", mibItemId, e);
        }
        return itemHardwareList;
    }

}