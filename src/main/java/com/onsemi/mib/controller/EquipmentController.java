package com.onsemi.mib.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.EquipmentDAO;
import com.onsemi.mib.dao.EquipmentFamilyDAO;
import com.onsemi.mib.dao.EquipmentGlobalFamilyDAO;
import com.onsemi.mib.dao.EquipmentGlobalRelTestGroupDAO;
import com.onsemi.mib.dao.EquipmentLogDAO;
import com.onsemi.mib.dao.EquipmentMonitoringDAO;
import com.onsemi.mib.dao.EquipmentRelTestGroupDAO;
import com.onsemi.mib.dao.EquipmentTechDAO;
import com.onsemi.mib.dao.EquipmentViMonitoringDAO;
import com.onsemi.mib.model.Equipment;
import com.onsemi.mib.model.EquipmentFamily;
import com.onsemi.mib.model.EquipmentGlobalFamily;
import com.onsemi.mib.model.EquipmentGlobalRelTestGroup;
import com.onsemi.mib.model.EquipmentLog;
import com.onsemi.mib.model.EquipmentMonitoring;
import com.onsemi.mib.model.EquipmentRelTestGroup;
import com.onsemi.mib.model.EquipmentTech;
import com.onsemi.mib.model.EquipmentViMonitoring;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSResponse;
import com.onsemi.mib.tools.SPTSWebService;
import com.onsemi.mib.tools.SystemUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/equipment")
@SessionAttributes({"userSession"})
public class EquipmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipmentController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String equipment(
            Model model,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String relTestGroup
    ) throws IOException {

        //eqptType 1 = Life ; 2 = Environment
        //cbmsType 0 = No ; 1 = Yes
        //Rule Life = Slot, Environment = rack (tray, basket)
        //currentStatus 0 = Inactive ; 1 = Active
        model.addAttribute("userEqptAdd", userSession.getEqptAdd());
        model.addAttribute("userEqptEdit", userSession.getEqptEdit());
        model.addAttribute("userEqptDelete", userSession.getEqptDelete());

        JSONObject param = new JSONObject();
        param.put("param", "");
        JSONArray getRelTestGroup = SPTSWebService.getEqptRelTestGroupByParam(param);

        List<LinkedHashMap<String, String>> relTestGroupList = SystemUtil.jsonArrayToList(getRelTestGroup);
        model.addAttribute("relTestGroupList", relTestGroupList);

        String relTestGroupTitle = "";

        if (relTestGroup == null || "".equals(relTestGroup)) {
            EquipmentDAO eqptD = new EquipmentDAO();
            List<Equipment> eqptList = eqptD.getEquipmentListByRelTestGroupPkid("No Rel Test Group");
            model.addAttribute("eqptList", eqptList);
        } else {
            String pkid = "";
            //get pkid
            JSONObject param1 = new JSONObject();
            param1.put("relTestGroup", relTestGroup);
            JSONArray getItemByParam = SPTSWebService.getEqptRelTestGroupByName(param1);
            for (int i = 0; i < getItemByParam.length(); i++) {
                pkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
            }
            EquipmentDAO eqptD = new EquipmentDAO();
            List<Equipment> eqptList = eqptD.getEquipmentListByRelTestGroupPkid(pkid);
            model.addAttribute("eqptList", eqptList);
            relTestGroupTitle = " (" + relTestGroup + ")";
        }
        model.addAttribute("relTestGroupTitle", relTestGroupTitle);

        EquipmentDAO eqptD = new EquipmentDAO();
        List<Equipment> eqptManufacturerList = eqptD.getEqptManufacturer("");
        model.addAttribute("eqptManufacturerList", eqptManufacturerList);

        eqptD = new EquipmentDAO();
        List<Equipment> eqptModelList = eqptD.getEqptModel("");
        model.addAttribute("eqptModelList", eqptModelList);

        EquipmentFamilyDAO eqptFD = new EquipmentFamilyDAO();
        List<EquipmentFamily> eqptFamilyList = eqptFD.getEquipmentFamilyList("");
        model.addAttribute("eqptFamilyList", eqptFamilyList);

        EquipmentRelTestGroupDAO eqptRD = new EquipmentRelTestGroupDAO();
        List<EquipmentRelTestGroup> eqptRelTestGroupList = eqptRD.getEquipmentRelTestGroupList("");
        model.addAttribute("eqptRelTestGroupList", eqptRelTestGroupList);

        EquipmentTechDAO eqptTD = new EquipmentTechDAO();
        List<EquipmentTech> eqptTechList = eqptTD.getEquipmentTechList("");
        model.addAttribute("eqptTechList", eqptTechList);

        EquipmentMonitoringDAO eqptMD = new EquipmentMonitoringDAO();
        List<EquipmentMonitoring> eqptMonList = eqptMD.getEquipmentMonitoringList("");
        model.addAttribute("eqptMonList", eqptMonList);

        EquipmentViMonitoringDAO eqptVD = new EquipmentViMonitoringDAO();
        List<EquipmentViMonitoring> eqptViMonList = eqptVD.getEquipmentViMonitoringList("");
        model.addAttribute("eqptViMonList", eqptViMonList);

        return "equipment/equipment";
    }

    @RequestMapping(value = "/{sptsPkid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String equipmentWithSptsPkid(
            Model model,
            @ModelAttribute UserSession userSession,
            //            @RequestParam(required = false) String relTestGroup,
            @PathVariable("sptsPkid") String sptsPkid
    ) throws IOException {

        //eqptType 1 = Life ; 2 = Environment
        //cbmsType 0 = No ; 1 = Yes
        //Rule Life = Slot, Environment = rack (tray, basket)
        //currentStatus 0 = Inactive ; 1 = Active
        model.addAttribute("userEqptAdd", userSession.getEqptAdd());
        model.addAttribute("userEqptEdit", userSession.getEqptEdit());
        model.addAttribute("userEqptDelete", userSession.getEqptDelete());

        EquipmentDAO eqptD = new EquipmentDAO();
        Equipment eqpt = eqptD.getEquipmentBySptsPkid(sptsPkid);
        model.addAttribute("eqpt", eqpt);

        JSONObject param = new JSONObject();
        param.put("param", "");
        JSONArray getRelTestGroup = SPTSWebService.getEqptRelTestGroupByParam(param);

        List<LinkedHashMap<String, String>> relTestGroupList = SystemUtil.jsonArrayToList(getRelTestGroup);
        model.addAttribute("relTestGroupList", relTestGroupList);

        String relTestGroupTitle = "";

        if (eqpt.getRelTestGroup() == null || "".equals(eqpt.getRelTestGroup())) {
            eqptD = new EquipmentDAO();
            List<Equipment> eqptList = eqptD.getEquipmentListByRelTestGroupPkid("No Rel Test Group");
            model.addAttribute("eqptList", eqptList);
        } else {
            String pkid = "";
            //get pkid
            JSONObject param1 = new JSONObject();
            param1.put("relTestGroup", eqpt.getRelTestGroup());
            JSONArray getItemByParam = SPTSWebService.getEqptRelTestGroupByName(param1);
            for (int i = 0; i < getItemByParam.length(); i++) {
                pkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
            }
            eqptD = new EquipmentDAO();
            List<Equipment> eqptList = eqptD.getEquipmentListByRelTestGroupPkid(pkid);
            model.addAttribute("eqptList", eqptList);
            relTestGroupTitle = " (" + eqpt.getRelTestGroup() + ")";
        }
        model.addAttribute("relTestGroupTitle", relTestGroupTitle);

        eqptD = new EquipmentDAO();
        List<Equipment> eqptManufacturerList = eqptD.getEqptManufacturer(eqpt.getEquipmentManufacturer());
        model.addAttribute("eqptManufacturerList", eqptManufacturerList);

        eqptD = new EquipmentDAO();
        List<Equipment> eqptModelList = eqptD.getEqptModel(eqpt.getEquipmentModel());
        model.addAttribute("eqptModelList", eqptModelList);

        EquipmentFamilyDAO eqptFD = new EquipmentFamilyDAO();
        List<EquipmentFamily> eqptFamilyList = eqptFD.getEquipmentFamilyList(eqpt.getFamilyName());
        model.addAttribute("eqptFamilyList", eqptFamilyList);

        EquipmentRelTestGroupDAO eqptRD = new EquipmentRelTestGroupDAO();
        List<EquipmentRelTestGroup> eqptRelTestGroupList = eqptRD.getEquipmentRelTestGroupList(eqpt.getRelTestGroup());
        model.addAttribute("eqptRelTestGroupList", eqptRelTestGroupList);

        EquipmentTechDAO eqptTD = new EquipmentTechDAO();
        List<EquipmentTech> eqptTechList = eqptTD.getEquipmentTechList(eqpt.getEquipTechPkid());
        model.addAttribute("eqptTechList", eqptTechList);

        EquipmentMonitoringDAO eqptMD = new EquipmentMonitoringDAO();
        List<EquipmentMonitoring> eqptMonList = eqptMD.getEquipmentMonitoringList(eqpt.getEquipMonitoringPkid());
        model.addAttribute("eqptMonList", eqptMonList);

        EquipmentViMonitoringDAO eqptVD = new EquipmentViMonitoringDAO();
        List<EquipmentViMonitoring> eqptViMonList = eqptVD.getEquipmentViMonitoringList(eqpt.getViMonitoringPkid());
        model.addAttribute("eqptViMonList", eqptViMonList);

        return "equipment/equipment";
    }

    @RequestMapping(value = "/updateListSpts", method = {RequestMethod.GET, RequestMethod.POST}) //checking SPTS data and update to MIB DB
    public String updateListSpts(
            Model model,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs
    ) throws IOException {

        //eqptType 1 = Life ; 2 = Environment
        //cbmsType 0 = No ; 1 = Yes
        //Rule Life = Slot, Environment = rack (tray, basket)
        //currentStatus 0 = Inactive ; 1 = Active
        //update SPTS data per item type into MIB DB
//        JSONObject params = new JSONObject();
//        params.put("param", "");
//        JSONArray getItemByParam = SPTSWebService.getEqptByParam(params);
        JSONObject params = new JSONObject();
        params.put("sitePKID", "1");
        params.put("siteName", "Seremban");
        JSONArray getItemByParam = SPTSWebService.getSptsEqptByParam(params);

        int count = 0;
        int countAdd = 0;
        int countUpdate = 0;

        //insert into database
        for (int i = 0; i < getItemByParam.length(); i++) {

            String pkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));

            Equipment eqpt = new Equipment();
            eqpt.setSptsPkid(pkid);
            eqpt.setEquipmentId(getItemByParam.getJSONObject(i).getString("equipment_id"));
            eqpt.setCurrentStatus(Integer.toString(getItemByParam.getJSONObject(i).getInt("current_status")));
            eqpt.setEquipmentType(Integer.toString(getItemByParam.getJSONObject(i).getInt("equipment_type")));
            eqpt.setCbmsType(Integer.toString(getItemByParam.getJSONObject(i).getInt("cbms_type")));
            eqpt.setCreatedBy("Update from SPTS");
            eqpt.setFlag(Integer.toString(getItemByParam.getJSONObject(i).getInt("current_status")));
            if (getItemByParam.getJSONObject(i).has("family_pkid")) {
                eqpt.setFamilyPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("family_pkid")));
            }
            if (getItemByParam.getJSONObject(i).has("rel_test_group_pkid")) {
                eqpt.setRelTestGroupPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("rel_test_group_pkid")));
            }
            if (getItemByParam.getJSONObject(i).has("equipment_manufacturer")) {
                eqpt.setEquipmentManufacturer(getItemByParam.getJSONObject(i).getString("equipment_manufacturer"));
            }
            if (getItemByParam.getJSONObject(i).has("equipment_model")) {
                eqpt.setEquipmentModel(getItemByParam.getJSONObject(i).getString("equipment_model"));
            }
            if (getItemByParam.getJSONObject(i).has("remarks")) {
                eqpt.setRemarks(getItemByParam.getJSONObject(i).getString("remarks"));
            }
            if (getItemByParam.getJSONObject(i).has("EquipTechPKID")) {
                eqpt.setEquipTechPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipTechPKID")));
            }
            if (getItemByParam.getJSONObject(i).has("EquipCapability")) {
                Object assembly = getItemByParam.getJSONObject(i).get("EquipCapability");
                if (assembly instanceof String) {
                    eqpt.setEquipCapability(getItemByParam.getJSONObject(i).getString("EquipCapability"));
                } else {
                    eqpt.setEquipCapability(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipCapability")));
                }
            }
            if (getItemByParam.getJSONObject(i).has("EquipMonitoringPKID")) {
                eqpt.setEquipMonitoringPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipTechPKID")));
            }
            if (getItemByParam.getJSONObject(i).has("VIMonitoringPKID")) {
                eqpt.setViMonitoringPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("VIMonitoringPKID")));
            }
            //slot and rack
            if (getItemByParam.getJSONObject(i).has("slot_qty")) {
                eqpt.setSlot(Integer.toString(getItemByParam.getJSONObject(i).getInt("slot_qty")));
            } else {
                eqpt.setSlot("0");
            }
            if (getItemByParam.getJSONObject(i).has("rack_total")) {
                eqpt.setRackTotal(Integer.toString(getItemByParam.getJSONObject(i).getInt("rack_total")));
            } else {
                eqpt.setRackTotal("0");
            }
            if (getItemByParam.getJSONObject(i).has("zone_per_rack")) {
                eqpt.setZonePerRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("zone_per_rack"))); //tray_per_basket_zone_capacity in SPTS DB
            } else {
                eqpt.setZonePerRack("0");
            }
            if (getItemByParam.getJSONObject(i).has("tray_qty_per_rack")) {
                eqpt.setTrayQtyPerRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("tray_qty_per_rack"))); //tray_zone_capacity
            } else {
                eqpt.setTrayQtyPerRack("0");
            }
            if (getItemByParam.getJSONObject(i).has("crocodile_qty_per_rack")) {
                eqpt.setBasketQtyPerRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("crocodile_qty_per_rack"))); //basket_zone_capacity
            } else {
                eqpt.setBasketQtyPerRack("0");
            }
            if (getItemByParam.getJSONObject(i).has("tray_qty_per_zone")) {
                eqpt.setTrayQtyPerZone(Integer.toString(getItemByParam.getJSONObject(i).getInt("tray_qty_per_zone"))); //tray_per_zone_capacity
            } else {
                eqpt.setTrayQtyPerZone("0");
            }
            if (getItemByParam.getJSONObject(i).has("crocodile_qty_per_zone")) {
                eqpt.setBasketQtyPerZone(Integer.toString(getItemByParam.getJSONObject(i).getInt("crocodile_qty_per_zone"))); //basket_per_zone_capacity
            } else {
                eqpt.setBasketQtyPerZone("0");
            }

            //slot table
//            EquipmentSlot eqptSlot = new EquipmentSlot(); //hold
            //check need to insert or update
            EquipmentDAO eqptD = new EquipmentDAO();
            int countPkid = eqptD.getCountPkid(pkid);
            LOGGER.info("pkid: " + pkid);
            if (countPkid == 0) { //insert
                eqptD = new EquipmentDAO();
                QueryResult q = eqptD.insertEquipment(eqpt);
                countAdd += q.getResult();

                //update log
                EquipmentLog log = new EquipmentLog();
                log.setEquipmentId(q.getGeneratedKey());
                log.setDetail("Added From SPTS");
                log.setCreatedBy(userSession.getFullname());
                EquipmentLogDAO logD = new EquipmentLogDAO();
                QueryResult logQ = logD.insertEquipmentLog(log);

            } else if (countPkid == 1) { //update
                eqptD = new EquipmentDAO();
                QueryResult q = eqptD.updateEquipmentBySptsPkid(eqpt);
                countUpdate += q.getResult();

                eqptD = new EquipmentDAO();
                Equipment eqpt1 = eqptD.getEquipmentBySptsPkid(pkid);

                //update log
                EquipmentLog log = new EquipmentLog();
                log.setEquipmentId(eqpt1.getId());
                log.setDetail("Data Updated From SPTS");
                log.setCreatedBy(userSession.getFullname());
                EquipmentLogDAO logD = new EquipmentLogDAO();
                QueryResult logQ = logD.insertEquipmentLog(log);
            }
            count += 1;
        }
        LOGGER.info("Total data: " + count);
        LOGGER.info("Total insert: " + countAdd);
        LOGGER.info("Total update: " + countUpdate);

        redirectAttrs.addFlashAttribute("success", "Total DataSet from SPTS: " + count + ". Total Insert to HEATS DB: " + countAdd + ". Total DataSet in HEATS: " + countUpdate + ".");
        return "redirect:/equipment";
    }

    @RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Equipment getEqptDetail(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String pkID
    ) throws IOException {

        //get data from spts first, then update local DB
        JSONObject param = new JSONObject();
        param.put("sitePKID", "1");
        param.put("siteName", "Seremban");
        param.put("equipmentPKID", pkID);
        param.put("equipmentID", "");
        param.put("familyName", "");
        param.put("relTestGroupName", "");
        param.put("active", "1");
        JSONArray getItemByParam = SPTSWebService.getSptsEqptByParamMib(param);
        for (int i = 0; i < getItemByParam.length(); i++) {
//            System.out.println(getItemByParam.getJSONObject(i));
            Equipment eqpt = new Equipment();
            eqpt.setSptsPkid(pkID);
            eqpt.setEquipmentId(getItemByParam.getJSONObject(i).getString("equipment_id"));
            eqpt.setCurrentStatus(Integer.toString(getItemByParam.getJSONObject(i).getInt("current_status")));
            eqpt.setEquipmentType(Integer.toString(getItemByParam.getJSONObject(i).getInt("equipment_type")));
            eqpt.setCbmsType(Integer.toString(getItemByParam.getJSONObject(i).getInt("cbms_type")));
            eqpt.setCreatedBy("Update from SPTS");
            eqpt.setFlag(Integer.toString(getItemByParam.getJSONObject(i).getInt("current_status")));
            if (getItemByParam.getJSONObject(i).has("family_pkid")) {
                eqpt.setFamilyPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("family_pkid")));
            }
            if (getItemByParam.getJSONObject(i).has("rel_test_group_pkid")) {
                eqpt.setRelTestGroupPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("rel_test_group_pkid")));
            }
            if (getItemByParam.getJSONObject(i).has("equipment_manufacturer")) {
                eqpt.setEquipmentManufacturer(getItemByParam.getJSONObject(i).getString("equipment_manufacturer"));
            }
            if (getItemByParam.getJSONObject(i).has("equipment_model")) {
                eqpt.setEquipmentModel(getItemByParam.getJSONObject(i).getString("equipment_model"));
            }
            if (getItemByParam.getJSONObject(i).has("remarks")) {
                eqpt.setRemarks(getItemByParam.getJSONObject(i).getString("remarks"));
            }
            if (getItemByParam.getJSONObject(i).has("EquipTechPKID")) {
                eqpt.setEquipTechPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipTechPKID")));
            }
            if (getItemByParam.getJSONObject(i).has("EquipCapability")) {
                Object assembly = getItemByParam.getJSONObject(i).get("EquipCapability");
                if (assembly instanceof String) {
                    eqpt.setEquipCapability(getItemByParam.getJSONObject(i).getString("EquipCapability"));
                } else {
                    eqpt.setEquipCapability(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipCapability")));
                }
            }
            if (getItemByParam.getJSONObject(i).has("EquipMonitoringPKID")) {
                eqpt.setEquipMonitoringPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipTechPKID")));
            }
            if (getItemByParam.getJSONObject(i).has("VIMonitoringPKID")) {
                eqpt.setViMonitoringPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("VIMonitoringPKID")));
            }
            //slot and rack
            if (getItemByParam.getJSONObject(i).has("slot_qty")) {
                eqpt.setSlot(Integer.toString(getItemByParam.getJSONObject(i).getInt("slot_qty")));
            } else {
                eqpt.setSlot("0");
            }
            if (getItemByParam.getJSONObject(i).has("rack_total")) {
                eqpt.setRackTotal(Integer.toString(getItemByParam.getJSONObject(i).getInt("rack_total")));
            } else {
                eqpt.setRackTotal("0");
            }
            if (getItemByParam.getJSONObject(i).has("zone_per_rack")) {
                eqpt.setZonePerRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("zone_per_rack"))); //tray_per_basket_zone_capacity in SPTS DB
            } else {
                eqpt.setZonePerRack("0");
            }
            if (getItemByParam.getJSONObject(i).has("tray_qty_per_rack")) {
                eqpt.setTrayQtyPerRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("tray_qty_per_rack"))); //tray_zone_capacity
            } else {
                eqpt.setTrayQtyPerRack("0");
            }
            if (getItemByParam.getJSONObject(i).has("crocodile_qty_per_rack")) {
                eqpt.setBasketQtyPerRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("crocodile_qty_per_rack"))); //basket_zone_capacity
            } else {
                eqpt.setBasketQtyPerRack("0");
            }
            if (getItemByParam.getJSONObject(i).has("tray_qty_per_zone")) {
                eqpt.setTrayQtyPerZone(Integer.toString(getItemByParam.getJSONObject(i).getInt("tray_qty_per_zone"))); //tray_per_zone_capacity
            } else {
                eqpt.setTrayQtyPerZone("0");
            }
            if (getItemByParam.getJSONObject(i).has("crocodile_qty_per_zone")) {
                eqpt.setBasketQtyPerZone(Integer.toString(getItemByParam.getJSONObject(i).getInt("crocodile_qty_per_zone"))); //basket_per_zone_capacity
            } else {
                eqpt.setBasketQtyPerZone("0");
            }

            EquipmentDAO eqptD = new EquipmentDAO();
            QueryResult q = eqptD.updateEquipmentBySptsPkid(eqpt);
        }

        EquipmentDAO eqptD = new EquipmentDAO();
        Equipment eqpt = eqptD.getEquipmentBySptsPkid(pkID);

        return eqpt;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

        EquipmentDAO eqptD = new EquipmentDAO();
        List<Equipment> eqptManufacturerList = eqptD.getEqptManufacturer("");
        model.addAttribute("eqptManufacturerList", eqptManufacturerList);

        eqptD = new EquipmentDAO();
        List<Equipment> eqptModelList = eqptD.getEqptModel("");
        model.addAttribute("eqptModelList", eqptModelList);

        EquipmentFamilyDAO eqptFD = new EquipmentFamilyDAO();
        List<EquipmentFamily> eqptFamilyList = eqptFD.getEquipmentFamilyList("");
        model.addAttribute("eqptFamilyList", eqptFamilyList);

        EquipmentRelTestGroupDAO eqptRD = new EquipmentRelTestGroupDAO();
        List<EquipmentRelTestGroup> eqptRelTestGroupList = eqptRD.getEquipmentRelTestGroupList("");
        model.addAttribute("eqptRelTestGroupList", eqptRelTestGroupList);

        EquipmentTechDAO eqptTD = new EquipmentTechDAO();
        List<EquipmentTech> eqptTechList = eqptTD.getEquipmentTechList("");
        model.addAttribute("eqptTechList", eqptTechList);

        EquipmentMonitoringDAO eqptMD = new EquipmentMonitoringDAO();
        List<EquipmentMonitoring> eqptMonList = eqptMD.getEquipmentMonitoringList("");
        model.addAttribute("eqptMonList", eqptMonList);

        EquipmentViMonitoringDAO eqptVD = new EquipmentViMonitoringDAO();
        List<EquipmentViMonitoring> eqptViMonList = eqptVD.getEquipmentViMonitoringList("");
        model.addAttribute("eqptViMonList", eqptViMonList);

        return "equipment/equipment_add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String sptsPkid,
            @RequestParam(required = false) String eqptId,
            @RequestParam(required = false) String familyName,
            @RequestParam(required = false) String relTestGroupName,
            @RequestParam(required = false) String eqptStatus,
            @RequestParam(required = false) String eqptType,
            @RequestParam(required = false) String eqptManufacturer,
            @RequestParam(required = false) String eqptModel,
            @RequestParam(required = false) String cbmsType,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String eqptTech,
            @RequestParam(required = false) String eqptCapability,
            @RequestParam(required = false) String eqptMon,
            @RequestParam(required = false) String eqptViMon,
            @RequestParam(required = false) String slotQty,
            @RequestParam(required = false) String rackQty,
            @RequestParam(required = false) String zonePerRack,
            @RequestParam(required = false) String trayQtyPerRack,
            @RequestParam(required = false) String basketQtyPerRack,
            @RequestParam(required = false) String trayQtyPerZone,
            @RequestParam(required = false) String basketQtyPerZone,
            @RequestParam(required = false) String flag
    ) throws IOException {

        Equipment equipment = new Equipment();
//        equipment.setSptsPkid("0");
        equipment.setEquipmentId(eqptId);
        equipment.setFamilyPkid(familyName);
        equipment.setRelTestGroupPkid(relTestGroupName);
        equipment.setCurrentStatus(eqptStatus);
        equipment.setEquipmentType(eqptType);
        equipment.setEquipmentManufacturer(eqptManufacturer);
        equipment.setEquipmentModel(eqptModel);
        equipment.setCbmsType(cbmsType);
        equipment.setRemarks(remarks);
        equipment.setEquipTechPkid(eqptTech);
        equipment.setEquipCapability(eqptCapability);
        equipment.setEquipMonitoringPkid(eqptMon);
        equipment.setViMonitoringPkid(eqptViMon);
        equipment.setSlot(slotQty);
        equipment.setRackTotal(rackQty);
        equipment.setZonePerRack(zonePerRack);
        equipment.setTrayQtyPerRack(trayQtyPerRack);
        equipment.setBasketQtyPerRack(basketQtyPerRack);
        equipment.setTrayQtyPerZone(trayQtyPerZone);
        equipment.setBasketQtyPerZone(basketQtyPerZone);
        equipment.setCreatedBy(userSession.getFullname());
        equipment.setFlag("0");

        //save to SPTS first
        JSONObject params = new JSONObject();
        params.put("equipmentID", eqptId);
        params.put("familyPKID", familyName);
        params.put("relTestGroupPKID", relTestGroupName);
        params.put("currentStatus", eqptStatus);
        params.put("equipmentType", eqptType);
        params.put("manufacturer", eqptManufacturer);
        params.put("modal", eqptModel);
        params.put("cbmsType", cbmsType);
        params.put("remarks", remarks);
        params.put("equipTechPKID", eqptTech);
        params.put("equipCapability", eqptCapability);
        params.put("equipMonitoringPKID", eqptMon);
        params.put("vIMonitoringPKID", eqptViMon);
        SPTSResponse sr = SPTSWebService.insertEqpt(params);

        if (sr.getStatus()) { //then update SPTS slot/tray table
            LOGGER.info("eqpt PKID from respondId: " + sr.getResponseId());
            //get spts pkid first
            JSONObject param1 = new JSONObject();
            param1.put("equipmentID", eqptId);
            JSONArray getItemByParam = SPTSWebService.getEqptByEqptId(param1);
            String epqtPkid = "";
            for (int i = 0; i < getItemByParam.length(); i++) {
                epqtPkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
                LOGGER.info("eqpt PKID from geteqptByEqptId: " + epqtPkid);
            }
            SPTSResponse sptsSlotTrayAdd = new SPTSResponse();
            if ("1".equals(eqptType)) { //Life - slot table

                for (int j = 0; j < Integer.parseInt(slotQty); j++) {
                    JSONObject paramSlot = new JSONObject();
                    paramSlot.put("equipmentPKID", epqtPkid);
                    sptsSlotTrayAdd = SPTSWebService.insertEqptSlot(paramSlot);
                }

            } else { //environment - tray table
                JSONObject paramTray = new JSONObject();
                paramTray.put("equipmentPKID", epqtPkid);
                paramTray.put("rack_total", rackQty);
                paramTray.put("trayPerBasketZoneCapacity", zonePerRack);
                paramTray.put("trayZoneCapacity", trayQtyPerRack);
                paramTray.put("basketZoneCapacity", basketQtyPerRack);
                paramTray.put("trayPerZoneCapaicty", trayQtyPerZone);
                paramTray.put("basketPerZoneCapacity", basketQtyPerZone);
                sptsSlotTrayAdd = SPTSWebService.insertEqptTray(paramTray);
            }

            if (sptsSlotTrayAdd.getStatus()) {
                //save to local DB
                equipment.setSptsPkid(epqtPkid);
                EquipmentDAO equipmentDAO = new EquipmentDAO();
                QueryResult queryResult = equipmentDAO.insertEquipment(equipment);
                args = new String[1];
                args[0] = sptsPkid + " - " + eqptId;
                if (queryResult.getGeneratedKey().equals("0")) {
                    //delete eqpt ID because failed to insert to local DB
                    JSONObject param = new JSONObject();
                    param.put("equipmentID", eqptId);
                    SPTSResponse deleteEqpt = SPTSWebService.deleteEqpt(param);
                    LOGGER.info("Result delete eqpt id due to failed to insert into local DB: " + deleteEqpt.getStatus());

                    model.addAttribute("error", "Fail to add Eqpt ID: " + eqptId + ". Pls contact system admin");
                    model.addAttribute("equipment", equipment);
                    return "equipment/add";
                } else {

                    //update log
                    EquipmentLog log = new EquipmentLog();
                    log.setEquipmentId(queryResult.getGeneratedKey());
                    log.setDetail("New Record Added");
                    log.setCreatedBy(userSession.getFullname());
                    EquipmentLogDAO logD = new EquipmentLogDAO();
                    QueryResult logQ = logD.insertEquipmentLog(log);

                    redirectAttrs.addFlashAttribute("success", "Successfully add equipment ID: " + eqptId);
                    return "redirect:/equipment/" + epqtPkid;
                }
            } else {
                //delete eqpt ID because failed to insert slot/tray
                JSONObject param = new JSONObject();
                param.put("equipmentID", eqptId);
                SPTSResponse deleteEqpt = SPTSWebService.deleteEqpt(param);
                LOGGER.info("Result delete eqpt id due to failed to insert tray/slot: " + deleteEqpt.getStatus());

                model.addAttribute("error", "Fail to add Eqpt slot/tray. Pls contact system admin");
                model.addAttribute("equipment", equipment);
                return "equipment/add";
            }

        } else {
            model.addAttribute("error", "Fail to add Eqpt ID: " + eqptId + ". Pls contact system admin");
            model.addAttribute("equipment", equipment);
            return "equipment/add";
        }

    }

    @RequestMapping(value = "/edit/{equipmentId}", method = RequestMethod.GET)
    public String edit(
            Model model,
            @PathVariable("equipmentId") String equipmentId
    ) {
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        Equipment equipment = equipmentDAO.getEquipment(equipmentId);
        model.addAttribute("equipment", equipment);
        return "equipment/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mibId,
            @RequestParam(required = false) String itemPKID,
            @RequestParam(required = false) String eqptId,
            @RequestParam(required = false) String familyName,
            @RequestParam(required = false) String relTestGroupName,
            @RequestParam(required = false) String eqptStatus,
            @RequestParam(required = false) String eqptType,
            @RequestParam(required = false) String eqptManufacturer,
            @RequestParam(required = false) String eqptModel,
            @RequestParam(required = false) String cbmsType,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String eqptTech,
            @RequestParam(required = false) String eqptCapability,
            @RequestParam(required = false) String eqptMon,
            @RequestParam(required = false) String eqptViMon,
            @RequestParam(required = false) String slotQty,
            @RequestParam(required = false) String rackQty,
            @RequestParam(required = false) String zonePerRack,
            @RequestParam(required = false) String trayQtyPerRack,
            @RequestParam(required = false) String basketQtyPerRack,
            @RequestParam(required = false) String trayQtyPerZone,
            @RequestParam(required = false) String basketQtyPerZone,
            @RequestParam(required = false) String flag
    ) throws IOException {

        //get version first
        String versionss = "";
        JSONObject paramV = new JSONObject();
        paramV.put("pkid", itemPKID);
//        LOGGER.info("itemPKID: " + itemPKID);
        JSONArray getItemByParamV = SPTSWebService.getEqptByPkid(paramV);
        for (int i = 0; i < getItemByParamV.length(); i++) {
//            LOGGER.info("masuk+++++++++++++++++++++++: ");
//            System.out.println(getItemByParam.getJSONObject(i));
            versionss = getItemByParamV.getJSONObject(i).getString("version");
//            LOGGER.info("versionss: " + getItemByParamV.getJSONObject(i).getString("version"));
        }

        //update spts
        JSONObject params = new JSONObject();
        params.put("pkID", itemPKID);
        params.put("version", versionss);
        params.put("equipmentID", eqptId);
        params.put("familyPKID", familyName);
        params.put("relTestGroupPKID", relTestGroupName);
        params.put("currentStatus", eqptStatus);
        params.put("equipmentType", eqptType);
        params.put("manufacturer", eqptManufacturer);
        params.put("modal", eqptModel);
        params.put("cbmsType", cbmsType);
        params.put("remarks", remarks);
        params.put("equipTechPKID", eqptTech);
        params.put("equipCapability", eqptCapability);
        params.put("equipMonitoringPKID", eqptMon);
        params.put("vIMonitoringPKID", eqptViMon);
        SPTSResponse sr = SPTSWebService.updateEqpt(params);
        if (sr.getStatus()) {
            //update slot/tray table
            SPTSResponse sptsSlotTrayAdd = new SPTSResponse();
            if ("1".equals(eqptType)) { //Life - slot table

                //get current slot qty
                JSONObject param = new JSONObject();
                param.put("equipmentPKID", itemPKID);
                JSONArray getItemByParam = SPTSWebService.getEqptSlotByEqptPkid(param);
                int currentSlotQty = getItemByParam.length();

                if (currentSlotQty < Integer.parseInt(slotQty)) { //add new slotID
                    for (int j = 0; j < (Integer.parseInt(slotQty) - currentSlotQty); j++) {
                        JSONObject paramSlot = new JSONObject();
                        paramSlot.put("equipmentPKID", itemPKID);
                        sptsSlotTrayAdd = SPTSWebService.insertEqptSlot(paramSlot);
                    }
                } else if (currentSlotQty > Integer.parseInt(slotQty)) { //delete slotID
                    int slotId = currentSlotQty;
                    for (int j = 0; j < (currentSlotQty - Integer.parseInt(slotQty)); j++) {
                        JSONObject paramSlot = new JSONObject();
                        paramSlot.put("equipmentPKID", itemPKID);
                        paramSlot.put("slotID", slotId);
                        sptsSlotTrayAdd = SPTSWebService.deleteEqptSlot(paramSlot);
                        slotId -= (j + 1);
                    }
                } else { //same slot qty
                    sptsSlotTrayAdd.setStatus(Boolean.TRUE);
                }
            } else { //environment - tray table

                String version = "";
                String trayPkid = "";
                JSONObject param = new JSONObject();
                param.put("equipmentPKID", itemPKID);
                JSONArray getItemByParam = SPTSWebService.getEqptTrayByEqptPkid(param);
                for (int i = 0; i < getItemByParamV.length(); i++) {
                    version = getItemByParam.getJSONObject(i).getString("version");
                    trayPkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
                }
//                LOGGER.info("versionTray: " + version);
//                LOGGER.info("pkidTray: " + trayPkid);

                JSONObject paramTray = new JSONObject();
                paramTray.put("pkID", trayPkid);
                paramTray.put("version", version);
                paramTray.put("equipmentPKID", itemPKID);
                paramTray.put("rack_total", rackQty);
                paramTray.put("trayPerBasketZoneCapacity", zonePerRack);
                paramTray.put("trayZoneCapacity", trayQtyPerRack);
                paramTray.put("basketZoneCapacity", basketQtyPerRack);
                paramTray.put("trayPerZoneCapaicty", trayQtyPerZone);
                paramTray.put("basketPerZoneCapacity", basketQtyPerZone);
                sptsSlotTrayAdd = SPTSWebService.updateEqptTray(paramTray);
            }
//            LOGGER.info("sptsSlotTrayAdd.getStatus(): " + sptsSlotTrayAdd.getStatus());
            if (sptsSlotTrayAdd.getStatus()) {
                //save to local DB
                Equipment equipment = new Equipment();
                equipment.setId(mibId);
                equipment.setSptsPkid(itemPKID);
                equipment.setEquipmentId(eqptId);
                equipment.setFamilyPkid(familyName);
                equipment.setRelTestGroupPkid(relTestGroupName);
                equipment.setCurrentStatus(eqptStatus);
                equipment.setEquipmentType(eqptType);
                equipment.setEquipmentManufacturer(eqptManufacturer);
                equipment.setEquipmentModel(eqptModel);
                equipment.setCbmsType(cbmsType);
                equipment.setRemarks(remarks);
                equipment.setEquipTechPkid(eqptTech);
                equipment.setEquipCapability(eqptCapability);
                equipment.setEquipMonitoringPkid(eqptMon);
                equipment.setViMonitoringPkid(eqptViMon);
                equipment.setSlot(slotQty);
                equipment.setRackTotal(rackQty);
                equipment.setZonePerRack(zonePerRack);
                equipment.setTrayQtyPerRack(trayQtyPerRack);
                equipment.setBasketQtyPerRack(basketQtyPerRack);
                equipment.setTrayQtyPerZone(trayQtyPerZone);
                equipment.setBasketQtyPerZone(basketQtyPerZone);
                equipment.setFlag("0");
                EquipmentDAO equipmentDAO = new EquipmentDAO();
                QueryResult queryResult = equipmentDAO.updateEquipment(equipment);
                if (queryResult.getResult() == 1) {

                    //update log
                    EquipmentLog log = new EquipmentLog();
                    log.setEquipmentId(mibId);
                    log.setDetail("Data Updated");
                    log.setCreatedBy(userSession.getFullname());
                    EquipmentLogDAO logD = new EquipmentLogDAO();
                    QueryResult logQ = logD.insertEquipmentLog(log);

                    redirectAttrs.addFlashAttribute("success", "Successfully update equipment ID: " + eqptId);
                } else {
                    LOGGER.info("Failed to update local DB");
                    redirectAttrs.addFlashAttribute("error", "Fail to update equipment ID: " + eqptId + ". Pls contact system admin");
                }
                return "redirect:/equipment/" + itemPKID;
            } else {
                LOGGER.info("Failed to update slot/tray table");
                return "redirect:/equipment/" + itemPKID;
            }
        } else {
            LOGGER.info("Failed to update eqpt ID ");

            redirectAttrs.addFlashAttribute("error", "Fail to update equipment ID: " + eqptId + ". Pls contact system admin");
            return "redirect:/equipment/" + itemPKID;
        }

    }

    @RequestMapping(value = "/delete/{pkid}/{mbid}", method = RequestMethod.GET)
    public String delete(
            Model model,
            @ModelAttribute UserSession userSession,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("pkid") String pkid,
            @PathVariable("mbid") String mbid
    ) throws IOException {

        //get eqptId from spts first
        String eqptId = "";
        JSONObject params = new JSONObject();
        params.put("pkid", pkid);
        JSONArray getItemByParam = SPTSWebService.getEqptByPkid(params);
        for (int i = 0; i < getItemByParam.length(); i++) {
            eqptId = getItemByParam.getJSONObject(i).getString("equipment_id");
        }
//        LOGGER.info("eqptId: " + eqptId);
        //delete to SPTS first then to local DB
        JSONObject param = new JSONObject();
        param.put("equipmentID", eqptId);
        SPTSResponse deleteEqpt = SPTSWebService.deleteEqpt(param);
        if (deleteEqpt.getStatus()) {
            redirectAttrs.addFlashAttribute("success", "Item deleted!");
//            LOGGER.info("+++++++++SPTS Updated+++++++++++");
            //update SPTS PKID into MIB DB

            EquipmentDAO equipmentDAO = new EquipmentDAO();
            Equipment equipment = equipmentDAO.getEquipment(mbid);
            equipmentDAO = new EquipmentDAO();
            QueryResult queryResult = equipmentDAO.deleteEquipment(mbid);

            if (queryResult.getResult() > 0) {

                //update log
                EquipmentLog log = new EquipmentLog();
                log.setEquipmentId(mbid);
                log.setDetail("Equipment Deleted");
                log.setCreatedBy(userSession.getFullname());
                EquipmentLogDAO logD = new EquipmentLogDAO();
                QueryResult logQ = logD.insertEquipmentLog(log);

                redirectAttrs.addFlashAttribute("success", "Succesfully Scrap Eqpt ID: " + eqptId);
                return "redirect:/equipment";
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to Scrap Item ID: " + eqptId + ". Pls contact system admin.");
                return "redirect:/equipment";
            }

        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(param.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (deleteEqpt.getErrorDetail().equals("")) {
                errorMessage = deleteEqpt.getErrorCode() + " - " + deleteEqpt.getErrorMessage();
            } else {
                errorMessage = deleteEqpt.getErrorCode() + " - " + deleteEqpt.getErrorDetail();
            }
            redirectAttrs.addFlashAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/equipment";
        }
    }

    @RequestMapping(value = "/family/add", method = RequestMethod.GET)
    public String familyAdd(Model model, @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userEqptFamilyAdd", userSession.getEqptFamilyAdd());
        model.addAttribute("userEqptFamilyDelete", userSession.getEqptFamilyDelete());
        model.addAttribute("userEqptFamilyAddGlobal", userSession.getEqptFamilyAddGlobal());

        //retrieve from SPTS first
        JSONObject params = new JSONObject();
        params.put("param", "");
        JSONArray getItemByParam = SPTSWebService.getEqptFamilyByParam(params);

        for (int i = 0; i < getItemByParam.length(); i++) {
            EquipmentFamilyDAO eqptFamilyD = new EquipmentFamilyDAO();
            int count = eqptFamilyD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid")));
            if (count == 0) { //insert into HEATS db
                EquipmentFamily family = new EquipmentFamily();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid")));
                family.setFamilyName(getItemByParam.getJSONObject(i).getString("family_name"));
                family.setCreatedBy("SPTS");
                eqptFamilyD = new EquipmentFamilyDAO();
                QueryResult q = eqptFamilyD.insertEquipmentFamily(family);
            } else if (count == 1) { //update family name by spts pkid 
                EquipmentFamily family = new EquipmentFamily();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid")));
                family.setFamilyName(getItemByParam.getJSONObject(i).getString("family_name"));
                eqptFamilyD = new EquipmentFamilyDAO();
                QueryResult q = eqptFamilyD.updateEquipmentFamilyBySptsPkid(family);
            }
        }

        //insert/update global table
        JSONArray getGlobalEqpFamily = SPTSWebService.getGlobalFamilyNameAll();
        for (int i = 0; i < getGlobalEqpFamily.length(); i++) {

            EquipmentGlobalFamily globalRelTest = new EquipmentGlobalFamily();
            globalRelTest.setSptsGuid(getGlobalEqpFamily.getJSONObject(i).getString("GUID"));
            globalRelTest.setCreatedDate(getGlobalEqpFamily.getJSONObject(i).getString("CreateDatetime").substring(0, 10) + " " + getGlobalEqpFamily.getJSONObject(i).getString("CreateDatetime").substring(11, 19));
//            String familyNameWithEscapeJs = StringEscapeUtils.escapeEcmaScript(getGlobalRelTestGroup.getJSONObject(i).getString("EquipmentFamilyName"));
//            globalRelTest.setFamilyName(familyNameWithEscapeJs);
            globalRelTest.setFamilyName(getGlobalEqpFamily.getJSONObject(i).getString("EquipmentFamilyName"));
            if (getGlobalEqpFamily.getJSONObject(i).has("GEFNAuthorizationGUID")) {
                globalRelTest.setGefnAuthorizationGuid(getGlobalEqpFamily.getJSONObject(i).getString("GEFNAuthorizationGUID"));
            }
            if (getGlobalEqpFamily.getJSONObject(i).has("LastModifiedDatetime")) {
                globalRelTest.setModifiedDate(getGlobalEqpFamily.getJSONObject(i).getString("LastModifiedDatetime").substring(0, 10) + " " + getGlobalEqpFamily.getJSONObject(i).getString("LastModifiedDatetime").substring(11, 19));
            }
            if (getGlobalEqpFamily.getJSONObject(i).has("LastModifiedUserName")) {
                globalRelTest.setModifiedBy(getGlobalEqpFamily.getJSONObject(i).getString("LastModifiedUserName"));
            }
            if (getGlobalEqpFamily.getJSONObject(i).has("LastModifiedSitePKID")) {
                globalRelTest.setModifiedSiteId(Integer.toString(getGlobalEqpFamily.getJSONObject(i).getInt("LastModifiedSitePKID")));
            }

            EquipmentGlobalFamilyDAO globalD = new EquipmentGlobalFamilyDAO();
            int countGlobalFamily = globalD.getCountGlobalFamilyNameByGuid(getGlobalEqpFamily.getJSONObject(i).getString("GUID"));
            if (countGlobalFamily == 0) {
//                LOGGER.info("+++++++++++insert global family name++++++++");
                globalD = new EquipmentGlobalFamilyDAO();
                QueryResult qGlobalRel = globalD.insertEquipmentGlobalFamily(globalRelTest);
            } else if (countGlobalFamily == 1) {
//                LOGGER.info("+++++++update global family name+++++++++");
                globalD = new EquipmentGlobalFamilyDAO();
                QueryResult qGlobalRel = globalD.updateEquipmentGlobalFamilyByGuid(globalRelTest);
            }
        }

        EquipmentFamilyDAO eqptFamilyD = new EquipmentFamilyDAO();
        List<EquipmentFamily> eqptFamily = eqptFamilyD.getEquipmentFamilyListleftJoinWithGlobal();
        model.addAttribute("eqptFamily", eqptFamily);

        return "equipment/eqptFamily";
    }

    @RequestMapping(value = "/family/save", method = RequestMethod.POST)
    public String familySave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String familyName
    ) throws IOException {

        //check if exist in DB or not
        EquipmentFamilyDAO eqptFamilyD = new EquipmentFamilyDAO();
        int count = eqptFamilyD.getCountFamilyName(familyName);
        if (count == 0) {
            //insert into SPTS first
            JSONObject params = new JSONObject();
            params.put("familyName", familyName);
            SPTSResponse sr = SPTSWebService.insertEqptFamily(params);
            if (sr.getStatus()) { //insert into local DB

                //insert into global list in global table
                JSONObject paramGlobal = new JSONObject();
                paramGlobal.put("equipmentFamilyName", familyName);
                SPTSResponse insertGlobalFamilyName = SPTSWebService.insertGlobalFamilyName(paramGlobal);
                if (insertGlobalFamilyName.getStatus()) {
                    LOGGER.info("save to global family table");
                } else {
                    LOGGER.info("fail save to global family table");
                }

                //get spts pkid first
                JSONObject param1 = new JSONObject();
                param1.put("familyName", familyName);
                JSONArray getItemByParam = SPTSWebService.getEqptFamilyByFamilyName(param1);
                String pkid = "";
                for (int i = 0; i < getItemByParam.length(); i++) {
                    pkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
                }
                EquipmentFamily equipmentfamily = new EquipmentFamily();
                equipmentfamily.setSptsPkid(pkid);
                equipmentfamily.setFamilyName(familyName);
                equipmentfamily.setCreatedBy(userSession.getFullname());
                EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentFamily(equipmentfamily);

                if (queryResult.getGeneratedKey().equals("0")) {
                    redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
                    return "redirect:/equipment/family/add";
                } else {

                    //send email to global-rel-it to manual sync global table via SPTS 
                    List<String> emails = new ArrayList<String>();
                    emails.add("global-rel-it@onsemi.com"); // add email requestor to the list

                    String[] myArray = new String[emails.size()];
                    String[] emailTo = emails.toArray(myArray);
                    //get current date and time
                    LocalDateTime instance = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    String formattedString = formatter.format(instance); //15-02-2022 12:43

                    //send INFORMATION email
                    LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                    EmailSender emailSender = new EmailSender();
                    emailSender.htmlEmailTable(
                            servletContext,
                            "", //user name requestor
                            //                    to, //to
                            emailTo,
                            "New Global Equipment Family", //subject
                            "<br />"
                            + "Pls be informed that new global eqpt family was added thru HEATS."
                            + "<br /> "
                            + "<br /> "
                            + "Family Name: " + familyName
                            + "<br /> "
                            + "Added By: " + userSession.getFullname()
                            + "<br /> "
                            + "Registration Date: " + formattedString
                            + "<br /> "
                            + "<br /> "
                            + "Please manually sync global eqpt family thru SPTS application. Otherwise, SPTS global table will not be updated."
                            + "<br /> "
                            + "<br />Thank you." //msg
                    );
                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + familyName);
                    return "redirect:/equipment/family/add";
                }
            } else {
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (sr.getErrorDetail().equals("")) {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                } else {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                }
                model.addAttribute("error", errorMessage);
                model.addAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/family/add";
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Duplicate Family Name. Pls register with different name");
            return "redirect:/equipment/family/add";
        }

    }

    @RequestMapping(value = "/family/delete/{equipmentfamilyId}", method = RequestMethod.GET)
    public String familyDelete(
            Model model,
            Locale locale,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) throws IOException {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        EquipmentFamily equipmentfamily = equipmentfamilyDAO.getEquipmentFamily(equipmentfamilyId);

        //delete spts local table
        JSONObject params = new JSONObject();
        params.put("familyName", equipmentfamily.getFamilyName());
        SPTSResponse sr = SPTSWebService.deleteEqptFamily(params);
        if (sr.getStatus()) {

            //get version and guid first table
            String version = "";
            JSONObject paramV = new JSONObject();
            paramV.put("equipmentFamilyName", equipmentfamily.getFamilyName());
            JSONArray getItemByParamV = SPTSWebService.getGlobalFamilyNameByParam(paramV);
            for (int i = 0; i < getItemByParamV.length(); i++) {
                version = getItemByParamV.getJSONObject(i).getString("Version");
                LOGGER.info("version: " + version);
            }

            String sptsGuid = "";

            String familyNameWithEscapeJs = StringEscapeUtils.escapeEcmaScript(equipmentfamily.getFamilyName());
            EquipmentGlobalFamilyDAO epqtD = new EquipmentGlobalFamilyDAO();
            int countFamilyName = epqtD.getCountGlobalFamilyName(familyNameWithEscapeJs);
            if (countFamilyName == 1) {
                epqtD = new EquipmentGlobalFamilyDAO();
                EquipmentGlobalFamily epqtG = epqtD.getEquipmentGlobalFamilyByFamilyName(familyNameWithEscapeJs);
                sptsGuid = epqtG.getSptsGuid();
            } else {
                sptsGuid = "0";
            }
            LOGGER.info("epqtG.getSptsGuid(): " + sptsGuid);

            //delete spts global table
            JSONObject paramDelete = new JSONObject();
//            paramDelete.put("guID", epqtG.getSptsGuid());
            paramDelete.put("guID", sptsGuid);
            paramDelete.put("version", version);
            SPTSResponse sr1 = SPTSWebService.deleteGlobalFamilyName(paramDelete);

            //delete heats global table 
            epqtD = new EquipmentGlobalFamilyDAO();
            QueryResult delete = epqtD.deleteEquipmentGlobalFamilyByGuid(sptsGuid);

            //delete local table
            equipmentfamilyDAO = new EquipmentFamilyDAO();
            QueryResult queryResult = equipmentfamilyDAO.deleteEquipmentFamily(equipmentfamilyId);
            if (queryResult.getResult() == 1) {

                //send email to global-rel-it to manual sync global table via SPTS 
                List<String> emails = new ArrayList<String>();
                emails.add("global-rel-it@onsemi.com"); // add email requestor to the list

                String[] myArray = new String[emails.size()];
                String[] emailTo = emails.toArray(myArray);
                //get current date and time
                LocalDateTime instance = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedString = formatter.format(instance); //15-02-2022 12:43

                //send INFORMATION email
                LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        //                    to, //to
                        emailTo,
                        "Deletion of Global Equipment Family", //subject
                        "<br />"
                        + "Pls be informed that new global eqpt family was deleted thru HEATS."
                        + "<br /> "
                        + "<br /> "
                        + "Family Name: " + equipmentfamily.getFamilyName()
                        + "<br /> "
                        + "Deleted By: " + userSession.getFullname()
                        + "<br /> "
                        + "Deletion Date: " + formattedString
                        + "<br /> "
                        + "<br /> "
                        + "Please manually sync global eqpt family thru SPTS application. Otherwise, SPTS global table will not be updated."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("success", equipmentfamily.getFamilyName() + " successfully deleted");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getFamilyName() + ". Pls contact system admin.");
            }
            return "redirect:/equipment/family/add";
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model.addAttribute("error", errorMessage);
            model.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/equipment/family/add";
        }
    }

    @RequestMapping(value = "/family/insertGlobal/{equipmentfamilyId}", method = RequestMethod.GET)
    public String familyInsertGlobal(
            Model model,
            Locale locale,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) throws IOException {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        EquipmentFamily equipmentfamily = equipmentfamilyDAO.getEquipmentFamily(equipmentfamilyId);

        JSONObject paramF = new JSONObject();
        paramF.put("equipmentFamilyName", equipmentfamily.getFamilyName());
        JSONArray getItemByParamF = SPTSWebService.getGlobalFamilyNameByParam(paramF);

        if (getItemByParamF.length() == 0) {

            //insert into global list in global table
            JSONObject paramGlobal = new JSONObject();
            paramGlobal.put("equipmentFamilyName", equipmentfamily.getFamilyName());
            SPTSResponse insertGlobalFamilyName = SPTSWebService.insertGlobalFamilyName(paramGlobal);
            if (insertGlobalFamilyName.getStatus()) {
                LOGGER.info("save to global family table");

                //insert into heats global table
                JSONObject paramV = new JSONObject();
                paramV.put("equipmentFamilyName", equipmentfamily.getFamilyName());
                JSONArray getItemByParamV = SPTSWebService.getGlobalFamilyNameByParam(paramV);
                for (int i = 0; i < getItemByParamV.length(); i++) {
                    EquipmentGlobalFamily globalFamily = new EquipmentGlobalFamily();
                    globalFamily.setSptsGuid(getItemByParamV.getJSONObject(i).getString("GUID"));
                    globalFamily.setCreatedDate(getItemByParamV.getJSONObject(i).getString("CreateDatetime").substring(0, 10) + " " + getItemByParamV.getJSONObject(i).getString("CreateDatetime").substring(11, 19));
                    globalFamily.setFamilyName(getItemByParamV.getJSONObject(i).getString("EquipmentFamilyName"));
                    if (getItemByParamV.getJSONObject(i).has("GEFNAuthorizationGUID")) {
                        globalFamily.setGefnAuthorizationGuid(getItemByParamV.getJSONObject(i).getString("GEFNAuthorizationGUID"));
                    }
                    if (getItemByParamV.getJSONObject(i).has("LastModifiedDatetime")) {
                        globalFamily.setModifiedDate(getItemByParamV.getJSONObject(i).getString("LastModifiedDatetime").substring(0, 10) + " " + getItemByParamV.getJSONObject(i).getString("LastModifiedDatetime").substring(11, 19));
                    }
                    if (getItemByParamV.getJSONObject(i).has("LastModifiedUserName")) {
                        globalFamily.setModifiedBy(getItemByParamV.getJSONObject(i).getString("LastModifiedUserName"));
                    }
                    if (getItemByParamV.getJSONObject(i).has("LastModifiedSitePKID")) {
                        globalFamily.setModifiedSiteId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("LastModifiedSitePKID")));
                    }
                    EquipmentGlobalFamilyDAO globalD = new EquipmentGlobalFamilyDAO();
                    int countGlobalFamily = globalD.getCountGlobalFamilyNameByGuid(getItemByParamV.getJSONObject(i).getString("GUID"));
                    if (countGlobalFamily == 0) {
                        LOGGER.info("+++++++++++insert global family name++++++++");
                        globalD = new EquipmentGlobalFamilyDAO();
                        QueryResult qGlobalRel = globalD.insertEquipmentGlobalFamily(globalFamily);
                    } else if (countGlobalFamily == 1) {
                        LOGGER.info("+++++++update global family name+++++++++");
                        globalD = new EquipmentGlobalFamilyDAO();
                        QueryResult qGlobalRel = globalD.updateEquipmentGlobalFamilyByGuid(globalFamily);
                    }
                }
                redirectAttrs.addFlashAttribute("success", equipmentfamily.getFamilyName() + " successfully added into Global List");
                return "redirect:/equipment/family/add";
            } else {
                LOGGER.info("fail save to global family table");
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(paramGlobal.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (insertGlobalFamilyName.getErrorDetail().equals("")) {
                    errorMessage = insertGlobalFamilyName.getErrorCode() + " - " + insertGlobalFamilyName.getErrorMessage();
                } else {
                    errorMessage = insertGlobalFamilyName.getErrorCode() + " - " + insertGlobalFamilyName.getErrorDetail();
                }
                redirectAttrs.addFlashAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/family/add";
            }

        } else {
            redirectAttrs.addFlashAttribute("error", equipmentfamily.getFamilyName() + " already exist in the Global List. Pls contact system admin.");
            return "redirect:/equipment/family/add";
        }

    }

    @RequestMapping(value = "/relTestGroup/add", method = RequestMethod.GET)
    public String relTestGroupAdd(Model model, @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userEqptRelTestGroupAdd", userSession.getEqptRelTestGroupAdd());
        model.addAttribute("userEqptRelTestGroupDelete", userSession.getEqptRelTestGroupDelete());
        model.addAttribute("userEqptRelTestGroupAddGlobal", userSession.getEqptRelTestGroupAddGlobal());

        //retrieve from SPTS first
        JSONObject params = new JSONObject();
        params.put("param", "");
        JSONArray getItemByParam = SPTSWebService.getEqptRelTestGroupByParam(params);

        for (int i = 0; i < getItemByParam.length(); i++) {
            EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
            int count = eqptRelTestGroupD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid")));
            if (count == 0) { //insert into HEATS db
                EquipmentRelTestGroup family = new EquipmentRelTestGroup();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid")));
                family.setRelTestGroupName(getItemByParam.getJSONObject(i).getString("rel_test_group_name"));
                family.setCreatedBy("SPTS");
                eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
                QueryResult q = eqptRelTestGroupD.insertEquipmentRelTestGroup(family);
            } else if (count == 1) { //update family name by spts pkid 
                EquipmentRelTestGroup family = new EquipmentRelTestGroup();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid")));
                family.setRelTestGroupName(getItemByParam.getJSONObject(i).getString("rel_test_group_name"));
                eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
                QueryResult q = eqptRelTestGroupD.updateEquipmentRelTestGroupBySptsPkid(family);
            }
        }

        //insert/update global table
        JSONArray getGlobalRelTestGroup = SPTSWebService.getGlobalRelTestGroupAll();
        for (int i = 0; i < getGlobalRelTestGroup.length(); i++) {

            EquipmentGlobalRelTestGroup globalRelTest = new EquipmentGlobalRelTestGroup();
            globalRelTest.setSptsGuid(getGlobalRelTestGroup.getJSONObject(i).getString("GUID"));
            globalRelTest.setCreatedDate(getGlobalRelTestGroup.getJSONObject(i).getString("CreateDatetime").substring(0, 10) + " " + getGlobalRelTestGroup.getJSONObject(i).getString("CreateDatetime").substring(11, 19));
            globalRelTest.setRelTestGroupName(getGlobalRelTestGroup.getJSONObject(i).getString("RelTestGroupName"));
            if (getGlobalRelTestGroup.getJSONObject(i).has("GRTGAuthorizationGUID")) {
                globalRelTest.setGrtgAuthorizationGuid(getGlobalRelTestGroup.getJSONObject(i).getString("GRTGAuthorizationGUID"));
            }
            if (getGlobalRelTestGroup.getJSONObject(i).has("LastModifiedDatetime")) {
                globalRelTest.setModifiedDate(getGlobalRelTestGroup.getJSONObject(i).getString("LastModifiedDatetime").substring(0, 10) + " " + getGlobalRelTestGroup.getJSONObject(i).getString("LastModifiedDatetime").substring(11, 19));
            }
            if (getGlobalRelTestGroup.getJSONObject(i).has("LastModifiedUserName")) {
                globalRelTest.setModifiedBy(getGlobalRelTestGroup.getJSONObject(i).getString("LastModifiedUserName"));
            }
            if (getGlobalRelTestGroup.getJSONObject(i).has("LastModifiedSitePKID")) {
                globalRelTest.setModifiedSiteId(Integer.toString(getGlobalRelTestGroup.getJSONObject(i).getInt("LastModifiedSitePKID")));
            }

            EquipmentGlobalRelTestGroupDAO globalD = new EquipmentGlobalRelTestGroupDAO();
            int countGlobalRelTest = globalD.getCountGlobalRelTestGroupByGuid(getGlobalRelTestGroup.getJSONObject(i).getString("GUID"));
            if (countGlobalRelTest == 0) {
//                LOGGER.info("+++++++++++insert global rel test group++++++++");
                globalD = new EquipmentGlobalRelTestGroupDAO();
                QueryResult qGlobalRel = globalD.insertEquipmentGlobalRelTestGroup(globalRelTest);
            } else if (countGlobalRelTest == 1) {
//                LOGGER.info("+++++++update global rel test group+++++++++");
                globalD = new EquipmentGlobalRelTestGroupDAO();
                QueryResult qGlobalRel = globalD.updateEquipmentGlobalRelTestGroupByGuid(globalRelTest);
            }
        }

        EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
        List<EquipmentRelTestGroup> eqptRelTestGroup = eqptRelTestGroupD.getEquipmentRelTestGroupListLeftJoinGlobalTable();
        model.addAttribute("eqptRelTestGroup", eqptRelTestGroup);

        return "equipment/eqptRelTestGroup";
    }

    @RequestMapping(value = "/relTestGroup/save", method = RequestMethod.POST)
    public String relTestGroupSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String relTestGroup
    ) throws IOException {

        //check if exist in DB or not
        EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
        int count = eqptRelTestGroupD.getCountRelTestGroupName(relTestGroup);
        if (count == 0) {
            //insert into SPTS first
            JSONObject params = new JSONObject();
            params.put("relTestGroupName", relTestGroup);
            SPTSResponse sr = SPTSWebService.insertEqptRelTestGroup(params);
            if (sr.getStatus()) {

                //insert into global list in global table
                JSONObject paramGlobal = new JSONObject();
                paramGlobal.put("relTestGroupName", relTestGroup);
                SPTSResponse insertGlobalRelTestGroup = SPTSWebService.insertGlobalRelTestGroup(paramGlobal);
                if (insertGlobalRelTestGroup.getStatus()) {
                    LOGGER.info("save to global rel test group table");
                } else {
                    LOGGER.info("fail save to global rel test group table");
                }

                //get spts pkid first
                JSONObject param1 = new JSONObject();
                param1.put("relTestGroup", relTestGroup);
                JSONArray getItemByParam = SPTSWebService.getEqptRelTestGroupByName(param1);
                String pkid = "";
                for (int i = 0; i < getItemByParam.length(); i++) {
                    pkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
                }
                //insert into local DB
                EquipmentRelTestGroup equipmentfamily = new EquipmentRelTestGroup();
                equipmentfamily.setSptsPkid(pkid);
                equipmentfamily.setRelTestGroupName(relTestGroup);
                equipmentfamily.setCreatedBy(userSession.getFullname());
                EquipmentRelTestGroupDAO equipmentfamilyDAO = new EquipmentRelTestGroupDAO();
                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentRelTestGroup(equipmentfamily);

                if (queryResult.getGeneratedKey().equals("0")) {
                    redirectAttrs.addFlashAttribute("error", "Failed to register " + relTestGroup + ". Pls contact system admin.");
                    return "redirect:/equipment/relTestGroup/add";
                } else {

                    //send email to global-rel-it to manual sync global table via SPTS 
                    List<String> emails = new ArrayList<String>();
                    emails.add("global-rel-it@onsemi.com"); // add email requestor to the list

                    String[] myArray = new String[emails.size()];
                    String[] emailTo = emails.toArray(myArray);
                    //get current date and time
                    LocalDateTime instance = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    String formattedString = formatter.format(instance); //15-02-2022 12:43

                    //send INFORMATION email
                    LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                    EmailSender emailSender = new EmailSender();
                    emailSender.htmlEmailTable(
                            servletContext,
                            "", //user name requestor
                            //                    to, //to
                            emailTo,
                            "New Global Rel Test Group", //subject
                            "<br />"
                            + "Pls be informed that new global rel test group was added thru HEATS."
                            + "<br /> "
                            + "<br /> "
                            + "Rel Test Group: " + relTestGroup
                            + "<br /> "
                            + "Added By: " + userSession.getFullname()
                            + "<br /> "
                            + "Registration Date: " + formattedString
                            + "<br /> "
                            + "<br /> "
                            + "Please manually sync global eqpt rel test group thru SPTS application. Otherwise, SPTS global table will not be updated."
                            + "<br /> "
                            + "<br />Thank you." //msg
                    );

                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + relTestGroup);
                    return "redirect:/equipment/relTestGroup/add";
                }
            } else {
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (sr.getErrorDetail().equals("")) {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                } else {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                }
                model.addAttribute("error", errorMessage);
                model.addAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/relTestGroup/add";
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Duplicate Rel Test Group Name. Pls register with different name");
            return "redirect:/equipment/relTestGroup/add";
        }

    }

    @RequestMapping(value = "/relTestGroup/delete/{eqptRelTestGroupId}", method = RequestMethod.GET)
    public String relTestGroupDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("eqptRelTestGroupId") String eqptRelTestGroupId
    ) throws IOException {
        EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
        EquipmentRelTestGroup relTestGroup = eqptRelTestGroupD.getEquipmentRelTestGroup(eqptRelTestGroupId);
        //delete from SPTS first
        JSONObject params = new JSONObject();
        params.put("relTestGroupName", relTestGroup.getRelTestGroupName());
        SPTSResponse sr = SPTSWebService.deleteEqptRelTestGroup(params);
        if (sr.getStatus()) {

            //get version and guid first table
            String version = "";
            JSONObject paramV = new JSONObject();
            paramV.put("relTestGroupName", relTestGroup.getRelTestGroupName());
            JSONArray getItemByParamV = SPTSWebService.getGlobalRelTestGroupByParam(paramV);
            for (int i = 0; i < getItemByParamV.length(); i++) {
                version = getItemByParamV.getJSONObject(i).getString("Version");
                LOGGER.info("version: " + version);
            }

            String sptsGuid = "";

            String relTestGroupNameWithEscapeJs = StringEscapeUtils.escapeEcmaScript(relTestGroup.getRelTestGroupName());
            EquipmentGlobalRelTestGroupDAO epqtD = new EquipmentGlobalRelTestGroupDAO();
            int countRelTestGroup = epqtD.getCountGlobalRelTestGroup(relTestGroupNameWithEscapeJs);
            if (countRelTestGroup == 1) {
                epqtD = new EquipmentGlobalRelTestGroupDAO();
                EquipmentGlobalRelTestGroup epqtG = epqtD.getEquipmentGlobalRelTestGroupByRelTestGroupName(relTestGroupNameWithEscapeJs);
                sptsGuid = epqtG.getSptsGuid();
            } else {
                sptsGuid = "0";
            }

            LOGGER.info("epqtG.getSptsGuid(): " + sptsGuid);
            //delete spts global table
            JSONObject paramDelete = new JSONObject();
//            paramDelete.put("guID", epqtG.getSptsGuid());
            paramDelete.put("guID", sptsGuid);
            paramDelete.put("version", version);
            SPTSResponse sr1 = SPTSWebService.deleteGlobalRelTestGroup(paramDelete);

            //delete heats global table 
            epqtD = new EquipmentGlobalRelTestGroupDAO();
            QueryResult delete = epqtD.deleteEquipmentGlobalRelTestGroupByGuid(sptsGuid);
//            QueryResult delete = epqtD.deleteEquipmentGlobalRelTestGroupByGuid(epqtG.getSptsGuid());

            //delete from local DB
            eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentRelTestGroup(eqptRelTestGroupId);
            if (queryResult.getResult() == 1) {

                //send email to global-rel-it to manual sync global table via SPTS 
                List<String> emails = new ArrayList<String>();
                emails.add("global-rel-it@onsemi.com"); // add email requestor to the list

                String[] myArray = new String[emails.size()];
                String[] emailTo = emails.toArray(myArray);
                //get current date and time
                LocalDateTime instance = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedString = formatter.format(instance); //15-02-2022 12:43

                //send INFORMATION email
                LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        //                    to, //to
                        emailTo,
                        "Deletion of Global Equipment Rel Test Group", //subject
                        "<br />"
                        + "Pls be informed that new global eqpt rel test group was deleted thru HEATS."
                        + "<br /> "
                        + "<br /> "
                        + "Rel Test Group: " + relTestGroup.getRelTestGroupName()
                        + "<br /> "
                        + "Deleted By: " + userSession.getFullname()
                        + "<br /> "
                        + "Deletion Date: " + formattedString
                        + "<br /> "
                        + "<br /> "
                        + "Please manually sync global eqpt rel test group thru SPTS application. Otherwise, SPTS global table will not be updated."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("success", relTestGroup.getRelTestGroupName() + " successfully deleted");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to delete " + relTestGroup.getRelTestGroupName() + ". Pls contact system admin.");
            }
            return "redirect:/equipment/relTestGroup/add";
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model.addAttribute("error", errorMessage);
            model.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/equipment/relTestGroup/add";
        }
    }

    @RequestMapping(value = "/relTestGroup/insertGlobal/{eqptRelTestGroupId}", method = RequestMethod.GET)
    public String relTestGroupInsertGlobal(
            Model model,
            Locale locale,
            @ModelAttribute UserSession userSession,
            RedirectAttributes redirectAttrs,
            @PathVariable("eqptRelTestGroupId") String eqptRelTestGroupId
    ) throws IOException {
        EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
        EquipmentRelTestGroup relTestGroup = eqptRelTestGroupD.getEquipmentRelTestGroup(eqptRelTestGroupId);

        JSONObject paramF = new JSONObject();
        paramF.put("relTestGroupName", relTestGroup.getRelTestGroupName());
        JSONArray getItemByParamF = SPTSWebService.getGlobalRelTestGroupByParam(paramF);

        if (getItemByParamF.length() == 0) {

            //insert into global list in global table
            JSONObject paramGlobal = new JSONObject();
            paramGlobal.put("relTestGroupName", relTestGroup.getRelTestGroupName());
            SPTSResponse insertGlobalRelTestGroup = SPTSWebService.insertGlobalRelTestGroup(paramGlobal);
            if (insertGlobalRelTestGroup.getStatus()) {
                LOGGER.info("save to global family table");

                //insert into heats global table
                JSONObject paramV = new JSONObject();
                paramV.put("relTestGroupName", relTestGroup.getRelTestGroupName());
                JSONArray getItemByParamV = SPTSWebService.getGlobalRelTestGroupByParam(paramV);
                for (int i = 0; i < getItemByParamV.length(); i++) {
                    EquipmentGlobalRelTestGroup globalRelTest = new EquipmentGlobalRelTestGroup();
                    globalRelTest.setSptsGuid(getItemByParamV.getJSONObject(i).getString("GUID"));
                    globalRelTest.setCreatedDate(getItemByParamV.getJSONObject(i).getString("CreateDatetime").substring(0, 10) + " " + getItemByParamV.getJSONObject(i).getString("CreateDatetime").substring(11, 19));
                    globalRelTest.setRelTestGroupName(getItemByParamV.getJSONObject(i).getString("RelTestGroupName"));
                    if (getItemByParamV.getJSONObject(i).has("GRTGAuthorizationGUID")) {
                        globalRelTest.setGrtgAuthorizationGuid(getItemByParamV.getJSONObject(i).getString("GRTGAuthorizationGUID"));
                    }
                    if (getItemByParamV.getJSONObject(i).has("LastModifiedDatetime")) {
                        globalRelTest.setModifiedDate(getItemByParamV.getJSONObject(i).getString("LastModifiedDatetime").substring(0, 10) + " " + getItemByParamV.getJSONObject(i).getString("LastModifiedDatetime").substring(11, 19));
                    }
                    if (getItemByParamV.getJSONObject(i).has("LastModifiedUserName")) {
                        globalRelTest.setModifiedBy(getItemByParamV.getJSONObject(i).getString("LastModifiedUserName"));
                    }
                    if (getItemByParamV.getJSONObject(i).has("LastModifiedSitePKID")) {
                        globalRelTest.setModifiedSiteId(Integer.toString(getItemByParamV.getJSONObject(i).getInt("LastModifiedSitePKID")));
                    }

                    EquipmentGlobalRelTestGroupDAO globalD = new EquipmentGlobalRelTestGroupDAO();
                    int countGlobalRelTest = globalD.getCountGlobalRelTestGroupByGuid(getItemByParamV.getJSONObject(i).getString("GUID"));
                    if (countGlobalRelTest == 0) {
                        LOGGER.info("+++++++++++insert global rel test group++++++++");
                        globalD = new EquipmentGlobalRelTestGroupDAO();
                        QueryResult qGlobalRel = globalD.insertEquipmentGlobalRelTestGroup(globalRelTest);
                    } else if (countGlobalRelTest == 1) {
                        LOGGER.info("+++++++update global rel test group+++++++++");
                        globalD = new EquipmentGlobalRelTestGroupDAO();
                        QueryResult qGlobalRel = globalD.updateEquipmentGlobalRelTestGroupByGuid(globalRelTest);
                    }
                }
                redirectAttrs.addFlashAttribute("success", relTestGroup.getRelTestGroupName() + " successfully added into Global List");
                return "redirect:/equipment/relTestGroup/add";
            } else {
                LOGGER.info("fail save to global rel test group table");
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(paramGlobal.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (insertGlobalRelTestGroup.getErrorDetail().equals("")) {
                    errorMessage = insertGlobalRelTestGroup.getErrorCode() + " - " + insertGlobalRelTestGroup.getErrorMessage();
                } else {
                    errorMessage = insertGlobalRelTestGroup.getErrorCode() + " - " + insertGlobalRelTestGroup.getErrorDetail();
                }
                redirectAttrs.addFlashAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/relTestGroup/add";
            }

        } else {
            redirectAttrs.addFlashAttribute("error", relTestGroup.getRelTestGroupName() + " already exist in the Global List. Pls contact system admin.");
            return "redirect:/equipment/relTestGroup/add";
        }
    }

    @RequestMapping(value = "/monitoring/add", method = RequestMethod.GET)
    public String monitoringAdd(Model model, @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userEqptMonAdd", userSession.getEqptMonAdd());
        model.addAttribute("userEqptMonDelete", userSession.getEqptMonDelete());

        //retrieve from SPTS first
        JSONObject params = new JSONObject();
        params.put("Name", "");
        JSONArray getItemByParam = SPTSWebService.getEqptMonitoringByParam(params);

        for (int i = 0; i < getItemByParam.length(); i++) {
            EquipmentMonitoringDAO eqptRelTestGroupD = new EquipmentMonitoringDAO();
            int count = eqptRelTestGroupD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
            if (count == 0) { //insert into HEATS db
                EquipmentMonitoring family = new EquipmentMonitoring();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                family.setName(getItemByParam.getJSONObject(i).getString("Name"));
                family.setCreatedBy("SPTS");
                eqptRelTestGroupD = new EquipmentMonitoringDAO();
                QueryResult q = eqptRelTestGroupD.insertEquipmentMonitoring(family);
            } else if (count == 1) { //update family name by spts pkid 
                EquipmentMonitoring family = new EquipmentMonitoring();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                family.setName(getItemByParam.getJSONObject(i).getString("Name"));
                eqptRelTestGroupD = new EquipmentMonitoringDAO();
                QueryResult q = eqptRelTestGroupD.updateEquipmentMonitoringBySptsPkid(family);
            }
        }

        EquipmentMonitoringDAO eqptRelTestGroupD = new EquipmentMonitoringDAO();
        List<EquipmentMonitoring> eqptMonitoring = eqptRelTestGroupD.getEquipmentMonitoringList();
        model.addAttribute("eqptMonitoring", eqptMonitoring);

        return "equipment/eqptMonitoring";
    }

    @RequestMapping(value = "/monitoring/save", method = RequestMethod.POST)
    public String monitoringSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String monitoring
    ) throws IOException {

        //check if exist in DB or not
        EquipmentMonitoringDAO eqptRelTestGroupD = new EquipmentMonitoringDAO();
        int count = eqptRelTestGroupD.getCountMonitoringName(monitoring);
        if (count == 0) {

            //insert into SPTS first
            JSONObject params = new JSONObject();
            params.put("name", monitoring);
            SPTSResponse sr = SPTSWebService.insertEqptMonitoring(params);
            LOGGER.info("sr.getResponseId: " + sr.getResponseId());
            if (sr.getResponseId() > 0) { //insert into local DB

                EquipmentMonitoring equipmentfamily = new EquipmentMonitoring();
                equipmentfamily.setSptsPkid(sr.getResponseId().toString());
                equipmentfamily.setName(monitoring);
                equipmentfamily.setCreatedBy(userSession.getFullname());
                EquipmentMonitoringDAO equipmentfamilyDAO = new EquipmentMonitoringDAO();
                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentMonitoring(equipmentfamily);

                if (queryResult.getGeneratedKey().equals("0")) {
                    redirectAttrs.addFlashAttribute("error", "Failed to register " + monitoring + ". Pls contact system admin.");
                    return "redirect:/equipment/monitoring/add";
                } else {
                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + monitoring);
                    return "redirect:/equipment/monitoring/add";
                }
            } else {
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (sr.getErrorDetail().equals("")) {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                } else {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                }
                model.addAttribute("error", errorMessage);
                model.addAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/monitoring/add";
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Duplicate Monitoring Name. Pls register with different name");
            return "redirect:/equipment/monitoring/add";
        }

    }

    @RequestMapping(value = "/monitoring/delete/{monitoringId}", method = RequestMethod.GET)
    public String monitoringIdDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("monitoringId") String monitoringId
    ) throws IOException {
        EquipmentMonitoringDAO eqptRelTestGroupD = new EquipmentMonitoringDAO();
        EquipmentMonitoring equipmentfamily = eqptRelTestGroupD.getEquipmentMonitoring(monitoringId);

//        eqptRelTestGroupD = new EquipmentMonitoringDAO();
//        QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentMonitoring(monitoringId);
//        if (queryResult.getResult() == 1) {
//            redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
//        } else {
//            redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
//        }
//        return "redirect:/equipment/monitoring/add";
        JSONObject param = new JSONObject();
        param.put("pkid", equipmentfamily.getSptsPkid());
        JSONArray getItemByParam = SPTSWebService.getEqptMonitoringByPkid(param);
        String version = "";
        for (int i = 0; i < getItemByParam.length(); i++) {
            version = getItemByParam.getJSONObject(i).getString("Version");
        }
        JSONObject params = new JSONObject();
        params.put("pkid", equipmentfamily.getSptsPkid());
        params.put("version", version);
        SPTSResponse sr = SPTSWebService.deleteEqptMonitoring(params);
        if (sr.getStatus()) { //delete from local DB
            eqptRelTestGroupD = new EquipmentMonitoringDAO();
            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentMonitoring(monitoringId);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
            }
            return "redirect:/equipment/monitoring/add";
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model.addAttribute("error", errorMessage);
            model.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/equipment/monitoring/add";
        }
    }

    @RequestMapping(value = "/tech/add", method = RequestMethod.GET)
    public String techAdd(Model model, @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userEqptTechAdd", userSession.getEqptTechAdd());
        model.addAttribute("userEqptTechDelete", userSession.getEqptTechDelete());

        //retrieve from SPTS first
        JSONObject params = new JSONObject();
        params.put("Name", "");
        JSONArray getItemByParam = SPTSWebService.getEqptTechByParam(params);

        for (int i = 0; i < getItemByParam.length(); i++) {
            EquipmentTechDAO eqptRelTestGroupD = new EquipmentTechDAO();
            int count = eqptRelTestGroupD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
            if (count == 0) { //insert into HEATS db
                EquipmentTech family = new EquipmentTech();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                family.setName(getItemByParam.getJSONObject(i).getString("Name"));
                family.setCreatedBy("SPTS");
                eqptRelTestGroupD = new EquipmentTechDAO();
                QueryResult q = eqptRelTestGroupD.insertEquipmentTech(family);
            } else if (count == 1) { //update family name by spts pkid 
                EquipmentTech family = new EquipmentTech();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                family.setName(getItemByParam.getJSONObject(i).getString("Name"));
                eqptRelTestGroupD = new EquipmentTechDAO();
                QueryResult q = eqptRelTestGroupD.updateEquipmentTechBySptsPkid(family);
            }
        }

        EquipmentTechDAO eqptRelTestGroupD = new EquipmentTechDAO();
        List<EquipmentTech> eqptTech = eqptRelTestGroupD.getEquipmentTechList();
        model.addAttribute("eqptTech", eqptTech);

        return "equipment/eqptTech";
    }

    @RequestMapping(value = "/tech/save", method = RequestMethod.POST)
    public String techSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String tech
    ) throws IOException {

        //check if exist in DB or not
        EquipmentTechDAO eqptRelTestGroupD = new EquipmentTechDAO();
        int count = eqptRelTestGroupD.getCountTechName(tech);
        if (count == 0) {

            //insert into SPTS first
            JSONObject params = new JSONObject();
            params.put("name", tech);
            SPTSResponse sr = SPTSWebService.insertEqptTech(params);
//            LOGGER.info("sr.getResponseId: " + sr.getResponseId());
            if (sr.getResponseId() > 0) { //insert into local DB

                EquipmentTech equipmentfamily = new EquipmentTech();
                equipmentfamily.setSptsPkid(sr.getResponseId().toString());
                equipmentfamily.setName(tech);
                equipmentfamily.setCreatedBy(userSession.getFullname());
                EquipmentTechDAO equipmentfamilyDAO = new EquipmentTechDAO();
                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentTech(equipmentfamily);

                if (queryResult.getGeneratedKey().equals("0")) {
                    redirectAttrs.addFlashAttribute("error", "Failed to register " + tech + ". Pls contact system admin.");
                    return "redirect:/equipment/tech/add";
                } else {
                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + tech);
                    return "redirect:/equipment/tech/add";
                }
            } else {
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (sr.getErrorDetail().equals("")) {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                } else {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                }
                model.addAttribute("error", errorMessage);
                model.addAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/tech/add";
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Duplicate Tech Name. Pls register with different name");
            return "redirect:/equipment/tech/add";
        }

    }

    @RequestMapping(value = "/tech/delete/{techId}", method = RequestMethod.GET)
    public String techDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("techId") String techId
    ) throws IOException {
        EquipmentTechDAO eqptRelTestGroupD = new EquipmentTechDAO();
        EquipmentTech equipmentfamily = eqptRelTestGroupD.getEquipmentTech(techId);

//        eqptRelTestGroupD = new EquipmentTechDAO();
//        QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentTech(techId);
//        if (queryResult.getResult() == 1) {
//            redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
//        } else {
//            redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
//        }
//        return "redirect:/equipment/tech/add";
        JSONObject param = new JSONObject();
        param.put("pkid", equipmentfamily.getSptsPkid());
        JSONArray getItemByParam = SPTSWebService.getEqptTechByPkid(param);
        String version = "";
        for (int i = 0; i < getItemByParam.length(); i++) {
            version = getItemByParam.getJSONObject(i).getString("Version");
        }
        JSONObject params = new JSONObject();
        params.put("pkid", equipmentfamily.getSptsPkid());
        params.put("version", version);
        SPTSResponse sr = SPTSWebService.deleteEqptTech(params);
        if (sr.getStatus()) { //delete from local DB
            eqptRelTestGroupD = new EquipmentTechDAO();
            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentTech(techId);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
            }
            return "redirect:/equipment/tech/add";
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model.addAttribute("error", errorMessage);
            model.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/equipment/tech/add";
        }
    }

    @RequestMapping(value = "/viMonitoring/add", method = RequestMethod.GET)
    public String viMonitoringAdd(Model model, @ModelAttribute UserSession userSession) throws IOException {

        model.addAttribute("userEqptViMonAdd", userSession.getEqptViMonAdd());
        model.addAttribute("userEqptViMonDelete", userSession.getEqptViMonDelete());

        //retrieve from SPTS first
        JSONObject params = new JSONObject();
        params.put("Name", "");
        JSONArray getItemByParam = SPTSWebService.getEqptViMonitoringByParam(params);

        for (int i = 0; i < getItemByParam.length(); i++) {
            EquipmentViMonitoringDAO eqptRelTestGroupD = new EquipmentViMonitoringDAO();
            int count = eqptRelTestGroupD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
            if (count == 0) { //insert into HEATS db
                EquipmentViMonitoring family = new EquipmentViMonitoring();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                family.setName(getItemByParam.getJSONObject(i).getString("Name"));
                family.setCreatedBy("SPTS");
                eqptRelTestGroupD = new EquipmentViMonitoringDAO();
                QueryResult q = eqptRelTestGroupD.insertEquipmentViMonitoring(family);
            } else if (count == 1) { //update family name by spts pkid 
                EquipmentViMonitoring family = new EquipmentViMonitoring();
                family.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                family.setName(getItemByParam.getJSONObject(i).getString("Name"));
                eqptRelTestGroupD = new EquipmentViMonitoringDAO();
                QueryResult q = eqptRelTestGroupD.updateEquipmentViMonitoringBySptsPkid(family);
            }
        }

        EquipmentViMonitoringDAO eqptRelTestGroupD = new EquipmentViMonitoringDAO();
        List<EquipmentViMonitoring> eqptViMonitoring = eqptRelTestGroupD.getEquipmentViMonitoringList();
        model.addAttribute("eqptViMonitoring", eqptViMonitoring);

        return "equipment/eqptViMonitoring";
    }

    @RequestMapping(value = "/viMonitoring/save", method = RequestMethod.POST)
    public String viMonitoringSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String viMonitoring
    ) throws IOException {

        //check if exist in DB or not
        EquipmentViMonitoringDAO eqptRelTestGroupD = new EquipmentViMonitoringDAO();
        int count = eqptRelTestGroupD.getCountViMonitoringName(viMonitoring);
        if (count == 0) {

            //insert into SPTS first
            JSONObject params = new JSONObject();
            params.put("name", viMonitoring);
            SPTSResponse sr = SPTSWebService.insertEqptViMonitoring(params);
//            LOGGER.info("sr.getResponseId: " + sr.getResponseId());
            if (sr.getResponseId() > 0) { //insert into local DB

                EquipmentViMonitoring equipmentfamily = new EquipmentViMonitoring();
                equipmentfamily.setSptsPkid(sr.getResponseId().toString());
                equipmentfamily.setName(viMonitoring);
                equipmentfamily.setCreatedBy(userSession.getFullname());
                EquipmentViMonitoringDAO equipmentfamilyDAO = new EquipmentViMonitoringDAO();
                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentViMonitoring(equipmentfamily);

                if (queryResult.getGeneratedKey().equals("0")) {
                    redirectAttrs.addFlashAttribute("error", "Failed to register " + viMonitoring + ". Pls contact system admin.");
                    return "redirect:/equipment/viMonitoring/add";
                } else {
                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + viMonitoring);
                    return "redirect:/equipment/viMonitoring/add";
                }
            } else {
                LinkedHashMap<String, String> item2;
                ObjectMapper mapper = new ObjectMapper();
                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
                });
                String errorMessage;
                if (sr.getErrorDetail().equals("")) {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
                } else {
                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
                }
                model.addAttribute("error", errorMessage);
                model.addAttribute("item2", item2);
                redirectAttrs.addFlashAttribute("error", errorMessage);
                return "redirect:/equipment/viMonitoring/add";
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Duplicate VI Monitoring Name. Pls register with different name");
            return "redirect:/equipment/viMonitoring/add";
        }

    }

    @RequestMapping(value = "/viMonitoring/delete/{viMonId}", method = RequestMethod.GET)
    public String viMonitoringDelete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("viMonId") String viMonId
    ) throws IOException {
        EquipmentViMonitoringDAO eqptRelTestGroupD = new EquipmentViMonitoringDAO();
        EquipmentViMonitoring equipmentfamily = eqptRelTestGroupD.getEquipmentViMonitoring(viMonId);

//        eqptRelTestGroupD = new EquipmentViMonitoringDAO();
//        QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentViMonitoring(viMonId);
//        if (queryResult.getResult() == 1) {
//            redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
//        } else {
//            redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
//        }
//        return "redirect:/equipment/viMonitoring/add";
        //hold until JF Lim provide primitive parameter for insert function
        //retrieve from SPTS first
        JSONObject param = new JSONObject();
        param.put("pkid", equipmentfamily.getSptsPkid());
        JSONArray getItemByParam = SPTSWebService.getEqptViMonitoringByPkid(param);
        String version = "";
        for (int i = 0; i < getItemByParam.length(); i++) {
            version = getItemByParam.getJSONObject(i).getString("Version");
        }
        JSONObject params = new JSONObject();
        params.put("pkid", equipmentfamily.getSptsPkid());
        params.put("version", version);
        SPTSResponse sr = SPTSWebService.deleteEqptViMonitoring(params);
        if (sr.getStatus()) { //delete from local DB
            eqptRelTestGroupD = new EquipmentViMonitoringDAO();
            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentViMonitoring(viMonId);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
            }
            return "redirect:/equipment/viMonitoring/add";
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model.addAttribute("error", errorMessage);
            model.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/equipment/viMonitoring/add";
        }
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String eqptId,
            @RequestParam(required = false) String relTestGroup,
            @RequestParam(required = false) String familyName,
            @RequestParam(required = false) String eqptType,
            @RequestParam(required = false) String eqptManufacturer,
            @RequestParam(required = false) String eqptModel,
            @RequestParam(required = false) String eqptTech,
            @RequestParam(required = false) String eqptMon,
            @RequestParam(required = false) String eqptViMon,
            @RequestParam(required = false) String eqptStatus,
            @RequestParam(required = false) String cbmsType) throws IOException {

        String query = "";
        int count = 0;

        EquipmentDAO eqptD = new EquipmentDAO();
        List<Equipment> eqptIdList = eqptD.getEqptId("");
        model.addAttribute("eqptIdList", eqptIdList);

        eqptD = new EquipmentDAO();
        List<Equipment> eqptManufacturerList = eqptD.getEqptManufacturer("");
        model.addAttribute("eqptManufacturerList", eqptManufacturerList);

        eqptD = new EquipmentDAO();
        List<Equipment> eqptModelList = eqptD.getEqptModel("");
        model.addAttribute("eqptModelList", eqptModelList);

        EquipmentFamilyDAO eqptFD = new EquipmentFamilyDAO();
        List<EquipmentFamily> eqptFamilyList = eqptFD.getEquipmentFamilyList("");
        model.addAttribute("eqptFamilyList", eqptFamilyList);

        EquipmentRelTestGroupDAO eqptRD = new EquipmentRelTestGroupDAO();
        List<EquipmentRelTestGroup> eqptRelTestGroupList = eqptRD.getEquipmentRelTestGroupList("");
        model.addAttribute("eqptRelTestGroupList", eqptRelTestGroupList);

        EquipmentTechDAO eqptTD = new EquipmentTechDAO();
        List<EquipmentTech> eqptTechList = eqptTD.getEquipmentTechList("");
        model.addAttribute("eqptTechList", eqptTechList);

        EquipmentMonitoringDAO eqptMD = new EquipmentMonitoringDAO();
        List<EquipmentMonitoring> eqptMonList = eqptMD.getEquipmentMonitoringList("");
        model.addAttribute("eqptMonList", eqptMonList);

        EquipmentViMonitoringDAO eqptVD = new EquipmentViMonitoringDAO();
        List<EquipmentViMonitoring> eqptViMonList = eqptVD.getEquipmentViMonitoringList("");
        model.addAttribute("eqptViMonList", eqptViMonList);

        if (eqptId != null) {
            if (!eqptId.equals("")) {
                count++;
                if ("All".equals(eqptId)) {
                    if (count == 1) {
                        query = query + " WHERE equipment_id LIKE \'%%' ";
                    } else if (count > 1) {
                        query = query + " AND equipment_id LIKE \'%%' ";
                    }
                } else {
                    if (count == 1) {
                        query = query + " WHERE equipment_id = '" + eqptId + "\' ";
                    } else if (count > 1) {
                        query = query + " AND equipment_id = ''" + eqptId + "\' ";
                    }
                }

            }
        }

        if (relTestGroup != null) {
            if (!relTestGroup.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE rel_test_group_pkid = '" + relTestGroup + "\' ";
                } else if (count > 1) {
                    query = query + " AND rel_test_group_pkid = '" + relTestGroup + "\' ";
                }
            }
        }

        if (familyName != null) {
            if (!familyName.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE family_pkid = '" + familyName + "\' ";
                } else if (count > 1) {
                    query = query + " AND family_pkid = '" + familyName + "\' ";
                }
            }
        }

        if (eqptType != null) {
            if (!eqptType.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equipment_type = '" + eqptType + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_type = '" + eqptType + "\' ";
                }
            }
        }

        if (eqptManufacturer != null) {
            if (!eqptManufacturer.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equipment_manufacturer = '" + eqptManufacturer + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_manufacturer = '" + eqptManufacturer + "\' ";
                }
            }
        }

        if (eqptModel != null) {
            if (!eqptModel.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equipment_model = '" + eqptModel + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_model = '" + eqptModel + "\' ";
                }
            }
        }

        if (eqptTech != null) {
            if (!eqptTech.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equip_tech_pkid = '" + eqptTech + "\' ";
                } else if (count > 1) {
                    query = query + " AND equip_tech_pkid = '" + eqptTech + "\' ";
                }
            }
        }

        if (eqptMon != null) {
            if (!eqptMon.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equip_monitoring_pkid = '" + eqptMon + "\' ";
                } else if (count > 1) {
                    query = query + " AND equip_monitoring_pkid = '" + eqptMon + "\' ";
                }
            }
        }

        if (eqptViMon != null) {
            if (!eqptViMon.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE vi_monitoring_pkid = '" + eqptViMon + "\' ";
                } else if (count > 1) {
                    query = query + " AND vi_monitoring_pkid = '" + eqptViMon + "\' ";
                }
            }
        }

        if (eqptStatus != null) {
            if (!eqptStatus.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE current_status = '" + eqptStatus + "\' ";
                } else if (count > 1) {
                    query = query + " AND current_status = '" + eqptStatus + "\' ";
                }
            }
        }

        if (cbmsType != null) {
            if (!cbmsType.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE cbms_type = '" + cbmsType + "\' ";
                } else if (count > 1) {
                    query = query + " AND cbms_type = '" + cbmsType + "\' ";
                }
            }
        }

        String finalQuery = "";

        if (count != 0) {
            finalQuery = "SELECT eq.*, fa.family_name AS familyName, rt.rel_test_group_name AS relTestGroup, te.name AS eqptTech, mo.name AS eqptMon, vi.name AS eqptViMon, "
                    + "IF(eq.current_status = '1', 'Active','Inactive') AS statusName, "
                    + "IF(eq.equipment_type = '1', 'Life','Environment') AS eqptType, "
                    + "IF(eq.cbms_type = '1', 'Yes','No') AS cbmsType "
                    + "FROM equipment eq "
                    + "LEFT JOIN equipment_family fa ON eq.family_pkid = fa.spts_pkid "
                    + "LEFT JOIN equipment_rel_test_group rt ON eq.rel_test_group_pkid = rt.spts_pkid "
                    + "LEFT JOIN equipment_tech te ON eq.equip_tech_pkid = te.spts_pkid "
                    + "LEFT JOIN equipment_monitoring mo ON eq.equip_monitoring_pkid = mo.spts_pkid "
                    + "LEFT JOIN equipment_vi_monitoring vi ON eq.vi_monitoring_pkid = vi.spts_pkid "
                    + query
                    + " ORDER BY eq.equipment_id";

        } else {
            finalQuery = "SELECT * FROM equipment WHERE flag = '1000'";
        }

        System.out.println("finalQuery: " + finalQuery);

        eqptD = new EquipmentDAO();
        List<Equipment> resultQuery = eqptD.getEquipmentforQuery(finalQuery);

        model.addAttribute("resultQuery", resultQuery);

        return "equipment/query";
    }
}
