package com.rcpl.jeep;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "LoginAuth", urlPatterns = {"/loginauth"})
public class LoginAuth extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
                
        try 
        {
           Connection con = DBService.getConnection();
           PreparedStatement pst = con.prepareStatement("select * from customers where userid = ? and pass = ?");
            
           String id = request.getParameter("userid");
           String pass = request.getParameter("password");
           
           pst.setString(1,id);
           pst.setString(2,pass);
           
           ResultSet rs = pst.executeQuery();
           
           if(rs.next())
           {
               out.println("<font color = green>Login Successful</font>");             
               
               if(rs.getString("type").equalsIgnoreCase("admin"))
               {
                   RequestDispatcher rd = request.getRequestDispatcher("admin");
                   request.setAttribute("id", id);
                   rd.include(request, response);
                   
               }
               else
               {
                   RequestDispatcher rd = request.getRequestDispatcher("sr");
                   request.setAttribute("id", id);
                   rd.include(request, response);                   
               }
           }
           else
           {
               response.sendRedirect("login.html");
               out.println("<font color = red>Login Failed</font>");
           }
           
           
        }
        catch(Exception e)
        {
            out.println(e.getMessage());
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
