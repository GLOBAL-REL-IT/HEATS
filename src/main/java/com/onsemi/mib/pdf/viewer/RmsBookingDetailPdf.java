/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.onsemi.mib.pdf.viewer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.mib.dao.RmsBookingDetailDAO;
import com.onsemi.mib.dao.RmsBookingHardwareDAO;
import com.onsemi.mib.model.RmsBookingDetail;
import com.onsemi.mib.model.RmsBookingHardware;
import com.onsemi.mib.pdf.AbstractITextPdfViewPotrait;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.supercsv.cellprocessor.Optional;

/**
 *
 * @author zbqb9x
 */
public class RmsBookingDetailPdf extends AbstractITextPdfViewPotrait {

    private static final Logger LOGGER = LoggerFactory.getLogger(RmsBookingDetailPdf.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        RmsBookingDetail rmsDetail = (RmsBookingDetail) model.get("rmsBookingDetail");

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String tajukReport = "Hardware Preparation For Loading";
//        String linkUtama = baseUrl + "/rmsbookingDetail/detail/" + rmsDetail.getId();
        String linkUtama = "/detail/" + rmsDetail.getId();

        RmsBookingDetailDAO detaildao = new RmsBookingDetailDAO();
        String bookid = detaildao.getBookingId(rmsDetail.getId());
        String rms = rmsDetail.getRmsNo();
        String event = rmsDetail.getEvent();
        String rms_event = rms + "_" + event;
        String device = rmsDetail.getDevice();
        String pakej = rmsDetail.getPackages();
        String remark = "";

        RmsBookingHardwareDAO rmsHD = new RmsBookingHardwareDAO();
        RmsBookingHardware rmsRemarks = rmsHD.getRmsBookingHardwareRemarksByBookingPkid(bookid);
        
        if (rmsRemarks == null) {
            remark = "-";
        } else {
            remark = rmsRemarks.getItemId();
        }

        // DEFINE ALL THE FONT HERE - START
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.BLACK);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.DARK_GRAY);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.DARK_GRAY);
        Font qrLabelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, BaseColor.BLACK);
        // DEFINE ALL THE FONT HERE - END

        // 001 HEADER TABLE (TITLE + QR) 
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{85, 15});

        // 001-001 LEFT: Title
        PdfPCell titleCell = new PdfPCell(new Phrase(tajukReport, titleFont));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(titleCell);

        // 001-002 RIGHT: QR CODE
        String qrText = linkUtama;

        // Create QR label
        PdfPCell labelCell = new PdfPCell(new Phrase(rms_event, qrLabelFont));
        labelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(2);

        // QR image
//        BarcodeQRCode qrCode = new BarcodeQRCode(qrText, 222, 222, null);
//        Image qrImage = qrCode.getImage();
//        qrImage.scaleAbsolute(40, 40);
        
        BarcodeQRCode qrCode = new BarcodeQRCode(qrText, 300, 300, null);
        Image qrImage = qrCode.getImage();
        qrImage.scaleToFit(40, 40);

        PdfPCell imageCell = new PdfPCell(qrImage, false);
        imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        imageCell.setBorder(Rectangle.NO_BORDER);

        // Wrap label + QR inside a small table
        PdfPTable qrWrapper = new PdfPTable(1);
        qrWrapper.setWidthPercentage(100);
        qrWrapper.addCell(labelCell);
        qrWrapper.addCell(imageCell);

        // Put wrapper into your main cell
        PdfPCell qrCell = new PdfPCell(qrWrapper);
        qrCell.setBorder(Rectangle.NO_BORDER);
        qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        headerTable.addCell(qrCell);

        PdfPCell labelCell2 = new PdfPCell(new Phrase("RMS_EVENT", qrLabelFont));
        labelCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        labelCell2.setBorder(Rectangle.NO_BORDER);
        labelCell2.setPaddingBottom(1);

        PdfPCell imgCell2 = new PdfPCell(qrImage, false);
        imgCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        imgCell2.setBorder(Rectangle.NO_BORDER);

        PdfPTable qrWrap2 = new PdfPTable(1);
        qrWrap2.setWidthPercentage(100);
        qrWrap2.addCell(labelCell2);
        qrWrap2.addCell(imgCell2);

        qrCell = new PdfPCell(qrWrap2);
        qrCell.setBorderColor(BaseColor.GRAY);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);

        // 002 ===== MAIN GRID =====
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5);
        table.setWidths(new float[]{20, 20, 25, 35});

        table.addCell(createField("RMS Event", rms_event, labelFont, valueFont));
        table.addCell(createField("Device", device, labelFont, valueFont));
        table.addCell(createField("Package", pakej, labelFont, valueFont));
        table.addCell(createTextArea("Booking Remarks", remark, labelFont, valueFont));

        for (int i = 0; i < 3; i++) {
            PdfPCell empty = new PdfPCell();
            empty.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty);
        }
        document.add(table);

        // 003 ===== MAIN CONTAINER (40% / 60%) =====
        PdfPTable mainTable = new PdfPTable(1);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new float[]{100}); // key requirement

        // 003-001 LEFT TABLE (Support Item)
        PdfPTable leftTable = new PdfPTable(4);
        leftTable.setWidthPercentage(100);
        leftTable.setWidths(new float[]{5, 30, 55, 10});

        // TABLE TITLE
        PdfPCell leftTitle = new PdfPCell(new Phrase("Support Item", headerFont));
        leftTitle.setColspan(4);
        leftTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
        leftTitle.setBorder(Rectangle.NO_BORDER);
        leftTitle.setPaddingBottom(5);
        leftTable.addCell(leftTitle);

        // TABLE Header Row
//        leftTable.addCell(new PdfPCell(new Phrase("No", headerFont)));
        PdfPCell noCell = new PdfPCell(new Phrase("No", headerFont));
        noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        noCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leftTable.addCell(noCell);
        leftTable.addCell(new PdfPCell(new Phrase("Item Type", headerFont)));
        leftTable.addCell(new PdfPCell(new Phrase("Item ID", headerFont)));
        PdfPCell qtyCell = new PdfPCell(new Phrase("Qty", headerFont));
        qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        qtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        leftTable.addCell(qtyCell);

        rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> otherList = rmsHD.getRmsBookingHardwareListForOtherHwByBookingPkid(bookid);

        int counter = 1;
        for (RmsBookingHardware data : otherList) {
            String checkFlag = data.getFlag();
            if (checkFlag.equalsIgnoreCase("99")) {
                // MANA YANG FLAG 99, KITA SKIP
            } else {
                PdfPCell cell01 = new PdfPCell(new Phrase(Integer.toString(counter), valueFont));
                cell01.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell01.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell01.setBorderColor(BaseColor.GRAY);
                leftTable.addCell(cell01);

                PdfPCell cell02 = new PdfPCell(new Phrase(data.getItemType(), valueFont));
                cell02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell02.setBorderColor(BaseColor.GRAY);
                leftTable.addCell(cell02);

                PdfPCell cell03 = new PdfPCell(new Phrase(data.getItemId(), valueFont));
                cell03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell03.setBorderColor(BaseColor.GRAY);
                leftTable.addCell(cell03);

                PdfPCell cell04 = new PdfPCell(new Phrase(data.getQty(), valueFont));
                cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell04.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell04.setBorderColor(BaseColor.GRAY);
                leftTable.addCell(cell04);

                counter++;
            }
        }

        PdfPCell leftWrapper = new PdfPCell(leftTable);
        leftWrapper.setPadding(5);
        leftWrapper.setBorder(Rectangle.NO_BORDER);
        mainTable.addCell(leftWrapper);

        // 003-002 RIGHT TABLE (Motherboard)
        PdfPTable rightTable = new PdfPTable(6);
        rightTable.setWidthPercentage(100);
        rightTable.setWidths(new float[]{5, 20, 46, 10, 10, 9});

        // Title
        PdfPCell rightTitle = new PdfPCell(new Phrase("Motherboard", headerFont));
        rightTitle.setColspan(6);
        rightTitle.setBorder(Rectangle.NO_BORDER);
        rightTitle.setPaddingBottom(5);
        rightTable.addCell(rightTitle);

        // Header Row
        PdfPCell noCell2 = new PdfPCell(new Phrase("No", headerFont));
        noCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightTable.addCell(noCell2);
        rightTable.addCell(new PdfPCell(new Phrase("Item Type", headerFont)));
        rightTable.addCell(new PdfPCell(new Phrase("Item ID", headerFont)));
        PdfPCell noCell5 = new PdfPCell(new Phrase("LC Qty", headerFont));
        noCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightTable.addCell(noCell5);
        PdfPCell noCell6 = new PdfPCell(new Phrase("PC Qty", headerFont));
        noCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightTable.addCell(noCell6);
        PdfPCell qrLabelCell = new PdfPCell(new Phrase("QR", headerFont));
        qrLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightTable.addCell(qrLabelCell);

        rmsHD = new RmsBookingHardwareDAO();
        List<RmsBookingHardware> BibList = rmsHD.getRmsBookingHardwareListForMotherboardByBookingPkid(bookid);

        int counter2 = 1;
        for (RmsBookingHardware data : BibList) {
            String checkFlag = data.getFlag();
            if (checkFlag.equalsIgnoreCase("99")) {
                // MANA YANG FLAG 99, KITA SKIP
            } else {
                PdfPCell count1 = new PdfPCell(new Phrase(Integer.toString(counter2), valueFont));
                count1.setHorizontalAlignment(Element.ALIGN_CENTER);
                count1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                count1.setBorderColor(BaseColor.GRAY);
                rightTable.addCell(count1);

                PdfPCell cellType = new PdfPCell(new Phrase(data.getItemType(), valueFont));
                cellType.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellType.setBorderColor(BaseColor.GRAY);
                rightTable.addCell(cellType);

                PdfPCell cellItemId = new PdfPCell(new Phrase(data.getItemId(), valueFont));
                cellItemId.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellItemId.setBorderColor(BaseColor.GRAY);
                rightTable.addCell(cellItemId);

                PdfPCell qty1 = new PdfPCell(new Phrase(data.getLcQty(), valueFont));
                qty1.setHorizontalAlignment(Element.ALIGN_CENTER);
                qty1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                qty1.setBorderColor(BaseColor.GRAY);
                rightTable.addCell(qty1);
                PdfPCell qty2 = new PdfPCell(new Phrase(data.getPcQty(), valueFont));
                qty2.setHorizontalAlignment(Element.ALIGN_CENTER);
                qty2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                qty2.setBorderColor(BaseColor.GRAY);
                rightTable.addCell(qty2);

                String pkid = data.getPkid();
//                String qrmb = baseUrl + "/rmsbookingDetail/groupDetail/" + bookid + "/" + pkid;
                String qrmb = "/groupDetail/" + bookid + "/" + pkid;
                
                qrCode = new BarcodeQRCode(qrmb, 300, 300, null);
                qrImage = qrCode.getImage();
                qrImage.scaleToFit(440, 40);


                qrCell = new PdfPCell(qrImage, false);
                qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                qrCell.setPadding(1);
                qrCell.setBorderColor(BaseColor.GRAY);
                rightTable.addCell(qrCell);

                counter2++;
            }
        }

        PdfPCell rightWrapper = new PdfPCell(rightTable);
        rightWrapper.setPadding(5);
        rightWrapper.setBorder(Rectangle.NO_BORDER);
        mainTable.addCell(rightWrapper);

        document.add(mainTable);
        document.close();
    }

    private static PdfPCell createField(String label, String value, Font labelFont, Font valueFont) {
        PdfPTable inner = new PdfPTable(1);
        inner.setWidthPercentage(100);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(2);
        inner.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setPadding(6);
        valueCell.setBorderColor(BaseColor.GRAY);
        inner.addCell(valueCell);

        PdfPCell wrapper = new PdfPCell(inner);
        wrapper.setBorder(Rectangle.NO_BORDER);
        wrapper.setPadding(5);

        return wrapper;
    }

    private static PdfPCell createTextArea(String label, String value, Font labelFont, Font valueFont) {
        PdfPTable inner = new PdfPTable(1);

        PdfPCell ll = new PdfPCell(new Phrase(label, labelFont));
        ll.setBorder(Rectangle.NO_BORDER);
        inner.addCell(ll);

        PdfPCell box = new PdfPCell(new Phrase(value, valueFont));
        box.setMinimumHeight(30);
        box.setBackgroundColor(new BaseColor(245, 245, 245));
        box.setBorderColor(BaseColor.GRAY);
        box.setPadding(8);
        inner.addCell(box);

        PdfPCell wrapper = new PdfPCell(inner);
        wrapper.setBorder(Rectangle.NO_BORDER);
        wrapper.setPadding(5);

        return wrapper;
    }

}
