package com.pm.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseStreamObserver) {

        log.info("createBillingAccount request received {}", billingRequest.toString());

        // Business logic - e.g save to database, perform calculations

        BillingResponse response =  BillingResponse.newBuilder().setAccountId("12345").setStatus("OK").build();

        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();

    }

}
