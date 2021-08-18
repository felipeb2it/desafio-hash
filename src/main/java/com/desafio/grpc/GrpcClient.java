package com.desafio.grpc;

import discount.DiscountGrpc;
import discount.DiscountOuterClass.GetDiscountRequest;
import discount.DiscountOuterClass.GetDiscountResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

	public static float callDiscount() {
      ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();

      DiscountGrpc.DiscountBlockingStub stub = DiscountGrpc.newBlockingStub(channel);

      GetDiscountResponse response = stub.getDiscount(GetDiscountRequest.newBuilder()
    		  .build());

      channel.shutdown();
      return response.getPercentage();
	}
	
}
