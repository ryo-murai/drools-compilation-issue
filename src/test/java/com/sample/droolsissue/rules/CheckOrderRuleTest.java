package com.sample.droolsissue.rules;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import com.sample.droolsissue.models.Book;
import com.sample.droolsissue.models.BookOrderLine;
import com.sample.droolsissue.models.DVD;
import com.sample.droolsissue.models.Product;

public class CheckOrderRuleTest {

	@Test
	public void testCompilationFails() {
		KieBase kieBase = createKieBase("rules/checkorder-not-compiled.drl");

		KieSession kieSession = kieBase.newKieSession();
		try {
			kieSession.insert(createFact());
			kieSession.setGlobal("discountProducts", createDiscountProducts());

			kieSession.fireAllRules();
		} finally {
			kieSession.dispose();
		}
	}

	@Test
	public void testCompilationFailsUsingKieContainer() {
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession();
		try {
			kieSession.insert(createFact());
			kieSession.setGlobal("discountProducts", createDiscountProducts());

			kieSession.fireAllRules();
		} finally {
			kieSession.dispose();
		}
	}

	private KieBase createKieBase(String drlPath) {
		KieHelper kieHelper = new KieHelper();
		kieHelper.addResource(ResourceFactory.newClassPathResource(drlPath), ResourceType.DRL);

		Results results = kieHelper.verify();
		if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
			System.out.println(results);
		}

		return kieHelper.build();
	}

	private BookOrderLine createFact() {
		Book orderedBook =
			Book.builder()
				.id("XX123456789")
				.category("ABC")
				.title("xxxxxxxxxxx")
				.price(1200)
				.ISBN("111-1234567890-8")
				.build();

		return
			BookOrderLine.builder()
				.product(orderedBook)
				.amount(1)
				.build();
	}

	private List<Product> createDiscountProducts() {
		DVD discountDvd = DVD.builder()
				.id("ZZ0987654321")
				.category("ABC")
				.title("xxxxxxxxxxx")
				.numOfDiscs(3)
				.price(5980)
				.build();

		return Arrays.asList(discountDvd);
	}
}
