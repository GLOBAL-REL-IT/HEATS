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
import com.onsemi.mib.model.EquipmentSlot;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EquipmentSlotDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentSlotDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public EquipmentSlotDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertEquipmentSlot(EquipmentSlot equipmentslot) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO equipment_slot (spts_pkid, slot_id, equipment_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, equipmentslot.getSptsPkid());
			ps.setString(2, equipmentslot.getSlotId());
			ps.setString(3, equipmentslot.getEquipmentId());
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

	public QueryResult updateEquipmentSlot(EquipmentSlot equipmentslot) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE equipment_slot SET spts_pkid = ?, slot_id = ?, equipment_id = ? WHERE id = ?"
			);
			ps.setString(1, equipmentslot.getSptsPkid());
			ps.setString(2, equipmentslot.getSlotId());
			ps.setString(3, equipmentslot.getEquipmentId());
			ps.setString(4, equipmentslot.getId());
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

	public QueryResult deleteEquipmentSlot(String equipmentslotId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM equipment_slot WHERE id = '" + equipmentslotId + "'"
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

	public EquipmentSlot getEquipmentSlot(String equipmentslotId) {
		String sql = "SELECT * FROM equipment_slot WHERE id = '" + equipmentslotId + "'";
		EquipmentSlot equipmentslot = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				equipmentslot = new EquipmentSlot();
				equipmentslot.setId(rs.getString("id"));
				equipmentslot.setSptsPkid(rs.getString("spts_pkid"));
				equipmentslot.setSlotId(rs.getString("slot_id"));
				equipmentslot.setEquipmentId(rs.getString("equipment_id"));
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
		return equipmentslot;
	}

	public List<EquipmentSlot> getEquipmentSlotList() {
		String sql = "SELECT * FROM equipment_slot ORDER BY id ASC";
		List<EquipmentSlot> equipmentslotList = new ArrayList<EquipmentSlot>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			EquipmentSlot equipmentslot;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				equipmentslot = new EquipmentSlot();
				equipmentslot.setId(rs.getString("id"));
				equipmentslot.setSptsPkid(rs.getString("spts_pkid"));
				equipmentslot.setSlotId(rs.getString("slot_id"));
				equipmentslot.setEquipmentId(rs.getString("equipment_id"));
				equipmentslotList.add(equipmentslot);
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
		return equipmentslotList;
	}
}