package com.onsemi.mib.tools;

import static com.google.common.io.CharStreams.copy;
import com.onsemi.mib.controller.EquipmentController;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SPTSWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SPTSWebService.class);

//    private static final String SPTS_WEB_SERVICE_URL = "http://sptstest.jorfei.com/SPTSServices/SPTSServices.asmx";
    private static final String SPTS_WEB_SERVICE_URL = "http://mysed-rel-app04/SPTSServices/SPTSServices.asmx";
    private static final String SPTS_WEB_SERVICE_URL_GLOBAL = "http://mysed-rel-app04/SPTSGlobalServices/SPTSGlobalServices.asmx";
    private static final String SPTS_ACTION_GETITEMALL = "http://tempuri.org/GetItemAll";
    private static final String SPTS_ACTION_GETITEMBYPKID = "http://tempuri.org/GetItemByPKID";
    private static final String SPTS_ACTION_GETITEMBYPARAM = "http://tempuri.org/GetItemByParam";
    private static final String SPTS_ACTION_GETITEMBYPARAM2 = "http://tempuri.org/GetItemByParam2";
    private static final String SPTS_ACTION_GETITEMWITHSFBYPARAM = "http://tempuri.org/GetItemWithSFByParam";
    private static final String SPTS_ACTION_INSERTITEM = "http://tempuri.org/InsertItem";
    private static final String SPTS_ACTION_UPDATEITEM = "http://tempuri.org/UpdateItem";
    private static final String SPTS_ACTION_UPDATEITEMSTATUS = "http://tempuri.org/UpdateItemStatus";
    private static final String SPTS_ACTION_DELETEITEM = "http://tempuri.org/DeleteItem";
    private static final String SPTS_ACTION_GETITEMACTIVITIESBYPARAM = "http://tempuri.org/GetItemActivitiesByParam";
    private static final String SPTS_ACTION_DISPOSEITEM = "http://tempuri.org/DisposeItem";
    //EXTRA
    private static final String SPTS_ACTION_GETRACKALL = "http://tempuri.org/GetRackAll";
    private static final String SPTS_ACTION_GETITEMTYPEALL = "http://tempuri.org/GetItemTypeAll";
    private static final String SPTS_ACTION_GETCARDTYPEALL = "http://tempuri.org/GetCardTypeAll";
    private static final String SPTS_ACTION_GETSUBTYPEALL = "http://tempuri.org/GetSubTypeAll";

    private static final String SPTS_ACTION_INSERTTRANSACTION = "http://tempuri.org/InsertTransaction";
    private static final String SPTS_ACTION_INSERTSFITEM = "http://tempuri.org/InsertSFItem";
    private static final String SPTS_ACTION_UPDATESFITEMLOCATION = "http://tempuri.org/UpdateSFItemLocation";
    private static final String SPTS_ACTION_DELETESFITEM = "http://tempuri.org/DeleteSFItem";
    private static final String SPTS_ACTION_INSERTACTIVITYLOG = "http://tempuri.org/InsertActivityLog";
    private static final String SPTS_ACTION_GETSFITEMBYPARAM = "http://tempuri.org/GetSFItemByParam";
    private static final String SPTS_ACTION_GETTRANSACTIONBYPARAM = "http://tempuri.org/GetTransactionByParam";
    private static final String SPTS_ACTION_DELETETRANSACTION = "http://tempuri.org/DeleteTransaction";

    private static final String SPTS_ACTION_GETEQPTFAMILY = "http://tempuri.org/EquipmentFamily_GetByParam";
    private static final String SPTS_ACTION_GETEQPTFAMILYBYFAMILYNAME = "http://tempuri.org/EquipmentFamily_GetByFamilyName";
    private static final String SPTS_ACTION_INSERTEQPTFAMILY = "http://tempuri.org/EquipmentFamily_Add";
    private static final String SPTS_ACTION_DELETEEQPTFAMILY = "http://tempuri.org/EquipmentFamily_Delete";

    private static final String SPTS_ACTION_GETEQPTRELTESTGROUP = "http://tempuri.org/RelTestGroup_GetByParam";
    private static final String SPTS_ACTION_GETEQPTRELTESTGROUPBYNAME = "http://tempuri.org/RelTestGroup_GetByRelTestGroupName";
    private static final String SPTS_ACTION_INSERTEQPTRELTESTGROUP = "http://tempuri.org/RelTestGroup_Add";
    private static final String SPTS_ACTION_DELETEEQPTRELTESTGROUP = "http://tempuri.org/RelTestGroup_Delete";

    private static final String SPTS_ACTION_GETEQPTMONITORINGBYPARAMS = "http://tempuri.org/EquipMonitoring_GetByParams";
    private static final String SPTS_ACTION_GETEQPTMONITORINGBYPKID = "http://tempuri.org/EquipMonitoring_GetByPKID";
    private static final String SPTS_ACTION_INSERTEQPTMONITORING = "http://tempuri.org/EquipMonitoring_Insert2";
    private static final String SPTS_ACTION_DELETEEQPTMONITORING = "http://tempuri.org/EquipMonitoring_DeleteByPKID";

    private static final String SPTS_ACTION_GETEQPTTECHBYPARAMS = "http://tempuri.org/EquipTech_GetByParams";
    private static final String SPTS_ACTION_GETEQPTTECHBYPKID = "http://tempuri.org/EquipTech_GetByPKID";
    private static final String SPTS_ACTION_INSERTEQPTTECH = "http://tempuri.org/EquipTech_Insert2";
    private static final String SPTS_ACTION_DELETEEQPTTECH = "http://tempuri.org/EquipTech_DeleteByPKID";

    private static final String SPTS_ACTION_GETEQPTVIMONITORINGBYPARAMS = "http://tempuri.org/VIMonitoring_GetByParams";
    private static final String SPTS_ACTION_GETEQPTVIMONITORINGBBYPKID = "http://tempuri.org/VIMonitoring_GetByPKID";
    private static final String SPTS_ACTION_INSERTEQPTVIMONITORING = "http://tempuri.org/VIMonitoring_Insert2";
    private static final String SPTS_ACTION_DELETEEQPTVIMONITORING = "http://tempuri.org/VIMonitoring_DeleteByPKID";

    private static final String SPTS_ACTION_GETGLOBALEQPTFAMILYNAMEALL = "http://tempuri.org/GetGlobalEquipmentFamilyNameAll";

    private static final String GETEQPTBYPARAM = "http://tempuri.org/Equipment_GetByParam";
    private static final String GETEQPTBYEQPTID = "http://tempuri.org/Equipment_GetByEquipmentID";
    private static final String GETSPTSEQPTGETBYPARAM = "http://tempuri.org/SPTSEquipment_GetByParam";
    private static final String GETEQPTGETBYRELTESTGROUP = "http://tempuri.org/Equipment_GetByRelTestGroup";
    private static final String GETSPTSEQPTGETBYPARAMMIB = "http://tempuri.org/SPTSEquipment_GetByParam_MIB";
    private static final String GETEQPTBYPKID = "http://tempuri.org/Equipment_GetByPKID";
    private static final String EQPTADD = "http://tempuri.org/Equipment_Add";
    private static final String EQPTUPDATE = "http://tempuri.org/Equipment_Update";
    private static final String EQPTDELETE = "http://tempuri.org/Equipment_Delete";

    private static final String EQPTSLOTADD = "http://tempuri.org/EquipmentSlot_Add";
    private static final String EQPTSLOTDELETE = "http://tempuri.org/EquipmentSlot_Delete";
    private static final String GETEQPTSLOTBYEQPTPKID = "http://tempuri.org/EquipmentSlot_GetByEquipmentPKID";

    private static final String EQPTTRAYADD = "http://tempuri.org/EquipmentTray_Add";
    private static final String EQPTTRAYUPDATE = "http://tempuri.org/EquipmentTray_Update";
    private static final String EQPTTRAYDELETE = "http://tempuri.org/EquipmentTray_Delete";
    private static final String GETEQPTTRAYBYEQPTPKID = "http://tempuri.org/EquipmentTray_GetByEquipmentPKID";

    private static final String GLOBALRELTESTGROUPADD = "http://tempuri.org/InsertGlobalRelTestGroup";
    private static final String GLOBALRELTESTGROUPDELETE = "http://tempuri.org/DeleteGlobalRelTestGroup";
    private static final String GETGLOBALRELTESTGROUPALL = "http://tempuri.org/GetGlobalRelTestGroupAll";
    private static final String GETGLOBALRELTESTGROUPBYPARAM = "http://tempuri.org/GetGlobalRelTestGroupDetailsByParam";

    private static final String GLOBALFAMILYNAMEADD = "http://tempuri.org/InsertGlobalEquipmentFamilyName";
    private static final String GLOBALFAMILYNAMEDELETE = "http://tempuri.org/DeleteGlobalEquipmentFamilyName";
    private static final String GETGLOBALFAMILYNAMEALL = "http://tempuri.org/GetGlobalEquipmentFamilyNameAll";
    private static final String GETGLOBALFAMILYNAMEBYPARAM = "http://tempuri.org/GetGlobalEquipmentFamilyNameDetailsByParam";

    //CBMS
    private static final String CBMS_WEB_SERVICE_URL = "http://mysed-rel-app02/CBMSServer/CBMSService.asmx";
    private static final String CBMS_ACTION_GETEQPTBOOKEDFOLFILES = "http://tempuri.org/GetBookedEquipmentFOLFiles";
    private static final String CBMS_ACTION_BOOKINGDETAILGETBYBOOKINGPKID = "http://tempuri.org/BookingDetail_GetByBookingPKID";
    private static final String CBMS_ACTION_GETBOOKEDEQUIPMENT = "http://tempuri.org/GetBookedEquipment";
    private static final String CBMS_ACTION_GETBOOKINGBYPKID = "http://tempuri.org/Booking_GetByPKID";
    
    // HARDWARE ID CONFIG / DATA
    private static final String HWIDCONFIG_DELETE_BYPKID    = "http://tempuri.org/ItemHardwareConfig_DeleteByPKID";
    private static final String HWIDCONFIG_GET_BYPKID       = "http://tempuri.org/ItemHardwareConfig_GetByPKID";
    private static final String HWIDCONFIG_GET_BYPARAMS     = "http://tempuri.org/ItemHardwareConfig_GetByParams";
    private static final String HWIDCONFIG_INSERT           = "http://tempuri.org/ItemHardwareConfig_Insert";
    private static final String HWIDCONFIG_UPDATE_BYPKID    = "http://tempuri.org/ItemHardwareConfig_UpdateByPKID";
    private static final String HWID_DELETE_BYPKID          = "http://tempuri.org/ItemHardware_DeleteByPKID";
    private static final String HWID_GET_BYPKID             = "http://tempuri.org/ItemHardware_GetByPKID";
    private static final String HWID_GET_BYPARAMS           = "http://tempuri.org/ItemHardware_GetByParams";
    private static final String HWID_INSERT                 = "http://tempuri.org/ItemHardware_Insert";
    private static final String HWID_UPDATE_BYPKID          = "http://tempuri.org/ItemHardware_UpdateByPKID";

    public static JSONArray getItemAll() throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONObject getItemByPKID(String pkID) throws IOException {
        JSONObject item = new JSONObject();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemByPKID(pkID), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getItemByPKIDResponse = soapBody.getJSONObject("GetItemByPKIDResponse");
            JSONObject getItemByPKIDResult = getItemByPKIDResponse.getJSONObject("GetItemByPKIDResult");
            JSONObject resultContent = getItemByPKIDResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            item = itemDS.getJSONObject("ITEMS");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return item;
    }

    public static JSONArray getItemByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemDS");
                JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ITEMS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getItemByParam2(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemByParam2(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMBYPARAM2);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemByParam2Response");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemByParam2Result");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemDS");
                JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ITEMS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getItemWithSfByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemWithSfByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMWITHSFBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemWithSFByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemWithSFByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemDS");
                JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ITEMS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertItemResponse");
            pkID = getAllItemResponse.getInt("InsertItemResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_UPDATEITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("UpdateItemResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("UpdateItemResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateItemStatus(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateItemStatus(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_UPDATEITEMSTATUS);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("UpdateItemStatusResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("UpdateItemStatusResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse disposeItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.disposeItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DISPOSEITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DisposeItemResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("DisposeItemResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteItemResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteItemResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    private static HashMap errorResponse(int result, String errorResponse) {
        HashMap error = new HashMap();
        if (result == 500) {
            System.out.println("SPTSWebService Status: " + result);
            System.out.println("SPTSWebService Response: " + errorResponse);
            JSONObject jsonObject = XML.toJSONObject(errorResponse);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject soapFault = soapBody.getJSONObject("soap:Fault");
            String faultCode = soapFault.getString("faultcode");
            String faultString = soapFault.getString("faultstring");
            System.out.println("faultCode: " + faultCode);
            System.out.println("faultString: " + faultString);
            error.put("errorCode", faultCode);
            error.put("errorMessage", faultString);
            try {
                JSONObject faultDetail = soapFault.getJSONObject("detail");
                if (!faultDetail.toString().equals("")) {
                    String faultDetailMessage = faultDetail.getString("message");
                    String faultDetailDescription = faultDetail.getString("description");
                    System.out.println("faultDetailMessage: " + faultDetailMessage);
                    System.out.println("faultDetailDescription: " + faultDetailDescription);
                    String errorDetail = faultDetailMessage;
                    if (!faultDetailDescription.equals("") && !faultDetailDescription.equals(faultDetailMessage)) {
                        errorDetail = faultDetailMessage + " - " + faultDetailDescription;
                    }
                    error.put("errorDetail", errorDetail);
                }
            } catch (Exception e) {
                error.put("errorDetail", "");
            }
        } else if (result == 400) {
            System.out.println("SPTSWebService Status: " + result);
            System.out.println("SPTSWebService Response: " + errorResponse);
            error.put("errorCode", Integer.toString(result));
            error.put("errorMessage", errorResponse);
            error.put("errorDetail", "");
        }
        return error;
    }

    //EXTRA
    public static JSONArray getRackAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getRackAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETRACKALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetRackAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetRackAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("RackDS");
            JSONArray jsonArray = itemDS.optJSONArray("RACKS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("RACKS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
    }

    public static JSONArray getItemTypeAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemTypeAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMTYPEALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemTypeAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemTypeAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
    }

    public static JSONArray getSubTypeAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getSubTypeAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETSUBTYPEALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetSubTypeAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetSubTypeAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
    }

    public static JSONArray getCardTypeAll() throws IOException {
        JSONArray racks = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getRackAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETCARDTYPEALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetCardTypeAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetCardTypeAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            //System.out.println(resultContent.toString());
            JSONObject itemDS = resultContent.getJSONObject("ItemDS");
            JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("ITEMS");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                racks = ja;
            } else {
                racks = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return racks;
    }

    public static JSONArray getSFItemByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getSFItemByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETSFITEMBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetSFItemByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetSFItemByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
//                 System.out.println(resultContent.toString());
                JSONObject itemDS = resultContent.getJSONObject("SFItemDS");
                JSONArray jsonArray = itemDS.optJSONArray("SFITEMS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("SFITEMS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertTransaction(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertTransaction(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTTRANSACTION);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertTransactionResponse");
            pkID = getAllItemResponse.getInt("InsertTransactionResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse insertSFItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertSFItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTSFITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertSFItemResponse");
            pkID = getAllItemResponse.getInt("InsertSFItemResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateSFItemLocation(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateSFItemLocation(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_UPDATESFITEMLOCATION);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("UpdateSFItemLocationResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("UpdateSFItemLocationResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse DeleteSFItem(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteSFItem(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETESFITEM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteSFItemResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteSFItemResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse insertActivityLog(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertActivityLog(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTACTIVITYLOG);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertActivityLogResponse");
            pkID = getAllItemResponse.getInt("InsertActivityLogResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getTransactionByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getTransactionByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETTRANSACTIONBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetTransactionByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetTransactionByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
//                System.out.println(resultContent.toString());
                JSONObject itemDS = resultContent.getJSONObject("TransactionDS");
                JSONArray jsonArray = itemDS.optJSONArray("TRANSACTIONS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("TRANSACTIONS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse DeleteTransaction(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteTransaction(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETETRANSACTION);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteTransactionResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteTransactionResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getItemActivitiesByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getItemActivitiesByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETITEMACTIVITIESBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetItemActivitiesByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetItemActivitiesByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemDS");
                JSONArray jsonArray = itemDS.optJSONArray("ITEMS");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ITEMS");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptFamilyByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptFamily(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTFAMILY);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentFamily_GetByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipmentFamily_GetByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("FamilyData");
                JSONArray jsonArray = itemDS.optJSONArray("Families");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Families");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptFamilyByFamilyName(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptFamilyByName(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTFAMILYBYFAMILYNAME);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentFamily_GetByFamilyNameResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipmentFamily_GetByFamilyNameResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("FamilyData");
                JSONArray jsonArray = itemDS.optJSONArray("Family");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Family");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptFamily(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptFamily(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTEQPTFAMILY);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentFamily_AddResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("EquipmentFamily_AddResult");
//            pkID = getAllItemResponse.getInt("EquipmentFamily_AddResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptFamily(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptFamily(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEEQPTFAMILY);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentFamily_DeleteResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("EquipmentFamily_DeleteResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptRelTestGroupByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptRelTestGroup(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTRELTESTGROUP);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("RelTestGroup_GetByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("RelTestGroup_GetByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("RelTestGroupData");
                JSONArray jsonArray = itemDS.optJSONArray("RelTestGroups");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("RelTestGroups");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptRelTestGroupByName(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptRelTestGroupByName(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTRELTESTGROUPBYNAME);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("RelTestGroup_GetByRelTestGroupNameResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("RelTestGroup_GetByRelTestGroupNameResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("RelTestGroupData");
                JSONArray jsonArray = itemDS.optJSONArray("RelTestGroup");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("RelTestGroup");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptRelTestGroup(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptRelTestGroup(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTEQPTRELTESTGROUP);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("RelTestGroup_AddResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("RelTestGroup_AddResult");
//            pkID = getAllItemResponse.getInt("RelTestGroup_AddResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptRelTestGroup(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptRelTestGroup(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEEQPTRELTESTGROUP);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("RelTestGroup_DeleteResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("RelTestGroup_DeleteResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptMonitoringByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptMonitoringByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTMONITORINGBYPARAMS);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipMonitoring_GetByParamsResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipMonitoring_GetByParamsResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipMonitoringData");
                JSONArray jsonArray = itemDS.optJSONArray("EquipMonitoring");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("EquipMonitoring");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptMonitoringByPkid(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptMonitoringByPkid(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTMONITORINGBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipMonitoring_GetByPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipMonitoring_GetByPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipMonitoringData");
                JSONArray jsonArray = itemDS.optJSONArray("EquipMonitoring");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("EquipMonitoring");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptMonitoring(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptMonitoring(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTEQPTMONITORING);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipMonitoring_Insert2Response");
            pkID = getAllItemResponse.getInt("EquipMonitoring_Insert2Result");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptMonitoring(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptMonitoring(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEEQPTMONITORING);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipMonitoring_DeleteByPKIDResponse");
//            LOGGER.info("getAllItemResponse: " + getAllItemResponse);
//            Boolean deleteResult = getAllItemResponse.getBoolean("EquipMonitoring_DeleteByPKIDResponse");
//            Boolean deleteResult = soapBody.getBoolean("EquipMonitoring_DeleteByPKIDResponse");
//            if (deleteResult) {
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
//            } else {
//                sr.setStatus(Boolean.FALSE);
//                sr.setResponseCode(result);
//                sr.setResponseId(0);
//                sr.setErrorCode("200");
//                sr.setErrorMessage("Update failed!");
//                sr.setErrorDetail("");
//            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptTechByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptTechByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTTECHBYPARAMS);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipTech_GetByParamsResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipTech_GetByParamsResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipTechData");
                JSONArray jsonArray = itemDS.optJSONArray("EquipTech");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("EquipTech");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptTechByPkid(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptTechByPkid(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTTECHBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipTech_GetByPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipTech_GetByPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipTechData");
                JSONArray jsonArray = itemDS.optJSONArray("EquipTech");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("EquipTech");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptTech(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptTech(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTEQPTTECH);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipTech_Insert2Response");
            pkID = getAllItemResponse.getInt("EquipTech_Insert2Result");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptTech(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptTech(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEEQPTTECH);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipTech_DeleteByPKIDResponse");
//            LOGGER.info("getAllItemResponse: " + getAllItemResponse);
//            Boolean deleteResult = getAllItemResponse.getBoolean("EquipMonitoring_DeleteByPKIDResponse");
//            Boolean deleteResult = soapBody.getBoolean("EquipMonitoring_DeleteByPKIDResponse");
//            if (deleteResult) {
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
//            } else {
//                sr.setStatus(Boolean.FALSE);
//                sr.setResponseCode(result);
//                sr.setResponseId(0);
//                sr.setErrorCode("200");
//                sr.setErrorMessage("Update failed!");
//                sr.setErrorDetail("");
//            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptViMonitoringByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptViMonitoringByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTVIMONITORINGBYPARAMS);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("VIMonitoring_GetByParamsResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("VIMonitoring_GetByParamsResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("VIMonitoringData");
                JSONArray jsonArray = itemDS.optJSONArray("VIMonitoring");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("VIMonitoring");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptViMonitoringByPkid(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptViMonitoringByPkid(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETEQPTVIMONITORINGBBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("VIMonitoring_GetByPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("VIMonitoring_GetByPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("VIMonitoringData");
                JSONArray jsonArray = itemDS.optJSONArray("VIMonitoring");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("VIMonitoring");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptViMonitoring(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptViMonitoring(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_INSERTEQPTVIMONITORING);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("VIMonitoring_Insert2Response");
            pkID = getAllItemResponse.getInt("VIMonitoring_Insert2Result");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptViMonitoring(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptViMonitoring(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_DELETEEQPTVIMONITORING);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("VIMonitoring_DeleteByPKIDResponse");
//            LOGGER.info("getAllItemResponse: " + getAllItemResponse);
//            Boolean deleteResult = getAllItemResponse.getBoolean("EquipMonitoring_DeleteByPKIDResponse");
//            Boolean deleteResult = soapBody.getBoolean("EquipMonitoring_DeleteByPKIDResponse");
//            if (deleteResult) {
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
//            } else {
//                sr.setStatus(Boolean.FALSE);
//                sr.setResponseCode(result);
//                sr.setResponseId(0);
//                sr.setErrorCode("200");
//                sr.setErrorMessage("Update failed!");
//                sr.setErrorDetail("");
//            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getGlobalEqptFamilyNameAll() throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getGlobalFamilyNameAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", SPTS_ACTION_GETGLOBALEQPTFAMILYNAMEALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetGlobalEquipmentFamilyNameAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetGlobalEquipmentFamilyNameAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("GlobalEquipmentFamilyNameDS");
            JSONArray jsonArray = itemDS.optJSONArray("GLOBALEQUIPMENTFAMILYNAME");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("GLOBALEQUIPMENTFAMILYNAME");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getSptsEqptByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getSptsEqptByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETSPTSEQPTGETBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
//            System.out.println("postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString());

            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("SPTSEquipment_GetByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("SPTSEquipment_GetByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentData");
                JSONArray jsonArray = itemDS.optJSONArray("SPTSEquipment");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("SPTSEquipment");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getSptsEqptByParamMib(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getSptsEqptByParamMib(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETSPTSEQPTGETBYPARAMMIB);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
//            System.out.println("postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString());

            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("SPTSEquipment_GetByParam_MIBResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("SPTSEquipment_GetByParam_MIBResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentData");
                JSONArray jsonArray = itemDS.optJSONArray("SPTSEquipment");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("SPTSEquipment");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptByRelTestGroup(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptByRelTestGroup(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETEQPTGETBYRELTESTGROUP);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_GetByRelTestGroupResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("Equipment_GetByRelTestGroupResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentData");
                JSONArray jsonArray = itemDS.optJSONArray("Equipments");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Equipments");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptbyParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETEQPTBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_GetByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("Equipment_GetByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentData");
                JSONArray jsonArray = itemDS.optJSONArray("Equipments");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Equipments");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptSlot(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptslot(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTSLOTADD);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentSlot_AddResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("EquipmentSlot_AddResult");
//            pkID = getAllItemResponse.getInt("RelTestGroup_AddResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptSlot(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptSlot(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTSLOTDELETE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentSlot_DeleteResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("EquipmentSlot_DeleteResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptSlotByEqptPkid(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptSlotByEqptPkid(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETEQPTSLOTBYEQPTPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentSlot_GetByEquipmentPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipmentSlot_GetByEquipmentPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentSlotData");
                JSONArray jsonArray = itemDS.optJSONArray("EquipmentSlots");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("EquipmentSlots");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqptTray(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqptTray(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTTRAYADD);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentTray_AddResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("EquipmentTray_AddResult");
//            pkID = getAllItemResponse.getInt("RelTestGroup_AddResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateEqptTray(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateEqptTray(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTTRAYUPDATE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentTray_UpdateResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("EquipmentTray_UpdateResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqptTray(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqptTray(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTTRAYDELETE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentTray_DeleteResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("EquipmentTray_DeleteResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptTrayByEqptPkid(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptTrayByEqptPkid(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETEQPTTRAYBYEQPTPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("EquipmentTray_GetByEquipmentPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("EquipmentTray_GetByEquipmentPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentTrayData");
                JSONArray jsonArray = itemDS.optJSONArray("EquipmentTray");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("EquipmentTray");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertEqpt(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertEqpt(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTADD);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_AddResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("Equipment_AddResult");
//            pkID = getAllItemResponse.getInt("RelTestGroup_AddResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse updateEqpt(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateEqpt(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTUPDATE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_UpdateResponse");
            Boolean updateResult = getAllItemResponse.getBoolean("Equipment_UpdateResult");
            if (updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteEqpt(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteEqpt(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", EQPTDELETE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_DeleteResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("Equipment_DeleteResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getEqptByEqptId(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptByEqptId(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETEQPTBYEQPTID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_GetByEquipmentIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("Equipment_GetByEquipmentIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentData");
                JSONArray jsonArray = itemDS.optJSONArray("Equipment");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Equipment");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getEqptByPkid(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getEqptByPkid(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETEQPTBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Equipment_GetByPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("Equipment_GetByPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("EquipmentData");
                JSONArray jsonArray = itemDS.optJSONArray("Equipment");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Equipment");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertGlobalRelTestGroup(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertGlobalRelTestGroup(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GLOBALRELTESTGROUPADD);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertGlobalRelTestGroupResponse");
            String updateResult = getAllItemResponse.getString("InsertGlobalRelTestGroupResult");
//            Boolean updateResult = getAllItemResponse.getBoolean("InsertGlobalRelTestGroupResult");
//            pkID = getAllItemResponse.getInt("RelTestGroup_AddResult");
            if (null != updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteGlobalRelTestGroup(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteGlobalRelTestGroup(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GLOBALRELTESTGROUPDELETE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteGlobalRelTestGroupResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteGlobalRelTestGroupResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getGlobalRelTestGroupAll() throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getGlobalRelTestGroupAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETGLOBALRELTESTGROUPALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
//            System.out.println("postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString());
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetGlobalRelTestGroupAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetGlobalRelTestGroupAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("GlobalRelTestGroupDS");
            JSONArray jsonArray = itemDS.optJSONArray("GLOBALRELTESTGROUP");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("GLOBALRELTESTGROUP");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getGlobalRelTestGroupByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getGlobalRelTestGroupByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETGLOBALRELTESTGROUPBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
//            System.out.println("postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString());
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetGlobalRelTestGroupDetailsByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetGlobalRelTestGroupDetailsByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("GlobalRelTestGroupDS");
                JSONArray jsonArray = itemDS.optJSONArray("GLOBALRELTESTGROUP");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("GLOBALRELTESTGROUP");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static SPTSResponse insertGlobalFamilyName(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.insertGlobalFamilyName(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GLOBALFAMILYNAMEADD);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("InsertGlobalEquipmentFamilyNameResponse");
            String updateResult = getAllItemResponse.getString("InsertGlobalEquipmentFamilyNameResult");
//            Boolean updateResult = getAllItemResponse.getBoolean("InsertGlobalRelTestGroupResult");
//            pkID = getAllItemResponse.getInt("RelTestGroup_AddResult");
            if (null != updateResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(pkID);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            }
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static SPTSResponse deleteGlobalFamilyName(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteGlobalFamilyName(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GLOBALFAMILYNAMEDELETE);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("DeleteGlobalEquipmentFamilyNameResponse");
            Boolean deleteResult = getAllItemResponse.getBoolean("DeleteGlobalEquipmentFamilyNameResult");
            if (deleteResult) {
                sr.setStatus(Boolean.TRUE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("");
                sr.setErrorMessage("");
                sr.setErrorDetail("");
            } else {
                sr.setStatus(Boolean.FALSE);
                sr.setResponseCode(result);
                sr.setResponseId(0);
                sr.setErrorCode("200");
                sr.setErrorMessage("Update failed!");
                sr.setErrorDetail("");
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }

    public static JSONArray getGlobalFamilyNameAll() throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getGlobalFamilyNameAll(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETGLOBALFAMILYNAMEALL);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
//            System.out.println("postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString());
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetGlobalEquipmentFamilyNameAllResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetGlobalEquipmentFamilyNameAllResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("GlobalEquipmentFamilyNameDS");
            JSONArray jsonArray = itemDS.optJSONArray("GLOBALEQUIPMENTFAMILYNAME");
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("GLOBALEQUIPMENTFAMILYNAME");
                JSONArray ja = new JSONArray();
                ja.put(jo);
                items = ja;
            } else {
                items = jsonArray;
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getGlobalFamilyNameByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getGlobalFamilyNameByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL_GLOBAL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", GETGLOBALFAMILYNAMEBYPARAM);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
//            System.out.println("postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString());
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetGlobalEquipmentFamilyNameDetailsByParamResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetGlobalEquipmentFamilyNameDetailsByParamResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("GlobalEquipmentFamilyNameDS");
                JSONArray jsonArray = itemDS.optJSONArray("GLOBALEQUIPMENTFAMILYNAME");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("GLOBALEQUIPMENTFAMILYNAME");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getBookedEqptFOLFiles(boolean noFtp) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getBookedEqptFOLFiles(noFtp), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(CBMS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", CBMS_ACTION_GETEQPTBOOKEDFOLFILES);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetBookedEquipmentFOLFilesResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetBookedEquipmentFOLFilesResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("NewDataSet");
                JSONArray jsonArray = itemDS.optJSONArray("BookedEquipmentFOLFiles");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("BookedEquipmentFOLFiles");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getBookingDetailByPKID(int pkid) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getBookingDetailByPKID(pkid), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(CBMS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", CBMS_ACTION_BOOKINGDETAILGETBYBOOKINGPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("BookingDetail_GetByBookingPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("BookingDetail_GetByBookingPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("BookingDetailData");
                JSONArray jsonArray = itemDS.optJSONArray("BookingDetail");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("BookingDetail");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }

    public static JSONArray getBookedEquipment(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getBookedEquipment(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(CBMS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", CBMS_ACTION_GETBOOKEDEQUIPMENT);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("GetBookedEquipmentResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("GetBookedEquipmentResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("NewDataSet");
                JSONArray jsonArray = itemDS.optJSONArray("BookedEquipment");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("BookedEquipment");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    public static JSONArray getBookingByPKID(int pkid) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getBookingByPKID(pkid), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(CBMS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", CBMS_ACTION_GETBOOKINGBYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("Booking_GetByPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("Booking_GetByPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("BookingData");
                JSONArray jsonArray = itemDS.optJSONArray("Booking");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("Booking");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    //<editor-fold defaultstate="collapsed" desc="HARDWARE ID CONFIGURATION / DATA">
    public static SPTSResponse insertItemHardwareConfig(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.ItemHardwareConfig_Insert(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWIDCONFIG_INSERT);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardwareConfig_InsertResponse");
            pkID = getAllItemResponse.getInt("ItemHardwareConfig_InsertResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
    
    public static SPTSResponse insertItemHardware(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        Integer pkID = 0;
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.ItemHardware_Insert(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWID_INSERT);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardware_InsertResponse");
            pkID = getAllItemResponse.getInt("ItemHardware_InsertResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(pkID);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
    
    public static SPTSResponse updateHardwareIdConfig(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateHardwareIdConfig(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWIDCONFIG_UPDATE_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardwareConfig_UpdateByPKIDResponse");
//            Boolean updateResult = getAllItemResponse.getBoolean("ItemHardwareConfig_UpdateByPKIDResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
    
    public static SPTSResponse updateHardwareId(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.updateHardwareId(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWID_UPDATE_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardware_UpdateByPKIDResponse");
//            Boolean updateResult = getAllItemResponse.getBoolean("ItemHardware_UpdateByPKIDResult");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
    
    public static JSONArray getHardwareIdConfigByPKID(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getHardwareIdConfigByPKID(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWIDCONFIG_GET_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getResponse = soapBody.getJSONObject("ItemHardwareConfig_GetByPKIDResponse");
            try {
                JSONObject getResult = getResponse.getJSONObject("ItemHardwareConfig_GetByPKIDResult");
                JSONObject resultContent = getResult.getJSONObject("diffgr:diffgram");
                JSONObject dataset = resultContent.getJSONObject("ItemHardwareConfigData");
                JSONArray jsonArray = dataset.optJSONArray("ItemHardwareConfig");
                if (jsonArray == null) {
                    JSONObject jo = dataset.getJSONObject("ItemHardwareConfig");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                LOGGER.info("SINI MASUK EXCEPTION >> "+e.getMessage());
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    public static JSONObject getHardwareIdConfigByPKIDJO(JSONObject params) throws IOException {
        JSONObject item = new JSONObject();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getHardwareIdConfigByPKID(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWIDCONFIG_GET_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getItemByPKIDResponse = soapBody.getJSONObject("ItemHardwareConfig_GetByPKIDResponse");
            JSONObject getItemByPKIDResult = getItemByPKIDResponse.getJSONObject("ItemHardwareConfig_GetByPKIDResult");
            JSONObject resultContent = getItemByPKIDResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemHardwareConfigData");
            item = itemDS.getJSONObject("ItemHardwareConfig");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return item;
    }
    
    public static JSONArray getHardwareIdByPKID(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getHardwareIdByPKID(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWID_GET_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardware_GetByPKIDResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("ItemHardware_GetByPKIDResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemHardwareData");
                JSONArray jsonArray = itemDS.optJSONArray("ItemHardware");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ItemHardware");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    public static JSONObject getHardwareIdByPKIDJO(JSONObject params) throws IOException {
        JSONObject item = new JSONObject();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getHardwareIdByPKID(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWID_GET_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getItemByPKIDResponse = soapBody.getJSONObject("ItemHardware_GetByPKIDResponse");
            JSONObject getItemByPKIDResult = getItemByPKIDResponse.getJSONObject("ItemHardware_GetByPKIDResult");
            JSONObject resultContent = getItemByPKIDResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("ItemHardwareData");
            item = itemDS.getJSONObject("ItemHardware");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return item;
    }
    
    public static JSONArray getHardwareIdConfigByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getHardwareIdConfigByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWIDCONFIG_GET_BYPARAMS);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardwareConfig_GetByParamsResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("ItemHardwareConfig_GetByParamsResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemHardwareConfigData");
                JSONArray jsonArray = itemDS.optJSONArray("ItemHardwareConfig");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ItemHardwareConfig");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    public static JSONArray getHardwareIdByParam(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.getHardwareIdByParam(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWID_GET_BYPARAMS);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardware_GetByParamsResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("ItemHardware_GetByParamsResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("ItemHardwareData");
                JSONArray jsonArray = itemDS.optJSONArray("ItemHardware");
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("ItemHardware");
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    items = ja;
                } else {
                    items = jsonArray;
                }
            } catch (Exception e) {
                //Ignore
            }
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return items;
    }
    
    // DELETE
    public static SPTSResponse deleteHardwareIdConfigByPKID(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteHardwareIdConfig(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWIDCONFIG_DELETE_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            LOGGER.info("WE DELETE THE SPTS DATA HERE, STATUS?");
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardwareConfig_DeleteByPKIDResponse");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            LOGGER.info("THIS FUNCTION WHERE IT IS FAILED TO DELETE THE DATA");
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
    
    public static SPTSResponse deleteHardwareIdByPKID(JSONObject params) throws IOException {
        SPTSResponse sr = new SPTSResponse();
        RequestEntity requestEntity = new StringRequestEntity(SPTSRequestXML.deleteHardwareId(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(SPTS_WEB_SERVICE_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", HWID_DELETE_BYPKID);
        HttpClient httpClient = new HttpClient();
        int result = httpClient.executeMethod(postMethod);
        if (result == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            StringBuilder stringBuilder = new StringBuilder();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            copy(reader, stringBuilder);
            reader.close();
            String xmlString = stringBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);
            JSONObject soapEnvelope = jsonObject.getJSONObject("soap:Envelope");
            JSONObject soapBody = soapEnvelope.getJSONObject("soap:Body");
            JSONObject getAllItemResponse = soapBody.getJSONObject("ItemHardware_DeleteByPKIDResponse");
            sr.setStatus(Boolean.TRUE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode("");
            sr.setErrorMessage("");
            sr.setErrorDetail("");
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            HashMap error = errorResponse(result, errorResponse);
            sr.setStatus(Boolean.FALSE);
            sr.setResponseCode(result);
            sr.setResponseId(0);
            sr.setErrorCode(error.get("errorCode").toString());
            sr.setErrorMessage(error.get("errorMessage").toString());
            sr.setErrorDetail(error.get("errorDetail").toString());
        }
        return sr;
    }
    //</editor-fold>

}