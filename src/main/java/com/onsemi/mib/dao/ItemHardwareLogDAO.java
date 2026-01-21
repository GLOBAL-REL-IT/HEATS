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
import com.onsemi.mib.model.ItemHardwareLog;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemHardwareLogDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemHardwareLogDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public ItemHardwareLogDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertItemHardwareLog(ItemHardwareLog itemhardwareLog) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO item_hardware_log (mib_hardware_id, rms_event, alu, created_by, created_date) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, itemhardwareLog.getMibHardwareId());
			ps.setString(2, itemhardwareLog.getRmsEvent());
			ps.setString(3, itemhardwareLog.getAlu());
			ps.setString(4, itemhardwareLog.getCreatedBy());
			ps.setString(5, itemhardwareLog.getCreatedDate());
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

	public QueryResult updateItemHardwareLog(ItemHardwareLog itemhardwareLog) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE item_hardware_log SET mib_hardware_id = ?, rms_event = ?, alu = ?, created_by = ?, created_date = ? WHERE id = ?"
			);
			ps.setString(1, itemhardwareLog.getMibHardwareId());
			ps.setString(2, itemhardwareLog.getRmsEvent());
			ps.setString(3, itemhardwareLog.getAlu());
			ps.setString(4, itemhardwareLog.getCreatedBy());
			ps.setString(5, itemhardwareLog.getCreatedDate());
			ps.setString(6, itemhardwareLog.getId());
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

	public QueryResult deleteItemHardwareLog(String itemhardwareLogId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM item_hardware_log WHERE id = '" + itemhardwareLogId + "'"
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

	public ItemHardwareLog getItemHardwareLog(String itemhardwareLogId) {
		String sql = "SELECT * FROM item_hardware_log WHERE id = '" + itemhardwareLogId + "'";
		ItemHardwareLog itemhardwareLog = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardwareLog = new ItemHardwareLog();
				itemhardwareLog.setId(rs.getString("id"));
				itemhardwareLog.setMibHardwareId(rs.getString("mib_hardware_id"));
				itemhardwareLog.setRmsEvent(rs.getString("rms_event"));
				itemhardwareLog.setAlu(rs.getString("alu"));
				itemhardwareLog.setCreatedBy(rs.getString("created_by"));
				itemhardwareLog.setCreatedDate(rs.getString("created_date"));
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
		return itemhardwareLog;
	}

	public List<ItemHardwareLog> getItemHardwareLogList() {
		String sql = "SELECT * FROM item_hardware_log ORDER BY id ASC";
		List<ItemHardwareLog> itemhardwareLogList = new ArrayList<ItemHardwareLog>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ItemHardwareLog itemhardwareLog;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardwareLog = new ItemHardwareLog();
				itemhardwareLog.setId(rs.getString("id"));
				itemhardwareLog.setMibHardwareId(rs.getString("mib_hardware_id"));
				itemhardwareLog.setRmsEvent(rs.getString("rms_event"));
				itemhardwareLog.setAlu(rs.getString("alu"));
				itemhardwareLog.setCreatedBy(rs.getString("created_by"));
				itemhardwareLog.setCreatedDate(rs.getString("created_date"));
				itemhardwareLogList.add(itemhardwareLog);
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
		return itemhardwareLogList;
	}
}