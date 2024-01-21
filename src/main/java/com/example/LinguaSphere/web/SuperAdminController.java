package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.Admin;
import com.example.LinguaSphere.entity.Language;
import com.example.LinguaSphere.entity.dto.AdminUpdate;
import com.example.LinguaSphere.service.AdminService;
import com.example.LinguaSphere.service.LanguageService;
import com.example.LinguaSphere.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/superAdmin")
public class SuperAdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private LessonService lessonService;

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
        Object[] validation = languageService.validateLanguage(language);
        if (!(boolean) validation[0]) {
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "superAdmin/addingLanguageForm";
        } else {
            languageService.save(language);
            model.addAttribute("languages", languageService.findAll());
            return "superAdmin/languagesList";
        }
    }

    @GetMapping("/addAdmin")
    public String getAdminAddingForm() {
        return "superAdmin/addingAdminForm";
    }

    @PostMapping("/addAdmin")
    public String addAdmin(Admin admin, Model model) {
        Object[] validation = adminService.validateAdmin(admin);
        if (!(boolean) validation[0]) {
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "superAdmin/addingAdminForm";
        } else {
            adminService.save(admin);
            model.addAttribute("admins", adminService.findAll());
            return "superAdmin/adminsList";
        }
    }

    @PostMapping("/deleteAdmin")
    public String deleteAdmin(Admin admin, Model model) {
        Admin adminRemove = adminService.findByLogin(admin.getLogin());
        if (adminRemove != null && admin.getPassword().equals(adminRemove.getPassword())) {
            adminService.deleteById(adminRemove.getId());
        }

        model.addAttribute("admins", adminService.findAll());
        return "superAdmin/adminsList";
    }

    @GetMapping("/updateAdmin")
    public String getUpdateAdminForm(Admin adminToUpdate, Model model) {
        Admin admin = adminService.findByLogin(adminToUpdate.getLogin());
        if (admin != null && adminToUpdate.getPassword().equals(admin.getPassword())) {
            model.addAttribute("admin", admin);
            return "superAdmin/updateAdminForm";
        }

        model.addAttribute("admins", adminService.findAll());
        return "superAdmin/adminsList";
    }

    @PostMapping("/updateAdmin")
    public String updateAdmin(AdminUpdate adminUpdated, Model model) {
        Admin admin = adminService.findByLogin((adminUpdated.getOldLogin()));
        if (admin != null && admin.getPassword().equals(adminUpdated.getOldPassword())) {
            Admin toValidate = new Admin(adminUpdated.getLogin(), adminUpdated.getPassword());

            Object[] validation = adminService.validateAdmin(toValidate);
            if (!(boolean) validation[0]) {
                model.addAttribute("admin", adminService.findByLogin(adminUpdated.getOldLogin()));
                model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
                return "superAdmin/updateAdminForm";
            } else {
                admin.setLogin(adminUpdated.getLogin());
                admin.setPassword(adminUpdated.getPassword());
                adminService.save(admin);
            }
        }

        model.addAttribute("admins", adminService.findAll());
        return "superAdmin/adminsList";
    }

    @PostMapping("/deleteLanguage")
    public String deleteLanguage(Language language, Model model) {
        Language languageRemove = languageService.findByName(language.getName());
        if (languageRemove != null) {
            languageService.deleteById(languageRemove.getId());
        }

        model.addAttribute("languages", languageService.findAll());
        return "superAdmin/languagesList";
    }

    @GetMapping("/updateLanguage")
    public String getUpdateLanguageForm(Language languageToUpdate, Model model) {
        Language language = languageService.findByName(languageToUpdate.getName());
        if (language != null) {
            model.addAttribute("language", language);
            return "superAdmin/updateLanguageForm";
        }

        model.addAttribute("languages", languageService.findAll());
        return "superAdmin/languagesList";
    }

    @PostMapping("/updateLanguage")
    public String updateLanguage(Language language, Model model) {
        Object[] validation = languageService.validateLanguage(language);
        if (!(boolean) validation[0]) {
            model.addAttribute("language", languageService.findById(language.getId()));
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "superAdmin/updateLanguageForm";
        } else {
            languageService.save(language);
            model.addAttribute("languages", languageService.findAll());
            return "superAdmin/languagesList";
        }
    }

}
