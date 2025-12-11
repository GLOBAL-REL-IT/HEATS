package com.onsemi.mib.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.EquipmentDAO;
import com.onsemi.mib.dao.EquipmentFamilyDAO;
import com.onsemi.mib.dao.EquipmentMonitoringDAO;
import com.onsemi.mib.dao.EquipmentRelTestGroupDAO;
import com.onsemi.mib.dao.EquipmentTechDAO;
import com.onsemi.mib.dao.EquipmentViMonitoringDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.ItemStorageFactoryDAO;
import com.onsemi.mib.dao.ItemTransactionDAO;
import com.onsemi.mib.model.Equipment;
import com.onsemi.mib.model.EquipmentFamily;
import com.onsemi.mib.model.EquipmentMonitoring;
import com.onsemi.mib.model.EquipmentRelTestGroup;
import com.onsemi.mib.model.EquipmentSlot;
import com.onsemi.mib.model.EquipmentTech;
import com.onsemi.mib.model.EquipmentViMonitoring;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.ItemStorageFactory;
import com.onsemi.mib.model.ItemTransaction;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSResponse;
import com.onsemi.mib.tools.SPTSWebService;
import com.onsemi.mib.tools.SystemUtil;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.ServletContext;
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
import org.springframework.web.servlet.ModelAndView;
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
        model.addAttribute("userEqptFamilyAdd", userSession.getEqptFamilyAdd());
        model.addAttribute("userEqptFamilyDelete", userSession.getEqptFamilyDelete());
        model.addAttribute("userEqptRelTestGroupAdd", userSession.getEqptRelTestGroupAdd());
        model.addAttribute("userEqptRelTestGroupDelete", userSession.getEqptRelTestGroupDelete());
        model.addAttribute("userEqptTechAdd", userSession.getEqptTechAdd());
        model.addAttribute("userEqptTechDelete", userSession.getEqptTechDelete());
        model.addAttribute("userEqptMonAdd", userSession.getEqptMonAdd());
        model.addAttribute("userEqptMonDelete", userSession.getEqptMonDelete());
        model.addAttribute("userEqptViMonAdd", userSession.getEqptViMonAdd());
        model.addAttribute("userEqptViMonDelete", userSession.getEqptViMonDelete());

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
            EquipmentSlot eqptSlot = new EquipmentSlot();

            //check need to insert or update
            EquipmentDAO eqptD = new EquipmentDAO();
            int countPkid = eqptD.getCountPkid(pkid);
            LOGGER.info("pkid: " + pkid);
            if (countPkid == 0) { //insert
                eqptD = new EquipmentDAO();
                QueryResult q = eqptD.insertEquipment(eqpt);
                countAdd += q.getResult();
            } else if (countPkid == 1) { //update
                eqptD = new EquipmentDAO();
                QueryResult q = eqptD.updateEquipmentBySptsPkid(eqpt);
                countUpdate += q.getResult();
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
    public Item getHardwareDetail(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String pkID
    ) throws IOException {

        //check if any info from SPTS need to update to MIB DB
        //update SPTS data per item type into MIB DB
        JSONObject params = new JSONObject();
        params.put("pkID", pkID);
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);

        int count = 0;
        int countAdd = 0;

        //insert into database
        for (int i = 0; i < getItemByParam.length(); i++) {

            ItemDAO hwD = new ItemDAO();
            int countPkid = hwD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
            if (countPkid == 1) {

                Item hw = new Item();
//                if (getItemByParam.getJSONObject(i).has("PKID")) {
                hw.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
//                }
                hw.setItemType(getItemByParam.getJSONObject(i).getString("ItemType"));
                hw.setItemId(getItemByParam.getJSONObject(i).getString("ItemID"));
                hw.setItemName(getItemByParam.getJSONObject(i).getString("ItemName"));
                if (getItemByParam.getJSONObject(i).has("SubType")) {
                    hw.setSubType(getItemByParam.getJSONObject(i).getString("SubType"));
                }
                if (getItemByParam.getJSONObject(i).has("ALUHrs")) {
                    hw.setAluHrs(Double.toString(getItemByParam.getJSONObject(i).getDouble("ALUHrs")));
                }
                if (getItemByParam.getJSONObject(i).has("AssemblyID")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("AssemblyID");
                    if (assembly instanceof String) {
                        hw.setAssemblyId(getItemByParam.getJSONObject(i).getString("AssemblyID"));
                    } else {
                        hw.setAssemblyId(Integer.toString(getItemByParam.getJSONObject(i).getInt("AssemblyID")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("Complexity")) {
                    hw.setComplexity(getItemByParam.getJSONObject(i).getString("Complexity"));
                }
                if (getItemByParam.getJSONObject(i).has("EquipmentManufacturer")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("EquipmentManufacturer");
                    if (assembly instanceof String) {
                        hw.setEquipmentManufacturer(getItemByParam.getJSONObject(i).getString("EquipmentManufacturer"));
                    } else {
                        hw.setEquipmentManufacturer(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipmentManufacturer")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("EquipmentModel")) {
                    Object eqptModel = getItemByParam.getJSONObject(i).get("EquipmentModel");
                    if (eqptModel instanceof String) {
                        hw.setEquipmentModel(getItemByParam.getJSONObject(i).getString("EquipmentModel"));
                    } else {
                        hw.setEquipmentModel(Integer.toString(getItemByParam.getJSONObject(i).getInt("EquipmentModel")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("EquipmentType")) {
                    hw.setEquipmentType(getItemByParam.getJSONObject(i).getString("EquipmentType"));
                }
                if (getItemByParam.getJSONObject(i).has("ExpirationDate")) {
                    String date1 = getItemByParam.getJSONObject(i).getString("ExpirationDate").substring(0, 10);
                    hw.setExpirationDate(date1);
                }
                if (getItemByParam.getJSONObject(i).has("ExternalRecleaningQty")) {
                    hw.setExternalRecleanQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("ExternalRecleaningQty")));
                }
                if (getItemByParam.getJSONObject(i).has("ExternalCleaningQty")) {
                    hw.setExternalCleanQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("ExternalCleaningQty")));
                }
                if (getItemByParam.getJSONObject(i).has("InternalCleaningQty")) {
                    hw.setInternalCleanQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("InternalCleaningQty")));
                }
                if (getItemByParam.getJSONObject(i).has("InternalRecleaningQty")) {
                    hw.setInternalRecleanQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("InternalRecleaningQty")));
                }
                if (getItemByParam.getJSONObject(i).has("IsConsumeable")) {
                    hw.setIsConsumable(Boolean.toString(getItemByParam.getJSONObject(i).getBoolean("IsConsumeable")));
                }
                if (getItemByParam.getJSONObject(i).has("IsCritical")) {
                    hw.setIsCritical(Boolean.toString(getItemByParam.getJSONObject(i).getBoolean("IsCritical")));
                }
                if (getItemByParam.getJSONObject(i).has("Manufacturer")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("Manufacturer");
                    if (assembly instanceof String) {
                        hw.setManufacturer(getItemByParam.getJSONObject(i).getString("Manufacturer"));
                    } else {
                        hw.setManufacturer(Integer.toString(getItemByParam.getJSONObject(i).getInt("Manufacturer")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("MaxQty")) {
                    hw.setMaxQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("MaxQty")));
                }
                if (getItemByParam.getJSONObject(i).has("MinQty")) {
                    hw.setMinQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("MinQty")));
                }
                if (getItemByParam.getJSONObject(i).has("Model")) {

                    Object modelSpts = getItemByParam.getJSONObject(i).get("Model");
                    if (modelSpts instanceof String) {
                        hw.setModel(getItemByParam.getJSONObject(i).getString("Model"));
                    } else {
                        hw.setModel(Integer.toString(getItemByParam.getJSONObject(i).getInt("Model")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("OnHandQty")) {
                    hw.setOnHandQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OnHandQty")));
                }
                if (getItemByParam.getJSONObject(i).has("OtherONQty")) {
                    hw.setOtherOnsemiQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OtherONQty")));
                }
                if (getItemByParam.getJSONObject(i).has("OtherQty")) {
                    hw.setOtherQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OtherQty")));
                }
                if (getItemByParam.getJSONObject(i).has("PMWW1")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("PMWW1");
                    if (assembly instanceof String) {
                        hw.setPmWw1(getItemByParam.getJSONObject(i).getString("PMWW1"));
                    } else {
                        hw.setPmWw1(Integer.toString(getItemByParam.getJSONObject(i).getInt("PMWW1")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("PMWW2")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("PMWW2");
                    if (assembly instanceof String) {
                        hw.setPmWw2(getItemByParam.getJSONObject(i).getString("PMWW2"));
                    } else {
                        hw.setPmWw2(Integer.toString(getItemByParam.getJSONObject(i).getInt("PMWW2")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("ProductionQty")) {
                    hw.setProductionQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("ProductionQty")));
                }
                if (getItemByParam.getJSONObject(i).has("ProductionStagingQty")) {
                    hw.setProductionStagingQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("ProductionStagingQty")));
                }
                if (getItemByParam.getJSONObject(i).has("QuarantineQty")) {
                    hw.setQuarantineQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("QuarantineQty")));
                }
                if (getItemByParam.getJSONObject(i).has("Rack")) {

                    Object rack = getItemByParam.getJSONObject(i).get("Rack");
                    if (rack instanceof String) {
                        hw.setRack(getItemByParam.getJSONObject(i).getString("Rack"));
                    } else {
                        hw.setRack(Integer.toString(getItemByParam.getJSONObject(i).getInt("Rack")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("Remarks")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("Remarks");
                    if (assembly instanceof String) {
                        hw.setRemarks(getItemByParam.getJSONObject(i).getString("Remarks"));
                    } else {
                        hw.setRemarks(Integer.toString(getItemByParam.getJSONObject(i).getInt("Remarks")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("RepairQty")) {
                    hw.setRepairQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("RepairQty")));
                }
                if (getItemByParam.getJSONObject(i).has("Shelf")) {

                    Object shelfStr = getItemByParam.getJSONObject(i).get("Shelf");
                    if (shelfStr instanceof String) {
                        hw.setShelf(getItemByParam.getJSONObject(i).getString("Shelf"));
                    } else {
                        hw.setShelf(Integer.toString(getItemByParam.getJSONObject(i).getInt("Shelf")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("StatusName")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("StatusName");
                    if (assembly instanceof String) {
                        hw.setStatus(getItemByParam.getJSONObject(i).getString("StatusName"));
                        if ("Scrapped".equals(getItemByParam.getJSONObject(i).getString("StatusName"))) {
                            hw.setFlag("99");
                        } else {
                            hw.setFlag("1");
                        }
                    } else {
                        hw.setStatus(Integer.toString(getItemByParam.getJSONObject(i).getInt("StatusName")));
                        if ("Scrapped".equals(Integer.toString(getItemByParam.getJSONObject(i).getInt("StatusName")))) {
                            hw.setFlag("99");
                        } else {
                            hw.setFlag("1");
                        }
                    }
                }
                if (getItemByParam.getJSONObject(i).has("StorageFactoryQty")) {
                    Object storage = getItemByParam.getJSONObject(i).get("StorageFactoryQty");
                    if (storage instanceof String) {
                        hw.setStorageFactoryQty(getItemByParam.getJSONObject(i).getString("StorageFactoryQty"));
                    } else {
                        hw.setStorageFactoryQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("StorageFactoryQty")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("StressType")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("StressType");
                    if (assembly instanceof String) {
                        hw.setStressType(getItemByParam.getJSONObject(i).getString("StressType"));
                    } else {
                        hw.setStressType(Integer.toString(getItemByParam.getJSONObject(i).getInt("StressType")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("TotalCost")) {
                    hw.setTotalCost(Double.toString(getItemByParam.getJSONObject(i).getDouble("TotalCost")));
                }
                if (getItemByParam.getJSONObject(i).has("TotalQty")) {
                    hw.setTotalQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("TotalQty")));
                }
                if (getItemByParam.getJSONObject(i).has("UnitCost")) {
                    hw.setUnitCost(Double.toString(getItemByParam.getJSONObject(i).getDouble("UnitCost")));
                }
                if (getItemByParam.getJSONObject(i).has("VendorQty")) {
                    hw.setVendorQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("VendorQty")));
                }
                if (getItemByParam.getJSONObject(i).has("DowntimeUnit")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("DowntimeUnit");
                    if (assembly instanceof String) {
                        hw.setDowntimeUnit(getItemByParam.getJSONObject(i).getString("DowntimeUnit"));
                    } else {
                        hw.setDowntimeUnit(Integer.toString(getItemByParam.getJSONObject(i).getInt("DowntimeUnit")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("DowntimeValue")) {
                    hw.setDowntimeValue(Double.toString(getItemByParam.getJSONObject(i).getDouble("DowntimeValue")));
                }
                if (getItemByParam.getJSONObject(i).has("ImplementationCost")) {
                    hw.setImplementationCost(Double.toString(getItemByParam.getJSONObject(i).getDouble("ImplementationCost")));
                }
                if (getItemByParam.getJSONObject(i).has("ManpowerUnit")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("ManpowerUnit");
                    if (assembly instanceof String) {
                        hw.setManpowerUnit(getItemByParam.getJSONObject(i).getString("ManpowerUnit"));
                    } else {
                        hw.setManpowerUnit(Integer.toString(getItemByParam.getJSONObject(i).getInt("ManpowerUnit")));
                    }
                }
                if (getItemByParam.getJSONObject(i).has("ManpowerValue")) {
                    hw.setManpowerValue(Double.toString(getItemByParam.getJSONObject(i).getDouble("ManpowerValue")));
                }

                hwD = new ItemDAO();
                QueryResult q = hwD.updateHardwareDetailFromSpts(hw);
                countAdd += q.getResult();
            }
            count += 1;
        }

        LOGGER.info("Total data: " + count);
        LOGGER.info("Total insert: " + countAdd);

        //set to model
        ItemDAO hwD = new ItemDAO();
        Item hw = hwD.getHardwareDetailByPkid(pkID);

        return hw;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "equipment/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String sptsPkid,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String familyPkid,
            @RequestParam(required = false) String relTestGroupPkid,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String equipmentManufacturer,
            @RequestParam(required = false) String equipmentModel,
            @RequestParam(required = false) String cbmsType,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String equipTechPkid,
            @RequestParam(required = false) String equipCapability,
            @RequestParam(required = false) String equipMonitoringPkid,
            @RequestParam(required = false) String viMonitoringPkid,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag
    ) {
        Equipment equipment = new Equipment();
        equipment.setSptsPkid(sptsPkid);
        equipment.setEquipmentId(equipmentId);
        equipment.setFamilyPkid(familyPkid);
        equipment.setRelTestGroupPkid(relTestGroupPkid);
        equipment.setCurrentStatus(currentStatus);
        equipment.setEquipmentType(equipmentType);
        equipment.setEquipmentManufacturer(equipmentManufacturer);
        equipment.setEquipmentModel(equipmentModel);
        equipment.setCbmsType(cbmsType);
        equipment.setRemarks(remarks);
        equipment.setEquipTechPkid(equipTechPkid);
        equipment.setEquipCapability(equipCapability);
        equipment.setEquipMonitoringPkid(equipMonitoringPkid);
        equipment.setViMonitoringPkid(viMonitoringPkid);
        equipment.setCreatedBy(createdBy);
        equipment.setCreatedDate(createdDate);
        equipment.setFlag(flag);
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        QueryResult queryResult = equipmentDAO.insertEquipment(equipment);
        args = new String[1];
        args[0] = sptsPkid + " - " + equipmentId;
        if (queryResult.getGeneratedKey().equals("0")) {
            model.addAttribute("error", messageSource.getMessage("general.label.save.error", args, locale));
            model.addAttribute("equipment", equipment);
            return "equipment/add";
        } else {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));
            return "redirect:/equipment/edit/" + queryResult.getGeneratedKey();
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
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String sptsPkid,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) String familyPkid,
            @RequestParam(required = false) String relTestGroupPkid,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String equipmentManufacturer,
            @RequestParam(required = false) String equipmentModel,
            @RequestParam(required = false) String cbmsType,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String equipTechPkid,
            @RequestParam(required = false) String equipCapability,
            @RequestParam(required = false) String equipMonitoringPkid,
            @RequestParam(required = false) String viMonitoringPkid,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String flag
    ) {
        Equipment equipment = new Equipment();
        equipment.setId(id);
        equipment.setSptsPkid(sptsPkid);
        equipment.setEquipmentId(equipmentId);
        equipment.setFamilyPkid(familyPkid);
        equipment.setRelTestGroupPkid(relTestGroupPkid);
        equipment.setCurrentStatus(currentStatus);
        equipment.setEquipmentType(equipmentType);
        equipment.setEquipmentManufacturer(equipmentManufacturer);
        equipment.setEquipmentModel(equipmentModel);
        equipment.setCbmsType(cbmsType);
        equipment.setRemarks(remarks);
        equipment.setEquipTechPkid(equipTechPkid);
        equipment.setEquipCapability(equipCapability);
        equipment.setEquipMonitoringPkid(equipMonitoringPkid);
        equipment.setViMonitoringPkid(viMonitoringPkid);
        equipment.setCreatedBy(createdBy);
        equipment.setCreatedDate(createdDate);
        equipment.setFlag(flag);
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        QueryResult queryResult = equipmentDAO.updateEquipment(equipment);
        args = new String[1];
        args[0] = sptsPkid + " - " + equipmentId;
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.update.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.update.error", args, locale));
        }
        return "redirect:/equipment/edit/" + id;
    }

    @RequestMapping(value = "/delete/{equipmentId}", method = RequestMethod.GET)
    public String delete(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @PathVariable("equipmentId") String equipmentId
    ) {
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        Equipment equipment = equipmentDAO.getEquipment(equipmentId);
        equipmentDAO = new EquipmentDAO();
        QueryResult queryResult = equipmentDAO.deleteEquipment(equipmentId);
        args = new String[1];
        args[0] = equipment.getSptsPkid() + " - " + equipment.getEquipmentId();
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.delete.success", args, locale));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.delete.error", args, locale));
        }
        return "redirect:/equipment";
    }

    @RequestMapping(value = "/family/add", method = RequestMethod.GET)
    public String familyAdd(Model model) throws IOException {

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

        EquipmentFamilyDAO eqptFamilyD = new EquipmentFamilyDAO();
        List<EquipmentFamily> eqptFamily = eqptFamilyD.getEquipmentFamilyList();
        model.addAttribute("eqptFamily", eqptFamily);

        return "equipment/eqptFamily";
    }

    @RequestMapping(value = "/family/save", method = RequestMethod.POST)
    public String save(
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
            RedirectAttributes redirectAttrs,
            @PathVariable("equipmentfamilyId") String equipmentfamilyId
    ) throws IOException {
        EquipmentFamilyDAO equipmentfamilyDAO = new EquipmentFamilyDAO();
        EquipmentFamily equipmentfamily = equipmentfamilyDAO.getEquipmentFamily(equipmentfamilyId);
        //delete from SPTS first
        JSONObject params = new JSONObject();
        params.put("familyName", equipmentfamily.getFamilyName());
        SPTSResponse sr = SPTSWebService.deleteEqptFamily(params);
        if (sr.getStatus()) { //delete from local DB
            equipmentfamilyDAO = new EquipmentFamilyDAO();
            QueryResult queryResult = equipmentfamilyDAO.deleteEquipmentFamily(equipmentfamilyId);
            if (queryResult.getResult() == 1) {
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

    @RequestMapping(value = "/relTestGroup/add", method = RequestMethod.GET)
    public String relTestGroupAdd(Model model) throws IOException {

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

        EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
        List<EquipmentRelTestGroup> eqptRelTestGroup = eqptRelTestGroupD.getEquipmentRelTestGroupList();
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
            if (sr.getStatus()) { //insert into local DB

                //get spts pkid first
                JSONObject param1 = new JSONObject();
                param1.put("relTestGroup", relTestGroup);
                JSONArray getItemByParam = SPTSWebService.getEqptRelTestGroupByName(param1);
                String pkid = "";
                for (int i = 0; i < getItemByParam.length(); i++) {
                    pkid = Integer.toString(getItemByParam.getJSONObject(i).getInt("pkid"));
                }
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
            @PathVariable("eqptRelTestGroupId") String eqptRelTestGroupId
    ) throws IOException {
        EquipmentRelTestGroupDAO eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
        EquipmentRelTestGroup equipmentfamily = eqptRelTestGroupD.getEquipmentRelTestGroup(eqptRelTestGroupId);
        //delete from SPTS first
        JSONObject params = new JSONObject();
        params.put("relTestGroupName", equipmentfamily.getRelTestGroupName());
        SPTSResponse sr = SPTSWebService.deleteEqptRelTestGroup(params);
        if (sr.getStatus()) { //delete from local DB
            eqptRelTestGroupD = new EquipmentRelTestGroupDAO();
            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentRelTestGroup(eqptRelTestGroupId);
            if (queryResult.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", equipmentfamily.getRelTestGroupName() + " successfully deleted");
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getRelTestGroupName() + ". Pls contact system admin.");
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

    @RequestMapping(value = "/monitoring/add", method = RequestMethod.GET)
    public String monitoringAdd(Model model) throws IOException {

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

            EquipmentMonitoring equipmentfamily = new EquipmentMonitoring();
            equipmentfamily.setSptsPkid("0");
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

            //hold until JF Lim provide primitive parameter
            //insert into SPTS first
//            JSONObject params = new JSONObject();
//            params.put("Name", monitoring);
//            SPTSResponse sr = SPTSWebService.insertEqptMonitoring(params);
//            LOGGER.info("sr.getResponseId: " + sr.getResponseId());
//            if (sr.getResponseId() > 0) { //insert into local DB
//
//                EquipmentMonitoring equipmentfamily = new EquipmentMonitoring();
//                equipmentfamily.setSptsPkid(sr.getResponseId().toString());
//                equipmentfamily.setName(monitoring);
//                equipmentfamily.setCreatedBy(userSession.getFullname());
//                EquipmentMonitoringDAO equipmentfamilyDAO = new EquipmentMonitoringDAO();
//                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentMonitoring(equipmentfamily);
//
//                if (queryResult.getGeneratedKey().equals("0")) {
//                    redirectAttrs.addFlashAttribute("error", "Failed to register " + monitoring + ". Pls contact system admin.");
//                    return "redirect:/equipment/monitoring/add";
//                } else {
//                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + monitoring);
//                    return "redirect:/equipment/monitoring/add";
//                }
//            } else {
//                LinkedHashMap<String, String> item2;
//                ObjectMapper mapper = new ObjectMapper();
//                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//                });
//                String errorMessage;
//                if (sr.getErrorDetail().equals("")) {
//                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
//                } else {
//                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
//                }
//                model.addAttribute("error", errorMessage);
//                model.addAttribute("item2", item2);
//                redirectAttrs.addFlashAttribute("error", errorMessage);
//                return "redirect:/equipment/monitoring/add";
//            }
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

        eqptRelTestGroupD = new EquipmentMonitoringDAO();
        QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentMonitoring(monitoringId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
        }
        return "redirect:/equipment/monitoring/add";

        //hold until JF Lim provide primitive parameter for insert function
        //retrieve from SPTS first
//        JSONObject param = new JSONObject();
//        param.put("pkid", equipmentfamily.getSptsPkid());
//        JSONArray getItemByParam = SPTSWebService.getEqptMonitoringByPkid(param);
//        String version = "";
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            version = getItemByParam.getJSONObject(i).getString("Version");
//        }
//        JSONObject params = new JSONObject();
//        params.put("pkid", equipmentfamily.getSptsPkid());
//        params.put("version", version);
//        SPTSResponse sr = SPTSWebService.deleteEqptMonitoring(params);
//        if (sr.getStatus()) { //delete from local DB
//            eqptRelTestGroupD = new EquipmentMonitoringDAO();
//            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentMonitoring(monitoringId);
//            if (queryResult.getResult() == 1) {
//                redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
//            } else {
//                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
//            }
//            return "redirect:/equipment/monitoring/add";
//        } else {
//            LinkedHashMap<String, String> item2;
//            ObjectMapper mapper = new ObjectMapper();
//            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//            });
//            String errorMessage;
//            if (sr.getErrorDetail().equals("")) {
//                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
//            } else {
//                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
//            }
//            model.addAttribute("error", errorMessage);
//            model.addAttribute("item2", item2);
//            redirectAttrs.addFlashAttribute("error", errorMessage);
//            return "redirect:/equipment/monitoring/add";
//        }
    }

    @RequestMapping(value = "/tech/add", method = RequestMethod.GET)
    public String techAdd(Model model) throws IOException {

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

            EquipmentTech equipmentfamily = new EquipmentTech();
            equipmentfamily.setSptsPkid("0");
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

            //hold until JF Lim provide primitive parameter
            //insert into SPTS first
//            JSONObject params = new JSONObject();
//            params.put("Name", tech);
//            SPTSResponse sr = SPTSWebService.insertEqptTech(params);
//            LOGGER.info("sr.getResponseId: " + sr.getResponseId());
//            if (sr.getResponseId() > 0) { //insert into local DB
//
//                EquipmentTech equipmentfamily = new EquipmentTech();
//                equipmentfamily.setSptsPkid(sr.getResponseId().toString());
//                equipmentfamily.setName(monitoring);
//                equipmentfamily.setCreatedBy(userSession.getFullname());
//                EquipmentTechDAO equipmentfamilyDAO = new EquipmentTechDAO();
//                QueryResult queryResult = equipmentfamilyDAO.insertEquipmentTech(equipmentfamily);
//
//                if (queryResult.getGeneratedKey().equals("0")) {
//                    redirectAttrs.addFlashAttribute("error", "Failed to register " + monitoring + ". Pls contact system admin.");
//                    return "redirect:/equipment/tech/add";
//                } else {
//                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + monitoring);
//                    return "redirect:/equipment/tech/add";
//                }
//            } else {
//                LinkedHashMap<String, String> item2;
//                ObjectMapper mapper = new ObjectMapper();
//                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//                });
//                String errorMessage;
//                if (sr.getErrorDetail().equals("")) {
//                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
//                } else {
//                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
//                }
//                model.addAttribute("error", errorMessage);
//                model.addAttribute("item2", item2);
//                redirectAttrs.addFlashAttribute("error", errorMessage);
//                return "redirect:/equipment/tech/add";
//            }
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

        eqptRelTestGroupD = new EquipmentTechDAO();
        QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentTech(techId);
        if (queryResult.getResult() == 1) {
            redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
        }
        return "redirect:/equipment/tech/add";

        //hold until JF Lim provide primitive parameter for insert function
        //retrieve from SPTS first
//        JSONObject param = new JSONObject();
//        param.put("pkid", equipmentfamily.getSptsPkid());
//        JSONArray getItemByParam = SPTSWebService.getEqptTechByPkid(param);
//        String version = "";
//        for (int i = 0; i < getItemByParam.length(); i++) {
//            version = getItemByParam.getJSONObject(i).getString("Version");
//        }
//        JSONObject params = new JSONObject();
//        params.put("pkid", equipmentfamily.getSptsPkid());
//        params.put("version", version);
//        SPTSResponse sr = SPTSWebService.deleteEqptTech(params);
//        if (sr.getStatus()) { //delete from local DB
//            eqptRelTestGroupD = new EquipmentTechDAO();
//            QueryResult queryResult = eqptRelTestGroupD.deleteEquipmentTech(techId);
//            if (queryResult.getResult() == 1) {
//                redirectAttrs.addFlashAttribute("success", equipmentfamily.getName() + " successfully deleted");
//            } else {
//                redirectAttrs.addFlashAttribute("error", "Failed to delete " + equipmentfamily.getName() + ". Pls contact system admin.");
//            }
//            return "redirect:/equipment/tech/add";
//        } else {
//            LinkedHashMap<String, String> item2;
//            ObjectMapper mapper = new ObjectMapper();
//            item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//            });
//            String errorMessage;
//            if (sr.getErrorDetail().equals("")) {
//                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
//            } else {
//                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
//            }
//            model.addAttribute("error", errorMessage);
//            model.addAttribute("item2", item2);
//            redirectAttrs.addFlashAttribute("error", errorMessage);
//            return "redirect:/equipment/tech/add";
//        }
    }

    @RequestMapping(value = "/viMonitoring/add", method = RequestMethod.GET)
    public String viMonitoringAdd(Model model) throws IOException {

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

            EquipmentViMonitoring equipmentfamily = new EquipmentViMonitoring();
            equipmentfamily.setSptsPkid("0");
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

            //hold until JF Lim provide primitive parameter
            //insert into SPTS first
//            JSONObject params = new JSONObject();
//            params.put("Name", viMonitoring);
//            SPTSResponse sr = SPTSWebService.insertEqptViMonitoring(params);
//            LOGGER.info("sr.getResponseId: " + sr.getResponseId());
//            if (sr.getResponseId() > 0) { //insert into local DB
//
//                EquipmentViMonitoring equipmentfamily = new EquipmentViMonitoring();
//                equipmentfamily.setSptsPkid(sr.getResponseId().toString());
//                equipmentfamily.setName(monitoring);
//                equipmentfamily.setCreatedBy(userSession.getFullname());
//                EquipmentViMonitoringDAO equipmentfamilyDAO = new EquipmentViMonitoringDAO();
//            QueryResult queryResult = equipmentfamilyDAO.insertEquipmentViMonitoring(equipmentfamily);
//
//                if (queryResult.getGeneratedKey().equals("0")) {
//                    redirectAttrs.addFlashAttribute("error", "Failed to register " + viMonitoring + ". Pls contact system admin.");
//                    return "redirect:/equipment/viMonitoring/add";
//                } else {
//                    redirectAttrs.addFlashAttribute("success", "Successfully registered " + viMonitoring);
//                    return "redirect:/equipment/viMonitoring/add";
//                }
//            } else {
//                LinkedHashMap<String, String> item2;
//                ObjectMapper mapper = new ObjectMapper();
//                item2 = mapper.readValue(params.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//                });
//                String errorMessage;
//                if (sr.getErrorDetail().equals("")) {
//                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
//                } else {
//                    errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
//                }
//                model.addAttribute("error", errorMessage);
//                model.addAttribute("item2", item2);
//                redirectAttrs.addFlashAttribute("error", errorMessage);
//                return "redirect:/equipment/viMonitoring/add";
//            }
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
}
