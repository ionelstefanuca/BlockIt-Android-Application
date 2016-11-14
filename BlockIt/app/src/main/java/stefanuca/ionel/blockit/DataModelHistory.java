package stefanuca.ionel.blockit;

import java.util.StringTokenizer;

/**
 * Created by ionel on 11/29/2015.
 */
public class DataModelHistory {
    private String tip;
    private String numar;
    private String continut;
    private String data;

    DataModelHistory(String s,String data)
    {
        this.data=data;
        StringTokenizer st = new StringTokenizer(s, "[/]");
        this.tip =st.nextToken();
        this.numar=st.nextToken();
        this.continut=st.nextToken();
    }

    String getTip()
    {
        return this.tip;
    }

    String getNumar()
    {
        return this.numar;
    }

    String getContinut15()
    {
        String str=this.continut+"...";
        if(continut.length()>15)
        {
            str= this.continut.substring(0,15);
            str+="...";
        }
        return str;
    }

    String getContinut()
    {
        return this.continut;
    }


    String getData()
    {
        return this.data;
    }
}
