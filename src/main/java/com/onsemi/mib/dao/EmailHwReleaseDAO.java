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
import com.onsemi.mib.model.EmailHwRelease;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailHwReleaseDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailHwReleaseDAO.class);
	private final Connection conn;
	private final DataSource dataSource;

	public EmailHwReleaseDAO() {
			DB db = new DB();
			this.conn = db.getConnection();
			this.dataSource = db.getDataSource();
		}

	public QueryResult insertEmailHwRelease(EmailHwRelease emailhwRelease) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO email_hw_release (user_name, email, flag, remarks) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, emailhwRelease.getUserName());
			ps.setString(2, emailhwRelease.getEmail());
			ps.setString(3, emailhwRelease.getFlag());
			ps.setString(4, emailhwRelease.getRemarks());
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

	public QueryResult updateEmailHwRelease(EmailHwRelease emailhwRelease) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE email_hw_release SET user_name = ?, email = ?, flag = ?, remarks = ? WHERE id = ?"
			);
			ps.setString(1, emailhwRelease.getUserName());
			ps.setString(2, emailhwRelease.getEmail());
			ps.setString(3, emailhwRelease.getFlag());
			ps.setString(4, emailhwRelease.getRemarks());
			ps.setString(5, emailhwRelease.getId());
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

	public QueryResult deleteEmailHwRelease(String emailhwReleaseId) {
		QueryResult queryResult = new QueryResult();
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM email_hw_release WHERE id = '" + emailhwReleaseId + "'"
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

	public EmailHwRelease getEmailHwRelease(String emailhwReleaseId) {
		String sql = "SELECT * FROM email_hw_release WHERE id = '" + emailhwReleaseId + "'";
		EmailHwRelease emailhwRelease = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				emailhwRelease = new EmailHwRelease();
				emailhwRelease.setId(rs.getString("id"));
				emailhwRelease.setUserName(rs.getString("user_name"));
				emailhwRelease.setEmail(rs.getString("email"));
				emailhwRelease.setFlag(rs.getString("flag"));
				emailhwRelease.setRemarks(rs.getString("remarks"));
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
		return emailhwRelease;
	}

	public List<EmailHwRelease> getEmailHwReleaseList() {
		String sql = "SELECT * FROM email_hw_release ORDER BY id ASC";
		List<EmailHwRelease> emailhwReleaseList = new ArrayList<EmailHwRelease>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			EmailHwRelease emailhwRelease;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				emailhwRelease = new EmailHwRelease();
				emailhwRelease.setId(rs.getString("id"));
				emailhwRelease.setUserName(rs.getString("user_name"));
				emailhwRelease.setEmail(rs.getString("email"));
				emailhwRelease.setFlag(rs.getString("flag"));
				emailhwRelease.setRemarks(rs.getString("remarks"));
				emailhwReleaseList.add(emailhwRelease);
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
		return emailhwReleaseList;
	}
}