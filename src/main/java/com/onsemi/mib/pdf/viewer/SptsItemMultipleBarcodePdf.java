package com.onsemi.mib.pdf.viewer;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import com.onsemi.hms.model.HWRequest;
import com.onsemi.mib.pdf.AbstractITextPdfViewPotraitBarcodeOuter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SptsItemMultipleBarcodePdf extends AbstractITextPdfViewPotraitBarcodeOuter {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Integer cellPadding = 4;

        Font fontBcode = fontOpenSans(12f, Font.BOLD);
        Font fontOuterId = fontOpenSans(10f, Font.BOLD);
        Font fontMthScrap = fontOpenSans(10f, Font.BOLD);
        Font fontsmall = fontOpenSans(8f, Font.BOLD);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setPadding(cellPadding);
        cellHeader.setBorder(Rectangle.NO_BORDER);
        cellHeader.setPaddingLeft(50.0f);//jarak dari kiri

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);
        cellContent.setBorder(Rectangle.NO_BORDER);
//        cellContent.setPaddingLeft(20.0f);
//        cellContent.setPaddingRight(55.0f);
        cellContent.setPaddingLeft(12.0f);
        cellContent.setPaddingRight(40.0f);

//        List<HWRequest> hwReqList = new ArrayList<HWRequest>();
//        hwReqList = (List<HWRequest>) model.get("hwReqList");
        int x = 0;

//        while(x<hwReqList.size()) {
//            doc.newPage();
//            PdfPTable table = new PdfPTable(1);
//            table.setWidthPercentage(100.0f);
//            table.setWidths(new float[]{3.0f});
//            table.setSpacingBefore(15);
//        
//            String boxId = hwReqList.get(x).getBoxId();
//            String itemType = hwReqList.get(x).getItemType();
//            String itemId = hwReqList.get(x).getItemId();
//            String totalQty = hwReqList.get(x).getTotalQty();
//            String reqDate = hwReqList.get(x).getReqDate();
//            
//            PdfContentByte cb = writer.getDirectContent();
//            Barcode128 code128 = new Barcode128();
//            code128.setGenerateChecksum(true);
//            code128.setFont(null);
//            code128.setCode(boxId);
//            code128.setSize(cellPadding);
//            Image code128Image = code128.createImageWithBarcode(cb, null, null);
//            PdfPCell barcode = new PdfPCell(code128Image);
//            barcode.setBorder(Rectangle.NO_BORDER);
//            barcode.setPaddingLeft(60.0f); //jarak dari kiri
//            barcode.setPaddingTop(0f);
//
//            table.addCell(barcode);
//            cellContent.setPhrase(new Phrase("", fontBcode));
//
//            table.addCell(cellContent);
//
//            cellHeader.setPhrase(new Phrase("Box ID: " + boxId, fontOuterId));
//            table.addCell(cellHeader);
//            cellContent.setPhrase(new Phrase("", fontOuterId));
//            table.addCell(cellContent);
//            table.addCell(cellContent);
//            cellHeader.setPhrase(new Phrase("Item Type: " + itemType, fontOuterId));
//            table.addCell(cellHeader);
//            cellContent.setPhrase(new Phrase("", fontOuterId));
//            table.addCell(cellContent);
//
//            String items = itemId + " (Qty: " + totalQty + ")";
//            cellContent.setPhrase(new Phrase(items, fontsmall));
//            table.addCell(cellContent);
//
////            if(pcbBId != null) {
////                items = pcbBId + " (Qty: " + pcbBQty + " )";
////                cellContent.setPhrase(new Phrase(items, fontsmall));
////                table.addCell(cellContent);
////            }
////            if(pcbCId != null) {
////                items = pcbCId + " (Qty: " + pcbCQty + " )";
////                cellContent.setPhrase(new Phrase(items, fontsmall));
////                table.addCell(cellContent);
////            }
////            if(pcbCtrId != null) {
////                items = pcbCtrId + " (Qty: " + pcbCtrQty + " )";
////                cellContent.setPhrase(new Phrase(items, fontsmall));
////                table.addCell(cellContent);
////            }
////            if(lcId != null) {
////                items = lcId + " (Qty: " + lcQty + " )";
////                cellContent.setPhrase(new Phrase(items, fontsmall));
////                table.addCell(cellContent);
////            }
////            if(pcId != null) {
////                items = pcId + " (" + pcQty + " )";
////                cellContent.setPhrase(new Phrase(items, fontsmall));
////                table.addCell(cellContent);
////            }
//
////            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
////            String nowDate = df.format(date);
////            Date dateReq = new SimpleDateFormat("dd MMM yyyy").parse(reqDate);
//            Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(reqDate);
//            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
//            String dateReq = formatter.format(date1);
//
//            cellHeader.setPhrase(new Phrase("Request Date: " + dateReq, fontMthScrap));
//            table.addCell(cellHeader);
//            doc.add(table);
//            x++;
//        }
    }

}