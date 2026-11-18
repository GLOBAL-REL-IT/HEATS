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
import com.onsemi.mib.model.ItemPm;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemPmDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemPmDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public ItemPmDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertItemPm(ItemPm itempm) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO item_pm (item_id, activity_type, activity, activity_duration_min, activity_interval_day, start_date, end_date, estimate_next_date, contact_person, status, created_by, created_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, itempm.getItemId());
			ps.setString(2, itempm.getActivityType());
			ps.setString(3, itempm.getActivity());
			ps.setString(4, itempm.getActivityDurationMin());
			ps.setString(5, itempm.getActivityIntervalDay());
			ps.setString(6, itempm.getStartDate());
			ps.setString(7, itempm.getEndDate());
			ps.setString(8, itempm.getEstimateNextDate());
			ps.setString(9, itempm.getContactPerson());
			ps.setString(10, itempm.getStatus());
			ps.setString(11, itempm.getCreatedBy());
			ps.setString(12, itempm.getCreatedDate());
			ps.setString(13, itempm.getFlag());
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

	public QueryResult updateItemPm(ItemPm itempm) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE item_pm SET item_id = ?, activity_type = ?, activity = ?, activity_duration_min = ?, activity_interval_day = ?, start_date = ?, end_date = ?, estimate_next_date = ?, contact_person = ?, status = ?, created_by = ?, created_date = ?, flag = ? WHERE id = ?"
			);
			ps.setString(1, itempm.getItemId());
			ps.setString(2, itempm.getActivityType());
			ps.setString(3, itempm.getActivity());
			ps.setString(4, itempm.getActivityDurationMin());
			ps.setString(5, itempm.getActivityIntervalDay());
			ps.setString(6, itempm.getStartDate());
			ps.setString(7, itempm.getEndDate());
			ps.setString(8, itempm.getEstimateNextDate());
			ps.setString(9, itempm.getContactPerson());
			ps.setString(10, itempm.getStatus());
			ps.setString(11, itempm.getCreatedBy());
			ps.setString(12, itempm.getCreatedDate());
			ps.setString(13, itempm.getFlag());
			ps.setString(14, itempm.getId());
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

	public QueryResult deleteItemPm(String itempmId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM item_pm WHERE id = ?"
			);
			ps.setString(1, itempmId);
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

	public ItemPm getItemPm(String itempmId) {
		String sql = "SELECT * FROM item_pm WHERE id = ?";
		ItemPm itempm = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itempmId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itempm = new ItemPm();
				itempm.setId(rs.getString("id"));
				itempm.setItemId(rs.getString("item_id"));
				itempm.setActivityType(rs.getString("activity_type"));
				itempm.setActivity(rs.getString("activity"));
				itempm.setActivityDurationMin(rs.getString("activity_duration_min"));
				itempm.setActivityIntervalDay(rs.getString("activity_interval_day"));
				itempm.setStartDate(rs.getString("start_date"));
				itempm.setEndDate(rs.getString("end_date"));
				itempm.setEstimateNextDate(rs.getString("estimate_next_date"));
				itempm.setContactPerson(rs.getString("contact_person"));
				itempm.setStatus(rs.getString("status"));
				itempm.setCreatedBy(rs.getString("created_by"));
				itempm.setCreatedDate(rs.getString("created_date"));
				itempm.setFlag(rs.getString("flag"));
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
		return itempm;
	}

	public List<ItemPm> getItemPmList() {
		String sql = "SELECT * FROM item_pm ORDER BY id ASC";
		List<ItemPm> itempmList = new ArrayList<ItemPm>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ItemPm itempm;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itempm = new ItemPm();
				itempm.setId(rs.getString("id"));
				itempm.setItemId(rs.getString("item_id"));
				itempm.setActivityType(rs.getString("activity_type"));
				itempm.setActivity(rs.getString("activity"));
				itempm.setActivityDurationMin(rs.getString("activity_duration_min"));
				itempm.setActivityIntervalDay(rs.getString("activity_interval_day"));
				itempm.setStartDate(rs.getString("start_date"));
				itempm.setEndDate(rs.getString("end_date"));
				itempm.setEstimateNextDate(rs.getString("estimate_next_date"));
				itempm.setContactPerson(rs.getString("contact_person"));
				itempm.setStatus(rs.getString("status"));
				itempm.setCreatedBy(rs.getString("created_by"));
				itempm.setCreatedDate(rs.getString("created_date"));
				itempm.setFlag(rs.getString("flag"));
				itempmList.add(itempm);
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
		return itempmList;
	}
}