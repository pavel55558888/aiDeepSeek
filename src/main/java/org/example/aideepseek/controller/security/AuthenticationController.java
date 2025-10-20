package org.example.aideepseek.controller.security;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.aideepseek.dto.AuthenticationDTO;
import org.example.aideepseek.dto.AuthenticationResponse;
import org.example.aideepseek.dto.ErrorDTO;
import org.example.aideepseek.security.services.jwt.UserDetailsServiceImpl;
import org.example.aideepseek.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Авторизация пользователя", description = "Получение токена, для дальнейшего взаимодействия с api")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final ErrorDTO errorDto = new ErrorDTO();

    @Operation(summary = "Авторизация", description = "Принимает данные пользователя и возвращает jwt токен, который живет n часов")
    @ApiResponse(responseCode = "200", description = "Успешно авторизовались, в ответ получили токен")
    @ApiResponse(
            responseCode = "400",
            description = "Валидация не прошла",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
    )
    @PostMapping("/authenticate")
    public <T> T createAuthenticationToken(@Valid @RequestBody AuthenticationDTO authenticationDTO, BindingResult bindingResult, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        if (bindingResult.hasErrors()){
            errorDto.setListError(bindingResult.getAllErrors());
            return (T) errorDto;
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверные данные");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Пользователь не найден");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return (T) new AuthenticationResponse(jwt);

    }

}
