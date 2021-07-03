package com.axis.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.axis.config.DynamodbConfig;
import com.axis.dao.InsuranceTypeDao;
import com.axis.model.InsuranceType;
import com.axis.model.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InsuranceTypeService {
    private InsuranceTypeDao insuranceTypeDao = new InsuranceTypeDao(DynamodbConfig.dynamoDBMapper());

    public List<InsuranceType> getListOfInsuranceType() {
        return this.insuranceTypeDao.getListOfInsuranceType();
    }

    public void saveOrUpdateInsuranceType(InsuranceType insuranceType) {
        this.insuranceTypeDao.saveOrUpdateInsuranceType(insuranceType);
    }

    public void addVendorToInsuranceType(Vendor vendor, String insuranceTypeId) {
        this.insuranceTypeDao.addVendorToInsuranceType(vendor, insuranceTypeId);
    }
}
