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
import com.onsemi.mib.model.ItemVisualInspection;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemVisualInspectionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemVisualInspectionDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ItemVisualInspectionDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertItemVisualInspection(ItemVisualInspection itemvisualInspection) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_visual_inspection (mib_item_id, module, pcb, pcb_reject, handle, handle_reject, metal_frame, metal_frame_reject, hardware_fasterners, hardware_fasterners_reject, "
                    + "clip_holder, clip_holder_reject, pcb_edge_finger, pcb_edge_finger_reject, connector, connector_reject, dut_sockets, dut_sockets_reject, edge_mb_banana, edge_mb_banana_reject, "
                    + "elect_component, elect_component_reject, solder_joint, solder_joint_reject, win_connector, win_connector_reject, remarks, final_status, created_by, created_date, flag,"
                    + "pcb_reject_qty, handle_reject_qty, metal_frame_reject_qty, hardware_fasterners_reject_qty, clip_holder_reject_qty, pcb_edge_finger_reject_qty, connector_reject_qty,"
                    + "dut_sockets_reject_qty, edge_mb_banana_reject_qty, elect_component_reject_qty, solder_joint_reject_qty, win_connector_reject_qty) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, itemvisualInspection.getMibItemId());
            ps.setString(2, itemvisualInspection.getModule());
            ps.setString(3, itemvisualInspection.getPcb());
            ps.setString(4, itemvisualInspection.getPcbReject());
            ps.setString(5, itemvisualInspection.getHandle());
            ps.setString(6, itemvisualInspection.getHandleReject());
            ps.setString(7, itemvisualInspection.getMetalFrame());
            ps.setString(8, itemvisualInspection.getMetalFrameReject());
            ps.setString(9, itemvisualInspection.getHardwareFasterners());
            ps.setString(10, itemvisualInspection.getHardwareFasternersReject());
            ps.setString(11, itemvisualInspection.getClipHolder());
            ps.setString(12, itemvisualInspection.getClipHolderReject());
            ps.setString(13, itemvisualInspection.getPcbEdgeFinger());
            ps.setString(14, itemvisualInspection.getPcbEdgeFingerReject());
            ps.setString(15, itemvisualInspection.getConnector());
            ps.setString(16, itemvisualInspection.getConnectorReject());
            ps.setString(17, itemvisualInspection.getDutSockets());
            ps.setString(18, itemvisualInspection.getDutSocketsReject());
            ps.setString(19, itemvisualInspection.getEdgeMbBanana());
            ps.setString(20, itemvisualInspection.getEdgeMbBananaReject());
            ps.setString(21, itemvisualInspection.getElectComponent());
            ps.setString(22, itemvisualInspection.getElectComponentReject());
            ps.setString(23, itemvisualInspection.getSolderJoint());
            ps.setString(24, itemvisualInspection.getSolderJointReject());
            ps.setString(25, itemvisualInspection.getWinConnector());
            ps.setString(26, itemvisualInspection.getWinConnectorReject());
            ps.setString(27, itemvisualInspection.getRemarks());
            ps.setString(28, itemvisualInspection.getFinalStatus());
            ps.setString(29, itemvisualInspection.getCreatedBy());
            ps.setString(30, itemvisualInspection.getCreatedDate());
            ps.setString(31, itemvisualInspection.getFlag());

            ps.setString(32, itemvisualInspection.getPcbRejectQty());
            ps.setString(33, itemvisualInspection.getHandleRejectQty());
            ps.setString(34, itemvisualInspection.getMetalFrameRejectQty());
            ps.setString(35, itemvisualInspection.getHardwareFasternersRejectQty());
            ps.setString(36, itemvisualInspection.getClipHolderRejectQty());
            ps.setString(37, itemvisualInspection.getPcbEdgeFingerRejectQty());
            ps.setString(38, itemvisualInspection.getConnectorRejectQty());
            ps.setString(39, itemvisualInspection.getDutSocketsRejectQty());
            ps.setString(40, itemvisualInspection.getEdgeMbBananaRejectQty());
            ps.setString(41, itemvisualInspection.getElectComponentRejectQty());
            ps.setString(42, itemvisualInspection.getSolderJointRejectQty());
            ps.setString(43, itemvisualInspection.getWinConnectorRejectQty());

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

    public QueryResult updateItemVisualInspection(ItemVisualInspection itemvisualInspection) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_visual_inspection SET mib_item_id = ?, module = ?, pcb = ?, pcb_reject = ?, handle = ?, handle_reject = ?, metal_frame = ?, metal_frame_reject = ?, hardware_fasterners = ?, hardware_fasterners_reject = ?, clip_holder = ?, clip_holder_reject = ?, pcb_edge_finger = ?, pcb_edge_finger_reject = ?, connector = ?, connector_reject = ?, dut_sockets = ?, dut_sockets_reject = ?, edge_mb_banana = ?, edge_mb_banana_reject = ?, elect_component = ?, elect_component_reject = ?, solder_joint = ?, solder_joint_reject = ?, win_connector = ?, win_connector_reject = ?, remarks = ?, final_status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, itemvisualInspection.getMibItemId());
            ps.setString(2, itemvisualInspection.getModule());
            ps.setString(3, itemvisualInspection.getPcb());
            ps.setString(4, itemvisualInspection.getPcbReject());
            ps.setString(5, itemvisualInspection.getHandle());
            ps.setString(6, itemvisualInspection.getHandleReject());
            ps.setString(7, itemvisualInspection.getMetalFrame());
            ps.setString(8, itemvisualInspection.getMetalFrameReject());
            ps.setString(9, itemvisualInspection.getHardwareFasterners());
            ps.setString(10, itemvisualInspection.getHardwareFasternersReject());
            ps.setString(11, itemvisualInspection.getClipHolder());
            ps.setString(12, itemvisualInspection.getClipHolderReject());
            ps.setString(13, itemvisualInspection.getPcbEdgeFinger());
            ps.setString(14, itemvisualInspection.getPcbEdgeFingerReject());
            ps.setString(15, itemvisualInspection.getConnector());
            ps.setString(16, itemvisualInspection.getConnectorReject());
            ps.setString(17, itemvisualInspection.getDutSockets());
            ps.setString(18, itemvisualInspection.getDutSocketsReject());
            ps.setString(19, itemvisualInspection.getEdgeMbBanana());
            ps.setString(20, itemvisualInspection.getEdgeMbBananaReject());
            ps.setString(21, itemvisualInspection.getElectComponent());
            ps.setString(22, itemvisualInspection.getElectComponentReject());
            ps.setString(23, itemvisualInspection.getSolderJoint());
            ps.setString(24, itemvisualInspection.getSolderJointReject());
            ps.setString(25, itemvisualInspection.getWinConnector());
            ps.setString(26, itemvisualInspection.getWinConnectorReject());
            ps.setString(27, itemvisualInspection.getRemarks());
            ps.setString(28, itemvisualInspection.getFinalStatus());
            ps.setString(29, itemvisualInspection.getCreatedBy());
            ps.setString(30, itemvisualInspection.getCreatedDate());
            ps.setString(31, itemvisualInspection.getFlag());
            ps.setString(32, itemvisualInspection.getId());
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

    public QueryResult updateItemVisualInspectionForAttachment(ItemVisualInspection itemvisualInspection) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item_visual_inspection SET pcb_reject_upload = ?, handle_reject_upload = ?, metal_frame_reject_upload = ?, hardware_fasterners_reject_upload = ?, clip_holder_reject_upload = ?, pcb_edge_finger_reject_upload = ?, "
                    + "connector_reject_upload = ?, dut_sockets_reject_upload = ?, edge_mb_banana_reject_upload = ?, elect_component_reject_upload = ?, solder_joint_reject_upload = ?, win_connector_reject_upload = ? WHERE id = ?"
            );
            ps.setString(1, itemvisualInspection.getPcbRejectUpload());
            ps.setString(2, itemvisualInspection.getHandleRejectUpload());
            ps.setString(3, itemvisualInspection.getMetalFrameRejectUpload());
            ps.setString(4, itemvisualInspection.getHardwareFasternersRejectUpload());
            ps.setString(5, itemvisualInspection.getClipHolderRejectUpload());
            ps.setString(6, itemvisualInspection.getPcbEdgeFingerRejectUpload());
            ps.setString(7, itemvisualInspection.getConnectorRejectUpload());
            ps.setString(8, itemvisualInspection.getDutSocketsRejectUpload());
            ps.setString(9, itemvisualInspection.getEdgeMbBananaRejectUpload());
            ps.setString(10, itemvisualInspection.getElectComponentRejectUpload());
            ps.setString(11, itemvisualInspection.getSolderJointRejectUpload());
            ps.setString(12, itemvisualInspection.getWinConnectorRejectUpload());
            ps.setString(13, itemvisualInspection.getId());
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

    public QueryResult deleteItemVisualInspection(String itemvisualInspectionId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM item_visual_inspection WHERE id = '" + itemvisualInspectionId + "'"
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

    public ItemVisualInspection getItemVisualInspection(String itemvisualInspectionId) {
        String sql = "SELECT * FROM item_visual_inspection WHERE id = '" + itemvisualInspectionId + "'";
        ItemVisualInspection itemvisualInspection = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemvisualInspection = new ItemVisualInspection();
                itemvisualInspection.setId(rs.getString("id"));
                itemvisualInspection.setMibItemId(rs.getString("mib_item_id"));
                itemvisualInspection.setModule(rs.getString("module"));
                itemvisualInspection.setPcb(rs.getString("pcb"));
                itemvisualInspection.setPcbReject(rs.getString("pcb_reject"));
                itemvisualInspection.setPcbRejectQty(rs.getString("pcb_reject_qty"));
                itemvisualInspection.setPcbRejectUpload(rs.getString("pcb_reject_upload"));
                itemvisualInspection.setHandle(rs.getString("handle"));
                itemvisualInspection.setHandleReject(rs.getString("handle_reject"));
                itemvisualInspection.setHandleRejectQty(rs.getString("handle_reject_qty"));
                itemvisualInspection.setHandleRejectUpload(rs.getString("handle_reject_upload"));
                itemvisualInspection.setMetalFrame(rs.getString("metal_frame"));
                itemvisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                itemvisualInspection.setMetalFrameRejectQty(rs.getString("metal_frame_reject_qty"));
                itemvisualInspection.setMetalFrameRejectUpload(rs.getString("metal_frame_reject_upload"));
                itemvisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                itemvisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                itemvisualInspection.setHardwareFasternersRejectQty(rs.getString("hardware_fasterners_reject_qty"));
                itemvisualInspection.setHardwareFasternersRejectUpload(rs.getString("hardware_fasterners_reject_upload"));
                itemvisualInspection.setClipHolder(rs.getString("clip_holder"));
                itemvisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                itemvisualInspection.setClipHolderRejectQty(rs.getString("clip_holder_reject_qty"));
                itemvisualInspection.setClipHolderRejectUpload(rs.getString("clip_holder_reject_upload"));
                itemvisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                itemvisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                itemvisualInspection.setPcbEdgeFingerRejectQty(rs.getString("pcb_edge_finger_reject_qty"));
                itemvisualInspection.setPcbEdgeFingerRejectUpload(rs.getString("pcb_edge_finger_reject_upload"));
                itemvisualInspection.setConnector(rs.getString("connector"));
                itemvisualInspection.setConnectorReject(rs.getString("connector_reject"));
                itemvisualInspection.setConnectorRejectQty(rs.getString("connector_reject_qty"));
                itemvisualInspection.setConnectorRejectUpload(rs.getString("connector_reject_upload"));
                itemvisualInspection.setDutSockets(rs.getString("dut_sockets"));
                itemvisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                itemvisualInspection.setDutSocketsRejectQty(rs.getString("dut_sockets_reject_qty"));
                itemvisualInspection.setDutSocketsRejectUpload(rs.getString("dut_sockets_reject_upload"));
                itemvisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                itemvisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                itemvisualInspection.setEdgeMbBananaRejectQty(rs.getString("edge_mb_banana_reject_qty"));
                itemvisualInspection.setEdgeMbBananaRejectUpload(rs.getString("edge_mb_banana_reject_upload"));
                itemvisualInspection.setElectComponent(rs.getString("elect_component"));
                itemvisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                itemvisualInspection.setElectComponentRejectQty(rs.getString("elect_component_reject_qty"));
                itemvisualInspection.setElectComponentRejectUpload(rs.getString("elect_component_reject_upload"));
                itemvisualInspection.setSolderJoint(rs.getString("solder_joint"));
                itemvisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                itemvisualInspection.setSolderJointRejectQty(rs.getString("solder_joint_reject_qty"));
                itemvisualInspection.setSolderJointRejectUpload(rs.getString("solder_joint_reject_upload"));
                itemvisualInspection.setWinConnector(rs.getString("win_connector"));
                itemvisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                itemvisualInspection.setWinConnectorRejectQty(rs.getString("win_connector_reject_qty"));
                itemvisualInspection.setWinConnectorRejectUpload(rs.getString("win_connector_reject_upload"));
                itemvisualInspection.setRemarks(rs.getString("remarks"));
                itemvisualInspection.setFinalStatus(rs.getString("final_status"));
                itemvisualInspection.setCreatedBy(rs.getString("created_by"));
                itemvisualInspection.setCreatedDate(rs.getString("created_date"));
                itemvisualInspection.setFlag(rs.getString("flag"));
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
        return itemvisualInspection;
    }

    public ItemVisualInspection getItemVisualInspectionByMibItemIdWithModuleItemRegistration(String mibItemId) {
        String sql = "SELECT * FROM item_visual_inspection WHERE mib_item_id = '" + mibItemId + "' AND module = 'Item Registration'";
        ItemVisualInspection itemvisualInspection = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemvisualInspection = new ItemVisualInspection();
                itemvisualInspection.setId(rs.getString("id"));
                itemvisualInspection.setMibItemId(rs.getString("mib_item_id"));
                itemvisualInspection.setModule(rs.getString("module"));
                itemvisualInspection.setPcb(rs.getString("pcb"));
                itemvisualInspection.setPcbReject(rs.getString("pcb_reject"));
                itemvisualInspection.setPcbRejectQty(rs.getString("pcb_reject_qty"));
                itemvisualInspection.setPcbRejectUpload(rs.getString("pcb_reject_upload"));
                itemvisualInspection.setHandle(rs.getString("handle"));
                itemvisualInspection.setHandleReject(rs.getString("handle_reject"));
                itemvisualInspection.setHandleRejectQty(rs.getString("handle_reject_qty"));
                itemvisualInspection.setHandleRejectUpload(rs.getString("handle_reject_upload"));
                itemvisualInspection.setMetalFrame(rs.getString("metal_frame"));
                itemvisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                itemvisualInspection.setMetalFrameRejectQty(rs.getString("metal_frame_reject_qty"));
                itemvisualInspection.setMetalFrameRejectUpload(rs.getString("metal_frame_reject_upload"));
                itemvisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                itemvisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                itemvisualInspection.setHardwareFasternersRejectQty(rs.getString("hardware_fasterners_reject_qty"));
                itemvisualInspection.setHardwareFasternersRejectUpload(rs.getString("hardware_fasterners_reject_upload"));
                itemvisualInspection.setClipHolder(rs.getString("clip_holder"));
                itemvisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                itemvisualInspection.setClipHolderRejectQty(rs.getString("clip_holder_reject_qty"));
                itemvisualInspection.setClipHolderRejectUpload(rs.getString("clip_holder_reject_upload"));
                itemvisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                itemvisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                itemvisualInspection.setPcbEdgeFingerRejectQty(rs.getString("pcb_edge_finger_reject_qty"));
                itemvisualInspection.setPcbEdgeFingerRejectUpload(rs.getString("pcb_edge_finger_reject_upload"));
                itemvisualInspection.setConnector(rs.getString("connector"));
                itemvisualInspection.setConnectorReject(rs.getString("connector_reject"));
                itemvisualInspection.setConnectorRejectQty(rs.getString("connector_reject_qty"));
                itemvisualInspection.setConnectorRejectUpload(rs.getString("connector_reject_upload"));
                itemvisualInspection.setDutSockets(rs.getString("dut_sockets"));
                itemvisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                itemvisualInspection.setDutSocketsRejectQty(rs.getString("dut_sockets_reject_qty"));
                itemvisualInspection.setDutSocketsRejectUpload(rs.getString("dut_sockets_reject_upload"));
                itemvisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                itemvisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                itemvisualInspection.setEdgeMbBananaRejectQty(rs.getString("edge_mb_banana_reject_qty"));
                itemvisualInspection.setEdgeMbBananaRejectUpload(rs.getString("edge_mb_banana_reject_upload"));
                itemvisualInspection.setElectComponent(rs.getString("elect_component"));
                itemvisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                itemvisualInspection.setElectComponentRejectQty(rs.getString("elect_component_reject_qty"));
                itemvisualInspection.setElectComponentRejectUpload(rs.getString("elect_component_reject_upload"));
                itemvisualInspection.setSolderJoint(rs.getString("solder_joint"));
                itemvisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                itemvisualInspection.setSolderJointRejectQty(rs.getString("solder_joint_reject_qty"));
                itemvisualInspection.setSolderJointRejectUpload(rs.getString("solder_joint_reject_upload"));
                itemvisualInspection.setWinConnector(rs.getString("win_connector"));
                itemvisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                itemvisualInspection.setWinConnectorRejectQty(rs.getString("win_connector_reject_qty"));
                itemvisualInspection.setWinConnectorRejectUpload(rs.getString("win_connector_reject_upload"));
                itemvisualInspection.setRemarks(rs.getString("remarks"));
                itemvisualInspection.setFinalStatus(rs.getString("final_status"));
                itemvisualInspection.setCreatedBy(rs.getString("created_by"));
                itemvisualInspection.setCreatedDate(rs.getString("created_date"));
                itemvisualInspection.setFlag(rs.getString("flag"));
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
        return itemvisualInspection;
    }

    public List<ItemVisualInspection> getItemVisualInspectionList() {
        String sql = "SELECT * FROM item_visual_inspection ORDER BY id ASC";
        List<ItemVisualInspection> itemvisualInspectionList = new ArrayList<ItemVisualInspection>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ItemVisualInspection itemvisualInspection;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemvisualInspection = new ItemVisualInspection();
                itemvisualInspection.setId(rs.getString("id"));
                itemvisualInspection.setMibItemId(rs.getString("mib_item_id"));
                itemvisualInspection.setModule(rs.getString("module"));
                itemvisualInspection.setPcb(rs.getString("pcb"));
                itemvisualInspection.setPcbReject(rs.getString("pcb_reject"));
                itemvisualInspection.setHandle(rs.getString("handle"));
                itemvisualInspection.setHandleReject(rs.getString("handle_reject"));
                itemvisualInspection.setMetalFrame(rs.getString("metal_frame"));
                itemvisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                itemvisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                itemvisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                itemvisualInspection.setClipHolder(rs.getString("clip_holder"));
                itemvisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                itemvisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                itemvisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                itemvisualInspection.setConnector(rs.getString("connector"));
                itemvisualInspection.setConnectorReject(rs.getString("connector_reject"));
                itemvisualInspection.setDutSockets(rs.getString("dut_sockets"));
                itemvisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                itemvisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                itemvisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                itemvisualInspection.setElectComponent(rs.getString("elect_component"));
                itemvisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                itemvisualInspection.setSolderJoint(rs.getString("solder_joint"));
                itemvisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                itemvisualInspection.setWinConnector(rs.getString("win_connector"));
                itemvisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                itemvisualInspection.setRemarks(rs.getString("remarks"));
                itemvisualInspection.setFinalStatus(rs.getString("final_status"));
                itemvisualInspection.setCreatedBy(rs.getString("created_by"));
                itemvisualInspection.setCreatedDate(rs.getString("created_date"));
                itemvisualInspection.setFlag(rs.getString("flag"));
                itemvisualInspectionList.add(itemvisualInspection);
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
        return itemvisualInspectionList;
    }

    public Integer getCountItemIdWithModuleItemRegistration(String MibItemId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM item_visual_inspection inc WHERE inc.mib_item_id = '" + MibItemId + "' AND inc.module = 'Item Registration'"
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
