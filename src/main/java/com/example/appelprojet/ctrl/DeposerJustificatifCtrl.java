package com.example.appelprojet.ctrl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@MultipartConfig(
        location="/tmp",
        fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5,
        maxRequestSize=1024*1024*5*5
)
//@WebServlet(name = "DeposerJustificatifCtrl", value = "/deposerJustificatifCtrl")
public class DeposerJustificatifCtrl extends HttpServlet {

    public static final String UPLOAD_DIRECTORY = "upload";
    public static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);
            String uploadPath = getServletContext().getRealPath("")
                    + File.separator + UPLOAD_DIRECTORY;
            //System.out.println(uploadPath);
            File uploadDir = new File(uploadPath);

            String message = "";
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            List<FileItem> formItems = null;
            try {
                formItems = upload.parseRequest(request);

            } catch (FileUploadException e) {
                message = "File has uploaded failed!";
                e.printStackTrace();
            }
            if (formItems == null){
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>File is too big!</h1>");
                out.println("</body></html>");;
            }
            if (formItems != null && formItems.size() > 0) {

                for (FileItem item : formItems) {

                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        System.out.println(filePath);
                        File storeFile = new File(filePath);

                        try {
                            item.write(storeFile);
                            message = "File "+ fileName + " has uploaded successfully!";
                        } catch (Exception e) {
                            e.printStackTrace();
                            message = "File has uploaded failed!";
                        }

                        RequestDispatcher rd = request.getRequestDispatcher("message");
                        HttpSession session = request.getSession(true);
                        session.setAttribute("message", message);

                        rd.forward(request, response);
                    }
                }
            }
        }

    }
}
