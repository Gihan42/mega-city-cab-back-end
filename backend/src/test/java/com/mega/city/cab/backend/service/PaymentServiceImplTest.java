package com.mega.city.cab.backend.service;


import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.entity.Payment;
import com.mega.city.cab.backend.entity.custom.CustomPaymentDetails;
import com.mega.city.cab.backend.entity.custom.CustomPaymentResult;
import com.mega.city.cab.backend.repo.PaymentRepo;
import com.mega.city.cab.backend.service.impl.PaymentServiceImpl;
import com.mega.city.cab.backend.util.response.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import net.sf.jasperreports.engine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.BadCredentialsException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Stripe.apiKey = "sk_test_51QitlYH7MFp5hXfRz09iTYNI2NB3LPpD0w6w169J7B6CwoUrNvO2Q8MLQNoyhIjXDVYLdzO3ylR0b7Cbq8I3IGCD00fjsWDzxb"; // Set your Stripe API key
    }

/*    @Test
    void testCreatePayment_Success() throws StripeException {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(100.0);
        paymentDto.setVehicleId(1L);

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long)(paymentDto.getAmount() * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Vehicle Payment")
                                                                .build()
                                                )
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .build();

        Session session = new Session();
        session.setId("session_id");
        session.setUrl("http://stripe.com/session_id");

        when(Session.create(params)).thenReturn(session);

        // Mock the getPayments() method to return an empty list
        when(paymentRepo.getPayments()).thenReturn(Collections.emptyList());

        // Rest of the test...
    }*/

    @Test
    void testCreatePayment_InvalidType() {
        // Arrange
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(100.0);
        paymentDto.setVehicleId(1L);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            paymentService.createPayment(paymentDto, "InvalidType");
        });
    }

    @Test
    void testSavePayment() {
        // Arrange
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(100.0);
        paymentDto.setVehicleId(1L);

        Payment payment = new Payment();
        when(modelMapper.map(any(PaymentDto.class), eq(Payment.class))).thenReturn(payment);
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        // Act
        Payment savedPayment = paymentService.savePayment(paymentDto);

        // Assert
        assertNotNull(savedPayment);
        verify(paymentRepo, times(1)).save(payment);
    }

    @Test
    void testGetAllPayments_Admin() {
        // Arrange
        when(paymentRepo.getPayments()).thenReturn(Collections.emptyList());

        // Act
        List<CustomPaymentResult> payments = paymentService.getAllPayments("Admin");

        // Assert
        assertNotNull(payments);
        assertEquals(0, payments.size());
    }

    @Test
    void testGetAllPayments_InvalidType() {
        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            paymentService.getAllPayments("InvalidType");
        });
    }

    @Test
    void testGetPaymentDetailsByPaymentId_User() {
        // Arrange
        Long paymentId = 1L;
        CustomPaymentDetails paymentDetails = mock(CustomPaymentDetails.class); // Mock the abstract class/interface
        when(paymentRepo.getPaymentDetailsByPaymentId(paymentId)).thenReturn(paymentDetails);

        // Act
        CustomPaymentDetails result = paymentService.getPaymentDetailsByPaymentId(paymentId, "User", "pdf");

        // Assert
        assertNotNull(result);
        assertEquals(paymentDetails, result);
    }

    @Test
    void testGetPaymentDetailsByPaymentId_InvalidType() {
        // Arrange
        Long paymentId = 1L;

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            paymentService.getPaymentDetailsByPaymentId(paymentId, "InvalidType", "pdf");
        });
    }

   /* @Test
    void testReturnExportReport_User() throws IOException, JRException, SQLException {
        // Arrange
        Long paymentId = 1L;
        String reportFormat = "pdf";
        when(dataSource.getConnection()).thenReturn(connection);

        // Load the report file from the classpath
        InputStream reportStream;
        try {
            reportStream = new ClassPathResource("megacitycabNewReport.jrxml").getInputStream();
        } catch (IOException e) {
            fail("Failed to load the report file: " + e.getMessage());
            return;
        }

        // Compile the report
        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager.compileReport(reportStream);
        } catch (JRException e) {
            fail("Failed to compile the report: " + e.getMessage());
            return;
        }

        // Ensure the report is not null
        assertNotNull(jasperReport, "JasperReport should not be null");

        // Set up parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java");
        parameters.put("paymentId", paymentId);

        // Mock JasperPrint and export
        JasperPrint jasperPrint = mock(JasperPrint.class);
        when(JasperFillManager.fillReport(eq(jasperReport), eq(parameters), eq(connection))).thenReturn(jasperPrint);
        when(JasperExportManager.exportReportToPdf(jasperPrint)).thenReturn(new byte[0]);

        // Act
        byte[] report = paymentService.returnExportReport(paymentId, reportFormat, "User");

        // Assert
        assertNotNull(report);
    }*/

    @Test
    void testReturnExportReport_InvalidType() {
        // Arrange
        Long paymentId = 1L;
        String reportFormat = "pdf";

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            paymentService.returnExportReport(paymentId, reportFormat, "InvalidType");
        });
    }
}