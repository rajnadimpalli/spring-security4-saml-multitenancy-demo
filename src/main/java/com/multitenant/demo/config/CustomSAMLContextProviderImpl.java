package com.multitenant.demo.config;

import com.multitenant.demo.model.SPTenantConfig;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.context.SAMLMessageContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PropertySource(value = { "classpath:application.properties" })
public class CustomSAMLContextProviderImpl extends SAMLContextProviderImpl {
    @Override
    public SAMLMessageContext getLocalAndPeerEntity(HttpServletRequest request, HttpServletResponse response) throws MetadataProviderException {
        SAMLMessageContext context = new SAMLMessageContext();
        populateGenericContext(request, response, context);
        //changed to send URL instead of URI
        populateLocalEntityId(context, request.getRequestURL().toString());
        populateLocalContext(context);
        populatePeerEntityId(context);
        populatePeerContext(context);
        return context;
    }

    @Override
    public SAMLMessageContext getLocalEntity(HttpServletRequest request, HttpServletResponse response) throws MetadataProviderException {
        SAMLMessageContext context = new SAMLMessageContext();
        populateGenericContext(request, response, context);
        populateLocalEntityId(context, request.getRequestURL().toString());
        populateLocalContext(context);
        return context;
    }

    @Override
    protected void populateLocalEntityId(SAMLMessageContext context, String requestURL) throws MetadataProviderException {
        String entityId;
        HTTPInTransport inTransport = (HTTPInTransport) context.getInboundMessageTransport();

        // Pre-configured entity Id
        entityId = (String) inTransport.getAttribute(org.springframework.security.saml.SAMLConstants.LOCAL_ENTITY_ID);
        if (entityId != null) {
            context.setLocalEntityId(entityId);
            context.setLocalEntityRole(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
            return;
        } else {
            String spEntityID = SPTenantConfig.getSPEntityIDByRequestUrl(requestURL);
            context.setLocalEntityId(spEntityID);
            context.setLocalEntityRole(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
        }
    }

    @Override
    protected void populatePeerEntityId(SAMLMessageContext context) throws MetadataProviderException {
        HTTPInTransport inTransport = (HTTPInTransport)context.getInboundMessageTransport();
        String entityId = (String)inTransport.getAttribute("peerEntityId");
        if (entityId != null) {
        } else {
            entityId = inTransport.getParameterValue("idp");
            if (entityId != null) {
                context.setPeerUserSelected(true);
            } else {
                entityId = super.metadata.getDefaultIDP();
                context.setPeerUserSelected(false);
            }
        }
        SPTenantConfig spTenantConfig = new SPTenantConfig();
        SPTenantConfig.SPTenant spTenant = spTenantConfig.getSPTenantBySPEntityID(context.getLocalEntityId());
        context.setPeerEntityId(spTenant.getIdpEntityId());
        context.setPeerEntityRole(IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
    }
}
