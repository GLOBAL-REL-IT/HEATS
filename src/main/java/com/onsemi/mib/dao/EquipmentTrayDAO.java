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
import com.onsemi.mib.model.EquipmentTray;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EquipmentTrayDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentTrayDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public EquipmentTrayDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertEquipmentTray(EquipmentTray equipmenttray) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO equipment_tray (spts_pkid, equipment_pkid, rack_total, tray_per_basket_zone_capacity, tray_zone_capacity, basket_zone_capacity, tray_per_zone_capacity, basket_per_zone_capacity) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, equipmenttray.getSptsPkid());
			ps.setString(2, equipmenttray.getEquipmentPkid());
			ps.setString(3, equipmenttray.getRackTotal());
			ps.setString(4, equipmenttray.getTrayPerBasketZoneCapacity());
			ps.setString(5, equipmenttray.getTrayZoneCapacity());
			ps.setString(6, equipmenttray.getBasketZoneCapacity());
			ps.setString(7, equipmenttray.getTrayPerZoneCapacity());
			ps.setString(8, equipmenttray.getBasketPerZoneCapacity());
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

	public QueryResult updateEquipmentTray(EquipmentTray equipmenttray) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE equipment_tray SET spts_pkid = ?, equipment_pkid = ?, rack_total = ?, tray_per_basket_zone_capacity = ?, tray_zone_capacity = ?, basket_zone_capacity = ?, tray_per_zone_capacity = ?, basket_per_zone_capacity = ? WHERE id = ?"
			);
			ps.setString(1, equipmenttray.getSptsPkid());
			ps.setString(2, equipmenttray.getEquipmentPkid());
			ps.setString(3, equipmenttray.getRackTotal());
			ps.setString(4, equipmenttray.getTrayPerBasketZoneCapacity());
			ps.setString(5, equipmenttray.getTrayZoneCapacity());
			ps.setString(6, equipmenttray.getBasketZoneCapacity());
			ps.setString(7, equipmenttray.getTrayPerZoneCapacity());
			ps.setString(8, equipmenttray.getBasketPerZoneCapacity());
			ps.setString(9, equipmenttray.getId());
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

	public QueryResult deleteEquipmentTray(String equipmenttrayId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM equipment_tray WHERE id = '" + equipmenttrayId + "'"
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

	public EquipmentTray getEquipmentTray(String equipmenttrayId) {
		String sql = "SELECT * FROM equipment_tray WHERE id = '" + equipmenttrayId + "'";
		EquipmentTray equipmenttray = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				equipmenttray = new EquipmentTray();
				equipmenttray.setId(rs.getString("id"));
				equipmenttray.setSptsPkid(rs.getString("spts_pkid"));
				equipmenttray.setEquipmentPkid(rs.getString("equipment_pkid"));
				equipmenttray.setRackTotal(rs.getString("rack_total"));
				equipmenttray.setTrayPerBasketZoneCapacity(rs.getString("tray_per_basket_zone_capacity"));
				equipmenttray.setTrayZoneCapacity(rs.getString("tray_zone_capacity"));
				equipmenttray.setBasketZoneCapacity(rs.getString("basket_zone_capacity"));
				equipmenttray.setTrayPerZoneCapacity(rs.getString("tray_per_zone_capacity"));
				equipmenttray.setBasketPerZoneCapacity(rs.getString("basket_per_zone_capacity"));
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
		return equipmenttray;
	}

	public List<EquipmentTray> getEquipmentTrayList() {
		String sql = "SELECT * FROM equipment_tray ORDER BY id ASC";
		List<EquipmentTray> equipmenttrayList = new ArrayList<EquipmentTray>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			EquipmentTray equipmenttray;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				equipmenttray = new EquipmentTray();
				equipmenttray.setId(rs.getString("id"));
				equipmenttray.setSptsPkid(rs.getString("spts_pkid"));
				equipmenttray.setEquipmentPkid(rs.getString("equipment_pkid"));
				equipmenttray.setRackTotal(rs.getString("rack_total"));
				equipmenttray.setTrayPerBasketZoneCapacity(rs.getString("tray_per_basket_zone_capacity"));
				equipmenttray.setTrayZoneCapacity(rs.getString("tray_zone_capacity"));
				equipmenttray.setBasketZoneCapacity(rs.getString("basket_zone_capacity"));
				equipmenttray.setTrayPerZoneCapacity(rs.getString("tray_per_zone_capacity"));
				equipmenttray.setBasketPerZoneCapacity(rs.getString("basket_per_zone_capacity"));
				equipmenttrayList.add(equipmenttray);
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
		return equipmenttrayList;
	}
}