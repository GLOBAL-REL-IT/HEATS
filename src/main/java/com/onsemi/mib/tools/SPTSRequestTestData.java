/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.tools;

import java.io.IOException;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author zbqb9x
 */
public class SPTSRequestTestData {

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("TEST THIS FUNCTION TO TEST FOR API getHardwareIdConfigByPKID ...");
        JSONObject param = new JSONObject();
        JSONObject paramV = new JSONObject();
        // THIS ONE IS THE DATA WE WANT TO SEND TO SPTS API

        // FUNCTION TO CHECK THE SELECT FUNCTION
//        paramV.put("pkid", "2");
//        JSONArray getItemByParamV = SPTSWebService.getHardwareIdConfigByPKID(paramV);
//        for (int i = 0; i < getItemByParamV.length(); i++) {
//            System.out.println(getItemByParamV.getJSONObject(i));
//        }
//        System.out.println("COUNT getHardwareIdConfigByPKID DATA ..." + getItemByParamV.length());

        // THIS ONE TO CHECK THE DELETE FUNCTION
        paramV.put("pkid", "5");
        SPTSResponse delete = SPTSWebService.DeleteSFItem(paramV);
        if (delete.getStatus()) {
            System.out.println("Delete Success: 4");
        } else {
            System.out.println("Delete Failed: 4");
        }
    }

}