package stefanuca.ionel.blockit;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ionel on 11/28/2015.
 */
public class CustomAdapterSMSlist extends BaseAdapter {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    private ArrayList<String> listArray;
    private final SharedPreferences pref;

    CustomAdapterSMSlist(SharedPreferences pref)
    {
        this.pref=pref;
    }

    void setArrayModel(ArrayList<String> list)
    {
        this.listArray=list;
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
            view = inflater.inflate(R.layout.my_sms_list, parent, false);


            if(index%2==0)
                view.setBackgroundResource(R.drawable.button_effect3);
            else
                view.setBackgroundResource(R.drawable.button_effect2);

        }

        final String dataModel = listArray.get(index);

        TextView textView = (TextView) view.findViewById(R.id.textView4);
        textView.setText(dataModel);


        Button button = (Button)view.findViewById(R.id.btn_sms_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(dataModel);
                editor.commit();

                listArray.remove(dataModel);
                notifyDataSetChanged();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

}