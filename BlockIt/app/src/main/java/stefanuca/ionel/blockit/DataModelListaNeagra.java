package stefanuca.ionel.blockit;

/**
 * Created by ionel on 11/28/2015.
 */
public class DataModelListaNeagra {
    private String numarTelefon;
    private String numeDetinator;

    DataModelListaNeagra(String nume,String tel)
    {
        this.numarTelefon=tel;
        this.numeDetinator=nume;
    }

    String getNumarTelefon()
    {
        return this.numarTelefon;
    }

    String getNumeDetinator()
    {
        return this.numeDetinator;
    }
}
