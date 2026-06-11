package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.ItemVisualInspection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.model.RmsBookingVisualInspection;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmsBookingVisualInspectionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingVisualInspectionDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RmsBookingVisualInspectionDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRmsBookingVisualInspection(RmsBookingVisualInspection rmsbookingVisualInspection) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO rms_booking_visual_inspection (group_id, module, pcb, pcb_reject, pcb_reject_upload, pcb_reject_qty, handle, handle_reject, "
                    + "handle_reject_upload, handle_reject_qty, metal_frame, metal_frame_reject, metal_frame_reject_upload, metal_frame_reject_qty, "
                    + "hardware_fasterners, hardware_fasterners_reject, hardware_fasterners_reject_upload, hardware_fasterners_reject_qty, clip_holder, "
                    + "clip_holder_reject, clip_holder_reject_upload, clip_holder_reject_qty, pcb_edge_finger, pcb_edge_finger_reject, pcb_edge_finger_reject_upload, "
                    + "pcb_edge_finger_reject_qty, connector, connector_reject, connector_reject_upload, connector_reject_qty, dut_sockets, dut_sockets_reject, "
                    + "dut_sockets_reject_upload, dut_sockets_reject_qty, edge_mb_banana, edge_mb_banana_reject, edge_mb_banana_reject_upload, edge_mb_banana_reject_qty, "
                    + "elect_component, elect_component_reject, elect_component_reject_upload, elect_component_reject_qty, solder_joint, solder_joint_reject, "
                    + "solder_joint_reject_upload, solder_joint_reject_qty, win_connector, win_connector_reject, win_connector_reject_upload, win_connector_reject_qty, "
                    + "remarks, final_status, created_by, created_date, flag, pcb_hardware_id,handle_hardware_id,metal_frame_hardware_id,hardware_fasterners_hardware_id,clip_holder_hardware_id,"
                    + "pcb_edge_finger_hardware_id,connector_hardware_id,dut_sockets_hardware_id,edge_mb_banana_hardware_id,elect_component_hardware_id,solder_joint_hardware_id,win_connector_hardware_id,"
                    + "teflon_connector,teflon_connector_hardware_id, teflon_connector_reject,teflon_connector_reject_qty,pogo_receptacles_pin,pogo_receptacles_pin_hardware_id,pogo_receptacles_pin_reject,pogo_receptacles_pin_reject_qty,"
                    + "cable_wired_copper_wire,cable_wired_copper_wire_hardware_id,cable_wired_copper_wire_reject, cable_wired_copper_wire_reject_qty,label_identification, label_identification_hardware_id, label_identification_reject,label_identification_reject_qty) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, rmsbookingVisualInspection.getGroupId());
            ps.setString(2, rmsbookingVisualInspection.getModule());
            ps.setString(3, rmsbookingVisualInspection.getPcb());
            ps.setString(4, rmsbookingVisualInspection.getPcbReject());
            ps.setString(5, rmsbookingVisualInspection.getPcbRejectUpload());
            ps.setString(6, rmsbookingVisualInspection.getPcbRejectQty());
            ps.setString(7, rmsbookingVisualInspection.getHandle());
            ps.setString(8, rmsbookingVisualInspection.getHandleReject());
            ps.setString(9, rmsbookingVisualInspection.getHandleRejectUpload());
            ps.setString(10, rmsbookingVisualInspection.getHandleRejectQty());
            ps.setString(11, rmsbookingVisualInspection.getMetalFrame());
            ps.setString(12, rmsbookingVisualInspection.getMetalFrameReject());
            ps.setString(13, rmsbookingVisualInspection.getMetalFrameRejectUpload());
            ps.setString(14, rmsbookingVisualInspection.getMetalFrameRejectQty());
            ps.setString(15, rmsbookingVisualInspection.getHardwareFasterners());
            ps.setString(16, rmsbookingVisualInspection.getHardwareFasternersReject());
            ps.setString(17, rmsbookingVisualInspection.getHardwareFasternersRejectUpload());
            ps.setString(18, rmsbookingVisualInspection.getHardwareFasternersRejectQty());
            ps.setString(19, rmsbookingVisualInspection.getClipHolder());
            ps.setString(20, rmsbookingVisualInspection.getClipHolderReject());
            ps.setString(21, rmsbookingVisualInspection.getClipHolderRejectUpload());
            ps.setString(22, rmsbookingVisualInspection.getClipHolderRejectQty());
            ps.setString(23, rmsbookingVisualInspection.getPcbEdgeFinger());
            ps.setString(24, rmsbookingVisualInspection.getPcbEdgeFingerReject());
            ps.setString(25, rmsbookingVisualInspection.getPcbEdgeFingerRejectUpload());
            ps.setString(26, rmsbookingVisualInspection.getPcbEdgeFingerRejectQty());
            ps.setString(27, rmsbookingVisualInspection.getConnector());
            ps.setString(28, rmsbookingVisualInspection.getConnectorReject());
            ps.setString(29, rmsbookingVisualInspection.getConnectorRejectUpload());
            ps.setString(30, rmsbookingVisualInspection.getConnectorRejectQty());
            ps.setString(31, rmsbookingVisualInspection.getDutSockets());
            ps.setString(32, rmsbookingVisualInspection.getDutSocketsReject());
            ps.setString(33, rmsbookingVisualInspection.getDutSocketsRejectUpload());
            ps.setString(34, rmsbookingVisualInspection.getDutSocketsRejectQty());
            ps.setString(35, rmsbookingVisualInspection.getEdgeMbBanana());
            ps.setString(36, rmsbookingVisualInspection.getEdgeMbBananaReject());
            ps.setString(37, rmsbookingVisualInspection.getEdgeMbBananaRejectUpload());
            ps.setString(38, rmsbookingVisualInspection.getEdgeMbBananaRejectQty());
            ps.setString(39, rmsbookingVisualInspection.getElectComponent());
            ps.setString(40, rmsbookingVisualInspection.getElectComponentReject());
            ps.setString(41, rmsbookingVisualInspection.getElectComponentRejectUpload());
            ps.setString(42, rmsbookingVisualInspection.getElectComponentRejectQty());
            ps.setString(43, rmsbookingVisualInspection.getSolderJoint());
            ps.setString(44, rmsbookingVisualInspection.getSolderJointReject());
            ps.setString(45, rmsbookingVisualInspection.getSolderJointRejectUpload());
            ps.setString(46, rmsbookingVisualInspection.getSolderJointRejectQty());
            ps.setString(47, rmsbookingVisualInspection.getWinConnector());
            ps.setString(48, rmsbookingVisualInspection.getWinConnectorReject());
            ps.setString(49, rmsbookingVisualInspection.getWinConnectorRejectUpload());
            ps.setString(50, rmsbookingVisualInspection.getWinConnectorRejectQty());
            ps.setString(51, rmsbookingVisualInspection.getRemarks());
            ps.setString(52, rmsbookingVisualInspection.getFinalStatus());
            ps.setString(53, rmsbookingVisualInspection.getCreatedBy());
            ps.setString(54, rmsbookingVisualInspection.getFlag());
            ps.setString(55, rmsbookingVisualInspection.getPcbHardwareId());
            ps.setString(56, rmsbookingVisualInspection.getHandleHardwareId());
            ps.setString(57, rmsbookingVisualInspection.getMetalFrameHardwareId());
            ps.setString(58, rmsbookingVisualInspection.getHardwareFasternersHardwareId());
            ps.setString(59, rmsbookingVisualInspection.getClipHolderHardwareId());
            ps.setString(60, rmsbookingVisualInspection.getPcbEdgeFingerHardwareId());
            ps.setString(61, rmsbookingVisualInspection.getConnectorHardwareId());
            ps.setString(62, rmsbookingVisualInspection.getDutSocketsHardwareId());
            ps.setString(63, rmsbookingVisualInspection.getEdgeMbBananaHardwareId());
            ps.setString(64, rmsbookingVisualInspection.getElectComponentHardwareId());
            ps.setString(65, rmsbookingVisualInspection.getSolderJointHardwareId());
            ps.setString(66, rmsbookingVisualInspection.getWinConnectorHardwareId());
            ps.setString(67, rmsbookingVisualInspection.getTeflonConnector());
            ps.setString(68, rmsbookingVisualInspection.getTeflonConnectorHardwareId());
            ps.setString(69, rmsbookingVisualInspection.getTeflonConnectorReject());
            ps.setString(70, rmsbookingVisualInspection.getTeflonConnectorRejectQty());
            ps.setString(71, rmsbookingVisualInspection.getPogoReceptaclesPin());
            ps.setString(72, rmsbookingVisualInspection.getPogoReceptaclesPinHardwareId());
            ps.setString(73, rmsbookingVisualInspection.getPogoReceptaclesPinReject());
            ps.setString(74, rmsbookingVisualInspection.getPogoReceptaclesPinRejectQty());
            ps.setString(75, rmsbookingVisualInspection.getCableWiredCopperWire());
            ps.setString(76, rmsbookingVisualInspection.getCableWiredCopperWireHardwareId());
            ps.setString(77, rmsbookingVisualInspection.getCableWiredCopperWireReject());
            ps.setString(78, rmsbookingVisualInspection.getCableWiredCopperWireRejectQty());
            ps.setString(79, rmsbookingVisualInspection.getLabelIdentification());
            ps.setString(80, rmsbookingVisualInspection.getLabelIdentificationHardwareId());
            ps.setString(81, rmsbookingVisualInspection.getLabelIdentificationReject());
            ps.setString(82, rmsbookingVisualInspection.getLabelIdentificationRejectQty());
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

    public QueryResult updateRmsBookingVisualInspection(RmsBookingVisualInspection rmsbookingVisualInspection) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_visual_inspection SET group_id = ?, module = ?, pcb = ?, pcb_reject = ?, pcb_reject_upload = ?, pcb_reject_qty = ?, handle = ?, handle_reject = ?, handle_reject_upload = ?, handle_reject_qty = ?, metal_frame = ?, metal_frame_reject = ?, metal_frame_reject_upload = ?, metal_frame_reject_qty = ?, hardware_fasterners = ?, hardware_fasterners_reject = ?, hardware_fasterners_reject_upload = ?, hardware_fasterners_reject_qty = ?, clip_holder = ?, clip_holder_reject = ?, clip_holder_reject_upload = ?, clip_holder_reject_qty = ?, pcb_edge_finger = ?, pcb_edge_finger_reject = ?, pcb_edge_finger_reject_upload = ?, pcb_edge_finger_reject_qty = ?, connector = ?, connector_reject = ?, connector_reject_upload = ?, connector_reject_qty = ?, dut_sockets = ?, dut_sockets_reject = ?, dut_sockets_reject_upload = ?, dut_sockets_reject_qty = ?, edge_mb_banana = ?, edge_mb_banana_reject = ?, edge_mb_banana_reject_upload = ?, edge_mb_banana_reject_qty = ?, elect_component = ?, elect_component_reject = ?, elect_component_reject_upload = ?, elect_component_reject_qty = ?, solder_joint = ?, solder_joint_reject = ?, solder_joint_reject_upload = ?, solder_joint_reject_qty = ?, win_connector = ?, win_connector_reject = ?, win_connector_reject_upload = ?, win_connector_reject_qty = ?, remarks = ?, final_status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, rmsbookingVisualInspection.getGroupId());
            ps.setString(2, rmsbookingVisualInspection.getModule());
            ps.setString(3, rmsbookingVisualInspection.getPcb());
            ps.setString(4, rmsbookingVisualInspection.getPcbReject());
            ps.setString(5, rmsbookingVisualInspection.getPcbRejectUpload());
            ps.setString(6, rmsbookingVisualInspection.getPcbRejectQty());
            ps.setString(7, rmsbookingVisualInspection.getHandle());
            ps.setString(8, rmsbookingVisualInspection.getHandleReject());
            ps.setString(9, rmsbookingVisualInspection.getHandleRejectUpload());
            ps.setString(10, rmsbookingVisualInspection.getHandleRejectQty());
            ps.setString(11, rmsbookingVisualInspection.getMetalFrame());
            ps.setString(12, rmsbookingVisualInspection.getMetalFrameReject());
            ps.setString(13, rmsbookingVisualInspection.getMetalFrameRejectUpload());
            ps.setString(14, rmsbookingVisualInspection.getMetalFrameRejectQty());
            ps.setString(15, rmsbookingVisualInspection.getHardwareFasterners());
            ps.setString(16, rmsbookingVisualInspection.getHardwareFasternersReject());
            ps.setString(17, rmsbookingVisualInspection.getHardwareFasternersRejectUpload());
            ps.setString(18, rmsbookingVisualInspection.getHardwareFasternersRejectQty());
            ps.setString(19, rmsbookingVisualInspection.getClipHolder());
            ps.setString(20, rmsbookingVisualInspection.getClipHolderReject());
            ps.setString(21, rmsbookingVisualInspection.getClipHolderRejectUpload());
            ps.setString(22, rmsbookingVisualInspection.getClipHolderRejectQty());
            ps.setString(23, rmsbookingVisualInspection.getPcbEdgeFinger());
            ps.setString(24, rmsbookingVisualInspection.getPcbEdgeFingerReject());
            ps.setString(25, rmsbookingVisualInspection.getPcbEdgeFingerRejectUpload());
            ps.setString(26, rmsbookingVisualInspection.getPcbEdgeFingerRejectQty());
            ps.setString(27, rmsbookingVisualInspection.getConnector());
            ps.setString(28, rmsbookingVisualInspection.getConnectorReject());
            ps.setString(29, rmsbookingVisualInspection.getConnectorRejectUpload());
            ps.setString(30, rmsbookingVisualInspection.getConnectorRejectQty());
            ps.setString(31, rmsbookingVisualInspection.getDutSockets());
            ps.setString(32, rmsbookingVisualInspection.getDutSocketsReject());
            ps.setString(33, rmsbookingVisualInspection.getDutSocketsRejectUpload());
            ps.setString(34, rmsbookingVisualInspection.getDutSocketsRejectQty());
            ps.setString(35, rmsbookingVisualInspection.getEdgeMbBanana());
            ps.setString(36, rmsbookingVisualInspection.getEdgeMbBananaReject());
            ps.setString(37, rmsbookingVisualInspection.getEdgeMbBananaRejectUpload());
            ps.setString(38, rmsbookingVisualInspection.getEdgeMbBananaRejectQty());
            ps.setString(39, rmsbookingVisualInspection.getElectComponent());
            ps.setString(40, rmsbookingVisualInspection.getElectComponentReject());
            ps.setString(41, rmsbookingVisualInspection.getElectComponentRejectUpload());
            ps.setString(42, rmsbookingVisualInspection.getElectComponentRejectQty());
            ps.setString(43, rmsbookingVisualInspection.getSolderJoint());
            ps.setString(44, rmsbookingVisualInspection.getSolderJointReject());
            ps.setString(45, rmsbookingVisualInspection.getSolderJointRejectUpload());
            ps.setString(46, rmsbookingVisualInspection.getSolderJointRejectQty());
            ps.setString(47, rmsbookingVisualInspection.getWinConnector());
            ps.setString(48, rmsbookingVisualInspection.getWinConnectorReject());
            ps.setString(49, rmsbookingVisualInspection.getWinConnectorRejectUpload());
            ps.setString(50, rmsbookingVisualInspection.getWinConnectorRejectQty());
            ps.setString(51, rmsbookingVisualInspection.getRemarks());
            ps.setString(52, rmsbookingVisualInspection.getFinalStatus());
            ps.setString(53, rmsbookingVisualInspection.getCreatedBy());
            ps.setString(54, rmsbookingVisualInspection.getCreatedDate());
            ps.setString(55, rmsbookingVisualInspection.getFlag());
            ps.setString(56, rmsbookingVisualInspection.getId());
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

    public QueryResult updateItemVisualInspectionForAttachment(RmsBookingVisualInspection itemvisualInspection) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE rms_booking_visual_inspection SET pcb_reject_upload = ?, handle_reject_upload = ?, metal_frame_reject_upload = ?, hardware_fasterners_reject_upload = ?, clip_holder_reject_upload = ?, pcb_edge_finger_reject_upload = ?, "
                    + "connector_reject_upload = ?, dut_sockets_reject_upload = ?, edge_mb_banana_reject_upload = ?, elect_component_reject_upload = ?, solder_joint_reject_upload = ?, win_connector_reject_upload = ?, "
                    + "teflon_connector_reject_upload = ?, pogo_receptacles_pin_reject_upload = ?, cable_wired_copper_wire_reject_upload = ?, label_identification_reject_upload = ? WHERE id = ?"
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
            ps.setString(13, itemvisualInspection.getTeflonConnectorRejectUpload());
            ps.setString(14, itemvisualInspection.getPogoReceptaclesPinRejectUpload());
            ps.setString(15, itemvisualInspection.getCableWiredCopperWireRejectUpload());
            ps.setString(16, itemvisualInspection.getLabelIdentificationRejectUpload());
            ps.setString(17, itemvisualInspection.getId());
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

    public QueryResult deleteRmsBookingVisualInspection(String rmsbookingVisualInspectionId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM rms_booking_visual_inspection WHERE id = '" + rmsbookingVisualInspectionId + "'"
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

    public RmsBookingVisualInspection getRmsBookingVisualInspection(String rmsbookingVisualInspectionId) {
        String sql = "SELECT * FROM rms_booking_visual_inspection WHERE id = '" + rmsbookingVisualInspectionId + "'";
        RmsBookingVisualInspection rmsbookingVisualInspection = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingVisualInspection = new RmsBookingVisualInspection();
                rmsbookingVisualInspection.setId(rs.getString("id"));
                rmsbookingVisualInspection.setGroupId(rs.getString("group_id"));
                rmsbookingVisualInspection.setModule(rs.getString("module"));
                rmsbookingVisualInspection.setPcb(rs.getString("pcb"));
                rmsbookingVisualInspection.setPcbReject(rs.getString("pcb_reject"));
                rmsbookingVisualInspection.setPcbRejectUpload(rs.getString("pcb_reject_upload"));
                rmsbookingVisualInspection.setPcbRejectQty(rs.getString("pcb_reject_qty"));
                rmsbookingVisualInspection.setHandle(rs.getString("handle"));
                rmsbookingVisualInspection.setHandleReject(rs.getString("handle_reject"));
                rmsbookingVisualInspection.setHandleRejectUpload(rs.getString("handle_reject_upload"));
                rmsbookingVisualInspection.setHandleRejectQty(rs.getString("handle_reject_qty"));
                rmsbookingVisualInspection.setMetalFrame(rs.getString("metal_frame"));
                rmsbookingVisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                rmsbookingVisualInspection.setMetalFrameRejectUpload(rs.getString("metal_frame_reject_upload"));
                rmsbookingVisualInspection.setMetalFrameRejectQty(rs.getString("metal_frame_reject_qty"));
                rmsbookingVisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                rmsbookingVisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                rmsbookingVisualInspection.setHardwareFasternersRejectUpload(rs.getString("hardware_fasterners_reject_upload"));
                rmsbookingVisualInspection.setHardwareFasternersRejectQty(rs.getString("hardware_fasterners_reject_qty"));
                rmsbookingVisualInspection.setClipHolder(rs.getString("clip_holder"));
                rmsbookingVisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                rmsbookingVisualInspection.setClipHolderRejectUpload(rs.getString("clip_holder_reject_upload"));
                rmsbookingVisualInspection.setClipHolderRejectQty(rs.getString("clip_holder_reject_qty"));
                rmsbookingVisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                rmsbookingVisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectUpload(rs.getString("pcb_edge_finger_reject_upload"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectQty(rs.getString("pcb_edge_finger_reject_qty"));
                rmsbookingVisualInspection.setConnector(rs.getString("connector"));
                rmsbookingVisualInspection.setConnectorReject(rs.getString("connector_reject"));
                rmsbookingVisualInspection.setConnectorRejectUpload(rs.getString("connector_reject_upload"));
                rmsbookingVisualInspection.setConnectorRejectQty(rs.getString("connector_reject_qty"));
                rmsbookingVisualInspection.setDutSockets(rs.getString("dut_sockets"));
                rmsbookingVisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                rmsbookingVisualInspection.setDutSocketsRejectUpload(rs.getString("dut_sockets_reject_upload"));
                rmsbookingVisualInspection.setDutSocketsRejectQty(rs.getString("dut_sockets_reject_qty"));
                rmsbookingVisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                rmsbookingVisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectUpload(rs.getString("edge_mb_banana_reject_upload"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectQty(rs.getString("edge_mb_banana_reject_qty"));
                rmsbookingVisualInspection.setElectComponent(rs.getString("elect_component"));
                rmsbookingVisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                rmsbookingVisualInspection.setElectComponentRejectUpload(rs.getString("elect_component_reject_upload"));
                rmsbookingVisualInspection.setElectComponentRejectQty(rs.getString("elect_component_reject_qty"));
                rmsbookingVisualInspection.setSolderJoint(rs.getString("solder_joint"));
                rmsbookingVisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                rmsbookingVisualInspection.setSolderJointRejectUpload(rs.getString("solder_joint_reject_upload"));
                rmsbookingVisualInspection.setSolderJointRejectQty(rs.getString("solder_joint_reject_qty"));
                rmsbookingVisualInspection.setWinConnector(rs.getString("win_connector"));
                rmsbookingVisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                rmsbookingVisualInspection.setWinConnectorRejectUpload(rs.getString("win_connector_reject_upload"));
                rmsbookingVisualInspection.setWinConnectorRejectQty(rs.getString("win_connector_reject_qty"));
                rmsbookingVisualInspection.setRemarks(rs.getString("remarks"));
                rmsbookingVisualInspection.setFinalStatus(rs.getString("final_status"));
                rmsbookingVisualInspection.setCreatedBy(rs.getString("created_by"));
                rmsbookingVisualInspection.setCreatedDate(rs.getString("created_date"));
                rmsbookingVisualInspection.setFlag(rs.getString("flag"));

                rmsbookingVisualInspection.setTeflonConnector(rs.getString("teflon_connector"));
                rmsbookingVisualInspection.setTeflonConnectorHardwareId(rs.getString("teflon_connector_hardware_id"));
                rmsbookingVisualInspection.setTeflonConnectorReject(rs.getString("teflon_connector_reject"));
                rmsbookingVisualInspection.setTeflonConnectorRejectQty(rs.getString("teflon_connector_reject_qty"));
                rmsbookingVisualInspection.setTeflonConnectorRejectUpload(rs.getString("teflon_connector_reject_upload"));
                rmsbookingVisualInspection.setPogoReceptaclesPin(rs.getString("pogo_receptacles_pin"));
                rmsbookingVisualInspection.setPogoReceptaclesPinHardwareId(rs.getString("pogo_receptacles_pin_hardware_id"));
                rmsbookingVisualInspection.setPogoReceptaclesPinReject(rs.getString("pogo_receptacles_pin_reject"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectQty(rs.getString("pogo_receptacles_pin_reject_qty"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectUpload(rs.getString("pogo_receptacles_pin_reject_upload"));
                rmsbookingVisualInspection.setCableWiredCopperWire(rs.getString("cable_wired_copper_wire"));
                rmsbookingVisualInspection.setCableWiredCopperWireHardwareId(rs.getString("cable_wired_copper_wire_hardware_id"));
                rmsbookingVisualInspection.setCableWiredCopperWireReject(rs.getString("cable_wired_copper_wire_reject"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectQty(rs.getString("cable_wired_copper_wire_reject_qty"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectUpload(rs.getString("cable_wired_copper_wire_reject_upload"));
                rmsbookingVisualInspection.setLabelIdentification(rs.getString("label_identification"));
                rmsbookingVisualInspection.setLabelIdentificationHardwareId(rs.getString("label_identification_hardware_id"));
                rmsbookingVisualInspection.setLabelIdentificationReject(rs.getString("label_identification_reject"));
                rmsbookingVisualInspection.setLabelIdentificationRejectQty(rs.getString("label_identification_reject_qty"));
                rmsbookingVisualInspection.setLabelIdentificationRejectUpload(rs.getString("label_identification_reject_upload"));
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
        return rmsbookingVisualInspection;
    }

    public RmsBookingVisualInspection getRmsBookingVisualInspectionByGroupId(String groupId) {
        String sql = "SELECT * FROM rms_booking_visual_inspection WHERE group_id = '" + groupId + "' and module = 'Before Loading'";
        RmsBookingVisualInspection rmsbookingVisualInspection = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingVisualInspection = new RmsBookingVisualInspection();
                rmsbookingVisualInspection.setId(rs.getString("id"));
                rmsbookingVisualInspection.setGroupId(rs.getString("group_id"));
                rmsbookingVisualInspection.setModule(rs.getString("module"));
                rmsbookingVisualInspection.setPcb(rs.getString("pcb"));
                rmsbookingVisualInspection.setPcbReject(rs.getString("pcb_reject"));
                rmsbookingVisualInspection.setPcbRejectUpload(rs.getString("pcb_reject_upload"));
                rmsbookingVisualInspection.setPcbRejectQty(rs.getString("pcb_reject_qty"));
                rmsbookingVisualInspection.setHandle(rs.getString("handle"));
                rmsbookingVisualInspection.setHandleReject(rs.getString("handle_reject"));
                rmsbookingVisualInspection.setHandleRejectUpload(rs.getString("handle_reject_upload"));
                rmsbookingVisualInspection.setHandleRejectQty(rs.getString("handle_reject_qty"));
                rmsbookingVisualInspection.setMetalFrame(rs.getString("metal_frame"));
                rmsbookingVisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                rmsbookingVisualInspection.setMetalFrameRejectUpload(rs.getString("metal_frame_reject_upload"));
                rmsbookingVisualInspection.setMetalFrameRejectQty(rs.getString("metal_frame_reject_qty"));
                rmsbookingVisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                rmsbookingVisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                rmsbookingVisualInspection.setHardwareFasternersRejectUpload(rs.getString("hardware_fasterners_reject_upload"));
                rmsbookingVisualInspection.setHardwareFasternersRejectQty(rs.getString("hardware_fasterners_reject_qty"));
                rmsbookingVisualInspection.setClipHolder(rs.getString("clip_holder"));
                rmsbookingVisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                rmsbookingVisualInspection.setClipHolderRejectUpload(rs.getString("clip_holder_reject_upload"));
                rmsbookingVisualInspection.setClipHolderRejectQty(rs.getString("clip_holder_reject_qty"));
                rmsbookingVisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                rmsbookingVisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectUpload(rs.getString("pcb_edge_finger_reject_upload"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectQty(rs.getString("pcb_edge_finger_reject_qty"));
                rmsbookingVisualInspection.setConnector(rs.getString("connector"));
                rmsbookingVisualInspection.setConnectorReject(rs.getString("connector_reject"));
                rmsbookingVisualInspection.setConnectorRejectUpload(rs.getString("connector_reject_upload"));
                rmsbookingVisualInspection.setConnectorRejectQty(rs.getString("connector_reject_qty"));
                rmsbookingVisualInspection.setDutSockets(rs.getString("dut_sockets"));
                rmsbookingVisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                rmsbookingVisualInspection.setDutSocketsRejectUpload(rs.getString("dut_sockets_reject_upload"));
                rmsbookingVisualInspection.setDutSocketsRejectQty(rs.getString("dut_sockets_reject_qty"));
                rmsbookingVisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                rmsbookingVisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectUpload(rs.getString("edge_mb_banana_reject_upload"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectQty(rs.getString("edge_mb_banana_reject_qty"));
                rmsbookingVisualInspection.setElectComponent(rs.getString("elect_component"));
                rmsbookingVisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                rmsbookingVisualInspection.setElectComponentRejectUpload(rs.getString("elect_component_reject_upload"));
                rmsbookingVisualInspection.setElectComponentRejectQty(rs.getString("elect_component_reject_qty"));
                rmsbookingVisualInspection.setSolderJoint(rs.getString("solder_joint"));
                rmsbookingVisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                rmsbookingVisualInspection.setSolderJointRejectUpload(rs.getString("solder_joint_reject_upload"));
                rmsbookingVisualInspection.setSolderJointRejectQty(rs.getString("solder_joint_reject_qty"));
                rmsbookingVisualInspection.setWinConnector(rs.getString("win_connector"));
                rmsbookingVisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                rmsbookingVisualInspection.setWinConnectorRejectUpload(rs.getString("win_connector_reject_upload"));
                rmsbookingVisualInspection.setWinConnectorRejectQty(rs.getString("win_connector_reject_qty"));
                rmsbookingVisualInspection.setRemarks(rs.getString("remarks"));
                rmsbookingVisualInspection.setFinalStatus(rs.getString("final_status"));
                rmsbookingVisualInspection.setCreatedBy(rs.getString("created_by"));
                rmsbookingVisualInspection.setCreatedDate(rs.getString("created_date"));
                rmsbookingVisualInspection.setFlag(rs.getString("flag"));
                rmsbookingVisualInspection.setPcbHardwareId(rs.getString("pcb_hardware_id"));
                rmsbookingVisualInspection.setHandleHardwareId(rs.getString("handle_hardware_id"));
                rmsbookingVisualInspection.setMetalFrameHardwareId(rs.getString("metal_frame_hardware_id"));
                rmsbookingVisualInspection.setHardwareFasternersHardwareId(rs.getString("hardware_fasterners_hardware_id"));
                rmsbookingVisualInspection.setClipHolderHardwareId(rs.getString("clip_holder_hardware_id"));
                rmsbookingVisualInspection.setPcbEdgeFingerHardwareId(rs.getString("pcb_edge_finger_hardware_id"));
                rmsbookingVisualInspection.setConnectorHardwareId(rs.getString("connector_hardware_id"));
                rmsbookingVisualInspection.setDutSocketsHardwareId(rs.getString("dut_sockets_hardware_id"));
                rmsbookingVisualInspection.setEdgeMbBananaHardwareId(rs.getString("edge_mb_banana_hardware_id"));
                rmsbookingVisualInspection.setElectComponentHardwareId(rs.getString("elect_component_hardware_id"));
                rmsbookingVisualInspection.setSolderJointHardwareId(rs.getString("solder_joint_hardware_id"));
                rmsbookingVisualInspection.setWinConnectorHardwareId(rs.getString("win_connector_hardware_id"));
                rmsbookingVisualInspection.setTeflonConnector(rs.getString("teflon_connector"));
                rmsbookingVisualInspection.setTeflonConnectorHardwareId(rs.getString("teflon_connector_hardware_id"));
                rmsbookingVisualInspection.setTeflonConnectorReject(rs.getString("teflon_connector_reject"));
                rmsbookingVisualInspection.setTeflonConnectorRejectQty(rs.getString("teflon_connector_reject_qty"));
                rmsbookingVisualInspection.setTeflonConnectorRejectUpload(rs.getString("teflon_connector_reject_upload"));
                rmsbookingVisualInspection.setPogoReceptaclesPin(rs.getString("pogo_receptacles_pin"));
                rmsbookingVisualInspection.setPogoReceptaclesPinHardwareId(rs.getString("pogo_receptacles_pin_hardware_id"));
                rmsbookingVisualInspection.setPogoReceptaclesPinReject(rs.getString("pogo_receptacles_pin_reject"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectQty(rs.getString("pogo_receptacles_pin_reject_qty"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectUpload(rs.getString("pogo_receptacles_pin_reject_upload"));
                rmsbookingVisualInspection.setCableWiredCopperWire(rs.getString("cable_wired_copper_wire"));
                rmsbookingVisualInspection.setCableWiredCopperWireHardwareId(rs.getString("cable_wired_copper_wire_hardware_id"));
                rmsbookingVisualInspection.setCableWiredCopperWireReject(rs.getString("cable_wired_copper_wire_reject"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectQty(rs.getString("cable_wired_copper_wire_reject_qty"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectUpload(rs.getString("cable_wired_copper_wire_reject_upload"));
                rmsbookingVisualInspection.setLabelIdentification(rs.getString("label_identification"));
                rmsbookingVisualInspection.setLabelIdentificationHardwareId(rs.getString("label_identification_hardware_id"));
                rmsbookingVisualInspection.setLabelIdentificationReject(rs.getString("label_identification_reject"));
                rmsbookingVisualInspection.setLabelIdentificationRejectQty(rs.getString("label_identification_reject_qty"));
                rmsbookingVisualInspection.setLabelIdentificationRejectUpload(rs.getString("label_identification_reject_upload"));

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
        return rmsbookingVisualInspection;
    }

    public RmsBookingVisualInspection getRmsBookingVisualInspectionByGroupIdAndStatus(String groupId, String status) {
        String sql = "SELECT * FROM rms_booking_visual_inspection WHERE group_id = '" + groupId + "' and module = '" + status + "'";
        RmsBookingVisualInspection rmsbookingVisualInspection = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingVisualInspection = new RmsBookingVisualInspection();
                rmsbookingVisualInspection.setId(rs.getString("id"));
                rmsbookingVisualInspection.setGroupId(rs.getString("group_id"));
                rmsbookingVisualInspection.setModule(rs.getString("module"));
                rmsbookingVisualInspection.setPcb(rs.getString("pcb"));
                rmsbookingVisualInspection.setPcbReject(rs.getString("pcb_reject"));
                rmsbookingVisualInspection.setPcbRejectUpload(rs.getString("pcb_reject_upload"));
                rmsbookingVisualInspection.setPcbRejectQty(rs.getString("pcb_reject_qty"));
                rmsbookingVisualInspection.setHandle(rs.getString("handle"));
                rmsbookingVisualInspection.setHandleReject(rs.getString("handle_reject"));
                rmsbookingVisualInspection.setHandleRejectUpload(rs.getString("handle_reject_upload"));
                rmsbookingVisualInspection.setHandleRejectQty(rs.getString("handle_reject_qty"));
                rmsbookingVisualInspection.setMetalFrame(rs.getString("metal_frame"));
                rmsbookingVisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                rmsbookingVisualInspection.setMetalFrameRejectUpload(rs.getString("metal_frame_reject_upload"));
                rmsbookingVisualInspection.setMetalFrameRejectQty(rs.getString("metal_frame_reject_qty"));
                rmsbookingVisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                rmsbookingVisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                rmsbookingVisualInspection.setHardwareFasternersRejectUpload(rs.getString("hardware_fasterners_reject_upload"));
                rmsbookingVisualInspection.setHardwareFasternersRejectQty(rs.getString("hardware_fasterners_reject_qty"));
                rmsbookingVisualInspection.setClipHolder(rs.getString("clip_holder"));
                rmsbookingVisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                rmsbookingVisualInspection.setClipHolderRejectUpload(rs.getString("clip_holder_reject_upload"));
                rmsbookingVisualInspection.setClipHolderRejectQty(rs.getString("clip_holder_reject_qty"));
                rmsbookingVisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                rmsbookingVisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectUpload(rs.getString("pcb_edge_finger_reject_upload"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectQty(rs.getString("pcb_edge_finger_reject_qty"));
                rmsbookingVisualInspection.setConnector(rs.getString("connector"));
                rmsbookingVisualInspection.setConnectorReject(rs.getString("connector_reject"));
                rmsbookingVisualInspection.setConnectorRejectUpload(rs.getString("connector_reject_upload"));
                rmsbookingVisualInspection.setConnectorRejectQty(rs.getString("connector_reject_qty"));
                rmsbookingVisualInspection.setDutSockets(rs.getString("dut_sockets"));
                rmsbookingVisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                rmsbookingVisualInspection.setDutSocketsRejectUpload(rs.getString("dut_sockets_reject_upload"));
                rmsbookingVisualInspection.setDutSocketsRejectQty(rs.getString("dut_sockets_reject_qty"));
                rmsbookingVisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                rmsbookingVisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectUpload(rs.getString("edge_mb_banana_reject_upload"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectQty(rs.getString("edge_mb_banana_reject_qty"));
                rmsbookingVisualInspection.setElectComponent(rs.getString("elect_component"));
                rmsbookingVisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                rmsbookingVisualInspection.setElectComponentRejectUpload(rs.getString("elect_component_reject_upload"));
                rmsbookingVisualInspection.setElectComponentRejectQty(rs.getString("elect_component_reject_qty"));
                rmsbookingVisualInspection.setSolderJoint(rs.getString("solder_joint"));
                rmsbookingVisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                rmsbookingVisualInspection.setSolderJointRejectUpload(rs.getString("solder_joint_reject_upload"));
                rmsbookingVisualInspection.setSolderJointRejectQty(rs.getString("solder_joint_reject_qty"));
                rmsbookingVisualInspection.setWinConnector(rs.getString("win_connector"));
                rmsbookingVisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                rmsbookingVisualInspection.setWinConnectorRejectUpload(rs.getString("win_connector_reject_upload"));
                rmsbookingVisualInspection.setWinConnectorRejectQty(rs.getString("win_connector_reject_qty"));
                rmsbookingVisualInspection.setRemarks(rs.getString("remarks"));
                rmsbookingVisualInspection.setFinalStatus(rs.getString("final_status"));
                rmsbookingVisualInspection.setCreatedBy(rs.getString("created_by"));
                rmsbookingVisualInspection.setCreatedDate(rs.getString("created_date"));
                rmsbookingVisualInspection.setFlag(rs.getString("flag"));
                rmsbookingVisualInspection.setPcbHardwareId(rs.getString("pcb_hardware_id"));
                rmsbookingVisualInspection.setHandleHardwareId(rs.getString("handle_hardware_id"));
                rmsbookingVisualInspection.setMetalFrameHardwareId(rs.getString("metal_frame_hardware_id"));
                rmsbookingVisualInspection.setHardwareFasternersHardwareId(rs.getString("hardware_fasterners_hardware_id"));
                rmsbookingVisualInspection.setClipHolderHardwareId(rs.getString("clip_holder_hardware_id"));
                rmsbookingVisualInspection.setPcbEdgeFingerHardwareId(rs.getString("pcb_edge_finger_hardware_id"));
                rmsbookingVisualInspection.setConnectorHardwareId(rs.getString("connector_hardware_id"));
                rmsbookingVisualInspection.setDutSocketsHardwareId(rs.getString("dut_sockets_hardware_id"));
                rmsbookingVisualInspection.setEdgeMbBananaHardwareId(rs.getString("edge_mb_banana_hardware_id"));
                rmsbookingVisualInspection.setElectComponentHardwareId(rs.getString("elect_component_hardware_id"));
                rmsbookingVisualInspection.setSolderJointHardwareId(rs.getString("solder_joint_hardware_id"));
                rmsbookingVisualInspection.setWinConnectorHardwareId(rs.getString("win_connector_hardware_id"));
                rmsbookingVisualInspection.setTeflonConnector(rs.getString("teflon_connector"));
                rmsbookingVisualInspection.setTeflonConnectorHardwareId(rs.getString("teflon_connector_hardware_id"));
                rmsbookingVisualInspection.setTeflonConnectorReject(rs.getString("teflon_connector_reject"));
                rmsbookingVisualInspection.setTeflonConnectorRejectQty(rs.getString("teflon_connector_reject_qty"));
                rmsbookingVisualInspection.setTeflonConnectorRejectUpload(rs.getString("teflon_connector_reject_upload"));
                rmsbookingVisualInspection.setPogoReceptaclesPin(rs.getString("pogo_receptacles_pin"));
                rmsbookingVisualInspection.setPogoReceptaclesPinHardwareId(rs.getString("pogo_receptacles_pin_hardware_id"));
                rmsbookingVisualInspection.setPogoReceptaclesPinReject(rs.getString("pogo_receptacles_pin_reject"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectQty(rs.getString("pogo_receptacles_pin_reject_qty"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectUpload(rs.getString("pogo_receptacles_pin_reject_upload"));
                rmsbookingVisualInspection.setCableWiredCopperWire(rs.getString("cable_wired_copper_wire"));
                rmsbookingVisualInspection.setCableWiredCopperWireHardwareId(rs.getString("cable_wired_copper_wire_hardware_id"));
                rmsbookingVisualInspection.setCableWiredCopperWireReject(rs.getString("cable_wired_copper_wire_reject"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectQty(rs.getString("cable_wired_copper_wire_reject_qty"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectUpload(rs.getString("cable_wired_copper_wire_reject_upload"));
                rmsbookingVisualInspection.setLabelIdentification(rs.getString("label_identification"));
                rmsbookingVisualInspection.setLabelIdentificationHardwareId(rs.getString("label_identification_hardware_id"));
                rmsbookingVisualInspection.setLabelIdentificationReject(rs.getString("label_identification_reject"));
                rmsbookingVisualInspection.setLabelIdentificationRejectQty(rs.getString("label_identification_reject_qty"));
                rmsbookingVisualInspection.setLabelIdentificationRejectUpload(rs.getString("label_identification_reject_upload"));

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
        return rmsbookingVisualInspection;
    }

    public List<RmsBookingVisualInspection> getRmsBookingVisualInspectionList() {
        String sql = "SELECT * FROM rms_booking_visual_inspection ORDER BY id ASC";
        List<RmsBookingVisualInspection> rmsbookingVisualInspectionList = new ArrayList<RmsBookingVisualInspection>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            RmsBookingVisualInspection rmsbookingVisualInspection;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rmsbookingVisualInspection = new RmsBookingVisualInspection();
                rmsbookingVisualInspection.setId(rs.getString("id"));
                rmsbookingVisualInspection.setGroupId(rs.getString("group_id"));
                rmsbookingVisualInspection.setModule(rs.getString("module"));
                rmsbookingVisualInspection.setPcb(rs.getString("pcb"));
                rmsbookingVisualInspection.setPcbReject(rs.getString("pcb_reject"));
                rmsbookingVisualInspection.setPcbRejectUpload(rs.getString("pcb_reject_upload"));
                rmsbookingVisualInspection.setPcbRejectQty(rs.getString("pcb_reject_qty"));
                rmsbookingVisualInspection.setHandle(rs.getString("handle"));
                rmsbookingVisualInspection.setHandleReject(rs.getString("handle_reject"));
                rmsbookingVisualInspection.setHandleRejectUpload(rs.getString("handle_reject_upload"));
                rmsbookingVisualInspection.setHandleRejectQty(rs.getString("handle_reject_qty"));
                rmsbookingVisualInspection.setMetalFrame(rs.getString("metal_frame"));
                rmsbookingVisualInspection.setMetalFrameReject(rs.getString("metal_frame_reject"));
                rmsbookingVisualInspection.setMetalFrameRejectUpload(rs.getString("metal_frame_reject_upload"));
                rmsbookingVisualInspection.setMetalFrameRejectQty(rs.getString("metal_frame_reject_qty"));
                rmsbookingVisualInspection.setHardwareFasterners(rs.getString("hardware_fasterners"));
                rmsbookingVisualInspection.setHardwareFasternersReject(rs.getString("hardware_fasterners_reject"));
                rmsbookingVisualInspection.setHardwareFasternersRejectUpload(rs.getString("hardware_fasterners_reject_upload"));
                rmsbookingVisualInspection.setHardwareFasternersRejectQty(rs.getString("hardware_fasterners_reject_qty"));
                rmsbookingVisualInspection.setClipHolder(rs.getString("clip_holder"));
                rmsbookingVisualInspection.setClipHolderReject(rs.getString("clip_holder_reject"));
                rmsbookingVisualInspection.setClipHolderRejectUpload(rs.getString("clip_holder_reject_upload"));
                rmsbookingVisualInspection.setClipHolderRejectQty(rs.getString("clip_holder_reject_qty"));
                rmsbookingVisualInspection.setPcbEdgeFinger(rs.getString("pcb_edge_finger"));
                rmsbookingVisualInspection.setPcbEdgeFingerReject(rs.getString("pcb_edge_finger_reject"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectUpload(rs.getString("pcb_edge_finger_reject_upload"));
                rmsbookingVisualInspection.setPcbEdgeFingerRejectQty(rs.getString("pcb_edge_finger_reject_qty"));
                rmsbookingVisualInspection.setConnector(rs.getString("connector"));
                rmsbookingVisualInspection.setConnectorReject(rs.getString("connector_reject"));
                rmsbookingVisualInspection.setConnectorRejectUpload(rs.getString("connector_reject_upload"));
                rmsbookingVisualInspection.setConnectorRejectQty(rs.getString("connector_reject_qty"));
                rmsbookingVisualInspection.setDutSockets(rs.getString("dut_sockets"));
                rmsbookingVisualInspection.setDutSocketsReject(rs.getString("dut_sockets_reject"));
                rmsbookingVisualInspection.setDutSocketsRejectUpload(rs.getString("dut_sockets_reject_upload"));
                rmsbookingVisualInspection.setDutSocketsRejectQty(rs.getString("dut_sockets_reject_qty"));
                rmsbookingVisualInspection.setEdgeMbBanana(rs.getString("edge_mb_banana"));
                rmsbookingVisualInspection.setEdgeMbBananaReject(rs.getString("edge_mb_banana_reject"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectUpload(rs.getString("edge_mb_banana_reject_upload"));
                rmsbookingVisualInspection.setEdgeMbBananaRejectQty(rs.getString("edge_mb_banana_reject_qty"));
                rmsbookingVisualInspection.setElectComponent(rs.getString("elect_component"));
                rmsbookingVisualInspection.setElectComponentReject(rs.getString("elect_component_reject"));
                rmsbookingVisualInspection.setElectComponentRejectUpload(rs.getString("elect_component_reject_upload"));
                rmsbookingVisualInspection.setElectComponentRejectQty(rs.getString("elect_component_reject_qty"));
                rmsbookingVisualInspection.setSolderJoint(rs.getString("solder_joint"));
                rmsbookingVisualInspection.setSolderJointReject(rs.getString("solder_joint_reject"));
                rmsbookingVisualInspection.setSolderJointRejectUpload(rs.getString("solder_joint_reject_upload"));
                rmsbookingVisualInspection.setSolderJointRejectQty(rs.getString("solder_joint_reject_qty"));
                rmsbookingVisualInspection.setWinConnector(rs.getString("win_connector"));
                rmsbookingVisualInspection.setWinConnectorReject(rs.getString("win_connector_reject"));
                rmsbookingVisualInspection.setWinConnectorRejectUpload(rs.getString("win_connector_reject_upload"));
                rmsbookingVisualInspection.setWinConnectorRejectQty(rs.getString("win_connector_reject_qty"));
                rmsbookingVisualInspection.setRemarks(rs.getString("remarks"));
                rmsbookingVisualInspection.setFinalStatus(rs.getString("final_status"));
                rmsbookingVisualInspection.setCreatedBy(rs.getString("created_by"));
                rmsbookingVisualInspection.setCreatedDate(rs.getString("created_date"));
                rmsbookingVisualInspection.setFlag(rs.getString("flag"));
                rmsbookingVisualInspection.setTeflonConnector(rs.getString("teflon_connector"));
                rmsbookingVisualInspection.setTeflonConnectorHardwareId(rs.getString("teflon_connector_hardware_id"));
                rmsbookingVisualInspection.setTeflonConnectorReject(rs.getString("teflon_connector_reject"));
                rmsbookingVisualInspection.setTeflonConnectorRejectQty(rs.getString("teflon_connector_reject_qty"));
                rmsbookingVisualInspection.setTeflonConnectorRejectUpload(rs.getString("teflon_connector_reject_upload"));
                rmsbookingVisualInspection.setPogoReceptaclesPin(rs.getString("pogo_receptacles_pin"));
                rmsbookingVisualInspection.setPogoReceptaclesPinHardwareId(rs.getString("pogo_receptacles_pin_hardware_id"));
                rmsbookingVisualInspection.setPogoReceptaclesPinReject(rs.getString("pogo_receptacles_pin_reject"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectQty(rs.getString("pogo_receptacles_pin_reject_qty"));
                rmsbookingVisualInspection.setPogoReceptaclesPinRejectUpload(rs.getString("pogo_receptacles_pin_reject_upload"));
                rmsbookingVisualInspection.setCableWiredCopperWire(rs.getString("cable_wired_copper_wire"));
                rmsbookingVisualInspection.setCableWiredCopperWireHardwareId(rs.getString("cable_wired_copper_wire_hardware_id"));
                rmsbookingVisualInspection.setCableWiredCopperWireReject(rs.getString("cable_wired_copper_wire_reject"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectQty(rs.getString("cable_wired_copper_wire_reject_qty"));
                rmsbookingVisualInspection.setCableWiredCopperWireRejectUpload(rs.getString("cable_wired_copper_wire_reject_upload"));
                rmsbookingVisualInspection.setLabelIdentification(rs.getString("label_identification"));
                rmsbookingVisualInspection.setLabelIdentificationHardwareId(rs.getString("label_identification_hardware_id"));
                rmsbookingVisualInspection.setLabelIdentificationReject(rs.getString("label_identification_reject"));
                rmsbookingVisualInspection.setLabelIdentificationRejectQty(rs.getString("label_identification_reject_qty"));
                rmsbookingVisualInspection.setLabelIdentificationRejectUpload(rs.getString("label_identification_reject_upload"));
                rmsbookingVisualInspectionList.add(rmsbookingVisualInspection);
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
        return rmsbookingVisualInspectionList;
    }

    public Integer getCountByGroupIdWithModuleBeforeLoading(String groupId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_visual_inspection inc WHERE inc.group_id = '" + groupId + "' AND inc.module = 'Before Loading'"
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

    public Integer getCountByGroupIdWithModuleAfterLoading(String groupId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_visual_inspection inc WHERE inc.group_id = '" + groupId + "' AND inc.module = 'After Loading'"
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

    public Integer getCountByGroupIdAndStatus(String groupId, String status) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM rms_booking_visual_inspection inc WHERE inc.group_id = '" + groupId + "' AND inc.module = '" + status + "'"
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
