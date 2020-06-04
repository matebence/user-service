package com.blesk.userservice.Controller;

import com.blesk.userservice.DTO.JwtMapper;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Payments;
import com.blesk.userservice.Service.Payments.PaymentsServiceImpl;
import com.blesk.userservice.Value.Keys;
import com.blesk.userservice.Value.Messages;
import com.stripe.exception.StripeException;
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
public class PaymentsResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private PaymentsServiceImpl paymentsService;

    @Autowired
    public PaymentsResource(PaymentsServiceImpl paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT')")
    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Payments> createPayments(@Valid @RequestBody Payments payments, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws StripeException {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_PAYMENTS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Payments payment = this.paymentsService.createPayment(payments);
        if ((payment == null) || (payment.getPaymentId() == null)) throw new UserServiceException(Messages.CREATE_PAYMENT, HttpStatus.BAD_REQUEST);

        EntityModel<Payments> entityModel = new EntityModel<Payments>(payment);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePayments(payment.getPaymentId(), httpServletRequest, httpServletResponse)).withRel("payment"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PostMapping("/refunds")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Payments> createRefunds(@Valid @RequestBody Payments payments, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws StripeException {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("CREATE_REFUNDS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Payments payment = this.paymentsService.createRefund(payments);
        if ((payment == null) || (!payment.getRefunded())) throw new UserServiceException(Messages.CREATE_REFUND, HttpStatus.BAD_REQUEST);
        return new EntityModel<Payments>(payment);
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @DeleteMapping("/payments/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePayments(@PathVariable long paymentId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("DELETE_PAYMENTS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Payments payment = this.paymentsService.getPayment(paymentId);
        if (payment == null) throw new UserServiceException(Messages.GET_PAYMENT, HttpStatus.NOT_FOUND);
        if (!this.paymentsService.deletePayment(payment)) throw new UserServiceException(Messages.DELETE_PAYMENT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PutMapping("/payments/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePayments(@Valid @RequestBody Payments payments, @PathVariable long paymentId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("UPDATE_PAYMENTS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Payments payment = this.paymentsService.getPayment(paymentId);
        if (payment == null) throw new UserServiceException(Messages.GET_PAYMENT, HttpStatus.BAD_REQUEST);

        if (!this.paymentsService.updatePayment(payment, payments)) throw new UserServiceException(Messages.UPDATE_PAYMENT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT')")
    @GetMapping("/payments/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Payments> retrievePayments(@PathVariable long paymentId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_PAYMENTS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        Payments payments = this.paymentsService.getPayment(paymentId);
        if (payments == null) throw new UserServiceException(Messages.GET_PAYMENT, HttpStatus.BAD_REQUEST);

        EntityModel<Payments> entityModel = new EntityModel<Payments>(payments);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePayments(paymentId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPayments(PaymentsResource.DEFAULT_NUMBER, PaymentsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-payments"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT')")
    @GetMapping("/payments/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Payments>> retrieveAllPayments(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_PAYMENTS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);

        List<Payments> payments = this.paymentsService.getAllPayments(pageNumber, pageSize);
        if (payments == null || payments.isEmpty()) throw new UserServiceException(Messages.GET_ALL_PAYMENTS, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Payments>> collectionModel = new CollectionModel(payments);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPayments(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPayments(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT')")
    @PostMapping("/payments/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Payments>> searchForPayments(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtMapper jwtMapper = (JwtMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();

        if (!jwtMapper.getGrantedPrivileges().contains("VIEW_PAYMENTS")) throw new UserServiceException(Messages.AUTH_EXCEPTION, HttpStatus.UNAUTHORIZED);
        if (search.get(Keys.PAGINATION) == null) throw new UserServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);

        Map<String, Object> payments = this.paymentsService.searchForPayment(search);
        if (payments == null || payments.isEmpty()) throw new UserServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Payments>> collectionModel = new CollectionModel((List<Payments>) payments.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForPayments(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) payments.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPayments(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) payments.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPayments(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }
}