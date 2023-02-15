package seedData;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SeedDataThread extends Thread {

    private String key;
    private List<String> movieList;
    private CountDownLatch latch;
    private String name;
    public SeedDataThread(String ApiKey, List<String> movieList, CountDownLatch latch,String name) {
        this.key = ApiKey;
        this.movieList = movieList;
        this.latch = latch;
        this.name = name;
    }
    public void run(){
        var omdbController = new omdbController();
        var outputList = new ArrayList<String>();
        System.out.println("Starting thread: " + name);
        for ( int j = 0; j < movieList.size(); j++ ) {
            String[] split = movieList.get(j).split(",");
            try {
                outputList.add((movieList.get(j) + "," + omdbController.searchImdb(this.key,split[1],split[2])).replace("\n"," "));
            } catch (Exception e) {
                outputList.add(movieList.get(j) + ",,,,");
            }
            //System.out.println(movieList.get(j));
        }
        try(BufferedWriter writer = Files.newBufferedWriter(Path.of("data/movie_titles"+name+".txt"), Charset.forName("UTF-8"))){
            for (var movieLine : outputList) {
                writer.write(movieLine);
                writer.newLine();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        latch.countDown();
        System.out.println("Ending thread: " + name);
        System.out.println(key);
    }
}
