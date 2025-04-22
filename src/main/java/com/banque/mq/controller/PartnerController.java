package com.banque.mq.controller;

import com.banque.mq.model.dto.PartnerDTO;
import com.banque.mq.service.PartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public Page<PartnerDTO> getAllPartners(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return partnerService.getAll(pageable);
    }

    @PostMapping
    public void createPartner(@Valid @RequestBody PartnerDTO dto) {
        partnerService.save(dto);
    }

    @DeleteMapping("/{alias}")
    public void deletePartner(@PathVariable Long id) {
        partnerService.delete(id);
    }
}