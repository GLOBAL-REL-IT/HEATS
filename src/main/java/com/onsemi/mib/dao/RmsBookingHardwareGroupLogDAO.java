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
import com.onsemi.mib.model.RmsBookingHardwareGroupLog;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RmsBookingHardwareGroupLogDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingHardwareGroupLogDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public RmsBookingHardwareGroupLogDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertRmsBookingHardwareGroupLog(RmsBookingHardwareGroupLog rmsbookingHardwareGroupLog) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO rms_booking_hardware_group_log (group_id, detail, created_by, created_date) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, rmsbookingHardwareGroupLog.getGroupId());
			ps.setString(2, rmsbookingHardwareGroupLog.getDetail());
			ps.setString(3, rmsbookingHardwareGroupLog.getCreatedBy());
			ps.setString(4, rmsbookingHardwareGroupLog.getCreatedDate());
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

	public QueryResult updateRmsBookingHardwareGroupLog(RmsBookingHardwareGroupLog rmsbookingHardwareGroupLog) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE rms_booking_hardware_group_log SET group_id = ?, detail = ?, created_by = ?, created_date = ? WHERE id = ?"
			);
			ps.setString(1, rmsbookingHardwareGroupLog.getGroupId());
			ps.setString(2, rmsbookingHardwareGroupLog.getDetail());
			ps.setString(3, rmsbookingHardwareGroupLog.getCreatedBy());
			ps.setString(4, rmsbookingHardwareGroupLog.getCreatedDate());
			ps.setString(5, rmsbookingHardwareGroupLog.getId());
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

	public QueryResult deleteRmsBookingHardwareGroupLog(String rmsbookingHardwareGroupLogId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM rms_booking_hardware_group_log WHERE id = '" + rmsbookingHardwareGroupLogId + "'"
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

	public RmsBookingHardwareGroupLog getRmsBookingHardwareGroupLog(String rmsbookingHardwareGroupLogId) {
		String sql = "SELECT * FROM rms_booking_hardware_group_log WHERE id = '" + rmsbookingHardwareGroupLogId + "'";
		RmsBookingHardwareGroupLog rmsbookingHardwareGroupLog = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rmsbookingHardwareGroupLog = new RmsBookingHardwareGroupLog();
				rmsbookingHardwareGroupLog.setId(rs.getString("id"));
				rmsbookingHardwareGroupLog.setGroupId(rs.getString("group_id"));
				rmsbookingHardwareGroupLog.setDetail(rs.getString("detail"));
				rmsbookingHardwareGroupLog.setCreatedBy(rs.getString("created_by"));
				rmsbookingHardwareGroupLog.setCreatedDate(rs.getString("created_date"));
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
		return rmsbookingHardwareGroupLog;
	}

	public List<RmsBookingHardwareGroupLog> getRmsBookingHardwareGroupLogList() {
		String sql = "SELECT * FROM rms_booking_hardware_group_log ORDER BY id ASC";
		List<RmsBookingHardwareGroupLog> rmsbookingHardwareGroupLogList = new ArrayList<RmsBookingHardwareGroupLog>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			RmsBookingHardwareGroupLog rmsbookingHardwareGroupLog;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rmsbookingHardwareGroupLog = new RmsBookingHardwareGroupLog();
				rmsbookingHardwareGroupLog.setId(rs.getString("id"));
				rmsbookingHardwareGroupLog.setGroupId(rs.getString("group_id"));
				rmsbookingHardwareGroupLog.setDetail(rs.getString("detail"));
				rmsbookingHardwareGroupLog.setCreatedBy(rs.getString("created_by"));
				rmsbookingHardwareGroupLog.setCreatedDate(rs.getString("created_date"));
				rmsbookingHardwareGroupLogList.add(rmsbookingHardwareGroupLog);
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
		return rmsbookingHardwareGroupLogList;
	}
}