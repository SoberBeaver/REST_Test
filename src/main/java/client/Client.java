package client;

import entity.Entrance;
import entity.Room;
import entity.User;
import io.restassured.response.ValidatableResponse;

public interface Client {
    Room[] getRoomsInfo();
    User[] getUsersInfo();
    ValidatableResponse checkEnterance(Entrance entrance, int keyId, int roomId);
}
