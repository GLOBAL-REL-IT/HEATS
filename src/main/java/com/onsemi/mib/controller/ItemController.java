package com.onsemi.mib.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.onsemi.mib.dao.EmailVmFailDAO;
import com.onsemi.mib.dao.HardwareDAO;
import com.onsemi.mib.dao.ItemDAO;
import com.onsemi.mib.dao.HimsRequestDAO;
import com.onsemi.mib.dao.HostnameDAO;
import com.onsemi.mib.dao.ItemActivityConfigDAO;
import com.onsemi.mib.dao.ItemAluConfigDAO;
import com.onsemi.mib.dao.ItemFunctionalTestDAO;
import com.onsemi.mib.dao.ItemLogDAO;
import com.onsemi.mib.dao.ItemMaverickDAO;
import com.onsemi.mib.dao.ItemRecallDAO;
import com.onsemi.mib.dao.ItemStorageFactoryDAO;
import com.onsemi.mib.dao.ItemTransactionDAO;
import com.onsemi.mib.dao.ItemVisualInspectionDAO;
import com.onsemi.mib.dao.ManualTestDAO;
import com.onsemi.mib.dao.ParameterDetailsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import com.onsemi.mib.dao.RequestDAO;
import com.onsemi.mib.model.EmailVmFail;
import com.onsemi.mib.model.Hardware;
import com.onsemi.mib.model.Item;
import com.onsemi.mib.model.HimsInventory;
import com.onsemi.mib.model.Hostname;
import com.onsemi.mib.model.ItemActivityConfig;
import com.onsemi.mib.model.ItemFunctionalTest;
import com.onsemi.mib.model.ItemLog;
import com.onsemi.mib.model.ItemMaverick;
import com.onsemi.mib.model.ItemRecall;
import com.onsemi.mib.model.ItemStorageFactory;
import com.onsemi.mib.model.ItemTransaction;
import com.onsemi.mib.model.ItemVisualInspection;
import com.onsemi.mib.model.ManualTest;
import com.onsemi.mib.model.ParameterDetails;
import com.onsemi.mib.model.Request;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.model.WhRetrieval;
import com.onsemi.mib.tools.EmailSender;
import com.onsemi.mib.tools.HimsRetrieve;
import com.onsemi.mib.tools.QueryResult;
import com.onsemi.mib.tools.SPTSResponse;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final String FOLDER_TEST = "\\\\mysed-rel-app05\\f$\\HEATS\\FT\\"; //server

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST}) //without checking SPTS data and update to MIB DB
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

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsageEqpt = pD.getGroupParameterDetailList("", "018");
        model.addAttribute("paramItemUsageEqpt", paramItemUsageEqpt);

        ItemDAO itemD = new ItemDAO();
        List<Item> listAssemblyId = itemD.getItemAssemblyId("");
        model.addAttribute("listAssemblyId", listAssemblyId);

        itemD = new ItemDAO();
        List<Item> listModel = itemD.getItemModel("");
        model.addAttribute("listModel", listModel);

        itemD = new ItemDAO();
        List<Item> listManufacturer = itemD.getItemManufacturer("");
        model.addAttribute("listManufacturer", listManufacturer);

        itemD = new ItemDAO();
        List<Item> listEqptModel = itemD.getItemEqptModel("");
        model.addAttribute("listEqptModel", listEqptModel);

        itemD = new ItemDAO();
        List<Item> listEqptType = itemD.getItemEqptType("");
        model.addAttribute("listEqptType", listEqptType);

        itemD = new ItemDAO();
        List<Item> listEqptManufacturer = itemD.getItemEqptManufacturer("");
        model.addAttribute("listEqptManufacturer", listEqptManufacturer);

        itemD = new ItemDAO();
        List<Item> listStressType = itemD.getItemStressType("");
        model.addAttribute("listStressType", listStressType);

        model.addAttribute("userItemAdd", userSession.getItemAdd());
        model.addAttribute("userItemEdit", userSession.getItemEdit());
        model.addAttribute("userItemDelete", userSession.getItemDelete());
        model.addAttribute("userItemHwAdd", userSession.getItemHardwareAdd());
        model.addAttribute("userItemHwEdit", userSession.getItemHardwareEdit());
        model.addAttribute("userItemHwDelete", userSession.getItemHardwareDelete());
        model.addAttribute("userItemActConfig", userSession.getItemActivityConfig());
        model.addAttribute("userItemActAdd", userSession.getItemActivityAdd());
        model.addAttribute("userItemActEdit", userSession.getItemActivityEdit());
        model.addAttribute("userItemMovement", userSession.getItemMovementAdd());
        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

        return "item/item";
//        return "hardware/hardware_json";
    }

    @RequestMapping(value = "/item2/{itemType}", method = {RequestMethod.GET, RequestMethod.POST}) //checking SPTS data and update to MIB DB
    public String request2(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("itemType") String itemType
    //            @RequestParam(required = false) String itemType
    ) throws IOException {

        model.addAttribute("userItemAdd", userSession.getItemAdd());
        model.addAttribute("userItemEdit", userSession.getItemEdit());
        model.addAttribute("userItemDelete", userSession.getItemDelete());
        model.addAttribute("userItemHwAdd", userSession.getItemHardwareAdd());
        model.addAttribute("userItemHwEdit", userSession.getItemHardwareEdit());
        model.addAttribute("userItemHwDelete", userSession.getItemHardwareDelete());
        model.addAttribute("userItemActConfig", userSession.getItemActivityConfig());
        model.addAttribute("userItemActAdd", userSession.getItemActivityAdd());
        model.addAttribute("userItemActEdit", userSession.getItemActivityEdit());
        model.addAttribute("userItemMovement", userSession.getItemMovementAdd());
        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

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

                ItemDAO hwD = new ItemDAO();
                int countPkid = hwD.getCountPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));

                if (countPkid == 0) {
                    hwD = new ItemDAO();
                    QueryResult q = hwD.insertHardwareDetail(hw);
                    countAdd += q.getResult();

                    //update log
                    ItemLog log = new ItemLog();
                    log.setItemId(q.getGeneratedKey());
                    log.setDetail("Added From SPTS");
                    log.setCreatedBy(userSession.getFullname());
                    ItemLogDAO logD = new ItemLogDAO();
                    QueryResult logQ = logD.insertItemLog(log);
                } else if (countPkid == 1) {
                    hwD = new ItemDAO();
                    QueryResult q = hwD.updateHardwareDetailFromSpts(hw);
                    countAdd += q.getResult();

//                    hwD = new ItemDAO();
//                    Item items = hwD.getHardwareDetailByPkid(Integer.toString(getItemByParam.getJSONObject(i).getInt("PKID")));
//                    //update log
//                    ItemLog log = new ItemLog();
//                    log.setItemId(items.getId());
//                    log.setDetail("Updated From SPTS");
//                    log.setCreatedBy(userSession.getFullname());
//                    ItemLogDAO logD = new ItemLogDAO();
//                    QueryResult logQ = logD.insertItemLog(log);
                }
                count += 1;
            }

            ItemDAO hwD = new ItemDAO();
            List<Item> itemList = hwD.getHardwareDetailListByItemType(itemType);
            model.addAttribute("itemList", itemList);
            itemTypeTitle = " (" + itemType + ")";
        }
        model.addAttribute("itemTypeTitle", itemTypeTitle);

        ParameterDetailsDAO pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsage = pD.getGroupParameterDetailList("", "001");
        model.addAttribute("paramItemUsage", paramItemUsage);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsageEqpt = pD.getGroupParameterDetailList("", "018");
        model.addAttribute("paramItemUsageEqpt", paramItemUsageEqpt);

        ItemDAO itemD = new ItemDAO();
        List<Item> listAssemblyId = itemD.getItemAssemblyId("");
        model.addAttribute("listAssemblyId", listAssemblyId);

        itemD = new ItemDAO();
        List<Item> listModel = itemD.getItemModel("");
        model.addAttribute("listModel", listModel);

        itemD = new ItemDAO();
        List<Item> listManufacturer = itemD.getItemManufacturer("");
        model.addAttribute("listManufacturer", listManufacturer);

        itemD = new ItemDAO();
        List<Item> listEqptModel = itemD.getItemEqptModel("");
        model.addAttribute("listEqptModel", listEqptModel);

        itemD = new ItemDAO();
        List<Item> listEqptType = itemD.getItemEqptType("");
        model.addAttribute("listEqptType", listEqptType);

        itemD = new ItemDAO();
        List<Item> listEqptManufacturer = itemD.getItemEqptManufacturer("");
        model.addAttribute("listEqptManufacturer", listEqptManufacturer);

        itemD = new ItemDAO();
        List<Item> listStressType = itemD.getItemStressType("");
        model.addAttribute("listStressType", listStressType);

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
        int countSf = 0;
        int countSfAdd = 0;

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

        //add storage factory data to DB
        JSONObject paramsSf = new JSONObject();
        paramsSf.put("itemPKID", pkID);
        JSONArray getSFItemByParam = SPTSWebService.getSFItemByParam(paramsSf);

        for (int i = 0; i < getSFItemByParam.length(); i++) {

            ItemStorageFactoryDAO itemSfD = new ItemStorageFactoryDAO();
            int countSf1 = itemSfD.getCountPkidAndItemPkid(Integer.toString(getSFItemByParam.getJSONObject(i).getInt("PKID")), Integer.toString(getSFItemByParam.getJSONObject(i).getInt("ItemPKID")));
            if (countSf1 == 0) {
                ItemStorageFactory itemSf = new ItemStorageFactory();
                itemSf.setSfPkid(Integer.toString(getSFItemByParam.getJSONObject(i).getInt("PKID")));
                itemSf.setItemPkid(Integer.toString(getSFItemByParam.getJSONObject(i).getInt("ItemPKID")));
                if (getSFItemByParam.getJSONObject(i).has("TransTypeName")) {
                    itemSf.setMovementType(getSFItemByParam.getJSONObject(i).getString("TransTypeName"));
                }
                if (getSFItemByParam.getJSONObject(i).has("TransQty")) {
                    itemSf.setQty(Integer.toString(getSFItemByParam.getJSONObject(i).getInt("TransQty")));
                }
                if (getSFItemByParam.getJSONObject(i).has("SFRack")) {
                    itemSf.setRack(getSFItemByParam.getJSONObject(i).getString("SFRack"));
                }
                if (getSFItemByParam.getJSONObject(i).has("SFShelf")) {
                    itemSf.setShelf(getSFItemByParam.getJSONObject(i).getString("SFShelf"));
                }
                if (getSFItemByParam.getJSONObject(i).has("DateTime")) {
                    String dateTimeSf = getSFItemByParam.getJSONObject(i).getString("DateTime").substring(0, 10) + " " + getSFItemByParam.getJSONObject(i).getString("DateTime").substring(11, 19);
                    itemSf.setMovementDatetime(dateTimeSf);
                }
                itemSf.setFlag("0");
                itemSfD = new ItemStorageFactoryDAO();
                QueryResult q = itemSfD.insertItemStorageFactory(itemSf);
                countSfAdd += q.getResult();
            }
            countSf += 1;
        }
        LOGGER.info("Total data SF: " + countSf);
        LOGGER.info("Total insert SF: " + countSfAdd);

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

    @RequestMapping(value = "/item/ajaxTransactionQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String ajaxTransactionQuery(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException {

        int countTrans = 0;
        int countTransAdd = 0;

        //add transaction to DB
        JSONObject params2 = new JSONObject();
        params2.put("itemsPKID", itemPKID);
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
                    Object assembly = getTransactionByParam.getJSONObject(i).get("Remarks");
                    if (assembly instanceof String) {
                        item.setRemarks(getTransactionByParam.getJSONObject(i).getString("Remarks"));
                    } else {
                        item.setRemarks(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("Remarks")));
                    }
                }

                itemD = new ItemTransactionDAO();
                QueryResult qI = itemD.insertItemTransaction(item);
                countTransAdd += qI.getResult();
            }
            countTrans += 1;
        }
        LOGGER.info("Total data Trans: " + countTrans);
        LOGGER.info("Total insert Trans: " + countTransAdd);

        ItemTransactionDAO hwD = new ItemTransactionDAO();
        List<ItemTransaction> itemList = hwD.getItemTransactionListByItemPkid(itemPKID);

        JSONArray jsonArray = new JSONArray();

//        {"data": "itemId"},
        for (ItemTransaction itm : itemList) {
            JSONObject jsonObject = new JSONObject();
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

    @RequestMapping(value = "/item/ajaxStorage", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String ajaxStorage(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemPKID
    ) throws IOException, ClassNotFoundException, SQLException {

        JSONArray jsonArray = new JSONArray();

        if (itemPKID == null || "".equals(itemPKID)) {

            jsonArray = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("itemId", "");
            jsonObject.put("rack", "");
            jsonObject.put("shelf", "");
            jsonObject.put("qty", "");
            jsonObject.put("movementDateTime", "");
            jsonObject.put("boxNo", "");
            jsonObject.put("invId", "");
//            jsonObject.put("movementType", Strings.nullToEmpty(itm.getMovementType()));
            jsonArray.put(jsonObject);
        } else {
//            ItemStorageFactoryDAO hwD = new ItemStorageFactoryDAO();
//            List<ItemStorageFactory> itemList = hwD.getItemStorageFactoryListByItemPkid(itemPKID);

            ItemDAO itemD = new ItemDAO();
            Item item = itemD.getHardwareDetailByPkid(itemPKID);
            String itemType = item.getItemType();
            String itemId = item.getItemId();
            String subType = item.getSubType();
            LOGGER.info("itemType: " + itemType);
            LOGGER.info("subType: " + subType);

            String whereClause = "";

            if ("BIB CARD".equals(itemType) || "BIB Card".equals(itemType)) {
                if (null == subType) {
                    whereClause = " equipment_id = '" + itemId + "' ";
                } else {
                    switch (subType) {
                        case "Load Card":
                        case "LOAD CARD":
                        case "LC_DUT":
                            whereClause = " load_card_id = '" + itemId + "' ";
                            break;
                        case "Program Card":
                        case "PROGRAM CARD":
                        case "PC_DUT":
                            whereClause = " program_card_id = '" + itemId + "' ";
                            break;
                        default:
                            whereClause = " equipment_id = '" + itemId + "' ";
                            break;
                    }
                }
            } else if (itemType.contains("PCB")) {
                if (itemId.contains("QUAL A")) {
                    whereClause = " pcb_a = '" + itemId + "' ";
                } else if (itemId.contains("QUAL B")) {
                    whereClause = " pcb_b = '" + itemId + "' ";
                } else if (itemId.contains("QUAL C")) {
                    whereClause = " pcb_c = '" + itemId + "' ";
                } else if (itemId.contains("CONTROL")) {
                    whereClause = " pcb_ctr = '" + itemId + "' ";
                } else {
                    whereClause = " equipment_id = '" + itemId + "' ";
                }
            } else {
                whereClause = " equipment_id = '" + itemId + "' ";
            }

            HimsRequestDAO himsD = new HimsRequestDAO();
            List<HimsInventory> hims = himsD.getWhInventoryActiveListByItemId(whereClause);

            jsonArray = new JSONArray();

            for (HimsInventory itm : hims) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("itemId", Strings.nullToEmpty(itemId));
                jsonObject.put("rack", Strings.nullToEmpty(itm.getInventoryRack()));
                jsonObject.put("shelf", Strings.nullToEmpty(itm.getInventoryShelf()));
                jsonObject.put("qty", Strings.nullToEmpty(itm.getQuantity()));
                jsonObject.put("movementDateTime", Strings.nullToEmpty(itm.getInventoryDate()));
                jsonObject.put("boxNo", Strings.nullToEmpty(itm.getBoxNo()));
                jsonObject.put("invId", Strings.nullToEmpty(itm.getId()));
                jsonArray.put(jsonObject);
            }
        }

//        for (ItemStorageFactory itm : itemList) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("itemId", Strings.nullToEmpty(itm.getItemId()));
//            jsonObject.put("rack", Strings.nullToEmpty(itm.getRack()));
//            jsonObject.put("shelf", Strings.nullToEmpty(itm.getShelf()));
//            jsonObject.put("qty", Strings.nullToEmpty(itm.getQty()));
//            jsonObject.put("movementDateTime", Strings.nullToEmpty(itm.getMovementDatetime()));
//            jsonObject.put("movementType", Strings.nullToEmpty(itm.getMovementType()));
//            jsonArray.put(jsonObject);
//        }
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
        }

        return "item/item";
    }

    @RequestMapping(value = "/json/getitembyparamitemtype", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> jsonGetItemByParamForItemType(
            @ModelAttribute UserSession userSession,
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String itemType
    ) throws IOException {

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
        }

        model.addAttribute("count", count);
        model.addAttribute("countAdd", countAdd);

        return "item/hardwareSPTSUpdate";
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

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsageEqpt = pD.getGroupParameterDetailList("", "018");
        model.addAttribute("paramItemUsageEqpt", paramItemUsageEqpt);

        model.addAttribute("itemType", itemType);

        ItemDAO itemD = new ItemDAO();
        List<Item> listAssemblyId = itemD.getItemAssemblyId("");
        model.addAttribute("listAssemblyId", listAssemblyId);

        itemD = new ItemDAO();
        List<Item> listModel = itemD.getItemModel("");
        model.addAttribute("listModel", listModel);

        itemD = new ItemDAO();
        List<Item> listManufacturer = itemD.getItemManufacturer("");
        model.addAttribute("listManufacturer", listManufacturer);

        itemD = new ItemDAO();
        List<Item> listEqptModel = itemD.getItemEqptModel("");
        model.addAttribute("listEqptModel", listEqptModel);

        itemD = new ItemDAO();
        List<Item> listEqptType = itemD.getItemEqptType("");
        model.addAttribute("listEqptType", listEqptType);

        itemD = new ItemDAO();
        List<Item> listEqptManufacturer = itemD.getItemEqptManufacturer("");
        model.addAttribute("listEqptManufacturer", listEqptManufacturer);

        itemD = new ItemDAO();
        List<Item> listStressType = itemD.getItemStressType("");
        model.addAttribute("listStressType", listStressType);

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
            @RequestParam(required = false) String manufacturer,
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
    ) throws IOException {

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
        item.setManufacturer(manufacturer);
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

                //update log
                ItemLog log = new ItemLog();
                log.setItemId(i.getGeneratedKey());
                log.setDetail("New Record Added");
                log.setCreatedBy(userSession.getFullname());
                ItemLogDAO logD = new ItemLogDAO();
                QueryResult logQ = logD.insertItemLog(log);

                redirectAttrs.addFlashAttribute("success", "Succesfully registered Item ID: " + itemId);
                if ("BIB".equals(itemTypeRead) || "BIB Card".equals(itemTypeRead)) {
                    return "redirect:/hw/item/addActivity/" + i.getGeneratedKey();
                } else {
                    //insert into SPTS
                    JSONObject addItem = new JSONObject();
                    addItem.put("itemID", itemId);
                    addItem.put("itemName", itemName);
                    addItem.put("onHandQty", onHandQty);
                    addItem.put("prodQty", productionQty);
                    addItem.put("repairQty", repairQty);
                    addItem.put("otherQty", otherQty);
                    addItem.put("quarantineQty", quarantineQty);
                    addItem.put("externalCleaningQty", externalCleanQty);
                    addItem.put("externalRecleaningQty", externalRecleanQty);
                    addItem.put("internalCleaningQty", internalCleanQty);
                    addItem.put("internalRecleaningQty", internalRecleanQty);
                    addItem.put("storageFactoryQty", storageFactoryQty);
                    addItem.put("prodStagingQty", productionStagingQty);
                    addItem.put("otherONQty", otherOnsemiQty);
                    addItem.put("vendorQty", vendorQty);
                    addItem.put("minQty", minQty);
                    addItem.put("maxQty", maxQty);
                    addItem.put("unit", "pcs");
                    addItem.put("unitCost", unitCost);
                    addItem.put("rack", rack);
                    addItem.put("shelf", shelf);
                    addItem.put("model", model2);
                    addItem.put("manufacturer", manufacturer);
                    addItem.put("equipmentType", equipmentType);
                    addItem.put("equipmentModel", equipmentModel);
                    addItem.put("equipmentManufacturer", equipmentManufacturer);
                    addItem.put("stressType", stressType);
                    addItem.put("isCritical", "0");
                    if ("true".equals(isConsumable)) {
                        addItem.put("isConsumeable", "1");
                    } else {
                        addItem.put("isConsumeable", "0");
                    }
                    addItem.put("itemType", itemTypeRead);
                    addItem.put("subType", subType);
                    addItem.put("assemblyID", assemblyId);
                    addItem.put("remarks", remarks);
                    addItem.put("expirationDate", expirationDate);
//                    addItem.put("downtimeValue", downtimeValue);
//                    addItem.put("downtimeUnit", downtimeUnit);
//                    addItem.put("implementationCost", implementationCost);
//                    addItem.put("manpowerValue", manpowerValue);
//                    addItem.put("manpowerUnit", manpowerUnit);
                    addItem.put("complexityScore", "0");

                    SPTSResponse sr = SPTSWebService.insertItem(addItem);

                    if (sr.getStatus()) {

                        //update log
                        log = new ItemLog();
                        log.setItemId(i.getGeneratedKey());
                        log.setDetail("Successfully Added into SPTS");
                        log.setCreatedBy(userSession.getFullname());
                        logD = new ItemLogDAO();
                        QueryResult logQ2 = logD.insertItemLog(log);

                        redirectAttrs.addFlashAttribute("success", "Item added!");
                        //update SPTS PKID into MIB DB
                        item = new Item();
                        item.setSptsPkid(sr.getResponseId().toString());
                        item.setId(i.getGeneratedKey());

                        itemD = new ItemDAO();
                        QueryResult i2 = itemD.updateItemSPTSPKID(item);

//                        return "redirect:/spts/edit/" + sr.getResponseId();
                        return "redirect:/";
                    } else {
                        LinkedHashMap<String, String> item2;
                        ObjectMapper mapper = new ObjectMapper();
                        item2 = mapper.readValue(addItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
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
//                        return "spts/add";
                        return "redirect:/";
                    }

//                    return "redirect:/";
                }
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to registered Item ID: " + itemId + ". Pls contact system admin.");
                return "redirect:/hw/item/add";
            }
        }
    }

    @RequestMapping(value = "/item/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemUpdate(
            Model model2,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemPKID,
            @RequestParam(required = false) String mibId,
            @RequestParam(required = false) String itemType2,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String assemblyId,
            @RequestParam(required = false) String itemUsage,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String manufacturer,
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
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String aluHrs,
            @RequestParam(required = false) String expirationDate
    ) throws IOException {

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

        //get version from spts first
        String version = "";
        JSONObject params = new JSONObject();
        params.put("pkID", itemPKID);
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
        for (int i = 0; i < getItemByParam.length(); i++) {
            version = getItemByParam.getJSONObject(i).getString("Version");
        }
        //update to SPTS first then to local DB
        JSONObject addItem = new JSONObject();
        addItem.put("pkID", itemPKID);
        addItem.put("version", version);
        addItem.put("itemID", itemId);
        addItem.put("itemName", itemName);
        addItem.put("onHandQty", onHandQty);
        addItem.put("prodQty", productionQty);
        addItem.put("repairQty", repairQty);
        addItem.put("otherQty", otherQty);
        addItem.put("quarantineQty", quarantineQty);
        addItem.put("externalCleaningQty", externalCleanQty);
        addItem.put("externalRecleaningQty", externalRecleanQty);
        addItem.put("internalCleaningQty", internalCleanQty);
        addItem.put("internalRecleaningQty", internalRecleanQty);
        addItem.put("storageFactoryQty", storageFactoryQty);
        addItem.put("prodStagingQty", productionStagingQty);
        addItem.put("otherONQty", otherOnsemiQty);
        addItem.put("vendorQty", vendorQty);
        addItem.put("unit", "pcs");
//        if (minQty != null || !"".equals(minQty)) {
        addItem.put("minQty", minQty);
//        }
//        if (maxQty != null || !"".equals(maxQty)) {
        addItem.put("maxQty", maxQty);
//        }
//        if (unitCost != null || !"".equals(unitCost)) {
        addItem.put("unitCost", unitCost);
//        }
//        if (rack != null || !"".equals(rack)) {
        addItem.put("rack", rack);
//        }
//        if (shelf != null || !"".equals(shelf)) {
        addItem.put("shelf", shelf);
//        }
//        if (model2 != null || !"".equals(model2)) {
        addItem.put("model", model);
//        }
//        if (manufacturer != null || !"".equals(manufacturer)) {
        addItem.put("manufacturer", manufacturer);
//        }
//        if (equipmentType != null || !"".equals(equipmentType)) {
        addItem.put("equipmentType", equipmentType);
//        }
//        if (equipmentModel != null || !"".equals(equipmentModel)) {
        addItem.put("equipmentModel", equipmentModel);
//        }
//        if (equipmentManufacturer != null || !"".equals(equipmentManufacturer)) {
        addItem.put("equipmentManufacturer", equipmentManufacturer);
//        }
//        if (stressType != null || !"".equals(stressType)) {
        addItem.put("stressType", stressType);
//        }
//        if (itemType2 != null || !"".equals(itemType2)) {
        addItem.put("itemType", itemType2);
//        }
//        if (subType != null || !"".equals(subType)) {
        addItem.put("subType", subType);
//        }
//        if (assemblyId != null || !"".equals(assemblyId)) {
        addItem.put("assemblyID", assemblyId);
//        }
//        if (remarks != null || !"".equals(remarks)) {
        addItem.put("remarks", remarks);
//        }
//        if (expirationDate != null || !"".equals(expirationDate)) {
        addItem.put("expirationDate", expirationDate);
//        }
//        if (aluHrs != null || !"".equals(aluHrs)) {
        addItem.put("aluHrs", aluHrs);
//        }
        if ("on".equals(isConsumable)) {
            addItem.put("isConsumeable", "1");
        } else {
            addItem.put("isConsumeable", "0");
        }
        addItem.put("complexityScore", "0");
        addItem.put("isCritical", "0");

        SPTSResponse sr = SPTSWebService.updateItem(addItem);
        if (sr.getStatus()) {
            redirectAttrs.addFlashAttribute("success", "Item updated!");
            LOGGER.info("+++++++++SPTS Updated+++++++++++");
            //update SPTS PKID into MIB DB

            Item item = new Item();
            item.setId(mibId);
            item.setSptsPkid(itemPKID);
            item.setItemType(itemType2);
            item.setSubType(subType);
            item.setSiteName("Seremban");
            item.setItemId(itemId);
            item.setItemName(itemName);
            item.setAssemblyId(assemblyId);
            item.setRack(rack);
            item.setShelf(shelf);
            item.setItemUsage(itemUsage);
            item.setOnHandQty(onHandQty);

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
            item.setModel(model);
            item.setManufacturer(manufacturer);
            item.setEquipmentType(equipmentType);
            item.setEquipmentModel(equipmentModel);
            item.setEquipmentManufacturer(equipmentManufacturer);
            item.setStressType(stressType);
            item.setRemarks(remarks);
            item.setAluHrs(aluHrs);
            item.setCreatedBy(userSession.getFullname());
            item.setStatus(status);
            item.setFlag("1");
            //check if itemID exist or not
            ItemDAO itemD = new ItemDAO();
            int count = itemD.getCountItemIdAndNotMibId(itemId, mibId);
            if (count > 0) {
                redirectAttrs.addFlashAttribute("error", "Duplicate Item ID: " + itemId + ". Pls register with different Item ID.");
                return "redirect:/hw";
            } else {
                itemD = new ItemDAO();
                QueryResult i = itemD.updateHardwareDetail(item);

                if (i.getResult() == 1) {

                    //update log
                    ItemLog log = new ItemLog();
                    log.setItemId(mibId);
                    log.setDetail("Data Updated");
                    log.setCreatedBy(userSession.getFullname());
                    ItemLogDAO logD = new ItemLogDAO();
                    QueryResult logQ = logD.insertItemLog(log);

                    redirectAttrs.addFlashAttribute("success", "Succesfully update Item ID: " + itemId);
                    return "redirect:/hw";
                } else {
                    redirectAttrs.addFlashAttribute("error", "Failed to update Item ID: " + itemId + ". Pls contact system admin.");
                    return "redirect:/hw";
                }
            }
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(addItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model2.addAttribute("error", errorMessage);
            model2.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
//                        return "spts/add";
            return "redirect:/hw";
        }

    }

    @RequestMapping(value = "/item/update2", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemUpdate2(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            //            @RequestParam(required = false) String itemPKID,
            @RequestParam(required = false) String id,
            //            @RequestParam(required = false) String itemType2,
            @RequestParam(required = false) String itemTypeRead,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String assemblyId,
            @RequestParam(required = false) String itemUsage,
            @RequestParam(required = false) String model2,
            @RequestParam(required = false) String manufacturer,
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
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String flag,
            @RequestParam(required = false) String aluHrs,
            @RequestParam(required = false) String expirationDate
    ) throws IOException {

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
        if (aluHrs == null || "".equals(aluHrs)) {
            aluHrs = "0";
        }

        Item item = new Item();
        item.setId(id);
//        item.setSptsPkid(itemPKID);
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
        item.setManufacturer(manufacturer);
        item.setEquipmentType(equipmentType);
        item.setEquipmentModel(equipmentModel);
        item.setEquipmentManufacturer(equipmentManufacturer);
        item.setStressType(stressType);
        item.setRemarks(remarks);
        item.setAluHrs(aluHrs);
        item.setCreatedBy(userSession.getFullname());
        item.setStatus(status);
        item.setFlag(flag);
        //check if itemID exist or not
        ItemDAO itemD = new ItemDAO();
        int count = itemD.getCountItemIdAndNotMibId(itemId, id);
        if (count > 0) {
            redirectAttrs.addFlashAttribute("error", "Duplicate Item ID: " + itemId + ". Pls register with different Item ID.");
            return "redirect:/hw/item/add2/" + id;
        } else {
            itemD = new ItemDAO();
            QueryResult i = itemD.updateHardwareDetail2(item);

            if (i.getResult() == 1) {

                //update log
                ItemLog log = new ItemLog();
                log.setItemId(id);
                log.setDetail("Data Updated");
                log.setCreatedBy(userSession.getFullname());
                ItemLogDAO logD = new ItemLogDAO();
                QueryResult logQ = logD.insertItemLog(log);

                redirectAttrs.addFlashAttribute("success", "Succesfully update Item ID: " + itemId);
                return "redirect:/hw/item/add2/" + id;
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to update Item ID: " + itemId + ". Pls contact system admin.");
                return "redirect:/hw/item/add2/" + id;
            }
        }

    }

    @RequestMapping(value = "/item/delete/{itemPKID}/{mibId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemDelete(
            Model model2,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("itemPKID") String itemPKID,
            @PathVariable("mibId") String mibId
    ) throws IOException {

        //get version from spts first
        String version = "";
        String itemID = "";
        JSONObject params = new JSONObject();
        params.put("pkID", itemPKID);
        JSONArray getItemByParam = SPTSWebService.getItemByParam(params);
        for (int i = 0; i < getItemByParam.length(); i++) {
            version = getItemByParam.getJSONObject(i).getString("Version");
            itemID = getItemByParam.getJSONObject(i).getString("ItemID");
        }

        LOGGER.info("version: " + version);
        //update to SPTS first then to local DB
        JSONObject addItem = new JSONObject();
        addItem.put("version", version);
        addItem.put("pkID", itemPKID);

        SPTSResponse sr = SPTSWebService.disposeItem(addItem);
        if (sr.getStatus()) {
            redirectAttrs.addFlashAttribute("success", "Item updated!");
            LOGGER.info("+++++++++SPTS Updated+++++++++++");
            //update SPTS PKID into MIB DB

            Item item = new Item();
            item.setId(mibId);
            item.setStatus("Scrapped");
            item.setFlag("99");
            ItemDAO itemD = new ItemDAO();
            QueryResult i = itemD.updateItemStatusAndFlag(item);

            if (i.getResult() == 1) {

                //update log
                ItemLog log = new ItemLog();
                log.setItemId(mibId);
                log.setDetail("Item Scrapped");
                log.setCreatedBy(userSession.getFullname());
                ItemLogDAO logD = new ItemLogDAO();
                QueryResult logQ = logD.insertItemLog(log);

                redirectAttrs.addFlashAttribute("success", "Succesfully Scrap Item ID: " + itemID);
                return "redirect:/hw";
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to Scrap Item ID: " + itemID + ". Pls contact system admin.");
                return "redirect:/hw";
            }

        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(addItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
            model2.addAttribute("error", errorMessage);
            model2.addAttribute("item2", item2);
            redirectAttrs.addFlashAttribute("error", errorMessage);
            return "redirect:/hw";
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

        model.addAttribute("userItemAdd", userSession.getItemAdd());
        model.addAttribute("userItemEdit", userSession.getItemEdit());
        model.addAttribute("userItemDelete", userSession.getItemDelete());
        model.addAttribute("userItemHwAdd", userSession.getItemHardwareAdd());
        model.addAttribute("userItemHwEdit", userSession.getItemHardwareEdit());
        model.addAttribute("userItemHwDelete", userSession.getItemHardwareDelete());
        model.addAttribute("userItemActConfig", userSession.getItemActivityConfig());
        model.addAttribute("userItemActAdd", userSession.getItemActivityAdd());
        model.addAttribute("userItemActEdit", userSession.getItemActivityEdit());
        model.addAttribute("userItemMovement", userSession.getItemMovementAdd());
        model.addAttribute("userItemSfRecall", userSession.getItemSfRecall());

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
            String buttonDisabled = "disabled";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);

            if (item.getStatus().contains("BIB")) {
                model.addAttribute("bibshow", teActiveTab);
//                model.addAttribute("bibbutton",buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Manual")) {
                model.addAttribute("manshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
//                model.addAttribute("manbutton",buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Leakage")) {
                model.addAttribute("leakshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
//                model.addAttribute("leakbutton",buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Power")) {
                model.addAttribute("psshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
//                model.addAttribute("psbutton",buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Winchester")) {
                model.addAttribute("winshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
//                model.addAttribute("winbutton",buttonDisabled);
            }
        } else {
            String teActive = "";
            String teActiveTab = "";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);
        }

        ItemFunctionalTestDAO itemdao2 = new ItemFunctionalTestDAO();
        ItemFunctionalTest itemdata2 = itemdao2.getItemActivityByItemId(mibItemId);

        if (itemdata2 != null) {
            model.addAttribute("dataTest", itemdata2);

            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList(itemdata2.getBibStatus(), "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList(itemdata2.getLeakStatus(), "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList(itemdata2.getPsStatus(), "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList(itemdata2.getWinStatus(), "016");
            model.addAttribute("winResultData", winResultData);
        } else {
            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("winResultData", winResultData);
        }

        model.addAttribute("viCheck", viCheck);
        model.addAttribute("bibCheck", bibCheck);
        model.addAttribute("manCheck", manCheck);
        model.addAttribute("leakCheck", leakCheck);
        model.addAttribute("psCheck", psCheck);
        model.addAttribute("winCheck", winCheck);

        String groupId = userSession.getGroup();
        model.addAttribute("groupId", groupId);

        itemD = new ItemDAO();
        List<Item> listAssemblyId = itemD.getItemAssemblyId(item.getAssemblyId());
        model.addAttribute("listAssemblyId", listAssemblyId);

        itemD = new ItemDAO();
        List<Item> listModel = itemD.getItemModel(item.getModel());
        model.addAttribute("listModel", listModel);

        itemD = new ItemDAO();
        List<Item> listManufacturer = itemD.getItemManufacturer(item.getManufacturer());
        model.addAttribute("listManufacturer", listManufacturer);

        itemD = new ItemDAO();
        List<Item> listEqptModel = itemD.getItemEqptModel(item.getEquipmentModel());
        model.addAttribute("listEqptModel", listEqptModel);

        itemD = new ItemDAO();
        List<Item> listEqptType = itemD.getItemEqptType(item.getEquipmentType());
        model.addAttribute("listEqptType", listEqptType);

        itemD = new ItemDAO();
        List<Item> listEqptManufacturer = itemD.getItemEqptManufacturer(item.getEquipmentManufacturer());
        model.addAttribute("listEqptManufacturer", listEqptManufacturer);

        itemD = new ItemDAO();
        List<Item> listStressType = itemD.getItemStressType(item.getStressType());
        model.addAttribute("listStressType", listStressType);

        return "item/item_add2";
    }

    @RequestMapping(value = "/item/add2Query/{mibItemId}", method = RequestMethod.GET)
    public String itemAdd2Query(
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
            String buttonDisabled = "disabled";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);

            if (item.getStatus().contains("BIB")) {
                model.addAttribute("bibshow", teActiveTab);
//                model.addAttribute("bibbutton",buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Manual")) {
                model.addAttribute("manshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
//                model.addAttribute("manbutton",buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Leakage")) {
                model.addAttribute("leakshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
//                model.addAttribute("leakbutton",buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Power")) {
                model.addAttribute("psshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
//                model.addAttribute("psbutton",buttonDisabled);
                model.addAttribute("winbutton", buttonDisabled);
            } else if (item.getStatus().contains("Winchester")) {
                model.addAttribute("winshow", teActiveTab);
                model.addAttribute("bibbutton", buttonDisabled);
                model.addAttribute("manbutton", buttonDisabled);
                model.addAttribute("leakbutton", buttonDisabled);
                model.addAttribute("psbutton", buttonDisabled);
//                model.addAttribute("winbutton",buttonDisabled);
            }
        } else {
            String teActive = "";
            String teActiveTab = "";
            model.addAttribute("teActive", teActive);
            model.addAttribute("teActiveTab", teActiveTab);
        }

        ItemFunctionalTestDAO itemdao2 = new ItemFunctionalTestDAO();
        ItemFunctionalTest itemdata2 = itemdao2.getItemActivityByItemId(mibItemId);

        if (itemdata2 != null) {
            model.addAttribute("dataTest", itemdata2);

            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList(itemdata2.getBibStatus(), "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList(itemdata2.getLeakStatus(), "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList(itemdata2.getPsStatus(), "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList(itemdata2.getWinStatus(), "016");
            model.addAttribute("winResultData", winResultData);
        } else {
            ParameterDetailsDAO pDx = new ParameterDetailsDAO();
            List<ParameterDetails> bibResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("bibResultData", bibResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> leakResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("leakResultData", leakResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> psResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("psResultData", psResultData);

            pDx = new ParameterDetailsDAO();
            List<ParameterDetails> winResultData = pDx.getGroupParameterDetailList("", "016");
            model.addAttribute("winResultData", winResultData);
        }

        model.addAttribute("viCheck", viCheck);
        model.addAttribute("bibCheck", bibCheck);
        model.addAttribute("manCheck", manCheck);
        model.addAttribute("leakCheck", leakCheck);
        model.addAttribute("psCheck", psCheck);
        model.addAttribute("winCheck", winCheck);

        return "item/item_add2_query";
    }

    @RequestMapping(value = "/item/updateManualTestStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateManualTestStatus(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mibItemId
    ) {

        String finalStatus = "";
        String statusMan = "";
        String statusLeak = "";
        String statusPs = "";
        String statusWin = "";

        ItemActivityConfig iac = new ItemActivityConfig();
        ItemActivityConfigDAO iacdao = new ItemActivityConfigDAO();

        iac = iacdao.getItemActivityByItemId(mibItemId);
        if (iac != null) {
            statusMan = iac.getManualTest();
            statusLeak = iac.getLeakageTest();
            statusPs = iac.getPsLeakageTest();
            statusWin = iac.getWinchesterChamberLeakageTest();
        }

        if (statusMan.equals("Yes")) {
            if (statusLeak.equals("Yes")) {
                finalStatus = "Pending Functional Test - Leakage Test";
            } else if (statusPs.equals("Yes")) {
                finalStatus = "Pending Functional Test - Power Supply Test";
            } else if (statusWin.equals("Yes")) {
                finalStatus = "Pending Functional Test - Winchester Chamber Test";
            }

            ItemDAO itemdao = new ItemDAO();

            Item hardwaredetail = new Item();
            hardwaredetail.setId(mibItemId);
            hardwaredetail.setStatus(finalStatus);

            QueryResult q2 = itemdao.updateItemStatus(hardwaredetail);
        } else {
            LOGGER.info("DO NOT PROCESS ANYTHING HERE");
        }

        // FUNCTION UPDATE STATUS KE NEXT FUNCTIONAL TEST
//        redirectAttrs.addFlashAttribute("error", "Failed to save Visual Inspection. Pls Contact System Admin");
        return "redirect:/hw/item/add2/" + mibItemId;
    }

    public String getLatestStatus(String itemId) {
        String status = "";
        String statusVi = "";
        String statusBib = "";
        String statusMan = "";
        String statusLeak = "";
        String statusPs = "";
        String statusWin = "";
        ItemActivityConfig iac = new ItemActivityConfig();
        ItemActivityConfigDAO iacdao = new ItemActivityConfigDAO();

        iac = iacdao.getItemActivityByItemId(itemId);
        if (iac != null) {
            statusVi = iac.getVi();
            statusBib = iac.getBibTest();
            statusMan = iac.getManualTest();
            statusLeak = iac.getLeakageTest();
            statusPs = iac.getPsLeakageTest();
            statusWin = iac.getWinchesterChamberLeakageTest();
        }

        // APA BENDA KITA KENA BUAT DEKAT SINI UNTUK DOUBLE CHECK ON THE LATEST CODE SO NO REPEATED STATUS
        return status;
    }

    public String getCurrentStatus(String itemId) {
        String status = "No setup found";
        String statusVi = "";
        String statusBib = "";
        String statusMan = "";
        String statusLeak = "";
        String statusPs = "";
        String statusWin = "";
        ItemActivityConfig iac = new ItemActivityConfig();
        ItemActivityConfigDAO iacdao = new ItemActivityConfigDAO();

        iac = iacdao.getItemActivityByItemId(itemId);
        if (iac != null) {
            statusVi = iac.getVi();
            statusBib = iac.getBibTest();
            statusMan = iac.getManualTest();
            statusLeak = iac.getLeakageTest();
            statusPs = iac.getPsLeakageTest();
            statusWin = iac.getWinchesterChamberLeakageTest();
        }

        // HANYA UNTUK FIRST TIME SELEPAS DIA COMPLETE KAN VISUAL INSPECTION
        if (statusBib.equals("Yes")) {
            status = "Pending Functional Test - BIB Test";
        } else if (statusMan.equals("Yes")) {
            status = "Pending Functional Test - Manual Test";
        } else if (statusLeak.equals("Yes")) {
            status = "Pending Functional Test - Leakage Test";
        } else if (statusPs.equals("Yes")) {
            status = "Pending Functional Test - Power Supply Test";
        } else if (statusWin.equals("Yes")) {
            status = "Pending Functional Test - Winchester Chamber Test";
        } else {
            status = "Good";
        }

        return status;
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
    ) throws IOException {

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
        String emailBodyFail = "";

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
            emailBodyFail = "PCB Fail : " + pcbReject + "<br /> ";
        }
        if ("Pass".equals(handle) || "NA".equals(handle)) {
            itemVm.setHandleRejectQty("0");
        } else {
            itemVm.setHandleRejectQty(handleRejectQty);
            emailBodyFail += "Handle Fail : " + handleReject + "<br /> ";
        }
        if ("Pass".equals(metalFrame) || "NA".equals(metalFrame)) {
            itemVm.setMetalFrameRejectQty("0");
        } else {
            itemVm.setMetalFrameRejectQty(metalFrameRejectQty);
            emailBodyFail += "MetalFrame Fail : " + metalFrameReject + "<br /> ";
        }
        if ("Pass".equals(hardwareFasterners) || "NA".equals(hardwareFasterners)) {
            itemVm.setHardwareFasternersRejectQty("0");
        } else {
            itemVm.setHardwareFasternersRejectQty(hardwareFasternersRejectQty);
            emailBodyFail += "Hardware Fasterner Fail : " + hardwareFasternersReject + "<br /> ";
        }
        if ("Pass".equals(clipHolder) || "NA".equals(clipHolder)) {
            itemVm.setClipHolderRejectQty("0");
        } else {
            itemVm.setClipHolderRejectQty(clipHolderRejectQty);
            emailBodyFail += "Clip Holder Fail : " + clipHolderReject + "<br /> ";
        }
        if ("Pass".equals(pcbEdgeFinger) || "NA".equals(pcbEdgeFinger)) {
            itemVm.setPcbEdgeFingerRejectQty("0");
        } else {
            itemVm.setPcbEdgeFingerRejectQty(pcbEdgeFingerRejectQty);
            emailBodyFail += "PCB Edge Finger Fail : " + pcbEdgeFingerReject + "<br /> ";
        }
        if ("Pass".equals(connector) || "NA".equals(connector)) {
            itemVm.setConnectorRejectQty("0");
        } else {
            itemVm.setConnectorRejectQty(connectorRejectQty);
            emailBodyFail += "Connector Fail : " + connectorReject + "<br /> ";
        }
        if ("Pass".equals(dutSockets) || "NA".equals(dutSockets)) {
            itemVm.setDutSocketsRejectQty("0");
        } else {
            itemVm.setDutSocketsRejectQty(dutSocketsRejectQty);
            emailBodyFail += "DUT Socket Fail : " + dutSocketsReject + "<br /> ";
        }
        if ("Pass".equals(edgeMbBanana) || "NA".equals(edgeMbBanana)) {
            itemVm.setEdgeMbBananaRejectQty("0");
        } else {
            itemVm.setEdgeMbBananaRejectQty(edgeMbBananaRejectQty);
            emailBodyFail += "Edge MB Banana Fail : " + edgeMbBananaReject + "<br /> ";
        }
        if ("Pass".equals(electComponent) || "NA".equals(electComponent)) {
            itemVm.setElectComponentRejectQty("0");
        } else {
            itemVm.setElectComponentRejectQty(electComponentRejectQty);
            emailBodyFail += "Elect Component Fail : " + electComponentReject + "<br /> ";
        }
        if ("Pass".equals(solderJoint) || "NA".equals(solderJoint)) {
            itemVm.setSolderJointRejectQty("0");
        } else {
            itemVm.setSolderJointRejectQty(solderJointRejectQty);
            emailBodyFail += "Solder Joint Fail : " + solderJointReject + "<br /> ";
        }
        if ("Pass".equals(winConnector) || "NA".equals(winConnector)) {
            itemVm.setWinConnectorRejectQty("0");
        } else {
            itemVm.setWinConnectorRejectQty(winConnectorRejectQty);
            emailBodyFail += "Win Connector Fail : " + winConnectorReject + "<br /> ";
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
            String flag = "";
            Item item = new Item();
            item.setId(mibItemId);
            if ("Fail".equals(finalStatus)) {
                status = "Failed Visual Inspection - Waiting Maverick CA";
                flag = "0";
            } else {
//                status = "Pending Functional Test";
                status = getCurrentStatus(mibItemId);
                if (status.equals("Good")) {
                    flag = "1";
                    insertSPTSData(mibItemId, userSession.getFullname()); //insert to SPTS if no functional test
                } else {
                    flag = "0";
                }
//                insertSPTSData(mibItemId, userSession.getFullname());
            }
            item.setStatus(status);
            item.setFlag(flag);
            ItemDAO iD = new ItemDAO();
//            QueryResult q2 = iD.updateItemStatus(item);
            QueryResult q2 = iD.updateItemStatusAndFlag(item);

            //update log
            ItemLog log = new ItemLog();
            log.setItemId(mibItemId);
            log.setDetail("VM Data Added");
            log.setCreatedBy(userSession.getFullname());
            ItemLogDAO logD = new ItemLogDAO();
            QueryResult logQ = logD.insertItemLog(log);

            if ("Fail".equals(finalStatus)) {

                //save to maverick table
                ItemMaverick maverick = new ItemMaverick();
                maverick.setMibItemId(mibItemId);
                maverick.setModule("Hardware Registration");
                maverick.setSubmodule("Visual Inspection");
                maverick.setStatus("Failed Visual Inspection");
                maverick.setFlag("0");
                maverick.setCreatedBy(userSession.getFullname());
                ItemMaverickDAO maverickD = new ItemMaverickDAO();
                QueryResult maverickAdd = maverickD.insertItemMaverick(maverick);

                EmailVmFailDAO userDao = new EmailVmFailDAO();
                List<EmailVmFail> userRecipientsList = userDao.getEmailVmFailList();

                String[] to = new String[userRecipientsList.size()];
                for (int i = 0; i < userRecipientsList.size(); i++) {
                    to[i] = userRecipientsList.get(i).getEmail();
                }

                //get current date and time
                LocalDateTime instance = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedString = formatter.format(instance); //15-02-2022 12:43

                //gethostname
                HostnameDAO hostnameD = new HostnameDAO();
                Hostname h = hostnameD.getHostnameFlagZero();
                String hostname = h.getHostname();

                ItemDAO itemD = new ItemDAO();
                Item item2 = itemD.getHardwareDetail(mibItemId);

                //send INFORMATION email
                LOGGER.info("######################### START EMAIL TO PIC ########################### ");
                EmailSender emailSender = new EmailSender();
                emailSender.htmlEmailTable(
                        servletContext,
                        "", //user name requestor
                        to, //to
                        //                        emailTo,
                        "Item Registration - Failed Visual Inspection", //subject
                        "<br />"
                        + "Please be informed that the item below failed the visual inspection."
                        + "<br /> "
                        + "<br /> "
                        + "Item ID: " + item2.getItemId()
                        + "<br /> "
                        + "Inspection Date: " + formattedString
                        + "<br /> "
                        + "<br /> "
                        + "Detail: <br />" + emailBodyFail
                        + "<br /> "
                        + "Please click <a href=\"http://" + hostname + "/HEATS/hw/item/add2/" + mibItemId + " \">HERE</a> for more detail."
                        + "<br /> "
                        + "<br />Thank you." //msg
                );

                redirectAttrs.addFlashAttribute("error", "Visual Inspection Fail. Pls go to Maverick Module for Corrective Action.");
                return "redirect:/hw/item/add2/" + mibItemId;
            } else {
                redirectAttrs.addFlashAttribute("success", "Visual Inspection Pass.");
                if (status.equals("Good")) {
                    return "redirect:/hw/item/pending";
                } else {
                    return "redirect:/hw/item/add2/" + mibItemId;
                }

            }

        } else {
            redirectAttrs.addFlashAttribute("error", "Failed to save Visual Inspection. Pls Contact System Admin");
            return "redirect:/hw/item/add2/" + mibItemId;
        }
    }

    @RequestMapping(value = "/item/save/{jenis}", method = {RequestMethod.GET, RequestMethod.POST})
    public String itemFunctionalTestSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("jenis") String jenis,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String totalQty,
            @RequestParam(required = false) String bibResult,
            @RequestParam(required = false) MultipartFile bibUpload,
            @RequestParam(required = false) String leakResult,
            @RequestParam(required = false) MultipartFile leakUpload,
            @RequestParam(required = false) String psResult,
            @RequestParam(required = false) MultipartFile psUpload,
            @RequestParam(required = false) String winResult,
            @RequestParam(required = false) MultipartFile winUpload,
            HttpServletResponse response
    ) throws IOException {

        // jenis2
        // ------------------------ // 
        // /bibTest
        // /leakTest
        // /psTest
        // /winTest
        String username = userSession.getFullname();
        String newStatus = "";
        String latestResult = "";
        checkInsertFunctionalTest(mibItemId, username);

        String pathBib = "";
        String pathLeak = "";
        String pathPs = "";
        String pathWin = "";

        switch (jenis) {
            case "bibTest":
                LOGGER.info("KITA MASUK DEKAT BIB TEST");
                break;
            case "leakTest":
                LOGGER.info("KITA MASUK UNTUK LEAKEAGE TEST");
                break;
            case "psTest":
                LOGGER.info("KITA DAPAT BUAT POWER SUPPLY TEST");
                break;
            case "winTest":
                LOGGER.info("KITA BUAT WINCHESTER TEST");
                break;
            default:
                LOGGER.info("KITA BREAK BY DEFAULT");
                break;
        }

        ItemFunctionalTest item = new ItemFunctionalTest();

        ItemActivityConfigDAO itemdao2 = new ItemActivityConfigDAO();
        ItemActivityConfig itemdata = itemdao2.getItemActivityByItemId(mibItemId);
        // SINI TAK CHECK DATA NULL - ASSUME SEBELUM NI DA CHECK AND *MESTI* ADA DATA
        String checkBib = itemdata.getBibTest();
        String checkMan = itemdata.getManualTest();
        String checkLeak = itemdata.getLeakageTest();
        String checkPs = itemdata.getPsLeakageTest();;
        String checkWin = itemdata.getWinchesterChamberLeakageTest();

        if (bibUpload != null) {
            try {
                // Get the file and save it somewhere
                byte[] bytesConnector = bibUpload.getBytes();
//                Path pathBibConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_winConnector_" + bibUpload.getOriginalFilename());
                Path pathConnector = Paths.get(FOLDER_TEST + "_bibTest_" + bibUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                if (bibUpload.getOriginalFilename() == null || bibUpload.getOriginalFilename().equalsIgnoreCase("")) {

                } else {
                    Files.write(pathConnector, bytesConnector);
                    pathBib = pathConnector.toString();
                }

                // UPDATE TABLE ITEM_FUNCTIONAL_TEST START
                ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
                item.setMibItemId(mibItemId);
                item.setBibQty(totalQty);
                item.setBibStatus(bibResult);
                item.setBibUpload(pathBib);
                item.setRemark("");
                item.setFinalStatus("");
                item.setFlag("0");
                itemdao.updateBibTest(item);
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST END

                // UPDATE STATUS TABLE ITEM START
                Item item0 = new Item();
                item0.setId(mibItemId);
                item0.setFlag("0");

                if (bibResult.equals("Pass")) {
                    if (checkMan.equals("Yes")) {
                        newStatus = "Pending Functional Test - Manual Test";
                    } else if (checkLeak.equals("Yes")) {
                        newStatus = "Pending Functional Test - Leakage Test";
                    } else if (checkPs.equals("Yes")) {
                        newStatus = "Pending Functional Test - Power Supply Test";
                    } else if (checkWin.equals("Yes")) {
                        newStatus = "Pending Functional Test - Winchester Chamber Test";
                    } else {
                        newStatus = "Good";
                        item0.setFlag("1");
                        insertSPTSData(mibItemId, username);
                    }
                } else {
                    newStatus = "Failed Functional Test - BIB Test - Waiting Maverick CA";
                    updateMaverickAndEmail(mibItemId, username, "BIB");
                }
                item0.setStatus(newStatus);

                ItemDAO itemDA = new ItemDAO();
                QueryResult iQ = itemDA.updateItemStatusAndFlag(item0);
                // UPDATE STATUS TABLE ITEM END
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (leakUpload != null) {
            try {
                // Get the file and save it somewhere
                byte[] bytesConnector = leakUpload.getBytes();
//                Path pathBibConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_winConnector_" + bibUpload.getOriginalFilename());
                Path pathConnector = Paths.get(FOLDER_TEST + "_leakTest_" + leakUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                if (leakUpload.getOriginalFilename() == null || leakUpload.getOriginalFilename().equalsIgnoreCase("")) {

                } else {
                    Files.write(pathConnector, bytesConnector);
                    pathLeak = pathConnector.toString();
                }
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST START
                ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
                item.setMibItemId(mibItemId);
                item.setLeakQty(totalQty);
                item.setLeakStatus(leakResult);
                item.setLeakUpload(pathLeak);
                item.setRemark("");
                item.setFinalStatus("");
                item.setFlag("0");
                itemdao.updateLeakageTest(item);
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST END

                // UPDATE STATUS TABLE ITEM START
                Item item0 = new Item();
                item0.setId(mibItemId);
                item0.setFlag("0");

                if (leakResult.equals("Pass")) {
                    if (checkPs.equals("Yes")) {
                        newStatus = "Pending Functional Test - Power Supply Test";
                    } else if (checkWin.equals("Yes")) {
                        newStatus = "Pending Functional Test - Winchester Chamber Test";
                    } else {
                        newStatus = "Good";
                        item0.setFlag("1");
                        insertSPTSData(mibItemId, username);
                    }
                } else {
                    newStatus = "Failed Functional Test - Leakage Test - Waiting Maverick CA";
                    updateMaverickAndEmail(mibItemId, username, "Leakage");
                }
                item0.setStatus(newStatus);

                ItemDAO itemDA = new ItemDAO();
                QueryResult iQ = itemDA.updateItemStatusAndFlag(item0);
                // UPDATE STATUS TABLE ITEM END
            } catch (IOException e) {
                e.printStackTrace();
            }
//            itemVm.setWinConnectorRejectUpload(pathLeak);
        }
        if (psUpload != null) {
            try {
                // Get the file and save it somewhere
                byte[] bytesConnector = psUpload.getBytes();
//                Path pathBibConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_winConnector_" + bibUpload.getOriginalFilename());
                Path pathConnector = Paths.get(FOLDER_TEST + "_psTest_" + psUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                if (psUpload.getOriginalFilename() == null || psUpload.getOriginalFilename().equalsIgnoreCase("")) {

                } else {
                    Files.write(pathConnector, bytesConnector);
                    pathPs = pathConnector.toString();
                }
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST START
                ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
                item.setMibItemId(mibItemId);
                item.setPsQty(totalQty);
                item.setPsStatus(psResult);
                item.setPsUpload(pathPs);
                item.setRemark("");
                item.setFinalStatus("");
                item.setFlag("0");
                itemdao.updatePowerTest(item);
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST END

                // UPDATE STATUS TABLE ITEM START
                Item item0 = new Item();
                item0.setId(mibItemId);
                item0.setFlag("0");

                if (psResult.equals("Pass")) {
                    if (checkWin.equals("Yes")) {
                        newStatus = "Pending Functional Test - Winchester Chamber Test";
                    } else {
                        newStatus = "Good";
                        item0.setFlag("1");
                        insertSPTSData(mibItemId, username);
                    }
                } else {
                    newStatus = "Failed Functional Test - Power Supply Test - Waiting Maverick CA";
                    updateMaverickAndEmail(mibItemId, username, "Power");
                }
                item0.setStatus(newStatus);

                ItemDAO itemDA = new ItemDAO();
                QueryResult iQ = itemDA.updateItemStatusAndFlag(item0);
                // UPDATE STATUS TABLE ITEM END
            } catch (IOException e) {
                e.printStackTrace();
            }
//            itemVm.setWinConnectorRejectUpload(pathBib);
        }
        if (winUpload != null) {
            try {
                // Get the file and save it somewhere
                byte[] bytesConnector = winUpload.getBytes();
//                Path pathBibConnector = Paths.get(UPLOADED_FOLDER + q.getGeneratedKey() + "_winConnector_" + bibUpload.getOriginalFilename());
                Path pathConnector = Paths.get(FOLDER_TEST + "_winTest_" + winUpload.getOriginalFilename()); // THIS ONE TESTING ONLY, USE CORRECT GENERATED KEY
                if (winUpload.getOriginalFilename() == null || winUpload.getOriginalFilename().equalsIgnoreCase("")) {

                } else {
                    Files.write(pathConnector, bytesConnector);
                    pathWin = pathConnector.toString();
                }
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST START
                ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
                item.setMibItemId(mibItemId);
                item.setWinQty(totalQty);
                item.setWinStatus(winResult);
                item.setWinUpload(pathWin);
                item.setRemark("");
                item.setFinalStatus("");
                item.setFlag("0");
                itemdao.updateWinchesterTest(item);
                // UPDATE TABLE ITEM_FUNCTIONAL_TEST END

                // UPDATE STATUS TABLE ITEM START
                Item item0 = new Item();
                item0.setId(mibItemId);
                item0.setFlag("1");

                if (winResult.equals("Pass")) {
                    newStatus = "Good";
                    insertSPTSData(mibItemId, username);
                } else {
                    newStatus = "Failed Functional Test - Winchester Chamber Leakage Test - Waiting Maverick CA";
                    updateMaverickAndEmail(mibItemId, username, "Winchester");
                }
                item0.setStatus(newStatus);

                ItemDAO itemDA = new ItemDAO();
                QueryResult iQ = itemDA.updateItemStatusAndFlag(item0);
                // UPDATE STATUS TABLE ITEM END
            } catch (IOException e) {
                e.printStackTrace();
            }
//            itemVm.setWinConnectorRejectUpload(pathBib);
        }

        return "redirect:/hw/item/add2/" + mibItemId;
    }

    @RequestMapping(value = "/item/updateStatus/{id}", method = RequestMethod.GET)
    public String updateStatus(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id) throws IOException {

        String username = userSession.getFullname();
        insertSPTSData(id, username);
        return "redirect:/hw/item/add2/" + id;
    }

    @RequestMapping(value = "/item/updateStatusFailed/{id}", method = RequestMethod.GET)
    public String updateStatusFailed(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id) {

        String username = userSession.getFullname();
        String manual = "Manual";
        String status = "Failed Functional Test - Manual Test - Waiting Maverick CA";

        Item item0 = new Item();
        ItemDAO itemDA = new ItemDAO();

        item0.setId(id);
        item0.setFlag("0");
        item0.setStatus(status);
        QueryResult iQ = itemDA.updateItemStatusAndFlag(item0);

        updateMaverickAndEmail(id, username, manual);
        return "redirect:/hw/item/add2/" + id;
    }

    public void updateMaverickAndEmail(String mibItemId, String username, String jenis) {

        String module = "Hardware Registration";
        String sub = "";
        String status = "Failed Functional Test";

        switch (jenis) {
            case "BIB":
                sub = "BIB Test";
                break;
            case "Manual":
                sub = "Manual Test";
                break;
            case "Leakage":
                sub = "Leakage Test";
                break;
            case "Power":
                sub = "Power Supply Leakage Test";
                break;
            case "Winchester":
                sub = "Winchester Chamber Leakage Test";
                break;
            default:
                break;
        }

        ItemMaverick maverick = new ItemMaverick();
        maverick.setMibItemId(mibItemId);
        maverick.setModule(module);
        maverick.setSubmodule(sub);
        maverick.setStatus(status + " - " + sub);
        maverick.setFlag("0");
        maverick.setCreatedBy(username);
        ItemMaverickDAO maverickD = new ItemMaverickDAO();
        QueryResult maverickAdd = maverickD.insertItemMaverick(maverick);

        EmailVmFailDAO userDao = new EmailVmFailDAO();
        List<EmailVmFail> userRecipientsList = userDao.getEmailVmFailList();

        String[] to = new String[userRecipientsList.size()];
        for (int i = 0; i < userRecipientsList.size(); i++) {
            to[i] = userRecipientsList.get(i).getEmail();
        }

        //get current date and time
        LocalDateTime instance = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedString = formatter.format(instance); //15-02-2022 12:43

        //gethostname
        HostnameDAO hostnameD = new HostnameDAO();
        Hostname h = hostnameD.getHostnameFlagZero();
        String hostname = h.getHostname();

        ItemDAO itemD = new ItemDAO();
        Item item2 = itemD.getHardwareDetail(mibItemId);

        //send INFORMATION email
        LOGGER.info("######################### START EMAIL TO  ########################### ");
        EmailSender emailSender = new EmailSender();
        emailSender.htmlEmailTable(
                servletContext,
                "", //user name requestor
                to, //to
                //                        emailTo,
                "Hardware Registration - " + status + " [" + sub + "]", //subject
                "<br />"
                + "Please be informed that the hardware below failed the functional test inspection."
                + "<br /> "
                + "<br /> "
                + "Item ID: " + item2.getItemId()
                + "<br /> "
                + "Inspection Date: " + formattedString
                + "<br /> "
                + "<br /> "
                + "Please click <a href=\"http://" + hostname + "/HEATS/hw/item/add2/" + mibItemId + " \">HERE</a> for more detail."
                + "<br /> "
                + "<br />Thank you." //msg
        );

    }

    public void insertSPTSData(String mibItemId, String username) throws IOException {

        ItemDAO itemdao = new ItemDAO();
        Item item = itemdao.getHardwareDetail(mibItemId);

        String itemId = item.getItemId();
        String itemName = item.getItemName();
        String onHandQty = item.getOnHandQty();
        String productionQty = item.getProductionQty();
        String repairQty = item.getRepairQty();
        String otherQty = item.getOtherQty();
        String quarantineQty = item.getQuarantineQty();
        String externalCleanQty = item.getExternalCleanQty();
        String externalRecleanQty = item.getExternalRecleanQty();
        String internalCleanQty = item.getInternalCleanQty();
        String internalRecleanQty = item.getInternalRecleanQty();
        String storageFactoryQty = item.getStorageFactoryQty();
        String productionStagingQty = item.getProductionStagingQty();
        String otherOnsemiQty = item.getOtherOnsemiQty();
        String vendorQty = item.getVendorQty();
        String minQty = item.getMinQty();
        String maxQty = item.getMaxQty();
        String unitCost = item.getUnitCost();
        String rack = item.getRack();
        String shelf = item.getShelf();
        String model = item.getModel();
        String manufacturer = item.getManufacturer();
        String equipmentType = item.getEquipmentType();
        String equipmentModel = item.getEquipmentModel();
        String equipmentManufacturer = item.getEquipmentManufacturer();
        String stressType = item.getStressType();
        String isConsumable = item.getIsConsumable();
        String itemTypeRead = item.getItemType();
        String subType = item.getSubType();
        String assemblyId = item.getAssemblyId();
        String remarks = item.getRemarks();
        String expirationDate = item.getExpirationDate();
        String downtimeValue = "";
        String downtimeUnit = "";
        String implementationCost = "";
        String manpowerValue = "";
        String manpowerUnit = "";

        JSONObject params2 = new JSONObject();
        String date1 = expirationDate.substring(0, 10);
        String time = expirationDate.substring(11, 19);
        String completeDateTime = date1 + "T" + time;
        LOGGER.info("completeDateTime : " + completeDateTime);

        JSONObject addItem = new JSONObject();
        addItem.put("itemID", itemId);
        addItem.put("itemName", itemName);
        addItem.put("onHandQty", onHandQty);
        addItem.put("prodQty", productionQty);
        addItem.put("repairQty", repairQty);
        addItem.put("otherQty", otherQty);
        addItem.put("quarantineQty", quarantineQty);
        addItem.put("externalCleaningQty", externalCleanQty);
        addItem.put("externalRecleaningQty", externalRecleanQty);
        addItem.put("internalCleaningQty", internalCleanQty);
        addItem.put("internalRecleaningQty", internalRecleanQty);
        addItem.put("storageFactoryQty", storageFactoryQty);
        addItem.put("prodStagingQty", productionStagingQty);
        addItem.put("otherONQty", otherOnsemiQty);
        addItem.put("vendorQty", vendorQty);
        addItem.put("minQty", minQty);
        addItem.put("maxQty", maxQty);
        addItem.put("unit", "pcs");
        addItem.put("unitCost", unitCost);
        addItem.put("rack", rack);
        addItem.put("shelf", shelf);
        addItem.put("model", model);
        addItem.put("manufacturer", manufacturer);
        addItem.put("equipmentType", equipmentType);
        addItem.put("equipmentModel", equipmentModel);
        addItem.put("equipmentManufacturer", equipmentManufacturer);
        addItem.put("stressType", stressType);
        addItem.put("isCritical", "0");
        if ("true".equals(isConsumable)) {
            addItem.put("isConsumeable", "1");
        } else {
            addItem.put("isConsumeable", "0");
        }
        addItem.put("itemType", itemTypeRead);
        addItem.put("subType", subType);
        addItem.put("assemblyID", assemblyId);
        addItem.put("remarks", remarks);
        addItem.put("expirationDate", completeDateTime);
//        addItem.put("downtimeValue", downtimeValue);
//        addItem.put("downtimeUnit", downtimeUnit);
//        addItem.put("implementationCost", implementationCost);
//        addItem.put("manpowerValue", manpowerValue);
//        addItem.put("manpowerUnit", manpowerUnit);
        addItem.put("complexityScore", "0");

        SPTSResponse sr = SPTSWebService.insertItem(addItem);

        if (sr.getStatus()) {

            //update log
            ItemLog log = new ItemLog();
//            log = new ItemLog();
            log.setItemId(mibItemId);
            log.setDetail("Successfully Added into SPTS");
            log.setCreatedBy(username);
            ItemLogDAO logD = new ItemLogDAO();
            QueryResult logQ2 = logD.insertItemLog(log);

            //update SPTS PKID into MIB DB
            LOGGER.info("sr.getResponseId().toString(): " + sr.getResponseId().toString());
            item = new Item();
            item.setSptsPkid(sr.getResponseId().toString());
            item.setId(mibItemId);

            ItemDAO itemD = new ItemDAO();
            QueryResult i2 = itemD.updateItemSPTSPKID(item);
        } else {
            LinkedHashMap<String, String> item2;
            ObjectMapper mapper = new ObjectMapper();
            item2 = mapper.readValue(addItem.toString(), new TypeReference<LinkedHashMap<String, String>>() {
            });
            String errorMessage;
            if (sr.getErrorDetail().equals("")) {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorMessage();
            } else {
                errorMessage = sr.getErrorCode() + " - " + sr.getErrorDetail();
            }
//            model.addAttribute("error", errorMessage);
//            model.addAttribute("item2", item2);
        }
    }

    public void checkInsertFunctionalTest(String itemId, String username) {
        ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
        ItemFunctionalTest item = itemdao.getItemActivityByItemId(itemId);
        if (item != null) {

        } else {
            itemdao = new ItemFunctionalTestDAO();
            ItemFunctionalTest itembaru = new ItemFunctionalTest();
            itembaru.setMibItemId(itemId);
            itembaru.setBibQty("0");
            itembaru.setBibStatus("");
            itembaru.setBibUpload("");
            itembaru.setManStatus("");
            itembaru.setLeakQty("0");
            itembaru.setLeakStatus("");
            itembaru.setLeakUpload("");
            itembaru.setPsQty("0");
            itembaru.setPsStatus("");
            itembaru.setPsUpload("");
            itembaru.setWinQty("0");
            itembaru.setWinStatus("");
            itembaru.setWinUpload("");
            itembaru.setRemark("");
            itembaru.setFinalStatus("");
            itembaru.setCreatedBy(username);
            itembaru.setFlag("0");
            itemdao.insertItemFunctionalTest(itembaru);
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

    @RequestMapping(value = "/item/ft/{type}/{itemid}", method = RequestMethod.GET)
    public void downloadAttachmentTest(HttpServletRequest request,
            @PathVariable("type") String type,
            @PathVariable("itemid") String itemid,
            HttpServletResponse response) throws IOException {

        ItemFunctionalTestDAO itemdao = new ItemFunctionalTestDAO();
        ItemFunctionalTest itemf = itemdao.getItemActivityByItemId(itemid);

        String attachment = "";
        switch (type) {
            case "bibtest":
                attachment = itemf.getBibUpload();
                break;
            case "leaktest":
                attachment = itemf.getLeakUpload();
                break;
            case "pstest":
                attachment = itemf.getPsUpload();
                break;
            case "wintest":
                attachment = itemf.getWinUpload();
                break;
            default:
                attachment = "";
                break;
        }

        String fullPath = attachment;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        String mimeType = servletContext.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        response.setHeader(headerKey, headerValue);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

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

        model.addAttribute("userItemActAdd", userSession.getItemActivityAdd());

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
            //            @RequestParam(required = false) String manComp,
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

        String status = "";
        String user = userSession.getLoginId();
        String flag = "1";
        String configId = "0";

        if ("on".equals(manualTestCheck)) {
            ManualTestDAO test = new ManualTestDAO();
            int saizQty = Integer.parseInt(qtyField);
            int saizDut = Integer.parseInt(dutField);
            String manComp = String.valueOf(nameRows.size());

            if (nameRows != null) {
                itemA.setManualTest("Yes");
                QueryResult q0 = test.insertManualTest(mibItemId, qtyField, dutField, manComp, user, flag);
                if (!"0".equals(q0.getGeneratedKey())) {
                    configId = q0.getGeneratedKey();
                    for (int c1 = 1; c1 <= saizQty; c1++) {
                        String qtyId = "0";
                        test = new ManualTestDAO();
                        QueryResult q1 = test.insertManual01(mibItemId, String.valueOf(c1), user, flag);
                        if (!"0".equals(q1.getGeneratedKey())) {
                            qtyId = q1.getGeneratedKey();
                        } else {

                        }
                        for (int c2 = 1; c2 <= saizDut; c2++) {
                            String dutId = "0";
                            test = new ManualTestDAO();
                            QueryResult q2 = test.insertManual02(mibItemId, qtyId, String.valueOf(c2), user, flag);
                            if (!"0".equals(q2.getGeneratedKey())) {
                                dutId = q2.getGeneratedKey();
                            } else {

                            }
                            int saiz = nameRows.size();
                            for (int i = 0; i < saiz; i++) {
                                test = new ManualTestDAO();
                                QueryResult q3 = test.insertManual03(mibItemId, qtyId, dutId, type.get(i), nameRows.get(i), num1Rows.get(i), num3Rows.get(i), num4Rows.get(i), num2Rows.get(i), status, user, flag);
                                if (!"0".equals(q3.getGeneratedKey())) {

                                }
                            }
                        }
                    }
                }
            } else {
                redirectAttrs.addFlashAttribute("error", "Failed to save Activity Configuration. Please create proper number of component.");
                return "redirect:/hw/item/addActivity/" + mibItemId;
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

            ItemDAO itemD2 = new ItemDAO();
            Item item2 = itemD2.getHardwareDetail(mibItemId);

            // CHECKING DI BAWAH BUAT SEBAB EVEN DA CREATE THE ACTIVITY, STATUS TAK UPDATE
            String newStatus = "RESET";
            if (item2.getStatus().equalsIgnoreCase("Pending Activity Selection")) {
                newStatus = "Pending Visual Inspection";
            } else {
                newStatus = item2.getStatus();
            }

            //update status on Item table
            Item item = new Item();
            item.setId(mibItemId);
//            item.setStatus("Pending Visual Inspection");
            item.setStatus(newStatus);
            item.setFlag("0");

            ItemDAO itemDA = new ItemDAO();
            QueryResult iQ = itemDA.updateItemStatusAndFlag(item);

            if ("on".equals(manualTestCheck)) {
                ManualTestDAO mt = new ManualTestDAO();
                mt.updateConfigId(itemQ.getGeneratedKey(), configId);
            }

            redirectAttrs.addFlashAttribute("success", "Activity Configuration Succesfully Added.");
            return "redirect:/hw/item/pending/";
        } else {

        }

        redirectAttrs.addFlashAttribute("error", "Failed to save Activity Configuration. Pls Contact System Admin");
        return "redirect:/hw/item/add2/" + mibItemId;
    }

    @RequestMapping(value = "/item/editActivity/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editActivity(
            Model model,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("id") String id) {

        String qty = "";
        String dut = "";
        String mibItemId = "";

        ItemActivityConfigDAO itemD = new ItemActivityConfigDAO();
        ItemActivityConfig item = itemD.getItemActivityConfigWithItemDetail(id);

        mibItemId = item.getMibItemId();

        ItemDAO itemdao = new ItemDAO();
        Item dataitem = itemdao.getHardwareDetail(mibItemId);
        String status = dataitem.getStatus();

        model.addAttribute("item", item);
        model.addAttribute("userItemActEdit", userSession.getItemActivityEdit());

        ManualTestDAO itemA = new ManualTestDAO();
        ManualTest itemA1 = itemA.getComponentConfig(id);

        // 2026 JAN - PLEASE ADD ON THE ALGORITHM HERE FOR ALL FOUND SCENARIO
        if (itemA1 == null) {
//            if (status.contains("Pending Visual Inspection")) {
//
//            } else {
//
//            }
//            redirectAttrs.addFlashAttribute("error", "Manual Test Configuration is missing. Please contact admin for further assistance.");
//            return "redirect:/hw/item/pending";
        } else {
//            if (status.contains("Pending Visual Inspection")) {
                qty = itemA1.getQty();
                dut = itemA1.getDut();
                ManualTestDAO itemB = new ManualTestDAO();
                List<ManualTest> itemB1 = itemB.getAllComponentConfig(mibItemId);
                model.addAttribute("listData", itemB1);
//            } else {
//                redirectAttrs.addFlashAttribute("error", "Functional Test already on-going.");
//                return "redirect:/hw/item/pending";
//            }
        }
        model.addAttribute("qty", qty);
        model.addAttribute("dut", dut);

//        return "admin/bib_config_edit";
        return "item/item_check_edit";
    }

    @RequestMapping(value = "/item/updateActivity", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateActivityConfig(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String viCheck,
            @RequestParam(required = false) String bibTestCheck,
            @RequestParam(required = false) String manualTestCheck,
            @RequestParam(required = false) String leakageTestCheck,
            @RequestParam(required = false) String psLeakageTestCheck,
            @RequestParam(required = false) String winchesterChamberLeakageTest,
            @RequestParam(required = false) String inputQuantity,
            @RequestParam(required = false) String inputDUT,
            @RequestParam(required = false, value = "component_name[]") List<String> nameRows,
            @RequestParam(required = false, value = "component_type[]") List<String> type,
            @RequestParam(required = false, value = "actual_value[]") List<String> num1Rows,
            @RequestParam(required = false, value = "percentage[]") List<String> num2Rows,
            @RequestParam(required = false, value = "lower[]") List<String> num3Rows,
            @RequestParam(required = false, value = "upper[]") List<String> num4Rows) {

        String vi = "No";
        String bb = "No";
        String mn = "No";
        String lk = "No";
        String ps = "No";
        String wn = "No";
        int saiz = 0;

        if ("on".equals(viCheck)) {
            vi = "Yes";
        }
        if ("on".equals(bibTestCheck)) {
            bb = "Yes";
        }
        if ("on".equals(manualTestCheck)) {
            mn = "Yes";
            // SINI FUNCTION NK DELETE / DEACTIVATE SEMUA CURRENT SETUP CONFIG
            ManualTestDAO man1 = new ManualTestDAO();
            man1.deleteConfigId(mibItemId);
            man1 = new ManualTestDAO();
            man1.deleteConfigLevel01(mibItemId);
            man1 = new ManualTestDAO();
            man1.deleteConfigLevel02(mibItemId);
            man1 = new ManualTestDAO();
            man1.deleteConfigLevel03(mibItemId);

            // SINI KITA NK CREATE BALIK SEMUA SETUP / CONFIG
            saiz = nameRows.size();
            String flag = "1";
            String configId = "0";
            String status = "";
            int saizQty = Integer.parseInt(inputQuantity);
            int saizDut = Integer.parseInt(inputDUT);
            String user = userSession.getLoginId();
            ItemActivityConfig itemA = new ItemActivityConfig();
            itemA.setManualTest("Yes");
            ManualTestDAO test = new ManualTestDAO();
            QueryResult q0 = test.insertManualTest(mibItemId, inputQuantity, inputDUT, String.valueOf(saiz), user, flag);
            if (!"0".equals(q0.getGeneratedKey())) {
                configId = q0.getGeneratedKey();
                for (int c1 = 1; c1 <= saizQty; c1++) {
                    String qtyId = "0";
                    test = new ManualTestDAO();
                    QueryResult q1 = test.insertManual01(mibItemId, String.valueOf(c1), user, flag);
                    if (!"0".equals(q1.getGeneratedKey())) {
                        qtyId = q1.getGeneratedKey();
                    } else {

                    }
                    for (int c2 = 1; c2 <= saizDut; c2++) {
                        String dutId = "0";
                        test = new ManualTestDAO();
                        QueryResult q2 = test.insertManual02(mibItemId, qtyId, String.valueOf(c2), user, flag);
                        if (!"0".equals(q2.getGeneratedKey())) {
                            dutId = q2.getGeneratedKey();
                        } else {

                        }
                        for (int i = 0; i < saiz; i++) {
                            test = new ManualTestDAO();
                            QueryResult q3 = test.insertManual03(mibItemId, qtyId, dutId, type.get(i), nameRows.get(i), num1Rows.get(i), num3Rows.get(i), num4Rows.get(i), num2Rows.get(i), status, user, flag);
                            if (!"0".equals(q3.getGeneratedKey())) {

                            }
                        }
                    }
                }
            }

            ManualTestDAO mt = new ManualTestDAO();
            mt.updateConfigId(id, configId);
        }

        if ("on".equals(leakageTestCheck)) {
            lk = "Yes";
        }
        if ("on".equals(psLeakageTestCheck)) {
            ps = "Yes";
        }
        if ("on".equals(winchesterChamberLeakageTest)) {
            wn = "Yes";
        }

        ItemActivityConfig xtvt = new ItemActivityConfig();
        ItemActivityConfigDAO itemdao = new ItemActivityConfigDAO();
        xtvt.setVi(vi);
        xtvt.setBibTest(bb);
        xtvt.setManualTest(mn);
        xtvt.setLeakageTest(lk);
        xtvt.setPsLeakageTest(ps);
        xtvt.setWinchesterChamberLeakageTest(wn);
        xtvt.setStatus("Update Config");
        xtvt.setFlag("0");
        xtvt.setId(id);
        QueryResult q = itemdao.updateItemActivityConfig(xtvt);

//        return "redirect:/hw/item/add2editActivity/" + mibItemId;
        return "redirect:/hw/item/editActivity/" + id;
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
        model.addAttribute("requestList", requestList);
        return "item/himsList";
    }

    @RequestMapping(value = "/item/retrieveSF/{invId}/{pkid}", method = {RequestMethod.GET, RequestMethod.POST})
    public String retrieveSF(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @PathVariable("invId") String invId,
            @PathVariable("pkid") String pkid
    ) throws ClassNotFoundException, SQLException {

        String himsRetrieve = HimsRetrieve.himsRetrieve(servletContext, userSession, invId);

        if (himsRetrieve.contains("Successfully")) {
            LOGGER.info("+++++++Retrieve Success+++++++");
            redirectAttrs.addFlashAttribute("success", "Item successfully recall from Storage Factory");
        } else {
            LOGGER.info("+++++++Retrieve Failed+++++++");
            redirectAttrs.addFlashAttribute("error", "Failed to recall from Storage Factory. Pls contact system admin for more detail");
        }

        return "redirect:/hw/item/ListRetrieveSF";
    }

    @RequestMapping(value = "/item/ListRetrieveSF", method = RequestMethod.GET)
    public String ListRetrieveSF(
            Model model,
            @ModelAttribute UserSession userSession
    ) throws ClassNotFoundException, SQLException {

        ItemRecallDAO itemRecallD = new ItemRecallDAO();
        List<ItemRecall> itemRecall = itemRecallD.getItemRecallListFlagZero();

        for (int i = 0; i < itemRecall.size(); i++) {
            HimsRequestDAO requestDAO = new HimsRequestDAO();
            WhRetrieval hims = requestDAO.getWhRetrievalForHeatsRecall(itemRecall.get(i).getHimsRetrieveId());

            requestDAO = new HimsRequestDAO();
            int count = requestDAO.getCountRetrieveId(itemRecall.get(i).getHimsRetrieveId());

            if (count > 0) {
                ItemRecall item = new ItemRecall();
                item.setStatus(hims.getStatus());
                if ("Closed".equals(hims.getStatus())) {
                    item.setFlag("1");
                } else {
                    item.setFlag("0");
                }
                item.setId(itemRecall.get(i).getId());
                itemRecallD = new ItemRecallDAO();
                QueryResult q = itemRecallD.updateItemRecallStatusAndFlag(item);
            }
        }

        itemRecallD = new ItemRecallDAO();
        List<ItemRecall> itemRecallFlagZero = itemRecallD.getItemRecallListFlagZero();

        model.addAttribute("itemRecallFlagZero", itemRecallFlagZero);
        return "item/item_retrieve_sf";
    }

    @RequestMapping(value = "/item/transaction/{pkid}", method = RequestMethod.GET)
    public String addTransaction(
            Model model,
            @ModelAttribute UserSession userSession,
            @PathVariable("pkid") String pkid
    ) {

        ItemDAO itemD = new ItemDAO();
        Item item = itemD.getHardwareDetailByPkid(pkid);

        ItemAluConfigDAO itemA = new ItemAluConfigDAO();
        int countAlu = itemA.getCountItemType(item.getItemType());

        //add movement history
        ItemTransactionDAO hwD = new ItemTransactionDAO();
        List<ItemTransaction> itemList = hwD.getItemTransactionListByItemPkid(pkid);
        model.addAttribute("itemList", itemList);

        model.addAttribute("item", item);
        model.addAttribute("countAlu", countAlu);
        return "item/item_transaction";
    }

    @RequestMapping(value = "/item/transaction/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String addTransactionSave(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String mibItemId,
            @RequestParam(required = false) String sptsPkid,
            @RequestParam(required = false) String countAlu,
            @RequestParam(required = false) String transaction,
            @RequestParam(required = false) String transactionToFrom,
            @RequestParam(required = false) String qty,
            @RequestParam(required = false) String alu,
            @RequestParam(required = false) String remarks,
            @RequestParam(required = false) String transactionDate,
            @RequestParam(required = false) String winchesterChamberLeakageTest
    ) throws IOException {

        ItemDAO itemD = new ItemDAO();
        Item item = itemD.getHardwareDetail(mibItemId);

//        TransType:- 1 = In, 2 = Out for Production, 3 = Out for Repairing, 4 = Out for Other Reason, 5 = Out for Adjustment, 6 = Production Return, 7 = Repairing Return, 8 = Others Return
//	, 9 = Out For Quarantine, 10 = Out For External Cleaning, 11 = Out For External Re-Cleaning, 12 = Out For Internal Cleaning, 13 = Out For Internal Re-Cleaning
//	, 14 = Return From Quarantine, 15 = Return From External Cleaning, 16 = Return From External Re-Cleaning, 17 = Return From Internal Cleaning, 18 = Return From Internal Re-Cleaning
//	, 19 = Out for Storage Factory, 20 = Return From Storage Factory, 21 = Shipped to Other ON Semi Site, 22 = Return From Other ON Semi Site, 23 = Shipped to Vendor, 24 = Return From Vendor
//	, 25 = Out_For_Production_Staging, 26 = Return_From_Production_Staging, 27 = Out_For_Production_From_Staging, 28 = Return_From_Production_To_Staging
        String transtype = "";

        if ("New".equals(transaction)) {
            transtype = "1";
        } else if ("Out".equals(transaction) && "Production".equals(transactionToFrom)) {
            transtype = "2";
        } else if ("Out".equals(transaction) && "Repair".equals(transactionToFrom)) {
            transtype = "3";
        } else if ("Out".equals(transaction) && "Other".equals(transactionToFrom)) {
            transtype = "4";
        } else if ("Out".equals(transaction) && "Adjustment".equals(transactionToFrom)) {
            transtype = "5";
        } else if ("Return".equals(transaction) && "Production".equals(transactionToFrom)) {
            transtype = "6";
        } else if ("Return".equals(transaction) && "Repair".equals(transactionToFrom)) {
            transtype = "7";
        } else if ("Return".equals(transaction) && "Other".equals(transactionToFrom)) {
            transtype = "8";
        } else if ("Out".equals(transaction) && "Quarantine".equals(transactionToFrom)) {
            transtype = "9";
        } else if ("Out".equals(transaction) && "External Clean".equals(transactionToFrom)) {
            transtype = "10";
        } else if ("Out".equals(transaction) && "External Re-clean".equals(transactionToFrom)) {
            transtype = "11";
        } else if ("Out".equals(transaction) && "Internal Clean".equals(transactionToFrom)) {
            transtype = "12";
        } else if ("Out".equals(transaction) && "Internal Re-clean".equals(transactionToFrom)) {
            transtype = "13";
        } else if ("Return".equals(transaction) && "Quarantine".equals(transactionToFrom)) {
            transtype = "14";
        } else if ("Return".equals(transaction) && "External Clean".equals(transactionToFrom)) {
            transtype = "15";
        } else if ("Return".equals(transaction) && "External Re-clean".equals(transactionToFrom)) {
            transtype = "16";
        } else if ("Return".equals(transaction) && "Internal Clean".equals(transactionToFrom)) {
            transtype = "17";
        } else if ("Return".equals(transaction) && "Internal Re-clean".equals(transactionToFrom)) {
            transtype = "18";
        } else if ("Out".equals(transaction) && "Storage Factory".equals(transactionToFrom)) {
            transtype = "19";
        } else if ("Return".equals(transaction) && "Storage Factory".equals(transactionToFrom)) {
            transtype = "20";
        } else if ("Out".equals(transaction) && "Other Onsemi".equals(transactionToFrom)) {
            transtype = "21";
        } else if ("Return".equals(transaction) && "Other Onsemi".equals(transactionToFrom)) {
            transtype = "22";
        } else if ("Out".equals(transaction) && "Vendor".equals(transactionToFrom)) {
            transtype = "23";
        } else if ("Return".equals(transaction) && "Vendor".equals(transactionToFrom)) {
            transtype = "24";
        } else if ("Out".equals(transaction) && "Production Staging".equals(transactionToFrom)) {
            transtype = "25";
        } else if ("Return".equals(transaction) && "Production Staging".equals(transactionToFrom)) {
            transtype = "26";
        } else if ("Out".equals(transaction) && "Out Production From Staging".equals(transactionToFrom)) {
            transtype = "27";
        } else if ("Return".equals(transaction) && "Retun Production to Staging".equals(transactionToFrom)) {
            transtype = "28";
        }

//        LOGGER.info("transactionDate : " + transactionDate);
//        LOGGER.info("sptsPkid : " + sptsPkid);
//        LOGGER.info("transtype : " + transtype);
//        LOGGER.info("alu : " + alu);
//        LOGGER.info("countAlu: " + countAlu);
        //update to SPTS
        JSONObject params2 = new JSONObject();
        String date1 = transactionDate.substring(0, 10);
        String time = transactionDate.substring(11, 19);
        String completeDateTime = date1 + "T" + time;

        params2.put("dateTime", completeDateTime);
        params2.put("itemsPKID", sptsPkid);
        params2.put("transType", transtype);
        params2.put("transQty", qty);
        params2.put("remarks", remarks);
        if ("1".equals(countAlu)) {
            if ("2".equals(transtype) || "6".equals(transtype) || "27".equals(transtype) || "28".equals(transtype)) {
                params2.put("lifetimeUsageHrs", alu);
            }

        }
        SPTSResponse TransPkid = SPTSWebService.insertTransaction(params2);

        if (TransPkid.getResponseId() > 0) {
            LOGGER.info("transaction done item ");

            //update HEATS table
            //check if any info from SPTS need to update to MIB DB
            //update SPTS data per item type into MIB DB
            JSONObject params = new JSONObject();
            params.put("pkID", sptsPkid);
            JSONArray getItemByParam = SPTSWebService.getItemByParam(params);

            int count = 0;
            int countAdd = 0;
            int countTrans = 0;
            int countTransAdd = 0;
            int countSf = 0;
            int countSfAdd = 0;

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
                        String date2 = getItemByParam.getJSONObject(i).getString("ExpirationDate").substring(0, 10);
                        hw.setExpirationDate(date2);
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

            //add transaction to DB
            JSONObject params3 = new JSONObject();
            params3.put("itemsPKID", sptsPkid);
            JSONArray getTransactionByParam = SPTSWebService.getTransactionByParam(params3);

            for (int i = 0; i < getTransactionByParam.length(); i++) {

                ItemTransactionDAO itemTransD = new ItemTransactionDAO();
                int countPkid = itemTransD.getCountPkidAndItemPkid(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("PKID")), Integer.toString(getTransactionByParam.getJSONObject(i).getInt("ItemsPKID")));
                if (countPkid == 0) {
                    ItemTransaction itemTran = new ItemTransaction();
                    itemTran.setSptsPkid(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("PKID")));
                    itemTran.setItemPkid(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("ItemsPKID")));
                    itemTran.setSiteName(getTransactionByParam.getJSONObject(i).getString("SiteName"));
                    String dateTime = getTransactionByParam.getJSONObject(i).getString("DateTime").substring(0, 10) + " " + getTransactionByParam.getJSONObject(i).getString("DateTime").substring(11, 19);
                    itemTran.setDateTime(dateTime);
                    itemTran.setTransType(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransType")));
                    itemTran.setTransTypeName(getTransactionByParam.getJSONObject(i).getString("TransTypeName"));
                    itemTran.setTransQty(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransQty")));
                    if (getTransactionByParam.getJSONObject(i).has("TransInQty")) {
                        itemTran.setTransInQty(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransInQty")));
                    }
                    if (getTransactionByParam.getJSONObject(i).has("TransOutQty")) {
                        itemTran.setTransOutQty(Integer.toString(getTransactionByParam.getJSONObject(i).getInt("TransOutQty")));
                    }
                    if (getTransactionByParam.getJSONObject(i).has("LifetimeUsageHrs")) {
                        itemTran.setAlu(Double.toString(getTransactionByParam.getJSONObject(i).getDouble("LifetimeUsageHrs")));
                    }
                    if (getTransactionByParam.getJSONObject(i).has("Remarks")) {
                        itemTran.setRemarks(getTransactionByParam.getJSONObject(i).getString("Remarks"));
                    }

                    itemTransD = new ItemTransactionDAO();
                    QueryResult qI = itemTransD.insertItemTransaction(itemTran);
                    countTransAdd += qI.getResult();
                }
                countTrans += 1;
            }
            LOGGER.info("Total data Trans: " + countTrans);
            LOGGER.info("Total insert Trans: " + countTransAdd);

            redirectAttrs.addFlashAttribute("success", "Transaction is added");
//            return "redirect:/hw/item";
//            return "redirect:/hw";
            return "redirect:/hw/item/transaction/" + sptsPkid;

        } else {
            LOGGER.info("TransPkid.getResponseId(): " + TransPkid.getResponseId());
            redirectAttrs.addFlashAttribute("error", "Failed to save transaction. Pls contact system admin for more detail.");
            return "redirect:/hw/item/transaction/" + sptsPkid;
        }

    }

    @RequestMapping(value = "/item/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(
            Model model,
            Locale locale,
            RedirectAttributes redirectAttrs,
            @ModelAttribute UserSession userSession,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String assemblyId,
            //            @RequestParam(required = false) String hardwareId,
            @RequestParam(required = false) String stressType,
            @RequestParam(required = false) String status,
            //            @RequestParam(required = false) String ateItemUsage,
            //            @RequestParam(required = false) String eqptItemUsage,
            @RequestParam(required = false) String model2,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String equipmentType,
            @RequestParam(required = false) String equipmentModel,
            @RequestParam(required = false) String equipmentManufacturer) throws IOException {

        String query = "";
        int count = 0;

        JSONArray getItemTypeAll = SPTSWebService.getItemTypeAll();

        for (int i = 0; i < getItemTypeAll.length(); i++) {

            ParameterDetailsDAO pD = new ParameterDetailsDAO();
            String masterCode = "002";
            String detailcode = pD.getNextDetailCode(masterCode);
            pD = new ParameterDetailsDAO();
            int countItemType = pD.getCountMasterCodeAndName(masterCode, getItemTypeAll.getJSONObject(i).getString("ItemType"));

            if (countItemType == 0) {
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
        List<ParameterDetails> paramItemType = pD.getGroupParameterDetailList("", "002");
        model.addAttribute("paramItemType", paramItemType);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsage = pD.getGroupParameterDetailList("", "001");
        model.addAttribute("paramItemUsage", paramItemUsage);

        pD = new ParameterDetailsDAO();
        List<ParameterDetails> paramItemUsageEqpt = pD.getGroupParameterDetailList("", "018");
        model.addAttribute("paramItemUsageEqpt", paramItemUsageEqpt);

        ItemDAO itemD = new ItemDAO();
        List<Item> listAssemblyId = itemD.getItemAssemblyId("");
        model.addAttribute("listAssemblyId", listAssemblyId);

        itemD = new ItemDAO();
        List<Item> listModel = itemD.getItemModel("");
        model.addAttribute("listModel", listModel);

        itemD = new ItemDAO();
        List<Item> listManufacturer = itemD.getItemManufacturer("");
        model.addAttribute("listManufacturer", listManufacturer);

        itemD = new ItemDAO();
        List<Item> listEqptModel = itemD.getItemEqptModel("");
        model.addAttribute("listEqptModel", listEqptModel);

        itemD = new ItemDAO();
        List<Item> listEqptType = itemD.getItemEqptType("");
        model.addAttribute("listEqptType", listEqptType);

        itemD = new ItemDAO();
        List<Item> listEqptManufacturer = itemD.getItemEqptManufacturer("");
        model.addAttribute("listEqptManufacturer", listEqptManufacturer);

        itemD = new ItemDAO();
        List<Item> listStressType = itemD.getItemStressType("");
        model.addAttribute("listStressType", listStressType);

        itemD = new ItemDAO();
        List<Item> listStatus = itemD.getItemStatus();
        model.addAttribute("listStatus", listStatus);

        itemD = new ItemDAO();
        List<Item> listSubType = itemD.getItemSubType();
        model.addAttribute("listSubType", listSubType);

        if (itemType != null) {
            if (!itemType.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE item_type = '" + itemType + "\' ";
                } else if (count > 1) {
                    query = query + " AND item_type = ''" + itemType + "\' ";
                }
            }
        }

        if (subType != null) {
            if (!subType.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE sub_type = '" + subType + "\' ";
                } else if (count > 1) {
                    query = query + " AND rmslot_event = '" + subType + "\' ";
                }
            }
        }

        if (itemId != null) {
            if (!itemId.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE item_id LIKE \'%" + itemId + "%' ";
                } else if (count > 1) {
                    query = query + " AND item_id LIKE \'%" + itemId + "%' ";
                }
            }
        }

        if (itemName != null) {
            if (!itemName.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE item_name LIKE \'%" + itemName + "%' ";
                } else if (count > 1) {
                    query = query + " AND item_name LIKE \'%" + itemName + "%' ";
                }
            }
        }

        if (assemblyId != null) {
            if (!assemblyId.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE assembly_id = '" + assemblyId + "\' ";
                } else if (count > 1) {
                    query = query + " AND assembly_id = '" + assemblyId + "\' ";
                }
            }
        }

        if (stressType != null) {
            if (!stressType.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE stress_type = '" + stressType + "\' ";
                } else if (count > 1) {
                    query = query + " AND stress_type = '" + stressType + "\' ";
                }
            }
        }

        if (model2 != null) {
            if (!model2.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE model = '" + model2 + "\' ";
                } else if (count > 1) {
                    query = query + " AND model = '" + model2 + "\' ";
                }
            }
        }

        if (manufacturer != null) {
            if (!manufacturer.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE manufacturer = '" + manufacturer + "\' ";
                } else if (count > 1) {
                    query = query + " AND manufacturer = '" + manufacturer + "\' ";
                }
            }
        }

        if (equipmentType != null) {
            if (!equipmentType.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equipment_type = '" + equipmentType + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_type = '" + equipmentType + "\' ";
                }
            }
        }

        if (equipmentModel != null) {
            if (!equipmentModel.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equipment_model = '" + equipmentModel + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_model = '" + equipmentModel + "\' ";
                }
            }
        }

        if (equipmentManufacturer != null) {
            if (!equipmentManufacturer.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE equipment_manufacturer = '" + equipmentManufacturer + "\' ";
                } else if (count > 1) {
                    query = query + " AND equipment_manufacturer = '" + equipmentManufacturer + "\' ";
                }
            }
        }

        if (status != null) {
            if (!status.equals("")) {
                count++;
                if (count == 1) {
                    query = query + " WHERE status = '" + status + "\' ";
                } else if (count > 1) {
                    query = query + " AND status = '" + status + "\' ";
                }
            }
        }

        String finalQuery = "";

        if (count != 0) {
//            finalQuery = "SELECT * FROM item " + query + " ORDER BY item_type, item_id";
            finalQuery = "SELECT it.*, vm.id AS vmId FROM item it LEFT JOIN item_visual_inspection vm ON it.id = vm.mib_item_id " + query + " ORDER BY it.item_type, it.item_id";

        } else {
            finalQuery = "SELECT * FROM item WHERE flag = '1000'";
        }

        System.out.println("finalQuery: " + finalQuery);

        itemD = new ItemDAO();
        List<Item> resultQuery = itemD.getitemQuery(finalQuery);
        model.addAttribute("resultQuery", resultQuery);

        return "item/query";
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
