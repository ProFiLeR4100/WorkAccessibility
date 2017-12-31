package com.aatech.WorkAccessibility.services;

import com.aatech.WorkAccessibility.GlobalVariables;
import com.aatech.WorkAccessibility.models.Company;
import com.aatech.WorkAccessibility.models.Vacancy;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Patch;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;
import java.util.Map;

@Rest(rootUrl = GlobalVariables.SERVER_HOST, converters = MappingJackson2HttpMessageConverter.class)
public interface CompanyRestService {
    // CRUD: Create
    @Put("/company/rest-create")
    Map<String, Object> create(@Body Company company);

    // CRUD: Read
    @Get("/company/rest-get-all")
    List<Company> getAll();

    @Get("/company/rest-get-by-id?id={id}")
    Company getById(@Path String id);

    // CRUD: Update
    @Patch("/company/rest-update?id={id}")
    Company update(@Path String id, @Body Company company);

    // CRUD: Delete
    @Delete("/company/rest-delete?id={id}")
    Company delete(@Path String id);
}
