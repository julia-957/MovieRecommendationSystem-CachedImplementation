package seedData;

import dk.easv.entities.Movie;
import javafx.event.Event;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        List<String> listOfKeys = new ArrayList<>();
        List<SeedDataThread> listOfThreads = new ArrayList<>();

        listOfKeys.add("5912fc2b");
        listOfKeys.add("db2006b8");
        listOfKeys.add("c77031");
        listOfKeys.add("23e46eb0");
        listOfKeys.add("6d87d17c");
        listOfKeys.add("c5a4d6fb");
        listOfKeys.add("120d3f7d");
        listOfKeys.add("66b26046");
        listOfKeys.add("9fea8acf");
        listOfKeys.add("5a878d0b");
        listOfKeys.add("74cb7a0b");
        listOfKeys.add("52a2d99e");
        listOfKeys.add("5e7b48bc");
        listOfKeys.add("a042cca2");
        listOfKeys.add("ffc945f1");
        listOfKeys.add("b6fd0545");
        listOfKeys.add("7ab544d");
        listOfKeys.add("c0a0cdf4");
        listOfKeys.add("b712184d");

        List<String> movieLines = null;
        try {
            movieLines = Files.readAllLines(Path.of("data/movie_titles.txt"));
            System.out.println(movieLines.size()/1000);
            CountDownLatch latch = new CountDownLatch( (int)(movieLines.size()/1000));
            for (int i = 0; i < (int)movieLines.size()/1000.0; i++) {
                var thread = new SeedDataThread(listOfKeys.get(i),movieLines.stream().skip(i*1000).limit(1000).toList(),latch, i+1+"");
                listOfThreads.add(thread);
                thread.start();
            }
            latch.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
