package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePagingDTO;
import com.epam.esm.dto.PageParamDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DataReaderFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Gift certificate controller.
 * This class gets request on url /rest/gift_certificates
 * and pass response
 */
@RestController
@RequestMapping("/rest/gift_certificates")
public class GiftCertificateController {
    private GiftCertificateService service;
    private OrderService orderService;

    @Autowired
    private DataReaderFromToken dataReaderFromToken;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param service      the service
     * @param orderService the order service
     */
    public GiftCertificateController(GiftCertificateService service, OrderService orderService) {
        this.service = service;
        this.orderService = orderService;
    }
    
    @GetMapping
    public ResponseEntity<GiftCertificatePagingDTO> getAll(final @RequestParam(value = "text", required = false) String text,
                                                           final @RequestParam(value = "tags", required = false) String tag,
                                                           final @RequestParam(value = "sort", required = false) String sort,
                                                           final @Valid PageParamDTO param, Errors error) {
        List<String> tags=new ArrayList<>();
        if(error.hasErrors()){
            throw new ControllerException();
        }
        if (!(StringUtils.isEmpty(tag))) {
            tags = new ArrayList<>(Arrays.asList(tag.split(",")));
        }
        return new ResponseEntity<>(service.getGiftCertificatesWithConditions(tags, param,text, sort), HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> getOne(final @PathVariable long id) {
        GiftCertificateDTO giftCertificateDTO=service.getOne(id);
        if(giftCertificateDTO!=null){
            return new ResponseEntity<>(giftCertificateDTO, HttpStatus.OK);
        }
        else {
            throw new ControllerException();
        }
    }

    /**
     * Create response entity.
     *
     * @param giftCertificate the gift certificate
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("(hasAuthority('admin')) AND #oauth2.hasScope('write')")
    public ResponseEntity<GiftCertificateDTO> create(final @RequestBody GiftCertificateDTO giftCertificate) {
        service.create(giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update response entity.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the response entity
     */
    @PutMapping("/{id}")
    @PreAuthorize("(hasAuthority('admin')) AND #oauth2.hasScope('write')")
    public ResponseEntity<GiftCertificateDTO> update(final @PathVariable long id, final @RequestBody GiftCertificateDTO giftCertificate) {
        service.update(id, giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("(hasAuthority('admin')) AND #oauth2.hasScope('write')")
    public ResponseEntity<GiftCertificateDTO> delete(final @PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin') AND #oauth2.hasScope('write')")
    public ResponseEntity<GiftCertificateDTO> changePrice(final @PathVariable long id, final @RequestBody GiftCertificateDTO giftCertificate) {
        service.changePrice(id, giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/orders")
    @PreAuthorize("(hasAuthority('admin') or (hasAuthority('user'))) AND #oauth2.hasScope('read')")
    public ResponseEntity<GiftCertificateDTO> doOrder(final @PathVariable long id,final @RequestBody Integer count) {
        String name = dataReaderFromToken.getUserNameFromToken();
        orderService.makeOrder(name, id,count);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/check")
    public ResponseEntity<List<GiftCertificateDTO>> checkOrder(final @RequestBody List<GiftCertificateDTO> dtoList ) {
       List<GiftCertificateDTO> list=service.checkCertificatesList(dtoList);
       return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
