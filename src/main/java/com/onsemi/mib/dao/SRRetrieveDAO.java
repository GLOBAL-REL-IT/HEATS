package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.SRKpi;
import com.onsemi.mib.model.SRRetrieve;
import com.onsemi.mib.tools.QueryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRRetrieveDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRRetrieveDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public SRRetrieveDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRetrieve(SRRetrieve sampleRetrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_retrieve (req_id, box_id, req_type, requestor_name, req_date, req_details, req_remarks, event, mth_to_scrap, pkg_family, modified_date, modified_by, created_date, created_by, status, flag ) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, sampleRetrieve.getReqId());
            ps.setString(2, sampleRetrieve.getBoxId());
            ps.setString(3, sampleRetrieve.getReqType());
            ps.setString(4, sampleRetrieve.getReqName());
            ps.setString(5, sampleRetrieve.getReqDate());
            ps.setString(6, sampleRetrieve.getReqDetails());
            ps.setString(7, sampleRetrieve.getReqRemarks());
            ps.setString(8, sampleRetrieve.getEvent());
            ps.setString(9, sampleRetrieve.getMthToScrap());
            ps.setString(10, sampleRetrieve.getPkgFamily());
            ps.setString(11, sampleRetrieve.getModifiedBy());
            ps.setString(12, sampleRetrieve.getCreatedBy());
            ps.setString(13, sampleRetrieve.getStatus());
            ps.setString(14, sampleRetrieve.getFlag());
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

    public SRRetrieve getRetrievePerReqId(String reqId) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                + "DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view, "
                + "DATE_FORMAT(verification_date,'%d/%m/%y %h:%i %p') AS verification_date_view, DATE_FORMAT(rl_received_date,'%d/%m/%y %h:%i %p') AS rl_received_date_view, "
                + "DATE_FORMAT(ship_date,'%d/%m/%y %h:%i %p') AS ship_date_view, DATE_FORMAT(req_date,'%d-%b-%y') AS req_date_view "
                + "FROM sr_retrieve "
                + "WHERE req_id = '" + reqId + "' "
                + "ORDER BY created_date DESC ";
        SRRetrieve sampleRetrieve = new SRRetrieve();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleRetrieve.setId(rs.getString("id"));
                sampleRetrieve.setReqId(rs.getString("req_id"));
                sampleRetrieve.setBoxId(rs.getString("box_id"));
                sampleRetrieve.setReqType(rs.getString("req_type"));
                sampleRetrieve.setReqName(rs.getString("requestor_name"));
                sampleRetrieve.setReqDate(rs.getString("req_date_view"));
                sampleRetrieve.setReqDetails(rs.getString("req_details"));
                sampleRetrieve.setReqRemarks(rs.getString("req_remarks"));
                sampleRetrieve.setEvent(rs.getString("event"));
                sampleRetrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleRetrieve.setPkgFamily(rs.getString("pkg_family"));
                sampleRetrieve.setVerificationDate(rs.getString("verification_date_view"));
                sampleRetrieve.setVerificationBy(rs.getString("verification_by"));
                sampleRetrieve.setInvoiceNo(rs.getString("invoice_no"));
                sampleRetrieve.setShipBy(rs.getString("ship_by"));
                sampleRetrieve.setShipDate(rs.getString("ship_date_view"));
                sampleRetrieve.setRlReceivedBy(rs.getString("rl_received_by"));
                sampleRetrieve.setRlReceivedDate(rs.getString("rl_received_date_view"));
                sampleRetrieve.setModifiedBy(rs.getString("modified_by"));
                sampleRetrieve.setModifiedDate(rs.getString("modified_date_view"));
                sampleRetrieve.setCreatedBy(rs.getString("created_by"));
                sampleRetrieve.setCreatedDate(rs.getString("created_date_view"));
                sampleRetrieve.setStatus(rs.getString("status"));
                sampleRetrieve.setFlag(rs.getString("flag"));
                sampleRetrieve.setAging(rs.getString("aging"));
                sampleRetrieve.setShipStatus(rs.getString("ship_status"));
                sampleRetrieve.setShipRemark(rs.getString("ship_remark"));
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
        return sampleRetrieve;
    }

    public SRRetrieve getRetrievePerReqIdActual(String reqId) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging "
                + "FROM sr_retrieve "
                + "WHERE req_id = '" + reqId + "' "
                + "ORDER BY created_date DESC ";
        SRRetrieve sampleRetrieve = new SRRetrieve();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleRetrieve.setId(rs.getString("id"));
                sampleRetrieve.setReqId(rs.getString("req_id"));
                sampleRetrieve.setBoxId(rs.getString("box_id"));
                sampleRetrieve.setReqType(rs.getString("req_type"));
                sampleRetrieve.setReqDetails(rs.getString("req_details"));
                sampleRetrieve.setReqRemarks(rs.getString("req_remarks"));
                sampleRetrieve.setEvent(rs.getString("event"));
                sampleRetrieve.setMthToScrap(rs.getString("mth_to_scrap"));
                sampleRetrieve.setPkgFamily(rs.getString("pkg_family"));
                sampleRetrieve.setModifiedBy(rs.getString("modified_by"));
                sampleRetrieve.setModifiedDate(rs.getString("modified_date"));
                sampleRetrieve.setCreatedBy(rs.getString("created_by"));
                sampleRetrieve.setCreatedDate(rs.getString("created_date"));
                sampleRetrieve.setStatus(rs.getString("status"));
                sampleRetrieve.setFlag(rs.getString("flag"));
                sampleRetrieve.setAging(rs.getString("aging"));
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
        return sampleRetrieve;
    }

    public SRRetrieve getRetrievePerRetrieveId(String id) {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view "
                + "FROM sr_retrieve "
                + "WHERE id = '" + id + "' "
                + "ORDER BY created_date DESC ";
        SRRetrieve sampleRetrieve = new SRRetrieve();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampleRetrieve.setId(rs.getString("id"));
                sampleRetrieve.setReqId(rs.getString("req_id"));
                sampleRetrieve.setBoxId(rs.getString("box_id"));
                sampleRetrieve.setReqType(rs.getString("req_type"));
                sampleRetrieve.setReqDetails(rs.getString("req_details"));
                sampleRetrieve.setReqRemarks(rs.getString("req_remarks"));
                sampleRetrieve.setEvent(rs.getString("event"));
                sampleRetrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampleRetrieve.setPkgFamily(rs.getString("pkg_family"));
                sampleRetrieve.setModifiedBy(rs.getString("modified_by"));
                sampleRetrieve.setModifiedDate(rs.getString("modified_date_view"));
                sampleRetrieve.setCreatedBy(rs.getString("created_by"));
                sampleRetrieve.setCreatedDate(rs.getString("created_date_view"));
                sampleRetrieve.setStatus(rs.getString("status"));
                sampleRetrieve.setFlag(rs.getString("flag"));
                sampleRetrieve.setAging(rs.getString("aging"));
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
        return sampleRetrieve;
    }

    public List<SRRetrieve> getAllReqMergeInner() {
        String sql = "SELECT R.*, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                + "DATE_FORMAT(R.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot "
                + "FROM sr_retrieve R, sr_req_inner I "
                + "WHERE R.req_id = I.req_id AND R.flag NOT LIKE '9' "
                + "GROUP BY R.id "
                + "ORDER BY R.created_date ASC ";
        List<SRRetrieve> reqList = new ArrayList<SRRetrieve>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRRetrieve sampRetrieve;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampRetrieve = new SRRetrieve();
                sampRetrieve.setId(rs.getString("R.id"));
                sampRetrieve.setReqId(rs.getString("R.req_id"));
                sampRetrieve.setBoxId(rs.getString("R.box_id"));
                sampRetrieve.setReqType(rs.getString("R.req_type"));
                sampRetrieve.setReqDetails(rs.getString("R.req_details"));
                sampRetrieve.setReqRemarks(rs.getString("R.req_remarks"));
                sampRetrieve.setEvent(rs.getString("R.event"));
                sampRetrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampRetrieve.setAging(rs.getString("aging"));
                sampRetrieve.setPkgFamily(rs.getString("R.pkg_family"));
                sampRetrieve.setCreatedDate(rs.getString("created_date_view"));
                sampRetrieve.setCreatedBy(rs.getString("R.created_by"));
                sampRetrieve.setModifiedDate(rs.getString("modified_date_view"));
                sampRetrieve.setModifiedBy(rs.getString("R.modified_by"));
                sampRetrieve.setFlag(rs.getString("R.flag"));
                sampRetrieve.setStatus(rs.getString("R.status"));
                sampRetrieve.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampRetrieve.setLotQty(rs.getString("count_lot"));
                reqList.add(sampRetrieve);
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
        return reqList;
    }

    public List<SRRetrieve> getAllScrapMergeInner() {
        String sql = "SELECT R.*, GROUP_CONCAT(I.rmslot_event SEPARATOR ', ') AS rmslot_event_concat, DATEDIFF(R.mth_to_scrap, NOW()) AS aging, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                + "DATE_FORMAT(R.modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, COUNT(I.rmslot_event) AS count_lot "
                + "FROM sr_retrieve R, sr_req_inner I "
                + "WHERE R.req_id = I.req_id AND R.flag LIKE '9' AND R.req_details = 'Recall for Scrap' "
                + "GROUP BY R.id "
                + "ORDER BY R.created_date ASC ";
        List<SRRetrieve> reqList = new ArrayList<SRRetrieve>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRRetrieve sampRetrieve;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sampRetrieve = new SRRetrieve();
                sampRetrieve.setId(rs.getString("R.id"));
                sampRetrieve.setReqId(rs.getString("R.req_id"));
                sampRetrieve.setBoxId(rs.getString("R.box_id"));
                sampRetrieve.setReqType(rs.getString("R.req_type"));
                sampRetrieve.setReqDetails(rs.getString("R.req_details"));
                sampRetrieve.setReqRemarks(rs.getString("R.req_remarks"));
                sampRetrieve.setEvent(rs.getString("R.event"));
                sampRetrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampRetrieve.setAging(rs.getString("aging"));
                sampRetrieve.setPkgFamily(rs.getString("R.pkg_family"));
                sampRetrieve.setCreatedDate(rs.getString("created_date_view"));
                sampRetrieve.setCreatedBy(rs.getString("R.created_by"));
                sampRetrieve.setModifiedDate(rs.getString("modified_date_view"));
                sampRetrieve.setModifiedBy(rs.getString("R.modified_by"));
                sampRetrieve.setFlag(rs.getString("R.flag"));
                sampRetrieve.setStatus(rs.getString("R.status"));
                sampRetrieve.setRmsLotEventConcat(rs.getString("rmslot_event_concat"));
                sampRetrieve.setLotQty(rs.getString("count_lot"));
                reqList.add(sampRetrieve);
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
        return reqList;
    }

    public List<SRKpi> getAllScrapDataPerMthKPI() {
        String sql = "SELECT COUNT(*) AS count, DATE_FORMAT(created_date,'%b-%Y') AS mthyr_req, DATE_FORMAT(created_date,'%m') AS mth_req, "
                + "DATE_FORMAT(created_date,'%Y') AS yr_req "
                + "FROM sr_retrieve "
                + "WHERE req_details = 'Recall for Scrap' AND PERIOD_DIFF(DATE_FORMAT(NOW(),'%Y%m'),DATE_FORMAT(created_date,'%Y%m')) <= 12 "
                + "GROUP BY DATE_FORMAT(created_date,'%Y%m') "
                + "ORDER BY DATE_FORMAT(created_date,'%Y%m') DESC ";
        List<SRKpi> srKpiList = new ArrayList<SRKpi>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRKpi srKpi;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srKpi = new SRKpi();
                srKpi.setScrapCount(rs.getString("count"));
                srKpi.setScrapMthYrReq(rs.getString("mthyr_req"));
                srKpi.setScrapMthReq(rs.getString("mth_req"));
                srKpi.setScrapYrReq(rs.getString("yr_req"));
                srKpiList.add(srKpi);
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
        return srKpiList;
    }

    public Integer getCountId(String id) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(id) AS count FROM sr_retrieve "
                    + "WHERE id = '" + id + "' "
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

    public Integer getCountReqId(String reqId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(id) AS count FROM sr_retrieve "
                    + "WHERE req_id = '" + reqId + "' "
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

    public QueryResult deleteReq(String id) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_retrieve WHERE id = '" + id + "'"
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

    public QueryResult updateShipmentStatusPerReqId(SRRetrieve sampleRetrieve) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_retrieve SET "
                + "verification_date = ?, verification_by = ?, invoice_no = ?, ship_date = ?, ship_by = ?, flag = ?, status = ?, modified_date = NOW(), modified_by = ? "
                + "WHERE req_id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleRetrieve.getVerificationDate());
            ps.setString(2, sampleRetrieve.getVerificationBy());
            ps.setString(3, sampleRetrieve.getInvoiceNo());
            ps.setString(4, sampleRetrieve.getShipDate());
            ps.setString(5, sampleRetrieve.getShipBy());
            ps.setString(6, sampleRetrieve.getFlag());
            ps.setString(7, sampleRetrieve.getStatus());
            ps.setString(8, sampleRetrieve.getModifiedBy());
            ps.setString(9, sampleRetrieve.getReqId());
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

    public QueryResult updateReceivingStatus(SRRetrieve sampleRetrieve) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_retrieve SET "
                + "rl_received_date = NOW(), rl_received_by = ?, flag = ?, status = ?, modified_date = NOW(), modified_by = ? "
                + "WHERE req_id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleRetrieve.getRlReceivedBy());
            ps.setString(2, sampleRetrieve.getFlag());
            ps.setString(3, sampleRetrieve.getStatus());
            ps.setString(4, sampleRetrieve.getModifiedBy());
            ps.setString(5, sampleRetrieve.getReqId());
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

    public QueryResult updateShipmentStatus(SRRetrieve sampleRetrieve) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_retrieve SET "
                + "ship_status = ?, ship_remark = ? "
                + "WHERE req_id  = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sampleRetrieve.getShipStatus());
            ps.setString(2, sampleRetrieve.getShipRemark());
            ps.setString(3, sampleRetrieve.getReqId());
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

    public Integer getCountRecallForScrapPerMth(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_retrieve WHERE shelf_id LIKE '%" + month + "%' AND flag = '1' "
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
