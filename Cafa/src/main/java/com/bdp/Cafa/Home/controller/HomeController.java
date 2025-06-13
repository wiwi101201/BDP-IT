package com.bdp.Cafa.Home.controller;

import com.bdp.Cafa.ruleList.model.RuleList;
import com.bdp.Cafa.ruleList.repository.RuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import iep.cms.starter.schema.models.campaigndefinition.CampaignDefinition;
import iep.cms.starter.schema.models.eventdefinition.EventDefinition;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private RuleRepository ruleRepository;
    @Value("${event.url}")
    private String eventURL;
    @Value("${campaign.url}")
    private String campaignURL;
    @Value("${fraud.url}")
    private String fraudURL;

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
        try {
        RuleList rule = ruleRepository.searchRule(configkey);
        if (rule == null) {
            return "error/errorPage";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(rule.getValue(), Map.class);
            if (rule.getConfigtype().equalsIgnoreCase("EVENT")) {
                EventDefinition eventDefinition = objectMapper.readValue(rule.getValue(), EventDefinition.class);
                model.addAttribute("event",eventDefinition);
            }
            else if (rule.getConfigtype().equalsIgnoreCase("CAMPAIGN")) {
                CampaignDefinition campaignDefinition = objectMapper.readValue(rule.getValue(), CampaignDefinition.class);
                model.addAttribute("campaign",campaignDefinition);
                int length = campaignDefinition.getEligibility().length;

            }
            else if (rule.getConfigtype().equalsIgnoreCase("FRAUD")) {
                System.out.println("masuk fraud");
                List<Map<String, Object>> properties = (List<Map<String, Object>>) map.get("eligibility");
                model.addAttribute("properties",properties);
            }
            model.addAttribute("rule", rule);
            return "ruleDetail";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("errorMessage", "Terjadi kesalahan saat memproses permintaan.");
        return "error/errorPage";
    }

    }

    @GetMapping("/delete-rule")
    public String deleteRule(@RequestParam("configkey") String configKey,@RequestParam("configtype") String configType, RedirectAttributes redirectAttributes) throws MalformedURLException {
        URL url = null;
        if (configType.equalsIgnoreCase("EVENT")) {
            url = new URL(eventURL+configKey);
        } else if (configType.equalsIgnoreCase("FRAUD")) {
            url = new URL(fraudURL+configKey);
        } else if (configType.equalsIgnoreCase("CAMPAIGN")) {
            url = new URL(campaignURL+configKey);
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println(response);
            } else {
                System.out.println("Request failed: " + responseCode);
            }
            connection.disconnect();
        } catch (ProtocolException exception) {
            throw new RuntimeException(exception);
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        try {
            redirectAttributes.addFlashAttribute("message", "Rule berhasil dihapus.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus rule: " + e.getMessage());
        }
        return "redirect:/rules";
    }

    @GetMapping("/rules/create")
    public String createRuleForm() {
        return "createRule";
    }
}
