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
import com.onsemi.mib.model.ItemPmSchedule;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemPmScheduleDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemPmScheduleDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public ItemPmScheduleDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertItemPmSchedule(ItemPmSchedule itempmSchedule) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO item_pm_schedule (pm_id, activity_type, activity, start_date, end_date, actual_start_date, actual_end_date, remarks, done_by, done_date, status, created_by, estimate_next_date, created_date, flag) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, itempmSchedule.getPmId());
			ps.setString(2, itempmSchedule.getActivityType());
			ps.setString(3, itempmSchedule.getActivity());
			ps.setString(4, itempmSchedule.getStartDate());
			ps.setString(5, itempmSchedule.getEndDate());
			ps.setString(6, itempmSchedule.getActualStartDate());
			ps.setString(7, itempmSchedule.getActualEndDate());
			ps.setString(8, itempmSchedule.getRemarks());
			ps.setString(9, itempmSchedule.getDoneBy());
			ps.setString(10, itempmSchedule.getDoneDate());
			ps.setString(11, itempmSchedule.getStatus());
			ps.setString(12, itempmSchedule.getCreatedBy());
			ps.setString(13, itempmSchedule.getEstimateNextDate());
			ps.setString(14, itempmSchedule.getCreatedDate());
			ps.setString(15, itempmSchedule.getFlag());
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

	public QueryResult updateItemPmSchedule(ItemPmSchedule itempmSchedule) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE item_pm_schedule SET pm_id = ?, activity_type = ?, activity = ?, start_date = ?, end_date = ?, actual_start_date = ?, actual_end_date = ?, remarks = ?, done_by = ?, done_date = ?, status = ?, created_by = ?, estimate_next_date = ?, created_date = ?, flag = ? WHERE id = ?"
			);
			ps.setString(1, itempmSchedule.getPmId());
			ps.setString(2, itempmSchedule.getActivityType());
			ps.setString(3, itempmSchedule.getActivity());
			ps.setString(4, itempmSchedule.getStartDate());
			ps.setString(5, itempmSchedule.getEndDate());
			ps.setString(6, itempmSchedule.getActualStartDate());
			ps.setString(7, itempmSchedule.getActualEndDate());
			ps.setString(8, itempmSchedule.getRemarks());
			ps.setString(9, itempmSchedule.getDoneBy());
			ps.setString(10, itempmSchedule.getDoneDate());
			ps.setString(11, itempmSchedule.getStatus());
			ps.setString(12, itempmSchedule.getCreatedBy());
			ps.setString(13, itempmSchedule.getEstimateNextDate());
			ps.setString(14, itempmSchedule.getCreatedDate());
			ps.setString(15, itempmSchedule.getFlag());
			ps.setString(16, itempmSchedule.getId());
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

	public QueryResult deleteItemPmSchedule(String itempmScheduleId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM item_pm_schedule WHERE id = ?"
			);
			ps.setString(1, itempmScheduleId);
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

	public ItemPmSchedule getItemPmSchedule(String itempmScheduleId) {
		String sql = "SELECT * FROM item_pm_schedule WHERE id = ?";
		ItemPmSchedule itempmSchedule = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itempmScheduleId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itempmSchedule = new ItemPmSchedule();
				itempmSchedule.setId(rs.getString("id"));
				itempmSchedule.setPmId(rs.getString("pm_id"));
				itempmSchedule.setActivityType(rs.getString("activity_type"));
				itempmSchedule.setActivity(rs.getString("activity"));
				itempmSchedule.setStartDate(rs.getString("start_date"));
				itempmSchedule.setEndDate(rs.getString("end_date"));
				itempmSchedule.setActualStartDate(rs.getString("actual_start_date"));
				itempmSchedule.setActualEndDate(rs.getString("actual_end_date"));
				itempmSchedule.setRemarks(rs.getString("remarks"));
				itempmSchedule.setDoneBy(rs.getString("done_by"));
				itempmSchedule.setDoneDate(rs.getString("done_date"));
				itempmSchedule.setStatus(rs.getString("status"));
				itempmSchedule.setCreatedBy(rs.getString("created_by"));
				itempmSchedule.setEstimateNextDate(rs.getString("estimate_next_date"));
				itempmSchedule.setCreatedDate(rs.getString("created_date"));
				itempmSchedule.setFlag(rs.getString("flag"));
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
		return itempmSchedule;
	}

	public List<ItemPmSchedule> getItemPmScheduleList() {
		String sql = "SELECT * FROM item_pm_schedule ORDER BY id ASC";
		List<ItemPmSchedule> itempmScheduleList = new ArrayList<ItemPmSchedule>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ItemPmSchedule itempmSchedule;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				itempmSchedule = new ItemPmSchedule();
				itempmSchedule.setId(rs.getString("id"));
				itempmSchedule.setPmId(rs.getString("pm_id"));
				itempmSchedule.setActivityType(rs.getString("activity_type"));
				itempmSchedule.setActivity(rs.getString("activity"));
				itempmSchedule.setStartDate(rs.getString("start_date"));
				itempmSchedule.setEndDate(rs.getString("end_date"));
				itempmSchedule.setActualStartDate(rs.getString("actual_start_date"));
				itempmSchedule.setActualEndDate(rs.getString("actual_end_date"));
				itempmSchedule.setRemarks(rs.getString("remarks"));
				itempmSchedule.setDoneBy(rs.getString("done_by"));
				itempmSchedule.setDoneDate(rs.getString("done_date"));
				itempmSchedule.setStatus(rs.getString("status"));
				itempmSchedule.setCreatedBy(rs.getString("created_by"));
				itempmSchedule.setEstimateNextDate(rs.getString("estimate_next_date"));
				itempmSchedule.setCreatedDate(rs.getString("created_date"));
				itempmSchedule.setFlag(rs.getString("flag"));
				itempmScheduleList.add(itempmSchedule);
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
		return itempmScheduleList;
	}
}