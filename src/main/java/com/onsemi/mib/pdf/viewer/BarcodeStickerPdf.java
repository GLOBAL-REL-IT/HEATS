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
import com.onsemi.mib.model.Request;

public class BarcodeStickerPdf extends AbstractITextPdfViewPotraitBarcodeSticker {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Request req = (Request) model.get("request");

        Integer cellPadding = 4;

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{8.6f, 2.4f});
        table.setSpacingBefore(15);

        Font fontHeader = fontOpenSans(6f, Font.BOLD);
        Font fontsmall = fontOpenSans(5f, Font.BOLD);
//        fontHeader.setColor(BaseColor.WHITE);

        PdfPCell cellHeader = new PdfPCell();
//        cellHeader.setBackgroundColor(BaseColor.DARK_GRAY);
        cellHeader.setPadding(cellPadding);
        cellHeader.setBorder(Rectangle.NO_BORDER);
//        cellHeader.setPaddingLeft(60.0f);//jarak dari kiri
        cellHeader.setPaddingLeft(60.0f);//jarak dari kiri
//        cellHeader.setPaddingLeft(120.0f);//jarak dari kiri

        Font fontContent = fontOpenSans();

        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);
        cellContent.setBorder(Rectangle.NO_BORDER);
        cellContent.setPaddingLeft(120.0f);

        PdfContentByte cb = writer.getDirectContent();
        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setFont(null);
//        code128.setCode(whShipping.getMpNo());
        code128.setCode(req.getRmsLotEvent());
        code128.setSize(cellPadding);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        PdfPCell barcode = new PdfPCell(code128Image);
        barcode.setBorder(Rectangle.NO_BORDER);
//        barcode.setPaddingLeft(60.0f); //jarak dari kiri
        barcode.setPaddingLeft(60.0f); //jarak dari kiri
        barcode.setPaddingTop(0f);

//        whShipping = (WhShipping) model.get("whShipping");
//        req = (Request) model.get("request");
        table.addCell(barcode);
        cellContent.setPhrase(new Phrase("", fontHeader));
        table.addCell(cellContent);

//        cellHeader.setPhrase(new Phrase("MP No: " + whShipping.getMpNo(), fontHeader));
        cellHeader.setPhrase(new Phrase("RMS Lot Event.: " + req.getRmsLotEvent(), fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase("", fontHeader));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Package: " + req.getPkgName(), fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase("", fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Quantity: " + req.getFinalQty(), fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase("", fontContent));
        table.addCell(cellContent);

        cellHeader.setPhrase(new Phrase("Scrap Date: " + req.getMthToScrap(), fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase("", fontContent));
        table.addCell(cellContent);

        if (req.getStressTypeMidPoint() != null) {
            cellHeader.setPhrase(new Phrase("Stress Type Mid-point: " + req.getStressTypeMidPoint(), fontHeader));
            table.addCell(cellHeader);
            cellContent.setPhrase(new Phrase("", fontContent));
            table.addCell(cellContent);
        }

        cellHeader.setPhrase(new Phrase("Location: " + req.getShelf(), fontHeader));
        table.addCell(cellHeader);
        cellContent.setPhrase(new Phrase("", fontContent));
        table.addCell(cellContent);

        doc.add(table);

    }
}
