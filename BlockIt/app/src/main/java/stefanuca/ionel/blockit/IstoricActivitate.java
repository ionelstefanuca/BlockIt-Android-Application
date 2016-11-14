package stefanuca.ionel.blockit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by ionel on 11/29/2015.
 */
public class IstoricActivitate extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPreferences model = getSharedPreferences("CallIstoricSMS", 0);

        CustomAdapterHistory adapter = new CustomAdapterHistory(this,model);
        ListView theListView = (ListView) findViewById(R.id.history_list);
        theListView.setAdapter(adapter);

    }
}
