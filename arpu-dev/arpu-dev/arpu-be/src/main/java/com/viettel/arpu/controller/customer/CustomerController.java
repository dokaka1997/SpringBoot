package com.viettel.arpu.controller.customer;

import com.viettel.arpu.constant.CommonConstant;
import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.request.CustomerSearchForm;
import com.viettel.arpu.model.response.BaseResponse;
import com.viettel.arpu.model.response.CustomerResponse;
import com.viettel.arpu.service.customer.CustomerService;
import com.viettel.arpu.service.customer.VersionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
@Controller
@RequestMapping(value = "api/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VersionService versionService;


    @ApiOperation(value = "hiển thị whitelist",
            notes = "<b>lấy tất cả whitelist trong hệ thống và theo điều kiện tìm kiếm</b>")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "page number", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "size of page", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "sort by")
    })
    @GetMapping(value = "/search", params = "activeStatus=all")
    public ResponseEntity<CustomerResponse> all(@Valid CustomerSearchForm customerSearchForm, @ApiIgnore Pageable pageable) {
        Page<Customer> page = customerService.all(customerSearchForm, pageable);
        Long latestVersion = getLatestVersion();
        return ResponseEntity.ok(new CustomerResponse(page.map(customer -> customer.toDTO(latestVersion)), getLatestVersion()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "page number", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "size of page", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "sort by")
    })
    @GetMapping(value = "/search", params = "activeStatus=" + Customer.ACTIVE)
    public ResponseEntity<CustomerResponse> active(@Valid CustomerSearchForm customerSearchForm, @ApiIgnore Pageable pageable) {
        Long latestVersion = getLatestVersion();
        Page<Customer> page = customerService.active(customerSearchForm, latestVersion, pageable);
        return ResponseEntity.ok(new CustomerResponse(page.map(customer -> customer.toDTO(latestVersion)), latestVersion));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "page number", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "size of page", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "sort by")
    })
    @GetMapping(value = "/search", params = "activeStatus=" + Customer.INACTIVE)
    public ResponseEntity<CustomerResponse> inactive(@Valid CustomerSearchForm customerSearchForm, @ApiIgnore Pageable pageable) {
        Long latestVersion = getLatestVersion();
        Page<Customer> page = customerService.inactive(customerSearchForm, latestVersion, pageable);
        return ResponseEntity.ok(new CustomerResponse(page.map(customer -> customer.toDTO(latestVersion)), latestVersion));
    }

    @ApiOperation(value = "khóa vay trong whitelist",
            notes = "<b>khóa vay trong whitelist</b>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "page number", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "size of page", defaultValue = "20"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "sort by")
    })
    @PostMapping("/lock/{id}")
    public ResponseEntity<BaseResponse<Customer>> lock(@PathVariable("id") Long id) {
        customerService.lock(id);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @ApiOperation(value = "Mở khóa vay trong whitelist",
            notes = "<b>khóa vay trong whitelist</b>")
    @PostMapping("/unlock/{id}")
    public ResponseEntity<BaseResponse<Customer>> unlock(@PathVariable("id") Long id) {
        customerService.unlock(id);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    private Long getLatestVersion() {
        return versionService.getLatestVersionForBatchId(CommonConstant.BATCH_WHITELIST_ID);
    }
}
