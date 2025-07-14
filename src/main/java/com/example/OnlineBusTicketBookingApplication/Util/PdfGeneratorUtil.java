package com.example.OnlineBusTicketBookingApplication.Util;
import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGeneratorUtil {
    public static byte[] generateInvoice(Booking booking) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("🚌 Bus Ticket Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Fonts
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Table setup
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add rows
        addRow(table, "Passenger Name", booking.getUser().getName(), labelFont, valueFont);
        addRow(table, "Email", booking.getUser().getEmail(), labelFont, valueFont);
        addRow(table, "Bus Number", booking.getBus().getBusNumber(), labelFont, valueFont);
        addRow(table, "Route", booking.getBus().getSource() + " → " + booking.getBus().getDestination(), labelFont, valueFont);
        addRow(table, "Departure", booking.getBus().getDepartureTime().toString(), labelFont, valueFont);
        addRow(table, "Arrival", booking.getBus().getArrivalTime().toString(), labelFont, valueFont);
        addRow(table, "Seats Booked", String.valueOf(booking.getSeatsBooked()), labelFont, valueFont);
        addRow(table, "Total Fare (₹)", String.format("%.2f", booking.getTotalFare()), labelFont, valueFont);
        addRow(table, "Booking Date", booking.getBookingDate().toString(), labelFont, valueFont);

        document.add(table);

        document.close();
        return out.toByteArray();
    }

    private static void addRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));

        cell1.setPadding(8f);
        cell2.setPadding(8f);
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);

        table.addCell(cell1);
        table.addCell(cell2);
    }
}
