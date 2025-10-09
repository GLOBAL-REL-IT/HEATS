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
import com.onsemi.mib.model.Retrieve;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetrieveDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public RetrieveDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertRetrieve(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_retrieve (req_id, returnable, requestor_name, req_remarks, req_date, created_by, created_date, status, flag, requestor_email) VALUES (?,?,?,?,NOW(),?,NOW(),?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, retrieve.getReqId());
            ps.setString(2, retrieve.getReturnable());
            ps.setString(3, retrieve.getRequestorName());
            ps.setString(4, retrieve.getReqRemarks());
            ps.setString(5, retrieve.getCreatedBy());
            ps.setString(6, retrieve.getStatus());
            ps.setString(7, retrieve.getFlag());
            ps.setString(8, retrieve.getRequestorEmail());
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

    public QueryResult updateRetrieve(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET req_id = ?, box_id = ?, returnable = ?, requestor_name = ?, req_remarks = ?, req_date = ?, verification_date = ?, verification_by = ?, rl_received_date = ?, rl_received_by = ?, return_verification = ?, return_by = ?, return_date = ?, created_by = ?, created_date = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getReqId());
            ps.setString(2, retrieve.getBoxId());
            ps.setString(3, retrieve.getReturnable());
            ps.setString(4, retrieve.getRequestorName());
            ps.setString(5, retrieve.getReqRemarks());
            ps.setString(6, retrieve.getReqDate());
            ps.setString(7, retrieve.getVerificationDate());
            ps.setString(8, retrieve.getVerificationBy());
            ps.setString(9, retrieve.getRlReceivedDate());
            ps.setString(10, retrieve.getRlReceivedBy());
            ps.setString(11, retrieve.getReturnVerification());
            ps.setString(12, retrieve.getReturnBy());
            ps.setString(13, retrieve.getReturnDate());
            ps.setString(14, retrieve.getCreatedBy());
            ps.setString(15, retrieve.getCreatedDate());
            ps.setString(16, retrieve.getStatus());
            ps.setString(17, retrieve.getFlag());
            ps.setString(18, retrieve.getId());
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

    public QueryResult updateRetrieveVerification(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET verification_date = NOW(), verification_by = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getVerificationBy());
            ps.setString(2, retrieve.getStatus());
            ps.setString(3, retrieve.getFlag());
            ps.setString(4, retrieve.getId());
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

    public QueryResult updateRetrieveReceived(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET rl_received_date = NOW(), rl_received_by = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getRlReceivedBy());
            ps.setString(2, retrieve.getStatus());
            ps.setString(3, retrieve.getFlag());
            ps.setString(4, retrieve.getId());
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

    public QueryResult updateRetrieveReturnable(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET returnable = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getReturnable());
            ps.setString(2, retrieve.getId());
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

    public QueryResult updateRetrieveReturn(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET return_date = NOW(), return_by = ?, status = ?, flag = ?, return_qty = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getReturnBy());
            ps.setString(2, retrieve.getStatus());
            ps.setString(3, retrieve.getFlag());
            ps.setString(4, retrieve.getReturnQty());
            ps.setString(5, retrieve.getId());
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

    public QueryResult updateRetrieveReInventory(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET re_inventory_date = NOW(), re_inventory_by = ?, status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getReInventoryBy());
            ps.setString(2, retrieve.getStatus());
            ps.setString(3, retrieve.getFlag());
            ps.setString(4, retrieve.getId());
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

    public QueryResult updateRetrieveStatusAndFlag(Retrieve retrieve) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_retrieve SET status = ?, flag = ? WHERE id = ?"
            );
            ps.setString(1, retrieve.getStatus());
            ps.setString(2, retrieve.getFlag());
            ps.setString(3, retrieve.getId());
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

    public QueryResult deleteRetrieve(String retrieveId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_retrieve WHERE id = '" + retrieveId + "'"
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

    public Retrieve getRetrieve(String retrieveId) {
        String sql = "SELECT * FROM sr_retrieve WHERE id = '" + retrieveId + "'";
        Retrieve retrieve = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrieve = new Retrieve();
                retrieve.setId(rs.getString("id"));
                retrieve.setReqId(rs.getString("req_id"));
                retrieve.setRequestorEmail(rs.getString("requestor_email"));
                retrieve.setReturnable(rs.getString("returnable"));
                retrieve.setRequestorName(rs.getString("requestor_name"));
                retrieve.setReqRemarks(rs.getString("req_remarks"));
                retrieve.setReqDate(rs.getString("req_date"));
                retrieve.setVerificationDate(rs.getString("verification_date"));
                retrieve.setVerificationBy(rs.getString("verification_by"));
                retrieve.setRlReceivedDate(rs.getString("rl_received_date"));
                retrieve.setRlReceivedBy(rs.getString("rl_received_by"));
//                retrieve.setReturnVerification(rs.getString("return_verification"));
                retrieve.setReturnBy(rs.getString("return_by"));
                retrieve.setReturnDate(rs.getString("return_date"));
                retrieve.setCreatedBy(rs.getString("created_by"));
                retrieve.setCreatedDate(rs.getString("created_date"));
                retrieve.setStatus(rs.getString("status"));
                retrieve.setFlag(rs.getString("flag"));
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
        return retrieve;
    }

    public Retrieve getRetrieveWithAllDetail(String retrieveId) {
        String sql = "SELECT ret.*, ftp.rms_id, ftp.rms_event, ftp.lot_type, "
                + "ftp.rmslot_event, ftp.actual_qty, ftp.pkg_family, ftp.pkg_name, inv.inventory_shelf, "
                + "DATE_FORMAT(ret.req_date,'%d %M %Y %h:%i %p') AS req_date_view, "
                + "DATE_FORMAT(ret.verification_date,'%d %M %Y %h:%i %p') AS verification_date_view, "
                + "DATE_FORMAT(ret.rl_received_date,'%d %M %Y %h:%i %p') AS rl_received_date_view, "
                + "DATE_FORMAT(ret.return_date,'%d %M %Y %h:%i %p') AS return_date_view, "
                + "DATE_FORMAT(ret.re_inventory_date,'%d %M %Y %h:%i %p') AS reInventory_date_view, "
                + "DATE_FORMAT(ftp.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "DATE_FORMAT(ftp.completed_date,'%d %M %Y') AS complete_date_view "
                + "FROM sr_retrieve ret, sr_ftp_data ftp, sr_inventory inv, sr_request re "
                + "WHERE ret.req_id = re.id AND re.ftp_id = ftp.id AND ret.req_id = inv.req_id "
                + "AND ret.id = '" + retrieveId + "'";
        Retrieve retrieve = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrieve = new Retrieve();
                retrieve.setId(rs.getString("ret.id"));
                retrieve.setReqId(rs.getString("ret.req_id"));
                retrieve.setReturnable(rs.getString("ret.returnable"));
                retrieve.setRequestorName(rs.getString("ret.requestor_name"));
                retrieve.setReqRemarks(rs.getString("ret.req_remarks"));
                retrieve.setReqDate(rs.getString("req_date_view"));
                retrieve.setVerificationDate(rs.getString("verification_date_view"));
                retrieve.setVerificationBy(rs.getString("ret.verification_by"));
                retrieve.setRlReceivedDate(rs.getString("rl_received_date_view"));
                retrieve.setRlReceivedBy(rs.getString("ret.rl_received_by"));
                retrieve.setReturnBy(rs.getString("ret.return_by"));
                retrieve.setReturnDate(rs.getString("return_date_view"));
                retrieve.setCreatedBy(rs.getString("ret.created_by"));
                retrieve.setCreatedDate(rs.getString("ret.created_date"));
                retrieve.setStatus(rs.getString("ret.status"));
                retrieve.setFlag(rs.getString("ret.flag"));
                retrieve.setRmsLotEvent(rs.getString("rmslot_event"));
                retrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                retrieve.setPackageFamily(rs.getString("pkg_family"));
                retrieve.setPackageName(rs.getString("pkg_name"));
                retrieve.setShelf(rs.getString("inventory_shelf"));
                retrieve.setQty(rs.getString("actual_qty"));
                retrieve.setReInventoryBy(rs.getString("re_inventory_by"));
                retrieve.setReInventoryDate(rs.getString("reInventory_date_view"));
                retrieve.setRmsId(rs.getString("rms_id"));
                retrieve.setRmsEvent(rs.getString("rms_event"));
                retrieve.setLot(rs.getString("lot_type"));
                retrieve.setCompleteDate(rs.getString("complete_date_view"));
                retrieve.setRequestorEmail(rs.getString("ret.requestor_email"));
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
        return retrieve;
    }

    public List<Retrieve> getRetrieveList() {
        String sql = "SELECT ret.*, ftp.rmslot_event, ftp.actual_qty, ftp.pkg_family, ftp.pkg_name, inv.inventory_shelf, "
                + "DATE_FORMAT(ret.req_date,'%d %M %Y %h:%i %p') AS req_date_view, "
                + "DATE_FORMAT(ret.verification_date,'%d %M %Y %h:%i %p') AS verification_date_view, "
                + "DATE_FORMAT(ret.rl_received_date,'%d %M %Y %h:%i %p') AS rl_received_date_view, "
                + "DATE_FORMAT(ret.return_date,'%d %M %Y %h:%i %p') AS return_date_view, "
                + "DATE_FORMAT(ftp.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, "
                + "DATE_FORMAT(ret.re_inventory_date,'%d %M %Y %h:%i %p') AS reInventory_date_view "
                + "FROM sr_retrieve ret, sr_ftp_data ftp, sr_inventory inv, sr_request re "
                + "WHERE ret.req_id = re.id AND re.ftp_id = ftp.id AND ret.req_id = inv.req_id AND ret.flag = '0' "
                + "ORDER BY ret.id DESC";
        List<Retrieve> retrieveList = new ArrayList<Retrieve>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            Retrieve retrieve;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retrieve = new Retrieve();
                retrieve.setId(rs.getString("id"));
                retrieve.setReqId(rs.getString("req_id"));
                retrieve.setReturnable(rs.getString("returnable"));
                retrieve.setRequestorName(rs.getString("requestor_name"));
                retrieve.setReqRemarks(rs.getString("req_remarks"));
                retrieve.setReqDate(rs.getString("req_date_view"));
                retrieve.setVerificationDate(rs.getString("verification_date_view"));
                retrieve.setVerificationBy(rs.getString("verification_by"));
                retrieve.setRlReceivedDate(rs.getString("rl_received_date_view"));
                retrieve.setRlReceivedBy(rs.getString("rl_received_by"));
//                retrieve.setReturnVerification(rs.getString("return_verification"));
                retrieve.setReturnBy(rs.getString("return_by"));
                retrieve.setReturnDate(rs.getString("return_date_view"));
                retrieve.setCreatedBy(rs.getString("created_by"));
                retrieve.setCreatedDate(rs.getString("created_date"));
                retrieve.setStatus(rs.getString("status"));
                retrieve.setFlag(rs.getString("flag"));
                retrieve.setRmsLotEvent(rs.getString("rmslot_event"));
                retrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                retrieve.setPackageFamily(rs.getString("pkg_family"));
                retrieve.setPackageName(rs.getString("pkg_name"));
                retrieve.setShelf(rs.getString("inventory_shelf"));
                retrieve.setQty(rs.getString("actual_qty"));
                retrieve.setReInventoryBy(rs.getString("re_inventory_by"));
                retrieve.setReInventoryDate(rs.getString("reInventory_date_view"));
                retrieve.setRequestorEmail(rs.getString("ret.requestor_email"));
                retrieveList.add(retrieve);
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
        return retrieveList;
    }

<<<<<<< HEAD:src/main/java/com/onsemi/mib/dao/RetrieveDAO.java
=======
    public Integer getCountSample() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_inventory_mgt WHERE STATUS = 'Shelf Not Available'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public Integer getCountInventory(String month) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_inventory WHERE MONTH(mth_to_scrap) = '" + month + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/dao/RetrieveDAO.java
    public Integer getCountInventory(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_inventory WHERE MONTH(inventory_date) = '" + month + "' AND YEAR(inventory_date) = '" + year + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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

    public Integer getCountRetrieve(String month, String year) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_retrieve WHERE STATUS != 'Cancel Request' AND MONTH(req_date) = '" + month + "' AND YEAR(req_date) = '" + year + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("data");
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
