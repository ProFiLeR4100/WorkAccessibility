package com.aatech.WorkAccessibility.services;

import com.aatech.WorkAccessibility.GlobalVariables;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Patch;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Map;

@Rest(rootUrl = GlobalVariables.SERVER_HOST, converters = MappingJackson2HttpMessageConverter.class)
public interface VacancyRestService {
    // CRUD: Create
    @Put("/vacancy/rest-update?id={id}")
    Map<String, Object> create(@Path String id, @Body Map<String, Object> map);

    // CRUD: Read
    @Get("/vacancy/rest-get-all")
    Map<String, Object> getAll();

    @Get("/vacancy/rest-get-by-id?id={id}")
    Map<String, Object> getById(@Path String id);

    // CRUD: Update
    @Patch("/vacancy/rest-update?id={id}")
    Map<String, Object> update(@Path String id, @Body Map<String, Object> map);

    // CRUD: Delete
    @Delete("/vacancy/rest-delete?id={id}")
    Map<String, Object> delete(@Path String id);
}
