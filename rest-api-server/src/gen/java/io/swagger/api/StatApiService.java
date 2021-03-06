package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.ErrorModel;
import io.swagger.model.StatisticSet;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-02-13T15:04:21.309Z")
public abstract class StatApiService {
    public abstract Response getCloudStatisticSet(String userUuid,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getHealthCheckStat(String userUuid,SecurityContext securityContext) throws NotFoundException;
    public abstract Response postCloudStatSet(String userUuid,StatisticSet cloudStatisticSet,SecurityContext securityContext) throws NotFoundException;
}
