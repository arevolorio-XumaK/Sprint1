/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.jcr.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jackrabbit.commons.JcrUtils;

/**
 *
 * @author xumakgt5
 */
@MultipartConfig
@WebServlet(urlPatterns = {"/createNewUser"})
public class createNewUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(null != response){
            if(request != null){
                String userName = request.getParameter("user");
                String password = request.getParameter("pass");
                String confPassword = request.getParameter("confPass");
                String show = autentication(userName,password,confPassword);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                try {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet createNewUser</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<center><h1>File Server</h1></center>");
                    out.println("<center>" + show + "</center>");
                    out.println("</body>");
                    out.println("</html>");
                } finally {
                    out.close();
                }
            } else {
                System.out.println("request empty");
            }
        } else {
            System.out.println("response empty");
        }
    }
    protected String autentication(String userName, String pass, String confPass){
        try{
            String message = "";
            String url = "http://localhost:8080/rmi";
            Repository repository = JcrUtils.getRepository(url);
            System.out.println("verify");
            /**
            * Enter to jackrabbit using the credentials admin:admin,password:admin
            */
            SimpleCredentials creds = new SimpleCredentials("admin","admin".toCharArray());
            Session jcrSession = repository.login(creds, "default");
            Node user = verifyName(userName,jcrSession);
            if(user != null){
                if(pass.equals(confPass)){
                    user.setProperty("password", pass);
                    message =  "The user " + userName + " is created.";
                } else {
                    System.out.println("the password don't match");
                    message = "the password and the confirm password don't match";
                }
            } else {
                System.out.println("The user already exists");
                message =  "The user already exists";
            }
            jcrSession.save();
            jcrSession.logout();
            return message;
        } catch(RepositoryException re){
            System.out.println(re);
            return "can't connect to repository";
        }
    }
    protected Node verifyName(String userName,Session jcrSession){
        try{
            Node root = jcrSession.getRootNode();
            if(root.hasNode("fileServer")){
                Node fileServer = root.getNode("fileServer");
                if(fileServer.hasNode("users")){
                    Node users = fileServer.getNode("users");
                    if(!users.hasNode(userName)){
                        Node currentUser = users.addNode(userName);
                        return currentUser;
                    } else {
                        return null;
                    }
                } else {
                    Node users = fileServer.addNode("users");
                    Node currentUser = users.addNode(userName);
                    return currentUser;
                }
            } else {
                Node fileServer = root.addNode("fileServer");
                Node users = fileServer.addNode("users");
                Node currentUser = users.addNode(userName);
                return currentUser;
            }
        } catch(RepositoryException re){
            System.out.println("Erron can't connect to jackrabbit");
            return null;
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
