package com.sample.droolsissue.models;

public interface OrderLine<T extends Product> {
	T getProduct();

	int getAmount();
}
