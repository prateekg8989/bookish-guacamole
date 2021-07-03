package com.axis.service;

import com.axis.dao.SoldInsurancePolicyDao;
import com.axis.dto.SoldInsurancePolicyDto;
import com.axis.model.SoldInsurancePolicy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class TestSoldInsurancePolicyService {
    @InjectMocks
    SoldInsurancePolicyService soldInsurancePolicyService;
    @Mock
    private SoldInsurancePolicyDao soldInsurancePolicyDao;

    @Test
    public void testGetCountByInsuranceType() {
        SoldInsurancePolicy obj1 = new SoldInsurancePolicy();
        obj1.setInsuranceTypeId("1");
        SoldInsurancePolicy obj2 = new SoldInsurancePolicy();
        obj2.setInsuranceTypeId("1");
        SoldInsurancePolicy obj3 = new SoldInsurancePolicy();
        obj3.setInsuranceTypeId("3");
        SoldInsurancePolicy obj4 = new SoldInsurancePolicy();
        obj4.setInsuranceTypeId("1");

        Mockito.when(soldInsurancePolicyDao.getListOfPolicies()).thenReturn(Stream.of(obj1, obj2, obj3, obj4).collect(Collectors.toList()));

        SoldInsurancePolicyDto soldInsurancePolicyDto = null;
        List<SoldInsurancePolicyDto> list = soldInsurancePolicyService.getCountByInsuranceType();
        for (SoldInsurancePolicyDto obj :
                list) {
            if (obj.getInsuranceTypeId().equals("1")) {
                soldInsurancePolicyDto = obj;
            }
        }

        Assert.assertEquals(3, soldInsurancePolicyDto.getTotalCount());
    }
}