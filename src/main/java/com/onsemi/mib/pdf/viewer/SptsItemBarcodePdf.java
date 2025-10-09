package com.onsemi.mib.pdf.viewer;

//package com.onsemi.hms.pdf.viewer;
//
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
//import com.onsemi.mib.model.HWRequest;
import com.onsemi.mib.pdf.AbstractITextPdfViewPotraitBarcodeOuter;

public class SptsItemBarcodePdf extends AbstractITextPdfViewPotraitBarcodeOuter {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        HWRequest hwreq = (HWRequest) model.get("hwreq");
//        String reqId = hwreq.getId();
//        String boxId = hwreq.getBoxId();
//        String itemType = hwreq.getItemType();
//        String itemID = hwreq.getItemId();
//        String itemQty = hwreq.getTotalQty();
//        String reqDate = hwreq.getReqDate();
//        String pcbBId = hwreq.getPcbBId();
//        String pcbBQty = hwreq.getPcbBQty();
//        String pcbCId = hwreq.getPcbCId();
//        String pcbCQty = hwreq.getPcbCQty();
//        String pcbCtrId = hwreq.getPcbCtrId();
//        String pcbCtrQty = hwreq.getPcbCtrQty();
//        String lcId = hwreq.getLcId();
//        String lcQty = hwreq.getLcQty();
//        String pcId = hwreq.getPcId();
//        String pcQty = hwreq.getPcQty();

        Integer cellPadding = 4;

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{3.0f});
        table.setSpacingBefore(15);
        
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
        cellContent.setPaddingLeft(20.0f);
//        cellContent.setPaddingRight(45.0f);
        cellContent.setPaddingRight(55.0f);
//        cellContent.setColspan(2);
        
        PdfContentByte cb = writer.getDirectContent();
        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setFont(null);
        code128.setCode(null);
        code128.setSize(cellPadding);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        PdfPCell barcode = new PdfPCell(code128Image);
        barcode.setBorder(Rectangle.NO_BORDER);
//        barcode.setPaddingLeft(60.0f); //jarak dari kiri
        barcode.setPaddingLeft(60.0f); //jarak dari kiri
        barcode.setPaddingTop(0f);
//        
//        table.addCell(barcode);
//        cellContent.setPhrase(new Phrase("", fontBcode));
//        
//        table.addCell(cellContent);
//
//        cellHeader.setPhrase(new Phrase("Box ID: " + boxId, fontOuterId));
//        table.addCell(cellHeader);
//        cellContent.setPhrase(new Phrase("", fontOuterId));
//        table.addCell(cellContent);
//        table.addCell(cellContent);
//        cellHeader.setPhrase(new Phrase("Item Type: " + itemType, fontOuterId));
//        table.addCell(cellHeader);
//        cellContent.setPhrase(new Phrase("", fontOuterId));
//        table.addCell(cellContent);
//        
//        String items = itemID + " (Qty: " + itemQty + ")";
//        cellContent.setPhrase(new Phrase(items, fontsmall));
//        table.addCell(cellContent);
//        
//        if(pcbBId != null) {
//            items = pcbBId + " (Qty: " + pcbBQty + " )";
//            cellContent.setPhrase(new Phrase(items, fontsmall));
//            table.addCell(cellContent);
//        }
//        if(pcbCId != null) {
//            items = pcbCId + " (Qty: " + pcbCQty + " )";
//            cellContent.setPhrase(new Phrase(items, fontsmall));
//            table.addCell(cellContent);
//        }
//        if(pcbCtrId != null) {
//            items = pcbCtrId + " (Qty: " + pcbCtrQty + " )";
//            cellContent.setPhrase(new Phrase(items, fontsmall));
//            table.addCell(cellContent);
//        }
//        if(lcId != null) {
//            items = lcId + " (Qty: " + lcQty + " )";
//            cellContent.setPhrase(new Phrase(items, fontsmall));
//            table.addCell(cellContent);
//        }
//        if(pcId != null) {
//            items = pcId + " (" + pcQty + " )";
//            cellContent.setPhrase(new Phrase(items, fontsmall));
//            table.addCell(cellContent);
//        }
//
//        cellHeader.setPhrase(new Phrase("Request Date: " + reqDate, fontMthScrap));
        cellHeader.setPhrase(new Phrase("Request Date: ", fontMthScrap));
        table.addCell(cellHeader);
        
        doc.add(table);
    }
}
