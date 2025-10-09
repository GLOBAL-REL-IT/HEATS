package com.onsemi.mib.pdf.viewer;

import com.itextpdf.text.BaseColor;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsemi.mib.model.DOList;
import com.onsemi.mib.pdf.AbstractITextPdfViewDoList;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoListPdf extends AbstractITextPdfViewDoList {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoListPdf.class);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

//        LOGGER.info("masuk pdf...");
        Font fontContent = fontOpenSans();

        PdfPTable tableHeader = new PdfPTable(3);
        tableHeader.setWidths(new float[]{4.0f, 15.0f, 5.0f});
        tableHeader.setTotalWidth(527);
        tableHeader.setLockedWidth(true);

        PdfPCell cellBlank = new PdfPCell();
        cellBlank.setPhrase(new Phrase("Rel Lab Copy", fontOpenSans(7f, Font.BOLD)));
        cellBlank.setFixedHeight(40);
        cellBlank.setPaddingBottom(5);
        cellBlank.setPaddingTop(0);
        cellBlank.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellBlank);

        PdfPCell cellTitle = new PdfPCell();
        cellTitle.setPhrase(new Phrase("DO for Sample Retention from Rel Lab to Sendayan", fontOpenSans(12f, Font.BOLD)));
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setFixedHeight(40);
        cellTitle.setPaddingBottom(5);
        cellTitle.setPaddingTop(0);
        cellTitle.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellTitle);

        ServletContext context = getServletContext();
        String file = "/resources/img/logoON.png";
        InputStream is = context.getResourceAsStream(file);
        byte[] bytes = IOUtils.toByteArray(is);
        Image image = Image.getInstance(bytes);

        PdfPCell cellLogo = new PdfPCell(image, true);
        cellLogo.setFixedHeight(45);
        cellLogo.setPaddingBottom(5);
        cellLogo.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellLogo);

        doc.add(tableHeader);

        doc.add(Chunk.NEWLINE);

        PdfPTable address = new PdfPTable(3);
        address.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        address.setTotalWidth(527);
        address.setLockedWidth(true);

        PdfPCell cellShipper = new PdfPCell();
        cellShipper.setPhrase(new Phrase("From :", fontOpenSans(10f, Font.UNDERLINE)));
        cellShipper.setPaddingTop(0);
        cellShipper.setPaddingLeft(5);
        cellShipper.setBorder(Rectangle.NO_BORDER);
        address.addCell(cellShipper);

        PdfPCell cellBlank2 = new PdfPCell();
        cellBlank2.setBorder(Rectangle.NO_BORDER);
        address.addCell(cellBlank2);

        PdfPCell cellConsignee = new PdfPCell();
        cellConsignee.setPhrase(new Phrase("To :", fontOpenSans(10f, Font.UNDERLINE)));
        cellConsignee.setPaddingTop(0);
        cellConsignee.setPaddingLeft(10);
        cellConsignee.setBorder(Rectangle.NO_BORDER);
        address.addCell(cellConsignee);

        doc.add(address);

        doc.add(Chunk.NEWLINE);

        PdfPTable address2 = new PdfPTable(3);
        address2.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        address2.setTotalWidth(527);
        address2.setLockedWidth(true);

        PdfPCell cellShipper2 = new PdfPCell();
        cellShipper2.setPhrase(new Phrase("ON SEMICONDUCTOR MALAYSIA SDN BHD\nLot 55 Senawang Industrial Estate\n70450 Seremban\nNegeri Sembilan", fontContent));
        cellShipper2.setLeading(1.1f, 1.1f);
        cellShipper2.setFixedHeight(65);
        cellShipper2.setPaddingBottom(5);
        cellShipper2.setPaddingTop(5);
        cellShipper2.setPaddingLeft(5);
        cellShipper2.setBorder(Rectangle.NO_BORDER);
        address2.addCell(cellShipper2);

        PdfPCell cellBlank3 = new PdfPCell();
        cellBlank3.setFixedHeight(65);
        cellBlank3.setPaddingBottom(5);
        cellBlank3.setBorder(Rectangle.NO_BORDER);
        address2.addCell(cellBlank3);

        PdfPCell cellConsignee2 = new PdfPCell();
        cellConsignee2.setPhrase(new Phrase("SECURIFORCE LOGISTIC SDN. BHD.\nPt 6136 & Pt 12681, Jalan Tech Valley 1\nSendayan Tech Valley, "
                + "Bandar Sri Sendayan\n71950 SEREMBAN\nNEGERI SEMBILAN", fontContent));
        cellConsignee2.setLeading(1.1f, 1.1f);
        cellConsignee2.setFixedHeight(65);
        cellConsignee2.setPaddingBottom(5);
        cellConsignee2.setPaddingTop(5);
        cellConsignee2.setPaddingLeft(10);
        cellConsignee2.setBorder(Rectangle.NO_BORDER);
        address2.addCell(cellConsignee2);

        doc.add(address2);

        doc.add(Chunk.NEWLINE);

        List<DOList> doList = (List<DOList>) model.get("doList");

        PdfPTable gts = new PdfPTable(3);
        gts.setWidths(new float[]{10.0f, 4.0f, 10.0f});
        gts.setTotalWidth(527);
        gts.setLockedWidth(true);

        PdfPCell cellShipper3 = new PdfPCell();
        cellShipper3.setPhrase(new Phrase("GTS Number : " + doList.get(0).getGtsNo(), fontContent));
        cellShipper3.setLeading(1.1f, 1.1f);
        cellShipper3.setFixedHeight(20);
        cellShipper3.setPaddingBottom(2);
        cellShipper3.setPaddingTop(5);
        cellShipper3.setPaddingLeft(5);
        cellShipper3.setBorder(Rectangle.NO_BORDER);
        gts.addCell(cellShipper3);

        PdfPCell cellBlank100 = new PdfPCell();
        cellBlank100.setFixedHeight(20);
        cellBlank100.setPaddingBottom(2);
        cellBlank100.setBorder(Rectangle.NO_BORDER);
        gts.addCell(cellBlank100);

        PdfPCell cellConsignee3 = new PdfPCell();
        cellConsignee3.setPhrase(new Phrase("Est. Shipment Date : " + doList.get(0).getShipDate(), fontContent));
        cellConsignee3.setLeading(1.1f, 1.1f);
        cellConsignee3.setFixedHeight(20);
        cellConsignee3.setPaddingBottom(2);
        cellConsignee3.setPaddingTop(5);
        cellConsignee3.setPaddingLeft(10);
        cellConsignee3.setBorder(Rectangle.NO_BORDER);
        gts.addCell(cellConsignee3);

        doc.add(gts);

        doc.add(Chunk.NEWLINE);

        
        PdfPTable box = new PdfPTable(1);
        box.setWidths(new float[]{4.3f});
        box.setTotalWidth(527);
        box.setLockedWidth(true);

        PdfPCell boxHeader = new PdfPCell();
        boxHeader.setPhrase(new Phrase("Total Box : " + doList.size(), fontOpenSans(11f, Font.BOLD)));
        boxHeader.setFixedHeight(35);
        boxHeader.setPaddingBottom(5);
        boxHeader.setPaddingTop(13);
        boxHeader.setPaddingLeft(5);
        boxHeader.setBorder(Rectangle.NO_BORDER);
        box.addCell(boxHeader);
        doc.add(box);
        
//        doc.add(Chunk.NEWLINE);
        
        Integer cellPadding = 5;

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{0.7f, 3.5f, 2.2f, 1.2f, 1.4f, 1.6f});
        table.setSpacingBefore(10);

        Font fontHeader = fontOpenSans(9.0f, Font.BOLD);
        fontHeader.setColor(BaseColor.WHITE);

        PdfPCell cellHeader = new PdfPCell();
        cellHeader.setBackgroundColor(BaseColor.GRAY);
        cellHeader.setPadding(cellPadding);

        Font fontContent2 = fontOpenSans(9.0f, Font.NORMAL);
        PdfPCell cellContent = new PdfPCell();
        cellContent.setPadding(cellPadding);

        Integer grandTotalQty = 0;
        Double grandTotalWeight = 0.00;
        Double grandTotalPrice = 0.00;
        int i = 0;
        while (i < doList.size()) {
            if (i == 0) {
                //Header
                cellHeader.setPhrase(new Phrase("No.", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Outer Box Barcode Label", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Outer Box ID", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("No. of Box", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Weight (KG)", fontHeader));
                table.addCell(cellHeader);
                cellHeader.setPhrase(new Phrase("Box Price (USD)", fontHeader));
                table.addCell(cellHeader);
            }
            cellContent.setPhrase(new Phrase(i + 1 + "", fontContent2));
            table.addCell(cellContent);

            PdfContentByte cb = writer.getDirectContent();
            Barcode128 code128 = new Barcode128();
            code128.setGenerateChecksum(true);
            code128.setFont(null);
            code128.setCode(doList.get(i).getBoxId());
            code128.setBarHeight(15f); // great! but what about width???
            Image code128Image = code128.createImageWithBarcode(cb, null, null);
            PdfPCell barcode = new PdfPCell(code128Image);
            barcode.setPaddingLeft(7.0f);
            barcode.setPaddingTop(3.5f);
            barcode.setPaddingBottom(3.5f);

            table.addCell(barcode);
            cellContent.setPhrase(new Phrase(doList.get(i).getBoxId(), fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase("1", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase("0.5", fontContent2));
            table.addCell(cellContent);
            cellContent.setPhrase(new Phrase("1.00", fontContent2));
            table.addCell(cellContent);

            grandTotalQty += Integer.parseInt("1");
            grandTotalWeight += Double.parseDouble("0.5");
            grandTotalPrice += Double.parseDouble("1.0");
            
            i++;
        }
        String jumlahBerat = String.format("%.1f", grandTotalWeight);
        String jumlahHarga = String.format("%.2f", grandTotalPrice);
        
        doc.add(table);

        PdfPTable total = new PdfPTable(4);
        total.setWidthPercentage(100.0f);
        total.setWidths(new float[]{6.4f, 1.2f, 1.4f, 1.6f});
//        total.setSpacingBefore(10);

        PdfPCell grand = new PdfPCell();
        grand.setPhrase(new Phrase("GRAND TOTAL  ", fontOpenSans(9.0f, Font.BOLD)));
//        grand.setFixedHeight(70);
        grand.setPaddingBottom(5);
        grand.setPaddingTop(5);
        grand.setPaddingRight(5);
//        sign2.setBorder(Rectangle.NO_BORDER);
        grand.setHorizontalAlignment(Element.ALIGN_RIGHT);
        total.addCell(grand);

        PdfPCell totalqty = new PdfPCell();
        totalqty.setPhrase(new Phrase(grandTotalQty.toString(), fontOpenSans(9.0f, Font.BOLD)));
//        totalqty.setFixedHeight(70);
        totalqty.setPaddingBottom(5);
        totalqty.setPaddingTop(5);
        totalqty.setPaddingLeft(5);
//        sign2.setBorder(Rectangle.NO_BORDER);
        total.addCell(totalqty);

        PdfPCell totalWeight = new PdfPCell();
        totalWeight.setPhrase(new Phrase(jumlahBerat, fontOpenSans(9.0f, Font.BOLD)));
//        cellDriver.setLeading(1.4f, 1.4f);
//        totalWeight.setFixedHeight(70);
        totalWeight.setPaddingBottom(5);
        totalWeight.setPaddingTop(5);
        totalWeight.setPaddingLeft(5);
//        cellDriver.setBorder(Rectangle.NO_BORDER);
        total.addCell(totalWeight);

        PdfPCell totalPrice = new PdfPCell();
        totalPrice.setPhrase(new Phrase(jumlahHarga, fontOpenSans(9.0f, Font.BOLD)));
//        cellDriver.setLeading(1.4f, 1.4f);
//        totalPrice.setFixedHeight(70);
        totalPrice.setPaddingBottom(5);
        totalPrice.setPaddingTop(5);
        totalPrice.setPaddingLeft(5);
//        cellDriver.setBorder(Rectangle.NO_BORDER);
        total.addCell(totalPrice);

        doc.add(total);

        doc.add(Chunk.NEWLINE);
        PdfPTable dummy = new PdfPTable(1);
        dummy.setWidths(new float[]{4.3f});
        dummy.setTotalWidth(527);
        dummy.setLockedWidth(true);
        PdfPCell boxHeader2 = new PdfPCell();
        boxHeader2.setPhrase(new Phrase(""));
        boxHeader2.setFixedHeight(35);
        boxHeader2.setPaddingBottom(5);
        boxHeader2.setPaddingTop(13);
        boxHeader2.setPaddingLeft(5);
        boxHeader2.setBorder(Rectangle.NO_BORDER);
        dummy.addCell(boxHeader2);
        doc.add(dummy);
        
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
//        doc.add(blank);
//        doc.add(blank);
//        doc.add(blank);
        doc.add(Chunk.NEWLINE);
        PdfPTable sign = new PdfPTable(3);
        sign.setWidths(new float[]{6.0f, 6.0f, 6.0f});
        sign.setTotalWidth(527);
        sign.setLockedWidth(true);

        PdfPCell sign2 = new PdfPCell();
        sign2.setPhrase(new Phrase("Rel Lab Sender Verification", fontOpenSans(9f, Font.UNDERLINE)));
        sign2.setFixedHeight(70);
        sign2.setPaddingBottom(5);
        sign2.setPaddingTop(5);
        sign2.setPaddingLeft(5);
//        sign2.setBorder(Rectangle.NO_BORDER);
        sign.addCell(sign2);

        PdfPCell sign3 = new PdfPCell();
        sign3.setPhrase(new Phrase("ON Semi Security Stamp", fontOpenSans(9f, Font.UNDERLINE)));
        sign3.setFixedHeight(70);
        sign3.setPaddingBottom(5);
        sign3.setPaddingTop(5);
        sign3.setPaddingLeft(5);
//        sign2.setBorder(Rectangle.NO_BORDER);
        sign.addCell(sign3);

        PdfPCell cellDriver = new PdfPCell();
        cellDriver.setPhrase(new Phrase("Sendayan Receiver Verification", fontOpenSans(9f, Font.UNDERLINE)));
//        cellDriver.setLeading(1.4f, 1.4f);
        cellDriver.setFixedHeight(70);
        cellDriver.setPaddingBottom(5);
        cellDriver.setPaddingTop(5);
        cellDriver.setPaddingLeft(5);
//        cellDriver.setBorder(Rectangle.NO_BORDER);
        sign.addCell(cellDriver);

        doc.add(sign);

        PdfPTable driver = new PdfPTable(1);
        driver.setWidths(new float[]{18.0f});
        driver.setTotalWidth(527);
        driver.setLockedWidth(true);

        PdfPCell driverCell = new PdfPCell();
        Phrase p = new Phrase("Driver Information", fontOpenSans(9f, Font.NORMAL));
        p.add(new Chunk("\n\n\nDriver Name       : ______________________________________________________________"
                     + "\n\nDriver IC No.       : ______________________________________________________________"
                     + "\n\nSeal No.               : _____________________      Vehicle No. : ________________________ ", fontOpenSans(9f, Font.NORMAL)));
        driverCell.setPhrase(p);
        driverCell.setFixedHeight(85);
        driverCell.setPaddingBottom(5);
        driverCell.setPaddingTop(5);
        driverCell.setPaddingLeft(5);
//        sign2.setBorder(Rectangle.NO_BORDER);
        driver.addCell(driverCell);

        doc.add(driver);

        doc.newPage();

        PdfPTable tableHeader2 = new PdfPTable(3);
        tableHeader2.setWidths(new float[]{4.0f, 15.0f, 5.0f});
        tableHeader2.setTotalWidth(527);
        tableHeader2.setLockedWidth(true);

        PdfPCell cellSecurity = new PdfPCell();
        cellSecurity.setPhrase(new Phrase("Sendayan Copy", fontOpenSans(7f, Font.BOLD)));
        cellSecurity.setFixedHeight(40);
        cellSecurity.setPaddingBottom(5);
        cellSecurity.setPaddingTop(0);
        cellSecurity.setBorder(Rectangle.NO_BORDER);
        tableHeader2.addCell(cellSecurity);

        PdfPCell cellTitle2 = new PdfPCell();
        cellTitle2.setPhrase(new Phrase("DO for Sample Retention from Rel Lab to Sendayan", fontOpenSans(12f, Font.BOLD)));
        cellTitle2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle2.setFixedHeight(40);
        cellTitle2.setPaddingBottom(5);
        cellTitle2.setPaddingTop(0);
        cellTitle2.setBorder(Rectangle.NO_BORDER);
        tableHeader2.addCell(cellTitle2);

        ServletContext context2 = getServletContext();
        String file2 = "/resources/img/logoON.png";
        InputStream is2 = context2.getResourceAsStream(file2);
        byte[] bytes2 = IOUtils.toByteArray(is2);
        Image image2 = Image.getInstance(bytes2);

        PdfPCell cellLogo2 = new PdfPCell(image2, true);
        cellLogo2.setFixedHeight(45);
        cellLogo2.setPaddingBottom(5);
        cellLogo2.setBorder(Rectangle.NO_BORDER);
        tableHeader2.addCell(cellLogo2);

        doc.add(tableHeader2);

        doc.add(Chunk.NEWLINE);

        doc.add(address);

        doc.add(Chunk.NEWLINE);

        doc.add(address2);

        doc.add(Chunk.NEWLINE);

        doc.add(gts);
        
        doc.add(box);

        doc.add(table);

        doc.add(total);
        
        doc.add(Chunk.NEWLINE);

        doc.add(dummy);
        
        doc.add(sign);

        doc.add(driver);
        
        doc.newPage();

        PdfPTable tableHeader3 = new PdfPTable(3);
        tableHeader3.setWidths(new float[]{4.0f, 15.0f, 5.0f});
        tableHeader3.setTotalWidth(527);
        tableHeader3.setLockedWidth(true);

        PdfPCell cellOnSecurity = new PdfPCell();
        cellOnSecurity.setPhrase(new Phrase("ON Security Copy", fontOpenSans(7f, Font.BOLD)));
        cellOnSecurity.setFixedHeight(40);
        cellOnSecurity.setPaddingBottom(5);
        cellOnSecurity.setPaddingTop(0);
        cellOnSecurity.setBorder(Rectangle.NO_BORDER);
        tableHeader3.addCell(cellOnSecurity);

        PdfPCell cellTitle3 = new PdfPCell();
        cellTitle3.setPhrase(new Phrase("DO for Sample Retention from Rel Lab to Sendayan", fontOpenSans(12f, Font.BOLD)));
        cellTitle3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle3.setFixedHeight(40);
        cellTitle3.setPaddingBottom(5);
        cellTitle3.setPaddingTop(0);
        cellTitle3.setBorder(Rectangle.NO_BORDER);
        tableHeader3.addCell(cellTitle3);

        ServletContext context3 = getServletContext();
        String file3 = "/resources/img/logoON.png";
        InputStream is3 = context3.getResourceAsStream(file3);
        byte[] bytes3 = IOUtils.toByteArray(is3);
        Image image3 = Image.getInstance(bytes3);

        PdfPCell cellLogo3 = new PdfPCell(image3, true);
        cellLogo3.setFixedHeight(45);
        cellLogo3.setPaddingBottom(5);
        cellLogo3.setBorder(Rectangle.NO_BORDER);
        tableHeader3.addCell(cellLogo3);

        doc.add(tableHeader3);

        doc.add(Chunk.NEWLINE);

        doc.add(address);

        doc.add(Chunk.NEWLINE);

        doc.add(address2);

        doc.add(Chunk.NEWLINE);

        doc.add(gts);
        
        doc.add(box);

        doc.add(table);

        doc.add(total);
        
        doc.add(Chunk.NEWLINE);

        doc.add(dummy);
        
        doc.add(sign);

        doc.add(driver);
    }
    
    public void shipmentArrivalChecklist(Document doc) {
        
    }
}
