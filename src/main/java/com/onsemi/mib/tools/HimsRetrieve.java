package com.onsemi.mib.tools;

import com.onsemi.mib.dao.HimsRequestDAO;
import com.onsemi.mib.dao.ItemRecallCsvFileDAO;
import com.onsemi.mib.dao.ItemRecallDAO;
import com.onsemi.mib.model.EmailConfig;
import com.onsemi.mib.model.ItemRecall;
import com.onsemi.mib.model.ItemRecallCsvFile;
import com.onsemi.mib.model.UserSession;
import com.onsemi.mib.model.WhInventory;
import com.onsemi.mib.model.WhRequest;
import com.onsemi.mib.model.WhRetrieval;
import com.onsemi.mib.model.WhStatusLog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class HimsRetrieve {

    //Delimiters which has to be in the CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String HEADER = "id,hardware_type,hardware_id,retrieval_reason,pcb a,pcb a qty,pcb b,pcb b qty, pcb c,pcb c qty, pcb ctr,pcb ctr qty,"
            + "quantity,box_no,gts_no,rack,shelf,requested_by,requested_email,requested_date,remarks,status";

//    @Autowired
//    ServletContext servletContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(HimsRetrieve.class);

    public static String himsRetrieve(ServletContext servletContext, @ModelAttribute UserSession userSession, String himInventoryId) throws ClassNotFoundException, SQLException {

        LOGGER.info("himInventoryId: " + himInventoryId);

        HimsRequestDAO inventoryD = new HimsRequestDAO();
        WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);

        WhRequest whRequest = new WhRequest();
        whRequest.setRequestType("Retrieve");
        whRequest.setEquipmentType(inventory.getEquipmentType());
//        whRequest.setEquipmentType(equipmentType);

        // retrieve
        whRequest.setRetrievalReason("Production");
        whRequest.setStatus("New Request");
//        if ("Motherboard".equals(equipmentType)) {
        if ("Motherboard".equals(inventory.getEquipmentType())) {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setProgramCardQty("0");
            whRequest.setLoadCardQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            //check either item can be request or not
            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountFlag0ForRetrieve(inventory.getEquipmentId());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            LOGGER.info("inventory.getRequestId(): " + inventory.getRequestId());
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }

        } else if ("Stencil".equals(inventory.getEquipmentType())) {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setProgramCardQty("0");
            whRequest.setLoadCardQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            //check either item can be request or not
            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountFlag0ForRetrieve(inventory.getEquipmentId());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }

        } else if ("PCB".equals(inventory.getEquipmentType())) {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setPcbA(inventory.getPcbA());
            whRequest.setPcbAQty(inventory.getPcbAQty());
            whRequest.setPcbB(inventory.getPcbB());
            whRequest.setPcbBQty(inventory.getPcbBQty());
            whRequest.setPcbC(inventory.getPcbC());
            whRequest.setPcbCQty(inventory.getPcbCQty());
            whRequest.setPcbCtr(inventory.getPcbCtr());
            whRequest.setPcbCtrQty(inventory.getPcbCtrQty());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setProgramCardQty("0");
            whRequest.setLoadCardQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            requestda = new HimsRequestDAO(); //check using box no
            int countitemflag0BoxNo = requestda.getCountRetrieveEquipmentIdAndBoxNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getBoxNo());
            if (countitemflag0BoxNo > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }
        } //load card
        else if ("Load Card".equals(inventory.getEquipmentType())) {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setLoadCard(inventory.getLoadCard());
            whRequest.setLoadCardQty(inventory.getLoadCardQty());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setProgramCardQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            requestda = new HimsRequestDAO(); //check using box no
            int countitemflag0BoxNo = requestda.getCountRetrieveEquipmentIdAndBoxNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getBoxNo());
            if (countitemflag0BoxNo > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }
        } else if ("Program Card".equals(inventory.getEquipmentType())) {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setProgramCard(inventory.getProgramCard());
            whRequest.setProgramCardQty(inventory.getProgramCardQty());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setLoadCardQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            requestda = new HimsRequestDAO(); //check using box no
            int countitemflag0BoxNo = requestda.getCountRetrieveEquipmentIdAndBoxNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getBoxNo());
            if (countitemflag0BoxNo > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRetrieval/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }
        } else if ("Load Card & Program Card".equals(inventory.getEquipmentType())) {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setLoadCard(inventory.getLoadCard());
            whRequest.setLoadCardQty(inventory.getLoadCardQty());
            whRequest.setProgramCard(inventory.getProgramCard());
            whRequest.setProgramCardQty(inventory.getProgramCardQty());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRequest/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            requestda = new HimsRequestDAO(); //check using box no
            int countitemflag0BoxNo = requestda.getCountRetrieveEquipmentIdAndBoxNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getBoxNo());
            if (countitemflag0BoxNo > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRequest/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }
        } else {
            whRequest.setInventoryId(himInventoryId);

//            HimsRequestDAO inventoryD = new HimsRequestDAO();
//            WhInventory inventory = inventoryD.getWhInventoryActive(himInventoryId);
            whRequest.setEquipmentId(inventory.getEquipmentId());
            whRequest.setMpNo(inventory.getMpNo());
            whRequest.setMpExpiryDate(inventory.getMpExpiryDate());
            whRequest.setRack(inventory.getInventoryRack());
            whRequest.setShelf(inventory.getInventoryShelf());
            whRequest.setQuantity(inventory.getQuantity());
            whRequest.setPcbAQty("0");
            whRequest.setPcbBQty("0");
            whRequest.setPcbCQty("0");
            whRequest.setPcbCtrQty("0");
            whRequest.setProgramCardQty("0");
            whRequest.setLoadCardQty("0");
            whRequest.setBoxNo(inventory.getBoxNo());
            whRequest.setGtsNo(inventory.getGtsNo());

            //check either item can be request or not
            HimsRequestDAO requestda = new HimsRequestDAO();
            int countitemflag0 = requestda.getCountRetrieveEquipmentIdAndMpNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getMpNo());
            if (countitemflag0 > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRequest/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            requestda = new HimsRequestDAO(); //check using box no
            int countitemflag0BoxNo = requestda.getCountRetrieveEquipmentIdAndBoxNoAndStatusCancelled(inventory.getEquipmentId(), inventory.getBoxNo());
            if (countitemflag0BoxNo > 0) {
//                redirectAttrs.addFlashAttribute("error", "This Hardware ID already requested. Please select another equipment ID.");
//                return "redirect:/wh/whRequest/add?requestType=" + requestType + "&equipmentType=" + equipmentType + "&retrievalReason=" + retrievalReason;
                return "This Hardware ID already requested. Please select another equipment ID.";
            }

            //update status at master table request for ship
            HimsRequestDAO reqD = new HimsRequestDAO();
            int countReq = reqD.getCountRequestId(inventory.getRequestId());
            if (countReq == 1) {
                WhRequest reqUpdate = new WhRequest();
                reqUpdate.setModifiedBy(userSession.getFullname());
                reqUpdate.setStatus("Requested for Retrieval");
                reqUpdate.setId(inventory.getRequestId());
                reqD = new HimsRequestDAO();
                QueryResult ru = reqD.updateWhRequestStatus(reqUpdate);
                if (ru.getResult() == 1) {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table done");
                } else {
                    LOGGER.info("[WhRequest-retrieval request] - update status at request table failed");
                }
            } else {
                LOGGER.info("[WhRequest-retrieval request] - requestId not found");
            }
        }

        //end if else for requestType
        whRequest.setRequestedBy(userSession.getFullname());

        //modified equipmentType
        String equipmentType1 = "";
//        if (equipmentType.contains("ATE")) {
        if (inventory.getEquipmentType().contains("ATE")) {
            equipmentType1 = "ATE";
        } else if (inventory.getEquipmentType().contains("EQP")) {
            equipmentType1 = "EQP";
        } else if (inventory.getEquipmentType().contains("Card")) {
            equipmentType1 = "Bib Cards";
        } else if (inventory.getEquipmentType().contains("REL")) {
            equipmentType1 = "REL_Operations Supply Items";
        } else {
            equipmentType1 = inventory.getEquipmentType();
        }

        //save approver email
        HimsRequestDAO econfD = new HimsRequestDAO();
        int count = econfD.getCountTaskWildCardForEmailconfig(equipmentType1);
        if (count == 1) {
            econfD = new HimsRequestDAO();
            EmailConfig econ = econfD.getEmailConfigByTaskWildCard(equipmentType1);
            String email = econ.getEmail();
            whRequest.setRequestorEmail(email);//email supervisor base on equipment type 
        } else {
            whRequest.setRequestorEmail(userSession.getEmail()); //email requestor
        }

        //check if remark contain line sepator
//        String remarkNoNewLine = remarks;
//        if (remarks.contains(NEW_LINE)) {
//            remarkNoNewLine = remarks.replaceAll(NEW_LINE, " ");
//        }
        whRequest.setRemarks("");
//        whRequest.setRemarks(remarks);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        String RemarksLogFull = "[" + formattedDate + "] - " + "";
        whRequest.setRemarksLog(RemarksLogFull);
        whRequest.setCreatedBy(userSession.getId());
        whRequest.setFlag("0");
        whRequest.setSfpkid("0");
        whRequest.setSfpkidB("0");
        whRequest.setSfpkidC("0");
        whRequest.setSfpkidCtr("0");//new 11/11/16
        whRequest.setSfpkidLc("0");
        whRequest.setSfpkidPc("0");

        HimsRequestDAO whRequestDAO = new HimsRequestDAO();
        QueryResult queryResult = whRequestDAO.insertWhRequest(whRequest);
        if (queryResult.getGeneratedKey().equals("0")) {
//            return "whRetrieval/request";
            return "Failed to insert data into Request Table";

        } else {
//            redirectAttrs.addFlashAttribute("success", messageSource.getMessage("general.label.save.success", args, locale));

            //update statusLog
            WhStatusLog stat = new WhStatusLog();
            stat.setRequestId(queryResult.getGeneratedKey());
            stat.setModule("cdars_wh_request");
            stat.setStatus("Requested for Retrieval");
            stat.setCreatedBy(userSession.getFullname());
            stat.setFlag("0");
            HimsRequestDAO statD = new HimsRequestDAO();
            QueryResult queryResultStat = statD.insertWhStatusLog(stat);
            if (queryResultStat.getGeneratedKey().equals("0")) {
                LOGGER.info("[WhRequest] - insert status log failed");
            } else {
                LOGGER.info("[WhRequest] - insert status log done");
            }

            HimsRequestDAO whredao = new HimsRequestDAO();
            WhRequest whrequest = whredao.getWhRequest(queryResult.getGeneratedKey());

            WhRetrieval whRetrieval = new WhRetrieval();
            whRetrieval.setRequestId(queryResult.getGeneratedKey());
            whRetrieval.setHardwareType(whrequest.getEquipmentType());
            whRetrieval.setHardwareId(whrequest.getEquipmentId());
            whRetrieval.setRetrievalReason(whrequest.getRetrievalReason());
            whRetrieval.setPcbA(whrequest.getPcbA());
            whRetrieval.setPcbAQty(whrequest.getPcbAQty());
            whRetrieval.setPcbB(whrequest.getPcbB());
            whRetrieval.setPcbBQty(whrequest.getPcbBQty());
            whRetrieval.setPcbC(whrequest.getPcbC());
            whRetrieval.setPcbCQty(whrequest.getPcbCQty());
            whRetrieval.setPcbCtr(whrequest.getPcbCtr());
            whRetrieval.setPcbCtrQty(whrequest.getPcbCtrQty());
            whRetrieval.setLoadCard(whrequest.getLoadCard());
            whRetrieval.setLoadCardQty(whrequest.getLoadCardQty());
            whRetrieval.setProgramCard(whrequest.getProgramCard());
            whRetrieval.setProgramCardQty(whrequest.getProgramCardQty());
            whRetrieval.setHardwareQty(whrequest.getQuantity());
            whRetrieval.setRack(whrequest.getRack());
            whRetrieval.setShelf(whrequest.getShelf());
            whRetrieval.setMpNo(whrequest.getMpNo());
            whRetrieval.setMpExpiryDate(whrequest.getMpExpiryDate());
            whRetrieval.setRequestedBy(whrequest.getRequestedBy());
            whRetrieval.setRequestedDate(whrequest.getRequestedDate());
            whRetrieval.setRemarks(whrequest.getRemarks());
            whRetrieval.setStatus("Requested");
            whRetrieval.setFlag("0");
            whRetrieval.setBoxNo(whrequest.getBoxNo());
            HimsRequestDAO whRetrievalDAO = new HimsRequestDAO();
            QueryResult queryResultRetrieval = whRetrievalDAO.insertWhRetrieval(whRetrieval);
//            if (queryResultRetrieval.getResult() == 1) {
            if (!queryResultRetrieval.getGeneratedKey().equals(0)) {
                LOGGER.info("done save to retrieval table");

                //save into MIB Item_recall table
                ItemRecall itemRecall = new ItemRecall();
                itemRecall.setHimsRetrieveId(queryResultRetrieval.getGeneratedKey());
                itemRecall.setItemType(whrequest.getEquipmentType());
                if (null == whrequest.getEquipmentType()) {
                    itemRecall.setItemId(whrequest.getEquipmentId());
                } else {
                    switch (whrequest.getEquipmentType()) {
                        case "Load Card":
                            itemRecall.setItemId(whrequest.getLoadCard());
                            break;
                        case "Program Card":
                            itemRecall.setItemId(whrequest.getProgramCard());
                            break;
                        case "Load Card & Program Card":
                            String itemIdLcPc = whrequest.getLoadCard() + " / " + whrequest.getProgramCard();
                            itemRecall.setItemId(itemIdLcPc);
                            break;
                        default:
                            itemRecall.setItemId(whrequest.getEquipmentId());
                            break;
                    }
                }
                itemRecall.setBoxNo(whrequest.getBoxNo());
                itemRecall.setQty(whrequest.getQuantity());
                itemRecall.setStatus("Requested");
                itemRecall.setCreatedBy(userSession.getFullname());
                itemRecall.setFlag("0");
                ItemRecallDAO itemRecallD = new ItemRecallDAO();
                QueryResult qItemRecall = itemRecallD.insertItemRecall(itemRecall);
            } else {
                LOGGER.info("failed save to retrieval table");
            }

            String username = System.getProperty("user.name");
            if (!"fg79cj".equals(username)) {
                username = "imperial";
            }
            ItemRecallCsvFileDAO itemRecallCsvFileDAO = new ItemRecallCsvFileDAO();
            ItemRecallCsvFile itemRecallCsvFile = itemRecallCsvFileDAO.getItemRecallCsvFileForActiveLocation();
            String fileLocation = itemRecallCsvFile.getFile();
            String emailCsv = itemRecallCsvFile.getEmailCsv();
            String emailNotification = itemRecallCsvFile.getEmailNotification();

            File file = new File(fileLocation);
//            File file = new File("C:\\HIMS_CSV\\RL\\cdars_retrieve.csv");
//            File file = new File("\\\\mysed-rel-app03\\d$\\HIMS\\RL\\cdars_retrieve.csv");

            if (file.exists()) {
                //Create List for holding Employee objects
                LOGGER.info("tiada header");
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(fileLocation, true); //testing
//                    fileWriter = new FileWriter("C:\\HIMS_CSV\\RL\\cdars_retrieve.csv", true); //testing
//                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\HIMS\\RL\\cdars_retrieve.csv", true); //production
                    //New Line after the header
                    fileWriter.append(LINE_SEPARATOR);

                    fileWriter.append(queryResult.getGeneratedKey());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getEquipmentType());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getEquipmentId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRetrievalReason());
                    fileWriter.append(COMMA_DELIMITER);
                    if ("Load Card".equals(whrequest.getEquipmentType())) {
                        fileWriter.append(whrequest.getLoadCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getLoadCardQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbB());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbBQty());
                    } else if ("Program Card".equals(whrequest.getEquipmentType())) {
                        fileWriter.append(whrequest.getPcbA());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbAQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCardQty());
                    } else if ("Load Card & Program Card".equals(whrequest.getEquipmentType())) {
                        fileWriter.append(whrequest.getLoadCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getLoadCardQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCardQty());
                    } else {
                        fileWriter.append(whrequest.getPcbA());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbAQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbB());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbBQty());
                    }
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbC());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbCQty());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbCtr());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbCtrQty());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getQuantity());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getBoxNo());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getGtsNo());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRack());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getShelf());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRequestedBy());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRequestorEmail());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRequestedDate());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRemarks());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("New Request");
                    fileWriter.append(COMMA_DELIMITER);
                    System.out.println("append to CSV file Succeed!!!");
                } catch (Exception ee) {
                    ee.printStackTrace();
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException ie) {
                        System.out.println("Error occured while closing the fileWriter");
                        ie.printStackTrace();
                    }
                }
            } else {
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(fileLocation); //testing
//                    fileWriter = new FileWriter("C:\\HIMS_CSV\\RL\\cdars_retrieve.csv"); //testing
//                    fileWriter = new FileWriter("\\\\mysed-rel-app03\\d$\\HIMS\\RL\\cdars_retrieve.csv"); //production
                    LOGGER.info("no file yet");
                    //Adding the header
                    fileWriter.append(HEADER);

                    //New Line after the header
                    fileWriter.append(LINE_SEPARATOR);

                    fileWriter.append(queryResult.getGeneratedKey());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getEquipmentType());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getEquipmentId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRetrievalReason());
                    fileWriter.append(COMMA_DELIMITER);
                    if ("Load Card".equals(whrequest.getEquipmentType())) {
                        fileWriter.append(whrequest.getLoadCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getLoadCardQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbB());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbBQty());
                    } else if ("Program Card".equals(whrequest.getEquipmentType())) {
                        fileWriter.append(whrequest.getPcbA());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbAQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCardQty());
                    } else if ("Load Card & Program Card".equals(whrequest.getEquipmentType())) {
                        fileWriter.append(whrequest.getLoadCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getLoadCardQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCard());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getProgramCardQty());
                    } else {
                        fileWriter.append(whrequest.getPcbA());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbAQty());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbB());
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(whrequest.getPcbBQty());
                    }
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbC());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbCQty());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbCtr());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getPcbCtrQty());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getQuantity());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getBoxNo());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getGtsNo());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRack());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getShelf());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRequestedBy());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRequestorEmail());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRequestedDate());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(whrequest.getRemarks());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append("New Request");
                    fileWriter.append(COMMA_DELIMITER);
                    System.out.println("Write new to CSV file Succeed!!!");
                } catch (Exception ee) {
                    ee.printStackTrace();
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException ie) {
                        System.out.println("Error occured while closing the fileWriter");
                        ie.printStackTrace();
                    }
                }
            }

//                send email
            LOGGER.info("send email to hms");

            EmailSender emailSender = new EmailSender();
            com.onsemi.mib.model.User user = new com.onsemi.mib.model.User();
            user.setFullname(userSession.getFullname());
            String[] to = {emailCsv};
//            String[] to = {"hmsrelon@gmail.com"}; //production
//            String[] to = {"fg79cj@onsemi.com"}; //testing
            emailSender.htmlEmailWithAttachment(
                    servletContext,
                    //                    user name
                    user,
                    //                    to
                    to,
                    // attachment file
                    new File(fileLocation),
                    //                    new File("C:\\HIMS_CSV\\RL\\cdars_retrieve.csv"),
                    //                    new File("\\\\mysed-rel-app03\\d$\\HIMS\\RL\\cdars_retrieve.csv"),
                    //                    subject
                    "New Hardware Request from HIMS",
                    //                    msg
                    "New Hardware Request has been added to HIMS"
            );

            EmailSender emailSenderSbnFactory = new EmailSender();
            com.onsemi.mib.model.User user2 = new com.onsemi.mib.model.User();
            user2.setFullname("All");
            String[] to2 = {emailNotification};
//                String[] to2 = {"sbnfactory@gmail.com", "fg79cj@onsemi.com"};
//            String[] to2 = {"sbnfactory@gmail.com"}; //production
//            String[] to2 = {"fg79cj@onsemi.com"}; //testing
            emailSenderSbnFactory.htmlEmailManyTo(
                    servletContext,
                    //                    user name
                    user2,
                    //                    to
                    to2,
                    //                    subject
                    "New Hardware Request for Retrieval from SBN Factory",
                    //                    msg
                    "New request for hardware retrieval from SBN Factory has been made. Please go to the HIMS SF system for verification process. Thank you. "
            );
//            return "redirect:/wh/whRetrieval";
            return "Successfully retrieve item";

        }

    }
}
