package by.amushinsky.storage.mvc.controller.test;

import org.hamcrest.CoreMatchers;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import by.amushinsky.storage.core.FabricStock;
import by.amushinsky.storage.core.Storage;
import by.amushinsky.storage.mvc.controller.StorageController;
import by.amushinsky.storage.service.api.StorageService;

public class StorageControllerTest {
	@Test
	public void testStorage() throws Exception {
		Storage expectedStorage = createStorage();
		StorageService storageService = mock(StorageService.class);
		when(storageService.getStorage()).thenReturn(expectedStorage);

		StorageController controller = new StorageController();
		controller.setStorageService(storageService);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB_INF/views/storage.jsp")).build();
		mockMvc.perform(get("/storage")).andExpect(view().name("storage")).andExpect(model().attributeExists("storage"))
				.andExpect(model().attribute("storage", CoreMatchers.equalTo(expectedStorage)));
	}

	public Storage createStorage() {
		List<FabricStock> expectedStocks = new ArrayList<>();
		expectedStocks.add(new FabricStock("linen 100%", new BigDecimal("500.00")));
		expectedStocks.add(new FabricStock("cotton 100%", new BigDecimal("460.10")));
		expectedStocks.add(new FabricStock("cot/lin 50/50", new BigDecimal("1200.50")));
		expectedStocks.add(new FabricStock("PE 100%", new BigDecimal("799.50")));
		return new Storage(expectedStocks, new BigDecimal("2960.10"));
	}

}
