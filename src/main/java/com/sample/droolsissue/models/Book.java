package com.sample.droolsissue.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Book implements Product {
	private String asin;

	private String title;

	private String category;

	private int price;

	private String ISBN;
}
