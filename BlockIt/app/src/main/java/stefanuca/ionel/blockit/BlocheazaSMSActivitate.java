package stefanuca.ionel.blockit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ionel on 11/28/2015.
 */
public class BlocheazaSMSActivitate extends Activity {
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blocheaza_sms_uri);


        SharedPreferences model = getSharedPreferences("cuvinteFrazeSMSUri", 0);
        final CustomAdapterSMSlist adapter = new CustomAdapterSMSlist(model);
        setList();
        adapter.setArrayModel(list);
        adapter.notifyDataSetChanged();
        final ListView theListView = (ListView) findViewById(R.id.listViewSms);
        theListView.setAdapter(adapter);


        Button buttonX = (Button)findViewById(R.id.btn_cuv_sms);
        buttonX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                TextView textVi = (TextView)findViewById(R.id.cuvinte_sms);

                if(textVi.getText().length()>0) {
                    SharedPreferences model = getSharedPreferences("cuvinteFrazeSMSUri", 0);
                    SharedPreferences.Editor editor = model.edit();
                    editor.putString(textVi.getText().toString(), textVi.getText().toString());
                    editor.commit();
                    setList();
                    adapter.setArrayModel(list);
                    adapter.notifyDataSetChanged();
                    textVi.setText("");
                }
            }
        });
    }

    private void setList()
    {
        SharedPreferences model = getSharedPreferences("cuvinteFrazeSMSUri", 0);
        SharedPreferences.Editor editor = model.edit();
        list= new ArrayList <>();

        Map<String, ?> prefMap=model.getAll();
        for(String key:prefMap.keySet()) {
            this.list.add(key);
        }
    }

}
