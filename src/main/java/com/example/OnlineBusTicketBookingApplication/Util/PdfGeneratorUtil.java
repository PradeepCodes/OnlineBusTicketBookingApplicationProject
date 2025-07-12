package com.example.OnlineBusTicketBookingApplication.Util;
import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class PdfGeneratorUtil {
    public static byte[] generateInvoice(Booking booking) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Bus Ticket Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Booking Details:"));
        document.add(new Paragraph("Passenger: " + booking.getUser().getName()));
        document.add(new Paragraph("Email: " + booking.getUser().getEmail()));
        document.add(new Paragraph("Bus No: " + booking.getBus().getBusNumber()));
        document.add(new Paragraph("From: " + booking.getBus().getSource()));
        document.add(new Paragraph("To: " + booking.getBus().getDestination()));
        document.add(new Paragraph("Departure: " + booking.getBus().getDepartureTime()));
        document.add(new Paragraph("Seats Booked: " + booking.getSeatsBooked()));
        document.add(new Paragraph("Total Fare: ₹" + booking.getTotalFare()));
        document.add(new Paragraph("Booking Date: " + booking.getBookingDate()));

        document.close();

        return out.toByteArray();
    }
}
