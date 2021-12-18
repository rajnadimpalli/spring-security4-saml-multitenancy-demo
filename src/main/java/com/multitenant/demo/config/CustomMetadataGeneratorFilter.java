package com.multitenant.demo.config;

import com.multitenant.demo.model.SPTenantConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@PropertySource(value = { "classpath:application.properties" })
public class CustomMetadataGeneratorFilter extends MetadataGeneratorFilter {
    public CustomMetadataGeneratorFilter(MetadataGenerator generator) {
        super(generator);
    }

    @Override
    protected void processMetadataInitialization(HttpServletRequest request) throws ServletException {
        if (super.manager.getHostedSPName() == null || !super.manager.getHostedSPName().equals(request.getServerName())) {
            synchronized (CustomMetadataGeneratorFilter.class) {
                String requestURL = request.getRequestURL().toString();

                String entityId = SPTenantConfig.getSPEntityIDByRequestUrl(requestURL);
                super.generator.setEntityId(entityId);
                super.manager.setHostedSPName(null);
                super.generator.setId(null);
                super.processMetadataInitialization(request);
            }
        }
    }
}
