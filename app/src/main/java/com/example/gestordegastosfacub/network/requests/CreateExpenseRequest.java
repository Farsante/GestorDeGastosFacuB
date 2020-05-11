package com.example.gestordegastosfacub.network.requests;

public class CreateExpenseRequest {
    private String description;
    private Double amount;
    private Integer accountId;
    private Integer categoyId;
    private Integer providerId;

    public CreateExpenseRequest(String description, Double amount, Integer accountId, Integer categoyId, Integer providerId) {
        this.description = description;
        this.amount = amount;
        this.accountId = accountId;
        this.categoyId = categoyId;
        this.providerId = providerId;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAccountId() { return accountId; }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCategoyId() { return categoyId; }

    public void setCategoyId(Integer categoyId) {
        this.categoyId = categoyId;
    }

    public Integer getProviderId() { return providerId; }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}
