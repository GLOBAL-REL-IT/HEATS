package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.HimsInventory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.model.Log;
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
}
