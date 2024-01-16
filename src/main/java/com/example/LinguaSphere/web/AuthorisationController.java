package com.example.LinguaSphere.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.LinguaSphere.config.security.JwtTokenService;
import com.example.LinguaSphere.entity.Teacher;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.LoginDto;
import com.example.LinguaSphere.entity.dto.TeacherDto;
import com.example.LinguaSphere.entity.dto.UserDto;
import com.example.LinguaSphere.helper.ConvertHelper;
import com.example.LinguaSphere.service.TeacherService;
import com.example.LinguaSphere.service.UserService;
import com.example.LinguaSphere.service.impl.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/authorisation")
public class AuthorisationController {

    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public String authorisation() {
        return "authorisation/authorisation";
    }

    @PostMapping("/student_authorisation")
    public String authorisationStudent(@ModelAttribute("authenticationRequest") LoginDto authenticationRequest, Model model) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword(), Collections.emptyList()));
        } catch (final BadCredentialsException ex) {
            model.addAttribute("errorText", "Логін або пароль ввидено неправильно!");
            return "authorisation/authorisation";
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtTokenService.generateToken(userDetails);

        /*String[] segments = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(segments[0]));
        String payload = new String(decoder.decode(segments[1]));*/

        User userFounded = userService.findByEmail(authenticationRequest.getEmail()).orElse(null);

        UserDto userDto = modelMapper.map(userFounded, UserDto.class);
        String day = String.valueOf(userFounded.getDateOfBirth().getDayOfMonth() + 1);
        String month = ConvertHelper.monthToStringUserPage(userFounded.getDateOfBirth().getMonthValue());
        String year = String.valueOf(userFounded.getDateOfBirth().getYear());
        String date = day + " " + month + " " + year;
        userDto.setDateOfBirth(date);
        model.addAttribute("user", userDto);
        model.addAttribute("token", token);
        return "user/userPage";
    }

    @PostMapping("/teacher_authorisation")
    public String authorisationTeacher(@ModelAttribute("authenticationRequest") LoginDto authenticationRequest, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(authenticationRequest.getEmail());
        if (teacherFounded != null) {
            TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
            model.addAttribute("teacher", teacherDto);
            return "teacher/teacherPage";
        } else {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }
    }

}
