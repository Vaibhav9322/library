package com.library.dao;

import com.library.entity.Publisher;
import com.library.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {
    private static final String PUBLISHERS_FILE = "publishers.txt";
    
    public boolean addPublisher(Publisher publisher) {
        publisher.setPublisherId(getNextId());
        String line = publisherToString(publisher);
        FileUtil.appendToFile(PUBLISHERS_FILE, line);
        return true;
    }
    
    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = new ArrayList<>();
        List<String> lines = FileUtil.readFile(PUBLISHERS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                publishers.add(stringToPublisher(line));
            }
        }
        return publishers;
    }
    
    public Publisher getPublisherById(int publisherId) {
        List<String> lines = FileUtil.readFile(PUBLISHERS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Publisher publisher = stringToPublisher(line);
                if (publisher.getPublisherId() == publisherId) {
                    return publisher;
                }
            }
        }
        return null;
    }
    
    private String publisherToString(Publisher publisher) {
        return publisher.getPublisherId() + "|" + publisher.getPublisherName() + "|" + 
               publisher.getAddress() + "|" + publisher.getPhone() + "|" + 
               publisher.getEmail() + "|" + publisher.getWebsite();
    }
    
    private Publisher stringToPublisher(String line) {
        String[] parts = line.split("\\|");
        Publisher publisher = new Publisher();
        publisher.setPublisherId(Integer.parseInt(parts[0]));
        publisher.setPublisherName(parts[1]);
        publisher.setAddress(parts[2]);
        publisher.setPhone(parts[3]);
        publisher.setEmail(parts[4]);
        publisher.setWebsite(parts[5]);
        return publisher;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(PUBLISHERS_FILE);
        int maxId = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) maxId = id;
            }
        }
        return maxId + 1;
    }
}