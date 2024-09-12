package com.smp.currency_rate.controller;

import com.smp.currency_rate.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/")
    public String getYuanRate(Model model) {
        try {
            BigDecimal yuanRate = currencyService.getYuanRate();
            model.addAttribute("yuanRate", yuanRate);
            model.addAttribute("currentDate", LocalDate.now());
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching Yuan rate: " + e.getMessage());
            return "error";
        }
    }
}
