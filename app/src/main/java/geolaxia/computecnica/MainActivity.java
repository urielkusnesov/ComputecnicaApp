package geolaxia.computecnica;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private MailService mailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mailService = new MailService(this);
        checkForSmsPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Send(View view){
        EditText name = (EditText) findViewById(R.id.nameEditText);
        EditText phone = (EditText) findViewById(R.id.phoneEditText);
        EditText email = (EditText) findViewById(R.id.emailEditText);
        EditText detail = (EditText) findViewById(R.id.detailEditText);
        String subject = "Solicitud";
        String body = "Nueva solicitud \n Nombre: " + name.getText() + "\n Tel: " + phone.getText() + "\n Email: " + email.getText() + "\n Detalle: " + detail.getText();

        try{
            mailService.sendMail(subject, body);

            /*SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("01158709193", null, body, null, null);
            smsManager.sendTextMessage("01136670275", null, body, null, null);
            smsManager.sendTextMessage("01164182619", null, body, null, null);*/
        }catch (Exception ex){
            ShowResult(false);
        }
    }

    public void ShowResult(Boolean sendOk){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Solicitud");
        alertDialog.setMessage("La solicitud se envío con éxito");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}
