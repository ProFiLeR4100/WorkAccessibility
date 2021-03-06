package com.aatech.WorkAccessibility.services;

import com.aatech.WorkAccessibility.GlobalVariables;
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
public interface VacancyRestService {
    // CRUD: Create
    @Put("/vacancy/rest-create")
    Map<String, Object> create(@Body Vacancy vacancy);

    // CRUD: Read
    @Get("/vacancy/rest-get-all")
    List<Vacancy> getAll();

    @Get("/vacancy/rest-get-by-id?id={id}")
    Vacancy getById(@Path String id);

    // CRUD: Update
    @Patch("/vacancy/rest-update?id={id}")
    Vacancy update(@Path String id, @Body Vacancy vacancy);

    // CRUD: Delete
    @Delete("/vacancy/rest-delete?id={id}")
    Vacancy delete(@Path String id);
}
