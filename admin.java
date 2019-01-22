package com.rcpl.jeep;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "admin", urlPatterns = {"/admin"})
public class admin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try
        {
          Connection con = DBService.getConnection();
          
          int dif=0,ser=0;
          int id = Integer.parseInt((String)request.getAttribute("id"));
          do{
              PreparedStatement st = con.prepareStatement("select datediff(duedate,curdate()),servicesleft from services where userid=?;");
              st.setInt(1, id);
              ResultSet rst = st.executeQuery();
              rst.next();
              ser=rst.getInt("servicesleft");
              dif=rst.getInt("datediff(duedate,curdate())");
              if(dif<0 && ser>0)
              {
                  PreparedStatement str=con.prepareStatement("update services set servicesleft = servicesleft-1 , duedate = date_add(duedate, interval 3 month) where userid=?;");
                  str.setInt(1, id);
                  if(str.executeUpdate()>0)
                  {
                      ser=ser-1;
                  }
              }
            }while(ser>0);
          
          PreparedStatement pst = con.prepareStatement("select * from customers,services,car where services.userid = customers.userid and car.modelno = services.modelno;");

          ResultSet rs = pst.executeQuery();
          
out.println("<link rel=shortcut icon href=img/favicon.ico type=image/x-icon>"
                  + "<link rel=apple-touch-icon href=img/apple-touch-icon.png>"
                  + "<link rel=apple-touch-icon sizes=72x72 href=img/apple-touch-icon-72x72.png>"
                  + "<link rel=apple-touch-icon sizes=114x114 href=img/apple-touch-icon-114x114.png>"
                  + "<link rel=stylesheet type=text/css  href=css/bootstrap.css>"
                  + "<link rel=stylesheet type=text/css href=fonts/font-awesome/css/font-awesome.css>"
                  + "<link href=css/owl.carousel.css rel=stylesheet media=screen>"
                  + "<link href=css/owl.theme.css rel=stylesheet media=screen>"
                  + "<link rel=stylesheet type=text/css href=css/style.css>"
                  + "<link rel=stylesheet type=text/css href=css/nivo-lightbox/nivo-lightbox.css>"
                  + "<link rel=stylesheet type=text/css href=css/nivo-lightbox/default.css>"
                  + "<link href=https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700 rel=stylesheet>");

         out.println("<div id=testimonials class=text-center>"
         + "<div class=overlay>"
         + "<div class=container>"
         + "<div class=section-title>");
         
          out.println("<center>");
         if(rs.next())
         {
          out.println("<h1>Welcome Admin"+"</h1><hr>");
         }
         

         out.println("<br><br><table border = 5><tr bgcolor = #abbcc align = center>"
                      + "<th>USER ID</th>"
                      + "<th>NAME</th>"
                      + "<th>DOB</th>"
                      + "<th>MODEL NO</th>"
                      + "<th>CAR NAME</th>"
                      + "<th>DATE OF PURCHASE</th>"
                      + "<th>SERVICES LEFT</th>"
                      + "<th>DUE DATE</th>"
                      + "<th>BILL NO</th>"
                      + "</tr>");
              do{                     
                    out.println("<tr bgcolor = #ffaaa>"
                      + "<td>"+rs.getInt("userid")+"</td>"
                      + "<td>"+rs.getString("name")+"</td>"
                      + "<td>"+rs.getString("dob")+"</td>"
                      + "<td>"+rs.getInt("modelno")+"</td>"
                      + "<td>"+rs.getString("carname")+"</td>"
                      + "<td>"+rs.getString("dateofpurchase")+"</td>"
                      + "<td>"+rs.getInt("servicesleft")+"</td>"
                      + "<td>"+rs.getString("duedate")+"</td>"
                      + "<td>"+rs.getInt("billno")+"</td>"
                      + "</tr>");
                                     
              }while(rs.next());
                   
             out.println("</table>");

             
             out.println("</center>");
             
          
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
