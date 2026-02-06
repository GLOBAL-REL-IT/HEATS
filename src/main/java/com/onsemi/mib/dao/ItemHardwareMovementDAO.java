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
import com.onsemi.mib.model.ItemHardwareMovement;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemHardwareMovementDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemHardwareMovementDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public ItemHardwareMovementDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertItemHardwareMovement(ItemHardwareMovement itemhardwareMovement) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO item_hardware_movement (mib_hardware_id, trans_type, rms_event, alu, created_by, created_date) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, itemhardwareMovement.getMibHardwareId());
			ps.setString(2, itemhardwareMovement.getTransType());
			ps.setString(3, itemhardwareMovement.getRmsEvent());
			ps.setString(4, itemhardwareMovement.getAlu());
			ps.setString(5, itemhardwareMovement.getCreatedBy());
			ps.setString(6, itemhardwareMovement.getCreatedDate());
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

	public QueryResult updateItemHardwareMovement(ItemHardwareMovement itemhardwareMovement) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE item_hardware_movement SET mib_hardware_id = ?, trans_type = ?, rms_event = ?, alu = ?, created_by = ?, created_date = ? WHERE id = ?"
			);
			ps.setString(1, itemhardwareMovement.getMibHardwareId());
			ps.setString(2, itemhardwareMovement.getTransType());
			ps.setString(3, itemhardwareMovement.getRmsEvent());
			ps.setString(4, itemhardwareMovement.getAlu());
			ps.setString(5, itemhardwareMovement.getCreatedBy());
			ps.setString(6, itemhardwareMovement.getCreatedDate());
			ps.setString(7, itemhardwareMovement.getId());
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

	public QueryResult deleteItemHardwareMovement(String itemhardwareMovementId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM item_hardware_movement WHERE id = '" + itemhardwareMovementId + "'"
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

	public ItemHardwareMovement getItemHardwareMovement(String itemhardwareMovementId) {
		String sql = "SELECT * FROM item_hardware_movement WHERE id = '" + itemhardwareMovementId + "'";
		ItemHardwareMovement itemhardwareMovement = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardwareMovement = new ItemHardwareMovement();
				itemhardwareMovement.setId(rs.getString("id"));
				itemhardwareMovement.setMibHardwareId(rs.getString("mib_hardware_id"));
				itemhardwareMovement.setTransType(rs.getString("trans_type"));
				itemhardwareMovement.setRmsEvent(rs.getString("rms_event"));
				itemhardwareMovement.setAlu(rs.getString("alu"));
				itemhardwareMovement.setCreatedBy(rs.getString("created_by"));
				itemhardwareMovement.setCreatedDate(rs.getString("created_date"));
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
		return itemhardwareMovement;
	}

	public List<ItemHardwareMovement> getItemHardwareMovementList() {
		String sql = "SELECT * FROM item_hardware_movement ORDER BY id ASC";
		List<ItemHardwareMovement> itemhardwareMovementList = new ArrayList<ItemHardwareMovement>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ItemHardwareMovement itemhardwareMovement;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardwareMovement = new ItemHardwareMovement();
				itemhardwareMovement.setId(rs.getString("id"));
				itemhardwareMovement.setMibHardwareId(rs.getString("mib_hardware_id"));
				itemhardwareMovement.setTransType(rs.getString("trans_type"));
				itemhardwareMovement.setRmsEvent(rs.getString("rms_event"));
				itemhardwareMovement.setAlu(rs.getString("alu"));
				itemhardwareMovement.setCreatedBy(rs.getString("created_by"));
				itemhardwareMovement.setCreatedDate(rs.getString("created_date"));
				itemhardwareMovementList.add(itemhardwareMovement);
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
		return itemhardwareMovementList;
	}
}