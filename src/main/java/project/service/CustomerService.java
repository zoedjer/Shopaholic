package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dbaccess.CustomerTable;
import project.entity.Customer;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerTable customerTab;

    public List<Customer> getAll(Long userId, int from) {
	return customerTab.getAllCustomer(userId, from);
    }

    public void addAddress(Long id, String lineOne, String lineTwo, String city, String county, String country) {
	customerTab.addAddress(id, lineOne, lineTwo, city, county, country);
    }

    public long createCustomer(Long userId, String first_name, String last_name, String phone, String email,
	    String line_one, String line_two, String city, String county, String country) {
	return customerTab.insert(userId, first_name, last_name, phone, email, line_one, line_two, city, county,
		country);
    }

    public void editCustomer(Long id, String first_name, String last_name, String phone, String email, String line_one,
	    String line_two, String city, String county, String country) {
	customerTab.edit(id, first_name, last_name, phone, email, line_one, line_two, city, county, country);
    }

    public void removeCustomer(long... ids) {
	customerTab.remove(ids);
    }

}
