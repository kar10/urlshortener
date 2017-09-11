package hr.spring.urlshortener.dto;

import java.util.HashMap;
import java.util.Map;

public class PageCounterMap {

	Map<String, Integer> stats;
	
	public PageCounterMap() {
		stats = new HashMap<>();
	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}
}
