package com.coworkingspace.backend.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {
    private String cloudName;
    private String apiKey;
    private String apiSecret;
    private String cloudPath;

    public CloudinaryConfig() {
        System.out.println("create cloudinary config");
    }

    public CloudinaryConfig(String cloudName, String apiKey, String apiSecret, String cloudPath) {
        this.cloudName = cloudName;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.cloudPath = cloudPath;
    }

    public String getCloudName() {
        return cloudName;
    }

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getCloudPath() {
        return cloudPath;
    }

    public void setCloudPath(String cloudPath) {
        this.cloudPath = cloudPath;
    }

    @Bean
    @Primary
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cloudinary cloudinaryCfg() {
        System.out.println("create cloudinaryCfg");
        return new Cloudinary(
                Map.of("cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret)
        );
    }

    @Bean
    @Qualifier("singletonCloudinary")
    @Scope(value = "singleton")
    public Cloudinary singletonCloudinary() {
        System.out.println("create singleton cloudinaryCfg");
        return new Cloudinary(
                Map.of("cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret)
        );
    }
}
