package com.zt.entity;

import java.util.List;

public class EnterpriseInfo {
    private String enterpriseName;
    private String enterpriseAbbr;
    private List<Investmenter> investmenterList;

    public EnterpriseInfo() {
    }

    public EnterpriseInfo(String enterpriseName, String enterpriseAbbr, List<Investmenter> investmenterList) {
        this.enterpriseName = enterpriseName;
        this.enterpriseAbbr = enterpriseAbbr;
        this.investmenterList = investmenterList;
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

    public List<Investmenter> getInvestmenterList() {
        return investmenterList;
    }

    public void setInvestmenterList(List<Investmenter> investmenterList) {
        this.investmenterList = investmenterList;
    }
}
