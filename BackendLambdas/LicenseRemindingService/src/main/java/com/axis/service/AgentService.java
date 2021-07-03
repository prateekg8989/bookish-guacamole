package com.axis.service;

import com.axis.config.SesConfig;
import com.axis.dao.AgentDao;
import com.axis.model.Agent;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgentService {

    static final String FROM = "prateekg8989@gmail.com";
    static final String FROMNAME = "JD-Bank";
    static final String SMTP_USERNAME = System.getenv("SMTP_USERNAME");
    static final String SMTP_PASSWORD = System.getenv("SMTP_PASSWORD");
    ;
    static final String HOST = "email-smtp.us-east-2.amazonaws.com";
    static final int PORT = 587;
    private AgentDao agentDao = new AgentDao();

    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public void sendEmails() {
        Map<String, List<String>> map = getEmailsOfAgentsWhoseLicenseExpiredOrGoingToExpire();
        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for (Map.Entry<String, List<String>> entry :
                entries) {
            if(entry.getValue().size() > 0) {
                sendEmail(entry.getValue(), "JD-Bank notification", entry.getKey());
            }
        }
    }

    public Map<String, List<String>> getEmailsOfAgentsWhoseLicenseExpiredOrGoingToExpire() {
        List<String> emailsWhoseLicenseExpired = new ArrayList<>();
        List<String> emailsWhoseLicenseGoingToExpire = new ArrayList<>();
        List<Agent> agents = agentDao.getAgentWhoseLicenseExpiredOrGoingToExpire();
        agents.forEach(System.out::println);
        agents.forEach(s -> {
            Date todaysDate = new Date();
            System.out.println("Agent:- > " + s);
            if (todaysDate.after(s.getLicenseExpiryDate())) {
                System.out.println("license-expired" + s.getEmail());
                emailsWhoseLicenseExpired.add(s.getEmail());
            } else {
                System.out.println("license-expiringSoon" + s.getEmail());
                emailsWhoseLicenseGoingToExpire.add(s.getEmail());
            }
        });

        Map<String, List<String>> map = new HashMap<>();
        map.put("license-expired", emailsWhoseLicenseExpired);
        map.put("license-expiringSoon", emailsWhoseLicenseGoingToExpire);
        return map;
    }

    private void sendEmail(List<String> recepientEmails, String subject, String templateName) {
        SesClient sesClient = SesConfig.getSesClient();
        String htmlPart = readLineByLineJava8(templateName + "-body.txt");

        Content subjectContent = Content.builder().data(subject).build();
        Content contentBody = Content.builder().data(htmlPart).build();
        Body body1 = Body.builder().html(contentBody).build();
        Message message = Message.builder().subject(subjectContent).body(body1).build();
        Destination destination = Destination.builder().toAddresses(recepientEmails).build();

        sesClient.sendEmail(SendEmailRequest.builder().message(message).source("prateekg8989@gmail.com").destination(destination).build());
        System.out.println("Email sent!");
    }

}
