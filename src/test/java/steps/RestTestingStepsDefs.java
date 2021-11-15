package steps;

import client.Impl.Client;
import entity.Room;
import entity.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static entity.Entrance.ENTRANCE;
import static entity.Entrance.EXIT;

public class RestTestingStepsDefs {
    Client client = new Client();
    ValidatableResponse vResponse;
    User[] users;

    Integer[] keyIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    Integer[] roomIds = {1, 2, 3, 4, 5};

    @Given("All rooms are empty")
    public void allRoomsAreEmpty() {
        client.clearAllRooms();
        Assert.assertEquals( 0, client.getRoomsInfo().length);
    }

    @When("Person #{int} enters in a room #{int}")
    public void personEntersInARoom(int keyId, int roomId) {
        vResponse = client.checkEnterance(ENTRANCE, keyId, roomId);
    }

    @Then("There is a person #{int} in the room #{int}")
    public void thereIsAPersonInTheRoom(int keyId, int roomId) {
        Room[] rooms = client.getRoomsInfo();
        Assert.assertEquals(roomId, rooms[0].roomId);
        Assert.assertEquals(keyId,  rooms[0].userIds[0].intValue());
    }

    @When("Person tries to enter in a same room twice")
    public void personTriesToEnterInASameRoomTwice() {
        client.checkEnterance(ENTRANCE, 1, 1);
        vResponse = client.checkEnterance(ENTRANCE, 1, 1);
    }

    @Then("Response status is {int}")
    public void responseStatusIs(int code) {
        vResponse.statusCode(code);
    }

    @When("Person tries to enter in a wrong room")
    public void personTriesToEnterInAWrongRoom() {
        for (int keyId : keyIds) {
            for (int roomId : roomIds){
                if (keyId % roomId != 0){
                    System.out.println("key: " + keyId + " room: " + roomId);
                    vResponse = client.checkEnterance(ENTRANCE, keyId, roomId);
                    vResponse.statusCode(403);
                }
            }
        }
    }

    @When("Person tries to enter and to exit in a valid room")
    public void personTriesToEnterAndToExitInAValidRoom() {
        for (int keyId : keyIds) {
            for (int roomId : roomIds){
                if (keyId % roomId == 0){
                    System.out.println("key: " + keyId + " room: " + roomId);
                    vResponse = client.checkEnterance(ENTRANCE, keyId, roomId);
                    vResponse.statusCode(200);
                    vResponse = client.checkEnterance(EXIT, keyId, roomId);
                    vResponse.statusCode(200);
                }
            }
        }
    }

    @When("Person #{int} exits from a room #{int}")
    public void personExitsFromARoom(int keyId, int roomId) {
        vResponse = client.checkEnterance(EXIT, keyId, roomId);
    }

    @When("Sending parameters is {string}, {long}, {long}")
    public void sendingParametersIsEnteranceKeyIdRoomId(String enterance, long keyId, long roomId) {
        vResponse = client.checkEnterance(enterance, keyId, roomId);
    }

    @When("Users info sending parameters is {long}, {long}")
    public void usersInfoSendingParametersIsStartEnd(long start, long end) {
        vResponse = client.getUsersInfoResponce(start, end);
    }

    @When("Try get user info only about {int}")
    public void tryGetUserInfoOnlyAboutUser(int userId) {
        users = client.getUsersInfo(userId, userId);
    }

    @Then("Receive information about only this {int}")
    public void receiveInformationAboutOnlyThisUser(int userId) {
        Assert.assertNotEquals("Empty body", 0, users.length);
        Assert.assertEquals("User id's mismatch", userId, users[0].id);
    }

    @Then("Receive information about only one user")
    public void receiveInformationAboutOnlyOneUser() {
        Assert.assertEquals("Received info is not about single user", 1, users.length);
    }

    @When("Users info sending range is {int}, {int}")
    public void usersInfoSendingRangeIsStartEnd(int start, int end) {
        users = client.getUsersInfo(start, end);
    }

    @Then("Receive information only about users in sending range {int}, {int}")
    public void receiveInformationOnlyAboutUsersInSendingRange(int start, int end) {
        Assert.assertNotEquals("Empty body", 0, users.length);
        Assert.assertTrue("Received more users then expected",end - start >= users.length);
        for (User user: users) {
            Assert.assertTrue("User is out of sending range", user.id >= start & user.id <= end);
        }

    }
}
