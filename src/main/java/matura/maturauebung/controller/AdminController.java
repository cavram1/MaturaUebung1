package matura.maturauebung.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import matura.maturauebung.model.Admin;
import matura.maturauebung.repository.AdminRepository;
import matura.maturauebung.service.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;


    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "logged in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while logging in",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/admin/register")
    private ResponseEntity<Admin> register(@Parameter(description = "the request body") @RequestBody Admin a) {

        try {
            if (a != null) {
                adminRepository.save(a);
                adminRepository.flush();

                return new ResponseEntity<>(a, HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "logged in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while logging in",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/admin/login")
    private ResponseEntity<Admin> login(@Parameter(description = "the request body") @RequestBody Admin a) {

        Admin admin = null;

        try {
            admin = adminRepository.findFirstByEmailAndPassword(a.getEmail(), a.getPassword());
            if (admin != null) {

                String token = HashGenerator.generateHash(a);
                admin.setToken(token);
                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();

                return new ResponseEntity<>(a, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(a, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
