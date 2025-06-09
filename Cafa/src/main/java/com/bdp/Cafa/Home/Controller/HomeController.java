package com.bdp.Cafa.Home.Controller;

import com.bdp.Cafa.RuleList.Model.RuleList;
import com.bdp.Cafa.RuleList.Repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private RuleRepository ruleRepository;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "home";
    }
    @GetMapping("/rules")
    public String hello(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("activePage", "rules");
        List<RuleList> rules;
        if (keyword != null && !keyword.isEmpty()) {
            rules = ruleRepository.searchRules(keyword);
        } else {
            rules = ruleRepository.findAll();
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("rulesList", rules);
        return "rules";
    }

    @GetMapping("/approval")
    public String approval(Model model) {
        model.addAttribute("activePage", "approval");
        return "approval";
    }

    @GetMapping("/log")
    public String log(Model model) {
        model.addAttribute("activePage", "log");
        return "log";
    }

    @GetMapping("/laporan")
    public String laporan(Model model) {
        model.addAttribute("activePage", "laporan");
        return "laporan";
    }
}
