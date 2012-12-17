package ${package}.ui.controller;

import ${package}.domain.Permission;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.with;
import com.jayway.restassured.authentication.FormAuthConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PermissionControllerTestIT {

    private String target = "http://localhost:10000/permission";
    private String login = "http://localhost:10000/j_spring_security_check";
    private String username = "endy";
    private String password = "123";

    @Test
    public void testSaveInvalid(){
        Permission u = new Permission();
        
        given()
            .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
            .contentType("application/json")
            .body(u)
            .expect().statusCode(400).when().post(target);
    }

    @Test
    public void testSaveUpdateDelete() {

        Permission x = new Permission();
        x.setLabel("Permission Percobaan");
        x.setValue("test");

        String id = testSave(target, x);
        System.out.println("Id : " + id);
        testGetExistingById(id, x);
        
        x.setLabel("Ganti Label");
        x.setValue("Ganti Value");
        
        testUpdateExisting(id, x);
        testGetExistingById(id, x);
        testDeleteExistingById(id);
    }

    private String testSave(String target, Permission x) {
        String location = given()
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .contentType("application/json")
                .body(x)
                .expect().statusCode(201).when().post(target)
                .getHeader("Location");

        assertNotNull(location);
        assertTrue(location.startsWith(target));

        String[] locationSplit = location.split("/");
        String id = locationSplit[locationSplit.length - 1];

        return id;
    }

    private void testGetExistingById(String id, Permission x) {
        with().header("Accept", "application/json")
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .expect()
                .statusCode(200)
                .body("label", equalTo(x.getLabel()), "value", equalTo(x.getValue()))
                .when().get(target + "/" + id);
    }

    private void testUpdateExisting(String id, Permission x) {

        given()
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .contentType("application/json")
                .body(x)
                .expect()
                .statusCode(200).when().put(target + "/" + id);
    }

    private void testDeleteExistingById(String id) {
        given().auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .expect().statusCode(200).when().delete(target + "/" + id);

        given().auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .expect().statusCode(404).when().get(target + "/" + id);
    }

    @Test
    public void testGetExistingById() {
        with().header("Accept", "application/json")
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .expect()
                .statusCode(200)
                .body("id", equalTo("user-view"),
                "label", equalTo("Lihat User"),
                "value", equalTo("ROLE_USER_VIEW")).when()
                .get(target + "/" + "user-view");
    }

    @Test
    public void testGetNonExistentById() {
        with()
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .expect().statusCode(404).when().get(target + "/" + "/nonexistentconfig");
    }

    @Test
    public void testFindAll() {
        with()
                .auth().form(username, password, new FormAuthConfig(login, "j_username", "j_password"))
                .header("Accept", "application/json").expect().statusCode(200)
                .body("id", hasItems("user", "user-view")).when().get(target);
    }

}
