package geolaxia.computecnica;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


/**
 * Created by uriel on 15/7/2018.
 */

public class MailService {
    private MainActivity context;

    public MailService(MainActivity context){
        this.context = context;
    }

    public void sendMail(String subject, String messageBody) {
        try {
            Mail message = createMessage(subject, messageBody);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Mail createMessage(String subject, String messageBody) throws MessagingException, UnsupportedEncodingException {
        String emailTo = "service@computecnica.com.ar";
        Mail mail = new Mail("computecnica.arg", "serv2018");
        mail.setFrom("computecnica.arg@gmail.com");
        mail.setTo(emailTo.split(";"));
        mail.setSubject(subject);
        mail.setBody(messageBody);
        return mail;
    }

    private Mail createMessage(Mail otherMail) throws MessagingException, UnsupportedEncodingException {
        String emailTo = otherMail.getTo()[0];
        Mail mail = new Mail("computecnica.arg", "serv2018");
        mail.setFrom(otherMail.getFrom());
        mail.setTo(otherMail.getTo());
        mail.setSubject(otherMail.getSubject());
        mail.setBody(otherMail.getBody());
        return mail;
    }

    private class SendMailTask extends AsyncTask<Mail, Void, Boolean> {
        private ProgressDialog progressDialog;

        public SendMailTask(){
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "Espere por favor", "Enviando solicitud", true, false);
        }

        @Override
        protected void onPostExecute(Boolean sendOk) {
            try{
                super.onPostExecute(sendOk);
                progressDialog.dismiss();
                context.ShowResult(true);
            }catch (Exception e){
                context.ShowResult(false);
            }
        }

        @Override
        protected Boolean doInBackground(Mail... mails) {
            try {
                Mail mail = createMessage(mails[0]);
                mail.send();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
