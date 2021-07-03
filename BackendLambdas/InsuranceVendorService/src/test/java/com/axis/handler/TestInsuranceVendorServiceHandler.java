package com.axis.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.tests.EventLoader;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import com.amazonaws.services.lambda.runtime.tests.annotations.HandlerParams;
import com.amazonaws.services.lambda.runtime.tests.annotations.Response;
import com.axis.dao.SoldInsurancePolicyDao;
import com.axis.dto.SoldInsurancePolicyDto;
import com.axis.model.SoldInsurancePolicy;
import com.axis.service.SoldInsurancePolicyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;
@RunWith(MockitoJUnitRunner.class)
public class TestInsuranceVendorServiceHandler {

    @Mock
    SoldInsurancePolicyService soldInsurancePolicyService;
    @InjectMocks
    InsuranceVendorServiceHandler insuranceVendorServiceHandler;

    @Test
    public void testHandler() {

        APIGatewayProxyRequestEvent event = EventLoader.loadApiGatewayRestEvent("event1.json");
        SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
        obj1.setInsuranceTypeId("1");

        Mockito.when(soldInsurancePolicyService.getCountByInsuranceType()).thenReturn(Stream.of(obj1).collect(Collectors.toList()));

        APIGatewayProxyResponseEvent result = insuranceVendorServiceHandler.myHandlerFunction(event, null);

        Assert.assertEquals(200, result.getStatusCode().intValue());
    }

}
