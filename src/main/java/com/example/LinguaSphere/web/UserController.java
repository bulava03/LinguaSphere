package com.example.LinguaSphere.web;

import com.example.LinguaSphere.config.security.JwtTokenService;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.LoginDto;
import com.example.LinguaSphere.entity.dto.RequestDto;
import com.example.LinguaSphere.entity.dto.UserDto;
import com.example.LinguaSphere.helper.ConvertHelper;
import com.example.LinguaSphere.service.UserService;
import com.example.LinguaSphere.service.impl.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Collections;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("userPage")
    public String userPage(@ModelAttribute("request") RequestDto request, Model model) {
        String token = request.getToken();

        String[] segments = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(segments[1]));

        JsonNode node;
        String username;

        ObjectMapper mapper = new ObjectMapper();

        try {
            node = mapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            String day = String.valueOf(userFounded.getDateOfBirth().getDayOfMonth() + 1);
            String month = ConvertHelper.monthToStringUserPage(userFounded.getDateOfBirth().getMonthValue());
            String year = String.valueOf(userFounded.getDateOfBirth().getYear());
            String date = day + " " + month + " " + year;
            userDto.setDateOfBirth(date);
            model.addAttribute("user", userDto);
            model.addAttribute("token", token);
            return "user/userPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("userSchedule")
    public String getUserSchedule(@ModelAttribute("request") RequestDto request, Model model) {
        String token = request.getToken();

        String[] segments = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(segments[1]));

        JsonNode node;
        String username;

        ObjectMapper mapper = new ObjectMapper();

        try {
            node = mapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            String day = String.valueOf(userFounded.getDateOfBirth().getDayOfMonth() + 1);
            String month = ConvertHelper.monthToStringUserPage(userFounded.getDateOfBirth().getMonthValue());
            String year = String.valueOf(userFounded.getDateOfBirth().getYear());
            String date = day + " " + month + " " + year;
            userDto.setDateOfBirth(date);
            model.addAttribute("user", userDto);
            model.addAttribute("token", token);
            return "user/userSchedule";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

}
