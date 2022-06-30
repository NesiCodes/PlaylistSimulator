import java.io.File;
import java.util.*;

public class Main {

    private static ArrayList<Album> albums = new ArrayList<Album>();
    private static Hashtable<String, Double> albumHashMap = new Hashtable<>();

    public static void main(String[] args) {
        //When the program is first run it gets the album info for song name and their duration from 2 album files
        Album album = new Album("Stormbringer", "Deep Purple");
        try{
            File pointsFile = new File("src/Album1.txt");
            Scanner myReader = new Scanner(pointsFile);
            int count = 0;
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] dataArray = data.split(", ");
                if(count > 0){
                    albumHashMap.put(dataArray[0],Double.valueOf(dataArray[1]));
                }
                count++;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        for (String i : albumHashMap.keySet()) {
            album.addSong(i,albumHashMap.get(i));
        }
        albums.add(album);

        //Second Album
        album = new Album("For those about to rock", "AC/DC");
        try{
            File pointsFile = new File("src/Album2.txt");
            Scanner myReader = new Scanner(pointsFile);
            int count = 0;
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] dataArray = data.split(", ");
                if(count > 0){
                    albumHashMap.put(dataArray[0],Double.valueOf(dataArray[1]));
                }
                count++;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        for (String i : albumHashMap.keySet()) {
            album.addSong(i,albumHashMap.get(i));
        }
        albums.add(album);

        //Create a new playlist and try to add songs that exist in the albums either by their name or track number, if not they cant be added
        LinkedList<Song> playList = new LinkedList<Song>();
        albums.get(0).addToPlayList("You can't do it right", playList);
        albums.get(0).addToPlayList("Holy man", playList);
        albums.get(0).addToPlayList("Speed king", playList);  // For example this one does not exist and should give an error
        albums.get(0).addToPlayList(9, playList);
        albums.get(1).addToPlayList(8, playList);
        albums.get(1).addToPlayList(3, playList);
        albums.get(1).addToPlayList(2, playList);
        albums.get(1).addToPlayList(24, playList);  // There is no track 24 so it should give an error

        //After we add the songs in the playlist we play it and then show the options to the user
        play(playList);
    }

    //Here are the options that the user can use on their playlist as mentioned in the proposal
    //It has checks for everything so that the program wont crash for example users wanting to play the next song when they are at the end etc
    private static void play(LinkedList<Song> playList) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        boolean forward = true;
        ListIterator<Song> listIterator = playList.listIterator();
        if(playList.size() == 0) {
            System.out.println("No songs in playlist");
            return;
        } else {
            System.out.println("Now playing " + listIterator.next().toString());
            printMenu();
        }

        while(!quit) {
            int action = scanner.nextInt();
            scanner.nextLine();

            switch(action) {
                case 0:
                    System.out.println("Playlist complete.");
                    quit = true;
                    break;
                case 1:
                    if(!forward) {
                        if(listIterator.hasNext()) {
                            listIterator.next();
                        }
                        forward = true;
                    }
                    if(listIterator.hasNext()) {
                        System.out.println("Now playing " + listIterator.next().toString());
                    } else {
                        System.out.println("We have reached the end of the playlist");
                        forward = false;
                    }
                    break;

                case 2:
                    if(forward) {
                        if(listIterator.hasPrevious()) {
                            listIterator.previous();
                        }
                        forward = false;
                    }
                    if(listIterator.hasPrevious()) {
                        System.out.println("Now playing " + listIterator.previous().toString());
                    } else {
                        System.out.println("We are at the start of the playlist");
                        forward = true;
                    }
                    break;
                case 3:
                    if(forward) {
                        if(listIterator.hasPrevious()) {
                            System.out.println("Now replaying " + listIterator.previous().toString());
                            forward = false;
                        } else {
                            System.out.println("We are at the start of the list");
                        }
                    } else {
                        if(listIterator.hasNext()) {
                            System.out.println("Now replaying " + listIterator.next().toString());
                            forward = true;
                        } else {
                            System.out.println("We have reached the end of the list");
                        }
                    }
                    break;
                case 4:
                    printList(playList);
                    break;
                case 5:
                    printMenu();
                    break;

                case 6:
                    if(playList.size() >0) {
                        listIterator.remove();
                        if(listIterator.hasNext()) {
                            System.out.println("Now playing " + listIterator.next());
                        } else if(listIterator.hasPrevious()) {
                            System.out.println("Now playing " + listIterator.previous());
                        }
                    }
                    break;

            }
        }
    }

    private static void printMenu() {
        System.out.println("Available actions:\npress");
        System.out.println("0 - to quit\n" +
                "1 - to play next song\n" +
                "2 - to play previous song\n" +
                "3 - to replay the current song\n" +
                "4 - list songs in the playlist\n" +
                "5 - print available actions.\n" +
                "6 - delete current song from playlist");
    }


    //Prints the current playlist
    private static void printList(LinkedList<Song> playList) {
        Iterator<Song> iterator = playList.iterator();
        System.out.println("================================");
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("================================");
    }

}
