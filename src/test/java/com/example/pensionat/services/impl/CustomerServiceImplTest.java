package com.example.pensionat.services.impl;

import com.example.pensionat.controllers.AuthController;
import com.example.pensionat.dtos.*;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.impl.CustomerServiceImpl;
import com.example.pensionat.services.providers.BlacklistStreamAndUrlProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private BlacklistStreamAndUrlProvider provider;
    @MockBean
    private JavaMailSender emailSender;

    Long id = 1L;
    String name = "Allan Berg";
    String email = "allan@mail.com";


    Customer customer = new Customer(id, name, email);
    CustomerDTO customerDTO = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(id, name, email);
    SimpleBlacklistCustomerDTO blacklistCustomer = new SimpleBlacklistCustomerDTO(name, email, false);
    int pageNum = 1;
    Pageable pageable = PageRequest.of(pageNum - 1, 5);
    Page<Customer> mockedPage = new PageImpl<>(List.of(customer));

    String blacklistResponseJson = "{\"statusText\":\"Blacklisted\",\"ok\":false}";
    BlacklistResponse blacklistResponse = new BlacklistResponse("Blacklisted", false);
    String dateString = "2024-05-08T13:11:50.490321";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    DetailedBlacklistCustomerDTO detailedBlacklistCustomer = new DetailedBlacklistCustomerDTO
                                        (17, email, name, "bed&basse", simpleDateFormat.parse(dateString), false);
    DetailedBlacklistCustomerDTO[] array = new DetailedBlacklistCustomerDTO[1];

    CustomerServiceImplTest() throws ParseException {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        List<SimpleCustomerDTO> actual = service.getAllCustomers();
        assertEquals(1, actual.size());
        assertEquals(actual.get(0).getId(), customer.getId());
        assertEquals(actual.get(0).getName(), customer.getName());
        assertEquals(actual.get(0).getEmail(), customer.getEmail());
    }

    @Test
    void addCustomer() {
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        SimpleCustomerDTO actual = service.addCustomer(simpleCustomerDTO);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void removeCustomerById() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        String feedback = service.removeCustomerById(id);
        assertTrue(feedback.equalsIgnoreCase("Customer removed successfully"));
    }

    @Test
    void updateCustomer() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        String feedback = service.updateCustomer(simpleCustomerDTO);
        assertTrue(feedback.equalsIgnoreCase("Customer updated successfully"));
    }

    @Test
    void getCustomersByEmail() {
        when(customerRepo.findByEmailContains(email, pageable)).thenReturn(mockedPage);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        Page<SimpleCustomerDTO> actual = service.getCustomersByEmail(customer.getEmail(), pageNum);
        assertEquals(1, actual.getTotalElements());
        assertEquals(customer.getId(), actual.getContent().get(0).getId());
        assertEquals(customer.getName(), actual.getContent().get(0).getName());
        assertEquals(customer.getEmail(), actual.getContent().get(0).getEmail());
    }

    @Test
    void getAllCustomersPage() {
        when(customerRepo.findAll(pageable)).thenReturn(mockedPage);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        Page<SimpleCustomerDTO> actual = service.getAllCustomersPage(pageNum);
        assertEquals(1, actual.getTotalElements());
        assertEquals(customer.getId(), actual.getContent().get(0).getId());
        assertEquals(customer.getName(), actual.getContent().get(0).getName());
        assertEquals(customer.getEmail(), actual.getContent().get(0).getEmail());
    }

    @Test
    void getCustomerByEmail() {
        when(customerRepo.findByEmail(email)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        SimpleCustomerDTO actual = service.getCustomerByEmail(email);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void whenCheckIfEmailBlacklistedShouldReturnCorrectBoolean() throws IOException, InterruptedException {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        CustomerServiceImpl spyService = spy(service);

        when(provider.getHttpResponse(email)).thenReturn(mockResponse);
        doReturn(blacklistResponse).when(spyService).mapToBlacklistResponse(any(HttpResponse.class));

        Boolean actual = spyService.checkIfEmailBlacklisted(email);

        assertEquals(actual, blacklistResponse.getOk());
        verify(spyService, times(1)).mapToBlacklistResponse(mockResponse);
        verify(provider, times(1)).getHttpResponse(email);
    }

    @Test
    void whenMapToBlacklistResponseShouldMapCorrectly() throws JsonProcessingException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(blacklistResponseJson);

        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        BlacklistResponse actual = service.mapToBlacklistResponse(mockResponse);

        assertEquals(actual.getStatusText(), "Blacklisted");
        assertEquals(actual.getOk(), false);
    }

    @Test
    public void updateOrAddToBlacklistShouldReturnCorrectly_Response200() throws Exception {
        CustomerServiceImpl service = Mockito.spy(new CustomerServiceImpl(customerRepo, provider));

        when(provider.getBlacklistUrl()).thenReturn("http://mockurl.com");
        doReturn(200).when(service).sendHttpRequest(eq("POST"), anyString(), anyString());

        String result = service.updateOrAddToBlacklist(blacklistCustomer);
        assertEquals(blacklistCustomer.getEmail() + " är nu svartlistad!", result);
    }

    @Test
    public void updateOrAddToBlacklistShouldReturnCorrectly_Response400Then204() throws Exception {
        CustomerServiceImpl service = Mockito.spy(new CustomerServiceImpl(customerRepo, provider));

        when(provider.getBlacklistUrl()).thenReturn("http://mockurl.com");
        doReturn(400).when(service).sendHttpRequest(eq("POST"), anyString(), anyString());
        doReturn(204).when(service).sendHttpRequest(eq("PUT"), anyString(), anyString());

        String result = service.updateOrAddToBlacklist(blacklistCustomer);
        assertEquals(blacklistCustomer.getEmail() + " är redan svartlistad. Uppgifter uppdaterade.", result);
    }

    @Test
    public void updateOrAddToBlacklistShouldReturnCorrectly_Response400ThenNon204() throws Exception {
        CustomerServiceImpl service = Mockito.spy(new CustomerServiceImpl(customerRepo, provider));

        when(provider.getBlacklistUrl()).thenReturn("http://mockurl.com");
        doReturn(400).when(service).sendHttpRequest(eq("POST"), anyString(), anyString());
        doReturn(400).when(service).sendHttpRequest(eq("PUT"), anyString(), anyString());

        String result = service.updateOrAddToBlacklist(blacklistCustomer);
        assertEquals("Blacklist blev ej uppdaterad. kontakta support.", result);
    }

    @Test
    void getBlacklistPage() {

    }

    @Test
    void whenGetBlacklistShouldReturnCorrectObject() throws IOException {
        array[0] = detailedBlacklistCustomer;
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        CustomerServiceImpl spyService = spy(service);

        when(provider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("blacklist.json"));
        doReturn(array).when(spyService).mapToDetailedBlacklistCustomerDTOArray(any(InputStream.class));

        List<SimpleBlacklistCustomerDTO> actual = spyService.getBlacklist();

        assertEquals(actual.get(0).getName(), detailedBlacklistCustomer.getName());
        assertEquals(actual.get(0).getEmail(), detailedBlacklistCustomer.getEmail());
        assertEquals(actual.get(0).getOk(), detailedBlacklistCustomer.getOk());
        assertEquals(actual.size(), 1);
    }

    @Test
    void whenMapToDetailedBlacklistCustomerDTOArrayShouldMapCorrectly() throws IOException {
        DetailedBlacklistCustomerDTO[] actual;

        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        actual = service.mapToDetailedBlacklistCustomerDTOArray(getClass().getClassLoader().getResourceAsStream("blacklist.json"));

        assertEquals(actual[2].getId(), detailedBlacklistCustomer.getId());
        assertEquals(actual[2].getEmail(), detailedBlacklistCustomer.getEmail());
        assertEquals(actual[2].getName(), detailedBlacklistCustomer.getName());
        assertEquals(actual[2].getGroup(), detailedBlacklistCustomer.getGroup());
        //assertEquals(actual[2].getCreated(), detailedBlacklistCustomer.getCreated());
        assertInstanceOf(Date.class, actual[2].getCreated());
        assertInstanceOf(Date.class, detailedBlacklistCustomer.getCreated());
        assertEquals(actual[2].getOk(), detailedBlacklistCustomer.getOk());
    }

    @Test
    void makeHttpRequest() {

    }

    @Test
    void addToModelBlacklist() {

    }

    @Test
    void addToModelBlacklistSearch() {

    }

    @Test
    void getBlacklistBySearch() {

    }

    @Test
    void getCustomerFromBlacklistByEmail() {

    }
}