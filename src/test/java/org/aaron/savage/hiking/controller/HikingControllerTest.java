package org.aaron.savage.hiking.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class HikingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetImage() throws Exception {

        // act

        // arrange
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getImage?munro=Ben Hope")
                        .contentType(MediaType.IMAGE_JPEG_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testGetAllMountains() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getAllMountains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testGetMountainByName() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMountainByName?name=Ben Nevis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testGetMountainById() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\"}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMountainById?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testGetMountainsByRegion() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMountainsByRegion?region=Scotland")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testGetUserByUsername() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"username\":\"aaron67\",\"email\":\"\",\"activated\":\"\"}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getUserByUsername?username=aaron67")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testGetUserByPassword() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"username\":\"aaron67\",\"email\":\"\",\"activated\":\"\"}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getUserByPassword?password=password123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testCreateUser() throws Exception {

        // arrange
        String randomUsername = UUID.randomUUID().toString();

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createUser?username=" + randomUsername + "&password=dog&email=aaron.g.savage@icloud.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testDuplicateEntryWhenCreateUser() throws Exception {

        // arrange
        String randomUsername = UUID.randomUUID().toString();

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createUser?username=" + randomUsername + "&password=dog&email=aaron.g.savage@icloud.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(post("/hiking/createUser?username=" + randomUsername + "&password=dog&email=aaron.g.savage@icloud.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult2.getResponse().getStatus()).isEqualTo(409);
    }

    @Test
    void testValidateUserCode() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/validateUserCode?username=fb77c3da-fe3e-43c9-ad7a-9155a0c38aca&code=122253")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testValidateUserCodeDoesNotMatch() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/validateUserCode?username=758d8c0c-16e3-4a57-8c62-acb3bba0ead6&code=000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testResendCode() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/resendCode?username=758d8c0c-16e3-4a57-8c62-acb3bba0ead6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testGenerateNewCode() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/generateNewCode?username=758d8c0c-16e3-4a57-8c62-acb3bba0ead6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testIsUsernameActivated() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/isUsernameActivated?username=758d8c0c-16e3-4a57-8c62-acb3bba0ead6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testIsUsernameActivatedUserDoesNotExist() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/isUsernameActivated?username=userDoesNotExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testIsUsernameActivatedUserNotActivated() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/isUsernameActivated?username=bde764f6-c901-4da9-aedc-ced19653ea3c")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    void testResetPassword() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/resetPassword?username=53116723-bf2f-4ae9-9dae-e27947773c3f&newPassword=newPass2&code=515572")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testResetPasswordUserCodeDoesNotMatch() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/resetPassword?username=758d8c0c-16e3-4a57-8c62-acb3bba0ead6&newPassword=newPass2&code=000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testValidateLogin() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/validateLogin?username=758d8c0c-16e3-4a57-8c62-acb3bba0ead6&password=dog")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testValidateLoginUserNotActivated() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/validateLogin?username=059828d3-6dc0-4e51-b23c-67cba6df7033&password=dog")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    void testValidateLoginUserDoesNotExist() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/validateLogin?username=userDoesNotExist&password=dog")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetMunrosBaggedByUsername() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"username\":\"aaron67\",\"mountainId\":36,\"mountainName\":\"\",\"date\":\"27/05/2000\",\"rating\":\"10/10\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMunrosBaggedByUsername?username=aaron67")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testAddMunroToBag() throws Exception {

        // arrange
        long randomMountainId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/addMunroToBag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"gerardSavage88\",\"mountainId\":\"" + randomMountainId + "\",\"mountainName\":\"Dreish\",\"date\":\"17/10/2010\",\"rating\": 4}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testAddMunroToBagDuplicateEntry() throws Exception {

        // arrange
        long randomMountainId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/addMunroToBag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"gerardSavage88\",\"mountainId\":\"" + randomMountainId + "\",\"mountainName\":\"Dreish\",\"date\":\"17/10/2010\",\"rating\": 4}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(post("/hiking/addMunroToBag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"gerardSavage88\",\"mountainId\":\"" + randomMountainId + "\",\"mountainName\":\"Dreish\",\"date\":\"17/10/2010\",\"rating\": 4}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult2.getResponse().getStatus()).isEqualTo(409);
    }

    @Test
    void testGetAllTrips() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getAllTrips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testAddUserToTrip() throws Exception {

        // arrange
        long randomTripId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/addUserToTrip?username=HashTest9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + randomTripId + "\", \"usernames\":[], \"mountainId\":\"27\", \"mountainName\":\"Dreish\", \"date\":\"17/10/2010\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testAddUserToTripDuplicateEntry() throws Exception {

        // arrange
        long randomTripId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/addUserToTrip?username=HashTest9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + randomTripId + "\", \"usernames\":[], \"mountainId\":\"27\", \"mountainName\":\"Dreish\", \"date\":\"17/10/2010\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(post("/hiking/addUserToTrip?username=HashTest9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + randomTripId + "\", \"usernames\":[], \"mountainId\":\"27\", \"mountainName\":\"Dreish\", \"date\":\"17/10/2010\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult2.getResponse().getStatus()).isEqualTo(409);
    }

    @Test
    void testGetTripsByMountainName() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":67,\"usernames\":[\"anne00\"],\"mountainId\":28,\"mountainName\":\"Mayar\",\"date\":\"11/09/2018\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getTripsByMountainName?mountainName=Mayar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testGetTripsByDate() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":67,\"usernames\":[\"anne00\"],\"mountainId\":28,\"mountainName\":\"Mayar\",\"date\":\"11/09/2018\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getTripsByDate?date=11/09/2018")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testCreateTrip() throws Exception {

        // arrange
        String randomDate = String.valueOf(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createTrip?username=HashTest9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mountainId\":\"27\", \"mountainName\":\"Dreish\", \"date\":\"" + randomDate + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testCreateTripDuplicateEntry() throws Exception {

        // arrange
        String randomDate = String.valueOf(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createTrip?username=HashTest9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mountainId\":\"27\", \"mountainName\":\"Dreish\", \"date\":\"" + randomDate + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(post("/hiking/createTrip?username=HashTest9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mountainId\":\"27\", \"mountainName\":\"Dreish\", \"date\":\"" + randomDate + "\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult2.getResponse().getStatus()).isEqualTo(409);
    }

    @Test
    void testGetAllReviews() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getAllReviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testCreateReview() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createReview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"username\": \"gerard88\",\n" +
                                "    \"reviewDate\": \"27/5/2000\",\n" +
                                "    \"mountainId\": 27,\n" +
                                "    \"mountainName\": \"Dreish\",\n" +
                                "    \"walkDate\": \"19/11/1980\",\n" +
                                "    \"rating\": \"4\",\n" +
                                "    \"comment\": \"it was fun\"\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void testGetReviewsByMountainName() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":3,\"username\":\"gerard88\",\"reviewDate\":\"23/3/2023\",\"mountainId\":28,\"mountainName\":\"Mayar\",\"walkDate\":\"9/7/2020\",\"rating\":\"4\",\"comment\":\"it was fun\"}]";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getReviewsByMountainName?name=Mayar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }
}