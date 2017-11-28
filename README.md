## DRLコンパイルエラー再現コード

### 主要ファイル
* 問題DRLファイル: `drools-compilation-issue/src/main/resources/rules/checkorder-not-compiled.drl`
* 動作確認JUnitテスト: `/drools-compilation-issue/src/test/java/com/sample/droolsissue/rules/CheckOrderRuleTest.java`

### 実行方法
```
$ mvn test
```

### 発生事象
* 以下のDRLでコンパイルエラーが発生する
```mvel
    when
		$orderLine: OrderLine()

		Product(
			id == $orderLine.product.id || category == $orderLine.product.category
		) from discountProducts
```

```
java.lang.RuntimeException: Error while creating KieBase[Message [id=1, kieBase=defaultKieBase, level=ERROR, path=checkorder-not-compiled.drl, line=15, column=0
   text=Unable to Analyse Expression id == $orderLine.product.id || category == $orderLine.product.category:
[Error: unable to resolve method using strict-mode: java.lang.Object.category()]
[Near : {... Line.product.id || category == $orderLine.product.category ....}]
                                                               ^
[Line: 15, Column: 2]]]
	at org.drools.compiler.kie.builder.impl.KieContainerImpl.getKieBase(KieContainerImpl.java:524)
	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:684)
	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:626)
	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:608)
    ... 
```

* pom.xmlに以下の依存関係を追加して`mvn test`すると正常動作する。
```xml
		<dependency>
			 <groupId>org.mvel</groupId>
			 <artifactId>mvel2</artifactId>
			 <version>2.3.2.Final</version>
		</dependency>
```

### 参考
https://issues.jboss.org/browse/DROOLS-1724

