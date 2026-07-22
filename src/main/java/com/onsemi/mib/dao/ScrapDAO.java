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
import com.onsemi.mib.model.Scrap;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public ScrapDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertScrap(Scrap scrap) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_scrap (request_id, month_scrap, scrap_by, scrap_date, status, created_by, created_date, flag) VALUES (?,?,?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, scrap.getRequestId());
            ps.setString(2, scrap.getMonthScrap());
            ps.setString(3, scrap.getScrapBy());
            ps.setString(4, scrap.getScrapDate());
            ps.setString(5, scrap.getStatus());
            ps.setString(6, scrap.getCreatedBy());
            ps.setString(7, scrap.getFlag());
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

    public QueryResult updateScrap(Scrap scrap) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_scrap SET request_id = ?, box_id = ?, month_scrap = ?, scrap_by = ?, scrap_date = ?, status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ? "
            );
            ps.setString(1, scrap.getRequestId());
            ps.setString(2, scrap.getBoxId());
            ps.setString(3, scrap.getMonthScrap());
            ps.setString(4, scrap.getScrapBy());
            ps.setString(5, scrap.getScrapDate());
            ps.setString(6, scrap.getStatus());
            ps.setString(7, scrap.getCreatedBy());
            ps.setString(8, scrap.getCreatedDate());
            ps.setString(9, scrap.getFlag());
            ps.setString(10, scrap.getId());
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

    public QueryResult deleteScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM sr_scrap WHERE id = ? ");
            ps.setString(1, scrapId);
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

    public QueryResult revertScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE sr_scrap SET STATUS = 'Pending Scrap', flag = '0' WHERE id = ? ");
            ps.setString(1, scrapId);
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

    public QueryResult updateReadyScrap(String scrapId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE sr_scrap SET STATUS = 'Ready for Scrap', flag = '0' WHERE id = ? ");
            ps.setString(1, scrapId);
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setResult(0);
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

    public QueryResult scrapAll(Scrap scrap) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_scrap SET STATUS = ?, flag = ?, scrap_by = ?, scrap_date = NOW() WHERE STATUS = 'Ready for Scrap' AND flag = '0' "
            );
            ps.setString(1, scrap.getStatus());
            ps.setString(2, scrap.getFlag());
            ps.setString(3, scrap.getScrapBy());
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

    public QueryResult readyScrap(String rmsEvent) {
        String scrapId = getScrapId(rmsEvent);
        QueryResult queryResult = updateReadyScrap(scrapId);
        return queryResult;
    }

    public Scrap getScrap(String scrapId) {
        String sql = "SELECT * FROM sr_scrap WHERE id = ? ";
        Scrap scrap = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, scrapId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrap = new Scrap();
                scrap.setId(rs.getString("id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setBoxId(rs.getString("box_id"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setScrapBy(rs.getString("scrap_by"));
                scrap.setScrapDate(rs.getString("scrap_date"));
                scrap.setStatus(rs.getString("status"));
                scrap.setCreatedBy(rs.getString("created_by"));
                scrap.setCreatedDate(rs.getString("created_date"));
                scrap.setFlag(rs.getString("flag"));
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
        return scrap;
    }

    public Scrap getScrapById(String scrapId) {
        String sql = "SELECT sc.*, re.id AS reqID, re.ftp_id AS ftpId, re.inv_id AS invID FROM sr_scrap sc, sr_request re "
                    + "WHERE sc.request_id = re.id AND sc.id = ? ";
        Scrap scrap = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, scrapId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrap = new Scrap();
                scrap.setId(rs.getString("id"));
                scrap.setRequestId(rs.getString("reqID"));
                scrap.setFtpId(rs.getString("ftpId"));
                scrap.setInvId(rs.getString("invID"));
                scrap.setStatus(rs.getString("status"));
                scrap.setFlag(rs.getString("flag"));
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
        return scrap;
    }

    public List<Scrap> getScrapList() {
        String sql = "SELECT * FROM sr_scrap ORDER BY id ASC";
        List<Scrap> scrapList = new ArrayList<Scrap>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Scrap scrap;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrap = new Scrap();
                scrap.setId(rs.getString("id"));
                scrap.setRequestId(rs.getString("request_id"));
//                scrap.setBoxId(rs.getString("box_id"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setScrapBy(rs.getString("scrap_by"));
                scrap.setScrapDate(rs.getString("scrap_date"));
                scrap.setStatus(rs.getString("status"));
                scrap.setCreatedBy(rs.getString("created_by"));
                scrap.setCreatedDate(rs.getString("created_date"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
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
        return scrapList;
    }

    public List<Scrap> getPendingScrapList() {
        String sql = "SELECT sc.id AS scrap_id, rq.id AS request_id, ftp.id AS ftp_id, DATE_FORMAT(sc.month_scrap,'%M %Y') AS month_scrap, final_qty, actual_qty, inventory_rack, inventory_shelf, inventory_date, "
                    + "sc.status scrap_status, rq.status AS request_status, rmslot_event, lot_qty, p_status, pkg_name, sc.status AS scrap_status, sc.flag AS flag, inv.`status` AS inventory_status "
                    + "FROM sr_scrap sc "
                    + "INNER JOIN sr_request rq ON request_id = rq.id "
                    + "INNER JOIN sr_ftp_data ftp ON ftp_id = ftp.id "
                    + "INNER JOIN sr_inventory inv ON inv_id = inv.id "
                    + "WHERE sc.flag IN ('0') ORDER BY sc.month_scrap ASC";
        List<Scrap> scrapList = new ArrayList<Scrap>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Scrap scrap;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrap = new Scrap();
                scrap.setId(rs.getString("scrap_id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setStatus(rs.getString("scrap_status"));
                scrap.setActualQty(rs.getString("actual_qty"));
                scrap.setRmsLotEvent(rs.getString("rmslot_event"));
                scrap.setPackageName(rs.getString("pkg_name"));
                scrap.setInvId(rs.getString("inventory_rack"));
                scrap.setShelf(rs.getString("inventory_shelf"));
                scrap.setInvStatus(rs.getString("inventory_status"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
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
        return scrapList;
    }

    public List<Scrap> getReadyScrapList() {
        String sql = "SELECT sc.id AS scrap_id, rq.id AS request_id, ftp.id AS ftp_id, inv.id AS invId, MONTHNAME(month_scrap) AS month_scrap, final_qty, actual_qty, inventory_rack, inventory_shelf, inventory_date, "
                    + "sc.status scrap_status, rq.status AS request_status, rmslot_event, lot_qty, p_status, pkg_name, sc.status AS scrap_status, sc.flag AS flag, inv.`status` AS inventory_status "
                    + "FROM sr_scrap sc "
                    + "INNER JOIN sr_request rq ON request_id = rq.id "
                    + "INNER JOIN sr_ftp_data ftp ON ftp_id = ftp.id "
                    + "INNER JOIN sr_inventory inv ON inv_id = inv.id "
                    + "WHERE sc.flag IN ('0') AND sc.status = 'Ready for Scrap' ORDER BY scrap_id ASC";
        List<Scrap> scrapList = new ArrayList<Scrap>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Scrap scrap;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrap = new Scrap();
                scrap.setId(rs.getString("scrap_id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setFtpId(rs.getString("ftp_id"));
                scrap.setInvId(rs.getString("invId"));
                scrap.setMonthScrap(rs.getString("month_scrap"));
                scrap.setStatus(rs.getString("scrap_status"));
                scrap.setActualQty(rs.getString("actual_qty"));
                scrap.setRmsLotEvent(rs.getString("rmslot_event"));
                scrap.setPackageName(rs.getString("pkg_name"));
                scrap.setShelf(rs.getString("inventory_shelf"));
                scrap.setInvStatus(rs.getString("inventory_status"));
                scrap.setFlag(rs.getString("flag"));
                scrapList.add(scrap);
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
        return scrapList;
    }

    public String getScrapId(String rms_event) {
        String scrapId = "0";
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT s1.id FROM sr_scrap s1 "
                    + "INNER JOIN sr_request s2 ON s1.request_id = s2.id "
                    + "INNER JOIN sr_ftp_data s3 ON s2.ftp_id = s3.id "
                    + "WHERE rmslot_event = ? "
            );
            ps.setString(1, rms_event);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrapId = rs.getString("id");
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
        return scrapId;
    }

    public Scrap getScrapbyRmsLotEvent(String rms_event) {
        String sql = "SELECT s1.id, s1.request_id, s2.ftp_id, s2.inv_id FROM sr_scrap s1  "
                    + "INNER JOIN sr_request s2 ON s1.request_id = s2.id "
                    + "INNER JOIN sr_ftp_data s3 ON s2.ftp_id = s3.id "
                    + "WHERE rmslot_event = ? ";
        Scrap scrap = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rms_event);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scrap = new Scrap();
                scrap.setId(rs.getString("id"));
                scrap.setRequestId(rs.getString("request_id"));
                scrap.setFtpId(rs.getString("ftp_id"));
                scrap.setInvId(rs.getString("inv_id"));
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
        return scrap;
    }

    public Integer getCountPendingScrap(String rmsLotEvent) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(sc.id) AS COUNT "
                    + "FROM sr_scrap sc, sr_request re, sr_ftp_data ft "
                    + "WHERE sc.`status` = 'Pending Scrap' AND sc.request_id = re.id "
                    + "AND re.ftp_id = ft.id AND ft.rmslot_event = ? "
            );
            ps.setString(1, rmsLotEvent);
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

    public Integer getCountReadyForScrap() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(sc.id) AS count FROM sr_scrap sc WHERE sc.`status` = 'Ready for Scrap' AND sc.flag = '0'"
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