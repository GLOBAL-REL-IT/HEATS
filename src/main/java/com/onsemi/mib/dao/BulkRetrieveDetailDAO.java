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
import com.onsemi.mib.model.BulkRetrieveDetail;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BulkRetrieveDetailDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkRetrieveDetailDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public BulkRetrieveDetailDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertBulkRetrieveDetail(BulkRetrieveDetail bulkRetrieveDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO sr_bulk_retrieve_detail (bulk_id, req_id, returnable, remarks, created_date, flag) VALUES (?,?,?,?,NOW(),?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, bulkRetrieveDetail.getBulkId());
            ps.setString(2, bulkRetrieveDetail.getReqId());
            ps.setString(3, bulkRetrieveDetail.getReturnable());
            ps.setString(4, bulkRetrieveDetail.getRemarks());
            ps.setString(5, bulkRetrieveDetail.getFlag());
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

    public QueryResult updateBulkRetrieveDetail(BulkRetrieveDetail bulkRetrieveDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_bulk_retrieve_detail SET bulk_id = ?, req_id = ?, returnable = ?, remarks = ? WHERE id = ?"
            );
            ps.setString(1, bulkRetrieveDetail.getBulkId());
            ps.setString(2, bulkRetrieveDetail.getReqId());
            ps.setString(3, bulkRetrieveDetail.getReturnable());
            ps.setString(4, bulkRetrieveDetail.getRemarks());
            ps.setString(5, bulkRetrieveDetail.getId());
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

    public QueryResult updateBulkRetrieveDetailForFlag(BulkRetrieveDetail bulkRetrieveDetail) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE sr_bulk_retrieve_detail SET flag = ? WHERE id = ?"
            );
            ps.setString(1, bulkRetrieveDetail.getFlag());
            ps.setString(2, bulkRetrieveDetail.getId());
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

    public QueryResult deleteBulkRetrieveDetail(String bulkRetrieveDetailId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_bulk_retrieve_detail WHERE id = '" + bulkRetrieveDetailId + "'"
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

    public BulkRetrieveDetail getBulkRetrieveDetail(String bulkRetrieveDetailId) {
        String sql = "SELECT * FROM sr_bulk_retrieve_detail WHERE id = '" + bulkRetrieveDetailId + "'";
        BulkRetrieveDetail bulkRetrieveDetail = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bulkRetrieveDetail = new BulkRetrieveDetail();
                bulkRetrieveDetail.setId(rs.getString("id"));
                bulkRetrieveDetail.setBulkId(rs.getString("bulk_id"));
                bulkRetrieveDetail.setReqId(rs.getString("req_id"));
                bulkRetrieveDetail.setReturnable(rs.getString("returnable"));
                bulkRetrieveDetail.setRemarks(rs.getString("remarks"));
                bulkRetrieveDetail.setFlag(rs.getString("flag"));
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
        return bulkRetrieveDetail;
    }

    public List<BulkRetrieveDetail> getBulkRetrieveDetailList() {
        String sql = "SELECT * FROM sr_bulk_retrieve_detail ORDER BY id ASC";
        List<BulkRetrieveDetail> bulkRetrieveDetailList = new ArrayList<BulkRetrieveDetail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            BulkRetrieveDetail bulkRetrieveDetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bulkRetrieveDetail = new BulkRetrieveDetail();
                bulkRetrieveDetail.setId(rs.getString("id"));
                bulkRetrieveDetail.setBulkId(rs.getString("bulk_id"));
                bulkRetrieveDetail.setReqId(rs.getString("req_id"));
                bulkRetrieveDetail.setReturnable(rs.getString("returnable"));
                bulkRetrieveDetail.setRemarks(rs.getString("remarks"));
                bulkRetrieveDetail.setFlag(rs.getString("flag"));
                bulkRetrieveDetailList.add(bulkRetrieveDetail);
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
        return bulkRetrieveDetailList;
    }

    public Integer getCountBulkDetailByBulkId(String bulkId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_bulk_retrieve_detail WHERE bulk_id = '" + bulkId + "'");
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

    public Integer getCountBulkDetailByBulkIdAndReqIdAndFlagZero(String bulkId, String reqId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_bulk_retrieve_detail WHERE bulk_id = '" + bulkId + "' AND req_id = '" + reqId + "' AND flag = '0'");
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

    public Integer getCountBulkDetailByReqIdAndFlagZero(String reqId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS data FROM sr_bulk_retrieve_detail WHERE req_id = '" + reqId + "' AND flag = '0'");
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

    public List<BulkRetrieveDetail> getBulkRetrieveDetailListWithOtherTable(String bulkId) {
        String sql = "SELECT bulkD.id AS bulkDetailId, bulk.id AS bulkId, re.id AS reqId, inv.id AS invId, ft.rmslot_event, ft.actual_qty, ft.pkg_family, "
                + "ft.pkg_name, DATE_FORMAT(ft.mth_to_scrap,'%M %Y') AS mth_to_scrap_view, inv.inventory_shelf, ft.completed_date, bulkD.returnable, bulkD.remarks, bulkD.created_date "
                + "FROM sr_bulk_retrieve bulk, sr_bulk_retrieve_detail bulkD, sr_request re, sr_ftp_data ft, sr_inventory inv "
                + "WHERE bulk.id = bulkD.bulk_id AND bulkD.req_id = re.id AND re.ftp_id = ft.id AND re.inv_id = inv.id AND bulk.id = '" + bulkId + "'";
        List<BulkRetrieveDetail> bulkRetrieveDetailList = new ArrayList<BulkRetrieveDetail>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            BulkRetrieveDetail bulkRetrieveDetail;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bulkRetrieveDetail = new BulkRetrieveDetail();
                bulkRetrieveDetail.setId(rs.getString("bulkDetailId"));
                bulkRetrieveDetail.setBulkId(rs.getString("bulkId"));
                bulkRetrieveDetail.setReqId(rs.getString("reqId"));
                bulkRetrieveDetail.setInvId(rs.getString("invId"));
                bulkRetrieveDetail.setReturnable(rs.getString("returnable"));
                bulkRetrieveDetail.setRemarks(rs.getString("remarks"));
                bulkRetrieveDetail.setRmsLotEvent(rs.getString("rmslot_event"));
                bulkRetrieveDetail.setQty(rs.getString("actual_qty"));
                bulkRetrieveDetail.setPkgFamily(rs.getString("pkg_family"));
                bulkRetrieveDetail.setPkgName(rs.getString("pkg_name"));
                bulkRetrieveDetail.setScrapDate(rs.getString("mth_to_scrap_view"));
                bulkRetrieveDetail.setLocation(rs.getString("inventory_shelf"));
                bulkRetrieveDetail.setCompleteDate(rs.getString("completed_date"));
                bulkRetrieveDetail.setCreatedDate(rs.getString("created_date"));

                bulkRetrieveDetailList.add(bulkRetrieveDetail);
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
        return bulkRetrieveDetailList;
    }
}
