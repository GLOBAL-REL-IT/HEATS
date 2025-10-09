package com.onsemi.mib.tools;

import java.io.IOException;
import java.util.Iterator;
import org.json.JSONObject;

public class MIBGatewayRequestXML {

    public static String getCBMSGetBookedEquipment(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<CBMS_GetBookedEquipment xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</CBMS_GetBookedEquipment>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getCBMSGetBookingDetailsByBookingPKID(String pkID) throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<CBMS_GetBookingDetailsByBookingPKID xmlns=\"http://tempuri.org/\">"
                + "<bookingPKID>" + pkID + "</bookingPKID>"
                + "</CBMS_GetBookingDetailsByBookingPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getCBMSGetBookingDetailsByRMSNoAndEventNameCode(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<CBMS_GetBookingDetailsByRMSNoAndEventNameCode xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</CBMS_GetBookingDetailsByRMSNoAndEventNameCode>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getCBMSGetRMSToBook() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<CBMS_GetRMSToBook xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }
}
