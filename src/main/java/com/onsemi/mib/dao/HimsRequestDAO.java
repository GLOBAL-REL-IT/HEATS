package com.onsemi.mib.dao;

import com.onsemi.mib.model.EmailConfig;
import com.onsemi.mib.model.HimsInventory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.mib.model.WhInventory;
import com.onsemi.mib.model.WhRequest;
import com.onsemi.mib.model.WhRetrieval;
import com.onsemi.mib.model.WhStatusLog;
import com.onsemi.mib.tools.QueryResult;
import java.sql.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HimsRequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(HimsRequestDAO.class);
    private final Connection conn;
//    private final DataSource dataSource;

    public HimsRequestDAO() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://mysed-rel-app03:3306/cdars?serverTimezone=UTC&useLegacyDatetimeCode=false", "root", "root");
        this.conn = conn;
    }

    public List<HimsInventory> getWhInventoryActiveList() {
        String sql = "SELECT * FROM cdars_wh_inventory WHERE flag = '0' ORDER BY id DESC";
        List<HimsInventory> whInventoryList = new ArrayList<HimsInventory>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            HimsInventory whInventory;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new HimsInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
                whInventoryList.add(whInventory);
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
        return whInventoryList;
    }

    public List<HimsInventory> getWhInventoryActiveListByItemId(String columnName, String itemId) {
        String sql = "SELECT *,"
                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS inventoryDate "
                + " FROM cdars_wh_inventory "
                + "WHERE flag = '0' "
                + "AND " + columnName + " = ? ORDER BY id DESC";
        LOGGER.info("sql: " + sql);
        List<HimsInventory> whInventoryList = new ArrayList<>();
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemId);
            HimsInventory whInventory;
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    whInventory = new HimsInventory();
                    whInventory.setId(rs.getString("id"));
                    whInventory.setRequestId(rs.getString("request_id"));
                    whInventory.setMpNo(rs.getString("mp_no"));
                    whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                    whInventory.setEquipmentType(rs.getString("equipment_type"));
                    whInventory.setEquipmentId(rs.getString("equipment_id"));
                    whInventory.setPcbA(rs.getString("pcb_a"));
                    whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                    whInventory.setPcbB(rs.getString("pcb_b"));
                    whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                    whInventory.setPcbC(rs.getString("pcb_c"));
                    whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                    whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                    whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                    whInventory.setQuantity(rs.getString("quantity"));
                    whInventory.setRequestedBy(rs.getString("requested_by"));
                    whInventory.setRequestedDate(rs.getString("requested_date"));
                    whInventory.setRemarks(rs.getString("remarks"));
                    whInventory.setVerifiedDate(rs.getString("verified_date"));
                    whInventory.setInventoryDate(rs.getString("inventoryDate"));
                    whInventory.setInventoryLocation(rs.getString("inventory_location"));
                    whInventory.setInventoryRack(rs.getString("inventory_rack"));
                    whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                    whInventory.setInventoryBy(rs.getString("inventory_by"));
                    whInventory.setStatus(rs.getString("status"));
                    whInventory.setReceivedDate(rs.getString("received_date"));
                    whInventory.setFlag(rs.getString("flag"));
                    whInventory.setLoadCard(rs.getString("load_card_id"));
                    whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                    whInventory.setProgramCard(rs.getString("program_card_id"));
                    whInventory.setProgramCardQty(rs.getString("program_card_qty"));
                    whInventory.setBoxNo(rs.getString("box_no"));
                    whInventoryList.add(whInventory);
                }
            }
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
        return whInventoryList;
    }

//    public List<HimsInventory> getWhInventoryActiveListByItemId(String whereClause) {
//        String sql = "SELECT *,"
//                + "DATE_FORMAT(inventory_date,'%d %M %Y %h:%i %p') AS inventoryDate "
//                + " FROM cdars_wh_inventory WHERE flag = '0' AND ? ORDER BY id DESC";
//        LOGGER.info("sql: " + sql);
//        List<HimsInventory> whInventoryList = new ArrayList<>();
//            try (PreparedStatement ps = conn.prepareStatement(sql)) {
//                ps.setString(1, whereClause);
//                HimsInventory whInventory;
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    whInventory = new HimsInventory();
//                    whInventory.setId(rs.getString("id"));
//                    whInventory.setRequestId(rs.getString("request_id"));
//                    whInventory.setMpNo(rs.getString("mp_no"));
//                    whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
//                    whInventory.setEquipmentType(rs.getString("equipment_type"));
//                    whInventory.setEquipmentId(rs.getString("equipment_id"));
//                    whInventory.setPcbA(rs.getString("pcb_a"));
//                    whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
//                    whInventory.setPcbB(rs.getString("pcb_b"));
//                    whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
//                    whInventory.setPcbC(rs.getString("pcb_c"));
//                    whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
//                    whInventory.setPcbCtr(rs.getString("pcb_ctr"));
//                    whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
//                    whInventory.setQuantity(rs.getString("quantity"));
//                    whInventory.setRequestedBy(rs.getString("requested_by"));
//                    whInventory.setRequestedDate(rs.getString("requested_date"));
//                    whInventory.setRemarks(rs.getString("remarks"));
//                    whInventory.setVerifiedDate(rs.getString("verified_date"));
//                    whInventory.setInventoryDate(rs.getString("inventoryDate"));
//                    whInventory.setInventoryLocation(rs.getString("inventory_location"));
//                    whInventory.setInventoryRack(rs.getString("inventory_rack"));
//                    whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
//                    whInventory.setInventoryBy(rs.getString("inventory_by"));
//                    whInventory.setStatus(rs.getString("status"));
//                    whInventory.setReceivedDate(rs.getString("received_date"));
//                    whInventory.setFlag(rs.getString("flag"));
//                    whInventory.setLoadCard(rs.getString("load_card_id"));
//                    whInventory.setLoadCardQty(rs.getString("load_card_qty"));
//                    whInventory.setProgramCard(rs.getString("program_card_id"));
//                    whInventory.setProgramCardQty(rs.getString("program_card_qty"));
//                    whInventory.setBoxNo(rs.getString("box_no"));
//                    whInventoryList.add(whInventory);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return whInventoryList;
//    }
    public WhInventory getWhInventoryActive(String whInventoryId) {
        String sql = "SELECT * FROM cdars_wh_inventory WHERE id = ? AND flag = '0'";
        WhInventory whInventory = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, whInventoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whInventory = new WhInventory();
                whInventory.setId(rs.getString("id"));
                whInventory.setRequestId(rs.getString("request_id"));
                whInventory.setMpNo(rs.getString("mp_no"));
                whInventory.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whInventory.setEquipmentType(rs.getString("equipment_type"));
                whInventory.setEquipmentId(rs.getString("equipment_id"));
                whInventory.setPcbA(rs.getString("pcb_a"));
                whInventory.setPcbAQty(rs.getString("pcb_a_qty"));
                whInventory.setPcbB(rs.getString("pcb_b"));
                whInventory.setPcbBQty(rs.getString("pcb_b_qty"));
                whInventory.setPcbC(rs.getString("pcb_c"));
                whInventory.setPcbCQty(rs.getString("pcb_c_qty"));
                whInventory.setPcbCtr(rs.getString("pcb_ctr"));
                whInventory.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whInventory.setQuantity(rs.getString("quantity"));
                whInventory.setRequestedBy(rs.getString("requested_by"));
                whInventory.setRequestedDate(rs.getString("requested_date"));
                whInventory.setRemarks(rs.getString("remarks"));
                whInventory.setVerifiedDate(rs.getString("verified_date"));
                whInventory.setInventoryDate(rs.getString("inventory_date"));
                whInventory.setInventoryLocation(rs.getString("inventory_location"));
                whInventory.setInventoryRack(rs.getString("inventory_rack"));
                whInventory.setInventoryShelf(rs.getString("inventory_shelf"));
                whInventory.setInventoryBy(rs.getString("inventory_by"));
                whInventory.setStatus(rs.getString("status"));
                whInventory.setReceivedDate(rs.getString("received_date"));
                whInventory.setFlag(rs.getString("flag"));
                whInventory.setLoadCard(rs.getString("load_card_id"));
                whInventory.setLoadCardQty(rs.getString("load_card_qty"));
                whInventory.setProgramCard(rs.getString("program_card_id"));
                whInventory.setProgramCardQty(rs.getString("program_card_qty"));
                whInventory.setBoxNo(rs.getString("box_no"));
                whInventory.setGtsNo(rs.getString("gts_no"));
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
        return whInventory;
    }

    public Integer getCountFlag0ForRetrieve(String equipmentId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE request_type = 'Retrieve' AND equipment_id = ? AND flag = '0' "
            );
            ps.setString(1, equipmentId);
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

    public Integer getCountRequestId(String requestId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE id = ? "
            );
            ResultSet rs = ps.executeQuery();
            ps.setString(1, requestId);
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

    public Integer getCountRetrieveId(String retrieveId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_retrieval WHERE id = ? "
            );
            ResultSet rs = ps.executeQuery();
            ps.setString(1, retrieveId);
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

    public QueryResult updateWhRequestStatus(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE cdars_wh_request SET modified_by = ?, modified_date = NOW(), status = ? WHERE id = ?"
            );
            ps.setString(1, whRequest.getModifiedBy());
            ps.setString(2, whRequest.getStatus());
            ps.setString(3, whRequest.getId());
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

    public Integer getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(String equipmentId, String mpNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE request_type = 'Retrieve' AND equipment_id = ? AND mp_no = ? AND status <> 'Cancelled' "
            );
            ps.setString(1, equipmentId);
            ps.setString(2, mpNo);
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

    public Integer getCountRetrieveEquipmentIdAndBoxNoAndStatusCancelled(String equipmentId, String boxNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT count(*) AS count FROM cdars_wh_request WHERE request_type = 'Retrieve' AND equipment_id = ? AND box_no = ? AND status <> 'Cancelled'"
            );
            ps.setString(1, equipmentId);
            ps.setString(2, boxNo);
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

    public Integer getCountTaskWildCardForEmailconfig(String job) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM cdars_email_config WHERE task_pdetails_code LIKE ? "
            );
            ps.setString(1, job + "%");
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

    public EmailConfig getEmailConfigByTaskWildCard(String task) {
        String sql = "SELECT * FROM cdars_email_config WHERE task_pdetails_code LIKE ? ";
        EmailConfig emailConfig = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, task + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emailConfig = new EmailConfig();
                emailConfig.setId(rs.getString("id"));
                emailConfig.setTaskPdetailsCode(rs.getString("task_pdetails_code"));
                emailConfig.setUserOncid(rs.getString("user_oncid"));
                emailConfig.setUserName(rs.getString("user_name"));
                emailConfig.setEmail(rs.getString("email"));
                emailConfig.setFlag(rs.getString("flag"));
                emailConfig.setRemarks(rs.getString("remarks"));
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
        return emailConfig;
    }

    public QueryResult insertWhRequest(WhRequest whRequest) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_request (inventory_id, request_type, equipment_type, pcb_type, equipment_id, "
                    + "pcb_a, pcb_a_qty, pcb_b, pcb_b_qty, pcb_c, pcb_c_qty, pcb_ctr, pcb_ctr_qty, mp_no, mp_expiry_date, "
                    + "quantity, rack, shelf, requested_by, requestor_email, requested_date, remarks, remarks_log, created_by, "
                    + "created_date, status, flag, retrieval_reason, sfpkid, sfpkidB, sfpkidC, sfpkidCtr, load_card_id, load_card_qty, "
                    + "program_card_id, program_card_qty,sfpkidLc,sfpkidPc, box_no,change_flag,gts_no) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRequest.getInventoryId());
            ps.setString(2, whRequest.getRequestType());
            ps.setString(3, whRequest.getEquipmentType());
            ps.setString(4, whRequest.getPcbType());
            ps.setString(5, whRequest.getEquipmentId());
            ps.setString(6, whRequest.getPcbA());
            ps.setString(7, whRequest.getPcbAQty());
            ps.setString(8, whRequest.getPcbB());
            ps.setString(9, whRequest.getPcbBQty());
            ps.setString(10, whRequest.getPcbC());
            ps.setString(11, whRequest.getPcbCQty());
            ps.setString(12, whRequest.getPcbCtr());
            ps.setString(13, whRequest.getPcbCtrQty());
            ps.setString(14, whRequest.getMpNo());
            ps.setString(15, whRequest.getMpExpiryDate());
            ps.setString(16, whRequest.getQuantity());
            ps.setString(17, whRequest.getRack());
            ps.setString(18, whRequest.getShelf());
            ps.setString(19, whRequest.getRequestedBy());
            ps.setString(20, whRequest.getRequestorEmail());
            ps.setString(21, whRequest.getRemarks());
            ps.setString(22, whRequest.getRemarksLog());
            ps.setString(23, whRequest.getCreatedBy());
            ps.setString(24, whRequest.getStatus());
            ps.setString(25, whRequest.getFlag());
            ps.setString(26, whRequest.getRetrievalReason());
            ps.setString(27, whRequest.getSfpkid());
            ps.setString(28, whRequest.getSfpkidB());
            ps.setString(29, whRequest.getSfpkidC());
            ps.setString(30, whRequest.getSfpkidCtr());
            ps.setString(31, whRequest.getLoadCard());
            ps.setString(32, whRequest.getLoadCardQty());
            ps.setString(33, whRequest.getProgramCard());
            ps.setString(34, whRequest.getProgramCardQty());
            ps.setString(35, whRequest.getSfpkidLc());
            ps.setString(36, whRequest.getSfpkidPc());
            ps.setString(37, whRequest.getBoxNo());
            ps.setString(38, whRequest.getChangeFlag());
            ps.setString(39, whRequest.getGtsNo());
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

    public QueryResult insertWhStatusLog(WhStatusLog whStatusLog) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_status_log (request_id, module, status, status_date, created_by, flag) VALUES (?,?,?,NOW(),?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whStatusLog.getRequestId());
            ps.setString(2, whStatusLog.getModule());
            ps.setString(3, whStatusLog.getStatus());
            ps.setString(4, whStatusLog.getCreatedBy());
            ps.setString(5, whStatusLog.getFlag());
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

    public WhRequest getWhRequest(String whRequestId) {
        String sql = "SELECT *,DATE_FORMAT(requested_date,'%d %M %Y %h:%i %p') AS requested_date_view, DATE_FORMAT(mp_expiry_date,'%d %M %Y') AS mp_expiry_date_view FROM cdars_wh_request WHERE id = '" + whRequestId + "'";
        WhRequest whRequest = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRequest = new WhRequest();
                whRequest.setId(rs.getString("id"));
                whRequest.setInventoryId(rs.getString("inventory_id"));
                whRequest.setRequestType(rs.getString("request_type"));
                whRequest.setEquipmentType(rs.getString("equipment_type"));
                whRequest.setPcbType(rs.getString("pcb_type"));
                whRequest.setEquipmentId(rs.getString("equipment_id"));
                whRequest.setPcbA(rs.getString("pcb_a"));
                whRequest.setPcbAQty(rs.getString("pcb_a_qty"));
                whRequest.setPcbB(rs.getString("pcb_b"));
                whRequest.setPcbBQty(rs.getString("pcb_b_qty"));
                whRequest.setPcbC(rs.getString("pcb_c"));
                whRequest.setPcbCQty(rs.getString("pcb_c_qty"));
                whRequest.setPcbCtr(rs.getString("pcb_ctr"));
                whRequest.setPcbCtrQty(rs.getString("pcb_ctr_qty"));
                whRequest.setMpNo(rs.getString("mp_no"));
                whRequest.setMpExpiryDate(rs.getString("mp_expiry_date"));
                whRequest.setMpExpiryDateView(rs.getString("mp_expiry_date_view"));
                whRequest.setRack(rs.getString("rack"));
                whRequest.setShelf(rs.getString("shelf"));
                whRequest.setQuantity(rs.getString("quantity"));
                whRequest.setRequestedBy(rs.getString("requested_by"));
                whRequest.setRequestorEmail(rs.getString("requestor_email"));
                whRequest.setRequestedDate(rs.getString("requested_date"));
                whRequest.setRequestedDateView(rs.getString("requested_date_view"));
                whRequest.setFinalApprovedStatus(rs.getString("final_approved_status"));
                whRequest.setFinalApprovedBy(rs.getString("final_approved_by"));
                whRequest.setFinalApprovedDate(rs.getString("final_approved_date"));
                whRequest.setRemarks(rs.getString("remarks"));
                whRequest.setRemarksLog(rs.getString("remarks_log"));
                whRequest.setRemarksApprover(rs.getString("remarks_approver"));
                whRequest.setCreatedBy(rs.getString("created_by"));
                whRequest.setCreatedDate(rs.getString("created_date"));
                whRequest.setModifiedBy(rs.getString("modified_by"));
                whRequest.setModifiedDate(rs.getString("modified_date"));
                whRequest.setStatus(rs.getString("status"));
                whRequest.setFlag(rs.getString("flag"));
                whRequest.setRetrievalReason(rs.getString("retrieval_reason"));
                whRequest.setSfpkid(rs.getString("sfpkid"));
                whRequest.setSfpkidB(rs.getString("sfpkidB"));
                whRequest.setSfpkidC(rs.getString("sfpkidC"));
                whRequest.setSfpkidCtr(rs.getString("sfpkidCtr"));
                //phase 2
                whRequest.setLoadCard(rs.getString("load_card_id"));
                whRequest.setLoadCardQty(rs.getString("load_card_qty"));
                whRequest.setProgramCard(rs.getString("program_card_id"));
                whRequest.setProgramCardQty(rs.getString("program_card_qty"));
                whRequest.setSfpkidLc(rs.getString("sfpkidLc"));
                whRequest.setSfpkidPc(rs.getString("sfpkidPc"));
                //phase 3
                whRequest.setBoxNo(rs.getString("box_no"));
                whRequest.setGtsNo(rs.getString("gts_no"));
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
        return whRequest;
    }

    public QueryResult insertWhRetrieval(WhRetrieval whRetrieval) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO cdars_wh_retrieval (request_id, hardware_type, hardware_id, "
                    + "pcb_a, pcb_a_qty, pcb_b, pcb_b_qty, pcb_c, pcb_c_qty, pcb_ctr, pcb_ctr_qty,"
                    + "hardware_qty, mp_no, mp_expiry_date, location, shelf, rack, requested_by, "
                    + "requested_date, remarks, status, flag, retrieval_reason,load_card_id, load_card_qty, program_card_id, program_card_qty, box_no) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, whRetrieval.getRequestId());
            ps.setString(2, whRetrieval.getHardwareType());
            ps.setString(3, whRetrieval.getHardwareId());
            ps.setString(4, whRetrieval.getPcbA());
            ps.setString(5, whRetrieval.getPcbAQty());
            ps.setString(6, whRetrieval.getPcbB());
            ps.setString(7, whRetrieval.getPcbBQty());
            ps.setString(8, whRetrieval.getPcbC());
            ps.setString(9, whRetrieval.getPcbCQty());
            ps.setString(10, whRetrieval.getPcbCtr());
            ps.setString(11, whRetrieval.getPcbCtrQty());
            ps.setString(12, whRetrieval.getHardwareQty());
            ps.setString(13, whRetrieval.getMpNo());
            ps.setString(14, whRetrieval.getMpExpiryDate());
            ps.setString(15, whRetrieval.getLocation());
            ps.setString(16, whRetrieval.getShelf());
            ps.setString(17, whRetrieval.getRack());
            ps.setString(18, whRetrieval.getRequestedBy());
            ps.setString(19, whRetrieval.getRequestedDate());
            ps.setString(20, whRetrieval.getRemarks());
            ps.setString(21, whRetrieval.getStatus());
            ps.setString(22, whRetrieval.getFlag());
            ps.setString(23, whRetrieval.getRetrievalReason());
            ps.setString(24, whRetrieval.getLoadCard());
            ps.setString(25, whRetrieval.getLoadCardQty());
            ps.setString(26, whRetrieval.getProgramCard());
            ps.setString(27, whRetrieval.getProgramCardQty());
            ps.setString(28, whRetrieval.getBoxNo());

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

    public WhRetrieval getWhRetrievalForHeatsRecall(String whRetrievalId) {
        String sql = "SELECT id, box_no, hardware_qty, status, hardware_id, hardware_type, load_card_id, program_card_id FROM cdars_wh_retrieval WHERE id = '" + whRetrievalId + "'";
        WhRetrieval whRetrieval = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                whRetrieval = new WhRetrieval();
                whRetrieval.setId(rs.getString("id"));
                whRetrieval.setHardwareType(rs.getString("hardware_type"));
                whRetrieval.setHardwareId(rs.getString("hardware_id"));
                whRetrieval.setHardwareQty(rs.getString("hardware_qty"));
                whRetrieval.setStatus(rs.getString("status"));
                whRetrieval.setBoxNo(rs.getString("box_no"));
                //lc & pc
                whRetrieval.setLoadCard(rs.getString("load_card_id"));
                whRetrieval.setProgramCard(rs.getString("program_card_id"));
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
        return whRetrieval;
    }

}
