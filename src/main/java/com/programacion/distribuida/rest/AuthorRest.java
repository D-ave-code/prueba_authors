package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Authors;
import com.programacion.distribuida.servicios.AuthorRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @Inject AuthorRepository repository;

    @GET
    @Path("/{id}")
    @Operation(summary = "Devuelve un author buscando por ID",
            description = "Retorna un objeto author")
    @APIResponse(description = "JSON con el author solicitado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Authors.class)))
    @APIResponse(responseCode = "204", description = "author no encontrado")
    public Authors findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }


    @Operation(summary = "Devuelve un JSON con los authores en la DB",
            description = "Retorna todos los authores")
    @APIResponse(description = "authores encontrados",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Authors.class)))
    @APIResponse(responseCode = "204", description = "No existen authores")
    @GET
    public List<Authors> findAll() {
        return repository
                .findAll()
                .list();
    }


    @POST
    @Transactional
    @Operation(summary = "Agrega un author",
            description = "Crea un objeto author")
    @RequestBody(description = "Crea un nuevo author",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Authors.class)))
    public void insert(Authors obj) {
        repository.persist(obj);
    }
    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Edita un author",
            description = "Edita un objeto author")
    @RequestBody(description = "Edita un nuevo author",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Authors.class)))
    public void update(Authors obj, @PathParam("id") Long id) {
       var author = repository.findById(id);
        author.setFirstName(obj.getFirstName());
        author.setLastName(obj.getLastName());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Borra un author",
            description = "Borra un objeto author")
    @RequestBody(description = "Borra un nuevo author",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Authors.class)))
    public void delete( @PathParam("id") Long id ) {
        repository.deleteById(id);
    }
}
