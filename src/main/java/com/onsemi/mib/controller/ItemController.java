package com.onsemi.mib.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsemi.mib.dao.FTPDao;
import com.onsemi.mib.dao.HardwareDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.HimsRequestDAO;
import com.onsemi.mib.dao.InventoryDAO;
import com.onsemi.mib.dao.InventoryMgtDAO;
import com.onsemi.mib.dao.LogDAO;
import com.onsemi.mib.dao.LogFtpDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RequestDAO;
import com.onsemi.mib.model.FTPdata;
import com.onsemi.mib.model.Hardware;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.HimsInventory;
import com.onsemi.mib.model.Inventory;
import com.onsemi.mib.model.InventoryMgt;
import com.onsemi.mib.model.JSONResponse;
import com.onsemi.mib.model.Log;
import com.onsemi.mib.model.LogFtp;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.Request;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSWebService;
import com.onsemi.mib.tools.SystemUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
@RequestMapping(value = "/hw")
@SessionAttributes({"userSession"})
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    String[] args = {};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

//    @RequestMapping(value = "/hardware", method = RequestMethod.GET)
    @RequestMapping(value = "/item", method = {RequestMethod.GET, RequestMethod.POST})
    public String request(
            Model model,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemType
    ) throws IOException {

        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();
        List<LinkedHashMap<String, String>> itemTpeAll = SystemUtil.jsonArrayToList(getItemTypeAll);
        model.addAttribute("itemTpeAll", itemTpeAll);

        String itemTypeTitle = "";

        if (itemType == null || "".equals(itemType)) {
            ItemDAO hwD = new ItemDAO();
            List<Item> itemList = hwD.getHardwareDetailListByItemType("No Item Type");
            model.addAttribute("itemList", itemList);
        } else {

            //update SPTS data per item type into MIB DB
            JSONObject params = new JSONObject();
            params.put("itemType", itemType);
            JSONArray getItemByParam = SPTSWebService.getItemByParam(params);

            int count = 0;
            int countAdd = 0;

            //insert into database
            for (int i = 0; i < getItemByParam.length(); i++) {

                ItemDAO hwD = new ItemDAO();
                int countPkid = hwD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                if (countPkid == 0) {

                    Item hw = new Item();
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
                    if (getItemByParam.getJSONObject(i).has("OtherOnsemiQty")) {
                        hw.setOtherOnsemiQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OtherOnsemiQty")));
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
                    if (getItemByParam.getJSONObject(i).has("PKID")) {
                        hw.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
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
                    QueryResult q = hwD.insertHardwareDetail(hw);
                    countAdd += q.getResult();
                }
                count += 1;
            }
            LOGGER.info("Total data: " + count);
            LOGGER.info("Total insert: " + countAdd);

            ItemDAO hwD = new ItemDAO();
            List<Item> itemList = hwD.getHardwareDetailListByItemType(itemType);
            model.addAttribute("itemList", itemList);
            itemTypeTitle = " (" + itemType + ")";
        }
        model.addAttribute("itemTypeTitle", itemTypeTitle);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsage = pD.getGroupParameterDetailList("", "001");
        model.addAttribute("paramItemUsage", paramItemUsage);

        return "item/item";
//        return "hardware/hardware_json";
    }

    @RequestMapping(value = "/item/detail", method = {RequestMethod.GET, RequestMethod.POST})
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
                if (getItemByParam.getJSONObject(i).has("OtherOnsemiQty")) {
                    hw.setOtherOnsemiQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OtherOnsemiQty")));
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

    @RequestMapping(value = "/item/hardwareList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Hardware> hardwareList(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {

        LOGGER.info("itemPKID: " + itemPKID);

        HardwareDAO hwD = new HardwareDAO();
        List<Hardware> hw = hwD.getHardwareListByItemId(itemPKID);

        return hw;
    }

    @RequestMapping(value = "/hardwareJsonSPTS", method = {RequestMethod.GET, RequestMethod.POST})
    public String hardwareJsonSPTS(
            Model model,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemType
    ) throws IOException {

        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();
        List<LinkedHashMap<String, String>> itemTpeAll = SystemUtil.jsonArrayToList(getItemTypeAll);
        model.addAttribute("itemTpeAll", itemTpeAll);

        if (itemType == null) {
            JSONObject params = new JSONObject();
            params.put("itemType", "No Item Type");
            JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
            List<LinkedHashMap<String, String>> itemList = SystemUtil.jsonArrayToList(getItemByParam);
            model.addAttribute("itemList", itemList);
        } else {
            JSONObject params = new JSONObject();
            params.put("itemType", itemType);
            JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
            List<LinkedHashMap<String, String>> itemList = SystemUtil.jsonArrayToList(getItemByParam);
            model.addAttribute("itemList", itemList);

            int count = 0;
            int countAdd = 0;

            //insert into database
            for (int i = 0; i < getItemByParam.length(); i++) {
                Item hw = new Item();
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
                if (getItemByParam.getJSONObject(i).has("OtherOnsemiQty")) {
                    hw.setOtherOnsemiQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OtherOnsemiQty")));
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
                if (getItemByParam.getJSONObject(i).has("PKID")) {
                    hw.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                }
                if (getItemByParam.getJSONObject(i).has("StatusName")) {
                    Object assembly = getItemByParam.getJSONObject(i).get("StatusName");
                    if (assembly instanceof String) {
                        hw.setStatus(getItemByParam.getJSONObject(i).getString("StatusName"));
                    } else {
                        hw.setStatus(Integer.toString(getItemByParam.getJSONObject(i).getInt("StatusName")));
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
                count += 1;

                ItemDAO hwD = new ItemDAO();
                int countPkid = hwD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
                if (countPkid == 0) {
                    hwD = new ItemDAO();
                    QueryResult q = hwD.insertHardwareDetail(hw);
                    countAdd += q.getResult();
                }
            }
            LOGGER.info("Total data: " + count);
            LOGGER.info("Total insert: " + countAdd);
        }

        return "item/item";
//        return "hardware/hardware_json";
    }

    @RequestMapping(value = "/json/getitembyparamitemtype", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> jsonGetItemByParamForItemType(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemType
    ) throws IOException {

        LOGGER.info("itemType: " + itemType);
        JSONObject params = new JSONObject();
        params.put("itemType", itemType);
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);

        //set to model
        List<Item> hardwareDetailList = new ArrayList<Item>();
        for (int i = 0; i < getItemByParam.length(); i++) {
            Item hw = new Item();
            hw.setItemType(getItemByParam.getJSONObject(i).getString("ItemType"));
            hw.setItemId(getItemByParam.getJSONObject(i).getString("ItemID"));
            hw.setItemName(getItemByParam.getJSONObject(i).getString("ItemName"));
            int PKID = getItemByParam.getJSONObject(i).getInt("PKID");
            LOGGER.info("PKID: " + PKID);
            hw.setSptsPkid(Integer.toString(PKID));
            hardwareDetailList.add(hw);
        }

        return hardwareDetailList;
    }

    @RequestMapping(value = "/json/getitembyparam", method = RequestMethod.GET)
    @ResponseBody
    public Item jsonGetItemByParam(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String pkID
    ) throws IOException {

        LOGGER.info("pkID: " + pkID);
        JSONObject params = new JSONObject();
        params.put("pkID", pkID);
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);

        if (getItemByParam.getJSONObject(0).has("SubType")) {
            LOGGER.info("subtype: " + getItemByParam.getJSONObject(0).getString("SubType"));
        } else {
            LOGGER.info("subtype: not found! ");
        }

        //set to model
        Item hw = new Item();
        hw.setItemType(getItemByParam.getJSONObject(0).getString("ItemType"));
        hw.setItemId(getItemByParam.getJSONObject(0).getString("ItemID"));
        hw.setItemName(getItemByParam.getJSONObject(0).getString("ItemName"));
        if (getItemByParam.getJSONObject(0).has("SubType")) {
            hw.setSubType(getItemByParam.getJSONObject(0).getString("SubType"));
        }
        if (getItemByParam.getJSONObject(0).has("ALUHrs")) {
            hw.setAluHrs(Double.toString(getItemByParam.getJSONObject(0).getDouble("ALUHrs")));
        }
        if (getItemByParam.getJSONObject(0).has("AssemblyID")) {
            hw.setAssemblyId(getItemByParam.getJSONObject(0).getString("AssemblyID"));
        }
        if (getItemByParam.getJSONObject(0).has("Complexity")) {
            hw.setComplexity(getItemByParam.getJSONObject(0).getString("Complexity"));
        }
        if (getItemByParam.getJSONObject(0).has("EquipmentManufacturer")) {
            hw.setEquipmentManufacturer(getItemByParam.getJSONObject(0).getString("EquipmentManufacturer"));
        }
        if (getItemByParam.getJSONObject(0).has("EquipmentModel")) {
            hw.setEquipmentModel(getItemByParam.getJSONObject(0).getString("EquipmentModel"));
        }
        if (getItemByParam.getJSONObject(0).has("EquipmentType")) {
            hw.setEquipmentType(getItemByParam.getJSONObject(0).getString("EquipmentType"));
        }
        if (getItemByParam.getJSONObject(0).has("ExpirationDate")) {
            hw.setExpirationDate(getItemByParam.getJSONObject(0).getString("ExpirationDate"));
        }
        if (getItemByParam.getJSONObject(0).has("ExternalRecleaningQty")) {
            hw.setExternalRecleanQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("ExternalRecleaningQty")));
        }
        if (getItemByParam.getJSONObject(0).has("ExternalCleaningQty")) {
            hw.setExternalRecleanQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("ExternalCleaningQty")));
        }
        if (getItemByParam.getJSONObject(0).has("InternalCleaningQty")) {
            hw.setInternalCleanQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("InternalCleaningQty")));
        }
        if (getItemByParam.getJSONObject(0).has("InternalRecleaningQty")) {
            hw.setInternalRecleanQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("InternalRecleaningQty")));
        }
        if (getItemByParam.getJSONObject(0).has("IsConsumable")) {
            hw.setIsConsumable(Boolean.toString(getItemByParam.getJSONObject(0).getBoolean("IsConsumable")));
        }
        if (getItemByParam.getJSONObject(0).has("IsCritical")) {
            hw.setIsCritical(Boolean.toString(getItemByParam.getJSONObject(0).getBoolean("IsCritical")));
        }
        if (getItemByParam.getJSONObject(0).has("Manufacturer")) {
            hw.setManufacturer(getItemByParam.getJSONObject(0).getString("Manufacturer"));
        }
        if (getItemByParam.getJSONObject(0).has("MaxQty")) {
            hw.setMaxQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("MaxQty")));
        }
        if (getItemByParam.getJSONObject(0).has("MinQty")) {
            hw.setMinQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("MinQty")));
        }
        if (getItemByParam.getJSONObject(0).has("Model")) {
            hw.setModel(getItemByParam.getJSONObject(0).getString("Model"));
        }
        if (getItemByParam.getJSONObject(0).has("OnHandQty")) {
            hw.setOnHandQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("OnHandQty")));
        }
        if (getItemByParam.getJSONObject(0).has("OtherOnsemiQty")) {
            hw.setOtherOnsemiQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("OtherOnsemiQty")));
        }
        if (getItemByParam.getJSONObject(0).has("OtherQty")) {
            hw.setOtherQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("OtherQty")));
        }
        if (getItemByParam.getJSONObject(0).has("PMWW1")) {
            hw.setPmWw1(getItemByParam.getJSONObject(0).getString("PMWW1"));
        }
        if (getItemByParam.getJSONObject(0).has("PMWW2")) {
            hw.setPmWw2(getItemByParam.getJSONObject(0).getString("PMWW2"));
        }
        if (getItemByParam.getJSONObject(0).has("ProductionQty")) {
            hw.setProductionQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("ProductionQty")));
        }
        if (getItemByParam.getJSONObject(0).has("ProductionStagingQty")) {
            hw.setProductionStagingQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("ProductionStagingQty")));
        }
        if (getItemByParam.getJSONObject(0).has("QuarantineQty")) {
            hw.setQuarantineQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("QuarantineQty")));
        }
        if (getItemByParam.getJSONObject(0).has("Rack")) {
            hw.setRack(getItemByParam.getJSONObject(0).getString("Rack"));
        }
        if (getItemByParam.getJSONObject(0).has("Remarks")) {
            hw.setRemarks(getItemByParam.getJSONObject(0).getString("Remarks"));
        }
        if (getItemByParam.getJSONObject(0).has("RepairQty")) {
            hw.setRepairQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("RepairQty")));
        }
        if (getItemByParam.getJSONObject(0).has("Shelf")) {
            hw.setShelf(getItemByParam.getJSONObject(0).getString("Shelf"));
        }
        if (getItemByParam.getJSONObject(0).has("PKID")) {
            hw.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(0).getInt("PKID")));
        }
        if (getItemByParam.getJSONObject(0).has("StatusName")) {
            hw.setStatus(getItemByParam.getJSONObject(0).getString("StatusName"));
        }
        if (getItemByParam.getJSONObject(0).has("StorageFactoryQty")) {
            hw.setStorageFactoryQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("StorageFactoryQty")));
        }
        if (getItemByParam.getJSONObject(0).has("StressType")) {
            hw.setStressType(getItemByParam.getJSONObject(0).getString("StressType"));
        }
        if (getItemByParam.getJSONObject(0).has("TotalCost")) {
            hw.setTotalCost(Double.toString(getItemByParam.getJSONObject(0).getDouble("TotalCost")));
        }
        if (getItemByParam.getJSONObject(0).has("TotalQty")) {
            hw.setTotalQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("TotalQty")));
        }
        if (getItemByParam.getJSONObject(0).has("UnitCost")) {
            hw.setUnitCost(Double.toString(getItemByParam.getJSONObject(0).getDouble("UnitCost")));
        }
        if (getItemByParam.getJSONObject(0).has("VendorQty")) {
            hw.setVendorQty(Integer.toString(getItemByParam.getJSONObject(0).getInt("VendorQty")));
        }
        if (getItemByParam.getJSONObject(0).has("DowntimeUnit")) {
            hw.setStorageFactoryQty(getItemByParam.getJSONObject(0).getString("DowntimeUnit"));
        }
        if (getItemByParam.getJSONObject(0).has("DowntimeValue")) {
            hw.setStressType(Double.toString(getItemByParam.getJSONObject(0).getDouble("DowntimeValue")));
        }
        if (getItemByParam.getJSONObject(0).has("ImplementationCost")) {
            hw.setTotalCost(Double.toString(getItemByParam.getJSONObject(0).getDouble("ImplementationCost")));
        }
        if (getItemByParam.getJSONObject(0).has("ManpowerUnit")) {
            hw.setTotalQty(getItemByParam.getJSONObject(0).getString("ManpowerUnit"));
        }
        if (getItemByParam.getJSONObject(0).has("ManpowerValue")) {
            hw.setUnitCost(Double.toString(getItemByParam.getJSONObject(0).getDouble("ManpowerValue")));
        }

        return hw;
    }

    @RequestMapping(value = "/updateFromSPTS", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateFromSPTS(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws IOException {

        JSONArray getItemByParam = SPTSWebService.getItemAll();

        int count = 0;
        int countAdd = 0;

        //insert into database
        for (int i = 0; i < getItemByParam.length(); i++) {
            Item hw = new Item();
            hw.setSiteName(getItemByParam.getJSONObject(i).getString("SiteName"));
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
            if (getItemByParam.getJSONObject(i).has("OtherOnsemiQty")) {
                hw.setOtherOnsemiQty(Integer.toString(getItemByParam.getJSONObject(i).getInt("OtherOnsemiQty")));
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
            if (getItemByParam.getJSONObject(i).has("PKID")) {
                hw.setSptsPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
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
            count += 1;

            ItemDAO hwD = new ItemDAO();
            int countPkid = hwD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
            if (countPkid == 0) {
                hwD = new ItemDAO();
                QueryResult q = hwD.insertHardwareDetail(hw);
                countAdd += q.getResult();
            }
            LOGGER.info("Total data: " + count);
            LOGGER.info("Total insert: " + countAdd);
        }

        model.addAttribute("count", count);
        model.addAttribute("countAdd", countAdd);

        return "item/hardwareSPTSUpdate";
//        return "hardware/hardware_json";
    }

    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    public String equipment(
            Model model,
            @ModelAttribute UserSession userSession
    ) {
        return "item/equipment";
    }

    @RequestMapping(value = "/himsList", method = RequestMethod.GET)
    public String himsList(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws ClassNotFoundException, SQLException {
        HimsRequestDAO requestDAO = new HimsRequestDAO();
        List<HimsInventory> requestList = requestDAO.getWhInventoryActiveList();
        Integer count = requestList.size();
        LOGGER.info("count : " + count);
        model.addAttribute("requestList", requestList);
        return "item/himsList";
    }

    @RequestMapping(value = "/view/{reqId}", method = RequestMethod.GET)
    public String view(
            Model model,
            HttpServletRequest request,
            @PathVariable("reqId") String reqId
    ) throws UnsupportedEncodingException {
        String pdfUrl = URLEncoder.encode(request.getContextPath() + "/sr/request/viewBarcodeStickerPdf/" + reqId, "UTF-8");
        String backUrl = servletContext.getContextPath() + "/";
        model.addAttribute("pdfUrl", pdfUrl);
        model.addAttribute("backUrl", backUrl);
        model.addAttribute("pageTitle", "Barcode Sticker");
        return "pdf/viewer";
    }

    @RequestMapping(value = "/viewBarcodeStickerPdf/{reqId}", method = RequestMethod.GET)
    public ModelAndView viewWhBarcodeStickerPdf(
            Model model,
            @PathVariable("reqId") String reqId
    ) {

        RequestDAO reqD = new RequestDAO();
        Request request = reqD.getRequestWithFtpAndInventory(reqId);

        return new ModelAndView("barcodeStickerPdf", "request", request);
    }

}
