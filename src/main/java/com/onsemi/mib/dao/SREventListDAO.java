package com.onsemi.mib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.model.EventGroup;
import com.onsemi.mib.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SREventListDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SREventListDAO.class);
    private final Connection conn;

    public SREventListDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
    }

    public QueryResult insertEventGroup(EventGroup eventGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO event_group (event_group_code, event_group_details, event_group_status, event_group_flag, modified_by, modified_date, created_by, created_date) " +
                    "VALUES (?,?,?,?,?,NOW(),?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, eventGroup.getEventGroupCode());
            ps.setString(2, eventGroup.getEventGroupDetails());
            ps.setString(3, eventGroup.getEventGroupStatus());
            ps.setString(4, eventGroup.getEventGroupFlag());
            ps.setString(5, eventGroup.getGroupModifiedBy());
            ps.setString(6, eventGroup.getGroupCreatedBy());
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

    public QueryResult updateGroupEvent(EventGroup eventGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE event_group " +
                    "SET event_group_code = ?, event_group_details = ?, event_group_status = ?, event_group_flag = ?, modified_by = ?, modified_date = NOW() " +
                    "WHERE id = ? "
            );
            ps.setString(1, eventGroup.getEventGroupCode());
            ps.setString(2, eventGroup.getEventGroupDetails());
            ps.setString(3, eventGroup.getEventGroupStatus());
            ps.setString(4, eventGroup.getEventGroupFlag());
            ps.setString(5, eventGroup.getGroupModifiedBy());
            ps.setString(6, eventGroup.getGroupId());
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
    
    public QueryResult deleteGroupEvent(String groupId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM event_group WHERE id = '" + groupId + "'"
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
    
    public EventGroup getGroup(String groupId) {
        String sql = "SELECT * FROM event_group WHERE id = '" + groupId + "'";
            EventGroup eventGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eventGroup = new EventGroup(
                        rs.getString("id"),
                        rs.getString("event_group_code"),
                        rs.getString("event_group_details"),
                        rs.getString("event_group_status"),
                        rs.getString("event_group_flag"),
                        rs.getString("modified_date"),
                        rs.getString("modified_by"),
                        rs.getString("created_date"),
                        rs.getString("created_by")
                );
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
        return eventGroup;
    }
    
    public Integer getCountByEventGroupCode(String groupCode) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM event_group WHERE event_group_code = '" + groupCode + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public List<EventGroup> getGroupList() {
        String sql = "SELECT * FROM event_group ORDER BY event_group_code ";
        List<EventGroup> eventGroupList = new ArrayList<EventGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EventGroup eventGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eventGroup = new EventGroup(
                        rs.getString("id"),
                        rs.getString("event_group_code"),
                        rs.getString("event_group_details"),
                        rs.getString("event_group_status"),
                        rs.getString("event_group_flag"),
                        rs.getString("modified_by"),
                        rs.getString("modified_date"),
                        rs.getString("created_by"),
                        rs.getString("created_date")
                        
                );
                eventGroupList.add(eventGroup);
            }
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
        return eventGroupList;
    }
    
    public Integer getCountByEventGroupId(String groupId) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM event_group WHERE id = '" + groupId + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }
    
    
    //event
    public QueryResult insertEvent(EventGroup eventGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO event_list (event_group_id, event_code, event_name, requirement_status, modified_by, modified_date, created_by, created_date) " +
                    "VALUES (?,?,?,?,?,NOW(),?,NOW())", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, eventGroup.getEventGroupId());
            ps.setString(2, eventGroup.getEventCode());
            ps.setString(3, eventGroup.getEventName());
            ps.setString(4, eventGroup.getRequirementStatus());
            ps.setString(5, eventGroup.getEventModifiedBy());
            ps.setString(6, eventGroup.getEventCreatedBy());
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

    public QueryResult updateEvent(EventGroup eventGroup) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE event_list " +
                    "SET event_group_id = ?, event_code = ?, event_name = ?, requirement_status = ?, modified_by = ?, modified_date = NOW() " +
                    "WHERE id = ? "
            );
            ps.setString(1, eventGroup.getEventGroupId());
            ps.setString(2, eventGroup.getEventCode());
            ps.setString(3, eventGroup.getEventName());
            ps.setString(4, eventGroup.getRequirementStatus());
            ps.setString(5, eventGroup.getEventModifiedBy());
            ps.setString(6, eventGroup.getEventId());
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
    
    public QueryResult deleteEvent(String eventId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM event_list WHERE id = '" + eventId + "'"
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
    
    public EventGroup getEventDetails(String eventId) {
        String sql = "SELECT * " 
                   + "FROM event_list L, event_group G " 
                   + "WHERE L.event_group_id = G.id AND L.id = '" + eventId + "' " 
                   + "ORDER BY event_code ";
            EventGroup eventGroup = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eventGroup = new EventGroup();
                eventGroup.setEventId( rs.getString("L.id"));
                eventGroup.setEventGroupId(rs.getString("L.event_group_id"));
                eventGroup.setEventCode(rs.getString("L.event_code"));
                eventGroup.setEventName(rs.getString("L.event_name"));
                eventGroup.setRequirementStatus(rs.getString("L.requirement_status"));
                eventGroup.setEventModifiedBy(rs.getString("L.modified_by"));
                eventGroup.setEventModifiedDate(rs.getString("L.modified_date"));
                eventGroup.setEventCreatedBy(rs.getString("L.created_by"));
                eventGroup.setEventCreatedDate(rs.getString("L.created_date"));
                eventGroup.setEventGroupCode(rs.getString("G.event_group_code"));
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
        return eventGroup;
    }
    
    public Integer getCountByEventCode(String eventCode) {
        Integer count = null;
        String sql = "SELECT count(id) AS count FROM event_list WHERE event_code = '" + eventCode + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
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
        return count;
    }

    public List<EventGroup> getEventList() {
        String sql = "SELECT * " 
                   + "FROM event_group G, event_list L " 
                   + "WHERE G.id = L.event_group_id ";
        List<EventGroup> eventGroupList = new ArrayList<EventGroup>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            EventGroup eventGroup;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eventGroup = new EventGroup();
                eventGroup.setEventId( rs.getString("L.id"));
                eventGroup.setEventGroupId(rs.getString("L.event_group_id"));
                eventGroup.setEventCode(rs.getString("L.event_code"));
                eventGroup.setEventName(rs.getString("L.event_name"));
                eventGroup.setRequirementStatus(rs.getString("L.requirement_status"));
                eventGroup.setEventModifiedBy(rs.getString("L.modified_by"));
                eventGroup.setEventModifiedDate(rs.getString("L.modified_date"));
                eventGroup.setEventCreatedBy(rs.getString("L.created_by"));
                eventGroup.setEventCreatedDate(rs.getString("L.created_date"));
                eventGroup.setEventGroupCode(rs.getString("G.event_group_code"));
                eventGroupList.add(eventGroup);
            }
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
        return eventGroupList;
    }
}
