package com.onsemi.mib.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.onsemi.mib.db.DB;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.model.LDAPUser;
import java.math.BigInteger;
import java.sql.Connection;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LDAPUserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LDAPUserDAO.class);
    private final DataSource dataSource;

    public LDAPUserDAO() {
        DB db = new DB();
        this.dataSource = db.getDataSource();
    }

    public QueryResult insert(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "INSERT INTO user_ldap (login_id, oncid, firstname, lastname, email, title, group_id, is_active, created_by, created_time, request_access) VALUES (?,?,?,?,?,?,?,'1',?,NOW(),?)";
        QueryRunner queryRunner = new QueryRunner(dataSource);
//        LOGGER.info("masuk sqlllllllllllllllll");
        try {
//            Long result = queryRunner.insert(
            BigInteger result = queryRunner.insert(
                    sql,
                    //                    new ScalarHandler<Long>(),
                    new ScalarHandler<BigInteger>(),
                    user.getLoginId(),
                    user.getOncid(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getTitle(),
                    user.getGroupId(),
                    user.getCreatedBy(),
                    user.getRequestAccess()
            );
            queryResult.setResult(result.intValue());
            queryResult.setGeneratedKey(result.toString());
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult update(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE user_ldap SET oncid = ?, firstname = ?, lastname = ?, email = ?, title = ?, group_id = ?, is_active = ?, modified_by = ?, modified_time = NOW() WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getOncid(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getTitle(),
                    user.getGroupId(),
                    user.getIsActive(),
                    user.getModifiedBy(),
                    user.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateRequestAccessByLoginId(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE user_ldap SET request_access = ? WHERE login_id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getRequestAccess(),
                    user.getLoginId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateAuth(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE user_ldap "
                + "SET sr_email_shipping = ?, sr_email_retrieve = ?, sr_email_ship_to_rl = ?, hw_email_shipping = ?, hw_email_retrieve = ?, hw_email_ship_to_rl = ?, "
                + "features_test_email = ?, features_track_gts = ?, features_track_inventory = ?, features_create_gts = ?, modified_by = ?, modified_time = NOW() "
                + "WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getSrEmailShipping(),
                    user.getSrEmailRetrieve(),
                    user.getSrEmailShipToRl(),
                    user.getHwEmailShipping(),
                    user.getHwEmailRetrieve(),
                    user.getHwEmailShipToRl(),
                    user.getFeaturesTestEmail(),
                    user.getFeaturesTrackGts(),
                    user.getFeaturesTrackInventory(),
                    user.getFeaturesCreateGts(),
                    user.getModifiedBy(),
                    user.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateAuth2(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE user_ldap "
                + "SET sr_scrap = ?, sr_email_retrieve = ?, modified_by = ?, modified_time = NOW() "
                + "WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getScrap(),
                    user.getSrEmailRetrieve(),
                    user.getModifiedBy(),
                    user.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult updateGroup(LDAPUser user) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "UPDATE user_ldap SET group_id = ?, modified_by = ?, modified_time = NOW() WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    user.getGroupId(),
                    user.getModifiedBy(),
                    user.getId()
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public QueryResult delete(String id) {
        QueryResult queryResult = new QueryResult();
        queryResult.setResult(0);
        String sql = "DELETE FROM user_ldap WHERE id = ?";
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            Integer result = queryRunner.update(
                    sql,
                    id
            );
            queryResult.setResult(result);
        } catch (SQLException ex) {
            queryResult.setErrorMessage(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
        return queryResult;
    }

    public LDAPUser get(String id) {
        LDAPUser user = new LDAPUser();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name "
                + "FROM user_ldap u "
                + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                + "WHERE u.id = ?";
        try {
            ResultSetHandler<LDAPUser> h = new ResultSetHandler<LDAPUser>() {
                @Override
                public LDAPUser handle(ResultSet rs) throws SQLException {
                    LDAPUser user = new LDAPUser();
                    while (rs.next()) {
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                        user.setPassword(rs.getString("password"));
                        user.setIsActive(rs.getString("is_active"));
                        //auth
                        user.setSrEmailRetrieve(rs.getString("sr_email_retrieve"));
                        user.setScrap(rs.getString("sr_scrap"));
//                        user.setSrEmailShipping(rs.getString("sr_email_shipping"));
//                        user.setSrEmailShipToRl(rs.getString("sr_email_ship_to_rl"));
//                        user.setHwEmailRetrieve(rs.getString("hw_email_retrieve"));
//                        user.setHwEmailShipToRl(rs.getString("hw_email_ship_to_rl"));
//                        user.setFeaturesTestEmail(rs.getString("features_test_email"));
//                        user.setFeaturesTrackGts(rs.getString("features_track_gts"));
//                        user.setFeaturesTrackInventory(rs.getString("features_track_inventory"));
//                        user.setFeaturesCreateGts(rs.getString("features_create_gts"));
                    }
                    return user;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            user = run.query(sql, h, id);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }

    public LDAPUser getByLoginId(String loginId) {
        LDAPUser user = new LDAPUser();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name, uac.* FROM user_ldap u "
                + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                + "LEFT JOIN user_access_control uac ON u.id = uac.user_id "
                + "WHERE u.login_id = ?";
        try {
            ResultSetHandler<LDAPUser> h = new ResultSetHandler<LDAPUser>() {
                @Override
                public LDAPUser handle(ResultSet rs) throws SQLException {
                    LDAPUser user = new LDAPUser();
                    while (rs.next()) {
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                        user.setRequestAccess(rs.getString("request_access"));
                        //auth
                        user.setSrEmailRetrieve(rs.getString("sr_email_retrieve"));
                        user.setScrap(rs.getString("sr_scrap"));
                        user.setItemAdd(rs.getString("uac.item_add"));
                        user.setItemEdit(rs.getString("uac.item_edit"));
                        user.setItemDelete(rs.getString("uac.item_delete"));
                        user.setItemActivityConfig(rs.getString("uac.item_activity_config"));
                        user.setItemActivityAdd(rs.getString("uac.item_activity_add"));
                        user.setItemActivityEdit(rs.getString("uac.item_activity_edit"));
                        user.setItemHardwareAdd(rs.getString("uac.item_hardware_add"));
                        user.setItemHardwareEdit(rs.getString("uac.item_hardware_edit"));
                        user.setItemHardwareDelete(rs.getString("uac.item_hardware_delete"));
                        user.setItemMovementAdd(rs.getString("uac.item_movement_add"));
                        user.setItemSfRecall(rs.getString("uac.item_sf_recall"));
                        user.setEqptAdd(rs.getString("uac.eqpt_add"));
                        user.setEqptEdit(rs.getString("uac.eqpt_edit"));
                        user.setEqptDelete(rs.getString("uac.eqpt_delete"));
                        user.setEqptFamilyAdd(rs.getString("uac.eqpt_family_add"));
                        user.setEqptFamilyDelete(rs.getString("uac.eqpt_family_delete"));
                        user.setEqptRelTestGroupAdd(rs.getString("uac.eqpt_rel_test_group_add"));
                        user.setEqptRelTestGroupDelete(rs.getString("uac.eqpt_rel__test_group_delete"));
                        user.setEqptTechAdd(rs.getString("uac.eqpt_tech_add"));
                        user.setEqptTechDelete(rs.getString("uac.eqpt_tech_delete"));
                        user.setEqptMonAdd(rs.getString("uac.eqpt_mon_add"));
                        user.setEqptMonDelete(rs.getString("uac.eqpt_mon_delete"));
                        user.setEqptViMonAdd(rs.getString("uac.eqpt_vi_mon_add"));
                        user.setEqptViMonDelete(rs.getString("uac.eqpt_vi_mon_delete"));
                        user.setEqptFamilyAddGlobal(rs.getString("uac.eqpt_family_add_global"));
                        user.setEqptRelTestGroupAddGlobal(rs.getString("uac.eqpt_rel_test_group_add_global"));
                    }
                    return user;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            user = run.query(sql, h, loginId);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }

    public List<LDAPUser> list() {
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM user_ldap u "
                + "LEFT JOIN user_group ug ON (u.group_id = ug.id)";
        try {
            ResultSetHandler<List<LDAPUser>> h = new ResultSetHandler<List<LDAPUser>>() {
                @Override
                public List<LDAPUser> handle(ResultSet rs) throws SQLException {
                    List<LDAPUser> list = new ArrayList<LDAPUser>();
                    while (rs.next()) {
                        LDAPUser user = new LDAPUser();
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                        //auth
                        user.setSrEmailRetrieve(rs.getString("sr_email_retrieve"));
                        user.setScrap(rs.getString("sr_scrap"));
//                        user.setSrEmailShipping(rs.getString("sr_email_shipping"));
//                        user.setSrEmailShipToRl(rs.getString("sr_email_ship_to_rl"));
//                        user.setHwEmailRetrieve(rs.getString("hw_email_retrieve"));
//                        user.setHwEmailShipToRl(rs.getString("hw_email_ship_to_rl"));
//                        user.setFeaturesTestEmail(rs.getString("features_test_email"));
//                        user.setFeaturesTrackGts(rs.getString("features_track_gts"));
//                        user.setFeaturesTrackInventory(rs.getString("features_track_inventory"));
//                        user.setFeaturesCreateGts(rs.getString("features_create_gts"));
                        list.add(user);
                    }
                    return list;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            list = run.query(sql, h);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return list;
    }

    public List<LDAPUser> listByGroupId(String groupId) {
        String filterGroupId = "";
        if (!groupId.equals("")) {
            filterGroupId = "WHERE u.group_id = '" + groupId + "' ";
        }
        List<LDAPUser> list = new ArrayList<LDAPUser>();
        String sql = "SELECT u.*, IFNULL(ug.code, '') AS group_code, IFNULL(ug.name, '') AS group_name FROM user_ldap u "
                + "LEFT JOIN user_group ug ON (u.group_id = ug.id) "
                + filterGroupId
                + "ORDER BY u.firstname";
        try {
            ResultSetHandler<List<LDAPUser>> h = new ResultSetHandler<List<LDAPUser>>() {
                @Override
                public List<LDAPUser> handle(ResultSet rs) throws SQLException {
                    List<LDAPUser> list = new ArrayList<LDAPUser>();
                    while (rs.next()) {
                        LDAPUser user = new LDAPUser();
                        user.setId(rs.getString("id"));
                        user.setLoginId(rs.getString("login_id"));
                        user.setOncid(rs.getString("oncid"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setTitle(rs.getString("title"));
                        user.setGroupId(rs.getString("group_id"));
                        user.setGroupCode(rs.getString("group_code"));
                        user.setGroupName(rs.getString("group_name"));
                        //auth
                        user.setSrEmailRetrieve(rs.getString("sr_email_retrieve"));
                        user.setScrap(rs.getString("sr_scrap"));
//                        user.setSrEmailShipping(rs.getString("sr_email_shipping"));
//                        user.setSrEmailShipToRl(rs.getString("sr_email_ship_to_rl"));
//                        user.setHwEmailRetrieve(rs.getString("hw_email_retrieve"));
//                        user.setHwEmailShipToRl(rs.getString("hw_email_ship_to_rl"));
//                        user.setFeaturesTestEmail(rs.getString("features_test_email"));
//                        user.setFeaturesTrackGts(rs.getString("features_track_gts"));
//                        user.setFeaturesTrackInventory(rs.getString("features_track_inventory"));
//                        user.setFeaturesCreateGts(rs.getString("features_create_gts"));
                        list.add(user);
                    }
                    return list;
                }
            };
            QueryRunner run = new QueryRunner(dataSource);
            list = run.query(sql, h);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
        return list;
    }
}
