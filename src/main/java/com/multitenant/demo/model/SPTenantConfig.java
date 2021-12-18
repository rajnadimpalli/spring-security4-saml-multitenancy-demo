package com.multitenant.demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPTenantConfig {
    private Map<String,SPTenant> spTenants = null;

    public SPTenantConfig(){
        this.spTenants = new HashMap<String,SPTenant>();
        SPTenant tenant1 = new SPTenant("http://localhost:8080/sp","http://www.okta.com/exk13tdnao7zEuFpS0h8","https://rajp.oktapreview.com/app/exk13tdnao7zEuFpS0h8/sso/saml/metadata");
        this.spTenants.put("http://localhost:8080/sp",tenant1);
        SPTenant tenant2 = new SPTenant("http://localhost:8081/sp","http://www.okta.com/exk13vhg66goQ5XTL0h8","https://rajp.oktapreview.com/app/exk13vhg66goQ5XTL0h8/sso/saml/metadata");
        this.spTenants.put("http://localhost:8081/sp",tenant2);
    }

    public List<String> getIDPList(){
        List<String> idpMetadataList = null;
        if(this.spTenants!=null) {
            idpMetadataList = new ArrayList<>();
            for (Map.Entry<String, SPTenant> spTenantEntry : this.spTenants.entrySet()) {
                SPTenant spTenant = spTenantEntry.getValue();
                String idpMetadata = spTenant.getIdpMetadataPath();
                if (!idpMetadataList.contains(idpMetadata)) {
                    idpMetadataList.add(idpMetadata);
                }
            }
        }
        return idpMetadataList;
    }

    public SPTenant getSPTenantBySPEntityID(String spEntityId){
        return this.spTenants.get(spEntityId);
    }

    public static String getSPEntityIDByRequestUrl(String requestUrl){
        String spEntityId = "";
        if(org.apache.commons.lang.StringUtils.ordinalIndexOf(requestUrl, "/", 3) != -1) {
            spEntityId = requestUrl.substring(0, org.apache.commons.lang.StringUtils.ordinalIndexOf(requestUrl, "/", 3)).concat("/sp");
        } else {
            spEntityId = requestUrl.concat("/sp");
        }

        return spEntityId;
    }

    public class SPTenant{
        private String spEntityId;
        private String idpEntityId;
        private String idpMetadataPath;

        public SPTenant(String spEntityId, String idpEntityId, String idpMetadataPath) {
            this.spEntityId = spEntityId;
            this.idpEntityId = idpEntityId;
            this.idpMetadataPath = idpMetadataPath;
        }

        public String getIdpEntityId() {
            return idpEntityId;
        }

        public void setIdpEntityId(String idpEntityId) {
            this.idpEntityId = idpEntityId;
        }

        public String getIdpMetadataPath() {
            return idpMetadataPath;
        }

        public void setIdpMetadataPath(String idpMetadataPath) {
            this.idpMetadataPath = idpMetadataPath;
        }

        public String getSpEntityId() {
            return spEntityId;
        }

        public void setSpEntityId(String spEntityId) {
            this.spEntityId = spEntityId;
        }
    }
}
