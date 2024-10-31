package com.esprit.examen.services;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {

	@Mock
	private StockRepository stockRepository;

	@InjectMocks
	private StockServiceImpl stockService;

	private Stock stock;

	@Before
	public void setUp() {
		stock = new Stock("stock test", 10, 100);
	}

	@Test
	public void testRetrieveAllStocks() {
		List<Stock> stocks = new ArrayList<>();
		stocks.add(stock);

		when(stockRepository.findAll()).thenReturn(stocks);

		List<Stock> retrievedStocks = stockService.retrieveAllStocks();

		assertEquals(stocks.size(), retrievedStocks.size());
		assertEquals(stock.getLibelleStock(), retrievedStocks.get(0).getLibelleStock());
	}
	@Test
	public void testAddStock() {
		when(stockRepository.save(stock)).thenReturn(stock);

		Stock savedStock = stockService.addStock(stock);

		assertNotNull(savedStock);
		assertEquals(stock.getLibelleStock(), savedStock.getLibelleStock());
	}

	@Test
	public void testDeleteStock() {
		long stockId = 1L;

		stockService.deleteStock(stockId);

		verify(stockRepository, times(1)).deleteById(stockId);
	}

	@Test
	public void testUpdateStock() {
		Stock updatedStock = new Stock("updated stock", 20, 200);

		when(stockRepository.save(updatedStock)).thenReturn(updatedStock);

		Stock result = stockService.updateStock(updatedStock);

		assertNotNull(result);
		assertEquals(updatedStock.getLibelleStock(), result.getLibelleStock());
		assertEquals(updatedStock.getQte(), result.getQte());
		assertEquals(updatedStock.getQteMin(), result.getQteMin());
	}

	@Test
	public void testRetrieveStock() {
		long stockId = 1L;
		Stock expectedStock = new Stock("test", 10, 100);

		when(stockRepository.findById(stockId)).thenReturn(java.util.Optional.ofNullable(expectedStock));

		Stock result = stockService.retrieveStock(stockId);

		assertNotNull(result);
		assertEquals(expectedStock.getLibelleStock(), result.getLibelleStock());
		assertEquals(expectedStock.getQte(), result.getQte());
		assertEquals(expectedStock.getQteMin(), result.getQteMin());
	}

	@Test
	public void testRetrieveStatusStock() {
		// Given
		List<Stock> stocksEnRouge = new ArrayList<>();
		Stock stock1 = new Stock("Stock1", 5, 10);
		Stock stock2 = new Stock("Stock2", 8, 20);
		stocksEnRouge.add(stock1);
		stocksEnRouge.add(stock2);

		when(stockRepository.retrieveStatusStock()).thenReturn(stocksEnRouge);

		// When
		String statusMessage = stockService.retrieveStatusStock();

		// Then
		assertNotNull(statusMessage);
		assertTrue(statusMessage.contains("Stock1"));
		assertTrue(statusMessage.contains("Stock2"));
		assertTrue(statusMessage.contains("5"));
		assertTrue(statusMessage.contains("8"));
		assertTrue(statusMessage.contains("10"));
		assertTrue(statusMessage.contains("20"));
	}

}
