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
import com.onsemi.mib.model.ItemHardware;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemHardwareDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemHardwareDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public ItemHardwareDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertItemHardware(ItemHardware itemhardware) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO item_hardware (mib_item_id, spts_pkid, hardware_id, alu, status, rms_event, shelf_time, created_by, created_date, verify_by, verify_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, itemhardware.getMibItemId());
			ps.setString(2, itemhardware.getSptsPkid());
			ps.setString(3, itemhardware.getHardwareId());
			ps.setString(4, itemhardware.getAlu());
			ps.setString(5, itemhardware.getStatus());
			ps.setString(6, itemhardware.getRmsEvent());
			ps.setString(7, itemhardware.getShelfTime());
			ps.setString(8, itemhardware.getCreatedBy());
			ps.setString(9, itemhardware.getCreatedDate());
			ps.setString(10, itemhardware.getVerifyBy());
			ps.setString(11, itemhardware.getVerifyDate());
			ps.setString(12, itemhardware.getFlag());
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

	public QueryResult updateItemHardware(ItemHardware itemhardware) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE item_hardware SET mib_item_id = ?, spts_pkid = ?, hardware_id = ?, alu = ?, status = ?, rms_event = ?, shelf_time = ?, created_by = ?, created_date = ?, verify_by = ?, verify_date = ?, flag = ? WHERE id = ?"
			);
			ps.setString(1, itemhardware.getMibItemId());
			ps.setString(2, itemhardware.getSptsPkid());
			ps.setString(3, itemhardware.getHardwareId());
			ps.setString(4, itemhardware.getAlu());
			ps.setString(5, itemhardware.getStatus());
			ps.setString(6, itemhardware.getRmsEvent());
			ps.setString(7, itemhardware.getShelfTime());
			ps.setString(8, itemhardware.getCreatedBy());
			ps.setString(9, itemhardware.getCreatedDate());
			ps.setString(10, itemhardware.getVerifyBy());
			ps.setString(11, itemhardware.getVerifyDate());
			ps.setString(12, itemhardware.getFlag());
			ps.setString(13, itemhardware.getId());
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

	public QueryResult deleteItemHardware(String itemhardwareId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM item_hardware WHERE id = '" + itemhardwareId + "'"
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

	public ItemHardware getItemHardware(String itemhardwareId) {
		String sql = "SELECT * FROM item_hardware WHERE id = '" + itemhardwareId + "'";
		ItemHardware itemhardware = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardware = new ItemHardware();
				itemhardware.setId(rs.getString("id"));
				itemhardware.setMibItemId(rs.getString("mib_item_id"));
				itemhardware.setSptsPkid(rs.getString("spts_pkid"));
				itemhardware.setHardwareId(rs.getString("hardware_id"));
				itemhardware.setAlu(rs.getString("alu"));
				itemhardware.setStatus(rs.getString("status"));
				itemhardware.setRmsEvent(rs.getString("rms_event"));
				itemhardware.setShelfTime(rs.getString("shelf_time"));
				itemhardware.setCreatedBy(rs.getString("created_by"));
				itemhardware.setCreatedDate(rs.getString("created_date"));
				itemhardware.setVerifyBy(rs.getString("verify_by"));
				itemhardware.setVerifyDate(rs.getString("verify_date"));
				itemhardware.setFlag(rs.getString("flag"));
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
		return itemhardware;
	}

	public List<ItemHardware> getItemHardwareList() {
		String sql = "SELECT * FROM item_hardware ORDER BY id ASC";
		List<ItemHardware> itemhardwareList = new ArrayList<ItemHardware>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ItemHardware itemhardware;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardware = new ItemHardware();
				itemhardware.setId(rs.getString("id"));
				itemhardware.setMibItemId(rs.getString("mib_item_id"));
				itemhardware.setSptsPkid(rs.getString("spts_pkid"));
				itemhardware.setHardwareId(rs.getString("hardware_id"));
				itemhardware.setAlu(rs.getString("alu"));
				itemhardware.setStatus(rs.getString("status"));
				itemhardware.setRmsEvent(rs.getString("rms_event"));
				itemhardware.setShelfTime(rs.getString("shelf_time"));
				itemhardware.setCreatedBy(rs.getString("created_by"));
				itemhardware.setCreatedDate(rs.getString("created_date"));
				itemhardware.setVerifyBy(rs.getString("verify_by"));
				itemhardware.setVerifyDate(rs.getString("verify_date"));
				itemhardware.setFlag(rs.getString("flag"));
				itemhardwareList.add(itemhardware);
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
		return itemhardwareList;
	}
}