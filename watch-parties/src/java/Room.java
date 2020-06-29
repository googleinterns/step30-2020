import java.util.ArrayList;
import java.lang.Integer;

public class Room {
  private String title;
  private int partyLeaderID;
  private ArrayList<Integer> currentUserIDs;
  private boolean privateRoom;
  private int currentVideo;//index of current video in queue
  private boolean paused;
  private int currentPosition;
  private ArrayList<Video> queuedVideos;
  private ArrayList<Video> suggestedVideos;
}

