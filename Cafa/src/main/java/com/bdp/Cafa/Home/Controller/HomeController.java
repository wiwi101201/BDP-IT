package com.bdp.Cafa.Home.Controller;

import com.bdp.Cafa.RuleList.Model.RuleList;
import com.bdp.Cafa.RuleList.Repository.RuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
    public String rules(@RequestParam(name = "keyword", required = false) String keyword, Model model, HttpSession session) {
        model.addAttribute("activePage", "rules");
        List<RuleList> rules;
        if (keyword != null && !keyword.isEmpty()) {
            rules = ruleRepository.searchRules(keyword);
        } else {
            rules = ruleRepository.findAll();
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("ruleLists", rules);
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

    @GetMapping("/rule-detail")  // Changed to handle the form submission
    public String showRuleDetail(@RequestParam(name = "configkey", required = false) String configkey, Model model) {
        RuleList rule = ruleRepository.searchRule(configkey);
        if (rule == null) {
            return "error"; // Or redirect:/rules
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(rule.getValue(), Map.class);
            if (rule.getConfigtype().equalsIgnoreCase("EVENT")) {
                List<Map<String, Object>> properties = (List<Map<String, Object>>) map.get("properties");
                for (Map<String, Object> prop : properties)
                {

                    for (Map.Entry<String, Object> entry : prop.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();

                        System.out.println("Key: " + key + ", Value: " + value);
                    }
                }
                model.addAttribute("properties",properties);
            }
            else if (rule.getConfigtype().equalsIgnoreCase("CAMPAIGN")) {
                System.out.println("masuk campaign");
            }
            model.addAttribute("rule", rule);
            return "ruleDetail";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
