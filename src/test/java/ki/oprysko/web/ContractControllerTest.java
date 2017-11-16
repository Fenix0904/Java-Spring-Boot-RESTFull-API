package ki.oprysko.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ki.oprysko.domain.Country;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.User;
import ki.oprysko.repository.RoleRepository;
import ki.oprysko.service.*;
import ki.oprysko.web.controller.LoanController;
import ki.oprysko.web.forms.Error;
import ki.oprysko.web.forms.Success;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class ContractControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BlackListService blacks;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private LoanService loans;

    @MockBean
    private LimitService limit;



    @Test
    @WithMockUser(username="Fenix0904")
    public void whenPersonNotInBlackListThenApplyContract() throws Exception {
        List<Contract> list = Collections.singletonList(
                new Contract("test", new Country("Ukraine"), new User("Svyatoslav", "Oprysko"))
        );
        ObjectMapper mapper = new ObjectMapper();
        given(this.loans.getAll()).willReturn(list);
        this.mvc.perform(
                get("/get-all-contracts").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(list))
        );
    }

    @Test
    @WithMockUser(username="Fenix0904")
    public void whenLoadThenApplyContract() throws Exception {
        List<Contract> list = Collections.singletonList(
                new Contract("test", new Country("Ukraine"), new User("Svyatoslav", "Oprysko"))
        );
        ObjectMapper mapper = new ObjectMapper();
        given(this.loans.getByUser(0)).willReturn(list);
        this.mvc.perform(
                get("/0").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(list))
        );
    }

    @Test
    @WithMockUser(username="Fenix0904")
    public void whenApplyThenSave() throws Exception {
        Contract contract = new Contract("test", new Country("Ukraine"), new User("Svyatoslav", "Oprysko"));
        ObjectMapper mapper = new ObjectMapper();
        given(this.blacks.isBlackListPerson(0)).willReturn(false);
        given(this.loans.apply(contract)).willReturn(contract);
        this.mvc.perform(
                post("/apply").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(
                                contract
                        )
                )
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(new Success<>(contract)))
        );
        System.out.println(mapper.writeValueAsString(contract));
    }

    @Test
    @WithMockUser(username="Fenix0904")
    public void whenInBlacklistThenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(this.blacks.isBlackListPerson(0)).willReturn(true);
        this.mvc.perform(
                post("/apply").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(
                                new Contract("test", new Country("Ukraine"), new User("Svyatoslav", "Oprysko"))
                        )
                )
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(new Error("User 0 in blacklist")))
        );
    }

}