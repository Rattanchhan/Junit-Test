package com.spring_boot.caching;
import org.junit.jupiter.api.Test;
import java.util.List;

class CachingDemoTestApplicationTests {
	@Test
	public void contextLoads() {
		List<String> names = List.of("Dara","Ratana","Panha","Ratha");
		List<Integer> items = Generic.getList(names);

		assert items.size() == 4;
		assert items.getFirst() == 4;
		assert items.getLast() == 5;
		assert items.get(1) == 6;
		assert items.get(2) == 5;
	}

	@Test
	public void evenNumber() {
		List<Integer> numbers = List.of(2, 5, 8, 0);
		List<Integer> result = Generic.getEvenNumber(numbers);

		assert result.size() == 3;
		assert result.getFirst() == 2;
	}

}
