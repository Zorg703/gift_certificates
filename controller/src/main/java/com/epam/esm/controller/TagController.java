package com.epam.esm.controller;

import com.epam.esm.dto.PageParamDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The Tag controller.
 * This class gets request on url /rest/tags
 * and pass response
 */
@RestController
@RequestMapping("/rest/tags")
public class TagController {
    private TagService service;


    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Get all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    @PreAuthorize("(hasAuthority('admin') or (hasAuthority('user'))) AND #oauth2.hasScope('read')")
    public ResponseEntity<List<TagDTO>> getAll(@Valid PageParamDTO param, Errors error) {
        if (error.hasErrors()) {
            throw new ControllerException();
        }
        return new ResponseEntity<>(service.getAll(param), HttpStatus.OK);
    }


    @GetMapping("/toptag")
    @PreAuthorize("(hasAuthority('admin') or (hasAuthority('user'))) AND #oauth2.hasScope('read')")
    public ResponseEntity<List<UserDTO>> getTopTag(final @RequestParam(value = "mode") String mode) {
        List<UserDTO> tags = new ArrayList<>();
        return "popular".equals(mode) ? new ResponseEntity<>(service.getTopTags(), HttpStatus.OK) : new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Get one response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    @PreAuthorize("(hasAuthority('admin') or (hasAuthority('user'))) AND #oauth2.hasScope('read')")
    public ResponseEntity<TagDTO> getOne(final @PathVariable long id) {
        return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
    }

    /**
     * Create response entity.
     *
     * @param tag the tag
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("(hasAuthority('admin')) AND #oauth2.hasScope('write')")
    public ResponseEntity<TagDTO> create(final @Valid @RequestBody TagDTO tag) {
        service.create(tag);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("(hasAuthority('admin')) AND #oauth2.hasScope('write')")
    public ResponseEntity<TagDTO> delete(final @PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
