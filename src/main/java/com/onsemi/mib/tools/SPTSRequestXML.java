package com.onsemi.mib.tools;

import java.io.IOException;
import java.util.Iterator;
import org.json.JSONObject;

public class SPTSRequestXML {

    public static String getItemAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemByPKID(String pkID) throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemByPKID xmlns=\"http://tempuri.org/\">"
                + "<pkID>" + pkID + "</pkID>"
                + "</GetItemByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetItemByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemByParam2(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemByParam2 xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetItemByParam2>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemWithSfByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemWithSFByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetItemWithSFByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<UpdateItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</UpdateItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateItemStatus(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<UpdateItemStatus xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</UpdateItemStatus>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String disposeItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DisposeItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DisposeItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    //EXTRA
    public static String getRackAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetRackAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemTypeAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemTypeAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getSubTypeAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetSubTypeAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getCardTypeAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetCardTypeAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertTransaction(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertTransaction xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertTransaction>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getSFItemByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetSFItemByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetSFItemByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertSFItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertSFItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertSFItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateSFItemLocation(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<UpdateSFItemLocation xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</UpdateSFItemLocation>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteSFItem(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteSFItem xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteSFItem>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertActivityLog(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertActivityLog xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertActivityLog>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getTransactionByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetTransactionByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetTransactionByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteTransaction(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteTransaction xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteTransaction>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getItemActivitiesByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetItemActivitiesByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetItemActivitiesByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptFamily(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentFamily_GetByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentFamily_GetByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptFamilyByName(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentFamily_GetByFamilyName xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentFamily_GetByFamilyName>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptFamily(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentFamily_Add xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentFamily_Add>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptFamily(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentFamily_Delete xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentFamily_Delete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptRelTestGroup(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<RelTestGroup_GetByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</RelTestGroup_GetByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptRelTestGroupByName(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<RelTestGroup_GetByRelTestGroupName xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</RelTestGroup_GetByRelTestGroupName>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptRelTestGroup(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<RelTestGroup_Add xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</RelTestGroup_Add>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptRelTestGroup(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<RelTestGroup_Delete xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</RelTestGroup_Delete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptMonitoringByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipMonitoring_GetByParams xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipMonitoring_GetByParams>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptMonitoringByPkid(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipMonitoring_GetByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipMonitoring_GetByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptMonitoring(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipMonitoring_Insert2 xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipMonitoring_Insert2>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptMonitoring(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipMonitoring_DeleteByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipMonitoring_DeleteByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptTechByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipTech_GetByParams xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipTech_GetByParams>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptTechByPkid(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipTech_GetByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipTech_GetByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptTech(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipTech_Insert2 xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipTech_Insert2>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptTech(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipTech_DeleteByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipTech_DeleteByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptViMonitoringByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<VIMonitoring_GetByParams xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</VIMonitoring_GetByParams>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptViMonitoringByPkid(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<VIMonitoring_GetByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</VIMonitoring_GetByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptViMonitoring(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<VIMonitoring_Insert2 xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</VIMonitoring_Insert2>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptViMonitoring(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<VIMonitoring_DeleteByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</VIMonitoring_DeleteByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getSptsEqptByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<SPTSEquipment_GetByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</SPTSEquipment_GetByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getSptsEqptByParamMib(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<SPTSEquipment_GetByParam_MIB xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</SPTSEquipment_GetByParam_MIB>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptByRelTestGroup(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_GetByRelTestGroup xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_GetByRelTestGroup>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptSlotByEqptPkid(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentSlot_GetByEquipmentPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentSlot_GetByEquipmentPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptbyParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_GetByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_GetByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptslot(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentSlot_Add xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentSlot_Add>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptSlot(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentSlot_Delete xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentSlot_Delete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqptTray(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentTray_Add xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentTray_Add>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateEqptTray(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentTray_Update xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentTray_Update>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqptTray(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentTray_Delete xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentTray_Delete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptTrayByEqptPkid(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<EquipmentTray_GetByEquipmentPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</EquipmentTray_GetByEquipmentPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertEqpt(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_Add xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_Add>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String updateEqpt(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_Update xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_Update>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteEqpt(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_Delete xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_Delete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptByEqptId(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_GetByEquipmentID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_GetByEquipmentID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getEqptByPkid(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Equipment_GetByPKID xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</Equipment_GetByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertGlobalRelTestGroup(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertGlobalRelTestGroup xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertGlobalRelTestGroup>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteGlobalRelTestGroup(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteGlobalRelTestGroup xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteGlobalRelTestGroup>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getGlobalRelTestGroupAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetGlobalRelTestGroupAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getGlobalRelTestGroupByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetGlobalRelTestGroupDetailsByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetGlobalRelTestGroupDetailsByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String insertGlobalFamilyName(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<InsertGlobalEquipmentFamilyName xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</InsertGlobalEquipmentFamilyName>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String deleteGlobalFamilyName(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<DeleteGlobalEquipmentFamilyName xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</DeleteGlobalEquipmentFamilyName>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getGlobalFamilyNameAll() throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetGlobalEquipmentFamilyNameAll xmlns=\"http://tempuri.org/\" />"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getGlobalFamilyNameByParam(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetGlobalEquipmentFamilyNameDetailsByParam xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetGlobalEquipmentFamilyNameDetailsByParam>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getBookingDetailByPKID(int pkID) throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<BookingDetail_GetByBookingPKID  xmlns=\"http://tempuri.org/\">"
                + "<bookingPKID>" + pkID + "</bookingPKID>"
                + "</BookingDetail_GetByBookingPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getBookedEquipment(JSONObject params) throws IOException {
        String paramsXmlString = "";
        for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = params.get(key).toString();
            paramsXmlString += "<" + key + ">" + value + "</" + key + ">";
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetBookedEquipment xmlns=\"http://tempuri.org/\">"
                + paramsXmlString
                + "</GetBookedEquipment>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getBookedEqptFOLFiles(boolean noFtp) throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<GetBookedEquipmentFOLFiles  xmlns=\"http://tempuri.org/\">"
                + "<noFTP>" + noFtp + "</noFTP>"
                + "</GetBookedEquipmentFOLFiles>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

    public static String getBookingByPKID(int pkID) throws IOException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<Booking_GetByPKID xmlns=\"http://tempuri.org/\">"
                + "<pkid>" + pkID + "</pkid>"
                + "</Booking_GetByPKID>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        return xmlString;
    }

}
