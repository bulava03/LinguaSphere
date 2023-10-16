package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.UserDto;
import com.example.LinguaSphere.entity.dto.UserForm;
import com.example.LinguaSphere.helper.ConvertHelper;
import com.example.LinguaSphere.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public String getRegistrationPage(Model model) {
        UserForm user = new UserForm();
        user.setYear(-1);
        model.addAttribute("user", user);
        return "registration/registration";
    }

    @PostMapping()
    public String registration(@ModelAttribute("user") User user, Model model) {
        Object[] validation = userService.validateUser(user);
        if (!(boolean) validation[0]) {
            UserForm userForm = modelMapper.map(user, UserForm.class);
            userForm.setDay(user.getDateOfBirth().getDayOfMonth());
            userForm.setMonth(ConvertHelper.monthToString(user.getDateOfBirth().getMonthValue()));
            userForm.setYear(user.getDateOfBirth().getYear());

            model.addAttribute("user", userForm);
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "registration/registration";
        } else if (userService.findByEmail(user.getEmail()).isPresent()) {
            UserForm userForm = modelMapper.map(user, UserForm.class);
            userForm.setDay(user.getDateOfBirth().getDayOfMonth());
            userForm.setMonth(ConvertHelper.monthToString(user.getDateOfBirth().getMonthValue()));
            userForm.setYear(user.getDateOfBirth().getYear());

            model.addAttribute("user", userForm);
            model.addAttribute("errorText", "Таку електронну пошту вже зайнято!");
            return "registration/registration";
        } else {
            user.setDateOfBirth(LocalDateTime.of(user.getDateOfBirth().getYear(), user.getDateOfBirth().getMonthValue(), user.getDateOfBirth().getDayOfMonth(), 0, 0, 0));
            userService.addUser(user);

            UserDto userDto = modelMapper.map(user, UserDto.class);
            String day = String.valueOf(user.getDateOfBirth().getDayOfMonth() + 1);
            String month = ConvertHelper.monthToStringUserPage(user.getDateOfBirth().getMonthValue());
            String year = String.valueOf(user.getDateOfBirth().getYear());
            String date = day + " " + month + " " + year;
            userDto.setDateOfBirth(date);
            model.addAttribute("user", userDto);
            return "user/userPage";
        }
    }

}
