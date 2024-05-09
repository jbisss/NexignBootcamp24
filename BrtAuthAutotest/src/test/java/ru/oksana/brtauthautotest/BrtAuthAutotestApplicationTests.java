package ru.oksana.brtauthautotest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class BrtAuthAutotestApplicationTests {

    final String LINE_BREAK = "\n";

    @Test
    void test() {
        final String cdrTest = """
                02,79115763555,79215763555,1709798657,1709798657
                01,79215763555,79115763555,1709798657,1709798657
                01,79115763777,79215763555,1709798659,1709798659
                02,79215763555,79115763777,1709798659,1709798659
                02,79115763777,79215763555,1709798657,1709798658
                01,79215763555,79115763777,1709798657,1709798658
                01,79115763555,79215763555,1709798657,1709798657
                02,79215763555,79115763555,1709798657,1709798658
                02,79212131255,79115763777,1709798657,1709798716
                01,79115763777,79212131255,1709798657,1709798716
                01,79015763555,79211233555,1709798657,1709798716
                02,79211233555,79015763555,1709798657,1709798716
                02,79212131155,79312131155,1713899448,1713899508
                01,79312131155,79212131155,1713899448,1713899508""";

        final String expectedResponse = """
                02,79115763555,79215763555,1709798657,1709798657,11
                01,79215763555,79115763555,1709798657,1709798657,12
                02,79215763555,79115763777,1709798659,1709798659,12
                01,79215763555,79115763777,1709798657,1709798658,12
                01,79115763555,79215763555,1709798657,1709798657,11
                02,79215763555,79115763555,1709798657,1709798658,12
                02,79212131255,79115763777,1709798657,1709798716,12
                02,79211233555,79015763555,1709798657,1709798716,12
                02,79212131155,79312131155,1713899448,1713899508,11""";

        RestAssured.baseURI = "http://localhost:52948";
        Response response = given()
                .contentType(ContentType.TEXT)
                .body(cdrTest)
                .when()
                .post("/test/test");
        String responseBody = response.getBody().print();

        response.then()
                .assertThat()
                .statusCode(200);
        String[] responseRows = responseBody.split(LINE_BREAK);
        String[] expectedRows = expectedResponse.split(LINE_BREAK);
        for (int i = 0; i < responseRows.length; i++) {
            assertEquals(expectedRows[i], responseRows[i]);
        }
    }
}
