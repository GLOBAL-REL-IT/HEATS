package com.onsemi.mib.controller;

import com.google.common.base.Strings;
import com.onsemi.mib.dao.HardwareDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.HimsRequestDAO;
import com.onsemi.mib.dao.ItemActivityConfigDAO;
import com.onsemi.mib.dao.ItemTransactionDAO;
import com.onsemi.mib.dao.ItemVisualInspectionDAO;
import com.onsemi.mib.dao.ManualTestDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RequestDAO;
import com.onsemi.mib.model.Hardware;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.HimsInventory;
import com.onsemi.mib.model.ItemActivityConfig;
import com.onsemi.mib.model.ItemTransaction;
import com.onsemi.mib.model.ItemVisualInspection;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.Request;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSWebService;
import com.onsemi.mib.tools.SystemUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/hw")
@SessionAttributes({"userSession"})
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    String[] args = {};

    private static final String UPLOADED_FOLDER = "\\\\mysed-rel-app05\\f$\\HEATS\\VI-Attachment\\"; //server

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/item", method = {RequestMethod.GET, RequestMethod.POST}) //without checking SPTS data and update to MIB DB
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

    @RequestMapping(value = "/item2", method = {RequestMethod.GET, RequestMethod.POST}) //checking SPTS data and update to MIB DB
    public String request2(
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
        int countTrans = 0;
        int countTransAdd = 0;

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

        //add transaction to DB
        JSONObject params2 = new JSONObject();
        params2.put("itemsPKID", pkID);
        JSONArray getTransactionByParam = SPTSWebService.getTransactionByParam(params2);

        for (int i = 0; i < getTransactionByParam.length(); i++) {

            ItemTransactionDAO itemD = new ItemTransactionDAO();
            int countPkid = itemD.getCountPkidAndItemPkid(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("PKID")), Integer.toString(getTransactionByParam.getJSONObject(i).getInt("ItemsPKID")));
            if (countPkid == 0) {
                ItemTransaction item = new ItemTransaction();
                item.setSptsPkid(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("PKID")));
                item.setItemPkid(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("ItemsPKID")));
                item.setSiteName(getTransactionByParam.getJSONObject(i).getString("SiteName"));
                String dateTime = getTransactionByParam.getJSONObject(i).getString("DateTime").substring(0, 10) + " " + getTransactionByParam.getJSONObject(i).getString("DateTime").substring(11, 19);
                item.setDateTime(dateTime);
                item.setTransType(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransType")));
                item.setTransTypeName(getTransactionByParam.getJSONObject(i).getString("TransTypeName"));
                item.setTransQty(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransQty")));
                if (getTransactionByParam.getJSONObject(i).has("TransInQty")) {
                    item.setTransInQty(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransInQty")));
                }
                if (getTransactionByParam.getJSONObject(i).has("TransOutQty")) {
                    item.setTransOutQty(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransOutQty")));
                }
                if (getTransactionByParam.getJSONObject(i).has("LifetimeUsageHrs")) {
                    item.setAlu(Double.toString(getTransactionByParam.getJSONObject(i).getDouble("LifetimeUsageHrs")));
                }
                if (getTransactionByParam.getJSONObject(i).has("Remarks")) {
                    item.setRemarks(getTransactionByParam.getJSONObject(i).getString("Remarks"));
                }

                itemD = new ItemTransactionDAO();
                QueryResult qI = itemD.insertItemTransaction(item);
                countTransAdd += qI.getResult();
            }
            countTrans += 1;
        }
        LOGGER.info("Total data Trans: " + countTrans);
        LOGGER.info("Total insert Trans: " + countTransAdd);

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

    @RequestMapping(value = "/item/testHtmlData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String testHtmlData(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {

        LOGGER.info("itemPKID: " + itemPKID);
        String data = "";
        data = "<thead>"
                + "                                                        <tr>"
                + "                                                            <th class=\"col-12\">Site</th>"
                + "                                                            <th>Hardware</th>"
                + "                                                            <th>ALU</th>"
                + "                                                            <th>MFG Date</th>"
                + "                                                            <th>RMS_Event</th>"
                + "                                                            <th>Status</th>"
                + "                                                        </tr>"
                + "                                                    </thead>"
                + "                                                    <tbody>";
        data += "<tr>"
                + "<td>Airi Satou</td>"
                + "                        <td class=\"sorting_1\">Accountant</td>"
                + "                        <td>Tokyo</td>"
                + "                        <td class=\"dt-type-numeric\">33</td>"
                + "                        <td class=\"dt-type-date\">2008-11-28 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">1200000</td>"
                + "                    </tr><tr>"
                + "                        <td>Garrett Winters</td>"
                + "                        <td class=\"sorting_1\">Accountant</td>"
                + "                        <td>Tokyo</td>"
                + "                        <td class=\"dt-type-numeric\">63</td>"
                + "                        <td class=\"dt-type-date\">2011-07-25 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">163500</td>"
                + "                    </tr><tr>"
                + "                        <td>Airi Satou</td>"
                + "                        <td class=\"sorting_1\">Accountant</td>"
                + "                        <td>Tokyo</td>"
                + "                        <td class=\"dt-type-numeric\">33</td>"
                + "                        <td class=\"dt-type-date\">2008-11-28 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">162</td>"
                + "                    </tr><tr>"
                + "                        <td>Garrett Winters</td>"
                + "                        <td class=\"sorting_1\">Accountant</td>"
                + "                        <td>Tokyo</td>"
                + "                        <td class=\"dt-type-numeric\">63</td>"
                + "                        <td class=\"dt-type-date\">2011-07-25 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">170</td>"
                + "                    </tr><tr>"
                + "                        <td>Angelica Ramos</td>"
                + "                        <td class=\"sorting_1\">Chief Executive Officer (CEO)</td>"
                + "                        <td>London</td>"
                + "                        <td class=\"dt-type-numeric\">47</td>"
                + "                        <td class=\"dt-type-date\">2009-10-09 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">86000</td>"
                + "                    </tr><tr>"
                + "                        <td>Angelica Ramos</td>"
                + "                        <td class=\"sorting_1\">Chief Executive Officer (CEO)</td>"
                + "                        <td>London</td>"
                + "                        <td class=\"dt-type-numeric\">47</td>"
                + "                        <td class=\"dt-type-date\">2009-10-09 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">1</td>"
                + "                    </tr><tr>"
                + "                        <td>Paul Byrd</td>"
                + "                        <td class=\"sorting_1\">Chief Financial Officer (CFO)</td>"
                + "                        <td>New York</td>"
                + "                        <td class=\"dt-type-numeric\">64</td>"
                + "                        <td class=\"dt-type-date\">2010-06-09 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">725</td>"
                + "                    </tr><tr>"
                + "                        <td>Angelica Ramos</td>"
                + "                        <td class=\"sorting_1\">Chief Executive Officer (CEO)</td>"
                + "                        <td>London</td>"
                + "                        <td class=\"dt-type-numeric\">47</td>"
                + "                        <td class=\"dt-type-date\">2009-10-09 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">86000</td>"
                + "                    </tr><tr>"
                + "                        <td>Angelica Ramos</td>"
                + "                        <td class=\"sorting_1\">Chief Executive Officer (CEO)</td>"
                + "                        <td>London</td>"
                + "                        <td class=\"dt-type-numeric\">47</td>"
                + "                        <td class=\"dt-type-date\">2009-10-09 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">1</td>"
                + "                    </tr><tr>"
                + "                        <td>Paul Byrd</td>"
                + "                        <td class=\"sorting_1\">Chief Financial Officer (CFO)</td>"
                + "                        <td>New York</td>"
                + "                        <td class=\"dt-type-numeric\">64</td>"
                + "                        <td class=\"dt-type-date\">2010-06-09 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">725</td>"
                + "                    </tr><tr>"
                + "                        <td>Yuri Berry</td>"
                + "                        <td class=\"sorting_1\">Chief Marketing Officer (CMO)</td>"
                + "                        <td>New York</td>"
                + "                        <td class=\"dt-type-numeric\">40</td>"
                + "                        <td class=\"dt-type-date\">2009-06-25 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">675</td>"
                + "                    </tr><tr>"
                + "                        <td>Fiona Green</td>"
                + "                        <td class=\"sorting_1\">Chief Operating Officer (COO)</td>"
                + "                        <td>San Francisco</td>"
                + "                        <td class=\"dt-type-numeric\">48</td>"
                + "                        <td class=\"dt-type-date\">2010-03-11 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">470600</td>"
                + "                    </tr><tr>"
                + "                        <td>Fiona Green</td>"
                + "                        <td class=\"sorting_1\">Chief Operating Officer (COO)</td>"
                + "                        <td>San Francisco</td>"
                + "                        <td class=\"dt-type-numeric\">48</td>"
                + "                        <td class=\"dt-type-date\">2010-03-11 00:00:00</td>"
                + "                        <td class=\"dt-type-numeric\">850</td>"
                + "                    </tr>";
        data += "</tbody>";

//        HardwareDAO hwD = new HardwareDAO();
//        List<Hardware> hw = hwD.getHardwareListByItemId(itemPKID);
        return data;
    }

    @RequestMapping(value = "/item/ajaxHtmlSample", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String ajaxHtmlSample(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {

        String data = "{"
                + "  \"draw\": 1,"
                + "  \"recordsTotal\": 57,"
                + "  \"recordsFiltered\": 57,"
                + "  \"data\": ["
                + "    {"
                + "      \"first_name\": \"Airi\","
                + "      \"last_name\": \"Satou\","
                + "      \"position\": \"Accountant\","
                + "      \"office\": \"Tokyo\","
                + "      \"start_date\": \"28th Nov 08\","
                + "      \"salary\": \"$162,700\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Angelica\","
                + "      \"last_name\": \"Ramos\","
                + "      \"position\": \"Chief Executive Officer (CEO)\","
                + "      \"office\": \"London\","
                + "      \"start_date\": \"9th Oct 09\","
                + "      \"salary\": \"$1,200,000\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Ashton\","
                + "      \"last_name\": \"Cox\","
                + "      \"position\": \"Junior Technical Author\","
                + "      \"office\": \"San Francisco\","
                + "      \"start_date\": \"12th Jan 09\","
                + "      \"salary\": \"$86,000\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Bradley\","
                + "      \"last_name\": \"Greer\","
                + "      \"position\": \"Software Engineer\","
                + "      \"office\": \"London\","
                + "      \"start_date\": \"13th Oct 12\","
                + "      \"salary\": \"$132,000\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Brenden\","
                + "      \"last_name\": \"Wagner\","
                + "      \"position\": \"Software Engineer\","
                + "      \"office\": \"San Francisco\","
                + "      \"start_date\": \"7th Jun 11\","
                + "      \"salary\": \"$206,850\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Brielle\","
                + "      \"last_name\": \"Williamson\","
                + "      \"position\": \"Integration Specialist\","
                + "      \"office\": \"New York\","
                + "      \"start_date\": \"2nd Dec 12\","
                + "      \"salary\": \"$372,000\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Bruno\","
                + "      \"last_name\": \"Nash\","
                + "      \"position\": \"Software Engineer\","
                + "      \"office\": \"London\","
                + "      \"start_date\": \"3rd May 11\","
                + "      \"salary\": \"$163,500\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Caesar\","
                + "      \"last_name\": \"Vance\","
                + "      \"position\": \"Pre-Sales Support\","
                + "      \"office\": \"New York\","
                + "      \"start_date\": \"12th Dec 11\","
                + "      \"salary\": \"$106,450\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Cara\","
                + "      \"last_name\": \"Stevens\","
                + "      \"position\": \"Sales Assistant\","
                + "      \"office\": \"New York\","
                + "      \"start_date\": \"6th Dec 11\","
                + "      \"salary\": \"$145,600\""
                + "    },"
                + "    {"
                + "      \"first_name\": \"Cedric\","
                + "      \"last_name\": \"Kelly\","
                + "      \"position\": \"Senior Javascript Developer\","
                + "      \"office\": \"Edinburgh\","
                + "      \"start_date\": \"29th Mar 12\","
                + "      \"salary\": \"$433,060\""
                + "    }"
                + "  ]"
                + "}";

//        ItemTransactionDAO hwD = new ItemTransactionDAO();
//        List<ItemTransaction> hw = hwD.getItemTransactionListByItemPkid(itemPKID);
        return data;
    }

    @RequestMapping(value = "/item/ajaxHtmlSampleHardware", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String ajaxHtmlSampleHardware(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {

        ItemDAO item = new ItemDAO();
        List<Item> itemList = item.getDataTest(itemPKID);

        JSONArray jsonArray = new JSONArray();
        for (Item itm : itemList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", Strings.nullToEmpty(itm.getId()));
            jsonObject.put("item_id", Strings.nullToEmpty(itm.getItemId()));
            jsonObject.put("item_name", Strings.nullToEmpty(itm.getItemName()));
            jsonObject.put("item_type", Strings.nullToEmpty(itm.getItemType()));
            jsonObject.put("assembly_id", Strings.nullToEmpty(itm.getAssemblyId()));
            jsonObject.put("spts_id", Strings.nullToEmpty(itm.getSptsPkid()));
            jsonObject.put("aluhrs", Strings.nullToEmpty(itm.getAluHrs()));
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/item/ajaxTransaction", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String ajaxTransaction(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {

//        ItemDAO item = new ItemDAO();
//        List<Item> itemList = item.getDataTest(itemPKID);
        ItemTransactionDAO hwD = new ItemTransactionDAO();
        List<ItemTransaction> itemList = hwD.getItemTransactionListByItemPkid(itemPKID);

        JSONArray jsonArray = new JSONArray();

//        {"data": "itemId"},
//                                                           {"data": "dateTime"},
//                                                           {"data": "transTypeName"},
//                                                           {"data": "transInQty"},
//                                                           {"data": "transOutQty"},
//                                                           {"data": "alu"},
//                                                           {"data": "remarks"}
        for (ItemTransaction itm : itemList) {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", Strings.nullToEmpty(itm.getId()));
            jsonObject.put("itemId", Strings.nullToEmpty(itm.getItemId()));
            jsonObject.put("dateTime", Strings.nullToEmpty(itm.getDateTime()));
            jsonObject.put("transTypeName", Strings.nullToEmpty(itm.getTransTypeName()));
            jsonObject.put("transInQty", Strings.nullToEmpty(itm.getTransInQty()));
            jsonObject.put("transOutQty", Strings.nullToEmpty(itm.getTransOutQty()));
            jsonObject.put("alu", Strings.nullToEmpty(itm.getAlu()));
            jsonObject.put("remarks", Strings.nullToEmpty(itm.getRemarks()));
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/item/transList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<ItemTransaction> transList(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {
        ItemTransactionDAO hwD = new ItemTransactionDAO();
        List<ItemTransaction> hw = hwD.getItemTransactionListByItemPkid(itemPKID);
        return hw;
    }

    @RequestMapping(value = "/item/transList2/{itemPKID}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<ItemTransaction> transList2(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @PathVariable("itemPKID") String itemPKID
    ) throws IOException {
        ItemTransactionDAO hwD = new ItemTransactionDAO();
        List<ItemTransaction> hw = hwD.getItemTransactionListByItemPkid(itemPKID);
        return hw;
    }

    @RequestMapping(value = "/item/hardwareList/{itemPKID}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Hardware> hardwareList2(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @PathVariable("itemPKID") String itemPKID
    ) throws IOException {
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

    @RequestMapping(value = "/item/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemAdd(
            Model model,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemType
    ) throws IOException {

        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();

        for (int i = 0; i < getItemTypeAll.length(); i++) {

            ParameterDetailsDAO pD = new ParameterDetailsDAO();
            String masterCode = "002";
            String detailcode = pD.getNextDetailCode(masterCode);
            pD = new ParameterDetailsDAO();
            int count = pD.getCountMasterCodeAndName(masterCode, getItemTypeAll.getJSONObject(i).getString("ItemType"));

            if (count == 0) {
                ParameterDetails param = new ParameterDetails();
                param.setMasterCode(masterCode);
                param.setDetailCode(detailcode);
                param.setName(getItemTypeAll.getJSONObject(i).getString("ItemType"));
                param.setCreatedBy(userSession.getId());
                pD = new ParameterDetailsDAO();
                QueryResult q = pD.insertParameterDetails(param);
            }
        }

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemType = pD.getGroupParameterDetailList(itemType, "002");
        model.addAttribute("paramItemType", paramItemType);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsage = pD.getGroupParameterDetailList("", "001");
        model.addAttribute("paramItemUsage", paramItemUsage);

        model.addAttribute("itemType", itemType);
        LOGGER.info("itemType: " + itemType);

        return "item/item_add";
    }

    @RequestMapping(value = "/item/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemTypeRead,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String assemblyId,
            @RequestParam(required = false) String itemUsage,
            @RequestParam(required = false) String model2,
            @RequestParam(required = false) String manufacture,
            @RequestParam(required = false) String unitCost,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String equipmentModel,
            @RequestParam(required = false) String equipmentManufacturer,
            @RequestParam(required = false) String minQty,
            @RequestParam(required = false) String maxQty,
            @RequestParam(required = false) String rack,
            @RequestParam(required = false) String shelf,
            @RequestParam(required = false) String stressType,
            @RequestParam(required = false) String onHandQty,
            @RequestParam(required = false) String productionQty,
            @RequestParam(required = false) String otherOnsemiQty,
            @RequestParam(required = false) String productionStagingQty,
            @RequestParam(required = false) String repairQty,
            @RequestParam(required = false) String quarantineQty,
            @RequestParam(required = false) String externalCleanQty,
            @RequestParam(required = false) String externalRecleanQty,
            @RequestParam(required = false) String internalCleanQty,
            @RequestParam(required = false) String internalRecleanQty,
            @RequestParam(required = false) String otherQty,
            @RequestParam(required = false) String vendorQty,
            @RequestParam(required = false) String storageFactoryQty,
            @RequestParam(required = false) String totalQty,
            @RequestParam(required = false) String isConsumable,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String expirationDate
    ) {

        Item item = new Item();
        LOGGER.info("itemTypeRead: " + itemTypeRead);
        item.setItemType(itemTypeRead);
        item.setSubType(subType);
        item.setSiteName("Seremban");
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setAssemblyId(assemblyId);
        item.setRack(rack);
        item.setShelf(shelf);
        item.setItemUsage(itemUsage);
        item.setOnHandQty(onHandQty);

        if (productionQty == null || "".equals(productionQty)) {
            productionQty = "0";
        }
        if (productionStagingQty == null || "".equals(productionStagingQty)) {
            productionStagingQty = "0";
        }
        if (repairQty == null || "".equals(repairQty)) {
            repairQty = "0";
        }
        if (otherQty == null || "".equals(otherQty)) {
            otherQty = "0";
        }
        if (quarantineQty == null || "".equals(quarantineQty)) {
            quarantineQty = "0";
        }
        if (externalCleanQty == null || "".equals(externalCleanQty)) {
            externalCleanQty = "0";
        }
        if (externalRecleanQty == null || "".equals(externalRecleanQty)) {
            externalRecleanQty = "0";
        }
        if (internalCleanQty == null || "".equals(internalCleanQty)) {
            internalCleanQty = "0";
        }
        if (internalRecleanQty == null || "".equals(internalRecleanQty)) {
            internalRecleanQty = "0";
        }
        if (storageFactoryQty == null || "".equals(storageFactoryQty)) {
            storageFactoryQty = "0";
        }
        if (otherOnsemiQty == null || "".equals(otherOnsemiQty)) {
            otherOnsemiQty = "0";
        }
        if (vendorQty == null || "".equals(vendorQty)) {
            vendorQty = "0";
        }
        item.setProductionQty(productionQty);
        item.setProductionStagingQty(productionStagingQty);
        item.setRepairQty(repairQty);
        item.setOtherQty(otherQty);
        item.setQuarantineQty(quarantineQty);
        item.setExternalCleanQty(externalCleanQty);
        item.setExternalRecleanQty(externalRecleanQty);
        item.setInternalCleanQty(internalCleanQty);
        item.setInternalRecleanQty(internalRecleanQty);
        item.setStorageFactoryQty(storageFactoryQty);
        item.setOtherOnsemiQty(otherOnsemiQty);
        item.setVendorQty(vendorQty);

        int totalQ = Integer.parseInt(onHandQty) + Integer.parseInt(productionQty) + Integer.parseInt(productionStagingQty) + Integer.parseInt(repairQty) + Integer.parseInt(otherQty) + Integer.parseInt(quarantineQty) + Integer.parseInt(externalCleanQty)
                + Integer.parseInt(externalRecleanQty) + Integer.parseInt(internalCleanQty) + Integer.parseInt(internalRecleanQty) + Integer.parseInt(storageFactoryQty) + Integer.parseInt(otherOnsemiQty) + Integer.parseInt(vendorQty);

        item.setTotalQty(Integer.toString(totalQ));
        item.setUnitCost(unitCost);

        Double totalCost = totalQ * Double.parseDouble(unitCost);
        item.setTotalCost(totalCost.toString());

        item.setMinQty(minQty);
        item.setMaxQty(maxQty);
        item.setExpirationDate(expirationDate);
        if ("on".equals(isConsumable)) {
            isConsumable = "true";
        } else {
            isConsumable = "false";
        }
        item.setIsConsumable(isConsumable);
        item.setModel(model2);
        item.setManufacturer(manufacture);
        item.setEquipmentType(equipmentType);
        item.setEquipmentModel(equipmentModel);
        item.setEquipmentManufacturer(equipmentManufacturer);
        item.setStressType(stressType);
        item.setRemarks(remarks);
        item.setCreatedBy(userSession.getFullname());
        if ("BIB".equals(itemTypeRead) || "BIB Card".equals(itemTypeRead)) {
            item.setStatus("Pending Activity Selection");
//             item.setStatus("Pending Visual Inspection");
            item.setFlag("0");
        } else {
            item.setStatus("Good");
            item.setFlag("1");
        }
        //check if itemID exist or not
        ItemDAO itemD = new ItemDAO();
        int count = itemD.getCountItemId(itemId);
        if (count > 0) {
            redirectAttrs.addFlashAttribute("error", "Duplicate Item ID: " + itemId + ". Pls register with different Item ID.");
            return "redirect:/hw/item/add";
        } else {
            itemD = new ItemDAO();
            QueryResult i = itemD.insertHardwareDetail(item);

            if (i.getResult() == 1) {
                redirectAttrs.addFlashAttribute("success", "Succesfully registered Item ID: " + itemId);
                if ("BIB".equals(itemTypeRead) || "BIB Card".equals(itemTypeRead)) {
//                    return "redirect:/hw/item/add";
                    return "redirect:/hw/item/addActivity/" + i.getGeneratedKey();
//                    return "redirect:/hw/item/add2/" + i.getGeneratedKey();
                } else {
                    return "redirect:/";
                }
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to registered Item ID: " + itemId + ". Pls contact system admin.");
                return "redirect:/hw/item/add";
            }
//            return returnPage;
        }
    }

    @RequestMapping(value = "/item/pending", method = {RequestMethod.GET, RequestMethod.POST}) //list of pending VM or functional test for new item registration
    public String pending(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws IOException {

        ItemDAO itemD = new ItemDAO();
        List<Item> item = itemD.getItemListPendingVMFunctionalTest();
        model.addAttribute("item", item);

        return "item/item_pending";
    }

    @RequestMapping(value = "/item/add2/{mibItemId}", method = RequestMethod.GET)
    public String itemAdd2(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("mibItemId") String mibItemId
    ) throws IOException {

        ItemActivityConfigDAO itemdao = new ItemActivityConfigDAO();
        ItemActivityConfig itemData = itemdao.getItemActivityByItemId(mibItemId);
        String viCheck = "NO";
        String bibCheck = "NO";
        String manCheck = "NO";
        String leakCheck = "NO";
        String psCheck = "NO";
        String winCheck = "NO";
        if (itemData != null) {
            viCheck = itemData.getVi();
            bibCheck = itemData.getBibTest();
            manCheck = itemData.getManualTest();
            leakCheck = itemData.getLeakageTest();
            psCheck = itemData.getPsLeakageTest();
            winCheck = itemData.getWinchesterChamberLeakageTest();
        } else {
            return "redirect:/hw/item/addActivity/" + mibItemId;
        }

        ItemDAO itemD = new ItemDAO();
        Item item = itemD.getHardwareDetail(mibItemId);
        model.addAttribute("item", item);

        String isConsumable = "";
        if ("on".equals(item.getIsConsumable()) || "true".equals(item.getIsConsumable())) {
            isConsumable = "checked";
        } else {
            isConsumable = "";
        }
        model.addAttribute("isConsumable", isConsumable);

        ItemVisualInspection itemVm = new ItemVisualInspection(); //declare new model to prevent null pointer exception

        ItemVisualInspectionDAO itemVmD = new ItemVisualInspectionDAO(); //check if already have VM data
        int count = itemVmD.getCountItemIdWithModuleItemRegistration(mibItemId);

        if (count == 1) { // assigned itemVm model with data
            itemVmD = new ItemVisualInspectionDAO();
            itemVm = itemVmD.getItemVisualInspectionByMibItemIdWithModuleItemRegistration(mibItemId);
        }

        model.addAttribute("itemVm", itemVm);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> BibPassFail = pD.getGroupParameterDetailList("", "016");
        model.addAttribute("BibPassFail", BibPassFail);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsage = pD.getGroupParameterDetailList(item.getItemUsage(), "001");
        model.addAttribute("paramItemUsage", paramItemUsage);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> pcbReject = pD.getGroupParameterDetailList(itemVm.getPcbReject(), "003");
        model.addAttribute("pcbReject", pcbReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> handleReject = pD.getGroupParameterDetailList(itemVm.getHandleReject(), "004");
        model.addAttribute("handleReject", handleReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> metalFrameReject = pD.getGroupParameterDetailList(itemVm.getMetalFrameReject(), "005");
        model.addAttribute("metalFrameReject", metalFrameReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> hardwareFasternersReject = pD.getGroupParameterDetailList(itemVm.getHardwareFasternersReject(), "006");
        model.addAttribute("hardwareFasternersReject", hardwareFasternersReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> clipHolderReject = pD.getGroupParameterDetailList(itemVm.getClipHolderReject(), "007");
        model.addAttribute("clipHolderReject", clipHolderReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> pcbEdgeFingerReject = pD.getGroupParameterDetailList(itemVm.getPcbEdgeFingerReject(), "008");
        model.addAttribute("pcbEdgeFingerReject", pcbEdgeFingerReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> connectorReject = pD.getGroupParameterDetailList(itemVm.getConnectorReject(), "009");
        model.addAttribute("connectorReject", connectorReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> dutSocketsReject = pD.getGroupParameterDetailList(itemVm.getDutSocketsReject(), "010");
        model.addAttribute("dutSocketsReject", dutSocketsReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> edgeMbBananaReject = pD.getGroupParameterDetailList(itemVm.getEdgeMbBananaReject(), "011");
        model.addAttribute("edgeMbBananaReject", edgeMbBananaReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> electComponentReject = pD.getGroupParameterDetailList(itemVm.getElectComponentReject(), "012");
        model.addAttribute("electComponentReject", electComponentReject);

//        pD = new ParameterDetailsDAO();
//        List<ParameterDetails> cableWireReject = pD.getGroupParameterDetailList(item.getItemUsage(), "013");
//        model.addAttribute("cableWireReject", cableWireReject);
        pD = new ParameterDetailsDAO();
        List<ParameterDetails> solderJointReject = pD.getGroupParameterDetailList(itemVm.getSolderJointReject(), "014");
        model.addAttribute("solderJointReject", solderJointReject);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> winConnectorReject = pD.getGroupParameterDetailList(itemVm.getWinConnectorReject(), "015");
        model.addAttribute("winConnectorReject", winConnectorReject);

        if (item.getStatus().contains("Good")) {
            String hwActive = "active";
            String hwActiveTab = "show active";
            model.addAttribute("hwActive", hwActive);
            model.addAttribute("hwActiveTab", hwActiveTab);
        } else {
            String hwActive = "";
            String hwActiveTab = "";
            model.addAttribute("hwActive", hwActive);
            model.addAttribute("hwActiveTab", hwActiveTab);
        }
        if (item.getStatus().contains("Visual Inspection")) {
            String vmActive = "active";
            String vmActiveTab = "show active";
            model.addAttribute("vmActive", vmActive);
            model.addAttribute("vmActiveTab", vmActiveTab);
        } else {
            String vmActive = "";
            String vmActiveTab = "";
            model.addAttribute("vmActive", vmActive);
            model.addAttribute("vmActiveTab", vmActiveTab);
        }

        if (item.getStatus().contains("Test")) {
            String teActive = "active";
            String teActiveTab = "show active";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);
        } else {
            String teActive = "";
            String teActiveTab = "";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);
        }

        model.addAttribute("viCheck", viCheck);
        model.addAttribute("bibCheck", bibCheck);
        model.addAttribute("manCheck", manCheck);
        model.addAttribute("leakCheck", leakCheck);
        model.addAttribute("psCheck", psCheck);
        model.addAttribute("winCheck", winCheck);

        return "item/item_add2";
    }

    @RequestMapping(value = "/item/vm/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemVmSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String itemStatus,
            @RequestParam(required = false) String pcb,
            @RequestParam(required = false) String pcbReject,
            @RequestParam(required = false) String handle,
            @RequestParam(required = false) String handleReject,
            @RequestParam(required = false) String metalFrame,
            @RequestParam(required = false) String metalFrameReject,
            @RequestParam(required = false) String hardwareFasterners,
            @RequestParam(required = false) String hardwareFasternersReject,
            @RequestParam(required = false) String clipHolder,
            @RequestParam(required = false) String clipHolderReject,
            @RequestParam(required = false) String pcbEdgeFinger,
            @RequestParam(required = false) String pcbEdgeFingerReject,
            @RequestParam(required = false) String connector,
            @RequestParam(required = false) String connectorReject,
            @RequestParam(required = false) String dutSockets,
            @RequestParam(required = false) String dutSocketsReject,
            @RequestParam(required = false) String edgeMbBanana,
            @RequestParam(required = false) String edgeMbBananaReject,
            @RequestParam(required = false) String electComponent,
            @RequestParam(required = false) String electComponentReject,
            @RequestParam(required = false) String solderJoint,
            @RequestParam(required = false) String solderJointReject,
            @RequestParam(required = false) String winConnector,
            @RequestParam(required = false) String winConnectorReject,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String pcbRejectQty,
            @RequestParam(required = false) MultipartFile pcbRejectUpload,
            @RequestParam(required = false) String handleRejectQty,
            @RequestParam(required = false) MultipartFile handleRejectUpload,
            @RequestParam(required = false) String metalFrameRejectQty,
            @RequestParam(required = false) MultipartFile metalFrameRejectUpload,
            @RequestParam(required = false) String hardwareFasternersRejectQty,
            @RequestParam(required = false) MultipartFile hardwareFasternersRejectUpload,
            @RequestParam(required = false) String clipHolderRejectQty,
            @RequestParam(required = false) MultipartFile clipHolderRejectUpload,
            @RequestParam(required = false) String pcbEdgeFingerRejectQty,
            @RequestParam(required = false) MultipartFile pcbEdgeFingerRejectUpload,
            @RequestParam(required = false) String connectorRejectQty,
            @RequestParam(required = false) MultipartFile connectorRejectUpload,
            @RequestParam(required = false) String dutSocketsRejectQty,
            @RequestParam(required = false) MultipartFile dutSocketsRejectUpload,
            @RequestParam(required = false) String edgeMbBananaRejectQty,
            @RequestParam(required = false) MultipartFile edgeMbBananaRejectUpload,
            @RequestParam(required = false) String electComponentRejectQty,
            @RequestParam(required = false) MultipartFile electComponentRejectUpload,
            @RequestParam(required = false) String solderJointRejectQty,
            @RequestParam(required = false) MultipartFile solderJointRejectUpload,
            @RequestParam(required = false) String winConnectorRejectQty,
            @RequestParam(required = false) MultipartFile winConnectorRejectUpload
    ) {

        String finalStatus = "";
        String stringPathPcb = "";
        String stringPathHandle = "";
        String stringPathmetalFrame = "";
        String stringPathHardwareFasterners = "";
        String stringPathclipHolder = "";
        String stringPathPcbEdgeFinger = "";
        String stringPathConnector = "";
        String stringPathDutSockets = "";
        String stringPathEdgeMbBanana = "";
        String stringPathElectComponent = "";
        String stringPathSolderJoint = "";
        String stringPathWinConnector = "";

        ItemVisualInspection itemVm = new ItemVisualInspection();

        itemVm.setMibItemId(mibItemId);
        if (null == itemStatus) {
            itemVm.setModule("Item Registration");
        } else {
            switch (itemStatus) {
                case "Pending Visual Inspection":
                    itemVm.setModule("Item Registration");
                    break;
                case "Pending Visual Inspection (from Maverick)":
                    itemVm.setModule("Item Registration (2nd Visual Inspection");
                    break;
                default:
                    itemVm.setModule("Item Registration");
                    break;
            }
        }
        itemVm.setPcb(pcb);
        itemVm.setPcbReject(pcbReject);
        itemVm.setHandle(handle);
        itemVm.setHandleReject(handleReject);
        itemVm.setMetalFrame(metalFrame);
        itemVm.setMetalFrameReject(metalFrameReject);
        itemVm.setHardwareFasterners(hardwareFasterners);
        itemVm.setHardwareFasternersReject(hardwareFasternersReject);
        itemVm.setClipHolder(clipHolder);
        itemVm.setClipHolderReject(clipHolderReject);
        itemVm.setPcbEdgeFinger(pcbEdgeFinger);
        itemVm.setPcbEdgeFingerReject(pcbEdgeFingerReject);
        itemVm.setConnector(connector);
        itemVm.setConnectorReject(connectorReject);
        itemVm.setDutSockets(dutSockets);
        itemVm.setDutSocketsReject(dutSocketsReject);
        itemVm.setEdgeMbBanana(edgeMbBanana);
        itemVm.setEdgeMbBananaReject(edgeMbBananaReject);
        itemVm.setElectComponent(electComponent);
        itemVm.setElectComponentReject(electComponentReject);
        itemVm.setSolderJoint(solderJoint);
        itemVm.setSolderJointReject(solderJointReject);
        itemVm.setWinConnector(winConnector);
        itemVm.setWinConnectorReject(winConnectorReject);
        itemVm.setRemarks(remarks);

        if ("Pass".equals(pcb) || "NA".equals(pcb)) {
            itemVm.setPcbRejectQty("0");
        } else {
            itemVm.setPcbRejectQty(pcbRejectQty);
        }
        if ("Pass".equals(handle) || "NA".equals(handle)) {
            itemVm.setHandleRejectQty("0");
        } else {
            itemVm.setHandleRejectQty(handleRejectQty);
        }
        if ("Pass".equals(metalFrame) || "NA".equals(metalFrame)) {
            itemVm.setMetalFrameRejectQty("0");
        } else {
            itemVm.setMetalFrameRejectQty(metalFrameRejectQty);
        }
        if ("Pass".equals(hardwareFasterners) || "NA".equals(hardwareFasterners)) {
            itemVm.setHardwareFasternersRejectQty("0");
        } else {
            itemVm.setHardwareFasternersRejectQty(hardwareFasternersRejectQty);
        }
        if ("Pass".equals(clipHolder) || "NA".equals(clipHolder)) {
            itemVm.setClipHolderRejectQty("0");
        } else {
            itemVm.setClipHolderRejectQty(clipHolderRejectQty);
        }
        if ("Pass".equals(pcbEdgeFinger) || "NA".equals(pcbEdgeFinger)) {
            itemVm.setPcbEdgeFingerRejectQty("0");
        } else {
            itemVm.setPcbEdgeFingerRejectQty(pcbEdgeFingerRejectQty);
        }
        if ("Pass".equals(connector) || "NA".equals(connector)) {
            itemVm.setConnectorRejectQty("0");
        } else {
            itemVm.setConnectorRejectQty(connectorRejectQty);
        }
        if ("Pass".equals(dutSockets) || "NA".equals(dutSockets)) {
            itemVm.setDutSocketsRejectQty("0");
        } else {
            itemVm.setDutSocketsRejectQty(dutSocketsRejectQty);
        }
        if ("Pass".equals(edgeMbBanana) || "NA".equals(edgeMbBanana)) {
            itemVm.setEdgeMbBananaRejectQty("0");
        } else {
            itemVm.setEdgeMbBananaRejectQty(edgeMbBananaRejectQty);
        }
        if ("Pass".equals(electComponent) || "NA".equals(electComponent)) {
            itemVm.setElectComponentRejectQty("0");
        } else {
            itemVm.setElectComponentRejectQty(electComponentRejectQty);
        }
        if ("Pass".equals(solderJoint) || "NA".equals(solderJoint)) {
            itemVm.setSolderJointRejectQty("0");
        } else {
            itemVm.setSolderJointRejectQty(solderJointRejectQty);
        }
        if ("Pass".equals(winConnector) || "NA".equals(winConnector)) {
            itemVm.setWinConnectorRejectQty("0");
        } else {
            itemVm.setWinConnectorRejectQty(winConnectorRejectQty);
        }

        if ("Fail".equals(pcb) || "Fail".equals(handle) || "Fail".equals(metalFrame) || "Fail".equals(hardwareFasterners) || "Fail".equals(clipHolder) || "Fail".equals(pcbEdgeFinger) || "Fail".equals(connector)
                || "Fail".equals(dutSockets) || "Fail".equals(edgeMbBanana) || "Fail".equals(electComponent) || "Fail".equals(solderJoint) || "Fail".equals(winConnector)) {
            finalStatus = "Fail";
            itemVm.setFlag("99");
        } else {
            finalStatus = "Pass";
            itemVm.setFlag("0");
        }
        itemVm.setFinalStatus(finalStatus);
        itemVm.setCreatedBy(userSession.getFullname());

        ItemVisualInspectionDAO itemVmD = new ItemVisualInspectionDAO();
        QueryResult q = itemVmD.insertItemVisualInspection(itemVm);
        if (!"0".equals(q.getGeneratedKey())) {

            itemVm = new ItemVisualInspection();
            LOGGER.info("pcbRejectUpload: " + pcbRejectUpload);

            //check if user upload any attachment
//            if (!pcbRejectUpload.isEmpty()) {
            if (pcbRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesPcb = pcbRejectUpload.getBytes();
                    Path pathPcb = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_pcb_" + pcbRejectUpload.getOriginalFilename());
                    Files.write(pathPcb, bytesPcb);
                    stringPathPcb = pathPcb.toString();
                    LOGGER.info("pathPcb : " + pathPcb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setPcbRejectUpload(stringPathPcb);
            }
            if (handleRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesHandle = handleRejectUpload.getBytes();
                    Path pathHandle = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_handle_" + handleRejectUpload.getOriginalFilename());
                    Files.write(pathHandle, bytesHandle);
                    stringPathHandle = pathHandle.toString();
                    LOGGER.info("pathHandle : " + pathHandle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setHandleRejectUpload(stringPathHandle);
            }
            if (metalFrameRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesMetalFrame = metalFrameRejectUpload.getBytes();
                    Path pathMetalFrame = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_metalFrame_" + metalFrameRejectUpload.getOriginalFilename());
                    Files.write(pathMetalFrame, bytesMetalFrame);
                    stringPathmetalFrame = pathMetalFrame.toString();
                    LOGGER.info("pathMetalFrame : " + pathMetalFrame);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setMetalFrameRejectUpload(stringPathmetalFrame);
            }
            if (hardwareFasternersRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesHardwareFasterners = hardwareFasternersRejectUpload.getBytes();
                    Path pathHardwareFasterners = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_hardwareFasteners_" + hardwareFasternersRejectUpload.getOriginalFilename());
                    Files.write(pathHardwareFasterners, bytesHardwareFasterners);
                    stringPathHardwareFasterners = pathHardwareFasterners.toString();
                    LOGGER.info("pathHardwareFasterners : " + pathHardwareFasterners);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setHardwareFasternersRejectUpload(stringPathHardwareFasterners);
            }
            if (clipHolderRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesClipHolder = clipHolderRejectUpload.getBytes();
                    Path pathClipHolder = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_clipHolder_" + clipHolderRejectUpload.getOriginalFilename());
                    Files.write(pathClipHolder, bytesClipHolder);
                    stringPathclipHolder = pathClipHolder.toString();
                    LOGGER.info("pathClipHolder : " + pathClipHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setClipHolderRejectUpload(stringPathclipHolder);
            }

            if (pcbEdgeFingerRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesPcbEdgeFinger = pcbEdgeFingerRejectUpload.getBytes();
                    Path pathPcbEdgeFinger = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_pcbEdgeFinger_" + pcbEdgeFingerRejectUpload.getOriginalFilename());
                    Files.write(pathPcbEdgeFinger, bytesPcbEdgeFinger);
                    stringPathPcbEdgeFinger = pathPcbEdgeFinger.toString();
                    LOGGER.info("pathPcbEdgeFinger : " + pathPcbEdgeFinger);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setPcbEdgeFingerRejectUpload(stringPathPcbEdgeFinger);
            }
            if (connectorRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesConnector = connectorRejectUpload.getBytes();
                    Path pathConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_connector_" + connectorRejectUpload.getOriginalFilename());
                    Files.write(pathConnector, bytesConnector);
                    stringPathConnector = pathConnector.toString();
                    LOGGER.info("pathConnector : " + pathConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setConnectorRejectUpload(stringPathConnector);
            }
            if (dutSocketsRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesDutSockets = dutSocketsRejectUpload.getBytes();
                    Path pathDutSockets = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_dutSockets_" + dutSocketsRejectUpload.getOriginalFilename());
                    Files.write(pathDutSockets, bytesDutSockets);
                    stringPathDutSockets = pathDutSockets.toString();
                    LOGGER.info("pathDutSockets : " + pathDutSockets);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setDutSocketsRejectUpload(stringPathDutSockets);
            }
            if (edgeMbBananaRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesEdgeMbBanana = edgeMbBananaRejectUpload.getBytes();
                    Path pathEdgeMbBanana = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_edgeMbBanana_" + edgeMbBananaRejectUpload.getOriginalFilename());
                    Files.write(pathEdgeMbBanana, bytesEdgeMbBanana);
                    stringPathEdgeMbBanana = pathEdgeMbBanana.toString();
                    LOGGER.info("pathEdgeMbBanana : " + pathEdgeMbBanana);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setEdgeMbBananaRejectUpload(stringPathEdgeMbBanana);
            }
            if (electComponentRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesElectComponent = electComponentRejectUpload.getBytes();
                    Path pathElectComponent = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_electComponent_" + electComponentRejectUpload.getOriginalFilename());
                    Files.write(pathElectComponent, bytesElectComponent);
                    stringPathElectComponent = pathElectComponent.toString();
                    LOGGER.info("pathElectComponent : " + pathElectComponent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setElectComponentRejectUpload(stringPathElectComponent);
            }
            if (solderJointRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesSolderJoint = solderJointRejectUpload.getBytes();
                    Path pathSolderJoint = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_solderJoint_" + solderJointRejectUpload.getOriginalFilename());
                    Files.write(pathSolderJoint, bytesSolderJoint);
                    stringPathSolderJoint = pathSolderJoint.toString();
                    LOGGER.info("pathSolderJoint : " + pathSolderJoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setSolderJointRejectUpload(stringPathSolderJoint);
            }

            if (winConnectorRejectUpload != null) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytesWinConnector = winConnectorRejectUpload.getBytes();
                    Path pathWinConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_winConnector_" + winConnectorRejectUpload.getOriginalFilename());
                    Files.write(pathWinConnector, bytesWinConnector);
                    stringPathWinConnector = pathWinConnector.toString();
                    LOGGER.info("pathWinConnector : " + pathWinConnector);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemVm.setWinConnectorRejectUpload(stringPathWinConnector);
            }
            itemVm.setId(q.getGeneratedKey());
            itemVmD = new ItemVisualInspectionDAO();
            QueryResult q3 = itemVmD.updateItemVisualInspectionForAttachment(itemVm);

            //update Item DB
            String status = "";
            Item item = new Item();
            item.setId(mibItemId);
            if ("Fail".equals(finalStatus)) {
                status = "Failed Visual Inspection - Waiting Maverick CA";
            } else {
                status = "Pending Functional Test";
            }
            item.setStatus(status);
            ItemDAO iD = new ItemDAO();
            QueryResult q2 = iD.updateItemStatus(item);

            if ("Fail".equals(finalStatus)) {
                redirectAttrs.addFlashAttribute("error", "Visual Inspection Fail. Pls go to Maverick Module for Corrective Action.");
                return "redirect:/hw/item/add2/" + mibItemId;
            } else {
                redirectAttrs.addFlashAttribute("success", "Visual Inspection Pass.");
                return "redirect:/hw/item/add2/" + mibItemId;
            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to save Visual Inspection. Pls Contact System Admin");
            return "redirect:/hw/item/add2/" + mibItemId;
        }
    }

    @RequestMapping(value = "/item/vm/downloadAttach/{id}/{type}", method = RequestMethod.GET)
    public void downloadAttachment(HttpServletRequest request,
            @PathVariable("type") String type,
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        ItemVisualInspectionDAO itemD = new ItemVisualInspectionDAO();
        ItemVisualInspection item = itemD.getItemVisualInspection(id);

        String attachment = "";
        switch (type) {
            case "pcb":
                attachment = item.getPcbRejectUpload();
                break;
            case "handle":
                attachment = item.getHandleRejectUpload();
                break;
            case "metalFrame":
                attachment = item.getMetalFrameRejectUpload();
                break;
            case "hardwareFasterners":
                attachment = item.getHardwareFasternersRejectUpload();
                break;
            case "clipHolder":
                attachment = item.getClipHolderRejectUpload();
                break;
            case "pcbEdgeFinger":
                attachment = item.getPcbEdgeFingerRejectUpload();
                break;
            case "connector":
                attachment = item.getConnectorRejectUpload();
                break;
            case "dutSockets":
                attachment = item.getDutSocketsRejectUpload();
                break;
            case "edgeMbBanana":
                attachment = item.getEdgeMbBananaRejectUpload();
                break;
            case "electComponent":
                attachment = item.getElectComponentRejectUpload();
                break;
            case "solderJoint":
                attachment = item.getSolderJointRejectUpload();
                break;
            case "winConnector":
                attachment = item.getWinConnectorRejectUpload();
                break;
            default:
                attachment = "";
                break;
        }

        // construct the complete absolute path of the file
        String fullPath = attachment;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = servletContext.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }

    @RequestMapping(value = "/item/addActivity/{id}", method = RequestMethod.GET)
    public String addActivity(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id
    ) {

        ItemDAO itemD = new ItemDAO();
        Item item = itemD.getHardwareDetail(id);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsage = pD.getActivityParameter("", "017");

        model.addAttribute("item", item);
        model.addAttribute("activity", paramItemUsage);
        return "item/item_check";
    }

    @RequestMapping(value = "/item/addActivity/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String addActivitySave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String viCheck,
            @RequestParam(required = false) String bibTestCheck,
            @RequestParam(required = false) String manualTestCheck,
            @RequestParam(required = false) String leakageTestCheck,
            @RequestParam(required = false) String psLeakageTestCheck,
            @RequestParam(required = false) String winchesterChamberLeakageTest,
            @RequestParam(required = false) String qtyField,
            @RequestParam(required = false) String dutField,
            @RequestParam(required = false) String manComp,
            @RequestParam(required = false, value = "component_name[]") List<String> nameRows,
            @RequestParam(required = false, value = "component_type[]") List<String> type,
            @RequestParam(required = false, value = "actual_value[]") List<String> num1Rows,
            @RequestParam(required = false, value = "percentage[]") List<String> num2Rows,
            @RequestParam(required = false, value = "lower[]") List<String> num3Rows,
            @RequestParam(required = false, value = "upper[]") List<String> num4Rows
    ) {

        ItemActivityConfig itemA = new ItemActivityConfig();
        itemA.setMibItemId(mibItemId);
        if ("on".equals(viCheck)) {
            itemA.setVi("Yes");
        } else {
            itemA.setVi("No");
        }
        if ("on".equals(bibTestCheck)) {
            itemA.setBibTest("Yes");
        } else {
            itemA.setBibTest("No");
        }
        
        ManualTestDAO test = new ManualTestDAO();
        int saizQty = Integer.parseInt(qtyField);
        int saizDut = Integer.parseInt(dutField);
        
        String status = "";
        String user = userSession.getLoginId();
        String flag = "1";
        
        if ("on".equals(manualTestCheck)) {
            itemA.setManualTest("Yes");
            QueryResult q0 = test.insertManualTest(mibItemId, qtyField, dutField, manComp, user, flag);
            for (int c1 = 1; c1 <= saizQty; c1++) {
                String qtyId = "0";
                test = new ManualTestDAO();
                QueryResult q1 = test.insertManual01(mibItemId, String.valueOf(c1), user, flag);
                if (!"0".equals(q1.getGeneratedKey())) {
                    qtyId = q1.getGeneratedKey();
                } else {
                    
                }
                for (int c2 = 1; c2<=saizDut; c2++) {
                    String dutId = "0";
                    test = new ManualTestDAO();
                    QueryResult q2 = test.insertManual02(mibItemId, qtyId, String.valueOf(c2), user, flag);
                    if (!"0".equals(q2.getGeneratedKey())) {
                        dutId = q2.getGeneratedKey();
                    } else {
                        
                    }
                    int saiz = nameRows.size();
                    for (int i=0; i<saiz; i++) {
                        test = new ManualTestDAO();
                        QueryResult q3 = test.insertManual03(mibItemId, qtyId, dutId, nameRows.get(i), num1Rows.get(i), num3Rows.get(i), num4Rows.get(i), num2Rows.get(i), status, user, flag);
                        if (!"0".equals(q3.getGeneratedKey())) {
                            
                        }
                    }
                }
            }
        } else {
            itemA.setManualTest("No");
        }
        if ("on".equals(leakageTestCheck)) {
            itemA.setLeakageTest("Yes");
        } else {
            itemA.setLeakageTest("No");
        }
        if ("on".equals(psLeakageTestCheck)) {
            itemA.setPsLeakageTest("Yes");
        } else {
            itemA.setPsLeakageTest("No");
        }
        if ("on".equals(winchesterChamberLeakageTest)) {
            itemA.setWinchesterChamberLeakageTest("Yes");
        } else {
            itemA.setWinchesterChamberLeakageTest("No");
        }
        itemA.setFlag("0");
        itemA.setCreatedBy(userSession.getFullname());
        itemA.setStatus("New Config");

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        QueryResult itemQ = itemD.insertItemActivityConfig(itemA);
        if (!"0".equals(itemQ.getGeneratedKey())) {

            //update status on Item table
            Item item = new Item();
            item.setId(mibItemId);
            item.setStatus("Pending Visual Inspection");
            item.setFlag("0");

            ItemDAO itemDA = new ItemDAO();
            QueryResult iQ = itemDA.updateItemStatusAndFlag(item);

            redirectAttrs.addFlashAttribute("success", "Activity Configuration Succesfully Added.");
            return "redirect:/hw/item/pending/";
        } else {

        }

        redirectAttrs.addFlashAttribute("error", "Failed to save Activity Configuration. Pls Contact System Admin");
        return "redirect:/hw/item/add2/" + mibItemId;
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
