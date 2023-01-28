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
    void testGetAllMountains() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\",\"routeImage\":\"image\"}]";

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
        String expectedResponse = "[{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\",\"routeImage\":\"image\"}]";

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
    void testMissingParameterGetMountainByName() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMountainByName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetMountainById() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\",\"routeImage\":\"image\"}";

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
    void testMissingParameterGetMountainById() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMountainById")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetMountainsByRegion() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"name\":\"Ben Nevis\",\"height\":\"3000ft\",\"description\":\"very tall\",\"region\":\"Scotland\",\"coords\":\"35849484\",\"routeImage\":\"image\"}]";

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
    void testMissingParameterGetMountainsByRegion() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMountainsByRegion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetUserByUsername() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"username\":\"aaron67\",\"password\":\"password123\",\"email\":\"\"}";

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
    void testMissingParameterGetUserByUsername() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getUserByUsername")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetUserByPassword() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"username\":\"aaron67\",\"password\":\"password123\",\"email\":\"\"}";

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
    void testMissingParameterGetUserByPassword() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getUserByPassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testCreateUser() throws Exception {

        // arrange
        String randomUsername = UUID.randomUUID().toString();

        // act
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createUser?username=" + randomUsername + "&password=dog&email=cat")
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
        MvcResult mvcResult = this.mockMvc.perform(post("/hiking/createUser?username=" + randomUsername + "&password=dog&email=cat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = this.mockMvc.perform(post("/hiking/createUser?username=" + randomUsername + "&password=dog&email=cat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult2.getResponse().getStatus()).isEqualTo(409);

    }

    @Test
    void testGetMunrosBaggedByUsername() throws Exception {

        // arrange
        String expectedResponse = "[{\"id\":1,\"username\":\"aaron67\",\"mountainId\":36,\"date\":\"27/05/2000\",\"rating\":\"10/10\",\"comments\":\"very enjoyable\"}]";

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
    void testMissingParameterGetMunrosBaggedByUsername() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getMunrosBaggedByUsername")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetTripByOrganiserId() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"organiserId\":23,\"mountainId\":55,\"date\":\"03/08/2015\",\"description\":\"good trip had by all\"}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getTripByOrganiserId?organiserId=23")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testMissingParameterGetTripByOrganiserId() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getTripByOrganiserId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }

    @Test
    void testGetTripGroupByTripId() throws Exception {

        // arrange
        String expectedResponse = "{\"id\":1,\"tripId\":2,\"username\":\"aaron67\",\"status\":\"yes\"}";

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getTripGroupByTripId?tripId=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getContentType()).contains("application/json");
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void testMissingParameterGetTripGroupByTripId() throws Exception {

        // arrange

        // act
        MvcResult mvcResult = this.mockMvc.perform(get("/hiking/getTripGroupByTripId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(404);
    }
}