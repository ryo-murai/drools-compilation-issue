package com.sample.droolsissue.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DVDOrderLine implements OrderLine<DVD> {
	private DVD product;

	private int amount;
}
