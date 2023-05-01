package matura.maturauebung.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Kunde;
import matura.maturauebung.repository.AdminRepository;
import matura.maturauebung.repository.KundenRepository;
import matura.maturauebung.service.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class KundenController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private KundenRepository kundenRepository;

    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/kunde/create")
    private ResponseEntity<Kunde> createCusotmer(@Parameter(description = "the request body") @RequestBody Kunde kunde,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            if (admin != null) {

                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();

                if(kunde.getEmail().length() > 4){
                    throw new Exception("email to long");
                }

                kundenRepository.save(kunde);
                kundenRepository.flush();

                return new ResponseEntity<>(kunde, HttpStatus.OK);
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
    @PostMapping("/kunde/update")
    private ResponseEntity<Kunde> updateCustomer(@Parameter(description = "the request body") @RequestBody Kunde kunde,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Kunde> optionalKunde = kundenRepository.findById(kunde.getKundenID());

            if (admin != null) {
                if (optionalKunde.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Kunde k = optionalKunde.get();
                    k.setEmail(kunde.getEmail());
                    k.setName(kunde.getName());
                    kundenRepository.save(k);
                    kundenRepository.flush();

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
    @DeleteMapping("/kunde/delete/{id}")
    private ResponseEntity<Kunde> deletecustomer(@Parameter(description = "the request body")
                                                 @PathVariable("id") Long id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Kunde> optionalKunde = kundenRepository.findById(id);

            if (admin != null) {
                if (optionalKunde.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Kunde k = optionalKunde.get();
                    kundenRepository.delete(k);
                    kundenRepository.flush();

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


    @GetMapping("/kunde/load")
    private ResponseEntity<ArrayList<Kunde>> loadCustomers(@Parameter(description = "the request body")
                                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());

            ArrayList<Kunde> kundeArrayList;

            if (admin != null) {
                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();
                kundeArrayList = new ArrayList<>(kundenRepository.findAll());
                return new ResponseEntity<>(kundeArrayList, HttpStatus.OK);

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


    @GetMapping("/kunde/load/{id}")
    private ResponseEntity<Kunde> loadCustomer(@Parameter(description = "the request body")
                                                          @PathVariable("id") Long id,
                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Kunde> optionalKunde = kundenRepository.findById(id);

            if (admin != null) {
                if(optionalKunde.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Kunde k = optionalKunde.get();
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
