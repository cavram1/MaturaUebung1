package matura.maturauebung.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Modell;
import matura.maturauebung.repository.AdminRepository;
import matura.maturauebung.repository.ModellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class ModellController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModellRepository modellRepository;

    @Operation(description = "sumthing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created in successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "error occurred while creating customer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "unauthorized",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/modell/create")
    private ResponseEntity<Modell> createCusotmer(@Parameter(description = "the request body") @RequestBody Modell Modell,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            if (admin != null) {

                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();

                modellRepository.save(Modell);
                modellRepository.flush();

                return new ResponseEntity<>(Modell, HttpStatus.OK);
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
    @PostMapping("/modell/update")
    private ResponseEntity<Modell> updateCustomer(@Parameter(description = "the request body") @RequestBody Modell modell,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Modell> optionalModell = modellRepository.findById(modell.getModelID());

            if (admin != null) {
                if (optionalModell.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Modell m = optionalModell.get();
                    m.setMarke(modell.getMarke());
                    m.setName(modell.getName());
                    modellRepository.save(m);
                    modellRepository.flush();

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
    @DeleteMapping("/modell/delete/{id}")
    private ResponseEntity<Modell> deletecustomer(@Parameter(description = "the request body")
                                                  @PathVariable("id") Long id,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Modell> optionalModell = modellRepository.findById(id);

            if (admin != null) {
                if (optionalModell.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Modell k = optionalModell.get();
                    modellRepository.delete(k);
                    modellRepository.flush();

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


    @GetMapping("/modell/load")
    private ResponseEntity<ArrayList<Modell>> loadCustomers(@Parameter(description = "the request body")
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());

            ArrayList<Modell> ModellArrayList;

            if (admin != null) {
                admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                adminRepository.save(admin);
                adminRepository.flush();
                ModellArrayList = new ArrayList<>(modellRepository.findAll());
                return new ResponseEntity<>(ModellArrayList, HttpStatus.OK);

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


    @GetMapping("/modell/load/{id}")
    private ResponseEntity<Modell> loadCustomer(@Parameter(description = "the request body")
                                                @PathVariable("id") Long id,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Admin admin = null;
        token = token.replace("Bearer ", "");

        try {
            admin = adminRepository.findFirstByTokenAndExpireingTimeGreaterThan(token, System.currentTimeMillis());
            Optional<Modell> optionalModell = modellRepository.findById(id);

            if (admin != null) {
                if (optionalModell.isPresent()) {
                    admin.setExpireingTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    adminRepository.save(admin);
                    adminRepository.flush();

                    Modell k = optionalModell.get();
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
