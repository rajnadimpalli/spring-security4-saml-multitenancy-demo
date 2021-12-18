# spring-security4-saml-multitenancy-demo
Spring Security4 SAML - Multitenancy Demo

This is a demo application that demonstrates multi tenancy implementation (multiple SP's support for the same code) for Spring Security 4 application using Spring Security SAML extension. This project should only be used for reference.
Each SP can be configured to use it's own IDP (for ex: multiple applications on Okta)


This demo is built using: https://docs.spring.io/spring-security-saml/docs/current/reference/htmlsingle[Spring Security SAML Reference].
JavaDoc is also available in https://docs.spring.io/spring-security-saml/docs/current/api[the Spring Security API Documentation].
Please read above documents to understand the implementation

<hr>
SP to IDP mapping is configured in src/main/java/com/multitenant/demo/model/SPTenantConfig class. This should be copied to a XML or properties file or preferably database to make it scalable.
<hr>
== Steps to run demo

1. Create SAML application in Okta for Tenant 1
2. Create SAML application in Okta for Tenant 2
3. Update mapping in src/main/java/com/multitenant/demo/model/SPTenantConfig.java 
4. Run the project

<hr>

== Notes:
1. By default the demo sets SP entity id to : <base-url>/sp. This can be overwritten by updating src/main/java/com/multitenant/demo/model/SPTenantConfig.java
2. SP SAML keys/certificates are stored in resources/saml/samlKeystore.jks file
3. Password and key name are configured in application.properties (these should be moved to a secure store)
4. IDP metadata URL's are used in this demo but metadata files can be used instead (https://docs.spring.io/spring-security-saml/docs/current/reference/htmlsingle/#quick-start-idp-metadata)

