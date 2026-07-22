package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import com.onsemi.mib.model.SRArchive;
import com.onsemi.mib.tools.QueryResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRArchiveDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRArchiveDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public SRArchiveDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertArchive(SRArchive srArchive) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_archive (ftp_id, request_type, reasons_exclude, requestor_name, rel_req_name, rel_date_request, remarks, status, flag, "
                    + "modified_date, modified_by, created_date, created_by) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, srArchive.getFtpId());
            ps.setString(2, srArchive.getReqType());
            ps.setString(3, srArchive.getReasonsExc());
            ps.setString(4, srArchive.getReqName());
            ps.setString(5, srArchive.getRelReqName());
            ps.setString(6, srArchive.getRelDateReq());
            ps.setString(7, srArchive.getRemarks());
            ps.setString(8, srArchive.getStatus());
            ps.setString(9, srArchive.getFlag());
            ps.setString(10, srArchive.getModifiedBy());
            ps.setString(11, srArchive.getCreatedBy());
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

    public Integer getCountExistingGroupId(Integer groupId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_archive WHERE ftp_group_id = ? AND flag = 0 "
            );
            ps.setInt(1, groupId);
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

    public Integer getCountExistingGroupIdWithoutFlag(Integer groupId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_archive WHERE ftp_group_id = ? "
            );
            ps.setInt(1, groupId);
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

    public SRArchive getDataById(String id) {
        String sql = "SELECT * FROM sr_archive WHERE id = ? ";
        SRArchive ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new SRArchive();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setFtpId(rs.getString("ftp_id"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setFlag(rs.getString("flag"));
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
        return ftpdata;
    }

    public List<SRArchive> getAllDataView() {
        String sql = "SELECT *, DATEDIFF(F.mth_to_scrap, NOW()) AS aging, DATE_FORMAT(p_status_date,'%d %M %Y') AS p_status_date_view, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, "
                    + "DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view, DATE_FORMAT(A.rel_date_request,'%d %M %Y') AS rel_date_request_view,"
                    + "IFNULL(DATE_FORMAT(A.modified_date,'%d %M %Y %h:%i %p'), DATE_FORMAT(F.modified_date,'%d %M %Y %h:%i %p')) AS modified_date_view, "
                    + "IFNULL(DATE_FORMAT(A.created_date,'%d %M %Y %h:%i %p'), DATE_FORMAT(F.created_date,'%d %M %Y %h:%i %p')) AS created_date_view, "
                    + "GROUP_CONCAT(lot_type ORDER BY lot_type ASC SEPARATOR ', ') AS lot_concat, IFNULL(A.status, F.status) AS status_view "
                    + "FROM sr_ftp_data F, sr_archive A "
                    + "WHERE F.group_id = A.ftp_group_id AND A.flag = 0 AND DATEDIFF(F.mth_to_scrap, NOW()) > 0 and F.flag = 1 "
                    + "GROUP BY F.group_id "
                    + "ORDER BY A.created_date DESC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                //ftp
                srArchive.setFtpId(rs.getString("F.id"));
                srArchive.setGroupId(rs.getString("F.group_id"));
                srArchive.setRmsId(rs.getString("F.rms_id"));
                srArchive.setRmsEvent(rs.getString("F.rms_event"));
                srArchive.setLotType(rs.getString("F.lot_type"));
                srArchive.setRmsLotEvent(rs.getString("F.rmslot_event"));
                srArchive.setLotQty(rs.getString("F.lot_qty"));
                srArchive.setRmsStatus(rs.getString("F.rms_status"));
                srArchive.setpStatus(rs.getString("F.p_status"));
                srArchive.setPkgFamily(rs.getString("F.pkg_family"));
                srArchive.setPkgName(rs.getString("F.pkg_name"));
                srArchive.setFtpStatus(rs.getString("F.status"));
                srArchive.setFtpFlag(rs.getString("F.flag"));
                //archive
                srArchive.setId(rs.getString("A.id"));
                srArchive.setReqType(rs.getString("A.request_type"));
                srArchive.setReasonsExc(rs.getString("A.reasons_exclude"));
                srArchive.setReqName(rs.getString("A.requestor_name"));
                srArchive.setRelReqName(rs.getString("A.rel_req_name"));
                srArchive.setRemarks(rs.getString("A.remarks"));
                srArchive.setFlag(rs.getString("A.flag"));
                //modified
                srArchive.setRelDateReq(rs.getString("rel_date_request_view"));
                srArchive.setpStatusDate(rs.getString("p_status_date_view"));
                srArchive.setCompDate(rs.getString("completed_date_view"));
                srArchive.setScrapDate(rs.getString("scrap_date_view"));
                srArchive.setMthToScrap(rs.getString("mth_to_scrap_view"));
                srArchive.setModifiedDate(rs.getString("modified_date_view"));
                srArchive.setCreatedDate(rs.getString("created_date_view"));
                srArchive.setStatus(rs.getString("status_view"));
                //others
                srArchive.setAging(rs.getString("aging"));
                srArchive.setLotConcat(rs.getString("lot_concat"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getAllDataViewLatest() {
        String sql = "SELECT F.*, DATEDIFF(F.mth_to_scrap, NOW()) AS aging, DATE_FORMAT(F.p_status_date,'%d %M %Y') AS p_status_date_view, DATE_FORMAT(F.completed_date,'%d %M %Y') AS completed_date_view, "
                    + "DATE_FORMAT(F.scrap_date,'%d %M %Y') AS scrap_date_view, DATE_FORMAT(F.mth_to_scrap,'%b %Y') AS mth_to_scrap_view, "
                    + "DATE_FORMAT(F.cancel_date,'%d %M %Y %h:%i %p') AS cancelDateView, A.id, "
                    + "IFNULL(A.status, F.status) AS status_view "
                    + "FROM sr_ftp_data F, sr_archive A "
                    + "WHERE F.id = A.ftp_id AND A.flag = '0' "
                    + "ORDER BY A.created_date DESC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                //ftp
                srArchive.setId(rs.getString("A.id"));
                srArchive.setFtpId(rs.getString("F.id"));
//                srArchive.setGroupId(rs.getString("F.group_id"));
                srArchive.setRmsId(rs.getString("F.rms_id"));
                srArchive.setRmsEvent(rs.getString("F.rms_event"));
                srArchive.setLotType(rs.getString("F.lot_type"));
                srArchive.setRmsLotEvent(rs.getString("F.rmslot_event"));
                srArchive.setLotQty(rs.getString("F.lot_qty"));
                srArchive.setRmsStatus(rs.getString("F.rms_status"));
                srArchive.setpStatus(rs.getString("F.p_status"));
                srArchive.setPkgFamily(rs.getString("F.pkg_family"));
                srArchive.setPkgName(rs.getString("F.pkg_name"));
                srArchive.setFtpStatus(rs.getString("F.status"));
                srArchive.setFtpFlag(rs.getString("F.flag"));
                srArchive.setCancelBy(rs.getString("F.cancel_by"));
                srArchive.setCancelDate(rs.getString("cancelDateView"));
                //modified
                srArchive.setpStatusDate(rs.getString("p_status_date_view"));
                srArchive.setCompDate(rs.getString("completed_date_view"));
                srArchive.setScrapDate(rs.getString("scrap_date_view"));
                srArchive.setMthToScrap(rs.getString("mth_to_scrap_view"));
                srArchive.setStatus(rs.getString("status_view"));
                //others
                srArchive.setAging(rs.getString("aging"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public QueryResult updateStatusPerGroupId(SRArchive srArchive) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_archive SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE ftp_group_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, srArchive.getStatus());
            ps.setString(2, srArchive.getFlag());
            ps.setString(3, srArchive.getModifiedBy());
            ps.setString(4, srArchive.getGroupId());
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

    public QueryResult updateStatusPerId(SRArchive srArchive) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_archive SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, srArchive.getStatus());
            ps.setString(2, srArchive.getFlag());
            ps.setString(3, srArchive.getModifiedBy());
            ps.setString(4, srArchive.getId());
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

    public List<SRArchive> getDistinctStatus() {
        String sql = "SELECT DISTINCT ar.`status` FROM sr_archive ar ORDER BY ar.`status` ASC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                srArchive.setStatus(rs.getString("status"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getDistinctLotType() {
        String sql = "SELECT DISTINCT ar.lot_type FROM sr_ftp_data ar ORDER BY ar.lot_type ASC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                srArchive.setLotType(rs.getString("lot_type"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getDistinctEvent() {
        String sql = "SELECT DISTINCT ar.rms_event FROM sr_ftp_data ar ORDER BY ar.rms_event ASC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                srArchive.setRmsEvent(rs.getString("rms_event"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getDistinctPkgName() {
        String sql = "SELECT DISTINCT ar.pkg_name FROM sr_ftp_data ar ORDER BY ar.pkg_name ASC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                srArchive.setPkgName(rs.getString("pkg_name"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getDistinctPkgFamily() {
        String sql = "SELECT DISTINCT ar.pkg_family FROM sr_ftp_data ar ORDER BY ar.pkg_family ASC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                srArchive.setPkgFamily(rs.getString("pkg_family"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getDistinctRmsNo() {
        String sql = "SELECT DISTINCT ft.rms_id FROM sr_ftp_data ft, sr_archive ar WHERE ar.ftp_id = ft.id ORDER BY ft.rms_id ASC ";
        List<SRArchive> archiveList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SRArchive srArchive = new SRArchive();
                srArchive.setRmsId(rs.getString("rms_id"));
                archiveList.add(srArchive);
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
        return archiveList;
    }

    public List<SRArchive> getAllQueryList(String query) {
        String sql = query;
        List<SRArchive> reqList = new ArrayList<SRArchive>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRArchive srArchive;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srArchive = new SRArchive();
                srArchive.setId(rs.getString("id"));
                srArchive.setFtpId(rs.getString("ftp_id"));
                srArchive.setRmsId(rs.getString("rms_id"));
                srArchive.setRmsEvent(rs.getString("rms_event"));
                srArchive.setLotType(rs.getString("lot_type"));
                srArchive.setRmsLotEvent(rs.getString("rmslot_event"));
                srArchive.setPkgFamily(rs.getString("pkg_family"));
                srArchive.setPkgName(rs.getString("pkg_name"));
                srArchive.setMthToScrap(rs.getString("mth_to_scrap_view"));
                srArchive.setStatus(rs.getString("status"));
                reqList.add(srArchive);
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

}