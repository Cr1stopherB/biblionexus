package com.biblionexus.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportDTO {

    private Long id;

    @JsonProperty("user_id")
    private String userId;

    private String username;

    @JsonProperty("created_at")
    private String createdAt;

    public ReportDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
