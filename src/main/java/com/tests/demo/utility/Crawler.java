package com.tests.demo.utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Configuration;

import com.tests.demo.entity.NewsEntity;
import com.tests.demo.repository.NewsEntityRepositoryInterface;

@Configuration
public class Crawler {
    String url;
    
    public static void crawl(int level, String url, ArrayList<String> visited, NewsEntityRepositoryInterface newsEntityRepositoryInterface) {
        try {
            URL originUrl = new URL(url);
            if(level<=20) {
                Document doc = request(url, visited, newsEntityRepositoryInterface);
                if(doc != null) {
                    for(Element link : doc.select("a[href]")) {
                        String extractedLink = link.absUrl("href");
                        //"#" se refera la fragment url's de care nu avem nevoie
                        if(extractedLink.contains("https://") && !extractedLink.contains("#")) {
                            try {
                                URL extractedUrl = new URL(extractedLink);
                                //!!
                                //String extractedUrlString = extractedUrl.getPath().toString();
                                
                                //System.out.println((originUrl.getPath().toString()));
                                if(extractedUrl.getAuthority().equals(originUrl.getAuthority()) && extractedUrl.getPath().toString().contains(extractSubstringFromEnd(originUrl.toString()))) {
                                    if(visited.contains(extractedLink) == false) {
                                        crawl(level++, extractedLink, visited, newsEntityRepositoryInterface);
                                    }
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static Document request(String url, ArrayList<String> v, NewsEntityRepositoryInterface newsEntityRepositoryInterface) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                //System.out.println(doc.title());
                v.add(url);
                
                NewsEntity currentEntity = new NewsEntity();
                currentEntity.setSummarizedContent(null);
                currentEntity.setTitle(doc.title());
                currentEntity.setUrl(new URL(url));

                newsEntityRepositoryInterface.save(currentEntity);
                
                return doc;
            }
            return null;
        }
        catch(IOException e) {
            return null;
        }
    }

    public static String extractSubstringFromEnd(String input) {
        // Check if the input string is not empty
        if (input != null && !input.isEmpty()) {
            // Find the last occurrence of "/"
            int lastIndex = input.lastIndexOf("/");
            
            // Check if "/" is found in the string
            if (lastIndex != -1) {
                // Extract the substring from the end up to the first "/"
                return input.substring(lastIndex + 1);
            } else {
                // If "/" is not found, return the original string
                return input;
            }
        } else {
            // Return null or handle the case of an empty input as needed
            return null;
        }
    }
}