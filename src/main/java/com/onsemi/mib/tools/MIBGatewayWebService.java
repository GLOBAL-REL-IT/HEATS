package com.onsemi.mib.tools;

import static com.google.common.io.CharStreams.copy;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class MIBGatewayWebService {

    private static final String MIB_GATEWAY_URL = "http://mysed-rel-lrt04/MIBGatewayService/MIBGatewayService.asmx";
    private static final String MIB_GATEWAY_CBMS_GETBOOKEDEQUIPMENT = "http://tempuri.org/CBMS_GetBookedEquipment";
    private static final String MIB_GATEWAY_CBMS_GETBOOKINGDETAILSBYBOOKINGPKID = "http://tempuri.org/CBMS_GetBookingDetailsByBookingPKID";
    private static final String MIB_GATEWAY_CBMS_GETBOOKINGDETAILSBYRMSNOANDEVENTNAMECODE = "http://tempuri.org/CBMS_GetBookingDetailsByRMSNoAndEventNameCode";
    private static final String MIB_GATEWAY_CBMS_GETRMSTOBOOK = "http://tempuri.org/CBMS_GetRMSToBook";

    //MIB Gateway
    public static JSONArray getCBMSGetBookedEquipment(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(MIBGatewayRequestXML.getCBMSGetBookedEquipment(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(MIB_GATEWAY_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", MIB_GATEWAY_CBMS_GETBOOKEDEQUIPMENT);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("CBMS_GetBookedEquipmentResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("CBMS_GetBookedEquipmentResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("NewDataSet"); //need to change
                JSONArray jsonArray = itemDS.optJSONArray("BookedEquipment"); //need to change
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("BookedEquipment"); //need to change
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

    public static JSONObject getCBMSGetBookingDetailsByBookingPKID(String pkID) throws IOException {
        JSONObject item = new JSONObject();
        RequestEntity requestEntity = new StringRequestEntity(MIBGatewayRequestXML.getCBMSGetBookingDetailsByBookingPKID(pkID), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(MIB_GATEWAY_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", MIB_GATEWAY_CBMS_GETBOOKINGDETAILSBYBOOKINGPKID);
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
            JSONObject getItemByPKIDResponse = soapBody.getJSONObject("CBMS_GetBookingDetailsByBookingPKIDResponse");
            JSONObject getItemByPKIDResult = getItemByPKIDResponse.getJSONObject("CBMS_GetBookingDetailsByBookingPKIDResult");
            JSONObject resultContent = getItemByPKIDResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("BookingDetailData"); //need to change
            item = itemDS.getJSONObject("BookingDetail"); //need to change
        } else {
            String errorResponse = postMethod.getResponseBodyAsString();
            errorResponse(result, errorResponse);
        }
        return item;
    }

    public static JSONArray getCBMSGetBookingDetailsByRMSNoAndEventNameCode(JSONObject params) throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(MIBGatewayRequestXML.getCBMSGetBookingDetailsByRMSNoAndEventNameCode(params), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(MIB_GATEWAY_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", MIB_GATEWAY_CBMS_GETBOOKINGDETAILSBYRMSNOANDEVENTNAMECODE);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("CBMS_GetBookingDetailsByRMSNoAndEventNameCodeResponse");
            try {
                JSONObject getAllItemResult = getAllItemResponse.getJSONObject("CBMS_GetBookingDetailsByRMSNoAndEventNameCodeResult");
                JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
                JSONObject itemDS = resultContent.getJSONObject("NewDataSet"); //need to change
                JSONArray jsonArray = itemDS.optJSONArray("BookingDetails"); //need to change
                if (jsonArray == null) {
                    JSONObject jo = itemDS.getJSONObject("BookingDetails"); //need to change
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

    public static JSONArray getCBMSGetRMSToBook() throws IOException {
        JSONArray items = new JSONArray();
        RequestEntity requestEntity = new StringRequestEntity(MIBGatewayRequestXML.getCBMSGetRMSToBook(), "text/xml", "ISO-8859-1");
        PostMethod postMethod = new PostMethod(MIB_GATEWAY_URL);
        postMethod.setRequestEntity(requestEntity);
        postMethod.setRequestHeader("SOAPAction", MIB_GATEWAY_CBMS_GETRMSTOBOOK);
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
            JSONObject getAllItemResponse = soapBody.getJSONObject("CBMS_GetRMSToBookResponse");
            JSONObject getAllItemResult = getAllItemResponse.getJSONObject("CBMS_GetRMSToBookResult");
            JSONObject resultContent = getAllItemResult.getJSONObject("diffgr:diffgram");
            JSONObject itemDS = resultContent.getJSONObject("RMSInformationData"); //need to change
            JSONArray jsonArray = itemDS.optJSONArray("RMSForBookings"); //need to change
            if (jsonArray == null) {
                JSONObject jo = itemDS.getJSONObject("RMSForBookings"); //need to change
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

    private static HashMap errorResponse(int result, String errorResponse) {
        HashMap error = new HashMap();
        if (result == 500) {
            System.out.println("MIBGateway Status: " + result);
            System.out.println("MIBGateway Response: " + errorResponse);
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
            System.out.println("MIBGateway Status: " + result);
            System.out.println("MIBGateway Response: " + errorResponse);
            error.put("errorCode", Integer.toString(result));
            error.put("errorMessage", errorResponse);
            error.put("errorDetail", "");
        }
        return error;
    }
}
