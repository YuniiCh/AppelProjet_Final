package com.example.appelprojet.ctrl;
import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.EtudiantDAO;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.EtatValidation;
import com.example.appelprojet.util.EtatVerifier;
import com.example.appelprojet.util.FontionsUtiles;
import com.example.appelprojet.util.FontionsUtiles;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet(name = "UploadCtrl", value = "/uploadCtrl")
@MultipartConfig(
        location="/tmp",
        fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5,
        maxRequestSize=1024*1024*5*5
)
public class UploadCtrl extends HttpServlet {
    public static final String UPLOAD_DIRECTORY = "upload";
    public static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // données
        List<Long> seanceSelected = new ArrayList<>();
        Long idE = null;
        String nameJ ="";
        String fileName = "";
        String message = "";
        HttpSession session = request.getSession(true);
        session.setAttribute("action","pageUpload");

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);
            String uploadPath = getServletContext().getRealPath("")
                    + File.separator + UPLOAD_DIRECTORY; //".\\src\\main\\webapp\\jFiles";
            //System.out.println(uploadPath);
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            List<FileItem> formItems = null;

            try {
                formItems = upload.parseRequest(request);
            } catch (FileUploadException e) {
                message = "File has uploaded failed1!";
                e.printStackTrace();
            }

            if (formItems == null){
                RequestDispatcher rd = request.getRequestDispatcher("message");
                message = "File is too big!";
                session.setAttribute("message", message);
                rd.forward(request, response);
            }

            if (formItems != null && formItems.size() > 0) {

                // cherche le file
                for (FileItem item : formItems) {

                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + "\\" + fileName;
                        System.out.println(filePath);
                        File storeFile = new File(filePath);

                        try {
                            item.write(storeFile);
                            message = "File " + fileName + " has uploaded successfully!";
                        } catch (Exception e) {
                            e.printStackTrace();
                            message = "File has uploaded failed2!";
                        }

                    }else {
                        String fieldname = item.getFieldName();

                        if (fieldname.equals("nameJ")){
                            nameJ = item.getString();
                        }else if(fieldname.equals("idE")){
                            idE = Long.valueOf(item.getString());
                        }else if(fieldname.equals("idSSelect")) {
                            String idSS = item.getString();
                            if(idSS.endsWith("/")){idSS = idSS.substring(0, idSS.length() - 1);}
                            seanceSelected.add(Long.valueOf(idSS));
                        }
                    }
                }

                if (seanceSelected.isEmpty() || idE == null || nameJ.equals("") || fileName.equals("") ){
                    RequestDispatcher rd = request.getRequestDispatcher("message");
                    message = "Upload failed because of missed info";
                    session.setAttribute("message", message);
                    rd.forward(request, response);
                }

                Etudiant etudiant = null;
                Scolarite scolarite = null;
                Justificatif justificatif = null;
                try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
                    Transaction t = sn.beginTransaction();

                    etudiant = sn.get(Etudiant.class, idE);
                    t.commit();

                    // enregistrer le justificatif
                    scolarite = new EtudiantDAO().findScoByEtu(etudiant);
                }

                try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
                    Transaction t = sn.beginTransaction();
                    // Send email
                    FontionsUtiles.sendJustiMail(scolarite);
                    justificatif = new Justificatif(new Date(), EtatVerifier.ATTENDRE, fileName, nameJ, scolarite, etudiant);

                    sn.save(justificatif);
                    t.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                }

                try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
                    Transaction t = sn.beginTransaction();

                    // enregistrer dans le table Justifier
                    for (Long idS : seanceSelected) {
                        sn.saveOrUpdate(etudiant);
                        Seance seance = sn.get(Seance.class, idS);
                        Justifier justifier1 = new Justifier(new JustifierID(justificatif.getIdJ(),seance.getIdSeance()), EtatValidation.NULL);
                        seance.getJustifiers().put(justificatif,justifier1);
                        justificatif.getJustifiers().put(seance,justifier1);

                        // Update le presence en cas de déposer Justificatif avant le seance
                        if(seance.getDateDebut().after(justificatif.getDateDepot())){
                            Presence p = etudiant.getSeanPresences().get(seance);
                            p.setEtatPresence(EtatPresence.ABSENCE_SIGNALE);
                            sn.saveOrUpdate(p);
                        }
                    }
                    t.commit();
                    sn.close();
                }

                RequestDispatcher rd = request.getRequestDispatcher("message");
                session.setAttribute("message", message);
                rd.forward(request, response);

            }
        }

    }

}

