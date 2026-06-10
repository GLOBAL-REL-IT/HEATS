/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.pdf.viewer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingHardwareDAO;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingHardware;
import com.onsemi.mib.pdf.AbstractITextPdfViewPotrait;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zbqb9x
 */
public class RmsPdf extends AbstractITextPdfViewPotrait {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsPdf.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

//        WhWipGts whWipGts = (WhWipGts) model.get("whWipGtsDetail");
        RmsBookingDetail rmsDetail = (RmsBookingDetail) model.get("rmsBookingDetail");

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        Date date = new Date();
        String todayDate = dateFormat.format(date);
        Paragraph viewTitle2 = new Paragraph("Shipment Date : " + "HEWHEEHEHHEHEEHEH", fontOpenSans(6f, Font.NORMAL));
//        viewTitle2.setAlignment(Element.ALIGN_RIGHT);
        viewTitle2.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle2);

        String GtsNo = "GTS NO: " + "UHUHUHUHUU";
        Paragraph viewTitle3 = new Paragraph(GtsNo, fontOpenSans(8f, Font.BOLD));
//        viewTitle3.setAlignment(Element.ALIGN_RIGHT);
        viewTitle3.setAlignment(Element.ALIGN_LEFT);
        doc.add(viewTitle3);

        PdfContentByte cb = writer.getDirectContent();
        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setFont(null);
        code128.setCode("JNSDINAIOWNDIOW");
//        code128.setSize(cellPadding);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
//        code128Image.setRight(100f);

//        barcode.setRight(0);
        doc.add(code128Image);

        String title = "\nHIMS RL Shipping List (Rel Lab to SBN Factory) - WIP Shipment [Stress WIP]";
        Paragraph viewTitle = new Paragraph(title, fontOpenSans(10f, Font.BOLD));
        viewTitle.setAlignment(Element.ALIGN_CENTER);
        doc.add(viewTitle);

        Integer cellPadding = 5;

//        PdfPTable table = new PdfPTable(6);
//        table.setWidthPercentage(100.0f);
//        table.setWidths(new float[]{0.5f, 1.5f, 2.0f, 1.5f, 3.5f, 0.5f});
//        table.setSpacingBefore(20);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100.0f);
//        table.setWidths(new float[]{0.5f, 1.5f, 1.3f, 1.3f, 2.7f , 1.0f, 0.8f, 1.0f, 0.5f});
        table.setWidths(new float[]{0.5f, 3.5f, 2.5f, 2.5f});
        table.setSpacingBefore(20);

        Font fontHeader = fontOpenSans(6.5f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);
        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent = fontOpenSans(6.5f, Font.NORMAL);
        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);
        
        RmsBookingDetailDAO detaildao = new RmsBookingDetailDAO();
        String bookid = detaildao.getBookingId(rmsDetail.getId());

        RmsBookingHardwareDAO bookdao = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> list = bookdao.getRmsHardwareList(bookid);


//        List<WhWipShipment> whWipShipmentList = (List<WhWipShipment>) model.get("whWipShipmentList");

        int i = 0;
        while (i < list.size()) {
            if (i == 0) {
                //Header
                cellHeader.setPhrase(new Phrase("No", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("RMS Event", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Interval", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Quantity", fontHeader));
                table.addCell(cellHeader);
            }
            cellContent.setPhrase(new Phrase(i + 1 + "", fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(list.get(i).getBookingPkid(), fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(list.get(i).getItemId(), fontContent));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase(list.get(i).getQty(), fontContent));
            table.addCell(cellContent);
            i++;
        }
        doc.add(table);

        doc.add(Chunk.NEWLINE);

        PdfPTable blank = new PdfPTable(1);
        blank.setWidths(new float[]{10.0f});
        blank.setTotalWidth(527);
        blank.setLockedWidth(true);

        PdfPCell blankcell = new PdfPCell();
        blankcell.setPhrase(new Phrase("\n", fontContent));
        blankcell.setFixedHeight(25);
        blankcell.setPaddingBottom(5);
        blankcell.setPaddingLeft(5);
        blankcell.setBorder(Rectangle.NO_BORDER);
        blank.addCell(blankcell);
        doc.add(blank);
        doc.add(blank);

        PdfPTable box = new PdfPTable(3);
        box.setWidths(new float[]{4.3f, 2.0f, 20.0f});
        box.setTotalWidth(527);
        box.setLockedWidth(true);

        PdfPCell boxHeader = new PdfPCell();
        boxHeader.setPhrase(new Phrase("BOX QUANTITY:", fontOpenSans(9f, Font.NORMAL)));
        boxHeader.setFixedHeight(20);
        boxHeader.setPaddingBottom(5);
        boxHeader.setPaddingTop(5);
        boxHeader.setPaddingLeft(5);
        boxHeader.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxHeader);

        PdfPCell boxCell = new PdfPCell();
//        boxHeader.setPhrase(new Phrase("\n", fontContent));
        boxHeader.setFixedHeight(19);
        boxHeader.setPaddingBottom(8);
        boxHeader.setPaddingRight(100);
//        boxCell.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxCell);

        PdfPCell boxCellBlank = new PdfPCell();
        boxCellBlank.setPhrase(new Phrase("*REMARKS: BOX QTY MUST MATCH GTS QTY:", fontOpenSans(9f, Font.NORMAL)));
        boxCellBlank.setFixedHeight(20);
        boxCellBlank.setPaddingBottom(5);
        boxCellBlank.setPaddingTop(5);
        boxCellBlank.setPaddingLeft(5);
        boxCellBlank.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxCellBlank);

        doc.add(box);

        doc.add(blank);

        PdfPTable sign = new PdfPTable(3);
        sign.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        sign.setTotalWidth(527);
        sign.setLockedWidth(true);

        PdfPCell sign2 = new PdfPCell();
        sign2.setPhrase(new Phrase("SECURITY STAFF :\n\n\n\nREL LAB STAFF :", fontContent));
        sign2.setFixedHeight(100);
        sign2.setPaddingBottom(5);
        sign2.setPaddingTop(5);
        sign2.setPaddingLeft(5);
        sign2.setBorder(Rectangle.NO_BORDER);
        sign.addCell(sign2);

        PdfPCell cellBlank4 = new PdfPCell();
        cellBlank4.setFixedHeight(100);
        cellBlank4.setPaddingBottom(5);
        cellBlank4.setBorder(Rectangle.NO_BORDER);
        sign.addCell(cellBlank4);

        PdfPCell cellDriver = new PdfPCell();
        cellDriver.setPhrase(new Phrase("DRIVER:\nI/C NO: _________________________________\n\nSEAL NO :_______________________________\nTRUCK/LORRY NO: _____________________\n"
                + "TIME DEPARTURE: ______________________", fontContent));
        cellDriver.setLeading(1.4f, 1.4f);
        cellDriver.setFixedHeight(100);
        cellDriver.setPaddingBottom(5);
        cellDriver.setPaddingTop(5);
        cellDriver.setPaddingLeft(10);
        cellDriver.setBorder(Rectangle.NO_BORDER);
        sign.addCell(cellDriver);

        doc.add(sign);
    }
}
