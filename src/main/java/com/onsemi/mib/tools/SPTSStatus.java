package com.onsemi.mib.tools;

public class SPTSStatus {

    // to assign status from SPTS hardwarestatus
    public String sptsStatus(int status) {

        String sptsStatus = "";

        switch (status) {
            case -1:
                sptsStatus = "Scrapped";
                break;
            case 0:
                sptsStatus = "No Stock";
                break;
            case 1:
                sptsStatus = "Good";
                break;
            case 2:
                sptsStatus = "Production";
                break;
            case 3:
                sptsStatus = "Repair";
                break;
            case 4:
                sptsStatus = "Others";
                break;
            case 5:
                sptsStatus = "Quarantine";
                break;
            case 6:
                sptsStatus = "External Cleaning";
                break;
            case 7:
                sptsStatus = "External Re-cleaning";
                break;
            case 8:
                sptsStatus = "Internal Cleaning";
                break;
            case 9:
                sptsStatus = "Internal Re-cleaning";
                break;
            case 10:
                sptsStatus = "Storage Factory";
                break;
            case 11:
                sptsStatus = "Shipped to Other ON Semi";
                break;
            case 12:
                sptsStatus = "Shipped to Vendor";
                break;
            case 13:
                sptsStatus = "Out for Production Staging";
                break;
            default:
                sptsStatus = "Available";
                break;
        }

        return sptsStatus;
    }
}
