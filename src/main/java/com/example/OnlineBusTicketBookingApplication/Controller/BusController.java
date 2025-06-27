package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "searchBus";
    }

    @PostMapping("/search")
    public String searchBuses(@ModelAttribute Bus bus, Model model) {
        List<Bus> buses = busService.searchBuses(bus.getSource(), bus.getDestination());
        model.addAttribute("buses", buses);
        return "busList";
    }

    @GetMapping("/add")
    public String addBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "addBus";
    }

    @PostMapping("/add")
    public String saveBus(@ModelAttribute Bus bus) {
        busService.saveBus(bus);
        return "redirect:/buses/add?success";
    }
    @GetMapping("/manage")
    public String listBuses(Model model) {
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);
        return "busManage"; // Will render busManage.html
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Bus bus = busService.getBusById(id);
        model.addAttribute("bus", bus);
        return "addBus"; // Reuse the addBus form
    }

    @GetMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id) {
        busService.deleteBusById(id);
        return "redirect:/buses/manage";
    }
}
