package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.SRKpi;
import com.onsemi.mib.model.SRRetrieve;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRKpiDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SRKpiDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public SRKpiDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }

    public Integer getGoal(String requirement) {
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT cycle_time_goal_day FROM sr_kpi_requirement WHERE title = ? "
            );
            ps.setString(1, requirement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("cycle_time_goal_day");
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

    public List<SRKpi> getAllScrapDataPerMth() {
        String sql = "SELECT COUNT(DISTINCT(req_id)) AS count, DATE_FORMAT(created_date,'%b-%y')  as dateMonth, DATE_FORMAT(created_date,'%b-%Y') AS mthyr_req, "
                    + "DATE_FORMAT(created_date,'%m') AS mth_req, DATE_FORMAT(created_date,'%Y') AS yr_req "
                    + "FROM sr_retrieve "
                    + "WHERE req_type = 'Auto Recall from Sendayan' AND TIMESTAMPDIFF(MONTH, created_date, NOW()) <= 12 "
                    + "GROUP BY DATE_FORMAT(created_date,'%b-%y') "
                    + "ORDER BY YEAR(created_date) DESC, MONTH(created_date) DESC ";
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

    public List<SRRetrieve> getAllScrapData() {
        String sql = "SELECT *, GROUP_CONCAT(I.lot ORDER BY I.lot ASC SEPARATOR ', ') AS lot_concat, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS created_date_view, "
                    + "IF(rl_received_date IS NULL, 'Pending', DATE_FORMAT(rl_received_date,'%d/%m/%y %h:%i %p')) AS received_date_view, UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                    + "DATEDIFF(R.created_date,mth_to_scrap) AS cycle_time_1, "
                    + "IF(ship_date IS NULL, DATEDIFF(NOW(),R.created_date), DATEDIFF(ship_date,R.created_date)) AS cycle_time_2, "
                    + "IF(ship_date IS NULL, 'Pending', DATE_FORMAT(ship_date,'%d/%m/%y %h:%i %p')) AS ship_date_view "
                    + "FROM sr_retrieve R, sr_req_inner I "
                    + "WHERE R.req_id = I.req_id AND req_details = 'Recall for Scrap' AND PERIOD_DIFF(DATE_FORMAT(NOW(),'%Y%m'),DATE_FORMAT(R.created_date,'%Y%m'))<=12 "
                    + "GROUP BY I.rms_no, I.event, R.id "
                    + "ORDER BY R.mth_to_scrap DESC ";
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
                sampRetrieve.setRmsNo(rs.getString("rms_no"));
                sampRetrieve.setEvent(rs.getString("R.event"));
                sampRetrieve.setMthToScrap(rs.getString("mth_to_scrap_view"));
                sampRetrieve.setPkgFamily(rs.getString("R.pkg_family"));
                sampRetrieve.setCreatedDate(rs.getString("created_date_view"));
                sampRetrieve.setShipDate(rs.getString("ship_date_view"));
                sampRetrieve.setCreatedBy(rs.getString("R.created_by"));
                sampRetrieve.setRlReceivedDate(rs.getString("received_date_view"));
                sampRetrieve.setRlReceivedBy(rs.getString("R.rl_received_by"));
                sampRetrieve.setFlag(rs.getString("R.flag"));
                sampRetrieve.setStatus(rs.getString("R.status"));
                sampRetrieve.setLotConcat(rs.getString("lot_concat"));
                sampRetrieve.setCycleTime1(rs.getString("cycle_time_1"));
                sampRetrieve.setCycleTime2(rs.getString("cycle_time_2"));
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

    public List<SRKpi> getMthToScrapVsReqDateData(String mthToScrapVsReqGoal, String reqVsShipGoal) {
        String sql = "SELECT COUNT(*) as total_req, DATE_FORMAT(created_date,'%b-%y') AS mthyr_req, "
                    + "COUNT(IF(DATEDIFF(created_date,mth_to_scrap)<=?,1,NULL)) AS req_pass, "
                    + "COUNT(IF(DATEDIFF(created_date,mth_to_scrap)>?,1,NULL)) AS req_fail, "
                    + "ROUND(COUNT(IF(DATEDIFF(created_date,mth_to_scrap)<=?,1,NULL))/COUNT(*)*100,1) AS req_percent, "
                    + "COUNT(IF(DATEDIFF(IF(ship_date IS NULL, NOW(),ship_date),mth_to_scrap)<=?,1,NULL)) AS ship_pass, "
                    + "COUNT(IF(DATEDIFF(IF(ship_date IS NULL, NOW(),ship_date),mth_to_scrap)>?,1,NULL)) AS ship_fail, "
                    + "ROUND(COUNT(IF(DATEDIFF(IF(ship_date IS NULL, NOW(),ship_date),created_date)<=?,1,NULL))/COUNT(*)*100,1) AS ship_percent "
                    + "FROM sr_retrieve "
                    + "WHERE req_details = 'Recall for Scrap' AND PERIOD_DIFF(DATE_FORMAT(NOW(),'%Y%m'),DATE_FORMAT(created_date,'%Y%m')) <= 12 "
                    + "GROUP BY DATE_FORMAT(created_date,'%Y%m') "
                    + "ORDER BY DATE_FORMAT(created_date,'%Y%m') DESC ";
        List<SRKpi> srKpiList = new ArrayList<SRKpi>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mthToScrapVsReqGoal);
            ps.setString(2, mthToScrapVsReqGoal);
            ps.setString(3, mthToScrapVsReqGoal);
            ps.setString(4, reqVsShipGoal);
            ps.setString(5, reqVsShipGoal);
            ps.setString(6, reqVsShipGoal);
            SRKpi srKpi;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srKpi = new SRKpi();
                srKpi.setScrapCount(rs.getString("total_req"));
                srKpi.setScrapMthYrReq(rs.getString("mthyr_req"));
                srKpi.setMthToScrapVsReqPass(rs.getString("req_pass"));
                srKpi.setMthToScrapVsReqFail(rs.getString("req_fail"));
                srKpi.setMthToScrapVsReqPercent(rs.getString("req_percent"));
                srKpi.setReqVsShipDatePass(rs.getString("ship_pass"));
                srKpi.setReqVsShipDateFail(rs.getString("ship_fail"));
                srKpi.setReqVsShipDatePercent(rs.getString("ship_percent"));
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

    //retrieve
    public List<SRKpi> getAllRetrieveDataPerMth() {
        String sql = "SELECT COUNT(*) AS count, DATE_FORMAT(created_date,'%b-%y') AS date_request, DATE_FORMAT(created_date,'%b-%Y') AS mthyr_req, "
                    + "DATE_FORMAT(created_date,'%m') AS mth_req, DATE_FORMAT(created_date,'%Y') AS yr_req "
                    + "FROM sr_retrieve "
                    + "WHERE req_type != 'Auto Recall from Sendayan' AND TIMESTAMPDIFF(MONTH, created_date, NOW()) <= 12 "
                    + "GROUP BY DATE_FORMAT(created_date,'%b-%y') "
                    + "ORDER BY created_date DESC ";
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

    public List<SRKpi> getAllRetrieveData() {
        String sql = "SELECT *, GROUP_CONCAT(I.lot ORDER BY I.lot ASC SEPARATOR ', ') AS lot_concat, DATE_FORMAT(R.created_date,'%d/%m/%y %h:%i %p') AS req_date_view, "
                    + "IF(rl_received_date IS NULL, 'Pending', DATE_FORMAT(rl_received_date,'%d/%m/%y %h:%i %p')) AS received_date_view, "
                    + "UPPER(DATE_FORMAT(R.mth_to_scrap,'%b %y')) AS mth_to_scrap_view, "
                    + "IF(ship_date IS NULL, DATEDIFF(NOW(),R.created_date), DATEDIFF(ship_date,R.created_date)) AS cycle_time_1, "
                    + "IF(rl_received_date IS NULL, DATEDIFF(NOW(),ship_date), DATEDIFF(rl_received_date,ship_date)) AS cycle_time_2, "
                    + "IF(ship_date IS NULL, 'Pending', DATE_FORMAT(ship_date,'%d/%m/%y %h:%i %p')) AS ship_date_view, "
                    + "IF(requestor_name IS NULL, R.created_by, CONCAT(R.created_by, ' on behalf of ', requestor_name)) AS req_by,"
                    + "IF(R.req_remarks IS NULL, R.req_details, CONCAT(R.req_details, ' (', R.req_remarks,')')) AS reason_recall "
                    + "FROM sr_retrieve R, sr_req_inner I "
                    + "WHERE R.req_id = I.req_id AND req_details != 'Recall for Scrap' AND PERIOD_DIFF(DATE_FORMAT(NOW(),'%Y%m'),DATE_FORMAT(R.created_date,'%Y%m'))<=12 "
                    + "GROUP BY I.rms_no, I.event, R.id "
                    + "ORDER BY DATE_FORMAT(R.created_date,'%Y%m') DESC ";
        List<SRKpi> reqList = new ArrayList<SRKpi>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            SRKpi srKpi;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srKpi = new SRKpi();
                srKpi.setId(rs.getString("R.id"));
                srKpi.setReqId(rs.getString("R.req_id"));
                srKpi.setBoxId(rs.getString("R.box_id"));
                srKpi.setReqType(rs.getString("R.req_type"));
                srKpi.setReqDetails(rs.getString("R.req_details"));
                srKpi.setReqRemarks(rs.getString("R.req_remarks"));
                srKpi.setReasonRecall(rs.getString("reason_recall"));
                srKpi.setRmsNo(rs.getString("rms_no"));
                srKpi.setEvent(rs.getString("R.event"));
                srKpi.setMthToScrap(rs.getString("mth_to_scrap_view"));
                srKpi.setPkgFamily(rs.getString("R.pkg_family"));
                srKpi.setRequestDate(rs.getString("req_date_view"));
                srKpi.setRequestBy(rs.getString("req_by"));
                srKpi.setShipDate(rs.getString("ship_date_view"));
//                srKpi.setCreatedBy(rs.getString("R.created_by"));
                srKpi.setRelReceivedDate(rs.getString("received_date_view"));
                srKpi.setRelReceivedBy(rs.getString("R.rl_received_by"));
                srKpi.setFlag(rs.getString("R.flag"));
                srKpi.setStatus(rs.getString("R.status"));
                srKpi.setLotConcat(rs.getString("lot_concat"));
                srKpi.setCycleTime1(rs.getString("cycle_time_1"));
                srKpi.setCycleTime2(rs.getString("cycle_time_2"));
                reqList.add(srKpi);
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

    //activity
    public List<SRKpi> getActivityReqDateVSShipDateData(String reqVsShipGoal) {
        String sql = "SELECT COUNT(*) as total_req, DATE_FORMAT(created_date,'%b-%y') AS mthyr_req, "
                + "COUNT(IF(DATEDIFF(ship_date,created_date)<=?,1,NULL)) AS req_pass, "
                + "COUNT(IF(DATEDIFF(ship_date,created_date)>?,1,NULL)) AS req_fail, "
                + "ROUND(COUNT(IF(DATEDIFF(ship_date,created_date)<=?,1,NULL))/COUNT(*)*100,1) AS req_percent "
                + "FROM sr_retrieve "
                + "WHERE req_details != 'Recall for Scrap' AND PERIOD_DIFF(DATE_FORMAT(NOW(),'%Y%m'),DATE_FORMAT(created_date,'%Y%m')) <= 12 "
                + "GROUP BY DATE_FORMAT(created_date,'%Y%m') "
                + "ORDER BY DATE_FORMAT(created_date,'%Y%m') DESC ";
        List<SRKpi> srKpiList = new ArrayList<SRKpi>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reqVsShipGoal);
            ps.setString(2, reqVsShipGoal);
            ps.setString(3, reqVsShipGoal);
            SRKpi srKpi;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srKpi = new SRKpi();
                srKpi.setActivityCount(rs.getString("total_req"));
                srKpi.setActivityMthYrReq(rs.getString("mthyr_req"));
                srKpi.setActivityReqVsShipDatePass(rs.getString("req_pass"));
                srKpi.setActivityReqVsShipDateFail(rs.getString("req_fail"));
                srKpi.setActivityReqVsShipDatePercent(rs.getString("req_percent"));
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

}