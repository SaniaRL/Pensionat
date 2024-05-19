package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.models.customers;
import com.example.pensionat.repositories.ContractCustomersRepo;
import com.example.pensionat.services.convert.ContractCustomerConverter;
import com.example.pensionat.services.impl.ContractCustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class ContractCustomerServiceImplTest {

    @Mock
    private ContractCustomersRepo contractCustomersRepo;

    ContractCustomerServiceImpl sut;

    int pageNum;
    int pageSize;
    List<customers> customerList;
    Pageable pageable;
    Page<customers> page;


    @BeforeEach()
    void setup() {
        //Arrange here ?
        contractCustomersRepo = mock(ContractCustomersRepo.class);
        sut = new ContractCustomerServiceImpl(contractCustomersRepo);
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
    void getCustomersBySearch(){

    }

//    addToModelUtil() är private
    /*

        private void addToModelUtil(Page<ContractCustomerDTO> p, Model model, int currentPage){
        model.addAttribute("allCustomers", p.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", p.getTotalElements());
        model.addAttribute("totalPages", p.getTotalPages());
    }

        @Override
    public void addToModel(int currentPage, Model model){
        Page<ContractCustomerDTO> c = getAllCustomersPage(currentPage);
        addToModelUtil(c, model, currentPage);
    }
     */

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
    void addToModelSorted(){

    }

    @Test
    void saveAll(){

    }

    /*

    @Override
    public Page<ContractCustomerDTO> getCustomersBySearch(int pageNum, String search, String sort, String order){
        Pageable pageable;
        if(order.equals("asc")) {
            pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sort).descending());
        }
        Page<customers> page = contractCustomersRepo.findByCompanyNameContainsOrContactNameContains(search, search, pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public void addToModelSorted(int currentPage, String sortBy, String order, Model model){
        Page<ContractCustomerDTO> c = getAllCustomersSortedPage(currentPage, sortBy, order);
        addToModelUtil(c, model, currentPage);
        model.addAttribute("order", order);
        model.addAttribute("sort", sortBy);
    }

    @Override
    public void addToModelSearch(int currentPage, String search, String sort, String order, Model model) {
        Page<ContractCustomerDTO> p = getCustomersBySearch(currentPage, search, sort, order);
        addToModelUtil(p, model, currentPage);
        model.addAttribute("order", order);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
    }



    @Override
    public void saveAll(List<DetailedContractCustomerDTO> customers){
        contractCustomersRepo.saveAll(customers.stream()
                .map(ContractCustomerConverter::detailedContractCustomerToCustomers).toList());
    }


    private XmlMapper getMapper(){
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);

        return new XmlMapper(module);
    }


    @Override
    public AllCustomersDTO fetchContractCustomers(String url) throws IOException {

        allcustomers allCustomers = getMapper().readValue(new URL(url), allcustomers.class);

        return ContractCustomerConverter.allCustomerToAllCustomerDTO(allCustomers);
    }

     */
}
