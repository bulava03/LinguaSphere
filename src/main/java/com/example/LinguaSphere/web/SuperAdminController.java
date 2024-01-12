package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.Admin;
import com.example.LinguaSphere.entity.Language;
import com.example.LinguaSphere.service.AdminService;
import com.example.LinguaSphere.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/superAdmin")
public class SuperAdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private LanguageService languageService;

    @GetMapping()
    public String getSuperAdminPage() {
        return "superAdmin/superAdminPage";
    }

    @GetMapping("/getLanguagesList")
    public String getLanguagesList(Model model) {
        model.addAttribute("languages", languageService.findAll());
        return "superAdmin/languagesList";
    }

    @GetMapping("/getAdminsList")
    public String getAdminsList(Model model) {
        model.addAttribute("admins", adminService.findAll());
        return "superAdmin/adminsList";
    }

    @GetMapping("/addLanguage")
    public String getLanguageAddingForm() {
        return "superAdmin/addingLanguageForm";
    }

    @PostMapping("addLanguage")
    public String addLanguage(Language language, Model model) {
        languageService.save(language);
        model.addAttribute("languages", languageService.findAll());
        return "superAdmin/languagesList";
    }

    @GetMapping("/addAdmin")
    public String getAdminAddingForm() {
        return "superAdmin/addingAdminForm";
    }

    @PostMapping("/addAdmin")
    public String addAdmin(Admin admin, Model model) {
        adminService.save(admin);
        model.addAttribute("admins", adminService.findAll());
        return "superAdmin/adminsList";
    }

}
