package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.axis.model.InsuranceType;
import com.axis.model.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InsuranceTypeDao {

    private DynamoDBMapper dynamoDBMapper;

    public InsuranceTypeDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<InsuranceType> getListOfInsuranceType() {
        return this.dynamoDBMapper.scan(InsuranceType.class, new DynamoDBScanExpression());
    }

    public void saveOrUpdateInsuranceType(InsuranceType insuranceType) {
        if (insuranceType.getInsuranceTypeId() == null) {
            this.dynamoDBMapper.save(insuranceType);
        } else {
            InsuranceType insuranceType1 = this.dynamoDBMapper.load(InsuranceType.class, insuranceType.getInsuranceTypeId());
            if (insuranceType.getInsuranceTypeName() != null) {
                insuranceType1.setInsuranceTypeName(insuranceType.getInsuranceTypeName());
            }
            this.dynamoDBMapper.save(insuranceType1);
        }
    }

    public void addVendorToInsuranceType(Vendor vendor, String insuranceTypeId) {
        InsuranceType insuranceType = this.dynamoDBMapper.load(InsuranceType.class, insuranceTypeId);
        vendor.setVendorId(UUID.randomUUID().toString());
        if(insuranceType.getListOfVendors() == null) {
            List<Vendor> listOfVendors = new ArrayList<Vendor>();
            listOfVendors.add(vendor);
            insuranceType.setListOfVendors(listOfVendors);
        } else {
            insuranceType.getListOfVendors().add(vendor);
        }
        this.dynamoDBMapper.save(insuranceType);
    }

}
