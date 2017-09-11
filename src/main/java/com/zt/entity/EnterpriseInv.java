package com.zt.entity;

public class EnterpriseInv {
    private String enterpriseName;
    private String enterpriseAbbr;
    private String investmenter;
    private String invType;

    public EnterpriseInv() {
    }

    public EnterpriseInv(String enterpriseName, String investmenter, String invType) {
        this.enterpriseName = enterpriseName;
        this.investmenter = investmenter;
        this.invType = invType;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseAbbr() {
        return enterpriseAbbr;
    }

    public void setEnterpriseAbbr(String enterpriseAbbr) {
        this.enterpriseAbbr = enterpriseAbbr;
    }

    public String getInvestmenter() {
        return investmenter;
    }

    public void setInvestmenter(String investmenter) {
        this.investmenter = investmenter;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }
}
