package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.AllCustomersDTO;
import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.models.customers;
import com.example.pensionat.repositories.ContractCustomersRepo;
import com.example.pensionat.services.convert.ContractCustomerConverter;
import com.example.pensionat.services.impl.ContractCustomerServiceImpl;
import com.example.pensionat.services.providers.XmlStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContractCustomerServiceImplTest {

    @Mock
    private ContractCustomersRepo contractCustomersRepo;

    @Mock
    private XmlStreamProvider xmlStreamProvider;

    @MockBean
    private JavaMailSender emailSender;

    ContractCustomerServiceImpl sut;

    int pageNum;
    int pageSize;
    List<customers> customerList;
    Pageable pageable;
    Page<customers> page;


    @BeforeEach()
    void setup() {
        //Arrange here ?
        sut = new ContractCustomerServiceImpl(contractCustomersRepo, xmlStreamProvider);
        pageNum = 1;
        pageSize = 1;
        customerList = Arrays.asList(new customers(1L, "ABC", "A B", "title",
                        "address", "city", 123, "A-Land", "phone", "fax"),
                new customers(2L, "CDE", "C D", "title",
                        "address", "city", 123, "B-Land", "phone", "fax"),
                new customers(3L, "EFG", "E F", "title",
                        "address", "city", 123, "C-Land", "phone", "fax"));

    }

    @Test
    void getAllCustomersPage(){
        pageable = PageRequest.of(pageNum - 1, pageSize);
        page = new PageImpl<>(customerList, pageable, customerList.size());

        when(contractCustomersRepo.findAll(pageable)).thenReturn(page);

        Page<ContractCustomerDTO> result = sut.getAllCustomersPage(pageNum, pageSize);

        assertNotNull(result);
        assertEquals(customerList.size(), result.getTotalElements());
        assertEquals(customerList.size()/ pageSize, result.getTotalPages());
        verify(contractCustomersRepo, times(1)).findAll(pageable);
    }

    @Test
    void getAllCustomersSortedPage() {
        String sortBy = "companyName";
        String order = "asc";

        pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortBy).ascending());
        page = new PageImpl<>(customerList, pageable, customerList.size());

        when(contractCustomersRepo.findAll(pageable)).thenReturn(page);

        Page<ContractCustomerDTO> result = sut.getAllCustomersSortedPage(pageNum, sortBy, order, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(customerList.size(), result.getTotalElements());
        assertEquals(customerList.size(), result.getTotalPages());
        assertTrue(pageable.getSort().isSorted());
        assertEquals(Sort.Direction.ASC, Objects.requireNonNull(pageable.getSort().getOrderFor(sortBy)).getDirection());
        verify(contractCustomersRepo, times(1)).findAll(pageable);
        verify(contractCustomersRepo, times(1)).findAll(argThat((Pageable pageable) -> pageable.getSort().toString().contains(sortBy)));
    }


    @Test
    void getCustomerById(){
        long customerId = 1L;
        customers expectedCustomer = new customers(customerId, "ABC", "A B", "title", "address", "city", 123, "A-Land", "phone", "fax");
        when(contractCustomersRepo.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        customers result = sut.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(expectedCustomer, result);
        verify(contractCustomersRepo, times(1)).findById(customerId);
    }

    //Fundera på om vi verkligen ska ha denna - är det värt att testa? Idk. Inget känns på riktigt.
    @Test
    void getCustomerByIdWhenCustomerIsNull() {
        long customerId = 1000L;
        when(contractCustomersRepo.findById(customerId)).thenReturn(Optional.empty());

        customers result = sut.getCustomerById(customerId);

        //Vi gör optional till null i metoden
        assertNull(result);
        verify(contractCustomersRepo, times(1)).findById(customerId);
    }
    // getDetailedContractCustomerById() är samma + converter

    @Test
    void addToModel() {
        try{
            List<ContractCustomerDTO> customerDTOList = customerList.stream()
                    .map(ContractCustomerConverter::customersToContractCustomerDto)
                    .toList();
            Page<ContractCustomerDTO> page = new PageImpl<>(customerDTOList);
            Model model = new ConcurrentModel();

            Method method = ContractCustomerServiceImpl.class.getDeclaredMethod("addToModelUtil", Page.class, Model.class, int.class);
            method.setAccessible(true);
            method.invoke(sut, page, model, pageNum);

            //TODO det funkar men det gnäller
            assertEquals(customerDTOList.size(), ((List<ContractCustomerDTO>) Objects.requireNonNull(model.getAttribute("allCustomers"))).size());
            assertEquals(pageNum, model.getAttribute("currentPage"));
            assertEquals((long)customerDTOList.size(), model.getAttribute("totalItems"));
            assertEquals(1, model.getAttribute("totalPages"));
        } catch (Exception e) {
            //TODO Flera olika exceptions ??? Inga exceptions ???

        }

    }

    @Test
    void whenFetchContractCustomersShouldMapCorrectly() throws IOException {
        // Arrange
        when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("contract.xml"));

        // Act men också arrange blir galen ju
        AllCustomersDTO result = sut.fetchContractCustomers();
        List<DetailedContractCustomerDTO> resultList = result.getContractCustomerList();

        //Assert
        assertEquals(3, resultList.size());
        assertEquals(1, resultList.get(0).getId());
        assertEquals("Persson Kommanditbolag", resultList.get(0).getCompanyName());
        assertEquals("Maria Åslund", resultList.get(0).getContactName());
        assertEquals("gardener", resultList.get(0).getContactTitle());
        assertEquals("Anderssons Gata 259", resultList.get(0).getStreetAddress());
        assertEquals("Kramland", resultList.get(0).getCity());
        assertEquals(60843, resultList.get(0).getPostalCode());
        assertEquals("Sverige", resultList.get(0).getCountry());
        assertEquals("076-340-7143", resultList.get(0).getPhone());
        assertEquals("1500-16026", resultList.get(0).getFax());
    }

    @Test
    void saveAllContractCustomersShouldSave() throws IOException {
        contractCustomersRepo.deleteAll();
        when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("contract.xml"));

        AllCustomersDTO result = sut.fetchContractCustomers();
        List<DetailedContractCustomerDTO> resultList = result.getContractCustomerList();

        sut.saveAll(resultList);

        verify(contractCustomersRepo, times(1)).saveAll(any());
    }

}
