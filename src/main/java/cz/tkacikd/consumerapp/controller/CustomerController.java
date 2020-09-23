package cz.tkacikd.consumerapp.controller;

import cz.tkacikd.consumerapp.domain.CustomerRegisterData;
import cz.tkacikd.consumerapp.domain.Token;
import cz.tkacikd.consumerapp.jwt.Jwt;
import cz.tkacikd.consumerapp.domain.Customer;
import cz.tkacikd.consumerapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class CustomerController {

    private CustomerService cstService;

    @Autowired
    CustomerController(CustomerService cstService) {
        this.cstService = cstService;
    }

    @PostMapping("/createCustomer")
    public Customer save(@RequestBody Customer cst) {
        return cstService.saveNewCustomer(cst);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody CustomerRegisterData crd) {
        String email = crd.getEmail();
        String password = crd.getPassword();
        Customer customer = cstService.authenticateCustomer(email, password);
        return new ResponseEntity<>(Jwt.generateJWTToken(customer), HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestBody Token token) {
        return Jwt.validateJwtToken(token.getToken());
    }
}
