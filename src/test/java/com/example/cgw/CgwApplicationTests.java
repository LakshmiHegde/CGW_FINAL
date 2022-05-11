package com.example.cgw;

import com.example.cgw.JPAData.*;
import com.example.cgw.dao.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class CgwApplicationTests {

    @Autowired
    PartnerRepo partnerRepo;

    @Autowired
    DeliveryRepo deliveryRepo;
    @Autowired
    AddressRepo addressRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ItemsRepo itemsRepo;
    @Test
    void Address() {
        List<Address> addresses=addressRepo.findByPincode("560003");
        Assert.assertEquals(1,addresses.size());
        Address address=addressRepo.findByLocAndCityAndState("Sainagar","Bangalore","Karnataka","583231");
        Assert.assertEquals(null,address);
    }

    @Test
    void Customer()
    {
        Customer customer=customerRepo.findByUsername("adhya");
        Assert.assertNotEquals(null,customer);
    }
    @Test
    void Delivery()
    {
        Delivery delivery= deliveryRepo.findByUsername("suresh");
        Assert.assertNotEquals(null,delivery);
    }
    @Test
    void Items()
    {
        List<Items> items=itemsRepo.findAllByPartner(partnerRepo.findByUsername("kamathstores"));
        Assert.assertNotEquals(0,items.size());
    }
    @Test
    void Partner()
    {
        List<Partner> partners=partnerRepo.findByStoreLoc("Vijay Nagar","sweet");
        Assert.assertEquals(2,partners.size());
        partners=partnerRepo.findByStoreLoc("Vijay Nagar","gift");
        Assert.assertEquals(1,partners.size());
        Partner partner=partnerRepo.findByStoreName("Bake n' Take");
        Assert.assertNotEquals(null,partner);
    }
}
