package com.rcpl.jeep;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Register", urlPatterns = {"/regis"})
public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
 
        try 
        {
            Connection con = DBService.getConnection();
            PreparedStatement pst = con.prepareStatement("insert into customers values(?,?,?,?)");
            
            
            int userid = Integer.parseInt(request.getParameter("userid"));           
            String fname = request.getParameter("firstname");
            String lname = request.getParameter("lastname");            
            String dob = request.getParameter("dob");            
            String pass = request.getParameter("password"); 
            String name = fname +" "+lname;
            
            pst.setInt(1,userid);
            pst.setString(2,name);
            pst.setString(3,dob);
            pst.setString(4, pass);
            
            int p = pst.executeUpdate();
            
            if(p>0)
            {
                out.println("<font color = green>Records inserted successfully.</font>");
            }
            else
            {
                out.println("<font color = red>Records not inserted successfully.</font>");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
