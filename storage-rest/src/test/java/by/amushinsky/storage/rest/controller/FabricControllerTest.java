package by.amushinsky.storage.rest.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import by.amushinsky.storage.core.Fabric;
import by.amushinsky.storage.rest.config.TestConfig;
import by.amushinsky.storage.service.api.FabricService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class FabricControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private FabricService fabricService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(context).build();
        Mockito.reset(fabricService);
    }

    @Test
    public void testAddFabric() throws Exception {
        fail("Not yet implemented");
        Fabric fabric = new Fabric("name");
        int id = 1;

        Mockito.doAnswer((invoker) -> {
            invoker.getArgumentAt(0, Fabric.class).setId(id);
            return null;
        }).when(fabricService).addFabric(fabric);

        mockMvc.perform(post("/fabrics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":0,\"name\":name}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    public void testGetAllFabrics() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllFabricStocks() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetFabricAmountById() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTotalFabricAmount() {
        fail("Not yet implemented");
    }

    @Test
    public void testValidationFailure() {
        fail("Not yet implemented");
    }

    @Test
    public void testNotUniqueName() {
        fail("Not yet implemented");
    }

    @Test
    public void testWrongJson() {
        fail("Not yet implemented");
    }

    @Test
    public void testTypeMismatch() {
        // fail("Not yet implemented");
    }

    @Test
    public void testUnknownException() {
        // fail("Not yet implemented");
    }

}
