package com.shop.controller;

import com.shop.entity.RegistrationToken;
import com.shop.entity.Role;
import com.shop.entity.RoleEnum;
import com.shop.entity.User;
import com.shop.event.SendEmailEvent;
import com.shop.service.JwtTokenService;
import com.shop.service.RegistrationTokenService;
import com.shop.service.RoleService;
import com.shop.service.UserService;
import com.shop.validation.groups.Current;
import com.shop.validation.groups.DefaultFirst;
import com.shop.validation.groups.Exists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenService jwtTokenService;
    private final RegistrationTokenService registrationTokenService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${react.origin}")
    private String origin;

    private void sendRegistrationToken(User user, HttpServletRequest request) {
        RegistrationToken registrationToken = registrationTokenService.generateAndSaveToken(user);
        SendEmailEvent userSendEmailEvent = new SendEmailEvent(
                user.getEmail(),
                "registration.confirm.subject",
                "registration-confirm.html",
                request.getLocale(),
                Map.of("user", user,
                        "url", String.format("%sconfirm-registration?token=%s", origin,
                                registrationToken.getToken())));
        eventPublisher.publishEvent(userSendEmailEvent);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Validated(DefaultFirst.class) User user, HttpServletRequest request) {
        userService.save(user);
        roleService.save(new Role(RoleEnum.ROLE_USER, user));
        sendRegistrationToken(user, request);
        return user;
    }

    @GetMapping("/confirm-registration")
    public User confirmRegistration(@Validated({Exists.class, Current.class}) RegistrationToken token,
                                    HttpServletResponse response) throws BindException {
        RegistrationToken validatedToken = registrationTokenService.validateToken(token);
        User user = validatedToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        jwtTokenService.authWithoutPassword(user, response);
        return user;
    }

    @GetMapping("/resend-registration-token")
    public User resendRegistrationTokenByUsername(@Validated({Exists.class}) RegistrationToken token,
                                                  HttpServletRequest request) {
        RegistrationToken existingToken = registrationTokenService.findByToken(token.getToken());
        User user = existingToken.getUser();
        sendRegistrationToken(user, request);
        return user;
    }

}