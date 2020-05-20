package com.blesk.userservice.Controller;

import com.blesk.userservice.DTO.JwtMapper;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Genders;
import com.blesk.userservice.Service.Genders.GendersServiceImpl;
import com.blesk.userservice.Value.Keys;
import com.blesk.userservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class GendersResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private GendersServiceImpl gendersService;

    @Autowired
    public GendersResource(GendersServiceImpl gendersService) {
        this.gendersService = gendersService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PostMapping("/genders")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Genders> createGenders(@Valid @RequestBody Genders genders, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_GENDERS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Genders gender = this.gendersService.createGender(genders);
        if (gender == null) throw new UserServiceException(Messages.CREATE_GENDER, HttpStatus.BAD_REQUEST);

        EntityModel<Genders> entityModel = new EntityModel<Genders>(gender);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveGenders(gender.getGenderId(), httpServletRequest, httpServletResponse)).withRel("gender"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @DeleteMapping("/genders/{genderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteGenders(@PathVariable long genderId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("DELETE_GENDERS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Genders gender = this.gendersService.getGender(genderId);
        if (gender == null) throw new UserServiceException(Messages.GET_GENDER, HttpStatus.NOT_FOUND);
        if (!this.gendersService.deleteGender(genderId)) throw new UserServiceException(Messages.DELETE_GENDER, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PutMapping("/genders/{genderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateGenders(@Valid @RequestBody Genders genders, @PathVariable long genderId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("UPDATE_GENDERS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Genders gender = this.gendersService.getGender(genderId);
        if (gender == null) throw new UserServiceException(Messages.GET_GENDER, HttpStatus.BAD_REQUEST);

        gender.setName(getNotNull(genders.getName(), gender.getName()));
        if (!this.gendersService.updateGender(gender)) throw new UserServiceException(Messages.UPDATE_GENDER, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/genders/{genderId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Genders> retrieveGenders(@PathVariable long genderId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_GENDERS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Genders genders = this.gendersService.getGender(genderId);
        if (genders == null) throw new UserServiceException(Messages.GET_GENDER, HttpStatus.BAD_REQUEST);

        EntityModel<Genders> entityModel = new EntityModel<Genders>(genders);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveGenders(genderId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllGenders(GendersResource.DEFAULT_NUMBER, GendersResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-genders"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/genders/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Genders>> retrieveAllGenders(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_GENDERS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        List<Genders> genders = this.gendersService.getAllGenders(pageNumber, pageSize);
        if (genders == null || genders.isEmpty()) throw new UserServiceException(Messages.GET_ALL_GENDERS, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Genders>> collectionModel = new CollectionModel(genders);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllGenders(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllGenders(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN')")
    @PostMapping("/genders/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Genders>> searchForGenders(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();

        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_GENDERS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        if (search.get(Keys.PAGINATION) == null) throw new UserServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);

        Map<String, Object> genders = this.gendersService.searchForGender(search);
        if (genders == null || genders.isEmpty()) throw new UserServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Genders>> collectionModel = new CollectionModel((List<Genders>) genders.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForGenders(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) genders.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForGenders(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) genders.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForGenders(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    private static <T> T getNotNull(T a, T b) {
        return b != null && a != null && !a.equals(b) ? a : b;
    }
}