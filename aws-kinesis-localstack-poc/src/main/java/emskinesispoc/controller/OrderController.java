package emskinesispoc.controller;

import emskinesispoc.service.KinesisStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {

    @Autowired
    private KinesisStreamService kinesisStreamService;

    @Value("${aws.stremName}")
    private String stremName;

    @Value("${aws.partionKey}")
    private String key = "12345";





    @PostMapping("/stream")
    public ResponseEntity<String> addToStream(@RequestBody String order) throws IOException {

        System.out.println(" OrderController Call add to Stream -- " + order);
        System.out.println(" OrderController stremName -- " + stremName);



        try {
            kinesisStreamService.pushToStream(stremName, key);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException " + e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.ok("Data inserted into kinesis successfully.");
    }

}
