package api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;

public class UserApiTest {

    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    private static final String TOKEN = "Bearer 040f7c001ba687666f4c5d15fe612424c11ead57d309a77518db673edb9e1fb8";
    private static final String USERS_ENDPOINT = "/users";
    private String reusablePayload;
    int userId;
    


    @Test
    public void getUsers() {
    	Response response = ApiHelper.get(BASE_URL, TOKEN, USERS_ENDPOINT);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        List<Map<String, Object>> users = response.jsonPath().getList("");

        for (Map<String, Object> user : users) {
            assertValidUser(user);
        }
    }

    @Test
    public void createUser() throws JsonProcessingException {
        reusablePayload = ApiUtil.getRandomUser();

        Response response = ApiHelper.post(BASE_URL, TOKEN, USERS_ENDPOINT, reusablePayload);
        userId = response.jsonPath().getInt("id");
        Assert.assertEquals(response.getStatusCode(), 201, "User creation failed");

        String expectedName = (String) ApiUtil.toMap(reusablePayload).get("name");
        Assert.assertEquals(response.jsonPath().getString("name"), expectedName, "Name mismatch in response");
    }


    @Test(dependsOnMethods = "createUser")
    public void deleteUser() {

        Response deleteResponse = ApiHelper.delete(BASE_URL, TOKEN, USERS_ENDPOINT + "/" + userId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 204, "User deletion failed");
    }
    
    private void assertValidUser(Map<String, Object> user) {
        int id = (Integer) user.get("id");
        String name = (String) user.get("name");
        String email = (String) user.get("email");
        String gender = (String) user.get("gender");
        String status = (String) user.get("status");

        Assert.assertNotNull(id, "User ID is null");
        Assert.assertNotNull(name, "Name is null for user ID: " + id);
        Assert.assertTrue(email.contains("@"), "Invalid email for user ID: " + id + ", email: " + email);
        Assert.assertTrue(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"),
                "Invalid gender for user ID: " + id + ", gender: " + gender);
        Assert.assertEquals(status, "active", "User status is not active for user ID: " + id);
    }
}
