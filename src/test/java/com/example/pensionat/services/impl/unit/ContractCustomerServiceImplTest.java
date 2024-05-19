package com.example.pensionat.services.impl.unit;

import com.example.pensionat.models.customers;
import com.example.pensionat.repositories.ContractCustomersRepo;
import com.example.pensionat.services.impl.ContractCustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContractCustomerServiceImplTest {

    @Mock
    private ContractCustomersRepo contractCustomersRepo;

    ContractCustomerServiceImpl sut;

    int pageNum;
    int pageSize;
    List<customers> customerList;
    Page<customers> customersPage;


    @BeforeEach()
    void setup() {
        //Arrange here ?
        ContractCustomersRepo repository = mock(ContractCustomersRepo.class);
        sut = new ContractCustomerServiceImpl(contractCustomersRepo);
        pageNum = 1;
        pageSize = 1;
        customerList = Arrays.asList(new customers(1L, "ABC", "A B", "title",
                        "address", "city", 123, "A-Land", "phone", "fax"),
                new customers(2L, "CDE", "C D", "title",
                        "address", "city", 123, "B-Land", "phone", "fax"),
                new customers(3L, "EFG", "E F", "title",
                        "address", "city", 123, "C-Land", "phone", "fax"));
        customersPage = new PageImpl<>(customerList);
    }


    /*    @Override
public Page<ContractCustomerDTO> getAllCustomersPage(int pageNum) {
    //TODO plocka ut 10 ?
    Pageable pageable = PageRequest.of(pageNum - 1, 10);
    Page<customers> page = contractCustomersRepo.findAll(pageable);
    return page.map(ContractCustomerConverter::customersToContractCustomerDto);
}

//TODO test att pageable har pageNumber = pageNum - 1
//TODO test pageSize = 10
//TODO getTotalElements
//TODO getTotalPages
 */

    @Test
    void getAllCustomersPageTest(){
        //TODO test att pageable har pageNumber = pageNum - 1

        //TODO test pageSize = 10
        //TODO getTotalElements
        //TODO getTotalPages

    }

    @Test
    void getAllCustomersSortedPage(){

    }

    @Test
    void getCustomerById(){

    }

    @Test
    void getDetailedContractCustomerById(){
        
    }

    @Test
    void saveAll(){

    }

    /*
    @Override
    public Page<ContractCustomerDTO> getAllCustomersPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 10);
        Page<customers> page = contractCustomersRepo.findAll(pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public Page<ContractCustomerDTO> getAllCustomersSortedPage(int pageNum, String sortBy, String order) {
        Pageable pageable;
        if(order.equals("asc")) {
            pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sortBy).descending());
        }

        Page<customers> page = contractCustomersRepo.findAll(pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public customers getCustomerById(Long id) {
        return contractCustomersRepo.findById(id).orElse(null);
    }

    @Override
    public DetailedContractCustomerDTO getDetailedContractCustomerById(Long id) {
        customers cCustomer = contractCustomersRepo.findById(id).orElse(null);
        if(cCustomer!= null){
            return ContractCustomerConverter.contractCustomerToDetailedContractCustomer(cCustomer);
        }
        return null;
    }

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
    public void addToModel(int currentPage, Model model){
        Page<ContractCustomerDTO> c = getAllCustomersPage(currentPage);
        addToModelUtil(c, model, currentPage);
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

    private void addToModelUtil(Page<ContractCustomerDTO> p, Model model, int currentPage){
        model.addAttribute("allCustomers", p.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", p.getTotalElements());
        model.addAttribute("totalPages", p.getTotalPages());
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
