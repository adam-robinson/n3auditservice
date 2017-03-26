package com.searchmetrics.n3jobservice.api.rest;

import com.searchmetrics.n3jobservice.dao.JobsRepository;
import com.searchmetrics.n3jobservice.dto.Job;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.ok;

/**
 * Created by arobinson on 3/25/17.
 */
@Api
@Path("/")
public class JobResource {

    private final JobsRepository jobsRepository;

    public JobResource(final JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("dashboard")
    public Response getDashboard() {
        return ok().build();
    }

    /**
     * Endpoint to request a {@link Job} by crawl id
     *
     * @param crawlId       {@link Long} value corresponding to the job to be
     *                                  examined
     * @return              200 status and a Job object on success.  404 or
     * 500 status code otherwise
     *
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("job/{crawlId}")
    public Response getJob(@PathParam("crawlId") final Long crawlId) {
        final Optional<Job> jobOptional = jobsRepository.findById(crawlId);

        if (!jobOptional.isPresent()) {
            return Response.status(NOT_FOUND)
                    .entity("No Job found matching crawlId: " + crawlId).build();
        }

        return ok().entity(jobOptional).build();
    }

    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jobs/")
    public Response getAllJobs() {
        return ok().entity(jobsRepository.findAllJobsStream().collect(Collectors.toList())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public Response getStatus() {
        return ok().build();
    }
}
