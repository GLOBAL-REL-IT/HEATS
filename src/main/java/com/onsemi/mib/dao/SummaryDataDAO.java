package com.onsemi.mib.dao;

import com.onsemi.mib.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.onsemi.mib.model.SummaryData;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummaryDataDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryDataDAO.class);
//    private final Connection conn;
    private final DataSource dataSource;

    public SummaryDataDAO() {
        DB db = new DB();
//        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
    }
    
    private static final String SQL_GET_ALL_NO_RETENTION_SUMMARY_DATA = "SELECT pkg_family, rms_event, COUNT(id) AS total_lot FROM sr_ftp_data WHERE flag = 0 AND ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) GROUP BY pkg_family, rms_event ORDER BY pkg_family, rms_event"; 
    private static final String SQL_GET_ALL_RENTENTION_SUMMARY_DATA = "SELECT pkg_family, rms_event, COUNT(id) AS total_lot FROM sr_ftp_data WHERE flag = 0 AND status NOT LIKE '%Cancelled Lot' AND ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) GROUP BY pkg_family, rms_event ORDER BY pkg_family, rms_event";
    private static final String SQL_GET_MONTH_TO_SCRAP = "SELECT DISTINCT(UPPER(DATE_FORMAT(mth_to_scrap,'%M %Y'))) AS mth_to_scrap_view, mth_to_scrap, pkg_family, rms_event FROM sr_ftp_data WHERE pkg_family = ? AND (rms_event LIKE ? OR rms_event = ?) AND flag = 0 AND CASE WHEN ASCII(RIGHT(rms_event,1)) BETWEEN ASCII('A') AND ASCII('Z') THEN 1 ELSE 0 END AND ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) ORDER BY mth_to_scrap"; 
    private static final String SQL_GET_MONTH_TO_SCRAP_FILTER = "SELECT DISTINCT(UPPER(DATE_FORMAT(mth_to_scrap,'%M %Y'))) AS mth_to_scrap_view, mth_to_scrap, pkg_family, rms_event FROM sr_ftp_data WHERE pkg_family = ? AND (rms_event LIKE ? OR rms_event = ?) AND flag = 0 AND ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) GROUP BY mth_to_scrap_view ORDER BY mth_to_scrap";
    private static final String SQL_GET_COUNT_TOTAL_LOT_PER_MONTH = "SELECT DATE_FORMAT(mth_to_scrap,'%b ''%y') AS mth_to_scrap_view, COUNT(id) AS total_lot FROM sr_ftp_data WHERE (rms_event = ? OR rms_event LIKE ?) AND pkg_family = ? AND flag = 0 AND ((YEAR(mth_to_scrap) > YEAR(NOW())) OR (MONTH(mth_to_scrap) > MONTH(NOW()) AND YEAR(mth_to_scrap) = YEAR(NOW()))) GROUP BY mth_to_scrap ORDER BY mth_to_scrap";

    public List<SummaryData> getAllNoRetentionSummaryData() {
        List<SummaryData> summDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_NO_RETENTION_SUMMARY_DATA); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SummaryData summaryData = new SummaryData();
                summaryData.setPkgFamily(rs.getString("pkg_family"));
                summaryData.setEvent(rs.getString("rms_event"));
                summaryData.setTotalLot(rs.getString("total_lot"));
                summDataList.add(summaryData);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllNoRetentionSummaryData", e);
        }
        return summDataList;
    }

    public List<SummaryData> getAllRetentionSummaryData() {
        List<SummaryData> summDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_RENTENTION_SUMMARY_DATA); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SummaryData summaryData = new SummaryData();
                summaryData.setPkgFamily(rs.getString("pkg_family"));
                summaryData.setEvent(rs.getString("rms_event"));
                summaryData.setTotalLot(rs.getString("total_lot"));
                summDataList.add(summaryData);
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getAllRetentionSummaryData", e);
        }
        return summDataList;
    }

    public List<SummaryData> getMthToScrap(String pkgFamily, String event) {
        
        List<SummaryData> summDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MONTH_TO_SCRAP)) {
            ps.setString(1, pkgFamily);
            ps.setString(2, event + "_");
            ps.setString(3, event);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SummaryData summaryData = new SummaryData();
                    summaryData.setEvent(rs.getString("rms_event"));
                    summaryData.setPkgFamily(rs.getString("pkg_family"));
                    summaryData.setMthToScrap(rs.getString("mth_to_scrap"));
                    summaryData.setMthToScrapView(rs.getString("mth_to_scrap_view"));
                    summDataList.add(summaryData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getMthToScrap", e);
        }
        return summDataList;
    }

    public List<SummaryData> getMthToScrapFilter(String pkgFamily, String event) {
        List<SummaryData> summDataList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_MONTH_TO_SCRAP_FILTER)) {
            ps.setString(1, pkgFamily);
            ps.setString(2, event + "_");
            ps.setString(3, event);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SummaryData summaryData = new SummaryData();
                    summaryData.setEvent(rs.getString("rms_event"));
                    summaryData.setPkgFamily(rs.getString("pkg_family"));
                    summaryData.setMthToScrap(rs.getString("mth_to_scrap"));
                    summaryData.setMthToScrapView(rs.getString("mth_to_scrap_view"));
                    summDataList.add(summaryData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getMthToScrapFilter", e);
        }
        return summDataList;
    }

    public List<SummaryData> getCountTotalLotPerMth(String pkgFamily, String event) {
        List<SummaryData> summDataList = new ArrayList<>();
        String mthToScrap = "";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_GET_COUNT_TOTAL_LOT_PER_MONTH)) {
            ps.setString(1, event);
            ps.setString(2, event + "_");
            ps.setString(3, pkgFamily);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mthToScrap += rs.getString("mth_to_scrap_view") + " (" + rs.getString("total_lot") + "), ";
                    SummaryData summaryData = new SummaryData();
                    summaryData.setEvent(event);
                    summaryData.setPkgFamily(pkgFamily);
                    summaryData.setMthToScrap(mthToScrap);
                    summDataList.add(summaryData);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error in getCountTotalLotPerMth", e);
        }
        return summDataList;
    }

}