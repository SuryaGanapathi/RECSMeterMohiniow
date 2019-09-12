package com.recsmeterreading.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.recsmeterreading.model.ServiceNumber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PdfDownloader extends FileProvider {
    private Context context;

    public PdfDownloader() {
    }

    public PdfDownloader(Context context) {
        this.context = context;
    }

    private void createandDisplayPdf(String unbilledServises, List<ServiceNumber> services) {

        Document doc = new Document();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String pdfName = unbilledServises
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        try {
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Meter";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();



            File file = new File(dir, pdfName);
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            for(int i=0; i<services.size(); i++){
                Paragraph p1 = new Paragraph((i+1)+". Service Number "+" : "+services.get(i).getValue());
                Font paraFont= new Font(Font.FontFamily.COURIER);
                p1.setAlignment(Paragraph.ALIGN_LEFT);
                p1.setFont(paraFont);

                //add paragraph to document
                doc.add(p1);
            }


//            Toast.makeText(this,""+path,Toast.LENGTH_LONG).show();
            Log.e("PdfDownloader",""+path);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf(pdfName, "Meter");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
//            Toast.makeText(this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
            Log.e("PdfDownloader","Can't read pdf file-;");
        }
    }
}
