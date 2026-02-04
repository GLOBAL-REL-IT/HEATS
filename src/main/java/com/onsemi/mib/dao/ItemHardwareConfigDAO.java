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
import com.onsemi.mib.model.ItemHardwareConfig;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemHardwareConfigDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemHardwareConfigDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public ItemHardwareConfigDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertItemHardwareConfig(ItemHardwareConfig itemhardwareConfig) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO item_hardware_config (item_type, sub_type, same_item_id, supplier, assembly_no, revision, mfg_date, component, event, part_number, alu, shelf_time, created_date, created_by, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, itemhardwareConfig.getItemType());
			ps.setString(2, itemhardwareConfig.getSubType());
			ps.setString(3, itemhardwareConfig.getSameItemId());
			ps.setString(4, itemhardwareConfig.getSupplier());
			ps.setString(5, itemhardwareConfig.getAssemblyNo());
			ps.setString(6, itemhardwareConfig.getRevision());
			ps.setString(7, itemhardwareConfig.getMfgDate());
			ps.setString(8, itemhardwareConfig.getComponent());
			ps.setString(9, itemhardwareConfig.getEvent());
			ps.setString(10, itemhardwareConfig.getPartNumber());
			ps.setString(11, itemhardwareConfig.getAlu());
			ps.setString(12, itemhardwareConfig.getShelfTime());
			ps.setString(13, itemhardwareConfig.getCreatedDate());
			ps.setString(14, itemhardwareConfig.getCreatedBy());
			ps.setString(15, itemhardwareConfig.getFlag());
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

	public QueryResult updateItemHardwareConfig(ItemHardwareConfig itemhardwareConfig) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE item_hardware_config SET item_type = ?, sub_type = ?, same_item_id = ?, supplier = ?, assembly_no = ?, revision = ?, mfg_date = ?, component = ?, event = ?, part_number = ?, alu = ?, shelf_time = ?, created_date = ?, created_by = ?, flag = ? WHERE id = ?"
			);
			ps.setString(1, itemhardwareConfig.getItemType());
			ps.setString(2, itemhardwareConfig.getSubType());
			ps.setString(3, itemhardwareConfig.getSameItemId());
			ps.setString(4, itemhardwareConfig.getSupplier());
			ps.setString(5, itemhardwareConfig.getAssemblyNo());
			ps.setString(6, itemhardwareConfig.getRevision());
			ps.setString(7, itemhardwareConfig.getMfgDate());
			ps.setString(8, itemhardwareConfig.getComponent());
			ps.setString(9, itemhardwareConfig.getEvent());
			ps.setString(10, itemhardwareConfig.getPartNumber());
			ps.setString(11, itemhardwareConfig.getAlu());
			ps.setString(12, itemhardwareConfig.getShelfTime());
			ps.setString(13, itemhardwareConfig.getCreatedDate());
			ps.setString(14, itemhardwareConfig.getCreatedBy());
			ps.setString(15, itemhardwareConfig.getFlag());
			ps.setString(16, itemhardwareConfig.getId());
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

	public QueryResult deleteItemHardwareConfig(String itemhardwareConfigId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM item_hardware_config WHERE id = '" + itemhardwareConfigId + "'"
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

	public ItemHardwareConfig getItemHardwareConfig(String itemhardwareConfigId) {
		String sql = "SELECT * FROM item_hardware_config WHERE id = '" + itemhardwareConfigId + "'";
		ItemHardwareConfig itemhardwareConfig = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardwareConfig = new ItemHardwareConfig();
				itemhardwareConfig.setId(rs.getString("id"));
				itemhardwareConfig.setItemType(rs.getString("item_type"));
				itemhardwareConfig.setSubType(rs.getString("sub_type"));
				itemhardwareConfig.setSameItemId(rs.getString("same_item_id"));
				itemhardwareConfig.setSupplier(rs.getString("supplier"));
				itemhardwareConfig.setAssemblyNo(rs.getString("assembly_no"));
				itemhardwareConfig.setRevision(rs.getString("revision"));
				itemhardwareConfig.setMfgDate(rs.getString("mfg_date"));
				itemhardwareConfig.setComponent(rs.getString("component"));
				itemhardwareConfig.setEvent(rs.getString("event"));
				itemhardwareConfig.setPartNumber(rs.getString("part_number"));
				itemhardwareConfig.setAlu(rs.getString("alu"));
				itemhardwareConfig.setShelfTime(rs.getString("shelf_time"));
				itemhardwareConfig.setCreatedDate(rs.getString("created_date"));
				itemhardwareConfig.setCreatedBy(rs.getString("created_by"));
				itemhardwareConfig.setFlag(rs.getString("flag"));
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
		return itemhardwareConfig;
	}

	public List<ItemHardwareConfig> getItemHardwareConfigList() {
		String sql = "SELECT * FROM item_hardware_config ORDER BY id ASC";
		List<ItemHardwareConfig> itemhardwareConfigList = new ArrayList<ItemHardwareConfig>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ItemHardwareConfig itemhardwareConfig;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itemhardwareConfig = new ItemHardwareConfig();
				itemhardwareConfig.setId(rs.getString("id"));
				itemhardwareConfig.setItemType(rs.getString("item_type"));
				itemhardwareConfig.setSubType(rs.getString("sub_type"));
				itemhardwareConfig.setSameItemId(rs.getString("same_item_id"));
				itemhardwareConfig.setSupplier(rs.getString("supplier"));
				itemhardwareConfig.setAssemblyNo(rs.getString("assembly_no"));
				itemhardwareConfig.setRevision(rs.getString("revision"));
				itemhardwareConfig.setMfgDate(rs.getString("mfg_date"));
				itemhardwareConfig.setComponent(rs.getString("component"));
				itemhardwareConfig.setEvent(rs.getString("event"));
				itemhardwareConfig.setPartNumber(rs.getString("part_number"));
				itemhardwareConfig.setAlu(rs.getString("alu"));
				itemhardwareConfig.setShelfTime(rs.getString("shelf_time"));
				itemhardwareConfig.setCreatedDate(rs.getString("created_date"));
				itemhardwareConfig.setCreatedBy(rs.getString("created_by"));
				itemhardwareConfig.setFlag(rs.getString("flag"));
				itemhardwareConfigList.add(itemhardwareConfig);
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
		return itemhardwareConfigList;
	}
}