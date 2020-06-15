package com.blesk.userservice.Controller;

import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Payouts;
import com.blesk.userservice.Service.Payouts.PayoutsServiceImpl;
import com.blesk.userservice.Value.Keys;
import com.blesk.userservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class PayoutsResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private PayoutsServiceImpl payoutsService;

    @Autowired
    public PayoutsResource(PayoutsServiceImpl payoutsService) {
        this.payoutsService = payoutsService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/payouts")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Payouts> createPayouts(@Valid @RequestBody Payouts payouts, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Payouts payout = this.payoutsService.createPayout(payouts);
        if ((payout == null) || (payout.getPayoutId() == null)) throw new UserServiceException(Messages.CREATE_PAYOUT, HttpStatus.BAD_REQUEST);

        EntityModel<Payouts> entityModel = new EntityModel<Payouts>(payout);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePayouts(payout.getPayoutId(), httpServletRequest, httpServletResponse)).withRel("payout"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @DeleteMapping("/payouts/{payoutId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deletePayouts(@PathVariable long payoutId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Payouts payout = this.payoutsService.getPayout(payoutId);
        if (payout == null) throw new UserServiceException(Messages.GET_PAYOUT, HttpStatus.NOT_FOUND);
        if (!this.payoutsService.deletePayout(payout)) throw new UserServiceException(Messages.DELETE_PAYOUT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PutMapping("/payouts/{payoutId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePayouts(@Valid @RequestBody Payouts payouts, @PathVariable long payoutId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Payouts payout = this.payoutsService.getPayout(payoutId);
        if (payout == null) throw new UserServiceException(Messages.GET_PAYOUT, HttpStatus.BAD_REQUEST);

        if (!this.payoutsService.updatePayout(payout, payouts)) throw new UserServiceException(Messages.UPDATE_PAYOUT, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/payouts/{payoutId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Payouts> retrievePayouts(@PathVariable long payoutId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Payouts payouts = this.payoutsService.getPayout(payoutId);
        if (payouts == null) throw new UserServiceException(Messages.GET_PAYOUT, HttpStatus.BAD_REQUEST);

        EntityModel<Payouts> entityModel = new EntityModel<Payouts>(payouts);
        entityModel.add(linkTo(methodOn(this.getClass()).retrievePayouts(payoutId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllPayouts(PayoutsResource.DEFAULT_NUMBER, PayoutsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-payouts"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/payouts/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<List<Payouts>> retrieveAllPayouts(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Payouts> payouts = this.payoutsService.getAllPayouts(pageNumber, pageSize);
        if (payouts == null || payouts.isEmpty()) throw new UserServiceException(Messages.GET_ALL_PAYOUTS, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Payouts>> collectionModel = new CollectionModel(payouts);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPayouts(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllPayouts(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/payouts/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Payouts>> searchForPayouts(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new UserServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> payouts = this.payoutsService.searchForPayout(search);
        if (payouts == null || payouts.isEmpty()) throw new UserServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<List<Payouts>> collectionModel = new CollectionModel((List<Payouts>) payouts.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForPayouts(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) payouts.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPayouts(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) payouts.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForPayouts(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/payouts/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<List<Payouts>> joinPayouts(@PathVariable String columName, @RequestBody List<Long> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Payouts> payouts = this.payoutsService.getPayoutsForJoin(ids, columName);
        if (payouts == null || payouts.isEmpty()) throw new UserServiceException(Messages.GET_ALL_PAYOUTS, HttpStatus.BAD_REQUEST);
        return new CollectionModel(payouts);
    }
}