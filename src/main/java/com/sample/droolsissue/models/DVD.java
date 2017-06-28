package com.sample.droolsissue.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DVD implements Product {
	private String asin;

	private String title;

	private String category;

	private int price;

	private int numOfDiscs;
}
