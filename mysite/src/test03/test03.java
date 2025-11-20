package test03;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;


public class test03 extends HttpServlet{ 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
    throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        out.print("test03test03test03 !!!!!!");
    }
}
