package com.example.cgw.controller;

import com.example.cgw.JPAData.Items;
import com.example.cgw.JPAData.Orders;
import com.example.cgw.JPAData.Partner;
import com.example.cgw.dao.ItemsRepo;
import com.example.cgw.dao.OrdersRepo;
import com.example.cgw.dao.PartnerRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class Partner_pages {
    @Autowired
    PartnerRepo partnerRepo;

    @Autowired
    OrdersRepo ordersRepo;

    @Autowired
    ItemsRepo itemsRepo;

    private static final Logger logger = LogManager.getLogger(Pages.class);

    @GetMapping("/partner/{id}")
    public Partner getPartner(@PathVariable("id") int id)
    {
        Partner partner=partnerRepo.findById(id);
        return partner;
    }

    @GetMapping ("/partner/inventory/{id}")
    public List<Items> viewInventory(@PathVariable("id") int id)
    {
        logger.info("--View inventory to partner---");
        System.out.println("Inventory");
        Partner p=partnerRepo.findById(id);
        List<Items> items=itemsRepo.findAllByPartner(p);
        for(Items i : items)
            System.out.println(i);
        return items;
    }

    @GetMapping ("/partner/orders/{id}")
    public List<Orders> viewOrders(@PathVariable("id") int id)
    {

       System.out.println("partner orders");
       Partner p=partnerRepo.findById(id);
        logger.info("---View orders for partner "+p.getStoreName()+" ---");
       List<Orders> orders=ordersRepo.findAllByPartner(p);
       return orders;

    }

    @PostMapping(path = "/partner/saveitem")
    public String  saveProductToDB(int shopid,MultipartFile file, String item_name, int qty, double price,  String unit)
    {
        Partner partner=partnerRepo.findById(shopid);
        Items item=new Items();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            item.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        item.setPartner(partner);
        item.setItem_name(item_name);
        item.setQty(qty);
        item.setPrice(price);
        item.setUnit(unit);
        itemsRepo.save(item);
        return "done";
    }


    @PostMapping(path = "/partner/addprofilephoto")
    public String  saveProfile(int shopid,MultipartFile file)
    {
        Partner partner=partnerRepo.findById(shopid);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            partner.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        partnerRepo.save(partner);
        return "done";
    }

}
