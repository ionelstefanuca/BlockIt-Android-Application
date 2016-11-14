package stefanuca.ionel.blockit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ionel on 11/29/2015.
 */
public class CitesteSMsIntreg extends Activity {
    private String numarul;
    private String mesajul;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citeste_sms_intreg);

        savedInstanceState = getIntent().getExtras();
         numarul = savedInstanceState.getString("numarul");
         mesajul = savedInstanceState.getString("mesajul");
         data = savedInstanceState.getString("data");

        TextView nrt = (TextView)findViewById(R.id.sms_txt1);
        nrt.setText("Numar telefon: "+numarul);

        TextView sms = (TextView)findViewById(R.id.sms_body);
        sms.setText(mesajul);

        TextView dataa = (TextView)findViewById(R.id.sms_data_);
        dataa.setText(data);
    }

    public void TrimiteSMS(View v) {
        Uri uri = Uri.parse("smsto:"+numarul);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);

        TextView body_msg = (TextView)findViewById(R.id.re_sms);

        it.putExtra("sms_body", body_msg.getText().toString());
        startActivity(it);
    }
}
