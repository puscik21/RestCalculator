package com.silvair.demo.controller;

import com.silvair.demo.service.history.HistoryService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculateControllerTest {

    @Autowired
    WebApplicationContext webAppContextSetup;

    @Autowired
    HistoryService historyService;

    MockMvcRequestSpecification fineRequest;
    MockMvcRequestSpecification badRequest;

    private final String CALCULATE_PATH = "/calculate";
    private final String HISTORY_PATH = "/history";
    private final String HISTORY_SUM_PATH = "/history/sum";
    private final String STATISTICS_PATH = "/statistics";

    @BeforeAll
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webAppContextSetup);
        prepareFineRequest();
        prepareBadRequest();
    }

    private void prepareFineRequest() {
        fineRequest = RestAssuredMockMvc.given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"a\": 3,\n" +
                        "    \"b\": 5,\n" +
                        "    \"text\": \"\",\n" +
                        "    \"type\": \"ADD\"\n" +
                        "}");
    }

    private void prepareBadRequest() {
        badRequest = RestAssuredMockMvc.given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"a\": 3,\n" +
                        "    \"b\": 5,\n" +
                        "    \"text\": \"\",\n" +
                        "    \"type\": \"TEXT\"\n" +
                        "}");
    }

    @Test
    void operationHistoryShouldBeSaved() {
        int countBefore = historyService.getAllHistoryRecords().size();
        RestAssuredMockMvc.given()
                .spec(fineRequest)
                .post(CALCULATE_PATH);
        assertEquals(countBefore + 1, historyService.getAllHistoryRecords().size());
    }

    @Test
    void calculateFineRequestShouldReturnOkStatus() {
        ResponseOptions response = RestAssuredMockMvc.given()
                .spec(fineRequest)
                .post(CALCULATE_PATH);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    void calculateBadRequestShouldReturnBadRequestStatus() {
        ResponseOptions response = RestAssuredMockMvc.given()
                .spec(badRequest)
                .post(CALCULATE_PATH);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    void callingHistoryShouldSaveHistoryRecord() {
        RestAssuredMockMvc.given()
                .get(HISTORY_PATH);
        assertEquals(HISTORY_PATH, historyService.getNumberOfHistoryRecords(1).get(0).getPath());
    }

    @Test
    void callingHistorySumShouldSaveHistoryRecord() {
        RestAssuredMockMvc.given()
                .get(HISTORY_SUM_PATH);
        assertEquals(HISTORY_SUM_PATH, historyService.getNumberOfHistoryRecords(1).get(0).getPath());
    }

    @Test
    void callingStatisticsShouldSaveHistoryRecord() {
        RestAssuredMockMvc.given()
                .get(STATISTICS_PATH);
        assertEquals(STATISTICS_PATH, historyService.getNumberOfHistoryRecords(1).get(0).getPath());
    }
}