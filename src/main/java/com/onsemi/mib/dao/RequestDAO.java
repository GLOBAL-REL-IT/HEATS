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
import com.onsemi.mib.model.Request;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RequestDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRequest(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_request (ftp_id, final_qty, request_by, request_date, flag, status, Stress_type_mid_point) VALUES (?,?,?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, request.getFtpId());
            ps.setString(2, request.getFinalQty());
            ps.setString(3, request.getRequestBy());
            ps.setString(4, request.getFlag());
            ps.setString(5, request.getStatus());
            ps.setString(6, request.getStressTypeMidPoint());
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

    public QueryResult updateRequest(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_request SET ftp_id = ?, inv_id = ?, box_id = ?, final_qty = ?, request_by = ?, request_date = ?, modified_date = ?, modified_by = ?, flag = ?, status = ?, Stress_type_mid_point = ? WHERE id = ?"
            );
            ps.setString(1, request.getFtpId());
            ps.setString(2, request.getInvId());
            ps.setString(3, request.getBoxId());
            ps.setString(4, request.getFinalQty());
            ps.setString(5, request.getRequestBy());
            ps.setString(6, request.getRequestDate());
            ps.setString(7, request.getModifiedDate());
            ps.setString(8, request.getModifiedBy());
            ps.setString(9, request.getFlag());
            ps.setString(10, request.getStatus());
            ps.setString(11, request.getStressTypeMidPoint());
            ps.setString(12, request.getId());
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

    public QueryResult updateRequestStatusAndFlag(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_request SET modified_date = NOW(), modified_by = ?, flag = ?, status = ? WHERE id = ?"
            );
            ps.setString(1, request.getModifiedBy());
            ps.setString(2, request.getFlag());
            ps.setString(3, request.getStatus());
            ps.setString(4, request.getId());
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

    public QueryResult updateRequestForInvId(Request request) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_request SET inv_id = ? WHERE id = ?"
            );
            ps.setString(1, request.getInvId());
            ps.setString(2, request.getId());
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

    public QueryResult deleteRequest(String requestId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_request WHERE id = ? "
            );
            ps.setString(1, requestId);
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

    public Request getRequest(String requestId) {
        String sql = "SELECT * FROM sr_request WHERE id = ? ";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setFtpId(rs.getString("ftp_id"));
                request.setInvId(rs.getString("inv_id"));
                request.setBoxId(rs.getString("box_id"));
                request.setFinalQty(rs.getString("final_qty"));
                request.setRequestBy(rs.getString("request_by"));
                request.setRequestDate(rs.getString("request_date"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setFlag(rs.getString("flag"));
                request.setStatus(rs.getString("status"));
                request.setStressTypeMidPoint(rs.getString("Stress_type_mid_point"));
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
        return request;
    }

    public Request getRequestWithFtpAndInventory(String requestId) {
        String sql = "SELECT re.*, "
                + "ftp.rms_id, ftp.rms_event, ftp.lot_type, ftp.rmslot_event, ftp.lot_qty, ftp.p_status, "
                + "ftp.pkg_family, ftp.pkg_name, ftp.actual_qty, "
                + "DATE_FORMAT(ftp.scrap_date,'%d %M %Y') AS scrap_date_view, "
                + "DATE_FORMAT(ftp.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "DATE_FORMAT(ftp.completed_date,'%d %M %Y') AS completed_date_view, "
                + "DATE_FORMAT(inv.inventory_date,'%d %M %Y') AS inventory_date_view, "
                + "inv.inventory_rack, inv.inventory_shelf "
                + "FROM sr_request re "
                + "INNER JOIN sr_ftp_data ftp ON re.ftp_id = ftp.id "
                + "LEFT JOIN sr_inventory inv ON re.inv_id = inv.id "
                + "WHERE re.id = ? ";
        Request request = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("re.id"));
                request.setFtpId(rs.getString("re.ftp_id"));
                request.setInvId(rs.getString("re.inv_id"));
                request.setFinalQty(rs.getString("re.final_qty"));
                request.setRequestBy(rs.getString("re.request_by"));
                request.setRequestDate(rs.getString("re.request_date"));
                request.setModifiedDate(rs.getString("re.modified_date"));
                request.setModifiedBy(rs.getString("re.modified_by"));
                request.setFlag(rs.getString("re.flag"));
                request.setStatus(rs.getString("re.status"));
                request.setRmsId(rs.getString("ftp.rms_id"));
                request.setRmsEvent(rs.getString("ftp.rms_event"));
                request.setLotType(rs.getString("ftp.lot_type"));
                request.setRmsLotEvent(rs.getString("ftp.rmslot_event"));
                request.setpStatus(rs.getString("ftp.p_status"));
                request.setPkgFamily(rs.getString("ftp.pkg_family"));
                request.setPkgName(rs.getString("ftp.pkg_name"));
                request.setScrapDate(rs.getString("scrap_date_view"));
                request.setMthToScrap(rs.getString("mth_to_scrap_view"));
                request.setCompletedDate(rs.getString("completed_date_view"));
                request.setShelf(rs.getString("inv.inventory_shelf"));
                request.setInvDate(rs.getString("inventory_date_view"));
                request.setLotQty(rs.getString("ftp.actual_qty"));
                request.setStressTypeMidPoint(rs.getString("Stress_type_mid_point"));
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
        return request;
    }

    public List<Request> getRequestList() {
        String sql = "SELECT * FROM sr_request ORDER BY id ASC";
        List<Request> requestList = new ArrayList<Request>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Request request;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setFtpId(rs.getString("ftp_id"));
                request.setInvId(rs.getString("inv_id"));
                request.setBoxId(rs.getString("box_id"));
                request.setFinalQty(rs.getString("final_qty"));
                request.setRequestBy(rs.getString("request_by"));
                request.setRequestDate(rs.getString("request_date"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setFlag(rs.getString("flag"));
                request.setStatus(rs.getString("status"));
                request.setStressTypeMidPoint(rs.getString("Stress_type_mid_point"));
                requestList.add(request);
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
        return requestList;
    }

    public List<Request> getRequestListJoinWithFtpAndInventory() {
        String sql = "SELECT re.*, "
                + "ftp.rms_id, ftp.rms_event, ftp.lot_type, ftp.rmslot_event, ftp.lot_qty, ftp.p_status, "
                + "ftp.pkg_family, ftp.pkg_name, ftp.scrap_date, ftp.mth_to_scrap, ftp.completed_date, "
                + "inv.inventory_rack, inv.inventory_shelf "
                + "FROM sr_request re "
                + "INNER JOIN sr_ftp_data ftp ON re.ftp_id = ftp.id "
                + "LEFT JOIN sr_inventory inv ON re.inv_id = inv.id "
                + "ORDER BY id DESC";
        List<Request> requestList = new ArrayList<Request>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Request request;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                request = new Request();
                request.setId(rs.getString("id"));
                request.setFtpId(rs.getString("ftp_id"));
                request.setInvId(rs.getString("inv_id"));
                request.setBoxId(rs.getString("box_id"));
                request.setFinalQty(rs.getString("final_qty"));
                request.setRequestBy(rs.getString("request_by"));
                request.setRequestDate(rs.getString("request_date"));
                request.setModifiedDate(rs.getString("modified_date"));
                request.setModifiedBy(rs.getString("modified_by"));
                request.setFlag(rs.getString("flag"));
                request.setStatus(rs.getString("status"));
                request.setRmsId(rs.getString("rms_id"));
                request.setRmsEvent(rs.getString("rms_event"));
                request.setLotType(rs.getString("lot_type"));
                request.setRmsLotEvent(rs.getString("rmslot_event"));
                request.setpStatus(rs.getString("p_status"));
                request.setPkgFamily(rs.getString("pkg_family"));
                request.setPkgName(rs.getString("pkg_name"));
                request.setScrapDate(rs.getString("scrap_date"));
                request.setMthToScrap(rs.getString("mth_to_scrap"));
                request.setCompletedDate(rs.getString("completed_date"));
                request.setRack(rs.getString("inventory_rack"));
                request.setShelf(rs.getString("inventory_shelf"));
                request.setStressTypeMidPoint(rs.getString("Stress_type_mid_point"));
                requestList.add(request);
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
        return requestList;
    }

    public Integer getCountFTPIdWithFlagZero(String ftpId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_request WHERE ftp_id = ? AND flag = '0'"
            );
            ps.setString(1, ftpId);
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