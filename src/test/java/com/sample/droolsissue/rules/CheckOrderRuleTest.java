package com.sample.droolsissue.rules;

import java.util.Arrays;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import com.sample.droolsissue.models.Book;
import com.sample.droolsissue.models.BookOrderLine;

public class CheckOrderRuleTest {

	@Test
	public void test() {
		Book orderedBook = Book.builder()
						.asin("XX123456789")
						.category("Books > Novel")
						.title("xxxxxxxxxxx")
						.price(1200)
						.ISBN("111-1234567890-8")
						.build();

		BookOrderLine orderLine1 =
			BookOrderLine.builder()
				.product(orderedBook)
				.amount(1)
				.build();

		String drlPath = "rules/checkorder.drl";
		//String drlPath = "rules/checkorder-not-compiled.drl";
		KieHelper kieHelper = new KieHelper();
		kieHelper.addResource(ResourceFactory.newClassPathResource(drlPath), ResourceType.DRL);

		Results results = kieHelper.verify();
		if(results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
			System.out.println(results);
		}

		KieBase kieBase = kieHelper.build();
		KieSession kieSession = kieBase.newKieSession();

		kieSession.insert(orderLine1);
		kieSession.setGlobal("discountProducts", Arrays.asList(orderedBook));
		kieSession.fireAllRules();

		kieSession.dispose();
	}

}
