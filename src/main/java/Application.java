import client.Impl.Client;

public class Application {
    public static void main(String[] args) {
        Client client = new Client();

//        client.clearAllRooms();
        client.getUsersInfoResponce(1, 20);

    }
}
