package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;
import com.et.eatingtogether.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService cs;
    private final HttpSession session;

    @GetMapping("/")
    public String myPage(Model model){
        model.addAttribute("customer", cs.findByEmail((String)session.getAttribute("customerLoginEmail")));
        return "customer/mypage";
    }

    @GetMapping("/update")
    public String updateForm(Model model){
        model.addAttribute("customer", cs.findByEmail((String)session.getAttribute("customerLoginEmail")));
        return "customer/update";
    }

    @PutMapping("/update")
    public String updateForm(@ModelAttribute CustomerDetailDTO customerDetailDTO){
        return cs.update(customerDetailDTO);
    }

    @GetMapping("/history")
    public String historyList(Model model){
        List<OrderDTO> orderDTOList = cs.orderList();
        model.addAttribute("orderList",orderDTOList);
        return "customer/orderList";
    }

    @GetMapping("/history/{orderNumber}")
    public String history(@PathVariable("orderNumber") Long orderNumber, Model model){
        OrderDTO orderDTO = cs.findOrder(orderNumber);
        List<OrderMenuDTO> menuDTOList = cs.orderMenu(orderNumber);
        CustomerDetailDTO customerDetailDTO = cs.findById(orderDTO.getCustomerNumber());
        model.addAttribute("order",orderDTO);
        model.addAttribute("menuList",menuDTOList);
        model.addAttribute("customer",customerDetailDTO);
        return "customer/order";
    }

}
