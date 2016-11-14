package stefanuca.ionel.blockit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ionel on 11/29/2015.
 */
public class CustomAdapterHistory extends BaseAdapter {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    private ArrayList<DataModelHistory> listArray;
    private SharedPreferences pref;
    private Context context;

    CustomAdapterHistory( Context con,SharedPreferences pref)
    {
        this.context=con;
        this.pref=pref;
        this.actualizareArray();
    }


    @Override
    public int getCount() {
        return listArray.size();    // total number of elements in the list
    }

    @Override
    public Object getItem(int i) {
        return listArray.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i;                   // index number
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.my_history_list, parent, false);
        }

        if(index%4==0)
            view.setBackgroundResource(R.drawable.button_effect1);
        else
        if(index%4==1)
            view.setBackgroundResource(R.drawable.button_effect2);
        else
        if(index%4==2)
            view.setBackgroundResource(R.drawable.button_effect3);
        else
            view.setBackgroundResource(R.drawable.button_effect4);



        final DataModelHistory dataModel = listArray.get(index);

        //setam numarul
        TextView textView = (TextView) view.findViewById(R.id.history_numar);
        textView.setText(dataModel.getNumar());

        //setam continutul si poza pentru sms-uri
        ImageView img1 =(ImageView)view.findViewById(R.id.imageView1);
        if(dataModel.getTip().equals("call")) {
            img1.setBackgroundResource(R.drawable.history_call);
            textView = (TextView) view.findViewById(R.id.history_mesaj);
            textView.setText(dataModel.getContinut15());
        }

        if(dataModel.getTip().equals("sms")) {
            textView = (TextView) view.findViewById(R.id.history_mesaj);
            textView.setText(dataModel.getContinut15());
            ImageView img =(ImageView)view.findViewById(R.id.imageView1);
            img.setBackgroundResource(R.drawable.history_sms);
        }


        //setam data
        textView = (TextView) view.findViewById(R.id.history_date);
        textView.setText("Data: "+dataModel.getData());

       Button button = (Button)view.findViewById(R.id.history_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(dataModel.getData());
                editor.commit();
                actualizareArray();
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataModel.getTip().equals("sms"))
                {
                    Intent intent = new Intent(context,CitesteSMsIntreg.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("data", dataModel.getData());
                    intent.putExtra("mesajul", dataModel.getContinut());
                    intent.putExtra("numarul", dataModel.getNumar());

                    context.startActivity(intent);
                }

            }
        });


        return view;
    }



    private void actualizareArray()
    {
        listArray= new ArrayList<>();

        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            listArray.add(new DataModelHistory(entry.getValue().toString(),entry.getKey().toString()));
        }


       this.notifyDataSetChanged();
    }

}