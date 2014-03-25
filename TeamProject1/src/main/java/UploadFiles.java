/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jackrabbit.commons.JcrUtils;

/**
 *
 * @author xumakgt5
 */
@WebServlet(urlPatterns = {"/UploadFiles"})
public class UploadFiles extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.jcr.RepositoryException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RepositoryException {
        Session jcrSession = repoLogin();
        if(jcrSession != null){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet UploadFiles</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet UploadFiles at " + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        } else {
            System.out.println("Error with session start");
        }
    }
    
    /**
     * This method is used to create a Session on jackrabbit repository.
     * 
     * @return a Session that is going to be used to enter to Session Nodes.
     * @throws NoSuchWorkspaceException
     * @throws RepositoryException 
     */
    protected Session repoLogin() throws NoSuchWorkspaceException, RepositoryException{
        try{
            String url = "http://localhost:8080/rmi";
            Repository repository = JcrUtils.getRepository(url);
            /**
            * Enter to jackrabbit using the credentials admin:admin,password:admin
            */
            SimpleCredentials creds = new SimpleCredentials("admin","admin".toCharArray());
            Session jcrSession = repository.login(creds, "default");
            Node root = jcrSession.getRootNode();
            return jcrSession;
        }catch(RepositoryException RE){
            System.out.println("Error with start Session of JackRabbit");
            return null;
        }
    }
    
    /**
     * This method is used to exit the session that is used in the program.
     * 
     * @param jcrSession receive the session that is going to be closed. 
     */
    protected void repoLogout(Session jcrSession){
        if(jcrSession != null){
            jcrSession.logout();
        } else {
            System.out.println("Session can't be closed");
        }
    }
    
    protected void startRepo(Session jcrSession){
        if(jcrSession != null){
            try{
                Node root = jcrSession.getRootNode();
                Node appNode;
                if(!root.hasNode("storefiles")){
                    appNode = root.addNode("storefiles");
                    if(!appNode.hasNode("storefiles/images")){
                        appNode.addNode("images");              
                    }
                    if(!appNode.hasNode("storefiles/music")){
                        appNode.addNode("music");               
                    }
                    if(!appNode.hasNode("storefiles/documents")){
                        appNode.addNode("documents");                
                    }
                    if(!appNode.hasNode("storefiles/others")){
                        appNode.addNode("others");              
                    }
                }
            }catch(RepositoryException re){
                System.out.println("Error:" + re);
            }
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
        try {
            processRequest(request, response);
        } catch (RepositoryException ex) {
            Logger.getLogger(UploadFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (RepositoryException ex) {
            Logger.getLogger(UploadFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
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
