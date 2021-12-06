<%@ page import="com.example.AutomatedMailSystem.MailProject.SendMailBySite" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    GenerateReport();
%>

<%!
    public static void GenerateReport() {
        String line;
        try {
            URL url = new URL("http://localhost:8088/AutomatedMailSystem_war/DownloadReports");
          //  URL url = new URL("http://93.104.213.236/RobbialacAutomatedMailSystem/DownloadReports");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            line = in.readLine();
            System.out.println(line);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>