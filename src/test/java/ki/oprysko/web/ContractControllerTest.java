package ki.oprysko.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ki.oprysko.domain.Country;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.Person;
import ki.oprysko.service.LimitService;
import ki.oprysko.service.LoanService;
import ki.oprysko.web.forms.Error;
import ki.oprysko.web.forms.Success;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ki.oprysko.service.BlackListService;

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
    private BlackListService blacks;

    @MockBean
    private LoanService loans;

    @MockBean
    private LimitService limit;

    @Test
    public void whenPersonNotInBlackListThenApplyContract() throws Exception {
        List<Contract> list = Collections.singletonList(
                new Contract("test",  new Country("Ukraine"), new Person("Svyatoslav", "Oprysko"))
        );
        ObjectMapper mapper = new ObjectMapper();
        given(this.loans.getAll()).willReturn(list);
        this.mvc.perform(
                get("/").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(list))
        );
    }

    @Test
    public void whenLoadThenApplyContract() throws Exception {
        List<Contract> list = Collections.singletonList(
                new Contract("test",  new Country("Ukraine"), new Person("Svyatoslav", "Oprysko"))
        );
        ObjectMapper mapper = new ObjectMapper();
        given(this.loans.getByPerson(0)).willReturn(list);
        this.mvc.perform(
                get("/0").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(list))
        );
    }

    @Test
    public void whenApplyThenSave() throws Exception {
        Contract contract = new Contract("test",  new Country("Ukraine"), new Person("Svyatoslav", "Oprysko"));
        ObjectMapper mapper = new ObjectMapper();
        given(this.blacks.isBlackListPerson(0)).willReturn(false);
        given(this.loans.apply(contract)).willReturn(contract);
        this.mvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(
                                contract
                        )
                )
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(new Success<Contract>(contract)))
        );
    }

    @Test
    public void whenInBlacklistThenError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(this.blacks.isBlackListPerson(0)).willReturn(true);
        this.mvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(
                                new Contract("test",  new Country("Ukraine"), new Person("Svyatoslav", "Oprysko"))
                        )
                )
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(new Error("User 0 in blacklist")))
        );
    }

}