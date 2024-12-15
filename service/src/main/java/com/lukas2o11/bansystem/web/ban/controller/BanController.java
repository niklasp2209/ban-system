package com.lukas2o11.bansystem.web.ban.controller;

import com.github.lukas2o11.bansystem.api.Ban;
import com.lukas2o11.bansystem.web.ban.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(
        path = "/api/v1/bans",
        produces = "application/json"
)
public class BanController {

    private final BanService service;

    @Autowired
    public BanController(BanService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Ban> getBanById(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.getBanById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Ban>> listBans(@RequestParam("page") Optional<Integer> page, @RequestParam("pageSize") Optional<Integer> pageSize) {
        return ResponseEntity.ok(service.listBans(page, pageSize));
    }
}
