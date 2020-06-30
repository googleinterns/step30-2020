import java.util.ArrayList;
import java.lang.Integer;

public class Room {
  private String title;
  private int partyLeaderID;
  private String videoURL;
  private ArrayList<Integer> currentUserIDs;
  private boolean privateRoom;
  //private int currentVideo;//index of current video in queue
  private boolean paused;
  private int currentPosition;
  //private ArrayList<Video> queuedVideos;
  //private ArrayList<Video> suggestedVideos;
  public void createRoomEntity(){
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity entity = new Entity("Room");
    taskEntity.setProperty("title", title);
    taskEntity.setProperty("users", currentUserIDs);
    taskEntity.setProperty("privateRoom", privateRoom);
    taskEntity.setProperty("paused", paused);
    taskEntity.setProperty("currentPosition", currentPosition);
    taskEntity.setProperty("partyLeaderID", partyLeaderID);
    taskEntity.setProperty("videoURL", videoURL);
    datastore.put(entity);
  }
  public Room(Entity entity){
    title = entity.getProperty("title");
    partyLeaderID = entity.getProperty("partyLeaderID");
    currentUserIDs = entity.getProperty("users");
    privateRoom = entity.getProperty("privateRoom");
    paused = entity.getProperty("paused");
    currentPosition = entity.getProperty("currentPosition");
    videoURL = entity.getProperty("videoURL");
  }
}

