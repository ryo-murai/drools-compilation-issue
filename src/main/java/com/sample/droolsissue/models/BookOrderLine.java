package com.sample.droolsissue.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookOrderLine implements OrderLine<Book> {
	private Book product;

	private int amount;
}
