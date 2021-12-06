package com.example.AutomatedMailSystem.Reports;

import com.example.AutomatedMailSystem.Database.DBHandler;
import com.example.AutomatedMailSystem.MailProject.SendMailBySite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet(name = "DownloadReports", value = "/DownloadReports")
public class DownloadReports extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //String Path = "//opt//apache-tomcat-2//webapps//HNBFinance//Reports//"; // plz change this
        String Path = "E://Reports//";


        File filePath = File.createTempFile("ManualReloadReport", ".csv", new File(Path));
        String filename = filePath.getName();

        Connection con = DBHandler.createDBConnection();
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.append("MobileNumber");
            fw.append(',');
            fw.append("SecretCode");
            fw.append(',');
            fw.append("ExternalReferenceCode");
            fw.append(',');
            fw.append("ExternalReferenceID");
            fw.append(',');
            fw.append("ProductName");
            fw.append(',');
            fw.append("ReloadValue");
            fw.append(',');
            fw.append("LoyaltyPointValue");
            fw.append(',');
            fw.append("Status");
            fw.append(',');
            fw.append("DateTime");
            fw.append('\n');

            {
                ResultSet RsGetReport = con.createStatement().executeQuery("SELECT * FROM reloads WHERE Status != 'success'");
                while (RsGetReport.next()) {
                    fw.append(RsGetReport.getString(2));
                    fw.append(',');
                    fw.append(RsGetReport.getString(3));
                    fw.append(',');
                    fw.append(RsGetReport.getString(4));
                    fw.append(',');
                    fw.append(RsGetReport.getString(5));
                    fw.append(',');
                    fw.append(RsGetReport.getString(6));
                    fw.append(',');
                    fw.append(RsGetReport.getString(7));
                    fw.append(',');
                    fw.append(RsGetReport.getString(8));
                    fw.append(',');
                    fw.append(RsGetReport.getString(9));
                    fw.append(',');
                    fw.append(RsGetReport.getString(10));
                    fw.append('\n');
                    System.out.printf("");
                }
                fw.flush();
                fw.close();
                con.close();
                response.setContentType("text/plain");


                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                File my_file = new File(Path + filename);


                OutputStream out = response.getOutputStream();
                FileInputStream in = new FileInputStream(my_file);
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }


                in.close();
                out.flush();



                SendMailBySite.SendMail(Path,filename);
                File myObj = new File(Path + filename);
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                myObj.delete();
                            }
                        },
                        3600000
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
