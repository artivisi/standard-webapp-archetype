package ${package}.ui.controller;

import com.jayway.restassured.authentication.FormAuthConfig;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.with;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class HomepageControllerTestIT {

    private String target = "http://localhost:10000/homepage";
    private String login = "http://localhost:10000/j_spring_security_check";
    private String username = "endy";
    private String password = "123";


    @Test
    public void testGetAppinfo() {
        with().header("Accept", "application/json")
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .expect()
                .statusCode(200)
                .body("profileDefault", equalTo("development"),
                    "profileActive", equalTo(""),
                    "namaAplikasi", equalTo("${project-name}")
                )
                .when()
                .get(target + "/" + "appinfo");
    }

}
