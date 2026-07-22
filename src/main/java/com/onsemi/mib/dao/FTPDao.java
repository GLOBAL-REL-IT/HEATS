package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.tools.QueryResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTPDao.class);
    private final Connection conn;
    private final DataSource dataSource;

    public FTPDao() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertFTPdata(FTPdata ftpdata) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_ftp_data (rms_id, lot_qty, p_status, lot_type, pkg_family, pkg_name, scrap_date, mth_to_scrap, "
                    + "completed_date, rms_event, modified_date, modified_by, created_date, created_by, status, rmslot_event,flag, actual_qty, creator) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ftpdata.getRmsId());
            ps.setString(2, ftpdata.getUnitQty());
            ps.setString(3, ftpdata.getProcessStatus());
            ps.setString(4, ftpdata.getLotType());
            ps.setString(5, ftpdata.getPkgFamily());
            ps.setString(6, ftpdata.getPkgName());
            ps.setString(7, ftpdata.getScrapDate());
            ps.setString(8, ftpdata.getMthToScrap());
            ps.setString(9, ftpdata.getCompleteDate());
            ps.setString(10, ftpdata.getEvent());
            ps.setString(11, ftpdata.getModifiedBy());
            ps.setString(12, ftpdata.getCreatedBy());
            ps.setString(13, ftpdata.getStatus());
            ps.setString(14, ftpdata.getRmsLotEvent());
            ps.setString(15, ftpdata.getFlag());
            ps.setString(16, ftpdata.getActualQty());
            ps.setString(17, ftpdata.getCreator());
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

    public Integer getCountExistingData(String rmsId, String event, String lot) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? AND lot_type = ? "
            );
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ps.setString(3, lot);
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

    public Integer getCountExistingDataNew(String rmsId, String event, String lot) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? AND lot_type = ? AND creator = 'FTP' "
            );
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ps.setString(3, lot);
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

    public Integer getCountExistingGroupId(String groupId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE group_id = ? "
            );
            ps.setString(1, groupId);
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

    public Integer getCountFtpById(String ftpId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE id = ? "
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

    public Integer getCountExistingRMSLotEvent(String rmslotevent) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rmslot_event = ? "
            );
            ps.setString(1, rmslotevent);
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

    public Integer getCountRMSLotEventWithFlagZero(String rmslotevent) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rmslot_event = ? AND flag = '0' "
            );
            ps.setString(1, rmslotevent);
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

    public Integer getCountExistingRMSLotEventFlagZeroOrOne(String rmslotevent) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rmslot_event = ? AND (flag = 0 OR flag = 1) "
            );
            ps.setString(1, rmslotevent);
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

    public Integer getCountExistingRmsEvent(String rmsId, String event) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? "
            );
            ps.setString(1, rmsId);
            ps.setString(2, event);
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

    public Integer getMaxGroupID() {
        Integer max = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT MAX(group_id) AS max FROM sr_ftp_data "
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                max = rs.getInt("max");
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
        return max;
    }

    public FTPdata getFtpData(String rmsId, String event, String lot) {
        String sql = "SELECT *, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, "
                    + "DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, DATE_FORMAT(modified_date,'%d %M %Y') AS modified_date_view, "
                    + "DATE_FORMAT(created_date,'%d %M %Y') AS created_date_view, DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view "
                    + "FROM sr_ftp_data "
                    + "WHERE rms_id = ? AND rms_event = ? AND lot_type = ? ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ps.setString(3, lot);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date_view"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setModifiedDate(rs.getString("modified_date_view"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public FTPdata getFtpDataPerRmsLotEvent(String rmslotevent) {
        String sql = "SELECT *, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, "
                    + "DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, "
                    + "DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view "
                    + "FROM sr_ftp_data "
                    + "WHERE rmslot_event = ? ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rmslotevent);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date_view"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public FTPdata getFtpDataPerRmsLotEventFlagZero(String rmslotevent) {
        String sql = "SELECT *, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, "
                + "DATE_FORMAT(scrap_date,'%d %M %Y') AS scrap_date_view, "
                + "DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view "
                + "FROM sr_ftp_data "
                + "WHERE rmslot_event = ? AND flag = '0' ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rmslotevent);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setActualQty(rs.getString("actual_qty"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date_view"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public FTPdata getFtpData2(String rmsId, String event, String lot) {
        String sql = "SELECT * FROM sr_ftp_data WHERE rms_id LIKE ? AND rms_event = ? AND lot_type = ? ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rmsId+"%");
            ps.setString(2, event);
            ps.setString(3, lot);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public FTPdata getFtpDataPerCond(String event, String mthToScrap, String pkgFamily, String rmsNo, String lotNo) {
        String sql = "SELECT * FROM sr_ftp_data "
                    + "WHERE rms_event = ? AND pkg_family = ? AND mth_to_scrap = ? "
                    + "AND rms_id = ? AND lot_type = ? AND flag = 0 ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event);
            ps.setString(2, pkgFamily);
            ps.setString(3, mthToScrap);
            ps.setString(4, rmsNo);
            ps.setString(5, lotNo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public List<FTPdata> getFtpLotDataPerCond(String event, String mthToScrap, String pkgFamily, String rmsNo) {
        String sql = "SELECT * FROM sr_ftp_data "
                    + "WHERE rms_event = ? AND pkg_family = ? AND mth_to_scrap = ? "
                    + "AND rms_id = ? AND flag = 0 ";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event);
            ps.setString(2, pkgFamily);
            ps.setString(3, mthToScrap);
            ps.setString(4, rmsNo);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public FTPdata getFtpDataPerRMSLotEvent(String event, String mthToScrap, String pkgFamily, String rmsLotEvent) {
        String sql = "SELECT * FROM sr_ftp_data "
                    + "WHERE rms_event = ? AND pkg_family = ? AND mth_to_scrap = ? "
                    + "AND rmslot_event = ? AND flag = 0 ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event);
            ps.setString(2, pkgFamily);
            ps.setString(3, mthToScrap);
            ps.setString(4, rmsLotEvent);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public Integer getGroupId(String rmsId, String event) {
        Integer groupId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT DISTINCT group_id FROM sr_ftp_data WHERE rms_id = ? AND rms_event = ? "
            );
            ps.setString(1, rmsId);
            ps.setString(2, event);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groupId = rs.getInt("group_id");
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
        return groupId;
    }

    public FTPdata getFtpDataById(String id) {
        String sql = "SELECT * FROM sr_ftp_data WHERE id = ? ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
//                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
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

    public FTPdata getFtpDataByRequestId(String requestId) {
        String sql = "SELECT ft.* FROM sr_ftp_data ft, sr_request re WHERE re.ftp_id = ft.id AND re.id = ? ";
        FTPdata ftpdata = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, requestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
//                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setCreator(rs.getString("creator"));
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

    public List<FTPdata> getAllFtpData() {
        String sql = "SELECT id, group_id, rms_id, rms_event, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, DATEDIFF(NOW(),completed_date) AS packingDay, "
                    + "GROUP_CONCAT(lot_type ORDER BY lot_type ASC SEPARATOR ', ') AS lot_concat, DATE_FORMAT(completed_date,'%d %b %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view "
                    + "FROM sr_ftp_data "
                    + "WHERE ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) and flag = 0 "
                    + "GROUP BY group_id "
                    + "ORDER BY completed_date ASC ";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setConcatLot(rs.getString("lot_concat"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));//aging
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setPackingDay(rs.getString("packingDay"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public List<FTPdata> getAllFtpDataLatest() {
        String sql = "SELECT id, rmslot_event, rms_id, rms_event, lot_type, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, DATEDIFF(NOW(),completed_date) AS packingDay, "
                    + "DATE_FORMAT(completed_date,'%d %b %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view "
                    + "FROM sr_ftp_data "
                    + "WHERE ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) and flag = 0 "
                    + "ORDER BY completed_date ASC ";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));//aging
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setPackingDay(rs.getString("packingDay"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public List<FTPdata> getAllFtpDataforMonhtlyReport(String fromDate, String toDate) {
        String sql = "SELECT id, group_id, rms_id, rms_event, pkg_family, pkg_name, p_status, status, "
                    + "DATEDIFF(mth_to_scrap, NOW()) AS aging, DATEDIFF(NOW(),completed_date) AS packingDay, "
                    + "GROUP_CONCAT(lot_type ORDER BY lot_type ASC SEPARATOR ', ') AS lot_concat, "
                    + "DATE_FORMAT(completed_date,'%d %b %Y') AS completed_date_view, "
                    + "DATE_FORMAT(mth_to_scrap,'%b %Y') AS mth_to_scrap_view "
                    + "FROM sr_ftp_data "
                    + "WHERE ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) "
                    + "and flag = 0 AND completed_date BETWEEN ? AND LAST_DAY(?) "
                    + "GROUP BY group_id "
                    + "ORDER BY completed_date ASC ";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setConcatLot(rs.getString("lot_concat"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));//aging
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setPackingDay(rs.getString("packingDay"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public List<FTPdata> getAllExpiredFtpData() {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging FROM sr_ftp_data "
                + "WHERE flag IN(0,1,9) AND ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) ";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setAging(rs.getString("aging"));//aging
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public List<FTPdata> getAllExpiredFtpDataNew() {
        String sql = "SELECT *, DATEDIFF(mth_to_scrap, NOW()) AS aging FROM sr_ftp_data "
                + "WHERE status like '%New Record%' AND flag IN(0) AND ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) ";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
//                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setAging(rs.getString("aging"));//aging
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public List<FTPdata> getExpFtpData() {
        String sql = "SELECT id, group_id, rms_id, rms_event, pkg_family, pkg_name, p_status, status, DATEDIFF(mth_to_scrap, NOW()) AS aging, "
                    + "GROUP_CONCAT(lot_type SEPARATOR ',') AS lot_concat, DATE_FORMAT(completed_date,'%d %M %Y') AS completed_date_view, DATE_FORMAT(mth_to_scrap,'%M %Y') AS mth_to_scrap_view "
                    + "FROM sr_ftp_data "
                    + "WHERE ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) "
                    + "GROUP BY group_id "
                    + "ORDER BY completed_date DESC";
        List<FTPdata> ftpDataList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setConcatLot(rs.getString("lot_concat"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setCompleteDate(rs.getString("completed_date_view"));
                ftpdata.setAging(rs.getString("aging"));//aging
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap_view"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setStatus(rs.getString("status"));
                ftpDataList.add(ftpdata);
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
        return ftpDataList;
    }

    public QueryResult updateStatus(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getId());
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

    public QueryResult updateStatusPerGroupId(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE group_id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getGroupId());
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

    public QueryResult updateStatusbyFtpId(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ?, cancel_by = NULL, cancel_date = NULL WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getId());
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

    public QueryResult updateStatusAndFlagbyFtpId(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getStatus());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getModifiedBy());
            ps.setString(4, ftpData.getId());
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

    public QueryResult updateQty(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET lot_qty = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getUnitQty());
            ps.setString(2, ftpData.getModifiedBy());
            ps.setString(3, ftpData.getId());
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

    public QueryResult updateActualQty(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET actual_qty = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getActualQty());
            ps.setString(2, ftpData.getModifiedBy());
            ps.setString(3, ftpData.getId());
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

    public QueryResult updatePkgFamily(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET pkg_family = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getPkgFamily());
            ps.setString(2, ftpData.getModifiedBy());
            ps.setString(3, ftpData.getId());
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

    public QueryResult updateActualQtyFlagAndStatus(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET actual_qty = ?, flag = ?, status = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getActualQty());
            ps.setString(2, ftpData.getFlag());
            ps.setString(3, ftpData.getStatus());
            ps.setString(4, ftpData.getId());
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

    public QueryResult updateMthToScrap(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET completed_date = ?, scrap_date = ?, mth_to_scrap = ?, modified_date = NOW(), modified_by = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getCompleteDate());
            ps.setString(2, ftpData.getScrapDate());
            ps.setString(3, ftpData.getMthToScrap());
            ps.setString(4, ftpData.getModifiedBy());
            ps.setString(5, ftpData.getId());
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

    public QueryResult updateCancelRetention(FTPdata ftpData) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET cancel_by = ?, cancel_date = NOW(), status = ?, flag = ? WHERE id = ? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpData.getCancelBy());
            ps.setString(2, ftpData.getStatus());
            ps.setString(3, ftpData.getFlag());
            ps.setString(4, ftpData.getId());
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

    public List<FTPdata> getAllSubEventPerRms(String event, String rmsNo) {
        String sql = "SELECT rms_event FROM sr_ftp_data "
                    + "WHERE flag = 0 AND (rms_event LIKE ? OR rms_event = ?) AND rms_id = ? "
                    + "GROUP BY group_id "
                    + "ORDER BY rms_event ";
        List<FTPdata> subEventList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event+"_");
            ps.setString(2, event);
            ps.setString(3, rmsNo);
            FTPdata ftpData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpData = new FTPdata();
                ftpData.setEvent(rs.getString("rms_event"));
                subEventList.add(ftpData);
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
        return subEventList;
    }

    public List<FTPdata> getAllEventPerRms(String rmsNo) {
        String sql = "SELECT DISTINCT rms_event  FROM sr_ftp_data WHERE flag = 0 AND rms_id = ? ORDER BY rms_event ";
        List<FTPdata> subEventList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rmsNo);
            FTPdata ftpData;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpData = new FTPdata();
                ftpData.setEvent(rs.getString("rms_event"));
                subEventList.add(ftpData);
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
        return subEventList;
    }

    public QueryResult updateSelectedExpiredLot(FTPdata ftpdata) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_ftp_data SET status = ? , flag = ?, modified_date = NOW(), modified_by = ?, cancel_by = ?, cancel_date = NOW() "
                    + "WHERE id = ? AND flag = 0 AND ((YEAR(mth_to_scrap) < YEAR(NOW())) OR (MONTH(mth_to_scrap) < MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ftpdata.getStatus());
            ps.setString(2, ftpdata.getFlag());
            ps.setString(3, ftpdata.getModifiedBy());
            ps.setString(4, ftpdata.getCancelBy());
            ps.setString(5, ftpdata.getId());
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

    public String getActualEvent(String event, String rmsId, String mthToScrap) {
        String actEvent = event;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT DISTINCT (rms_event) AS act_event FROM sr_ftp_data "
                    + "WHERE (rms_event LIKE ? OR rms_event = ?) AND rms_id = ? AND mth_to_scrap = ? AND flag = 0 "
            );
            ps.setString(1, event+"_");
            ps.setString(2, event);
            ps.setString(3, rmsId);
            ps.setString(4, mthToScrap);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actEvent = rs.getString("act_event");
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

        return actEvent;
    }

    public String getLatestRevDate() {
        String revDate = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT DATE_FORMAT(MAX(created_date), '%d-%b-%Y %h:%m %p') AS maxDate FROM sr_ftp_data "
            );

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                revDate = rs.getString("maxDate");
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
        return revDate;
    }

    public List<FTPdata> getAllActualDetailsPerRmsExtQuery(String rmsNo, String extQuery) {
        String sql = "SELECT * FROM sr_ftp_data WHERE flag = 0 AND rms_id = '" + rmsNo + "' " + extQuery + "ORDER BY id ";
        List<FTPdata> rmsDetailsList = new ArrayList<FTPdata>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            FTPdata ftpdata;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ftpdata = new FTPdata();
                ftpdata.setId(rs.getString("id"));
                ftpdata.setGroupId(rs.getString("group_id"));
                ftpdata.setRmsId(rs.getString("rms_id"));
                ftpdata.setEvent(rs.getString("rms_event"));
                ftpdata.setLotType(rs.getString("lot_type"));
                ftpdata.setRmsLotEvent(rs.getString("rmslot_event"));
                ftpdata.setUnitQty(rs.getString("lot_qty"));
                ftpdata.setStatus(rs.getString("rms_status"));
                ftpdata.setProcessStatus(rs.getString("p_status"));
                ftpdata.setPkgFamily(rs.getString("pkg_family"));
                ftpdata.setPkgName(rs.getString("pkg_name"));
                ftpdata.setScrapDate(rs.getString("scrap_date"));
                ftpdata.setMthToScrap(rs.getString("mth_to_scrap"));
                ftpdata.setCompleteDate(rs.getString("completed_date"));
                ftpdata.setStatus(rs.getString("status"));
                ftpdata.setFlag(rs.getString("flag"));
                ftpdata.setModifiedDate(rs.getString("modified_date"));
                ftpdata.setModifiedBy(rs.getString("modified_by"));
                ftpdata.setCreatedDate(rs.getString("created_date"));
                ftpdata.setCreatedBy(rs.getString("created_by"));
                rmsDetailsList.add(ftpdata);
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
        return rmsDetailsList;
    }

}