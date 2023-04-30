package matura.maturauebung.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Marke;
import matura.maturauebung.repository.AdminRepository;
import matura.maturauebung.repository.MarkenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class MarkeController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MarkenRepository MarkeRepository;

    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/marke/create")
    private ResponseEntity<Marke> createCusotmer(@Parameter(description = "the request body") @RequestBody Marke Marke,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            if (admin != null) {

                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();

                MarkeRepository.save(Marke);
                MarkeRepository.flush();

                return new ResponseEntity<>(Marke, HttpStatus.OK);
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
    @PostMapping("/marke/update")
    private ResponseEntity<Marke> updateCustomer(@Parameter(description = "the request body") @RequestBody Marke marke,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Marke> optionalMarke = MarkeRepository.findById(marke.getMarkeID());

            if (admin != null) {
                if (optionalMarke.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Marke m = optionalMarke.get();
                    m.setName(m.getName());
                    MarkeRepository.save(m);
                    MarkeRepository.flush();

                    return new ResponseEntity<>(m, HttpStatus.OK);
                }
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
    @DeleteMapping("/marke/delete/{id}")
    private ResponseEntity<Marke> deletecustomer(@Parameter(description = "the request body")
                                                  @PathVariable("id") Long id,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Marke> optionalMarke = MarkeRepository.findById(id);

            if (admin != null) {
                if (optionalMarke.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Marke k = optionalMarke.get();
                    MarkeRepository.delete(k);
                    MarkeRepository.flush();

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


    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })


    @GetMapping("/marke/load")
    private ResponseEntity<ArrayList<Marke>> loadCustomers(@Parameter(description = "the request body")
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());

            ArrayList<Marke> MarkeArrayList;

            if (admin != null) {
                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();
                MarkeArrayList = new ArrayList<>(MarkeRepository.findAll());
                return new ResponseEntity<>(MarkeArrayList, HttpStatus.OK);

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


    @GetMapping("/marke/load/{id}")
    private ResponseEntity<Marke> loadCustomer(@Parameter(description = "the request body")
                                                @PathVariable("id") Long id,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Marke> optionalMarke = MarkeRepository.findById(id);

            if (admin != null) {
                if (optionalMarke.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Marke k = optionalMarke.get();
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
