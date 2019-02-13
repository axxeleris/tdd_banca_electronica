package login;

import com.pe.tdd.domain.User;
import com.pe.tdd.exception.InvalidUserAndPasswordException;
import com.pe.tdd.repository.UserRepository;
import com.pe.tdd.service.LoginService;
import com.pe.tdd.service.impl.LoginServiceImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepDefinitions {

    String userName;
    String password;

    User actualUser;

    RuntimeException currentException;

    UserRepository userRepository = mock(UserRepository.class);
    LoginService loginService = new LoginServiceImpl(userRepository);

    @Given("^Con usuario valido$")
    public void con_usuario_valido() throws Exception {
        userName = "usuario";
    }

    @Given("^contraseña valida$")
    public void contraseña_valida() throws Exception {
        password = "secret";
    }

    @Given("^contraseña no valida$")
    public void contraseña_no_valida() throws Exception {
        password = "mal_password";
    }

    @When("^validar datos de inicio de sesion$")
    public void validar_datos_de_inicio_de_sesion() throws Exception {
        when(userRepository.findUser(anyString()))
                .thenReturn(
                        new User("usuario", "password", false)
                );

        try {
            actualUser = loginService.login(userName, password);
        } catch (RuntimeException t) {
            currentException = t;
        }
    }

    @Then("^inicio de sesion exitoso$")
    public void inicio_de_sesion_exitoso()  {
        assertNotNull(actualUser);
    }

    @Given("^es usuario no valido$")
    public void es_usuario_no_valido()  {
        userName = "usuario_no_valido";
    }

    @Then("^inicio de sesion no exitoso$")
    public void inicio_de_sesion_no_exitoso() {
        assertTrue(currentException instanceof InvalidUserAndPasswordException);
    }


}
