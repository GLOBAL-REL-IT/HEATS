package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.DOList;
import com.onsemi.mib.model.UserEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DOListDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DOListDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public DOListDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insertDOList(DOList doList) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO sr_do_list (req_id, box_id, req_type, total_units, total_weight, event, pkg_family, mth_to_scrap, modified_date, modified_by, created_date, created_by, status, flag, unit_price) "
              + "VALUES (?,?,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, doList.getReqId());
            ps.setString(2, doList.getBoxId());
            ps.setString(3, doList.getReqType());
            ps.setString(4, doList.getTotalUnits());
            ps.setString(5, doList.getTotalWeight());
            ps.setString(6, doList.getEvent());
            ps.setString(7, doList.getPkgFamily());
            ps.setString(8, doList.getMthToScrap());
            ps.setString(9, doList.getModifiedBy());
            ps.setString(10, doList.getCreatedBy());
            ps.setString(11, doList.getStatus());
            ps.setString(12, doList.getFlag());
            ps.setString(13, doList.getUnitPrice());
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

    public QueryResult updateDo(DOList doList) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_do_list "
                   + "SET gts_no = ?, shipping_date = ?, status = ?, flag = ?, modified_date = NOW(), modified_by = ?, total_box = ?, index_count = ? "
                   + "WHERE flag = '0' ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, doList.getGtsNo());
            ps.setString(2, doList.getShipDate());
            ps.setString(3, doList.getStatus());
            ps.setString(4, doList.getFlag());
            ps.setString(5, doList.getModifiedBy());
            ps.setString(6, doList.getTotalBox());
            ps.setString(7, doList.getIndexCount());
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
    
    
    public List<DOList> getAllDOListActual(String flag) {
        String sql = "SELECT * "
                   + "FROM sr_do_list "
                   + "WHERE flag = '" + flag + "' ";
        List<DOList> doActualList = new ArrayList<DOList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DOList doList ;
            while (rs.next()) {
                doList = new DOList();
                doList.setDoId(rs.getString("id"));
                doList.setReqId(rs.getString("req_id"));
                doList.setBoxId(rs.getString("box_id"));
                doList.setReqType(rs.getString("req_type"));
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setShipDate(rs.getString("shipping_date"));
                doList.setTotalUnits(rs.getString("total_units"));
                doList.setTotalWeight(rs.getString("total_weight"));
                doList.setTotalBox(rs.getString("total_box"));
                doList.setUnitPrice(rs.getString("unit_price"));
                doList.setEvent(rs.getString("event"));
                doList.setPkgFamily(rs.getString("pkg_family"));
                doList.setMthToScrap(rs.getString("mth_to_scrap"));
                doList.setStatus(rs.getString("status"));
                doList.setFlag(rs.getString("flag"));
                doList.setModifiedBy(rs.getString("modified_by"));
                doList.setModifiedDate(rs.getString("modified_date")); 
                doList.setCreatedDate(rs.getString("created_date"));
                doList.setCreatedBy(rs.getString("created_by"));
                doActualList.add(doList);                
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
        return doActualList;
    }
    
    public DOList getAllDOListActualPerReqId(String reqId) {
        String sql = "SELECT * "
                   + "FROM sr_do_list "
                   + "WHERE req_id = '" + reqId  + "' ";
        DOList doList = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doList = new DOList();
                doList.setDoId(rs.getString("id"));
                doList.setReqId(rs.getString("req_id"));
                doList.setBoxId(rs.getString("box_id"));
                doList.setReqType(rs.getString("req_type"));
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setShipDate(rs.getString("shipping_date"));
                doList.setTotalUnits(rs.getString("total_units"));
                doList.setTotalWeight(rs.getString("total_weight"));
                doList.setTotalBox(rs.getString("total_box"));
                doList.setUnitPrice(rs.getString("unit_price"));
                doList.setEvent(rs.getString("event"));
                doList.setPkgFamily(rs.getString("pkg_family"));
                doList.setMthToScrap(rs.getString("mth_to_scrap"));
                doList.setStatus(rs.getString("status"));
                doList.setFlag(rs.getString("flag"));
                doList.setModifiedBy(rs.getString("modified_by"));
                doList.setModifiedDate(rs.getString("modified_date")); 
                doList.setCreatedDate(rs.getString("created_date"));
                doList.setCreatedBy(rs.getString("created_by"));           
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
        return doList;
    }
    
    public DOList getDistinctDOListDetails() {
        String sql = "SELECT MIN(index_count) AS kira, gts_no, DATE_FORMAT(shipping_date,'%d/%m/%y %h:%i %p') AS shipping_date_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view "
                   + "FROM sr_do_list "
                   + "WHERE flag = '1' ";
        DOList doList = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doList = new DOList();
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setIndexCount(rs.getString("kira"));
                doList.setShipDate(rs.getString("shipping_date_view"));
                doList.setModifiedDate(rs.getString("modified_date_view")); 
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
        return doList;
    }
    
    public DOList getDOActualPerDoId(String id) {
        String sql = "SELECT * "
                   + "FROM sr_do_list "
                   + "WHERE id = '" + id  + "' ";
        DOList doList = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doList = new DOList();
                doList.setDoId(rs.getString("id"));
                doList.setReqId(rs.getString("req_id"));
                doList.setBoxId(rs.getString("box_id"));
                doList.setReqType(rs.getString("req_type"));
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setShipDate(rs.getString("shipping_date"));
                doList.setTotalUnits(rs.getString("total_units"));
                doList.setTotalWeight(rs.getString("total_weight"));
                doList.setTotalBox(rs.getString("total_box"));
                doList.setUnitPrice(rs.getString("unit_price"));
                doList.setEvent(rs.getString("event"));
                doList.setPkgFamily(rs.getString("pkg_family"));
                doList.setMthToScrap(rs.getString("mth_to_scrap"));
                doList.setStatus(rs.getString("status"));
                doList.setFlag(rs.getString("flag"));
                doList.setModifiedBy(rs.getString("modified_by"));
                doList.setModifiedDate(rs.getString("modified_date")); 
                doList.setCreatedDate(rs.getString("created_date"));
                doList.setCreatedBy(rs.getString("created_by"));           
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
        return doList;
    }
    
    public List<DOList> getAllDOListDisplay(String flag) {
        String sql = "SELECT *, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, "
                   + "DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view, DATE_FORMAT(shipping_date,'%d/%m/%y %h:%i %p') AS shipping_date_view "
                   + "FROM sr_do_list "
                   + "WHERE flag = '" + flag + "' ";
        List<DOList> doActualList = new ArrayList<DOList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DOList doList ;
            while (rs.next()) {
                doList = new DOList();
                doList.setDoId(rs.getString("id"));
                doList.setReqId(rs.getString("req_id"));
                doList.setBoxId(rs.getString("box_id"));
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setReqType(rs.getString("req_type"));
                doList.setShipDate(rs.getString("shipping_date_view"));
                doList.setTotalUnits(rs.getString("total_units"));
                doList.setTotalWeight(rs.getString("total_weight"));
                doList.setUnitPrice(rs.getString("unit_price"));
                doList.setTotalBox(rs.getString("total_box"));
                doList.setEvent(rs.getString("event"));
                doList.setPkgFamily(rs.getString("pkg_family"));
                doList.setMthToScrap(rs.getString("mth_to_scrap_view"));
                doList.setStatus(rs.getString("status"));
                doList.setFlag(rs.getString("flag"));
                doList.setModifiedBy(rs.getString("modified_by"));
                doList.setModifiedDate(rs.getString("modified_date_view")); 
                doList.setCreatedDate(rs.getString("created_date_view"));
                doList.setCreatedBy(rs.getString("created_by"));
                doActualList.add(doList);                
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
        return doActualList;
    }

    public Integer getCountOpenDO() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(id) AS count FROM sr_do_list " +
                "WHERE flag = '0' OR flag = '1' "
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
    
    public Integer getCountVerifiedDO() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(id) AS count FROM sr_do_list " +
                "WHERE flag = '1' "
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
    
    public Integer getCountOpenDetails() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(id) AS count FROM sr_do_list " +
                "WHERE flag = '0' "
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
    
    public Integer getMaxIndexCount() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT MAX(index_count) AS count "
              + "FROM sr_do_list "
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
    
    public Integer getCheckMinIndexCountStatus() {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT IFNULL(MIN(index_count),'0') AS count, flag " 
                + "FROM sr_do_list " 
                + "WHERE flag = 1 AND (SELECT COUNT(id) FROM sr_do_list WHERE flag = 0) != 0 "
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
    
    public QueryResult updateDoStatus(DOList doList) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_do_list "
                   + "SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? "
                   + "WHERE flag = 1 ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, doList.getStatus());
            ps.setString(2, doList.getFlag());
            ps.setString(3, doList.getModifiedBy());
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
    
    public QueryResult updateDoShipStatus(DOList doList) {
        QueryResult queryResult = new QueryResult();
        String sql = "UPDATE sr_do_list "
                   + "SET status = ?, flag = ?, modified_date = NOW(), modified_by = ? "
                   + "WHERE flag = 1 ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, doList.getStatus());
            ps.setString(2, doList.getFlag());
            ps.setString(3, doList.getModifiedBy());
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

//    public List<UserEmail> getEmailAll() {
//        String sql = "SELECT * "
//                   + "FROM user_group g, user_ldap p "
//                   + "WHERE g.id = p.group_id AND (p.group_id = 1 OR p.group_id = 3 OR p.group_id = 6) ";
//        List<UserEmail> userEmailList = new ArrayList<UserEmail>();
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            UserEmail userEmail;
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                userEmail = new UserEmail();
//                userEmail.setId(rs.getString("p.id"));
//                userEmail.setLoginId(rs.getString("login_id"));
//                userEmail.setUserId(rs.getString("oncid"));
//                userEmail.setFullname(rs.getString("firstname") + " " + rs.getString("lastname"));
//                userEmail.setEmail(rs.getString("email"));
//                userEmail.setGroupId(rs.getString("p.group_id"));
//                userEmail.setIsActive(rs.getString("is_active"));
//                userEmail.setCreatedTime(rs.getString("p.created_time"));
//                userEmail.setModifiedBy(rs.getString("p.modified_by"));
//                userEmail.setModifiedTime(rs.getString("p.modified_time"));
//                userEmailList.add(userEmail);
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return userEmailList;
//    }
    
    public Integer getCountSameGtsNo(String gtsNo) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(id) AS count FROM sr_do_list " +
                "WHERE gts_no = '" + gtsNo + "' "
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
    
    public List<DOList> getAllGtsNo() {
        String sql = "SELECT DISTINCT(gts_no) "
                   + "FROM sr_do_list ";
        List<DOList> doActualList = new ArrayList<DOList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DOList doList ;
            while (rs.next()) {
                doList = new DOList();
                doList.setGtsNo(rs.getString("gts_no"));
                doActualList.add(doList);                
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
        return doActualList;
    }
    
    public List<DOList> getAllListPerGtsNo(String gtsNo) {
        String sql = "SELECT *, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, "
                   + "DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view, DATE_FORMAT(shipping_date,'%d/%m/%y %h:%i %p') AS shipping_date_view "
                   + "FROM sr_do_list "
                   + "WHERE gts_no = '" + gtsNo + "' ";
        List<DOList> doActualList = new ArrayList<DOList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DOList doList ;
            while (rs.next()) {
                doList = new DOList();
                doList.setDoId(rs.getString("id"));
                doList.setReqId(rs.getString("req_id"));
                doList.setBoxId(rs.getString("box_id"));
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setReqType(rs.getString("req_type"));
                doList.setShipDate(rs.getString("shipping_date_view"));
                doList.setTotalUnits(rs.getString("total_units"));
                doList.setTotalWeight(rs.getString("total_weight"));
                doList.setUnitPrice(rs.getString("unit_price"));
                doList.setTotalBox(rs.getString("total_box"));
                doList.setEvent(rs.getString("event"));
                doList.setPkgFamily(rs.getString("pkg_family"));
                doList.setMthToScrap(rs.getString("mth_to_scrap_view"));
                doList.setStatus(rs.getString("status"));
                doList.setFlag(rs.getString("flag"));
                doList.setModifiedBy(rs.getString("modified_by"));
                doList.setModifiedDate(rs.getString("modified_date_view")); 
                doList.setCreatedDate(rs.getString("created_date_view"));
                doList.setCreatedBy(rs.getString("created_by"));
                doActualList.add(doList);                
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
        return doActualList;
    }
    
    public List<DOList> getAllDOListDisplayPerGts(String gtsNo) {
        String sql = "SELECT *, UPPER(DATE_FORMAT(mth_to_scrap,'%b %y')) AS mth_to_scrap_view, DATE_FORMAT(modified_date,'%d/%m/%y %h:%i %p') AS modified_date_view, "
                   + "DATE_FORMAT(created_date,'%d/%m/%y %h:%i %p') AS created_date_view, DATE_FORMAT(shipping_date,'%d/%m/%y %h:%i %p') AS shipping_date_view "
                   + "FROM sr_do_list "
                   + "WHERE gts_no = '" + gtsNo + "' ";
        List<DOList> doActualList = new ArrayList<DOList>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DOList doList ;
            while (rs.next()) {
                doList = new DOList();
                doList.setDoId(rs.getString("id"));
                doList.setReqId(rs.getString("req_id"));
                doList.setBoxId(rs.getString("box_id"));
                doList.setGtsNo(rs.getString("gts_no"));
                doList.setReqType(rs.getString("req_type"));
                doList.setShipDate(rs.getString("shipping_date_view"));
                doList.setTotalUnits(rs.getString("total_units"));
                doList.setTotalWeight(rs.getString("total_weight"));
                doList.setUnitPrice(rs.getString("unit_price"));
                doList.setTotalBox(rs.getString("total_box"));
                doList.setEvent(rs.getString("event"));
                doList.setPkgFamily(rs.getString("pkg_family"));
                doList.setMthToScrap(rs.getString("mth_to_scrap_view"));
                doList.setStatus(rs.getString("status"));
                doList.setFlag(rs.getString("flag"));
                doList.setModifiedBy(rs.getString("modified_by"));
                doList.setModifiedDate(rs.getString("modified_date_view")); 
                doList.setCreatedDate(rs.getString("created_date_view"));
                doList.setCreatedBy(rs.getString("created_by"));
                doActualList.add(doList);                
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
        return doActualList;
    }
    
    public QueryResult deleteReq(String reqId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM sr_do_list WHERE req_id = '" + reqId + "'"
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
    
    public Integer getCountExistingRequest(String reqId) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(id) AS count FROM sr_do_list " +
                "WHERE req_id = '" + reqId + "' "
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