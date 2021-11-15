package client.Impl;

import entity.Entrance;
import entity.Room;
import entity.User;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static entity.Entrance.EXIT;

public class Client implements client.Client {
    private static final String BASE_URL = "http://localhost:8080";

    @Override
    public Room[] getRoomsInfo() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .request("GET", "/info/rooms")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Room[].class);
    }

    @Override
    public User[] getUsersInfo() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .request("GET", "/info/users")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(User[].class);
    }

    public User[] getUsersInfo(int start, int end) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .param("start", start)
                .param("end", end)
                .request("GET", "/info/users")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(User[].class);
    }

    @Override
    public ValidatableResponse checkEnterance(Entrance entrance, int keyId, int roomId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .param("entrance", entrance)
                .param("keyId", keyId)
                .param("roomId", roomId)
                .request("GET", "/check")
                .prettyPeek()
                .then();
    }

    public ValidatableResponse checkEnterance(String entrance, long keyId, long roomId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .param("entrance", entrance)
                .param("keyId", keyId)
                .param("roomId", roomId)
                .request("GET", "/check")
                .prettyPeek()
                .then();
    }

    public ValidatableResponse getUsersInfoResponce(long start, long end) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .param("start", start)
                .param("end", end)
                .request("GET", "/info/users")
                .prettyPeek()
                .then();
    }

    public void clearAllRooms(){
        Room[] rooms = getRoomsInfo();
        for (Room room:rooms) {
            for (int userId: room.userIds) {
                checkEnterance(EXIT.toString(), userId, room.roomId);
            }
        }
    }
}
