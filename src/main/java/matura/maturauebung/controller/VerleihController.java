package matura.maturauebung.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Verleih;
import matura.maturauebung.repository.AdminRepository;
import matura.maturauebung.repository.VerleihRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class VerleihController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VerleihRepository verleihRepository;

    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/verleih/leihen")
    private ResponseEntity<Verleih> createNewVerleihung(@Parameter(description = "the request body")
                                                        @RequestBody Verleih verleih,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            if (admin != null) {

                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();

                verleihRepository.save(verleih);
                verleihRepository.flush();

                return new ResponseEntity<>(verleih, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })


    @GetMapping("/rental/period/{from}/{to}")
    private ResponseEntity<ArrayList<Verleih>> getFreeModells(@Parameter(description = "the request body")
                                                              @PathVariable("from") LocalDate from,
                                                              @PathVariable("to") LocalDate to,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());

            ArrayList<Verleih> VerleihArrayList;

            if (admin != null) {
                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();
                VerleihArrayList = new ArrayList<>(verleihRepository.findAll());
                return new ResponseEntity<>(VerleihArrayList, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })


    @GetMapping("/Verleih/load/{id}")
    private ResponseEntity<Verleih> loadCustomer(@Parameter(description = "the request body")
                                                 @PathVariable("id") Long id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Verleih> optionalVerleih = verleihRepository.findById(id);

            if (admin != null) {
                if (optionalVerleih.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Verleih k = optionalVerleih.get();
                    return new ResponseEntity<>(k, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
