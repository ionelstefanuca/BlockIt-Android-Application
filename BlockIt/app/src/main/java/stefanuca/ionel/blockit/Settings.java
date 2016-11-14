package stefanuca.ionel.blockit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by ionel on 11/29/2015.
 */
public class Settings extends Activity {
    private CheckBox sms_ln,call_ln,private_nr_call,block_call_no_list_contact,block_sms_no_list_contact,block_sms_with_forbidden_content;
    private Button btn;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SharedPreferences prefs = getSharedPreferences("settingsXML", Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor = prefs.edit();

        sms_ln = (CheckBox)findViewById(R.id.sms_ln);
        call_ln = (CheckBox)findViewById(R.id.call_ln);
        private_nr_call = (CheckBox)findViewById(R.id.private_nr_call);
        block_call_no_list_contact = (CheckBox)findViewById(R.id.block_call_no_list_contact);
        block_sms_no_list_contact = (CheckBox)findViewById((R.id.block_sms_no_list_contact));
        block_sms_with_forbidden_content = (CheckBox)findViewById((R.id.block_sms_with_forbidden_content));

        String s_sms_ln="";
        String s_call_l="";
        String s_private_nr_call="";
        String s_block_call_no_list_contact="";
        String s_block_sms_no_list_contact="";
        String s_block_sms_with_forbidden_content="";
        {
            s_sms_ln = prefs.getString("sms_lista_neagra","nu");
            if(s_sms_ln.equals("da"))
                sms_ln.setChecked(true);

            s_call_l = prefs.getString("call_lista_neagra","nu");
            if(s_call_l.equals("da"))
                call_ln.setChecked(true);

            s_private_nr_call = prefs.getString("private_nr_call","nu");
            if(s_private_nr_call.equals("da"))
                private_nr_call.setChecked(true);

            s_block_call_no_list_contact= prefs.getString("block_call_no_list_contact","nu");
            if(s_block_call_no_list_contact.equals("da"))
                 block_call_no_list_contact.setChecked(true);


            s_block_sms_no_list_contact = prefs.getString("block_sms_no_list_contact","nu");
            if(s_block_sms_no_list_contact.equals("da"))
                block_sms_no_list_contact.setChecked(true);

            s_block_sms_with_forbidden_content = prefs.getString("block_sms_with_forbidden_content","nu");
            if(s_block_sms_with_forbidden_content.equals("da"))
                block_sms_with_forbidden_content.setChecked(true);

        }



        //vom scrie in fisierul xml noile setari
        Button btn_l = (Button)findViewById(R.id.save_settings);
        btn_l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (sms_ln.isChecked()) {
                    editor.putString("sms_lista_neagra", "da");
                } else
                    editor.putString("sms_lista_neagra", "nu");


                if (call_ln.isChecked()) {
                    editor.putString("call_lista_neagra", "da");
                } else
                    editor.putString("call_lista_neagra", "nu");


                if (private_nr_call.isChecked()) {
                    editor.putString("private_nr_call", "da");
                } else
                    editor.putString("private_nr_call", "nu");


                if (block_call_no_list_contact.isChecked()) {
                    editor.putString("block_call_no_list_contact", "da");
                } else
                    editor.putString("block_call_no_list_contact", "nu");

                if (block_sms_no_list_contact.isChecked()) {
                    editor.putString("block_sms_no_list_contact", "da");
                } else
                    editor.putString("block_sms_no_list_contact", "nu");

                if (block_sms_with_forbidden_content.isChecked()) {
                    editor.putString("block_sms_with_forbidden_content", "da");
                } else
                    editor.putString("block_sms_with_forbidden_content", "nu");

                editor.commit();

                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
