package com.blesk.userservice.Controller;

import com.blesk.userservice.DTO.JwtMapper;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Proxy.AccountsServiceProxy;
import com.blesk.userservice.Service.Users.UsersServiceImpl;
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
import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class UsersResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private UsersServiceImpl usersService;
    private AccountsServiceProxy accountsServiceProxy;

    @Autowired
    public UsersResource(UsersServiceImpl usersService, AccountsServiceProxy accountsServiceProxy) {
        this.usersService = usersService;
        this.accountsServiceProxy = accountsServiceProxy;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Users> createUsers(@Valid @RequestBody Users users, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_USERS")) {
            throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        Users user = this.usersService.createUser(users);
        if (user == null) {
            throw new UserServiceException(Messages.CREATE_USER, HttpStatus.BAD_REQUEST);
        }

        EntityModel<Users> entityModel = new EntityModel<Users>(user);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveUsers(user.getUserId(), httpServletRequest, httpServletResponse)).withRel("user"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteUsers(@PathVariable long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("DELETE_USERS")) {
            throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        Boolean result;
        try {
            result = this.usersService.deleteUser(userId, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        } catch (UserServiceException ex) {
            ex.setHttpStatus(HttpStatus.BAD_REQUEST);
            throw ex;
        }

        if (!result) {
            throw new UserServiceException(Messages.DELETE_USER, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateUsers(@Valid @RequestBody Users users, @PathVariable long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("UPDATE_USERS")) {
            throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        Users user = this.usersService.getUser(userId, false);
        if (user == null) {
            throw new UserServiceException(Messages.GET_USER, HttpStatus.BAD_REQUEST);
        }

        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setGender(users.getGender());
        user.setBalance(users.getBalance());
        user.setTel(users.getTel());
        user.setImg(users.getImg());
        user.setPlaces(users.getPlaces());

        if (!this.usersService.updateUser(user)) {
            throw new UserServiceException(Messages.UPDATE_USER, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Users> retrieveUsers(@PathVariable long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_USERS")) {
            throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        Users users = this.usersService.getUser(userId, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (users == null) {
            throw new UserServiceException(Messages.GET_USER, HttpStatus.BAD_REQUEST);
        }

        EntityModel<Users> accountDetails = this.accountsServiceProxy.retrieveAccounts(users.getAccountId());

        if (accountDetails.getContent() != null) {
            users.setUserName(accountDetails.getContent().getUserName() != null ? accountDetails.getContent().getUserName() : "");
            users.setEmail(accountDetails.getContent().getEmail() != null ? accountDetails.getContent().getEmail() : "");
        }

        EntityModel<Users> entityModel = new EntityModel<Users>(users);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveUsers(userId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers(UsersResource.DEFAULT_NUMBER, UsersResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-users"));

        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/users/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Users>> retrieveAllUsers(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_USERS")) {
            throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        List<Users> users = this.usersService.getAllUsers(pageNumber, pageSize, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (users == null || users.isEmpty()) {
            throw new UserServiceException(Messages.GET_ALL_USERS, HttpStatus.BAD_REQUEST);
        }

        CollectionModel<List<Users>> collectionModel = new CollectionModel(users);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));

        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/users/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Users>> searchForUsers(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_ALL_USERS")) {
            throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        }

        if (search.get(Keys.PAGINATION) == null) {
            throw new UserServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> users = this.usersService.searchForUser(search, (httpServletRequest.isUserInRole("SYSTEM") || httpServletRequest.isUserInRole("ADMIN")));
        if (users == null || users.isEmpty()) {
            throw new UserServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);
        }

        CollectionModel<List<Users>> collectionModel = new CollectionModel((List<Users>) users.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForUsers(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) users.get("hasPrev")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForUsers(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        }
        if ((boolean) users.get("hasNext")) {
            collectionModel.add(linkTo(methodOn(this.getClass()).searchForUsers(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        }

        return collectionModel;
    }
}