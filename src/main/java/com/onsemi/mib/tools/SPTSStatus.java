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

    public String sptsTransType(int transType) {

//        TransType:- 1 = In, 2 = Out for Production, 3 = Out for Repairing, 4 = Out for Other Reason, 5 = Out for Adjustment, 6 = Production Return, 7 = Repairing Return, 8 = Others Return
//	, 9 = Out For Quarantine, 10 = Out For External Cleaning, 11 = Out For External Re-Cleaning, 12 = Out For Internal Cleaning, 13 = Out For Internal Re-Cleaning
//	, 14 = Return From Quarantine, 15 = Return From External Cleaning, 16 = Return From External Re-Cleaning, 17 = Return From Internal Cleaning, 18 = Return From Internal Re-Cleaning
//	, 19 = Out for Storage Factory, 20 = Return From Storage Factory, 21 = Shipped to Other ON Semi Site, 22 = Return From Other ON Semi Site, 23 = Shipped to Vendor, 24 = Return From Vendor
//	, 25 = Out_For_Production_Staging, 26 = Return_From_Production_Staging, 27 = Out_For_Production_From_Staging, 28 = Return_From_Production_To_Staging
        String sptsStatus = "";

        switch (transType) {
            case 1:
                sptsStatus = "In";
                break;
            case 2:
                sptsStatus = "Out for Production";
                break;
            case 3:
                sptsStatus = "Out for Repairing";
                break;
            case 4:
                sptsStatus = "Out for Other Reason";
                break;
            case 5:
                sptsStatus = "Out for Adjustment";
                break;
            case 6:
                sptsStatus = "Production Return";
                break;
            case 7:
                sptsStatus = "Repairing Return";
                break;
            case 8:
                sptsStatus = "Others Return";
                break;
            case 9:
                sptsStatus = "Out for Quarantine";
                break;
            case 10:
                sptsStatus = "Out for External Cleaning";
                break;
            case 11:
                sptsStatus = "Out for External Re-cleaning";
                break;
            case 12:
                sptsStatus = "Out for Internal Cleaning";
                break;
            case 13:
                sptsStatus = "Out for Internal Re-cleaning";
                break;
            case 14:
                sptsStatus = "Return From Quarantine";
                break;
            case 15:
                sptsStatus = "Return From External Cleaning";
                break;
            case 16:
                sptsStatus = "Return From External Re-Cleaning";
                break;
            case 17:
                sptsStatus = "Return From Internal Cleaning";
                break;
            case 18:
                sptsStatus = "Return From Internal Re-Cleaning";
                break;
            case 19:
                sptsStatus = "Out for Storage Factory";
                break;
            case 20:
                sptsStatus = "Return From Storage Factory";
                break;
            case 21:
                sptsStatus = "Shipped to Other ON Semi Site";
                break;
            case 22:
                sptsStatus = "Return From Other ON Semi Site";
                break;
            case 23:
                sptsStatus = "Shipped to Vendor";
                break;
            case 24:
                sptsStatus = "Return From Vendor";
                break;
            case 25:
                sptsStatus = "Out for Production Staging";
                break;
            case 26:
                sptsStatus = "Return from Production Staging";
                break;
            case 27:
                sptsStatus = "Out for Production from Staging";
                break;
            case 28:
                sptsStatus = "Retur from Production to Staging";
                break;
            default:
                sptsStatus = "Good";
                break;
        }

        return sptsStatus;
    }
}
